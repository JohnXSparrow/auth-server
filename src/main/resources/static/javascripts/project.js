/*
	Masked Input plugin for jQuery
	Copyright (c) 2007-2013 Josh Bush (digitalbush.com)
	Licensed under the MIT license (http://digitalbush.com/projects/masked-input-plugin/#license)
	Version: 1.3.1
*/

!function(e){function t(){var e=document.createElement("input"),t="onpaste";return e.setAttribute(t,""),"function"==typeof e[t]?"paste":"input"}var n,a=t()+".mask",i=navigator.userAgent,r=/iphone/i.test(i),o=/android/i.test(i);e.mask={definitions:{9:"[0-9]",a:"[A-Za-z]","*":"[A-Za-z0-9]"},dataName:"rawMaskFn",placeholder:"_"},e.fn.extend({caret:function(e,t){var n;return 0===this.length||this.is(":hidden")?void 0:"number"==typeof e?(t="number"==typeof t?t:e,this.each(function(){this.setSelectionRange?this.setSelectionRange(e,t):this.createTextRange&&(n=this.createTextRange(),n.collapse(!0),n.moveEnd("character",t),n.moveStart("character",e),n.select())})):(this[0].setSelectionRange?(e=this[0].selectionStart,t=this[0].selectionEnd):document.selection&&document.selection.createRange&&(n=document.selection.createRange(),e=0-n.duplicate().moveStart("character",-1e5),t=e+n.text.length),{begin:e,end:t})},unmask:function(){return this.trigger("unmask")},mask:function(t,i){var c,s,l,u,d,f;return!t&&this.length>0?(c=e(this[0]),c.data(e.mask.dataName)()):(i=e.extend({placeholder:e.mask.placeholder,completed:null},i),s=e.mask.definitions,l=[],u=f=t.length,d=null,e.each(t.split(""),function(e,t){"?"==t?(f--,u=e):s[t]?(l.push(RegExp(s[t])),null===d&&(d=l.length-1)):l.push(null)}),this.trigger("unmask").each(function(){function c(e){for(;f>++e&&!l[e];);return e}function h(e){for(;--e>=0&&!l[e];);return e}function m(e,t){var n,a;if(!(0>e)){for(n=e,a=c(t);f>n;n++)if(l[n]){if(!(f>a&&l[n].test($[a])))break;$[n]=$[a],$[a]=i.placeholder,a=c(a)}b(),x.caret(Math.max(d,e))}}function p(e){var t,n,a,r;for(t=e,n=i.placeholder;f>t;t++)if(l[t]){if(a=c(t),r=$[t],$[t]=n,!(f>a&&l[a].test(r)))break;n=r}}function g(e){var t,n,a,i=e.which;8===i||46===i||r&&127===i?(t=x.caret(),n=t.begin,a=t.end,0===a-n&&(n=46!==i?h(n):a=c(n-1),a=46===i?c(a):a),k(n,a),m(n,a-1),e.preventDefault()):27==i&&(x.val(R),x.caret(0,y()),e.preventDefault())}function v(t){var n,a,r,s=t.which,u=x.caret();t.ctrlKey||t.altKey||t.metaKey||32>s||s&&(0!==u.end-u.begin&&(k(u.begin,u.end),m(u.begin,u.end-1)),n=c(u.begin-1),f>n&&(a=String.fromCharCode(s),l[n].test(a)&&(p(n),$[n]=a,b(),r=c(n),o?setTimeout(e.proxy(e.fn.caret,x,r),0):x.caret(r),i.completed&&r>=f&&i.completed.call(x))),t.preventDefault())}function k(e,t){var n;for(n=e;t>n&&f>n;n++)l[n]&&($[n]=i.placeholder)}function b(){x.val($.join(""))}function y(e){var t,n,a=x.val(),r=-1;for(t=0,pos=0;f>t;t++)if(l[t]){for($[t]=i.placeholder;pos++<a.length;)if(n=a.charAt(pos-1),l[t].test(n)){$[t]=n,r=t;break}if(pos>a.length)break}else $[t]===a.charAt(pos)&&t!==u&&(pos++,r=t);return e?b():u>r+1?(x.val(""),k(0,f)):(b(),x.val(x.val().substring(0,r+1))),u?t:d}var x=e(this),$=e.map(t.split(""),function(e){return"?"!=e?s[e]?i.placeholder:e:void 0}),R=x.val();x.data(e.mask.dataName,function(){return e.map($,function(e,t){return l[t]&&e!=i.placeholder?e:null}).join("")}),x.attr("readonly")||x.one("unmask",function(){x.unbind(".mask").removeData(e.mask.dataName)}).bind("focus.mask",function(){clearTimeout(n);var e;R=x.val(),e=y(),n=setTimeout(function(){b(),e==t.length?x.caret(0,e):x.caret(e)},10)}).bind("blur.mask",function(){y(),x.val()!=R&&x.change()}).bind("keydown.mask",g).bind("keypress.mask",v).bind(a,function(){setTimeout(function(){var e=y(!0);x.caret(e),i.completed&&e==x.val().length&&i.completed.call(x)},0)}),y()}))}})}(jQuery),$(".navbar-toggle").on("click",function(){$(".sidebar").removeClass("hidden-sm hidden-xs")}),$(".close-sidebar").on("click",function(e){e.preventDefault(),$(".sidebar").addClass("hidden-sm hidden-xs")}),$("input:file").on("change",function(){var e=$(this).val();$(".custom-file strong").text(e),$(".custom-file").parent().addClass("success")}),$(".sidebar").css("height",$(document).height()),jQuery(function(e){e(".date").mask("99/99/9999")});