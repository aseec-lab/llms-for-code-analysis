










import numpy            as np
import nibabel          as nib
import scipy.ndimage    as scn
from   collections      import OrderedDict

from   .check           import check_img_compatibility, repr_imgs, check_img
from   .read            import read_img, get_img_data, get_img_info
from   .mask            import binarise, load_mask
from   ..utils.strings  import search_list


def drain_rois(img):
    

    img_data = get_img_data(img)

    out = np.zeros(img_data.shape, dtype=img_data.dtype)

    krn_dim = [3] * img_data.ndim
    kernel  = np.ones(krn_dim, dtype=int)

    vals = np.unique(img_data)
    vals = vals[vals != 0]

    for i in vals:
        roi  = img_data == i
        hits = scn.binary_hit_or_miss(roi, kernel)
        roi[hits] = 0
        out[roi > 0] = i

    return out


def pick_rois(rois_img, roi_values, bg_val=0):
    

    img = read_img(rois_img)
    img_data = img.get_data()

    if bg_val == 0:
        out = np.zeros(img_data.shape, dtype=img_data.dtype)
    else:
        out = np.ones(img_data.shape, dtype=img_data.dtype) * bg_val

    for r in roi_values:
        out[img_data == r] = r

    return nib.Nifti2Image(out, affine=img.affine, header=img.header)


def largest_connected_component(volume):
    

    
    volume = np.asarray(volume)
    labels, num_labels = scn.label(volume)
    if not num_labels:
        raise ValueError('No non-zero values: no connected components found.')

    if num_labels == 1:
        return volume.astype(np.bool)

    label_count = np.bincount(labels.ravel().astype(np.int))
    
    label_count[0] = 0
    return labels == label_count.argmax()


def large_clusters_mask(volume, min_cluster_size):
    

    labels, num_labels = scn.label(volume)

    labels_to_keep = set([i for i in range(num_labels)
                         if np.sum(labels == i) >= min_cluster_size])

    clusters_mask = np.zeros_like(volume, dtype=int)
    for l in range(num_labels):
        if l in labels_to_keep:
            clusters_mask[labels == l] = 1

    return clusters_mask


def create_rois_mask(roislist, filelist):
    

    roifiles = []

    for roi in roislist:
        try:
            roi_file = search_list(roi, filelist)[0]
        except Exception as exc:
            raise Exception('Error creating list of roi files. \n {}'.format(str(exc)))
        else:
            roifiles.append(roi_file)

    return binarise(roifiles)


def get_unique_nonzeros(arr):
    

    rois = np.unique(arr)
    rois = rois[np.nonzero(rois)]
    rois.sort()

    return rois


def get_roilist_from_atlas(atlas_img):
    

    return get_unique_nonzeros(check_img(atlas_img).get_data())


def get_rois_centers_of_mass(vol):
    

    from scipy.ndimage.measurements import center_of_mass

    roisvals = np.unique(vol)
    roisvals = roisvals[roisvals != 0]

    rois_centers = OrderedDict()
    for r in roisvals:
        rois_centers[r] = center_of_mass(vol, vol, r)

    return rois_centers


def partition_timeseries(image, roi_img, mask_img=None, zeroe=True, roi_values=None, outdict=False):
    

    img  = read_img(image)
    rois = read_img(roi_img)

    
    check_img_compatibility(img, rois, only_check_3d=True)

    
    roi_data = rois.get_data()
    if roi_values is not None:
        for rv in roi_values:
            if not np.any(roi_data == rv):
                raise ValueError('Could not find value {} in rois_img {}.'.format(rv, repr_imgs(roi_img)))
    else:
        roi_values = get_unique_nonzeros(roi_data)

    
    if mask_img is None:
        mask_data = None
    else:
        mask = load_mask(mask_img)
        check_img_compatibility(img, mask, only_check_3d=True)
        mask_data = mask.get_data()

    
    if outdict:
        extract_data = _extract_timeseries_dict
    else:
        extract_data = _extract_timeseries_list

    
    try:
        return extract_data(img.get_data(), rois.get_data(), mask_data,
                            roi_values=roi_values, zeroe=zeroe)
    except:
        raise


def partition_volume(*args, **kwargs):
    

    return partition_timeseries(*args, **kwargs)


def _check_for_partition(datavol, roivol, maskvol=None):
    if datavol.ndim != 4 and datavol.ndim != 3:
        raise AttributeError('Expected a volume with 3 or 4 dimensions. '
                             '`datavol` has {} dimensions.'.format(datavol.ndim))

    if datavol.shape[:3] != roivol.shape:
        raise AttributeError('Expected a ROI volume with the same 3D shape as the timeseries volume. '
                             'In this case, datavol has shape {} and roivol {}.'.format(datavol.shape, roivol.shape))

    if maskvol is not None:
        if datavol.shape[:3] != maskvol.shape:
            raise AttributeError('Expected a mask volume with the same 3D shape as the timeseries volume. '
                                 'In this case, datavol has shape {} and maskvol {}.'.format(datavol.shape,
                                                                                             maskvol.shape))


def _partition_data(datavol, roivol, roivalue, maskvol=None, zeroe=True):
    

    if maskvol is not None:
        
        indices = (roivol == roivalue) * (maskvol > 0)
    else:
        
        indices = roivol == roivalue

    if datavol.ndim == 4:
        ts = datavol[indices, :]
    else:
        ts = datavol[indices]

    
    if zeroe:
        if datavol.ndim == 4:
            ts = ts[ts.sum(axis=1) != 0, :]

    return ts


def _extract_timeseries_dict(tsvol, roivol, maskvol=None, roi_values=None, zeroe=True):
    

    _check_for_partition(tsvol, roivol, maskvol)

    
    if roi_values is None:
        roi_values = get_unique_nonzeros(roivol)

    ts_dict = OrderedDict()
    for r in roi_values:
        ts = _partition_data(tsvol, roivol, r, maskvol, zeroe)

        if len(ts) == 0:
            ts = np.zeros(tsvol.shape[-1])

        ts_dict[r] = ts

    return ts_dict


def _extract_timeseries_list(tsvol, roivol, maskvol=None, roi_values=None, zeroe=True):
    

    _check_for_partition(tsvol, roivol, maskvol)

    if roi_values is None:
        roi_values = get_unique_nonzeros(roivol)

    ts_list = []
    for r in roi_values:
        ts = _partition_data(tsvol, roivol, r, maskvol, zeroe)

        if len(ts) == 0:
            ts = np.zeros(tsvol.shape[-1])

        ts_list.append(ts)

    return ts_list


def get_3D_from_4D(image, vol_idx=0):
    

    img      = check_img(image)
    hdr, aff = get_img_info(img)

    if len(img.shape) != 4:
        raise AttributeError('Volume in {} does not have 4 dimensions.'.format(repr_imgs(img)))

    if not 0 <= vol_idx < img.shape[3]:
        raise IndexError('IndexError: 4th dimension in volume {} has {} volumes, '
                         'not {}.'.format(repr_imgs(img), img.shape[3], vol_idx))

    img_data = img.get_data()
    new_vol  = img_data[:, :, :, vol_idx].copy()

    hdr.set_data_shape(hdr.get_data_shape()[:3])

    return new_vol, hdr, aff
