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
const __wasmStringModules = ['AGFzbQEAAAAFg4CAgAABAAEGqoGAgAAdfwBBAQt/AEEQC38AQSILfwBBMAt/AEE4C38AQcAAC38AQcgAC38AQdAAC38AQdgAC38AQeAAC38AQegAC38AQfAAC38AQYIBC38AQZABC38AQZgBC38AQaABC38AQbIBC38AQcABC38AQcgBC38AQdABC38AQdgBC38AQeABC38AQegBC38AQfABC38AQfQBC38AQfwBC38AQYYCC38AQYoCC38AQZICCweFgoCAAB4GbWVtb3J5AgAFZGF0YTADAAVkYXRhMQMBBWRhdGEyAwIFZGF0YTMDAwVkYXRhNAMEBWRhdGE1AwUFZGF0YTYDBgVkYXRhNwMHBWRhdGE4AwgFZGF0YTkDCQZkYXRhMTADCgZkYXRhMTEDCwZkYXRhMTIDDAZkYXRhMTMDDQZkYXRhMTQDDgZkYXRhMTUDDwZkYXRhMTYDEAZkYXRhMTcDEQZkYXRhMTgDEgZkYXRhMTkDEwZkYXRhMjADFAZkYXRhMjEDFQZkYXRhMjIDFgZkYXRhMjMDFwZkYXRhMjQDGAZkYXRhMjUDGQZkYXRhMjYDGgZkYXRhMjcDGwZkYXRhMjgDHAuPg4CAAB0AQQELDXVzZSUyMHN0cmljdAAAQRALEGRlc3RpbmF0aW9uLW91dAAAQSILDHNvdXJjZS1vdmVyAABBMAsGcm91bmQAAEE4CwZyb3VuZAAAQcAACwZyb3VuZAAAQcgACwZyb3VuZAAAQdAACwZyb3VuZAAAQdgACwZyb3VuZAAAQeAACwZyb3VuZAAAQegACwZyb3VuZAAAQfAACxBkZXN0aW5hdGlvbi1vdXQAAEGCAQsMc291cmNlLW92ZXIAAEGQAQsGcm91bmQAAEGYAQsGcm91bmQAAEGgAQsQZGVzdGluYXRpb24tb3V0AABBsgELDHNvdXJjZS1vdmVyAABBwAELBnJvdW5kAABByAELBnJvdW5kAABB0AELBmJsYWNrAABB2AELBnJvdW5kAABB4AELBnJvdW5kAABB6AELB3N0cmluZwAAQfABCwJfAABB9AELB3N0cmluZwAAQfwBCwlmdW5jdGlvbgAAQYYCCwMyZAAAQYoCCwdzdHJpbmcAAEGSAgsHc3RyaW5nAA=='].map(__bytes => {
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
    const __callInstance80 = new WebAssembly.Instance(__callWasmModule, {
        env: {
            impFunc: () => {
                (function (global) {
                    lS(0, 0);
                    var requestInterval, cancelInterval;
                    (() => {
                        const __callInstance79 = new WebAssembly.Instance(__callWasmModule, {
                            env: {
                                impFunc: () => {
                                    (function () {
                                        var raf = global.requestAnimationFrame || global.webkitRequestAnimationFrame || global.mozRequestAnimationFrame || global.oRequestAnimationFrame || global.msRequestAnimationFrame, caf = global.cancelAnimationFrame || global.webkitCancelAnimationFrame || global.mozCancelAnimationFrame || global.oCancelAnimationFrame || global.msCancelAnimationFrame;
                                        if (raf && caf) {
                                            requestInterval = function (fn) {
                                                var handle = { value: null };
                                                function loop() {
                                                    handle.value = raf(loop);
                                                    (() => {
                                                        const __callInstance78 = new WebAssembly.Instance(__callWasmModule, {
                                                            env: {
                                                                impFunc: () => {
                                                                    fn();
                                                                }
                                                            }
                                                        });
                                                        const __exports = __callInstance78.exports;
                                                        return __exports.data();
                                                    })();
                                                }
                                                (() => {
                                                    const __callInstance77 = new WebAssembly.Instance(__callWasmModule, {
                                                        env: {
                                                            impFunc: () => {
                                                                loop();
                                                            }
                                                        }
                                                    });
                                                    const __exports = __callInstance77.exports;
                                                    return __exports.data();
                                                })();
                                                return handle;
                                            };
                                            cancelInterval = function (handle) {
                                                (() => {
                                                    const __callInstance76 = new WebAssembly.Instance(__callWasmModule, {
                                                        env: {
                                                            impFunc: () => {
                                                                caf(handle.value);
                                                            }
                                                        }
                                                    });
                                                    const __exports = __callInstance76.exports;
                                                    return __exports.data();
                                                })();
                                            };
                                        } else {
                                            requestInterval = setInterval;
                                            cancelInterval = clearInterval;
                                        }
                                    }());
                                }
                            }
                        });
                        const __exports = __callInstance79.exports;
                        return __exports.data();
                    })();
                    var KEYFRAME = 500, STROKE = 0.08, TAU = 2 * Math.PI, TWO_OVER_SQRT_2 = 2 / Math.sqrt(2);
                    function circle(ctx, x, y, r) {
                        (() => {
                            const __callInstance75 = new WebAssembly.Instance(__callWasmModule, {
                                env: {
                                    impFunc: () => {
                                        ctx.beginPath();
                                    }
                                }
                            });
                            const __exports = __callInstance75.exports;
                            return __exports.data();
                        })();
                        (() => {
                            const __callInstance74 = new WebAssembly.Instance(__callWasmModule, {
                                env: {
                                    impFunc: () => {
                                        ctx.arc(x, y, r, 0, TAU, false);
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
                                        ctx.fill();
                                    }
                                }
                            });
                            const __exports = __callInstance73.exports;
                            return __exports.data();
                        })();
                    }
                    function line(ctx, ax, ay, bx, by) {
                        (() => {
                            const __callInstance72 = new WebAssembly.Instance(__callWasmModule, {
                                env: {
                                    impFunc: () => {
                                        ctx.beginPath();
                                    }
                                }
                            });
                            const __exports = __callInstance72.exports;
                            return __exports.data();
                        })();
                        (() => {
                            const __callInstance71 = new WebAssembly.Instance(__callWasmModule, {
                                env: {
                                    impFunc: () => {
                                        ctx.moveTo(ax, ay);
                                    }
                                }
                            });
                            const __exports = __callInstance71.exports;
                            return __exports.data();
                        })();
                        (() => {
                            const __callInstance70 = new WebAssembly.Instance(__callWasmModule, {
                                env: {
                                    impFunc: () => {
                                        ctx.lineTo(bx, by);
                                    }
                                }
                            });
                            const __exports = __callInstance70.exports;
                            return __exports.data();
                        })();
                        (() => {
                            const __callInstance69 = new WebAssembly.Instance(__callWasmModule, {
                                env: {
                                    impFunc: () => {
                                        ctx.stroke();
                                    }
                                }
                            });
                            const __exports = __callInstance69.exports;
                            return __exports.data();
                        })();
                    }
                    function puff(ctx, t, cx, cy, rx, ry, rmin, rmax) {
                        var c = Math.cos(t * TAU), s = Math.sin(t * TAU);
                        rmax -= rmin;
                        (() => {
                            const __callInstance68 = new WebAssembly.Instance(__callWasmModule, {
                                env: {
                                    impFunc: () => {
                                        circle(ctx, cx - s * rx, cy + c * ry + rmax * 0.5, rmin + (1 - c * 0.5) * rmax);
                                    }
                                }
                            });
                            const __exports = __callInstance68.exports;
                            return __exports.data();
                        })();
                    }
                    function puffs(ctx, t, cx, cy, rx, ry, rmin, rmax) {
                        var i;
                        (() => {
                            i = 5;
                            const __forInstance0 = new WebAssembly.Instance(__forWasmModule, {
                                env: {
                                    test: () => {
                                        return i-- ? 1 : 0;
                                    },
                                    update: () => {
                                    },
                                    body: () => {
                                        (() => {
                                            const __callInstance67 = new WebAssembly.Instance(__callWasmModule, {
                                                env: {
                                                    impFunc: () => {
                                                        puff(ctx, t + i / 5, cx, cy, rx, ry, rmin, rmax);
                                                    }
                                                }
                                            });
                                            const __exports = __callInstance67.exports;
                                            return __exports.data();
                                        })();
                                    }
                                }
                            });
                            const __exports = __forInstance0.exports;
                            return __exports.data();
                        })();
                    }
                    function cloud(ctx, t, cx, cy, cw, s, color) {
                        t /= 30000;
                        var a = cw * 0.21, b = cw * 0.12, c = cw * 0.24, d = cw * 0.28;
                        ctx.fillStyle = color;
                        (() => {
                            const __callInstance66 = new WebAssembly.Instance(__callWasmModule, {
                                env: {
                                    impFunc: () => {
                                        puffs(ctx, t, cx, cy, a, b, c, d);
                                    }
                                }
                            });
                            const __exports = __callInstance66.exports;
                            return __exports.data();
                        })();
                        ctx.globalCompositeOperation = lS(0, 1);
                        (() => {
                            const __callInstance65 = new WebAssembly.Instance(__callWasmModule, {
                                env: {
                                    impFunc: () => {
                                        puffs(ctx, t, cx, cy, a, b, c - s, d - s);
                                    }
                                }
                            });
                            const __exports = __callInstance65.exports;
                            return __exports.data();
                        })();
                        ctx.globalCompositeOperation = lS(0, 2);
                    }
                    function sun(ctx, t, cx, cy, cw, s, color) {
                        t /= 120000;
                        var a = cw * 0.25 - s * 0.5, b = cw * 0.32 + s * 0.5, c = cw * 0.5 - s * 0.5, i, p, cos, sin;
                        ctx.strokeStyle = color;
                        ctx.lineWidth = s;
                        ctx.lineCap = lS(0, 3);
                        ctx.lineJoin = lS(0, 4);
                        (() => {
                            const __callInstance64 = new WebAssembly.Instance(__callWasmModule, {
                                env: {
                                    impFunc: () => {
                                        ctx.beginPath();
                                    }
                                }
                            });
                            const __exports = __callInstance64.exports;
                            return __exports.data();
                        })();
                        (() => {
                            const __callInstance63 = new WebAssembly.Instance(__callWasmModule, {
                                env: {
                                    impFunc: () => {
                                        ctx.arc(cx, cy, a, 0, TAU, false);
                                    }
                                }
                            });
                            const __exports = __callInstance63.exports;
                            return __exports.data();
                        })();
                        (() => {
                            const __callInstance62 = new WebAssembly.Instance(__callWasmModule, {
                                env: {
                                    impFunc: () => {
                                        ctx.stroke();
                                    }
                                }
                            });
                            const __exports = __callInstance62.exports;
                            return __exports.data();
                        })();
                        (() => {
                            i = 8;
                            const __forInstance1 = new WebAssembly.Instance(__forWasmModule, {
                                env: {
                                    test: () => {
                                        return i-- ? 1 : 0;
                                    },
                                    update: () => {
                                    },
                                    body: () => {
                                        {
                                            p = (t + i / 8) * TAU;
                                            cos = Math.cos(p);
                                            sin = Math.sin(p);
                                            (() => {
                                                const __callInstance61 = new WebAssembly.Instance(__callWasmModule, {
                                                    env: {
                                                        impFunc: () => {
                                                            line(ctx, cx + cos * b, cy + sin * b, cx + cos * c, cy + sin * c);
                                                        }
                                                    }
                                                });
                                                const __exports = __callInstance61.exports;
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
                    function moon(ctx, t, cx, cy, cw, s, color) {
                        t /= 15000;
                        var a = cw * 0.29 - s * 0.5, b = cw * 0.05, c = Math.cos(t * TAU), p = c * TAU / -16;
                        ctx.strokeStyle = color;
                        ctx.lineWidth = s;
                        ctx.lineCap = lS(0, 5);
                        ctx.lineJoin = lS(0, 6);
                        cx += c * b;
                        (() => {
                            const __callInstance60 = new WebAssembly.Instance(__callWasmModule, {
                                env: {
                                    impFunc: () => {
                                        ctx.beginPath();
                                    }
                                }
                            });
                            const __exports = __callInstance60.exports;
                            return __exports.data();
                        })();
                        (() => {
                            const __callInstance59 = new WebAssembly.Instance(__callWasmModule, {
                                env: {
                                    impFunc: () => {
                                        ctx.arc(cx, cy, a, p + TAU / 8, p + TAU * 7 / 8, false);
                                    }
                                }
                            });
                            const __exports = __callInstance59.exports;
                            return __exports.data();
                        })();
                        (() => {
                            const __callInstance58 = new WebAssembly.Instance(__callWasmModule, {
                                env: {
                                    impFunc: () => {
                                        ctx.arc(cx + Math.cos(p) * a * TWO_OVER_SQRT_2, cy + Math.sin(p) * a * TWO_OVER_SQRT_2, a, p + TAU * 5 / 8, p + TAU * 3 / 8, true);
                                    }
                                }
                            });
                            const __exports = __callInstance58.exports;
                            return __exports.data();
                        })();
                        (() => {
                            const __callInstance57 = new WebAssembly.Instance(__callWasmModule, {
                                env: {
                                    impFunc: () => {
                                        ctx.closePath();
                                    }
                                }
                            });
                            const __exports = __callInstance57.exports;
                            return __exports.data();
                        })();
                        (() => {
                            const __callInstance56 = new WebAssembly.Instance(__callWasmModule, {
                                env: {
                                    impFunc: () => {
                                        ctx.stroke();
                                    }
                                }
                            });
                            const __exports = __callInstance56.exports;
                            return __exports.data();
                        })();
                    }
                    function rain(ctx, t, cx, cy, cw, s, color) {
                        t /= 1350;
                        var a = cw * 0.16, b = TAU * 11 / 12, c = TAU * 7 / 12, i, p, x, y;
                        ctx.fillStyle = color;
                        (() => {
                            i = 4;
                            const __forInstance2 = new WebAssembly.Instance(__forWasmModule, {
                                env: {
                                    test: () => {
                                        return i-- ? 1 : 0;
                                    },
                                    update: () => {
                                    },
                                    body: () => {
                                        {
                                            p = (t + i / 4) % 1;
                                            x = cx + (i - 1.5) / 1.5 * (i === 1 || i === 2 ? -1 : 1) * a;
                                            y = cy + p * p * cw;
                                            (() => {
                                                const __callInstance55 = new WebAssembly.Instance(__callWasmModule, {
                                                    env: {
                                                        impFunc: () => {
                                                            ctx.beginPath();
                                                        }
                                                    }
                                                });
                                                const __exports = __callInstance55.exports;
                                                return __exports.data();
                                            })();
                                            (() => {
                                                const __callInstance54 = new WebAssembly.Instance(__callWasmModule, {
                                                    env: {
                                                        impFunc: () => {
                                                            ctx.moveTo(x, y - s * 1.5);
                                                        }
                                                    }
                                                });
                                                const __exports = __callInstance54.exports;
                                                return __exports.data();
                                            })();
                                            (() => {
                                                const __callInstance53 = new WebAssembly.Instance(__callWasmModule, {
                                                    env: {
                                                        impFunc: () => {
                                                            ctx.arc(x, y, s * 0.75, b, c, false);
                                                        }
                                                    }
                                                });
                                                const __exports = __callInstance53.exports;
                                                return __exports.data();
                                            })();
                                            (() => {
                                                const __callInstance52 = new WebAssembly.Instance(__callWasmModule, {
                                                    env: {
                                                        impFunc: () => {
                                                            ctx.fill();
                                                        }
                                                    }
                                                });
                                                const __exports = __callInstance52.exports;
                                                return __exports.data();
                                            })();
                                        }
                                    }
                                }
                            });
                            const __exports = __forInstance2.exports;
                            return __exports.data();
                        })();
                    }
                    function sleet(ctx, t, cx, cy, cw, s, color) {
                        t /= 750;
                        var a = cw * 0.1875, i, p, x, y;
                        ctx.strokeStyle = color;
                        ctx.lineWidth = s * 0.5;
                        ctx.lineCap = lS(0, 7);
                        ctx.lineJoin = lS(0, 8);
                        (() => {
                            i = 4;
                            const __forInstance3 = new WebAssembly.Instance(__forWasmModule, {
                                env: {
                                    test: () => {
                                        return i-- ? 1 : 0;
                                    },
                                    update: () => {
                                    },
                                    body: () => {
                                        {
                                            p = (t + i / 4) % 1;
                                            x = Math.floor(cx + (i - 1.5) / 1.5 * (i === 1 || i === 2 ? -1 : 1) * a) + 0.5;
                                            y = cy + p * cw;
                                            (() => {
                                                const __callInstance51 = new WebAssembly.Instance(__callWasmModule, {
                                                    env: {
                                                        impFunc: () => {
                                                            line(ctx, x, y - s * 1.5, x, y + s * 1.5);
                                                        }
                                                    }
                                                });
                                                const __exports = __callInstance51.exports;
                                                return __exports.data();
                                            })();
                                        }
                                    }
                                }
                            });
                            const __exports = __forInstance3.exports;
                            return __exports.data();
                        })();
                    }
                    function snow(ctx, t, cx, cy, cw, s, color) {
                        t /= 3000;
                        var a = cw * 0.16, b = s * 0.75, u = t * TAU * 0.7, ux = Math.cos(u) * b, uy = Math.sin(u) * b, v = u + TAU / 3, vx = Math.cos(v) * b, vy = Math.sin(v) * b, w = u + TAU * 2 / 3, wx = Math.cos(w) * b, wy = Math.sin(w) * b, i, p, x, y;
                        ctx.strokeStyle = color;
                        ctx.lineWidth = s * 0.5;
                        ctx.lineCap = lS(0, 9);
                        ctx.lineJoin = lS(0, 10);
                        (() => {
                            i = 4;
                            const __forInstance4 = new WebAssembly.Instance(__forWasmModule, {
                                env: {
                                    test: () => {
                                        return i-- ? 1 : 0;
                                    },
                                    update: () => {
                                    },
                                    body: () => {
                                        {
                                            p = (t + i / 4) % 1;
                                            x = cx + Math.sin((p + i / 4) * TAU) * a;
                                            y = cy + p * cw;
                                            (() => {
                                                const __callInstance50 = new WebAssembly.Instance(__callWasmModule, {
                                                    env: {
                                                        impFunc: () => {
                                                            line(ctx, x - ux, y - uy, x + ux, y + uy);
                                                        }
                                                    }
                                                });
                                                const __exports = __callInstance50.exports;
                                                return __exports.data();
                                            })();
                                            (() => {
                                                const __callInstance49 = new WebAssembly.Instance(__callWasmModule, {
                                                    env: {
                                                        impFunc: () => {
                                                            line(ctx, x - vx, y - vy, x + vx, y + vy);
                                                        }
                                                    }
                                                });
                                                const __exports = __callInstance49.exports;
                                                return __exports.data();
                                            })();
                                            (() => {
                                                const __callInstance48 = new WebAssembly.Instance(__callWasmModule, {
                                                    env: {
                                                        impFunc: () => {
                                                            line(ctx, x - wx, y - wy, x + wx, y + wy);
                                                        }
                                                    }
                                                });
                                                const __exports = __callInstance48.exports;
                                                return __exports.data();
                                            })();
                                        }
                                    }
                                }
                            });
                            const __exports = __forInstance4.exports;
                            return __exports.data();
                        })();
                    }
                    function fogbank(ctx, t, cx, cy, cw, s, color) {
                        t /= 30000;
                        var a = cw * 0.21, b = cw * 0.06, c = cw * 0.21, d = cw * 0.28;
                        ctx.fillStyle = color;
                        (() => {
                            const __callInstance47 = new WebAssembly.Instance(__callWasmModule, {
                                env: {
                                    impFunc: () => {
                                        puffs(ctx, t, cx, cy, a, b, c, d);
                                    }
                                }
                            });
                            const __exports = __callInstance47.exports;
                            return __exports.data();
                        })();
                        ctx.globalCompositeOperation = lS(0, 11);
                        (() => {
                            const __callInstance46 = new WebAssembly.Instance(__callWasmModule, {
                                env: {
                                    impFunc: () => {
                                        puffs(ctx, t, cx, cy, a, b, c - s, d - s);
                                    }
                                }
                            });
                            const __exports = __callInstance46.exports;
                            return __exports.data();
                        })();
                        ctx.globalCompositeOperation = lS(0, 12);
                    }
                    var WIND_PATHS = [
                            [
                                -0.75,
                                -0.18,
                                -0.7219,
                                -0.1527,
                                -0.6971,
                                -0.1225,
                                -0.6739,
                                -0.091,
                                -0.6516,
                                -0.0588,
                                -0.6298,
                                -0.0262,
                                -0.6083,
                                0.0065,
                                -0.5868,
                                0.0396,
                                -0.5643,
                                0.0731,
                                -0.5372,
                                0.1041,
                                -0.5033,
                                0.1259,
                                -0.4662,
                                0.1406,
                                -0.4275,
                                0.1493,
                                -0.3881,
                                0.153,
                                -0.3487,
                                0.1526,
                                -0.3095,
                                0.1488,
                                -0.2708,
                                0.1421,
                                -0.2319,
                                0.1342,
                                -0.1943,
                                0.1217,
                                -0.16,
                                0.1025,
                                -0.129,
                                0.0785,
                                -0.1012,
                                0.0509,
                                -0.0764,
                                0.0206,
                                -0.0547,
                                -0.012,
                                -0.0378,
                                -0.0472,
                                -0.0324,
                                -0.0857,
                                -0.0389,
                                -0.1241,
                                -0.0546,
                                -0.1599,
                                -0.0814,
                                -0.1876,
                                -0.1193,
                                -0.1964,
                                -0.1582,
                                -0.1935,
                                -0.1931,
                                -0.1769,
                                -0.2157,
                                -0.1453,
                                -0.229,
                                -0.1085,
                                -0.2327,
                                -0.0697,
                                -0.224,
                                -0.0317,
                                -0.2064,
                                0.0033,
                                -0.1853,
                                0.0362,
                                -0.1613,
                                0.0672,
                                -0.135,
                                0.0961,
                                -0.1051,
                                0.1213,
                                -0.0706,
                                0.1397,
                                -0.0332,
                                0.1512,
                                0.0053,
                                0.158,
                                0.0442,
                                0.1624,
                                0.0833,
                                0.1636,
                                0.1224,
                                0.1615,
                                0.1613,
                                0.1565,
                                0.1999,
                                0.15,
                                0.2378,
                                0.1402,
                                0.2749,
                                0.1279,
                                0.3118,
                                0.1147,
                                0.3487,
                                0.1015,
                                0.3858,
                                0.0892,
                                0.4236,
                                0.0787,
                                0.4621,
                                0.0715,
                                0.5012,
                                0.0702,
                                0.5398,
                                0.0766,
                                0.5768,
                                0.089,
                                0.6123,
                                0.1055,
                                0.6466,
                                0.1244,
                                0.6805,
                                0.144,
                                0.7147,
                                0.163,
                                0.75,
                                0.18
                            ],
                            [
                                -0.75,
                                0,
                                -0.7033,
                                0.0195,
                                -0.6569,
                                0.0399,
                                -0.6104,
                                0.06,
                                -0.5634,
                                0.0789,
                                -0.5155,
                                0.0954,
                                -0.4667,
                                0.1089,
                                -0.4174,
                                0.1206,
                                -0.3676,
                                0.1299,
                                -0.3174,
                                0.1365,
                                -0.2669,
                                0.1398,
                                -0.2162,
                                0.1391,
                                -0.1658,
                                0.1347,
                                -0.1157,
                                0.1271,
                                -0.0661,
                                0.1169,
                                -0.017,
                                0.1046,
                                0.0316,
                                0.0903,
                                0.0791,
                                0.0728,
                                0.1259,
                                0.0534,
                                0.1723,
                                0.0331,
                                0.2188,
                                0.0129,
                                0.2656,
                                -0.0064,
                                0.3122,
                                -0.0263,
                                0.3586,
                                -0.0466,
                                0.4052,
                                -0.0665,
                                0.4525,
                                -0.0847,
                                0.5007,
                                -0.1002,
                                0.5497,
                                -0.113,
                                0.5991,
                                -0.124,
                                0.6491,
                                -0.1325,
                                0.6994,
                                -0.138,
                                0.75,
                                -0.14
                            ]
                        ], WIND_OFFSETS = [
                            {
                                start: 0.36,
                                end: 0.11
                            },
                            {
                                start: 0.56,
                                end: 0.16
                            }
                        ];
                    function leaf(ctx, t, x, y, cw, s, color) {
                        var a = cw / 8, b = a / 3, c = 2 * b, d = t % 1 * TAU, e = Math.cos(d), f = Math.sin(d);
                        ctx.fillStyle = color;
                        ctx.strokeStyle = color;
                        ctx.lineWidth = s;
                        ctx.lineCap = lS(0, 13);
                        ctx.lineJoin = lS(0, 14);
                        (() => {
                            const __callInstance45 = new WebAssembly.Instance(__callWasmModule, {
                                env: {
                                    impFunc: () => {
                                        ctx.beginPath();
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
                                        ctx.arc(x, y, a, d, d + Math.PI, false);
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
                                        ctx.arc(x - b * e, y - b * f, c, d + Math.PI, d, false);
                                    }
                                }
                            });
                            const __exports = __callInstance43.exports;
                            return __exports.data();
                        })();
                        (() => {
                            const __callInstance42 = new WebAssembly.Instance(__callWasmModule, {
                                env: {
                                    impFunc: () => {
                                        ctx.arc(x + c * e, y + c * f, b, d + Math.PI, d, true);
                                    }
                                }
                            });
                            const __exports = __callInstance42.exports;
                            return __exports.data();
                        })();
                        ctx.globalCompositeOperation = lS(0, 15);
                        (() => {
                            const __callInstance41 = new WebAssembly.Instance(__callWasmModule, {
                                env: {
                                    impFunc: () => {
                                        ctx.fill();
                                    }
                                }
                            });
                            const __exports = __callInstance41.exports;
                            return __exports.data();
                        })();
                        ctx.globalCompositeOperation = lS(0, 16);
                        (() => {
                            const __callInstance40 = new WebAssembly.Instance(__callWasmModule, {
                                env: {
                                    impFunc: () => {
                                        ctx.stroke();
                                    }
                                }
                            });
                            const __exports = __callInstance40.exports;
                            return __exports.data();
                        })();
                    }
                    function swoosh(ctx, t, cx, cy, cw, s, index, total, color) {
                        t /= 2500;
                        var path = WIND_PATHS[index], a = (t + index - WIND_OFFSETS[index].start) % total, c = (t + index - WIND_OFFSETS[index].end) % total, e = (t + index) % total, b, d, f, i;
                        ctx.strokeStyle = color;
                        ctx.lineWidth = s;
                        ctx.lineCap = lS(0, 17);
                        ctx.lineJoin = lS(0, 18);
                        (() => {
                            const __ifInstance0 = new WebAssembly.Instance(__ifWasmModule, {
                                env: {
                                    impFunc1: () => {
                                        {
                                            (() => {
                                                const __callInstance39 = new WebAssembly.Instance(__callWasmModule, {
                                                    env: {
                                                        impFunc: () => {
                                                            ctx.beginPath();
                                                        }
                                                    }
                                                });
                                                const __exports = __callInstance39.exports;
                                                return __exports.data();
                                            })();
                                            a *= path.length / 2 - 1;
                                            b = Math.floor(a);
                                            a -= b;
                                            b *= 2;
                                            b += 2;
                                            (() => {
                                                const __callInstance38 = new WebAssembly.Instance(__callWasmModule, {
                                                    env: {
                                                        impFunc: () => {
                                                            ctx.moveTo(cx + (path[b - 2] * (1 - a) + path[b] * a) * cw, cy + (path[b - 1] * (1 - a) + path[b + 1] * a) * cw);
                                                        }
                                                    }
                                                });
                                                const __exports = __callInstance38.exports;
                                                return __exports.data();
                                            })();
                                            (() => {
                                                const __ifInstance1 = new WebAssembly.Instance(__ifWasmModule, {
                                                    env: {
                                                        impFunc1: () => {
                                                            {
                                                                c *= path.length / 2 - 1;
                                                                d = Math.floor(c);
                                                                c -= d;
                                                                d *= 2;
                                                                d += 2;
                                                                (() => {
                                                                    i = b;
                                                                    const __forInstance5 = new WebAssembly.Instance(__forWasmModule, {
                                                                        env: {
                                                                            test: () => {
                                                                                return i !== d ? 1 : 0;
                                                                            },
                                                                            update: () => {
                                                                                i += 2;
                                                                            },
                                                                            body: () => {
                                                                                (() => {
                                                                                    const __callInstance37 = new WebAssembly.Instance(__callWasmModule, {
                                                                                        env: {
                                                                                            impFunc: () => {
                                                                                                ctx.lineTo(cx + path[i] * cw, cy + path[i + 1] * cw);
                                                                                            }
                                                                                        }
                                                                                    });
                                                                                    const __exports = __callInstance37.exports;
                                                                                    return __exports.data();
                                                                                })();
                                                                            }
                                                                        }
                                                                    });
                                                                    const __exports = __forInstance5.exports;
                                                                    return __exports.data();
                                                                })();
                                                                (() => {
                                                                    const __callInstance36 = new WebAssembly.Instance(__callWasmModule, {
                                                                        env: {
                                                                            impFunc: () => {
                                                                                ctx.lineTo(cx + (path[d - 2] * (1 - c) + path[d] * c) * cw, cy + (path[d - 1] * (1 - c) + path[d + 1] * c) * cw);
                                                                            }
                                                                        }
                                                                    });
                                                                    const __exports = __callInstance36.exports;
                                                                    return __exports.data();
                                                                })();
                                                            }
                                                        },
                                                        impFunc2: () => {
                                                            for (i = b; i !== path.length; i += 2)
                                                                (() => {
                                                                    const __callInstance35 = new WebAssembly.Instance(__callWasmModule, {
                                                                        env: {
                                                                            impFunc: () => {
                                                                                ctx.lineTo(cx + path[i] * cw, cy + path[i + 1] * cw);
                                                                            }
                                                                        }
                                                                    });
                                                                    const __exports = __callInstance35.exports;
                                                                    return __exports.data();
                                                                })();
                                                        }
                                                    }
                                                });
                                                const __exports = __ifInstance1.exports;
                                                return __exports.data(c < 1 ? 1 : 0);
                                            })();
                                            (() => {
                                                const __callInstance34 = new WebAssembly.Instance(__callWasmModule, {
                                                    env: {
                                                        impFunc: () => {
                                                            ctx.stroke();
                                                        }
                                                    }
                                                });
                                                const __exports = __callInstance34.exports;
                                                return __exports.data();
                                            })();
                                        }
                                    },
                                    impFunc2: () => {
                                        (() => {
                                            const __ifInstance2 = new WebAssembly.Instance(__ifWasmModule, {
                                                env: {
                                                    impFunc1: () => {
                                                        {
                                                            (() => {
                                                                const __callInstance33 = new WebAssembly.Instance(__callWasmModule, {
                                                                    env: {
                                                                        impFunc: () => {
                                                                            ctx.beginPath();
                                                                        }
                                                                    }
                                                                });
                                                                const __exports = __callInstance33.exports;
                                                                return __exports.data();
                                                            })();
                                                            c *= path.length / 2 - 1;
                                                            d = Math.floor(c);
                                                            c -= d;
                                                            d *= 2;
                                                            d += 2;
                                                            (() => {
                                                                const __callInstance32 = new WebAssembly.Instance(__callWasmModule, {
                                                                    env: {
                                                                        impFunc: () => {
                                                                            ctx.moveTo(cx + path[0] * cw, cy + path[1] * cw);
                                                                        }
                                                                    }
                                                                });
                                                                const __exports = __callInstance32.exports;
                                                                return __exports.data();
                                                            })();
                                                            (() => {
                                                                i = 2;
                                                                const __forInstance7 = new WebAssembly.Instance(__forWasmModule, {
                                                                    env: {
                                                                        test: () => {
                                                                            return i !== d ? 1 : 0;
                                                                        },
                                                                        update: () => {
                                                                            i += 2;
                                                                        },
                                                                        body: () => {
                                                                            (() => {
                                                                                const __callInstance31 = new WebAssembly.Instance(__callWasmModule, {
                                                                                    env: {
                                                                                        impFunc: () => {
                                                                                            ctx.lineTo(cx + path[i] * cw, cy + path[i + 1] * cw);
                                                                                        }
                                                                                    }
                                                                                });
                                                                                const __exports = __callInstance31.exports;
                                                                                return __exports.data();
                                                                            })();
                                                                        }
                                                                    }
                                                                });
                                                                const __exports = __forInstance7.exports;
                                                                return __exports.data();
                                                            })();
                                                            (() => {
                                                                const __callInstance30 = new WebAssembly.Instance(__callWasmModule, {
                                                                    env: {
                                                                        impFunc: () => {
                                                                            ctx.lineTo(cx + (path[d - 2] * (1 - c) + path[d] * c) * cw, cy + (path[d - 1] * (1 - c) + path[d + 1] * c) * cw);
                                                                        }
                                                                    }
                                                                });
                                                                const __exports = __callInstance30.exports;
                                                                return __exports.data();
                                                            })();
                                                            (() => {
                                                                const __callInstance29 = new WebAssembly.Instance(__callWasmModule, {
                                                                    env: {
                                                                        impFunc: () => {
                                                                            ctx.stroke();
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
                                            const __exports = __ifInstance2.exports;
                                            return __exports.data(c < 1 ? 1 : 0);
                                        })();
                                    }
                                }
                            });
                            const __exports = __ifInstance0.exports;
                            return __exports.data(a < 1 ? 1 : 0);
                        })();
                        (() => {
                            const __ifInstance3 = new WebAssembly.Instance(__ifWasmModule, {
                                env: {
                                    impFunc1: () => {
                                        {
                                            e *= path.length / 2 - 1;
                                            f = Math.floor(e);
                                            e -= f;
                                            f *= 2;
                                            f += 2;
                                            (() => {
                                                const __callInstance28 = new WebAssembly.Instance(__callWasmModule, {
                                                    env: {
                                                        impFunc: () => {
                                                            leaf(ctx, t, cx + (path[f - 2] * (1 - e) + path[f] * e) * cw, cy + (path[f - 1] * (1 - e) + path[f + 1] * e) * cw, cw, s, color);
                                                        }
                                                    }
                                                });
                                                const __exports = __callInstance28.exports;
                                                return __exports.data();
                                            })();
                                        }
                                    },
                                    impFunc2: () => {
                                    }
                                }
                            });
                            const __exports = __ifInstance3.exports;
                            return __exports.data(e < 1 ? 1 : 0);
                        })();
                    }
                    var Skycons = function (opts) {
                        this.list = [];
                        this.interval = null;
                        this.color = opts && opts.color ? opts.color : lS(0, 19);
                        this.resizeClear = !!(opts && opts.resizeClear);
                    };
                    Skycons.CLEAR_DAY = function (ctx, t, color) {
                        var w = ctx.canvas.width, h = ctx.canvas.height, s = Math.min(w, h);
                        (() => {
                            const __callInstance27 = new WebAssembly.Instance(__callWasmModule, {
                                env: {
                                    impFunc: () => {
                                        sun(ctx, t, w * 0.5, h * 0.5, s, s * STROKE, color);
                                    }
                                }
                            });
                            const __exports = __callInstance27.exports;
                            return __exports.data();
                        })();
                    };
                    Skycons.CLEAR_NIGHT = function (ctx, t, color) {
                        var w = ctx.canvas.width, h = ctx.canvas.height, s = Math.min(w, h);
                        (() => {
                            const __callInstance26 = new WebAssembly.Instance(__callWasmModule, {
                                env: {
                                    impFunc: () => {
                                        moon(ctx, t, w * 0.5, h * 0.5, s, s * STROKE, color);
                                    }
                                }
                            });
                            const __exports = __callInstance26.exports;
                            return __exports.data();
                        })();
                    };
                    Skycons.PARTLY_CLOUDY_DAY = function (ctx, t, color) {
                        var w = ctx.canvas.width, h = ctx.canvas.height, s = Math.min(w, h);
                        (() => {
                            const __callInstance25 = new WebAssembly.Instance(__callWasmModule, {
                                env: {
                                    impFunc: () => {
                                        sun(ctx, t, w * 0.625, h * 0.375, s * 0.75, s * STROKE, color);
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
                                        cloud(ctx, t, w * 0.375, h * 0.625, s * 0.75, s * STROKE, color);
                                    }
                                }
                            });
                            const __exports = __callInstance24.exports;
                            return __exports.data();
                        })();
                    };
                    Skycons.PARTLY_CLOUDY_NIGHT = function (ctx, t, color) {
                        var w = ctx.canvas.width, h = ctx.canvas.height, s = Math.min(w, h);
                        (() => {
                            const __callInstance23 = new WebAssembly.Instance(__callWasmModule, {
                                env: {
                                    impFunc: () => {
                                        moon(ctx, t, w * 0.667, h * 0.375, s * 0.75, s * STROKE, color);
                                    }
                                }
                            });
                            const __exports = __callInstance23.exports;
                            return __exports.data();
                        })();
                        (() => {
                            const __callInstance22 = new WebAssembly.Instance(__callWasmModule, {
                                env: {
                                    impFunc: () => {
                                        cloud(ctx, t, w * 0.375, h * 0.625, s * 0.75, s * STROKE, color);
                                    }
                                }
                            });
                            const __exports = __callInstance22.exports;
                            return __exports.data();
                        })();
                    };
                    Skycons.CLOUDY = function (ctx, t, color) {
                        var w = ctx.canvas.width, h = ctx.canvas.height, s = Math.min(w, h);
                        (() => {
                            const __callInstance21 = new WebAssembly.Instance(__callWasmModule, {
                                env: {
                                    impFunc: () => {
                                        cloud(ctx, t, w * 0.5, h * 0.5, s, s * STROKE, color);
                                    }
                                }
                            });
                            const __exports = __callInstance21.exports;
                            return __exports.data();
                        })();
                    };
                    Skycons.RAIN = function (ctx, t, color) {
                        var w = ctx.canvas.width, h = ctx.canvas.height, s = Math.min(w, h);
                        (() => {
                            const __callInstance20 = new WebAssembly.Instance(__callWasmModule, {
                                env: {
                                    impFunc: () => {
                                        rain(ctx, t, w * 0.5, h * 0.37, s * 0.9, s * STROKE, color);
                                    }
                                }
                            });
                            const __exports = __callInstance20.exports;
                            return __exports.data();
                        })();
                        (() => {
                            const __callInstance19 = new WebAssembly.Instance(__callWasmModule, {
                                env: {
                                    impFunc: () => {
                                        cloud(ctx, t, w * 0.5, h * 0.37, s * 0.9, s * STROKE, color);
                                    }
                                }
                            });
                            const __exports = __callInstance19.exports;
                            return __exports.data();
                        })();
                    };
                    Skycons.SLEET = function (ctx, t, color) {
                        var w = ctx.canvas.width, h = ctx.canvas.height, s = Math.min(w, h);
                        (() => {
                            const __callInstance18 = new WebAssembly.Instance(__callWasmModule, {
                                env: {
                                    impFunc: () => {
                                        sleet(ctx, t, w * 0.5, h * 0.37, s * 0.9, s * STROKE, color);
                                    }
                                }
                            });
                            const __exports = __callInstance18.exports;
                            return __exports.data();
                        })();
                        (() => {
                            const __callInstance17 = new WebAssembly.Instance(__callWasmModule, {
                                env: {
                                    impFunc: () => {
                                        cloud(ctx, t, w * 0.5, h * 0.37, s * 0.9, s * STROKE, color);
                                    }
                                }
                            });
                            const __exports = __callInstance17.exports;
                            return __exports.data();
                        })();
                    };
                    Skycons.SNOW = function (ctx, t, color) {
                        var w = ctx.canvas.width, h = ctx.canvas.height, s = Math.min(w, h);
                        (() => {
                            const __callInstance16 = new WebAssembly.Instance(__callWasmModule, {
                                env: {
                                    impFunc: () => {
                                        snow(ctx, t, w * 0.5, h * 0.37, s * 0.9, s * STROKE, color);
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
                                        cloud(ctx, t, w * 0.5, h * 0.37, s * 0.9, s * STROKE, color);
                                    }
                                }
                            });
                            const __exports = __callInstance15.exports;
                            return __exports.data();
                        })();
                    };
                    Skycons.WIND = function (ctx, t, color) {
                        var w = ctx.canvas.width, h = ctx.canvas.height, s = Math.min(w, h);
                        (() => {
                            const __callInstance14 = new WebAssembly.Instance(__callWasmModule, {
                                env: {
                                    impFunc: () => {
                                        swoosh(ctx, t, w * 0.5, h * 0.5, s, s * STROKE, 0, 2, color);
                                    }
                                }
                            });
                            const __exports = __callInstance14.exports;
                            return __exports.data();
                        })();
                        (() => {
                            const __callInstance13 = new WebAssembly.Instance(__callWasmModule, {
                                env: {
                                    impFunc: () => {
                                        swoosh(ctx, t, w * 0.5, h * 0.5, s, s * STROKE, 1, 2, color);
                                    }
                                }
                            });
                            const __exports = __callInstance13.exports;
                            return __exports.data();
                        })();
                    };
                    Skycons.FOG = function (ctx, t, color) {
                        var w = ctx.canvas.width, h = ctx.canvas.height, s = Math.min(w, h), k = s * STROKE;
                        (() => {
                            const __callInstance12 = new WebAssembly.Instance(__callWasmModule, {
                                env: {
                                    impFunc: () => {
                                        fogbank(ctx, t, w * 0.5, h * 0.32, s * 0.75, k, color);
                                    }
                                }
                            });
                            const __exports = __callInstance12.exports;
                            return __exports.data();
                        })();
                        t /= 5000;
                        var a = Math.cos(t * TAU) * s * 0.02, b = Math.cos((t + 0.25) * TAU) * s * 0.02, c = Math.cos((t + 0.5) * TAU) * s * 0.02, d = Math.cos((t + 0.75) * TAU) * s * 0.02, n = h * 0.936, e = Math.floor(n - k * 0.5) + 0.5, f = Math.floor(n - k * 2.5) + 0.5;
                        ctx.strokeStyle = color;
                        ctx.lineWidth = k;
                        ctx.lineCap = lS(0, 20);
                        ctx.lineJoin = lS(0, 21);
                        (() => {
                            const __callInstance11 = new WebAssembly.Instance(__callWasmModule, {
                                env: {
                                    impFunc: () => {
                                        line(ctx, a + w * 0.2 + k * 0.5, e, b + w * 0.8 - k * 0.5, e);
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
                                        line(ctx, c + w * 0.2 + k * 0.5, f, d + w * 0.8 - k * 0.5, f);
                                    }
                                }
                            });
                            const __exports = __callInstance10.exports;
                            return __exports.data();
                        })();
                    };
                    Skycons.prototype = {
                        _determineDrawingFunction: function (draw) {
                            (() => {
                                const __ifInstance4 = new WebAssembly.Instance(__ifWasmModule, {
                                    env: {
                                        impFunc1: () => {
                                            draw = Skycons[draw.toUpperCase().replace(/-/g, lS(0, 23))] || null;
                                        },
                                        impFunc2: () => {
                                        }
                                    }
                                });
                                const __exports = __ifInstance4.exports;
                                return __exports.data(typeof draw === lS(0, 22) ? 1 : 0);
                            })();
                            return draw;
                        },
                        add: function (el, draw) {
                            var obj;
                            (() => {
                                const __ifInstance5 = new WebAssembly.Instance(__ifWasmModule, {
                                    env: {
                                        impFunc1: () => {
                                            el = document.getElementById(el);
                                        },
                                        impFunc2: () => {
                                        }
                                    }
                                });
                                const __exports = __ifInstance5.exports;
                                return __exports.data(typeof el === lS(0, 24) ? 1 : 0);
                            })();
                            if (el === null)
                                return;
                            draw = this._determineDrawingFunction(draw);
                            if (typeof draw !== lS(0, 25))
                                return;
                            obj = {
                                element: el,
                                context: el.getContext(lS(0, 26)),
                                drawing: draw
                            };
                            (() => {
                                const __callInstance9 = new WebAssembly.Instance(__callWasmModule, {
                                    env: {
                                        impFunc: () => {
                                            this.list.push(obj);
                                        }
                                    }
                                });
                                const __exports = __callInstance9.exports;
                                return __exports.data();
                            })();
                            (() => {
                                const __callInstance8 = new WebAssembly.Instance(__callWasmModule, {
                                    env: {
                                        impFunc: () => {
                                            this.draw(obj, KEYFRAME);
                                        }
                                    }
                                });
                                const __exports = __callInstance8.exports;
                                return __exports.data();
                            })();
                        },
                        set: function (el, draw) {
                            var i;
                            (() => {
                                const __ifInstance6 = new WebAssembly.Instance(__ifWasmModule, {
                                    env: {
                                        impFunc1: () => {
                                            el = document.getElementById(el);
                                        },
                                        impFunc2: () => {
                                        }
                                    }
                                });
                                const __exports = __ifInstance6.exports;
                                return __exports.data(typeof el === lS(0, 27) ? 1 : 0);
                            })();
                            for (i = this.list.length; i--;)
                                if (this.list[i].element === el) {
                                    this.list[i].drawing = this._determineDrawingFunction(draw);
                                    (() => {
                                        const __callInstance7 = new WebAssembly.Instance(__callWasmModule, {
                                            env: {
                                                impFunc: () => {
                                                    this.draw(this.list[i], KEYFRAME);
                                                }
                                            }
                                        });
                                        const __exports = __callInstance7.exports;
                                        return __exports.data();
                                    })();
                                    return;
                                }
                            (() => {
                                const __callInstance6 = new WebAssembly.Instance(__callWasmModule, {
                                    env: {
                                        impFunc: () => {
                                            this.add(el, draw);
                                        }
                                    }
                                });
                                const __exports = __callInstance6.exports;
                                return __exports.data();
                            })();
                        },
                        remove: function (el) {
                            var i;
                            (() => {
                                const __ifInstance7 = new WebAssembly.Instance(__ifWasmModule, {
                                    env: {
                                        impFunc1: () => {
                                            el = document.getElementById(el);
                                        },
                                        impFunc2: () => {
                                        }
                                    }
                                });
                                const __exports = __ifInstance7.exports;
                                return __exports.data(typeof el === lS(0, 28) ? 1 : 0);
                            })();
                            for (i = this.list.length; i--;)
                                if (this.list[i].element === el) {
                                    (() => {
                                        const __callInstance5 = new WebAssembly.Instance(__callWasmModule, {
                                            env: {
                                                impFunc: () => {
                                                    this.list.splice(i, 1);
                                                }
                                            }
                                        });
                                        const __exports = __callInstance5.exports;
                                        return __exports.data();
                                    })();
                                    return;
                                }
                        },
                        draw: function (obj, time) {
                            var canvas = obj.context.canvas;
                            (() => {
                                const __ifInstance8 = new WebAssembly.Instance(__ifWasmModule, {
                                    env: {
                                        impFunc1: () => {
                                            canvas.width = canvas.width;
                                        },
                                        impFunc2: () => {
                                            (() => {
                                                const __callInstance4 = new WebAssembly.Instance(__callWasmModule, {
                                                    env: {
                                                        impFunc: () => {
                                                            obj.context.clearRect(0, 0, canvas.width, canvas.height);
                                                        }
                                                    }
                                                });
                                                const __exports = __callInstance4.exports;
                                                return __exports.data();
                                            })();
                                        }
                                    }
                                });
                                const __exports = __ifInstance8.exports;
                                return __exports.data(this.resizeClear ? 1 : 0);
                            })();
                            (() => {
                                const __callInstance3 = new WebAssembly.Instance(__callWasmModule, {
                                    env: {
                                        impFunc: () => {
                                            obj.drawing(obj.context, time, this.color);
                                        }
                                    }
                                });
                                const __exports = __callInstance3.exports;
                                return __exports.data();
                            })();
                        },
                        play: function () {
                            var self = this;
                            (() => {
                                const __callInstance2 = new WebAssembly.Instance(__callWasmModule, {
                                    env: {
                                        impFunc: () => {
                                            this.pause();
                                        }
                                    }
                                });
                                const __exports = __callInstance2.exports;
                                return __exports.data();
                            })();
                            this.interval = requestInterval(function () {
                                var now = Date.now(), i;
                                (() => {
                                    i = self.list.length;
                                    const __forInstance8 = new WebAssembly.Instance(__forWasmModule, {
                                        env: {
                                            test: () => {
                                                return i-- ? 1 : 0;
                                            },
                                            update: () => {
                                            },
                                            body: () => {
                                                (() => {
                                                    const __callInstance1 = new WebAssembly.Instance(__callWasmModule, {
                                                        env: {
                                                            impFunc: () => {
                                                                self.draw(self.list[i], now);
                                                            }
                                                        }
                                                    });
                                                    const __exports = __callInstance1.exports;
                                                    return __exports.data();
                                                })();
                                            }
                                        }
                                    });
                                    const __exports = __forInstance8.exports;
                                    return __exports.data();
                                })();
                            }, 1000 / 60);
                        },
                        pause: function () {
                            (() => {
                                const __ifInstance9 = new WebAssembly.Instance(__ifWasmModule, {
                                    env: {
                                        impFunc1: () => {
                                            {
                                                (() => {
                                                    const __callInstance0 = new WebAssembly.Instance(__callWasmModule, {
                                                        env: {
                                                            impFunc: () => {
                                                                cancelInterval(this.interval);
                                                            }
                                                        }
                                                    });
                                                    const __exports = __callInstance0.exports;
                                                    return __exports.data();
                                                })();
                                                this.interval = null;
                                            }
                                        },
                                        impFunc2: () => {
                                        }
                                    }
                                });
                                const __exports = __ifInstance9.exports;
                                return __exports.data(this.interval ? 1 : 0);
                            })();
                        }
                    };
                    global.Skycons = Skycons;
                }(this));
            }
        }
    });
    const __exports = __callInstance80.exports;
    return __exports.data();
})();