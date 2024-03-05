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
const __wasmStringModules = ['AGFzbQEAAAAFg4CAgAABAAEGyICAgAANfwBBAQt/AEEQC38AQSYLfwBBLAt/AEEuC38AQTQLfwBBPAt/AEHKAAt/AEHcAAt/AEHkAAt/AEHqAAt/AEH6AAt/AEGKAQsH9YCAgAAOBm1lbW9yeQIABWRhdGEwAwAFZGF0YTEDAQVkYXRhMgMCBWRhdGEzAwMFZGF0YTQDBAVkYXRhNQMFBWRhdGE2AwYFZGF0YTcDBwVkYXRhOAMIBWRhdGE5AwkGZGF0YTEwAwoGZGF0YTExAwsGZGF0YTEyAwwLyIGAgAANAEEBCw4lMjNmb3JtdWxhcmlvAABBEAsUJTIzbGlzdGFBY3RpdmlkYWRlcwAAQSYLBXRhc2sAAEEsCwEAAEEuCwV0YXNrAABBNAsHc3VibWl0AABBPAsNJTIzYWN0aXZpZGFkAABBygALEURPTUNvbnRlbnRMb2FkZWQAAEHcAAsGY2xpY2sAAEHkAAsFZG9uZQAAQeoACw9kZWxldGVfZm9yZXZlcgAAQfoACw9kZWxldGVfZm9yZXZlcgAAQYoBCwVkb25lAA=='].map(__bytes => {
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
const formularioUI = document.querySelector(lS(0, 0));
const listaActividadesUI = document.querySelector(lS(0, 1));
let arrayActividades = [];
const CrearTask = actividad => {
    let item = {
        actividad: actividad,
        estado: false
    };
    (() => {
        const __callInstance17 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    arrayActividades.push(item);
                }
            }
        });
        const __exports = __callInstance17.exports;
        return __exports.data();
    })();
    return item;
};
const GuardarDB = () => {
    (() => {
        const __callInstance16 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    localStorage.setItem(lS(0, 2), JSON.stringify(arrayActividades));
                }
            }
        });
        const __exports = __callInstance16.exports;
        return __exports.data();
    })();
    (() => {
        const __callInstance15 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    PintarDB();
                }
            }
        });
        const __exports = __callInstance15.exports;
        return __exports.data();
    })();
};
const PintarDB = () => {
    listaActividadesUI.innerHTML = lS(0, 3);
    arrayActividades = JSON.parse(localStorage.getItem(lS(0, 4)));
    (() => {
        const __ifInstance0 = new WebAssembly.Instance(__ifWasmModule, {
            env: {
                impFunc1: () => {
                    {
                        arrayActividades = [];
                    }
                },
                impFunc2: () => {
                    {
                        (() => {
                            const __callInstance14 = new WebAssembly.Instance(__callWasmModule, {
                                env: {
                                    impFunc: () => {
                                        arrayActividades.forEach(element => {
                                            (() => {
                                                const __ifInstance1 = new WebAssembly.Instance(__ifWasmModule, {
                                                    env: {
                                                        impFunc1: () => {
                                                            {
                                                                listaActividadesUI.innerHTML += `<div class="alert alert-success" role="alert"><i class="material-icons float-left">keyboard_arrow_right</i><span class="font-weight-bold">${ element.actividad }</span> - ${ element.estado }<span class="float-right"><i class="material-icons">done</i><i class="material-icons">delete_forever</i></span></div>`;
                                                            }
                                                        },
                                                        impFunc2: () => {
                                                            {
                                                                listaActividadesUI.innerHTML += `<div class="alert alert-warning" role="alert"><i class="material-icons float-left">keyboard_arrow_right</i><span class="font-weight-bold">${ element.actividad }</span> - ${ element.estado }<span class="float-right"><i class="material-icons">done</i><i class="material-icons">delete_forever</i></span></div>`;
                                                            }
                                                        }
                                                    }
                                                });
                                                const __exports = __ifInstance1.exports;
                                                return __exports.data(element.estado == true ? 1 : 0);
                                            })();
                                        });
                                    }
                                }
                            });
                            const __exports = __callInstance14.exports;
                            return __exports.data();
                        })();
                    }
                }
            }
        });
        const __exports = __ifInstance0.exports;
        return __exports.data(arrayActividades === null ? 1 : 0);
    })();
};
const EditarDB = actividad => {
    let indexArray = arrayActividades.findIndex(element => {
        return element.actividad === actividad;
    });
    arrayActividades[indexArray].estado = true;
    (() => {
        const __callInstance13 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    GuardarDB();
                }
            }
        });
        const __exports = __callInstance13.exports;
        return __exports.data();
    })();
};
const EliminarDB = actividad => {
    let indexArray;
    (() => {
        const __callInstance12 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    arrayActividades.forEach((element, index) => {
                        (() => {
                            const __ifInstance2 = new WebAssembly.Instance(__ifWasmModule, {
                                env: {
                                    impFunc1: () => {
                                        {
                                            indexArray = index;
                                        }
                                    },
                                    impFunc2: () => {
                                    }
                                }
                            });
                            const __exports = __ifInstance2.exports;
                            return __exports.data(element.actividad === actividad ? 1 : 0);
                        })();
                    });
                }
            }
        });
        const __exports = __callInstance12.exports;
        return __exports.data();
    })();
    (() => {
        const __callInstance11 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    arrayActividades.splice(indexArray, 1);
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
                    GuardarDB();
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
                formularioUI.addEventListener(lS(0, 5), e => {
                    (() => {
                        const __callInstance8 = new WebAssembly.Instance(__callWasmModule, {
                            env: {
                                impFunc: () => {
                                    e.preventDefault();
                                }
                            }
                        });
                        const __exports = __callInstance8.exports;
                        return __exports.data();
                    })();
                    let actividadUI = document.querySelector(lS(0, 6)).value;
                    (() => {
                        const __callInstance7 = new WebAssembly.Instance(__callWasmModule, {
                            env: {
                                impFunc: () => {
                                    CrearTask(actividadUI);
                                }
                            }
                        });
                        const __exports = __callInstance7.exports;
                        return __exports.data();
                    })();
                    (() => {
                        const __callInstance6 = new WebAssembly.Instance(__callWasmModule, {
                            env: {
                                impFunc: () => {
                                    GuardarDB();
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
                                    formularioUI.reset();
                                }
                            }
                        });
                        const __exports = __callInstance5.exports;
                        return __exports.data();
                    })();
                });
            }
        }
    });
    const __exports = __callInstance9.exports;
    return __exports.data();
})();
(() => {
    const __callInstance4 = new WebAssembly.Instance(__callWasmModule, {
        env: {
            impFunc: () => {
                document.addEventListener(lS(0, 7), PintarDB);
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
                listaActividadesUI.addEventListener(lS(0, 8), e => {
                    (() => {
                        const __callInstance2 = new WebAssembly.Instance(__callWasmModule, {
                            env: {
                                impFunc: () => {
                                    e.preventDefault();
                                }
                            }
                        });
                        const __exports = __callInstance2.exports;
                        return __exports.data();
                    })();
                    (() => {
                        const __ifInstance3 = new WebAssembly.Instance(__ifWasmModule, {
                            env: {
                                impFunc1: () => {
                                    {
                                        let text = e.path[2].childNodes[1].innerHTML;
                                        (() => {
                                            const __ifInstance4 = new WebAssembly.Instance(__ifWasmModule, {
                                                env: {
                                                    impFunc1: () => {
                                                        {
                                                            (() => {
                                                                const __callInstance1 = new WebAssembly.Instance(__callWasmModule, {
                                                                    env: {
                                                                        impFunc: () => {
                                                                            EliminarDB(text);
                                                                        }
                                                                    }
                                                                });
                                                                const __exports = __callInstance1.exports;
                                                                return __exports.data();
                                                            })();
                                                        }
                                                    },
                                                    impFunc2: () => {
                                                    }
                                                }
                                            });
                                            const __exports = __ifInstance4.exports;
                                            return __exports.data(e.target.innerHTML === lS(0, 11) ? 1 : 0);
                                        })();
                                        (() => {
                                            const __ifInstance5 = new WebAssembly.Instance(__ifWasmModule, {
                                                env: {
                                                    impFunc1: () => {
                                                        {
                                                            (() => {
                                                                const __callInstance0 = new WebAssembly.Instance(__callWasmModule, {
                                                                    env: {
                                                                        impFunc: () => {
                                                                            EditarDB(text);
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
                                            const __exports = __ifInstance5.exports;
                                            return __exports.data(e.target.innerHTML === lS(0, 12) ? 1 : 0);
                                        })();
                                    }
                                },
                                impFunc2: () => {
                                }
                            }
                        });
                        const __exports = __ifInstance3.exports;
                        return __exports.data(e.target.innerHTML === lS(0, 9) || e.target.innerHTML === lS(0, 10) ? 1 : 0);
                    })();
                });
            }
        }
    });
    const __exports = __callInstance3.exports;
    return __exports.data();
})();