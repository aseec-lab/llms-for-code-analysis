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
const __aB = 'AGFzbQEAAAABiYCAgAACYAAAYAJ/fwADg4CAgAACAQAFg4CAgAABAAEGhoCAgAABfwFBAAsHkYCAgAACBm1lbW9yeQIABGFycjAAAQqrgICAAAKPgICAAAAjACAAQQRsaiABNgIAC5GAgIAAAQF/QRAkAEEAQeDJ2gAQAAs=', __wAM = new WebAssembly.Instance(new WebAssembly.Module((() => {
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
const __wasmStringModules = ['AGFzbQEAAAAFg4CAgAABAAEGkICAgAADfwBBAQt/AEEQC38AQR4LB6KAgIAABAZtZW1vcnkCAAVkYXRhMAMABWRhdGExAwEFZGF0YTIDAgu8gICAAAMAQQELDU5hdmllclN0b2tlcwAAQRALDU5hdmllclN0b2tlcwAAQR4LEmNoZWNrc3VtJTIwZmFpbGVkAA=='].map(__bytes => {
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
var NavierStokes = new BenchmarkSuite(lS(0, 0), __lA(0, 16, 20), [new Benchmark(lS(0, 1), true, false, 180, runNavierStokes, setupNavierStokes, tearDownNavierStokes, null, 16)]);
var solver = null;
var nsFrameCounter = 0;
function runNavierStokes() {
    (() => {
        const __callInstance44 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    solver.update();
                }
            }
        });
        const __exports = __callInstance44.exports;
        return __exports.data();
    })();
    nsFrameCounter++;
    (() => {
        const __ifInstance0 = new WebAssembly.Instance(__ifWasmModule, {
            env: {
                impFunc1: () => {
                    (() => {
                        const __callInstance43 = new WebAssembly.Instance(__callWasmModule, {
                            env: {
                                impFunc: () => {
                                    checkResult(solver.getDens());
                                }
                            }
                        });
                        const __exports = __callInstance43.exports;
                        return __exports.data();
                    })();
                },
                impFunc2: () => {
                }
            }
        });
        const __exports = __ifInstance0.exports;
        return __exports.data(nsFrameCounter == 15 ? 1 : 0);
    })();
}
function checkResult(dens) {
    this.result = 0;
    (() => {
        var i = 7000;
        const __forInstance0 = new WebAssembly.Instance(__forWasmModule, {
            env: {
                test: () => {
                    return i < 7100 ? 1 : 0;
                },
                update: () => {
                    i++;
                },
                body: () => {
                    {
                        this.result += ~~(dens[i] * 10);
                    }
                }
            }
        });
        const __exports = __forInstance0.exports;
        return __exports.data();
    })();
    if (this.result != 77) {
        throw new Error(lS(0, 2));
    }
}
function setupNavierStokes() {
    solver = new FluidField(null);
    (() => {
        const __callInstance42 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    solver.setResolution(128, 128);
                }
            }
        });
        const __exports = __callInstance42.exports;
        return __exports.data();
    })();
    (() => {
        const __callInstance41 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    solver.setIterations(20);
                }
            }
        });
        const __exports = __callInstance41.exports;
        return __exports.data();
    })();
    (() => {
        const __callInstance40 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    solver.setDisplayFunction(function () {
                    });
                }
            }
        });
        const __exports = __callInstance40.exports;
        return __exports.data();
    })();
    (() => {
        const __callInstance39 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    solver.setUICallback(prepareFrame);
                }
            }
        });
        const __exports = __callInstance39.exports;
        return __exports.data();
    })();
    (() => {
        const __callInstance38 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    solver.reset();
                }
            }
        });
        const __exports = __callInstance38.exports;
        return __exports.data();
    })();
}
function tearDownNavierStokes() {
    solver = null;
}
function addPoints(field) {
    var n = 64;
    (() => {
        var i = 1;
        const __forInstance1 = new WebAssembly.Instance(__forWasmModule, {
            env: {
                test: () => {
                    return i <= n ? 1 : 0;
                },
                update: () => {
                    i++;
                },
                body: () => {
                    {
                        (() => {
                            const __callInstance37 = new WebAssembly.Instance(__callWasmModule, {
                                env: {
                                    impFunc: () => {
                                        field.setVelocity(i, i, n, n);
                                    }
                                }
                            });
                            const __exports = __callInstance37.exports;
                            return __exports.data();
                        })();
                        (() => {
                            const __callInstance36 = new WebAssembly.Instance(__callWasmModule, {
                                env: {
                                    impFunc: () => {
                                        field.setDensity(i, i, 5);
                                    }
                                }
                            });
                            const __exports = __callInstance36.exports;
                            return __exports.data();
                        })();
                        (() => {
                            const __callInstance35 = new WebAssembly.Instance(__callWasmModule, {
                                env: {
                                    impFunc: () => {
                                        field.setVelocity(i, n - i, -n, -n);
                                    }
                                }
                            });
                            const __exports = __callInstance35.exports;
                            return __exports.data();
                        })();
                        (() => {
                            const __callInstance34 = new WebAssembly.Instance(__callWasmModule, {
                                env: {
                                    impFunc: () => {
                                        field.setDensity(i, n - i, 20);
                                    }
                                }
                            });
                            const __exports = __callInstance34.exports;
                            return __exports.data();
                        })();
                        (() => {
                            const __callInstance33 = new WebAssembly.Instance(__callWasmModule, {
                                env: {
                                    impFunc: () => {
                                        field.setVelocity(128 - i, n + i, -n, -n);
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
                                        field.setDensity(128 - i, n + i, 30);
                                    }
                                }
                            });
                            const __exports = __callInstance32.exports;
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
var framesTillAddingPoints = 0;
var framesBetweenAddingPoints = 5;
function prepareFrame(field) {
    (() => {
        const __ifInstance1 = new WebAssembly.Instance(__ifWasmModule, {
            env: {
                impFunc1: () => {
                    {
                        (() => {
                            const __callInstance31 = new WebAssembly.Instance(__callWasmModule, {
                                env: {
                                    impFunc: () => {
                                        addPoints(field);
                                    }
                                }
                            });
                            const __exports = __callInstance31.exports;
                            return __exports.data();
                        })();
                        framesTillAddingPoints = framesBetweenAddingPoints;
                        framesBetweenAddingPoints++;
                    }
                },
                impFunc2: () => {
                    {
                        framesTillAddingPoints--;
                    }
                }
            }
        });
        const __exports = __ifInstance1.exports;
        return __exports.data(framesTillAddingPoints == 0 ? 1 : 0);
    })();
}
function FluidField(canvas) {
    function addFields(x, s, dt) {
        (() => {
            var i = 0;
            const __forInstance2 = new WebAssembly.Instance(__forWasmModule, {
                env: {
                    test: () => {
                        return i < size ? 1 : 0;
                    },
                    update: () => {
                        i++;
                    },
                    body: () => {
                        x[i] += dt * s[i];
                    }
                }
            });
            const __exports = __forInstance2.exports;
            return __exports.data();
        })();
    }
    function set_bnd(b, x) {
        (() => {
            const __ifInstance2 = new WebAssembly.Instance(__ifWasmModule, {
                env: {
                    impFunc1: () => {
                        {
                            (() => {
                                var i = 1;
                                const __forInstance3 = new WebAssembly.Instance(__forWasmModule, {
                                    env: {
                                        test: () => {
                                            return i <= width ? 1 : 0;
                                        },
                                        update: () => {
                                            i++;
                                        },
                                        body: () => {
                                            {
                                                x[i] = x[i + rowSize];
                                                x[i + (height + 1) * rowSize] = x[i + height * rowSize];
                                            }
                                        }
                                    }
                                });
                                const __exports = __forInstance3.exports;
                                return __exports.data();
                            })();
                            (() => {
                                var j = 1;
                                const __forInstance4 = new WebAssembly.Instance(__forWasmModule, {
                                    env: {
                                        test: () => {
                                            return j <= height ? 1 : 0;
                                        },
                                        update: () => {
                                            j++;
                                        },
                                        body: () => {
                                            {
                                                x[j * rowSize] = -x[1 + j * rowSize];
                                                x[width + 1 + j * rowSize] = -x[width + j * rowSize];
                                            }
                                        }
                                    }
                                });
                                const __exports = __forInstance4.exports;
                                return __exports.data();
                            })();
                        }
                    },
                    impFunc2: () => {
                        (() => {
                            const __ifInstance3 = new WebAssembly.Instance(__ifWasmModule, {
                                env: {
                                    impFunc1: () => {
                                        {
                                            (() => {
                                                var i = 1;
                                                const __forInstance5 = new WebAssembly.Instance(__forWasmModule, {
                                                    env: {
                                                        test: () => {
                                                            return i <= width ? 1 : 0;
                                                        },
                                                        update: () => {
                                                            i++;
                                                        },
                                                        body: () => {
                                                            {
                                                                x[i] = -x[i + rowSize];
                                                                x[i + (height + 1) * rowSize] = -x[i + height * rowSize];
                                                            }
                                                        }
                                                    }
                                                });
                                                const __exports = __forInstance5.exports;
                                                return __exports.data();
                                            })();
                                            (() => {
                                                var j = 1;
                                                const __forInstance6 = new WebAssembly.Instance(__forWasmModule, {
                                                    env: {
                                                        test: () => {
                                                            return j <= height ? 1 : 0;
                                                        },
                                                        update: () => {
                                                            j++;
                                                        },
                                                        body: () => {
                                                            {
                                                                x[j * rowSize] = x[1 + j * rowSize];
                                                                x[width + 1 + j * rowSize] = x[width + j * rowSize];
                                                            }
                                                        }
                                                    }
                                                });
                                                const __exports = __forInstance6.exports;
                                                return __exports.data();
                                            })();
                                        }
                                    },
                                    impFunc2: () => {
                                        {
                                            (() => {
                                                var i = 1;
                                                const __forInstance7 = new WebAssembly.Instance(__forWasmModule, {
                                                    env: {
                                                        test: () => {
                                                            return i <= width ? 1 : 0;
                                                        },
                                                        update: () => {
                                                            i++;
                                                        },
                                                        body: () => {
                                                            {
                                                                x[i] = x[i + rowSize];
                                                                x[i + (height + 1) * rowSize] = x[i + height * rowSize];
                                                            }
                                                        }
                                                    }
                                                });
                                                const __exports = __forInstance7.exports;
                                                return __exports.data();
                                            })();
                                            (() => {
                                                var j = 1;
                                                const __forInstance8 = new WebAssembly.Instance(__forWasmModule, {
                                                    env: {
                                                        test: () => {
                                                            return j <= height ? 1 : 0;
                                                        },
                                                        update: () => {
                                                            j++;
                                                        },
                                                        body: () => {
                                                            {
                                                                x[j * rowSize] = x[1 + j * rowSize];
                                                                x[width + 1 + j * rowSize] = x[width + j * rowSize];
                                                            }
                                                        }
                                                    }
                                                });
                                                const __exports = __forInstance8.exports;
                                                return __exports.data();
                                            })();
                                        }
                                    }
                                }
                            });
                            const __exports = __ifInstance3.exports;
                            return __exports.data(b === 2 ? 1 : 0);
                        })();
                    }
                }
            });
            const __exports = __ifInstance2.exports;
            return __exports.data(b === 1 ? 1 : 0);
        })();
        var maxEdge = (height + 1) * rowSize;
        x[0] = 0.5 * (x[1] + x[rowSize]);
        x[maxEdge] = 0.5 * (x[1 + maxEdge] + x[height * rowSize]);
        x[width + 1] = 0.5 * (x[width] + x[width + 1 + rowSize]);
        x[width + 1 + maxEdge] = 0.5 * (x[width + maxEdge] + x[width + 1 + height * rowSize]);
    }
    function lin_solve(b, x, x0, a, c) {
        (() => {
            const __ifInstance4 = new WebAssembly.Instance(__ifWasmModule, {
                env: {
                    impFunc1: () => {
                        {
                            (() => {
                                var j = 1;
                                const __forInstance9 = new WebAssembly.Instance(__forWasmModule, {
                                    env: {
                                        test: () => {
                                            return j <= height ? 1 : 0;
                                        },
                                        update: () => {
                                            j++;
                                        },
                                        body: () => {
                                            {
                                                var currentRow = j * rowSize;
                                                ++currentRow;
                                                (() => {
                                                    var i = 0;
                                                    const __forInstance10 = new WebAssembly.Instance(__forWasmModule, {
                                                        env: {
                                                            test: () => {
                                                                return i < width ? 1 : 0;
                                                            },
                                                            update: () => {
                                                                i++;
                                                            },
                                                            body: () => {
                                                                {
                                                                    x[currentRow] = x0[currentRow];
                                                                    ++currentRow;
                                                                }
                                                            }
                                                        }
                                                    });
                                                    const __exports = __forInstance10.exports;
                                                    return __exports.data();
                                                })();
                                            }
                                        }
                                    }
                                });
                                const __exports = __forInstance9.exports;
                                return __exports.data();
                            })();
                            (() => {
                                const __callInstance30 = new WebAssembly.Instance(__callWasmModule, {
                                    env: {
                                        impFunc: () => {
                                            set_bnd(b, x);
                                        }
                                    }
                                });
                                const __exports = __callInstance30.exports;
                                return __exports.data();
                            })();
                        }
                    },
                    impFunc2: () => {
                        {
                            var invC = 1 / c;
                            (() => {
                                var k = 0;
                                const __forInstance11 = new WebAssembly.Instance(__forWasmModule, {
                                    env: {
                                        test: () => {
                                            return k < iterations ? 1 : 0;
                                        },
                                        update: () => {
                                            k++;
                                        },
                                        body: () => {
                                            {
                                                (() => {
                                                    var j = 1;
                                                    const __forInstance12 = new WebAssembly.Instance(__forWasmModule, {
                                                        env: {
                                                            test: () => {
                                                                return j <= height ? 1 : 0;
                                                            },
                                                            update: () => {
                                                                j++;
                                                            },
                                                            body: () => {
                                                                {
                                                                    var lastRow = (j - 1) * rowSize;
                                                                    var currentRow = j * rowSize;
                                                                    var nextRow = (j + 1) * rowSize;
                                                                    var lastX = x[currentRow];
                                                                    ++currentRow;
                                                                    (() => {
                                                                        var i = 1;
                                                                        const __forInstance13 = new WebAssembly.Instance(__forWasmModule, {
                                                                            env: {
                                                                                test: () => {
                                                                                    return i <= width ? 1 : 0;
                                                                                },
                                                                                update: () => {
                                                                                    i++;
                                                                                },
                                                                                body: () => {
                                                                                    lastX = x[currentRow] = (x0[currentRow] + a * (lastX + x[++currentRow] + x[++lastRow] + x[++nextRow])) * invC;
                                                                                }
                                                                            }
                                                                        });
                                                                        const __exports = __forInstance13.exports;
                                                                        return __exports.data();
                                                                    })();
                                                                }
                                                            }
                                                        }
                                                    });
                                                    const __exports = __forInstance12.exports;
                                                    return __exports.data();
                                                })();
                                                (() => {
                                                    const __callInstance29 = new WebAssembly.Instance(__callWasmModule, {
                                                        env: {
                                                            impFunc: () => {
                                                                set_bnd(b, x);
                                                            }
                                                        }
                                                    });
                                                    const __exports = __callInstance29.exports;
                                                    return __exports.data();
                                                })();
                                            }
                                        }
                                    }
                                });
                                const __exports = __forInstance11.exports;
                                return __exports.data();
                            })();
                        }
                    }
                }
            });
            const __exports = __ifInstance4.exports;
            return __exports.data(a === 0 && c === 1 ? 1 : 0);
        })();
    }
    function diffuse(b, x, x0, dt) {
        var a = 0;
        (() => {
            const __callInstance28 = new WebAssembly.Instance(__callWasmModule, {
                env: {
                    impFunc: () => {
                        lin_solve(b, x, x0, a, 1 + 4 * a);
                    }
                }
            });
            const __exports = __callInstance28.exports;
            return __exports.data();
        })();
    }
    function lin_solve2(x, x0, y, y0, a, c) {
        (() => {
            const __ifInstance5 = new WebAssembly.Instance(__ifWasmModule, {
                env: {
                    impFunc1: () => {
                        {
                            (() => {
                                var j = 1;
                                const __forInstance14 = new WebAssembly.Instance(__forWasmModule, {
                                    env: {
                                        test: () => {
                                            return j <= height ? 1 : 0;
                                        },
                                        update: () => {
                                            j++;
                                        },
                                        body: () => {
                                            {
                                                var currentRow = j * rowSize;
                                                ++currentRow;
                                                (() => {
                                                    var i = 0;
                                                    const __forInstance15 = new WebAssembly.Instance(__forWasmModule, {
                                                        env: {
                                                            test: () => {
                                                                return i < width ? 1 : 0;
                                                            },
                                                            update: () => {
                                                                i++;
                                                            },
                                                            body: () => {
                                                                {
                                                                    x[currentRow] = x0[currentRow];
                                                                    y[currentRow] = y0[currentRow];
                                                                    ++currentRow;
                                                                }
                                                            }
                                                        }
                                                    });
                                                    const __exports = __forInstance15.exports;
                                                    return __exports.data();
                                                })();
                                            }
                                        }
                                    }
                                });
                                const __exports = __forInstance14.exports;
                                return __exports.data();
                            })();
                            (() => {
                                const __callInstance27 = new WebAssembly.Instance(__callWasmModule, {
                                    env: {
                                        impFunc: () => {
                                            set_bnd(1, x);
                                        }
                                    }
                                });
                                const __exports = __callInstance27.exports;
                                return __exports.data();
                            })();
                            (() => {
                                const __callInstance26 = new WebAssembly.Instance(__callWasmModule, {
                                    env: {
                                        impFunc: () => {
                                            set_bnd(2, y);
                                        }
                                    }
                                });
                                const __exports = __callInstance26.exports;
                                return __exports.data();
                            })();
                        }
                    },
                    impFunc2: () => {
                        {
                            var invC = 1 / c;
                            (() => {
                                var k = 0;
                                const __forInstance16 = new WebAssembly.Instance(__forWasmModule, {
                                    env: {
                                        test: () => {
                                            return k < iterations ? 1 : 0;
                                        },
                                        update: () => {
                                            k++;
                                        },
                                        body: () => {
                                            {
                                                (() => {
                                                    var j = 1;
                                                    const __forInstance17 = new WebAssembly.Instance(__forWasmModule, {
                                                        env: {
                                                            test: () => {
                                                                return j <= height ? 1 : 0;
                                                            },
                                                            update: () => {
                                                                j++;
                                                            },
                                                            body: () => {
                                                                {
                                                                    var lastRow = (j - 1) * rowSize;
                                                                    var currentRow = j * rowSize;
                                                                    var nextRow = (j + 1) * rowSize;
                                                                    var lastX = x[currentRow];
                                                                    var lastY = y[currentRow];
                                                                    ++currentRow;
                                                                    (() => {
                                                                        var i = 1;
                                                                        const __forInstance18 = new WebAssembly.Instance(__forWasmModule, {
                                                                            env: {
                                                                                test: () => {
                                                                                    return i <= width ? 1 : 0;
                                                                                },
                                                                                update: () => {
                                                                                    i++;
                                                                                },
                                                                                body: () => {
                                                                                    {
                                                                                        lastX = x[currentRow] = (x0[currentRow] + a * (lastX + x[currentRow] + x[lastRow] + x[nextRow])) * invC;
                                                                                        lastY = y[currentRow] = (y0[currentRow] + a * (lastY + y[++currentRow] + y[++lastRow] + y[++nextRow])) * invC;
                                                                                    }
                                                                                }
                                                                            }
                                                                        });
                                                                        const __exports = __forInstance18.exports;
                                                                        return __exports.data();
                                                                    })();
                                                                }
                                                            }
                                                        }
                                                    });
                                                    const __exports = __forInstance17.exports;
                                                    return __exports.data();
                                                })();
                                                (() => {
                                                    const __callInstance25 = new WebAssembly.Instance(__callWasmModule, {
                                                        env: {
                                                            impFunc: () => {
                                                                set_bnd(1, x);
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
                                                                set_bnd(2, y);
                                                            }
                                                        }
                                                    });
                                                    const __exports = __callInstance24.exports;
                                                    return __exports.data();
                                                })();
                                            }
                                        }
                                    }
                                });
                                const __exports = __forInstance16.exports;
                                return __exports.data();
                            })();
                        }
                    }
                }
            });
            const __exports = __ifInstance5.exports;
            return __exports.data(a === 0 && c === 1 ? 1 : 0);
        })();
    }
    function diffuse2(x, x0, y, y0, dt) {
        var a = 0;
        (() => {
            const __callInstance23 = new WebAssembly.Instance(__callWasmModule, {
                env: {
                    impFunc: () => {
                        lin_solve2(x, x0, y, y0, a, 1 + 4 * a);
                    }
                }
            });
            const __exports = __callInstance23.exports;
            return __exports.data();
        })();
    }
    function advect(b, d, d0, u, v, dt) {
        var Wdt0 = dt * width;
        var Hdt0 = dt * height;
        var Wp5 = width + 0.5;
        var Hp5 = height + 0.5;
        (() => {
            var j = 1;
            const __forInstance19 = new WebAssembly.Instance(__forWasmModule, {
                env: {
                    test: () => {
                        return j <= height ? 1 : 0;
                    },
                    update: () => {
                        j++;
                    },
                    body: () => {
                        {
                            var pos = j * rowSize;
                            (() => {
                                var i = 1;
                                const __forInstance20 = new WebAssembly.Instance(__forWasmModule, {
                                    env: {
                                        test: () => {
                                            return i <= width ? 1 : 0;
                                        },
                                        update: () => {
                                            i++;
                                        },
                                        body: () => {
                                            {
                                                var x = i - Wdt0 * u[++pos];
                                                var y = j - Hdt0 * v[pos];
                                                (() => {
                                                    const __ifInstance6 = new WebAssembly.Instance(__ifWasmModule, {
                                                        env: {
                                                            impFunc1: () => {
                                                                x = 0.5;
                                                            },
                                                            impFunc2: () => {
                                                                (() => {
                                                                    const __ifInstance7 = new WebAssembly.Instance(__ifWasmModule, {
                                                                        env: {
                                                                            impFunc1: () => {
                                                                                x = Wp5;
                                                                            },
                                                                            impFunc2: () => {
                                                                            }
                                                                        }
                                                                    });
                                                                    const __exports = __ifInstance7.exports;
                                                                    return __exports.data(x > Wp5 ? 1 : 0);
                                                                })();
                                                            }
                                                        }
                                                    });
                                                    const __exports = __ifInstance6.exports;
                                                    return __exports.data(x < 0.5 ? 1 : 0);
                                                })();
                                                var i0 = x | 0;
                                                var i1 = i0 + 1;
                                                (() => {
                                                    const __ifInstance8 = new WebAssembly.Instance(__ifWasmModule, {
                                                        env: {
                                                            impFunc1: () => {
                                                                y = 0.5;
                                                            },
                                                            impFunc2: () => {
                                                                (() => {
                                                                    const __ifInstance9 = new WebAssembly.Instance(__ifWasmModule, {
                                                                        env: {
                                                                            impFunc1: () => {
                                                                                y = Hp5;
                                                                            },
                                                                            impFunc2: () => {
                                                                            }
                                                                        }
                                                                    });
                                                                    const __exports = __ifInstance9.exports;
                                                                    return __exports.data(y > Hp5 ? 1 : 0);
                                                                })();
                                                            }
                                                        }
                                                    });
                                                    const __exports = __ifInstance8.exports;
                                                    return __exports.data(y < 0.5 ? 1 : 0);
                                                })();
                                                var j0 = y | 0;
                                                var j1 = j0 + 1;
                                                var s1 = x - i0;
                                                var s0 = 1 - s1;
                                                var t1 = y - j0;
                                                var t0 = 1 - t1;
                                                var row1 = j0 * rowSize;
                                                var row2 = j1 * rowSize;
                                                d[pos] = s0 * (t0 * d0[i0 + row1] + t1 * d0[i0 + row2]) + s1 * (t0 * d0[i1 + row1] + t1 * d0[i1 + row2]);
                                            }
                                        }
                                    }
                                });
                                const __exports = __forInstance20.exports;
                                return __exports.data();
                            })();
                        }
                    }
                }
            });
            const __exports = __forInstance19.exports;
            return __exports.data();
        })();
        (() => {
            const __callInstance22 = new WebAssembly.Instance(__callWasmModule, {
                env: {
                    impFunc: () => {
                        set_bnd(b, d);
                    }
                }
            });
            const __exports = __callInstance22.exports;
            return __exports.data();
        })();
    }
    function project(u, v, p, div) {
        var h = -0.5 / Math.sqrt(width * height);
        (() => {
            var j = 1;
            const __forInstance21 = new WebAssembly.Instance(__forWasmModule, {
                env: {
                    test: () => {
                        return j <= height ? 1 : 0;
                    },
                    update: () => {
                        j++;
                    },
                    body: () => {
                        {
                            var row = j * rowSize;
                            var previousRow = (j - 1) * rowSize;
                            var prevValue = row - 1;
                            var currentRow = row;
                            var nextValue = row + 1;
                            var nextRow = (j + 1) * rowSize;
                            (() => {
                                var i = 1;
                                const __forInstance22 = new WebAssembly.Instance(__forWasmModule, {
                                    env: {
                                        test: () => {
                                            return i <= width ? 1 : 0;
                                        },
                                        update: () => {
                                            i++;
                                        },
                                        body: () => {
                                            {
                                                div[++currentRow] = h * (u[++nextValue] - u[++prevValue] + v[++nextRow] - v[++previousRow]);
                                                p[currentRow] = 0;
                                            }
                                        }
                                    }
                                });
                                const __exports = __forInstance22.exports;
                                return __exports.data();
                            })();
                        }
                    }
                }
            });
            const __exports = __forInstance21.exports;
            return __exports.data();
        })();
        (() => {
            const __callInstance21 = new WebAssembly.Instance(__callWasmModule, {
                env: {
                    impFunc: () => {
                        set_bnd(0, div);
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
                        set_bnd(0, p);
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
                        lin_solve(0, p, div, 1, 4);
                    }
                }
            });
            const __exports = __callInstance19.exports;
            return __exports.data();
        })();
        var wScale = 0.5 * width;
        var hScale = 0.5 * height;
        (() => {
            var j = 1;
            const __forInstance23 = new WebAssembly.Instance(__forWasmModule, {
                env: {
                    test: () => {
                        return j <= height ? 1 : 0;
                    },
                    update: () => {
                        j++;
                    },
                    body: () => {
                        {
                            var prevPos = j * rowSize - 1;
                            var currentPos = j * rowSize;
                            var nextPos = j * rowSize + 1;
                            var prevRow = (j - 1) * rowSize;
                            var currentRow = j * rowSize;
                            var nextRow = (j + 1) * rowSize;
                            (() => {
                                var i = 1;
                                const __forInstance24 = new WebAssembly.Instance(__forWasmModule, {
                                    env: {
                                        test: () => {
                                            return i <= width ? 1 : 0;
                                        },
                                        update: () => {
                                            i++;
                                        },
                                        body: () => {
                                            {
                                                u[++currentPos] -= wScale * (p[++nextPos] - p[++prevPos]);
                                                v[currentPos] -= hScale * (p[++nextRow] - p[++prevRow]);
                                            }
                                        }
                                    }
                                });
                                const __exports = __forInstance24.exports;
                                return __exports.data();
                            })();
                        }
                    }
                }
            });
            const __exports = __forInstance23.exports;
            return __exports.data();
        })();
        (() => {
            const __callInstance18 = new WebAssembly.Instance(__callWasmModule, {
                env: {
                    impFunc: () => {
                        set_bnd(1, u);
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
                        set_bnd(2, v);
                    }
                }
            });
            const __exports = __callInstance17.exports;
            return __exports.data();
        })();
    }
    function dens_step(x, x0, u, v, dt) {
        (() => {
            const __callInstance16 = new WebAssembly.Instance(__callWasmModule, {
                env: {
                    impFunc: () => {
                        addFields(x, x0, dt);
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
                        diffuse(0, x0, x, dt);
                    }
                }
            });
            const __exports = __callInstance15.exports;
            return __exports.data();
        })();
        (() => {
            const __callInstance14 = new WebAssembly.Instance(__callWasmModule, {
                env: {
                    impFunc: () => {
                        advect(0, x, x0, u, v, dt);
                    }
                }
            });
            const __exports = __callInstance14.exports;
            return __exports.data();
        })();
    }
    function vel_step(u, v, u0, v0, dt) {
        (() => {
            const __callInstance13 = new WebAssembly.Instance(__callWasmModule, {
                env: {
                    impFunc: () => {
                        addFields(u, u0, dt);
                    }
                }
            });
            const __exports = __callInstance13.exports;
            return __exports.data();
        })();
        (() => {
            const __callInstance12 = new WebAssembly.Instance(__callWasmModule, {
                env: {
                    impFunc: () => {
                        addFields(v, v0, dt);
                    }
                }
            });
            const __exports = __callInstance12.exports;
            return __exports.data();
        })();
        var temp = u0;
        u0 = u;
        u = temp;
        var temp = v0;
        v0 = v;
        v = temp;
        (() => {
            const __callInstance11 = new WebAssembly.Instance(__callWasmModule, {
                env: {
                    impFunc: () => {
                        diffuse2(u, u0, v, v0, dt);
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
                        project(u, v, u0, v0);
                    }
                }
            });
            const __exports = __callInstance10.exports;
            return __exports.data();
        })();
        var temp = u0;
        u0 = u;
        u = temp;
        var temp = v0;
        v0 = v;
        v = temp;
        (() => {
            const __callInstance9 = new WebAssembly.Instance(__callWasmModule, {
                env: {
                    impFunc: () => {
                        advect(1, u, u0, u0, v0, dt);
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
                        advect(2, v, v0, u0, v0, dt);
                    }
                }
            });
            const __exports = __callInstance8.exports;
            return __exports.data();
        })();
        (() => {
            const __callInstance7 = new WebAssembly.Instance(__callWasmModule, {
                env: {
                    impFunc: () => {
                        project(u, v, u0, v0);
                    }
                }
            });
            const __exports = __callInstance7.exports;
            return __exports.data();
        })();
    }
    var uiCallback = function (d, u, v) {
    };
    function Field(dens, u, v) {
        this.setDensity = function (x, y, d) {
            dens[x + 1 + (y + 1) * rowSize] = d;
        };
        this.getDensity = function (x, y) {
            return dens[x + 1 + (y + 1) * rowSize];
        };
        this.setVelocity = function (x, y, xv, yv) {
            u[x + 1 + (y + 1) * rowSize] = xv;
            v[x + 1 + (y + 1) * rowSize] = yv;
        };
        this.getXVelocity = function (x, y) {
            return u[x + 1 + (y + 1) * rowSize];
        };
        this.getYVelocity = function (x, y) {
            return v[x + 1 + (y + 1) * rowSize];
        };
        this.width = function () {
            return width;
        };
        this.height = function () {
            return height;
        };
    }
    function queryUI(d, u, v) {
        (() => {
            var i = 0;
            const __forInstance25 = new WebAssembly.Instance(__forWasmModule, {
                env: {
                    test: () => {
                        return i < size ? 1 : 0;
                    },
                    update: () => {
                        i++;
                    },
                    body: () => {
                        u[i] = v[i] = d[i] = 0;
                    }
                }
            });
            const __exports = __forInstance25.exports;
            return __exports.data();
        })();
        (() => {
            const __callInstance6 = new WebAssembly.Instance(__callWasmModule, {
                env: {
                    impFunc: () => {
                        uiCallback(new Field(d, u, v));
                    }
                }
            });
            const __exports = __callInstance6.exports;
            return __exports.data();
        })();
    }
    this.update = function () {
        (() => {
            const __callInstance5 = new WebAssembly.Instance(__callWasmModule, {
                env: {
                    impFunc: () => {
                        queryUI(dens_prev, u_prev, v_prev);
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
                        vel_step(u, v, u_prev, v_prev, dt);
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
                        dens_step(dens, dens_prev, u, v, dt);
                    }
                }
            });
            const __exports = __callInstance3.exports;
            return __exports.data();
        })();
        (() => {
            const __callInstance2 = new WebAssembly.Instance(__callWasmModule, {
                env: {
                    impFunc: () => {
                        displayFunc(new Field(dens, u, v));
                    }
                }
            });
            const __exports = __callInstance2.exports;
            return __exports.data();
        })();
    };
    this.setDisplayFunction = function (func) {
        displayFunc = func;
    };
    this.iterations = function () {
        return iterations;
    };
    this.setIterations = function (iters) {
        (() => {
            const __ifInstance10 = new WebAssembly.Instance(__ifWasmModule, {
                env: {
                    impFunc1: () => {
                        iterations = iters;
                    },
                    impFunc2: () => {
                    }
                }
            });
            const __exports = __ifInstance10.exports;
            return __exports.data(iters > 0 && iters <= 100 ? 1 : 0);
        })();
    };
    this.setUICallback = function (callback) {
        uiCallback = callback;
    };
    var iterations = 10;
    var visc = 0.5;
    var dt = 0.1;
    var dens;
    var dens_prev;
    var u;
    var u_prev;
    var v;
    var v_prev;
    var width;
    var height;
    var rowSize;
    var size;
    var displayFunc;
    function reset() {
        rowSize = width + 2;
        size = (width + 2) * (height + 2);
        dens = new Array(size);
        dens_prev = new Array(size);
        u = new Array(size);
        u_prev = new Array(size);
        v = new Array(size);
        v_prev = new Array(size);
        (() => {
            var i = 0;
            const __forInstance26 = new WebAssembly.Instance(__forWasmModule, {
                env: {
                    test: () => {
                        return i < size ? 1 : 0;
                    },
                    update: () => {
                        i++;
                    },
                    body: () => {
                        dens_prev[i] = u_prev[i] = v_prev[i] = dens[i] = u[i] = v[i] = 0;
                    }
                }
            });
            const __exports = __forInstance26.exports;
            return __exports.data();
        })();
    }
    this.reset = reset;
    this.getDens = function () {
        return dens;
    };
    this.setResolution = function (hRes, wRes) {
        var res = wRes * hRes;
        if (res > 0 && res < 1000000 && (wRes != width || hRes != height)) {
            width = wRes;
            height = hRes;
            (() => {
                const __callInstance1 = new WebAssembly.Instance(__callWasmModule, {
                    env: {
                        impFunc: () => {
                            reset();
                        }
                    }
                });
                const __exports = __callInstance1.exports;
                return __exports.data();
            })();
            return true;
        }
        return false;
    };
    (() => {
        const __callInstance0 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    this.setResolution(64, 64);
                }
            }
        });
        const __exports = __callInstance0.exports;
        return __exports.data();
    })();
}