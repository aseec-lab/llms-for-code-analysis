













from collections import OrderedDict
from functools import partial

import matplotlib.pyplot as plt
import numpy as np


SECTORS = OrderedDict([
    (101, 'Basic Materials'),
    (102, 'Consumer Cyclical'),
    (103, 'Financial Services'),
    (104, 'Real Estate'),
    (205, 'Consumer Defensive'),
    (206, 'Healthcare'),
    (207, 'Utilities'),
    (308, 'Communication Services'),
    (309, 'Energy'),
    (310, 'Industrials'),
    (311, 'Technology')
])

CAP_BUCKETS = OrderedDict([
    ('Micro', (50000000, 300000000)),
    ('Small', (300000000, 2000000000)),
    ('Mid', (2000000000, 10000000000)),
    ('Large', (10000000000, 200000000000)),
    ('Mega', (200000000000, np.inf))
])


def compute_style_factor_exposures(positions, risk_factor):
    


    positions_wo_cash = positions.drop('cash', axis='columns')
    gross_exposure = positions_wo_cash.abs().sum(axis='columns')

    style_factor_exposure = positions_wo_cash.multiply(risk_factor) \
        .divide(gross_exposure, axis='index')
    tot_style_factor_exposure = style_factor_exposure.sum(axis='columns',
                                                          skipna=True)

    return tot_style_factor_exposure


def plot_style_factor_exposures(tot_style_factor_exposure, factor_name=None,
                                ax=None):
    


    if ax is None:
        ax = plt.gca()

    if factor_name is None:
        factor_name = tot_style_factor_exposure.name

    ax.plot(tot_style_factor_exposure.index, tot_style_factor_exposure,
            label=factor_name)
    avg = tot_style_factor_exposure.mean()
    ax.axhline(avg, linestyle='-.', label='Mean = {:.3}'.format(avg))
    ax.axhline(0, color='k', linestyle='-')
    _, _, y1, y2 = plt.axis()
    lim = max(abs(y1), abs(y2))
    ax.set(title='Exposure to {}'.format(factor_name),
           ylabel='{} \n weighted exposure'.format(factor_name),
           ylim=(-lim, lim))
    ax.legend(frameon=True, framealpha=0.5)

    return ax


def compute_sector_exposures(positions, sectors, sector_dict=SECTORS):
    


    sector_ids = sector_dict.keys()

    long_exposures = []
    short_exposures = []
    gross_exposures = []
    net_exposures = []

    positions_wo_cash = positions.drop('cash', axis='columns')
    long_exposure = positions_wo_cash[positions_wo_cash > 0] \
        .sum(axis='columns')
    short_exposure = positions_wo_cash[positions_wo_cash < 0] \
        .abs().sum(axis='columns')
    gross_exposure = positions_wo_cash.abs().sum(axis='columns')

    for sector_id in sector_ids:
        in_sector = positions_wo_cash[sectors == sector_id]

        long_sector = in_sector[in_sector > 0] \
            .sum(axis='columns').divide(long_exposure)
        short_sector = in_sector[in_sector < 0] \
            .sum(axis='columns').divide(short_exposure)
        gross_sector = in_sector.abs().sum(axis='columns') \
            .divide(gross_exposure)
        net_sector = long_sector.subtract(short_sector)

        long_exposures.append(long_sector)
        short_exposures.append(short_sector)
        gross_exposures.append(gross_sector)
        net_exposures.append(net_sector)

    return long_exposures, short_exposures, gross_exposures, net_exposures


def plot_sector_exposures_longshort(long_exposures, short_exposures,
                                    sector_dict=SECTORS, ax=None):
    


    if ax is None:
        ax = plt.gca()

    if sector_dict is None:
        sector_names = SECTORS.values()
    else:
        sector_names = sector_dict.values()

    color_list = plt.cm.gist_rainbow(np.linspace(0, 1, 11))

    ax.stackplot(long_exposures[0].index, long_exposures,
                 labels=sector_names, colors=color_list, alpha=0.8,
                 baseline='zero')
    ax.stackplot(long_exposures[0].index, short_exposures,
                 colors=color_list, alpha=0.8, baseline='zero')
    ax.axhline(0, color='k', linestyle='-')
    ax.set(title='Long and short exposures to sectors',
           ylabel='Proportion of long/short exposure in sectors')
    ax.legend(loc='upper left', frameon=True, framealpha=0.5)

    return ax


def plot_sector_exposures_gross(gross_exposures, sector_dict=None, ax=None):
    


    if ax is None:
        ax = plt.gca()

    if sector_dict is None:
        sector_names = SECTORS.values()
    else:
        sector_names = sector_dict.values()

    color_list = plt.cm.gist_rainbow(np.linspace(0, 1, 11))

    ax.stackplot(gross_exposures[0].index, gross_exposures,
                 labels=sector_names, colors=color_list, alpha=0.8,
                 baseline='zero')
    ax.axhline(0, color='k', linestyle='-')
    ax.set(title='Gross exposure to sectors',
           ylabel='Proportion of gross exposure \n in sectors')

    return ax


def plot_sector_exposures_net(net_exposures, sector_dict=None, ax=None):
    


    if ax is None:
        ax = plt.gca()

    if sector_dict is None:
        sector_names = SECTORS.values()
    else:
        sector_names = sector_dict.values()

    color_list = plt.cm.gist_rainbow(np.linspace(0, 1, 11))

    for i in range(len(net_exposures)):
        ax.plot(net_exposures[i], color=color_list[i], alpha=0.8,
                label=sector_names[i])
    ax.set(title='Net exposures to sectors',
           ylabel='Proportion of net exposure \n in sectors')

    return ax


