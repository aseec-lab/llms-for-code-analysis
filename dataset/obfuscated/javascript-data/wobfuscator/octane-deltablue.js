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
const __aB = 'AGFzbQEAAAABiYCAgAACYAAAYAJ/fwADg4CAgAACAQAFg4CAgAABAAEGhoCAgAABfwFBAAsHkYCAgAACBm1lbW9yeQIABGFycjAAAQqqgICAAAKPgICAAAAjACAAQQRsaiABNgIAC5CAgIAAAQF/QRAkAEEAQcaEBBAACw==', __wAM = new WebAssembly.Instance(new WebAssembly.Module((() => {
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
const __wasmStringModules = ['AGFzbQEAAAAFg4CAgAABAAEGgIGAgAAWfwBBAQt/AEEMC38AQRgLfwBBJgt/AEEwC38AQcIAC38AQc4AC38AQd4AC38AQeYAC38AQfQAC38AQf4AC38AQbIBC38AQcgBC38AQcwBC38AQeQBC38AQewBC38AQfQBC38AQfoBC38AQYACC38AQZoCC38AQbQCC38AQc4CCwfGgYCAABcGbWVtb3J5AgAFZGF0YTADAAVkYXRhMQMBBWRhdGEyAwIFZGF0YTMDAwVkYXRhNAMEBWRhdGE1AwUFZGF0YTYDBgVkYXRhNwMHBWRhdGE4AwgFZGF0YTkDCQZkYXRhMTADCgZkYXRhMTEDCwZkYXRhMTIDDAZkYXRhMTMDDQZkYXRhMTQDDgZkYXRhMTUDDwZkYXRhMTYDEAZkYXRhMTcDEQZkYXRhMTgDEgZkYXRhMTkDEwZkYXRhMjADFAZkYXRhMjEDFQvCg4CAABYAQQELCkRlbHRhQmx1ZQAAQQwLCkRlbHRhQmx1ZQAAQRgLDWluaGVyaXRzRnJvbQAAQSYLCXJlcXVpcmVkAABBMAsQc3Ryb25nUHJlZmVycmVkAABBwgALCnByZWZlcnJlZAAAQc4ACw5zdHJvbmdEZWZhdWx0AABB3gALB25vcm1hbAAAQeYACwx3ZWFrRGVmYXVsdAAAQfQACwh3ZWFrZXN0AABB/gALM0NvdWxkJTIwbm90JTIwc2F0aXNmeSUyMGElMjByZXF1aXJlZCUyMGNvbnN0cmFpbnQhAABBsgELFEN5Y2xlJTIwZW5jb3VudGVyZWQAAEHIAQsCdgAAQcwBCxdDaGFpbiUyMHRlc3QlMjBmYWlsZWQuAABB5AELBnNjYWxlAABB7AELB29mZnNldAAAQfQBCwRzcmMAAEH6AQsEZHN0AABBgAILGFByb2plY3Rpb24lMjAxJTIwZmFpbGVkAABBmgILGFByb2plY3Rpb24lMjAyJTIwZmFpbGVkAABBtAILGFByb2plY3Rpb24lMjAzJTIwZmFpbGVkAABBzgILGFByb2plY3Rpb24lMjA0JTIwZmFpbGVkAA=='].map(__bytes => {
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
var DeltaBlue = new BenchmarkSuite(lS(0, 0), __lA(0, 16, 20), [new Benchmark(lS(0, 1), true, false, 4400, deltaBlue)]);
(() => {
    const __callInstance77 = new WebAssembly.Instance(__callWasmModule, {
        env: {
            impFunc: () => {
                Object.defineProperty(Object.prototype, lS(0, 2), {
                    value: function (shuper) {
                        function Inheriter() {
                        }
                        Inheriter.prototype = shuper.prototype;
                        this.prototype = new Inheriter();
                        this.superConstructor = shuper;
                    }
                });
            }
        }
    });
    const __exports = __callInstance77.exports;
    return __exports.data();
})();
function OrderedCollection() {
    this.elms = new Array();
}
OrderedCollection.prototype.add = function (elm) {
    (() => {
        const __callInstance76 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    this.elms.push(elm);
                }
            }
        });
        const __exports = __callInstance76.exports;
        return __exports.data();
    })();
};
OrderedCollection.prototype.at = function (index) {
    return this.elms[index];
};
OrderedCollection.prototype.size = function () {
    return this.elms.length;
};
OrderedCollection.prototype.removeFirst = function () {
    return this.elms.pop();
};
OrderedCollection.prototype.remove = function (elm) {
    var index = 0, skipped = 0;
    (() => {
        var i = 0;
        const __forInstance0 = new WebAssembly.Instance(__forWasmModule, {
            env: {
                test: () => {
                    return i < this.elms.length ? 1 : 0;
                },
                update: () => {
                    i++;
                },
                body: () => {
                    {
                        var value = this.elms[i];
                        (() => {
                            const __ifInstance0 = new WebAssembly.Instance(__ifWasmModule, {
                                env: {
                                    impFunc1: () => {
                                        {
                                            this.elms[index] = value;
                                            index++;
                                        }
                                    },
                                    impFunc2: () => {
                                        {
                                            skipped++;
                                        }
                                    }
                                }
                            });
                            const __exports = __ifInstance0.exports;
                            return __exports.data(value != elm ? 1 : 0);
                        })();
                    }
                }
            }
        });
        const __exports = __forInstance0.exports;
        return __exports.data();
    })();
    (() => {
        var i = 0;
        const __forInstance1 = new WebAssembly.Instance(__forWasmModule, {
            env: {
                test: () => {
                    return i < skipped ? 1 : 0;
                },
                update: () => {
                    i++;
                },
                body: () => {
                    (() => {
                        const __callInstance75 = new WebAssembly.Instance(__callWasmModule, {
                            env: {
                                impFunc: () => {
                                    this.elms.pop();
                                }
                            }
                        });
                        const __exports = __callInstance75.exports;
                        return __exports.data();
                    })();
                }
            }
        });
        const __exports = __forInstance1.exports;
        return __exports.data();
    })();
};
function Strength(strengthValue, name) {
    this.strengthValue = strengthValue;
    this.name = name;
}
Strength.stronger = function (s1, s2) {
    return s1.strengthValue < s2.strengthValue;
};
Strength.weaker = function (s1, s2) {
    return s1.strengthValue > s2.strengthValue;
};
Strength.weakestOf = function (s1, s2) {
    return this.weaker(s1, s2) ? s1 : s2;
};
Strength.strongest = function (s1, s2) {
    return this.stronger(s1, s2) ? s1 : s2;
};
Strength.prototype.nextWeaker = function () {
    switch (this.strengthValue) {
    case 0:
        return Strength.WEAKEST;
    case 1:
        return Strength.WEAK_DEFAULT;
    case 2:
        return Strength.NORMAL;
    case 3:
        return Strength.STRONG_DEFAULT;
    case 4:
        return Strength.PREFERRED;
    case 5:
        return Strength.REQUIRED;
    }
};
Strength.REQUIRED = new Strength(0, lS(0, 3));
Strength.STONG_PREFERRED = new Strength(1, lS(0, 4));
Strength.PREFERRED = new Strength(2, lS(0, 5));
Strength.STRONG_DEFAULT = new Strength(3, lS(0, 6));
Strength.NORMAL = new Strength(4, lS(0, 7));
Strength.WEAK_DEFAULT = new Strength(5, lS(0, 8));
Strength.WEAKEST = new Strength(6, lS(0, 9));
function Constraint(strength) {
    this.strength = strength;
}
Constraint.prototype.addConstraint = function () {
    (() => {
        const __callInstance74 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    this.addToGraph();
                }
            }
        });
        const __exports = __callInstance74.exports;
        return __exports.data();
    })();
    (() => {
        const __callInstance73 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    planner.incrementalAdd(this);
                }
            }
        });
        const __exports = __callInstance73.exports;
        return __exports.data();
    })();
};
Constraint.prototype.satisfy = function (mark) {
    (() => {
        const __callInstance72 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    this.chooseMethod(mark);
                }
            }
        });
        const __exports = __callInstance72.exports;
        return __exports.data();
    })();
    if (!this.isSatisfied()) {
        (() => {
            const __ifInstance1 = new WebAssembly.Instance(__ifWasmModule, {
                env: {
                    impFunc1: () => {
                        (() => {
                            const __callInstance71 = new WebAssembly.Instance(__callWasmModule, {
                                env: {
                                    impFunc: () => {
                                        alert(lS(0, 10));
                                    }
                                }
                            });
                            const __exports = __callInstance71.exports;
                            return __exports.data();
                        })();
                    },
                    impFunc2: () => {
                    }
                }
            });
            const __exports = __ifInstance1.exports;
            return __exports.data(this.strength == Strength.REQUIRED ? 1 : 0);
        })();
        return null;
    }
    (() => {
        const __callInstance70 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    this.markInputs(mark);
                }
            }
        });
        const __exports = __callInstance70.exports;
        return __exports.data();
    })();
    var out = this.output();
    var overridden = out.determinedBy;
    (() => {
        const __ifInstance2 = new WebAssembly.Instance(__ifWasmModule, {
            env: {
                impFunc1: () => {
                    (() => {
                        const __callInstance69 = new WebAssembly.Instance(__callWasmModule, {
                            env: {
                                impFunc: () => {
                                    overridden.markUnsatisfied();
                                }
                            }
                        });
                        const __exports = __callInstance69.exports;
                        return __exports.data();
                    })();
                },
                impFunc2: () => {
                }
            }
        });
        const __exports = __ifInstance2.exports;
        return __exports.data(overridden != null ? 1 : 0);
    })();
    out.determinedBy = this;
    (() => {
        const __ifInstance3 = new WebAssembly.Instance(__ifWasmModule, {
            env: {
                impFunc1: () => {
                    (() => {
                        const __callInstance68 = new WebAssembly.Instance(__callWasmModule, {
                            env: {
                                impFunc: () => {
                                    alert(lS(0, 11));
                                }
                            }
                        });
                        const __exports = __callInstance68.exports;
                        return __exports.data();
                    })();
                },
                impFunc2: () => {
                }
            }
        });
        const __exports = __ifInstance3.exports;
        return __exports.data(!planner.addPropagate(this, mark) ? 1 : 0);
    })();
    out.mark = mark;
    return overridden;
};
Constraint.prototype.destroyConstraint = function () {
    (() => {
        const __ifInstance4 = new WebAssembly.Instance(__ifWasmModule, {
            env: {
                impFunc1: () => {
                    (() => {
                        const __callInstance67 = new WebAssembly.Instance(__callWasmModule, {
                            env: {
                                impFunc: () => {
                                    planner.incrementalRemove(this);
                                }
                            }
                        });
                        const __exports = __callInstance67.exports;
                        return __exports.data();
                    })();
                },
                impFunc2: () => {
                    (() => {
                        const __callInstance66 = new WebAssembly.Instance(__callWasmModule, {
                            env: {
                                impFunc: () => {
                                    this.removeFromGraph();
                                }
                            }
                        });
                        const __exports = __callInstance66.exports;
                        return __exports.data();
                    })();
                }
            }
        });
        const __exports = __ifInstance4.exports;
        return __exports.data(this.isSatisfied() ? 1 : 0);
    })();
};
Constraint.prototype.isInput = function () {
    return false;
};
function UnaryConstraint(v, strength) {
    (() => {
        const __callInstance65 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    UnaryConstraint.superConstructor.call(this, strength);
                }
            }
        });
        const __exports = __callInstance65.exports;
        return __exports.data();
    })();
    this.myOutput = v;
    this.satisfied = false;
    (() => {
        const __callInstance64 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    this.addConstraint();
                }
            }
        });
        const __exports = __callInstance64.exports;
        return __exports.data();
    })();
}
(() => {
    const __callInstance63 = new WebAssembly.Instance(__callWasmModule, {
        env: {
            impFunc: () => {
                UnaryConstraint.inheritsFrom(Constraint);
            }
        }
    });
    const __exports = __callInstance63.exports;
    return __exports.data();
})();
UnaryConstraint.prototype.addToGraph = function () {
    (() => {
        const __callInstance62 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    this.myOutput.addConstraint(this);
                }
            }
        });
        const __exports = __callInstance62.exports;
        return __exports.data();
    })();
    this.satisfied = false;
};
UnaryConstraint.prototype.chooseMethod = function (mark) {
    this.satisfied = this.myOutput.mark != mark && Strength.stronger(this.strength, this.myOutput.walkStrength);
};
UnaryConstraint.prototype.isSatisfied = function () {
    return this.satisfied;
};
UnaryConstraint.prototype.markInputs = function (mark) {
};
UnaryConstraint.prototype.output = function () {
    return this.myOutput;
};
UnaryConstraint.prototype.recalculate = function () {
    this.myOutput.walkStrength = this.strength;
    this.myOutput.stay = !this.isInput();
    (() => {
        const __ifInstance5 = new WebAssembly.Instance(__ifWasmModule, {
            env: {
                impFunc1: () => {
                    (() => {
                        const __callInstance61 = new WebAssembly.Instance(__callWasmModule, {
                            env: {
                                impFunc: () => {
                                    this.execute();
                                }
                            }
                        });
                        const __exports = __callInstance61.exports;
                        return __exports.data();
                    })();
                },
                impFunc2: () => {
                }
            }
        });
        const __exports = __ifInstance5.exports;
        return __exports.data(this.myOutput.stay ? 1 : 0);
    })();
};
UnaryConstraint.prototype.markUnsatisfied = function () {
    this.satisfied = false;
};
UnaryConstraint.prototype.inputsKnown = function () {
    return true;
};
UnaryConstraint.prototype.removeFromGraph = function () {
    (() => {
        const __ifInstance6 = new WebAssembly.Instance(__ifWasmModule, {
            env: {
                impFunc1: () => {
                    (() => {
                        const __callInstance60 = new WebAssembly.Instance(__callWasmModule, {
                            env: {
                                impFunc: () => {
                                    this.myOutput.removeConstraint(this);
                                }
                            }
                        });
                        const __exports = __callInstance60.exports;
                        return __exports.data();
                    })();
                },
                impFunc2: () => {
                }
            }
        });
        const __exports = __ifInstance6.exports;
        return __exports.data(this.myOutput != null ? 1 : 0);
    })();
    this.satisfied = false;
};
function StayConstraint(v, str) {
    (() => {
        const __callInstance59 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    StayConstraint.superConstructor.call(this, v, str);
                }
            }
        });
        const __exports = __callInstance59.exports;
        return __exports.data();
    })();
}
(() => {
    const __callInstance58 = new WebAssembly.Instance(__callWasmModule, {
        env: {
            impFunc: () => {
                StayConstraint.inheritsFrom(UnaryConstraint);
            }
        }
    });
    const __exports = __callInstance58.exports;
    return __exports.data();
})();
StayConstraint.prototype.execute = function () {
};
function EditConstraint(v, str) {
    (() => {
        const __callInstance57 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    EditConstraint.superConstructor.call(this, v, str);
                }
            }
        });
        const __exports = __callInstance57.exports;
        return __exports.data();
    })();
}
(() => {
    const __callInstance56 = new WebAssembly.Instance(__callWasmModule, {
        env: {
            impFunc: () => {
                EditConstraint.inheritsFrom(UnaryConstraint);
            }
        }
    });
    const __exports = __callInstance56.exports;
    return __exports.data();
})();
EditConstraint.prototype.isInput = function () {
    return true;
};
EditConstraint.prototype.execute = function () {
};
var Direction = new Object();
Direction.NONE = 0;
Direction.FORWARD = 1;
Direction.BACKWARD = -1;
function BinaryConstraint(var1, var2, strength) {
    (() => {
        const __callInstance55 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    BinaryConstraint.superConstructor.call(this, strength);
                }
            }
        });
        const __exports = __callInstance55.exports;
        return __exports.data();
    })();
    this.v1 = var1;
    this.v2 = var2;
    this.direction = Direction.NONE;
    (() => {
        const __callInstance54 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    this.addConstraint();
                }
            }
        });
        const __exports = __callInstance54.exports;
        return __exports.data();
    })();
}
(() => {
    const __callInstance53 = new WebAssembly.Instance(__callWasmModule, {
        env: {
            impFunc: () => {
                BinaryConstraint.inheritsFrom(Constraint);
            }
        }
    });
    const __exports = __callInstance53.exports;
    return __exports.data();
})();
BinaryConstraint.prototype.chooseMethod = function (mark) {
    (() => {
        const __ifInstance7 = new WebAssembly.Instance(__ifWasmModule, {
            env: {
                impFunc1: () => {
                    {
                        this.direction = this.v2.mark != mark && Strength.stronger(this.strength, this.v2.walkStrength) ? Direction.FORWARD : Direction.NONE;
                    }
                },
                impFunc2: () => {
                }
            }
        });
        const __exports = __ifInstance7.exports;
        return __exports.data(this.v1.mark == mark ? 1 : 0);
    })();
    (() => {
        const __ifInstance8 = new WebAssembly.Instance(__ifWasmModule, {
            env: {
                impFunc1: () => {
                    {
                        this.direction = this.v1.mark != mark && Strength.stronger(this.strength, this.v1.walkStrength) ? Direction.BACKWARD : Direction.NONE;
                    }
                },
                impFunc2: () => {
                }
            }
        });
        const __exports = __ifInstance8.exports;
        return __exports.data(this.v2.mark == mark ? 1 : 0);
    })();
    (() => {
        const __ifInstance9 = new WebAssembly.Instance(__ifWasmModule, {
            env: {
                impFunc1: () => {
                    {
                        this.direction = Strength.stronger(this.strength, this.v1.walkStrength) ? Direction.BACKWARD : Direction.NONE;
                    }
                },
                impFunc2: () => {
                    {
                        this.direction = Strength.stronger(this.strength, this.v2.walkStrength) ? Direction.FORWARD : Direction.BACKWARD;
                    }
                }
            }
        });
        const __exports = __ifInstance9.exports;
        return __exports.data(Strength.weaker(this.v1.walkStrength, this.v2.walkStrength) ? 1 : 0);
    })();
};
BinaryConstraint.prototype.addToGraph = function () {
    (() => {
        const __callInstance52 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    this.v1.addConstraint(this);
                }
            }
        });
        const __exports = __callInstance52.exports;
        return __exports.data();
    })();
    (() => {
        const __callInstance51 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    this.v2.addConstraint(this);
                }
            }
        });
        const __exports = __callInstance51.exports;
        return __exports.data();
    })();
    this.direction = Direction.NONE;
};
BinaryConstraint.prototype.isSatisfied = function () {
    return this.direction != Direction.NONE;
};
BinaryConstraint.prototype.markInputs = function (mark) {
    this.input().mark = mark;
};
BinaryConstraint.prototype.input = function () {
    return this.direction == Direction.FORWARD ? this.v1 : this.v2;
};
BinaryConstraint.prototype.output = function () {
    return this.direction == Direction.FORWARD ? this.v2 : this.v1;
};
BinaryConstraint.prototype.recalculate = function () {
    var ihn = this.input(), out = this.output();
    out.walkStrength = Strength.weakestOf(this.strength, ihn.walkStrength);
    out.stay = ihn.stay;
    (() => {
        const __ifInstance10 = new WebAssembly.Instance(__ifWasmModule, {
            env: {
                impFunc1: () => {
                    (() => {
                        const __callInstance50 = new WebAssembly.Instance(__callWasmModule, {
                            env: {
                                impFunc: () => {
                                    this.execute();
                                }
                            }
                        });
                        const __exports = __callInstance50.exports;
                        return __exports.data();
                    })();
                },
                impFunc2: () => {
                }
            }
        });
        const __exports = __ifInstance10.exports;
        return __exports.data(out.stay ? 1 : 0);
    })();
};
BinaryConstraint.prototype.markUnsatisfied = function () {
    this.direction = Direction.NONE;
};
BinaryConstraint.prototype.inputsKnown = function (mark) {
    var i = this.input();
    return i.mark == mark || i.stay || i.determinedBy == null;
};
BinaryConstraint.prototype.removeFromGraph = function () {
    (() => {
        const __ifInstance11 = new WebAssembly.Instance(__ifWasmModule, {
            env: {
                impFunc1: () => {
                    (() => {
                        const __callInstance49 = new WebAssembly.Instance(__callWasmModule, {
                            env: {
                                impFunc: () => {
                                    this.v1.removeConstraint(this);
                                }
                            }
                        });
                        const __exports = __callInstance49.exports;
                        return __exports.data();
                    })();
                },
                impFunc2: () => {
                }
            }
        });
        const __exports = __ifInstance11.exports;
        return __exports.data(this.v1 != null ? 1 : 0);
    })();
    (() => {
        const __ifInstance12 = new WebAssembly.Instance(__ifWasmModule, {
            env: {
                impFunc1: () => {
                    (() => {
                        const __callInstance48 = new WebAssembly.Instance(__callWasmModule, {
                            env: {
                                impFunc: () => {
                                    this.v2.removeConstraint(this);
                                }
                            }
                        });
                        const __exports = __callInstance48.exports;
                        return __exports.data();
                    })();
                },
                impFunc2: () => {
                }
            }
        });
        const __exports = __ifInstance12.exports;
        return __exports.data(this.v2 != null ? 1 : 0);
    })();
    this.direction = Direction.NONE;
};
function ScaleConstraint(src, scale, offset, dest, strength) {
    this.direction = Direction.NONE;
    this.scale = scale;
    this.offset = offset;
    (() => {
        const __callInstance47 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    ScaleConstraint.superConstructor.call(this, src, dest, strength);
                }
            }
        });
        const __exports = __callInstance47.exports;
        return __exports.data();
    })();
}
(() => {
    const __callInstance46 = new WebAssembly.Instance(__callWasmModule, {
        env: {
            impFunc: () => {
                ScaleConstraint.inheritsFrom(BinaryConstraint);
            }
        }
    });
    const __exports = __callInstance46.exports;
    return __exports.data();
})();
ScaleConstraint.prototype.addToGraph = function () {
    (() => {
        const __callInstance45 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    ScaleConstraint.superConstructor.prototype.addToGraph.call(this);
                }
            }
        });
        const __exports = __callInstance45.exports;
        return __exports.data();
    })();
    (() => {
        const __callInstance44 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    this.scale.addConstraint(this);
                }
            }
        });
        const __exports = __callInstance44.exports;
        return __exports.data();
    })();
    (() => {
        const __callInstance43 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    this.offset.addConstraint(this);
                }
            }
        });
        const __exports = __callInstance43.exports;
        return __exports.data();
    })();
};
ScaleConstraint.prototype.removeFromGraph = function () {
    (() => {
        const __callInstance42 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    ScaleConstraint.superConstructor.prototype.removeFromGraph.call(this);
                }
            }
        });
        const __exports = __callInstance42.exports;
        return __exports.data();
    })();
    (() => {
        const __ifInstance13 = new WebAssembly.Instance(__ifWasmModule, {
            env: {
                impFunc1: () => {
                    (() => {
                        const __callInstance41 = new WebAssembly.Instance(__callWasmModule, {
                            env: {
                                impFunc: () => {
                                    this.scale.removeConstraint(this);
                                }
                            }
                        });
                        const __exports = __callInstance41.exports;
                        return __exports.data();
                    })();
                },
                impFunc2: () => {
                }
            }
        });
        const __exports = __ifInstance13.exports;
        return __exports.data(this.scale != null ? 1 : 0);
    })();
    (() => {
        const __ifInstance14 = new WebAssembly.Instance(__ifWasmModule, {
            env: {
                impFunc1: () => {
                    (() => {
                        const __callInstance40 = new WebAssembly.Instance(__callWasmModule, {
                            env: {
                                impFunc: () => {
                                    this.offset.removeConstraint(this);
                                }
                            }
                        });
                        const __exports = __callInstance40.exports;
                        return __exports.data();
                    })();
                },
                impFunc2: () => {
                }
            }
        });
        const __exports = __ifInstance14.exports;
        return __exports.data(this.offset != null ? 1 : 0);
    })();
};
ScaleConstraint.prototype.markInputs = function (mark) {
    (() => {
        const __callInstance39 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    ScaleConstraint.superConstructor.prototype.markInputs.call(this, mark);
                }
            }
        });
        const __exports = __callInstance39.exports;
        return __exports.data();
    })();
    this.scale.mark = this.offset.mark = mark;
};
ScaleConstraint.prototype.execute = function () {
    (() => {
        const __ifInstance15 = new WebAssembly.Instance(__ifWasmModule, {
            env: {
                impFunc1: () => {
                    {
                        this.v2.value = this.v1.value * this.scale.value + this.offset.value;
                    }
                },
                impFunc2: () => {
                    {
                        this.v1.value = (this.v2.value - this.offset.value) / this.scale.value;
                    }
                }
            }
        });
        const __exports = __ifInstance15.exports;
        return __exports.data(this.direction == Direction.FORWARD ? 1 : 0);
    })();
};
ScaleConstraint.prototype.recalculate = function () {
    var ihn = this.input(), out = this.output();
    out.walkStrength = Strength.weakestOf(this.strength, ihn.walkStrength);
    out.stay = ihn.stay && this.scale.stay && this.offset.stay;
    (() => {
        const __ifInstance16 = new WebAssembly.Instance(__ifWasmModule, {
            env: {
                impFunc1: () => {
                    (() => {
                        const __callInstance38 = new WebAssembly.Instance(__callWasmModule, {
                            env: {
                                impFunc: () => {
                                    this.execute();
                                }
                            }
                        });
                        const __exports = __callInstance38.exports;
                        return __exports.data();
                    })();
                },
                impFunc2: () => {
                }
            }
        });
        const __exports = __ifInstance16.exports;
        return __exports.data(out.stay ? 1 : 0);
    })();
};
function EqualityConstraint(var1, var2, strength) {
    (() => {
        const __callInstance37 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    EqualityConstraint.superConstructor.call(this, var1, var2, strength);
                }
            }
        });
        const __exports = __callInstance37.exports;
        return __exports.data();
    })();
}
(() => {
    const __callInstance36 = new WebAssembly.Instance(__callWasmModule, {
        env: {
            impFunc: () => {
                EqualityConstraint.inheritsFrom(BinaryConstraint);
            }
        }
    });
    const __exports = __callInstance36.exports;
    return __exports.data();
})();
EqualityConstraint.prototype.execute = function () {
    this.output().value = this.input().value;
};
function Variable(name, initialValue) {
    this.value = initialValue || 0;
    this.constraints = new OrderedCollection();
    this.determinedBy = null;
    this.mark = 0;
    this.walkStrength = Strength.WEAKEST;
    this.stay = true;
    this.name = name;
}
Variable.prototype.addConstraint = function (c) {
    (() => {
        const __callInstance35 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    this.constraints.add(c);
                }
            }
        });
        const __exports = __callInstance35.exports;
        return __exports.data();
    })();
};
Variable.prototype.removeConstraint = function (c) {
    (() => {
        const __callInstance34 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    this.constraints.remove(c);
                }
            }
        });
        const __exports = __callInstance34.exports;
        return __exports.data();
    })();
    (() => {
        const __ifInstance17 = new WebAssembly.Instance(__ifWasmModule, {
            env: {
                impFunc1: () => {
                    this.determinedBy = null;
                },
                impFunc2: () => {
                }
            }
        });
        const __exports = __ifInstance17.exports;
        return __exports.data(this.determinedBy == c ? 1 : 0);
    })();
};
function Planner() {
    this.currentMark = 0;
}
Planner.prototype.incrementalAdd = function (c) {
    var mark = this.newMark();
    var overridden = c.satisfy(mark);
    (() => {
        const __forInstance14 = new WebAssembly.Instance(__forWasmModule, {
            env: {
                test: () => {
                    return overridden != null ? 1 : 0;
                },
                update: () => {
                },
                body: () => {
                    overridden = overridden.satisfy(mark);
                }
            }
        });
        const __exports = __forInstance14.exports;
        return __exports.data();
    })();
};
Planner.prototype.incrementalRemove = function (c) {
    var out = c.output();
    (() => {
        const __callInstance33 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    c.markUnsatisfied();
                }
            }
        });
        const __exports = __callInstance33.exports;
        return __exports.data();
    })();
    (() => {
        const __callInstance32 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    c.removeFromGraph();
                }
            }
        });
        const __exports = __callInstance32.exports;
        return __exports.data();
    })();
    var unsatisfied = this.removePropagateFrom(out);
    var strength = Strength.REQUIRED;
    do {
        (() => {
            var i = 0;
            const __forInstance2 = new WebAssembly.Instance(__forWasmModule, {
                env: {
                    test: () => {
                        return i < unsatisfied.size() ? 1 : 0;
                    },
                    update: () => {
                        i++;
                    },
                    body: () => {
                        {
                            var u = unsatisfied.at(i);
                            (() => {
                                const __ifInstance18 = new WebAssembly.Instance(__ifWasmModule, {
                                    env: {
                                        impFunc1: () => {
                                            (() => {
                                                const __callInstance31 = new WebAssembly.Instance(__callWasmModule, {
                                                    env: {
                                                        impFunc: () => {
                                                            this.incrementalAdd(u);
                                                        }
                                                    }
                                                });
                                                const __exports = __callInstance31.exports;
                                                return __exports.data();
                                            })();
                                        },
                                        impFunc2: () => {
                                        }
                                    }
                                });
                                const __exports = __ifInstance18.exports;
                                return __exports.data(u.strength == strength ? 1 : 0);
                            })();
                        }
                    }
                }
            });
            const __exports = __forInstance2.exports;
            return __exports.data();
        })();
        strength = strength.nextWeaker();
    } while (strength != Strength.WEAKEST);
};
Planner.prototype.newMark = function () {
    return ++this.currentMark;
};
Planner.prototype.makePlan = function (sources) {
    var mark = this.newMark();
    var plan = new Plan();
    var todo = sources;
    (() => {
        const __forInstance15 = new WebAssembly.Instance(__forWasmModule, {
            env: {
                test: () => {
                    return todo.size() > 0 ? 1 : 0;
                },
                update: () => {
                },
                body: () => {
                    {
                        var c = todo.removeFirst();
                        (() => {
                            const __ifInstance19 = new WebAssembly.Instance(__ifWasmModule, {
                                env: {
                                    impFunc1: () => {
                                        {
                                            (() => {
                                                const __callInstance30 = new WebAssembly.Instance(__callWasmModule, {
                                                    env: {
                                                        impFunc: () => {
                                                            plan.addConstraint(c);
                                                        }
                                                    }
                                                });
                                                const __exports = __callInstance30.exports;
                                                return __exports.data();
                                            })();
                                            c.output().mark = mark;
                                            (() => {
                                                const __callInstance29 = new WebAssembly.Instance(__callWasmModule, {
                                                    env: {
                                                        impFunc: () => {
                                                            this.addConstraintsConsumingTo(c.output(), todo);
                                                        }
                                                    }
                                                });
                                                const __exports = __callInstance29.exports;
                                                return __exports.data();
                                            })();
                                        }
                                    },
                                    impFunc2: () => {
                                    }
                                }
                            });
                            const __exports = __ifInstance19.exports;
                            return __exports.data(c.output().mark != mark && c.inputsKnown(mark) ? 1 : 0);
                        })();
                    }
                }
            }
        });
        const __exports = __forInstance15.exports;
        return __exports.data();
    })();
    return plan;
};
Planner.prototype.extractPlanFromConstraints = function (constraints) {
    var sources = new OrderedCollection();
    (() => {
        var i = 0;
        const __forInstance3 = new WebAssembly.Instance(__forWasmModule, {
            env: {
                test: () => {
                    return i < constraints.size() ? 1 : 0;
                },
                update: () => {
                    i++;
                },
                body: () => {
                    {
                        var c = constraints.at(i);
                        (() => {
                            const __ifInstance20 = new WebAssembly.Instance(__ifWasmModule, {
                                env: {
                                    impFunc1: () => {
                                        (() => {
                                            const __callInstance28 = new WebAssembly.Instance(__callWasmModule, {
                                                env: {
                                                    impFunc: () => {
                                                        sources.add(c);
                                                    }
                                                }
                                            });
                                            const __exports = __callInstance28.exports;
                                            return __exports.data();
                                        })();
                                    },
                                    impFunc2: () => {
                                    }
                                }
                            });
                            const __exports = __ifInstance20.exports;
                            return __exports.data(c.isInput() && c.isSatisfied() ? 1 : 0);
                        })();
                    }
                }
            }
        });
        const __exports = __forInstance3.exports;
        return __exports.data();
    })();
    return this.makePlan(sources);
};
Planner.prototype.addPropagate = function (c, mark) {
    var todo = new OrderedCollection();
    (() => {
        const __callInstance27 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    todo.add(c);
                }
            }
        });
        const __exports = __callInstance27.exports;
        return __exports.data();
    })();
    while (todo.size() > 0) {
        var d = todo.removeFirst();
        if (d.output().mark == mark) {
            (() => {
                const __callInstance26 = new WebAssembly.Instance(__callWasmModule, {
                    env: {
                        impFunc: () => {
                            this.incrementalRemove(c);
                        }
                    }
                });
                const __exports = __callInstance26.exports;
                return __exports.data();
            })();
            return false;
        }
        (() => {
            const __callInstance25 = new WebAssembly.Instance(__callWasmModule, {
                env: {
                    impFunc: () => {
                        d.recalculate();
                    }
                }
            });
            const __exports = __callInstance25.exports;
            return __exports.data();
        })();
        (() => {
            const __callInstance24 = new WebAssembly.Instance(__callWasmModule, {
                env: {
                    impFunc: () => {
                        this.addConstraintsConsumingTo(d.output(), todo);
                    }
                }
            });
            const __exports = __callInstance24.exports;
            return __exports.data();
        })();
    }
    return true;
};
Planner.prototype.removePropagateFrom = function (out) {
    out.determinedBy = null;
    out.walkStrength = Strength.WEAKEST;
    out.stay = true;
    var unsatisfied = new OrderedCollection();
    var todo = new OrderedCollection();
    (() => {
        const __callInstance23 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    todo.add(out);
                }
            }
        });
        const __exports = __callInstance23.exports;
        return __exports.data();
    })();
    (() => {
        const __forInstance16 = new WebAssembly.Instance(__forWasmModule, {
            env: {
                test: () => {
                    return todo.size() > 0 ? 1 : 0;
                },
                update: () => {
                },
                body: () => {
                    {
                        var v = todo.removeFirst();
                        (() => {
                            var i = 0;
                            const __forInstance4 = new WebAssembly.Instance(__forWasmModule, {
                                env: {
                                    test: () => {
                                        return i < v.constraints.size() ? 1 : 0;
                                    },
                                    update: () => {
                                        i++;
                                    },
                                    body: () => {
                                        {
                                            var c = v.constraints.at(i);
                                            (() => {
                                                const __ifInstance21 = new WebAssembly.Instance(__ifWasmModule, {
                                                    env: {
                                                        impFunc1: () => {
                                                            (() => {
                                                                const __callInstance22 = new WebAssembly.Instance(__callWasmModule, {
                                                                    env: {
                                                                        impFunc: () => {
                                                                            unsatisfied.add(c);
                                                                        }
                                                                    }
                                                                });
                                                                const __exports = __callInstance22.exports;
                                                                return __exports.data();
                                                            })();
                                                        },
                                                        impFunc2: () => {
                                                        }
                                                    }
                                                });
                                                const __exports = __ifInstance21.exports;
                                                return __exports.data(!c.isSatisfied() ? 1 : 0);
                                            })();
                                        }
                                    }
                                }
                            });
                            const __exports = __forInstance4.exports;
                            return __exports.data();
                        })();
                        var determining = v.determinedBy;
                        (() => {
                            var i = 0;
                            const __forInstance5 = new WebAssembly.Instance(__forWasmModule, {
                                env: {
                                    test: () => {
                                        return i < v.constraints.size() ? 1 : 0;
                                    },
                                    update: () => {
                                        i++;
                                    },
                                    body: () => {
                                        {
                                            var next = v.constraints.at(i);
                                            (() => {
                                                const __ifInstance22 = new WebAssembly.Instance(__ifWasmModule, {
                                                    env: {
                                                        impFunc1: () => {
                                                            {
                                                                (() => {
                                                                    const __callInstance21 = new WebAssembly.Instance(__callWasmModule, {
                                                                        env: {
                                                                            impFunc: () => {
                                                                                next.recalculate();
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
                                                                                todo.add(next.output());
                                                                            }
                                                                        }
                                                                    });
                                                                    const __exports = __callInstance20.exports;
                                                                    return __exports.data();
                                                                })();
                                                            }
                                                        },
                                                        impFunc2: () => {
                                                        }
                                                    }
                                                });
                                                const __exports = __ifInstance22.exports;
                                                return __exports.data(next != determining && next.isSatisfied() ? 1 : 0);
                                            })();
                                        }
                                    }
                                }
                            });
                            const __exports = __forInstance5.exports;
                            return __exports.data();
                        })();
                    }
                }
            }
        });
        const __exports = __forInstance16.exports;
        return __exports.data();
    })();
    return unsatisfied;
};
Planner.prototype.addConstraintsConsumingTo = function (v, coll) {
    var determining = v.determinedBy;
    var cc = v.constraints;
    (() => {
        var i = 0;
        const __forInstance6 = new WebAssembly.Instance(__forWasmModule, {
            env: {
                test: () => {
                    return i < cc.size() ? 1 : 0;
                },
                update: () => {
                    i++;
                },
                body: () => {
                    {
                        var c = cc.at(i);
                        (() => {
                            const __ifInstance23 = new WebAssembly.Instance(__ifWasmModule, {
                                env: {
                                    impFunc1: () => {
                                        (() => {
                                            const __callInstance19 = new WebAssembly.Instance(__callWasmModule, {
                                                env: {
                                                    impFunc: () => {
                                                        coll.add(c);
                                                    }
                                                }
                                            });
                                            const __exports = __callInstance19.exports;
                                            return __exports.data();
                                        })();
                                    },
                                    impFunc2: () => {
                                    }
                                }
                            });
                            const __exports = __ifInstance23.exports;
                            return __exports.data(c != determining && c.isSatisfied() ? 1 : 0);
                        })();
                    }
                }
            }
        });
        const __exports = __forInstance6.exports;
        return __exports.data();
    })();
};
function Plan() {
    this.v = new OrderedCollection();
}
Plan.prototype.addConstraint = function (c) {
    (() => {
        const __callInstance18 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    this.v.add(c);
                }
            }
        });
        const __exports = __callInstance18.exports;
        return __exports.data();
    })();
};
Plan.prototype.size = function () {
    return this.v.size();
};
Plan.prototype.constraintAt = function (index) {
    return this.v.at(index);
};
Plan.prototype.execute = function () {
    (() => {
        var i = 0;
        const __forInstance7 = new WebAssembly.Instance(__forWasmModule, {
            env: {
                test: () => {
                    return i < this.size() ? 1 : 0;
                },
                update: () => {
                    i++;
                },
                body: () => {
                    {
                        var c = this.constraintAt(i);
                        (() => {
                            const __callInstance17 = new WebAssembly.Instance(__callWasmModule, {
                                env: {
                                    impFunc: () => {
                                        c.execute();
                                    }
                                }
                            });
                            const __exports = __callInstance17.exports;
                            return __exports.data();
                        })();
                    }
                }
            }
        });
        const __exports = __forInstance7.exports;
        return __exports.data();
    })();
};
function chainTest(n) {
    planner = new Planner();
    var prev = null, first = null, last = null;
    (() => {
        var i = 0;
        const __forInstance8 = new WebAssembly.Instance(__forWasmModule, {
            env: {
                test: () => {
                    return i <= n ? 1 : 0;
                },
                update: () => {
                    i++;
                },
                body: () => {
                    {
                        var name = lS(0, 12) + i;
                        var v = new Variable(name);
                        (() => {
                            const __ifInstance24 = new WebAssembly.Instance(__ifWasmModule, {
                                env: {
                                    impFunc1: () => {
                                        new EqualityConstraint(prev, v, Strength.REQUIRED);
                                    },
                                    impFunc2: () => {
                                    }
                                }
                            });
                            const __exports = __ifInstance24.exports;
                            return __exports.data(prev != null ? 1 : 0);
                        })();
                        (() => {
                            const __ifInstance25 = new WebAssembly.Instance(__ifWasmModule, {
                                env: {
                                    impFunc1: () => {
                                        first = v;
                                    },
                                    impFunc2: () => {
                                    }
                                }
                            });
                            const __exports = __ifInstance25.exports;
                            return __exports.data(i == 0 ? 1 : 0);
                        })();
                        (() => {
                            const __ifInstance26 = new WebAssembly.Instance(__ifWasmModule, {
                                env: {
                                    impFunc1: () => {
                                        last = v;
                                    },
                                    impFunc2: () => {
                                    }
                                }
                            });
                            const __exports = __ifInstance26.exports;
                            return __exports.data(i == n ? 1 : 0);
                        })();
                        prev = v;
                    }
                }
            }
        });
        const __exports = __forInstance8.exports;
        return __exports.data();
    })();
    new StayConstraint(last, Strength.STRONG_DEFAULT);
    var edit = new EditConstraint(first, Strength.PREFERRED);
    var edits = new OrderedCollection();
    (() => {
        const __callInstance16 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    edits.add(edit);
                }
            }
        });
        const __exports = __callInstance16.exports;
        return __exports.data();
    })();
    var plan = planner.extractPlanFromConstraints(edits);
    (() => {
        var i = 0;
        const __forInstance9 = new WebAssembly.Instance(__forWasmModule, {
            env: {
                test: () => {
                    return i < 100 ? 1 : 0;
                },
                update: () => {
                    i++;
                },
                body: () => {
                    {
                        first.value = i;
                        (() => {
                            const __callInstance15 = new WebAssembly.Instance(__callWasmModule, {
                                env: {
                                    impFunc: () => {
                                        plan.execute();
                                    }
                                }
                            });
                            const __exports = __callInstance15.exports;
                            return __exports.data();
                        })();
                        (() => {
                            const __ifInstance27 = new WebAssembly.Instance(__ifWasmModule, {
                                env: {
                                    impFunc1: () => {
                                        (() => {
                                            const __callInstance14 = new WebAssembly.Instance(__callWasmModule, {
                                                env: {
                                                    impFunc: () => {
                                                        alert(lS(0, 13));
                                                    }
                                                }
                                            });
                                            const __exports = __callInstance14.exports;
                                            return __exports.data();
                                        })();
                                    },
                                    impFunc2: () => {
                                    }
                                }
                            });
                            const __exports = __ifInstance27.exports;
                            return __exports.data(last.value != i ? 1 : 0);
                        })();
                    }
                }
            }
        });
        const __exports = __forInstance9.exports;
        return __exports.data();
    })();
}
function projectionTest(n) {
    planner = new Planner();
    var scale = new Variable(lS(0, 14), 10);
    var offset = new Variable(lS(0, 15), 1000);
    var src = null, dst = null;
    var dests = new OrderedCollection();
    (() => {
        var i = 0;
        const __forInstance10 = new WebAssembly.Instance(__forWasmModule, {
            env: {
                test: () => {
                    return i < n ? 1 : 0;
                },
                update: () => {
                    i++;
                },
                body: () => {
                    {
                        src = new Variable(lS(0, 16) + i, i);
                        dst = new Variable(lS(0, 17) + i, i);
                        (() => {
                            const __callInstance13 = new WebAssembly.Instance(__callWasmModule, {
                                env: {
                                    impFunc: () => {
                                        dests.add(dst);
                                    }
                                }
                            });
                            const __exports = __callInstance13.exports;
                            return __exports.data();
                        })();
                        new StayConstraint(src, Strength.NORMAL);
                        new ScaleConstraint(src, scale, offset, dst, Strength.REQUIRED);
                    }
                }
            }
        });
        const __exports = __forInstance10.exports;
        return __exports.data();
    })();
    (() => {
        const __callInstance12 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    change(src, 17);
                }
            }
        });
        const __exports = __callInstance12.exports;
        return __exports.data();
    })();
    (() => {
        const __ifInstance28 = new WebAssembly.Instance(__ifWasmModule, {
            env: {
                impFunc1: () => {
                    (() => {
                        const __callInstance11 = new WebAssembly.Instance(__callWasmModule, {
                            env: {
                                impFunc: () => {
                                    alert(lS(0, 18));
                                }
                            }
                        });
                        const __exports = __callInstance11.exports;
                        return __exports.data();
                    })();
                },
                impFunc2: () => {
                }
            }
        });
        const __exports = __ifInstance28.exports;
        return __exports.data(dst.value != 1170 ? 1 : 0);
    })();
    (() => {
        const __callInstance10 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    change(dst, 1050);
                }
            }
        });
        const __exports = __callInstance10.exports;
        return __exports.data();
    })();
    (() => {
        const __ifInstance29 = new WebAssembly.Instance(__ifWasmModule, {
            env: {
                impFunc1: () => {
                    (() => {
                        const __callInstance9 = new WebAssembly.Instance(__callWasmModule, {
                            env: {
                                impFunc: () => {
                                    alert(lS(0, 19));
                                }
                            }
                        });
                        const __exports = __callInstance9.exports;
                        return __exports.data();
                    })();
                },
                impFunc2: () => {
                }
            }
        });
        const __exports = __ifInstance29.exports;
        return __exports.data(src.value != 5 ? 1 : 0);
    })();
    (() => {
        const __callInstance8 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    change(scale, 5);
                }
            }
        });
        const __exports = __callInstance8.exports;
        return __exports.data();
    })();
    (() => {
        var i = 0;
        const __forInstance11 = new WebAssembly.Instance(__forWasmModule, {
            env: {
                test: () => {
                    return i < n - 1 ? 1 : 0;
                },
                update: () => {
                    i++;
                },
                body: () => {
                    {
                        (() => {
                            const __ifInstance30 = new WebAssembly.Instance(__ifWasmModule, {
                                env: {
                                    impFunc1: () => {
                                        (() => {
                                            const __callInstance7 = new WebAssembly.Instance(__callWasmModule, {
                                                env: {
                                                    impFunc: () => {
                                                        alert(lS(0, 20));
                                                    }
                                                }
                                            });
                                            const __exports = __callInstance7.exports;
                                            return __exports.data();
                                        })();
                                    },
                                    impFunc2: () => {
                                    }
                                }
                            });
                            const __exports = __ifInstance30.exports;
                            return __exports.data(dests.at(i).value != i * 5 + 1000 ? 1 : 0);
                        })();
                    }
                }
            }
        });
        const __exports = __forInstance11.exports;
        return __exports.data();
    })();
    (() => {
        const __callInstance6 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    change(offset, 2000);
                }
            }
        });
        const __exports = __callInstance6.exports;
        return __exports.data();
    })();
    (() => {
        var i = 0;
        const __forInstance12 = new WebAssembly.Instance(__forWasmModule, {
            env: {
                test: () => {
                    return i < n - 1 ? 1 : 0;
                },
                update: () => {
                    i++;
                },
                body: () => {
                    {
                        (() => {
                            const __ifInstance31 = new WebAssembly.Instance(__ifWasmModule, {
                                env: {
                                    impFunc1: () => {
                                        (() => {
                                            const __callInstance5 = new WebAssembly.Instance(__callWasmModule, {
                                                env: {
                                                    impFunc: () => {
                                                        alert(lS(0, 21));
                                                    }
                                                }
                                            });
                                            const __exports = __callInstance5.exports;
                                            return __exports.data();
                                        })();
                                    },
                                    impFunc2: () => {
                                    }
                                }
                            });
                            const __exports = __ifInstance31.exports;
                            return __exports.data(dests.at(i).value != i * 5 + 2000 ? 1 : 0);
                        })();
                    }
                }
            }
        });
        const __exports = __forInstance12.exports;
        return __exports.data();
    })();
}
function change(v, newValue) {
    var edit = new EditConstraint(v, Strength.PREFERRED);
    var edits = new OrderedCollection();
    (() => {
        const __callInstance4 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    edits.add(edit);
                }
            }
        });
        const __exports = __callInstance4.exports;
        return __exports.data();
    })();
    var plan = planner.extractPlanFromConstraints(edits);
    (() => {
        var i = 0;
        const __forInstance13 = new WebAssembly.Instance(__forWasmModule, {
            env: {
                test: () => {
                    return i < 10 ? 1 : 0;
                },
                update: () => {
                    i++;
                },
                body: () => {
                    {
                        v.value = newValue;
                        (() => {
                            const __callInstance3 = new WebAssembly.Instance(__callWasmModule, {
                                env: {
                                    impFunc: () => {
                                        plan.execute();
                                    }
                                }
                            });
                            const __exports = __callInstance3.exports;
                            return __exports.data();
                        })();
                    }
                }
            }
        });
        const __exports = __forInstance13.exports;
        return __exports.data();
    })();
    (() => {
        const __callInstance2 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    edit.destroyConstraint();
                }
            }
        });
        const __exports = __callInstance2.exports;
        return __exports.data();
    })();
}
var planner = null;
function deltaBlue() {
    (() => {
        const __callInstance1 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    chainTest(100);
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
                    projectionTest(100);
                }
            }
        });
        const __exports = __callInstance0.exports;
        return __exports.data();
    })();
}