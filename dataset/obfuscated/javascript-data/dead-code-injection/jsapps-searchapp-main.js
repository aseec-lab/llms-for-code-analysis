function a0_0x5137(){const _0x87418d=['5089hzOegx','856SUGFya','1584rHCATz','getElementById','target','clear','length','addEventListener','click','58836xAvZUx','5863ONgasr','search','3130LdnzxD','readyState','28936vVyaXc','825950doRwfA','4008bBcIdy','107884IjdDNs','complete','9276ycuNEa','search-bar','input','8AxCJwA'];a0_0x5137=function(){return _0x87418d;};return a0_0x5137();}const a0_0x5e3ca9=a0_0x3faa;(function(_0x45d7b5,_0x45eb97){const _0x31545f=a0_0x3faa,_0x16c309=_0x45d7b5();while(!![]){try{const _0xc3f258=-parseInt(_0x31545f(0x1f5))/0x1+parseInt(_0x31545f(0x1fa))/0x2*(-parseInt(_0x31545f(0x1ed))/0x3)+-parseInt(_0x31545f(0x1fc))/0x4*(parseInt(_0x31545f(0x1f0))/0x5)+parseInt(_0x31545f(0x1f4))/0x6*(-parseInt(_0x31545f(0x1fb))/0x7)+parseInt(_0x31545f(0x1f2))/0x8*(parseInt(_0x31545f(0x1fd))/0x9)+parseInt(_0x31545f(0x1f3))/0xa+-parseInt(_0x31545f(0x1ee))/0xb*(-parseInt(_0x31545f(0x1f7))/0xc);if(_0xc3f258===_0x45eb97)break;else _0x16c309['push'](_0x16c309['shift']());}catch(_0x3a11bb){_0x16c309['push'](_0x16c309['shift']());}}}(a0_0x5137,0x4f690));function a0_0x3faa(_0x57bebf,_0x560dd1){const _0x5137bc=a0_0x5137();return a0_0x3faa=function(_0x3faac5,_0x12c111){_0x3faac5=_0x3faac5-0x1ed;let _0x39e9e5=_0x5137bc[_0x3faac5];return _0x39e9e5;},a0_0x3faa(_0x57bebf,_0x560dd1);}import{setSearchFocus,showClearTextButton,clearSearchText,clearPushListener}from'./modules/search-bar.js';import{getSearchTerm,retrieveSearchResults}from'./modules/data-functions.js';import{deleteSearchResults,buildSearchResults,clearStatsLine,setStatsLine}from'./modules/search-results.js';document[a0_0x5e3ca9(0x202)]('readystatechange',_0x17ace2=>{const _0x276824=a0_0x5e3ca9;_0x17ace2[_0x276824(0x1ff)][_0x276824(0x1f1)]===_0x276824(0x1f6)&&initApp();});const initApp=()=>{const _0x18546e=a0_0x5e3ca9;setSearchFocus(),document['getElementById'](_0x18546e(0x1ef))[_0x18546e(0x202)](_0x18546e(0x1f9),showClearTextButton);const _0x14f699=document['getElementById'](_0x18546e(0x200));_0x14f699[_0x18546e(0x202)](_0x18546e(0x203),clearSearchText),_0x14f699['addEventListener']('keydown',clearPushListener);const _0x5251cf=document[_0x18546e(0x1fe)](_0x18546e(0x1f8));_0x5251cf['addEventListener']('submit',submitSearch);},submitSearch=_0x2e31b2=>{_0x2e31b2['preventDefault'](),deleteSearchResults(),processSearch(),setSearchFocus();},processSearch=async()=>{const _0x3f8ada=a0_0x5e3ca9;clearStatsLine();const _0x5e810e=getSearchTerm();if(_0x5e810e==='')return;const _0x18be1e=await retrieveSearchResults(_0x5e810e);if(_0x18be1e[_0x3f8ada(0x201)])buildSearchResults(_0x18be1e);setStatsLine(_0x18be1e[_0x3f8ada(0x201)]);};