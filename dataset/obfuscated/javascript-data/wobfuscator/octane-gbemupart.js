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
const __aB = 'AGFzbQEAAAABiYCAgAACYAAAYAJ/fwADh4CAgAAGAQAAAAAABYOAgIAAAQABBoaAgIAAAX8BQQALB62AgIAABgZtZW1vcnkCAARhcnIwAAEEYXJyMQACBGFycjIAAwRhcnIzAAQEYXJyNAAFCpuBgIAABo+AgIAAACMAIABBBGxqIAE2AgALkYCAgAABAX9BECQAQQBBnMLEDBAAC5qAgIAAAQF/QRQkAEEAQQAQAEEBQQAQAEECQQAQAAuggICAAAEBf0EgJABBAEEAEABBAUEAEABBAkEAEABBA0EAEAALlICAgAABAX9BMCQAQQBBABAAQQFBABAAC46AgIAAAQF/QTgkAEEAQQAQAAs=', __wAM = new WebAssembly.Instance(new WebAssembly.Module((() => {
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
const __wasmStringModules = ['AGFzbQEAAAAFg4CAgAABAAEG7oaAgACTAX8AQQELfwBBCgt/AEEUC38AQSALfwBBLAt/AEE4C38AQcQAC38AQdwAC38AQcABC38AQZoCC38AQYIDC38AQY4DC38AQcADC38AQdQDC38AQdoDC38AQeoDC38AQe4DC38AQfIDC38AQfYDC38AQfoDC38AQf4DC38AQYIEC38AQYYEC38AQYoEC38AQY4EC38AQZIEC38AQZYEC38AQZoEC38AQZ4EC38AQaIEC38AQaYEC38AQaoEC38AQa4EC38AQbIEC38AQbYEC38AQboEC38AQb4EC38AQcIEC38AQcYEC38AQcoEC38AQc4EC38AQdIEC38AQdYEC38AQdoEC38AQd4EC38AQeIEC38AQeYEC38AQeoEC38AQe4EC38AQfIEC38AQfYEC38AQfoEC38AQf4EC38AQYIFC38AQYYFC38AQYoFC38AQY4FC38AQZIFC38AQZYFC38AQZoFC38AQZ4FC38AQaIFC38AQaYFC38AQaoFC38AQa4FC38AQbIFC38AQbYFC38AQboFC38AQb4FC38AQcIFC38AQcYFC38AQcoFC38AQc4FC38AQdIFC38AQdYFC38AQdoFC38AQd4FC38AQeIFC38AQegFC38AQe4FC38AQfQFC38AQb4GC38AQcAGC38AQcYGC38AQcwGC38AQdIGC38AQdQGC38AQdYGC38AQd4GC38AQZgHC38AQYwIC38AQbwJC38AQcoJC38AQdoJC38AQdwNC38AQeQNC38AQfQNC38AQdoOC38AQeIOC38AQYAPC38AQYgPC38AQd4RC38AQeYRC38AQaoSC38AQeITC38AQeoTC38AQZAUC38AQZwUC38AQZ4UC38AQbQUC38AQbIWC38AQegWC38AQYIXC38AQegXC38AQfwXC38AQZAYC38AQZYYC38AQfIbC38AQfobC38AQYQcC38AQagcC38AQbYcC38AQbgcC38AQfgcC38AQYIdC38AQYodC38AQYwdC38AQZYdC38AQZwdC38AQaQdC38AQYQfC38AQYofC38AQZIfC38AQbIgC38AQbYgC38AQcAgC38AQcYgC38AQdQgC38AQd4gC38AQeIgC38AQeYgC38AQe4gC38AQfAgC38AQfIgC38AQfQgC38AQfYgC38AQa4hCwfuioCAAJQBBm1lbW9yeQIABWRhdGEwAwAFZGF0YTEDAQVkYXRhMgMCBWRhdGEzAwMFZGF0YTQDBAVkYXRhNQMFBWRhdGE2AwYFZGF0YTcDBwVkYXRhOAMIBWRhdGE5AwkGZGF0YTEwAwoGZGF0YTExAwsGZGF0YTEyAwwGZGF0YTEzAw0GZGF0YTE0Aw4GZGF0YTE1Aw8GZGF0YTE2AxAGZGF0YTE3AxEGZGF0YTE4AxIGZGF0YTE5AxMGZGF0YTIwAxQGZGF0YTIxAxUGZGF0YTIyAxYGZGF0YTIzAxcGZGF0YTI0AxgGZGF0YTI1AxkGZGF0YTI2AxoGZGF0YTI3AxsGZGF0YTI4AxwGZGF0YTI5Ax0GZGF0YTMwAx4GZGF0YTMxAx8GZGF0YTMyAyAGZGF0YTMzAyEGZGF0YTM0AyIGZGF0YTM1AyMGZGF0YTM2AyQGZGF0YTM3AyUGZGF0YTM4AyYGZGF0YTM5AycGZGF0YTQwAygGZGF0YTQxAykGZGF0YTQyAyoGZGF0YTQzAysGZGF0YTQ0AywGZGF0YTQ1Ay0GZGF0YTQ2Ay4GZGF0YTQ3Ay8GZGF0YTQ4AzAGZGF0YTQ5AzEGZGF0YTUwAzIGZGF0YTUxAzMGZGF0YTUyAzQGZGF0YTUzAzUGZGF0YTU0AzYGZGF0YTU1AzcGZGF0YTU2AzgGZGF0YTU3AzkGZGF0YTU4AzoGZGF0YTU5AzsGZGF0YTYwAzwGZGF0YTYxAz0GZGF0YTYyAz4GZGF0YTYzAz8GZGF0YTY0A0AGZGF0YTY1A0EGZGF0YTY2A0IGZGF0YTY3A0MGZGF0YTY4A0QGZGF0YTY5A0UGZGF0YTcwA0YGZGF0YTcxA0cGZGF0YTcyA0gGZGF0YTczA0kGZGF0YTc0A0oGZGF0YTc1A0sGZGF0YTc2A0wGZGF0YTc3A00GZGF0YTc4A04GZGF0YTc5A08GZGF0YTgwA1AGZGF0YTgxA1EGZGF0YTgyA1IGZGF0YTgzA1MGZGF0YTg0A1QGZGF0YTg1A1UGZGF0YTg2A1YGZGF0YTg3A1cGZGF0YTg4A1gGZGF0YTg5A1kGZGF0YTkwA1oGZGF0YTkxA1sGZGF0YTkyA1wGZGF0YTkzA10GZGF0YTk0A14GZGF0YTk1A18GZGF0YTk2A2AGZGF0YTk3A2EGZGF0YTk4A2IGZGF0YTk5A2MHZGF0YTEwMANkB2RhdGExMDEDZQdkYXRhMTAyA2YHZGF0YTEwMwNnB2RhdGExMDQDaAdkYXRhMTA1A2kHZGF0YTEwNgNqB2RhdGExMDcDawdkYXRhMTA4A2wHZGF0YTEwOQNtB2RhdGExMTADbgdkYXRhMTExA28HZGF0YTExMgNwB2RhdGExMTMDcQdkYXRhMTE0A3IHZGF0YTExNQNzB2RhdGExMTYDdAdkYXRhMTE3A3UHZGF0YTExOAN2B2RhdGExMTkDdwdkYXRhMTIwA3gHZGF0YTEyMQN5B2RhdGExMjIDegdkYXRhMTIzA3sHZGF0YTEyNAN8B2RhdGExMjUDfQdkYXRhMTI2A34HZGF0YTEyNwN/B2RhdGExMjgDgAEHZGF0YTEyOQOBAQdkYXRhMTMwA4IBB2RhdGExMzEDgwEHZGF0YTEzMgOEAQdkYXRhMTMzA4UBB2RhdGExMzQDhgEHZGF0YTEzNQOHAQdkYXRhMTM2A4gBB2RhdGExMzcDiQEHZGF0YTEzOAOKAQdkYXRhMTM5A4sBB2RhdGExNDADjAEHZGF0YTE0MQONAQdkYXRhMTQyA44BB2RhdGExNDMDjwEHZGF0YTE0NAOQAQdkYXRhMTQ1A5EBB2RhdGExNDYDkgELtaaAgACTAQBBAQsIR2FtZWJveQAAQQoLCEdhbWVib3kAAEEUCwp1bmRlZmluZWQAAEEgCwp1bmRlZmluZWQAAEEsCwp1bmRlZmluZWQAAEE4Cwp1bmRlZmluZWQAAEHEAAsWVHlwZWRBcnJheVVuc3VwcG9ydGVkAABB3AALYyU3QiUyMnJlZ2lzdGVyQSUyMiUzQTE2MCUyQyUyMnJlZ2lzdGVyQiUyMiUzQTI1NSUyQyUyMnJlZ2lzdGVyQyUyMiUzQTI1NSUyQyUyMnJlZ2lzdGVyRSUyMiUzQTExJTJDAABBwAELWSUyMnJlZ2lzdGVyc0hMJTIyJTNBNTE2MDAlMkMlMjJwcm9ncmFtQ291bnRlciUyMiUzQTI0MzA5JTJDJTIyc3RhY2tQb2ludGVyJTIyJTNBNDk3MDYlMkMAAEGaAgtmJTIyc3VtUk9NJTIyJTNBMTAxNzE1NzglMkMlMjJzdW1NZW1vcnklMjIlM0EzNDM1ODU2JTJDJTIyc3VtTUJDUmFtJTIyJTNBMjM0NTk4JTJDJTIyc3VtVlJhbSUyMiUzQTAlN0QAAEGCAwsKdW5kZWZpbmVkAABBjgMLMUluY29ycmVjdCUyMGZpbmFsJTIwc3RhdGUlMjBvZiUyMHByb2Nlc3NvciUzQSUwQQAAQcADCxMlMjBhY3R1YWwlMjAlMjAlMjAAAEHUAwsEJTBBAABB2gMLDyUyMGV4cGVjdGVkJTIwAABB6gMLAkEAAEHuAwsCQgAAQfIDCwJDAABB9gMLAkQAAEH6AwsCRQAAQf4DCwJGAABBggQLAkcAAEGGBAsCSAAAQYoECwJJAABBjgQLAkoAAEGSBAsCSwAAQZYECwJMAABBmgQLAk0AAEGeBAsCTgAAQaIECwJPAABBpgQLAlAAAEGqBAsCUQAAQa4ECwJSAABBsgQLAlMAAEG2BAsCVAAAQboECwJVAABBvgQLAlYAAEHCBAsCVwAAQcYECwJYAABBygQLAlkAAEHOBAsCWgAAQdIECwJhAABB1gQLAmIAAEHaBAsCYwAAQd4ECwJkAABB4gQLAmUAAEHmBAsCZgAAQeoECwJnAABB7gQLAmgAAEHyBAsCaQAAQfYECwJqAABB+gQLAmsAAEH+BAsCbAAAQYIFCwJtAABBhgULAm4AAEGKBQsCbwAAQY4FCwJwAABBkgULAnEAAEGWBQsCcgAAQZoFCwJzAABBngULAnQAAEGiBQsCdQAAQaYFCwJ2AABBqgULAncAAEGuBQsCeAAAQbIFCwJ5AABBtgULAnoAAEG6BQsCMAAAQb4FCwIxAABBwgULAjIAAEHGBQsCMwAAQcoFCwI0AABBzgULAjUAAEHSBQsCNgAAQdYFCwI3AABB2gULAjgAAEHeBQsCOQAAQeIFCwQlMkIAAEHoBQsEJTJGAABB7gULBCUzRAAAQfQFC0hBQkNERUZHSElKS0xNTk9QUVJTVFVWV1hZWmFiY2RlZmdoaWprbG1ub3BxcnN0dXZ3eHl6MDEyMzQ1Njc4OSUyQiUyRiUzRAAAQb4GCwEAAEHABgsEJTIwAABBxgYLBCUzRAAAQcwGCwQlM0QAAEHSBgsBAABB1AYLAQAAQdYGCwdudW1iZXIAAEHeBgs4SW52YWxpZCUyMHNldHRpbmdzJTIwc3BlY2lmaWVkJTIwZm9yJTIwdGhlJTIwcmVzYW1wbGVyLgAAQZgHC3N2YXIlMjBidWZmZXJMZW5ndGglMjAlM0QlMjBNYXRoLm1pbihidWZmZXIubGVuZ3RoJTJDJTIwdGhpcy5vdXRwdXRCdWZmZXJTaXplKSUzQiUyMCUyMGlmJTIwKChidWZmZXJMZW5ndGglMjAlMjUlMjAAAEGMCAuvASklMjAlM0QlM0QlMjAwKSUyMCU3QiUyMCUyMCUyMCUyMGlmJTIwKGJ1ZmZlckxlbmd0aCUyMCUzRSUyMDApJTIwJTdCJTIwJTIwJTIwJTIwJTIwJTIwdmFyJTIwcmF0aW9XZWlnaHQlMjAlM0QlMjB0aGlzLnJhdGlvV2VpZ2h0JTNCJTIwJTIwJTIwJTIwJTIwJTIwdmFyJTIwd2VpZ2h0JTIwJTNEJTIwMCUzQgAAQbwJCw12YXIlMjBvdXRwdXQAAEHKCQsOJTIwJTNEJTIwMCUzQgAAQdoJC4EEdmFyJTIwYWN0dWFsUG9zaXRpb24lMjAlM0QlMjAwJTNCJTIwJTIwJTIwJTIwJTIwJTIwdmFyJTIwYW1vdW50VG9OZXh0JTIwJTNEJTIwMCUzQiUyMCUyMCUyMCUyMCUyMCUyMHZhciUyMGFscmVhZHlQcm9jZXNzZWRUYWlsJTIwJTNEJTIwIXRoaXMudGFpbEV4aXN0cyUzQiUyMCUyMCUyMCUyMCUyMCUyMHRoaXMudGFpbEV4aXN0cyUyMCUzRCUyMGZhbHNlJTNCJTIwJTIwJTIwJTIwJTIwJTIwdmFyJTIwb3V0cHV0QnVmZmVyJTIwJTNEJTIwdGhpcy5vdXRwdXRCdWZmZXIlM0IlMjAlMjAlMjAlMjAlMjAlMjB2YXIlMjBvdXRwdXRPZmZzZXQlMjAlM0QlMjAwJTNCJTIwJTIwJTIwJTIwJTIwJTIwdmFyJTIwY3VycmVudFBvc2l0aW9uJTIwJTNEJTIwMCUzQiUyMCUyMCUyMCUyMCUyMCUyMGRvJTIwJTdCJTIwJTIwJTIwJTIwJTIwJTIwJTIwJTIwaWYlMjAoYWxyZWFkeVByb2Nlc3NlZFRhaWwpJTIwJTdCJTIwJTIwJTIwJTIwJTIwJTIwJTIwJTIwJTIwJTIwd2VpZ2h0JTIwJTNEJTIwcmF0aW9XZWlnaHQlM0IAAEHcDQsHb3V0cHV0AABB5A0LDiUyMCUzRCUyMDAlM0IAAEH0DQtlJTdEJTIwJTIwJTIwJTIwJTIwJTIwJTIwJTIwZWxzZSUyMCU3QiUyMCUyMCUyMCUyMCUyMCUyMCUyMCUyMCUyMCUyMHdlaWdodCUyMCUzRCUyMHRoaXMubGFzdFdlaWdodCUzQgAAQdoOCwdvdXRwdXQAAEHiDgscJTIwJTNEJTIwdGhpcy5sYXN0T3V0cHV0JTVCAABBgA8LByU1RCUzQgAAQYgPC9QCYWxyZWFkeVByb2Nlc3NlZFRhaWwlMjAlM0QlMjB0cnVlJTNCJTIwJTIwJTIwJTIwJTIwJTIwJTIwJTIwJTdEJTIwJTIwJTIwJTIwJTIwJTIwJTIwJTIwd2hpbGUlMjAod2VpZ2h0JTIwJTNFJTIwMCUyMCUyNiUyNiUyMGFjdHVhbFBvc2l0aW9uJTIwJTNDJTIwYnVmZmVyTGVuZ3RoKSUyMCU3QiUyMCUyMCUyMCUyMCUyMCUyMCUyMCUyMCUyMCUyMGFtb3VudFRvTmV4dCUyMCUzRCUyMDElMjAlMkIlMjBhY3R1YWxQb3NpdGlvbiUyMC0lMjBjdXJyZW50UG9zaXRpb24lM0IlMjAlMjAlMjAlMjAlMjAlMjAlMjAlMjAlMjAlMjBpZiUyMCh3ZWlnaHQlMjAlM0UlM0QlMjBhbW91bnRUb05leHQpJTIwJTdCAABB3hELB291dHB1dAAAQeYRC0MlMjAlMkIlM0QlMjBidWZmZXIlNUJhY3R1YWxQb3NpdGlvbiUyQiUyQiU1RCUyMColMjBhbW91bnRUb05leHQlM0IAAEGqEgu2AWN1cnJlbnRQb3NpdGlvbiUyMCUzRCUyMGFjdHVhbFBvc2l0aW9uJTNCJTIwJTIwJTIwJTIwJTIwJTIwJTIwJTIwJTIwJTIwJTIwJTIwd2VpZ2h0JTIwLSUzRCUyMGFtb3VudFRvTmV4dCUzQiUyMCUyMCUyMCUyMCUyMCUyMCUyMCUyMCUyMCUyMCU3RCUyMCUyMCUyMCUyMCUyMCUyMCUyMCUyMCUyMCUyMGVsc2UlMjAlN0IAAEHiEwsHb3V0cHV0AABB6hMLJCUyMCUyQiUzRCUyMGJ1ZmZlciU1QmFjdHVhbFBvc2l0aW9uAABBkBQLCiUyMCUyQiUyMAAAQZwUCwEAAEGeFAsUJTVEJTIwKiUyMHdlaWdodCUzQgAAQbQUC/wBY3VycmVudFBvc2l0aW9uJTIwJTJCJTNEJTIwd2VpZ2h0JTNCJTIwJTIwJTIwJTIwJTIwJTIwJTIwJTIwJTIwJTIwJTIwJTIwd2VpZ2h0JTIwJTNEJTIwMCUzQiUyMCUyMCUyMCUyMCUyMCUyMCUyMCUyMCUyMCUyMCUyMCUyMGJyZWFrJTNCJTIwJTIwJTIwJTIwJTIwJTIwJTIwJTIwJTIwJTIwJTdEJTIwJTIwJTIwJTIwJTIwJTIwJTIwJTIwJTdEJTIwJTIwJTIwJTIwJTIwJTIwJTIwJTIwaWYlMjAod2VpZ2h0JTIwJTNEJTNEJTIwMCklMjAlN0IAAEGyFgs0b3V0cHV0QnVmZmVyJTVCb3V0cHV0T2Zmc2V0JTJCJTJCJTVEJTIwJTNEJTIwb3V0cHV0AABB6BYLGCUyMCUyRiUyMHJhdGlvV2VpZ2h0JTNCAABBghcLZSU3RCUyMCUyMCUyMCUyMCUyMCUyMCUyMCUyMGVsc2UlMjAlN0IlMjAlMjAlMjAlMjAlMjAlMjAlMjAlMjAlMjAlMjB0aGlzLmxhc3RXZWlnaHQlMjAlM0QlMjB3ZWlnaHQlM0IAAEHoFwsTdGhpcy5sYXN0T3V0cHV0JTVCAABB/BcLEyU1RCUyMCUzRCUyMG91dHB1dAAAQZAYCwQlM0IAAEGWGAvaA3RoaXMudGFpbEV4aXN0cyUyMCUzRCUyMHRydWUlM0IlMjAlMjAlMjAlMjAlMjAlMjAlMjAlMjAlMjAlMjBicmVhayUzQiUyMCUyMCUyMCUyMCUyMCUyMCUyMCUyMCU3RCUyMCUyMCUyMCUyMCUyMCUyMCU3RCUyMHdoaWxlJTIwKGFjdHVhbFBvc2l0aW9uJTIwJTNDJTIwYnVmZmVyTGVuZ3RoKSUzQiUyMCUyMCUyMCUyMCUyMCUyMHJldHVybiUyMHRoaXMuYnVmZmVyU2xpY2Uob3V0cHV0T2Zmc2V0KSUzQiUyMCUyMCUyMCUyMCU3RCUyMCUyMCUyMCUyMGVsc2UlMjAlN0IlMjAlMjAlMjAlMjAlMjAlMjByZXR1cm4lMjAodGhpcy5ub1JldHVybiklMjAlM0YlMjAwJTIwJTNBJTIwJTVCJTVEJTNCJTIwJTIwJTIwJTIwJTdEJTIwJTIwJTdEJTIwJTIwZWxzZSUyMCU3QiUyMCUyMCUyMCUyMHRocm93KG5ldyUyMEVycm9yKCUyMkJ1ZmZlciUyMHdhcyUyMG9mJTIwaW5jb3JyZWN0JTIwc2FtcGxlJTIwbGVuZ3RoLiUyMikpJTNCJTIwJTIwJTdEAABB8hsLB2J1ZmZlcgAAQfobCwlmdW5jdGlvbgAAQYQcCyNTZWxlY3QlMjBpbml0aWFsaXplV2ViQXVkaW8lMjBjYXNlAABBqBwLDUxpbnV4JTIwaTY4NgAAQbYcCwEAAEG4HAs/QnJvd3NlciUyMGRvZXMlMjBub3QlMjBzdXBwb3J0JTIwcmVhbCUyMHRpbWUlMjBhdWRpbyUyMG91dHB1dC4AAEH4HAsJTWFjSW50ZWwAAEGCHQsHTWFjUFBDAABBih0LAQAAQYwdCwlYQXVkaW9KUwAAQZYdCwRkaXYAAEGcHQsGc3R5bGUAAEGkHQvfAXBvc2l0aW9uJTNBJTIwZml4ZWQlM0IlMjBib3R0b20lM0ElMjAwcHglM0IlMjByaWdodCUzQSUyMDBweCUzQiUyMG1hcmdpbiUzQSUyMDBweCUzQiUyMHBhZGRpbmclM0ElMjAwcHglM0IlMjBib3JkZXIlM0ElMjBub25lJTNCJTIwd2lkdGglM0ElMjA4cHglM0IlMjBoZWlnaHQlM0ElMjA4cHglM0IlMjBvdmVyZmxvdyUzQSUyMGhpZGRlbiUzQiUyMHotaW5kZXglM0ElMjAtMTAwMCUzQiUyMAAAQYQfCwRkaXYAAEGKHwsGc3R5bGUAAEGSHwueAXBvc2l0aW9uJTNBJTIwc3RhdGljJTNCJTIwYm9yZGVyJTNBJTIwbm9uZSUzQiUyMHdpZHRoJTNBJTIwMHB4JTNCJTIwaGVpZ2h0JTNBJTIwMHB4JTNCJTIwdmlzaWJpbGl0eSUzQSUyMGhpZGRlbiUzQiUyMG1hcmdpbiUzQSUyMDhweCUzQiUyMHBhZGRpbmclM0ElMjAwcHglM0IAAEGyIAsDaWQAAEG2IAsJWEF1ZGlvSlMAAEHAIAsFYm9keQAAQcYgCw1YQXVkaW9KUy5zd2YAAEHUIAsJWEF1ZGlvSlMAAEHeIAsCOAAAQeIgCwI4AABB5iALBjkuMC4wAABB7iALAQAAQfAgCwEAAEHyIAsBAABB9CALAQAAQfYgCzZJbnZhbGlkJTIwc2V0dGluZ3MlMjBzcGVjaWZpZWQlMjBmb3IlMjB0aGUlMjByZXNpemVyLgAAQa4hCwlGdW5jdGlvbgA='].map(__bytes => {
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
var GameboyBenchmark = new BenchmarkSuite(lS(0, 0), __lA(0, 16, 20), [new Benchmark(lS(0, 1), false, false, 20, runGameboy, setupGameboy, tearDownGameboy, null, 4)]);
var decoded_gameboy_rom = null;
function setupGameboy() {
    if (!(typeof Uint8Array != lS(0, 2) && typeof Int8Array != lS(0, 3) && typeof Float32Array != lS(0, 4) && typeof Int32Array != lS(0, 5))) {
        throw lS(0, 6);
    }
    decoded_gameboy_rom = base64_decode(gameboy_rom);
    rom = null;
}
function runGameboy() {
    (() => {
        const __callInstance59 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    start(new GameBoyCanvas(), decoded_gameboy_rom);
                }
            }
        });
        const __exports = __callInstance59.exports;
        return __exports.data();
    })();
    gameboy.instructions = 0;
    gameboy.totalInstructions = 250000;
    (() => {
        const __forInstance40 = new WebAssembly.Instance(__forWasmModule, {
            env: {
                test: () => {
                    return gameboy.instructions <= gameboy.totalInstructions ? 1 : 0;
                },
                update: () => {
                },
                body: () => {
                    {
                        (() => {
                            const __callInstance58 = new WebAssembly.Instance(__callWasmModule, {
                                env: {
                                    impFunc: () => {
                                        gameboy.run();
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
                                        GameBoyAudioNode.run();
                                    }
                                }
                            });
                            const __exports = __callInstance57.exports;
                            return __exports.data();
                        })();
                    }
                }
            }
        });
        const __exports = __forInstance40.exports;
        return __exports.data();
    })();
    (() => {
        const __callInstance56 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    resetGlobalVariables();
                }
            }
        });
        const __exports = __callInstance56.exports;
        return __exports.data();
    })();
}
function tearDownGameboy() {
    decoded_gameboy_rom = null;
    expectedGameboyStateStr = null;
}
var expectedGameboyStateStr = lS(0, 7) + lS(0, 8) + lS(0, 9);
var GameBoyWindow = {};
function GameBoyContext() {
    this.createBuffer = function () {
        return new Buffer();
    };
    this.createImageData = function (w, h) {
        var result = {};
        result.data = new Uint8Array(w * h * 4);
        return result;
    };
    this.putImageData = function (buffer, x, y) {
        var sum = 0;
        (() => {
            var i = 0;
            const __forInstance0 = new WebAssembly.Instance(__forWasmModule, {
                env: {
                    test: () => {
                        return i < buffer.data.length ? 1 : 0;
                    },
                    update: () => {
                        i++;
                    },
                    body: () => {
                        {
                            sum += i * buffer.data[i];
                            sum = sum % 1000;
                        }
                    }
                }
            });
            const __exports = __forInstance0.exports;
            return __exports.data();
        })();
    };
    this.drawImage = function () {
    };
}
;
function GameBoyCanvas() {
    this.getContext = function () {
        return new GameBoyContext();
    };
    this.width = 160;
    this.height = 144;
    this.style = { visibility: 'visibile' };
}
function cout(message, colorIndex) {
}
function clear_terminal() {
}
var GameBoyAudioNode = {
    bufferSize: 0,
    onaudioprocess: null,
    connect: function () {
    },
    run: function () {
        var event = { outputBuffer: this.outputBuffer };
        (() => {
            const __callInstance55 = new WebAssembly.Instance(__callWasmModule, {
                env: {
                    impFunc: () => {
                        this.onaudioprocess(event);
                    }
                }
            });
            const __exports = __callInstance55.exports;
            return __exports.data();
        })();
    }
};
function GameBoyAudioContext() {
    this.createBufferSource = function () {
        return {
            noteOn: function () {
            },
            connect: function () {
            }
        };
    };
    this.sampleRate = 48000;
    this.destination = {};
    this.createBuffer = function (channels, len, sampleRate) {
        return {
            gain: 1,
            numberOfChannels: 1,
            length: 1,
            duration: 0.000020833333110203966,
            sampleRate: 48000
        };
    };
    this.createJavaScriptNode = function (bufferSize, inputChannels, outputChannels) {
        GameBoyAudioNode.bufferSize = bufferSize;
        GameBoyAudioNode.outputBuffer = {
            getChannelData: function (i) {
                return this.channelData[i];
            },
            channelData: []
        };
        (() => {
            var i = 0;
            const __forInstance1 = new WebAssembly.Instance(__forWasmModule, {
                env: {
                    test: () => {
                        return i < outputChannels ? 1 : 0;
                    },
                    update: () => {
                        i++;
                    },
                    body: () => {
                        {
                            GameBoyAudioNode.outputBuffer.channelData[i] = new Float32Array(bufferSize);
                        }
                    }
                }
            });
            const __exports = __forInstance1.exports;
            return __exports.data();
        })();
        return GameBoyAudioNode;
    };
}
var mock_date_time_counter = 0;
function new_Date() {
    return {
        getTime: function () {
            mock_date_time_counter += 16;
            return mock_date_time_counter;
        }
    };
}
function checkFinalState() {
    function sum(a) {
        var result = 0;
        (() => {
            var i = 0;
            const __forInstance2 = new WebAssembly.Instance(__forWasmModule, {
                env: {
                    test: () => {
                        return i < a.length ? 1 : 0;
                    },
                    update: () => {
                        i++;
                    },
                    body: () => {
                        {
                            result += a[i];
                        }
                    }
                }
            });
            const __exports = __forInstance2.exports;
            return __exports.data();
        })();
        return result;
    }
    var state = {
        registerA: gameboy.registerA,
        registerB: gameboy.registerB,
        registerC: gameboy.registerC,
        registerE: gameboy.registerE,
        registerF: gameboy.registerF,
        registersHL: gameboy.registersHL,
        programCounter: gameboy.programCounter,
        stackPointer: gameboy.stackPointer,
        sumROM: sum(gameboy.fromTypedArray(gameboy.ROM)),
        sumMemory: sum(gameboy.fromTypedArray(gameboy.memory)),
        sumMBCRam: sum(gameboy.fromTypedArray(gameboy.MBCRam)),
        sumVRam: sum(gameboy.fromTypedArray(gameboy.VRam))
    };
    var stateStr = JSON.stringify(state);
    (() => {
        const __ifInstance0 = new WebAssembly.Instance(__ifWasmModule, {
            env: {
                impFunc1: () => {
                    {
                        (() => {
                            const __ifInstance1 = new WebAssembly.Instance(__ifWasmModule, {
                                env: {
                                    impFunc1: () => {
                                        {
                                            (() => {
                                                const __callInstance54 = new WebAssembly.Instance(__callWasmModule, {
                                                    env: {
                                                        impFunc: () => {
                                                            alert(lS(0, 11) + lS(0, 12) + stateStr + lS(0, 13) + lS(0, 14) + expectedGameboyStateStr);
                                                        }
                                                    }
                                                });
                                                const __exports = __callInstance54.exports;
                                                return __exports.data();
                                            })();
                                        }
                                    },
                                    impFunc2: () => {
                                    }
                                }
                            });
                            const __exports = __ifInstance1.exports;
                            return __exports.data(stateStr != expectedGameboyStateStr ? 1 : 0);
                        })();
                    }
                },
                impFunc2: () => {
                    {
                        (() => {
                            const __callInstance53 = new WebAssembly.Instance(__callWasmModule, {
                                env: {
                                    impFunc: () => {
                                        alert(stateStr);
                                    }
                                }
                            });
                            const __exports = __callInstance53.exports;
                            return __exports.data();
                        })();
                    }
                }
            }
        });
        const __exports = __ifInstance0.exports;
        return __exports.data(typeof expectedGameboyStateStr != lS(0, 10) ? 1 : 0);
    })();
}
function resetGlobalVariables() {
    audioContextHandle = null;
    audioNode = null;
    audioSource = null;
    launchedContext = false;
    audioContextSampleBuffer = [];
    resampled = [];
    webAudioMinBufferSize = 15000;
    webAudioMaxBufferSize = 25000;
    webAudioActualSampleRate = 44100;
    XAudioJSSampleRate = 0;
    webAudioMono = false;
    XAudioJSVolume = 1;
    resampleControl = null;
    audioBufferSize = 0;
    resampleBufferStart = 0;
    resampleBufferEnd = 0;
    resampleBufferSize = 2;
    gameboy = null;
    gbRunInterval = null;
}
var toBase64 = [
    lS(0, 15),
    lS(0, 16),
    lS(0, 17),
    lS(0, 18),
    lS(0, 19),
    lS(0, 20),
    lS(0, 21),
    lS(0, 22),
    lS(0, 23),
    lS(0, 24),
    lS(0, 25),
    lS(0, 26),
    lS(0, 27),
    lS(0, 28),
    lS(0, 29),
    lS(0, 30),
    lS(0, 31),
    lS(0, 32),
    lS(0, 33),
    lS(0, 34),
    lS(0, 35),
    lS(0, 36),
    lS(0, 37),
    lS(0, 38),
    lS(0, 39),
    lS(0, 40),
    lS(0, 41),
    lS(0, 42),
    lS(0, 43),
    lS(0, 44),
    lS(0, 45),
    lS(0, 46),
    lS(0, 47),
    lS(0, 48),
    lS(0, 49),
    lS(0, 50),
    lS(0, 51),
    lS(0, 52),
    lS(0, 53),
    lS(0, 54),
    lS(0, 55),
    lS(0, 56),
    lS(0, 57),
    lS(0, 58),
    lS(0, 59),
    lS(0, 60),
    lS(0, 61),
    lS(0, 62),
    lS(0, 63),
    lS(0, 64),
    lS(0, 65),
    lS(0, 66),
    lS(0, 67),
    lS(0, 68),
    lS(0, 69),
    lS(0, 70),
    lS(0, 71),
    lS(0, 72),
    lS(0, 73),
    lS(0, 74),
    lS(0, 75),
    lS(0, 76),
    lS(0, 77),
    lS(0, 78),
    lS(0, 79)
];
var fromBase64 = lS(0, 80);
function base64(data) {
    try {
        var base64 = GameBoyWindow.btoa(data);
    } catch (error) {
        var base64 = lS(0, 81);
        var dataLength = data.length;
        (() => {
            const __ifInstance2 = new WebAssembly.Instance(__ifWasmModule, {
                env: {
                    impFunc1: () => {
                        {
                            var bytes = __lA(1, 20, 32);
                            var index = 0;
                            var remainder = dataLength % 3;
                            (() => {
                                const __forInstance41 = new WebAssembly.Instance(__forWasmModule, {
                                    env: {
                                        test: () => {
                                            return data.length % 3 > 0 ? 1 : 0;
                                        },
                                        update: () => {
                                        },
                                        body: () => {
                                            {
                                                data[data.length] = lS(0, 82);
                                            }
                                        }
                                    }
                                });
                                const __exports = __forInstance41.exports;
                                return __exports.data();
                            })();
                            (() => {
                                const __forInstance42 = new WebAssembly.Instance(__forWasmModule, {
                                    env: {
                                        test: () => {
                                            return index < dataLength ? 1 : 0;
                                        },
                                        update: () => {
                                        },
                                        body: () => {
                                            {
                                                bytes = [
                                                    data.charCodeAt(index++) & 255,
                                                    data.charCodeAt(index++) & 255,
                                                    data.charCodeAt(index++) & 255
                                                ];
                                                base64 += toBase64[bytes[0] >> 2] + toBase64[(bytes[0] & 3) << 4 | bytes[1] >> 4] + toBase64[(bytes[1] & 15) << 2 | bytes[2] >> 6] + toBase64[bytes[2] & 63];
                                            }
                                        }
                                    }
                                });
                                const __exports = __forInstance42.exports;
                                return __exports.data();
                            })();
                            (() => {
                                const __ifInstance3 = new WebAssembly.Instance(__ifWasmModule, {
                                    env: {
                                        impFunc1: () => {
                                            {
                                                base64[base64.length - 1] = lS(0, 83);
                                                (() => {
                                                    const __ifInstance4 = new WebAssembly.Instance(__ifWasmModule, {
                                                        env: {
                                                            impFunc1: () => {
                                                                {
                                                                    base64[base64.length - 2] = lS(0, 84);
                                                                    base64[base64.length - 3] = toBase64[(bytes[0] & 3) << 4];
                                                                }
                                                            },
                                                            impFunc2: () => {
                                                                {
                                                                    base64[base64.length - 2] = toBase64[(bytes[1] & 15) << 2];
                                                                }
                                                            }
                                                        }
                                                    });
                                                    const __exports = __ifInstance4.exports;
                                                    return __exports.data(remainder == 2 ? 1 : 0);
                                                })();
                                            }
                                        },
                                        impFunc2: () => {
                                        }
                                    }
                                });
                                const __exports = __ifInstance3.exports;
                                return __exports.data(remainder > 0 ? 1 : 0);
                            })();
                        }
                    },
                    impFunc2: () => {
                    }
                }
            });
            const __exports = __ifInstance2.exports;
            return __exports.data(dataLength > 0 ? 1 : 0);
        })();
    }
    return base64;
}
function base64_decode(data) {
    try {
        var decode64 = GameBoyWindow.atob(data);
    } catch (error) {
        var decode64 = lS(0, 85);
        var dataLength = data.length;
        (() => {
            const __ifInstance5 = new WebAssembly.Instance(__ifWasmModule, {
                env: {
                    impFunc1: () => {
                        {
                            var sixbits = __lA(2, 32, 48);
                            var index = 0;
                            (() => {
                                const __forInstance43 = new WebAssembly.Instance(__forWasmModule, {
                                    env: {
                                        test: () => {
                                            return index < dataLength ? 1 : 0;
                                        },
                                        update: () => {
                                        },
                                        body: () => {
                                            {
                                                sixbits = [
                                                    fromBase64.indexOf(data.charAt(index++)),
                                                    fromBase64.indexOf(data.charAt(index++)),
                                                    fromBase64.indexOf(data.charAt(index++)),
                                                    fromBase64.indexOf(data.charAt(index++))
                                                ];
                                                decode64 += String.fromCharCode(sixbits[0] << 2 | sixbits[1] >> 4) + String.fromCharCode((sixbits[1] & 15) << 4 | sixbits[2] >> 2) + String.fromCharCode((sixbits[2] & 3) << 6 | sixbits[3]);
                                            }
                                        }
                                    }
                                });
                                const __exports = __forInstance43.exports;
                                return __exports.data();
                            })();
                            (() => {
                                const __ifInstance6 = new WebAssembly.Instance(__ifWasmModule, {
                                    env: {
                                        impFunc1: () => {
                                            {
                                                decode64.length -= 1;
                                                (() => {
                                                    const __ifInstance7 = new WebAssembly.Instance(__ifWasmModule, {
                                                        env: {
                                                            impFunc1: () => {
                                                                {
                                                                    decode64.length -= 1;
                                                                }
                                                            },
                                                            impFunc2: () => {
                                                            }
                                                        }
                                                    });
                                                    const __exports = __ifInstance7.exports;
                                                    return __exports.data(sixbits[2] >= 64 ? 1 : 0);
                                                })();
                                            }
                                        },
                                        impFunc2: () => {
                                        }
                                    }
                                });
                                const __exports = __ifInstance6.exports;
                                return __exports.data(sixbits[3] >= 64 ? 1 : 0);
                            })();
                        }
                    },
                    impFunc2: () => {
                    }
                }
            });
            const __exports = __ifInstance5.exports;
            return __exports.data(dataLength > 3 && dataLength % 4 == 0 ? 1 : 0);
        })();
    }
    return decode64;
}
function to_little_endian_dword(str) {
    return to_little_endian_word(str) + String.fromCharCode(str >> 16 & 255, str >> 24 & 255);
}
function to_little_endian_word(str) {
    return to_byte(str) + String.fromCharCode(str >> 8 & 255);
}
function to_byte(str) {
    return String.fromCharCode(str & 255);
}
function arrayToBase64(arrayIn) {
    var binString = lS(0, 86);
    var length = arrayIn.length;
    (() => {
        var index = 0;
        const __forInstance3 = new WebAssembly.Instance(__forWasmModule, {
            env: {
                test: () => {
                    return index < length ? 1 : 0;
                },
                update: () => {
                    ++index;
                },
                body: () => {
                    {
                        (() => {
                            const __ifInstance8 = new WebAssembly.Instance(__ifWasmModule, {
                                env: {
                                    impFunc1: () => {
                                        {
                                            binString += String.fromCharCode(arrayIn[index]);
                                        }
                                    },
                                    impFunc2: () => {
                                    }
                                }
                            });
                            const __exports = __ifInstance8.exports;
                            return __exports.data(typeof arrayIn[index] == lS(0, 87) ? 1 : 0);
                        })();
                    }
                }
            }
        });
        const __exports = __forInstance3.exports;
        return __exports.data();
    })();
    return base64(binString);
}
function base64ToArray(b64String) {
    var binString = base64_decode(b64String);
    var outArray = [];
    var length = binString.length;
    (() => {
        var index = 0;
        const __forInstance4 = new WebAssembly.Instance(__forWasmModule, {
            env: {
                test: () => {
                    return index < length ? 1 : 0;
                },
                update: () => {
                },
                body: () => {
                    {
                        (() => {
                            const __callInstance52 = new WebAssembly.Instance(__callWasmModule, {
                                env: {
                                    impFunc: () => {
                                        outArray.push(binString.charCodeAt(index++) & 255);
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
        const __exports = __forInstance4.exports;
        return __exports.data();
    })();
    return outArray;
}
function Resampler(fromSampleRate, toSampleRate, channels, outputBufferSize, noReturn) {
    this.fromSampleRate = fromSampleRate;
    this.toSampleRate = toSampleRate;
    this.channels = channels | 0;
    this.outputBufferSize = outputBufferSize;
    this.noReturn = !!noReturn;
    (() => {
        const __callInstance51 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    this.initialize();
                }
            }
        });
        const __exports = __callInstance51.exports;
        return __exports.data();
    })();
}
Resampler.prototype.initialize = function () {
    if (this.fromSampleRate > 0 && this.toSampleRate > 0 && this.channels > 0) {
        (() => {
            const __ifInstance9 = new WebAssembly.Instance(__ifWasmModule, {
                env: {
                    impFunc1: () => {
                        {
                            this.resampler = this.bypassResampler;
                            this.ratioWeight = 1;
                        }
                    },
                    impFunc2: () => {
                        {
                            (() => {
                                const __callInstance50 = new WebAssembly.Instance(__callWasmModule, {
                                    env: {
                                        impFunc: () => {
                                            this.compileInterpolationFunction();
                                        }
                                    }
                                });
                                const __exports = __callInstance50.exports;
                                return __exports.data();
                            })();
                            this.resampler = this.interpolate;
                            this.ratioWeight = this.fromSampleRate / this.toSampleRate;
                            this.tailExists = false;
                            this.lastWeight = 0;
                            (() => {
                                const __callInstance49 = new WebAssembly.Instance(__callWasmModule, {
                                    env: {
                                        impFunc: () => {
                                            this.initializeBuffers();
                                        }
                                    }
                                });
                                const __exports = __callInstance49.exports;
                                return __exports.data();
                            })();
                        }
                    }
                }
            });
            const __exports = __ifInstance9.exports;
            return __exports.data(this.fromSampleRate == this.toSampleRate ? 1 : 0);
        })();
    } else {
        throw new Error(lS(0, 88));
    }
};
Resampler.prototype.compileInterpolationFunction = function () {
    var toCompile = lS(0, 89) + this.channels + lS(0, 90);
    (() => {
        var channel = 0;
        const __forInstance5 = new WebAssembly.Instance(__forWasmModule, {
            env: {
                test: () => {
                    return channel < this.channels ? 1 : 0;
                },
                update: () => {
                    ++channel;
                },
                body: () => {
                    {
                        toCompile += lS(0, 91) + channel + lS(0, 92);
                    }
                }
            }
        });
        const __exports = __forInstance5.exports;
        return __exports.data();
    })();
    toCompile += lS(0, 93);
    (() => {
        channel = 0;
        const __forInstance6 = new WebAssembly.Instance(__forWasmModule, {
            env: {
                test: () => {
                    return channel < this.channels ? 1 : 0;
                },
                update: () => {
                    ++channel;
                },
                body: () => {
                    {
                        toCompile += lS(0, 94) + channel + lS(0, 95);
                    }
                }
            }
        });
        const __exports = __forInstance6.exports;
        return __exports.data();
    })();
    toCompile += lS(0, 96);
    (() => {
        channel = 0;
        const __forInstance7 = new WebAssembly.Instance(__forWasmModule, {
            env: {
                test: () => {
                    return channel < this.channels ? 1 : 0;
                },
                update: () => {
                    ++channel;
                },
                body: () => {
                    {
                        toCompile += lS(0, 97) + channel + lS(0, 98) + channel + lS(0, 99);
                    }
                }
            }
        });
        const __exports = __forInstance7.exports;
        return __exports.data();
    })();
    toCompile += lS(0, 100);
    (() => {
        channel = 0;
        const __forInstance8 = new WebAssembly.Instance(__forWasmModule, {
            env: {
                test: () => {
                    return channel < this.channels ? 1 : 0;
                },
                update: () => {
                    ++channel;
                },
                body: () => {
                    {
                        toCompile += lS(0, 101) + channel + lS(0, 102);
                    }
                }
            }
        });
        const __exports = __forInstance8.exports;
        return __exports.data();
    })();
    toCompile += lS(0, 103);
    (() => {
        channel = 0;
        const __forInstance9 = new WebAssembly.Instance(__forWasmModule, {
            env: {
                test: () => {
                    return channel < this.channels ? 1 : 0;
                },
                update: () => {
                    ++channel;
                },
                body: () => {
                    {
                        toCompile += lS(0, 104) + channel + lS(0, 105) + (channel > 0 ? lS(0, 106) + channel : lS(0, 107)) + lS(0, 108);
                    }
                }
            }
        });
        const __exports = __forInstance9.exports;
        return __exports.data();
    })();
    toCompile += lS(0, 109);
    (() => {
        channel = 0;
        const __forInstance10 = new WebAssembly.Instance(__forWasmModule, {
            env: {
                test: () => {
                    return channel < this.channels ? 1 : 0;
                },
                update: () => {
                    ++channel;
                },
                body: () => {
                    {
                        toCompile += lS(0, 110) + channel + lS(0, 111);
                    }
                }
            }
        });
        const __exports = __forInstance10.exports;
        return __exports.data();
    })();
    toCompile += lS(0, 112);
    (() => {
        channel = 0;
        const __forInstance11 = new WebAssembly.Instance(__forWasmModule, {
            env: {
                test: () => {
                    return channel < this.channels ? 1 : 0;
                },
                update: () => {
                    ++channel;
                },
                body: () => {
                    {
                        toCompile += lS(0, 113) + channel + lS(0, 114) + channel + lS(0, 115);
                    }
                }
            }
        });
        const __exports = __forInstance11.exports;
        return __exports.data();
    })();
    toCompile += lS(0, 116);
    this.interpolate = window[lS(0, 146, true)](lS(0, 117), toCompile);
};
Resampler.prototype.bypassResampler = function (buffer) {
    if (this.noReturn) {
        this.outputBuffer = buffer;
        return buffer.length;
    } else {
        return buffer;
    }
};
Resampler.prototype.bufferSlice = function (sliceAmount) {
    if (this.noReturn) {
        return sliceAmount;
    } else {
        try {
            return this.outputBuffer.subarray(0, sliceAmount);
        } catch (error) {
            try {
                this.outputBuffer.length = sliceAmount;
                return this.outputBuffer;
            } catch (error) {
                return this.outputBuffer.slice(0, sliceAmount);
            }
        }
    }
};
Resampler.prototype.initializeBuffers = function () {
    try {
        this.outputBuffer = new Float32Array(this.outputBufferSize);
        this.lastOutput = new Float32Array(this.channels);
    } catch (error) {
        this.outputBuffer = [];
        this.lastOutput = [];
    }
};
function XAudioServer(channels, sampleRate, minBufferSize, maxBufferSize, underRunCallback, volume) {
    this.audioChannels = channels == 2 ? 2 : 1;
    webAudioMono = this.audioChannels == 1;
    XAudioJSSampleRate = sampleRate > 0 && sampleRate <= 16777215 ? sampleRate : 44100;
    webAudioMinBufferSize = minBufferSize >= samplesPerCallback << 1 && minBufferSize < maxBufferSize ? minBufferSize & (webAudioMono ? 4294967295 : 4294967294) : samplesPerCallback << 1;
    webAudioMaxBufferSize = Math.floor(maxBufferSize) > webAudioMinBufferSize + this.audioChannels ? maxBufferSize & (webAudioMono ? 4294967295 : 4294967294) : minBufferSize << 1;
    this.underRunCallback = typeof underRunCallback == lS(0, 118) ? underRunCallback : function () {
    };
    XAudioJSVolume = volume >= 0 && volume <= 1 ? volume : 1;
    this.audioType = -1;
    this.mozAudioTail = [];
    this.audioHandleMoz = null;
    this.audioHandleFlash = null;
    this.flashInitialized = false;
    this.mozAudioFound = false;
    (() => {
        const __callInstance48 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    this.initializeAudio();
                }
            }
        });
        const __exports = __callInstance48.exports;
        return __exports.data();
    })();
}
XAudioServer.prototype.MOZWriteAudio = function (buffer) {
    (() => {
        const __callInstance47 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    this.MOZWriteAudioNoCallback(buffer);
                }
            }
        });
        const __exports = __callInstance47.exports;
        return __exports.data();
    })();
    (() => {
        const __callInstance46 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    this.MOZExecuteCallback();
                }
            }
        });
        const __exports = __callInstance46.exports;
        return __exports.data();
    })();
};
XAudioServer.prototype.MOZWriteAudioNoCallback = function (buffer) {
    (() => {
        const __callInstance45 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    this.writeMozAudio(buffer);
                }
            }
        });
        const __exports = __callInstance45.exports;
        return __exports.data();
    })();
};
XAudioServer.prototype.callbackBasedWriteAudio = function (buffer) {
    (() => {
        const __callInstance44 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    this.callbackBasedWriteAudioNoCallback(buffer);
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
                    this.callbackBasedExecuteCallback();
                }
            }
        });
        const __exports = __callInstance43.exports;
        return __exports.data();
    })();
};
XAudioServer.prototype.callbackBasedWriteAudioNoCallback = function (buffer) {
    var length = buffer.length;
    (() => {
        var bufferCounter = 0;
        const __forInstance12 = new WebAssembly.Instance(__forWasmModule, {
            env: {
                test: () => {
                    return bufferCounter < length && audioBufferSize < webAudioMaxBufferSize ? 1 : 0;
                },
                update: () => {
                },
                body: () => {
                    {
                        audioContextSampleBuffer[audioBufferSize++] = buffer[bufferCounter++];
                    }
                }
            }
        });
        const __exports = __forInstance12.exports;
        return __exports.data();
    })();
};
XAudioServer.prototype.writeAudio = function (buffer) {
    (() => {
        const __ifInstance10 = new WebAssembly.Instance(__ifWasmModule, {
            env: {
                impFunc1: () => {
                    {
                        (() => {
                            const __callInstance42 = new WebAssembly.Instance(__callWasmModule, {
                                env: {
                                    impFunc: () => {
                                        this.MOZWriteAudio(buffer);
                                    }
                                }
                            });
                            const __exports = __callInstance42.exports;
                            return __exports.data();
                        })();
                    }
                },
                impFunc2: () => {
                    (() => {
                        const __ifInstance11 = new WebAssembly.Instance(__ifWasmModule, {
                            env: {
                                impFunc1: () => {
                                    {
                                        (() => {
                                            const __callInstance41 = new WebAssembly.Instance(__callWasmModule, {
                                                env: {
                                                    impFunc: () => {
                                                        this.callbackBasedWriteAudio(buffer);
                                                    }
                                                }
                                            });
                                            const __exports = __callInstance41.exports;
                                            return __exports.data();
                                        })();
                                    }
                                },
                                impFunc2: () => {
                                    (() => {
                                        const __ifInstance12 = new WebAssembly.Instance(__ifWasmModule, {
                                            env: {
                                                impFunc1: () => {
                                                    {
                                                        (() => {
                                                            const __ifInstance13 = new WebAssembly.Instance(__ifWasmModule, {
                                                                env: {
                                                                    impFunc1: () => {
                                                                        {
                                                                            (() => {
                                                                                const __callInstance40 = new WebAssembly.Instance(__callWasmModule, {
                                                                                    env: {
                                                                                        impFunc: () => {
                                                                                            this.callbackBasedWriteAudio(buffer);
                                                                                        }
                                                                                    }
                                                                                });
                                                                                const __exports = __callInstance40.exports;
                                                                                return __exports.data();
                                                                            })();
                                                                        }
                                                                    },
                                                                    impFunc2: () => {
                                                                        (() => {
                                                                            const __ifInstance14 = new WebAssembly.Instance(__ifWasmModule, {
                                                                                env: {
                                                                                    impFunc1: () => {
                                                                                        {
                                                                                            (() => {
                                                                                                const __callInstance39 = new WebAssembly.Instance(__callWasmModule, {
                                                                                                    env: {
                                                                                                        impFunc: () => {
                                                                                                            this.MOZWriteAudio(buffer);
                                                                                                        }
                                                                                                    }
                                                                                                });
                                                                                                const __exports = __callInstance39.exports;
                                                                                                return __exports.data();
                                                                                            })();
                                                                                        }
                                                                                    },
                                                                                    impFunc2: () => {
                                                                                    }
                                                                                }
                                                                            });
                                                                            const __exports = __ifInstance14.exports;
                                                                            return __exports.data(this.mozAudioFound ? 1 : 0);
                                                                        })();
                                                                    }
                                                                }
                                                            });
                                                            const __exports = __ifInstance13.exports;
                                                            return __exports.data(this.checkFlashInit() || launchedContext ? 1 : 0);
                                                        })();
                                                    }
                                                },
                                                impFunc2: () => {
                                                }
                                            }
                                        });
                                        const __exports = __ifInstance12.exports;
                                        return __exports.data(this.audioType == 2 ? 1 : 0);
                                    })();
                                }
                            }
                        });
                        const __exports = __ifInstance11.exports;
                        return __exports.data(this.audioType == 1 ? 1 : 0);
                    })();
                }
            }
        });
        const __exports = __ifInstance10.exports;
        return __exports.data(this.audioType == 0 ? 1 : 0);
    })();
};
XAudioServer.prototype.writeAudioNoCallback = function (buffer) {
    (() => {
        const __ifInstance15 = new WebAssembly.Instance(__ifWasmModule, {
            env: {
                impFunc1: () => {
                    {
                        (() => {
                            const __callInstance38 = new WebAssembly.Instance(__callWasmModule, {
                                env: {
                                    impFunc: () => {
                                        this.MOZWriteAudioNoCallback(buffer);
                                    }
                                }
                            });
                            const __exports = __callInstance38.exports;
                            return __exports.data();
                        })();
                    }
                },
                impFunc2: () => {
                    (() => {
                        const __ifInstance16 = new WebAssembly.Instance(__ifWasmModule, {
                            env: {
                                impFunc1: () => {
                                    {
                                        (() => {
                                            const __callInstance37 = new WebAssembly.Instance(__callWasmModule, {
                                                env: {
                                                    impFunc: () => {
                                                        this.callbackBasedWriteAudioNoCallback(buffer);
                                                    }
                                                }
                                            });
                                            const __exports = __callInstance37.exports;
                                            return __exports.data();
                                        })();
                                    }
                                },
                                impFunc2: () => {
                                    (() => {
                                        const __ifInstance17 = new WebAssembly.Instance(__ifWasmModule, {
                                            env: {
                                                impFunc1: () => {
                                                    {
                                                        (() => {
                                                            const __ifInstance18 = new WebAssembly.Instance(__ifWasmModule, {
                                                                env: {
                                                                    impFunc1: () => {
                                                                        {
                                                                            (() => {
                                                                                const __callInstance36 = new WebAssembly.Instance(__callWasmModule, {
                                                                                    env: {
                                                                                        impFunc: () => {
                                                                                            this.callbackBasedWriteAudioNoCallback(buffer);
                                                                                        }
                                                                                    }
                                                                                });
                                                                                const __exports = __callInstance36.exports;
                                                                                return __exports.data();
                                                                            })();
                                                                        }
                                                                    },
                                                                    impFunc2: () => {
                                                                        (() => {
                                                                            const __ifInstance19 = new WebAssembly.Instance(__ifWasmModule, {
                                                                                env: {
                                                                                    impFunc1: () => {
                                                                                        {
                                                                                            (() => {
                                                                                                const __callInstance35 = new WebAssembly.Instance(__callWasmModule, {
                                                                                                    env: {
                                                                                                        impFunc: () => {
                                                                                                            this.MOZWriteAudioNoCallback(buffer);
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
                                                                            const __exports = __ifInstance19.exports;
                                                                            return __exports.data(this.mozAudioFound ? 1 : 0);
                                                                        })();
                                                                    }
                                                                }
                                                            });
                                                            const __exports = __ifInstance18.exports;
                                                            return __exports.data(this.checkFlashInit() || launchedContext ? 1 : 0);
                                                        })();
                                                    }
                                                },
                                                impFunc2: () => {
                                                }
                                            }
                                        });
                                        const __exports = __ifInstance17.exports;
                                        return __exports.data(this.audioType == 2 ? 1 : 0);
                                    })();
                                }
                            }
                        });
                        const __exports = __ifInstance16.exports;
                        return __exports.data(this.audioType == 1 ? 1 : 0);
                    })();
                }
            }
        });
        const __exports = __ifInstance15.exports;
        return __exports.data(this.audioType == 0 ? 1 : 0);
    })();
};
XAudioServer.prototype.remainingBuffer = function () {
    if (this.audioType == 0) {
        return this.samplesAlreadyWritten - this.audioHandleMoz.mozCurrentSampleOffset();
    } else if (this.audioType == 1) {
        return (resampledSamplesLeft() * resampleControl.ratioWeight >> this.audioChannels - 1 << this.audioChannels - 1) + audioBufferSize;
    } else if (this.audioType == 2) {
        if (this.checkFlashInit() || launchedContext) {
            return (resampledSamplesLeft() * resampleControl.ratioWeight >> this.audioChannels - 1 << this.audioChannels - 1) + audioBufferSize;
        } else if (this.mozAudioFound) {
            return this.samplesAlreadyWritten - this.audioHandleMoz.mozCurrentSampleOffset();
        }
    }
    return 0;
};
XAudioServer.prototype.MOZExecuteCallback = function () {
    var samplesRequested = webAudioMinBufferSize - this.remainingBuffer();
    (() => {
        const __ifInstance20 = new WebAssembly.Instance(__ifWasmModule, {
            env: {
                impFunc1: () => {
                    {
                        (() => {
                            const __callInstance34 = new WebAssembly.Instance(__callWasmModule, {
                                env: {
                                    impFunc: () => {
                                        this.writeMozAudio(this.underRunCallback(samplesRequested));
                                    }
                                }
                            });
                            const __exports = __callInstance34.exports;
                            return __exports.data();
                        })();
                    }
                },
                impFunc2: () => {
                }
            }
        });
        const __exports = __ifInstance20.exports;
        return __exports.data(samplesRequested > 0 ? 1 : 0);
    })();
};
XAudioServer.prototype.callbackBasedExecuteCallback = function () {
    var samplesRequested = webAudioMinBufferSize - this.remainingBuffer();
    (() => {
        const __ifInstance21 = new WebAssembly.Instance(__ifWasmModule, {
            env: {
                impFunc1: () => {
                    {
                        (() => {
                            const __callInstance33 = new WebAssembly.Instance(__callWasmModule, {
                                env: {
                                    impFunc: () => {
                                        this.callbackBasedWriteAudioNoCallback(this.underRunCallback(samplesRequested));
                                    }
                                }
                            });
                            const __exports = __callInstance33.exports;
                            return __exports.data();
                        })();
                    }
                },
                impFunc2: () => {
                }
            }
        });
        const __exports = __ifInstance21.exports;
        return __exports.data(samplesRequested > 0 ? 1 : 0);
    })();
};
XAudioServer.prototype.executeCallback = function () {
    (() => {
        const __ifInstance22 = new WebAssembly.Instance(__ifWasmModule, {
            env: {
                impFunc1: () => {
                    {
                        (() => {
                            const __callInstance32 = new WebAssembly.Instance(__callWasmModule, {
                                env: {
                                    impFunc: () => {
                                        this.MOZExecuteCallback();
                                    }
                                }
                            });
                            const __exports = __callInstance32.exports;
                            return __exports.data();
                        })();
                    }
                },
                impFunc2: () => {
                    (() => {
                        const __ifInstance23 = new WebAssembly.Instance(__ifWasmModule, {
                            env: {
                                impFunc1: () => {
                                    {
                                        (() => {
                                            const __callInstance31 = new WebAssembly.Instance(__callWasmModule, {
                                                env: {
                                                    impFunc: () => {
                                                        this.callbackBasedExecuteCallback();
                                                    }
                                                }
                                            });
                                            const __exports = __callInstance31.exports;
                                            return __exports.data();
                                        })();
                                    }
                                },
                                impFunc2: () => {
                                    (() => {
                                        const __ifInstance24 = new WebAssembly.Instance(__ifWasmModule, {
                                            env: {
                                                impFunc1: () => {
                                                    {
                                                        (() => {
                                                            const __ifInstance25 = new WebAssembly.Instance(__ifWasmModule, {
                                                                env: {
                                                                    impFunc1: () => {
                                                                        {
                                                                            (() => {
                                                                                const __callInstance30 = new WebAssembly.Instance(__callWasmModule, {
                                                                                    env: {
                                                                                        impFunc: () => {
                                                                                            this.callbackBasedExecuteCallback();
                                                                                        }
                                                                                    }
                                                                                });
                                                                                const __exports = __callInstance30.exports;
                                                                                return __exports.data();
                                                                            })();
                                                                        }
                                                                    },
                                                                    impFunc2: () => {
                                                                        (() => {
                                                                            const __ifInstance26 = new WebAssembly.Instance(__ifWasmModule, {
                                                                                env: {
                                                                                    impFunc1: () => {
                                                                                        {
                                                                                            (() => {
                                                                                                const __callInstance29 = new WebAssembly.Instance(__callWasmModule, {
                                                                                                    env: {
                                                                                                        impFunc: () => {
                                                                                                            this.MOZExecuteCallback();
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
                                                                            const __exports = __ifInstance26.exports;
                                                                            return __exports.data(this.mozAudioFound ? 1 : 0);
                                                                        })();
                                                                    }
                                                                }
                                                            });
                                                            const __exports = __ifInstance25.exports;
                                                            return __exports.data(this.checkFlashInit() || launchedContext ? 1 : 0);
                                                        })();
                                                    }
                                                },
                                                impFunc2: () => {
                                                }
                                            }
                                        });
                                        const __exports = __ifInstance24.exports;
                                        return __exports.data(this.audioType == 2 ? 1 : 0);
                                    })();
                                }
                            }
                        });
                        const __exports = __ifInstance23.exports;
                        return __exports.data(this.audioType == 1 ? 1 : 0);
                    })();
                }
            }
        });
        const __exports = __ifInstance22.exports;
        return __exports.data(this.audioType == 0 ? 1 : 0);
    })();
};
XAudioServer.prototype.initializeAudio = function () {
    try {
        throw new Error(lS(0, 119));
        (() => {
            const __callInstance28 = new WebAssembly.Instance(__callWasmModule, {
                env: {
                    impFunc: () => {
                        this.preInitializeMozAudio();
                    }
                }
            });
            const __exports = __callInstance28.exports;
            return __exports.data();
        })();
        if (navigator.platform == lS(0, 120)) {
            throw new Error(lS(0, 121));
        }
        (() => {
            const __callInstance27 = new WebAssembly.Instance(__callWasmModule, {
                env: {
                    impFunc: () => {
                        this.initializeMozAudio();
                    }
                }
            });
            const __exports = __callInstance27.exports;
            return __exports.data();
        })();
    } catch (error) {
        try {
            (() => {
                const __callInstance26 = new WebAssembly.Instance(__callWasmModule, {
                    env: {
                        impFunc: () => {
                            this.initializeWebAudio();
                        }
                    }
                });
                const __exports = __callInstance26.exports;
                return __exports.data();
            })();
        } catch (error) {
            try {
                (() => {
                    const __callInstance25 = new WebAssembly.Instance(__callWasmModule, {
                        env: {
                            impFunc: () => {
                                this.initializeFlashAudio();
                            }
                        }
                    });
                    const __exports = __callInstance25.exports;
                    return __exports.data();
                })();
            } catch (error) {
                throw new Error(lS(0, 122));
            }
        }
    }
};
XAudioServer.prototype.preInitializeMozAudio = function () {
    this.audioHandleMoz = new Audio();
    (() => {
        const __callInstance24 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    this.audioHandleMoz.mozSetup(this.audioChannels, XAudioJSSampleRate);
                }
            }
        });
        const __exports = __callInstance24.exports;
        return __exports.data();
    })();
    this.samplesAlreadyWritten = 0;
    var emptySampleFrame = this.audioChannels == 2 ? __lA(3, 48, 56) : __lA(4, 56, 60);
    var prebufferAmount = 0;
    (() => {
        const __ifInstance27 = new WebAssembly.Instance(__ifWasmModule, {
            env: {
                impFunc1: () => {
                    {
                        (() => {
                            const __forInstance44 = new WebAssembly.Instance(__forWasmModule, {
                                env: {
                                    test: () => {
                                        return this.audioHandleMoz.mozCurrentSampleOffset() == 0 ? 1 : 0;
                                    },
                                    update: () => {
                                    },
                                    body: () => {
                                        {
                                            prebufferAmount += this.audioHandleMoz.mozWriteAudio(emptySampleFrame);
                                        }
                                    }
                                }
                            });
                            const __exports = __forInstance44.exports;
                            return __exports.data();
                        })();
                        var samplesToDoubleBuffer = prebufferAmount / this.audioChannels;
                        (() => {
                            var index = 0;
                            const __forInstance13 = new WebAssembly.Instance(__forWasmModule, {
                                env: {
                                    test: () => {
                                        return index < samplesToDoubleBuffer ? 1 : 0;
                                    },
                                    update: () => {
                                        index++;
                                    },
                                    body: () => {
                                        {
                                            this.samplesAlreadyWritten += this.audioHandleMoz.mozWriteAudio(emptySampleFrame);
                                        }
                                    }
                                }
                            });
                            const __exports = __forInstance13.exports;
                            return __exports.data();
                        })();
                    }
                },
                impFunc2: () => {
                }
            }
        });
        const __exports = __ifInstance27.exports;
        return __exports.data(navigator.platform != lS(0, 123) && navigator.platform != lS(0, 124) ? 1 : 0);
    })();
    this.samplesAlreadyWritten += prebufferAmount;
    webAudioMinBufferSize += this.samplesAlreadyWritten;
    this.mozAudioFound = true;
};
XAudioServer.prototype.initializeMozAudio = function () {
    (() => {
        const __callInstance23 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    this.writeMozAudio(getFloat32(webAudioMinBufferSize));
                }
            }
        });
        const __exports = __callInstance23.exports;
        return __exports.data();
    })();
    this.audioType = 0;
};
XAudioServer.prototype.initializeWebAudio = function () {
    if (launchedContext) {
        (() => {
            const __callInstance22 = new WebAssembly.Instance(__callWasmModule, {
                env: {
                    impFunc: () => {
                        resetCallbackAPIAudioBuffer(webAudioActualSampleRate, samplesPerCallback);
                    }
                }
            });
            const __exports = __callInstance22.exports;
            return __exports.data();
        })();
        this.audioType = 1;
    } else {
        throw new Error(lS(0, 125));
    }
};
XAudioServer.prototype.initializeFlashAudio = function () {
    var existingFlashload = document.getElementById(lS(0, 126));
    (() => {
        const __ifInstance28 = new WebAssembly.Instance(__ifWasmModule, {
            env: {
                impFunc1: () => {
                    {
                        var thisObj = this;
                        var mainContainerNode = document.createElement(lS(0, 127));
                        (() => {
                            const __callInstance21 = new WebAssembly.Instance(__callWasmModule, {
                                env: {
                                    impFunc: () => {
                                        mainContainerNode.setAttribute(lS(0, 128), lS(0, 129));
                                    }
                                }
                            });
                            const __exports = __callInstance21.exports;
                            return __exports.data();
                        })();
                        var containerNode = document.createElement(lS(0, 130));
                        (() => {
                            const __callInstance20 = new WebAssembly.Instance(__callWasmModule, {
                                env: {
                                    impFunc: () => {
                                        containerNode.setAttribute(lS(0, 131), lS(0, 132));
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
                                        containerNode.setAttribute(lS(0, 133), lS(0, 134));
                                    }
                                }
                            });
                            const __exports = __callInstance19.exports;
                            return __exports.data();
                        })();
                        (() => {
                            const __callInstance18 = new WebAssembly.Instance(__callWasmModule, {
                                env: {
                                    impFunc: () => {
                                        mainContainerNode.appendChild(containerNode);
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
                                        document.getElementsByTagName(lS(0, 135))[0].appendChild(mainContainerNode);
                                    }
                                }
                            });
                            const __exports = __callInstance17.exports;
                            return __exports.data();
                        })();
                        (() => {
                            const __callInstance16 = new WebAssembly.Instance(__callWasmModule, {
                                env: {
                                    impFunc: () => {
                                        swfobject.embedSWF(lS(0, 136), lS(0, 137), lS(0, 138), lS(0, 139), lS(0, 140), lS(0, 141), {}, { 'allowscriptaccess': 'always' }, { 'style': 'position: static; visibility: hidden; margin: 8px; padding: 0px; border: none' }, function (event) {
                                            (() => {
                                                const __ifInstance29 = new WebAssembly.Instance(__ifWasmModule, {
                                                    env: {
                                                        impFunc1: () => {
                                                            {
                                                                thisObj.audioHandleFlash = event.ref;
                                                            }
                                                        },
                                                        impFunc2: () => {
                                                            {
                                                                thisObj.audioType = 1;
                                                            }
                                                        }
                                                    }
                                                });
                                                const __exports = __ifInstance29.exports;
                                                return __exports.data(event.success ? 1 : 0);
                                            })();
                                        });
                                    }
                                }
                            });
                            const __exports = __callInstance16.exports;
                            return __exports.data();
                        })();
                    }
                },
                impFunc2: () => {
                    {
                        this.audioHandleFlash = existingFlashload;
                    }
                }
            }
        });
        const __exports = __ifInstance28.exports;
        return __exports.data(existingFlashload == null ? 1 : 0);
    })();
    this.audioType = 2;
};
XAudioServer.prototype.changeVolume = function (newVolume) {
    (() => {
        const __ifInstance30 = new WebAssembly.Instance(__ifWasmModule, {
            env: {
                impFunc1: () => {
                    {
                        XAudioJSVolume = newVolume;
                        (() => {
                            const __ifInstance31 = new WebAssembly.Instance(__ifWasmModule, {
                                env: {
                                    impFunc1: () => {
                                        {
                                            (() => {
                                                const __callInstance15 = new WebAssembly.Instance(__callWasmModule, {
                                                    env: {
                                                        impFunc: () => {
                                                            this.audioHandleFlash.changeVolume(XAudioJSVolume);
                                                        }
                                                    }
                                                });
                                                const __exports = __callInstance15.exports;
                                                return __exports.data();
                                            })();
                                        }
                                    },
                                    impFunc2: () => {
                                    }
                                }
                            });
                            const __exports = __ifInstance31.exports;
                            return __exports.data(this.checkFlashInit() ? 1 : 0);
                        })();
                        (() => {
                            const __ifInstance32 = new WebAssembly.Instance(__ifWasmModule, {
                                env: {
                                    impFunc1: () => {
                                        {
                                            this.audioHandleMoz.volume = XAudioJSVolume;
                                        }
                                    },
                                    impFunc2: () => {
                                    }
                                }
                            });
                            const __exports = __ifInstance32.exports;
                            return __exports.data(this.mozAudioFound ? 1 : 0);
                        })();
                    }
                },
                impFunc2: () => {
                }
            }
        });
        const __exports = __ifInstance30.exports;
        return __exports.data(newVolume >= 0 && newVolume <= 1 ? 1 : 0);
    })();
};
XAudioServer.prototype.writeMozAudio = function (buffer) {
    var length = this.mozAudioTail.length;
    (() => {
        const __ifInstance33 = new WebAssembly.Instance(__ifWasmModule, {
            env: {
                impFunc1: () => {
                    {
                        var samplesAccepted = this.audioHandleMoz.mozWriteAudio(this.mozAudioTail);
                        this.samplesAlreadyWritten += samplesAccepted;
                        (() => {
                            const __callInstance14 = new WebAssembly.Instance(__callWasmModule, {
                                env: {
                                    impFunc: () => {
                                        this.mozAudioTail.splice(0, samplesAccepted);
                                    }
                                }
                            });
                            const __exports = __callInstance14.exports;
                            return __exports.data();
                        })();
                    }
                },
                impFunc2: () => {
                }
            }
        });
        const __exports = __ifInstance33.exports;
        return __exports.data(length > 0 ? 1 : 0);
    })();
    length = Math.min(buffer.length, webAudioMaxBufferSize - this.samplesAlreadyWritten + this.audioHandleMoz.mozCurrentSampleOffset());
    var samplesAccepted = this.audioHandleMoz.mozWriteAudio(buffer);
    this.samplesAlreadyWritten += samplesAccepted;
    (() => {
        var index = 0;
        const __forInstance14 = new WebAssembly.Instance(__forWasmModule, {
            env: {
                test: () => {
                    return length > samplesAccepted ? 1 : 0;
                },
                update: () => {
                    --length;
                },
                body: () => {
                    {
                        (() => {
                            const __callInstance13 = new WebAssembly.Instance(__callWasmModule, {
                                env: {
                                    impFunc: () => {
                                        this.mozAudioTail.push(buffer[index++]);
                                    }
                                }
                            });
                            const __exports = __callInstance13.exports;
                            return __exports.data();
                        })();
                    }
                }
            }
        });
        const __exports = __forInstance14.exports;
        return __exports.data();
    })();
};
XAudioServer.prototype.checkFlashInit = function () {
    (() => {
        const __ifInstance34 = new WebAssembly.Instance(__ifWasmModule, {
            env: {
                impFunc1: () => {
                    {
                        this.flashInitialized = true;
                        (() => {
                            const __callInstance12 = new WebAssembly.Instance(__callWasmModule, {
                                env: {
                                    impFunc: () => {
                                        this.audioHandleFlash.initialize(this.audioChannels, XAudioJSVolume);
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
                                        resetCallbackAPIAudioBuffer(44100, samplesPerCallback);
                                    }
                                }
                            });
                            const __exports = __callInstance11.exports;
                            return __exports.data();
                        })();
                    }
                },
                impFunc2: () => {
                }
            }
        });
        const __exports = __ifInstance34.exports;
        return __exports.data(!this.flashInitialized && this.audioHandleFlash && this.audioHandleFlash.initialize ? 1 : 0);
    })();
    return this.flashInitialized;
};
function getFloat32(size) {
    try {
        return new Float32Array(size);
    } catch (error) {
        return new Array(size);
    }
}
function getFloat32Flat(size) {
    try {
        var newBuffer = new Float32Array(size);
    } catch (error) {
        var newBuffer = new Array(size);
        var audioSampleIndice = 0;
        do {
            newBuffer[audioSampleIndice] = 0;
        } while (++audioSampleIndice < size);
    }
    return newBuffer;
}
var samplesPerCallback = 2048;
var outputConvert = null;
function audioOutputFlashEvent() {
    (() => {
        const __callInstance10 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    resampleRefill();
                }
            }
        });
        const __exports = __callInstance10.exports;
        return __exports.data();
    })();
    return outputConvert();
}
function generateFlashStereoString() {
    var copyBinaryStringLeft = lS(0, 142);
    var copyBinaryStringRight = lS(0, 143);
    (() => {
        var index = 0;
        const __forInstance15 = new WebAssembly.Instance(__forWasmModule, {
            env: {
                test: () => {
                    return index < samplesPerCallback && resampleBufferStart != resampleBufferEnd ? 1 : 0;
                },
                update: () => {
                    ++index;
                },
                body: () => {
                    {
                        copyBinaryStringLeft += String.fromCharCode((Math.min(Math.max(resampled[resampleBufferStart++] + 1, 0), 2) * 16383 | 0) + 12288);
                        copyBinaryStringRight += String.fromCharCode((Math.min(Math.max(resampled[resampleBufferStart++] + 1, 0), 2) * 16383 | 0) + 12288);
                        (() => {
                            const __ifInstance35 = new WebAssembly.Instance(__ifWasmModule, {
                                env: {
                                    impFunc1: () => {
                                        {
                                            resampleBufferStart = 0;
                                        }
                                    },
                                    impFunc2: () => {
                                    }
                                }
                            });
                            const __exports = __ifInstance35.exports;
                            return __exports.data(resampleBufferStart == resampleBufferSize ? 1 : 0);
                        })();
                    }
                }
            }
        });
        const __exports = __forInstance15.exports;
        return __exports.data();
    })();
    return copyBinaryStringLeft + copyBinaryStringRight;
}
function generateFlashMonoString() {
    var copyBinaryString = lS(0, 144);
    (() => {
        var index = 0;
        const __forInstance16 = new WebAssembly.Instance(__forWasmModule, {
            env: {
                test: () => {
                    return index < samplesPerCallback && resampleBufferStart != resampleBufferEnd ? 1 : 0;
                },
                update: () => {
                    ++index;
                },
                body: () => {
                    {
                        copyBinaryString += String.fromCharCode((Math.min(Math.max(resampled[resampleBufferStart++] + 1, 0), 2) * 16383 | 0) + 12288);
                        (() => {
                            const __ifInstance36 = new WebAssembly.Instance(__ifWasmModule, {
                                env: {
                                    impFunc1: () => {
                                        {
                                            resampleBufferStart = 0;
                                        }
                                    },
                                    impFunc2: () => {
                                    }
                                }
                            });
                            const __exports = __ifInstance36.exports;
                            return __exports.data(resampleBufferStart == resampleBufferSize ? 1 : 0);
                        })();
                    }
                }
            }
        });
        const __exports = __forInstance16.exports;
        return __exports.data();
    })();
    return copyBinaryString;
}
var audioContextHandle = null;
var audioNode = null;
var audioSource = null;
var launchedContext = false;
var audioContextSampleBuffer = [];
var resampled = [];
var webAudioMinBufferSize = 15000;
var webAudioMaxBufferSize = 25000;
var webAudioActualSampleRate = 44100;
var XAudioJSSampleRate = 0;
var webAudioMono = false;
var XAudioJSVolume = 1;
var resampleControl = null;
var audioBufferSize = 0;
var resampleBufferStart = 0;
var resampleBufferEnd = 0;
var resampleBufferSize = 2;
function audioOutputEvent(event) {
    var index = 0;
    var buffer1 = event.outputBuffer.getChannelData(0);
    var buffer2 = event.outputBuffer.getChannelData(1);
    (() => {
        const __callInstance9 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    resampleRefill();
                }
            }
        });
        const __exports = __callInstance9.exports;
        return __exports.data();
    })();
    (() => {
        const __ifInstance37 = new WebAssembly.Instance(__ifWasmModule, {
            env: {
                impFunc1: () => {
                    {
                        (() => {
                            const __forInstance45 = new WebAssembly.Instance(__forWasmModule, {
                                env: {
                                    test: () => {
                                        return index < samplesPerCallback && resampleBufferStart != resampleBufferEnd ? 1 : 0;
                                    },
                                    update: () => {
                                    },
                                    body: () => {
                                        {
                                            buffer1[index] = resampled[resampleBufferStart++] * XAudioJSVolume;
                                            buffer2[index++] = resampled[resampleBufferStart++] * XAudioJSVolume;
                                            (() => {
                                                const __ifInstance38 = new WebAssembly.Instance(__ifWasmModule, {
                                                    env: {
                                                        impFunc1: () => {
                                                            {
                                                                resampleBufferStart = 0;
                                                            }
                                                        },
                                                        impFunc2: () => {
                                                        }
                                                    }
                                                });
                                                const __exports = __ifInstance38.exports;
                                                return __exports.data(resampleBufferStart == resampleBufferSize ? 1 : 0);
                                            })();
                                        }
                                    }
                                }
                            });
                            const __exports = __forInstance45.exports;
                            return __exports.data();
                        })();
                    }
                },
                impFunc2: () => {
                    {
                        (() => {
                            const __forInstance46 = new WebAssembly.Instance(__forWasmModule, {
                                env: {
                                    test: () => {
                                        return index < samplesPerCallback && resampleBufferStart != resampleBufferEnd ? 1 : 0;
                                    },
                                    update: () => {
                                    },
                                    body: () => {
                                        {
                                            buffer2[index] = buffer1[index] = resampled[resampleBufferStart++] * XAudioJSVolume;
                                            ++index;
                                            (() => {
                                                const __ifInstance39 = new WebAssembly.Instance(__ifWasmModule, {
                                                    env: {
                                                        impFunc1: () => {
                                                            {
                                                                resampleBufferStart = 0;
                                                            }
                                                        },
                                                        impFunc2: () => {
                                                        }
                                                    }
                                                });
                                                const __exports = __ifInstance39.exports;
                                                return __exports.data(resampleBufferStart == resampleBufferSize ? 1 : 0);
                                            })();
                                        }
                                    }
                                }
                            });
                            const __exports = __forInstance46.exports;
                            return __exports.data();
                        })();
                    }
                }
            }
        });
        const __exports = __ifInstance37.exports;
        return __exports.data(!webAudioMono ? 1 : 0);
    })();
    (() => {
        const __forInstance47 = new WebAssembly.Instance(__forWasmModule, {
            env: {
                test: () => {
                    return index < samplesPerCallback ? 1 : 0;
                },
                update: () => {
                },
                body: () => {
                    {
                        buffer2[index] = buffer1[index] = 0;
                        ++index;
                    }
                }
            }
        });
        const __exports = __forInstance47.exports;
        return __exports.data();
    })();
}
function resampleRefill() {
    (() => {
        const __ifInstance40 = new WebAssembly.Instance(__ifWasmModule, {
            env: {
                impFunc1: () => {
                    {
                        var resampleLength = resampleControl.resampler(getBufferSamples());
                        var resampledResult = resampleControl.outputBuffer;
                        (() => {
                            var index2 = 0;
                            const __forInstance17 = new WebAssembly.Instance(__forWasmModule, {
                                env: {
                                    test: () => {
                                        return index2 < resampleLength ? 1 : 0;
                                    },
                                    update: () => {
                                        ++index2;
                                    },
                                    body: () => {
                                        {
                                            resampled[resampleBufferEnd++] = resampledResult[index2];
                                            (() => {
                                                const __ifInstance41 = new WebAssembly.Instance(__ifWasmModule, {
                                                    env: {
                                                        impFunc1: () => {
                                                            {
                                                                resampleBufferEnd = 0;
                                                            }
                                                        },
                                                        impFunc2: () => {
                                                        }
                                                    }
                                                });
                                                const __exports = __ifInstance41.exports;
                                                return __exports.data(resampleBufferEnd == resampleBufferSize ? 1 : 0);
                                            })();
                                            (() => {
                                                const __ifInstance42 = new WebAssembly.Instance(__ifWasmModule, {
                                                    env: {
                                                        impFunc1: () => {
                                                            {
                                                                ++resampleBufferStart;
                                                                (() => {
                                                                    const __ifInstance43 = new WebAssembly.Instance(__ifWasmModule, {
                                                                        env: {
                                                                            impFunc1: () => {
                                                                                {
                                                                                    resampleBufferStart = 0;
                                                                                }
                                                                            },
                                                                            impFunc2: () => {
                                                                            }
                                                                        }
                                                                    });
                                                                    const __exports = __ifInstance43.exports;
                                                                    return __exports.data(resampleBufferStart == resampleBufferSize ? 1 : 0);
                                                                })();
                                                            }
                                                        },
                                                        impFunc2: () => {
                                                        }
                                                    }
                                                });
                                                const __exports = __ifInstance42.exports;
                                                return __exports.data(resampleBufferStart == resampleBufferEnd ? 1 : 0);
                                            })();
                                        }
                                    }
                                }
                            });
                            const __exports = __forInstance17.exports;
                            return __exports.data();
                        })();
                        audioBufferSize = 0;
                    }
                },
                impFunc2: () => {
                }
            }
        });
        const __exports = __ifInstance40.exports;
        return __exports.data(audioBufferSize > 0 ? 1 : 0);
    })();
}
function resampledSamplesLeft() {
    return (resampleBufferStart <= resampleBufferEnd ? 0 : resampleBufferSize) + resampleBufferEnd - resampleBufferStart;
}
function getBufferSamples() {
    try {
        return audioContextSampleBuffer.subarray(0, audioBufferSize);
    } catch (error) {
        try {
            audioContextSampleBuffer.length = audioBufferSize;
            return audioContextSampleBuffer;
        } catch (error) {
            return audioContextSampleBuffer.slice(0, audioBufferSize);
        }
    }
}
function resetCallbackAPIAudioBuffer(APISampleRate, bufferAlloc) {
    audioContextSampleBuffer = getFloat32(webAudioMaxBufferSize);
    audioBufferSize = webAudioMaxBufferSize;
    resampleBufferStart = 0;
    resampleBufferEnd = 0;
    resampleBufferSize = Math.max(webAudioMaxBufferSize * Math.ceil(XAudioJSSampleRate / APISampleRate), samplesPerCallback) << 1;
    (() => {
        const __ifInstance44 = new WebAssembly.Instance(__ifWasmModule, {
            env: {
                impFunc1: () => {
                    {
                        resampled = getFloat32Flat(resampleBufferSize);
                        resampleControl = new Resampler(XAudioJSSampleRate, APISampleRate, 1, resampleBufferSize, true);
                        outputConvert = generateFlashMonoString;
                    }
                },
                impFunc2: () => {
                    {
                        resampleBufferSize <<= 1;
                        resampled = getFloat32Flat(resampleBufferSize);
                        resampleControl = new Resampler(XAudioJSSampleRate, APISampleRate, 2, resampleBufferSize, true);
                        outputConvert = generateFlashStereoString;
                    }
                }
            }
        });
        const __exports = __ifInstance44.exports;
        return __exports.data(webAudioMono ? 1 : 0);
    })();
}
(() => {
    const __callInstance8 = new WebAssembly.Instance(__callWasmModule, {
        env: {
            impFunc: () => {
                (function () {
                    if (!launchedContext) {
                        try {
                            audioContextHandle = new GameBoyAudioContext();
                        } catch (error) {
                            try {
                                audioContextHandle = new AudioContext();
                            } catch (error) {
                                return;
                            }
                        }
                        try {
                            audioSource = audioContextHandle.createBufferSource();
                            audioSource.loop = false;
                            XAudioJSSampleRate = webAudioActualSampleRate = audioContextHandle.sampleRate;
                            audioSource.buffer = audioContextHandle.createBuffer(1, 1, webAudioActualSampleRate);
                            audioNode = audioContextHandle.createJavaScriptNode(samplesPerCallback, 1, 2);
                            audioNode.onaudioprocess = audioOutputEvent;
                            (() => {
                                const __callInstance7 = new WebAssembly.Instance(__callWasmModule, {
                                    env: {
                                        impFunc: () => {
                                            audioSource.connect(audioNode);
                                        }
                                    }
                                });
                                const __exports = __callInstance7.exports;
                                return __exports.data();
                            })();
                            (() => {
                                const __callInstance6 = new WebAssembly.Instance(__callWasmModule, {
                                    env: {
                                        impFunc: () => {
                                            audioNode.connect(audioContextHandle.destination);
                                        }
                                    }
                                });
                                const __exports = __callInstance6.exports;
                                return __exports.data();
                            })();
                            (() => {
                                const __callInstance5 = new WebAssembly.Instance(__callWasmModule, {
                                    env: {
                                        impFunc: () => {
                                            audioSource.noteOn(0);
                                        }
                                    }
                                });
                                const __exports = __callInstance5.exports;
                                return __exports.data();
                            })();
                        } catch (error) {
                            return;
                        }
                        launchedContext = true;
                    }
                }());
            }
        }
    });
    const __exports = __callInstance8.exports;
    return __exports.data();
})();
function Resize(widthOriginal, heightOriginal, targetWidth, targetHeight, blendAlpha, interpolationPass) {
    this.widthOriginal = Math.abs(parseInt(widthOriginal) || 0);
    this.heightOriginal = Math.abs(parseInt(heightOriginal) || 0);
    this.targetWidth = Math.abs(parseInt(targetWidth) || 0);
    this.targetHeight = Math.abs(parseInt(targetHeight) || 0);
    this.colorChannels = !!blendAlpha ? 4 : 3;
    this.interpolationPass = !!interpolationPass;
    this.targetWidthMultipliedByChannels = this.targetWidth * this.colorChannels;
    this.originalWidthMultipliedByChannels = this.widthOriginal * this.colorChannels;
    this.originalHeightMultipliedByChannels = this.heightOriginal * this.colorChannels;
    this.widthPassResultSize = this.targetWidthMultipliedByChannels * this.heightOriginal;
    this.finalResultSize = this.targetWidthMultipliedByChannels * this.targetHeight;
    (() => {
        const __callInstance4 = new WebAssembly.Instance(__callWasmModule, {
            env: {
                impFunc: () => {
                    this.initialize();
                }
            }
        });
        const __exports = __callInstance4.exports;
        return __exports.data();
    })();
}
Resize.prototype.initialize = function () {
    if (this.widthOriginal > 0 && this.heightOriginal > 0 && this.targetWidth > 0 && this.targetHeight > 0) {
        (() => {
            const __ifInstance45 = new WebAssembly.Instance(__ifWasmModule, {
                env: {
                    impFunc1: () => {
                        {
                            this.resizeWidth = this.bypassResizer;
                        }
                    },
                    impFunc2: () => {
                        {
                            this.ratioWeightWidthPass = this.widthOriginal / this.targetWidth;
                            (() => {
                                const __ifInstance46 = new WebAssembly.Instance(__ifWasmModule, {
                                    env: {
                                        impFunc1: () => {
                                            {
                                                (() => {
                                                    const __callInstance3 = new WebAssembly.Instance(__callWasmModule, {
                                                        env: {
                                                            impFunc: () => {
                                                                this.initializeFirstPassBuffers(true);
                                                            }
                                                        }
                                                    });
                                                    const __exports = __callInstance3.exports;
                                                    return __exports.data();
                                                })();
                                                this.resizeWidth = this.colorChannels == 4 ? this.resizeWidthInterpolatedRGBA : this.resizeWidthInterpolatedRGB;
                                            }
                                        },
                                        impFunc2: () => {
                                            {
                                                (() => {
                                                    const __callInstance2 = new WebAssembly.Instance(__callWasmModule, {
                                                        env: {
                                                            impFunc: () => {
                                                                this.initializeFirstPassBuffers(false);
                                                            }
                                                        }
                                                    });
                                                    const __exports = __callInstance2.exports;
                                                    return __exports.data();
                                                })();
                                                this.resizeWidth = this.colorChannels == 4 ? this.resizeWidthRGBA : this.resizeWidthRGB;
                                            }
                                        }
                                    }
                                });
                                const __exports = __ifInstance46.exports;
                                return __exports.data(this.ratioWeightWidthPass < 1 && this.interpolationPass ? 1 : 0);
                            })();
                        }
                    }
                }
            });
            const __exports = __ifInstance45.exports;
            return __exports.data(this.widthOriginal == this.targetWidth ? 1 : 0);
        })();
        (() => {
            const __ifInstance47 = new WebAssembly.Instance(__ifWasmModule, {
                env: {
                    impFunc1: () => {
                        {
                            this.resizeHeight = this.bypassResizer;
                        }
                    },
                    impFunc2: () => {
                        {
                            this.ratioWeightHeightPass = this.heightOriginal / this.targetHeight;
                            (() => {
                                const __ifInstance48 = new WebAssembly.Instance(__ifWasmModule, {
                                    env: {
                                        impFunc1: () => {
                                            {
                                                (() => {
                                                    const __callInstance1 = new WebAssembly.Instance(__callWasmModule, {
                                                        env: {
                                                            impFunc: () => {
                                                                this.initializeSecondPassBuffers(true);
                                                            }
                                                        }
                                                    });
                                                    const __exports = __callInstance1.exports;
                                                    return __exports.data();
                                                })();
                                                this.resizeHeight = this.resizeHeightInterpolated;
                                            }
                                        },
                                        impFunc2: () => {
                                            {
                                                (() => {
                                                    const __callInstance0 = new WebAssembly.Instance(__callWasmModule, {
                                                        env: {
                                                            impFunc: () => {
                                                                this.initializeSecondPassBuffers(false);
                                                            }
                                                        }
                                                    });
                                                    const __exports = __callInstance0.exports;
                                                    return __exports.data();
                                                })();
                                                this.resizeHeight = this.colorChannels == 4 ? this.resizeHeightRGBA : this.resizeHeightRGB;
                                            }
                                        }
                                    }
                                });
                                const __exports = __ifInstance48.exports;
                                return __exports.data(this.ratioWeightHeightPass < 1 && this.interpolationPass ? 1 : 0);
                            })();
                        }
                    }
                }
            });
            const __exports = __ifInstance47.exports;
            return __exports.data(this.heightOriginal == this.targetHeight ? 1 : 0);
        })();
    } else {
        throw new Error(lS(0, 145));
    }
};
Resize.prototype.resizeWidthRGB = function (buffer) {
    var ratioWeight = this.ratioWeightWidthPass;
    var weight = 0;
    var amountToNext = 0;
    var actualPosition = 0;
    var currentPosition = 0;
    var line = 0;
    var pixelOffset = 0;
    var outputOffset = 0;
    var nextLineOffsetOriginalWidth = this.originalWidthMultipliedByChannels - 2;
    var nextLineOffsetTargetWidth = this.targetWidthMultipliedByChannels - 2;
    var output = this.outputWidthWorkBench;
    var outputBuffer = this.widthBuffer;
    do {
        (() => {
            line = 0;
            const __forInstance18 = new WebAssembly.Instance(__forWasmModule, {
                env: {
                    test: () => {
                        return line < this.originalHeightMultipliedByChannels ? 1 : 0;
                    },
                    update: () => {
                    },
                    body: () => {
                        {
                            output[line++] = 0;
                            output[line++] = 0;
                            output[line++] = 0;
                        }
                    }
                }
            });
            const __exports = __forInstance18.exports;
            return __exports.data();
        })();
        weight = ratioWeight;
        do {
            amountToNext = 1 + actualPosition - currentPosition;
            if (weight >= amountToNext) {
                (() => {
                    line = 0, pixelOffset = actualPosition;
                    const __forInstance19 = new WebAssembly.Instance(__forWasmModule, {
                        env: {
                            test: () => {
                                return line < this.originalHeightMultipliedByChannels ? 1 : 0;
                            },
                            update: () => {
                                pixelOffset += nextLineOffsetOriginalWidth;
                            },
                            body: () => {
                                {
                                    output[line++] += buffer[pixelOffset++] * amountToNext;
                                    output[line++] += buffer[pixelOffset++] * amountToNext;
                                    output[line++] += buffer[pixelOffset] * amountToNext;
                                }
                            }
                        }
                    });
                    const __exports = __forInstance19.exports;
                    return __exports.data();
                })();
                currentPosition = actualPosition = actualPosition + 3;
                weight -= amountToNext;
            } else {
                (() => {
                    line = 0, pixelOffset = actualPosition;
                    const __forInstance20 = new WebAssembly.Instance(__forWasmModule, {
                        env: {
                            test: () => {
                                return line < this.originalHeightMultipliedByChannels ? 1 : 0;
                            },
                            update: () => {
                                pixelOffset += nextLineOffsetOriginalWidth;
                            },
                            body: () => {
                                {
                                    output[line++] += buffer[pixelOffset++] * weight;
                                    output[line++] += buffer[pixelOffset++] * weight;
                                    output[line++] += buffer[pixelOffset] * weight;
                                }
                            }
                        }
                    });
                    const __exports = __forInstance20.exports;
                    return __exports.data();
                })();
                currentPosition += weight;
                break;
            }
        } while (weight > 0 && actualPosition < this.originalWidthMultipliedByChannels);
        (() => {
            line = 0, pixelOffset = outputOffset;
            const __forInstance21 = new WebAssembly.Instance(__forWasmModule, {
                env: {
                    test: () => {
                        return line < this.originalHeightMultipliedByChannels ? 1 : 0;
                    },
                    update: () => {
                        pixelOffset += nextLineOffsetTargetWidth;
                    },
                    body: () => {
                        {
                            outputBuffer[pixelOffset++] = output[line++] / ratioWeight;
                            outputBuffer[pixelOffset++] = output[line++] / ratioWeight;
                            outputBuffer[pixelOffset] = output[line++] / ratioWeight;
                        }
                    }
                }
            });
            const __exports = __forInstance21.exports;
            return __exports.data();
        })();
        outputOffset += 3;
    } while (outputOffset < this.targetWidthMultipliedByChannels);
    return outputBuffer;
};
Resize.prototype.resizeWidthInterpolatedRGB = function (buffer) {
    var ratioWeight = (this.widthOriginal - 1) / this.targetWidth;
    var weight = 0;
    var finalOffset = 0;
    var pixelOffset = 0;
    var outputBuffer = this.widthBuffer;
    (() => {
        var targetPosition = 0;
        const __forInstance22 = new WebAssembly.Instance(__forWasmModule, {
            env: {
                test: () => {
                    return targetPosition < this.targetWidthMultipliedByChannels ? 1 : 0;
                },
                update: () => {
                    targetPosition += 3, weight += ratioWeight;
                },
                body: () => {
                    {
                        secondWeight = weight % 1;
                        firstWeight = 1 - secondWeight;
                        (() => {
                            finalOffset = targetPosition, pixelOffset = Math.floor(weight) * 3;
                            const __forInstance23 = new WebAssembly.Instance(__forWasmModule, {
                                env: {
                                    test: () => {
                                        return finalOffset < this.widthPassResultSize ? 1 : 0;
                                    },
                                    update: () => {
                                        pixelOffset += this.originalWidthMultipliedByChannels, finalOffset += this.targetWidthMultipliedByChannels;
                                    },
                                    body: () => {
                                        {
                                            outputBuffer[finalOffset] = buffer[pixelOffset] * firstWeight + buffer[pixelOffset + 3] * secondWeight;
                                            outputBuffer[finalOffset + 1] = buffer[pixelOffset + 1] * firstWeight + buffer[pixelOffset + 4] * secondWeight;
                                            outputBuffer[finalOffset + 2] = buffer[pixelOffset + 2] * firstWeight + buffer[pixelOffset + 5] * secondWeight;
                                        }
                                    }
                                }
                            });
                            const __exports = __forInstance23.exports;
                            return __exports.data();
                        })();
                    }
                }
            }
        });
        const __exports = __forInstance22.exports;
        return __exports.data();
    })();
    return outputBuffer;
};
Resize.prototype.resizeWidthRGBA = function (buffer) {
    var ratioWeight = this.ratioWeightWidthPass;
    var weight = 0;
    var amountToNext = 0;
    var actualPosition = 0;
    var currentPosition = 0;
    var line = 0;
    var pixelOffset = 0;
    var outputOffset = 0;
    var nextLineOffsetOriginalWidth = this.originalWidthMultipliedByChannels - 3;
    var nextLineOffsetTargetWidth = this.targetWidthMultipliedByChannels - 3;
    var output = this.outputWidthWorkBench;
    var outputBuffer = this.widthBuffer;
    do {
        (() => {
            line = 0;
            const __forInstance24 = new WebAssembly.Instance(__forWasmModule, {
                env: {
                    test: () => {
                        return line < this.originalHeightMultipliedByChannels ? 1 : 0;
                    },
                    update: () => {
                    },
                    body: () => {
                        {
                            output[line++] = 0;
                            output[line++] = 0;
                            output[line++] = 0;
                            output[line++] = 0;
                        }
                    }
                }
            });
            const __exports = __forInstance24.exports;
            return __exports.data();
        })();
        weight = ratioWeight;
        do {
            amountToNext = 1 + actualPosition - currentPosition;
            if (weight >= amountToNext) {
                (() => {
                    line = 0, pixelOffset = actualPosition;
                    const __forInstance25 = new WebAssembly.Instance(__forWasmModule, {
                        env: {
                            test: () => {
                                return line < this.originalHeightMultipliedByChannels ? 1 : 0;
                            },
                            update: () => {
                                pixelOffset += nextLineOffsetOriginalWidth;
                            },
                            body: () => {
                                {
                                    output[line++] += buffer[pixelOffset++] * amountToNext;
                                    output[line++] += buffer[pixelOffset++] * amountToNext;
                                    output[line++] += buffer[pixelOffset++] * amountToNext;
                                    output[line++] += buffer[pixelOffset] * amountToNext;
                                }
                            }
                        }
                    });
                    const __exports = __forInstance25.exports;
                    return __exports.data();
                })();
                currentPosition = actualPosition = actualPosition + 4;
                weight -= amountToNext;
            } else {
                (() => {
                    line = 0, pixelOffset = actualPosition;
                    const __forInstance26 = new WebAssembly.Instance(__forWasmModule, {
                        env: {
                            test: () => {
                                return line < this.originalHeightMultipliedByChannels ? 1 : 0;
                            },
                            update: () => {
                                pixelOffset += nextLineOffsetOriginalWidth;
                            },
                            body: () => {
                                {
                                    output[line++] += buffer[pixelOffset++] * weight;
                                    output[line++] += buffer[pixelOffset++] * weight;
                                    output[line++] += buffer[pixelOffset++] * weight;
                                    output[line++] += buffer[pixelOffset] * weight;
                                }
                            }
                        }
                    });
                    const __exports = __forInstance26.exports;
                    return __exports.data();
                })();
                currentPosition += weight;
                break;
            }
        } while (weight > 0 && actualPosition < this.originalWidthMultipliedByChannels);
        (() => {
            line = 0, pixelOffset = outputOffset;
            const __forInstance27 = new WebAssembly.Instance(__forWasmModule, {
                env: {
                    test: () => {
                        return line < this.originalHeightMultipliedByChannels ? 1 : 0;
                    },
                    update: () => {
                        pixelOffset += nextLineOffsetTargetWidth;
                    },
                    body: () => {
                        {
                            outputBuffer[pixelOffset++] = output[line++] / ratioWeight;
                            outputBuffer[pixelOffset++] = output[line++] / ratioWeight;
                            outputBuffer[pixelOffset++] = output[line++] / ratioWeight;
                            outputBuffer[pixelOffset] = output[line++] / ratioWeight;
                        }
                    }
                }
            });
            const __exports = __forInstance27.exports;
            return __exports.data();
        })();
        outputOffset += 4;
    } while (outputOffset < this.targetWidthMultipliedByChannels);
    return outputBuffer;
};
Resize.prototype.resizeWidthInterpolatedRGBA = function (buffer) {
    var ratioWeight = (this.widthOriginal - 1) / this.targetWidth;
    var weight = 0;
    var finalOffset = 0;
    var pixelOffset = 0;
    var outputBuffer = this.widthBuffer;
    (() => {
        var targetPosition = 0;
        const __forInstance28 = new WebAssembly.Instance(__forWasmModule, {
            env: {
                test: () => {
                    return targetPosition < this.targetWidthMultipliedByChannels ? 1 : 0;
                },
                update: () => {
                    targetPosition += 4, weight += ratioWeight;
                },
                body: () => {
                    {
                        secondWeight = weight % 1;
                        firstWeight = 1 - secondWeight;
                        (() => {
                            finalOffset = targetPosition, pixelOffset = Math.floor(weight) * 4;
                            const __forInstance29 = new WebAssembly.Instance(__forWasmModule, {
                                env: {
                                    test: () => {
                                        return finalOffset < this.widthPassResultSize ? 1 : 0;
                                    },
                                    update: () => {
                                        pixelOffset += this.originalWidthMultipliedByChannels, finalOffset += this.targetWidthMultipliedByChannels;
                                    },
                                    body: () => {
                                        {
                                            outputBuffer[finalOffset] = buffer[pixelOffset] * firstWeight + buffer[pixelOffset + 4] * secondWeight;
                                            outputBuffer[finalOffset + 1] = buffer[pixelOffset + 1] * firstWeight + buffer[pixelOffset + 5] * secondWeight;
                                            outputBuffer[finalOffset + 2] = buffer[pixelOffset + 2] * firstWeight + buffer[pixelOffset + 6] * secondWeight;
                                            outputBuffer[finalOffset + 3] = buffer[pixelOffset + 3] * firstWeight + buffer[pixelOffset + 7] * secondWeight;
                                        }
                                    }
                                }
                            });
                            const __exports = __forInstance29.exports;
                            return __exports.data();
                        })();
                    }
                }
            }
        });
        const __exports = __forInstance28.exports;
        return __exports.data();
    })();
    return outputBuffer;
};
Resize.prototype.resizeHeightRGB = function (buffer) {
    var ratioWeight = this.ratioWeightHeightPass;
    var weight = 0;
    var amountToNext = 0;
    var actualPosition = 0;
    var currentPosition = 0;
    var pixelOffset = 0;
    var outputOffset = 0;
    var output = this.outputHeightWorkBench;
    var outputBuffer = this.heightBuffer;
    do {
        (() => {
            pixelOffset = 0;
            const __forInstance30 = new WebAssembly.Instance(__forWasmModule, {
                env: {
                    test: () => {
                        return pixelOffset < this.targetWidthMultipliedByChannels ? 1 : 0;
                    },
                    update: () => {
                    },
                    body: () => {
                        {
                            output[pixelOffset++] = 0;
                            output[pixelOffset++] = 0;
                            output[pixelOffset++] = 0;
                        }
                    }
                }
            });
            const __exports = __forInstance30.exports;
            return __exports.data();
        })();
        weight = ratioWeight;
        do {
            amountToNext = 1 + actualPosition - currentPosition;
            if (weight >= amountToNext) {
                (() => {
                    pixelOffset = 0;
                    const __forInstance31 = new WebAssembly.Instance(__forWasmModule, {
                        env: {
                            test: () => {
                                return pixelOffset < this.targetWidthMultipliedByChannels ? 1 : 0;
                            },
                            update: () => {
                            },
                            body: () => {
                                {
                                    output[pixelOffset++] += buffer[actualPosition++] * amountToNext;
                                    output[pixelOffset++] += buffer[actualPosition++] * amountToNext;
                                    output[pixelOffset++] += buffer[actualPosition++] * amountToNext;
                                }
                            }
                        }
                    });
                    const __exports = __forInstance31.exports;
                    return __exports.data();
                })();
                currentPosition = actualPosition;
                weight -= amountToNext;
            } else {
                (() => {
                    pixelOffset = 0, amountToNext = actualPosition;
                    const __forInstance32 = new WebAssembly.Instance(__forWasmModule, {
                        env: {
                            test: () => {
                                return pixelOffset < this.targetWidthMultipliedByChannels ? 1 : 0;
                            },
                            update: () => {
                            },
                            body: () => {
                                {
                                    output[pixelOffset++] += buffer[amountToNext++] * weight;
                                    output[pixelOffset++] += buffer[amountToNext++] * weight;
                                    output[pixelOffset++] += buffer[amountToNext++] * weight;
                                }
                            }
                        }
                    });
                    const __exports = __forInstance32.exports;
                    return __exports.data();
                })();
                currentPosition += weight;
                break;
            }
        } while (weight > 0 && actualPosition < this.widthPassResultSize);
        (() => {
            pixelOffset = 0;
            const __forInstance33 = new WebAssembly.Instance(__forWasmModule, {
                env: {
                    test: () => {
                        return pixelOffset < this.targetWidthMultipliedByChannels ? 1 : 0;
                    },
                    update: () => {
                    },
                    body: () => {
                        {
                            outputBuffer[outputOffset++] = Math.round(output[pixelOffset++] / ratioWeight);
                            outputBuffer[outputOffset++] = Math.round(output[pixelOffset++] / ratioWeight);
                            outputBuffer[outputOffset++] = Math.round(output[pixelOffset++] / ratioWeight);
                        }
                    }
                }
            });
            const __exports = __forInstance33.exports;
            return __exports.data();
        })();
    } while (outputOffset < this.finalResultSize);
    return outputBuffer;
};
Resize.prototype.resizeHeightInterpolated = function (buffer) {
    var ratioWeight = (this.heightOriginal - 1) / this.targetHeight;
    var weight = 0;
    var finalOffset = 0;
    var pixelOffset = 0;
    var pixelOffsetAccumulated = 0;
    var pixelOffsetAccumulated2 = 0;
    var outputBuffer = this.heightBuffer;
    do {
        secondWeight = weight % 1;
        firstWeight = 1 - secondWeight;
        pixelOffsetAccumulated = Math.floor(weight) * this.targetWidthMultipliedByChannels;
        pixelOffsetAccumulated2 = pixelOffsetAccumulated + this.targetWidthMultipliedByChannels;
        (() => {
            pixelOffset = 0;
            const __forInstance34 = new WebAssembly.Instance(__forWasmModule, {
                env: {
                    test: () => {
                        return pixelOffset < this.targetWidthMultipliedByChannels ? 1 : 0;
                    },
                    update: () => {
                        ++pixelOffset;
                    },
                    body: () => {
                        {
                            outputBuffer[finalOffset++] = buffer[pixelOffsetAccumulated + pixelOffset] * firstWeight + buffer[pixelOffsetAccumulated2 + pixelOffset] * secondWeight;
                        }
                    }
                }
            });
            const __exports = __forInstance34.exports;
            return __exports.data();
        })();
        weight += ratioWeight;
    } while (finalOffset < this.finalResultSize);
    return outputBuffer;
};
Resize.prototype.resizeHeightRGBA = function (buffer) {
    var ratioWeight = this.ratioWeightHeightPass;
    var weight = 0;
    var amountToNext = 0;
    var actualPosition = 0;
    var currentPosition = 0;
    var pixelOffset = 0;
    var outputOffset = 0;
    var output = this.outputHeightWorkBench;
    var outputBuffer = this.heightBuffer;
    do {
        (() => {
            pixelOffset = 0;
            const __forInstance35 = new WebAssembly.Instance(__forWasmModule, {
                env: {
                    test: () => {
                        return pixelOffset < this.targetWidthMultipliedByChannels ? 1 : 0;
                    },
                    update: () => {
                    },
                    body: () => {
                        {
                            output[pixelOffset++] = 0;
                            output[pixelOffset++] = 0;
                            output[pixelOffset++] = 0;
                            output[pixelOffset++] = 0;
                        }
                    }
                }
            });
            const __exports = __forInstance35.exports;
            return __exports.data();
        })();
        weight = ratioWeight;
        do {
            amountToNext = 1 + actualPosition - currentPosition;
            if (weight >= amountToNext) {
                (() => {
                    pixelOffset = 0;
                    const __forInstance36 = new WebAssembly.Instance(__forWasmModule, {
                        env: {
                            test: () => {
                                return pixelOffset < this.targetWidthMultipliedByChannels ? 1 : 0;
                            },
                            update: () => {
                            },
                            body: () => {
                                {
                                    output[pixelOffset++] += buffer[actualPosition++] * amountToNext;
                                    output[pixelOffset++] += buffer[actualPosition++] * amountToNext;
                                    output[pixelOffset++] += buffer[actualPosition++] * amountToNext;
                                    output[pixelOffset++] += buffer[actualPosition++] * amountToNext;
                                }
                            }
                        }
                    });
                    const __exports = __forInstance36.exports;
                    return __exports.data();
                })();
                currentPosition = actualPosition;
                weight -= amountToNext;
            } else {
                (() => {
                    pixelOffset = 0, amountToNext = actualPosition;
                    const __forInstance37 = new WebAssembly.Instance(__forWasmModule, {
                        env: {
                            test: () => {
                                return pixelOffset < this.targetWidthMultipliedByChannels ? 1 : 0;
                            },
                            update: () => {
                            },
                            body: () => {
                                {
                                    output[pixelOffset++] += buffer[amountToNext++] * weight;
                                    output[pixelOffset++] += buffer[amountToNext++] * weight;
                                    output[pixelOffset++] += buffer[amountToNext++] * weight;
                                    output[pixelOffset++] += buffer[amountToNext++] * weight;
                                }
                            }
                        }
                    });
                    const __exports = __forInstance37.exports;
                    return __exports.data();
                })();
                currentPosition += weight;
                break;
            }
        } while (weight > 0 && actualPosition < this.widthPassResultSize);
        (() => {
            pixelOffset = 0;
            const __forInstance38 = new WebAssembly.Instance(__forWasmModule, {
                env: {
                    test: () => {
                        return pixelOffset < this.targetWidthMultipliedByChannels ? 1 : 0;
                    },
                    update: () => {
                    },
                    body: () => {
                        {
                            outputBuffer[outputOffset++] = Math.round(output[pixelOffset++] / ratioWeight);
                            outputBuffer[outputOffset++] = Math.round(output[pixelOffset++] / ratioWeight);
                            outputBuffer[outputOffset++] = Math.round(output[pixelOffset++] / ratioWeight);
                            outputBuffer[outputOffset++] = Math.round(output[pixelOffset++] / ratioWeight);
                        }
                    }
                }
            });
            const __exports = __forInstance38.exports;
            return __exports.data();
        })();
    } while (outputOffset < this.finalResultSize);
    return outputBuffer;
};
Resize.prototype.resizeHeightInterpolatedRGBA = function (buffer) {
    var ratioWeight = (this.heightOriginal - 1) / this.targetHeight;
    var weight = 0;
    var finalOffset = 0;
    var pixelOffset = 0;
    var outputBuffer = this.heightBuffer;
    (() => {
        const __forInstance48 = new WebAssembly.Instance(__forWasmModule, {
            env: {
                test: () => {
                    return pixelOffset < this.finalResultSize ? 1 : 0;
                },
                update: () => {
                },
                body: () => {
                    {
                        secondWeight = weight % 1;
                        firstWeight = 1 - secondWeight;
                        (() => {
                            pixelOffset = Math.floor(weight) * 4;
                            const __forInstance39 = new WebAssembly.Instance(__forWasmModule, {
                                env: {
                                    test: () => {
                                        return pixelOffset < this.targetWidthMultipliedByChannels ? 1 : 0;
                                    },
                                    update: () => {
                                        pixelOffset += 4;
                                    },
                                    body: () => {
                                        {
                                            outputBuffer[finalOffset++] = buffer[pixelOffset] * firstWeight + buffer[pixelOffset + 4] * secondWeight;
                                            outputBuffer[finalOffset++] = buffer[pixelOffset + 1] * firstWeight + buffer[pixelOffset + 5] * secondWeight;
                                            outputBuffer[finalOffset++] = buffer[pixelOffset + 2] * firstWeight + buffer[pixelOffset + 6] * secondWeight;
                                            outputBuffer[finalOffset++] = buffer[pixelOffset + 3] * firstWeight + buffer[pixelOffset + 7] * secondWeight;
                                        }
                                    }
                                }
                            });
                            const __exports = __forInstance39.exports;
                            return __exports.data();
                        })();
                        weight += ratioWeight;
                    }
                }
            }
        });
        const __exports = __forInstance48.exports;
        return __exports.data();
    })();
    return outputBuffer;
};
Resize.prototype.resize = function (buffer) {
    return this.resizeHeight(this.resizeWidth(buffer));
};
Resize.prototype.bypassResizer = function (buffer) {
    return buffer;
};
Resize.prototype.initializeFirstPassBuffers = function (BILINEARAlgo) {
    this.widthBuffer = this.generateFloatBuffer(this.widthPassResultSize);
    (() => {
        const __ifInstance49 = new WebAssembly.Instance(__ifWasmModule, {
            env: {
                impFunc1: () => {
                    {
                        this.outputWidthWorkBench = this.generateFloatBuffer(this.originalHeightMultipliedByChannels);
                    }
                },
                impFunc2: () => {
                }
            }
        });
        const __exports = __ifInstance49.exports;
        return __exports.data(!BILINEARAlgo ? 1 : 0);
    })();
};
Resize.prototype.initializeSecondPassBuffers = function (BILINEARAlgo) {
    this.heightBuffer = this.generateUint8Buffer(this.finalResultSize);
    (() => {
        const __ifInstance50 = new WebAssembly.Instance(__ifWasmModule, {
            env: {
                impFunc1: () => {
                    {
                        this.outputHeightWorkBench = this.generateFloatBuffer(this.targetWidthMultipliedByChannels);
                    }
                },
                impFunc2: () => {
                }
            }
        });
        const __exports = __ifInstance50.exports;
        return __exports.data(!BILINEARAlgo ? 1 : 0);
    })();
};
Resize.prototype.generateFloatBuffer = function (bufferLength) {
    try {
        return new Float32Array(bufferLength);
    } catch (error) {
        return [];
    }
};
Resize.prototype.generateUint8Buffer = function (bufferLength) {
    try {
        return this.checkForOperaMathBug(new Uint8Array(bufferLength));
    } catch (error) {
        return [];
    }
};
Resize.prototype.checkForOperaMathBug = function (typedArray) {
    typedArray[0] = -1;
    typedArray[0] >>= 0;
    if (typedArray[0] != 255) {
        return [];
    } else {
        return typedArray;
    }
};