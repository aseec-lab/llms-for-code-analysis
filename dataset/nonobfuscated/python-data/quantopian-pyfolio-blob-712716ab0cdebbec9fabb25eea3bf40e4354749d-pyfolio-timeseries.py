













from __future__ import division

from collections import OrderedDict
from functools import partial

import empyrical as ep
import numpy as np
import pandas as pd
import scipy as sp
import scipy.stats as stats
from sklearn import linear_model

from .deprecate import deprecated
from .interesting_periods import PERIODS
from .txn import get_turnover
from .utils import APPROX_BDAYS_PER_MONTH, APPROX_BDAYS_PER_YEAR
from .utils import DAILY

DEPRECATION_WARNING = ("Risk functions in pyfolio.timeseries are deprecated "
                       "and will be removed in a future release. Please "
                       "install the empyrical package instead.")


def var_cov_var_normal(P, c, mu=0, sigma=1):
    


    alpha = sp.stats.norm.ppf(1 - c, mu, sigma)
    return P - P * (alpha + 1)


@deprecated(msg=DEPRECATION_WARNING)
def max_drawdown(returns):
    


    return ep.max_drawdown(returns)


@deprecated(msg=DEPRECATION_WARNING)
def annual_return(returns, period=DAILY):
    


    return ep.annual_return(returns, period=period)


@deprecated(msg=DEPRECATION_WARNING)
def annual_volatility(returns, period=DAILY):
    


    return ep.annual_volatility(returns, period=period)


@deprecated(msg=DEPRECATION_WARNING)
def calmar_ratio(returns, period=DAILY):
    


    return ep.calmar_ratio(returns, period=period)


@deprecated(msg=DEPRECATION_WARNING)
def omega_ratio(returns, annual_return_threshhold=0.0):
    


    return ep.omega_ratio(returns,
                          required_return=annual_return_threshhold)


@deprecated(msg=DEPRECATION_WARNING)
def sortino_ratio(returns, required_return=0, period=DAILY):
    


    return ep.sortino_ratio(returns, required_return=required_return)


@deprecated(msg=DEPRECATION_WARNING)
def downside_risk(returns, required_return=0, period=DAILY):
    


    return ep.downside_risk(returns,
                            required_return=required_return,
                            period=period)


@deprecated(msg=DEPRECATION_WARNING)
def sharpe_ratio(returns, risk_free=0, period=DAILY):
    


    return ep.sharpe_ratio(returns, risk_free=risk_free, period=period)


@deprecated(msg=DEPRECATION_WARNING)
def alpha_beta(returns, factor_returns):
    


    return ep.alpha_beta(returns, factor_returns=factor_returns)


@deprecated(msg=DEPRECATION_WARNING)
def alpha(returns, factor_returns):
    


    return ep.alpha(returns, factor_returns=factor_returns)


@deprecated(msg=DEPRECATION_WARNING)
def beta(returns, factor_returns):
    


    return ep.beta(returns, factor_returns)


@deprecated(msg=DEPRECATION_WARNING)
def stability_of_timeseries(returns):
    


    return ep.stability_of_timeseries(returns)


@deprecated(msg=DEPRECATION_WARNING)
def tail_ratio(returns):
    


    return ep.tail_ratio(returns)


def common_sense_ratio(returns):
    


    return ep.tail_ratio(returns) * \
        (1 + ep.annual_return(returns))


def normalize(returns, starting_value=1):
    


    return starting_value * (returns / returns.iloc[0])


@deprecated(msg=DEPRECATION_WARNING)
def cum_returns(returns, starting_value=0):
    


    return ep.cum_returns(returns, starting_value=starting_value)


@deprecated(msg=DEPRECATION_WARNING)
def aggregate_returns(returns, convert_to):
    


    return ep.aggregate_returns(returns, convert_to=convert_to)


