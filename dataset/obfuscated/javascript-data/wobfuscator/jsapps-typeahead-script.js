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
const __wasmStringModules = ['AGFzbQEAAAAFg4CAgAABAAEGzoCAgAANfwBBAQt/AEHoAAt/AEH6AAt/AEGQAQt/AEGYAQt/AEGcAQt/AEGgAQt/AEGiAQt/AEGkAQt/AEGmAQt/AEG8AQt/AEHKAQt/AEHSAQsH9YCAgAAOBm1lbW9yeQIABWRhdGEwAwAFZGF0YTEDAQVkYXRhMgMCBWRhdGEzAwMFZGF0YTQDBAVkYXRhNQMFBWRhdGE2AwYFZGF0YTcDBwVkYXRhOAMIBWRhdGE5AwkGZGF0YTEwAwoGZGF0YTExAwsGZGF0YTEyAwwLlYKAgAANAEEBC2VodHRwcyUzQSUyRiUyRnJhdy5naXRodWJ1c2VyY29udGVudC5jb20lMkZqYWlpbWVyaWlvcyUyRmpzLWFwcHMlMkZtYXN0ZXIlMkYxN190eXBlLWFoZWFkJTJGZGF0YS5qc29uAABB6AALECUyM3NlYXJjaC1pbnB1dAAAQfoACxQlMjNzaG93LXN1Z2dlc3Rpb25zAABBkAELBmtleXVwAABBmAELA2dpAABBnAELA2dpAABBoAELAQAAQaIBCwEAAEGkAQsBAABBpgELFCUyM29wZW4tc2VhcmNoLWZvcm0AAEG8AQsNLnNlYXJjaC1mb3JtAABBygELBmNsaWNrAABB0gELCGlzLW9wZW4A'].map(__bytes => {
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
const endpoint = lS(0, 0);
const getData = [];
const searchInput = document.querySelector(lS(0, 1));
const searchList = document.querySelector(lS(0, 2));
(() => {
    const __callInstance5 = new WebAssembly.Instance(__callWasmModule, {
        env: {
            impFunc: () => {
                searchInput.addEventListener(lS(0, 3), displayMatches);
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
                fetch(endpoint).then(dataResponse => {
                    (() => {
                        const __callInstance3 = new WebAssembly.Instance(__callWasmModule, {
                            env: {
                                impFunc: () => {
                                    dataResponse.json().then(data => {
                                        (() => {
                                            const __callInstance2 = new WebAssembly.Instance(__callWasmModule, {
                                                env: {
                                                    impFunc: () => {
                                                        getData.push(...data);
                                                    }
                                                }
                                            });
                                            const __exports = __callInstance2.exports;
                                            return __exports.data();
                                        })();
                                    });
                                }
                            }
                        });
                        const __exports = __callInstance3.exports;
                        return __exports.data();
                    })();
                });
            }
        }
    });
    const __exports = __callInstance4.exports;
    return __exports.data();
})();
function findMatches(wordToMatch, getData) {
    return getData.filter(data => {
        const regex = new RegExp(wordToMatch, lS(0, 4));
        return data.title.match(regex) || data.description.match(regex);
    });
}
function displayMatches() {
    const matchArray = findMatches(this.value, getData);
    const html = matchArray.map(place => {
        const regexWord = new RegExp(this.value, lS(0, 5));
        const title = place.title.replace(regexWord, `<span class="hl">${ this.value }</span>`);
        const description = place.description.replace(regexWord, `<span class="hl">${ this.value }</span>`);
        return `<li><h6>${ title }</h6> <p>${ description }</li>`;
    }).join(lS(0, 6));
    searchList.innerHTML = html;
    (() => {
        const __ifInstance0 = new WebAssembly.Instance(__ifWasmModule, {
            env: {
                impFunc1: () => {
                    {
                        searchList.innerHTML = lS(0, 8);
                    }
                },
                impFunc2: () => {
                }
            }
        });
        const __exports = __ifInstance0.exports;
        return __exports.data(searchInput.value == lS(0, 7) ? 1 : 0);
    })();
}
const button = document.querySelector(lS(0, 9));
const searchForm = document.querySelector(lS(0, 10));
(() => {
    const __callInstance1 = new WebAssembly.Instance(__callWasmModule, {
        env: {
            impFunc: () => {
                button.addEventListener(lS(0, 11), () => {
                    (() => {
                        const __callInstance0 = new WebAssembly.Instance(__callWasmModule, {
                            env: {
                                impFunc: () => {
                                    searchForm.classList.toggle(lS(0, 12));
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