// { "framework": "Vue" }
/******/ (function(modules) { // webpackBootstrap
/******/ 	// The module cache
/******/ 	var installedModules = {};

/******/ 	// The require function
/******/ 	function __webpack_require__(moduleId) {

/******/ 		// Check if module is in cache
/******/ 		if(installedModules[moduleId])
/******/ 			return installedModules[moduleId].exports;

/******/ 		// Create a new module (and put it into the cache)
/******/ 		var module = installedModules[moduleId] = {
/******/ 			exports: {},
/******/ 			id: moduleId,
/******/ 			loaded: false
/******/ 		};

/******/ 		// Execute the module function
/******/ 		modules[moduleId].call(module.exports, module, module.exports, __webpack_require__);

/******/ 		// Flag the module as loaded
/******/ 		module.loaded = true;

/******/ 		// Return the exports of the module
/******/ 		return module.exports;
/******/ 	}


/******/ 	// expose the modules object (__webpack_modules__)
/******/ 	__webpack_require__.m = modules;

/******/ 	// expose the module cache
/******/ 	__webpack_require__.c = installedModules;

/******/ 	// __webpack_public_path__
/******/ 	__webpack_require__.p = "";

/******/ 	// Load entry module and return exports
/******/ 	return __webpack_require__(0);
/******/ })
/************************************************************************/
/******/ ([
/* 0 */
/***/ (function(module, exports, __webpack_require__) {

	var __vue_exports__, __vue_options__
	var __vue_styles__ = []

	/* styles */
	__vue_styles__.push(__webpack_require__(1)
	)

	/* script */
	__vue_exports__ = __webpack_require__(2)

	/* template */
	var __vue_template__ = __webpack_require__(3)
	__vue_options__ = __vue_exports__ = __vue_exports__ || {}
	if (
	  typeof __vue_exports__.default === "object" ||
	  typeof __vue_exports__.default === "function"
	) {
	if (Object.keys(__vue_exports__).some(function (key) { return key !== "default" && key !== "__esModule" })) {console.error("named exports are not supported in *.vue files.")}
	__vue_options__ = __vue_exports__ = __vue_exports__.default
	}
	if (typeof __vue_options__ === "function") {
	  __vue_options__ = __vue_options__.options
	}
	__vue_options__.__file = "D:\\WebstormProjects\\Weex\\vue\\lookBigPic.vue"
	__vue_options__.render = __vue_template__.render
	__vue_options__.staticRenderFns = __vue_template__.staticRenderFns
	__vue_options__._scopeId = "data-v-69e42295"
	__vue_options__.style = __vue_options__.style || {}
	__vue_styles__.forEach(function (module) {
	  for (var name in module) {
	    __vue_options__.style[name] = module[name]
	  }
	})
	if (typeof __register_static_styles__ === "function") {
	  __register_static_styles__(__vue_options__._scopeId, __vue_styles__)
	}

	module.exports = __vue_exports__
	module.exports.el = 'true'
	new Vue(module.exports)


/***/ }),
/* 1 */
/***/ (function(module, exports) {

	module.exports = {
	  "wrapper": {
	    "flexDirection": "column",
	    "justifyContent": "center"
	  },
	  "button": {
	    "fontSize": 60,
	    "width": 450,
	    "textAlign": "center",
	    "marginTop": 30,
	    "marginLeft": 150,
	    "paddingTop": 20,
	    "paddingBottom": 20,
	    "borderWidth": 2,
	    "borderStyle": "solid",
	    "color": "#666666",
	    "borderColor": "#DDDDDD",
	    "backgroundColor": "#F5F5F5"
	  },
	  "group": {
	    "flexDirection": "row",
	    "justifyContent": "center",
	    "marginBottom": 40
	  },
	  "title": {
	    "fontSize": 45,
	    "color": "#888888"
	  },
	  "count": {
	    "fontSize": 45,
	    "fontWeight": "bold",
	    "marginLeft": 12,
	    "color": "#41B883"
	  }
	}

/***/ }),
/* 2 */
/***/ (function(module, exports) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//

	//    import test from '../static/js/test'
	var modal = weex.requireModule('modal');
	var upsoft = weex.requireModule('upsoft');
	exports.default = {
	    data: function data() {
	        return {
	            weexStar: 'unknown',
	            vueStar: 'unknown',
	            postValue: 'unknown',
	            path: 'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1535715352133&di=68d6019711883e099117d7da4398a49c&imgtype=0&src=http%3A%2F%2Fpic41.photophoto.cn%2F20161217%2F1155116491394363_b.jpg',
//	            imageList: ['file:///storage/emulated/0/upsoftsdk/weex/ic_weex.png', 'http://192.168.0.67:8080/FirstWebProject/sl01.jpg', 'http://192.168.0.67:8080/FirstWebProject/sl02.jpg', 'http://192.168.0.67:8080/FirstWebProject/sl03.jpg']
	            imageList: ['https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1535715352135&di=ae4f60d6a79b4e8002f5e4d6ec6fe7dd&imgtype=0&src=http%3A%2F%2Fimg17.3lian.com%2Fd%2Ffile%2F201702%2F09%2F65345f70b77ad89ae45a028e99d62ea8.jpg',
	             'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1535715352134&di=063110d2817f16fcbf16a4fc71601ea9&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01c1b75541b2cd000001a64bb14a39.jpg',
	             'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1535715352134&di=00676352a31cff84b9ef8e6eb6a809ea&imgtype=0&src=http%3A%2F%2Fpic39.nipic.com%2F20140311%2F10600816_135850674000_2.jpg',
	              'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1535715352133&di=68d6019711883e099117d7da4398a49c&imgtype=0&src=http%3A%2F%2Fpic41.photophoto.cn%2F20161217%2F1155116491394363_b.jpg']
	        };
	    },


	    methods: {
	        lookBigPic: function lookBigPic(event) {
	            //                return upsoft.lookBigPic({
	            //                    max: 1,
	            //                    mode: 2,
	            //                    showCamera: 'true',
	            //                    enablePreview: 'true',
	            //                    enableCrop: 'true'
	            //                });
	            return upsoft.lookBigPic(this.imageList, 1);
	        }
	    }
	};
	module.exports = exports['default'];

/***/ }),
/* 3 */
/***/ (function(module, exports) {

	module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
	  return _c('div', {
	    staticClass: ["wrapper"]
	  }, [_c('div', {
	    staticClass: ["wrapper"]
	  }, [_c('text', {
	    staticClass: ["button"],
	    on: {
	      "click": _vm.lookBigPic
	    }
	  }, [_vm._v("查看大图")])]), _c('div', {
	    staticClass: ["wrapper"]
	  }, [_c('image', {
	    staticStyle: {
	      width: "800px",
	      height: "800px"
	    },
	    attrs: {
	      "src": _vm.path,
	      "alt": "",
	      "id": "img",
	      "resize": "contain"
	    }
	  })])])
	},staticRenderFns: []}
	module.exports.render._withStripped = true

/***/ })
/******/ ]);