def rolling_beta(returns, factor_returns,
                 rolling_window=APPROX_BDAYS_PER_MONTH * 6):
    


    if factor_returns.ndim > 1:
        
        return factor_returns.apply(partial(rolling_beta, returns),
                                    rolling_window=rolling_window)
    else:
        out = pd.Series(index=returns.index)
        for beg, end in zip(returns.index[0:-rolling_window],
                            returns.index[rolling_window:]):
            out.loc[end] = ep.beta(
                returns.loc[beg:end],
                factor_returns.loc[beg:end])

        return out


def rolling_regression(returns, factor_returns,
                       rolling_window=APPROX_BDAYS_PER_MONTH * 6,
                       nan_threshold=0.1):
    


    
    ret_no_na = returns.dropna()

    columns = ['alpha'] + factor_returns.columns.tolist()
    rolling_risk = pd.DataFrame(columns=columns,
                                index=ret_no_na.index)

    rolling_risk.index.name = 'dt'

    for beg, end in zip(ret_no_na.index[:-rolling_window],
                        ret_no_na.index[rolling_window:]):
        returns_period = ret_no_na[beg:end]
        factor_returns_period = factor_returns.loc[returns_period.index]

        if np.all(factor_returns_period.isnull().mean()) < nan_threshold:
            factor_returns_period_dnan = factor_returns_period.dropna()
            reg = linear_model.LinearRegression(fit_intercept=True).fit(
                factor_returns_period_dnan,
                returns_period.loc[factor_returns_period_dnan.index])
            rolling_risk.loc[end, factor_returns.columns] = reg.coef_
            rolling_risk.loc[end, 'alpha'] = reg.intercept_

    return rolling_risk


def gross_lev(positions):
    


    exposure = positions.drop('cash', axis=1).abs().sum(axis=1)
    return exposure / positions.sum(axis=1)


def value_at_risk(returns, period=None, sigma=2.0):
    

    if period is not None:
        returns_agg = ep.aggregate_returns(returns, period)
    else:
        returns_agg = returns.copy()

    value_at_risk = returns_agg.mean() - sigma * returns_agg.std()
    return value_at_risk


SIMPLE_STAT_FUNCS = [
    ep.annual_return,
    ep.cum_returns_final,
    ep.annual_volatility,
    ep.sharpe_ratio,
    ep.calmar_ratio,
    ep.stability_of_timeseries,
    ep.max_drawdown,
    ep.omega_ratio,
    ep.sortino_ratio,
    stats.skew,
    stats.kurtosis,
    ep.tail_ratio,
    value_at_risk
]

FACTOR_STAT_FUNCS = [
    ep.alpha,
    ep.beta,
]

STAT_FUNC_NAMES = {
    'annual_return': 'Annual return',
    'cum_returns_final': 'Cumulative returns',
    'annual_volatility': 'Annual volatility',
    'sharpe_ratio': 'Sharpe ratio',
    'calmar_ratio': 'Calmar ratio',
    'stability_of_timeseries': 'Stability',
    'max_drawdown': 'Max drawdown',
    'omega_ratio': 'Omega ratio',
    'sortino_ratio': 'Sortino ratio',
    'skew': 'Skew',
    'kurtosis': 'Kurtosis',
    'tail_ratio': 'Tail ratio',
    'common_sense_ratio': 'Common sense ratio',
    'value_at_risk': 'Daily value at risk',
    'alpha': 'Alpha',
    'beta': 'Beta',
}


def perf_stats(returns, factor_returns=None, positions=None,
               transactions=None, turnover_denom='AGB'):
    


    stats = pd.Series()
    for stat_func in SIMPLE_STAT_FUNCS:
        stats[STAT_FUNC_NAMES[stat_func.__name__]] = stat_func(returns)

    if positions is not None:
        stats['Gross leverage'] = gross_lev(positions).mean()
        if transactions is not None:
            stats['Daily turnover'] = get_turnover(positions,
                                                   transactions,
                                                   turnover_denom).mean()
    if factor_returns is not None:
        for stat_func in FACTOR_STAT_FUNCS:
            res = stat_func(returns, factor_returns)
            stats[STAT_FUNC_NAMES[stat_func.__name__]] = res

    return stats