def compute_cap_exposures(positions, caps):
    


    long_exposures = []
    short_exposures = []
    gross_exposures = []
    net_exposures = []

    positions_wo_cash = positions.drop('cash', axis='columns')
    tot_gross_exposure = positions_wo_cash.abs().sum(axis='columns')
    tot_long_exposure = positions_wo_cash[positions_wo_cash > 0] \
        .sum(axis='columns')
    tot_short_exposure = positions_wo_cash[positions_wo_cash < 0] \
        .abs().sum(axis='columns')

    for bucket_name, boundaries in CAP_BUCKETS.items():
        in_bucket = positions_wo_cash[(caps >= boundaries[0]) &
                                      (caps <= boundaries[1])]

        gross_bucket = in_bucket.abs().sum(axis='columns') \
            .divide(tot_gross_exposure)
        long_bucket = in_bucket[in_bucket > 0] \
            .sum(axis='columns').divide(tot_long_exposure)
        short_bucket = in_bucket[in_bucket < 0] \
            .sum(axis='columns').divide(tot_short_exposure)
        net_bucket = long_bucket.subtract(short_bucket)

        gross_exposures.append(gross_bucket)
        long_exposures.append(long_bucket)
        short_exposures.append(short_bucket)
        net_exposures.append(net_bucket)

    return long_exposures, short_exposures, gross_exposures, net_exposures


def plot_cap_exposures_longshort(long_exposures, short_exposures, ax=None):
    


    if ax is None:
        ax = plt.gca()

    color_list = plt.cm.gist_rainbow(np.linspace(0, 1, 5))

    ax.stackplot(long_exposures[0].index, long_exposures,
                 labels=CAP_BUCKETS.keys(), colors=color_list, alpha=0.8,
                 baseline='zero')
    ax.stackplot(long_exposures[0].index, short_exposures, colors=color_list,
                 alpha=0.8, baseline='zero')
    ax.axhline(0, color='k', linestyle='-')
    ax.set(title='Long and short exposures to market caps',
           ylabel='Proportion of long/short exposure in market cap buckets')
    ax.legend(loc='upper left', frameon=True, framealpha=0.5)

    return ax


def plot_cap_exposures_gross(gross_exposures, ax=None):
    


    if ax is None:
        ax = plt.gca()

    color_list = plt.cm.gist_rainbow(np.linspace(0, 1, 5))

    ax.stackplot(gross_exposures[0].index, gross_exposures,
                 labels=CAP_BUCKETS.keys(), colors=color_list, alpha=0.8,
                 baseline='zero')
    ax.axhline(0, color='k', linestyle='-')
    ax.set(title='Gross exposure to market caps',
           ylabel='Proportion of gross exposure \n in market cap buckets')

    return ax


def plot_cap_exposures_net(net_exposures, ax=None):
    


    if ax is None:
        ax = plt.gca()

    color_list = plt.cm.gist_rainbow(np.linspace(0, 1, 5))

    cap_names = CAP_BUCKETS.keys()
    for i in range(len(net_exposures)):
        ax.plot(net_exposures[i], color=color_list[i], alpha=0.8,
                label=cap_names[i])
    ax.axhline(0, color='k', linestyle='-')
    ax.set(title='Net exposure to market caps',
           ylabel='Proportion of net exposure \n in market cap buckets')

    return ax


def compute_volume_exposures(shares_held, volumes, percentile):
    


    shares_held = shares_held.replace(0, np.nan)

    shares_longed = shares_held[shares_held > 0]
    shares_shorted = -1 * shares_held[shares_held < 0]
    shares_grossed = shares_held.abs()

    longed_frac = shares_longed.divide(volumes)
    shorted_frac = shares_shorted.divide(volumes)
    grossed_frac = shares_grossed.divide(volumes)

    
    
    
    
    
    
    

    longed_threshold = 100 * longed_frac.apply(
        partial(np.nanpercentile, q=100 * percentile),
        axis='columns',
    )
    shorted_threshold = 100 * shorted_frac.apply(
        partial(np.nanpercentile, q=100 * percentile),
        axis='columns',
    )
    grossed_threshold = 100 * grossed_frac.apply(
        partial(np.nanpercentile, q=100 * percentile),
        axis='columns',
    )

    return longed_threshold, shorted_threshold, grossed_threshold


def plot_volume_exposures_longshort(longed_threshold, shorted_threshold,
                                    percentile, ax=None):
    


    if ax is None:
        ax = plt.gca()

    ax.plot(longed_threshold.index, longed_threshold,
            color='b', label='long')
    ax.plot(shorted_threshold.index, shorted_threshold,
            color='r', label='short')
    ax.axhline(0, color='k')
    ax.set(title='Long and short exposures to illiquidity',
           ylabel='{}th percentile of proportion of volume (%)'
           .format(100 * percentile))
    ax.legend(frameon=True, framealpha=0.5)

    return ax


def plot_volume_exposures_gross(grossed_threshold, percentile, ax=None):
    


    if ax is None:
        ax = plt.gca()

    ax.plot(grossed_threshold.index, grossed_threshold,
            color='b', label='gross')
    ax.axhline(0, color='k')
    ax.set(title='Gross exposure to illiquidity',
           ylabel='{}th percentile of \n proportion of volume (%)'
           .format(100 * percentile))
    ax.legend(frameon=True, framealpha=0.5)

    return ax
