function a0_0x545c(_0x3e7b20,_0x1e3bb5){const _0x342438=a0_0x3424();return a0_0x545c=function(_0x545c34,_0x1ed931){_0x545c34=_0x545c34-0x79;let _0xc9ad33=_0x342438[_0x545c34];return _0xc9ad33;},a0_0x545c(_0x3e7b20,_0x1e3bb5);}const a0_0x227617=a0_0x545c;function a0_0x3424(){const _0x54c9c1=['complete','target','hMMnI','6ZppWkY','click','5757960npgPLb','3185371rFmujq','appendChild','setItem','newItem','length','itemEntryForm','input','parse','label','getId','lastElementChild','htmlFor','getList','item','myToDoList','Aychv','getItem','div','IDcMB','JlROc','7247925NQiOEC','listItems','Cegry','tabIndex','setId','removeItemFromList','_id','YMqvC','8cBoPTR','6376540rvwczM','clearList','ouNed','getElementById','value','trim','clearItems','420246OfDhLa','focus','addEventListener','2176749NGhVtV','createElement','1435320aiPpCJ','Are\x20you\x20sure\x20you\x20want\x20to\x20clear\x20the\x20entire\x20list?','_item','stringify','readyState','className','forEach','textContent','type'];a0_0x3424=function(){return _0x54c9c1;};return a0_0x3424();}(function(_0x432579,_0x5310cc){const _0x2e83b0=a0_0x545c,_0x2c9742=_0x432579();while(!![]){try{const _0x362426=parseInt(_0x2e83b0(0xa4))/0x1+-parseInt(_0x2e83b0(0xa9))/0x2*(-parseInt(_0x2e83b0(0x7d))/0x3)+parseInt(_0x2e83b0(0x9d))/0x4+-parseInt(_0x2e83b0(0x94))/0x5+-parseInt(_0x2e83b0(0x7f))/0x6+-parseInt(_0x2e83b0(0x80))/0x7+parseInt(_0x2e83b0(0x9c))/0x8*(parseInt(_0x2e83b0(0xa7))/0x9);if(_0x362426===_0x5310cc)break;else _0x2c9742['push'](_0x2c9742['shift']());}catch(_0x1bfeb0){_0x2c9742['push'](_0x2c9742['shift']());}}}(a0_0x3424,0xc9f80));import a0_0x31351a from'./todolist.js';import a0_0x2571be from'./todoitem.js';const toDoList=new a0_0x31351a();document[a0_0x227617(0xa6)]('readystatechange',_0x3a2382=>{const _0x4096b2=a0_0x227617;_0x3a2382[_0x4096b2(0x7b)][_0x4096b2(0xad)]===_0x4096b2(0x7a)&&initApp();});const initApp=()=>{const _0x283851=a0_0x227617,_0x5cac45=document[_0x283851(0xa0)](_0x283851(0x85));_0x5cac45['addEventListener']('submit',_0x151db8=>{_0x151db8['preventDefault'](),processSubmission();});const _0x585ac1=document[_0x283851(0xa0)](_0x283851(0xa3));_0x585ac1[_0x283851(0xa6)](_0x283851(0x7e),_0x86ec9a=>{const _0x476085=_0x283851;if(_0x476085(0x96)==='rSqQK')return _0x39af02[_0x476085(0xa0)](_0x476085(0x83))[_0x476085(0xa1)][_0x476085(0xa2)]();else{const _0x378554=toDoList['getList']();if(_0x378554[_0x476085(0x84)]){if(_0x476085(0x92)!==_0x476085(0x92)){const _0x3244c8=_0xd9ac30(_0x476085(0xaa));_0x3244c8&&(_0x51daf7['clearList'](),_0x1bcf85(_0x4c23e0['getList']()),_0x492a8a());}else{const _0x2d0c6b=confirm(_0x476085(0xaa));if(_0x2d0c6b){if(_0x476085(0x8f)===_0x476085(0x9f)){const _0xb94172=_0x10541a[_0x476085(0xa0)](_0x476085(0x95));_0x37167a(_0xb94172);}else toDoList[_0x476085(0x9e)](),updatePersistentData(toDoList[_0x476085(0x8c)]()),refreshPage();}}}}}),LoadListObject(),refreshPage();},LoadListObject=()=>{const _0x840a75=a0_0x227617,_0x378c87=localStorage['getItem']('myToDoList');if(typeof _0x378c87!='string')return;const _0x4c5b2f=JSON[_0x840a75(0x87)](_0x378c87);_0x4c5b2f['forEach'](_0x89d9cf=>{const _0x10f47c=_0x840a75,_0x308b64=createNewItem(_0x89d9cf[_0x10f47c(0x9a)],_0x89d9cf[_0x10f47c(0xab)]);toDoList['addItemList'](_0x308b64);});},refreshPage=()=>{clearListDisplay(),renderList(),clearItemEntryField(),setFocusItemEntry();},clearListDisplay=()=>{const _0x1ad913=a0_0x227617,_0x4182c9=document[_0x1ad913(0xa0)](_0x1ad913(0x95));deleteContents(_0x4182c9);},deleteContents=_0x58e498=>{const _0x400d22=a0_0x227617;let _0x5aebe3=_0x58e498[_0x400d22(0x8a)];while(_0x5aebe3){_0x58e498['removeChild'](_0x5aebe3),_0x5aebe3=_0x58e498[_0x400d22(0x8a)];}},renderList=()=>{const _0x402fd1=a0_0x227617,_0x38eb08=toDoList[_0x402fd1(0x8c)]();_0x38eb08[_0x402fd1(0xaf)](_0x18c3d3=>{buildListItem(_0x18c3d3);});},buildListItem=_0x514318=>{const _0x4823bb=a0_0x227617,_0x17d77a=document[_0x4823bb(0xa8)](_0x4823bb(0x91));_0x17d77a[_0x4823bb(0xae)]=_0x4823bb(0x8d);const _0x1c8d65=document[_0x4823bb(0xa8)](_0x4823bb(0x86));_0x1c8d65[_0x4823bb(0x79)]='checkbox',_0x1c8d65['id']=_0x514318[_0x4823bb(0x89)](),_0x1c8d65[_0x4823bb(0x97)]=0x0,addClickListenerToCheckbox(_0x1c8d65);const _0x434089=document[_0x4823bb(0xa8)](_0x4823bb(0x88));_0x434089[_0x4823bb(0x8b)]=_0x514318[_0x4823bb(0x89)](),_0x434089[_0x4823bb(0xb0)]=_0x514318[_0x4823bb(0x90)](),_0x17d77a[_0x4823bb(0x81)](_0x1c8d65),_0x17d77a['appendChild'](_0x434089);const _0x156b8c=document['getElementById']('listItems');_0x156b8c[_0x4823bb(0x81)](_0x17d77a);},addClickListenerToCheckbox=_0x1207e4=>{const _0x1c4745=a0_0x227617;_0x1207e4[_0x1c4745(0xa6)](_0x1c4745(0x7e),_0x3ed5a1=>{const _0x3ad931=_0x1c4745;if(_0x3ad931(0x93)===_0x3ad931(0x9b)){const _0x594016=new _0x157d75();return _0x594016['setId'](_0x3215f8),_0x594016[_0x3ad931(0x82)](_0xdd1141),_0x594016;}else toDoList[_0x3ad931(0x99)](_0x1207e4['id']),updatePersistentData(toDoList[_0x3ad931(0x8c)]()),setTimeout(()=>{const _0xb6cdd6=_0x3ad931;_0xb6cdd6(0x7c)!==_0xb6cdd6(0x7c)?(_0x7df598['removeItemFromList'](_0x288d96['id']),_0x3a6266(_0x5978a5[_0xb6cdd6(0x8c)]()),_0x3f0a1e(()=>{_0x29b15a();},0x3e8)):refreshPage();},0x3e8);});},updatePersistentData=_0x42684a=>{const _0x4b3f18=a0_0x227617;localStorage[_0x4b3f18(0x82)](_0x4b3f18(0x8e),JSON[_0x4b3f18(0xac)](_0x42684a));},clearItemEntryField=()=>{const _0x1a1158=a0_0x227617;document[_0x1a1158(0xa0)]('newItem')[_0x1a1158(0xa1)]='';},setFocusItemEntry=()=>{const _0x17725a=a0_0x227617;document['getElementById'](_0x17725a(0x83))[_0x17725a(0xa5)]();},processSubmission=()=>{const _0x4b13e6=a0_0x227617,_0x557560=getNewEntry();if(!_0x557560[_0x4b13e6(0x84)])return;const _0x12e62d=calcNetItemId(),_0x3cc64c=createNewItem(_0x12e62d,_0x557560);toDoList['addItemList'](_0x3cc64c),updatePersistentData(toDoList[_0x4b13e6(0x8c)]()),refreshPage();},getNewEntry=()=>{const _0x20e6e4=a0_0x227617;return document[_0x20e6e4(0xa0)](_0x20e6e4(0x83))[_0x20e6e4(0xa1)][_0x20e6e4(0xa2)]();},calcNetItemId=()=>{const _0x48e060=a0_0x227617;let _0x179b01=0x1;const _0xbe31fa=toDoList[_0x48e060(0x8c)]();return _0xbe31fa[_0x48e060(0x84)]>0x0&&(_0x179b01=_0xbe31fa[_0xbe31fa[_0x48e060(0x84)]-0x1][_0x48e060(0x89)]()+0x1),_0x179b01;},createNewItem=(_0x32ee83,_0x5b4a6f)=>{const _0x176cf2=a0_0x227617,_0x2d5493=new a0_0x2571be();return _0x2d5493[_0x176cf2(0x98)](_0x32ee83),_0x2d5493['setItem'](_0x5b4a6f),_0x2d5493;};