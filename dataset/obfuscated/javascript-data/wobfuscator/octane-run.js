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
const __wasmStringModules = ['AGFzbQEAAAAFg4CAgAABAAEGkYGAgAAZfwBBAQt/AEEEC38AQQ4LfwBBHAt/AEEqC38AQTYLfwBBxAALfwBB1gALfwBB4gALfwBB7AALfwBB/gALfwBBiAELfwBBlgELfwBBpgELfwBBtgELfwBBxAELfwBBzgELfwBB2AELfwBB5gELfwBB9gELfwBBjAILfwBBpAILfwBBrAILfwBBsgILfwBByAILB+GBgIAAGgZtZW1vcnkCAAVkYXRhMAMABWRhdGExAwEFZGF0YTIDAgVkYXRhMwMDBWRhdGE0AwQFZGF0YTUDBQVkYXRhNgMGBWRhdGE3AwcFZGF0YTgDCAVkYXRhOQMJBmRhdGExMAMKBmRhdGExMQMLBmRhdGExMgMMBmRhdGExMwMNBmRhdGExNAMOBmRhdGExNQMPBmRhdGExNgMQBmRhdGExNwMRBmRhdGExOAMSBmRhdGExOQMTBmRhdGEyMAMUBmRhdGEyMQMVBmRhdGEyMgMWBmRhdGEyMwMXBmRhdGEyNAMYC7yDgIAAGQBBAQsBAABBBAsIYmFzZS5qcwAAQQ4LDHJpY2hhcmRzLmpzAABBHAsNZGVsdGFibHVlLmpzAABBKgsKY3J5cHRvLmpzAABBNgsMcmF5dHJhY2UuanMAAEHEAAsQZWFybGV5LWJveWVyLmpzAABB1gALCnJlZ2V4cC5qcwAAQeIACwlzcGxheS5qcwAAQewACxFuYXZpZXItc3Rva2VzLmpzAABB/gALCXBkZmpzLmpzAABBiAELDG1hbmRyZWVsLmpzAABBlgELD2diZW11LXBhcnQxLmpzAABBpgELD2diZW11LXBhcnQyLmpzAABBtgELDWNvZGUtbG9hZC5qcwAAQcQBCwlib3gyZC5qcwAAQc4BCwh6bGliLmpzAABB2AELDXpsaWItZGF0YS5qcwAAQeYBCw50eXBlc2NyaXB0LmpzAABB9gELFHR5cGVzY3JpcHQtaW5wdXQuanMAAEGMAgsXdHlwZXNjcmlwdC1jb21waWxlci5qcwAAQaQCCwclM0ElMjAAAEGsAgsFLS0tLQAAQbICCxRTY29yZSUyMCh2ZXJzaW9uJTIwAABByAILCCklM0ElMjAA'].map(__bytes => {
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
var base_dir = lS(0, 0);
(() => {
    const __callInstance24 = new WebAssembly.Instance(__callWasmModule, {
        env: {
            impFunc: () => {
                load(base_dir + lS(0, 1));
            }
        }
    });
    const __exports = __callInstance24.exports;
    return __exports.data();
})();
(() => {
    const __callInstance23 = new WebAssembly.Instance(__callWasmModule, {
        env: {
            impFunc: () => {
                load(base_dir + lS(0, 2));
            }
        }
    });
    const __exports = __callInstance23.exports;
    return __exports.data();
})();
(() => {
    const __callInstance22 = new WebAssembly.Instance(__callWasmModule, {
        env: {
            impFunc: () => {
                load(base_dir + lS(0, 3));
            }
        }
    });
    const __exports = __callInstance22.exports;
    return __exports.data();
})();
(() => {
    const __callInstance21 = new WebAssembly.Instance(__callWasmModule, {
        env: {
            impFunc: () => {
                load(base_dir + lS(0, 4));
            }
        }
    });
    const __exports = __callInstance21.exports;
    return __exports.data();
})();
(() => {
    const __callInstance20 = new WebAssembly.Instance(__callWasmModule, {
        env: {
            impFunc: () => {
                load(base_dir + lS(0, 5));
            }
        }
    });
    const __exports = __callInstance20.exports;
    return __exports.data();
})();
(() => {
    const __callInstance19 = new WebAssembly.Instance(__callWasmModule, {
        env: {
            impFunc: () => {
                load(base_dir + lS(0, 6));
            }
        }
    });
    const __exports = __callInstance19.exports;
    return __exports.data();
})();
(() => {
    const __callInstance18 = new WebAssembly.Instance(__callWasmModule, {
        env: {
            impFunc: () => {
                load(base_dir + lS(0, 7));
            }
        }
    });
    const __exports = __callInstance18.exports;
    return __exports.data();
})();
(() => {
    const __callInstance17 = new WebAssembly.Instance(__callWasmModule, {
        env: {
            impFunc: () => {
                load(base_dir + lS(0, 8));
            }
        }
    });
    const __exports = __callInstance17.exports;
    return __exports.data();
})();
(() => {
    const __callInstance16 = new WebAssembly.Instance(__callWasmModule, {
        env: {
            impFunc: () => {
                load(base_dir + lS(0, 9));
            }
        }
    });
    const __exports = __callInstance16.exports;
    return __exports.data();
})();
(() => {
    const __callInstance15 = new WebAssembly.Instance(__callWasmModule, {
        env: {
            impFunc: () => {
                load(base_dir + lS(0, 10));
            }
        }
    });
    const __exports = __callInstance15.exports;
    return __exports.data();
})();
(() => {
    const __callInstance14 = new WebAssembly.Instance(__callWasmModule, {
        env: {
            impFunc: () => {
                load(base_dir + lS(0, 11));
            }
        }
    });
    const __exports = __callInstance14.exports;
    return __exports.data();
})();
(() => {
    const __callInstance13 = new WebAssembly.Instance(__callWasmModule, {
        env: {
            impFunc: () => {
                load(base_dir + lS(0, 12));
            }
        }
    });
    const __exports = __callInstance13.exports;
    return __exports.data();
})();
(() => {
    const __callInstance12 = new WebAssembly.Instance(__callWasmModule, {
        env: {
            impFunc: () => {
                load(base_dir + lS(0, 13));
            }
        }
    });
    const __exports = __callInstance12.exports;
    return __exports.data();
})();
(() => {
    const __callInstance11 = new WebAssembly.Instance(__callWasmModule, {
        env: {
            impFunc: () => {
                load(base_dir + lS(0, 14));
            }
        }
    });
    const __exports = __callInstance11.exports;
    return __exports.data();
})();
(() => {
    const __callInstance10 = new WebAssembly.Instance(__callWasmModule, {
        env: {
            impFunc: () => {
                load(base_dir + lS(0, 15));
            }
        }
    });
    const __exports = __callInstance10.exports;
    return __exports.data();
})();
(() => {
    const __callInstance9 = new WebAssembly.Instance(__callWasmModule, {
        env: {
            impFunc: () => {
                load(base_dir + lS(0, 16));
            }
        }
    });
    const __exports = __callInstance9.exports;
    return __exports.data();
})();
(() => {
    const __callInstance8 = new WebAssembly.Instance(__callWasmModule, {
        env: {
            impFunc: () => {
                load(base_dir + lS(0, 17));
            }
        }
    });
    const __exports = __callInstance8.exports;
    return __exports.data();
})();
(() => {
    const __callInstance7 = new WebAssembly.Instance(__callWasmModule, {
        env: {
            impFunc: () => {
                load(base_dir + lS(0, 18));
            }
        }
    });
    const __exports = __callInstance7.exports;
    return __exports.data();
})();
(() => {
    const __callInstance6 = new WebAssembly.Instance(__callWasmModule, {
        env: {
            impFunc: () => {
                load(base_dir + lS(0, 19));
            }
        }
    });
    const __exports = __callInstance6.exports;
    return __exports.data();
})();
(() => {
    const __callInstance5 = new WebAssembly.Instance(__callWasmModule, {
        env: {
            impFunc: () => {
                load(base_dir + lS(0, 20));
            }
        }
    });
    const __exports = __callInstance5.exports;
    return __exports.data();
})();
var success = true;
function PrintResult(name, result) {
    (() => {
        const __callInstance4 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    print(name + lS(0, 21) + result);
                }
            }
        });
        const __exports = __callInstance4.exports;
        return __exports.data();
    })();
}
function PrintError(name, error) {
    (() => {
        const __callInstance3 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    PrintResult(name, error);
                }
            }
        });
        const __exports = __callInstance3.exports;
        return __exports.data();
    })();
    success = false;
}
function PrintScore(score) {
    (() => {
        const __ifInstance0 = new WebAssembly.Instance(__ifWasmModule, {
            env: {
                impFunc1: () => {
                    {
                        (() => {
                            const __callInstance2 = new WebAssembly.Instance(__callWasmModule, {
                                env: {
                                    impFunc: () => {
                                        print(lS(0, 22));
                                    }
                                }
                            });
                            const __exports = __callInstance2.exports;
                            return __exports.data();
                        })();
                        (() => {
                            const __callInstance1 = new WebAssembly.Instance(__callWasmModule, {
                                env: {
                                    impFunc: () => {
                                        print(lS(0, 23) + BenchmarkSuite.version + lS(0, 24) + score);
                                    }
                                }
                            });
                            const __exports = __callInstance1.exports;
                            return __exports.data();
                        })();
                    }
                },
                impFunc2: () => {
                }
            }
        });
        const __exports = __ifInstance0.exports;
        return __exports.data(success ? 1 : 0);
    })();
}
BenchmarkSuite.config.doWarmup = undefined;
BenchmarkSuite.config.doDeterministic = undefined;
(() => {
    const __callInstance0 = new WebAssembly.Instance(__callWasmModule, {
        env: {
            impFunc: () => {
                BenchmarkSuite.RunSuites({
                    NotifyResult: PrintResult,
                    NotifyError: PrintError,
                    NotifyScore: PrintScore
                });
            }
        }
    });
    const __exports = __callInstance0.exports;
    return __exports.data();
})();