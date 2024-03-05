
from peri.viz import base

from builtins import range

import matplotlib as mpl
import matplotlib.pylab as pl

from matplotlib import ticker
from matplotlib.gridspec import GridSpec
from matplotlib.offsetbox import AnchoredText
from mpl_toolkits.axes_grid1.inset_locator import zoomed_inset_axes
from mpl_toolkits.axes_grid1.inset_locator import mark_inset
from mpl_toolkits.axes_grid1 import ImageGrid
from matplotlib.patches import Circle, Rectangle

from peri.test import analyze
from peri import util
from peri.logger import log

import numpy as np
import time
import pickle
plt = mpl.pyplot





def lbl(axis, label, size=22):
    

    at = AnchoredText(label, loc=2, prop=dict(size=size), frameon=True)
    at.patch.set_boxstyle("round,pad=0.,rounding_size=0.0")
    
    
    
    
    
    
    axis.add_artist(at)

def summary_plot(state, samples, zlayer=None, xlayer=None, truestate=None):
    def MAD(d):
        return np.median(np.abs(d - np.median(d)))

    s = state
    t = s.get_model_image()

    if zlayer is None:
        zlayer = t.shape[0]//2

    if xlayer is None:
        xlayer = t.shape[2]//2

    mu = samples.mean(axis=0)
    std = samples.std(axis=0)

    fig, axs = pl.subplots(3,3, figsize=(20,12))
    axs[0][0].imshow(s.image[zlayer], vmin=0, vmax=1)
    axs[0][1].imshow(t[zlayer], vmin=0, vmax=1)
    axs[0][2].imshow((s.image-t)[zlayer], vmin=-1, vmax=1)
    axs[0][0].set_xticks([])
    axs[0][0].set_yticks([])
    axs[0][1].set_xticks([])
    axs[0][1].set_yticks([])
    axs[0][2].set_xticks([])
    axs[0][2].set_yticks([])

    axs[1][0].imshow(s.image[:,:,xlayer], vmin=0, vmax=1)
    axs[1][1].imshow(t[:,:,xlayer], vmin=0, vmax=1)
    axs[1][2].imshow((s.image-t)[:,:,xlayer], vmin=-1, vmax=1)
    axs[1][0].set_xticks([])
    axs[1][0].set_yticks([])
    axs[1][1].set_xticks([])
    axs[1][1].set_yticks([])
    axs[1][2].set_xticks([])
    axs[1][2].set_yticks([])

    try:
        alpha = 0.5 if truestate is not None else 0.8
        axs[2][0].hist(std[s.b_rad], bins=np.logspace(-3,0,50), label='Radii',
                histtype='stepfilled', alpha=alpha, color='red')
        if truestate is not None:
            d = np.abs(mu - truestate)
            axs[2][0].hist(d[s.b_pos], bins=np.logspace(-3,0,50), color='red',
                    histtype='step', alpha=1)
        axs[2][0].semilogx()

        axs[2][0].hist(std[s.b_pos], bins=np.logspace(-3,0,50), label='Positions',
                histtype='stepfilled', alpha=alpha, color='blue')
        if truestate is not None:
            d = np.abs(mu - truestate)
            axs[2][0].hist(d[s.b_rad], bins=np.logspace(-3,0,50), color='blue',
                    histtype='step', alpha=1)
        axs[2][0].semilogx()
        axs[2][0].legend(loc='upper right')
        axs[2][0].set_xlabel("Estimated standard deviation")
        axs[2][0].set_ylim(bottom=0)
    except Exception as e:
        pass

    d = s.state[s.b_rad]
    m = 2*1.4826 * MAD(d)
    mb = d.mean()

    d = d[(d > mb - m) & (d < mb +m)]
    d = s.state[s.b_rad]
    axs[2][1].hist(d, bins=50, histtype='stepfilled', alpha=0.8)
    axs[2][1].set_xlabel("Radii")
    axs[2][1].set_ylim(bottom=0)

    if truestate is not None:
        axs[2][1].hist(truestate[s.b_rad], bins=50, histtype='step', alpha=0.8)

    axs[2][2].hist((s.image-t)[s.image_mask==1].ravel(), bins=150,
            histtype='stepfilled', alpha=0.8)
    axs[2][2].set_xlim(-0.35, 0.35)
    axs[2][2].semilogy()
    axs[2][2].set_ylim(bottom=0)
    axs[2][2].set_xlabel("Pixel value differences")

    pl.subplots_adjust(left=0, right=1, bottom=0, top=1, wspace=0.05, hspace=0.05)
    pl.tight_layout()

