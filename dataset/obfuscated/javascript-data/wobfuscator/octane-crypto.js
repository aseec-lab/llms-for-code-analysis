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
const __aB = 'AGFzbQEAAAABiYCAgAACYAAAYAJ/fwADhoCAgAAFAQAAAAAFg4CAgAABAAEGhoCAgAABfwFBAAsHpoCAgAAFBm1lbW9yeQIABGFycjAAAQRhcnIxAAIEYXJyMgADBGFycjMABAqUhoCAAAWPgICAAAAjACAAQQRsaiABNgIAC5CAgIAAAQF/QRAkAEEAQcWfEBAAC46AgIAAAQF/QRQkAEEAQQAQAAu+hYCAAAEBf0EYJABBAEECEABBAUEDEABBAkEFEABBA0EHEABBBEELEABBBUENEABBBkEREABBB0ETEABBCEEXEABBCUEdEABBCkEfEABBC0ElEABBDEEpEABBDUErEABBDkEvEABBD0E1EABBEEE7EABBEUE9EABBEkHDABAAQRNBxwAQAEEUQckAEABBFUHPABAAQRZB0wAQAEEXQdkAEABBGEHhABAAQRlB5QAQAEEaQecAEABBG0HrABAAQRxB7QAQAEEdQfEAEABBHkH/ABAAQR9BgwEQAEEgQYkBEABBIUGLARAAQSJBlQEQAEEjQZcBEABBJEGdARAAQSVBowEQAEEmQacBEABBJ0GtARAAQShBswEQAEEpQbUBEABBKkG/ARAAQStBwQEQAEEsQcUBEABBLUHHARAAQS5B0wEQAEEvQd8BEABBMEHjARAAQTFB5QEQAEEyQekBEABBM0HvARAAQTRB8QEQAEE1QfsBEABBNkGBAhAAQTdBhwIQAEE4QY0CEABBOUGPAhAAQTpBlQIQAEE7QZkCEABBPEGbAhAAQT1BpQIQAEE+QbMCEABBP0G3AhAAQcAAQbkCEABBwQBBvQIQAEHCAEHLAhAAQcMAQdECEABBxABB2wIQAEHFAEHdAhAAQcYAQeECEABBxwBB5wIQAEHIAEHvAhAAQckAQfUCEABBygBB+wIQAEHLAEH/AhAAQcwAQYUDEABBzQBBjQMQAEHOAEGRAxAAQc8AQZkDEABB0ABBowMQAEHRAEGlAxAAQdIAQa8DEABB0wBBsQMQAEHUAEG3AxAAQdUAQbsDEABB1gBBwQMQAEHXAEHJAxAAQdgAQc0DEABB2QBBzwMQAEHaAEHTAxAAQdsAQd8DEABB3ABB5wMQAEHdAEHrAxAAQd4AQfMDEABB3wBB9wMQAEHgAEH9AxAAC4+AgIAAAQF/QZwDJABBAEEAEAAL', __wAM = new WebAssembly.Instance(new WebAssembly.Module((() => {
        try {
            let binary_string = atob(__aB), len = binary_string.length, bytes = new Uint8Array(len);
            for (let i = 0; i < len; i++) {
                bytes[i] = binary_string.charCodeAt(i);
            }
            return bytes;
        } catch (err) {
            return new Uint8Array(global.Buffer.from(__aB, 'base64'));
        }
    })()));
