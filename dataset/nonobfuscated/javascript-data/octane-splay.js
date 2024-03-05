

var Splay = new BenchmarkSuite('Splay', [81491, 2739514], [
  new Benchmark("Splay", true, false, 1400,
    SplayRun, SplaySetup, SplayTearDown, SplayRMS)
]);


var kSplayTreeSize = 8000;
var kSplayTreeModifications = 80;
var kSplayTreePayloadDepth = 5;

var splayTree = null;
var splaySampleTimeStart = 0.0;

function GeneratePayloadTree(depth, tag) {
  if (depth == 0) {
    return {
      array  : [ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 ],
      string : 'String for key ' + tag + ' in leaf node'
    };
  } else {
    return {
      left:  GeneratePayloadTree(depth - 1, tag),
      right: GeneratePayloadTree(depth - 1, tag)
    };
  }
}


function GenerateKey() {
      return Math.random();
}

var splaySamples = 0;
var splaySumOfSquaredPauses = 0;

function SplayRMS() {
  return Math.round(Math.sqrt(splaySumOfSquaredPauses / splaySamples) * 10000);
}

function SplayUpdateStats(time) {
  var pause = time - splaySampleTimeStart;
  splaySampleTimeStart = time;
  splaySamples++;
  splaySumOfSquaredPauses += pause * pause;
}

function InsertNewNode() {
    var key;
  do {
    key = GenerateKey();
  } while (splayTree.find(key) != null);
  var payload = GeneratePayloadTree(kSplayTreePayloadDepth, String(key));
  splayTree.insert(key, payload);
  return key;
}


function SplaySetup() {
      if (!performance.now) {
    throw "PerformanceNowUnsupported";
  }

  splayTree = new SplayTree();
  splaySampleTimeStart = performance.now()
  for (var i = 0; i < kSplayTreeSize; i++) {
    InsertNewNode();
    if ((i+1) % 20 == 19) {
      SplayUpdateStats(performance.now());
    }
  }
}


function SplayTearDown() {
        var keys = splayTree.exportKeys();
  splayTree = null;

  splaySamples = 0;
  splaySumOfSquaredPauses = 0;

    var length = keys.length;
  if (length != kSplayTreeSize) {
    throw new Error("Splay tree has wrong size");
  }

    for (var i = 0; i < length - 1; i++) {
    if (keys[i] >= keys[i + 1]) {
      throw new Error("Splay tree not sorted");
    }
  }
}


function SplayRun() {
    for (var i = 0; i < kSplayTreeModifications; i++) {
    var key = InsertNewNode();
    var greatest = splayTree.findGreatestLessThan(key);
    if (greatest == null) splayTree.remove(key);
    else splayTree.remove(greatest.key);
  }
  SplayUpdateStats(performance.now());
}



function SplayTree() {
};



SplayTree.prototype.root_ = null;



SplayTree.prototype.isEmpty = function() {
  return !this.root_;
};



SplayTree.prototype.insert = function(key, value) {
  if (this.isEmpty()) {
    this.root_ = new SplayTree.Node(key, value);
    return;
  }
      this.splay_(key);
  if (this.root_.key == key) {
    return;
  }
  var node = new SplayTree.Node(key, value);
  if (key > this.root_.key) {
    node.left = this.root_;
    node.right = this.root_.right;
    this.root_.right = null;
  } else {
    node.right = this.root_;
    node.left = this.root_.left;
    this.root_.left = null;
  }
  this.root_ = node;
};



SplayTree.prototype.remove = function(key) {
  if (this.isEmpty()) {
    throw Error('Key not found: ' + key);
  }
  this.splay_(key);
  if (this.root_.key != key) {
    throw Error('Key not found: ' + key);
  }
  var removed = this.root_;
  if (!this.root_.left) {
    this.root_ = this.root_.right;
  } else {
    var right = this.root_.right;
    this.root_ = this.root_.left;
        this.splay_(key);
            this.root_.right = right;
  }
  return removed;
};



SplayTree.prototype.find = function(key) {
  if (this.isEmpty()) {
    return null;
  }
  this.splay_(key);
  return this.root_.key == key ? this.root_ : null;
};



SplayTree.prototype.findMax = function(opt_startNode) {
  if (this.isEmpty()) {
    return null;
  }
  var current = opt_startNode || this.root_;
  while (current.right) {
    current = current.right;
  }
  return current;
};



SplayTree.prototype.findGreatestLessThan = function(key) {
  if (this.isEmpty()) {
    return null;
  }
      this.splay_(key);
      if (this.root_.key < key) {
    return this.root_;
  } else if (this.root_.left) {
    return this.findMax(this.root_.left);
  } else {
    return null;
  }
};



SplayTree.prototype.exportKeys = function() {
  var result = [];
  if (!this.isEmpty()) {
    this.root_.traverse_(function(node) { result.push(node.key); });
  }
  return result;
};



SplayTree.prototype.splay_ = function(key) {
  if (this.isEmpty()) {
    return;
  }
            var dummy, left, right;
  dummy = left = right = new SplayTree.Node(null, null);
  var current = this.root_;
  while (true) {
    if (key < current.key) {
      if (!current.left) {
        break;
      }
      if (key < current.left.key) {
                var tmp = current.left;
        current.left = tmp.right;
        tmp.right = current;
        current = tmp;
        if (!current.left) {
          break;
        }
      }
            right.left = current;
      right = current;
      current = current.left;
    } else if (key > current.key) {
      if (!current.right) {
        break;
      }
      if (key > current.right.key) {
                var tmp = current.right;
        current.right = tmp.left;
        tmp.left = current;
        current = tmp;
        if (!current.right) {
          break;
        }
      }
            left.right = current;
      left = current;
      current = current.right;
    } else {
      break;
    }
  }
    left.right = current.left;
  right.left = current.right;
  current.left = dummy.right;
  current.right = dummy.left;
  this.root_ = current;
};



SplayTree.Node = function(key, value) {
  this.key = key;
  this.value = value;
};



SplayTree.Node.prototype.left = null;



SplayTree.Node.prototype.right = null;



SplayTree.Node.prototype.traverse_ = function(f) {
  var current = this;
  while (current) {
    var left = current.left;
    if (left) left.traverse_(f);
    f(current);
    current = current.right;
  }
};
