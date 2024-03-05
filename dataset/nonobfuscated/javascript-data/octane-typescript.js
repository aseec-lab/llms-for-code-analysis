
var typescript = new BenchmarkSuite('Typescript', [255011322], [
  new Benchmark("Typescript",
                false,
                true,
                5,
                runTypescript,
                setupTypescript,
                tearDownTypescript,
                null,
                1)
]);


function setupTypescript() {
}


function tearDownTypescript() {
  compiler_input = null;
}


var parseErrors = [];


function runTypescript() {
  var compiler = createCompiler();
  compiler.addUnit(compiler_input, "compiler_input.ts");
  parseErrors = [];
  compiler.reTypeCheck();
  compiler.emit({
           createFile: function (fileName) { return outfile; },
           fileExists: function (path) { return false; },
           directoryExists: function (path) { return false; },
           resolvePath: function (path) { return path; }
  });
  
  if (parseErrors.length != 192 && parseErrors.length != 193) {
    throw new Error("Parse errors.");
  }
  compiler = null;
}

var outfile = {
  checksum: -412589664, 
  cumulative_checksum: 0,
  Write: function (s) { this.Verify(s); },
  WriteLine: function (s) { this.Verify(s + "\n"); },
  Close: function () {
    if (this.checksum != this.cumulative_checksum) {
      throw new Error("Wrong checksum.");
    }
    this.cumulative_checksum = 0;
  },
  Verify: function (s) {
    for(var i = 0; i < s.length; i++) {
      var c = s.charCodeAt(i);
      this.cumulative_checksum = (this.cumulative_checksum << 1) ^ c;
    }
  }
};


var outerr = {
  checksum: 0,
  cumulative_checksum: 0,
  Write: function (s) { this.Verify(s); },
  WriteLine: function (s) { this.Verify(s + "\n"); },
  Close: function () {
    if (this.checksum != this.cumulative_checksum) {
      throw new Error("Wrong checksum.");
    }
    this.cumulative_checksum = 0;
  },
  Verify: function (s) {
    for(var i = 0; i < s.length; i++) {
      var c = s.charCodeAt(i);
      this.cumulative_checksum = (this.cumulative_checksum << 1) ^ c;
    }
  }
};


function createCompiler() {
  var settings = new TypeScript.CompilationSettings();
  settings.codeGenTarget = TypeScript.CodeGenTarget.ES5;
  var compiler = new TypeScript.TypeScriptCompiler(
      outerr, new TypeScript.NullLogger, settings);
  compiler.setErrorCallback(function (start, len, message) { 
    parseErrors.push({ start: start, len: len, message: message }); 
  });
  compiler.parser.errorRecovery = true;
  compiler.typeCheck();
  return compiler
}



