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
const __wasmStringModules = ['AGFzbQEAAAAFg4CAgAABAAEGtoCAgAAKfwBBAQt/AEEIC38AQRILfwBBHgt/AEEmC38AQS4LfwBBNgt/AEHAAAt/AEHGAAt/AEHUAAsH2oCAgAALBm1lbW9yeQIABWRhdGEwAwAFZGF0YTEDAQVkYXRhMgMCBWRhdGEzAwMFZGF0YTQDBAVkYXRhNQMFBWRhdGE2AwYFZGF0YTcDBwVkYXRhOAMIBWRhdGE5AwkL+4CAgAAKAEEBCwUlM0FEAABBCAsJJTIzZG9sYXIAAEESCwslMjNiaXRjb2luAABBHgsGY2xpY2sAAEEmCwZkb2xhcgAAQS4LBmNsaWNrAABBNgsIYml0Y29pbgAAQcAACwRHRVQAAEHGAAsNJTIzcmVzdWx0YWRvAABB1AALAQA='].map(__bytes => {
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
    const __callInstance7 = new WebAssembly.Instance(__callWasmModule, {
        env: {
            impFunc: () => {
                console.log(lS(0, 0));
            }
        }
    });
    const __exports = __callInstance7.exports;
    return __exports.data();
})();
let botonDolar = document.querySelector(lS(0, 1));
let botonBitcoin = document.querySelector(lS(0, 2));
(() => {
    const __callInstance6 = new WebAssembly.Instance(__callWasmModule, {
        env: {
            impFunc: () => {
                botonDolar.addEventListener(lS(0, 3), function () {
                    (() => {
                        const __callInstance5 = new WebAssembly.Instance(__callWasmModule, {
                            env: {
                                impFunc: () => {
                                    obtenerDatos(lS(0, 4));
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
    const __exports = __callInstance6.exports;
    return __exports.data();
})();
(() => {
    const __callInstance4 = new WebAssembly.Instance(__callWasmModule, {
        env: {
            impFunc: () => {
                botonBitcoin.addEventListener(lS(0, 5), function () {
                    (() => {
                        const __callInstance3 = new WebAssembly.Instance(__callWasmModule, {
                            env: {
                                impFunc: () => {
                                    obtenerDatos(lS(0, 6));
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
function obtenerDatos(valor) {
    let url = `https://mindicador.cl/api/${ valor }`;
    const api = new XMLHttpRequest();
    (() => {
        const __callInstance2 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    api.open(lS(0, 7), url, true);
                }
            }
        });
        const __exports = __callInstance2.exports;
        return __exports.data();
    })();
    (() => {
        const __callInstance1 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    api.send();
                }
            }
        });
        const __exports = __callInstance1.exports;
        return __exports.data();
    })();
    api.onreadystatechange = respuesta;
}
function respuesta() {
    if (this.status == 200 && this.readyState == 4) {
        let datos = JSON.parse(this.responseText);
        (() => {
            const __callInstance0 = new WebAssembly.Instance(__callWasmModule, {
                env: {
                    impFunc: () => {
                        console.log(datos);
                    }
                }
            });
            const __exports = __callInstance0.exports;
            return __exports.data();
        })();
        let resultado = document.querySelector(lS(0, 8));
        resultado.innerHTML = lS(0, 9);
        let i = 0;
        for (let item of datos.serie) {
            i++;
            resultado.innerHTML += `<li>Fecha: ${ item.fecha.substring(0, 10) } - $${ item.valor }</li>`;
            if (i > 15) {
                break;
            }
        }
    }
}