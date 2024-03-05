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
const __aB = 'AGFzbQEAAAABiYCAgAACYAAAYAJ/fwADg4CAgAACAQAFg4CAgAABAAEGhoCAgAABfwFBAAsHkYCAgAACBm1lbW9yeQIABGFycjAAAQqsgICAAAKPgICAAAAjACAAQQRsaiABNgIAC5KAgIAAAQF/QRAkAEEAQfrTzPkAEAAL', __wAM = new WebAssembly.Instance(new WebAssembly.Module((() => {
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
const __wasmStringModules = ['AGFzbQEAAAAFg4CAgAABAAEGrYCAgAAIfwBBAQt/AEEOC38AQRoLfwBBLgt/AEHAAAt/AEHGAAt/AEHaAAt/AEHgAAsHyoCAgAAJBm1lbW9yeQIABWRhdGEwAwAFZGF0YTEDAQVkYXRhMgMCBWRhdGEzAwMFZGF0YTQDBAVkYXRhNQMFBWRhdGE2AwYFZGF0YTcDBwuRgYCAAAgAQQELC1R5cGVzY3JpcHQAAEEOCwtUeXBlc2NyaXB0AABBGgsSY29tcGlsZXJfaW5wdXQudHMAAEEuCxBQYXJzZSUyMGVycm9ycy4AAEHAAAsEJTBBAABBxgALEldyb25nJTIwY2hlY2tzdW0uAABB2gALBCUwQQAAQeAACxJXcm9uZyUyMGNoZWNrc3VtLgA='].map(__bytes => {
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
var typescript = new BenchmarkSuite(lS(0, 0), __lA(0, 16, 20), [new Benchmark(lS(0, 1), false, true, 5, runTypescript, setupTypescript, tearDownTypescript, null, 1)]);
function setupTypescript() {
}
function tearDownTypescript() {
    compiler_input = null;
}
var parseErrors = [];
function runTypescript() {
    var compiler = createCompiler();
    (() => {
        const __callInstance9 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    compiler.addUnit(compiler_input, lS(0, 2));
                }
            }
        });
        const __exports = __callInstance9.exports;
        return __exports.data();
    })();
    parseErrors = [];
    (() => {
        const __callInstance8 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    compiler.reTypeCheck();
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
                    compiler.emit({
                        createFile: function (fileName) {
                            return outfile;
                        },
                        fileExists: function (path) {
                            return false;
                        },
                        directoryExists: function (path) {
                            return false;
                        },
                        resolvePath: function (path) {
                            return path;
                        }
                    });
                }
            }
        });
        const __exports = __callInstance7.exports;
        return __exports.data();
    })();
    if (parseErrors.length != 192 && parseErrors.length != 193) {
        throw new Error(lS(0, 3));
    }
    compiler = null;
}
var outfile = {
    checksum: -412589664,
    cumulative_checksum: 0,
    Write: function (s) {
        (() => {
            const __callInstance6 = new WebAssembly.Instance(__callWasmModule, {
                env: {
                    impFunc: () => {
                        this.Verify(s);
                    }
                }
            });
            const __exports = __callInstance6.exports;
            return __exports.data();
        })();
    },
    WriteLine: function (s) {
        (() => {
            const __callInstance5 = new WebAssembly.Instance(__callWasmModule, {
                env: {
                    impFunc: () => {
                        this.Verify(s + lS(0, 4));
                    }
                }
            });
            const __exports = __callInstance5.exports;
            return __exports.data();
        })();
    },
    Close: function () {
        if (this.checksum != this.cumulative_checksum) {
            throw new Error(lS(0, 5));
        }
        this.cumulative_checksum = 0;
    },
    Verify: function (s) {
        (() => {
            var i = 0;
            const __forInstance0 = new WebAssembly.Instance(__forWasmModule, {
                env: {
                    test: () => {
                        return i < s.length ? 1 : 0;
                    },
                    update: () => {
                        i++;
                    },
                    body: () => {
                        {
                            var c = s.charCodeAt(i);
                            this.cumulative_checksum = this.cumulative_checksum << 1 ^ c;
                        }
                    }
                }
            });
            const __exports = __forInstance0.exports;
            return __exports.data();
        })();
    }
};
var outerr = {
    checksum: 0,
    cumulative_checksum: 0,
    Write: function (s) {
        (() => {
            const __callInstance4 = new WebAssembly.Instance(__callWasmModule, {
                env: {
                    impFunc: () => {
                        this.Verify(s);
                    }
                }
            });
            const __exports = __callInstance4.exports;
            return __exports.data();
        })();
    },
    WriteLine: function (s) {
        (() => {
            const __callInstance3 = new WebAssembly.Instance(__callWasmModule, {
                env: {
                    impFunc: () => {
                        this.Verify(s + lS(0, 6));
                    }
                }
            });
            const __exports = __callInstance3.exports;
            return __exports.data();
        })();
    },
    Close: function () {
        if (this.checksum != this.cumulative_checksum) {
            throw new Error(lS(0, 7));
        }
        this.cumulative_checksum = 0;
    },
    Verify: function (s) {
        (() => {
            var i = 0;
            const __forInstance1 = new WebAssembly.Instance(__forWasmModule, {
                env: {
                    test: () => {
                        return i < s.length ? 1 : 0;
                    },
                    update: () => {
                        i++;
                    },
                    body: () => {
                        {
                            var c = s.charCodeAt(i);
                            this.cumulative_checksum = this.cumulative_checksum << 1 ^ c;
                        }
                    }
                }
            });
            const __exports = __forInstance1.exports;
            return __exports.data();
        })();
    }
};
function createCompiler() {
    var settings = new TypeScript.CompilationSettings();
    settings.codeGenTarget = TypeScript.CodeGenTarget.ES5;
    var compiler = new TypeScript.TypeScriptCompiler(outerr, new TypeScript.NullLogger(), settings);
    (() => {
        const __callInstance2 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    compiler.setErrorCallback(function (start, len, message) {
                        (() => {
                            const __callInstance1 = new WebAssembly.Instance(__callWasmModule, {
                                env: {
                                    impFunc: () => {
                                        parseErrors.push({
                                            start: start,
                                            len: len,
                                            message: message
                                        });
                                    }
                                }
                            });
                            const __exports = __callInstance1.exports;
                            return __exports.data();
                        })();
                    });
                }
            }
        });
        const __exports = __callInstance2.exports;
        return __exports.data();
    })();
    compiler.parser.errorRecovery = true;
    (() => {
        const __callInstance0 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    compiler.typeCheck();
                }
            }
        });
        const __exports = __callInstance0.exports;
        return __exports.data();
    })();
    return compiler;
}