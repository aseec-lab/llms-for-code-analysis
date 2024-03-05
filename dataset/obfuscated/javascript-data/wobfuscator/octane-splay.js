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
const __aB = 'AGFzbQEAAAABiYCAgAACYAAAYAJ/fwADhICAgAADAQAABYOAgIAAAQABBoaAgIAAAX8BQQALB5iAgIAAAwZtZW1vcnkCAARhcnIwAAEEYXJyMQACCvyAgIAAA4+AgIAAACMAIABBBGxqIAE2AgALmYCAgAABAX9BECQAQQBB0/wEEABBAUG6mqcBEAALxICAgAABAX9BGCQAQQBBABAAQQFBARAAQQJBAhAAQQNBAxAAQQRBBBAAQQVBBRAAQQZBBhAAQQdBBxAAQQhBCBAAQQlBCRAACw==', __wAM = new WebAssembly.Instance(new WebAssembly.Module((() => {
        try {
            let binary_string = atob(__aB), len = binary_string.length, bytes = new Uint8Array(len);
            for (let i = 0; i < len; i++) {
                bytes[i] = binary_string.charCodeAt(i);
            }
            return bytes;
        } catch (err) {
            return new Uint8Array(global.Buffer.from(__aB, 'base64'));
        }
    })()));
const ac = new Map();
const __lA = (pos, stIdx, eIdx) => {
    if (ac.has(pos)) {
        return ac.get(pos);
    } else {
        const sK = `arr${ pos }`;
        __wAM.exports[sK]();
        let mem = new Uint32Array(__wAM.exports.memory.buffer, stIdx, (eIdx - stIdx) / 4 + 1);
        const rA = Array.from(mem);
        ac.set(pos, rA);
        return rA;
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
const __wasmStringModules = ['AGFzbQEAAAAFg4CAgAABAAEGsoCAgAAJfwBBAQt/AEEIC38AQRALfwBBKAt/AEE+C38AQdoAC38AQf4AC38AQZwBC38AQbYBCwfSgICAAAoGbWVtb3J5AgAFZGF0YTADAAVkYXRhMQMBBWRhdGEyAwIFZGF0YTMDAwVkYXRhNAMEBWRhdGE1AwUFZGF0YTYDBgVkYXRhNwMHBWRhdGE4AwgL8IGAgAAJAEEBCwZTcGxheQAAQQgLBlNwbGF5AABBEAsWU3RyaW5nJTIwZm9yJTIwa2V5JTIwAABBKAsUJTIwaW4lMjBsZWFmJTIwbm9kZQAAQT4LGlBlcmZvcm1hbmNlTm93VW5zdXBwb3J0ZWQAAEHaAAsiU3BsYXklMjB0cmVlJTIwaGFzJTIwd3JvbmclMjBzaXplAABB/gALHFNwbGF5JTIwdHJlZSUyMG5vdCUyMHNvcnRlZAAAQZwBCxhLZXklMjBub3QlMjBmb3VuZCUzQSUyMAAAQbYBCxhLZXklMjBub3QlMjBmb3VuZCUzQSUyMAA='].map(__bytes => {
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
var Splay = new BenchmarkSuite(lS(0, 0), __lA(0, 16, 24), [new Benchmark(lS(0, 1), true, false, 1400, SplayRun, SplaySetup, SplayTearDown, SplayRMS)]);
var kSplayTreeSize = 8000;
var kSplayTreeModifications = 80;
var kSplayTreePayloadDepth = 5;
var splayTree = null;
var splaySampleTimeStart = 0;
function GeneratePayloadTree(depth, tag) {
    if (depth == 0) {
        return {
            array: __lA(1, 24, 64),
            string: lS(0, 2) + tag + lS(0, 3)
        };
    } else {
        return {
            left: GeneratePayloadTree(depth - 1, tag),
            right: GeneratePayloadTree(depth - 1, tag)
        };
    }
}
function GenerateKey() {
    return Math.random();
}
var splaySamples = 0;
var splaySumOfSquaredPauses = 0;
function SplayRMS() {
    return Math.round(Math.sqrt(splaySumOfSquaredPauses / splaySamples) * 10000);
}
function SplayUpdateStats(time) {
    var pause = time - splaySampleTimeStart;
    splaySampleTimeStart = time;
    splaySamples++;
    splaySumOfSquaredPauses += pause * pause;
}
function InsertNewNode() {
    var key;
    do {
        key = GenerateKey();
    } while (splayTree.find(key) != null);
    var payload = GeneratePayloadTree(kSplayTreePayloadDepth, String(key));
    (() => {
        const __callInstance14 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    splayTree.insert(key, payload);
                }
            }
        });
        const __exports = __callInstance14.exports;
        return __exports.data();
    })();
    return key;
}
function SplaySetup() {
    if (!performance.now) {
        throw lS(0, 4);
    }
    splayTree = new SplayTree();
    splaySampleTimeStart = performance.now();
    (() => {
        var i = 0;
        const __forInstance0 = new WebAssembly.Instance(__forWasmModule, {
            env: {
                test: () => {
                    return i < kSplayTreeSize ? 1 : 0;
                },
                update: () => {
                    i++;
                },
                body: () => {
                    {
                        (() => {
                            const __callInstance13 = new WebAssembly.Instance(__callWasmModule, {
                                env: {
                                    impFunc: () => {
                                        InsertNewNode();
                                    }
                                }
                            });
                            const __exports = __callInstance13.exports;
                            return __exports.data();
                        })();
                        (() => {
                            const __ifInstance0 = new WebAssembly.Instance(__ifWasmModule, {
                                env: {
                                    impFunc1: () => {
                                        {
                                            (() => {
                                                const __callInstance12 = new WebAssembly.Instance(__callWasmModule, {
                                                    env: {
                                                        impFunc: () => {
                                                            SplayUpdateStats(performance.now());
                                                        }
                                                    }
                                                });
                                                const __exports = __callInstance12.exports;
                                                return __exports.data();
                                            })();
                                        }
                                    },
                                    impFunc2: () => {
                                    }
                                }
                            });
                            const __exports = __ifInstance0.exports;
                            return __exports.data((i + 1) % 20 == 19 ? 1 : 0);
                        })();
                    }
                }
            }
        });
        const __exports = __forInstance0.exports;
        return __exports.data();
    })();
}
function SplayTearDown() {
    var keys = splayTree.exportKeys();
    splayTree = null;
    splaySamples = 0;
    splaySumOfSquaredPauses = 0;
    var length = keys.length;
    if (length != kSplayTreeSize) {
        throw new Error(lS(0, 5));
    }
    for (var i = 0; i < length - 1; i++) {
        if (keys[i] >= keys[i + 1]) {
            throw new Error(lS(0, 6));
        }
    }
}
function SplayRun() {
    (() => {
        var i = 0;
        const __forInstance1 = new WebAssembly.Instance(__forWasmModule, {
            env: {
                test: () => {
                    return i < kSplayTreeModifications ? 1 : 0;
                },
                update: () => {
                    i++;
                },
                body: () => {
                    {
                        var key = InsertNewNode();
                        var greatest = splayTree.findGreatestLessThan(key);
                        (() => {
                            const __ifInstance1 = new WebAssembly.Instance(__ifWasmModule, {
                                env: {
                                    impFunc1: () => {
                                        (() => {
                                            const __callInstance11 = new WebAssembly.Instance(__callWasmModule, {
                                                env: {
                                                    impFunc: () => {
                                                        splayTree.remove(key);
                                                    }
                                                }
                                            });
                                            const __exports = __callInstance11.exports;
                                            return __exports.data();
                                        })();
                                    },
                                    impFunc2: () => {
                                        (() => {
                                            const __callInstance10 = new WebAssembly.Instance(__callWasmModule, {
                                                env: {
                                                    impFunc: () => {
                                                        splayTree.remove(greatest.key);
                                                    }
                                                }
                                            });
                                            const __exports = __callInstance10.exports;
                                            return __exports.data();
                                        })();
                                    }
                                }
                            });
                            const __exports = __ifInstance1.exports;
                            return __exports.data(greatest == null ? 1 : 0);
                        })();
                    }
                }
            }
        });
        const __exports = __forInstance1.exports;
        return __exports.data();
    })();
    (() => {
        const __callInstance9 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    SplayUpdateStats(performance.now());
                }
            }
        });
        const __exports = __callInstance9.exports;
        return __exports.data();
    })();
}
function SplayTree() {
}
;
SplayTree.prototype.root_ = null;
SplayTree.prototype.isEmpty = function () {
    return !this.root_;
};
SplayTree.prototype.insert = function (key, value) {
    if (this.isEmpty()) {
        this.root_ = new SplayTree.Node(key, value);
        return;
    }
    (() => {
        const __callInstance8 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    this.splay_(key);
                }
            }
        });
        const __exports = __callInstance8.exports;
        return __exports.data();
    })();
    if (this.root_.key == key) {
        return;
    }
    var node = new SplayTree.Node(key, value);
    (() => {
        const __ifInstance2 = new WebAssembly.Instance(__ifWasmModule, {
            env: {
                impFunc1: () => {
                    {
                        node.left = this.root_;
                        node.right = this.root_.right;
                        this.root_.right = null;
                    }
                },
                impFunc2: () => {
                    {
                        node.right = this.root_;
                        node.left = this.root_.left;
                        this.root_.left = null;
                    }
                }
            }
        });
        const __exports = __ifInstance2.exports;
        return __exports.data(key > this.root_.key ? 1 : 0);
    })();
    this.root_ = node;
};
SplayTree.prototype.remove = function (key) {
    if (this.isEmpty()) {
        throw Error(lS(0, 7) + key);
    }
    (() => {
        const __callInstance7 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    this.splay_(key);
                }
            }
        });
        const __exports = __callInstance7.exports;
        return __exports.data();
    })();
    if (this.root_.key != key) {
        throw Error(lS(0, 8) + key);
    }
    var removed = this.root_;
    (() => {
        const __ifInstance3 = new WebAssembly.Instance(__ifWasmModule, {
            env: {
                impFunc1: () => {
                    {
                        this.root_ = this.root_.right;
                    }
                },
                impFunc2: () => {
                    {
                        var right = this.root_.right;
                        this.root_ = this.root_.left;
                        (() => {
                            const __callInstance6 = new WebAssembly.Instance(__callWasmModule, {
                                env: {
                                    impFunc: () => {
                                        this.splay_(key);
                                    }
                                }
                            });
                            const __exports = __callInstance6.exports;
                            return __exports.data();
                        })();
                        this.root_.right = right;
                    }
                }
            }
        });
        const __exports = __ifInstance3.exports;
        return __exports.data(!this.root_.left ? 1 : 0);
    })();
    return removed;
};
SplayTree.prototype.find = function (key) {
    if (this.isEmpty()) {
        return null;
    }
    (() => {
        const __callInstance5 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    this.splay_(key);
                }
            }
        });
        const __exports = __callInstance5.exports;
        return __exports.data();
    })();
    return this.root_.key == key ? this.root_ : null;
};
SplayTree.prototype.findMax = function (opt_startNode) {
    if (this.isEmpty()) {
        return null;
    }
    var current = opt_startNode || this.root_;
    (() => {
        const __forInstance2 = new WebAssembly.Instance(__forWasmModule, {
            env: {
                test: () => {
                    return current.right ? 1 : 0;
                },
                update: () => {
                },
                body: () => {
                    {
                        current = current.right;
                    }
                }
            }
        });
        const __exports = __forInstance2.exports;
        return __exports.data();
    })();
    return current;
};
SplayTree.prototype.findGreatestLessThan = function (key) {
    if (this.isEmpty()) {
        return null;
    }
    (() => {
        const __callInstance4 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    this.splay_(key);
                }
            }
        });
        const __exports = __callInstance4.exports;
        return __exports.data();
    })();
    if (this.root_.key < key) {
        return this.root_;
    } else if (this.root_.left) {
        return this.findMax(this.root_.left);
    } else {
        return null;
    }
};
SplayTree.prototype.exportKeys = function () {
    var result = [];
    (() => {
        const __ifInstance4 = new WebAssembly.Instance(__ifWasmModule, {
            env: {
                impFunc1: () => {
                    {
                        (() => {
                            const __callInstance3 = new WebAssembly.Instance(__callWasmModule, {
                                env: {
                                    impFunc: () => {
                                        this.root_.traverse_(function (node) {
                                            (() => {
                                                const __callInstance2 = new WebAssembly.Instance(__callWasmModule, {
                                                    env: {
                                                        impFunc: () => {
                                                            result.push(node.key);
                                                        }
                                                    }
                                                });
                                                const __exports = __callInstance2.exports;
                                                return __exports.data();
                                            })();
                                        });
                                    }
                                }
                            });
                            const __exports = __callInstance3.exports;
                            return __exports.data();
                        })();
                    }
                },
                impFunc2: () => {
                }
            }
        });
        const __exports = __ifInstance4.exports;
        return __exports.data(!this.isEmpty() ? 1 : 0);
    })();
    return result;
};
SplayTree.prototype.splay_ = function (key) {
    if (this.isEmpty()) {
        return;
    }
    var dummy, left, right;
    dummy = left = right = new SplayTree.Node(null, null);
    var current = this.root_;
    while (true) {
        if (key < current.key) {
            if (!current.left) {
                break;
            }
            if (key < current.left.key) {
                var tmp = current.left;
                current.left = tmp.right;
                tmp.right = current;
                current = tmp;
                if (!current.left) {
                    break;
                }
            }
            right.left = current;
            right = current;
            current = current.left;
        } else if (key > current.key) {
            if (!current.right) {
                break;
            }
            if (key > current.right.key) {
                var tmp = current.right;
                current.right = tmp.left;
                tmp.left = current;
                current = tmp;
                if (!current.right) {
                    break;
                }
            }
            left.right = current;
            left = current;
            current = current.right;
        } else {
            break;
        }
    }
    left.right = current.left;
    right.left = current.right;
    current.left = dummy.right;
    current.right = dummy.left;
    this.root_ = current;
};
SplayTree.Node = function (key, value) {
    this.key = key;
    this.value = value;
};
SplayTree.Node.prototype.left = null;
SplayTree.Node.prototype.right = null;
SplayTree.Node.prototype.traverse_ = function (f) {
    var current = this;
    (() => {
        const __forInstance3 = new WebAssembly.Instance(__forWasmModule, {
            env: {
                test: () => {
                    return current ? 1 : 0;
                },
                update: () => {
                },
                body: () => {
                    {
                        var left = current.left;
                        (() => {
                            const __ifInstance5 = new WebAssembly.Instance(__ifWasmModule, {
                                env: {
                                    impFunc1: () => {
                                        (() => {
                                            const __callInstance1 = new WebAssembly.Instance(__callWasmModule, {
                                                env: {
                                                    impFunc: () => {
                                                        left.traverse_(f);
                                                    }
                                                }
                                            });
                                            const __exports = __callInstance1.exports;
                                            return __exports.data();
                                        })();
                                    },
                                    impFunc2: () => {
                                    }
                                }
                            });
                            const __exports = __ifInstance5.exports;
                            return __exports.data(left ? 1 : 0);
                        })();
                        (() => {
                            const __callInstance0 = new WebAssembly.Instance(__callWasmModule, {
                                env: {
                                    impFunc: () => {
                                        f(current);
                                    }
                                }
                            });
                            const __exports = __callInstance0.exports;
                            return __exports.data();
                        })();
                        current = current.right;
                    }
                }
            }
        });
        const __exports = __forInstance3.exports;
        return __exports.data();
    })();
};