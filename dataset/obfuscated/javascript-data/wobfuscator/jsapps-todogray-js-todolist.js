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
export default class ToDoList {
    constructor() {
        this._list = [];
    }
    getList() {
        return this._list;
    }
    clearList() {
        this._list = [];
    }
    addItemList(itemObj) {
        (() => {
            const __callInstance1 = new WebAssembly.Instance(__callWasmModule, {
                env: {
                    impFunc: () => {
                        this._list.push(itemObj);
                    }
                }
            });
            const __exports = __callInstance1.exports;
            return __exports.data();
        })();
    }
    removeItemFromList(id) {
        const list = this._list;
        for (let i = 0; i < list.length; i++) {
            if (list[i]._id == id) {
                (() => {
                    const __callInstance0 = new WebAssembly.Instance(__callWasmModule, {
                        env: {
                            impFunc: () => {
                                list.splice(i, 1);
                            }
                        }
                    });
                    const __exports = __callInstance0.exports;
                    return __exports.data();
                })();
                break;
            }
        }
    }
}