const ac = new Map();
const __lA = (pos, stIdx, eIdx) => {
    if (ac.has(pos)) {
        return ac.get(pos);
    } else {
        const sK = `arr${ pos }`;
        __wAM.exports[sK]();
        let mem = new Uint32Array(__wAM.exports.memory.buffer, stIdx, (eIdx - stIdx) / 4 + 1);
        const rA = Array.from(mem);
        ac.set(pos, rA);
        return rA;
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
const __wasmStringModules = ['AGFzbQEAAAAFg4CAgAABAAEG2YGAgAAlfwBBAQt/AEEKC38AQRQLfwBBHgt/AEEmC38AQS4LfwBB1AALfwBB2AALfwBB3AALfwBB4AALfwBB5AALfwBB6AALfwBB6gALfwBB7gALfwBB8gALfwBB9AALfwBB+AALfwBBgAELfwBBggELfwBBiAELfwBBjAELfwBBrgELfwBBzAELfwBB0AELfwBB0gELfwBB8gELfwBBkgILfwBBlAQLfwBBnAQLfwBBngYLfwBBoAcLfwBBoggLfwBBpAkLfwBBpgoLfwBBqAsLfwBB+AsLfwBByAwLB82CgIAAJgZtZW1vcnkCAAVkYXRhMAMABWRhdGExAwEFZGF0YTIDAgVkYXRhMwMDBWRhdGE0AwQFZGF0YTUDBQVkYXRhNgMGBWRhdGE3AwcFZGF0YTgDCAVkYXRhOQMJBmRhdGExMAMKBmRhdGExMQMLBmRhdGExMgMMBmRhdGExMwMNBmRhdGExNAMOBmRhdGExNQMPBmRhdGExNgMQBmRhdGExNwMRBmRhdGExOAMSBmRhdGExOQMTBmRhdGEyMAMUBmRhdGEyMQMVBmRhdGEyMgMWBmRhdGEyMwMXBmRhdGEyNAMYBmRhdGEyNQMZBmRhdGEyNgMaBmRhdGEyNwMbBmRhdGEyOAMcBmRhdGEyOQMdBmRhdGEzMAMeBmRhdGEzMQMfBmRhdGEzMgMgBmRhdGEzMwMhBmRhdGEzNAMiBmRhdGEzNQMjBmRhdGEzNgMkC42OgIAAJQBBAQsHQ3J5cHRvAABBCgsIRW5jcnlwdAAAQRQLCERlY3J5cHQAAEEeCwdudW1iZXIAAEEmCwdzdHJpbmcAAEEuCyUwMTIzNDU2Nzg5YWJjZGVmZ2hpamtsbW5vcHFyc3R1dnd4eXoAAEHUAAsCMAAAQdgACwJhAABB3AALAkEAAEHgAAsCLQAAQeQACwItAABB6AALAQAAQeoACwIwAABB7gALAjAAAEHyAAsBAABB9AALAi0AAEH4AAsHbnVtYmVyAABBgAELAQAAQYIBCwQlMEEAAEGIAQsCMAAAQYwBCyFNZXNzYWdlJTIwdG9vJTIwbG9uZyUyMGZvciUyMFJTQQAAQa4BCx1JbnZhbGlkJTIwUlNBJTIwcHVibGljJTIwa2V5AABBzAELAjAAAEHQAQsBAABB0gELHkludmFsaWQlMjBSU0ElMjBwcml2YXRlJTIwa2V5AABB8gELHkludmFsaWQlMjBSU0ElMjBwcml2YXRlJTIwa2V5AABBkgILgQJhNTI2MTkzOTk3NTk0OGJiN2E1OGRmZmU1ZmY1NGU2NWYwNDk4ZjkxNzVmNWEwOTI4ODgxMGI4OTc1ODcxZTk5YWYzYjVkZDk0MDU3YjBmYzA3NTM1ZjVmOTc0NDQ1MDRmYTM1MTY5ZDQ2MWQwZDMwY2YwMTkyZTMwNzcyN2MwNjUxNjhjNzg4NzcxYzU2MWE5NDAwZmI0OTE3NWU5ZTZhYTRlMjNmZTExYWY2OWU5NDEyZGQyM2IwY2I2Njg0YzRjMjQyOWJjZTEzOWU4NDhhYjI2ZDA4MjkwNzMzNTFmNGFjZDM2MDc0ZWFmZDAzNmE1ZWI4MzM1OWQyYTY5OGQzAABBlAQLBjEwMDAxAABBnAQLgQI4ZTk5MTJmNmQzNjQ1ODk0ZThkMzhjYjU4YzBkYjgxZmY1MTZjZjRjN2U1YTE0YzdmMWVkZGIxNDU5ZDJjZGVkNGQ4ZDI5M2ZjOTdhZWU2YWVmYjg2MTg1OWM4YjZhM2QxZGZlNzEwNDYzZTFmOWRkYzcyMDQ4YzA5NzUxOTcxYzRhNTgwYWE1MWViNTIzMzU3YTNjYzQ4ZDMxY2ZhZDFkNGExNjUwNjZlZDkyZDQ3NDhmYjY1NzEyMTFkYTVjYjE0YmMxMWI2ZTJkZjdjMWE1NTllNmQ1YWMxY2Q1Yzk0NzAzYTIyODkxNDY0ZmJhMjNkMGQ5NjUwODYyNzdhMTYxAABBngYLgQFkMDkwY2U1OGE5MmM3NTIzM2E2NDg2Y2IwYTkyMDliZjM1ODNiNjRmNTQwYzc2ZjUyOTRiYjk3ZDI4NWVlZDMzYWVjMjIwYmRlMTRiMjQxNzk1MTE3OGFjMTUyY2VhYjZkYTcwOTA5MDViNDc4MTk1NDk4YjM1MjA0OGYxNWU3ZAAAQaAHC4EBY2FiNTc1ZGM2NTJiYjY2ZGYxNWEwMzU5NjA5ZDUxZDFkYjE4NDc1MGMwMGM2Njk4YjkwZWYzNDY1Yzk5NjU1MTAzZWRiZjBkNTRjNTZhZWMwY2UzYzRkMjI1OTIzMzgwOTJhMTI2YTBjYzQ5ZjY1YTRhMzBkMjIyYjQxMWU1OGYAAEGiCAuBATFhMjRiY2E4ZTI3M2RmMmYwZTQ3YzE5OWJiZjY3ODYwNGU3ZGY3MjE1NDgwYzc3YzhkYjM5ZjQ5YjAwMGNlMmNmNzUwMDAzOGFjZmZmNTQzM2I3ZDU4MmEwMWYxODI2ZTZmNGQ0MmUxYzU3ZjVlMWZlZjdiMTJhYWJjNTlmZDI1AABBpAkLgQEzZDA2OTgyZWZiYmU0NzMzOWUxZjZkMzZiMTIxNmI4YTc0MWQ0MTBiMGM2NjJmNTRmNzExOGIyN2I5YTRlYzlkOTE0MzM3ZWIzOTg0MWQ4NjY2ZjMwMzQ0MDhjZjk0ZjViNjJmMTFjNDAyZmM5OTRmZTE1YTA1NDkzMTUwZDlmZAAAQaYKC4EBM2EzZTczMWFjZDg5NjBiN2ZmOWViODFhN2ZmOTNiZDFjZmE3NGNiZDU2OTg3ZGI1OGI0NTk0ZmIwOWMwOTA4NGRiMTczNGM4MTQzZjk4YjYwMmI5ODFhYWE5MjQzY2EyOGRlYjY5YjViMjgwZWU4ZGNlZTBmZDI2MjVlNTMyNTAAAEGoCwtOVGhlJTIwcXVpY2slMjBicm93biUyMGZveCUyMGp1bXBlZCUyMG92ZXIlMjB0aGUlMjBleHRyZW1lbHklMjBsYXp5JTIwZnJvZyElMjAAAEH4CwtPTm93JTIwaXMlMjB0aGUlMjB0aW1lJTIwZm9yJTIwYWxsJTIwZ29vZCUyMG1lbiUyMHRvJTIwY29tZSUyMHRvJTIwdGhlJTIwcGFydHkuAABByAwLHENyeXB0byUyMG9wZXJhdGlvbiUyMGZhaWxlZAA='].map(__bytes => {
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
var Crypto = new BenchmarkSuite(lS(0, 0), __lA(0, 16, 20), [
    new Benchmark(lS(0, 1), true, false, 3900, encrypt),
    new Benchmark(lS(0, 2), true, false, 220, decrypt)
]);
var dbits;
var BI_DB;
var BI_DM;
var BI_DV;
var BI_FP;
var BI_FV;
var BI_F1;
var BI_F2;
var canary = 244837814094590;
var j_lm = (canary & 16777215) == 15715070;
function BigInteger(a, b, c) {
    this.array = new Array();
    (() => {
        const __ifInstance0 = new WebAssembly.Instance(__ifWasmModule, {
            env: {
                impFunc1: () => {
                    (() => {
                        const __ifInstance1 = new WebAssembly.Instance(__ifWasmModule, {
                            env: {
                                impFunc1: () => {
                                    (() => {
                                        const __callInstance159 = new WebAssembly.Instance(__callWasmModule, {
                                            env: {
                                                impFunc: () => {
                                                    this.fromNumber(a, b, c);
                                                }
                                            }
                                        });
                                        const __exports = __callInstance159.exports;
                                        return __exports.data();
                                    })();
                                },
                                impFunc2: () => {
                                    (() => {
                                        const __ifInstance2 = new WebAssembly.Instance(__ifWasmModule, {
                                            env: {
                                                impFunc1: () => {
                                                    (() => {
                                                        const __callInstance158 = new WebAssembly.Instance(__callWasmModule, {
                                                            env: {
                                                                impFunc: () => {
                                                                    this.fromString(a, 256);
                                                                }
                                                            }
                                                        });
                                                        const __exports = __callInstance158.exports;
                                                        return __exports.data();
                                                    })();
                                                },
                                                impFunc2: () => {
                                                    (() => {
                                                        const __callInstance157 = new WebAssembly.Instance(__callWasmModule, {
                                                            env: {
                                                                impFunc: () => {
                                                                    this.fromString(a, b);
                                                                }
                                                            }
                                                        });
                                                        const __exports = __callInstance157.exports;
                                                        return __exports.data();
                                                    })();
                                                }
                                            }
                                        });
                                        const __exports = __ifInstance2.exports;
                                        return __exports.data(b == null && lS(0, 4) != typeof a ? 1 : 0);
                                    })();
                                }
                            }
                        });
                        const __exports = __ifInstance1.exports;
                        return __exports.data(lS(0, 3) == typeof a ? 1 : 0);
                    })();
                },
                impFunc2: () => {
                }
            }
        });
        const __exports = __ifInstance0.exports;
        return __exports.data(a != null ? 1 : 0);
    })();
}
function nbi() {
    return new BigInteger(null);
}
function am1(i, x, w, j, c, n) {
    var this_array = this.array;
    var w_array = w.array;
    (() => {
        const __forInstance26 = new WebAssembly.Instance(__forWasmModule, {
            env: {
                test: () => {
                    return --n >= 0 ? 1 : 0;
                },
                update: () => {
                },
                body: () => {
                    {
                        var v = x * this_array[i++] + w_array[j] + c;
                        c = Math.floor(v / 67108864);
                        w_array[j++] = v & 67108863;
                    }
                }
            }
        });
        const __exports = __forInstance26.exports;
        return __exports.data();
    })();
    return c;
}
function am2(i, x, w, j, c, n) {
    var this_array = this.array;
    var w_array = w.array;
    var xl = x & 32767, xh = x >> 15;
    (() => {
        const __forInstance27 = new WebAssembly.Instance(__forWasmModule, {
            env: {
                test: () => {
                    return --n >= 0 ? 1 : 0;
                },
                update: () => {
                },
                body: () => {
                    {
                        var l = this_array[i] & 32767;
                        var h = this_array[i++] >> 15;
                        var m = xh * l + h * xl;
                        l = xl * l + ((m & 32767) << 15) + w_array[j] + (c & 1073741823);
                        c = (l >>> 30) + (m >>> 15) + xh * h + (c >>> 30);
                        w_array[j++] = l & 1073741823;
                    }
                }
            }
        });
        const __exports = __forInstance27.exports;
        return __exports.data();
    })();
    return c;
}
function am3(i, x, w, j, c, n) {
    var this_array = this.array;
    var w_array = w.array;
    var xl = x & 16383, xh = x >> 14;
    (() => {
        const __forInstance28 = new WebAssembly.Instance(__forWasmModule, {
            env: {
                test: () => {
                    return --n >= 0 ? 1 : 0;
                },
                update: () => {
                },
                body: () => {
                    {
                        var l = this_array[i] & 16383;
                        var h = this_array[i++] >> 14;
                        var m = xh * l + h * xl;
                        l = xl * l + ((m & 16383) << 14) + w_array[j] + c;
                        c = (l >> 28) + (m >> 14) + xh * h;
                        w_array[j++] = l & 268435455;
                    }
                }
            }
        });
        const __exports = __forInstance28.exports;
        return __exports.data();
    })();
    return c;
}
function am4(i, x, w, j, c, n) {
    var this_array = this.array;
    var w_array = w.array;
    var xl = x & 8191, xh = x >> 13;
    (() => {
        const __forInstance29 = new WebAssembly.Instance(__forWasmModule, {
            env: {
                test: () => {
                    return --n >= 0 ? 1 : 0;
                },
                update: () => {
                },
                body: () => {
                    {
                        var l = this_array[i] & 8191;
                        var h = this_array[i++] >> 13;
                        var m = xh * l + h * xl;
                        l = xl * l + ((m & 8191) << 13) + w_array[j] + c;
                        c = (l >> 26) + (m >> 13) + xh * h;
                        w_array[j++] = l & 67108863;
                    }
                }
            }
        });
        const __exports = __forInstance29.exports;
        return __exports.data();
    })();
    return c;
}
setupEngine = function (fn, bits) {
    BigInteger.prototype.am = fn;
    dbits = bits;
    BI_DB = dbits;
    BI_DM = (1 << dbits) - 1;
    BI_DV = 1 << dbits;
    BI_FP = 52;
    BI_FV = Math.pow(2, BI_FP);
    BI_F1 = BI_FP - dbits;
    BI_F2 = 2 * dbits - BI_FP;
};
var BI_RM = lS(0, 5);
var BI_RC = new Array();
var rr, vv;
rr = lS(0, 6).charCodeAt(0);
(() => {
    vv = 0;
    const __forInstance0 = new WebAssembly.Instance(__forWasmModule, {
        env: {
            test: () => {
                return vv <= 9 ? 1 : 0;
            },
            update: () => {
                ++vv;
            },
            body: () => {
                BI_RC[rr++] = vv;
            }
        }
    });
    const __exports = __forInstance0.exports;
    return __exports.data();
})();
rr = lS(0, 7).charCodeAt(0);
(() => {
    vv = 10;
    const __forInstance1 = new WebAssembly.Instance(__forWasmModule, {
        env: {
            test: () => {
                return vv < 36 ? 1 : 0;
            },
            update: () => {
                ++vv;
            },
            body: () => {
                BI_RC[rr++] = vv;
            }
        }
    });
    const __exports = __forInstance1.exports;
    return __exports.data();
})();
rr = lS(0, 8).charCodeAt(0);
(() => {
    vv = 10;
    const __forInstance2 = new WebAssembly.Instance(__forWasmModule, {
        env: {
            test: () => {
                return vv < 36 ? 1 : 0;
            },
            update: () => {
                ++vv;
            },
            body: () => {
                BI_RC[rr++] = vv;
            }
        }
    });
    const __exports = __forInstance2.exports;
    return __exports.data();
})();
function int2char(n) {
    return BI_RM.charAt(n);
}
function intAt(s, i) {
    var c = BI_RC[s.charCodeAt(i)];
    return c == null ? -1 : c;
}
function bnpCopyTo(r) {
    var this_array = this.array;
    var r_array = r.array;
    (() => {
        var i = this.t - 1;
        const __forInstance3 = new WebAssembly.Instance(__forWasmModule, {
            env: {
                test: () => {
                    return i >= 0 ? 1 : 0;
                },
                update: () => {
                    --i;
                },
                body: () => {
                    r_array[i] = this_array[i];
                }
            }
        });
        const __exports = __forInstance3.exports;
        return __exports.data();
    })();
    r.t = this.t;
    r.s = this.s;
}
function bnpFromInt(x) {
    var this_array = this.array;
    this.t = 1;
    this.s = x < 0 ? -1 : 0;
    (() => {
        const __ifInstance3 = new WebAssembly.Instance(__ifWasmModule, {
            env: {
                impFunc1: () => {
                    this_array[0] = x;
                },
                impFunc2: () => {
                    (() => {
                        const __ifInstance4 = new WebAssembly.Instance(__ifWasmModule, {
                            env: {
                                impFunc1: () => {
                                    this_array[0] = x + DV;
                                },
                                impFunc2: () => {
                                    this.t = 0;
                                }
                            }
                        });
                        const __exports = __ifInstance4.exports;
                        return __exports.data(x < -1 ? 1 : 0);
                    })();
                }
            }
        });
        const __exports = __ifInstance3.exports;
        return __exports.data(x > 0 ? 1 : 0);
    })();
}
function nbv(i) {
    var r = nbi();
    (() => {
        const __callInstance156 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    r.fromInt(i);
                }
            }
        });
        const __exports = __callInstance156.exports;
        return __exports.data();
    })();
    return r;
}
function bnpFromString(s, b) {
    var this_array = this.array;
    var k;
    if (b == 16)
        k = 4;
    else if (b == 8)
        k = 3;
    else if (b == 256)
        k = 8;
    else if (b == 2)
        k = 1;
    else if (b == 32)
        k = 5;
    else if (b == 4)
        k = 2;
    else {
        (() => {
            const __callInstance155 = new WebAssembly.Instance(__callWasmModule, {
                env: {
                    impFunc: () => {
                        this.fromRadix(s, b);
                    }
                }
            });
            const __exports = __callInstance155.exports;
            return __exports.data();
        })();
        return;
    }
    this.t = 0;
    this.s = 0;
    var i = s.length, mi = false, sh = 0;
    while (--i >= 0) {
        var x = k == 8 ? s[i] & 255 : intAt(s, i);
        if (x < 0) {
            (() => {
                const __ifInstance5 = new WebAssembly.Instance(__ifWasmModule, {
                    env: {
                        impFunc1: () => {
                            mi = true;
                        },
                        impFunc2: () => {
                        }
                    }
                });
                const __exports = __ifInstance5.exports;
                return __exports.data(s.charAt(i) == lS(0, 9) ? 1 : 0);
            })();
            continue;
        }
        mi = false;
        (() => {
            const __ifInstance6 = new WebAssembly.Instance(__ifWasmModule, {
                env: {
                    impFunc1: () => {
                        this_array[this.t++] = x;
                    },
                    impFunc2: () => {
                        (() => {
                            const __ifInstance7 = new WebAssembly.Instance(__ifWasmModule, {
                                env: {
                                    impFunc1: () => {
                                        {
                                            this_array[this.t - 1] |= (x & (1 << BI_DB - sh) - 1) << sh;
                                            this_array[this.t++] = x >> BI_DB - sh;
                                        }
                                    },
                                    impFunc2: () => {
                                        this_array[this.t - 1] |= x << sh;
                                    }
                                }
                            });
                            const __exports = __ifInstance7.exports;
                            return __exports.data(sh + k > BI_DB ? 1 : 0);
                        })();
                    }
                }
            });
            const __exports = __ifInstance6.exports;
            return __exports.data(sh == 0 ? 1 : 0);
        })();
        sh += k;
        (() => {
            const __ifInstance8 = new WebAssembly.Instance(__ifWasmModule, {
                env: {
                    impFunc1: () => {
                        sh -= BI_DB;
                    },
                    impFunc2: () => {
                    }
                }
            });
            const __exports = __ifInstance8.exports;
            return __exports.data(sh >= BI_DB ? 1 : 0);
        })();
    }
    (() => {
        const __ifInstance9 = new WebAssembly.Instance(__ifWasmModule, {
            env: {
                impFunc1: () => {
                    {
                        this.s = -1;
                        (() => {
                            const __ifInstance10 = new WebAssembly.Instance(__ifWasmModule, {
                                env: {
                                    impFunc1: () => {
                                        this_array[this.t - 1] |= (1 << BI_DB - sh) - 1 << sh;
                                    },
                                    impFunc2: () => {
                                    }
                                }
                            });
                            const __exports = __ifInstance10.exports;
                            return __exports.data(sh > 0 ? 1 : 0);
                        })();
                    }
                },
                impFunc2: () => {
                }
            }
        });
        const __exports = __ifInstance9.exports;
        return __exports.data(k == 8 && (s[0] & 128) != 0 ? 1 : 0);
    })();
    (() => {
        const __callInstance154 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    this.clamp();
                }
            }
        });
        const __exports = __callInstance154.exports;
        return __exports.data();
    })();
    (() => {
        const __ifInstance11 = new WebAssembly.Instance(__ifWasmModule, {
            env: {
                impFunc1: () => {
                    (() => {
                        const __callInstance153 = new WebAssembly.Instance(__callWasmModule, {
                            env: {
                                impFunc: () => {
                                    BigInteger.ZERO.subTo(this, this);
                                }
                            }
                        });
                        const __exports = __callInstance153.exports;
                        return __exports.data();
                    })();
                },
                impFunc2: () => {
                }
            }
        });
        const __exports = __ifInstance11.exports;
        return __exports.data(mi ? 1 : 0);
    })();
}
function bnpClamp() {
    var this_array = this.array;
    var c = this.s & BI_DM;
    (() => {
        const __forInstance30 = new WebAssembly.Instance(__forWasmModule, {
            env: {
                test: () => {
                    return this.t > 0 && this_array[this.t - 1] == c ? 1 : 0;
                },
                update: () => {
                },
                body: () => {
                    --this.t;
                }
            }
        });
        const __exports = __forInstance30.exports;
        return __exports.data();
    })();
}
function bnToString(b) {
    var this_array = this.array;
    if (this.s < 0)
        return lS(0, 10) + this.negate().toString(b);
    var k;
    if (b == 16)
        k = 4;
    else if (b == 8)
        k = 3;
    else if (b == 2)
        k = 1;
    else if (b == 32)
        k = 5;
    else if (b == 4)
        k = 2;
    else
        return this.toRadix(b);
    var km = (1 << k) - 1, d, m = false, r = lS(0, 11), i = this.t;
    var p = BI_DB - i * BI_DB % k;
    (() => {
        const __ifInstance12 = new WebAssembly.Instance(__ifWasmModule, {
            env: {
                impFunc1: () => {
                    {
                        (() => {
                            const __ifInstance13 = new WebAssembly.Instance(__ifWasmModule, {
                                env: {
                                    impFunc1: () => {
                                        {
                                            m = true;
                                            r = int2char(d);
                                        }
                                    },
                                    impFunc2: () => {
                                    }
                                }
                            });
                            const __exports = __ifInstance13.exports;
                            return __exports.data(p < BI_DB && (d = this_array[i] >> p) > 0 ? 1 : 0);
                        })();
                        (() => {
                            const __forInstance31 = new WebAssembly.Instance(__forWasmModule, {
                                env: {
                                    test: () => {
                                        return i >= 0 ? 1 : 0;
                                    },
                                    update: () => {
                                    },
                                    body: () => {
                                        {
                                            (() => {
                                                const __ifInstance14 = new WebAssembly.Instance(__ifWasmModule, {
                                                    env: {
                                                        impFunc1: () => {
                                                            {
                                                                d = (this_array[i] & (1 << p) - 1) << k - p;
                                                                d |= this_array[--i] >> (p += BI_DB - k);
                                                            }
                                                        },
                                                        impFunc2: () => {
                                                            {
                                                                d = this_array[i] >> (p -= k) & km;
                                                                (() => {
                                                                    const __ifInstance15 = new WebAssembly.Instance(__ifWasmModule, {
                                                                        env: {
                                                                            impFunc1: () => {
                                                                                {
                                                                                    p += BI_DB;
                                                                                    --i;
                                                                                }
                                                                            },
                                                                            impFunc2: () => {
                                                                            }
                                                                        }
                                                                    });
                                                                    const __exports = __ifInstance15.exports;
                                                                    return __exports.data(p <= 0 ? 1 : 0);
                                                                })();
                                                            }
                                                        }
                                                    }
                                                });
                                                const __exports = __ifInstance14.exports;
                                                return __exports.data(p < k ? 1 : 0);
                                            })();
                                            (() => {
                                                const __ifInstance16 = new WebAssembly.Instance(__ifWasmModule, {
                                                    env: {
                                                        impFunc1: () => {
                                                            m = true;
                                                        },
                                                        impFunc2: () => {
                                                        }
                                                    }
                                                });
                                                const __exports = __ifInstance16.exports;
                                                return __exports.data(d > 0 ? 1 : 0);
                                            })();
                                            (() => {
                                                const __ifInstance17 = new WebAssembly.Instance(__ifWasmModule, {
                                                    env: {
                                                        impFunc1: () => {
                                                            r += int2char(d);
                                                        },
                                                        impFunc2: () => {
                                                        }
                                                    }
                                                });
                                                const __exports = __ifInstance17.exports;
                                                return __exports.data(m ? 1 : 0);
                                            })();
                                        }
                                    }
                                }
                            });
                            const __exports = __forInstance31.exports;
                            return __exports.data();
                        })();
                    }
                },
                impFunc2: () => {
                }
            }
        });
        const __exports = __ifInstance12.exports;
        return __exports.data(i-- > 0 ? 1 : 0);
    })();
    return m ? r : lS(0, 12);
}
function bnNegate() {
    var r = nbi();
    (() => {
        const __callInstance152 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    BigInteger.ZERO.subTo(this, r);
                }
            }
        });
        const __exports = __callInstance152.exports;
        return __exports.data();
    })();
    return r;
}
function bnAbs() {
    return this.s < 0 ? this.negate() : this;
}
function bnCompareTo(a) {
    var this_array = this.array;
    var a_array = a.array;
    var r = this.s - a.s;
    if (r != 0)
        return r;
    var i = this.t;
    r = i - a.t;
    if (r != 0)
        return r;
    while (--i >= 0)
        if ((r = this_array[i] - a_array[i]) != 0)
            return r;
    return 0;
}
function nbits(x) {
    var r = 1, t;
    (() => {
        const __ifInstance18 = new WebAssembly.Instance(__ifWasmModule, {
            env: {
                impFunc1: () => {
                    {
                        x = t;
                        r += 16;
                    }
                },
                impFunc2: () => {
                }
            }
        });
        const __exports = __ifInstance18.exports;
        return __exports.data((t = x >>> 16) != 0 ? 1 : 0);
    })();
    (() => {
        const __ifInstance19 = new WebAssembly.Instance(__ifWasmModule, {
            env: {
                impFunc1: () => {
                    {
                        x = t;
                        r += 8;
                    }
                },
                impFunc2: () => {
                }
            }
        });
        const __exports = __ifInstance19.exports;
        return __exports.data((t = x >> 8) != 0 ? 1 : 0);
    })();
    (() => {
        const __ifInstance20 = new WebAssembly.Instance(__ifWasmModule, {
            env: {
                impFunc1: () => {
                    {
                        x = t;
                        r += 4;
                    }
                },
                impFunc2: () => {
                }
            }
        });
        const __exports = __ifInstance20.exports;
        return __exports.data((t = x >> 4) != 0 ? 1 : 0);
    })();
    (() => {
        const __ifInstance21 = new WebAssembly.Instance(__ifWasmModule, {
            env: {
                impFunc1: () => {
                    {
                        x = t;
                        r += 2;
                    }
                },
                impFunc2: () => {
                }
            }
        });
        const __exports = __ifInstance21.exports;
        return __exports.data((t = x >> 2) != 0 ? 1 : 0);
    })();
    (() => {
        const __ifInstance22 = new WebAssembly.Instance(__ifWasmModule, {
            env: {
                impFunc1: () => {
                    {
                        x = t;
                        r += 1;
                    }
                },
                impFunc2: () => {
                }
            }
        });
        const __exports = __ifInstance22.exports;
        return __exports.data((t = x >> 1) != 0 ? 1 : 0);
    })();
    return r;
}
function bnBitLength() {
    var this_array = this.array;
    if (this.t <= 0)
        return 0;
    return BI_DB * (this.t - 1) + nbits(this_array[this.t - 1] ^ this.s & BI_DM);
}
function bnpDLShiftTo(n, r) {
    var this_array = this.array;
    var r_array = r.array;
    var i;
    (() => {
        i = this.t - 1;
        const __forInstance4 = new WebAssembly.Instance(__forWasmModule, {
            env: {
                test: () => {
                    return i >= 0 ? 1 : 0;
                },
                update: () => {
                    --i;
                },
                body: () => {
                    r_array[i + n] = this_array[i];
                }
            }
        });
        const __exports = __forInstance4.exports;
        return __exports.data();
    })();
    (() => {
        i = n - 1;
        const __forInstance5 = new WebAssembly.Instance(__forWasmModule, {
            env: {
                test: () => {
                    return i >= 0 ? 1 : 0;
                },
                update: () => {
                    --i;
                },
                body: () => {
                    r_array[i] = 0;
                }
            }
        });
        const __exports = __forInstance5.exports;
        return __exports.data();
    })();
    r.t = this.t + n;
    r.s = this.s;
}
function bnpDRShiftTo(n, r) {
    var this_array = this.array;
    var r_array = r.array;
    (() => {
        var i = n;
        const __forInstance6 = new WebAssembly.Instance(__forWasmModule, {
            env: {
                test: () => {
                    return i < this.t ? 1 : 0;
                },
                update: () => {
                    ++i;
                },
                body: () => {
                    r_array[i - n] = this_array[i];
                }
            }
        });
        const __exports = __forInstance6.exports;
        return __exports.data();
    })();
    r.t = Math.max(this.t - n, 0);
    r.s = this.s;
}
function bnpLShiftTo(n, r) {
    var this_array = this.array;
    var r_array = r.array;
    var bs = n % BI_DB;
    var cbs = BI_DB - bs;
    var bm = (1 << cbs) - 1;
    var ds = Math.floor(n / BI_DB), c = this.s << bs & BI_DM, i;
    (() => {
        i = this.t - 1;
        const __forInstance7 = new WebAssembly.Instance(__forWasmModule, {
            env: {
                test: () => {
                    return i >= 0 ? 1 : 0;
                },
                update: () => {
                    --i;
                },
                body: () => {
                    {
                        r_array[i + ds + 1] = this_array[i] >> cbs | c;
                        c = (this_array[i] & bm) << bs;
                    }
                }
            }
        });
        const __exports = __forInstance7.exports;
        return __exports.data();
    })();
    (() => {
        i = ds - 1;
        const __forInstance8 = new WebAssembly.Instance(__forWasmModule, {
            env: {
                test: () => {
                    return i >= 0 ? 1 : 0;
                },
                update: () => {
                    --i;
                },
                body: () => {
                    r_array[i] = 0;
                }
            }
        });
        const __exports = __forInstance8.exports;
        return __exports.data();
    })();
    r_array[ds] = c;
    r.t = this.t + ds + 1;
    r.s = this.s;
    (() => {
        const __callInstance151 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    r.clamp();
                }
            }
        });
        const __exports = __callInstance151.exports;
        return __exports.data();
    })();
}
function bnpRShiftTo(n, r) {
    var this_array = this.array;
    var r_array = r.array;
    r.s = this.s;
    var ds = Math.floor(n / BI_DB);
    if (ds >= this.t) {
        r.t = 0;
        return;
    }
    var bs = n % BI_DB;
    var cbs = BI_DB - bs;
    var bm = (1 << bs) - 1;
    r_array[0] = this_array[ds] >> bs;
    (() => {
        var i = ds + 1;
        const __forInstance9 = new WebAssembly.Instance(__forWasmModule, {
            env: {
                test: () => {
                    return i < this.t ? 1 : 0;
                },
                update: () => {
                    ++i;
                },
                body: () => {
                    {
                        r_array[i - ds - 1] |= (this_array[i] & bm) << cbs;
                        r_array[i - ds] = this_array[i] >> bs;
                    }
                }
            }
        });
        const __exports = __forInstance9.exports;
        return __exports.data();
    })();
    (() => {
        const __ifInstance23 = new WebAssembly.Instance(__ifWasmModule, {
            env: {
                impFunc1: () => {
                    r_array[this.t - ds - 1] |= (this.s & bm) << cbs;
                },
                impFunc2: () => {
                }
            }
        });
        const __exports = __ifInstance23.exports;
        return __exports.data(bs > 0 ? 1 : 0);
    })();
    r.t = this.t - ds;
    (() => {
        const __callInstance150 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    r.clamp();
                }
            }
        });
        const __exports = __callInstance150.exports;
        return __exports.data();
    })();
}
function bnpSubTo(a, r) {
    var this_array = this.array;
    var r_array = r.array;
    var a_array = a.array;
    var i = 0, c = 0, m = Math.min(a.t, this.t);
    (() => {
        const __forInstance32 = new WebAssembly.Instance(__forWasmModule, {
            env: {
                test: () => {
                    return i < m ? 1 : 0;
                },
                update: () => {
                },
                body: () => {
                    {
                        c += this_array[i] - a_array[i];
                        r_array[i++] = c & BI_DM;
                        c >>= BI_DB;
                    }
                }
            }
        });
        const __exports = __forInstance32.exports;
        return __exports.data();
    })();
    (() => {
        const __ifInstance24 = new WebAssembly.Instance(__ifWasmModule, {
            env: {
                impFunc1: () => {
                    {
                        c -= a.s;
                        (() => {
                            const __forInstance33 = new WebAssembly.Instance(__forWasmModule, {
                                env: {
                                    test: () => {
                                        return i < this.t ? 1 : 0;
                                    },
                                    update: () => {
                                    },
                                    body: () => {
                                        {
                                            c += this_array[i];
                                            r_array[i++] = c & BI_DM;
                                            c >>= BI_DB;
                                        }
                                    }
                                }
                            });
                            const __exports = __forInstance33.exports;
                            return __exports.data();
                        })();
                        c += this.s;
                    }
                },
                impFunc2: () => {
                    {
                        c += this.s;
                        (() => {
                            const __forInstance34 = new WebAssembly.Instance(__forWasmModule, {
                                env: {
                                    test: () => {
                                        return i < a.t ? 1 : 0;
                                    },
                                    update: () => {
                                    },
                                    body: () => {
                                        {
                                            c -= a_array[i];
                                            r_array[i++] = c & BI_DM;
                                            c >>= BI_DB;
                                        }
                                    }
                                }
                            });
                            const __exports = __forInstance34.exports;
                            return __exports.data();
                        })();
                        c -= a.s;
                    }
                }
            }
        });
        const __exports = __ifInstance24.exports;
        return __exports.data(a.t < this.t ? 1 : 0);
    })();
    r.s = c < 0 ? -1 : 0;
    (() => {
        const __ifInstance25 = new WebAssembly.Instance(__ifWasmModule, {
            env: {
                impFunc1: () => {
                    r_array[i++] = BI_DV + c;
                },
                impFunc2: () => {
                    (() => {
                        const __ifInstance26 = new WebAssembly.Instance(__ifWasmModule, {
                            env: {
                                impFunc1: () => {
                                    r_array[i++] = c;
                                },
                                impFunc2: () => {
                                }
                            }
                        });
                        const __exports = __ifInstance26.exports;
                        return __exports.data(c > 0 ? 1 : 0);
                    })();
                }
            }
        });
        const __exports = __ifInstance25.exports;
        return __exports.data(c < -1 ? 1 : 0);
    })();
    r.t = i;
    (() => {
        const __callInstance149 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    r.clamp();
                }
            }
        });
        const __exports = __callInstance149.exports;
        return __exports.data();
    })();
}
function bnpMultiplyTo(a, r) {
    var this_array = this.array;
    var r_array = r.array;
    var x = this.abs(), y = a.abs();
    var y_array = y.array;
    var i = x.t;
    r.t = i + y.t;
    (() => {
        const __forInstance35 = new WebAssembly.Instance(__forWasmModule, {
            env: {
                test: () => {
                    return --i >= 0 ? 1 : 0;
                },
                update: () => {
                },
                body: () => {
                    r_array[i] = 0;
                }
            }
        });
        const __exports = __forInstance35.exports;
        return __exports.data();
    })();
    (() => {
        i = 0;
        const __forInstance10 = new WebAssembly.Instance(__forWasmModule, {
            env: {
                test: () => {
                    return i < y.t ? 1 : 0;
                },
                update: () => {
                    ++i;
                },
                body: () => {
                    r_array[i + x.t] = x.am(0, y_array[i], r, i, 0, x.t);
                }
            }
        });
        const __exports = __forInstance10.exports;
        return __exports.data();
    })();
    r.s = 0;
    (() => {
        const __callInstance148 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    r.clamp();
                }
            }
        });
        const __exports = __callInstance148.exports;
        return __exports.data();
    })();
    (() => {
        const __ifInstance27 = new WebAssembly.Instance(__ifWasmModule, {
            env: {
                impFunc1: () => {
                    (() => {
                        const __callInstance147 = new WebAssembly.Instance(__callWasmModule, {
                            env: {
                                impFunc: () => {
                                    BigInteger.ZERO.subTo(r, r);
                                }
                            }
                        });
                        const __exports = __callInstance147.exports;
                        return __exports.data();
                    })();
                },
                impFunc2: () => {
                }
            }
        });
        const __exports = __ifInstance27.exports;
        return __exports.data(this.s != a.s ? 1 : 0);
    })();
}
function bnpSquareTo(r) {
    var x = this.abs();
    var x_array = x.array;
    var r_array = r.array;
    var i = r.t = 2 * x.t;
    (() => {
        const __forInstance36 = new WebAssembly.Instance(__forWasmModule, {
            env: {
                test: () => {
                    return --i >= 0 ? 1 : 0;
                },
                update: () => {
                },
                body: () => {
                    r_array[i] = 0;
                }
            }
        });
        const __exports = __forInstance36.exports;
        return __exports.data();
    })();
    (() => {
        i = 0;
        const __forInstance11 = new WebAssembly.Instance(__forWasmModule, {
            env: {
                test: () => {
                    return i < x.t - 1 ? 1 : 0;
                },
                update: () => {
                    ++i;
                },
                body: () => {
                    {
                        var c = x.am(i, x_array[i], r, 2 * i, 0, 1);
                        (() => {
                            const __ifInstance28 = new WebAssembly.Instance(__ifWasmModule, {
                                env: {
                                    impFunc1: () => {
                                        {
                                            r_array[i + x.t] -= BI_DV;
                                            r_array[i + x.t + 1] = 1;
                                        }
                                    },
                                    impFunc2: () => {
                                    }
                                }
                            });
                            const __exports = __ifInstance28.exports;
                            return __exports.data((r_array[i + x.t] += x.am(i + 1, 2 * x_array[i], r, 2 * i + 1, c, x.t - i - 1)) >= BI_DV ? 1 : 0);
                        })();
                    }
                }
            }
        });
        const __exports = __forInstance11.exports;
        return __exports.data();
    })();
    (() => {
        const __ifInstance29 = new WebAssembly.Instance(__ifWasmModule, {
            env: {
                impFunc1: () => {
                    r_array[r.t - 1] += x.am(i, x_array[i], r, 2 * i, 0, 1);
                },
                impFunc2: () => {
                }
            }
        });
        const __exports = __ifInstance29.exports;
        return __exports.data(r.t > 0 ? 1 : 0);
    })();
    r.s = 0;
    (() => {
        const __callInstance146 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    r.clamp();
                }
            }
        });
        const __exports = __callInstance146.exports;
        return __exports.data();
    })();
}
function bnpDivRemTo(m, q, r) {
    var pm = m.abs();
    if (pm.t <= 0)
        return;
    var pt = this.abs();
    if (pt.t < pm.t) {
        (() => {
            const __ifInstance30 = new WebAssembly.Instance(__ifWasmModule, {
                env: {
                    impFunc1: () => {
                        (() => {
                            const __callInstance145 = new WebAssembly.Instance(__callWasmModule, {
                                env: {
                                    impFunc: () => {
                                        q.fromInt(0);
                                    }
                                }
                            });
                            const __exports = __callInstance145.exports;
                            return __exports.data();
                        })();
                    },
                    impFunc2: () => {
                    }
                }
            });
            const __exports = __ifInstance30.exports;
            return __exports.data(q != null ? 1 : 0);
        })();
        (() => {
            const __ifInstance31 = new WebAssembly.Instance(__ifWasmModule, {
                env: {
                    impFunc1: () => {
                        (() => {
                            const __callInstance144 = new WebAssembly.Instance(__callWasmModule, {
                                env: {
                                    impFunc: () => {
                                        this.copyTo(r);
                                    }
                                }
                            });
                            const __exports = __callInstance144.exports;
                            return __exports.data();
                        })();
                    },
                    impFunc2: () => {
                    }
                }
            });
            const __exports = __ifInstance31.exports;
            return __exports.data(r != null ? 1 : 0);
        })();
        return;
    }
    (() => {
        const __ifInstance32 = new WebAssembly.Instance(__ifWasmModule, {
            env: {
                impFunc1: () => {
                    r = nbi();
                },
                impFunc2: () => {
                }
            }
        });
        const __exports = __ifInstance32.exports;
        return __exports.data(r == null ? 1 : 0);
    })();
    var y = nbi(), ts = this.s, ms = m.s;
    var pm_array = pm.array;
    var nsh = BI_DB - nbits(pm_array[pm.t - 1]);
    (() => {
        const __ifInstance33 = new WebAssembly.Instance(__ifWasmModule, {
            env: {
                impFunc1: () => {
                    {
                        (() => {
                            const __callInstance143 = new WebAssembly.Instance(__callWasmModule, {
                                env: {
                                    impFunc: () => {
                                        pm.lShiftTo(nsh, y);
                                    }
                                }
                            });
                            const __exports = __callInstance143.exports;
                            return __exports.data();
                        })();
                        (() => {
                            const __callInstance142 = new WebAssembly.Instance(__callWasmModule, {
                                env: {
                                    impFunc: () => {
                                        pt.lShiftTo(nsh, r);
                                    }
                                }
                            });
                            const __exports = __callInstance142.exports;
                            return __exports.data();
                        })();
                    }
                },
                impFunc2: () => {
                    {
                        (() => {
                            const __callInstance141 = new WebAssembly.Instance(__callWasmModule, {
                                env: {
                                    impFunc: () => {
                                        pm.copyTo(y);
                                    }
                                }
                            });
                            const __exports = __callInstance141.exports;
                            return __exports.data();
                        })();
                        (() => {
                            const __callInstance140 = new WebAssembly.Instance(__callWasmModule, {
                                env: {
                                    impFunc: () => {
                                        pt.copyTo(r);
                                    }
                                }
                            });
                            const __exports = __callInstance140.exports;
                            return __exports.data();
                        })();
                    }
                }
            }
        });
        const __exports = __ifInstance33.exports;
        return __exports.data(nsh > 0 ? 1 : 0);
    })();
    var ys = y.t;
    var y_array = y.array;
    var y0 = y_array[ys - 1];
    if (y0 == 0)
        return;
    var yt = y0 * (1 << BI_F1) + (ys > 1 ? y_array[ys - 2] >> BI_F2 : 0);
    var d1 = BI_FV / yt, d2 = (1 << BI_F1) / yt, e = 1 << BI_F2;
    var i = r.t, j = i - ys, t = q == null ? nbi() : q;
    (() => {
        const __callInstance139 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    y.dlShiftTo(j, t);
                }
            }
        });
        const __exports = __callInstance139.exports;
        return __exports.data();
    })();
    var r_array = r.array;
    (() => {
        const __ifInstance34 = new WebAssembly.Instance(__ifWasmModule, {
            env: {
                impFunc1: () => {
                    {
                        r_array[r.t++] = 1;
                        (() => {
                            const __callInstance138 = new WebAssembly.Instance(__callWasmModule, {
                                env: {
                                    impFunc: () => {
                                        r.subTo(t, r);
                                    }
                                }
                            });
                            const __exports = __callInstance138.exports;
                            return __exports.data();
                        })();
                    }
                },
                impFunc2: () => {
                }
            }
        });
        const __exports = __ifInstance34.exports;
        return __exports.data(r.compareTo(t) >= 0 ? 1 : 0);
    })();
    (() => {
        const __callInstance137 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    BigInteger.ONE.dlShiftTo(ys, t);
                }
            }
        });
        const __exports = __callInstance137.exports;
        return __exports.data();
    })();
    (() => {
        const __callInstance136 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    t.subTo(y, y);
                }
            }
        });
        const __exports = __callInstance136.exports;
        return __exports.data();
    })();
    (() => {
        const __forInstance37 = new WebAssembly.Instance(__forWasmModule, {
            env: {
                test: () => {
                    return y.t < ys ? 1 : 0;
                },
                update: () => {
                },
                body: () => {
                    y_array[y.t++] = 0;
                }
            }
        });
        const __exports = __forInstance37.exports;
        return __exports.data();
    })();
    (() => {
        const __forInstance38 = new WebAssembly.Instance(__forWasmModule, {
            env: {
                test: () => {
                    return --j >= 0 ? 1 : 0;
                },
                update: () => {
                },
                body: () => {
                    {
                        var qd = r_array[--i] == y0 ? BI_DM : Math.floor(r_array[i] * d1 + (r_array[i - 1] + e) * d2);
                        (() => {
                            const __ifInstance35 = new WebAssembly.Instance(__ifWasmModule, {
                                env: {
                                    impFunc1: () => {
                                        {
                                            (() => {
                                                const __callInstance135 = new WebAssembly.Instance(__callWasmModule, {
                                                    env: {
                                                        impFunc: () => {
                                                            y.dlShiftTo(j, t);
                                                        }
                                                    }
                                                });
                                                const __exports = __callInstance135.exports;
                                                return __exports.data();
                                            })();
                                            (() => {
                                                const __callInstance134 = new WebAssembly.Instance(__callWasmModule, {
                                                    env: {
                                                        impFunc: () => {
                                                            r.subTo(t, r);
                                                        }
                                                    }
                                                });
                                                const __exports = __callInstance134.exports;
                                                return __exports.data();
                                            })();
                                            (() => {
                                                const __forInstance39 = new WebAssembly.Instance(__forWasmModule, {
                                                    env: {
                                                        test: () => {
                                                            return r_array[i] < --qd ? 1 : 0;
                                                        },
                                                        update: () => {
                                                        },
                                                        body: () => {
                                                            (() => {
                                                                const __callInstance133 = new WebAssembly.Instance(__callWasmModule, {
                                                                    env: {
                                                                        impFunc: () => {
                                                                            r.subTo(t, r);
                                                                        }
                                                                    }
                                                                });
                                                                const __exports = __callInstance133.exports;
                                                                return __exports.data();
                                                            })();
                                                        }
                                                    }
                                                });
                                                const __exports = __forInstance39.exports;
                                                return __exports.data();
                                            })();
                                        }
                                    },
                                    impFunc2: () => {
                                    }
                                }
                            });
                            const __exports = __ifInstance35.exports;
                            return __exports.data((r_array[i] += y.am(0, qd, r, j, 0, ys)) < qd ? 1 : 0);
                        })();
                    }
                }
            }
        });
        const __exports = __forInstance38.exports;
        return __exports.data();
    })();
    (() => {
        const __ifInstance36 = new WebAssembly.Instance(__ifWasmModule, {
            env: {
                impFunc1: () => {
                    {
                        (() => {
                            const __callInstance132 = new WebAssembly.Instance(__callWasmModule, {
                                env: {
                                    impFunc: () => {
                                        r.drShiftTo(ys, q);
                                    }
                                }
                            });
                            const __exports = __callInstance132.exports;
                            return __exports.data();
                        })();
                        (() => {
                            const __ifInstance37 = new WebAssembly.Instance(__ifWasmModule, {
                                env: {
                                    impFunc1: () => {
                                        (() => {
                                            const __callInstance131 = new WebAssembly.Instance(__callWasmModule, {
                                                env: {
                                                    impFunc: () => {
                                                        BigInteger.ZERO.subTo(q, q);
                                                    }
                                                }
                                            });
                                            const __exports = __callInstance131.exports;
                                            return __exports.data();
                                        })();
                                    },
                                    impFunc2: () => {
                                    }
                                }
                            });
                            const __exports = __ifInstance37.exports;
                            return __exports.data(ts != ms ? 1 : 0);
                        })();
                    }
                },
                impFunc2: () => {
                }
            }
        });
        const __exports = __ifInstance36.exports;
        return __exports.data(q != null ? 1 : 0);
    })();
    r.t = ys;
    (() => {
        const __callInstance130 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    r.clamp();
                }
            }
        });
        const __exports = __callInstance130.exports;
        return __exports.data();
    })();
    (() => {
        const __ifInstance38 = new WebAssembly.Instance(__ifWasmModule, {
            env: {
                impFunc1: () => {
                    (() => {
                        const __callInstance129 = new WebAssembly.Instance(__callWasmModule, {
                            env: {
                                impFunc: () => {
                                    r.rShiftTo(nsh, r);
                                }
                            }
                        });
                        const __exports = __callInstance129.exports;
                        return __exports.data();
                    })();
                },
                impFunc2: () => {
                }
            }
        });
        const __exports = __ifInstance38.exports;
        return __exports.data(nsh > 0 ? 1 : 0);
    })();
    (() => {
        const __ifInstance39 = new WebAssembly.Instance(__ifWasmModule, {
            env: {
                impFunc1: () => {
                    (() => {
                        const __callInstance128 = new WebAssembly.Instance(__callWasmModule, {
                            env: {
                                impFunc: () => {
                                    BigInteger.ZERO.subTo(r, r);
                                }
                            }
                        });
                        const __exports = __callInstance128.exports;
                        return __exports.data();
                    })();
                },
                impFunc2: () => {
                }
            }
        });
        const __exports = __ifInstance39.exports;
        return __exports.data(ts < 0 ? 1 : 0);
    })();
}
function bnMod(a) {
    var r = nbi();
    (() => {
        const __callInstance127 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    this.abs().divRemTo(a, null, r);
                }
            }
        });
        const __exports = __callInstance127.exports;
        return __exports.data();
    })();
    (() => {
        const __ifInstance40 = new WebAssembly.Instance(__ifWasmModule, {
            env: {
                impFunc1: () => {
                    (() => {
                        const __callInstance126 = new WebAssembly.Instance(__callWasmModule, {
                            env: {
                                impFunc: () => {
                                    a.subTo(r, r);
                                }
                            }
                        });
                        const __exports = __callInstance126.exports;
                        return __exports.data();
                    })();
                },
                impFunc2: () => {
                }
            }
        });
        const __exports = __ifInstance40.exports;
        return __exports.data(this.s < 0 && r.compareTo(BigInteger.ZERO) > 0 ? 1 : 0);
    })();
    return r;
}
function Classic(m) {
    this.m = m;
}
function cConvert(x) {
    if (x.s < 0 || x.compareTo(this.m) >= 0)
        return x.mod(this.m);
    else
        return x;
}
function cRevert(x) {
    return x;
}
function cReduce(x) {
    (() => {
        const __callInstance125 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    x.divRemTo(this.m, null, x);
                }
            }
        });
        const __exports = __callInstance125.exports;
        return __exports.data();
    })();
}
function cMulTo(x, y, r) {
    (() => {
        const __callInstance124 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    x.multiplyTo(y, r);
                }
            }
        });
        const __exports = __callInstance124.exports;
        return __exports.data();
    })();
    (() => {
        const __callInstance123 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    this.reduce(r);
                }
            }
        });
        const __exports = __callInstance123.exports;
        return __exports.data();
    })();
}
function cSqrTo(x, r) {
    (() => {
        const __callInstance122 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    x.squareTo(r);
                }
            }
        });
        const __exports = __callInstance122.exports;
        return __exports.data();
    })();
    (() => {
        const __callInstance121 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    this.reduce(r);
                }
            }
        });
        const __exports = __callInstance121.exports;
        return __exports.data();
    })();
}
Classic.prototype.convert = cConvert;
Classic.prototype.revert = cRevert;
Classic.prototype.reduce = cReduce;
Classic.prototype.mulTo = cMulTo;
Classic.prototype.sqrTo = cSqrTo;
function bnpInvDigit() {
    var this_array = this.array;
    if (this.t < 1)
        return 0;
    var x = this_array[0];
    if ((x & 1) == 0)
        return 0;
    var y = x & 3;
    y = y * (2 - (x & 15) * y) & 15;
    y = y * (2 - (x & 255) * y) & 255;
    y = y * (2 - ((x & 65535) * y & 65535)) & 65535;
    y = y * (2 - x * y % BI_DV) % BI_DV;
    return y > 0 ? BI_DV - y : -y;
}
function Montgomery(m) {
    this.m = m;
    this.mp = m.invDigit();
    this.mpl = this.mp & 32767;
    this.mph = this.mp >> 15;
    this.um = (1 << BI_DB - 15) - 1;
    this.mt2 = 2 * m.t;
}
function montConvert(x) {
    var r = nbi();
    (() => {
        const __callInstance120 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    x.abs().dlShiftTo(this.m.t, r);
                }
            }
        });
        const __exports = __callInstance120.exports;
        return __exports.data();
    })();
    (() => {
        const __callInstance119 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    r.divRemTo(this.m, null, r);
                }
            }
        });
        const __exports = __callInstance119.exports;
        return __exports.data();
    })();
    (() => {
        const __ifInstance41 = new WebAssembly.Instance(__ifWasmModule, {
            env: {
                impFunc1: () => {
                    (() => {
                        const __callInstance118 = new WebAssembly.Instance(__callWasmModule, {
                            env: {
                                impFunc: () => {
                                    this.m.subTo(r, r);
                                }
                            }
                        });
                        const __exports = __callInstance118.exports;
                        return __exports.data();
                    })();
                },
                impFunc2: () => {
                }
            }
        });
        const __exports = __ifInstance41.exports;
        return __exports.data(x.s < 0 && r.compareTo(BigInteger.ZERO) > 0 ? 1 : 0);
    })();
    return r;
}
function montRevert(x) {
    var r = nbi();
    (() => {
        const __callInstance117 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    x.copyTo(r);
                }
            }
        });
        const __exports = __callInstance117.exports;
        return __exports.data();
    })();
    (() => {
        const __callInstance116 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    this.reduce(r);
                }
            }
        });
        const __exports = __callInstance116.exports;
        return __exports.data();
    })();
    return r;
}
function montReduce(x) {
    var x_array = x.array;
    (() => {
        const __forInstance40 = new WebAssembly.Instance(__forWasmModule, {
            env: {
                test: () => {
                    return x.t <= this.mt2 ? 1 : 0;
                },
                update: () => {
                },
                body: () => {
                    x_array[x.t++] = 0;
                }
            }
        });
        const __exports = __forInstance40.exports;
        return __exports.data();
    })();
    (() => {
        var i = 0;
        const __forInstance12 = new WebAssembly.Instance(__forWasmModule, {
            env: {
                test: () => {
                    return i < this.m.t ? 1 : 0;
                },
                update: () => {
                    ++i;
                },
                body: () => {
                    {
                        var j = x_array[i] & 32767;
                        var u0 = j * this.mpl + ((j * this.mph + (x_array[i] >> 15) * this.mpl & this.um) << 15) & BI_DM;
                        j = i + this.m.t;
                        x_array[j] += this.m.am(0, u0, x, i, 0, this.m.t);
                        (() => {
                            const __forInstance41 = new WebAssembly.Instance(__forWasmModule, {
                                env: {
                                    test: () => {
                                        return x_array[j] >= BI_DV ? 1 : 0;
                                    },
                                    update: () => {
                                    },
                                    body: () => {
                                        {
                                            x_array[j] -= BI_DV;
                                            x_array[++j]++;
                                        }
                                    }
                                }
                            });
                            const __exports = __forInstance41.exports;
                            return __exports.data();
                        })();
                    }
                }
            }
        });
        const __exports = __forInstance12.exports;
        return __exports.data();
    })();
    (() => {
        const __callInstance115 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    x.clamp();
                }
            }
        });
        const __exports = __callInstance115.exports;
        return __exports.data();
    })();
    (() => {
        const __callInstance114 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    x.drShiftTo(this.m.t, x);
                }
            }
        });
        const __exports = __callInstance114.exports;
        return __exports.data();
    })();
    (() => {
        const __ifInstance42 = new WebAssembly.Instance(__ifWasmModule, {
            env: {
                impFunc1: () => {
                    (() => {
                        const __callInstance113 = new WebAssembly.Instance(__callWasmModule, {
                            env: {
                                impFunc: () => {
                                    x.subTo(this.m, x);
                                }
                            }
                        });
                        const __exports = __callInstance113.exports;
                        return __exports.data();
                    })();
                },
                impFunc2: () => {
                }
            }
        });
        const __exports = __ifInstance42.exports;
        return __exports.data(x.compareTo(this.m) >= 0 ? 1 : 0);
    })();
}
function montSqrTo(x, r) {
    (() => {
        const __callInstance112 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    x.squareTo(r);
                }
            }
        });
        const __exports = __callInstance112.exports;
        return __exports.data();
    })();
    (() => {
        const __callInstance111 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    this.reduce(r);
                }
            }
        });
        const __exports = __callInstance111.exports;
        return __exports.data();
    })();
}
function montMulTo(x, y, r) {
    (() => {
        const __callInstance110 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    x.multiplyTo(y, r);
                }
            }
        });
        const __exports = __callInstance110.exports;
        return __exports.data();
    })();
    (() => {
        const __callInstance109 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    this.reduce(r);
                }
            }
        });
        const __exports = __callInstance109.exports;
        return __exports.data();
    })();
}
Montgomery.prototype.convert = montConvert;
Montgomery.prototype.revert = montRevert;
Montgomery.prototype.reduce = montReduce;
Montgomery.prototype.mulTo = montMulTo;
Montgomery.prototype.sqrTo = montSqrTo;
function bnpIsEven() {
    var this_array = this.array;
    return (this.t > 0 ? this_array[0] & 1 : this.s) == 0;
}
function bnpExp(e, z) {
    if (e > 4294967295 || e < 1)
        return BigInteger.ONE;
    var r = nbi(), r2 = nbi(), g = z.convert(this), i = nbits(e) - 1;
    (() => {
        const __callInstance108 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    g.copyTo(r);
                }
            }
        });
        const __exports = __callInstance108.exports;
        return __exports.data();
    })();
    (() => {
        const __forInstance42 = new WebAssembly.Instance(__forWasmModule, {
            env: {
                test: () => {
                    return --i >= 0 ? 1 : 0;
                },
                update: () => {
                },
                body: () => {
                    {
                        (() => {
                            const __callInstance107 = new WebAssembly.Instance(__callWasmModule, {
                                env: {
                                    impFunc: () => {
                                        z.sqrTo(r, r2);
                                    }
                                }
                            });
                            const __exports = __callInstance107.exports;
                            return __exports.data();
                        })();
                        (() => {
                            const __ifInstance43 = new WebAssembly.Instance(__ifWasmModule, {
                                env: {
                                    impFunc1: () => {
                                        (() => {
                                            const __callInstance106 = new WebAssembly.Instance(__callWasmModule, {
                                                env: {
                                                    impFunc: () => {
                                                        z.mulTo(r2, g, r);
                                                    }
                                                }
                                            });
                                            const __exports = __callInstance106.exports;
                                            return __exports.data();
                                        })();
                                    },
                                    impFunc2: () => {
                                        {
                                            var t = r;
                                            r = r2;
                                            r2 = t;
                                        }
                                    }
                                }
                            });
                            const __exports = __ifInstance43.exports;
                            return __exports.data((e & 1 << i) > 0 ? 1 : 0);
                        })();
                    }
                }
            }
        });
        const __exports = __forInstance42.exports;
        return __exports.data();
    })();
    return z.revert(r);
}
function bnModPowInt(e, m) {
    var z;
    (() => {
        const __ifInstance44 = new WebAssembly.Instance(__ifWasmModule, {
            env: {
                impFunc1: () => {
                    z = new Classic(m);
                },
                impFunc2: () => {
                    z = new Montgomery(m);
                }
            }
        });
        const __exports = __ifInstance44.exports;
        return __exports.data(e < 256 || m.isEven() ? 1 : 0);
    })();
    return this.exp(e, z);
}
BigInteger.prototype.copyTo = bnpCopyTo;
BigInteger.prototype.fromInt = bnpFromInt;
BigInteger.prototype.fromString = bnpFromString;
BigInteger.prototype.clamp = bnpClamp;
BigInteger.prototype.dlShiftTo = bnpDLShiftTo;
BigInteger.prototype.drShiftTo = bnpDRShiftTo;
BigInteger.prototype.lShiftTo = bnpLShiftTo;
BigInteger.prototype.rShiftTo = bnpRShiftTo;
BigInteger.prototype.subTo = bnpSubTo;
BigInteger.prototype.multiplyTo = bnpMultiplyTo;
BigInteger.prototype.squareTo = bnpSquareTo;
BigInteger.prototype.divRemTo = bnpDivRemTo;
BigInteger.prototype.invDigit = bnpInvDigit;
BigInteger.prototype.isEven = bnpIsEven;
BigInteger.prototype.exp = bnpExp;
BigInteger.prototype.toString = bnToString;
BigInteger.prototype.negate = bnNegate;
BigInteger.prototype.abs = bnAbs;
BigInteger.prototype.compareTo = bnCompareTo;
BigInteger.prototype.bitLength = bnBitLength;
BigInteger.prototype.mod = bnMod;
BigInteger.prototype.modPowInt = bnModPowInt;
BigInteger.ZERO = nbv(0);
BigInteger.ONE = nbv(1);
function bnClone() {
    var r = nbi();
    (() => {
        const __callInstance105 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    this.copyTo(r);
                }
            }
        });
        const __exports = __callInstance105.exports;
        return __exports.data();
    })();
    return r;
}
function bnIntValue() {
    var this_array = this.array;
    if (this.s < 0) {
        if (this.t == 1)
            return this_array[0] - BI_DV;
        else if (this.t == 0)
            return -1;
    } else if (this.t == 1)
        return this_array[0];
    else if (this.t == 0)
        return 0;
    return (this_array[1] & (1 << 32 - BI_DB) - 1) << BI_DB | this_array[0];
}
function bnByteValue() {
    var this_array = this.array;
    return this.t == 0 ? this.s : this_array[0] << 24 >> 24;
}
function bnShortValue() {
    var this_array = this.array;
    return this.t == 0 ? this.s : this_array[0] << 16 >> 16;
}
function bnpChunkSize(r) {
    return Math.floor(Math.LN2 * BI_DB / Math.log(r));
}
function bnSigNum() {
    var this_array = this.array;
    if (this.s < 0)
        return -1;
    else if (this.t <= 0 || this.t == 1 && this_array[0] <= 0)
        return 0;
    else
        return 1;
}
function bnpToRadix(b) {
    (() => {
        const __ifInstance45 = new WebAssembly.Instance(__ifWasmModule, {
            env: {
                impFunc1: () => {
                    b = 10;
                },
                impFunc2: () => {
                }
            }
        });
        const __exports = __ifInstance45.exports;
        return __exports.data(b == null ? 1 : 0);
    })();
    if (this.signum() == 0 || b < 2 || b > 36)
        return lS(0, 13);
    var cs = this.chunkSize(b);
    var a = Math.pow(b, cs);
    var d = nbv(a), y = nbi(), z = nbi(), r = lS(0, 14);
    (() => {
        const __callInstance104 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    this.divRemTo(d, y, z);
                }
            }
        });
        const __exports = __callInstance104.exports;
        return __exports.data();
    })();
    (() => {
        const __forInstance43 = new WebAssembly.Instance(__forWasmModule, {
            env: {
                test: () => {
                    return y.signum() > 0 ? 1 : 0;
                },
                update: () => {
                },
                body: () => {
                    {
                        r = (a + z.intValue()).toString(b).substr(1) + r;
                        (() => {
                            const __callInstance103 = new WebAssembly.Instance(__callWasmModule, {
                                env: {
                                    impFunc: () => {
                                        y.divRemTo(d, y, z);
                                    }
                                }
                            });
                            const __exports = __callInstance103.exports;
                            return __exports.data();
                        })();
                    }
                }
            }
        });
        const __exports = __forInstance43.exports;
        return __exports.data();
    })();
    return z.intValue().toString(b) + r;
}
function bnpFromRadix(s, b) {
    (() => {
        const __callInstance102 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    this.fromInt(0);
                }
            }
        });
        const __exports = __callInstance102.exports;
        return __exports.data();
    })();
    (() => {
        const __ifInstance46 = new WebAssembly.Instance(__ifWasmModule, {
            env: {
                impFunc1: () => {
                    b = 10;
                },
                impFunc2: () => {
                }
            }
        });
        const __exports = __ifInstance46.exports;
        return __exports.data(b == null ? 1 : 0);
    })();
    var cs = this.chunkSize(b);
    var d = Math.pow(b, cs), mi = false, j = 0, w = 0;
    for (var i = 0; i < s.length; ++i) {
        var x = intAt(s, i);
        if (x < 0) {
            (() => {
                const __ifInstance47 = new WebAssembly.Instance(__ifWasmModule, {
                    env: {
                        impFunc1: () => {
                            mi = true;
                        },
                        impFunc2: () => {
                        }
                    }
                });
                const __exports = __ifInstance47.exports;
                return __exports.data(s.charAt(i) == lS(0, 15) && this.signum() == 0 ? 1 : 0);
            })();
            continue;
        }
        w = b * w + x;
        (() => {
            const __ifInstance48 = new WebAssembly.Instance(__ifWasmModule, {
                env: {
                    impFunc1: () => {
                        {
                            (() => {
                                const __callInstance101 = new WebAssembly.Instance(__callWasmModule, {
                                    env: {
                                        impFunc: () => {
                                            this.dMultiply(d);
                                        }
                                    }
                                });
                                const __exports = __callInstance101.exports;
                                return __exports.data();
                            })();
                            (() => {
                                const __callInstance100 = new WebAssembly.Instance(__callWasmModule, {
                                    env: {
                                        impFunc: () => {
                                            this.dAddOffset(w, 0);
                                        }
                                    }
                                });
                                const __exports = __callInstance100.exports;
                                return __exports.data();
                            })();
                            j = 0;
                            w = 0;
                        }
                    },
                    impFunc2: () => {
                    }
                }
            });
            const __exports = __ifInstance48.exports;
            return __exports.data(++j >= cs ? 1 : 0);
        })();
    }
    (() => {
        const __ifInstance49 = new WebAssembly.Instance(__ifWasmModule, {
            env: {
                impFunc1: () => {
                    {
                        (() => {
                            const __callInstance99 = new WebAssembly.Instance(__callWasmModule, {
                                env: {
                                    impFunc: () => {
                                        this.dMultiply(Math.pow(b, j));
                                    }
                                }
                            });
                            const __exports = __callInstance99.exports;
                            return __exports.data();
                        })();
                        (() => {
                            const __callInstance98 = new WebAssembly.Instance(__callWasmModule, {
                                env: {
                                    impFunc: () => {
                                        this.dAddOffset(w, 0);
                                    }
                                }
                            });
                            const __exports = __callInstance98.exports;
                            return __exports.data();
                        })();
                    }
                },
                impFunc2: () => {
                }
            }
        });
        const __exports = __ifInstance49.exports;
        return __exports.data(j > 0 ? 1 : 0);
    })();
    (() => {
        const __ifInstance50 = new WebAssembly.Instance(__ifWasmModule, {
            env: {
                impFunc1: () => {
                    (() => {
                        const __callInstance97 = new WebAssembly.Instance(__callWasmModule, {
                            env: {
                                impFunc: () => {
                                    BigInteger.ZERO.subTo(this, this);
                                }
                            }
                        });
                        const __exports = __callInstance97.exports;
                        return __exports.data();
                    })();
                },
                impFunc2: () => {
                }
            }
        });
        const __exports = __ifInstance50.exports;
        return __exports.data(mi ? 1 : 0);
    })();
}
function bnpFromNumber(a, b, c) {
    (() => {
        const __ifInstance51 = new WebAssembly.Instance(__ifWasmModule, {
            env: {
                impFunc1: () => {
                    {
                        (() => {
                            const __ifInstance52 = new WebAssembly.Instance(__ifWasmModule, {
                                env: {
                                    impFunc1: () => {
                                        (() => {
                                            const __callInstance96 = new WebAssembly.Instance(__callWasmModule, {
                                                env: {
                                                    impFunc: () => {
                                                        this.fromInt(1);
                                                    }
                                                }
                                            });
                                            const __exports = __callInstance96.exports;
                                            return __exports.data();
                                        })();
                                    },
                                    impFunc2: () => {
                                        {
                                            (() => {
                                                const __callInstance95 = new WebAssembly.Instance(__callWasmModule, {
                                                    env: {
                                                        impFunc: () => {
                                                            this.fromNumber(a, c);
                                                        }
                                                    }
                                                });
                                                const __exports = __callInstance95.exports;
                                                return __exports.data();
                                            })();
                                            (() => {
                                                const __ifInstance53 = new WebAssembly.Instance(__ifWasmModule, {
                                                    env: {
                                                        impFunc1: () => {
                                                            (() => {
                                                                const __callInstance94 = new WebAssembly.Instance(__callWasmModule, {
                                                                    env: {
                                                                        impFunc: () => {
                                                                            this.bitwiseTo(BigInteger.ONE.shiftLeft(a - 1), op_or, this);
                                                                        }
                                                                    }
                                                                });
                                                                const __exports = __callInstance94.exports;
                                                                return __exports.data();
                                                            })();
                                                        },
                                                        impFunc2: () => {
                                                        }
                                                    }
                                                });
                                                const __exports = __ifInstance53.exports;
                                                return __exports.data(!this.testBit(a - 1) ? 1 : 0);
                                            })();
                                            (() => {
                                                const __ifInstance54 = new WebAssembly.Instance(__ifWasmModule, {
                                                    env: {
                                                        impFunc1: () => {
                                                            (() => {
                                                                const __callInstance93 = new WebAssembly.Instance(__callWasmModule, {
                                                                    env: {
                                                                        impFunc: () => {
                                                                            this.dAddOffset(1, 0);
                                                                        }
                                                                    }
                                                                });
                                                                const __exports = __callInstance93.exports;
                                                                return __exports.data();
                                                            })();
                                                        },
                                                        impFunc2: () => {
                                                        }
                                                    }
                                                });
                                                const __exports = __ifInstance54.exports;
                                                return __exports.data(this.isEven() ? 1 : 0);
                                            })();
                                            (() => {
                                                const __forInstance44 = new WebAssembly.Instance(__forWasmModule, {
                                                    env: {
                                                        test: () => {
                                                            return !this.isProbablePrime(b) ? 1 : 0;
                                                        },
                                                        update: () => {
                                                        },
                                                        body: () => {
                                                            {
                                                                (() => {
                                                                    const __callInstance92 = new WebAssembly.Instance(__callWasmModule, {
                                                                        env: {
                                                                            impFunc: () => {
                                                                                this.dAddOffset(2, 0);
                                                                            }
                                                                        }
                                                                    });
                                                                    const __exports = __callInstance92.exports;
                                                                    return __exports.data();
                                                                })();
                                                                (() => {
                                                                    const __ifInstance55 = new WebAssembly.Instance(__ifWasmModule, {
                                                                        env: {
                                                                            impFunc1: () => {
                                                                                (() => {
                                                                                    const __callInstance91 = new WebAssembly.Instance(__callWasmModule, {
                                                                                        env: {
                                                                                            impFunc: () => {
                                                                                                this.subTo(BigInteger.ONE.shiftLeft(a - 1), this);
                                                                                            }
                                                                                        }
                                                                                    });
                                                                                    const __exports = __callInstance91.exports;
                                                                                    return __exports.data();
                                                                                })();
                                                                            },
                                                                            impFunc2: () => {
                                                                            }
                                                                        }
                                                                    });
                                                                    const __exports = __ifInstance55.exports;
                                                                    return __exports.data(this.bitLength() > a ? 1 : 0);
                                                                })();
                                                            }
                                                        }
                                                    }
                                                });
                                                const __exports = __forInstance44.exports;
                                                return __exports.data();
                                            })();
                                        }
                                    }
                                }
                            });
                            const __exports = __ifInstance52.exports;
                            return __exports.data(a < 2 ? 1 : 0);
                        })();
                    }
                },
                impFunc2: () => {
                    {
                        var x = __lA(1, 20, 24), t = a & 7;
                        x.length = (a >> 3) + 1;
                        (() => {
                            const __callInstance90 = new WebAssembly.Instance(__callWasmModule, {
                                env: {
                                    impFunc: () => {
                                        b.nextBytes(x);
                                    }
                                }
                            });
                            const __exports = __callInstance90.exports;
                            return __exports.data();
                        })();
                        (() => {
                            const __ifInstance56 = new WebAssembly.Instance(__ifWasmModule, {
                                env: {
                                    impFunc1: () => {
                                        x[0] &= (1 << t) - 1;
                                    },
                                    impFunc2: () => {
                                    }
                                }
                            });
                            const __exports = __ifInstance56.exports;
                            return __exports.data(t > 0 ? 1 : 0);
                        })();
                        (() => {
                            const __callInstance89 = new WebAssembly.Instance(__callWasmModule, {
                                env: {
                                    impFunc: () => {
                                        this.fromString(x, 256);
                                    }
                                }
                            });
                            const __exports = __callInstance89.exports;
                            return __exports.data();
                        })();
                    }
                }
            }
        });
        const __exports = __ifInstance51.exports;
        return __exports.data(lS(0, 16) == typeof b ? 1 : 0);
    })();
}
function bnToByteArray() {
    var this_array = this.array;
    var i = this.t, r = new Array();
    r[0] = this.s;
    var p = BI_DB - i * BI_DB % 8, d, k = 0;
    (() => {
        const __ifInstance57 = new WebAssembly.Instance(__ifWasmModule, {
            env: {
                impFunc1: () => {
                    {
                        (() => {
                            const __ifInstance58 = new WebAssembly.Instance(__ifWasmModule, {
                                env: {
                                    impFunc1: () => {
                                        r[k++] = d | this.s << BI_DB - p;
                                    },
                                    impFunc2: () => {
                                    }
                                }
                            });
                            const __exports = __ifInstance58.exports;
                            return __exports.data(p < BI_DB && (d = this_array[i] >> p) != (this.s & BI_DM) >> p ? 1 : 0);
                        })();
                        (() => {
                            const __forInstance45 = new WebAssembly.Instance(__forWasmModule, {
                                env: {
                                    test: () => {
                                        return i >= 0 ? 1 : 0;
                                    },
                                    update: () => {
                                    },
                                    body: () => {
                                        {
                                            (() => {
                                                const __ifInstance59 = new WebAssembly.Instance(__ifWasmModule, {
                                                    env: {
                                                        impFunc1: () => {
                                                            {
                                                                d = (this_array[i] & (1 << p) - 1) << 8 - p;
                                                                d |= this_array[--i] >> (p += BI_DB - 8);
                                                            }
                                                        },
                                                        impFunc2: () => {
                                                            {
                                                                d = this_array[i] >> (p -= 8) & 255;
                                                                (() => {
                                                                    const __ifInstance60 = new WebAssembly.Instance(__ifWasmModule, {
                                                                        env: {
                                                                            impFunc1: () => {
                                                                                {
                                                                                    p += BI_DB;
                                                                                    --i;
                                                                                }
                                                                            },
                                                                            impFunc2: () => {
                                                                            }
                                                                        }
                                                                    });
                                                                    const __exports = __ifInstance60.exports;
                                                                    return __exports.data(p <= 0 ? 1 : 0);
                                                                })();
                                                            }
                                                        }
                                                    }
                                                });
                                                const __exports = __ifInstance59.exports;
                                                return __exports.data(p < 8 ? 1 : 0);
                                            })();
                                            (() => {
                                                const __ifInstance61 = new WebAssembly.Instance(__ifWasmModule, {
                                                    env: {
                                                        impFunc1: () => {
                                                            d |= -256;
                                                        },
                                                        impFunc2: () => {
                                                        }
                                                    }
                                                });
                                                const __exports = __ifInstance61.exports;
                                                return __exports.data((d & 128) != 0 ? 1 : 0);
                                            })();
                                            (() => {
                                                const __ifInstance62 = new WebAssembly.Instance(__ifWasmModule, {
                                                    env: {
                                                        impFunc1: () => {
                                                            ++k;
                                                        },
                                                        impFunc2: () => {
                                                        }
                                                    }
                                                });
                                                const __exports = __ifInstance62.exports;
                                                return __exports.data(k == 0 && (this.s & 128) != (d & 128) ? 1 : 0);
                                            })();
                                            (() => {
                                                const __ifInstance63 = new WebAssembly.Instance(__ifWasmModule, {
                                                    env: {
                                                        impFunc1: () => {
                                                            r[k++] = d;
                                                        },
                                                        impFunc2: () => {
                                                        }
                                                    }
                                                });
                                                const __exports = __ifInstance63.exports;
                                                return __exports.data(k > 0 || d != this.s ? 1 : 0);
                                            })();
                                        }
                                    }
                                }
                            });
                            const __exports = __forInstance45.exports;
                            return __exports.data();
                        })();
                    }
                },
                impFunc2: () => {
                }
            }
        });
        const __exports = __ifInstance57.exports;
        return __exports.data(i-- > 0 ? 1 : 0);
    })();
    return r;
}
function bnEquals(a) {
    return this.compareTo(a) == 0;
}
function bnMin(a) {
    return this.compareTo(a) < 0 ? this : a;
}
function bnMax(a) {
    return this.compareTo(a) > 0 ? this : a;
}
function bnpBitwiseTo(a, op, r) {
    var this_array = this.array;
    var a_array = a.array;
    var r_array = r.array;
    var i, f, m = Math.min(a.t, this.t);
    (() => {
        i = 0;
        const __forInstance13 = new WebAssembly.Instance(__forWasmModule, {
            env: {
                test: () => {
                    return i < m ? 1 : 0;
                },
                update: () => {
                    ++i;
                },
                body: () => {
                    r_array[i] = op(this_array[i], a_array[i]);
                }
            }
        });
        const __exports = __forInstance13.exports;
        return __exports.data();
    })();
    (() => {
        const __ifInstance64 = new WebAssembly.Instance(__ifWasmModule, {
            env: {
                impFunc1: () => {
                    {
                        f = a.s & BI_DM;
                        (() => {
                            i = m;
                            const __forInstance14 = new WebAssembly.Instance(__forWasmModule, {
                                env: {
                                    test: () => {
                                        return i < this.t ? 1 : 0;
                                    },
                                    update: () => {
                                        ++i;
                                    },
                                    body: () => {
                                        r_array[i] = op(this_array[i], f);
                                    }
                                }
                            });
                            const __exports = __forInstance14.exports;
                            return __exports.data();
                        })();
                        r.t = this.t;
                    }
                },
                impFunc2: () => {
                    {
                        f = this.s & BI_DM;
                        (() => {
                            i = m;
                            const __forInstance15 = new WebAssembly.Instance(__forWasmModule, {
                                env: {
                                    test: () => {
                                        return i < a.t ? 1 : 0;
                                    },
                                    update: () => {
                                        ++i;
                                    },
                                    body: () => {
                                        r_array[i] = op(f, a_array[i]);
                                    }
                                }
                            });
                            const __exports = __forInstance15.exports;
                            return __exports.data();
                        })();
                        r.t = a.t;
                    }
                }
            }
        });
        const __exports = __ifInstance64.exports;
        return __exports.data(a.t < this.t ? 1 : 0);
    })();
    r.s = op(this.s, a.s);
    (() => {
        const __callInstance88 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    r.clamp();
                }
            }
        });
        const __exports = __callInstance88.exports;
        return __exports.data();
    })();
}
function op_and(x, y) {
    return x & y;
}
function bnAnd(a) {
    var r = nbi();
    (() => {
        const __callInstance87 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    this.bitwiseTo(a, op_and, r);
                }
            }
        });
        const __exports = __callInstance87.exports;
        return __exports.data();
    })();
    return r;
}
function op_or(x, y) {
    return x | y;
}
function bnOr(a) {
    var r = nbi();
    (() => {
        const __callInstance86 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    this.bitwiseTo(a, op_or, r);
                }
            }
        });
        const __exports = __callInstance86.exports;
        return __exports.data();
    })();
    return r;
}
function op_xor(x, y) {
    return x ^ y;
}
function bnXor(a) {
    var r = nbi();
    (() => {
        const __callInstance85 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    this.bitwiseTo(a, op_xor, r);
                }
            }
        });
        const __exports = __callInstance85.exports;
        return __exports.data();
    })();
    return r;
}
function op_andnot(x, y) {
    return x & ~y;
}
function bnAndNot(a) {
    var r = nbi();
    (() => {
        const __callInstance84 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    this.bitwiseTo(a, op_andnot, r);
                }
            }
        });
        const __exports = __callInstance84.exports;
        return __exports.data();
    })();
    return r;
}
function bnNot() {
    var this_array = this.array;
    var r = nbi();
    var r_array = r.array;
    (() => {
        var i = 0;
        const __forInstance16 = new WebAssembly.Instance(__forWasmModule, {
            env: {
                test: () => {
                    return i < this.t ? 1 : 0;
                },
                update: () => {
                    ++i;
                },
                body: () => {
                    r_array[i] = BI_DM & ~this_array[i];
                }
            }
        });
        const __exports = __forInstance16.exports;
        return __exports.data();
    })();
    r.t = this.t;
    r.s = ~this.s;
    return r;
}
function bnShiftLeft(n) {
    var r = nbi();
    (() => {
        const __ifInstance65 = new WebAssembly.Instance(__ifWasmModule, {
            env: {
                impFunc1: () => {
                    (() => {
                        const __callInstance83 = new WebAssembly.Instance(__callWasmModule, {
                            env: {
                                impFunc: () => {
                                    this.rShiftTo(-n, r);
                                }
                            }
                        });
                        const __exports = __callInstance83.exports;
                        return __exports.data();
                    })();
                },
                impFunc2: () => {
                    (() => {
                        const __callInstance82 = new WebAssembly.Instance(__callWasmModule, {
                            env: {
                                impFunc: () => {
                                    this.lShiftTo(n, r);
                                }
                            }
                        });
                        const __exports = __callInstance82.exports;
                        return __exports.data();
                    })();
                }
            }
        });
        const __exports = __ifInstance65.exports;
        return __exports.data(n < 0 ? 1 : 0);
    })();
    return r;
}
function bnShiftRight(n) {
    var r = nbi();
    (() => {
        const __ifInstance66 = new WebAssembly.Instance(__ifWasmModule, {
            env: {
                impFunc1: () => {
                    (() => {
                        const __callInstance81 = new WebAssembly.Instance(__callWasmModule, {
                            env: {
                                impFunc: () => {
                                    this.lShiftTo(-n, r);
                                }
                            }
                        });
                        const __exports = __callInstance81.exports;
                        return __exports.data();
                    })();
                },
                impFunc2: () => {
                    (() => {
                        const __callInstance80 = new WebAssembly.Instance(__callWasmModule, {
                            env: {
                                impFunc: () => {
                                    this.rShiftTo(n, r);
                                }
                            }
                        });
                        const __exports = __callInstance80.exports;
                        return __exports.data();
                    })();
                }
            }
        });
        const __exports = __ifInstance66.exports;
        return __exports.data(n < 0 ? 1 : 0);
    })();
    return r;
}
function lbit(x) {
    if (x == 0)
        return -1;
    var r = 0;
    (() => {
        const __ifInstance67 = new WebAssembly.Instance(__ifWasmModule, {
            env: {
                impFunc1: () => {
                    {
                        x >>= 16;
                        r += 16;
                    }
                },
                impFunc2: () => {
                }
            }
        });
        const __exports = __ifInstance67.exports;
        return __exports.data((x & 65535) == 0 ? 1 : 0);
    })();
    (() => {
        const __ifInstance68 = new WebAssembly.Instance(__ifWasmModule, {
            env: {
                impFunc1: () => {
                    {
                        x >>= 8;
                        r += 8;
                    }
                },
                impFunc2: () => {
                }
            }
        });
        const __exports = __ifInstance68.exports;
        return __exports.data((x & 255) == 0 ? 1 : 0);
    })();
    (() => {
        const __ifInstance69 = new WebAssembly.Instance(__ifWasmModule, {
            env: {
                impFunc1: () => {
                    {
                        x >>= 4;
                        r += 4;
                    }
                },
                impFunc2: () => {
                }
            }
        });
        const __exports = __ifInstance69.exports;
        return __exports.data((x & 15) == 0 ? 1 : 0);
    })();
    (() => {
        const __ifInstance70 = new WebAssembly.Instance(__ifWasmModule, {
            env: {
                impFunc1: () => {
                    {
                        x >>= 2;
                        r += 2;
                    }
                },
                impFunc2: () => {
                }
            }
        });
        const __exports = __ifInstance70.exports;
        return __exports.data((x & 3) == 0 ? 1 : 0);
    })();
    (() => {
        const __ifInstance71 = new WebAssembly.Instance(__ifWasmModule, {
            env: {
                impFunc1: () => {
                    ++r;
                },
                impFunc2: () => {
                }
            }
        });
        const __exports = __ifInstance71.exports;
        return __exports.data((x & 1) == 0 ? 1 : 0);
    })();
    return r;
}
function bnGetLowestSetBit() {
    var this_array = this.array;
    for (var i = 0; i < this.t; ++i)
        if (this_array[i] != 0)
            return i * BI_DB + lbit(this_array[i]);
    if (this.s < 0)
        return this.t * BI_DB;
    return -1;
}
function cbit(x) {
    var r = 0;
    (() => {
        const __forInstance46 = new WebAssembly.Instance(__forWasmModule, {
            env: {
                test: () => {
                    return x != 0 ? 1 : 0;
                },
                update: () => {
                },
                body: () => {
                    {
                        x &= x - 1;
                        ++r;
                    }
                }
            }
        });
        const __exports = __forInstance46.exports;
        return __exports.data();
    })();
    return r;
}
function bnBitCount() {
    var r = 0, x = this.s & BI_DM;
    (() => {
        var i = 0;
        const __forInstance17 = new WebAssembly.Instance(__forWasmModule, {
            env: {
                test: () => {
                    return i < this.t ? 1 : 0;
                },
                update: () => {
                    ++i;
                },
                body: () => {
                    r += cbit(this_array[i] ^ x);
                }
            }
        });
        const __exports = __forInstance17.exports;
        return __exports.data();
    })();
    return r;
}
function bnTestBit(n) {
    var this_array = this.array;
    var j = Math.floor(n / BI_DB);
    if (j >= this.t)
        return this.s != 0;
    return (this_array[j] & 1 << n % BI_DB) != 0;
}
function bnpChangeBit(n, op) {
    var r = BigInteger.ONE.shiftLeft(n);
    (() => {
        const __callInstance79 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    this.bitwiseTo(r, op, r);
                }
            }
        });
        const __exports = __callInstance79.exports;
        return __exports.data();
    })();
    return r;
}
function bnSetBit(n) {
    return this.changeBit(n, op_or);
}
function bnClearBit(n) {
    return this.changeBit(n, op_andnot);
}
function bnFlipBit(n) {
    return this.changeBit(n, op_xor);
}
function bnpAddTo(a, r) {
    var this_array = this.array;
    var a_array = a.array;
    var r_array = r.array;
    var i = 0, c = 0, m = Math.min(a.t, this.t);
    (() => {
        const __forInstance47 = new WebAssembly.Instance(__forWasmModule, {
            env: {
                test: () => {
                    return i < m ? 1 : 0;
                },
                update: () => {
                },
                body: () => {
                    {
                        c += this_array[i] + a_array[i];
                        r_array[i++] = c & BI_DM;
                        c >>= BI_DB;
                    }
                }
            }
        });
        const __exports = __forInstance47.exports;
        return __exports.data();
    })();
    (() => {
        const __ifInstance72 = new WebAssembly.Instance(__ifWasmModule, {
            env: {
                impFunc1: () => {
                    {
                        c += a.s;
                        (() => {
                            const __forInstance48 = new WebAssembly.Instance(__forWasmModule, {
                                env: {
                                    test: () => {
                                        return i < this.t ? 1 : 0;
                                    },
                                    update: () => {
                                    },
                                    body: () => {
                                        {
                                            c += this_array[i];
                                            r_array[i++] = c & BI_DM;
                                            c >>= BI_DB;
                                        }
                                    }
                                }
                            });
                            const __exports = __forInstance48.exports;
                            return __exports.data();
                        })();
                        c += this.s;
                    }
                },
                impFunc2: () => {
                    {
                        c += this.s;
                        (() => {
                            const __forInstance49 = new WebAssembly.Instance(__forWasmModule, {
                                env: {
                                    test: () => {
                                        return i < a.t ? 1 : 0;
                                    },
                                    update: () => {
                                    },
                                    body: () => {
                                        {
                                            c += a_array[i];
                                            r_array[i++] = c & BI_DM;
                                            c >>= BI_DB;
                                        }
                                    }
                                }
                            });
                            const __exports = __forInstance49.exports;
                            return __exports.data();
                        })();
                        c += a.s;
                    }
                }
            }
        });
        const __exports = __ifInstance72.exports;
        return __exports.data(a.t < this.t ? 1 : 0);
    })();
    r.s = c < 0 ? -1 : 0;
    (() => {
        const __ifInstance73 = new WebAssembly.Instance(__ifWasmModule, {
            env: {
                impFunc1: () => {
                    r_array[i++] = c;
                },
                impFunc2: () => {
                    (() => {
                        const __ifInstance74 = new WebAssembly.Instance(__ifWasmModule, {
                            env: {
                                impFunc1: () => {
                                    r_array[i++] = BI_DV + c;
                                },
                                impFunc2: () => {
                                }
                            }
                        });
                        const __exports = __ifInstance74.exports;
                        return __exports.data(c < -1 ? 1 : 0);
                    })();
                }
            }
        });
        const __exports = __ifInstance73.exports;
        return __exports.data(c > 0 ? 1 : 0);
    })();
    r.t = i;
    (() => {
        const __callInstance78 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    r.clamp();
                }
            }
        });
        const __exports = __callInstance78.exports;
        return __exports.data();
    })();
}
function bnAdd(a) {
    var r = nbi();
    (() => {
        const __callInstance77 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    this.addTo(a, r);
                }
            }
        });
        const __exports = __callInstance77.exports;
        return __exports.data();
    })();
    return r;
}
function bnSubtract(a) {
    var r = nbi();
    (() => {
        const __callInstance76 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    this.subTo(a, r);
                }
            }
        });
        const __exports = __callInstance76.exports;
        return __exports.data();
    })();
    return r;
}
function bnMultiply(a) {
    var r = nbi();
    (() => {
        const __callInstance75 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    this.multiplyTo(a, r);
                }
            }
        });
        const __exports = __callInstance75.exports;
        return __exports.data();
    })();
    return r;
}
function bnDivide(a) {
    var r = nbi();
    (() => {
        const __callInstance74 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    this.divRemTo(a, r, null);
                }
            }
        });
        const __exports = __callInstance74.exports;
        return __exports.data();
    })();
    return r;
}
function bnRemainder(a) {
    var r = nbi();
    (() => {
        const __callInstance73 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    this.divRemTo(a, null, r);
                }
            }
        });
        const __exports = __callInstance73.exports;
        return __exports.data();
    })();
    return r;
}
function bnDivideAndRemainder(a) {
    var q = nbi(), r = nbi();
    (() => {
        const __callInstance72 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    this.divRemTo(a, q, r);
                }
            }
        });
        const __exports = __callInstance72.exports;
        return __exports.data();
    })();
    return new Array(q, r);
}
function bnpDMultiply(n) {
    var this_array = this.array;
    this_array[this.t] = this.am(0, n - 1, this, 0, 0, this.t);
    ++this.t;
    (() => {
        const __callInstance71 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    this.clamp();
                }
            }
        });
        const __exports = __callInstance71.exports;
        return __exports.data();
    })();
}
function bnpDAddOffset(n, w) {
    var this_array = this.array;
    (() => {
        const __forInstance50 = new WebAssembly.Instance(__forWasmModule, {
            env: {
                test: () => {
                    return this.t <= w ? 1 : 0;
                },
                update: () => {
                },
                body: () => {
                    this_array[this.t++] = 0;
                }
            }
        });
        const __exports = __forInstance50.exports;
        return __exports.data();
    })();
    this_array[w] += n;
    (() => {
        const __forInstance51 = new WebAssembly.Instance(__forWasmModule, {
            env: {
                test: () => {
                    return this_array[w] >= BI_DV ? 1 : 0;
                },
                update: () => {
                },
                body: () => {
                    {
                        this_array[w] -= BI_DV;
                        (() => {
                            const __ifInstance75 = new WebAssembly.Instance(__ifWasmModule, {
                                env: {
                                    impFunc1: () => {
                                        this_array[this.t++] = 0;
                                    },
                                    impFunc2: () => {
                                    }
                                }
                            });
                            const __exports = __ifInstance75.exports;
                            return __exports.data(++w >= this.t ? 1 : 0);
                        })();
                        ++this_array[w];
                    }
                }
            }
        });
        const __exports = __forInstance51.exports;
        return __exports.data();
    })();
}
function NullExp() {
}
function nNop(x) {
    return x;
}
function nMulTo(x, y, r) {
    (() => {
        const __callInstance70 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    x.multiplyTo(y, r);
                }
            }
        });
        const __exports = __callInstance70.exports;
        return __exports.data();
    })();
}
function nSqrTo(x, r) {
    (() => {
        const __callInstance69 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    x.squareTo(r);
                }
            }
        });
        const __exports = __callInstance69.exports;
        return __exports.data();
    })();
}
NullExp.prototype.convert = nNop;
NullExp.prototype.revert = nNop;
NullExp.prototype.mulTo = nMulTo;
NullExp.prototype.sqrTo = nSqrTo;
function bnPow(e) {
    return this.exp(e, new NullExp());
}
function bnpMultiplyLowerTo(a, n, r) {
    var r_array = r.array;
    var a_array = a.array;
    var i = Math.min(this.t + a.t, n);
    r.s = 0;
    r.t = i;
    (() => {
        const __forInstance52 = new WebAssembly.Instance(__forWasmModule, {
            env: {
                test: () => {
                    return i > 0 ? 1 : 0;
                },
                update: () => {
                },
                body: () => {
                    r_array[--i] = 0;
                }
            }
        });
        const __exports = __forInstance52.exports;
        return __exports.data();
    })();
    var j;
    (() => {
        j = r.t - this.t;
        const __forInstance18 = new WebAssembly.Instance(__forWasmModule, {
            env: {
                test: () => {
                    return i < j ? 1 : 0;
                },
                update: () => {
                    ++i;
                },
                body: () => {
                    r_array[i + this.t] = this.am(0, a_array[i], r, i, 0, this.t);
                }
            }
        });
        const __exports = __forInstance18.exports;
        return __exports.data();
    })();
    (() => {
        j = Math.min(a.t, n);
        const __forInstance19 = new WebAssembly.Instance(__forWasmModule, {
            env: {
                test: () => {
                    return i < j ? 1 : 0;
                },
                update: () => {
                    ++i;
                },
                body: () => {
                    (() => {
                        const __callInstance68 = new WebAssembly.Instance(__callWasmModule, {
                            env: {
                                impFunc: () => {
                                    this.am(0, a_array[i], r, i, 0, n - i);
                                }
                            }
                        });
                        const __exports = __callInstance68.exports;
                        return __exports.data();
                    })();
                }
            }
        });
        const __exports = __forInstance19.exports;
        return __exports.data();
    })();
    (() => {
        const __callInstance67 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    r.clamp();
                }
            }
        });
        const __exports = __callInstance67.exports;
        return __exports.data();
    })();
}
function bnpMultiplyUpperTo(a, n, r) {
    var r_array = r.array;
    var a_array = a.array;
    --n;
    var i = r.t = this.t + a.t - n;
    r.s = 0;
    (() => {
        const __forInstance53 = new WebAssembly.Instance(__forWasmModule, {
            env: {
                test: () => {
                    return --i >= 0 ? 1 : 0;
                },
                update: () => {
                },
                body: () => {
                    r_array[i] = 0;
                }
            }
        });
        const __exports = __forInstance53.exports;
        return __exports.data();
    })();
    (() => {
        i = Math.max(n - this.t, 0);
        const __forInstance20 = new WebAssembly.Instance(__forWasmModule, {
            env: {
                test: () => {
                    return i < a.t ? 1 : 0;
                },
                update: () => {
                    ++i;
                },
                body: () => {
                    r_array[this.t + i - n] = this.am(n - i, a_array[i], r, 0, 0, this.t + i - n);
                }
            }
        });
        const __exports = __forInstance20.exports;
        return __exports.data();
    })();
    (() => {
        const __callInstance66 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    r.clamp();
                }
            }
        });
        const __exports = __callInstance66.exports;
        return __exports.data();
    })();
    (() => {
        const __callInstance65 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    r.drShiftTo(1, r);
                }
            }
        });
        const __exports = __callInstance65.exports;
        return __exports.data();
    })();
}
function Barrett(m) {
    this.r2 = nbi();
    this.q3 = nbi();
    (() => {
        const __callInstance64 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    BigInteger.ONE.dlShiftTo(2 * m.t, this.r2);
                }
            }
        });
        const __exports = __callInstance64.exports;
        return __exports.data();
    })();
    this.mu = this.r2.divide(m);
    this.m = m;
}
function barrettConvert(x) {
    if (x.s < 0 || x.t > 2 * this.m.t)
        return x.mod(this.m);
    else if (x.compareTo(this.m) < 0)
        return x;
    else {
        var r = nbi();
        (() => {
            const __callInstance63 = new WebAssembly.Instance(__callWasmModule, {
                env: {
                    impFunc: () => {
                        x.copyTo(r);
                    }
                }
            });
            const __exports = __callInstance63.exports;
            return __exports.data();
        })();
        (() => {
            const __callInstance62 = new WebAssembly.Instance(__callWasmModule, {
                env: {
                    impFunc: () => {
                        this.reduce(r);
                    }
                }
            });
            const __exports = __callInstance62.exports;
            return __exports.data();
        })();
        return r;
    }
}
function barrettRevert(x) {
    return x;
}
function barrettReduce(x) {
    (() => {
        const __callInstance61 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    x.drShiftTo(this.m.t - 1, this.r2);
                }
            }
        });
        const __exports = __callInstance61.exports;
        return __exports.data();
    })();
    (() => {
        const __ifInstance76 = new WebAssembly.Instance(__ifWasmModule, {
            env: {
                impFunc1: () => {
                    {
                        x.t = this.m.t + 1;
                        (() => {
                            const __callInstance60 = new WebAssembly.Instance(__callWasmModule, {
                                env: {
                                    impFunc: () => {
                                        x.clamp();
                                    }
                                }
                            });
                            const __exports = __callInstance60.exports;
                            return __exports.data();
                        })();
                    }
                },
                impFunc2: () => {
                }
            }
        });
        const __exports = __ifInstance76.exports;
        return __exports.data(x.t > this.m.t + 1 ? 1 : 0);
    })();
    (() => {
        const __callInstance59 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    this.mu.multiplyUpperTo(this.r2, this.m.t + 1, this.q3);
                }
            }
        });
        const __exports = __callInstance59.exports;
        return __exports.data();
    })();
    (() => {
        const __callInstance58 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    this.m.multiplyLowerTo(this.q3, this.m.t + 1, this.r2);
                }
            }
        });
        const __exports = __callInstance58.exports;
        return __exports.data();
    })();
    (() => {
        const __forInstance54 = new WebAssembly.Instance(__forWasmModule, {
            env: {
                test: () => {
                    return x.compareTo(this.r2) < 0 ? 1 : 0;
                },
                update: () => {
                },
                body: () => {
                    (() => {
                        const __callInstance57 = new WebAssembly.Instance(__callWasmModule, {
                            env: {
                                impFunc: () => {
                                    x.dAddOffset(1, this.m.t + 1);
                                }
                            }
                        });
                        const __exports = __callInstance57.exports;
                        return __exports.data();
                    })();
                }
            }
        });
        const __exports = __forInstance54.exports;
        return __exports.data();
    })();
    (() => {
        const __callInstance56 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    x.subTo(this.r2, x);
                }
            }
        });
        const __exports = __callInstance56.exports;
        return __exports.data();
    })();
    (() => {
        const __forInstance55 = new WebAssembly.Instance(__forWasmModule, {
            env: {
                test: () => {
                    return x.compareTo(this.m) >= 0 ? 1 : 0;
                },
                update: () => {
                },
                body: () => {
                    (() => {
                        const __callInstance55 = new WebAssembly.Instance(__callWasmModule, {
                            env: {
                                impFunc: () => {
                                    x.subTo(this.m, x);
                                }
                            }
                        });
                        const __exports = __callInstance55.exports;
                        return __exports.data();
                    })();
                }
            }
        });
        const __exports = __forInstance55.exports;
        return __exports.data();
    })();
}
function barrettSqrTo(x, r) {
    (() => {
        const __callInstance54 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    x.squareTo(r);
                }
            }
        });
        const __exports = __callInstance54.exports;
        return __exports.data();
    })();
    (() => {
        const __callInstance53 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    this.reduce(r);
                }
            }
        });
        const __exports = __callInstance53.exports;
        return __exports.data();
    })();
}
function barrettMulTo(x, y, r) {
    (() => {
        const __callInstance52 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    x.multiplyTo(y, r);
                }
            }
        });
        const __exports = __callInstance52.exports;
        return __exports.data();
    })();
    (() => {
        const __callInstance51 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    this.reduce(r);
                }
            }
        });
        const __exports = __callInstance51.exports;
        return __exports.data();
    })();
}
Barrett.prototype.convert = barrettConvert;
Barrett.prototype.revert = barrettRevert;
Barrett.prototype.reduce = barrettReduce;
Barrett.prototype.mulTo = barrettMulTo;
Barrett.prototype.sqrTo = barrettSqrTo;
function bnModPow(e, m) {
    var e_array = e.array;
    var i = e.bitLength(), k, r = nbv(1), z;
    if (i <= 0)
        return r;
    else
        (() => {
            const __ifInstance77 = new WebAssembly.Instance(__ifWasmModule, {
                env: {
                    impFunc1: () => {
                        k = 1;
                    },
                    impFunc2: () => {
                        (() => {
                            const __ifInstance78 = new WebAssembly.Instance(__ifWasmModule, {
                                env: {
                                    impFunc1: () => {
                                        k = 3;
                                    },
                                    impFunc2: () => {
                                        (() => {
                                            const __ifInstance79 = new WebAssembly.Instance(__ifWasmModule, {
                                                env: {
                                                    impFunc1: () => {
                                                        k = 4;
                                                    },
                                                    impFunc2: () => {
                                                        (() => {
                                                            const __ifInstance80 = new WebAssembly.Instance(__ifWasmModule, {
                                                                env: {
                                                                    impFunc1: () => {
                                                                        k = 5;
                                                                    },
                                                                    impFunc2: () => {
                                                                        k = 6;
                                                                    }
                                                                }
                                                            });
                                                            const __exports = __ifInstance80.exports;
                                                            return __exports.data(i < 768 ? 1 : 0);
                                                        })();
                                                    }
                                                }
                                            });
                                            const __exports = __ifInstance79.exports;
                                            return __exports.data(i < 144 ? 1 : 0);
                                        })();
                                    }
                                }
                            });
                            const __exports = __ifInstance78.exports;
                            return __exports.data(i < 48 ? 1 : 0);
                        })();
                    }
                }
            });
            const __exports = __ifInstance77.exports;
            return __exports.data(i < 18 ? 1 : 0);
        })();
    (() => {
        const __ifInstance81 = new WebAssembly.Instance(__ifWasmModule, {
            env: {
                impFunc1: () => {
                    z = new Classic(m);
                },
                impFunc2: () => {
                    (() => {
                        const __ifInstance82 = new WebAssembly.Instance(__ifWasmModule, {
                            env: {
                                impFunc1: () => {
                                    z = new Barrett(m);
                                },
                                impFunc2: () => {
                                    z = new Montgomery(m);
                                }
                            }
                        });
                        const __exports = __ifInstance82.exports;
                        return __exports.data(m.isEven() ? 1 : 0);
                    })();
                }
            }
        });
        const __exports = __ifInstance81.exports;
        return __exports.data(i < 8 ? 1 : 0);
    })();
    var g = new Array(), n = 3, k1 = k - 1, km = (1 << k) - 1;
    g[1] = z.convert(this);
    (() => {
        const __ifInstance83 = new WebAssembly.Instance(__ifWasmModule, {
            env: {
                impFunc1: () => {
                    {
                        var g2 = nbi();
                        (() => {
                            const __callInstance50 = new WebAssembly.Instance(__callWasmModule, {
                                env: {
                                    impFunc: () => {
                                        z.sqrTo(g[1], g2);
                                    }
                                }
                            });
                            const __exports = __callInstance50.exports;
                            return __exports.data();
                        })();
                        (() => {
                            const __forInstance56 = new WebAssembly.Instance(__forWasmModule, {
                                env: {
                                    test: () => {
                                        return n <= km ? 1 : 0;
                                    },
                                    update: () => {
                                    },
                                    body: () => {
                                        {
                                            g[n] = nbi();
                                            (() => {
                                                const __callInstance49 = new WebAssembly.Instance(__callWasmModule, {
                                                    env: {
                                                        impFunc: () => {
                                                            z.mulTo(g2, g[n - 2], g[n]);
                                                        }
                                                    }
                                                });
                                                const __exports = __callInstance49.exports;
                                                return __exports.data();
                                            })();
                                            n += 2;
                                        }
                                    }
                                }
                            });
                            const __exports = __forInstance56.exports;
                            return __exports.data();
                        })();
                    }
                },
                impFunc2: () => {
                }
            }
        });
        const __exports = __ifInstance83.exports;
        return __exports.data(k > 1 ? 1 : 0);
    })();
    var j = e.t - 1, w, is1 = true, r2 = nbi(), t;
    i = nbits(e_array[j]) - 1;
    (() => {
        const __forInstance57 = new WebAssembly.Instance(__forWasmModule, {
            env: {
                test: () => {
                    return j >= 0 ? 1 : 0;
                },
                update: () => {
                },
                body: () => {
                    {
                        (() => {
                            const __ifInstance84 = new WebAssembly.Instance(__ifWasmModule, {
                                env: {
                                    impFunc1: () => {
                                        w = e_array[j] >> i - k1 & km;
                                    },
                                    impFunc2: () => {
                                        {
                                            w = (e_array[j] & (1 << i + 1) - 1) << k1 - i;
                                            (() => {
                                                const __ifInstance85 = new WebAssembly.Instance(__ifWasmModule, {
                                                    env: {
                                                        impFunc1: () => {
                                                            w |= e_array[j - 1] >> BI_DB + i - k1;
                                                        },
                                                        impFunc2: () => {
                                                        }
                                                    }
                                                });
                                                const __exports = __ifInstance85.exports;
                                                return __exports.data(j > 0 ? 1 : 0);
                                            })();
                                        }
                                    }
                                }
                            });
                            const __exports = __ifInstance84.exports;
                            return __exports.data(i >= k1 ? 1 : 0);
                        })();
                        n = k;
                        (() => {
                            const __forInstance58 = new WebAssembly.Instance(__forWasmModule, {
                                env: {
                                    test: () => {
                                        return (w & 1) == 0 ? 1 : 0;
                                    },
                                    update: () => {
                                    },
                                    body: () => {
                                        {
                                            w >>= 1;
                                            --n;
                                        }
                                    }
                                }
                            });
                            const __exports = __forInstance58.exports;
                            return __exports.data();
                        })();
                        (() => {
                            const __ifInstance86 = new WebAssembly.Instance(__ifWasmModule, {
                                env: {
                                    impFunc1: () => {
                                        {
                                            i += BI_DB;
                                            --j;
                                        }
                                    },
                                    impFunc2: () => {
                                    }
                                }
                            });
                            const __exports = __ifInstance86.exports;
                            return __exports.data((i -= n) < 0 ? 1 : 0);
                        })();
                        (() => {
                            const __ifInstance87 = new WebAssembly.Instance(__ifWasmModule, {
                                env: {
                                    impFunc1: () => {
                                        {
                                            (() => {
                                                const __callInstance48 = new WebAssembly.Instance(__callWasmModule, {
                                                    env: {
                                                        impFunc: () => {
                                                            g[w].copyTo(r);
                                                        }
                                                    }
                                                });
                                                const __exports = __callInstance48.exports;
                                                return __exports.data();
                                            })();
                                            is1 = false;
                                        }
                                    },
                                    impFunc2: () => {
                                        {
                                            (() => {
                                                const __forInstance59 = new WebAssembly.Instance(__forWasmModule, {
                                                    env: {
                                                        test: () => {
                                                            return n > 1 ? 1 : 0;
                                                        },
                                                        update: () => {
                                                        },
                                                        body: () => {
                                                            {
                                                                (() => {
                                                                    const __callInstance47 = new WebAssembly.Instance(__callWasmModule, {
                                                                        env: {
                                                                            impFunc: () => {
                                                                                z.sqrTo(r, r2);
                                                                            }
                                                                        }
                                                                    });
                                                                    const __exports = __callInstance47.exports;
                                                                    return __exports.data();
                                                                })();
                                                                (() => {
                                                                    const __callInstance46 = new WebAssembly.Instance(__callWasmModule, {
                                                                        env: {
                                                                            impFunc: () => {
                                                                                z.sqrTo(r2, r);
                                                                            }
                                                                        }
                                                                    });
                                                                    const __exports = __callInstance46.exports;
                                                                    return __exports.data();
                                                                })();
                                                                n -= 2;
                                                            }
                                                        }
                                                    }
                                                });
                                                const __exports = __forInstance59.exports;
                                                return __exports.data();
                                            })();
                                            (() => {
                                                const __ifInstance88 = new WebAssembly.Instance(__ifWasmModule, {
                                                    env: {
                                                        impFunc1: () => {
                                                            (() => {
                                                                const __callInstance45 = new WebAssembly.Instance(__callWasmModule, {
                                                                    env: {
                                                                        impFunc: () => {
                                                                            z.sqrTo(r, r2);
                                                                        }
                                                                    }
                                                                });
                                                                const __exports = __callInstance45.exports;
                                                                return __exports.data();
                                                            })();
                                                        },
                                                        impFunc2: () => {
                                                            {
                                                                t = r;
                                                                r = r2;
                                                                r2 = t;
                                                            }
                                                        }
                                                    }
                                                });
                                                const __exports = __ifInstance88.exports;
                                                return __exports.data(n > 0 ? 1 : 0);
                                            })();
                                            (() => {
                                                const __callInstance44 = new WebAssembly.Instance(__callWasmModule, {
                                                    env: {
                                                        impFunc: () => {
                                                            z.mulTo(r2, g[w], r);
                                                        }
                                                    }
                                                });
                                                const __exports = __callInstance44.exports;
                                                return __exports.data();
                                            })();
                                        }
                                    }
                                }
                            });
                            const __exports = __ifInstance87.exports;
                            return __exports.data(is1 ? 1 : 0);
                        })();
                        (() => {
                            const __forInstance60 = new WebAssembly.Instance(__forWasmModule, {
                                env: {
                                    test: () => {
                                        return j >= 0 && (e_array[j] & 1 << i) == 0 ? 1 : 0;
                                    },
                                    update: () => {
                                    },
                                    body: () => {
                                        {
                                            (() => {
                                                const __callInstance43 = new WebAssembly.Instance(__callWasmModule, {
                                                    env: {
                                                        impFunc: () => {
                                                            z.sqrTo(r, r2);
                                                        }
                                                    }
                                                });
                                                const __exports = __callInstance43.exports;
                                                return __exports.data();
                                            })();
                                            t = r;
                                            r = r2;
                                            r2 = t;
                                            (() => {
                                                const __ifInstance89 = new WebAssembly.Instance(__ifWasmModule, {
                                                    env: {
                                                        impFunc1: () => {
                                                            {
                                                                i = BI_DB - 1;
                                                                --j;
                                                            }
                                                        },
                                                        impFunc2: () => {
                                                        }
                                                    }
                                                });
                                                const __exports = __ifInstance89.exports;
                                                return __exports.data(--i < 0 ? 1 : 0);
                                            })();
                                        }
                                    }
                                }
                            });
                            const __exports = __forInstance60.exports;
                            return __exports.data();
                        })();
                    }
                }
            }
        });
        const __exports = __forInstance57.exports;
        return __exports.data();
    })();
    return z.revert(r);
}
function bnGCD(a) {
    var x = this.s < 0 ? this.negate() : this.clone();
    var y = a.s < 0 ? a.negate() : a.clone();
    (() => {
        const __ifInstance90 = new WebAssembly.Instance(__ifWasmModule, {
            env: {
                impFunc1: () => {
                    {
                        var t = x;
                        x = y;
                        y = t;
                    }
                },
                impFunc2: () => {
                }
            }
        });
        const __exports = __ifInstance90.exports;
        return __exports.data(x.compareTo(y) < 0 ? 1 : 0);
    })();
    var i = x.getLowestSetBit(), g = y.getLowestSetBit();
    if (g < 0)
        return x;
    (() => {
        const __ifInstance91 = new WebAssembly.Instance(__ifWasmModule, {
            env: {
                impFunc1: () => {
                    g = i;
                },
                impFunc2: () => {
                }
            }
        });
        const __exports = __ifInstance91.exports;
        return __exports.data(i < g ? 1 : 0);
    })();
    (() => {
        const __ifInstance92 = new WebAssembly.Instance(__ifWasmModule, {
            env: {
                impFunc1: () => {
                    {
                        (() => {
                            const __callInstance42 = new WebAssembly.Instance(__callWasmModule, {
                                env: {
                                    impFunc: () => {
                                        x.rShiftTo(g, x);
                                    }
                                }
                            });
                            const __exports = __callInstance42.exports;
                            return __exports.data();
                        })();
                        (() => {
                            const __callInstance41 = new WebAssembly.Instance(__callWasmModule, {
                                env: {
                                    impFunc: () => {
                                        y.rShiftTo(g, y);
                                    }
                                }
                            });
                            const __exports = __callInstance41.exports;
                            return __exports.data();
                        })();
                    }
                },
                impFunc2: () => {
                }
            }
        });
        const __exports = __ifInstance92.exports;
        return __exports.data(g > 0 ? 1 : 0);
    })();
    (() => {
        const __forInstance61 = new WebAssembly.Instance(__forWasmModule, {
            env: {
                test: () => {
                    return x.signum() > 0 ? 1 : 0;
                },
                update: () => {
                },
                body: () => {
                    {
                        (() => {
                            const __ifInstance93 = new WebAssembly.Instance(__ifWasmModule, {
                                env: {
                                    impFunc1: () => {
                                        (() => {
                                            const __callInstance40 = new WebAssembly.Instance(__callWasmModule, {
                                                env: {
                                                    impFunc: () => {
                                                        x.rShiftTo(i, x);
                                                    }
                                                }
                                            });
                                            const __exports = __callInstance40.exports;
                                            return __exports.data();
                                        })();
                                    },
                                    impFunc2: () => {
                                    }
                                }
                            });
                            const __exports = __ifInstance93.exports;
                            return __exports.data((i = x.getLowestSetBit()) > 0 ? 1 : 0);
                        })();
                        (() => {
                            const __ifInstance94 = new WebAssembly.Instance(__ifWasmModule, {
                                env: {
                                    impFunc1: () => {
                                        (() => {
                                            const __callInstance39 = new WebAssembly.Instance(__callWasmModule, {
                                                env: {
                                                    impFunc: () => {
                                                        y.rShiftTo(i, y);
                                                    }
                                                }
                                            });
                                            const __exports = __callInstance39.exports;
                                            return __exports.data();
                                        })();
                                    },
                                    impFunc2: () => {
                                    }
                                }
                            });
                            const __exports = __ifInstance94.exports;
                            return __exports.data((i = y.getLowestSetBit()) > 0 ? 1 : 0);
                        })();
                        (() => {
                            const __ifInstance95 = new WebAssembly.Instance(__ifWasmModule, {
                                env: {
                                    impFunc1: () => {
                                        {
                                            (() => {
                                                const __callInstance38 = new WebAssembly.Instance(__callWasmModule, {
                                                    env: {
                                                        impFunc: () => {
                                                            x.subTo(y, x);
                                                        }
                                                    }
                                                });
                                                const __exports = __callInstance38.exports;
                                                return __exports.data();
                                            })();
                                            (() => {
                                                const __callInstance37 = new WebAssembly.Instance(__callWasmModule, {
                                                    env: {
                                                        impFunc: () => {
                                                            x.rShiftTo(1, x);
                                                        }
                                                    }
                                                });
                                                const __exports = __callInstance37.exports;
                                                return __exports.data();
                                            })();
                                        }
                                    },
                                    impFunc2: () => {
                                        {
                                            (() => {
                                                const __callInstance36 = new WebAssembly.Instance(__callWasmModule, {
                                                    env: {
                                                        impFunc: () => {
                                                            y.subTo(x, y);
                                                        }
                                                    }
                                                });
                                                const __exports = __callInstance36.exports;
                                                return __exports.data();
                                            })();
                                            (() => {
                                                const __callInstance35 = new WebAssembly.Instance(__callWasmModule, {
                                                    env: {
                                                        impFunc: () => {
                                                            y.rShiftTo(1, y);
                                                        }
                                                    }
                                                });
                                                const __exports = __callInstance35.exports;
                                                return __exports.data();
                                            })();
                                        }
                                    }
                                }
                            });
                            const __exports = __ifInstance95.exports;
                            return __exports.data(x.compareTo(y) >= 0 ? 1 : 0);
                        })();
                    }
                }
            }
        });
        const __exports = __forInstance61.exports;
        return __exports.data();
    })();
    (() => {
        const __ifInstance96 = new WebAssembly.Instance(__ifWasmModule, {
            env: {
                impFunc1: () => {
                    (() => {
                        const __callInstance34 = new WebAssembly.Instance(__callWasmModule, {
                            env: {
                                impFunc: () => {
                                    y.lShiftTo(g, y);
                                }
                            }
                        });
                        const __exports = __callInstance34.exports;
                        return __exports.data();
                    })();
                },
                impFunc2: () => {
                }
            }
        });
        const __exports = __ifInstance96.exports;
        return __exports.data(g > 0 ? 1 : 0);
    })();
    return y;
}
function bnpModInt(n) {
    var this_array = this.array;
    if (n <= 0)
        return 0;
    var d = BI_DV % n, r = this.s < 0 ? n - 1 : 0;
    (() => {
        const __ifInstance97 = new WebAssembly.Instance(__ifWasmModule, {
            env: {
                impFunc1: () => {
                    (() => {
                        const __ifInstance98 = new WebAssembly.Instance(__ifWasmModule, {
                            env: {
                                impFunc1: () => {
                                    r = this_array[0] % n;
                                },
                                impFunc2: () => {
                                    for (var i = this.t - 1; i >= 0; --i)
                                        r = (d * r + this_array[i]) % n;
                                }
                            }
                        });
                        const __exports = __ifInstance98.exports;
                        return __exports.data(d == 0 ? 1 : 0);
                    })();
                },
                impFunc2: () => {
                }
            }
        });
        const __exports = __ifInstance97.exports;
        return __exports.data(this.t > 0 ? 1 : 0);
    })();
    return r;
}
function bnModInverse(m) {
    var ac = m.isEven();
    if (this.isEven() && ac || m.signum() == 0)
        return BigInteger.ZERO;
    var u = m.clone(), v = this.clone();
    var a = nbv(1), b = nbv(0), c = nbv(0), d = nbv(1);
    (() => {
        const __forInstance62 = new WebAssembly.Instance(__forWasmModule, {
            env: {
                test: () => {
                    return u.signum() != 0 ? 1 : 0;
                },
                update: () => {
                },
                body: () => {
                    {
                        (() => {
                            const __forInstance63 = new WebAssembly.Instance(__forWasmModule, {
                                env: {
                                    test: () => {
                                        return u.isEven() ? 1 : 0;
                                    },
                                    update: () => {
                                    },
                                    body: () => {
                                        {
                                            (() => {
                                                const __callInstance33 = new WebAssembly.Instance(__callWasmModule, {
                                                    env: {
                                                        impFunc: () => {
                                                            u.rShiftTo(1, u);
                                                        }
                                                    }
                                                });
                                                const __exports = __callInstance33.exports;
                                                return __exports.data();
                                            })();
                                            (() => {
                                                const __ifInstance99 = new WebAssembly.Instance(__ifWasmModule, {
                                                    env: {
                                                        impFunc1: () => {
                                                            {
                                                                (() => {
                                                                    const __ifInstance100 = new WebAssembly.Instance(__ifWasmModule, {
                                                                        env: {
                                                                            impFunc1: () => {
                                                                                {
                                                                                    (() => {
                                                                                        const __callInstance32 = new WebAssembly.Instance(__callWasmModule, {
                                                                                            env: {
                                                                                                impFunc: () => {
                                                                                                    a.addTo(this, a);
                                                                                                }
                                                                                            }
                                                                                        });
                                                                                        const __exports = __callInstance32.exports;
                                                                                        return __exports.data();
                                                                                    })();
                                                                                    (() => {
                                                                                        const __callInstance31 = new WebAssembly.Instance(__callWasmModule, {
                                                                                            env: {
                                                                                                impFunc: () => {
                                                                                                    b.subTo(m, b);
                                                                                                }
                                                                                            }
                                                                                        });
                                                                                        const __exports = __callInstance31.exports;
                                                                                        return __exports.data();
                                                                                    })();
                                                                                }
                                                                            },
                                                                            impFunc2: () => {
                                                                            }
                                                                        }
                                                                    });
                                                                    const __exports = __ifInstance100.exports;
                                                                    return __exports.data(!a.isEven() || !b.isEven() ? 1 : 0);
                                                                })();
                                                                (() => {
                                                                    const __callInstance30 = new WebAssembly.Instance(__callWasmModule, {
                                                                        env: {
                                                                            impFunc: () => {
                                                                                a.rShiftTo(1, a);
                                                                            }
                                                                        }
                                                                    });
                                                                    const __exports = __callInstance30.exports;
                                                                    return __exports.data();
                                                                })();
                                                            }
                                                        },
                                                        impFunc2: () => {
                                                            (() => {
                                                                const __ifInstance101 = new WebAssembly.Instance(__ifWasmModule, {
                                                                    env: {
                                                                        impFunc1: () => {
                                                                            (() => {
                                                                                const __callInstance29 = new WebAssembly.Instance(__callWasmModule, {
                                                                                    env: {
                                                                                        impFunc: () => {
                                                                                            b.subTo(m, b);
                                                                                        }
                                                                                    }
                                                                                });
                                                                                const __exports = __callInstance29.exports;
                                                                                return __exports.data();
                                                                            })();
                                                                        },
                                                                        impFunc2: () => {
                                                                        }
                                                                    }
                                                                });
                                                                const __exports = __ifInstance101.exports;
                                                                return __exports.data(!b.isEven() ? 1 : 0);
                                                            })();
                                                        }
                                                    }
                                                });
                                                const __exports = __ifInstance99.exports;
                                                return __exports.data(ac ? 1 : 0);
                                            })();
                                            (() => {
                                                const __callInstance28 = new WebAssembly.Instance(__callWasmModule, {
                                                    env: {
                                                        impFunc: () => {
                                                            b.rShiftTo(1, b);
                                                        }
                                                    }
                                                });
                                                const __exports = __callInstance28.exports;
                                                return __exports.data();
                                            })();
                                        }
                                    }
                                }
                            });
                            const __exports = __forInstance63.exports;
                            return __exports.data();
                        })();
                        (() => {
                            const __forInstance64 = new WebAssembly.Instance(__forWasmModule, {
                                env: {
                                    test: () => {
                                        return v.isEven() ? 1 : 0;
                                    },
                                    update: () => {
                                    },
                                    body: () => {
                                        {
                                            (() => {
                                                const __callInstance27 = new WebAssembly.Instance(__callWasmModule, {
                                                    env: {
                                                        impFunc: () => {
                                                            v.rShiftTo(1, v);
                                                        }
                                                    }
                                                });
                                                const __exports = __callInstance27.exports;
                                                return __exports.data();
                                            })();
                                            (() => {
                                                const __ifInstance102 = new WebAssembly.Instance(__ifWasmModule, {
                                                    env: {
                                                        impFunc1: () => {
                                                            {
                                                                (() => {
                                                                    const __ifInstance103 = new WebAssembly.Instance(__ifWasmModule, {
                                                                        env: {
                                                                            impFunc1: () => {
                                                                                {
                                                                                    (() => {
                                                                                        const __callInstance26 = new WebAssembly.Instance(__callWasmModule, {
                                                                                            env: {
                                                                                                impFunc: () => {
                                                                                                    c.addTo(this, c);
                                                                                                }
                                                                                            }
                                                                                        });
                                                                                        const __exports = __callInstance26.exports;
                                                                                        return __exports.data();
                                                                                    })();
                                                                                    (() => {
                                                                                        const __callInstance25 = new WebAssembly.Instance(__callWasmModule, {
                                                                                            env: {
                                                                                                impFunc: () => {
                                                                                                    d.subTo(m, d);
                                                                                                }
                                                                                            }
                                                                                        });
                                                                                        const __exports = __callInstance25.exports;
                                                                                        return __exports.data();
                                                                                    })();
                                                                                }
                                                                            },
                                                                            impFunc2: () => {
                                                                            }
                                                                        }
                                                                    });
                                                                    const __exports = __ifInstance103.exports;
                                                                    return __exports.data(!c.isEven() || !d.isEven() ? 1 : 0);
                                                                })();
                                                                (() => {
                                                                    const __callInstance24 = new WebAssembly.Instance(__callWasmModule, {
                                                                        env: {
                                                                            impFunc: () => {
                                                                                c.rShiftTo(1, c);
                                                                            }
                                                                        }
                                                                    });
                                                                    const __exports = __callInstance24.exports;
                                                                    return __exports.data();
                                                                })();
                                                            }
                                                        },
                                                        impFunc2: () => {
                                                            (() => {
                                                                const __ifInstance104 = new WebAssembly.Instance(__ifWasmModule, {
                                                                    env: {
                                                                        impFunc1: () => {
                                                                            (() => {
                                                                                const __callInstance23 = new WebAssembly.Instance(__callWasmModule, {
                                                                                    env: {
                                                                                        impFunc: () => {
                                                                                            d.subTo(m, d);
                                                                                        }
                                                                                    }
                                                                                });
                                                                                const __exports = __callInstance23.exports;
                                                                                return __exports.data();
                                                                            })();
                                                                        },
                                                                        impFunc2: () => {
                                                                        }
                                                                    }
                                                                });
                                                                const __exports = __ifInstance104.exports;
                                                                return __exports.data(!d.isEven() ? 1 : 0);
                                                            })();
                                                        }
                                                    }
                                                });
                                                const __exports = __ifInstance102.exports;
                                                return __exports.data(ac ? 1 : 0);
                                            })();
                                            (() => {
                                                const __callInstance22 = new WebAssembly.Instance(__callWasmModule, {
                                                    env: {
                                                        impFunc: () => {
                                                            d.rShiftTo(1, d);
                                                        }
                                                    }
                                                });
                                                const __exports = __callInstance22.exports;
                                                return __exports.data();
                                            })();
                                        }
                                    }
                                }
                            });
                            const __exports = __forInstance64.exports;
                            return __exports.data();
                        })();
                        (() => {
                            const __ifInstance105 = new WebAssembly.Instance(__ifWasmModule, {
                                env: {
                                    impFunc1: () => {
                                        {
                                            (() => {
                                                const __callInstance21 = new WebAssembly.Instance(__callWasmModule, {
                                                    env: {
                                                        impFunc: () => {
                                                            u.subTo(v, u);
                                                        }
                                                    }
                                                });
                                                const __exports = __callInstance21.exports;
                                                return __exports.data();
                                            })();
                                            (() => {
                                                const __ifInstance106 = new WebAssembly.Instance(__ifWasmModule, {
                                                    env: {
                                                        impFunc1: () => {
                                                            (() => {
                                                                const __callInstance20 = new WebAssembly.Instance(__callWasmModule, {
                                                                    env: {
                                                                        impFunc: () => {
                                                                            a.subTo(c, a);
                                                                        }
                                                                    }
                                                                });
                                                                const __exports = __callInstance20.exports;
                                                                return __exports.data();
                                                            })();
                                                        },
                                                        impFunc2: () => {
                                                        }
                                                    }
                                                });
                                                const __exports = __ifInstance106.exports;
                                                return __exports.data(ac ? 1 : 0);
                                            })();
                                            (() => {
                                                const __callInstance19 = new WebAssembly.Instance(__callWasmModule, {
                                                    env: {
                                                        impFunc: () => {
                                                            b.subTo(d, b);
                                                        }
                                                    }
                                                });
                                                const __exports = __callInstance19.exports;
                                                return __exports.data();
                                            })();
                                        }
                                    },
                                    impFunc2: () => {
                                        {
                                            (() => {
                                                const __callInstance18 = new WebAssembly.Instance(__callWasmModule, {
                                                    env: {
                                                        impFunc: () => {
                                                            v.subTo(u, v);
                                                        }
                                                    }
                                                });
                                                const __exports = __callInstance18.exports;
                                                return __exports.data();
                                            })();
                                            (() => {
                                                const __ifInstance107 = new WebAssembly.Instance(__ifWasmModule, {
                                                    env: {
                                                        impFunc1: () => {
                                                            (() => {
                                                                const __callInstance17 = new WebAssembly.Instance(__callWasmModule, {
                                                                    env: {
                                                                        impFunc: () => {
                                                                            c.subTo(a, c);
                                                                        }
                                                                    }
                                                                });
                                                                const __exports = __callInstance17.exports;
                                                                return __exports.data();
                                                            })();
                                                        },
                                                        impFunc2: () => {
                                                        }
                                                    }
                                                });
                                                const __exports = __ifInstance107.exports;
                                                return __exports.data(ac ? 1 : 0);
                                            })();
                                            (() => {
                                                const __callInstance16 = new WebAssembly.Instance(__callWasmModule, {
                                                    env: {
                                                        impFunc: () => {
                                                            d.subTo(b, d);
                                                        }
                                                    }
                                                });
                                                const __exports = __callInstance16.exports;
                                                return __exports.data();
                                            })();
                                        }
                                    }
                                }
                            });
                            const __exports = __ifInstance105.exports;
                            return __exports.data(u.compareTo(v) >= 0 ? 1 : 0);
                        })();
                    }
                }
            }
        });
        const __exports = __forInstance62.exports;
        return __exports.data();
    })();
    if (v.compareTo(BigInteger.ONE) != 0)
        return BigInteger.ZERO;
    if (d.compareTo(m) >= 0)
        return d.subtract(m);
    if (d.signum() < 0)
        (() => {
            const __callInstance15 = new WebAssembly.Instance(__callWasmModule, {
                env: {
                    impFunc: () => {
                        d.addTo(m, d);
                    }
                }
            });
            const __exports = __callInstance15.exports;
            return __exports.data();
        })();
    else
        return d;
    if (d.signum() < 0)
        return d.add(m);
    else
        return d;
}
var lowprimes = __lA(2, 24, 412);
var lplim = (1 << 26) / lowprimes[lowprimes.length - 1];
function bnIsProbablePrime(t) {
    var i, x = this.abs();
    var x_array = x.array;
    if (x.t == 1 && x_array[0] <= lowprimes[lowprimes.length - 1]) {
        for (i = 0; i < lowprimes.length; ++i)
            if (x_array[0] == lowprimes[i])
                return true;
        return false;
    }
    if (x.isEven())
        return false;
    i = 1;
    while (i < lowprimes.length) {
        var m = lowprimes[i], j = i + 1;
        (() => {
            const __forInstance65 = new WebAssembly.Instance(__forWasmModule, {
                env: {
                    test: () => {
                        return j < lowprimes.length && m < lplim ? 1 : 0;
                    },
                    update: () => {
                    },
                    body: () => {
                        m *= lowprimes[j++];
                    }
                }
            });
            const __exports = __forInstance65.exports;
            return __exports.data();
        })();
        m = x.modInt(m);
        while (i < j)
            if (m % lowprimes[i++] == 0)
                return false;
    }
    return x.millerRabin(t);
}
function bnpMillerRabin(t) {
    var n1 = this.subtract(BigInteger.ONE);
    var k = n1.getLowestSetBit();
    if (k <= 0)
        return false;
    var r = n1.shiftRight(k);
    t = t + 1 >> 1;
    (() => {
        const __ifInstance108 = new WebAssembly.Instance(__ifWasmModule, {
            env: {
                impFunc1: () => {
                    t = lowprimes.length;
                },
                impFunc2: () => {
                }
            }
        });
        const __exports = __ifInstance108.exports;
        return __exports.data(t > lowprimes.length ? 1 : 0);
    })();
    var a = nbi();
    for (var i = 0; i < t; ++i) {
        (() => {
            const __callInstance14 = new WebAssembly.Instance(__callWasmModule, {
                env: {
                    impFunc: () => {
                        a.fromInt(lowprimes[i]);
                    }
                }
            });
            const __exports = __callInstance14.exports;
            return __exports.data();
        })();
        var y = a.modPow(r, this);
        if (y.compareTo(BigInteger.ONE) != 0 && y.compareTo(n1) != 0) {
            var j = 1;
            while (j++ < k && y.compareTo(n1) != 0) {
                y = y.modPowInt(2, this);
                if (y.compareTo(BigInteger.ONE) == 0)
                    return false;
            }
            if (y.compareTo(n1) != 0)
                return false;
        }
    }
    return true;
}
BigInteger.prototype.chunkSize = bnpChunkSize;
BigInteger.prototype.toRadix = bnpToRadix;
BigInteger.prototype.fromRadix = bnpFromRadix;
BigInteger.prototype.fromNumber = bnpFromNumber;
BigInteger.prototype.bitwiseTo = bnpBitwiseTo;
BigInteger.prototype.changeBit = bnpChangeBit;
BigInteger.prototype.addTo = bnpAddTo;
BigInteger.prototype.dMultiply = bnpDMultiply;
BigInteger.prototype.dAddOffset = bnpDAddOffset;
BigInteger.prototype.multiplyLowerTo = bnpMultiplyLowerTo;
BigInteger.prototype.multiplyUpperTo = bnpMultiplyUpperTo;
BigInteger.prototype.modInt = bnpModInt;
BigInteger.prototype.millerRabin = bnpMillerRabin;
BigInteger.prototype.clone = bnClone;
BigInteger.prototype.intValue = bnIntValue;
BigInteger.prototype.byteValue = bnByteValue;
BigInteger.prototype.shortValue = bnShortValue;
BigInteger.prototype.signum = bnSigNum;
BigInteger.prototype.toByteArray = bnToByteArray;
BigInteger.prototype.equals = bnEquals;
BigInteger.prototype.min = bnMin;
BigInteger.prototype.max = bnMax;
BigInteger.prototype.and = bnAnd;
BigInteger.prototype.or = bnOr;
BigInteger.prototype.xor = bnXor;
BigInteger.prototype.andNot = bnAndNot;
BigInteger.prototype.not = bnNot;
BigInteger.prototype.shiftLeft = bnShiftLeft;
BigInteger.prototype.shiftRight = bnShiftRight;
BigInteger.prototype.getLowestSetBit = bnGetLowestSetBit;
BigInteger.prototype.bitCount = bnBitCount;
BigInteger.prototype.testBit = bnTestBit;
BigInteger.prototype.setBit = bnSetBit;
BigInteger.prototype.clearBit = bnClearBit;
BigInteger.prototype.flipBit = bnFlipBit;
BigInteger.prototype.add = bnAdd;
BigInteger.prototype.subtract = bnSubtract;
BigInteger.prototype.multiply = bnMultiply;
BigInteger.prototype.divide = bnDivide;
BigInteger.prototype.remainder = bnRemainder;
BigInteger.prototype.divideAndRemainder = bnDivideAndRemainder;
BigInteger.prototype.modPow = bnModPow;
BigInteger.prototype.modInverse = bnModInverse;
BigInteger.prototype.pow = bnPow;
BigInteger.prototype.gcd = bnGCD;
BigInteger.prototype.isProbablePrime = bnIsProbablePrime;
function Arcfour() {
    this.i = 0;
    this.j = 0;
    this.S = new Array();
}
function ARC4init(key) {
    var i, j, t;
    (() => {
        i = 0;
        const __forInstance22 = new WebAssembly.Instance(__forWasmModule, {
            env: {
                test: () => {
                    return i < 256 ? 1 : 0;
                },
                update: () => {
                    ++i;
                },
                body: () => {
                    this.S[i] = i;
                }
            }
        });
        const __exports = __forInstance22.exports;
        return __exports.data();
    })();
    j = 0;
    (() => {
        i = 0;
        const __forInstance23 = new WebAssembly.Instance(__forWasmModule, {
            env: {
                test: () => {
                    return i < 256 ? 1 : 0;
                },
                update: () => {
                    ++i;
                },
                body: () => {
                    {
                        j = j + this.S[i] + key[i % key.length] & 255;
                        t = this.S[i];
                        this.S[i] = this.S[j];
                        this.S[j] = t;
                    }
                }
            }
        });
        const __exports = __forInstance23.exports;
        return __exports.data();
    })();
    this.i = 0;
    this.j = 0;
}
function ARC4next() {
    var t;
    this.i = this.i + 1 & 255;
    this.j = this.j + this.S[this.i] & 255;
    t = this.S[this.i];
    this.S[this.i] = this.S[this.j];
    this.S[this.j] = t;
    return this.S[t + this.S[this.i] & 255];
}
Arcfour.prototype.init = ARC4init;
Arcfour.prototype.next = ARC4next;
function prng_newstate() {
    return new Arcfour();
}
var rng_psize = 256;
var rng_state;
var rng_pool;
var rng_pptr;
function rng_seed_int(x) {
    rng_pool[rng_pptr++] ^= x & 255;
    rng_pool[rng_pptr++] ^= x >> 8 & 255;
    rng_pool[rng_pptr++] ^= x >> 16 & 255;
    rng_pool[rng_pptr++] ^= x >> 24 & 255;
    (() => {
        const __ifInstance109 = new WebAssembly.Instance(__ifWasmModule, {
            env: {
                impFunc1: () => {
                    rng_pptr -= rng_psize;
                },
                impFunc2: () => {
                }
            }
        });
        const __exports = __ifInstance109.exports;
        return __exports.data(rng_pptr >= rng_psize ? 1 : 0);
    })();
}
function rng_seed_time() {
    (() => {
        const __callInstance13 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    rng_seed_int(1122926989487);
                }
            }
        });
        const __exports = __callInstance13.exports;
        return __exports.data();
    })();
}
(() => {
    const __ifInstance110 = new WebAssembly.Instance(__ifWasmModule, {
        env: {
            impFunc1: () => {
                {
                    rng_pool = new Array();
                    rng_pptr = 0;
                    var t;
                    (() => {
                        const __forInstance66 = new WebAssembly.Instance(__forWasmModule, {
                            env: {
                                test: () => {
                                    return rng_pptr < rng_psize ? 1 : 0;
                                },
                                update: () => {
                                },
                                body: () => {
                                    {
                                        t = Math.floor(65536 * Math.random());
                                        rng_pool[rng_pptr++] = t >>> 8;
                                        rng_pool[rng_pptr++] = t & 255;
                                    }
                                }
                            }
                        });
                        const __exports = __forInstance66.exports;
                        return __exports.data();
                    })();
                    rng_pptr = 0;
                    (() => {
                        const __callInstance12 = new WebAssembly.Instance(__callWasmModule, {
                            env: {
                                impFunc: () => {
                                    rng_seed_time();
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
    const __exports = __ifInstance110.exports;
    return __exports.data(rng_pool == null ? 1 : 0);
})();
function rng_get_byte() {
    (() => {
        const __ifInstance111 = new WebAssembly.Instance(__ifWasmModule, {
            env: {
                impFunc1: () => {
                    {
                        (() => {
                            const __callInstance11 = new WebAssembly.Instance(__callWasmModule, {
                                env: {
                                    impFunc: () => {
                                        rng_seed_time();
                                    }
                                }
                            });
                            const __exports = __callInstance11.exports;
                            return __exports.data();
                        })();
                        rng_state = prng_newstate();
                        (() => {
                            const __callInstance10 = new WebAssembly.Instance(__callWasmModule, {
                                env: {
                                    impFunc: () => {
                                        rng_state.init(rng_pool);
                                    }
                                }
                            });
                            const __exports = __callInstance10.exports;
                            return __exports.data();
                        })();
                        (() => {
                            rng_pptr = 0;
                            const __forInstance24 = new WebAssembly.Instance(__forWasmModule, {
                                env: {
                                    test: () => {
                                        return rng_pptr < rng_pool.length ? 1 : 0;
                                    },
                                    update: () => {
                                        ++rng_pptr;
                                    },
                                    body: () => {
                                        rng_pool[rng_pptr] = 0;
                                    }
                                }
                            });
                            const __exports = __forInstance24.exports;
                            return __exports.data();
                        })();
                        rng_pptr = 0;
                    }
                },
                impFunc2: () => {
                }
            }
        });
        const __exports = __ifInstance111.exports;
        return __exports.data(rng_state == null ? 1 : 0);
    })();
    return rng_state.next();
}
function rng_get_bytes(ba) {
    var i;
    (() => {
        i = 0;
        const __forInstance25 = new WebAssembly.Instance(__forWasmModule, {
            env: {
                test: () => {
                    return i < ba.length ? 1 : 0;
                },
                update: () => {
                    ++i;
                },
                body: () => {
                    ba[i] = rng_get_byte();
                }
            }
        });
        const __exports = __forInstance25.exports;
        return __exports.data();
    })();
}
function SecureRandom() {
}
SecureRandom.prototype.nextBytes = rng_get_bytes;
function parseBigInt(str, r) {
    return new BigInteger(str, r);
}
function linebrk(s, n) {
    var ret = lS(0, 17);
    var i = 0;
    (() => {
        const __forInstance67 = new WebAssembly.Instance(__forWasmModule, {
            env: {
                test: () => {
                    return i + n < s.length ? 1 : 0;
                },
                update: () => {
                },
                body: () => {
                    {
                        ret += s.substring(i, i + n) + lS(0, 18);
                        i += n;
                    }
                }
            }
        });
        const __exports = __forInstance67.exports;
        return __exports.data();
    })();
    return ret + s.substring(i, s.length);
}
function byte2Hex(b) {
    if (b < 16)
        return lS(0, 19) + b.toString(16);
    else
        return b.toString(16);
}
function pkcs1pad2(s, n) {
    if (n < s.length + 11) {
        (() => {
            const __callInstance9 = new WebAssembly.Instance(__callWasmModule, {
                env: {
                    impFunc: () => {
                        alert(lS(0, 20));
                    }
                }
            });
            const __exports = __callInstance9.exports;
            return __exports.data();
        })();
        return null;
    }
    var ba = new Array();
    var i = s.length - 1;
    (() => {
        const __forInstance68 = new WebAssembly.Instance(__forWasmModule, {
            env: {
                test: () => {
                    return i >= 0 && n > 0 ? 1 : 0;
                },
                update: () => {
                },
                body: () => {
                    ba[--n] = s.charCodeAt(i--);
                }
            }
        });
        const __exports = __forInstance68.exports;
        return __exports.data();
    })();
    ba[--n] = 0;
    var rng = new SecureRandom();
    var x = __lA(3, 412, 416);
    (() => {
        const __forInstance69 = new WebAssembly.Instance(__forWasmModule, {
            env: {
                test: () => {
                    return n > 2 ? 1 : 0;
                },
                update: () => {
                },
                body: () => {
                    {
                        (() => {
                            const __forInstance70 = new WebAssembly.Instance(__forWasmModule, {
                                env: {
                                    test: () => {
                                        return x[0] == 0 ? 1 : 0;
                                    },
                                    update: () => {
                                    },
                                    body: () => {
                                        (() => {
                                            const __callInstance8 = new WebAssembly.Instance(__callWasmModule, {
                                                env: {
                                                    impFunc: () => {
                                                        rng.nextBytes(x);
                                                    }
                                                }
                                            });
                                            const __exports = __callInstance8.exports;
                                            return __exports.data();
                                        })();
                                    }
                                }
                            });
                            const __exports = __forInstance70.exports;
                            return __exports.data();
                        })();
                        ba[--n] = x[0];
                    }
                }
            }
        });
        const __exports = __forInstance69.exports;
        return __exports.data();
    })();
    ba[--n] = 2;
    ba[--n] = 0;
    return new BigInteger(ba);
}
function RSAKey() {
    this.n = null;
    this.e = 0;
    this.d = null;
    this.p = null;
    this.q = null;
    this.dmp1 = null;
    this.dmq1 = null;
    this.coeff = null;
}
function RSASetPublic(N, E) {
    (() => {
        const __ifInstance112 = new WebAssembly.Instance(__ifWasmModule, {
            env: {
                impFunc1: () => {
                    {
                        this.n = parseBigInt(N, 16);
                        this.e = parseInt(E, 16);
                    }
                },
                impFunc2: () => {
                    (() => {
                        const __callInstance7 = new WebAssembly.Instance(__callWasmModule, {
                            env: {
                                impFunc: () => {
                                    alert(lS(0, 21));
                                }
                            }
                        });
                        const __exports = __callInstance7.exports;
                        return __exports.data();
                    })();
                }
            }
        });
        const __exports = __ifInstance112.exports;
        return __exports.data(N != null && E != null && N.length > 0 && E.length > 0 ? 1 : 0);
    })();
}
function RSADoPublic(x) {
    return x.modPowInt(this.e, this.n);
}
function RSAEncrypt(text) {
    var m = pkcs1pad2(text, this.n.bitLength() + 7 >> 3);
    if (m == null)
        return null;
    var c = this.doPublic(m);
    if (c == null)
        return null;
    var h = c.toString(16);
    if ((h.length & 1) == 0)
        return h;
    else
        return lS(0, 22) + h;
}
RSAKey.prototype.doPublic = RSADoPublic;
RSAKey.prototype.setPublic = RSASetPublic;
RSAKey.prototype.encrypt = RSAEncrypt;
function pkcs1unpad2(d, n) {
    var b = d.toByteArray();
    var i = 0;
    (() => {
        const __forInstance71 = new WebAssembly.Instance(__forWasmModule, {
            env: {
                test: () => {
                    return i < b.length && b[i] == 0 ? 1 : 0;
                },
                update: () => {
                },
                body: () => {
                    ++i;
                }
            }
        });
        const __exports = __forInstance71.exports;
        return __exports.data();
    })();
    if (b.length - i != n - 1 || b[i] != 2)
        return null;
    ++i;
    while (b[i] != 0)
        if (++i >= b.length)
            return null;
    var ret = lS(0, 23);
    (() => {
        const __forInstance72 = new WebAssembly.Instance(__forWasmModule, {
            env: {
                test: () => {
                    return ++i < b.length ? 1 : 0;
                },
                update: () => {
                },
                body: () => {
                    ret += String.fromCharCode(b[i]);
                }
            }
        });
        const __exports = __forInstance72.exports;
        return __exports.data();
    })();
    return ret;
}
function RSASetPrivate(N, E, D) {
    (() => {
        const __ifInstance113 = new WebAssembly.Instance(__ifWasmModule, {
            env: {
                impFunc1: () => {
                    {
                        this.n = parseBigInt(N, 16);
                        this.e = parseInt(E, 16);
                        this.d = parseBigInt(D, 16);
                    }
                },
                impFunc2: () => {
                    (() => {
                        const __callInstance6 = new WebAssembly.Instance(__callWasmModule, {
                            env: {
                                impFunc: () => {
                                    alert(lS(0, 24));
                                }
                            }
                        });
                        const __exports = __callInstance6.exports;
                        return __exports.data();
                    })();
                }
            }
        });
        const __exports = __ifInstance113.exports;
        return __exports.data(N != null && E != null && N.length > 0 && E.length > 0 ? 1 : 0);
    })();
}
function RSASetPrivateEx(N, E, D, P, Q, DP, DQ, C) {
    (() => {
        const __ifInstance114 = new WebAssembly.Instance(__ifWasmModule, {
            env: {
                impFunc1: () => {
                    {
                        this.n = parseBigInt(N, 16);
                        this.e = parseInt(E, 16);
                        this.d = parseBigInt(D, 16);
                        this.p = parseBigInt(P, 16);
                        this.q = parseBigInt(Q, 16);
                        this.dmp1 = parseBigInt(DP, 16);
                        this.dmq1 = parseBigInt(DQ, 16);
                        this.coeff = parseBigInt(C, 16);
                    }
                },
                impFunc2: () => {
                    (() => {
                        const __callInstance5 = new WebAssembly.Instance(__callWasmModule, {
                            env: {
                                impFunc: () => {
                                    alert(lS(0, 25));
                                }
                            }
                        });
                        const __exports = __callInstance5.exports;
                        return __exports.data();
                    })();
                }
            }
        });
        const __exports = __ifInstance114.exports;
        return __exports.data(N != null && E != null && N.length > 0 && E.length > 0 ? 1 : 0);
    })();
}
function RSAGenerate(B, E) {
    var rng = new SecureRandom();
    var qs = B >> 1;
    this.e = parseInt(E, 16);
    var ee = new BigInteger(E, 16);
    for (;;) {
        for (;;) {
            this.p = new BigInteger(B - qs, 1, rng);
            if (this.p.subtract(BigInteger.ONE).gcd(ee).compareTo(BigInteger.ONE) == 0 && this.p.isProbablePrime(10))
                break;
        }
        for (;;) {
            this.q = new BigInteger(qs, 1, rng);
            if (this.q.subtract(BigInteger.ONE).gcd(ee).compareTo(BigInteger.ONE) == 0 && this.q.isProbablePrime(10))
                break;
        }
        (() => {
            const __ifInstance115 = new WebAssembly.Instance(__ifWasmModule, {
                env: {
                    impFunc1: () => {
                        {
                            var t = this.p;
                            this.p = this.q;
                            this.q = t;
                        }
                    },
                    impFunc2: () => {
                    }
                }
            });
            const __exports = __ifInstance115.exports;
            return __exports.data(this.p.compareTo(this.q) <= 0 ? 1 : 0);
        })();
        var p1 = this.p.subtract(BigInteger.ONE);
        var q1 = this.q.subtract(BigInteger.ONE);
        var phi = p1.multiply(q1);
        if (phi.gcd(ee).compareTo(BigInteger.ONE) == 0) {
            this.n = this.p.multiply(this.q);
            this.d = ee.modInverse(phi);
            this.dmp1 = this.d.mod(p1);
            this.dmq1 = this.d.mod(q1);
            this.coeff = this.q.modInverse(this.p);
            break;
        }
    }
}
function RSADoPrivate(x) {
    if (this.p == null || this.q == null)
        return x.modPow(this.d, this.n);
    var xp = x.mod(this.p).modPow(this.dmp1, this.p);
    var xq = x.mod(this.q).modPow(this.dmq1, this.q);
    (() => {
        const __forInstance73 = new WebAssembly.Instance(__forWasmModule, {
            env: {
                test: () => {
                    return xp.compareTo(xq) < 0 ? 1 : 0;
                },
                update: () => {
                },
                body: () => {
                    xp = xp.add(this.p);
                }
            }
        });
        const __exports = __forInstance73.exports;
        return __exports.data();
    })();
    return xp.subtract(xq).multiply(this.coeff).mod(this.p).multiply(this.q).add(xq);
}
function RSADecrypt(ctext) {
    var c = parseBigInt(ctext, 16);
    var m = this.doPrivate(c);
    if (m == null)
        return null;
    return pkcs1unpad2(m, this.n.bitLength() + 7 >> 3);
}
RSAKey.prototype.doPrivate = RSADoPrivate;
RSAKey.prototype.setPrivate = RSASetPrivate;
RSAKey.prototype.setPrivateEx = RSASetPrivateEx;
RSAKey.prototype.generate = RSAGenerate;
RSAKey.prototype.decrypt = RSADecrypt;
nValue = lS(0, 26);
eValue = lS(0, 27);
dValue = lS(0, 28);
pValue = lS(0, 29);
qValue = lS(0, 30);
dmp1Value = lS(0, 31);
dmq1Value = lS(0, 32);
coeffValue = lS(0, 33);
(() => {
    const __callInstance4 = new WebAssembly.Instance(__callWasmModule, {
        env: {
            impFunc: () => {
                setupEngine(am3, 28);
            }
        }
    });
    const __exports = __callInstance4.exports;
    return __exports.data();
})();
var TEXT = lS(0, 34) + lS(0, 35);
var encrypted;
function encrypt() {
    var RSA = new RSAKey();
    (() => {
        const __callInstance3 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    RSA.setPublic(nValue, eValue);
                }
            }
        });
        const __exports = __callInstance3.exports;
        return __exports.data();
    })();
    (() => {
        const __callInstance2 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    RSA.setPrivateEx(nValue, eValue, dValue, pValue, qValue, dmp1Value, dmq1Value, coeffValue);
                }
            }
        });
        const __exports = __callInstance2.exports;
        return __exports.data();
    })();
    encrypted = RSA.encrypt(TEXT);
}
function decrypt() {
    var RSA = new RSAKey();
    (() => {
        const __callInstance1 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    RSA.setPublic(nValue, eValue);
                }
            }
        });
        const __exports = __callInstance1.exports;
        return __exports.data();
    })();
    (() => {
        const __callInstance0 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    RSA.setPrivateEx(nValue, eValue, dValue, pValue, qValue, dmp1Value, dmq1Value, coeffValue);
                }
            }
        });
        const __exports = __callInstance0.exports;
        return __exports.data();
    })();
    var decrypted = RSA.decrypt(encrypted);
    if (decrypted != TEXT) {
        throw new Error(lS(0, 36));
    }
}