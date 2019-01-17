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
        __vue_options__.__file = "/usr/src/app/raw/e52df4621b372602ff4407e859db253a/App.vue"
        __vue_options__.render = __vue_template__.render
        __vue_options__.staticRenderFns = __vue_template__.staticRenderFns
        __vue_options__._scopeId = "data-v-3bf0a21b"
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
            "wrapper": {
                "flexDirection": "column",
                "justifyContent": "center"
            },
            "button": {
                "fontSize": "60",
                "width": "450",
                "textAlign": "center",
                "marginTop": "30",
                "marginLeft": "150",
                "paddingTop": "20",
                "paddingBottom": "20",
                "borderWidth": "2",
                "borderStyle": "solid",
                "color": "#666666",
                "borderColor": "#DDDDDD",
                "backgroundColor": "#F5F5F5"
            }
        }

        /***/
    }),
    /* 3 */
    /***/
    (function(module, exports, __webpack_require__) {

        "use strict";


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
        //
        //
        //
        //
        //
        //
        //
        //
        //

        var navigator = weex.requireModule('navigator');
        var modal = weex.requireModule('modal');
        //    var app = weex.requireModule('app')
        exports.default = {
            methods: {
                jumpUrl: function jumpUrl(event) {
                    console.log('will jump url');
                    navigator.push({
                        url: 'https://h5.m.taobao.com/trip/wxc-minibar/index.html?_wx_tpl=https%3A%2F%2Fh5.m.taobao.com%2Ftrip%2Fwxc-minibar%2Fdemo%2Findex.native-min.js',
                        animated: "true"
                    }, function(event) {
                        modal.toast({
                            message: 'callback: ' + event
                        });
                    });
                },
                jumpFile: function jumpFile(event) {
                    console.log('will jump file');
                    navigator.push({
                        //                    url: "sdcard:/"+app.getFilePath() + "picker.js",
                        url: "sdcard:///storage/emulated/0/upsoft/UpsoftDemo/weex/RichText.js",
                        animated: "true"
                    }, function(event) {
                        modal.toast({
                            message: 'callback: ' + event
                        });
                    });
                },
                jumpAsset: function jumpAsset(event) {
                    console.log('will jump asset');
                    navigator.push({
                        //                    url: 'file://assets/list.js',
                        url: 'file://assets/js/image.js',
                        animated: "true"
                    }, function(event) {
                        modal.toast({
                            message: 'callback: ' + event
                        });
                    });
                }
            }
        };

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
                return _c('div', [_vm._m(0), _c('div', {
                    staticClass: ["wrapper"]
                }, [_c('text', {
                    staticClass: ["button"],
                    on: {
                        "click": _vm.jumpUrl
                    }
                }, [_vm._v("跳转远程URL")])]), _c('div', {
                    staticClass: ["wrapper"]
                }, [_c('text', {
                    staticClass: ["button"],
                    on: {
                        "click": _vm.jumpFile
                    }
                }, [_vm._v("跳转SDCard URL")])]), _c('div', {
                    staticClass: ["wrapper"]
                }, [_c('text', {
                    staticClass: ["button"],
                    on: {
                        "click": _vm.jumpAsset
                    }
                }, [_vm._v("跳转Asset URL")])])])
            },
            staticRenderFns: [function() {
                var _vm = this;
                var _h = _vm.$createElement;
                var _c = _vm._self._c || _h;
                return _c('div', {
                    staticClass: ["wrapper"]
                }, [_c('text', {
                    staticClass: ["button"]
                }, [_vm._v("这是存放在Assets中的js bundle")])])
            }]
        }
        module.exports.render._withStripped = true

        /***/
    })
    /******/
]);