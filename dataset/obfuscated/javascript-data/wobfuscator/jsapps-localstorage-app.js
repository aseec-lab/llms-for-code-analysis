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
const __wasmStringModules = ['AGFzbQEAAAAFg4CAgAABAAEGsIGAgAAefwBBAQt/AEEIC38AQRgLfwBBKAt/AEE4C38AQcQAC38AQc4AC38AQdoAC38AQeQAC38AQewAC38AQfQAC38AQYIBC38AQY4BC38AQZoBC38AQaYBC38AQbYBC38AQcQBC38AQdABC38AQd4BC38AQeoBC38AQfYBC38AQYICC38AQY4CC38AQZwCC38AQagCC38AQbQCC38AQcACC38AQc4CC38AQdoCC38AQeYCCweOgoCAAB8GbWVtb3J5AgAFZGF0YTADAAVkYXRhMQMBBWRhdGEyAwIFZGF0YTMDAwVkYXRhNAMEBWRhdGE1AwUFZGF0YTYDBgVkYXRhNwMHBWRhdGE4AwgFZGF0YTkDCQZkYXRhMTADCgZkYXRhMTEDCwZkYXRhMTIDDAZkYXRhMTMDDQZkYXRhMTQDDgZkYXRhMTUDDwZkYXRhMTYDEAZkYXRhMTcDEQZkYXRhMTgDEgZkYXRhMTkDEwZkYXRhMjADFAZkYXRhMjEDFQZkYXRhMjIDFgZkYXRhMjMDFwZkYXRhMjQDGAZkYXRhMjUDGQZkYXRhMjYDGgZkYXRhMjcDGwZkYXRhMjgDHAZkYXRhMjkDHQv3g4CAAB4AQQELBmphaW1lAABBCAsObm9tYnJlVXN1YXJpbwAAQRgLDm5vbWJyZVVzdWFyaW8AAEEoCw4lMjNidG5Cb3RvbmVzAABBOAsLJTIzYm90b25lcwAAQcQACwklMjNmb25kbwAAQc4ACwtjb2xvckZvbmRvAABB2gALCGJnLWRhcmsAAEHkAAsGY2xpY2sAAEHsAAsGY2xpY2sAAEH0AAsMYnRuLXByaW1hcnkAAEGCAQsLYmctcHJpbWFyeQAAQY4BCwtjb2xvckZvbmRvAABBmgELC2JnLXByaW1hcnkAAEGmAQsOYnRuLXNlY29uZGFyeQAAQbYBCw1iZy1zZWNvbmRhcnkAAEHEAQsLY29sb3JGb25kbwAAQdABCw1iZy1zZWNvbmRhcnkAAEHeAQsLYnRuLWRhbmdlcgAAQeoBCwpiZy1kYW5nZXIAAEH2AQsLY29sb3JGb25kbwAAQYICCwpiZy1kYW5nZXIAAEGOAgsMYnRuLXN1Y2Nlc3MAAEGcAgsLYmctc3VjY2VzcwAAQagCCwtjb2xvckZvbmRvAABBtAILC2JnLXN1Y2Nlc3MAAEHAAgsMYnRuLXdhcm5pbmcAAEHOAgsLYmctd2FybmluZwAAQdoCCwtjb2xvckZvbmRvAABB5gILC2JnLXdhcm5pbmcA'].map(__bytes => {
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
const nombre = lS(0, 0);
(() => {
    const __callInstance14 = new WebAssembly.Instance(__callWasmModule, {
        env: {
            impFunc: () => {
                localStorage.setItem(lS(0, 1), nombre);
            }
        }
    });
    const __exports = __callInstance14.exports;
    return __exports.data();
})();
const nombreLocalStorage = localStorage.getItem(lS(0, 2));
(() => {
    const __callInstance13 = new WebAssembly.Instance(__callWasmModule, {
        env: {
            impFunc: () => {
                console.log(nombreLocalStorage);
            }
        }
    });
    const __exports = __callInstance13.exports;
    return __exports.data();
})();
const btnBotones = document.querySelector(lS(0, 3));
const botones = document.querySelector(lS(0, 4));
const fondo = document.querySelector(lS(0, 5));
(() => {
    const __callInstance12 = new WebAssembly.Instance(__callWasmModule, {
        env: {
            impFunc: () => {
                (() => {
                    const colorBG = localStorage.getItem(lS(0, 6));
                    (() => {
                        const __callInstance11 = new WebAssembly.Instance(__callWasmModule, {
                            env: {
                                impFunc: () => {
                                    console.log(colorBG);
                                }
                            }
                        });
                        const __exports = __callInstance11.exports;
                        return __exports.data();
                    })();
                    (() => {
                        const __ifInstance0 = new WebAssembly.Instance(__ifWasmModule, {
                            env: {
                                impFunc1: () => {
                                    {
                                        fondo.className = lS(0, 7);
                                    }
                                },
                                impFunc2: () => {
                                    {
                                        fondo.className = colorBG;
                                    }
                                }
                            }
                        });
                        const __exports = __ifInstance0.exports;
                        return __exports.data(colorBG === null ? 1 : 0);
                    })();
                })();
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
                (() => {
                    (() => {
                        const __callInstance9 = new WebAssembly.Instance(__callWasmModule, {
                            env: {
                                impFunc: () => {
                                    btnBotones.addEventListener(lS(0, 8), agregarBotones);
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
                                    botones.addEventListener(lS(0, 9), delegacion);
                                }
                            }
                        });
                        const __exports = __callInstance8.exports;
                        return __exports.data();
                    })();
                })();
            }
        }
    });
    const __exports = __callInstance10.exports;
    return __exports.data();
})();
function agregarBotones(e) {
    (() => {
        const __callInstance7 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    e.preventDefault();
                }
            }
        });
        const __exports = __callInstance7.exports;
        return __exports.data();
    })();
    botones.innerHTML = `
		<button class="btn btn-primary">primary</button>
		<button class="btn btn-secondary">secondary</button>
		<button class="btn btn-danger">danger</button>
		<button class="btn btn-success">success</button>
		<button class="btn btn-warning">warning</button>
	`;
}
function delegacion(e) {
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
                    console.log(e.target.classList[1]);
                }
            }
        });
        const __exports = __callInstance5.exports;
        return __exports.data();
    })();
    const colorBoton = e.target.classList[1];
    switch (colorBoton) {
    case lS(0, 10):
        fondo.className = lS(0, 11);
        (() => {
            const __callInstance4 = new WebAssembly.Instance(__callWasmModule, {
                env: {
                    impFunc: () => {
                        localStorage.setItem(lS(0, 12), lS(0, 13));
                    }
                }
            });
            const __exports = __callInstance4.exports;
            return __exports.data();
        })();
        break;
    case lS(0, 14):
        fondo.className = lS(0, 15);
        (() => {
            const __callInstance3 = new WebAssembly.Instance(__callWasmModule, {
                env: {
                    impFunc: () => {
                        localStorage.setItem(lS(0, 16), lS(0, 17));
                    }
                }
            });
            const __exports = __callInstance3.exports;
            return __exports.data();
        })();
        break;
    case lS(0, 18):
        fondo.className = lS(0, 19);
        (() => {
            const __callInstance2 = new WebAssembly.Instance(__callWasmModule, {
                env: {
                    impFunc: () => {
                        localStorage.setItem(lS(0, 20), lS(0, 21));
                    }
                }
            });
            const __exports = __callInstance2.exports;
            return __exports.data();
        })();
        break;
    case lS(0, 22):
        fondo.className = lS(0, 23);
        (() => {
            const __callInstance1 = new WebAssembly.Instance(__callWasmModule, {
                env: {
                    impFunc: () => {
                        localStorage.setItem(lS(0, 24), lS(0, 25));
                    }
                }
            });
            const __exports = __callInstance1.exports;
            return __exports.data();
        })();
        break;
    case lS(0, 26):
        fondo.className = lS(0, 27);
        (() => {
            const __callInstance0 = new WebAssembly.Instance(__callWasmModule, {
                env: {
                    impFunc: () => {
                        localStorage.setItem(lS(0, 28), lS(0, 29));
                    }
                }
            });
            const __exports = __callInstance0.exports;
            return __exports.data();
        })();
        break;
    }
}