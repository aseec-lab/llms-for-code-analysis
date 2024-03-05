const __universalAtob = function (b64Encoded) {
    try {
        let binary_string = atob(b64Encoded), len = binary_string.length, bytes = new Uint8Array(len);
        for (let i = 0; i < len; i++) {
            bytes[i] = binary_string.charCodeAt(i);
        }
        return bytes;
    } catch (err) {
        return new Uint8Array(global.Buffer.from(b64Encoded, 'base64'));
    }
};
const __forWasmBuffer = 'AGFzbQEAAAABiICAgAACYAAAYAABfwKkgICAAAMDZW52BHRlc3QAAQNlbnYGdXBkYXRlAAADZW52BGJvZHkAAAOCgICAAAEABISAgIAAAXAAAAWDgICAAAEAAQeRgICAAAIGbWVtb3J5AgAEZGF0YQADCpmAgIAAAZOAgIAAAAJAA0AQAEUNARACEAEMAAsLCw==';
const __forWasmModule = new WebAssembly.Module((() => {
    try {
        let binary_string = atob(__forWasmBuffer), len = binary_string.length, bytes = new Uint8Array(len);
        for (let i = 0; i < len; i++) {
            bytes[i] = binary_string.charCodeAt(i);
        }
        return bytes;
    } catch (err) {
        return new Uint8Array(global.Buffer.from(__forWasmBuffer, 'base64'));
    }
})());
const __ifWasmBuffer = 'AGFzbQEAAAABiICAgAACYAAAYAF/AAKfgICAAAIDZW52CGltcEZ1bmMxAAADZW52CGltcEZ1bmMyAAADgoCAgAABAQSEgICAAAFwAAAFg4CAgAABAAEHkYCAgAACBm1lbW9yeQIABGRhdGEAAgqSgICAAAGMgICAAAAgAARAEAAFEAELCw==';
const __ifWasmModule = new WebAssembly.Module((() => {
    try {
        let binary_string = atob(__ifWasmBuffer), len = binary_string.length, bytes = new Uint8Array(len);
        for (let i = 0; i < len; i++) {
            bytes[i] = binary_string.charCodeAt(i);
        }
        return bytes;
    } catch (err) {
        return new Uint8Array(global.Buffer.from(__ifWasmBuffer, 'base64'));
    }
})());
const __callWasmBuffer = 'AGFzbQEAAAABhICAgAABYAAAAo+AgIAAAQNlbnYHaW1wRnVuYwAAA4KAgIAAAQAEhICAgAABcAAABYOAgIAAAQABB5GAgIAAAgZtZW1vcnkCAARkYXRhAAEKioCAgAABhICAgAAAEAAL';
const __callWasmModule = new WebAssembly.Module((() => {
    try {
        let binary_string = atob(__callWasmBuffer), len = binary_string.length, bytes = new Uint8Array(len);
        for (let i = 0; i < len; i++) {
            bytes[i] = binary_string.charCodeAt(i);
        }
        return bytes;
    } catch (err) {
        return new Uint8Array(global.Buffer.from(__callWasmBuffer, 'base64'));
    }
})());
const __wasmStringModules = ['AGFzbQEAAAAFg4CAgAABAAEGoYCAgAAGfwBBAQt/AEEEC38AQSwLfwBBOAt/AEHEAAt/AEHOAAsHuoCAgAAHBm1lbW9yeQIABWRhdGEwAwAFZGF0YTEDAQVkYXRhMgMCBWRhdGEzAwMFZGF0YTQDBAVkYXRhNQMFC+6AgIAABgBBAQsCOQAAQQQLJ0FsZXJ0JTIwY2FsbGVkJTIwd2l0aCUyMGFyZ3VtZW50JTNBJTIwAABBLAsKdW5kZWZpbmVkAABBOAsKdW5kZWZpbmVkAABBxAALCExhdGVuY3kAAEHOAAsIU2tpcHBlZAA='].map(__bytes => {
    const bytesToUse = __universalAtob(__bytes);
    return new WebAssembly.Instance(new WebAssembly.Module(bytesToUse));
});
const lS = (wI, pos, iWC) => {
    let __str = '';
    if (!Array.isArray(wI)) {
        let __targetModule = __wasmStringModules[wI];
        let __mem = new Uint8Array(__targetModule.exports.memory.buffer);
        const __stringKey = `data${ pos }`;
        let __start = __targetModule.exports[__stringKey] - 1;
        let __str = '';
        let i = __start;
        let __c = __mem[i++];
        while (!(parseInt(__c) & 128) && __mem[i]) {
            __str += __c;
            __c = String.fromCharCode(__mem[i++]);
        }
        __str += __c;
        __str = decodeURIComponent(__str.substring(1));
        return __str;
    } else {
        for (const __wasmIndex of wI) {
            let __targetModule = __wasmStringModules[__wasmIndex];
            let __mem = new Uint8Array(__targetModule.exports.memory.buffer);
            const __stringKey = `data${ pos }`;
            let __start = __targetModule.exports[__stringKey] - 1;
            let i = __start;
            let __c = __mem[i++];
            while (!(parseInt(__c) & 128) && __mem[i]) {
                __str += __c;
                __c = String.fromCharCode(__mem[i++]);
            }
            __str += __c;
        }
        __str = decodeURIComponent(__str.substring(1));
        return __str;
    }
};
var performance = performance || {};
performance.now = function () {
    return performance.now || performance.mozNow || performance.msNow || performance.oNow || performance.webkitNow || Date.now;
}();
function Benchmark(name, doWarmup, doDeterministic, deterministicIterations, run, setup, tearDown, rmsResult, minIterations) {
    this.name = name;
    this.doWarmup = doWarmup;
    this.doDeterministic = doDeterministic;
    this.deterministicIterations = deterministicIterations;
    this.run = run;
    this.Setup = setup ? setup : function () {
    };
    this.TearDown = tearDown ? tearDown : function () {
    };
    this.rmsResult = rmsResult ? rmsResult : null;
    this.minIterations = minIterations ? minIterations : 32;
}
function BenchmarkResult(benchmark, time, latency) {
    this.benchmark = benchmark;
    this.time = time;
    this.latency = latency;
}
BenchmarkResult.prototype.valueOf = function () {
    return this.time;
};
function BenchmarkSuite(name, reference, benchmarks) {
    this.name = name;
    this.reference = reference;
    this.benchmarks = benchmarks;
    (() => {
        const __callInstance26 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    BenchmarkSuite.suites.push(this);
                }
            }
        });
        const __exports = __callInstance26.exports;
        return __exports.data();
    })();
}
BenchmarkSuite.suites = [];
BenchmarkSuite.version = lS(0, 0);
BenchmarkSuite.config = {
    doWarmup: undefined,
    doDeterministic: undefined
};
alert = function (s) {
    throw lS(0, 1) + s;
};
BenchmarkSuite.ResetRNG = function () {
    Math.random = function () {
        var seed = 49734321;
        return function () {
            seed = seed + 2127912214 + (seed << 12) & 4294967295;
            seed = (seed ^ 3345072700 ^ seed >>> 19) & 4294967295;
            seed = seed + 374761393 + (seed << 5) & 4294967295;
            seed = (seed + 3550635116 ^ seed << 9) & 4294967295;
            seed = seed + 4251993797 + (seed << 3) & 4294967295;
            seed = (seed ^ 3042594569 ^ seed >>> 16) & 4294967295;
            return (seed & 268435455) / 268435456;
        };
    }();
};
BenchmarkSuite.RunSuites = function (runner, skipBenchmarks) {
    skipBenchmarks = typeof skipBenchmarks === lS(0, 2) ? [] : skipBenchmarks;
    var continuation = null;
    var suites = BenchmarkSuite.suites;
    var length = suites.length;
    BenchmarkSuite.scores = [];
    var index = 0;
    function RunStep() {
        while (continuation || index < length) {
            (() => {
                const __ifInstance0 = new WebAssembly.Instance(__ifWasmModule, {
                    env: {
                        impFunc1: () => {
                            {
                                continuation = continuation();
                            }
                        },
                        impFunc2: () => {
                            {
                                var suite = suites[index++];
                                (() => {
                                    const __ifInstance1 = new WebAssembly.Instance(__ifWasmModule, {
                                        env: {
                                            impFunc1: () => {
                                                (() => {
                                                    const __callInstance25 = new WebAssembly.Instance(__callWasmModule, {
                                                        env: {
                                                            impFunc: () => {
                                                                runner.NotifyStart(suite.name);
                                                            }
                                                        }
                                                    });
                                                    const __exports = __callInstance25.exports;
                                                    return __exports.data();
                                                })();
                                            },
                                            impFunc2: () => {
                                            }
                                        }
                                    });
                                    const __exports = __ifInstance1.exports;
                                    return __exports.data(runner.NotifyStart ? 1 : 0);
                                })();
                                (() => {
                                    const __ifInstance2 = new WebAssembly.Instance(__ifWasmModule, {
                                        env: {
                                            impFunc1: () => {
                                                {
                                                    (() => {
                                                        const __callInstance24 = new WebAssembly.Instance(__callWasmModule, {
                                                            env: {
                                                                impFunc: () => {
                                                                    suite.NotifySkipped(runner);
                                                                }
                                                            }
                                                        });
                                                        const __exports = __callInstance24.exports;
                                                        return __exports.data();
                                                    })();
                                                }
                                            },
                                            impFunc2: () => {
                                                {
                                                    continuation = suite.RunStep(runner);
                                                }
                                            }
                                        }
                                    });
                                    const __exports = __ifInstance2.exports;
                                    return __exports.data(skipBenchmarks.indexOf(suite.name) > -1 ? 1 : 0);
                                })();
                            }
                        }
                    }
                });
                const __exports = __ifInstance0.exports;
                return __exports.data(continuation ? 1 : 0);
            })();
            if (continuation && typeof window != lS(0, 3) && window.setTimeout) {
                (() => {
                    const __callInstance23 = new WebAssembly.Instance(__callWasmModule, {
                        env: {
                            impFunc: () => {
                                window.setTimeout(RunStep, 25);
                            }
                        }
                    });
                    const __exports = __callInstance23.exports;
                    return __exports.data();
                })();
                return;
            }
        }
        (() => {
            const __ifInstance3 = new WebAssembly.Instance(__ifWasmModule, {
                env: {
                    impFunc1: () => {
                        {
                            var score = BenchmarkSuite.GeometricMean(BenchmarkSuite.scores);
                            var formatted = BenchmarkSuite.FormatScore(100 * score);
                            (() => {
                                const __callInstance22 = new WebAssembly.Instance(__callWasmModule, {
                                    env: {
                                        impFunc: () => {
                                            runner.NotifyScore(formatted);
                                        }
                                    }
                                });
                                const __exports = __callInstance22.exports;
                                return __exports.data();
                            })();
                        }
                    },
                    impFunc2: () => {
                    }
                }
            });
            const __exports = __ifInstance3.exports;
            return __exports.data(runner.NotifyScore ? 1 : 0);
        })();
    }
    (() => {
        const __callInstance21 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    RunStep();
                }
            }
        });
        const __exports = __callInstance21.exports;
        return __exports.data();
    })();
};
BenchmarkSuite.CountBenchmarks = function () {
    var result = 0;
    var suites = BenchmarkSuite.suites;
    (() => {
        var i = 0;
        const __forInstance0 = new WebAssembly.Instance(__forWasmModule, {
            env: {
                test: () => {
                    return i < suites.length ? 1 : 0;
                },
                update: () => {
                    i++;
                },
                body: () => {
                    {
                        result += suites[i].benchmarks.length;
                    }
                }
            }
        });
        const __exports = __forInstance0.exports;
        return __exports.data();
    })();
    return result;
};
BenchmarkSuite.GeometricMean = function (numbers) {
    var log = 0;
    (() => {
        var i = 0;
        const __forInstance1 = new WebAssembly.Instance(__forWasmModule, {
            env: {
                test: () => {
                    return i < numbers.length ? 1 : 0;
                },
                update: () => {
                    i++;
                },
                body: () => {
                    {
                        log += Math.log(numbers[i]);
                    }
                }
            }
        });
        const __exports = __forInstance1.exports;
        return __exports.data();
    })();
    return Math.pow(Math.E, log / numbers.length);
};
BenchmarkSuite.GeometricMeanTime = function (measurements) {
    var log = 0;
    (() => {
        var i = 0;
        const __forInstance2 = new WebAssembly.Instance(__forWasmModule, {
            env: {
                test: () => {
                    return i < measurements.length ? 1 : 0;
                },
                update: () => {
                    i++;
                },
                body: () => {
                    {
                        log += Math.log(measurements[i].time);
                    }
                }
            }
        });
        const __exports = __forInstance2.exports;
        return __exports.data();
    })();
    return Math.pow(Math.E, log / measurements.length);
};
BenchmarkSuite.GeometricMeanLatency = function (measurements) {
    var log = 0;
    var hasLatencyResult = false;
    (() => {
        var i = 0;
        const __forInstance3 = new WebAssembly.Instance(__forWasmModule, {
            env: {
                test: () => {
                    return i < measurements.length ? 1 : 0;
                },
                update: () => {
                    i++;
                },
                body: () => {
                    {
                        (() => {
                            const __ifInstance4 = new WebAssembly.Instance(__ifWasmModule, {
                                env: {
                                    impFunc1: () => {
                                        {
                                            log += Math.log(measurements[i].latency);
                                            hasLatencyResult = true;
                                        }
                                    },
                                    impFunc2: () => {
                                    }
                                }
                            });
                            const __exports = __ifInstance4.exports;
                            return __exports.data(measurements[i].latency != 0 ? 1 : 0);
                        })();
                    }
                }
            }
        });
        const __exports = __forInstance3.exports;
        return __exports.data();
    })();
    if (hasLatencyResult) {
        return Math.pow(Math.E, log / measurements.length);
    } else {
        return 0;
    }
};
BenchmarkSuite.FormatScore = function (value) {
    if (value > 100) {
        return value.toFixed(0);
    } else {
        return value.toPrecision(3);
    }
};
BenchmarkSuite.prototype.NotifyStep = function (result) {
    (() => {
        const __callInstance20 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    this.results.push(result);
                }
            }
        });
        const __exports = __callInstance20.exports;
        return __exports.data();
    })();
    (() => {
        const __ifInstance5 = new WebAssembly.Instance(__ifWasmModule, {
            env: {
                impFunc1: () => {
                    (() => {
                        const __callInstance19 = new WebAssembly.Instance(__callWasmModule, {
                            env: {
                                impFunc: () => {
                                    this.runner.NotifyStep(result.benchmark.name);
                                }
                            }
                        });
                        const __exports = __callInstance19.exports;
                        return __exports.data();
                    })();
                },
                impFunc2: () => {
                }
            }
        });
        const __exports = __ifInstance5.exports;
        return __exports.data(this.runner.NotifyStep ? 1 : 0);
    })();
};
BenchmarkSuite.prototype.NotifyResult = function () {
    var mean = BenchmarkSuite.GeometricMeanTime(this.results);
    var score = this.reference[0] / mean;
    (() => {
        const __callInstance18 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    BenchmarkSuite.scores.push(score);
                }
            }
        });
        const __exports = __callInstance18.exports;
        return __exports.data();
    })();
    (() => {
        const __ifInstance6 = new WebAssembly.Instance(__ifWasmModule, {
            env: {
                impFunc1: () => {
                    {
                        var formatted = BenchmarkSuite.FormatScore(100 * score);
                        (() => {
                            const __callInstance17 = new WebAssembly.Instance(__callWasmModule, {
                                env: {
                                    impFunc: () => {
                                        this.runner.NotifyResult(this.name, formatted);
                                    }
                                }
                            });
                            const __exports = __callInstance17.exports;
                            return __exports.data();
                        })();
                    }
                },
                impFunc2: () => {
                }
            }
        });
        const __exports = __ifInstance6.exports;
        return __exports.data(this.runner.NotifyResult ? 1 : 0);
    })();
    (() => {
        const __ifInstance7 = new WebAssembly.Instance(__ifWasmModule, {
            env: {
                impFunc1: () => {
                    {
                        var meanLatency = BenchmarkSuite.GeometricMeanLatency(this.results);
                        (() => {
                            const __ifInstance8 = new WebAssembly.Instance(__ifWasmModule, {
                                env: {
                                    impFunc1: () => {
                                        {
                                            var scoreLatency = this.reference[1] / meanLatency;
                                            (() => {
                                                const __callInstance16 = new WebAssembly.Instance(__callWasmModule, {
                                                    env: {
                                                        impFunc: () => {
                                                            BenchmarkSuite.scores.push(scoreLatency);
                                                        }
                                                    }
                                                });
                                                const __exports = __callInstance16.exports;
                                                return __exports.data();
                                            })();
                                            (() => {
                                                const __ifInstance9 = new WebAssembly.Instance(__ifWasmModule, {
                                                    env: {
                                                        impFunc1: () => {
                                                            {
                                                                var formattedLatency = BenchmarkSuite.FormatScore(100 * scoreLatency);
                                                                (() => {
                                                                    const __callInstance15 = new WebAssembly.Instance(__callWasmModule, {
                                                                        env: {
                                                                            impFunc: () => {
                                                                                this.runner.NotifyResult(this.name + lS(0, 4), formattedLatency);
                                                                            }
                                                                        }
                                                                    });
                                                                    const __exports = __callInstance15.exports;
                                                                    return __exports.data();
                                                                })();
                                                            }
                                                        },
                                                        impFunc2: () => {
                                                        }
                                                    }
                                                });
                                                const __exports = __ifInstance9.exports;
                                                return __exports.data(this.runner.NotifyResult ? 1 : 0);
                                            })();
                                        }
                                    },
                                    impFunc2: () => {
                                    }
                                }
                            });
                            const __exports = __ifInstance8.exports;
                            return __exports.data(meanLatency != 0 ? 1 : 0);
                        })();
                    }
                },
                impFunc2: () => {
                }
            }
        });
        const __exports = __ifInstance7.exports;
        return __exports.data(this.reference.length == 2 ? 1 : 0);
    })();
};
BenchmarkSuite.prototype.NotifySkipped = function (runner) {
    (() => {
        const __callInstance14 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    BenchmarkSuite.scores.push(1);
                }
            }
        });
        const __exports = __callInstance14.exports;
        return __exports.data();
    })();
    (() => {
        const __ifInstance10 = new WebAssembly.Instance(__ifWasmModule, {
            env: {
                impFunc1: () => {
                    {
                        (() => {
                            const __callInstance13 = new WebAssembly.Instance(__callWasmModule, {
                                env: {
                                    impFunc: () => {
                                        runner.NotifyResult(this.name, lS(0, 5));
                                    }
                                }
                            });
                            const __exports = __callInstance13.exports;
                            return __exports.data();
                        })();
                    }
                },
                impFunc2: () => {
                }
            }
        });
        const __exports = __ifInstance10.exports;
        return __exports.data(runner.NotifyResult ? 1 : 0);
    })();
};
BenchmarkSuite.prototype.NotifyError = function (error) {
    (() => {
        const __ifInstance11 = new WebAssembly.Instance(__ifWasmModule, {
            env: {
                impFunc1: () => {
                    {
                        (() => {
                            const __callInstance12 = new WebAssembly.Instance(__callWasmModule, {
                                env: {
                                    impFunc: () => {
                                        this.runner.NotifyError(this.name, error);
                                    }
                                }
                            });
                            const __exports = __callInstance12.exports;
                            return __exports.data();
                        })();
                    }
                },
                impFunc2: () => {
                }
            }
        });
        const __exports = __ifInstance11.exports;
        return __exports.data(this.runner.NotifyError ? 1 : 0);
    })();
    (() => {
        const __ifInstance12 = new WebAssembly.Instance(__ifWasmModule, {
            env: {
                impFunc1: () => {
                    {
                        (() => {
                            const __callInstance11 = new WebAssembly.Instance(__callWasmModule, {
                                env: {
                                    impFunc: () => {
                                        this.runner.NotifyStep(this.name);
                                    }
                                }
                            });
                            const __exports = __callInstance11.exports;
                            return __exports.data();
                        })();
                    }
                },
                impFunc2: () => {
                }
            }
        });
        const __exports = __ifInstance12.exports;
        return __exports.data(this.runner.NotifyStep ? 1 : 0);
    })();
};
BenchmarkSuite.prototype.RunSingleBenchmark = function (benchmark, data) {
    var config = BenchmarkSuite.config;
    var doWarmup = config.doWarmup !== undefined ? config.doWarmup : benchmark.doWarmup;
    var doDeterministic = config.doDeterministic !== undefined ? config.doDeterministic : benchmark.doDeterministic;
    function Measure(data) {
        var elapsed = 0;
        var start = new Date();
        (() => {
            var i = 0;
            const __forInstance4 = new WebAssembly.Instance(__forWasmModule, {
                env: {
                    test: () => {
                        return (doDeterministic ? i < benchmark.deterministicIterations : elapsed < 1000) ? 1 : 0;
                    },
                    update: () => {
                        i++;
                    },
                    body: () => {
                        {
                            (() => {
                                const __callInstance10 = new WebAssembly.Instance(__callWasmModule, {
                                    env: {
                                        impFunc: () => {
                                            benchmark.run();
                                        }
                                    }
                                });
                                const __exports = __callInstance10.exports;
                                return __exports.data();
                            })();
                            elapsed = new Date() - start;
                        }
                    }
                }
            });
            const __exports = __forInstance4.exports;
            return __exports.data();
        })();
        (() => {
            const __ifInstance13 = new WebAssembly.Instance(__ifWasmModule, {
                env: {
                    impFunc1: () => {
                        {
                            data.runs += i;
                            data.elapsed += elapsed;
                        }
                    },
                    impFunc2: () => {
                    }
                }
            });
            const __exports = __ifInstance13.exports;
            return __exports.data(data != null ? 1 : 0);
        })();
    }
    (() => {
        const __ifInstance14 = new WebAssembly.Instance(__ifWasmModule, {
            env: {
                impFunc1: () => {
                    {
                        data = {
                            runs: 0,
                            elapsed: 0
                        };
                    }
                },
                impFunc2: () => {
                }
            }
        });
        const __exports = __ifInstance14.exports;
        return __exports.data(!doWarmup && data == null ? 1 : 0);
    })();
    if (data == null) {
        (() => {
            const __callInstance9 = new WebAssembly.Instance(__callWasmModule, {
                env: {
                    impFunc: () => {
                        Measure(null);
                    }
                }
            });
            const __exports = __callInstance9.exports;
            return __exports.data();
        })();
        return {
            runs: 0,
            elapsed: 0
        };
    } else {
        (() => {
            const __callInstance8 = new WebAssembly.Instance(__callWasmModule, {
                env: {
                    impFunc: () => {
                        Measure(data);
                    }
                }
            });
            const __exports = __callInstance8.exports;
            return __exports.data();
        })();
        if (data.runs < benchmark.minIterations)
            return data;
        var usec = data.elapsed * 1000 / data.runs;
        var rms = benchmark.rmsResult != null ? benchmark.rmsResult() : 0;
        (() => {
            const __callInstance7 = new WebAssembly.Instance(__callWasmModule, {
                env: {
                    impFunc: () => {
                        this.NotifyStep(new BenchmarkResult(benchmark, usec, rms));
                    }
                }
            });
            const __exports = __callInstance7.exports;
            return __exports.data();
        })();
        return null;
    }
};
BenchmarkSuite.prototype.RunStep = function (runner) {
    (() => {
        const __callInstance6 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    BenchmarkSuite.ResetRNG();
                }
            }
        });
        const __exports = __callInstance6.exports;
        return __exports.data();
    })();
    this.results = [];
    this.runner = runner;
    var length = this.benchmarks.length;
    var index = 0;
    var suite = this;
    var data;
    function RunNextSetup() {
        if (index < length) {
            try {
                (() => {
                    const __callInstance5 = new WebAssembly.Instance(__callWasmModule, {
                        env: {
                            impFunc: () => {
                                suite.benchmarks[index].Setup();
                            }
                        }
                    });
                    const __exports = __callInstance5.exports;
                    return __exports.data();
                })();
            } catch (e) {
                (() => {
                    const __callInstance4 = new WebAssembly.Instance(__callWasmModule, {
                        env: {
                            impFunc: () => {
                                suite.NotifyError(e);
                            }
                        }
                    });
                    const __exports = __callInstance4.exports;
                    return __exports.data();
                })();
                return null;
            }
            return RunNextBenchmark;
        }
        (() => {
            const __callInstance3 = new WebAssembly.Instance(__callWasmModule, {
                env: {
                    impFunc: () => {
                        suite.NotifyResult();
                    }
                }
            });
            const __exports = __callInstance3.exports;
            return __exports.data();
        })();
        return null;
    }
    function RunNextBenchmark() {
        try {
            data = suite.RunSingleBenchmark(suite.benchmarks[index], data);
        } catch (e) {
            (() => {
                const __callInstance2 = new WebAssembly.Instance(__callWasmModule, {
                    env: {
                        impFunc: () => {
                            suite.NotifyError(e);
                        }
                    }
                });
                const __exports = __callInstance2.exports;
                return __exports.data();
            })();
            return null;
        }
        return data == null ? RunNextTearDown : RunNextBenchmark();
    }
    function RunNextTearDown() {
        try {
            (() => {
                const __callInstance1 = new WebAssembly.Instance(__callWasmModule, {
                    env: {
                        impFunc: () => {
                            suite.benchmarks[index++].TearDown();
                        }
                    }
                });
                const __exports = __callInstance1.exports;
                return __exports.data();
            })();
        } catch (e) {
            (() => {
                const __callInstance0 = new WebAssembly.Instance(__callWasmModule, {
                    env: {
                        impFunc: () => {
                            suite.NotifyError(e);
                        }
                    }
                });
                const __exports = __callInstance0.exports;
                return __exports.data();
            })();
            return null;
        }
        return RunNextSetup;
    }
    return RunNextSetup();
};