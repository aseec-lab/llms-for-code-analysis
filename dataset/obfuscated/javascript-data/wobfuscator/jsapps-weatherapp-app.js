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
const __wasmStringModules = ['AGFzbQEAAAAFg4CAgAABAAEGxYCAgAAMfwBBAQt/AEEWC38AQSQLfwBBOgt/AEHQAAt/AEHqAAt/AEGYAQt/AEGgAQt/AEGoAQt/AEGsAQt/AEGwAQt/AEG0AQsH7ICAgAANBm1lbW9yeQIABWRhdGEwAwAFZGF0YTEDAQVkYXRhMgMCBWRhdGEzAwMFZGF0YTQDBAVkYXRhNQMFBWRhdGE2AwYFZGF0YTcDBwVkYXRhOAMIBWRhdGE5AwkGZGF0YTEwAwoGZGF0YTExAwsL54GAgAAMAEEBCxMubG9jYXRpb24tdGltZXpvbmUAAEEWCw0udGVtcGVyYXR1cmUAAEEkCxQudGVtcGVyYXR1cmUtZGVncmVlAABBOgsULnRlbXBlcmF0dXJlJTIwc3BhbgAAQdAACxkudGVtcGVyYXR1cmUtZGVzY3JpcHRpb24AAEHqAAstaHR0cHMlM0ElMkYlMkZjb3JzLWFueXdoZXJlLmhlcm9rdWFwcC5jb20lMkYAAEGYAQsGLmljb24AAEGgAQsGY2xpY2sAAEGoAQsCRgAAQawBCwJDAABBsAELAkYAAEG0AQsCXwA='].map(__bytes => {
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
    const __callInstance6 = new WebAssembly.Instance(__callWasmModule, {
        env: {
            impFunc: () => {
                (function () {
                    let long;
                    let lat;
                    let locationTimezone = document.querySelector(lS(0, 0));
                    let temperatureSection = document.querySelector(lS(0, 1));
                    let temperatureDegree = document.querySelector(lS(0, 2));
                    let temperatureSpan = document.querySelector(lS(0, 3));
                    let temperatureDescription = document.querySelector(lS(0, 4));
                    if (navigator.geolocation) {
                        (() => {
                            const __callInstance5 = new WebAssembly.Instance(__callWasmModule, {
                                env: {
                                    impFunc: () => {
                                        navigator.geolocation.getCurrentPosition(position => {
                                            long = position.coords.longitude;
                                            lat = position.coords.latitude;
                                            const proxy = lS(0, 5);
                                            const api = `${ proxy }https://api.darksky.net/forecast/3ed2820bdef835d0923968060af681dd/${ lat },${ long }`;
                                            (() => {
                                                const __callInstance4 = new WebAssembly.Instance(__callWasmModule, {
                                                    env: {
                                                        impFunc: () => {
                                                            fetch(api).then(response => {
                                                                return response.json();
                                                            }).then(data => {
                                                                (() => {
                                                                    const __callInstance3 = new WebAssembly.Instance(__callWasmModule, {
                                                                        env: {
                                                                            impFunc: () => {
                                                                                console.log(data);
                                                                            }
                                                                        }
                                                                    });
                                                                    const __exports = __callInstance3.exports;
                                                                    return __exports.data();
                                                                })();
                                                                const {temperature, summary, icon} = data.currently;
                                                                locationTimezone.textContent = data.timezone;
                                                                temperatureDegree.textContent = temperature;
                                                                temperatureDescription.textContent = summary;
                                                                (() => {
                                                                    const __callInstance2 = new WebAssembly.Instance(__callWasmModule, {
                                                                        env: {
                                                                            impFunc: () => {
                                                                                setIcons(icon, document.querySelector(lS(0, 6)));
                                                                            }
                                                                        }
                                                                    });
                                                                    const __exports = __callInstance2.exports;
                                                                    return __exports.data();
                                                                })();
                                                                let celsius = (temperature - 32) * (5 / 9);
                                                                (() => {
                                                                    const __callInstance1 = new WebAssembly.Instance(__callWasmModule, {
                                                                        env: {
                                                                            impFunc: () => {
                                                                                temperatureSection.addEventListener(lS(0, 7), () => {
                                                                                    (() => {
                                                                                        const __ifInstance0 = new WebAssembly.Instance(__ifWasmModule, {
                                                                                            env: {
                                                                                                impFunc1: () => {
                                                                                                    {
                                                                                                        temperatureSpan.textContent = lS(0, 9);
                                                                                                        temperatureDegree.textContent = Math.floor(celsius);
                                                                                                    }
                                                                                                },
                                                                                                impFunc2: () => {
                                                                                                    {
                                                                                                        temperatureSpan.textContent = lS(0, 10);
                                                                                                        temperatureDegree.textContent = temperature;
                                                                                                    }
                                                                                                }
                                                                                            }
                                                                                        });
                                                                                        const __exports = __ifInstance0.exports;
                                                                                        return __exports.data(temperatureSpan.textContent === lS(0, 8) ? 1 : 0);
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
                                                const __exports = __callInstance4.exports;
                                                return __exports.data();
                                            })();
                                        });
                                    }
                                }
                            });
                            const __exports = __callInstance5.exports;
                            return __exports.data();
                        })();
                    }
                    function setIcons(icon, iconID) {
                        const skycons = new Skycons({ color: 'white' });
                        const currentIcon = icon.replace(/-/g, lS(0, 11)).toUpperCase();
                        (() => {
                            const __callInstance0 = new WebAssembly.Instance(__callWasmModule, {
                                env: {
                                    impFunc: () => {
                                        skycons.play();
                                    }
                                }
                            });
                            const __exports = __callInstance0.exports;
                            return __exports.data();
                        })();
                        return skycons.set(iconID, Skycons[currentIcon]);
                    }
                }());
            }
        }
    });
    const __exports = __callInstance6.exports;
    return __exports.data();
})();