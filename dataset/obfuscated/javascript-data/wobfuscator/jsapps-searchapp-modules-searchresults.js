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
const __wasmStringModules = ['AGFzbQEAAAAFg4CAgAABAAEG+YCAgAAVfwBBAQt/AEESC38AQRgLfwBBKAt/AEE4C38AQT4LfwBBzAALfwBB0gALfwBB4AALfwBB5AALfwBB7AALfwBB8gALfwBBgAELfwBBhgELfwBBjAELfwBBmgELfwBBngELfwBBsgELfwBBugELfwBBvAELfwBBxAELB72BgIAAFgZtZW1vcnkCAAVkYXRhMAMABWRhdGExAwEFZGF0YTIDAgVkYXRhMwMDBWRhdGE0AwQFZGF0YTUDBQVkYXRhNgMGBWRhdGE3AwcFZGF0YTgDCAVkYXRhOQMJBmRhdGExMAMKBmRhdGExMQMLBmRhdGExMgMMBmRhdGExMwMNBmRhdGExNAMOBmRhdGExNQMPBmRhdGExNgMQBmRhdGExNwMRBmRhdGExOAMSBmRhdGExOQMTBmRhdGEyMAMUC6iCgIAAFQBBAQsPc2VhcmNoLXJlc3VsdHMAAEESCwRkaXYAAEEYCw9yZXN1bHQtY29udGVudAAAQSgLD3NlYXJjaC1yZXN1bHRzAABBOAsEZGl2AABBPgsMcmVzdWx0LWl0ZW0AAEHMAAsEZGl2AABB0gALDXJlc3VsdC10aXRsZQAAQeAACwJhAABB5AALB19ibGFuawAAQewACwRkaXYAAEHyAAsNcmVzdWx0LWltYWdlAABBgAELBGltZwAAQYYBCwRkaXYAAEGMAQsMcmVzdWx0LXRleHQAAEGaAQsCcAAAQZ4BCxNyZXN1bHQtZGVzY3JpcHRpb24AAEGyAQsGc3RhdHMAAEG6AQsBAABBvAELBnN0YXRzAABBxAELDU5vJTIwUmVzdWx0cwA='].map(__bytes => {
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
export const deleteSearchResults = () => {
    const parentElement = document.getElementById(lS(0, 0));
    let child = parentElement.lastElementChild;
    (() => {
        const __forInstance0 = new WebAssembly.Instance(__forWasmModule, {
            env: {
                test: () => {
                    return child ? 1 : 0;
                },
                update: () => {
                },
                body: () => {
                    {
                        (() => {
                            const __callInstance16 = new WebAssembly.Instance(__callWasmModule, {
                                env: {
                                    impFunc: () => {
                                        parentElement.removeChild(child);
                                    }
                                }
                            });
                            const __exports = __callInstance16.exports;
                            return __exports.data();
                        })();
                        child = parentElement.lastElementChild;
                    }
                }
            }
        });
        const __exports = __forInstance0.exports;
        return __exports.data();
    })();
};
export const buildSearchResults = resultArray => {
    (() => {
        const __callInstance15 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    console.log(resultArray);
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
                    resultArray.forEach(result => {
                        const resultItem = createResultItem(result);
                        const resultContents = document.createElement(lS(0, 1));
                        (() => {
                            const __callInstance13 = new WebAssembly.Instance(__callWasmModule, {
                                env: {
                                    impFunc: () => {
                                        resultContents.classList.add(lS(0, 2));
                                    }
                                }
                            });
                            const __exports = __callInstance13.exports;
                            return __exports.data();
                        })();
                        (() => {
                            const __ifInstance0 = new WebAssembly.Instance(__ifWasmModule, {
                                env: {
                                    impFunc1: () => {
                                        {
                                            const resultImage = createResultImage(result);
                                            (() => {
                                                const __callInstance12 = new WebAssembly.Instance(__callWasmModule, {
                                                    env: {
                                                        impFunc: () => {
                                                            resultContents.append(resultImage);
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
                            return __exports.data(result.img ? 1 : 0);
                        })();
                        const resultText = createResultText(result);
                        (() => {
                            const __callInstance11 = new WebAssembly.Instance(__callWasmModule, {
                                env: {
                                    impFunc: () => {
                                        resultContents.append(resultText);
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
                                        resultItem.append(resultContents);
                                    }
                                }
                            });
                            const __exports = __callInstance10.exports;
                            return __exports.data();
                        })();
                        const searchResults = document.getElementById(lS(0, 3));
                        (() => {
                            const __callInstance9 = new WebAssembly.Instance(__callWasmModule, {
                                env: {
                                    impFunc: () => {
                                        searchResults.append(resultItem);
                                    }
                                }
                            });
                            const __exports = __callInstance9.exports;
                            return __exports.data();
                        })();
                    });
                }
            }
        });
        const __exports = __callInstance14.exports;
        return __exports.data();
    })();
};
const createResultItem = result => {
    const resultItem = document.createElement(lS(0, 4));
    (() => {
        const __callInstance8 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    resultItem.classList.add(lS(0, 5));
                }
            }
        });
        const __exports = __callInstance8.exports;
        return __exports.data();
    })();
    const resultTitle = document.createElement(lS(0, 6));
    (() => {
        const __callInstance7 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    resultTitle.classList.add(lS(0, 7));
                }
            }
        });
        const __exports = __callInstance7.exports;
        return __exports.data();
    })();
    const link = document.createElement(lS(0, 8));
    link.href = `https://en.wikipedia.org/?curid=${ result.id }`;
    link.textContent = result.title;
    link.target = lS(0, 9);
    (() => {
        const __callInstance6 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    resultTitle.append(link);
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
                    resultItem.append(resultTitle);
                }
            }
        });
        const __exports = __callInstance5.exports;
        return __exports.data();
    })();
    return resultItem;
};
const createResultImage = result => {
    const resultImage = document.createElement(lS(0, 10));
    (() => {
        const __callInstance4 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    resultImage.classList.add(lS(0, 11));
                }
            }
        });
        const __exports = __callInstance4.exports;
        return __exports.data();
    })();
    const img = document.createElement(lS(0, 12));
    img.src = result.img;
    img.alt = result.title;
    (() => {
        const __callInstance3 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    resultImage.append(img);
                }
            }
        });
        const __exports = __callInstance3.exports;
        return __exports.data();
    })();
    return resultImage;
};
const createResultText = result => {
    const resultText = document.createElement(lS(0, 13));
    (() => {
        const __callInstance2 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    resultText.classList.add(lS(0, 14));
                }
            }
        });
        const __exports = __callInstance2.exports;
        return __exports.data();
    })();
    const resultDescription = document.createElement(lS(0, 15));
    (() => {
        const __callInstance1 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    resultDescription.classList.add(lS(0, 16));
                }
            }
        });
        const __exports = __callInstance1.exports;
        return __exports.data();
    })();
    resultDescription.textContent = result.text;
    (() => {
        const __callInstance0 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    resultText.append(resultDescription);
                }
            }
        });
        const __exports = __callInstance0.exports;
        return __exports.data();
    })();
    return resultText;
};
export const clearStatsLine = () => {
    document.getElementById(lS(0, 17)).textContent = lS(0, 18);
};
export const setStatsLine = numberOfResults => {
    const statsLine = document.getElementById(lS(0, 19));
    (() => {
        const __ifInstance1 = new WebAssembly.Instance(__ifWasmModule, {
            env: {
                impFunc1: () => {
                    {
                        statsLine.textContent = `Diplaying ${ numberOfResults } results.`;
                    }
                },
                impFunc2: () => {
                    {
                        statsLine.textContent = lS(0, 20);
                    }
                }
            }
        });
        const __exports = __ifInstance1.exports;
        return __exports.data(numberOfResults ? 1 : 0);
    })();
};