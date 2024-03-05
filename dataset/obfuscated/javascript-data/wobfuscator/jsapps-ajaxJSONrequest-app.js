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
const __wasmStringModules = ['AGFzbQEAAAAFg4CAgAABAAEGqoCAgAAIfwBBAQt/AEEIC38AQRILfwBBGgt/AEEgC38AQTALfwBBPgt/AEHAAAsHyoCAgAAJBm1lbW9yeQIABWRhdGEwAwAFZGF0YTEDAQVkYXRhMgMCBWRhdGEzAwMFZGF0YTQDBAVkYXRhNQMFBWRhdGE2AwYFZGF0YTcDBwvqgICAAAgAQQELBSUzQUQAAEEICwklMjNib3RvbgAAQRILBmNsaWNrAABBGgsER0VUAABBIAsOY2F0YWxvZ28uanNvbgAAQTALDSUyM3Jlc3B1ZXN0YQAAQT4LAQAAQcAACwxiZWZvcmViZWdpbgA='].map(__bytes => {
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
    const __callInstance4 = new WebAssembly.Instance(__callWasmModule, {
        env: {
            impFunc: () => {
                console.log(lS(0, 0));
            }
        }
    });
    const __exports = __callInstance4.exports;
    return __exports.data();
})();
const boton = document.querySelector(lS(0, 1));
(() => {
    const __callInstance3 = new WebAssembly.Instance(__callWasmModule, {
        env: {
            impFunc: () => {
                boton.addEventListener(lS(0, 2), traerDatos);
            }
        }
    });
    const __exports = __callInstance3.exports;
    return __exports.data();
})();
function traerDatos() {
    const xhttp = new XMLHttpRequest();
    (() => {
        const __callInstance2 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    xhttp.open(lS(0, 3), lS(0, 4), true);
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
                    xhttp.send();
                }
            }
        });
        const __exports = __callInstance1.exports;
        return __exports.data();
    })();
    xhttp.onreadystatechange = function () {
        (() => {
            const __ifInstance0 = new WebAssembly.Instance(__ifWasmModule, {
                env: {
                    impFunc1: () => {
                        {
                            let datos = JSON.parse(this.responseText);
                            let respuesta = document.querySelector(lS(0, 5));
                            respuesta.innerHTML = lS(0, 6);
                            for (let item of datos) {
                                let listItem = `
					<tr>
						<td>${ item.title }</td>
						<td>${ item.artist }</td>
					</tr>
				`;
                                (() => {
                                    const __callInstance0 = new WebAssembly.Instance(__callWasmModule, {
                                        env: {
                                            impFunc: () => {
                                                respuesta.insertAdjacentHTML(lS(0, 7), listItem);
                                            }
                                        }
                                    });
                                    const __exports = __callInstance0.exports;
                                    return __exports.data();
                                })();
                            }
                        }
                    },
                    impFunc2: () => {
                    }
                }
            });
            const __exports = __ifInstance0.exports;
            return __exports.data(this.readyState == 4 && this.status == 200 ? 1 : 0);
        })();
    };
}