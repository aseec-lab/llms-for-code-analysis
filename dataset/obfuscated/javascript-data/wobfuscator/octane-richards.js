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
const __aB = 'AGFzbQEAAAABiYCAgAACYAAAYAJ/fwADg4CAgAACAQAFg4CAgAABAAEGhoCAgAABfwFBAAsHkYCAgAACBm1lbW9yeQIABGFycjAAAQqqgICAAAKPgICAAAAjACAAQQRsaiABNgIAC5CAgIAAAQF/QRAkAEEAQeaTAhAACw==', __wAM = new WebAssembly.Instance(new WebAssembly.Module((() => {
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
const __wasmStringModules = ['AGFzbQEAAAAFg4CAgAABAAEGzICAgAANfwBBAQt/AEEMC38AQRYLfwBBzAALfwBB5gALfwBB6gALfwBB+AALfwBB/gALfwBBhgELfwBBkAELfwBBnAELfwBBqAELfwBBtgELB/WAgIAADgZtZW1vcnkCAAVkYXRhMAMABWRhdGExAwEFZGF0YTIDAgVkYXRhMwMDBWRhdGE0AwQFZGF0YTUDBQVkYXRhNgMGBWRhdGE3AwcFZGF0YTgDCAVkYXRhOQMJBmRhdGExMAMKBmRhdGExMQMLBmRhdGExMgMMC/eBgIAADQBBAQsJUmljaGFyZHMAAEEMCwlSaWNoYXJkcwAAQRYLNEVycm9yJTIwZHVyaW5nJTIwZXhlY3V0aW9uJTNBJTIwcXVldWVDb3VudCUyMCUzRCUyMAAAQcwACxklMkMlMjBob2xkQ291bnQlMjAlM0QlMjAAAEHmAAsCLgAAQeoACw10Y2IlMjAlN0IlMjAAAEH4AAsEJTQwAABB/gALByUyMCU3RAAAQYYBCwlJZGxlVGFzawAAQZABCwtEZXZpY2VUYXNrAABBnAELC1dvcmtlclRhc2sAAEGoAQsMSGFuZGxlclRhc2sAAEG2AQsHUGFja2V0AA=='].map(__bytes => {
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
var Richards = new BenchmarkSuite(lS(0, 0), __lA(0, 16, 20), [new Benchmark(lS(0, 1), true, false, 8200, runRichards)]);
function runRichards() {
    var scheduler = new Scheduler();
    (() => {
        const __callInstance16 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    scheduler.addIdleTask(ID_IDLE, 0, null, COUNT);
                }
            }
        });
        const __exports = __callInstance16.exports;
        return __exports.data();
    })();
    var queue = new Packet(null, ID_WORKER, KIND_WORK);
    queue = new Packet(queue, ID_WORKER, KIND_WORK);
    (() => {
        const __callInstance15 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    scheduler.addWorkerTask(ID_WORKER, 1000, queue);
                }
            }
        });
        const __exports = __callInstance15.exports;
        return __exports.data();
    })();
    queue = new Packet(null, ID_DEVICE_A, KIND_DEVICE);
    queue = new Packet(queue, ID_DEVICE_A, KIND_DEVICE);
    queue = new Packet(queue, ID_DEVICE_A, KIND_DEVICE);
    (() => {
        const __callInstance14 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    scheduler.addHandlerTask(ID_HANDLER_A, 2000, queue);
                }
            }
        });
        const __exports = __callInstance14.exports;
        return __exports.data();
    })();
    queue = new Packet(null, ID_DEVICE_B, KIND_DEVICE);
    queue = new Packet(queue, ID_DEVICE_B, KIND_DEVICE);
    queue = new Packet(queue, ID_DEVICE_B, KIND_DEVICE);
    (() => {
        const __callInstance13 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    scheduler.addHandlerTask(ID_HANDLER_B, 3000, queue);
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
                    scheduler.addDeviceTask(ID_DEVICE_A, 4000, null);
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
                    scheduler.addDeviceTask(ID_DEVICE_B, 5000, null);
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
                    scheduler.schedule();
                }
            }
        });
        const __exports = __callInstance10.exports;
        return __exports.data();
    })();
    if (scheduler.queueCount != EXPECTED_QUEUE_COUNT || scheduler.holdCount != EXPECTED_HOLD_COUNT) {
        var msg = lS(0, 2) + scheduler.queueCount + lS(0, 3) + scheduler.holdCount + lS(0, 4);
        throw new Error(msg);
    }
}
var COUNT = 1000;
var EXPECTED_QUEUE_COUNT = 2322;
var EXPECTED_HOLD_COUNT = 928;
function Scheduler() {
    this.queueCount = 0;
    this.holdCount = 0;
    this.blocks = new Array(NUMBER_OF_IDS);
    this.list = null;
    this.currentTcb = null;
    this.currentId = null;
}
var ID_IDLE = 0;
var ID_WORKER = 1;
var ID_HANDLER_A = 2;
var ID_HANDLER_B = 3;
var ID_DEVICE_A = 4;
var ID_DEVICE_B = 5;
var NUMBER_OF_IDS = 6;
var KIND_DEVICE = 0;
var KIND_WORK = 1;
Scheduler.prototype.addIdleTask = function (id, priority, queue, count) {
    (() => {
        const __callInstance9 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    this.addRunningTask(id, priority, queue, new IdleTask(this, 1, count));
                }
            }
        });
        const __exports = __callInstance9.exports;
        return __exports.data();
    })();
};
Scheduler.prototype.addWorkerTask = function (id, priority, queue) {
    (() => {
        const __callInstance8 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    this.addTask(id, priority, queue, new WorkerTask(this, ID_HANDLER_A, 0));
                }
            }
        });
        const __exports = __callInstance8.exports;
        return __exports.data();
    })();
};
Scheduler.prototype.addHandlerTask = function (id, priority, queue) {
    (() => {
        const __callInstance7 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    this.addTask(id, priority, queue, new HandlerTask(this));
                }
            }
        });
        const __exports = __callInstance7.exports;
        return __exports.data();
    })();
};
Scheduler.prototype.addDeviceTask = function (id, priority, queue) {
    (() => {
        const __callInstance6 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    this.addTask(id, priority, queue, new DeviceTask(this));
                }
            }
        });
        const __exports = __callInstance6.exports;
        return __exports.data();
    })();
};
Scheduler.prototype.addRunningTask = function (id, priority, queue, task) {
    (() => {
        const __callInstance5 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    this.addTask(id, priority, queue, task);
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
                    this.currentTcb.setRunning();
                }
            }
        });
        const __exports = __callInstance4.exports;
        return __exports.data();
    })();
};
Scheduler.prototype.addTask = function (id, priority, queue, task) {
    this.currentTcb = new TaskControlBlock(this.list, id, priority, queue, task);
    this.list = this.currentTcb;
    this.blocks[id] = this.currentTcb;
};
Scheduler.prototype.schedule = function () {
    this.currentTcb = this.list;
    (() => {
        const __forInstance1 = new WebAssembly.Instance(__forWasmModule, {
            env: {
                test: () => {
                    return this.currentTcb != null ? 1 : 0;
                },
                update: () => {
                },
                body: () => {
                    {
                        (() => {
                            const __ifInstance0 = new WebAssembly.Instance(__ifWasmModule, {
                                env: {
                                    impFunc1: () => {
                                        {
                                            this.currentTcb = this.currentTcb.link;
                                        }
                                    },
                                    impFunc2: () => {
                                        {
                                            this.currentId = this.currentTcb.id;
                                            this.currentTcb = this.currentTcb.run();
                                        }
                                    }
                                }
                            });
                            const __exports = __ifInstance0.exports;
                            return __exports.data(this.currentTcb.isHeldOrSuspended() ? 1 : 0);
                        })();
                    }
                }
            }
        });
        const __exports = __forInstance1.exports;
        return __exports.data();
    })();
};
Scheduler.prototype.release = function (id) {
    var tcb = this.blocks[id];
    if (tcb == null)
        return tcb;
    (() => {
        const __callInstance3 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    tcb.markAsNotHeld();
                }
            }
        });
        const __exports = __callInstance3.exports;
        return __exports.data();
    })();
    if (tcb.priority > this.currentTcb.priority) {
        return tcb;
    } else {
        return this.currentTcb;
    }
};
Scheduler.prototype.holdCurrent = function () {
    this.holdCount++;
    (() => {
        const __callInstance2 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    this.currentTcb.markAsHeld();
                }
            }
        });
        const __exports = __callInstance2.exports;
        return __exports.data();
    })();
    return this.currentTcb.link;
};
Scheduler.prototype.suspendCurrent = function () {
    (() => {
        const __callInstance1 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    this.currentTcb.markAsSuspended();
                }
            }
        });
        const __exports = __callInstance1.exports;
        return __exports.data();
    })();
    return this.currentTcb;
};
Scheduler.prototype.queue = function (packet) {
    var t = this.blocks[packet.id];
    if (t == null)
        return t;
    this.queueCount++;
    packet.link = null;
    packet.id = this.currentId;
    return t.checkPriorityAdd(this.currentTcb, packet);
};
function TaskControlBlock(link, id, priority, queue, task) {
    this.link = link;
    this.id = id;
    this.priority = priority;
    this.queue = queue;
    this.task = task;
    (() => {
        const __ifInstance1 = new WebAssembly.Instance(__ifWasmModule, {
            env: {
                impFunc1: () => {
                    {
                        this.state = STATE_SUSPENDED;
                    }
                },
                impFunc2: () => {
                    {
                        this.state = STATE_SUSPENDED_RUNNABLE;
                    }
                }
            }
        });
        const __exports = __ifInstance1.exports;
        return __exports.data(queue == null ? 1 : 0);
    })();
}
var STATE_RUNNING = 0;
var STATE_RUNNABLE = 1;
var STATE_SUSPENDED = 2;
var STATE_HELD = 4;
var STATE_SUSPENDED_RUNNABLE = STATE_SUSPENDED | STATE_RUNNABLE;
var STATE_NOT_HELD = ~STATE_HELD;
TaskControlBlock.prototype.setRunning = function () {
    this.state = STATE_RUNNING;
};
TaskControlBlock.prototype.markAsNotHeld = function () {
    this.state = this.state & STATE_NOT_HELD;
};
TaskControlBlock.prototype.markAsHeld = function () {
    this.state = this.state | STATE_HELD;
};
TaskControlBlock.prototype.isHeldOrSuspended = function () {
    return (this.state & STATE_HELD) != 0 || this.state == STATE_SUSPENDED;
};
TaskControlBlock.prototype.markAsSuspended = function () {
    this.state = this.state | STATE_SUSPENDED;
};
TaskControlBlock.prototype.markAsRunnable = function () {
    this.state = this.state | STATE_RUNNABLE;
};
TaskControlBlock.prototype.run = function () {
    var packet;
    (() => {
        const __ifInstance2 = new WebAssembly.Instance(__ifWasmModule, {
            env: {
                impFunc1: () => {
                    {
                        packet = this.queue;
                        this.queue = packet.link;
                        (() => {
                            const __ifInstance3 = new WebAssembly.Instance(__ifWasmModule, {
                                env: {
                                    impFunc1: () => {
                                        {
                                            this.state = STATE_RUNNING;
                                        }
                                    },
                                    impFunc2: () => {
                                        {
                                            this.state = STATE_RUNNABLE;
                                        }
                                    }
                                }
                            });
                            const __exports = __ifInstance3.exports;
                            return __exports.data(this.queue == null ? 1 : 0);
                        })();
                    }
                },
                impFunc2: () => {
                    {
                        packet = null;
                    }
                }
            }
        });
        const __exports = __ifInstance2.exports;
        return __exports.data(this.state == STATE_SUSPENDED_RUNNABLE ? 1 : 0);
    })();
    return this.task.run(packet);
};
TaskControlBlock.prototype.checkPriorityAdd = function (task, packet) {
    if (this.queue == null) {
        this.queue = packet;
        (() => {
            const __callInstance0 = new WebAssembly.Instance(__callWasmModule, {
                env: {
                    impFunc: () => {
                        this.markAsRunnable();
                    }
                }
            });
            const __exports = __callInstance0.exports;
            return __exports.data();
        })();
        if (this.priority > task.priority)
            return this;
    } else {
        this.queue = packet.addTo(this.queue);
    }
    return task;
};
TaskControlBlock.prototype.toString = function () {
    return lS(0, 5) + this.task + lS(0, 6) + this.state + lS(0, 7);
};
function IdleTask(scheduler, v1, count) {
    this.scheduler = scheduler;
    this.v1 = v1;
    this.count = count;
}
IdleTask.prototype.run = function (packet) {
    this.count--;
    if (this.count == 0)
        return this.scheduler.holdCurrent();
    if ((this.v1 & 1) == 0) {
        this.v1 = this.v1 >> 1;
        return this.scheduler.release(ID_DEVICE_A);
    } else {
        this.v1 = this.v1 >> 1 ^ 53256;
        return this.scheduler.release(ID_DEVICE_B);
    }
};
IdleTask.prototype.toString = function () {
    return lS(0, 8);
};
function DeviceTask(scheduler) {
    this.scheduler = scheduler;
    this.v1 = null;
}
DeviceTask.prototype.run = function (packet) {
    if (packet == null) {
        if (this.v1 == null)
            return this.scheduler.suspendCurrent();
        var v = this.v1;
        this.v1 = null;
        return this.scheduler.queue(v);
    } else {
        this.v1 = packet;
        return this.scheduler.holdCurrent();
    }
};
DeviceTask.prototype.toString = function () {
    return lS(0, 9);
};
function WorkerTask(scheduler, v1, v2) {
    this.scheduler = scheduler;
    this.v1 = v1;
    this.v2 = v2;
}
WorkerTask.prototype.run = function (packet) {
    if (packet == null) {
        return this.scheduler.suspendCurrent();
    } else {
        (() => {
            const __ifInstance4 = new WebAssembly.Instance(__ifWasmModule, {
                env: {
                    impFunc1: () => {
                        {
                            this.v1 = ID_HANDLER_B;
                        }
                    },
                    impFunc2: () => {
                        {
                            this.v1 = ID_HANDLER_A;
                        }
                    }
                }
            });
            const __exports = __ifInstance4.exports;
            return __exports.data(this.v1 == ID_HANDLER_A ? 1 : 0);
        })();
        packet.id = this.v1;
        packet.a1 = 0;
        (() => {
            var i = 0;
            const __forInstance0 = new WebAssembly.Instance(__forWasmModule, {
                env: {
                    test: () => {
                        return i < DATA_SIZE ? 1 : 0;
                    },
                    update: () => {
                        i++;
                    },
                    body: () => {
                        {
                            this.v2++;
                            (() => {
                                const __ifInstance5 = new WebAssembly.Instance(__ifWasmModule, {
                                    env: {
                                        impFunc1: () => {
                                            this.v2 = 1;
                                        },
                                        impFunc2: () => {
                                        }
                                    }
                                });
                                const __exports = __ifInstance5.exports;
                                return __exports.data(this.v2 > 26 ? 1 : 0);
                            })();
                            packet.a2[i] = this.v2;
                        }
                    }
                }
            });
            const __exports = __forInstance0.exports;
            return __exports.data();
        })();
        return this.scheduler.queue(packet);
    }
};
WorkerTask.prototype.toString = function () {
    return lS(0, 10);
};
function HandlerTask(scheduler) {
    this.scheduler = scheduler;
    this.v1 = null;
    this.v2 = null;
}
HandlerTask.prototype.run = function (packet) {
    (() => {
        const __ifInstance6 = new WebAssembly.Instance(__ifWasmModule, {
            env: {
                impFunc1: () => {
                    {
                        (() => {
                            const __ifInstance7 = new WebAssembly.Instance(__ifWasmModule, {
                                env: {
                                    impFunc1: () => {
                                        {
                                            this.v1 = packet.addTo(this.v1);
                                        }
                                    },
                                    impFunc2: () => {
                                        {
                                            this.v2 = packet.addTo(this.v2);
                                        }
                                    }
                                }
                            });
                            const __exports = __ifInstance7.exports;
                            return __exports.data(packet.kind == KIND_WORK ? 1 : 0);
                        })();
                    }
                },
                impFunc2: () => {
                }
            }
        });
        const __exports = __ifInstance6.exports;
        return __exports.data(packet != null ? 1 : 0);
    })();
    if (this.v1 != null) {
        var count = this.v1.a1;
        var v;
        if (count < DATA_SIZE) {
            if (this.v2 != null) {
                v = this.v2;
                this.v2 = this.v2.link;
                v.a1 = this.v1.a2[count];
                this.v1.a1 = count + 1;
                return this.scheduler.queue(v);
            }
        } else {
            v = this.v1;
            this.v1 = this.v1.link;
            return this.scheduler.queue(v);
        }
    }
    return this.scheduler.suspendCurrent();
};
HandlerTask.prototype.toString = function () {
    return lS(0, 11);
};
var DATA_SIZE = 4;
function Packet(link, id, kind) {
    this.link = link;
    this.id = id;
    this.kind = kind;
    this.a1 = 0;
    this.a2 = new Array(DATA_SIZE);
}
Packet.prototype.addTo = function (queue) {
    this.link = null;
    if (queue == null)
        return this;
    var peek, next = queue;
    (() => {
        const __forInstance2 = new WebAssembly.Instance(__forWasmModule, {
            env: {
                test: () => {
                    return (peek = next.link) != null ? 1 : 0;
                },
                update: () => {
                },
                body: () => {
                    next = peek;
                }
            }
        });
        const __exports = __forInstance2.exports;
        return __exports.data();
    })();
    next.link = this;
    return queue;
};
Packet.prototype.toString = function () {
    return lS(0, 12);
};