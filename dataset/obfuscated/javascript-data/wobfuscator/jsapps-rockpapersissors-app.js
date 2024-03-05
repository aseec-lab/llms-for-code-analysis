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
const __wasmStringModules = ['AGFzbQEAAAAFg4CAgAABAAEGxoGAgAAifwBBAQt/AEEKC38AQRwLfwBBJAt/AEEsC38AQTYLfwBBPgt/AEHOAAt/AEHcAAt/AEHwAAt/AEH+AAt/AEGMAQt/AEGOAQt/AEGUAQt/AEGcAQt/AEGmAQt/AEGuAQt/AEHIAQt/AEHkAQt/AEHuAQt/AEGCAgt/AEGIAgt/AEGSAgt/AEGiAgt/AEG0Agt/AEG8Agt/AEHCAgt/AEHSAgt/AEHkAgt/AEHuAgt/AEH2Agt/AEGGAwt/AEGYAwt/AEGsAwsHsoKAgAAjBm1lbW9yeQIABWRhdGEwAwAFZGF0YTEDAQVkYXRhMgMCBWRhdGEzAwMFZGF0YTQDBAVkYXRhNQMFBWRhdGE2AwYFZGF0YTcDBwVkYXRhOAMIBWRhdGE5AwkGZGF0YTEwAwoGZGF0YTExAwsGZGF0YTEyAwwGZGF0YTEzAw0GZGF0YTE0Aw4GZGF0YTE1Aw8GZGF0YTE2AxAGZGF0YTE3AxEGZGF0YTE4AxIGZGF0YTE5AxMGZGF0YTIwAxQGZGF0YTIxAxUGZGF0YTIyAxYGZGF0YTIzAxcGZGF0YTI0AxgGZGF0YTI1AxkGZGF0YTI2AxoGZGF0YTI3AxsGZGF0YTI4AxwGZGF0YTI5Ax0GZGF0YTMwAx4GZGF0YTMxAx8GZGF0YTMyAyAGZGF0YTMzAyEL04SAgAAiAEEBCwcuaW50cm8AAEEKCxAuaW50cm8lMjBidXR0b24AAEEcCwcubWF0Y2gAAEEkCwZjbGljawAAQSwLCGZhZGVPdXQAAEE2CwdmYWRlSW4AAEE+Cw8uY29tcHV0ZXItaGFuZAAAQc4ACw0ucGxheWVyLWhhbmQAAEHcAAsSLm9wdGlvbnMlMjBidXR0b24AAEHwAAsNLmhhbmRzJTIwaW1nAABB/gALDWFuaW1hdGlvbmVuZAAAQYwBCwEAAEGOAQsFcm9jawAAQZQBCwZwYXBlcgAAQZwBCwlzY2lzc29ycwAAQaYBCwZjbGljawAAQa4BCxhzaGFrZVBsYXllciUyMDJzJTIwZWFzZQAAQcgBCxpzaGFrZUNvbXB1dGVyJTIwMnMlMjBlYXNlAABB5AELCC53aW5uZXIAAEHuAQsSSXQlMjBpcyUyMGElMjB0aWUAAEGCAgsFcm9jawAAQYgCCwlzY2lzc29ycwAAQZICCw9QbGF5ZXIlMjBXaW5zIQAAQaICCxBDb21wdXRlciUyMFdpbnMAAEG0AgsGcGFwZXIAAEG8AgsFcm9jawAAQcICCw9QbGF5ZXIlMjBXaW5zIQAAQdICCxBDb21wdXRlciUyMFdpbnMAAEHkAgsJc2Npc3NvcnMAAEHuAgsGcGFwZXIAAEH2AgsPUGxheWVyJTIwV2lucyEAAEGGAwsQQ29tcHV0ZXIlMjBXaW5zAABBmAMLEi5wbGF5ZXItc2NvcmUlMjBwAABBrAMLFC5jb21wdXRlci1zY29yZSUyMHAA'].map(__bytes => {
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
const game = () => {
    let pScore = 0;
    let cScore = 0;
    const startGame = () => {
        const introScreen = document.querySelector(lS(0, 0));
        const playBtn = document.querySelector(lS(0, 1));
        const match = document.querySelector(lS(0, 2));
        (() => {
            const __callInstance17 = new WebAssembly.Instance(__callWasmModule, {
                env: {
                    impFunc: () => {
                        playBtn.addEventListener(lS(0, 3), () => {
                            (() => {
                                const __callInstance16 = new WebAssembly.Instance(__callWasmModule, {
                                    env: {
                                        impFunc: () => {
                                            introScreen.classList.add(lS(0, 4));
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
                                            match.classList.add(lS(0, 5));
                                        }
                                    }
                                });
                                const __exports = __callInstance15.exports;
                                return __exports.data();
                            })();
                        });
                    }
                }
            });
            const __exports = __callInstance17.exports;
            return __exports.data();
        })();
    };
    const playMatch = () => {
        const computerHand = document.querySelector(lS(0, 6));
        const playerHand = document.querySelector(lS(0, 7));
        const options = document.querySelectorAll(lS(0, 8));
        const hands = document.querySelectorAll(lS(0, 9));
        (() => {
            const __callInstance14 = new WebAssembly.Instance(__callWasmModule, {
                env: {
                    impFunc: () => {
                        hands.forEach(hand => {
                            (() => {
                                const __callInstance13 = new WebAssembly.Instance(__callWasmModule, {
                                    env: {
                                        impFunc: () => {
                                            hand.addEventListener(lS(0, 10), function () {
                                                this.style.animation = lS(0, 11);
                                            });
                                        }
                                    }
                                });
                                const __exports = __callInstance13.exports;
                                return __exports.data();
                            })();
                        });
                    }
                }
            });
            const __exports = __callInstance14.exports;
            return __exports.data();
        })();
        const computerOptions = [
            lS(0, 12),
            lS(0, 13),
            lS(0, 14)
        ];
        (() => {
            const __callInstance12 = new WebAssembly.Instance(__callWasmModule, {
                env: {
                    impFunc: () => {
                        options.forEach(option => {
                            (() => {
                                const __callInstance11 = new WebAssembly.Instance(__callWasmModule, {
                                    env: {
                                        impFunc: () => {
                                            option.addEventListener(lS(0, 15), function () {
                                                const computerNumber = Math.floor(Math.random() * 3);
                                                const computerChoise = computerOptions[computerNumber];
                                                (() => {
                                                    const __callInstance10 = new WebAssembly.Instance(__callWasmModule, {
                                                        env: {
                                                            impFunc: () => {
                                                                setTimeout(() => {
                                                                    (() => {
                                                                        const __callInstance9 = new WebAssembly.Instance(__callWasmModule, {
                                                                            env: {
                                                                                impFunc: () => {
                                                                                    compareHands(this.textContent, computerChoise);
                                                                                }
                                                                            }
                                                                        });
                                                                        const __exports = __callInstance9.exports;
                                                                        return __exports.data();
                                                                    })();
                                                                    playerHand.src = `./imgs/${ this.textContent }.png`;
                                                                    computerHand.src = `./imgs/${ computerChoise }.png`;
                                                                }, 2000);
                                                            }
                                                        }
                                                    });
                                                    const __exports = __callInstance10.exports;
                                                    return __exports.data();
                                                })();
                                                playerHand.style.animation = lS(0, 16);
                                                computerHand.style.animation = lS(0, 17);
                                            });
                                        }
                                    }
                                });
                                const __exports = __callInstance11.exports;
                                return __exports.data();
                            })();
                        });
                    }
                }
            });
            const __exports = __callInstance12.exports;
            return __exports.data();
        })();
    };
    const compareHands = (playerChoise, computerChoise) => {
        const winner = document.querySelector(lS(0, 18));
        if (playerChoise === computerChoise) {
            winner.textContent = lS(0, 19);
            return;
        }
        if (playerChoise === lS(0, 20)) {
            if (computerChoise === lS(0, 21)) {
                winner.textContent = lS(0, 22);
                pScore++;
                (() => {
                    const __callInstance8 = new WebAssembly.Instance(__callWasmModule, {
                        env: {
                            impFunc: () => {
                                updateScore();
                            }
                        }
                    });
                    const __exports = __callInstance8.exports;
                    return __exports.data();
                })();
                return;
            } else {
                winner.textContent = lS(0, 23);
                cScore++;
                (() => {
                    const __callInstance7 = new WebAssembly.Instance(__callWasmModule, {
                        env: {
                            impFunc: () => {
                                updateScore();
                            }
                        }
                    });
                    const __exports = __callInstance7.exports;
                    return __exports.data();
                })();
                return;
            }
        }
        if (playerChoise === lS(0, 24)) {
            if (computerChoise === lS(0, 25)) {
                winner.textContent = lS(0, 26);
                pScore++;
                (() => {
                    const __callInstance6 = new WebAssembly.Instance(__callWasmModule, {
                        env: {
                            impFunc: () => {
                                updateScore();
                            }
                        }
                    });
                    const __exports = __callInstance6.exports;
                    return __exports.data();
                })();
                return;
            } else {
                winner.textContent = lS(0, 27);
                cScore++;
                (() => {
                    const __callInstance5 = new WebAssembly.Instance(__callWasmModule, {
                        env: {
                            impFunc: () => {
                                updateScore();
                            }
                        }
                    });
                    const __exports = __callInstance5.exports;
                    return __exports.data();
                })();
                return;
            }
        }
        if (playerChoise === lS(0, 28)) {
            if (computerChoise === lS(0, 29)) {
                winner.textContent = lS(0, 30);
                pScore++;
                (() => {
                    const __callInstance4 = new WebAssembly.Instance(__callWasmModule, {
                        env: {
                            impFunc: () => {
                                updateScore();
                            }
                        }
                    });
                    const __exports = __callInstance4.exports;
                    return __exports.data();
                })();
                return;
            } else {
                winner.textContent = lS(0, 31);
                cScore++;
                (() => {
                    const __callInstance3 = new WebAssembly.Instance(__callWasmModule, {
                        env: {
                            impFunc: () => {
                                updateScore();
                            }
                        }
                    });
                    const __exports = __callInstance3.exports;
                    return __exports.data();
                })();
                return;
            }
        }
    };
    const updateScore = () => {
        const playerScore = document.querySelector(lS(0, 32));
        const computerScore = document.querySelector(lS(0, 33));
        playerScore.textContent = pScore;
        computerScore.textContent = cScore;
    };
    (() => {
        const __callInstance2 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    startGame();
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
                    playMatch();
                }
            }
        });
        const __exports = __callInstance1.exports;
        return __exports.data();
    })();
};
(() => {
    const __callInstance0 = new WebAssembly.Instance(__callWasmModule, {
        env: {
            impFunc: () => {
                game();
            }
        }
    });
    const __exports = __callInstance0.exports;
    return __exports.data();
})();