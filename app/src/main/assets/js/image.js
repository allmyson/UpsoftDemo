// { "framework": "Vue" }
"use weex:vue";

/******/
(function(modules) { // webpackBootstrap
    /******/ // The module cache
    /******/
    var installedModules = {};
    /******/
    /******/ // The require function
    /******/
    function __webpack_require__(moduleId) {
        /******/
        /******/ // Check if module is in cache
        /******/
        if (installedModules[moduleId]) {
            /******/
            return installedModules[moduleId].exports;
            /******/
        }
        /******/ // Create a new module (and put it into the cache)
        /******/
        var module = installedModules[moduleId] = {
            /******/
            i: moduleId,
            /******/
            l: false,
            /******/
            exports: {}
            /******/
        };
        /******/
        /******/ // Execute the module function
        /******/
        modules[moduleId].call(module.exports, module, module.exports, __webpack_require__);
        /******/
        /******/ // Flag the module as loaded
        /******/
        module.l = true;
        /******/
        /******/ // Return the exports of the module
        /******/
        return module.exports;
        /******/
    }
    /******/
    /******/
    /******/ // expose the modules object (__webpack_modules__)
    /******/
    __webpack_require__.m = modules;
    /******/
    /******/ // expose the module cache
    /******/
    __webpack_require__.c = installedModules;
    /******/
    /******/ // define getter function for harmony exports
    /******/
    __webpack_require__.d = function(exports, name, getter) {
        /******/
        if (!__webpack_require__.o(exports, name)) {
            /******/
            Object.defineProperty(exports, name, {
                /******/
                configurable: false,
                /******/
                enumerable: true,
                /******/
                get: getter
                    /******/
            });
            /******/
        }
        /******/
    };
    /******/
    /******/ // getDefaultExport function for compatibility with non-harmony modules
    /******/
    __webpack_require__.n = function(module) {
        /******/
        var getter = module && module.__esModule ?
            /******/
            function getDefault() {
                return module['default'];
            } :
            /******/
            function getModuleExports() {
                return module;
            };
        /******/
        __webpack_require__.d(getter, 'a', getter);
        /******/
        return getter;
        /******/
    };
    /******/
    /******/ // Object.prototype.hasOwnProperty.call
    /******/
    __webpack_require__.o = function(object, property) {
        return Object.prototype.hasOwnProperty.call(object, property);
    };
    /******/
    /******/ // __webpack_public_path__
    /******/
    __webpack_require__.p = "";
    /******/
    /******/ // Load entry module and return exports
    /******/
    return __webpack_require__(__webpack_require__.s = 0);
    /******/
})
/************************************************************************/
/******/
([
    /* 0 */
    /***/
    (function(module, exports, __webpack_require__) {

        "use strict";


        var _App = __webpack_require__(1);

        var _App2 = _interopRequireDefault(_App);

        function _interopRequireDefault(obj) {
            return obj && obj.__esModule ? obj : {
                default: obj
            };
        }

        _App2.default.el = '#root';
        new Vue(_App2.default);

        /***/
    }),
    /* 1 */
    /***/
    (function(module, exports, __webpack_require__) {

        var __vue_exports__, __vue_options__
        var __vue_styles__ = []

        /* styles */
        __vue_styles__.push(__webpack_require__(2))

        /* script */
        __vue_exports__ = __webpack_require__(3)

        /* template */
        var __vue_template__ = __webpack_require__(4)
        __vue_options__ = __vue_exports__ = __vue_exports__ || {}
        if (
            typeof __vue_exports__.default === "object" ||
            typeof __vue_exports__.default === "function"
        ) {
            if (Object.keys(__vue_exports__).some(function(key) {
                    return key !== "default" && key !== "__esModule"
                })) {
                console.error("named exports are not supported in *.vue files.")
            }
            __vue_options__ = __vue_exports__ = __vue_exports__.default
        }
        if (typeof __vue_options__ === "function") {
            __vue_options__ = __vue_options__.options
        }
        __vue_options__.__file = "/usr/src/app/raw/8a7a2d68563c4be0368fc6377d72900d/App.vue"
        __vue_options__.render = __vue_template__.render
        __vue_options__.staticRenderFns = __vue_template__.staticRenderFns
        __vue_options__._scopeId = "data-v-2a8c9093"
        __vue_options__.style = __vue_options__.style || {}
        __vue_styles__.forEach(function(module) {
            for (var name in module) {
                __vue_options__.style[name] = module[name]
            }
        })
        if (typeof __register_static_styles__ === "function") {
            __register_static_styles__(__vue_options__._scopeId, __vue_styles__)
        }

        module.exports = __vue_exports__


        /***/
    }),
    /* 2 */
    /***/
    (function(module, exports) {

        module.exports = {
            "body": {
                "backgroundColor": "#ffffff"
            },
            "image": {
                "width": "600",
                "backgroundColor": "#FFFFDF",
                "height": "400"
            },
            "scroller": {
                "width": "750",
                "flex": 1
            }
        }

        /***/
    }),
    /* 3 */
    /***/
    (function(module, exports, __webpack_require__) {

        "use strict";
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


        /***/
    }),
    /* 4 */
    /***/
    (function(module, exports) {

        module.exports = {
            render: function() {
                var _vm = this;
                var _h = _vm.$createElement;
                var _c = _vm._self._c || _h;
                return _c('div', [_c('scroller', {
                    staticClass: ["scroller"],
                    appendAsTree: true,
                    attrs: {
                        "append": "tree",
                        "offsetAccuracy": "10"
                    },
                    on: {
                        "scroll": _vm.scrollHandler
                    }
                }, [_vm._m(0)])])
            },
            staticRenderFns: [function() {
                var _vm = this;
                var _h = _vm.$createElement;
                var _c = _vm._self._c || _h;
                return _c('div', [_c('text', {
                    staticStyle: {
                        fontFamily: "iconfont4",
                        fontSize: "50px",
                        color: "green"
                    }
                }, [_vm._v("加载网络图片(https)")]), _c('image', {
                    staticClass: ["image"],
                    attrs: {
                        "src": "https://img.alicdn.com/tps/TB1z.55OFXXXXcLXXXXXXXXXXXX-560-560.jpg"
                    }
                }), _c('text', {
                    staticStyle: {
                        fontFamily: "iconfont4",
                        fontSize: "50px",
                        color: "green"
                    }
                }, [_vm._v("加载网络图片(http)")]), _c('image', {
                    staticClass: ["image"],
                    attrs: {
                        "src": "http://ov62vgsq0.bkt.clouddn.com/15037343027147299"
                    }
                }), _c('text', {
                    staticStyle: {
                        fontFamily: "iconfont4",
                        fontSize: "50px",
                        color: "green"
                    }
                }, [_vm._v("加载drawable图片")]), _c('image', {
                    staticClass: ["image"],
                    attrs: {
                        "src": "drawable://ic_drawable"
                    }
                }), _c('text', {
                    staticStyle: {
                        fontFamily: "iconfont4",
                        fontSize: "50px",
                        color: "green"
                    }
                }, [_vm._v("加载mipmap图片")]), _c('image', {
                    staticClass: ["image"],
                    attrs: {
                        "src": "mipmap://ic_mipmap"
                    }
                }), _c('text', {
                    staticStyle: {
                        fontFamily: "iconfont4",
                        fontSize: "50px",
                        color: "green"
                    }
                }, [_vm._v("加载assets图片")]), _c('image', {
                    staticClass: ["image"],
                    attrs: {
                        "src": "assets://img/ic_assets.png"
                    }
                }), _c('text', {
                    staticStyle: {
                        fontFamily: "iconfont4",
                        fontSize: "50px",
                        color: "green"
                    }
                }, [_vm._v("加载sdcard图片")]), _c('image', {
                    staticClass: ["image"],
                    attrs: {
                        "src": "file:///storage/emulated/0/upsoft/UpsoftDemo/weex/ic_weex.png"
                    }
                })])
            }]
        }
        module.exports.render._withStripped = true

        /***/
    })
    /******/
]);