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
const __wasmStringModules = ['AGFzbQEAAAAFg4CAgAABAAEGlYCAgAAEfwBBAQt/AEEKC38AQRALfwBBGAsHqoCAgAAFBm1lbW9yeQIABWRhdGEwAwAFZGF0YTEDAQVkYXRhMgMCBWRhdGEzAwMLsICAgAAEAEEBCwdzZWFyY2gAAEEKCwQlMjAAAEEQCwZxdWVyeQAAQRgLCnRodW1ibmFpbAA='].map(__bytes => {
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
export const getSearchTerm = () => {
    const rawSearchTerm = document.getElementById(lS(0, 0)).value.trim();
    const regex = /[ ]{2,}/gi;
    const searchTerm = rawSearchTerm.replaceAll(regex, lS(0, 1));
    return searchTerm;
};
export const retrieveSearchResults = async searchTerm => {
    const wikiSearchString = getwikiSearchString(searchTerm);
    const wikiSearchResults = await requestData(wikiSearchString);
    let resultArray = [];
    (() => {
        const __ifInstance0 = new WebAssembly.Instance(__ifWasmModule, {
            env: {
                impFunc1: () => {
                    {
                        resultArray = processWikiResults(wikiSearchResults.query.pages);
                    }
                },
                impFunc2: () => {
                }
            }
        });
        const __exports = __ifInstance0.exports;
        return __exports.data(wikiSearchResults.hasOwnProperty(lS(0, 2)) ? 1 : 0);
    })();
    return resultArray;
};
const getwikiSearchString = searchTerm => {
    const maxChars = getMaxChars();
    const rawSearchString = `https://en.wikipedia.org/w/api.php?action=query&generator=search&gsrsearch=${ searchTerm }&gsrlimit=20&prop=pageimages|extracts&exchars=${ maxChars }&exintro&explaintext&exlimit=max&format=json&origin=*`;
    const searchString = encodeURI(rawSearchString);
    return searchString;
};
const getMaxChars = () => {
    const width = window.innerWidth || document.body.clientWidth;
    let maxChars;
    (() => {
        const __ifInstance1 = new WebAssembly.Instance(__ifWasmModule, {
            env: {
                impFunc1: () => {
                    maxChars = 65;
                },
                impFunc2: () => {
                }
            }
        });
        const __exports = __ifInstance1.exports;
        return __exports.data(width < 414 ? 1 : 0);
    })();
    (() => {
        const __ifInstance2 = new WebAssembly.Instance(__ifWasmModule, {
            env: {
                impFunc1: () => {
                    maxChars = 90;
                },
                impFunc2: () => {
                }
            }
        });
        const __exports = __ifInstance2.exports;
        return __exports.data(width >= 414 && width < 600 ? 1 : 0);
    })();
    (() => {
        const __ifInstance3 = new WebAssembly.Instance(__ifWasmModule, {
            env: {
                impFunc1: () => {
                    maxChars = 120;
                },
                impFunc2: () => {
                }
            }
        });
        const __exports = __ifInstance3.exports;
        return __exports.data(width >= 600 ? 1 : 0);
    })();
    return maxChars;
};
const requestData = async searchString => {
    try {
        const response = await fetch(searchString);
        const data = await response.json();
        return data;
    } catch (error) {
        (() => {
            const __callInstance2 = new WebAssembly.Instance(__callWasmModule, {
                env: {
                    impFunc: () => {
                        console.log(error);
                    }
                }
            });
            const __exports = __callInstance2.exports;
            return __exports.data();
        })();
    }
};
const processWikiResults = results => {
    const resultArray = [];
    (() => {
        const __callInstance1 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    Object.keys(results).forEach(key => {
                        const id = key;
                        const title = results[key].title;
                        const text = results[key].extract;
                        const img = results[key].hasOwnProperty(lS(0, 3)) ? results[key].thumbnail.source : null;
                        const item = {
                            id: id,
                            title: title,
                            img: img,
                            text: text
                        };
                        (() => {
                            const __callInstance0 = new WebAssembly.Instance(__callWasmModule, {
                                env: {
                                    impFunc: () => {
                                        resultArray.push(item);
                                    }
                                }
                            });
                            const __exports = __callInstance0.exports;
                            return __exports.data();
                        })();
                    });
                }
            }
        });
        const __exports = __callInstance1.exports;
        return __exports.data();
    })();
    return resultArray;
};