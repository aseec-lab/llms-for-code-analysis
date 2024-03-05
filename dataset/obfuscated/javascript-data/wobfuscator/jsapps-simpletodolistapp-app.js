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
const __wasmStringModules = ['AGFzbQEAAAAFg4CAgAABAAEGqoCAgAAIfwBBAQt/AEEMC38AQRgLfwBBIgt/AEEqC38AQSwLfwBBNAt/AEHAAAsHyoCAgAAJBm1lbW9yeQIABWRhdGEwAwAFZGF0YTEDAQVkYXRhMgMCBWRhdGEzAwMFZGF0YTQDBAVkYXRhNQMFBWRhdGE2AwYFZGF0YTcDBwvggICAAAgAQQELCXRvZG9Gb3JtAABBDAsKdG9kb0lucHV0AABBGAsJaXRlbUxpc3QAAEEiCwdzdWJtaXQAAEEqCwEAAEEsCwZlbXB0eQAAQTQLC2FmdGVyYmVnaW4AAEHAAAsBAA=='].map(__bytes => {
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
let todoForm = document.getElementById(lS(0, 0));
let todoInput = document.getElementById(lS(0, 1));
let itemList = document.getElementById(lS(0, 2));
(() => {
    const __callInstance7 = new WebAssembly.Instance(__callWasmModule, {
        env: {
            impFunc: () => {
                todoForm.addEventListener(lS(0, 3), function (e) {
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
                    if (todoInput.value == lS(0, 4)) {
                        (() => {
                            const __callInstance5 = new WebAssembly.Instance(__callWasmModule, {
                                env: {
                                    impFunc: () => {
                                        inputoEmpty();
                                    }
                                }
                            });
                            const __exports = __callInstance5.exports;
                            return __exports.data();
                        })();
                        return false;
                    }
                    (() => {
                        const __callInstance4 = new WebAssembly.Instance(__callWasmModule, {
                            env: {
                                impFunc: () => {
                                    addItem(todoInput.value);
                                }
                            }
                        });
                        const __exports = __callInstance4.exports;
                        return __exports.data();
                    })();
                });
            }
        }
    });
    const __exports = __callInstance7.exports;
    return __exports.data();
})();
function inputoEmpty() {
    (() => {
        const __callInstance3 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    console.log(lS(0, 5));
                }
            }
        });
        const __exports = __callInstance3.exports;
        return __exports.data();
    })();
}
function addItem(item) {
    let listItem = `<li>${ item } <button onclick="removeItem(this)">x</button>`;
    (() => {
        const __callInstance2 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    list.insertAdjacentHTML(lS(0, 6), listItem);
                }
            }
        });
        const __exports = __callInstance2.exports;
        return __exports.data();
    })();
    todoInput.value = lS(0, 7);
    (() => {
        const __callInstance1 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    todoInput.focus();
                }
            }
        });
        const __exports = __callInstance1.exports;
        return __exports.data();
    })();
}
function removeItem(itemToDelete) {
    (() => {
        const __callInstance0 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    itemToDelete.parentElement.remove();
                }
            }
        });
        const __exports = __callInstance0.exports;
        return __exports.data();
    })();
}