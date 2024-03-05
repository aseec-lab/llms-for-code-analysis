

var base_dir = '';
load(base_dir + 'base.js');
load(base_dir + 'richards.js');
load(base_dir + 'deltablue.js');
load(base_dir + 'crypto.js');
load(base_dir + 'raytrace.js');
load(base_dir + 'earley-boyer.js');
load(base_dir + 'regexp.js');
load(base_dir + 'splay.js');
load(base_dir + 'navier-stokes.js');
load(base_dir + 'pdfjs.js');
load(base_dir + 'mandreel.js');
load(base_dir + 'gbemu-part1.js');
load(base_dir + 'gbemu-part2.js');
load(base_dir + 'code-load.js');
load(base_dir + 'box2d.js');
load(base_dir + 'zlib.js');
load(base_dir + 'zlib-data.js');
load(base_dir + 'typescript.js');
load(base_dir + 'typescript-input.js');
load(base_dir + 'typescript-compiler.js');

var success = true;

function PrintResult(name, result) {
  print(name + ': ' + result);
}


function PrintError(name, error) {
  PrintResult(name, error);
  success = false;
}


function PrintScore(score) {
  if (success) {
    print('----');
    print('Score (version ' + BenchmarkSuite.version + '): ' + score);
  }
}


BenchmarkSuite.config.doWarmup = undefined;
BenchmarkSuite.config.doDeterministic = undefined;

BenchmarkSuite.RunSuites({ NotifyResult: PrintResult,
                           NotifyError: PrintError,
                           NotifyScore: PrintScore });