def perf_stats_bootstrap(returns, factor_returns=None, return_stats=True,
                         **kwargs):
    


    bootstrap_values = OrderedDict()

    for stat_func in SIMPLE_STAT_FUNCS:
        stat_name = STAT_FUNC_NAMES[stat_func.__name__]
        bootstrap_values[stat_name] = calc_bootstrap(stat_func,
                                                     returns)

    if factor_returns is not None:
        for stat_func in FACTOR_STAT_FUNCS:
            stat_name = STAT_FUNC_NAMES[stat_func.__name__]
            bootstrap_values[stat_name] = calc_bootstrap(
                stat_func,
                returns,
                factor_returns=factor_returns)

    bootstrap_values = pd.DataFrame(bootstrap_values)

    if return_stats:
        stats = bootstrap_values.apply(calc_distribution_stats)
        return stats.T[['mean', 'median', '5%', '95%']]
    else:
        return bootstrap_values


def calc_bootstrap(func, returns, *args, **kwargs):
    


    n_samples = kwargs.pop('n_samples', 1000)
    out = np.empty(n_samples)

    factor_returns = kwargs.pop('factor_returns', None)

    for i in range(n_samples):
        idx = np.random.randint(len(returns), size=len(returns))
        returns_i = returns.iloc[idx].reset_index(drop=True)
        if factor_returns is not None:
            factor_returns_i = factor_returns.iloc[idx].reset_index(drop=True)
            out[i] = func(returns_i, factor_returns_i,
                          *args, **kwargs)
        else:
            out[i] = func(returns_i,
                          *args, **kwargs)

    return out


def calc_distribution_stats(x):
    


    return pd.Series({'mean': np.mean(x),
                      'median': np.median(x),
                      'std': np.std(x),
                      '5%': np.percentile(x, 5),
                      '25%': np.percentile(x, 25),
                      '75%': np.percentile(x, 75),
                      '95%': np.percentile(x, 95),
                      'IQR': np.subtract.reduce(
                          np.percentile(x, [75, 25])),
                      })


def get_max_drawdown_underwater(underwater):
    


    valley = np.argmin(underwater)  
    
    peak = underwater[:valley][underwater[:valley] == 0].index[-1]
    
    try:
        recovery = underwater[valley:][underwater[valley:] == 0].index[0]
    except IndexError:
        recovery = np.nan  
    return peak, valley, recovery


def get_max_drawdown(returns):
    


    returns = returns.copy()
    df_cum = cum_returns(returns, 1.0)
    running_max = np.maximum.accumulate(df_cum)
    underwater = df_cum / running_max - 1
    return get_max_drawdown_underwater(underwater)


def get_top_drawdowns(returns, top=10):
    


    returns = returns.copy()
    df_cum = ep.cum_returns(returns, 1.0)
    running_max = np.maximum.accumulate(df_cum)
    underwater = df_cum / running_max - 1

    drawdowns = []
    for t in range(top):
        peak, valley, recovery = get_max_drawdown_underwater(underwater)
        
        if not pd.isnull(recovery):
            underwater.drop(underwater[peak: recovery].index[1:-1],
                            inplace=True)
        else:
            
            underwater = underwater.loc[:peak]

        drawdowns.append((peak, valley, recovery))
        if (len(returns) == 0) or (len(underwater) == 0):
            break

    return drawdowns


