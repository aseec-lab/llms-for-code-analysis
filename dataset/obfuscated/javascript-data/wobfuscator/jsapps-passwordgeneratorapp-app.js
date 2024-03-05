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
const __wasmStringModules = ['AGFzbQEAAAAFg4CAgAABAAEGwYGAgAAhfwBBAQt/AEEKC38AQSALfwBBKAt/AEE2C38AQT4LfwBBzgALfwBB1gALfwBB5AALfwBB7AALfwBB+gALfwBBiAELfwBBkAELfwBBnAELfwBBrAELfwBBtAELfwBBxAELfwBBzAELfwBB0gELfwBB4AELfwBB6AELfwBB6gELfwBB7AELfwBB8gELfwBB+AELfwBBiAILfwBBmAILfwBBoAILfwBBsAILfwBBtgILfwBByAILfwBB0AILfwBB4gILB6mCgIAAIgZtZW1vcnkCAAVkYXRhMAMABWRhdGExAwEFZGF0YTIDAgVkYXRhMwMDBWRhdGE0AwQFZGF0YTUDBQVkYXRhNgMGBWRhdGE3AwcFZGF0YTgDCAVkYXRhOQMJBmRhdGExMAMKBmRhdGExMQMLBmRhdGExMgMMBmRhdGExMwMNBmRhdGExNAMOBmRhdGExNQMPBmRhdGExNgMQBmRhdGExNwMRBmRhdGExOAMSBmRhdGExOQMTBmRhdGEyMAMUBmRhdGEyMQMVBmRhdGEyMgMWBmRhdGEyMwMXBmRhdGEyNAMYBmRhdGEyNQMZBmRhdGEyNgMaBmRhdGEyNwMbBmRhdGEyOAMcBmRhdGEyOQMdBmRhdGEzMAMeBmRhdGEzMQMfBmRhdGEzMgMgC/WDgIAAIQBBAQsHJTIzYXBwAABBCgsVJTIzbnVtZXJvLWNhcmFjdGVyZXMAAEEgCwdzdWJtaXQAAEEoCwxidG4tbWFzLXVubwAAQTYLBmNsaWNrAABBPgsOYnRuLW1lbm9zLXVubwAAQc4ACwZjbGljawAAQdYACw1idG4tc2ltYm9sb3MAAEHkAAsGY2xpY2sAAEHsAAsMc2ltYm9sb3MlMjAAAEH6AAsMYnRuLW51bWVyb3MAAEGIAQsGY2xpY2sAAEGQAQsLbnVtZXJvcyUyMAAAQZwBCw5idG4tbWF5dXNjdWxhAABBrAELBmNsaWNrAABBtAELDm1heXVzY3VsYXMlMjAAAEHEAQsGZmFsc2UAAEHMAQsFbm9uZQAAQdIBCwxidG4tZ2VuZXJhcgAAQeABCwZjbGljawAAQegBCwEAAEHqAQsBAABB7AELBCUyMAAAQfIBCwQlMjAAAEH4AQsPaW5wdXQtcGFzc3dvcmQAAEGIAgsPaW5wdXQtcGFzc3dvcmQAAEGYAgsGY2xpY2sAAEGgAgsPaW5wdXQtcGFzc3dvcmQAAEGwAgsFY29weQAAQbYCCxAuYWxlcnRhLWNvcGlhZG8AAEHIAgsHYWN0aXZlAABB0AILEC5hbGVydGEtY29waWFkbwAAQeICCwdhY3RpdmUA'].map(__bytes => {
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
(() => {
    const __callInstance25 = new WebAssembly.Instance(__callWasmModule, {
        env: {
            impFunc: () => {
                (function () {
                    var app = document.querySelector(lS(0, 0));
                    var inputCaracteres = document.querySelector(lS(0, 1));
                    var configuracion = {
                        caracteres: parseInt(inputCaracteres.value),
                        simbolos: true,
                        numeros: true,
                        mayusculas: true,
                        minusculas: true
                    };
                    var caracteres = {
                        numeros: '0 1 2 3 4 5 6 7 8 9',
                        simbolos: '~ ! @ # $ % ^ & * ( ) _ + - = [ { } ] ; : , < . ? /',
                        mayusculas: 'A B C D E F G H I J K L M N O P Q R S T U V W X Y Z',
                        minusculas: 'a b c d e f g h i j k l m n o p q r s t u v w x y z'
                    };
                    (() => {
                        const __callInstance24 = new WebAssembly.Instance(__callWasmModule, {
                            env: {
                                impFunc: () => {
                                    app.addEventListener(lS(0, 2), function (e) {
                                        (() => {
                                            const __callInstance23 = new WebAssembly.Instance(__callWasmModule, {
                                                env: {
                                                    impFunc: () => {
                                                        e.preventDefault();
                                                    }
                                                }
                                            });
                                            const __exports = __callInstance23.exports;
                                            return __exports.data();
                                        })();
                                    });
                                }
                            }
                        });
                        const __exports = __callInstance24.exports;
                        return __exports.data();
                    })();
                    (() => {
                        const __callInstance22 = new WebAssembly.Instance(__callWasmModule, {
                            env: {
                                impFunc: () => {
                                    app.elements.namedItem(lS(0, 3)).addEventListener(lS(0, 4), () => {
                                        configuracion.caracteres++;
                                        inputCaracteres.value = configuracion.caracteres;
                                    });
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
                                    app.elements.namedItem(lS(0, 5)).addEventListener(lS(0, 6), () => {
                                        (() => {
                                            const __ifInstance0 = new WebAssembly.Instance(__ifWasmModule, {
                                                env: {
                                                    impFunc1: () => {
                                                        {
                                                            configuracion.caracteres--;
                                                            inputCaracteres.value = configuracion.caracteres;
                                                        }
                                                    },
                                                    impFunc2: () => {
                                                    }
                                                }
                                            });
                                            const __exports = __ifInstance0.exports;
                                            return __exports.data(configuracion.caracteres > 1 ? 1 : 0);
                                        })();
                                    });
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
                                    app.elements.namedItem(lS(0, 7)).addEventListener(lS(0, 8), function () {
                                        (() => {
                                            const __callInstance19 = new WebAssembly.Instance(__callWasmModule, {
                                                env: {
                                                    impFunc: () => {
                                                        btnToggle(this);
                                                    }
                                                }
                                            });
                                            const __exports = __callInstance19.exports;
                                            return __exports.data();
                                        })();
                                        configuracion.simbolos = !configuracion.simbolos;
                                        (() => {
                                            const __callInstance18 = new WebAssembly.Instance(__callWasmModule, {
                                                env: {
                                                    impFunc: () => {
                                                        console.log(lS(0, 9) + configuracion.simbolos);
                                                    }
                                                }
                                            });
                                            const __exports = __callInstance18.exports;
                                            return __exports.data();
                                        })();
                                    });
                                }
                            }
                        });
                        const __exports = __callInstance20.exports;
                        return __exports.data();
                    })();
                    (() => {
                        const __callInstance17 = new WebAssembly.Instance(__callWasmModule, {
                            env: {
                                impFunc: () => {
                                    app.elements.namedItem(lS(0, 10)).addEventListener(lS(0, 11), function () {
                                        (() => {
                                            const __callInstance16 = new WebAssembly.Instance(__callWasmModule, {
                                                env: {
                                                    impFunc: () => {
                                                        btnToggle(this);
                                                    }
                                                }
                                            });
                                            const __exports = __callInstance16.exports;
                                            return __exports.data();
                                        })();
                                        configuracion.numeros = !configuracion.numeros;
                                        (() => {
                                            const __callInstance15 = new WebAssembly.Instance(__callWasmModule, {
                                                env: {
                                                    impFunc: () => {
                                                        console.log(lS(0, 12) + configuracion.numeros);
                                                    }
                                                }
                                            });
                                            const __exports = __callInstance15.exports;
                                            return __exports.data();
                                        })();
                                    });
                                }
                            }
                        });
                        const __exports = __callInstance17.exports;
                        return __exports.data();
                    })();
                    (() => {
                        const __callInstance14 = new WebAssembly.Instance(__callWasmModule, {
                            env: {
                                impFunc: () => {
                                    app.elements.namedItem(lS(0, 13)).addEventListener(lS(0, 14), function () {
                                        (() => {
                                            const __callInstance13 = new WebAssembly.Instance(__callWasmModule, {
                                                env: {
                                                    impFunc: () => {
                                                        btnToggle(this);
                                                    }
                                                }
                                            });
                                            const __exports = __callInstance13.exports;
                                            return __exports.data();
                                        })();
                                        configuracion.mayusculas = !configuracion.mayusculas;
                                        (() => {
                                            const __callInstance12 = new WebAssembly.Instance(__callWasmModule, {
                                                env: {
                                                    impFunc: () => {
                                                        console.log(lS(0, 15) + configuracion.mayusculas);
                                                    }
                                                }
                                            });
                                            const __exports = __callInstance12.exports;
                                            return __exports.data();
                                        })();
                                    });
                                }
                            }
                        });
                        const __exports = __callInstance14.exports;
                        return __exports.data();
                    })();
                    let btnToggle = elemento => {
                        (() => {
                            const __callInstance11 = new WebAssembly.Instance(__callWasmModule, {
                                env: {
                                    impFunc: () => {
                                        elemento.classList.toggle(lS(0, 16));
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
                                        elemento.childNodes[0].nextElementSibling.classList.toggle(lS(0, 17));
                                    }
                                }
                            });
                            const __exports = __callInstance10.exports;
                            return __exports.data();
                        })();
                    };
                    (() => {
                        const __callInstance9 = new WebAssembly.Instance(__callWasmModule, {
                            env: {
                                impFunc: () => {
                                    app.elements.namedItem(lS(0, 18)).addEventListener(lS(0, 19), function () {
                                        (() => {
                                            const __callInstance8 = new WebAssembly.Instance(__callWasmModule, {
                                                env: {
                                                    impFunc: () => {
                                                        generatePassword();
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
                    let generatePassword = () => {
                        let caracteresFinales = lS(0, 20);
                        let password = lS(0, 21);
                        for (propiedad in configuracion) {
                            (() => {
                                const __ifInstance1 = new WebAssembly.Instance(__ifWasmModule, {
                                    env: {
                                        impFunc1: () => {
                                            {
                                                caracteresFinales += caracteres[propiedad] + lS(0, 22);
                                            }
                                        },
                                        impFunc2: () => {
                                        }
                                    }
                                });
                                const __exports = __ifInstance1.exports;
                                return __exports.data(configuracion[propiedad] == true ? 1 : 0);
                            })();
                        }
                        caracteresFinales = caracteresFinales.trim();
                        caracteresFinales = caracteresFinales.split(lS(0, 23));
                        (() => {
                            var i = 0;
                            const __forInstance0 = new WebAssembly.Instance(__forWasmModule, {
                                env: {
                                    test: () => {
                                        return i < configuracion.caracteres ? 1 : 0;
                                    },
                                    update: () => {
                                        i++;
                                    },
                                    body: () => {
                                        {
                                            password += caracteresFinales[Math.floor(Math.random() * caracteresFinales.length)];
                                        }
                                    }
                                }
                            });
                            const __exports = __forInstance0.exports;
                            return __exports.data();
                        })();
                        app.elements.namedItem(lS(0, 24)).value = password;
                    };
                    (() => {
                        const __callInstance7 = new WebAssembly.Instance(__callWasmModule, {
                            env: {
                                impFunc: () => {
                                    app.elements.namedItem(lS(0, 25)).addEventListener(lS(0, 26), () => {
                                        (() => {
                                            const __callInstance6 = new WebAssembly.Instance(__callWasmModule, {
                                                env: {
                                                    impFunc: () => {
                                                        copyPassword();
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
                    let copyPassword = () => {
                        (() => {
                            const __callInstance5 = new WebAssembly.Instance(__callWasmModule, {
                                env: {
                                    impFunc: () => {
                                        app.elements.namedItem(lS(0, 27)).select();
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
                                        document.execCommand(lS(0, 28));
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
                                        document.querySelector(lS(0, 29)).classList.add(lS(0, 30));
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
                                        setTimeout(function () {
                                            (() => {
                                                const __callInstance1 = new WebAssembly.Instance(__callWasmModule, {
                                                    env: {
                                                        impFunc: () => {
                                                            document.querySelector(lS(0, 31)).classList.remove(lS(0, 32));
                                                        }
                                                    }
                                                });
                                                const __exports = __callInstance1.exports;
                                                return __exports.data();
                                            })();
                                        }, 2000);
                                    }
                                }
                            });
                            const __exports = __callInstance2.exports;
                            return __exports.data();
                        })();
                    };
                    (() => {
                        const __callInstance0 = new WebAssembly.Instance(__callWasmModule, {
                            env: {
                                impFunc: () => {
                                    generatePassword();
                                }
                            }
                        });
                        const __exports = __callInstance0.exports;
                        return __exports.data();
                    })();
                }());
            }
        }
    });
    const __exports = __callInstance25.exports;
    return __exports.data();
})();