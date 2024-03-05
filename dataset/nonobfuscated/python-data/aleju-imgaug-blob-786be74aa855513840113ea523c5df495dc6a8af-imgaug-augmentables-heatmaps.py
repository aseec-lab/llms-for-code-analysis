from __future__ import print_function, division, absolute_import

import numpy as np
import six.moves as sm

from .. import imgaug as ia


class HeatmapsOnImage(object):
    


    def __init__(self, arr, shape, min_value=0.0, max_value=1.0):
        

        ia.do_assert(ia.is_np_array(arr), "Expected numpy array as heatmap input array, got type %s" % (type(arr),))
        
        ia.do_assert(arr.shape[0] > 0 and arr.shape[1] > 0,
                     "Expected numpy array as heatmap with height and width greater than 0, got shape %s." % (
                         arr.shape,))
        ia.do_assert(arr.dtype.type in [np.float32],
                     "Heatmap input array expected to be of dtype float32, got dtype %s." % (arr.dtype,))
        ia.do_assert(arr.ndim in [2, 3], "Heatmap input array must be 2d or 3d, got shape %s." % (arr.shape,))
        ia.do_assert(len(shape) in [2, 3],
                     "Argument 'shape' in HeatmapsOnImage expected to be 2d or 3d, got shape %s." % (shape,))
        ia.do_assert(min_value < max_value)
        if np.min(arr.flat[0:50]) < min_value - np.finfo(arr.dtype).eps \
                or np.max(arr.flat[0:50]) > max_value + np.finfo(arr.dtype).eps:
            import warnings
            warnings.warn(
                ("Value range of heatmap was chosen to be (%.8f, %.8f), but "
                 "found actual min/max of (%.8f, %.8f). Array will be "
                 "clipped to chosen value range.") % (
                    min_value, max_value, np.min(arr), np.max(arr)))
            arr = np.clip(arr, min_value, max_value)

        if arr.ndim == 2:
            arr = arr[..., np.newaxis]
            self.arr_was_2d = True
        else:
            self.arr_was_2d = False

        eps = np.finfo(np.float32).eps
        min_is_zero = 0.0 - eps < min_value < 0.0 + eps
        max_is_one = 1.0 - eps < max_value < 1.0 + eps
        if min_is_zero and max_is_one:
            self.arr_0to1 = arr
        else:
            self.arr_0to1 = (arr - min_value) / (max_value - min_value)

        
        
        self.shape = shape

        self.min_value = min_value
        self.max_value = max_value

    def get_arr(self):
        

        if self.arr_was_2d and self.arr_0to1.shape[2] == 1:
            arr = self.arr_0to1[:, :, 0]
        else:
            arr = self.arr_0to1

        eps = np.finfo(np.float32).eps
        min_is_zero = 0.0 - eps < self.min_value < 0.0 + eps
        max_is_one = 1.0 - eps < self.max_value < 1.0 + eps
        if min_is_zero and max_is_one:
            return np.copy(arr)
        else:
            diff = self.max_value - self.min_value
            return self.min_value + diff * arr

    
    
    

    def draw(self, size=None, cmap="jet"):
        

        heatmaps_uint8 = self.to_uint8()
        heatmaps_drawn = []

        for c in sm.xrange(heatmaps_uint8.shape[2]):
            
            heatmap_c = heatmaps_uint8[..., c:c+1]

            if size is not None:
                heatmap_c_rs = ia.imresize_single_image(heatmap_c, size, interpolation="nearest")
            else:
                heatmap_c_rs = heatmap_c
            heatmap_c_rs = np.squeeze(heatmap_c_rs).astype(np.float32) / 255.0

            if cmap is not None:
                
                import matplotlib.pyplot as plt

                cmap_func = plt.get_cmap(cmap)
                heatmap_cmapped = cmap_func(heatmap_c_rs)
                heatmap_cmapped = np.delete(heatmap_cmapped, 3, 2)
            else:
                heatmap_cmapped = np.tile(heatmap_c_rs[..., np.newaxis], (1, 1, 3))

            heatmap_cmapped = np.clip(heatmap_cmapped * 255, 0, 255).astype(np.uint8)

            heatmaps_drawn.append(heatmap_cmapped)
        return heatmaps_drawn

    def draw_on_image(self, image, alpha=0.75, cmap="jet", resize="heatmaps"):
        

        
        ia.do_assert(image.ndim == 3)
        ia.do_assert(image.shape[2] == 3)
        ia.do_assert(image.dtype.type == np.uint8)

        ia.do_assert(0 - 1e-8 <= alpha <= 1.0 + 1e-8)
        ia.do_assert(resize in ["heatmaps", "image"])

        if resize == "image":
            image = ia.imresize_single_image(image, self.arr_0to1.shape[0:2], interpolation="cubic")

        heatmaps_drawn = self.draw(
            size=image.shape[0:2] if resize == "heatmaps" else None,
            cmap=cmap
        )

        mix = [
            np.clip((1-alpha) * image + alpha * heatmap_i, 0, 255).astype(np.uint8)
            for heatmap_i
            in heatmaps_drawn
        ]

        return mix

    def invert(self):
        

        arr_inv = HeatmapsOnImage.from_0to1(1 - self.arr_0to1, shape=self.shape, min_value=self.min_value,
                                            max_value=self.max_value)
        arr_inv.arr_was_2d = self.arr_was_2d
        return arr_inv

    def pad(self, top=0, right=0, bottom=0, left=0, mode="constant", cval=0.0):
        

        arr_0to1_padded = ia.pad(self.arr_0to1, top=top, right=right, bottom=bottom, left=left, mode=mode, cval=cval)
        return HeatmapsOnImage.from_0to1(arr_0to1_padded, shape=self.shape, min_value=self.min_value,
                                         max_value=self.max_value)

    def pad_to_aspect_ratio(self, aspect_ratio, mode="constant", cval=0.0, return_pad_amounts=False):
        

        arr_0to1_padded, pad_amounts = ia.pad_to_aspect_ratio(self.arr_0to1, aspect_ratio=aspect_ratio, mode=mode,
                                                              cval=cval, return_pad_amounts=True)
        heatmaps = HeatmapsOnImage.from_0to1(arr_0to1_padded, shape=self.shape, min_value=self.min_value,
                                             max_value=self.max_value)
        if return_pad_amounts:
            return heatmaps, pad_amounts
        else:
            return heatmaps

    def avg_pool(self, block_size):
        

        arr_0to1_reduced = ia.avg_pool(self.arr_0to1, block_size, cval=0.0)
        return HeatmapsOnImage.from_0to1(arr_0to1_reduced, shape=self.shape, min_value=self.min_value,
                                         max_value=self.max_value)

    def max_pool(self, block_size):
        

        arr_0to1_reduced = ia.max_pool(self.arr_0to1, block_size)
        return HeatmapsOnImage.from_0to1(arr_0to1_reduced, shape=self.shape, min_value=self.min_value,
                                         max_value=self.max_value)

    @ia.deprecated(alt_func="HeatmapsOnImage.resize()",
                   comment="resize() has the exactly same interface.")
    def scale(self, *args, **kwargs):
        return self.resize(*args, **kwargs)

    def resize(self, sizes, interpolation="cubic"):
        

        arr_0to1_resized = ia.imresize_single_image(self.arr_0to1, sizes, interpolation=interpolation)

        
        
        
        arr_0to1_resized = np.clip(arr_0to1_resized, 0.0, 1.0)

        return HeatmapsOnImage.from_0to1(arr_0to1_resized, shape=self.shape, min_value=self.min_value,
                                         max_value=self.max_value)

    def to_uint8(self):
        

        
        
        arr_0to255 = np.clip(np.round(self.arr_0to1 * 255), 0, 255)
        arr_uint8 = arr_0to255.astype(np.uint8)
        return arr_uint8

    @staticmethod
    def from_uint8(arr_uint8, shape, min_value=0.0, max_value=1.0):
        

        arr_0to1 = arr_uint8.astype(np.float32) / 255.0
        return HeatmapsOnImage.from_0to1(arr_0to1, shape, min_value=min_value, max_value=max_value)

    @staticmethod
    def from_0to1(arr_0to1, shape, min_value=0.0, max_value=1.0):
        

        heatmaps = HeatmapsOnImage(arr_0to1, shape, min_value=0.0, max_value=1.0)
        heatmaps.min_value = min_value
        heatmaps.max_value = max_value
        return heatmaps

    @classmethod
    def change_normalization(cls, arr, source, target):
        

        ia.do_assert(ia.is_np_array(arr))

        if isinstance(source, HeatmapsOnImage):
            source = (source.min_value, source.max_value)
        else:
            ia.do_assert(isinstance(source, tuple))
            ia.do_assert(len(source) == 2)
            ia.do_assert(source[0] < source[1])

        if isinstance(target, HeatmapsOnImage):
            target = (target.min_value, target.max_value)
        else:
            ia.do_assert(isinstance(target, tuple))
            ia.do_assert(len(target) == 2)
            ia.do_assert(target[0] < target[1])

        
        
        
        eps = np.finfo(arr.dtype).eps
        mins_same = source[0] - 10*eps < target[0] < source[0] + 10*eps
        maxs_same = source[1] - 10*eps < target[1] < source[1] + 10*eps
        if mins_same and maxs_same:
            return np.copy(arr)

        min_source, max_source = source
        min_target, max_target = target

        diff_source = max_source - min_source
        diff_target = max_target - min_target

        arr_0to1 = (arr - min_source) / diff_source
        arr_target = min_target + arr_0to1 * diff_target

        return arr_target

    def copy(self):
        

        return self.deepcopy()

    def deepcopy(self):
        

        return HeatmapsOnImage(self.get_arr(), shape=self.shape, min_value=self.min_value, max_value=self.max_value)
