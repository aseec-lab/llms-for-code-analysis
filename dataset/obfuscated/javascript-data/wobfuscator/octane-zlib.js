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
const __aB = 'AGFzbQEAAAABiYCAgAACYAAAYAJ/fwADg4CAgAACAQAFg4CAgAABAAEGhoCAgAABfwFBAAsHkYCAgAACBm1lbW9yeQIABGFycjAAAQqsgICAAAKPgICAAAAjACAAQQRsaiABNgIAC5KAgIAAAQF/QRAkAEEAQayM78gAEAAL', __wAM = new WebAssembly.Instance(new WebAssembly.Module((() => {
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
const __wasmStringModules = ['AGFzbQEAAAAFg4CAgAABAAEGlYCAgAAEfwBBAQt/AEEIC38AQQ4LfwBBGAsHqoCAgAAFBm1lbW9yeQIABWRhdGEwAwAFZGF0YTEDAQVkYXRhMgMCBWRhdGEzAwMLqoCAgAAEAEEBCwV6bGliAABBCAsFemxpYgAAQQ4LCWZ1bmN0aW9uAABBGAsCMQA='].map(__bytes => {
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
new BenchmarkSuite(lS(0, 0), __lA(0, 16, 20), [new Benchmark(lS(0, 1), false, true, 10, runZlib, undefined, tearDownZlib, null, 3)]);
var zlibEval = eval;
function runZlib() {
    (() => {
        const __ifInstance0 = new WebAssembly.Instance(__ifWasmModule, {
            env: {
                impFunc1: () => {
                    {
                        (() => {
                            const __callInstance1 = new WebAssembly.Instance(__callWasmModule, {
                                env: {
                                    impFunc: () => {
                                        InitializeZlibBenchmark();
                                    }
                                }
                            });
                            const __exports = __callInstance1.exports;
                            return __exports.data();
                        })();
                    }
                },
                impFunc2: () => {
                }
            }
        });
        const __exports = __ifInstance0.exports;
        return __exports.data(typeof Ya != lS(0, 2) ? 1 : 0);
    })();
    (() => {
        const __callInstance0 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    Ya([lS(0, 3)]);
                }
            }
        });
        const __exports = __callInstance0.exports;
        return __exports.data();
    })();
}
function tearDownZlib() {
    delete $;
    delete $a;
    delete Aa;
    delete Ab;
    delete Ba;
    delete Bb;
    delete C;
    delete Ca;
    delete Cb;
    delete D;
    delete Da;
    delete Db;
    delete Ea;
    delete Eb;
    delete F;
    delete Fa;
    delete Fb;
    delete G;
    delete Ga;
    delete Gb;
    delete Ha;
    delete Hb;
    delete I;
    delete Ia;
    delete Ib;
    delete J;
    delete Ja;
    delete Jb;
    delete Ka;
    delete Kb;
    delete L;
    delete La;
    delete Lb;
    delete Ma;
    delete Mb;
    delete Module;
    delete N;
    delete Na;
    delete Nb;
    delete O;
    delete Oa;
    delete Ob;
    delete P;
    delete Pa;
    delete Pb;
    delete Q;
    delete Qa;
    delete Qb;
    delete R;
    delete Ra;
    delete Rb;
    delete S;
    delete Sa;
    delete Sb;
    delete T;
    delete Ta;
    delete Tb;
    delete U;
    delete Ua;
    delete Ub;
    delete V;
    delete Va;
    delete Vb;
    delete W;
    delete Wa;
    delete Wb;
    delete X;
    delete Xa;
    delete Y;
    delete Ya;
    delete Z;
    delete Za;
    delete ab;
    delete ba;
    delete bb;
    delete ca;
    delete cb;
    delete da;
    delete db;
    delete ea;
    delete eb;
    delete fa;
    delete fb;
    delete ga;
    delete gb;
    delete ha;
    delete hb;
    delete ia;
    delete ib;
    delete j;
    delete ja;
    delete jb;
    delete k;
    delete ka;
    delete kb;
    delete la;
    delete lb;
    delete ma;
    delete mb;
    delete n;
    delete na;
    delete nb;
    delete oa;
    delete ob;
    delete pa;
    delete pb;
    delete qa;
    delete qb;
    delete r;
    delete ra;
    delete rb;
    delete sa;
    delete sb;
    delete t;
    delete ta;
    delete tb;
    delete u;
    delete ua;
    delete ub;
    delete v;
    delete va;
    delete vb;
    delete w;
    delete wa;
    delete wb;
    delete x;
    delete xa;
    delete xb;
    delete ya;
    delete yb;
    delete z;
    delete za;
    delete zb;
}