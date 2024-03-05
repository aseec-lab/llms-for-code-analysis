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
const __wasmStringModules = ['AGFzbQEAAAAFg4CAgAABAAEG1YCAgAAPfwBBAQt/AEEQC38AQRwLfwBBJgt/AEEuC38AQTYLfwBBxAALfwBB0AALfwBB4AALfwBB7gALfwBB+gALfwBBhgELfwBBlAELfwBBoAELfwBBrgELB4eBgIAAEAZtZW1vcnkCAAVkYXRhMAMABWRhdGExAwEFZGF0YTIDAgVkYXRhMwMDBWRhdGE0AwQFZGF0YTUDBQVkYXRhNgMGBWRhdGE3AwcFZGF0YTgDCAVkYXRhOQMJBmRhdGExMAMKBmRhdGExMQMLBmRhdGExMgMMBmRhdGExMwMNBmRhdGExNAMOC/iBgIAADwBBAQsOJTIzYnRuQm90b25lcwAAQRALCyUyM2JvdG9uZXMAAEEcCwklMjNmb25kbwAAQSYLBmNsaWNrAABBLgsGY2xpY2sAAEE2CwxidG4tcHJpbWFyeQAAQcQACwtiZy1wcmltYXJ5AABB0AALDmJ0bi1zZWNvbmRhcnkAAEHgAAsNYmctc2Vjb25kYXJ5AABB7gALC2J0bi1kYW5nZXIAAEH6AAsKYmctZGFuZ2VyAABBhgELDGJ0bi1zdWNjZXNzAABBlAELC2JnLXN1Y2Nlc3MAAEGgAQsMYnRuLXdhcm5pbmcAAEGuAQsLYmctd2FybmluZwA='].map(__bytes => {
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
const btnBotones = document.querySelector(lS(0, 0));
const botones = document.querySelector(lS(0, 1));
const fondo = document.querySelector(lS(0, 2));
(() => {
    const __callInstance5 = new WebAssembly.Instance(__callWasmModule, {
        env: {
            impFunc: () => {
                (() => {
                    (() => {
                        const __callInstance4 = new WebAssembly.Instance(__callWasmModule, {
                            env: {
                                impFunc: () => {
                                    btnBotones.addEventListener(lS(0, 3), agregarBotones);
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
                                    botones.addEventListener(lS(0, 4), delegacion);
                                }
                            }
                        });
                        const __exports = __callInstance3.exports;
                        return __exports.data();
                    })();
                })();
            }
        }
    });
    const __exports = __callInstance5.exports;
    return __exports.data();
})();
function agregarBotones(e) {
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
        const __callInstance1 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    e.preventDefault();
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
                    console.log(e.target.classList[1]);
                }
            }
        });
        const __exports = __callInstance0.exports;
        return __exports.data();
    })();
    const colorBoton = e.target.classList[1];
    switch (colorBoton) {
    case lS(0, 5):
        fondo.className = lS(0, 6);
        break;
    case lS(0, 7):
        fondo.className = lS(0, 8);
        break;
    case lS(0, 9):
        fondo.className = lS(0, 10);
        break;
    case lS(0, 11):
        fondo.className = lS(0, 12);
        break;
    case lS(0, 13):
        fondo.className = lS(0, 14);
        break;
    }
}