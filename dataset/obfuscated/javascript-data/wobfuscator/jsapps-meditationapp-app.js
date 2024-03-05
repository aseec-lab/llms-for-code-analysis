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
const __wasmStringModules = ['AGFzbQEAAAAFg4CAgAABAAEG4oCAgAARfwBBAQt/AEEIC38AQRALfwBBGgt/AEE0C38AQcwAC38AQeQAC38AQfQAC38AQYwBC38AQZQBC38AQZwBC38AQagBC38AQbQBC38AQbwBC38AQcgBC38AQdoBC38AQeoBCweZgYCAABIGbWVtb3J5AgAFZGF0YTADAAVkYXRhMQMBBWRhdGEyAwIFZGF0YTMDAwVkYXRhNAMEBWRhdGE1AwUFZGF0YTYDBgVkYXRhNwMHBWRhdGE4AwgFZGF0YTkDCQZkYXRhMTADCgZkYXRhMTEDCwZkYXRhMTIDDAZkYXRhMTMDDQZkYXRhMTQDDgZkYXRhMTUDDwZkYXRhMTYDEAvBgoCAABEAQQELBi5zb25nAABBCAsGLnBsYXkAAEEQCwgucmVwbGF5AABBGgsZLm1vdmluZy1vdXRsaW5lJTIwY2lyY2xlAABBNAsXLnZpZC1jb250YWluZXIlMjB2aWRlbwAAQcwACxcuc291bmQtcGlja2VyJTIwYnV0dG9uAABB5AALDi50aW1lLWRpc3BsYXkAAEH0AAsWLnRpbWUtc2VsZWN0JTIwYnV0dG9uAABBjAELBmNsaWNrAABBlAELBmNsaWNrAABBnAELC2RhdGEtc291bmQAAEGoAQsLZGF0YS12aWRlbwAAQbQBCwZjbGljawAAQbwBCwpkYXRhLXRpbWUAAEHIAQsQc3ZnJTJGcGF1c2Uuc3ZnAABB2gELD3N2ZyUyRnBsYXkuc3ZnAABB6gELD3N2ZyUyRnBsYXkuc3ZnAA=='].map(__bytes => {
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
const song = document.querySelector(lS(0, 0));
const play = document.querySelector(lS(0, 1));
const replay = document.querySelector(lS(0, 2));
const outline = document.querySelector(lS(0, 3));
const video = document.querySelector(lS(0, 4));
const sounds = document.querySelectorAll(lS(0, 5));
const timeDisplay = document.querySelector(lS(0, 6));
const outlineLength = outline.getTotalLength();
const timeSelect = document.querySelectorAll(lS(0, 7));
let fakeDuration = 600;
outline.style.strokeDasharray = outlineLength;
outline.style.strokeDashoffset = outlineLength;
(() => {
    const __callInstance12 = new WebAssembly.Instance(__callWasmModule, {
        env: {
            impFunc: () => {
                play.addEventListener(lS(0, 8), function () {
                    (() => {
                        const __callInstance11 = new WebAssembly.Instance(__callWasmModule, {
                            env: {
                                impFunc: () => {
                                    checkPlaying(song);
                                }
                            }
                        });
                        const __exports = __callInstance11.exports;
                        return __exports.data();
                    })();
                });
            }
        }
    });
    const __exports = __callInstance12.exports;
    return __exports.data();
})();
(() => {
    const __callInstance10 = new WebAssembly.Instance(__callWasmModule, {
        env: {
            impFunc: () => {
                sounds.forEach(sounds => {
                    (() => {
                        const __callInstance9 = new WebAssembly.Instance(__callWasmModule, {
                            env: {
                                impFunc: () => {
                                    sounds.addEventListener(lS(0, 9), function () {
                                        song.src = this.getAttribute(lS(0, 10));
                                        video.src = this.getAttribute(lS(0, 11));
                                        (() => {
                                            const __callInstance8 = new WebAssembly.Instance(__callWasmModule, {
                                                env: {
                                                    impFunc: () => {
                                                        checkPlaying(song);
                                                    }
                                                }
                                            });
                                            const __exports = __callInstance8.exports;
                                            return __exports.data();
                                        })();
                                    });
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
    const __exports = __callInstance10.exports;
    return __exports.data();
})();
(() => {
    const __callInstance7 = new WebAssembly.Instance(__callWasmModule, {
        env: {
            impFunc: () => {
                timeSelect.forEach(option => {
                    (() => {
                        const __callInstance6 = new WebAssembly.Instance(__callWasmModule, {
                            env: {
                                impFunc: () => {
                                    option.addEventListener(lS(0, 12), function () {
                                        fakeDuration = this.getAttribute(lS(0, 13));
                                        timeDisplay.textContent = `${ Math.floor(fakeDuration / 60) }:${ Math.floor(fakeDuration % 60) }`;
                                    });
                                }
                            }
                        });
                        const __exports = __callInstance6.exports;
                        return __exports.data();
                    })();
                });
            }
        }
    });
    const __exports = __callInstance7.exports;
    return __exports.data();
})();
const checkPlaying = song => {
    (() => {
        const __ifInstance0 = new WebAssembly.Instance(__ifWasmModule, {
            env: {
                impFunc1: () => {
                    {
                        (() => {
                            const __callInstance5 = new WebAssembly.Instance(__callWasmModule, {
                                env: {
                                    impFunc: () => {
                                        song.play();
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
                                        video.play();
                                    }
                                }
                            });
                            const __exports = __callInstance4.exports;
                            return __exports.data();
                        })();
                        play.src = lS(0, 14);
                    }
                },
                impFunc2: () => {
                    {
                        (() => {
                            const __callInstance3 = new WebAssembly.Instance(__callWasmModule, {
                                env: {
                                    impFunc: () => {
                                        song.pause();
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
                                        video.pause();
                                    }
                                }
                            });
                            const __exports = __callInstance2.exports;
                            return __exports.data();
                        })();
                        play.src = lS(0, 15);
                    }
                }
            }
        });
        const __exports = __ifInstance0.exports;
        return __exports.data(song.paused ? 1 : 0);
    })();
};
song.ontimeupdate = () => {
    let currentTime = song.currentTime;
    let elapsedTime = fakeDuration - currentTime;
    let seconds = Math.floor(elapsedTime % 60);
    let minutes = Math.floor(elapsedTime / 60);
    let progress = outlineLength - currentTime / fakeDuration * outlineLength;
    outline.style.strokeDashoffset = progress;
    timeDisplay.textContent = `${ minutes }:${ seconds }`;
    (() => {
        const __ifInstance1 = new WebAssembly.Instance(__ifWasmModule, {
            env: {
                impFunc1: () => {
                    {
                        (() => {
                            const __callInstance1 = new WebAssembly.Instance(__callWasmModule, {
                                env: {
                                    impFunc: () => {
                                        song.pause();
                                    }
                                }
                            });
                            const __exports = __callInstance1.exports;
                            return __exports.data();
                        })();
                        song.currentTime = 0;
                        song.src = lS(0, 16);
                        (() => {
                            const __callInstance0 = new WebAssembly.Instance(__callWasmModule, {
                                env: {
                                    impFunc: () => {
                                        video.pause();
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
        return __exports.data(currentTime > fakeDuration ? 1 : 0);
    })();
};