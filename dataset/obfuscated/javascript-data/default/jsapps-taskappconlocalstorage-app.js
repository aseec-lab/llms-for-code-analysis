function a0_0x4189(){const _0x3b090c=['10TPmvfJ','addEventListener','\x27)\x22>delete</a>\x0a\x09\x09\x09</div>\x0a\x09\x09</div>','11ZXLayH','6366564qINQlf','preventDefault','log','11190240jDKokg','#formTask','6SbrpZj','querySelector','tasks','innerHTML','4147392PHuUcV','10219349XlwRkJ','#tasks','setItem','7oqPKuY','push','2101617tPYjqX','301295gEGFoj','stringify','#description','title','insertAdjacentHTML','value','length','parse','#title','splice','getItem','53774AzGkWo','36BUCsfv','afterbegin','reset'];a0_0x4189=function(){return _0x3b090c;};return a0_0x4189();}const a0_0x3c753d=a0_0x551a;(function(_0x537957,_0x26c897){const _0x25379a=a0_0x551a,_0x30fd63=_0x537957();while(!![]){try{const _0x156feb=-parseInt(_0x25379a(0x116))/0x1*(parseInt(_0x25379a(0x124))/0x2)+parseInt(_0x25379a(0x112))/0x3+parseInt(_0x25379a(0x125))/0x4*(parseInt(_0x25379a(0x119))/0x5)+-parseInt(_0x25379a(0x131))/0x6*(parseInt(_0x25379a(0x113))/0x7)+parseInt(_0x25379a(0x12f))/0x8+parseInt(_0x25379a(0x118))/0x9*(-parseInt(_0x25379a(0x128))/0xa)+-parseInt(_0x25379a(0x12b))/0xb*(parseInt(_0x25379a(0x12c))/0xc);if(_0x156feb===_0x26c897)break;else _0x30fd63['push'](_0x30fd63['shift']());}catch(_0x4072a4){_0x30fd63['push'](_0x30fd63['shift']());}}}(a0_0x4189,0xde827));let formTask=document['querySelector'](a0_0x3c753d(0x130));formTask[a0_0x3c753d(0x129)]('submit',saveTask);function saveTask(_0x1bf58a){const _0x39b763=a0_0x3c753d;_0x1bf58a[_0x39b763(0x12d)]();let _0x3809a1=document['querySelector'](_0x39b763(0x121))[_0x39b763(0x11e)],_0x215d20=document[_0x39b763(0x132)](_0x39b763(0x11b))[_0x39b763(0x11e)];if(_0x3809a1=='')return inputEmpty(),![];const _0x470dc0={'title':_0x3809a1,'description':_0x215d20};if(localStorage[_0x39b763(0x123)](_0x39b763(0x133))===null){let _0x358fe4=[];_0x358fe4[_0x39b763(0x117)](_0x470dc0),localStorage[_0x39b763(0x115)](_0x39b763(0x133),JSON[_0x39b763(0x11a)](_0x358fe4));}else{let _0x1e9c1b=JSON[_0x39b763(0x120)](localStorage[_0x39b763(0x123)](_0x39b763(0x133)));_0x1e9c1b[_0x39b763(0x117)](_0x470dc0),localStorage[_0x39b763(0x115)](_0x39b763(0x133),JSON['stringify'](_0x1e9c1b));}getTask(),formTask[_0x39b763(0x127)]();}function inputEmpty(){const _0x1a9084=a0_0x3c753d;console[_0x1a9084(0x12e)]('empty');}function a0_0x551a(_0x106d93,_0x11d072){const _0x418934=a0_0x4189();return a0_0x551a=function(_0x551a94,_0x5e4d92){_0x551a94=_0x551a94-0x112;let _0x4933c8=_0x418934[_0x551a94];return _0x4933c8;},a0_0x551a(_0x106d93,_0x11d072);}function getTask(){const _0x5b70d5=a0_0x3c753d;let _0x12da5d=JSON['parse'](localStorage[_0x5b70d5(0x123)]('tasks')),_0x47d254=document['querySelector'](_0x5b70d5(0x114));_0x47d254[_0x5b70d5(0x134)]='';for(let _0x22b2b9=0x0;_0x22b2b9<_0x12da5d[_0x5b70d5(0x11f)];_0x22b2b9++){let _0x4f307b=_0x12da5d[_0x22b2b9][_0x5b70d5(0x11c)],_0x55982d=_0x12da5d[_0x22b2b9]['description'];listItem='\x0a\x09\x09<div\x20class=\x22card\x20my-3\x22>\x0a\x09\x09\x09<div\x20class=\x22card-body\x22>\x0a\x09\x09\x09\x09<h4>'+_0x4f307b+'</h4>\x0a\x09\x09\x09\x09<p>'+_0x55982d+'</p>\x0a\x09\x09\x09\x09<a\x20class=\x22btn\x20btn-danger\x22\x20onClick=\x22deleteTask(\x27'+_0x4f307b+_0x5b70d5(0x12a),_0x47d254[_0x5b70d5(0x11d)](_0x5b70d5(0x126),listItem);}}function deleteTask(_0x3ceee8){const _0x240eed=a0_0x3c753d;let _0x23c176=JSON[_0x240eed(0x120)](localStorage[_0x240eed(0x123)](_0x240eed(0x133)));for(let _0x5d4a44=0x0;_0x5d4a44<_0x23c176[_0x240eed(0x11f)];_0x5d4a44++){_0x23c176[_0x5d4a44][_0x240eed(0x11c)]==_0x3ceee8&&_0x23c176[_0x240eed(0x122)](_0x5d4a44,0x1);}localStorage[_0x240eed(0x115)](_0x240eed(0x133),JSON[_0x240eed(0x11a)](_0x23c176)),getTask();}getTask();