def pretty_summary(state, samples, zlayer=None, xlayer=None, vertical=False):
    s = state
    h = np.array(samples)

    slicez = zlayer or s.image.shape[0]//2
    slicex = xlayer or s.image.shape[2]//2
    slicer1 = np.s_[slicez,s.pad:-s.pad,s.pad:-s.pad]
    slicer2 = np.s_[s.pad:-s.pad,s.pad:-s.pad,slicex]
    center = (slicez, s.image.shape[1]//2, slicex)

    if vertical:
        fig = pl.figure(figsize=(12,24))
    else:
        fig = pl.figure(figsize=(24,8))

    
    
    if vertical:
        gs1 = ImageGrid(fig, rect=[0.02, 0.55, 0.99, 0.40], nrows_ncols=(2,3), axes_pad=0.1)
    else:
        gs1 = ImageGrid(fig, rect=[0.02, 0.0, 0.4, 1.00], nrows_ncols=(2,3), axes_pad=0.1)

    for i,slicer in enumerate([slicer1, slicer2]):
        ax_real = gs1[3*i+0]
        ax_fake = gs1[3*i+1]
        ax_diff = gs1[3*i+2]

        diff = s.get_model_image() - s.image
        ax_real.imshow(s.image[slicer], cmap=pl.cm.bone_r)
        ax_real.set_xticks([])
        ax_real.set_yticks([])
        ax_fake.imshow(s.get_model_image()[slicer], cmap=pl.cm.bone_r)
        ax_fake.set_xticks([])
        ax_fake.set_yticks([])
        ax_diff.imshow(diff[slicer], cmap=pl.cm.RdBu, vmin=-1.0, vmax=1.0)
        ax_diff.set_xticks([])
        ax_diff.set_yticks([])

        if i == 0:
            ax_real.set_title("Confocal image", fontsize=24)
            ax_fake.set_title("Model image", fontsize=24)
            ax_diff.set_title("Difference", fontsize=24)
            ax_real.set_ylabel('x-y')
        else:
            ax_real.set_ylabel('x-z')

    
    
    mu = h.mean(axis=0)
    std = h.std(axis=0)

    if vertical:
        gs2 = GridSpec(2,2, left=0.10, bottom=0.10, right=0.99, top=0.52,
                wspace=0.45, hspace=0.45)
    else:
        gs2 = GridSpec(2,2, left=0.50, bottom=0.12, right=0.95, top=0.95,
                wspace=0.35, hspace=0.35)

    ax_hist = pl.subplot(gs2[0,0])
    ax_hist.hist(std[s.b_pos], bins=np.logspace(-2.5, 0, 50), alpha=0.7, label='POS', histtype='stepfilled')
    ax_hist.hist(std[s.b_rad], bins=np.logspace(-2.5, 0, 50), alpha=0.7, label='RAD', histtype='stepfilled')
    ax_hist.set_xlim((10**-2.4, 1))
    ax_hist.semilogx()
    ax_hist.set_xlabel(r"$\bar{\sigma}$")
    ax_hist.set_ylabel(r"$P(\bar{\sigma})$")
    ax_hist.legend(loc='upper right')

    ax_diff = pl.subplot(gs2[0,1])
    ax_diff.hist((s.get_model_image() - s.image)[s.image_mask==1.].ravel(), bins=1000, histtype='stepfilled', alpha=0.7)
    ax_diff.semilogy()
    ax_diff.set_ylabel(r"$P(\delta)$")
    ax_diff.set_xlabel(r"$\delta = M_i - d_i$")
    ax_diff.locator_params(axis='x', nbins=5)

    pos = mu[s.b_pos].reshape(-1,3)
    rad = mu[s.b_rad]
    mask = analyze.trim_box(s, pos)
    pos = pos[mask]
    rad = rad[mask]

    gx, gy = analyze.gofr(pos, rad, mu[s.b_zscale][0], resolution=5e-2,mask_start=0.5)
    mask = gx < 5
    gx = gx[mask]
    gy = gy[mask]
    ax_gofr = pl.subplot(gs2[1,0])
    ax_gofr.plot(gx, gy, '-', lw=1)
    ax_gofr.set_xlabel(r"$r/d$")
    ax_gofr.set_ylabel(r"$g(r/d)$")
    ax_gofr.locator_params(axis='both', nbins=5)

    gx, gy = analyze.gofr(pos, rad, mu[s.b_zscale][0], method='surface')
    mask = gx < 5
    gx = gx[mask]
    gy = gy[mask]
    gy[gy <= 0.] = gy[gy>0].min()
    ax_gofrs = pl.subplot(gs2[1,1])
    ax_gofrs.plot(gx, gy, '-', lw=1)
    ax_gofrs.set_xlabel(r"$r/d$")
    ax_gofrs.set_ylabel(r"$g_{\rm{surface}}(r/d)$")
    ax_gofrs.locator_params(axis='both', nbins=5)
    ax_gofrs.grid(b=False, which='minor', axis='y')
    

    ylim = ax_gofrs.get_ylim()
    ax_gofrs.set_ylim(gy.min(), ylim[1])

    
    

def scan(im, cycles=1, sleep=0.3, vmin=0, vmax=1, cmap='bone'):
    pl.figure(1)
    pl.show()
    time.sleep(3)
    for c in range(cycles):
        for i, sl in enumerate(im):
            log.info('{}'.format(i))
            pl.clf()
            pl.imshow(sl, cmap=cmap, interpolation='nearest',
                    origin='lower', vmin=vmin, vmax=vmax)
            pl.draw()
            time.sleep(sleep)

def scan_together(im, p, delay=2, vmin=0, vmax=1, cmap='bone'):
    pl.figure(1)
    pl.show()
    time.sleep(3)
    z,y,x = p.T
    for i in range(len(im)):
        log.info('{}'.format(i))
        sl = im[i]
        pl.clf()
        pl.imshow(sl, cmap=cmap, interpolation='nearest', origin='lower',
                vmin=vmin, vmax=vmax)
        m = z.astype('int') == i
        pl.plot(x[m], y[m], 'o')
        pl.xlim(0, sl.shape[0])
        pl.ylim(0, sl.shape[1])
        pl.draw()
        time.sleep(delay)

def sample_compare(N, samples, truestate, burn=0):
    h = samples[burn:]
    strue = truestate

    mu = h.mean(axis=0)
    std = h.std(axis=0)
    pl.figure(figsize=(20,4))
    pl.errorbar(range(len(mu)), (mu-strue), yerr=5*std/np.sqrt(h.shape[0]),
            fmt='.', lw=0.15, alpha=0.5)
    pl.vlines([0,3*N-0.5, 4*N-0.5], -1, 1, linestyle='dashed', lw=4, alpha=0.5)
    pl.hlines(0, 0, len(mu), linestyle='dashed', lw=5, alpha=0.5)
    pl.xlim(0, len(mu))
    pl.ylim(-0.02, 0.02)
    pl.show()

def generative_model(s,x,y,z,r, factor=1.1):
    

    pl.close('all')

    slicez = int(round(z.mean()))
    slicex = s.image.shape[2]//2
    slicer1 = np.s_[slicez,s.pad:-s.pad,s.pad:-s.pad]
    slicer2 = np.s_[s.pad:-s.pad,s.pad:-s.pad,slicex]
    center = (slicez, s.image.shape[1]//2, slicex)

    fig = pl.figure(figsize=(factor*13,factor*10))

    
    
    gs1 = ImageGrid(fig, rect=[0.0, 0.6, 1.0, 0.35], nrows_ncols=(1,3),
            axes_pad=0.1)
    ax_real = gs1[0]
    ax_fake = gs1[1]
    ax_diff = gs1[2]

    diff = s.get_model_image() - s.image
    ax_real.imshow(s.image[slicer1], cmap=pl.cm.bone_r)
    ax_real.set_xticks([])
    ax_real.set_yticks([])
    ax_real.set_title("Confocal image", fontsize=24)
    ax_fake.imshow(s.get_model_image()[slicer1], cmap=pl.cm.bone_r)
    ax_fake.set_xticks([])
    ax_fake.set_yticks([])
    ax_fake.set_title("Model image", fontsize=24)
    ax_diff.imshow(diff[slicer1], cmap=pl.cm.RdBu, vmin=-0.1, vmax=0.1)
    ax_diff.set_xticks([])
    ax_diff.set_yticks([])
    ax_diff.set_title("Difference", fontsize=24)

    
    
    gs2 = ImageGrid(fig, rect=[0.1, 0.0, 0.4, 0.55], nrows_ncols=(3,2),
            axes_pad=0.1)
    ax_plt1 = fig.add_subplot(gs2[0])
    ax_plt2 = fig.add_subplot(gs2[1])
    ax_ilm1 = fig.add_subplot(gs2[2])
    ax_ilm2 = fig.add_subplot(gs2[3])
    ax_psf1 = fig.add_subplot(gs2[4])
    ax_psf2 = fig.add_subplot(gs2[5])

    c = int(z.mean()), int(y.mean())+s.pad, int(x.mean())+s.pad
    if s.image.shape[0] > 2*s.image.shape[1]//3:
        w = s.image.shape[2] - 2*s.pad
        h = 2*w//3
    else:
        h = s.image.shape[0] - 2*s.pad
        w = 3*h//2

    w,h = w//2, h//2
    xyslice = np.s_[slicez, c[1]-h:c[1]+h, c[2]-w:c[2]+w]
    yzslice = np.s_[c[0]-h:c[0]+h, c[1]-w:c[1]+w, slicex]

    
    
    

    ax_plt1.imshow(1-s.obj.get_field()[xyslice], cmap=pl.cm.bone_r, vmin=0, vmax=1)
    ax_plt1.set_xticks([])
    ax_plt1.set_yticks([])
    ax_plt1.set_ylabel("Platonic", fontsize=22)
    ax_plt1.set_title("x-y", fontsize=24)
    ax_plt2.imshow(1-s._platonic_image()[yzslice], cmap=pl.cm.bone_r, vmin=0, vmax=1)
    ax_plt2.set_xticks([])
    ax_plt2.set_yticks([])
    ax_plt2.set_title("y-z", fontsize=24)

    ax_ilm1.imshow(s.ilm.get_field()[xyslice], cmap=pl.cm.bone_r)
    ax_ilm1.set_xticks([])
    ax_ilm1.set_yticks([])
    ax_ilm1.set_ylabel("ILM", fontsize=22)
    ax_ilm2.imshow(s.ilm.get_field()[yzslice], cmap=pl.cm.bone_r)
    ax_ilm2.set_xticks([])
    ax_ilm2.set_yticks([])

    t = s.ilm.get_field().copy()
    t *= 0
    t[c] = 1
    s.psf.set_tile(util.Tile(t.shape))
    psf = (s.psf.execute(t)+5e-5)**0.1

    ax_psf1.imshow(psf[xyslice], cmap=pl.cm.bone)
    ax_psf1.set_xticks([])
    ax_psf1.set_yticks([])
    ax_psf1.set_ylabel("PSF", fontsize=22)
    ax_psf2.imshow(psf[yzslice], cmap=pl.cm.bone)
    ax_psf2.set_xticks([])
    ax_psf2.set_yticks([])

    
    
    ax_zoom = fig.add_axes([0.48, 0.018, 0.45, 0.52])

    
    im = s.image[slicer1]
    sh = np.array(im.shape)
    cx = x.mean()
    cy = y.mean()

    extent = [0,sh[0],0,sh[1]]
    ax_zoom.set_xticks([])
    ax_zoom.set_yticks([])
    ax_zoom.imshow(im, extent=extent, cmap=pl.cm.bone_r)
    ax_zoom.set_xlim(cx-12, cx+12)
    ax_zoom.set_ylim(cy-12, cy+12)
    ax_zoom.set_title("Sampled positions", fontsize=24)
    ax_zoom.hexbin(x,y, gridsize=32, mincnt=0, cmap=pl.cm.hot)

    zoom1 = zoomed_inset_axes(ax_zoom, 30, loc=3)
    zoom1.imshow(im, extent=extent, cmap=pl.cm.bone_r)
    zoom1.set_xlim(cx-1.0/6, cx+1.0/6)
    zoom1.set_ylim(cy-1.0/6, cy+1.0/6)
    zoom1.hexbin(x,y,gridsize=32, mincnt=5, cmap=pl.cm.hot)
    zoom1.set_xticks([])
    zoom1.set_yticks([])
    zoom1.hlines(cy-1.0/6 + 1.0/32, cx-1.0/6+5e-2, cx-1.0/6+5e-2+1e-1, lw=3)
    zoom1.text(cx-1.0/6 + 1.0/24, cy-1.0/6+5e-2, '0.1px')
    mark_inset(ax_zoom, zoom1, loc1=2, loc2=4, fc="none", ec="0.0")

    
    
    
    
    
    
    
    





def examine_unexplained_noise(state, bins=1000, xlim=(-10,10)):
    

    r = state.residuals
    q = np.fft.fftn(r)
    
    calc_sig = lambda x: np.sqrt(np.dot(x,x) / x.size)
    rh, xr = np.histogram(r.ravel() / calc_sig(r.ravel()), bins=bins,
            density=True)
    bigq = np.append(q.real.ravel(), q.imag.ravel())
    qh, xq = np.histogram(bigq / calc_sig(q.real.ravel()), bins=bins,
            density=True)
    xr = 0.5*(xr[1:] + xr[:-1])
    xq = 0.5*(xq[1:] + xq[:-1])

    gauss = lambda t : np.exp(-t*t*0.5) / np.sqrt(2*np.pi)

    plt.figure(figsize=[16,8])
    axes = []
    for a, (x, r, lbl) in enumerate([[xr, rh, 'Real'], [xq, qh, 'Fourier']]):
        ax = plt.subplot(1,2,a+1)
        ax.semilogy(x, r, label='Data')
        ax.plot(x, gauss(x), label='Gauss Fit', scalex=False, scaley=False)
        ax.set_xlabel('Residuals value $r/\sigma$')
        ax.set_ylabel('Probability $P(r/\sigma)$')
        ax.legend(loc='upper right')
        ax.set_title('{}-Space'.format(lbl))
        ax.set_xlim(xlim)
        axes.append(ax)
    return axes

def compare_data_model_residuals(s, tile, data_vmin='calc', data_vmax='calc',
         res_vmin=-0.1, res_vmax=0.1, edgepts='calc', do_imshow=True,
         data_cmap=plt.cm.bone, res_cmap=plt.cm.RdBu):
    

    
    
    residuals = s.residuals[tile.slicer].squeeze()
    data = s.data[tile.slicer].squeeze()
    model = s.model[tile.slicer].squeeze()
    if data.ndim != 2:
        raise ValueError('tile does not give a 2D slice')

    im = np.zeros([data.shape[0], data.shape[1], 4])
    if data_vmin == 'calc':
        data_vmin = 0.5*(data.min() + model.min())
    if data_vmax == 'calc':
        data_vmax = 0.5*(data.max() + model.max())

    
    upper_mask, center_mask, lower_mask = trisect_image(im.shape, edgepts)

    
    gm = data_cmap(center_data(model, data_vmin, data_vmax))
    dt = data_cmap(center_data(data, data_vmin, data_vmax))
    rs = res_cmap(center_data(residuals, res_vmin, res_vmax))

    for a in range(4):
        im[:,:,a][upper_mask] = rs[:,:,a][upper_mask]
        im[:,:,a][center_mask] = gm[:,:,a][center_mask]
        im[:,:,a][lower_mask] = dt[:,:,a][lower_mask]
    if do_imshow:
        return plt.imshow(im)
    else:
        return im

def trisect_image(imshape, edgepts='calc'):
    

    im_x, im_y = np.meshgrid(np.arange(imshape[0]), np.arange(imshape[1]),
            indexing='ij')
    if np.size(edgepts) == 1:
        
        f = np.sqrt(2./3.) if edgepts == 'calc' else edgepts
        
        lower_edge = (imshape[0] * (1-f),  imshape[1] * f)
        upper_edge = (imshape[0] * f,      imshape[1] * (1-f))
    else:
        upper_edge, lower_edge = edgepts

    
    lower_slope = lower_edge[1] / max(float(imshape[0] - lower_edge[0]), 1e-9)
    upper_slope = (imshape[1] - upper_edge[1]) / float(upper_edge[0])
    
    lower_intercept = -lower_slope * lower_edge[0]
    upper_intercept = upper_edge[1]
    lower_mask = im_y < (im_x * lower_slope + lower_intercept)
    upper_mask = im_y > (im_x * upper_slope + upper_intercept)

    center_mask= -(lower_mask | upper_mask)
    return upper_mask, center_mask, lower_mask




def center_data(data, vmin, vmax):
    

    ans = data - vmin
    ans /= (vmax - vmin)
    return np.clip(ans, 0, 1)

def sim_crb_diff(std0, std1, N=10000):
    

    a = std0*np.random.randn(N, len(std0))
    b = std1*np.random.randn(N, len(std1))
    return a - b

def diag_crb_particles(state):
    crbpos = []
    crbrad = []

    for i in np.arange(state.N)[state.state[state.b_typ]==1.]:
        log.info('{}'.format(i))
        bl = state.blocks_particle(i)
        for b in bl[:-1]:
            crbpos.append(np.sqrt(1.0/state.fisher_information(blocks=[b])))
        crbrad.append(np.sqrt(1.0/state.fisher_information(blocks=[bl[-1]])))

    cx, cr = np.array(crbpos).reshape(-1,3), np.squeeze(np.array(crbrad))
    cx[np.isinf(cx)] = 0
    cr[np.isinf(cr)] = 0
    return cx, cr

def crb_compare(state0, samples0, state1, samples1, crb0=None, crb1=None,
        zlayer=None, xlayer=None):
    

    s0 = state0
    s1 = state1
    h0 = np.array(samples0)
    h1 = np.array(samples1)

    slicez = zlayer or s0.image.shape[0]//2
    slicex = xlayer or s0.image.shape[2]//2
    slicer1 = np.s_[slicez,s0.pad:-s0.pad,s0.pad:-s0.pad]
    slicer2 = np.s_[s0.pad:-s0.pad,s0.pad:-s0.pad,slicex]
    center = (slicez, s0.image.shape[1]//2, slicex)

    mu0 = h0.mean(axis=0)
    mu1 = h1.mean(axis=0)

    std0 = h0.std(axis=0)
    std1 = h1.std(axis=0)

    mask0 = (s0.state[s0.b_typ]==1.) & (
        analyze.trim_box(s0, mu0[s0.b_pos].reshape(-1,3)))
    mask1 = (s1.state[s1.b_typ]==1.) & (
        analyze.trim_box(s1, mu1[s1.b_pos].reshape(-1,3)))
    active0 = np.arange(s0.N)[mask0]
    active1 = np.arange(s1.N)[mask1]

    pos0 = mu0[s0.b_pos].reshape(-1,3)[active0]
    pos1 = mu1[s1.b_pos].reshape(-1,3)[active1]
    rad0 = mu0[s0.b_rad][active0]
    rad1 = mu1[s1.b_rad][active1]

    link = analyze.nearest(pos0, pos1)
    dpos = pos0 - pos1[link]
    drad = rad0 - rad1[link]

    drift = dpos.mean(axis=0)
    log.info('drift {}'.format(drift))

    dpos -= drift

    fig = pl.figure(figsize=(24,10))

    
    
    gs0 = ImageGrid(fig, rect=[0.02, 0.4, 0.4, 0.60], nrows_ncols=(2,3), axes_pad=0.1)

    lbl(gs0[0], 'A')
    for i,slicer in enumerate([slicer1, slicer2]):
        ax_real = gs0[3*i+0]
        ax_fake = gs0[3*i+1]
        ax_diff = gs0[3*i+2]

        diff0 = s0.get_model_image() - s0.image
        diff1 = s1.get_model_image() - s1.image
        a = (s0.image - s1.image)
        b = (s0.get_model_image() - s1.get_model_image())
        c = (diff0 - diff1)

        ptp = 0.7*max([np.abs(a).max(), np.abs(b).max(), np.abs(c).max()])
        cmap = pl.cm.RdBu_r
        ax_real.imshow(a[slicer], cmap=cmap, vmin=-ptp, vmax=ptp)
        ax_real.set_xticks([])
        ax_real.set_yticks([])
        ax_fake.imshow(b[slicer], cmap=cmap, vmin=-ptp, vmax=ptp)
        ax_fake.set_xticks([])
        ax_fake.set_yticks([])
        ax_diff.imshow(c[slicer], cmap=cmap, vmin=-ptp, vmax=ptp)
        ax_diff.set_xticks([])
        ax_diff.set_yticks([])

        if i == 0:
            ax_real.set_title(r"$\Delta$ Confocal image", fontsize=24)
            ax_fake.set_title(r"$\Delta$ Model image", fontsize=24)
            ax_diff.set_title(r"$\Delta$ Difference", fontsize=24)
            ax_real.set_ylabel('x-y')
        else:
            ax_real.set_ylabel('x-z')

    
    
    gs1 = GridSpec(1,3, left=0.05, bottom=0.125, right=0.42, top=0.37,
                wspace=0.15, hspace=0.05)

    spos0 = std0[s0.b_pos].reshape(-1,3)[active0]
    spos1 = std1[s1.b_pos].reshape(-1,3)[active1]
    srad0 = std0[s0.b_rad][active0]
    srad1 = std1[s1.b_rad][active1]

    def hist(ax, vals, bins, *args, **kwargs):
        y,x = np.histogram(vals, bins=bins)
        x = (x[1:] + x[:-1])/2
        y /= len(vals)
        ax.plot(x,y, *args, **kwargs)

    def pp(ind, tarr, tsim, tcrb, var='x'):
        bins = 10**np.linspace(-3, 0.0, 30)
        bin2 = 10**np.linspace(-3, 0.0, 100)
        bins = np.linspace(0.0, 0.2, 30)
        bin2 = np.linspace(0.0, 0.2, 100)
        xlim = (0.0, 0.12)
        
        ylim = (1e-2, 30)

        ticks = ticker.FuncFormatter(lambda x, pos: '{:0.0f}'.format(np.log10(x)))
        scaler = lambda x: x 

        ax_crb = pl.subplot(gs1[0,ind])
        ax_crb.hist(scaler(np.abs(tarr)), bins=bins,
                normed=True, alpha=0.7, histtype='stepfilled', lw=1)
        ax_crb.hist(scaler(np.abs(tcrb)).ravel(), bins=bin2,
                normed=True, alpha=1.0, histtype='step', ls='solid', lw=1.5, color='k')
        ax_crb.hist(scaler(np.abs(tsim).ravel()), bins=bin2,
                normed=True, alpha=1.0, histtype='step', lw=3)
        ax_crb.set_xlabel(r"$\Delta = |%s(t_1) - %s(t_0)|$" % (var,var), fontsize=24)
        
        ax_crb.set_xlim(xlim)
        
        
        
        ax_crb.grid(b=False, which='both', axis='both')

        if ind == 0:
            lbl(ax_crb, 'B')
            ax_crb.set_ylabel(r"$P(\Delta)$")
        else:
            ax_crb.set_yticks([])

        ax_crb.locator_params(axis='x', nbins=3)

    f,g = 1.5, 1.95
    sim = f*sim_crb_diff(spos0[:,1], spos1[:,1][link])
    crb = g*sim_crb_diff(crb0[0][:,1][active0], crb1[0][:,1][active1][link])
    pp(0, dpos[:,1], sim, crb, 'x')

    sim = f*sim_crb_diff(spos0[:,0], spos1[:,0][link])
    crb = g*sim_crb_diff(crb0[0][:,0][active0], crb1[0][:,0][active1][link])
    pp(1, dpos[:,0], sim, crb, 'z')

    sim = f*sim_crb_diff(srad0, srad1[link])
    crb = g*sim_crb_diff(crb0[1][active0], crb1[1][active1][link])
    pp(2, drad, sim, crb, 'a')

    
    

    
    
    gs2 = GridSpec(2,2, left=0.48, bottom=0.12, right=0.99, top=0.95,
                wspace=0.35, hspace=0.35)

    ax_hist = pl.subplot(gs2[0,0])
    ax_hist.hist(std0[s0.b_pos], bins=np.logspace(-3.0, 0, 50), alpha=0.7, label='POS', histtype='stepfilled')
    ax_hist.hist(std0[s0.b_rad], bins=np.logspace(-3.0, 0, 50), alpha=0.7, label='RAD', histtype='stepfilled')
    ax_hist.set_xlim((10**-3.0, 1))
    ax_hist.semilogx()
    ax_hist.set_xlabel(r"$\bar{\sigma}$")
    ax_hist.set_ylabel(r"$P(\bar{\sigma})$")
    ax_hist.legend(loc='upper right')
    lbl(ax_hist, 'C')

    imdiff = ((s0.get_model_image() - s0.image)/s0._sigma_field)[s0.image_mask==1.].ravel()
    mu = imdiff.mean()
    
    
    x = np.linspace(-5,5,10000)

    ax_diff = pl.subplot(gs2[0,1])
    ax_diff.plot(x, 1.0/np.sqrt(2*np.pi) * np.exp(-(x-mu)**2 / 2), '-', alpha=0.7, color='k', lw=2)
    ax_diff.hist(imdiff, bins=1000, histtype='step', alpha=0.7, normed=True)
    ax_diff.semilogy()
    ax_diff.set_ylabel(r"$P(\delta)$")
    ax_diff.set_xlabel(r"$\delta = (M_i - d_i)/\sigma_i$")
    ax_diff.locator_params(axis='x', nbins=5)
    ax_diff.grid(b=False, which='minor', axis='y')
    ax_diff.set_xlim(-5, 5)
    ax_diff.set_ylim(1e-4, 1e0)
    lbl(ax_diff, 'D')

    pos = mu0[s0.b_pos].reshape(-1,3)
    rad = mu0[s0.b_rad]
    mask = analyze.trim_box(s0, pos)
    pos = pos[mask]
    rad = rad[mask]

    gx, gy = analyze.gofr(pos, rad, mu0[s0.b_zscale][0], resolution=5e-2,mask_start=0.5)
    mask = gx < 5
    gx = gx[mask]
    gy = gy[mask]
    ax_gofr = pl.subplot(gs2[1,0])
    ax_gofr.plot(gx, gy, '-', lw=1)
    ax_gofr.set_xlabel(r"$r/a$")
    ax_gofr.set_ylabel(r"$g(r/a)$")
    ax_gofr.locator_params(axis='both', nbins=5)
    
    lbl(ax_gofr, 'E')

    gx, gy = analyze.gofr(pos, rad, mu0[s0.b_zscale][0], method='surface')
    mask = gx < 5
    gx = gx[mask]
    gy = gy[mask]
    gy[gy <= 0.] = gy[gy>0].min()
    ax_gofrs = pl.subplot(gs2[1,1])
    ax_gofrs.plot(gx, gy, '-', lw=1)
    ax_gofrs.set_xlabel(r"$r/a$")
    ax_gofrs.set_ylabel(r"$g_{\rm{surface}}(r/a)$")
    ax_gofrs.locator_params(axis='both', nbins=5)
    ax_gofrs.grid(b=False, which='minor', axis='y')
    
    lbl(ax_gofrs, 'F')

    ylim = ax_gofrs.get_ylim()
    ax_gofrs.set_ylim(gy.min(), ylim[1])
    

def crb_rad(state0, samples0, state1, samples1, crb0, crb1):
    s0 = state0
    s1 = state1
    h0 = np.array(samples0)
    h1 = np.array(samples1)

    mu0 = h0.mean(axis=0)
    mu1 = h1.mean(axis=0)

    std0 = h0.std(axis=0)
    std1 = h1.std(axis=0)

    mask0 = (s0.state[s0.b_typ]==1.) & (
        analyze.trim_box(s0, mu0[s0.b_pos].reshape(-1,3)))
    mask1 = (s1.state[s1.b_typ]==1.) & (
        analyze.trim_box(s1, mu1[s1.b_pos].reshape(-1,3)))
    active0 = np.arange(s0.N)[mask0]
    active1 = np.arange(s1.N)[mask1]

    pos0 = mu0[s0.b_pos].reshape(-1,3)[active0]
    pos1 = mu1[s1.b_pos].reshape(-1,3)[active1]
    rad0 = mu0[s0.b_rad][active0]
    rad1 = mu1[s1.b_rad][active1]

    link = analyze.nearest(pos0, pos1)
    dpos = pos0 - pos1[link]
    drad = rad0 - rad1[link]

    spos0 = std0[s0.b_pos].reshape(-1,3)[active0]
    spos1 = std1[s1.b_pos].reshape(-1,3)[active1]
    srad0 = std0[s0.b_rad][active0]
    srad1 = std1[s1.b_rad][active1]

    def pp(ax, tarr, tsim, tcrb, var='x'):
        bins = 10**np.linspace(-3, 0.0, 30)
        bin2 = 10**np.linspace(-3, 0.0, 100)
        bins = np.linspace(0.0, 0.1, 30)
        bin2 = np.linspace(0.0, 0.1, 100)
        xlim = (0, 0.1)
        
        ylim = (1e-2, 30)

        ticks = ticker.FuncFormatter(lambda x, pos: '{:0.0f}'.format(np.log10(x)))
        scaler = lambda x: x 

        ax_crb = ax
        ax_crb.hist(scaler(np.abs(tarr)), bins=bins,
                normed=True, alpha=0.7, histtype='stepfilled', lw=1, label='Radii differences')

        y,x = np.histogram(np.abs(tcrb).ravel(), bins=bin2, normed=True)
        x = (x[1:] + x[:-1])/2
        ax_crb.step(x, y, lw=3, color='k', ls='solid', label='CRB')

        y,x = np.histogram(np.abs(tsim).ravel(), bins=bin2, normed=True)
        x = (x[1:] + x[:-1])/2
        ax_crb.step(x, y, lw=3, ls='solid', label='Estimated Error')

        ax_crb.set_xlabel(r"$\Delta = |%s(t_1) - %s(t_0)|$" % (var,var), fontsize=28)
        ax_crb.set_ylabel(r"$P(\Delta)$", fontsize=28)
        ax_crb.set_xlim(xlim)
        ax_crb.grid(b=False, which='both', axis='both')
        ax_crb.legend(loc='best', ncol=1)

    fig = pl.figure()
    ax = pl.gca()

    f,g = 1.5, 1.85
    sim = f*sim_crb_diff(srad0, srad1[link])
    crb = g*sim_crb_diff(crb0[1][active0], crb1[1][active1][link])
    pp(ax, drad, sim, crb, 'a')


def twoslice(field, center=None, size=6.0, cmap='bone_r', vmin=0, vmax=1,
        orientation='vertical', figpad=1.09, off=0.01):
    

    center = center or [i//2 for i in field.shape]
    slices = []
    for i,c in enumerate(center):
        blank = [np.s_[:]]*len(center)
        blank[i] = c
        slices.append(tuple(blank))

    z,y,x = [float(i) for i in field.shape]
    w = float(x + z)
    h = float(y + z)

    def show(field, ax, slicer, transpose=False):
        tmp = field[slicer] if not transpose else field[slicer].T
        ax.imshow(
            tmp, cmap=cmap, interpolation='nearest',
            vmin=vmin, vmax=vmax
        )
        ax.set_xticks([])
        ax.set_yticks([])
        ax.grid('off')

    if orientation.startswith('v'):
        
        log.info('{} {} {} {} {} {}'.format(x, y, z, w, h, x/h))
        r = x/h
        q = y/h
        f = 1 / (1 + 3*off)
        fig = pl.figure(figsize=(size*r, size*f))
        ax1 = fig.add_axes((off, f*(1-q)+2*off, f, f*q))
        ax2 = fig.add_axes((off, off,           f, f*(1-q)))

        show(field, ax1, slices[0])
        show(field, ax2, slices[1])
    else:
        
        r = y/w
        q = x/w
        f = 1 / (1 + 3*off)
        fig = pl.figure(figsize=(size*f, size*r))
        ax1 = fig.add_axes((off,    off,   f*q, f))
        ax2 = fig.add_axes((2*off+f*q, off, f*(1-q), f))

        show(field, ax1, slices[0])
        show(field, ax2, slices[2], transpose=True)

    return fig, ax1, ax2

def twoslice_overlay(s, zlayer=None, xlayer=None, size=6.0,
        cmap='bone_r', vmin=0, vmax=1, showimage=False, solid=False, pad=None):
    pad = pad or s.pad
    trim = (np.s_[pad:-pad],)*3
    field = s.image[trim]

    slicez = zlayer or field.shape[0]//2
    slicex = xlayer or field.shape[2]//2
    slicer1 = np.s_[slicez,:,:]
    slicer2 = np.s_[:,:,slicex]

    sh = field.shape
    q = float(sh[1]) / (sh[0]+sh[1])
    r = float(sh[1] + sh[0]) / sh[1]

    fig = pl.figure(figsize=(size, size*r*1.05))
    ax1 = fig.add_axes((0, 1-q, 1, q))
    ax2 = fig.add_axes((0, 0, 1, 1-q))

    mu = s.state.copy()
    active = np.arange(s.N)[s.state[s.b_typ]==1.]

    pos = mu[s.b_pos].reshape(-1,3)[active]
    rad = mu[s.b_rad][active]

    def show(ax, slicer):
        talpha = 0.0 if not showimage else 1.0
        ax.imshow(field[slicer], cmap=cmap, interpolation='nearest', vmin=vmin, vmax=vmax, alpha=talpha)
        ax.set_xticks([])
        ax.set_yticks([])
        ax.set_axis_bgcolor('black')
        ax.grid('off')

    def circles(ax, layer, axis):
        
        talpha = 1.0 if not showimage else 0.8
        cedge = 'white' if not showimage else 'black'
        cface = 'white' if solid else 'none'
        particles = np.arange(len(pos))[np.abs(pos[:,axis] - layer) < rad]

        
        
        for i in particles:
            p = pos[i].copy()
            r = 2*np.sqrt(rad[i]**2 - (p[axis] - layer)**2)
            if axis==0:
                c = Circle((p[2]-pad,p[1]-pad), radius=r/2, fc=cface, ec=cedge, alpha=talpha)
            if axis==2:
                c = Circle((p[1]-pad,p[0]-pad), radius=r/2, fc=cface, ec=cedge, alpha=talpha)
            ax.add_patch(c)

    show(ax1, slicer1)
    show(ax2, slicer2)

    circles(ax1, slicez+pad, 0)
    circles(ax2, slicex+pad, 2)

def deconstruction(s):
    s.model_to_true_image()
    twoslice(s.image, pad=s.pad)
    twoslice(s.get_model_image(), pad=s.pad)
    twoslice(s.ilm.get_field() - s.offset*s.obj.get_field(), pad=s.pad, vmin=None, vmax=None)
    twoslice(1 - s.offset*s.obj.get_field(), pad=s.pad)
    twoslice_overlay(s)

def circles(st, layer, axis, ax=None, talpha=1.0, cedge='white', cface='white'):
    

    pos = st.obj_get_positions()
    rad = st.obj_get_radii()
    shape = st.ishape.shape.tolist()
    shape.pop(axis) 
    if ax is None:
        fig = plt.figure()
        axisbg = 'white' if cface == 'black' else 'black'
        sx, sy = ((1,shape[1]/float(shape[0])) if shape[0] > shape[1] else
                (shape[0]/float(shape[1]), 1))
        ax = fig.add_axes((0,0, sx, sy), axisbg=axisbg)
    
    particles = np.arange(len(pos))[np.abs(pos[:,axis] - layer) < rad]

    
    
    scale = 1.0 
    for i in particles:
        p = pos[i].copy()
        r = 2*np.sqrt(rad[i]**2 - (p[axis] - layer)**2)
        
        if axis==0:
            ix = 1; iy = 2
        elif axis == 1:
            ix = 0; iy = 2
        elif axis==2:
            ix = 0; iy = 1
        c = Circle((p[ix]/scale, p[iy]/scale), radius=r/2/scale, fc=cface,
                ec=cedge, alpha=talpha)
        ax.add_patch(c)
    
    plt.axis('equal') 
    return ax
