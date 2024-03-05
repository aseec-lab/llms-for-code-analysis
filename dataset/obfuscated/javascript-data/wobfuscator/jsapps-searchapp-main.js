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
const __wasmStringModules = ['AGFzbQEAAAAFg4CAgAABAAEGtoCAgAAKfwBBAQt/AEEUC38AQR4LfwBBJgt/AEEuC38AQTYLfwBBPgt/AEHIAAt/AEHUAAt/AEHcAAsH2oCAgAALBm1lbW9yeQIABWRhdGEwAwAFZGF0YTEDAQVkYXRhMgMCBWRhdGEzAwMFZGF0YTQDBAVkYXRhNQMFBWRhdGE2AwYFZGF0YTcDBwVkYXRhOAMIBWRhdGE5AwkLhIGAgAAKAEEBCxFyZWFkeXN0YXRlY2hhbmdlAABBFAsJY29tcGxldGUAAEEeCwdzZWFyY2gAAEEmCwZpbnB1dAAAQS4LBmNsZWFyAABBNgsGY2xpY2sAAEE+CwhrZXlkb3duAABByAALC3NlYXJjaC1iYXIAAEHUAAsHc3VibWl0AABB3AALAQA='].map(__bytes => {
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
import {
    setSearchFocus,
    showClearTextButton,
    clearSearchText,
    clearPushListener
} from './modules/search-bar.js';
import {
    getSearchTerm,
    retrieveSearchResults
} from './modules/data-functions.js';
import {
    deleteSearchResults,
    buildSearchResults,
    clearStatsLine,
    setStatsLine
} from './modules/search-results.js';
(() => {
    const __callInstance13 = new WebAssembly.Instance(__callWasmModule, {
        env: {
            impFunc: () => {
                document.addEventListener(lS(0, 0), event => {
                    (() => {
                        const __ifInstance0 = new WebAssembly.Instance(__ifWasmModule, {
                            env: {
                                impFunc1: () => {
                                    {
                                        (() => {
                                            const __callInstance12 = new WebAssembly.Instance(__callWasmModule, {
                                                env: {
                                                    impFunc: () => {
                                                        initApp();
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
                        const __exports = __ifInstance0.exports;
                        return __exports.data(event.target.readyState === lS(0, 1) ? 1 : 0);
                    })();
                });
            }
        }
    });
    const __exports = __callInstance13.exports;
    return __exports.data();
})();
const initApp = () => {
    (() => {
        const __callInstance11 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    setSearchFocus();
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
                    document.getElementById(lS(0, 2)).addEventListener(lS(0, 3), showClearTextButton);
                }
            }
        });
        const __exports = __callInstance10.exports;
        return __exports.data();
    })();
    const clear = document.getElementById(lS(0, 4));
    (() => {
        const __callInstance9 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    clear.addEventListener(lS(0, 5), clearSearchText);
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
                    clear.addEventListener(lS(0, 6), clearPushListener);
                }
            }
        });
        const __exports = __callInstance8.exports;
        return __exports.data();
    })();
    const form = document.getElementById(lS(0, 7));
    (() => {
        const __callInstance7 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    form.addEventListener(lS(0, 8), submitSearch);
                }
            }
        });
        const __exports = __callInstance7.exports;
        return __exports.data();
    })();
};
const submitSearch = e => {
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
    (() => {
        const __callInstance5 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    deleteSearchResults();
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
                    processSearch();
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
const processSearch = async () => {
    (() => {
        const __callInstance2 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    clearStatsLine();
                }
            }
        });
        const __exports = __callInstance2.exports;
        return __exports.data();
    })();
    const searchTerm = getSearchTerm();
    if (searchTerm === lS(0, 9))
        return;
    const resultArray = await retrieveSearchResults(searchTerm);
    (() => {
        const __ifInstance1 = new WebAssembly.Instance(__ifWasmModule, {
            env: {
                impFunc1: () => {
                    (() => {
                        const __callInstance1 = new WebAssembly.Instance(__callWasmModule, {
                            env: {
                                impFunc: () => {
                                    buildSearchResults(resultArray);
                                }
                            }
                        });
                        const __exports = __callInstance1.exports;
                        return __exports.data();
                    })();
                },
                impFunc2: () => {
                }
            }
        });
        const __exports = __ifInstance1.exports;
        return __exports.data(resultArray.length ? 1 : 0);
    })();
    (() => {
        const __callInstance0 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    setStatsLine(resultArray.length);
                }
            }
        });
        const __exports = __callInstance0.exports;
        return __exports.data();
    })();
};