def gen_drawdown_table(returns, top=10):
    


    df_cum = ep.cum_returns(returns, 1.0)
    drawdown_periods = get_top_drawdowns(returns, top=top)
    df_drawdowns = pd.DataFrame(index=list(range(top)),
                                columns=['Net drawdown in %',
                                         'Peak date',
                                         'Valley date',
                                         'Recovery date',
                                         'Duration'])

    for i, (peak, valley, recovery) in enumerate(drawdown_periods):
        if pd.isnull(recovery):
            df_drawdowns.loc[i, 'Duration'] = np.nan
        else:
            df_drawdowns.loc[i, 'Duration'] = len(pd.date_range(peak,
                                                                recovery,
                                                                freq='B'))
        df_drawdowns.loc[i, 'Peak date'] = (peak.to_pydatetime()
                                            .strftime('%Y-%m-%d'))
        df_drawdowns.loc[i, 'Valley date'] = (valley.to_pydatetime()
                                              .strftime('%Y-%m-%d'))
        if isinstance(recovery, float):
            df_drawdowns.loc[i, 'Recovery date'] = recovery
        else:
            df_drawdowns.loc[i, 'Recovery date'] = (recovery.to_pydatetime()
                                                    .strftime('%Y-%m-%d'))
        df_drawdowns.loc[i, 'Net drawdown in %'] = (
            (df_cum.loc[peak] - df_cum.loc[valley]) / df_cum.loc[peak]) * 100

    df_drawdowns['Peak date'] = pd.to_datetime(df_drawdowns['Peak date'])
    df_drawdowns['Valley date'] = pd.to_datetime(df_drawdowns['Valley date'])
    df_drawdowns['Recovery date'] = pd.to_datetime(
        df_drawdowns['Recovery date'])

    return df_drawdowns


def rolling_volatility(returns, rolling_vol_window):
    


    return returns.rolling(rolling_vol_window).std() \
        * np.sqrt(APPROX_BDAYS_PER_YEAR)


def rolling_sharpe(returns, rolling_sharpe_window):
    


    return returns.rolling(rolling_sharpe_window).mean() \
        / returns.rolling(rolling_sharpe_window).std() \
        * np.sqrt(APPROX_BDAYS_PER_YEAR)


def simulate_paths(is_returns, num_days,
                   starting_value=1, num_samples=1000, random_seed=None):
    


    samples = np.empty((num_samples, num_days))
    seed = np.random.RandomState(seed=random_seed)
    for i in range(num_samples):
        samples[i, :] = is_returns.sample(num_days, replace=True,
                                          random_state=seed)

    return samples


def summarize_paths(samples, cone_std=(1., 1.5, 2.), starting_value=1.):
    


    cum_samples = ep.cum_returns(samples.T,
                                 starting_value=starting_value).T

    cum_mean = cum_samples.mean(axis=0)
    cum_std = cum_samples.std(axis=0)

    if isinstance(cone_std, (float, int)):
        cone_std = [cone_std]

    cone_bounds = pd.DataFrame(columns=pd.Float64Index([]))
    for num_std in cone_std:
        cone_bounds.loc[:, float(num_std)] = cum_mean + cum_std * num_std
        cone_bounds.loc[:, float(-num_std)] = cum_mean - cum_std * num_std

    return cone_bounds


def forecast_cone_bootstrap(is_returns, num_days, cone_std=(1., 1.5, 2.),
                            starting_value=1, num_samples=1000,
                            random_seed=None):
    


    samples = simulate_paths(
        is_returns=is_returns,
        num_days=num_days,
        starting_value=starting_value,
        num_samples=num_samples,
        random_seed=random_seed
    )

    cone_bounds = summarize_paths(
        samples=samples,
        cone_std=cone_std,
        starting_value=starting_value
    )

    return cone_bounds


def extract_interesting_date_ranges(returns):
    


    returns_dupe = returns.copy()
    returns_dupe.index = returns_dupe.index.map(pd.Timestamp)
    ranges = OrderedDict()
    for name, (start, end) in PERIODS.items():
        try:
            period = returns_dupe.loc[start:end]
            if len(period) == 0:
                continue
            ranges[name] = period
        except BaseException:
            continue

    return ranges
