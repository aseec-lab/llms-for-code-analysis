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
const __aB = 'AGFzbQEAAAABiYCAgAACYAAAYAJ/fwADg4CAgAACAQAFg4CAgAABAAEGhoCAgAABfwFBAAsHkYCAgAACBm1lbW9yeQIABGFycjAAAQqqgICAAAKPgICAAAAjACAAQQRsaiABNgIAC5CAgIAAAQF/QRAkAEEAQZWVLRAACw==', __wAM = new WebAssembly.Instance(new WebAssembly.Module((() => {
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
const __wasmStringModules = ['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'].map(__bytes => {
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
var RayTrace = new BenchmarkSuite(lS(0, 0), __lA(0, 16, 20), [new Benchmark(lS(0, 1), true, false, 600, renderScene)]);
var checkNumber;
var Class = {
    create: function () {
        return function () {
            (() => {
                const __callInstance10 = new WebAssembly.Instance(__callWasmModule, {
                    env: {
                        impFunc: () => {
                            this.initialize.apply(this, arguments);
                        }
                    }
                });
                const __exports = __callInstance10.exports;
                return __exports.data();
            })();
        };
    }
};
Object.extend = function (destination, source) {
    for (var property in source) {
        destination[property] = source[property];
    }
    return destination;
};
(() => {
    const __ifInstance0 = new WebAssembly.Instance(__ifWasmModule, {
        env: {
            impFunc1: () => {
                var Flog = {};
            },
            impFunc2: () => {
            }
        }
    });
    const __exports = __ifInstance0.exports;
    return __exports.data(typeof Flog == lS(0, 2) ? 1 : 0);
})();
(() => {
    const __ifInstance1 = new WebAssembly.Instance(__ifWasmModule, {
        env: {
            impFunc1: () => {
                Flog.RayTracer = {};
            },
            impFunc2: () => {
            }
        }
    });
    const __exports = __ifInstance1.exports;
    return __exports.data(typeof Flog.RayTracer == lS(0, 3) ? 1 : 0);
})();
Flog.RayTracer.Color = Class.create();
Flog.RayTracer.Color.prototype = {
    red: 0,
    green: 0,
    blue: 0,
    initialize: function (r, g, b) {
        (() => {
            const __ifInstance2 = new WebAssembly.Instance(__ifWasmModule, {
                env: {
                    impFunc1: () => {
                        r = 0;
                    },
                    impFunc2: () => {
                    }
                }
            });
            const __exports = __ifInstance2.exports;
            return __exports.data(!r ? 1 : 0);
        })();
        (() => {
            const __ifInstance3 = new WebAssembly.Instance(__ifWasmModule, {
                env: {
                    impFunc1: () => {
                        g = 0;
                    },
                    impFunc2: () => {
                    }
                }
            });
            const __exports = __ifInstance3.exports;
            return __exports.data(!g ? 1 : 0);
        })();
        (() => {
            const __ifInstance4 = new WebAssembly.Instance(__ifWasmModule, {
                env: {
                    impFunc1: () => {
                        b = 0;
                    },
                    impFunc2: () => {
                    }
                }
            });
            const __exports = __ifInstance4.exports;
            return __exports.data(!b ? 1 : 0);
        })();
        this.red = r;
        this.green = g;
        this.blue = b;
    },
    add: function (c1, c2) {
        var result = new Flog.RayTracer.Color(0, 0, 0);
        result.red = c1.red + c2.red;
        result.green = c1.green + c2.green;
        result.blue = c1.blue + c2.blue;
        return result;
    },
    addScalar: function (c1, s) {
        var result = new Flog.RayTracer.Color(0, 0, 0);
        result.red = c1.red + s;
        result.green = c1.green + s;
        result.blue = c1.blue + s;
        (() => {
            const __callInstance9 = new WebAssembly.Instance(__callWasmModule, {
                env: {
                    impFunc: () => {
                        result.limit();
                    }
                }
            });
            const __exports = __callInstance9.exports;
            return __exports.data();
        })();
        return result;
    },
    subtract: function (c1, c2) {
        var result = new Flog.RayTracer.Color(0, 0, 0);
        result.red = c1.red - c2.red;
        result.green = c1.green - c2.green;
        result.blue = c1.blue - c2.blue;
        return result;
    },
    multiply: function (c1, c2) {
        var result = new Flog.RayTracer.Color(0, 0, 0);
        result.red = c1.red * c2.red;
        result.green = c1.green * c2.green;
        result.blue = c1.blue * c2.blue;
        return result;
    },
    multiplyScalar: function (c1, f) {
        var result = new Flog.RayTracer.Color(0, 0, 0);
        result.red = c1.red * f;
        result.green = c1.green * f;
        result.blue = c1.blue * f;
        return result;
    },
    divideFactor: function (c1, f) {
        var result = new Flog.RayTracer.Color(0, 0, 0);
        result.red = c1.red / f;
        result.green = c1.green / f;
        result.blue = c1.blue / f;
        return result;
    },
    limit: function () {
        this.red = this.red > 0 ? this.red > 1 ? 1 : this.red : 0;
        this.green = this.green > 0 ? this.green > 1 ? 1 : this.green : 0;
        this.blue = this.blue > 0 ? this.blue > 1 ? 1 : this.blue : 0;
    },
    distance: function (color) {
        var d = Math.abs(this.red - color.red) + Math.abs(this.green - color.green) + Math.abs(this.blue - color.blue);
        return d;
    },
    blend: function (c1, c2, w) {
        var result = new Flog.RayTracer.Color(0, 0, 0);
        result = Flog.RayTracer.Color.prototype.add(Flog.RayTracer.Color.prototype.multiplyScalar(c1, 1 - w), Flog.RayTracer.Color.prototype.multiplyScalar(c2, w));
        return result;
    },
    brightness: function () {
        var r = Math.floor(this.red * 255);
        var g = Math.floor(this.green * 255);
        var b = Math.floor(this.blue * 255);
        return r * 77 + g * 150 + b * 29 >> 8;
    },
    toString: function () {
        var r = Math.floor(this.red * 255);
        var g = Math.floor(this.green * 255);
        var b = Math.floor(this.blue * 255);
        return lS(0, 4) + r + lS(0, 5) + g + lS(0, 6) + b + lS(0, 7);
    }
};
(() => {
    const __ifInstance5 = new WebAssembly.Instance(__ifWasmModule, {
        env: {
            impFunc1: () => {
                var Flog = {};
            },
            impFunc2: () => {
            }
        }
    });
    const __exports = __ifInstance5.exports;
    return __exports.data(typeof Flog == lS(0, 8) ? 1 : 0);
})();
(() => {
    const __ifInstance6 = new WebAssembly.Instance(__ifWasmModule, {
        env: {
            impFunc1: () => {
                Flog.RayTracer = {};
            },
            impFunc2: () => {
            }
        }
    });
    const __exports = __ifInstance6.exports;
    return __exports.data(typeof Flog.RayTracer == lS(0, 9) ? 1 : 0);
})();
Flog.RayTracer.Light = Class.create();
Flog.RayTracer.Light.prototype = {
    position: null,
    color: null,
    intensity: 10,
    initialize: function (pos, color, intensity) {
        this.position = pos;
        this.color = color;
        this.intensity = intensity ? intensity : 10;
    },
    toString: function () {
        return lS(0, 10) + this.position.x + lS(0, 11) + this.position.y + lS(0, 12) + this.position.z + lS(0, 13);
    }
};
(() => {
    const __ifInstance7 = new WebAssembly.Instance(__ifWasmModule, {
        env: {
            impFunc1: () => {
                var Flog = {};
            },
            impFunc2: () => {
            }
        }
    });
    const __exports = __ifInstance7.exports;
    return __exports.data(typeof Flog == lS(0, 14) ? 1 : 0);
})();
(() => {
    const __ifInstance8 = new WebAssembly.Instance(__ifWasmModule, {
        env: {
            impFunc1: () => {
                Flog.RayTracer = {};
            },
            impFunc2: () => {
            }
        }
    });
    const __exports = __ifInstance8.exports;
    return __exports.data(typeof Flog.RayTracer == lS(0, 15) ? 1 : 0);
})();
Flog.RayTracer.Vector = Class.create();
Flog.RayTracer.Vector.prototype = {
    x: 0,
    y: 0,
    z: 0,
    initialize: function (x, y, z) {
        this.x = x ? x : 0;
        this.y = y ? y : 0;
        this.z = z ? z : 0;
    },
    copy: function (vector) {
        this.x = vector.x;
        this.y = vector.y;
        this.z = vector.z;
    },
    normalize: function () {
        var m = this.magnitude();
        return new Flog.RayTracer.Vector(this.x / m, this.y / m, this.z / m);
    },
    magnitude: function () {
        return Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
    },
    cross: function (w) {
        return new Flog.RayTracer.Vector(-this.z * w.y + this.y * w.z, this.z * w.x - this.x * w.z, -this.y * w.x + this.x * w.y);
    },
    dot: function (w) {
        return this.x * w.x + this.y * w.y + this.z * w.z;
    },
    add: function (v, w) {
        return new Flog.RayTracer.Vector(w.x + v.x, w.y + v.y, w.z + v.z);
    },
    subtract: function (v, w) {
        if (!w || !v)
            throw lS(0, 16) + v + lS(0, 17) + w + lS(0, 18);
        return new Flog.RayTracer.Vector(v.x - w.x, v.y - w.y, v.z - w.z);
    },
    multiplyVector: function (v, w) {
        return new Flog.RayTracer.Vector(v.x * w.x, v.y * w.y, v.z * w.z);
    },
    multiplyScalar: function (v, w) {
        return new Flog.RayTracer.Vector(v.x * w, v.y * w, v.z * w);
    },
    toString: function () {
        return lS(0, 19) + this.x + lS(0, 20) + this.y + lS(0, 21) + this.z + lS(0, 22);
    }
};
(() => {
    const __ifInstance9 = new WebAssembly.Instance(__ifWasmModule, {
        env: {
            impFunc1: () => {
                var Flog = {};
            },
            impFunc2: () => {
            }
        }
    });
    const __exports = __ifInstance9.exports;
    return __exports.data(typeof Flog == lS(0, 23) ? 1 : 0);
})();
(() => {
    const __ifInstance10 = new WebAssembly.Instance(__ifWasmModule, {
        env: {
            impFunc1: () => {
                Flog.RayTracer = {};
            },
            impFunc2: () => {
            }
        }
    });
    const __exports = __ifInstance10.exports;
    return __exports.data(typeof Flog.RayTracer == lS(0, 24) ? 1 : 0);
})();
Flog.RayTracer.Ray = Class.create();
Flog.RayTracer.Ray.prototype = {
    position: null,
    direction: null,
    initialize: function (pos, dir) {
        this.position = pos;
        this.direction = dir;
    },
    toString: function () {
        return lS(0, 25) + this.position + lS(0, 26) + this.direction + lS(0, 27);
    }
};
(() => {
    const __ifInstance11 = new WebAssembly.Instance(__ifWasmModule, {
        env: {
            impFunc1: () => {
                var Flog = {};
            },
            impFunc2: () => {
            }
        }
    });
    const __exports = __ifInstance11.exports;
    return __exports.data(typeof Flog == lS(0, 28) ? 1 : 0);
})();
(() => {
    const __ifInstance12 = new WebAssembly.Instance(__ifWasmModule, {
        env: {
            impFunc1: () => {
                Flog.RayTracer = {};
            },
            impFunc2: () => {
            }
        }
    });
    const __exports = __ifInstance12.exports;
    return __exports.data(typeof Flog.RayTracer == lS(0, 29) ? 1 : 0);
})();
Flog.RayTracer.Scene = Class.create();
Flog.RayTracer.Scene.prototype = {
    camera: null,
    shapes: [],
    lights: [],
    background: null,
    initialize: function () {
        this.camera = new Flog.RayTracer.Camera(new Flog.RayTracer.Vector(0, 0, -5), new Flog.RayTracer.Vector(0, 0, 1), new Flog.RayTracer.Vector(0, 1, 0));
        this.shapes = new Array();
        this.lights = new Array();
        this.background = new Flog.RayTracer.Background(new Flog.RayTracer.Color(0, 0, 0.5), 0.2);
    }
};
(() => {
    const __ifInstance13 = new WebAssembly.Instance(__ifWasmModule, {
        env: {
            impFunc1: () => {
                var Flog = {};
            },
            impFunc2: () => {
            }
        }
    });
    const __exports = __ifInstance13.exports;
    return __exports.data(typeof Flog == lS(0, 30) ? 1 : 0);
})();
(() => {
    const __ifInstance14 = new WebAssembly.Instance(__ifWasmModule, {
        env: {
            impFunc1: () => {
                Flog.RayTracer = {};
            },
            impFunc2: () => {
            }
        }
    });
    const __exports = __ifInstance14.exports;
    return __exports.data(typeof Flog.RayTracer == lS(0, 31) ? 1 : 0);
})();
(() => {
    const __ifInstance15 = new WebAssembly.Instance(__ifWasmModule, {
        env: {
            impFunc1: () => {
                Flog.RayTracer.Material = {};
            },
            impFunc2: () => {
            }
        }
    });
    const __exports = __ifInstance15.exports;
    return __exports.data(typeof Flog.RayTracer.Material == lS(0, 32) ? 1 : 0);
})();
Flog.RayTracer.Material.BaseMaterial = Class.create();
Flog.RayTracer.Material.BaseMaterial.prototype = {
    gloss: 2,
    transparency: 0,
    reflection: 0,
    refraction: 0.5,
    hasTexture: false,
    initialize: function () {
    },
    getColor: function (u, v) {
    },
    wrapUp: function (t) {
        t = t % 2;
        (() => {
            const __ifInstance16 = new WebAssembly.Instance(__ifWasmModule, {
                env: {
                    impFunc1: () => {
                        t += 2;
                    },
                    impFunc2: () => {
                    }
                }
            });
            const __exports = __ifInstance16.exports;
            return __exports.data(t < -1 ? 1 : 0);
        })();
        (() => {
            const __ifInstance17 = new WebAssembly.Instance(__ifWasmModule, {
                env: {
                    impFunc1: () => {
                        t -= 2;
                    },
                    impFunc2: () => {
                    }
                }
            });
            const __exports = __ifInstance17.exports;
            return __exports.data(t >= 1 ? 1 : 0);
        })();
        return t;
    },
    toString: function () {
        return lS(0, 33) + this.gloss + lS(0, 34) + this.transparency + lS(0, 35) + this.hasTexture + lS(0, 36);
    }
};
(() => {
    const __ifInstance18 = new WebAssembly.Instance(__ifWasmModule, {
        env: {
            impFunc1: () => {
                var Flog = {};
            },
            impFunc2: () => {
            }
        }
    });
    const __exports = __ifInstance18.exports;
    return __exports.data(typeof Flog == lS(0, 37) ? 1 : 0);
})();
(() => {
    const __ifInstance19 = new WebAssembly.Instance(__ifWasmModule, {
        env: {
            impFunc1: () => {
                Flog.RayTracer = {};
            },
            impFunc2: () => {
            }
        }
    });
    const __exports = __ifInstance19.exports;
    return __exports.data(typeof Flog.RayTracer == lS(0, 38) ? 1 : 0);
})();
Flog.RayTracer.Material.Solid = Class.create();
Flog.RayTracer.Material.Solid.prototype = Object.extend(new Flog.RayTracer.Material.BaseMaterial(), {
    initialize: function (color, reflection, refraction, transparency, gloss) {
        this.color = color;
        this.reflection = reflection;
        this.transparency = transparency;
        this.gloss = gloss;
        this.hasTexture = false;
    },
    getColor: function (u, v) {
        return this.color;
    },
    toString: function () {
        return lS(0, 39) + this.gloss + lS(0, 40) + this.transparency + lS(0, 41) + this.hasTexture + lS(0, 42);
    }
});
(() => {
    const __ifInstance20 = new WebAssembly.Instance(__ifWasmModule, {
        env: {
            impFunc1: () => {
                var Flog = {};
            },
            impFunc2: () => {
            }
        }
    });
    const __exports = __ifInstance20.exports;
    return __exports.data(typeof Flog == lS(0, 43) ? 1 : 0);
})();
(() => {
    const __ifInstance21 = new WebAssembly.Instance(__ifWasmModule, {
        env: {
            impFunc1: () => {
                Flog.RayTracer = {};
            },
            impFunc2: () => {
            }
        }
    });
    const __exports = __ifInstance21.exports;
    return __exports.data(typeof Flog.RayTracer == lS(0, 44) ? 1 : 0);
})();
Flog.RayTracer.Material.Chessboard = Class.create();
Flog.RayTracer.Material.Chessboard.prototype = Object.extend(new Flog.RayTracer.Material.BaseMaterial(), {
    colorEven: null,
    colorOdd: null,
    density: 0.5,
    initialize: function (colorEven, colorOdd, reflection, transparency, gloss, density) {
        this.colorEven = colorEven;
        this.colorOdd = colorOdd;
        this.reflection = reflection;
        this.transparency = transparency;
        this.gloss = gloss;
        this.density = density;
        this.hasTexture = true;
    },
    getColor: function (u, v) {
        var t = this.wrapUp(u * this.density) * this.wrapUp(v * this.density);
        if (t < 0)
            return this.colorEven;
        else
            return this.colorOdd;
    },
    toString: function () {
        return lS(0, 45) + this.gloss + lS(0, 46) + this.transparency + lS(0, 47) + this.hasTexture + lS(0, 48);
    }
});
(() => {
    const __ifInstance22 = new WebAssembly.Instance(__ifWasmModule, {
        env: {
            impFunc1: () => {
                var Flog = {};
            },
            impFunc2: () => {
            }
        }
    });
    const __exports = __ifInstance22.exports;
    return __exports.data(typeof Flog == lS(0, 49) ? 1 : 0);
})();
(() => {
    const __ifInstance23 = new WebAssembly.Instance(__ifWasmModule, {
        env: {
            impFunc1: () => {
                Flog.RayTracer = {};
            },
            impFunc2: () => {
            }
        }
    });
    const __exports = __ifInstance23.exports;
    return __exports.data(typeof Flog.RayTracer == lS(0, 50) ? 1 : 0);
})();
(() => {
    const __ifInstance24 = new WebAssembly.Instance(__ifWasmModule, {
        env: {
            impFunc1: () => {
                Flog.RayTracer.Shape = {};
            },
            impFunc2: () => {
            }
        }
    });
    const __exports = __ifInstance24.exports;
    return __exports.data(typeof Flog.RayTracer.Shape == lS(0, 51) ? 1 : 0);
})();
Flog.RayTracer.Shape.Sphere = Class.create();
Flog.RayTracer.Shape.Sphere.prototype = {
    initialize: function (pos, radius, material) {
        this.radius = radius;
        this.position = pos;
        this.material = material;
    },
    intersect: function (ray) {
        var info = new Flog.RayTracer.IntersectionInfo();
        info.shape = this;
        var dst = Flog.RayTracer.Vector.prototype.subtract(ray.position, this.position);
        var B = dst.dot(ray.direction);
        var C = dst.dot(dst) - this.radius * this.radius;
        var D = B * B - C;
        (() => {
            const __ifInstance25 = new WebAssembly.Instance(__ifWasmModule, {
                env: {
                    impFunc1: () => {
                        {
                            info.isHit = true;
                            info.distance = -B - Math.sqrt(D);
                            info.position = Flog.RayTracer.Vector.prototype.add(ray.position, Flog.RayTracer.Vector.prototype.multiplyScalar(ray.direction, info.distance));
                            info.normal = Flog.RayTracer.Vector.prototype.subtract(info.position, this.position).normalize();
                            info.color = this.material.getColor(0, 0);
                        }
                    },
                    impFunc2: () => {
                        {
                            info.isHit = false;
                        }
                    }
                }
            });
            const __exports = __ifInstance25.exports;
            return __exports.data(D > 0 ? 1 : 0);
        })();
        return info;
    },
    toString: function () {
        return lS(0, 52) + this.position + lS(0, 53) + this.radius + lS(0, 54);
    }
};
(() => {
    const __ifInstance26 = new WebAssembly.Instance(__ifWasmModule, {
        env: {
            impFunc1: () => {
                var Flog = {};
            },
            impFunc2: () => {
            }
        }
    });
    const __exports = __ifInstance26.exports;
    return __exports.data(typeof Flog == lS(0, 55) ? 1 : 0);
})();
(() => {
    const __ifInstance27 = new WebAssembly.Instance(__ifWasmModule, {
        env: {
            impFunc1: () => {
                Flog.RayTracer = {};
            },
            impFunc2: () => {
            }
        }
    });
    const __exports = __ifInstance27.exports;
    return __exports.data(typeof Flog.RayTracer == lS(0, 56) ? 1 : 0);
})();
(() => {
    const __ifInstance28 = new WebAssembly.Instance(__ifWasmModule, {
        env: {
            impFunc1: () => {
                Flog.RayTracer.Shape = {};
            },
            impFunc2: () => {
            }
        }
    });
    const __exports = __ifInstance28.exports;
    return __exports.data(typeof Flog.RayTracer.Shape == lS(0, 57) ? 1 : 0);
})();
Flog.RayTracer.Shape.Plane = Class.create();
Flog.RayTracer.Shape.Plane.prototype = {
    d: 0,
    initialize: function (pos, d, material) {
        this.position = pos;
        this.d = d;
        this.material = material;
    },
    intersect: function (ray) {
        var info = new Flog.RayTracer.IntersectionInfo();
        var Vd = this.position.dot(ray.direction);
        if (Vd == 0)
            return info;
        var t = -(this.position.dot(ray.position) + this.d) / Vd;
        if (t <= 0)
            return info;
        info.shape = this;
        info.isHit = true;
        info.position = Flog.RayTracer.Vector.prototype.add(ray.position, Flog.RayTracer.Vector.prototype.multiplyScalar(ray.direction, t));
        info.normal = this.position;
        info.distance = t;
        (() => {
            const __ifInstance29 = new WebAssembly.Instance(__ifWasmModule, {
                env: {
                    impFunc1: () => {
                        {
                            var vU = new Flog.RayTracer.Vector(this.position.y, this.position.z, -this.position.x);
                            var vV = vU.cross(this.position);
                            var u = info.position.dot(vU);
                            var v = info.position.dot(vV);
                            info.color = this.material.getColor(u, v);
                        }
                    },
                    impFunc2: () => {
                        {
                            info.color = this.material.getColor(0, 0);
                        }
                    }
                }
            });
            const __exports = __ifInstance29.exports;
            return __exports.data(this.material.hasTexture ? 1 : 0);
        })();
        return info;
    },
    toString: function () {
        return lS(0, 58) + this.position + lS(0, 59) + this.d + lS(0, 60);
    }
};
(() => {
    const __ifInstance30 = new WebAssembly.Instance(__ifWasmModule, {
        env: {
            impFunc1: () => {
                var Flog = {};
            },
            impFunc2: () => {
            }
        }
    });
    const __exports = __ifInstance30.exports;
    return __exports.data(typeof Flog == lS(0, 61) ? 1 : 0);
})();
(() => {
    const __ifInstance31 = new WebAssembly.Instance(__ifWasmModule, {
        env: {
            impFunc1: () => {
                Flog.RayTracer = {};
            },
            impFunc2: () => {
            }
        }
    });
    const __exports = __ifInstance31.exports;
    return __exports.data(typeof Flog.RayTracer == lS(0, 62) ? 1 : 0);
})();
Flog.RayTracer.IntersectionInfo = Class.create();
Flog.RayTracer.IntersectionInfo.prototype = {
    isHit: false,
    hitCount: 0,
    shape: null,
    position: null,
    normal: null,
    color: null,
    distance: null,
    initialize: function () {
        this.color = new Flog.RayTracer.Color(0, 0, 0);
    },
    toString: function () {
        return lS(0, 63) + this.position + lS(0, 64);
    }
};
(() => {
    const __ifInstance32 = new WebAssembly.Instance(__ifWasmModule, {
        env: {
            impFunc1: () => {
                var Flog = {};
            },
            impFunc2: () => {
            }
        }
    });
    const __exports = __ifInstance32.exports;
    return __exports.data(typeof Flog == lS(0, 65) ? 1 : 0);
})();
(() => {
    const __ifInstance33 = new WebAssembly.Instance(__ifWasmModule, {
        env: {
            impFunc1: () => {
                Flog.RayTracer = {};
            },
            impFunc2: () => {
            }
        }
    });
    const __exports = __ifInstance33.exports;
    return __exports.data(typeof Flog.RayTracer == lS(0, 66) ? 1 : 0);
})();
Flog.RayTracer.Camera = Class.create();
Flog.RayTracer.Camera.prototype = {
    position: null,
    lookAt: null,
    equator: null,
    up: null,
    screen: null,
    initialize: function (pos, lookAt, up) {
        this.position = pos;
        this.lookAt = lookAt;
        this.up = up;
        this.equator = lookAt.normalize().cross(this.up);
        this.screen = Flog.RayTracer.Vector.prototype.add(this.position, this.lookAt);
    },
    getRay: function (vx, vy) {
        var pos = Flog.RayTracer.Vector.prototype.subtract(this.screen, Flog.RayTracer.Vector.prototype.subtract(Flog.RayTracer.Vector.prototype.multiplyScalar(this.equator, vx), Flog.RayTracer.Vector.prototype.multiplyScalar(this.up, vy)));
        pos.y = pos.y * -1;
        var dir = Flog.RayTracer.Vector.prototype.subtract(pos, this.position);
        var ray = new Flog.RayTracer.Ray(pos, dir.normalize());
        return ray;
    },
    toString: function () {
        return lS(0, 67);
    }
};
(() => {
    const __ifInstance34 = new WebAssembly.Instance(__ifWasmModule, {
        env: {
            impFunc1: () => {
                var Flog = {};
            },
            impFunc2: () => {
            }
        }
    });
    const __exports = __ifInstance34.exports;
    return __exports.data(typeof Flog == lS(0, 68) ? 1 : 0);
})();
(() => {
    const __ifInstance35 = new WebAssembly.Instance(__ifWasmModule, {
        env: {
            impFunc1: () => {
                Flog.RayTracer = {};
            },
            impFunc2: () => {
            }
        }
    });
    const __exports = __ifInstance35.exports;
    return __exports.data(typeof Flog.RayTracer == lS(0, 69) ? 1 : 0);
})();
Flog.RayTracer.Background = Class.create();
Flog.RayTracer.Background.prototype = {
    color: null,
    ambience: 0,
    initialize: function (color, ambience) {
        this.color = color;
        this.ambience = ambience;
    }
};
(() => {
    const __ifInstance36 = new WebAssembly.Instance(__ifWasmModule, {
        env: {
            impFunc1: () => {
                var Flog = {};
            },
            impFunc2: () => {
            }
        }
    });
    const __exports = __ifInstance36.exports;
    return __exports.data(typeof Flog == lS(0, 70) ? 1 : 0);
})();
(() => {
    const __ifInstance37 = new WebAssembly.Instance(__ifWasmModule, {
        env: {
            impFunc1: () => {
                Flog.RayTracer = {};
            },
            impFunc2: () => {
            }
        }
    });
    const __exports = __ifInstance37.exports;
    return __exports.data(typeof Flog.RayTracer == lS(0, 71) ? 1 : 0);
})();
Flog.RayTracer.Engine = Class.create();
Flog.RayTracer.Engine.prototype = {
    canvas: null,
    initialize: function (options) {
        this.options = Object.extend({
            canvasHeight: 100,
            canvasWidth: 100,
            pixelWidth: 2,
            pixelHeight: 2,
            renderDiffuse: false,
            renderShadows: false,
            renderHighlights: false,
            renderReflections: false,
            rayDepth: 2
        }, options || {});
        this.options.canvasHeight /= this.options.pixelHeight;
        this.options.canvasWidth /= this.options.pixelWidth;
    },
    setPixel: function (x, y, color) {
        var pxW, pxH;
        pxW = this.options.pixelWidth;
        pxH = this.options.pixelHeight;
        (() => {
            const __ifInstance38 = new WebAssembly.Instance(__ifWasmModule, {
                env: {
                    impFunc1: () => {
                        {
                            this.canvas.fillStyle = color.toString();
                            (() => {
                                const __callInstance8 = new WebAssembly.Instance(__callWasmModule, {
                                    env: {
                                        impFunc: () => {
                                            this.canvas.fillRect(x * pxW, y * pxH, pxW, pxH);
                                        }
                                    }
                                });
                                const __exports = __callInstance8.exports;
                                return __exports.data();
                            })();
                        }
                    },
                    impFunc2: () => {
                        {
                            (() => {
                                const __ifInstance39 = new WebAssembly.Instance(__ifWasmModule, {
                                    env: {
                                        impFunc1: () => {
                                            {
                                                checkNumber += color.brightness();
                                            }
                                        },
                                        impFunc2: () => {
                                        }
                                    }
                                });
                                const __exports = __ifInstance39.exports;
                                return __exports.data(x === y ? 1 : 0);
                            })();
                        }
                    }
                }
            });
            const __exports = __ifInstance38.exports;
            return __exports.data(this.canvas ? 1 : 0);
        })();
    },
    renderScene: function (scene, canvas) {
        checkNumber = 0;
        (() => {
            const __ifInstance40 = new WebAssembly.Instance(__ifWasmModule, {
                env: {
                    impFunc1: () => {
                        {
                            this.canvas = canvas.getContext(lS(0, 72));
                        }
                    },
                    impFunc2: () => {
                        {
                            this.canvas = null;
                        }
                    }
                }
            });
            const __exports = __ifInstance40.exports;
            return __exports.data(canvas ? 1 : 0);
        })();
        var canvasHeight = this.options.canvasHeight;
        var canvasWidth = this.options.canvasWidth;
        (() => {
            var y = 0;
            const __forInstance0 = new WebAssembly.Instance(__forWasmModule, {
                env: {
                    test: () => {
                        return y < canvasHeight ? 1 : 0;
                    },
                    update: () => {
                        y++;
                    },
                    body: () => {
                        {
                            (() => {
                                var x = 0;
                                const __forInstance1 = new WebAssembly.Instance(__forWasmModule, {
                                    env: {
                                        test: () => {
                                            return x < canvasWidth ? 1 : 0;
                                        },
                                        update: () => {
                                            x++;
                                        },
                                        body: () => {
                                            {
                                                var yp = y * 1 / canvasHeight * 2 - 1;
                                                var xp = x * 1 / canvasWidth * 2 - 1;
                                                var ray = scene.camera.getRay(xp, yp);
                                                var color = this.getPixelColor(ray, scene);
                                                (() => {
                                                    const __callInstance7 = new WebAssembly.Instance(__callWasmModule, {
                                                        env: {
                                                            impFunc: () => {
                                                                this.setPixel(x, y, color);
                                                            }
                                                        }
                                                    });
                                                    const __exports = __callInstance7.exports;
                                                    return __exports.data();
                                                })();
                                            }
                                        }
                                    }
                                });
                                const __exports = __forInstance1.exports;
                                return __exports.data();
                            })();
                        }
                    }
                }
            });
            const __exports = __forInstance0.exports;
            return __exports.data();
        })();
        if (checkNumber !== 2321) {
            throw new Error(lS(0, 73));
        }
    },
    getPixelColor: function (ray, scene) {
        var info = this.testIntersection(ray, scene, null);
        if (info.isHit) {
            var color = this.rayTrace(info, ray, scene, 0);
            return color;
        }
        return scene.background.color;
    },
    testIntersection: function (ray, scene, exclude) {
        var hits = 0;
        var best = new Flog.RayTracer.IntersectionInfo();
        best.distance = 2000;
        (() => {
            var i = 0;
            const __forInstance2 = new WebAssembly.Instance(__forWasmModule, {
                env: {
                    test: () => {
                        return i < scene.shapes.length ? 1 : 0;
                    },
                    update: () => {
                        i++;
                    },
                    body: () => {
                        {
                            var shape = scene.shapes[i];
                            (() => {
                                const __ifInstance41 = new WebAssembly.Instance(__ifWasmModule, {
                                    env: {
                                        impFunc1: () => {
                                            {
                                                var info = shape.intersect(ray);
                                                (() => {
                                                    const __ifInstance42 = new WebAssembly.Instance(__ifWasmModule, {
                                                        env: {
                                                            impFunc1: () => {
                                                                {
                                                                    best = info;
                                                                    hits++;
                                                                }
                                                            },
                                                            impFunc2: () => {
                                                            }
                                                        }
                                                    });
                                                    const __exports = __ifInstance42.exports;
                                                    return __exports.data(info.isHit && info.distance >= 0 && info.distance < best.distance ? 1 : 0);
                                                })();
                                            }
                                        },
                                        impFunc2: () => {
                                        }
                                    }
                                });
                                const __exports = __ifInstance41.exports;
                                return __exports.data(shape != exclude ? 1 : 0);
                            })();
                        }
                    }
                }
            });
            const __exports = __forInstance2.exports;
            return __exports.data();
        })();
        best.hitCount = hits;
        return best;
    },
    getReflectionRay: function (P, N, V) {
        var c1 = -N.dot(V);
        var R1 = Flog.RayTracer.Vector.prototype.add(Flog.RayTracer.Vector.prototype.multiplyScalar(N, 2 * c1), V);
        return new Flog.RayTracer.Ray(P, R1);
    },
    rayTrace: function (info, ray, scene, depth) {
        var color = Flog.RayTracer.Color.prototype.multiplyScalar(info.color, scene.background.ambience);
        var oldColor = color;
        var shininess = Math.pow(10, info.shape.material.gloss + 1);
        (() => {
            var i = 0;
            const __forInstance3 = new WebAssembly.Instance(__forWasmModule, {
                env: {
                    test: () => {
                        return i < scene.lights.length ? 1 : 0;
                    },
                    update: () => {
                        i++;
                    },
                    body: () => {
                        {
                            var light = scene.lights[i];
                            var v = Flog.RayTracer.Vector.prototype.subtract(light.position, info.position).normalize();
                            (() => {
                                const __ifInstance43 = new WebAssembly.Instance(__ifWasmModule, {
                                    env: {
                                        impFunc1: () => {
                                            {
                                                var L = v.dot(info.normal);
                                                (() => {
                                                    const __ifInstance44 = new WebAssembly.Instance(__ifWasmModule, {
                                                        env: {
                                                            impFunc1: () => {
                                                                {
                                                                    color = Flog.RayTracer.Color.prototype.add(color, Flog.RayTracer.Color.prototype.multiply(info.color, Flog.RayTracer.Color.prototype.multiplyScalar(light.color, L)));
                                                                }
                                                            },
                                                            impFunc2: () => {
                                                            }
                                                        }
                                                    });
                                                    const __exports = __ifInstance44.exports;
                                                    return __exports.data(L > 0 ? 1 : 0);
                                                })();
                                            }
                                        },
                                        impFunc2: () => {
                                        }
                                    }
                                });
                                const __exports = __ifInstance43.exports;
                                return __exports.data(this.options.renderDiffuse ? 1 : 0);
                            })();
                            (() => {
                                const __ifInstance45 = new WebAssembly.Instance(__ifWasmModule, {
                                    env: {
                                        impFunc1: () => {
                                            {
                                                (() => {
                                                    const __ifInstance46 = new WebAssembly.Instance(__ifWasmModule, {
                                                        env: {
                                                            impFunc1: () => {
                                                                {
                                                                    var reflectionRay = this.getReflectionRay(info.position, info.normal, ray.direction);
                                                                    var refl = this.testIntersection(reflectionRay, scene, info.shape);
                                                                    (() => {
                                                                        const __ifInstance47 = new WebAssembly.Instance(__ifWasmModule, {
                                                                            env: {
                                                                                impFunc1: () => {
                                                                                    {
                                                                                        refl.color = this.rayTrace(refl, reflectionRay, scene, depth + 1);
                                                                                    }
                                                                                },
                                                                                impFunc2: () => {
                                                                                    {
                                                                                        refl.color = scene.background.color;
                                                                                    }
                                                                                }
                                                                            }
                                                                        });
                                                                        const __exports = __ifInstance47.exports;
                                                                        return __exports.data(refl.isHit && refl.distance > 0 ? 1 : 0);
                                                                    })();
                                                                    color = Flog.RayTracer.Color.prototype.blend(color, refl.color, info.shape.material.reflection);
                                                                }
                                                            },
                                                            impFunc2: () => {
                                                            }
                                                        }
                                                    });
                                                    const __exports = __ifInstance46.exports;
                                                    return __exports.data(this.options.renderReflections && info.shape.material.reflection > 0 ? 1 : 0);
                                                })();
                                            }
                                        },
                                        impFunc2: () => {
                                        }
                                    }
                                });
                                const __exports = __ifInstance45.exports;
                                return __exports.data(depth <= this.options.rayDepth ? 1 : 0);
                            })();
                            var shadowInfo = new Flog.RayTracer.IntersectionInfo();
                            (() => {
                                const __ifInstance48 = new WebAssembly.Instance(__ifWasmModule, {
                                    env: {
                                        impFunc1: () => {
                                            {
                                                var shadowRay = new Flog.RayTracer.Ray(info.position, v);
                                                shadowInfo = this.testIntersection(shadowRay, scene, info.shape);
                                                (() => {
                                                    const __ifInstance49 = new WebAssembly.Instance(__ifWasmModule, {
                                                        env: {
                                                            impFunc1: () => {
                                                                {
                                                                    var vA = Flog.RayTracer.Color.prototype.multiplyScalar(color, 0.5);
                                                                    var dB = 0.5 * Math.pow(shadowInfo.shape.material.transparency, 0.5);
                                                                    color = Flog.RayTracer.Color.prototype.addScalar(vA, dB);
                                                                }
                                                            },
                                                            impFunc2: () => {
                                                            }
                                                        }
                                                    });
                                                    const __exports = __ifInstance49.exports;
                                                    return __exports.data(shadowInfo.isHit && shadowInfo.shape != info.shape ? 1 : 0);
                                                })();
                                            }
                                        },
                                        impFunc2: () => {
                                        }
                                    }
                                });
                                const __exports = __ifInstance48.exports;
                                return __exports.data(this.options.renderShadows ? 1 : 0);
                            })();
                            (() => {
                                const __ifInstance50 = new WebAssembly.Instance(__ifWasmModule, {
                                    env: {
                                        impFunc1: () => {
                                            {
                                                var Lv = Flog.RayTracer.Vector.prototype.subtract(info.shape.position, light.position).normalize();
                                                var E = Flog.RayTracer.Vector.prototype.subtract(scene.camera.position, info.shape.position).normalize();
                                                var H = Flog.RayTracer.Vector.prototype.subtract(E, Lv).normalize();
                                                var glossWeight = Math.pow(Math.max(info.normal.dot(H), 0), shininess);
                                                color = Flog.RayTracer.Color.prototype.add(Flog.RayTracer.Color.prototype.multiplyScalar(light.color, glossWeight), color);
                                            }
                                        },
                                        impFunc2: () => {
                                        }
                                    }
                                });
                                const __exports = __ifInstance50.exports;
                                return __exports.data(this.options.renderHighlights && !shadowInfo.isHit && info.shape.material.gloss > 0 ? 1 : 0);
                            })();
                        }
                    }
                }
            });
            const __exports = __forInstance3.exports;
            return __exports.data();
        })();
        (() => {
            const __callInstance6 = new WebAssembly.Instance(__callWasmModule, {
                env: {
                    impFunc: () => {
                        color.limit();
                    }
                }
            });
            const __exports = __callInstance6.exports;
            return __exports.data();
        })();
        return color;
    }
};
function renderScene() {
    var scene = new Flog.RayTracer.Scene();
    scene.camera = new Flog.RayTracer.Camera(new Flog.RayTracer.Vector(0, 0, -15), new Flog.RayTracer.Vector(-0.2, 0, 5), new Flog.RayTracer.Vector(0, 1, 0));
    scene.background = new Flog.RayTracer.Background(new Flog.RayTracer.Color(0.5, 0.5, 0.5), 0.4);
    var sphere = new Flog.RayTracer.Shape.Sphere(new Flog.RayTracer.Vector(-1.5, 1.5, 2), 1.5, new Flog.RayTracer.Material.Solid(new Flog.RayTracer.Color(0, 0.5, 0.5), 0.3, 0, 0, 2));
    var sphere1 = new Flog.RayTracer.Shape.Sphere(new Flog.RayTracer.Vector(1, 0.25, 1), 0.5, new Flog.RayTracer.Material.Solid(new Flog.RayTracer.Color(0.9, 0.9, 0.9), 0.1, 0, 0, 1.5));
    var plane = new Flog.RayTracer.Shape.Plane(new Flog.RayTracer.Vector(0.1, 0.9, -0.5).normalize(), 1.2, new Flog.RayTracer.Material.Chessboard(new Flog.RayTracer.Color(1, 1, 1), new Flog.RayTracer.Color(0, 0, 0), 0.2, 0, 1, 0.7));
    (() => {
        const __callInstance5 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    scene.shapes.push(plane);
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
                    scene.shapes.push(sphere);
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
                    scene.shapes.push(sphere1);
                }
            }
        });
        const __exports = __callInstance3.exports;
        return __exports.data();
    })();
    var light = new Flog.RayTracer.Light(new Flog.RayTracer.Vector(5, 10, -1), new Flog.RayTracer.Color(0.8, 0.8, 0.8));
    var light1 = new Flog.RayTracer.Light(new Flog.RayTracer.Vector(-3, 5, -15), new Flog.RayTracer.Color(0.8, 0.8, 0.8), 100);
    (() => {
        const __callInstance2 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    scene.lights.push(light);
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
                    scene.lights.push(light1);
                }
            }
        });
        const __exports = __callInstance1.exports;
        return __exports.data();
    })();
    var imageWidth = 100;
    var imageHeight = 100;
    var pixelSize = lS(0, 74).split(lS(0, 75));
    var renderDiffuse = true;
    var renderShadows = true;
    var renderHighlights = true;
    var renderReflections = true;
    var rayDepth = 2;
    var raytracer = new Flog.RayTracer.Engine({
        canvasWidth: imageWidth,
        canvasHeight: imageHeight,
        pixelWidth: pixelSize[0],
        pixelHeight: pixelSize[1],
        'renderDiffuse': renderDiffuse,
        'renderHighlights': renderHighlights,
        'renderShadows': renderShadows,
        'renderReflections': renderReflections,
        'rayDepth': rayDepth
    });
    (() => {
        const __callInstance0 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    raytracer.renderScene(scene, null, 0);
                }
            }
        });
        const __exports = __callInstance0.exports;
        return __exports.data();
    })();
}