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
const __wasmStringModules = ['AGFzbQEAAAAFg4CAgAABAAEGgIGAgAAWfwBBAQt/AEEUC38AQR4LfwBBLgt/AEE2C38AQcIAC38AQcoAC38AQZABC38AQZwBC38AQaQBC38AQbABC38AQbYBC38AQbwBC38AQcQBC38AQc4BC38AQdYBC38AQeIBC38AQeoBC38AQfYBC38AQYACC38AQYICC38AQYwCCwfGgYCAABcGbWVtb3J5AgAFZGF0YTADAAVkYXRhMQMBBWRhdGEyAwIFZGF0YTMDAwVkYXRhNAMEBWRhdGE1AwUFZGF0YTYDBgVkYXRhNwMHBWRhdGE4AwgFZGF0YTkDCQZkYXRhMTADCgZkYXRhMTEDCwZkYXRhMTIDDAZkYXRhMTMDDQZkYXRhMTQDDgZkYXRhMTUDDwZkYXRhMTYDEAZkYXRhMTcDEQZkYXRhMTgDEgZkYXRhMTkDEwZkYXRhMjADFAZkYXRhMjEDFQvygoCAABYAQQELEXJlYWR5c3RhdGVjaGFuZ2UAAEEUCwljb21wbGV0ZQAAQR4LDml0ZW1FbnRyeUZvcm0AAEEuCwdzdWJtaXQAAEE2CwtjbGVhckl0ZW1zAABBwgALBmNsaWNrAABBygALREFyZSUyMHlvdSUyMHN1cmUlMjB5b3UlMjB3YW50JTIwdG8lMjBjbGVhciUyMHRoZSUyMGVudGlyZSUyMGxpc3QlM0YAAEGQAQsLbXlUb0RvTGlzdAAAQZwBCwdzdHJpbmcAAEGkAQsKbGlzdEl0ZW1zAABBsAELBGRpdgAAQbYBCwVpdGVtAABBvAELBmlucHV0AABBxAELCWNoZWNrYm94AABBzgELBmxhYmVsAABB1gELCmxpc3RJdGVtcwAAQeIBCwZjbGljawAAQeoBCwtteVRvRG9MaXN0AABB9gELCG5ld0l0ZW0AAEGAAgsBAABBggILCG5ld0l0ZW0AAEGMAgsIbmV3SXRlbQA='].map(__bytes => {
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
import ToDoList from './todolist.js';
import ToDoItem from './todoitem.js';
const toDoList = new ToDoList();
(() => {
    const __callInstance36 = new WebAssembly.Instance(__callWasmModule, {
        env: {
            impFunc: () => {
                document.addEventListener(lS(0, 0), event => {
                    (() => {
                        const __ifInstance0 = new WebAssembly.Instance(__ifWasmModule, {
                            env: {
                                impFunc1: () => {
                                    {
                                        (() => {
                                            const __callInstance35 = new WebAssembly.Instance(__callWasmModule, {
                                                env: {
                                                    impFunc: () => {
                                                        initApp();
                                                    }
                                                }
                                            });
                                            const __exports = __callInstance35.exports;
                                            return __exports.data();
                                        })();
                                    }
                                },
                                impFunc2: () => {
                                }
                            }
                        });
                        const __exports = __ifInstance0.exports;
                        return __exports.data(event.target.readyState === lS(0, 1) ? 1 : 0);
                    })();
                });
            }
        }
    });
    const __exports = __callInstance36.exports;
    return __exports.data();
})();
const initApp = () => {
    const itemEntryForm = document.getElementById(lS(0, 2));
    (() => {
        const __callInstance34 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    itemEntryForm.addEventListener(lS(0, 3), event => {
                        (() => {
                            const __callInstance33 = new WebAssembly.Instance(__callWasmModule, {
                                env: {
                                    impFunc: () => {
                                        event.preventDefault();
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
                                        processSubmission();
                                    }
                                }
                            });
                            const __exports = __callInstance32.exports;
                            return __exports.data();
                        })();
                    });
                }
            }
        });
        const __exports = __callInstance34.exports;
        return __exports.data();
    })();
    const clearItems = document.getElementById(lS(0, 4));
    (() => {
        const __callInstance31 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    clearItems.addEventListener(lS(0, 5), event => {
                        const list = toDoList.getList();
                        (() => {
                            const __ifInstance1 = new WebAssembly.Instance(__ifWasmModule, {
                                env: {
                                    impFunc1: () => {
                                        {
                                            const confirmed = confirm(lS(0, 6));
                                            (() => {
                                                const __ifInstance2 = new WebAssembly.Instance(__ifWasmModule, {
                                                    env: {
                                                        impFunc1: () => {
                                                            {
                                                                (() => {
                                                                    const __callInstance30 = new WebAssembly.Instance(__callWasmModule, {
                                                                        env: {
                                                                            impFunc: () => {
                                                                                toDoList.clearList();
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
                                                                                updatePersistentData(toDoList.getList());
                                                                            }
                                                                        }
                                                                    });
                                                                    const __exports = __callInstance29.exports;
                                                                    return __exports.data();
                                                                })();
                                                                (() => {
                                                                    const __callInstance28 = new WebAssembly.Instance(__callWasmModule, {
                                                                        env: {
                                                                            impFunc: () => {
                                                                                refreshPage();
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
                                                const __exports = __ifInstance2.exports;
                                                return __exports.data(confirmed ? 1 : 0);
                                            })();
                                        }
                                    },
                                    impFunc2: () => {
                                    }
                                }
                            });
                            const __exports = __ifInstance1.exports;
                            return __exports.data(list.length ? 1 : 0);
                        })();
                    });
                }
            }
        });
        const __exports = __callInstance31.exports;
        return __exports.data();
    })();
    (() => {
        const __callInstance27 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    LoadListObject();
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
                    refreshPage();
                }
            }
        });
        const __exports = __callInstance26.exports;
        return __exports.data();
    })();
};
const LoadListObject = () => {
    const storedList = localStorage.getItem(lS(0, 7));
    if (typeof storedList != lS(0, 8))
        return;
    const parsedList = JSON.parse(storedList);
    (() => {
        const __callInstance25 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    parsedList.forEach(itemObj => {
                        const newToDoItem = createNewItem(itemObj._id, itemObj._item);
                        (() => {
                            const __callInstance24 = new WebAssembly.Instance(__callWasmModule, {
                                env: {
                                    impFunc: () => {
                                        toDoList.addItemList(newToDoItem);
                                    }
                                }
                            });
                            const __exports = __callInstance24.exports;
                            return __exports.data();
                        })();
                    });
                }
            }
        });
        const __exports = __callInstance25.exports;
        return __exports.data();
    })();
};
const refreshPage = () => {
    (() => {
        const __callInstance23 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    clearListDisplay();
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
                    renderList();
                }
            }
        });
        const __exports = __callInstance22.exports;
        return __exports.data();
    })();
    (() => {
        const __callInstance21 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    clearItemEntryField();
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
                    setFocusItemEntry();
                }
            }
        });
        const __exports = __callInstance20.exports;
        return __exports.data();
    })();
};
const clearListDisplay = () => {
    const parentElement = document.getElementById(lS(0, 9));
    (() => {
        const __callInstance19 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    deleteContents(parentElement);
                }
            }
        });
        const __exports = __callInstance19.exports;
        return __exports.data();
    })();
};
const deleteContents = parentElement => {
    let child = parentElement.lastElementChild;
    (() => {
        const __forInstance0 = new WebAssembly.Instance(__forWasmModule, {
            env: {
                test: () => {
                    return child ? 1 : 0;
                },
                update: () => {
                },
                body: () => {
                    {
                        (() => {
                            const __callInstance18 = new WebAssembly.Instance(__callWasmModule, {
                                env: {
                                    impFunc: () => {
                                        parentElement.removeChild(child);
                                    }
                                }
                            });
                            const __exports = __callInstance18.exports;
                            return __exports.data();
                        })();
                        child = parentElement.lastElementChild;
                    }
                }
            }
        });
        const __exports = __forInstance0.exports;
        return __exports.data();
    })();
};
const renderList = () => {
    const list = toDoList.getList();
    (() => {
        const __callInstance17 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    list.forEach(item => {
                        (() => {
                            const __callInstance16 = new WebAssembly.Instance(__callWasmModule, {
                                env: {
                                    impFunc: () => {
                                        buildListItem(item);
                                    }
                                }
                            });
                            const __exports = __callInstance16.exports;
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
const buildListItem = item => {
    const div = document.createElement(lS(0, 10));
    div.className = lS(0, 11);
    const check = document.createElement(lS(0, 12));
    check.type = lS(0, 13);
    check.id = item.getId();
    check.tabIndex = 0;
    (() => {
        const __callInstance15 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    addClickListenerToCheckbox(check);
                }
            }
        });
        const __exports = __callInstance15.exports;
        return __exports.data();
    })();
    const label = document.createElement(lS(0, 14));
    label.htmlFor = item.getId();
    label.textContent = item.getItem();
    (() => {
        const __callInstance14 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    div.appendChild(check);
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
                    div.appendChild(label);
                }
            }
        });
        const __exports = __callInstance13.exports;
        return __exports.data();
    })();
    const container = document.getElementById(lS(0, 15));
    (() => {
        const __callInstance12 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    container.appendChild(div);
                }
            }
        });
        const __exports = __callInstance12.exports;
        return __exports.data();
    })();
};
const addClickListenerToCheckbox = checkbox => {
    (() => {
        const __callInstance11 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    checkbox.addEventListener(lS(0, 16), event => {
                        (() => {
                            const __callInstance10 = new WebAssembly.Instance(__callWasmModule, {
                                env: {
                                    impFunc: () => {
                                        toDoList.removeItemFromList(checkbox.id);
                                    }
                                }
                            });
                            const __exports = __callInstance10.exports;
                            return __exports.data();
                        })();
                        (() => {
                            const __callInstance9 = new WebAssembly.Instance(__callWasmModule, {
                                env: {
                                    impFunc: () => {
                                        updatePersistentData(toDoList.getList());
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
                                        setTimeout(() => {
                                            (() => {
                                                const __callInstance7 = new WebAssembly.Instance(__callWasmModule, {
                                                    env: {
                                                        impFunc: () => {
                                                            refreshPage();
                                                        }
                                                    }
                                                });
                                                const __exports = __callInstance7.exports;
                                                return __exports.data();
                                            })();
                                        }, 1000);
                                    }
                                }
                            });
                            const __exports = __callInstance8.exports;
                            return __exports.data();
                        })();
                    });
                }
            }
        });
        const __exports = __callInstance11.exports;
        return __exports.data();
    })();
};
const updatePersistentData = listArray => {
    (() => {
        const __callInstance6 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    localStorage.setItem(lS(0, 17), JSON.stringify(listArray));
                }
            }
        });
        const __exports = __callInstance6.exports;
        return __exports.data();
    })();
};
const clearItemEntryField = () => {
    document.getElementById(lS(0, 18)).value = lS(0, 19);
};
const setFocusItemEntry = () => {
    (() => {
        const __callInstance5 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    document.getElementById(lS(0, 20)).focus();
                }
            }
        });
        const __exports = __callInstance5.exports;
        return __exports.data();
    })();
};
const processSubmission = () => {
    const newEntryText = getNewEntry();
    if (!newEntryText.length)
        return;
    const nextItemId = calcNetItemId();
    const toDoItem = createNewItem(nextItemId, newEntryText);
    (() => {
        const __callInstance4 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    toDoList.addItemList(toDoItem);
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
                    updatePersistentData(toDoList.getList());
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
                    refreshPage();
                }
            }
        });
        const __exports = __callInstance2.exports;
        return __exports.data();
    })();
};
const getNewEntry = () => {
    return document.getElementById(lS(0, 21)).value.trim();
};
const calcNetItemId = () => {
    let nextItemId = 1;
    const list = toDoList.getList();
    (() => {
        const __ifInstance3 = new WebAssembly.Instance(__ifWasmModule, {
            env: {
                impFunc1: () => {
                    {
                        nextItemId = list[list.length - 1].getId() + 1;
                    }
                },
                impFunc2: () => {
                }
            }
        });
        const __exports = __ifInstance3.exports;
        return __exports.data(list.length > 0 ? 1 : 0);
    })();
    return nextItemId;
};
const createNewItem = (itemId, itemText) => {
    const toDo = new ToDoItem();
    (() => {
        const __callInstance1 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    toDo.setId(itemId);
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
                    toDo.setItem(itemText);
                }
            }
        });
        const __exports = __callInstance0.exports;
        return __exports.data();
    })();
    return toDo;
};