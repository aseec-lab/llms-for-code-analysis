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
const __wasmStringModules = ['AGFzbQEAAAAFg4CAgAABAAEG5IGAgAAnfwBBAQt/AEEQC38AQRgLfwBBIAt/AEEoC38AQTALfwBBNAt/AEHYAAt/AEHiAAt/AEHoAAt/AEHyAAt/AEH8AAt/AEGCAQt/AEGKAQt/AEGYAQt/AEGeAQt/AEGmAQt/AEGwAQt/AEG2AQt/AEHAAQt/AEHOAQt/AEHYAQt/AEHeAQt/AEHiAQt/AEHsAQt/AEHwAQt/AEH2AQt/AEH8AQt/AEGGAgt/AEGOAgt/AEGYAgt/AEGgAgt/AEGmAgt/AEHAAgt/AEHeAgt/AEHsAgt/AEHyAgt/AEH0Agt/AEH+AgsH34KAgAAoBm1lbW9yeQIABWRhdGEwAwAFZGF0YTEDAQVkYXRhMgMCBWRhdGEzAwMFZGF0YTQDBAVkYXRhNQMFBWRhdGE2AwYFZGF0YTcDBwVkYXRhOAMIBWRhdGE5AwkGZGF0YTEwAwoGZGF0YTExAwsGZGF0YTEyAwwGZGF0YTEzAw0GZGF0YTE0Aw4GZGF0YTE1Aw8GZGF0YTE2AxAGZGF0YTE3AxEGZGF0YTE4AxIGZGF0YTE5AxMGZGF0YTIwAxQGZGF0YTIxAxUGZGF0YTIyAxYGZGF0YTIzAxcGZGF0YTI0AxgGZGF0YTI1AxkGZGF0YTI2AxoGZGF0YTI3AxsGZGF0YTI4AxwGZGF0YTI5Ax0GZGF0YTMwAx4GZGF0YTMxAx8GZGF0YTMyAyAGZGF0YTMzAyEGZGF0YTM0AyIGZGF0YTM1AyMGZGF0YTM2AyQGZGF0YTM3AyUGZGF0YTM4AyYLuYSAgAAnAEEBCw11c2UlMjBzdHJpY3QAAEEQCwZ3aWR0aAAAQRgLBndpZHRoAABBIAsHaGVpZ2h0AABBKAsHc2Nyb2xsAABBMAsCLQAAQTQLIyUzRSUyMC5hY2NvcmRpb24tZ3JvdXAlMjAlM0UlMjAuaW4AAEHYAAsJY29sbGFwc2UAAEHiAAsFaGlkZQAAQegACwljb2xsYXBzZQAAQfIACwlhZGRDbGFzcwAAQfwACwVzaG93AABBggELBnNob3duAABBigELDHJlbW92ZUNsYXNzAABBmAELBWhpZGUAAEGeAQsHaGlkZGVuAABBpgELCWNvbGxhcHNlAABBsAELBWF1dG8AAEG2AQsJYWRkQ2xhc3MAAEHAAQsMcmVtb3ZlQ2xhc3MAAEHOAQsJY29sbGFwc2UAAEHYAQsFc2hvdwAAQd4BCwNpbgAAQeIBCwljb2xsYXBzZQAAQewBCwNpbgAAQfABCwVoaWRlAABB9gELBXNob3cAAEH8AQsJY29sbGFwc2UAAEGGAgsHb2JqZWN0AABBjgILCWNvbGxhcHNlAABBmAILB3N0cmluZwAAQaACCwVib2R5AABBpgILGGNsaWNrLmNvbGxhcHNlLmRhdGEtYXBpAABBwAILHSU1QmRhdGEtdG9nZ2xlJTNEY29sbGFwc2UlNUQAAEHeAgsMZGF0YS10YXJnZXQAAEHsAgsFaHJlZgAAQfICCwEAAEH0AgsJY29sbGFwc2UAAEH+AgsHdG9nZ2xlAA=='].map(__bytes => {
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
!function ($) {
    lS(0, 0);
    var Collapse = function (element, options) {
        this.$element = $(element);
        this.options = $.extend({}, $.fn.collapse.defaults, options);
        (() => {
            const __ifInstance0 = new WebAssembly.Instance(__ifWasmModule, {
                env: {
                    impFunc1: () => {
                        {
                            this.$parent = $(this.options.parent);
                        }
                    },
                    impFunc2: () => {
                    }
                }
            });
            const __exports = __ifInstance0.exports;
            return __exports.data(this.options.parent ? 1 : 0);
        })();
        this.options.toggle && this.toggle();
    };
    Collapse.prototype = {
        constructor: Collapse,
        dimension: function () {
            var hasWidth = this.$element.hasClass(lS(0, 1));
            return hasWidth ? lS(0, 2) : lS(0, 3);
        },
        show: function () {
            var dimension, scroll, actives, hasData;
            if (this.transitioning)
                return;
            dimension = this.dimension();
            scroll = $.camelCase([
                lS(0, 4),
                dimension
            ].join(lS(0, 5)));
            actives = this.$parent && this.$parent.find(lS(0, 6));
            if (actives && actives.length) {
                hasData = actives.data(lS(0, 7));
                if (hasData && hasData.transitioning)
                    return;
                (() => {
                    const __callInstance17 = new WebAssembly.Instance(__callWasmModule, {
                        env: {
                            impFunc: () => {
                                actives.collapse(lS(0, 8));
                            }
                        }
                    });
                    const __exports = __callInstance17.exports;
                    return __exports.data();
                })();
                hasData || actives.data(lS(0, 9), null);
            }
            (() => {
                const __callInstance16 = new WebAssembly.Instance(__callWasmModule, {
                    env: {
                        impFunc: () => {
                            this.$element[dimension](0);
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
                            this.transition(lS(0, 10), $.Event(lS(0, 11)), lS(0, 12));
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
                            this.$element[dimension](this.$element[0][scroll]);
                        }
                    }
                });
                const __exports = __callInstance14.exports;
                return __exports.data();
            })();
        },
        hide: function () {
            var dimension;
            if (this.transitioning)
                return;
            dimension = this.dimension();
            (() => {
                const __callInstance13 = new WebAssembly.Instance(__callWasmModule, {
                    env: {
                        impFunc: () => {
                            this.reset(this.$element[dimension]());
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
                            this.transition(lS(0, 13), $.Event(lS(0, 14)), lS(0, 15));
                        }
                    }
                });
                const __exports = __callInstance12.exports;
                return __exports.data();
            })();
            (() => {
                const __callInstance11 = new WebAssembly.Instance(__callWasmModule, {
                    env: {
                        impFunc: () => {
                            this.$element[dimension](0);
                        }
                    }
                });
                const __exports = __callInstance11.exports;
                return __exports.data();
            })();
        },
        reset: function (size) {
            var dimension = this.dimension();
            this.$element.removeClass(lS(0, 16))[dimension](size || lS(0, 17))[0].offsetWidth;
            (() => {
                const __callInstance10 = new WebAssembly.Instance(__callWasmModule, {
                    env: {
                        impFunc: () => {
                            this.$element[size !== null ? lS(0, 18) : lS(0, 19)](lS(0, 20));
                        }
                    }
                });
                const __exports = __callInstance10.exports;
                return __exports.data();
            })();
            return this;
        },
        transition: function (method, startEvent, completeEvent) {
            var that = this, complete = function () {
                    (() => {
                        const __ifInstance1 = new WebAssembly.Instance(__ifWasmModule, {
                            env: {
                                impFunc1: () => {
                                    (() => {
                                        const __callInstance9 = new WebAssembly.Instance(__callWasmModule, {
                                            env: {
                                                impFunc: () => {
                                                    that.reset();
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
                        const __exports = __ifInstance1.exports;
                        return __exports.data(startEvent.type == lS(0, 21) ? 1 : 0);
                    })();
                    that.transitioning = 0;
                    (() => {
                        const __callInstance8 = new WebAssembly.Instance(__callWasmModule, {
                            env: {
                                impFunc: () => {
                                    that.$element.trigger(completeEvent);
                                }
                            }
                        });
                        const __exports = __callInstance8.exports;
                        return __exports.data();
                    })();
                };
            (() => {
                const __callInstance7 = new WebAssembly.Instance(__callWasmModule, {
                    env: {
                        impFunc: () => {
                            this.$element.trigger(startEvent);
                        }
                    }
                });
                const __exports = __callInstance7.exports;
                return __exports.data();
            })();
            if (startEvent.isDefaultPrevented())
                return;
            this.transitioning = 1;
            (() => {
                const __callInstance6 = new WebAssembly.Instance(__callWasmModule, {
                    env: {
                        impFunc: () => {
                            this.$element[method](lS(0, 22));
                        }
                    }
                });
                const __exports = __callInstance6.exports;
                return __exports.data();
            })();
            $.support.transition && this.$element.hasClass(lS(0, 23)) ? this.$element.one($.support.transition.end, complete) : complete();
        },
        toggle: function () {
            (() => {
                const __callInstance5 = new WebAssembly.Instance(__callWasmModule, {
                    env: {
                        impFunc: () => {
                            this[this.$element.hasClass(lS(0, 24)) ? lS(0, 25) : lS(0, 26)]();
                        }
                    }
                });
                const __exports = __callInstance5.exports;
                return __exports.data();
            })();
        }
    };
    $.fn.collapse = function (option) {
        return this.each(function () {
            var $this = $(this), data = $this.data(lS(0, 27)), options = typeof option == lS(0, 28) && option;
            (() => {
                const __ifInstance2 = new WebAssembly.Instance(__ifWasmModule, {
                    env: {
                        impFunc1: () => {
                            (() => {
                                const __callInstance4 = new WebAssembly.Instance(__callWasmModule, {
                                    env: {
                                        impFunc: () => {
                                            $this.data(lS(0, 29), data = new Collapse(this, options));
                                        }
                                    }
                                });
                                const __exports = __callInstance4.exports;
                                return __exports.data();
                            })();
                        },
                        impFunc2: () => {
                        }
                    }
                });
                const __exports = __ifInstance2.exports;
                return __exports.data(!data ? 1 : 0);
            })();
            (() => {
                const __ifInstance3 = new WebAssembly.Instance(__ifWasmModule, {
                    env: {
                        impFunc1: () => {
                            (() => {
                                const __callInstance3 = new WebAssembly.Instance(__callWasmModule, {
                                    env: {
                                        impFunc: () => {
                                            data[option]();
                                        }
                                    }
                                });
                                const __exports = __callInstance3.exports;
                                return __exports.data();
                            })();
                        },
                        impFunc2: () => {
                        }
                    }
                });
                const __exports = __ifInstance3.exports;
                return __exports.data(typeof option == lS(0, 30) ? 1 : 0);
            })();
        });
    };
    $.fn.collapse.defaults = { toggle: true };
    $.fn.collapse.Constructor = Collapse;
    (() => {
        const __callInstance2 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    $(function () {
                        (() => {
                            const __callInstance1 = new WebAssembly.Instance(__callWasmModule, {
                                env: {
                                    impFunc: () => {
                                        $(lS(0, 31)).on(lS(0, 32), lS(0, 33), function (e) {
                                            var $this = $(this), href, target = $this.attr(lS(0, 34)) || e.preventDefault() || (href = $this.attr(lS(0, 35))) && href.replace(/.*(?=#[^\s]+$)/, lS(0, 36)), option = $(target).data(lS(0, 37)) ? lS(0, 38) : $this.data();
                                            (() => {
                                                const __callInstance0 = new WebAssembly.Instance(__callWasmModule, {
                                                    env: {
                                                        impFunc: () => {
                                                            $(target).collapse(option);
                                                        }
                                                    }
                                                });
                                                const __exports = __callInstance0.exports;
                                                return __exports.data();
                                            })();
                                        });
                                    }
                                }
                            });
                            const __exports = __callInstance1.exports;
                            return __exports.data();
                        })();
                    });
                }
            }
        });
        const __exports = __callInstance2.exports;
        return __exports.data();
    })();
}(window.jQuery);