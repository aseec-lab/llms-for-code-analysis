const a0_0x12548c=a0_0x121c;(function(_0x52dd98,_0x4af1e8){const _0x143339=a0_0x121c,_0x510986=_0x52dd98();while(!![]){try{const _0x1a5a0f=-parseInt(_0x143339(0xf2))/0x1*(parseInt(_0x143339(0x115))/0x2)+parseInt(_0x143339(0x112))/0x3+parseInt(_0x143339(0xee))/0x4+-parseInt(_0x143339(0xfc))/0x5*(parseInt(_0x143339(0xfd))/0x6)+-parseInt(_0x143339(0x124))/0x7*(-parseInt(_0x143339(0xf0))/0x8)+parseInt(_0x143339(0xfe))/0x9+parseInt(_0x143339(0x111))/0xa;if(_0x1a5a0f===_0x4af1e8)break;else _0x510986['push'](_0x510986['shift']());}catch(_0x4f7168){_0x510986['push'](_0x510986['shift']());}}}(a0_0x596c,0x487cc));import a0_0x2b0dcc from'./todolist.js';import a0_0x5de136 from'./todoitem.js';function a0_0x121c(_0x51f07e,_0x31df09){const _0x596c54=a0_0x596c();return a0_0x121c=function(_0x121ce8,_0x305f97){_0x121ce8=_0x121ce8-0xee;let _0x3bdb6f=_0x596c54[_0x121ce8];return _0x3bdb6f;},a0_0x121c(_0x51f07e,_0x31df09);}const toDoList=new a0_0x2b0dcc();function a0_0x596c(){const _0x1cf439=['forEach','div','\x20the\x20entir','readystate','removeChil','60RZfAfv','141396TibZYK','4414680yNhgQt','clearList','htmlFor','readyState','change','myToDoList','lastElemen','itemEntryF','appendChil','length','ent','setItem','submit','item','createElem','addItemLis','preventDef','checkbox','click','178740FunkcU','1229409wjkrJs','stener','className','30OVPiUs','newItem','Are\x20you\x20su','label','type','textConten','stringify','addEventLi','value','trim','_item','re\x20you\x20wan','ault','ById','FromList','34615XLnwwZ','_id','getId','removeItem','parse','632192FthYbn','target','48wnOsiH','getElement','35081OoeFqX','getList','listItems','string','tChild'];a0_0x596c=function(){return _0x1cf439;};return a0_0x596c();}document['addEventLi'+a0_0x12548c(0x113)](a0_0x12548c(0xfa)+a0_0x12548c(0x102),_0x428f3a=>{const _0x5aa28e=a0_0x12548c;_0x428f3a[_0x5aa28e(0xef)][_0x5aa28e(0x101)]==='complete'&&initApp();});const initApp=()=>{const _0x88cbd0=a0_0x12548c,_0x27c8ca=document[_0x88cbd0(0xf1)+'ById'](_0x88cbd0(0x105)+'orm');_0x27c8ca[_0x88cbd0(0x11c)+_0x88cbd0(0x113)](_0x88cbd0(0x10a),_0x4da7a1=>{const _0xd24ab9=_0x88cbd0;_0x4da7a1[_0xd24ab9(0x10e)+_0xd24ab9(0x121)](),processSubmission();});const _0x3d5377=document['getElement'+_0x88cbd0(0x122)]('clearItems');_0x3d5377[_0x88cbd0(0x11c)+_0x88cbd0(0x113)](_0x88cbd0(0x110),_0x180b2a=>{const _0x40da2a=_0x88cbd0,_0x2b0c45=toDoList[_0x40da2a(0xf3)]();if(_0x2b0c45[_0x40da2a(0x107)]){const _0x315a15=confirm(_0x40da2a(0x117)+_0x40da2a(0x120)+'t\x20to\x20clear'+_0x40da2a(0xf9)+'e\x20list?');_0x315a15&&(toDoList[_0x40da2a(0xff)](),updatePersistentData(toDoList[_0x40da2a(0xf3)]()),refreshPage());}}),LoadListObject(),refreshPage();},LoadListObject=()=>{const _0x196947=a0_0x12548c,_0x123c51=localStorage['getItem'](_0x196947(0x103));if(typeof _0x123c51!=_0x196947(0xf5))return;const _0x261916=JSON[_0x196947(0x128)](_0x123c51);_0x261916[_0x196947(0xf7)](_0x19fcdb=>{const _0x379e7d=_0x196947,_0x11f636=createNewItem(_0x19fcdb[_0x379e7d(0x125)],_0x19fcdb[_0x379e7d(0x11f)]);toDoList[_0x379e7d(0x10d)+'t'](_0x11f636);});},refreshPage=()=>{clearListDisplay(),renderList(),clearItemEntryField(),setFocusItemEntry();},clearListDisplay=()=>{const _0x5ef843=a0_0x12548c,_0x191569=document[_0x5ef843(0xf1)+_0x5ef843(0x122)](_0x5ef843(0xf4));deleteContents(_0x191569);},deleteContents=_0x2327ff=>{const _0x4bd23a=a0_0x12548c;let _0x4198cc=_0x2327ff['lastElemen'+_0x4bd23a(0xf6)];while(_0x4198cc){_0x2327ff[_0x4bd23a(0xfb)+'d'](_0x4198cc),_0x4198cc=_0x2327ff[_0x4bd23a(0x104)+_0x4bd23a(0xf6)];}},renderList=()=>{const _0x319bfc=toDoList['getList']();_0x319bfc['forEach'](_0x35d06a=>{buildListItem(_0x35d06a);});},buildListItem=_0x15c242=>{const _0x38b036=a0_0x12548c,_0x16e4d0=document[_0x38b036(0x10c)+_0x38b036(0x108)](_0x38b036(0xf8));_0x16e4d0[_0x38b036(0x114)]=_0x38b036(0x10b);const _0x40bec9=document[_0x38b036(0x10c)+_0x38b036(0x108)]('input');_0x40bec9[_0x38b036(0x119)]=_0x38b036(0x10f),_0x40bec9['id']=_0x15c242[_0x38b036(0x126)](),_0x40bec9['tabIndex']=0x0,addClickListenerToCheckbox(_0x40bec9);const _0x3c82a6=document[_0x38b036(0x10c)+_0x38b036(0x108)](_0x38b036(0x118));_0x3c82a6[_0x38b036(0x100)]=_0x15c242[_0x38b036(0x126)](),_0x3c82a6[_0x38b036(0x11a)+'t']=_0x15c242['getItem'](),_0x16e4d0[_0x38b036(0x106)+'d'](_0x40bec9),_0x16e4d0[_0x38b036(0x106)+'d'](_0x3c82a6);const _0x18a9ec=document['getElement'+'ById'](_0x38b036(0xf4));_0x18a9ec[_0x38b036(0x106)+'d'](_0x16e4d0);},addClickListenerToCheckbox=_0x3df0ab=>{const _0x554f34=a0_0x12548c;_0x3df0ab[_0x554f34(0x11c)+_0x554f34(0x113)](_0x554f34(0x110),_0x1d2eac=>{const _0x2e5879=_0x554f34;toDoList[_0x2e5879(0x127)+_0x2e5879(0x123)](_0x3df0ab['id']),updatePersistentData(toDoList['getList']()),setTimeout(()=>{refreshPage();},0x3e8);});},updatePersistentData=_0x226bac=>{const _0x1d2c76=a0_0x12548c;localStorage[_0x1d2c76(0x109)](_0x1d2c76(0x103),JSON[_0x1d2c76(0x11b)](_0x226bac));},clearItemEntryField=()=>{const _0x2fb242=a0_0x12548c;document[_0x2fb242(0xf1)+_0x2fb242(0x122)](_0x2fb242(0x116))[_0x2fb242(0x11d)]='';},setFocusItemEntry=()=>{const _0xec1d1c=a0_0x12548c;document[_0xec1d1c(0xf1)+_0xec1d1c(0x122)](_0xec1d1c(0x116))['focus']();},processSubmission=()=>{const _0x524419=a0_0x12548c,_0x4cab54=getNewEntry();if(!_0x4cab54['length'])return;const _0x13d007=calcNetItemId(),_0xc17361=createNewItem(_0x13d007,_0x4cab54);toDoList[_0x524419(0x10d)+'t'](_0xc17361),updatePersistentData(toDoList['getList']()),refreshPage();},getNewEntry=()=>{const _0x441f9f=a0_0x12548c;return document['getElement'+'ById'](_0x441f9f(0x116))[_0x441f9f(0x11d)][_0x441f9f(0x11e)]();},calcNetItemId=()=>{const _0x1a85de=a0_0x12548c;let _0x10eb6b=0x1;const _0x48b218=toDoList[_0x1a85de(0xf3)]();return _0x48b218[_0x1a85de(0x107)]>0x0&&(_0x10eb6b=_0x48b218[_0x48b218[_0x1a85de(0x107)]-0x1]['getId']()+0x1),_0x10eb6b;},createNewItem=(_0x4d7e78,_0xa5370)=>{const _0x59db88=new a0_0x5de136();return _0x59db88['setId'](_0x4d7e78),_0x59db88['setItem'](_0xa5370),_0x59db88;};