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
	__vue_options__.__file = "D:\\WebstormProjects\\Weex\\vue\\choosePic.vue"
	__vue_options__.render = __vue_template__.render
	__vue_options__.staticRenderFns = __vue_template__.staticRenderFns
	__vue_options__._scopeId = "data-v-ce6eb412"
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
	var stream = weex.requireModule('stream');
	var upsoft = weex.requireModule('upsoft');
	exports.default = {
	    data: function data() {
	        return {
	            weexStar: 'unknown',
	            vueStar: 'unknown',
	            postValue: 'unknown',
	            path: 'file:///storage/emulated/0/upsoftsdk/weex/ic_weex.png',
	            imageList: []
	        };
	    },


	    methods: {
	        getStarCount: function getStarCount(repo, callback) {
	            return stream.fetch({
	                method: 'GET',
	                type: 'json',
	                url: 'https://api.github.com/repos/' + repo
	            }, callback);
	        },
	        getUserInfo: function getUserInfo(callback) {
	            return stream.fetch({
	                method: 'POST',
	                type: 'json',
	                url: 'http://172.16.1.59/cgi/bp/usermgt/user/getCurrent'
	            }, callback);
	        },
	        choosePic: function choosePic(event) {
	            var _this = this;

	            return upsoft.choosePic({
	                max: 1,
	                mode: 2,
	                showCamera: 'true',
	                enablePreview: 'true',
	                enableCrop: 'true'
	            }, function (event) {
	                if (event.status == 1) {
	                    modal.toast({ message: '选择照片回调执行 ' + event.msg });
	                    _this.path = 'file://' + event.data[0];
	                } else if (event.status == 0) {
	                    modal.toast({ message: '选择照片回调执行 ' + event.msg });
	                }
	            });
	        }
	    },
	    mounted: function mounted() {
//	        modal.toast({ message: 'mounted执行 ' });
	        //            loadScript('http://cdn.bootcss.com/blueimp-md5/1.1.0/js/md5.js',function () {
	        //                modal.toast({message: 'md5.js加载完成 '})
	        //                alert('md5.js加载完成')
	        //            })
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
	      "click": _vm.choosePic
	    }
	  }, [_vm._v("选择图片")])]), _c('div', {
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