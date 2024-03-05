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
const __wasmStringModules = ['AGFzbQEAAAAFg4CAgAABAAEGpICAgAAHfwBBAQt/AEEQC38AQRoLfwBBKAt/AEEqC38AQSwLfwBBNAsHwoCAgAAIBm1lbW9yeQIABWRhdGEwAwAFZGF0YTEDAQVkYXRhMgMCBWRhdGEzAwMFZGF0YTQDBAVkYXRhNQMFBWRhdGE2AwYL1oCAgAAHAEEBCw4lMjNmb3JtdWxhcmlvAABBEAsJJTIzYm90b24AAEEaCw0lMjNyZXN1bHRhZG8AAEEoCwEAAEEqCwEAAEEsCwZjbGljawAAQTQLBmtleXVwAA=='].map(__bytes => {
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
const productos = [
    {
        nombre: 'manzana',
        valor: 500
    },
    {
        nombre: 'pera',
        valor: 300
    },
    {
        nombre: 'mango',
        valor: 250
    },
    {
        nombre: 'naranja',
        valor: 300
    },
    {
        nombre: 'uvas',
        valor: 400
    },
    {
        nombre: 'ciruela',
        valor: 550
    }
];
const formulario = document.querySelector(lS(0, 0));
const boton = document.querySelector(lS(0, 1));
const resultado = document.querySelector(lS(0, 2));
const filtrar = () => {
    resultado.innerHTML = lS(0, 3);
    const text = formulario.value.toLowerCase();
    for (let producto of productos) {
        let nombre = producto.nombre.toLocaleLowerCase();
        (() => {
            const __ifInstance0 = new WebAssembly.Instance(__ifWasmModule, {
                env: {
                    impFunc1: () => {
                        {
                            resultado.innerHTML += `<li>${ producto.nombre } - Valor ${ producto.valor }</li>`;
                        }
                    },
                    impFunc2: () => {
                    }
                }
            });
            const __exports = __ifInstance0.exports;
            return __exports.data(nombre.indexOf(text) !== -1 ? 1 : 0);
        })();
    }
    (() => {
        const __ifInstance1 = new WebAssembly.Instance(__ifWasmModule, {
            env: {
                impFunc1: () => {
                    {
                        resultado.innerHTML += `<li>Producto no encontrado</li>`;
                    }
                },
                impFunc2: () => {
                }
            }
        });
        const __exports = __ifInstance1.exports;
        return __exports.data(resultado.innerHTML === lS(0, 4) ? 1 : 0);
    })();
};
(() => {
    const __callInstance2 = new WebAssembly.Instance(__callWasmModule, {
        env: {
            impFunc: () => {
                boton.addEventListener(lS(0, 5), filtrar);
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
                formulario.addEventListener(lS(0, 6), filtrar);
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
                filtrar();
            }
        }
    });
    const __exports = __callInstance0.exports;
    return __exports.data();
})();