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
const __wasmStringModules = ['AGFzbQEAAAAFg4CAgAABAAEG0YCAgAAPfwBBAQt/AEEKC38AQRILfwBBGgt/AEEgC38AQSYLfwBBLAt/AEEyC38AQToLfwBBPAt/AEHEAAt/AEHKAAt/AEHQAAt/AEHYAAt/AEHgAAsHh4GAgAAQBm1lbW9yeQIABWRhdGEwAwAFZGF0YTEDAQVkYXRhMgMCBWRhdGEzAwMFZGF0YTQDBAVkYXRhNQMFBWRhdGE2AwYFZGF0YTcDBwVkYXRhOAMIBWRhdGE5AwkGZGF0YTEwAwoGZGF0YTExAwsGZGF0YTEyAwwGZGF0YTEzAw0GZGF0YTE0Aw4Lo4GAgAAPAEEBCwdzZWFyY2gAAEEKCwdzZWFyY2gAAEESCwZjbGVhcgAAQRoLBW5vbmUAAEEgCwVmbGV4AABBJgsFZmxleAAAQSwLBW5vbmUAAEEyCwdzZWFyY2gAAEE6CwEAAEE8CwZjbGVhcgAAQcQACwVmbGV4AABBygALBW5vbmUAAEHQAAsGRW50ZXIAAEHYAAsGU3BhY2UAAEHgAAsGY2xlYXIA'].map(__bytes => {
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
export const setSearchFocus = () => {
    (() => {
        const __callInstance11 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    document.getElementById(lS(0, 0)).focus();
                }
            }
        });
        const __exports = __callInstance11.exports;
        return __exports.data();
    })();
};
export const showClearTextButton = () => {
    const search = document.getElementById(lS(0, 1));
    const clear = document.getElementById(lS(0, 2));
    (() => {
        const __ifInstance0 = new WebAssembly.Instance(__ifWasmModule, {
            env: {
                impFunc1: () => {
                    {
                        (() => {
                            const __callInstance10 = new WebAssembly.Instance(__callWasmModule, {
                                env: {
                                    impFunc: () => {
                                        clear.classList.remove(lS(0, 3));
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
                                        clear.classList.add(lS(0, 4));
                                    }
                                }
                            });
                            const __exports = __callInstance9.exports;
                            return __exports.data();
                        })();
                    }
                },
                impFunc2: () => {
                    {
                        (() => {
                            const __callInstance8 = new WebAssembly.Instance(__callWasmModule, {
                                env: {
                                    impFunc: () => {
                                        clear.classList.remove(lS(0, 5));
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
                                        clear.classList.add(lS(0, 6));
                                    }
                                }
                            });
                            const __exports = __callInstance7.exports;
                            return __exports.data();
                        })();
                    }
                }
            }
        });
        const __exports = __ifInstance0.exports;
        return __exports.data(search.value.length > 0 ? 1 : 0);
    })();
};
export const clearSearchText = e => {
    (() => {
        const __callInstance6 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    e.preventDefault();
                }
            }
        });
        const __exports = __callInstance6.exports;
        return __exports.data();
    })();
    document.getElementById(lS(0, 7)).value = lS(0, 8);
    const clear = document.getElementById(lS(0, 9));
    (() => {
        const __callInstance5 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    clear.classList.remove(lS(0, 10));
                }
            }
        });
        const __exports = __callInstance5.exports;
        return __exports.data();
    })();
    (() => {
        const __callInstance4 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    clear.classList.add(lS(0, 11));
                }
            }
        });
        const __exports = __callInstance4.exports;
        return __exports.data();
    })();
    (() => {
        const __callInstance3 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    setSearchFocus();
                }
            }
        });
        const __exports = __callInstance3.exports;
        return __exports.data();
    })();
};
export const clearPushListener = e => {
    (() => {
        const __callInstance2 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    console.log(e.key);
                }
            }
        });
        const __exports = __callInstance2.exports;
        return __exports.data();
    })();
    (() => {
        const __ifInstance1 = new WebAssembly.Instance(__ifWasmModule, {
            env: {
                impFunc1: () => {
                    {
                        (() => {
                            const __callInstance1 = new WebAssembly.Instance(__callWasmModule, {
                                env: {
                                    impFunc: () => {
                                        e.preventDefault();
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
                                        document.getElementById(lS(0, 14)).click();
                                    }
                                }
                            });
                            const __exports = __callInstance0.exports;
                            return __exports.data();
                        })();
                    }
                },
                impFunc2: () => {
                }
            }
        });
        const __exports = __ifInstance1.exports;
        return __exports.data(e.key === lS(0, 12) || e.key === lS(0, 13) ? 1 : 0);
    })();
};