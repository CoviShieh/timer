/**
 * 基础jq小工具， 用于简化js逻辑
 * 
 * @ author lcq(latest)
 * @ update 2018.3.1
 */
;
(function(global) {
    function factory() {
        /**
         * 封装的一些小工具
         * @param {Object} $
         */
        (function($) {
            "use strict";
            $.fn.transitionEnd = function(callback) {
                var events = ['webkitTransitionEnd', 'transitionend', 'oTransitionEnd', 'MSTransitionEnd', 'msTransitionEnd'],
                    i, dom = this;

                function fireCallBack(e) {
                    /*jshint validthis:true */
                    if (e.target !== this) return;
                    callback.call(this, e);
                    for (i = 0; i < events.length; i++) {
                        dom.off(events[i], fireCallBack);
                    }
                }
                if (callback) {
                    for (i = 0; i < events.length; i++) {
                        dom.on(events[i], fireCallBack);
                    }
                }
                return this;
            };

            $.supportTouch = (function() {
                var support = {
                    touch: !!(('ontouchstart' in window) || window.DocumentTouch && document instanceof window.DocumentTouch)
                };
                return support;
            })();

            $.touchEvents = {
                start: $.support.touch ? 'touchstart' : 'mousedown',
                move: $.support.touch ? 'touchmove' : 'mousemove',
                end: $.support.touch ? 'touchend' : 'mouseup'
            };

            $.getTouchPosition = function(e) {
                e = e.originalEvent || e; //jquery wrap the originevent
                if (e.type === 'touchstart' || e.type === 'touchmove' || e.type === 'touchend') {
                    return {
                        x: e.targetTouches[0].pageX,
                        y: e.targetTouches[0].pageY
                    };
                } else {
                    return {
                        x: e.pageX,
                        y: e.pageY
                    };
                }
            };

            $.fn.scrollHeight = function() {
                return this[0].scrollHeight;
            };

            $.fn.transform = function(transform) {
                for (var i = 0; i < this.length; i++) {
                    var elStyle = this[i].style;
                    elStyle.webkitTransform = elStyle.MsTransform = elStyle.msTransform = elStyle.MozTransform = elStyle.OTransform = elStyle.transform = transform;
                }
                return this;
            };
            $.fn.transition = function(duration) {
                if (typeof duration !== 'string') {
                    duration = duration + 'ms';
                }
                for (var i = 0; i < this.length; i++) {
                    var elStyle = this[i].style;
                    elStyle.webkitTransitionDuration = elStyle.MsTransitionDuration = elStyle.msTransitionDuration = elStyle.MozTransitionDuration = elStyle.OTransitionDuration = elStyle.transitionDuration = duration;
                }
                return this;
            };

            $.getTranslate = function(el, axis) {
                var matrix, curTransform, curStyle, transformMatrix;

                // automatic axis detection
                if (typeof axis === 'undefined') {
                    axis = 'x';
                }

                curStyle = window.getComputedStyle(el, null);
                if (window.WebKitCSSMatrix) {
                    // Some old versions of Webkit choke when 'none' is passed; pass
                    // empty string instead in this case
                    transformMatrix = new WebKitCSSMatrix(curStyle.webkitTransform === 'none' ? '' : curStyle.webkitTransform);
                } else {
                    transformMatrix = curStyle.MozTransform || curStyle.OTransform || curStyle.MsTransform || curStyle.msTransform || curStyle.transform || curStyle.getPropertyValue('transform').replace('translate(', 'matrix(1, 0, 0, 1,');
                    matrix = transformMatrix.toString().split(',');
                }

                if (axis === 'x') {
                    //Latest Chrome and webkits Fix
                    if (window.WebKitCSSMatrix)
                        curTransform = transformMatrix.m41;
                    //Crazy IE10 Matrix
                    else if (matrix.length === 16)
                        curTransform = parseFloat(matrix[12]);
                    //Normal Browsers
                    else
                        curTransform = parseFloat(matrix[4]);
                }
                if (axis === 'y') {
                    //Latest Chrome and webkits Fix
                    if (window.WebKitCSSMatrix)
                        curTransform = transformMatrix.m42;
                    //Crazy IE10 Matrix
                    else if (matrix.length === 16)
                        curTransform = parseFloat(matrix[13]);
                    //Normal Browsers
                    else
                        curTransform = parseFloat(matrix[5]);
                }

                return curTransform || 0;
            };
            $.requestAnimationFrame = function(callback) {
                if (window.requestAnimationFrame) return window.requestAnimationFrame(callback);
                else if (window.webkitRequestAnimationFrame) return window.webkitRequestAnimationFrame(callback);
                else if (window.mozRequestAnimationFrame) return window.mozRequestAnimationFrame(callback);
                else {
                    return window.setTimeout(callback, 1000 / 60);
                }
            };

            $.cancelAnimationFrame = function(id) {
                if (window.cancelAnimationFrame) return window.cancelAnimationFrame(id);
                else if (window.webkitCancelAnimationFrame) return window.webkitCancelAnimationFrame(id);
                else if (window.mozCancelAnimationFrame) return window.mozCancelAnimationFrame(id);
                else {
                    return window.clearTimeout(id);
                }
            };

            $.fn.join = function(arg) {
                return this.toArray().join(arg);
            }

            //解除绑定的事件
            $.off = function(event) {
                $(event.target).siblings().each(function(index, el) {
                    $(this).off();
                });
            };

            // 深度遍历
            $.deepCopy = function(source) {
                // buildInObject, 用于处理无法遍历Date等对象的问题
                var buildInObject = {
                    '[object Function]': 1,
                    '[object RegExp]': 1,
                    '[object Date]': 1,
                    '[object Error]': 1,
                    '[object CanvasGradient]': 1
                };
                var result = source;
                var i;
                var len;
                if (!source ||
                    source instanceof Number ||
                    source instanceof String ||
                    source instanceof Boolean
                ) {
                    return result;
                } else if (source instanceof Array) {
                    result = [];
                    var resultLen = 0;
                    for (i = 0, len = source.length; i < len; i++) {
                        result[resultLen++] = $.deepCopy(source[i]);
                    }
                } else if ('object' == typeof source) {
                    if (buildInObject[Object.prototype.toString.call(source)] ||
                        source.__nonRecursion
                    ) {
                        return result;
                    }
                    result = {};
                    for (i in source) {
                        if (source.hasOwnProperty(i)) {
                            result[i] = $.deepCopy(source[i]);
                        }
                    }
                }
                return result;
            }

            $.isString = function(str) {
                return (typeof str == 'string') && str.constructor == String;
            }

            $.isNumber = function(num) {
                return (typeof num == 'number' && !isNaN(num));
            }

            $.isObject = function(obj) {
                return (typeof obj == 'object') && obj.constructor == Object;
            }

            $.isNull = function isNull(arg1) {
                return !arg1 && arg1 !== 0 && typeof arg1 !== "boolean" ? true : false;
            }

            $.isFunction = function(f) {
                return (typeof f == 'function') && Object.prototype.toString.call(f) === '[object Function]';
            }

            $.uuid = function(len, radix) {
                var chars = '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz'.split('');
                var uuid = [],
                    i;
                radix = radix || chars.length;
                if (len) {
                    // Compact form
                    for (i = 0; i < len; i++) uuid[i] = chars[0 | Math.random() * radix];
                } else {
                    // rfc4122, version 4 form
                    var r;

                    // rfc4122 requires these characters
                    uuid[8] = uuid[13] = uuid[18] = uuid[23] = '-';
                    uuid[14] = '4';

                    // Fill in random data.  At i==19 set the high bits of clock sequence as
                    // per rfc4122, sec. 4.1.5
                    for (i = 0; i < 36; i++) {
                        if (!uuid[i]) {
                            r = 0 | Math.random() * 16;
                            uuid[i] = chars[(i == 19) ? (r & 0x3) | 0x8 : r];
                        }
                    }
                }

                return uuid.join('');
            }

            $.isArrayEqual = function(a, b) {
                if (a.length !== b.length) {
                    return false;
                }

                var length = a.length;

                for (var i = 0; i < length; i++) {
                    if (!$.compare(a[i], b[i])) {
                        return false;
                    }
                }

                return true;
            }

            $.isObjectEqual = function(a, b) {
                var keya = Object.keys(a);
                var keyb = Object.keys(b);

                if (keya.length !== keyb.length) {
                    return false;
                }

                return keya.every(function(key) {
                    if (!$.compare(a[key], b[key])) {
                        return false;
                    }
                    return true;
                });
            }

            $.compare = function(a, b) {
                if (a === b) {
                    return true;
                }

                if (typeof a !== typeof b || a === null || b === null) {
                    return false;
                }

                if (Array.isArray(a)) {
                    if (!Array.isArray(b)) {
                        return false;
                    }
                    return $.isArrayEqual(a, b);
                }

                if (typeof a === "object") {
                    return $.isObjectEqual(a, b);
                }

                return false;
            }

            /*1.用浏览器内部转换器实现html转码*/
            $.htmlEncode = function(html) {
                //1.首先动态创建一个容器标签元素，如DIV
                var temp = document.createElement("div");
                //2.然后将要转换的字符串设置为这个元素的innerText(ie支持)或者textContent(火狐，google支持)
                (temp.textContent != undefined) ? (temp.textContent = html) : (temp.innerText = html);
                //3.最后返回这个元素的innerHTML，即得到经过HTML编码转换的字符串了
                var output = temp.innerHTML;
                temp = null;
                return output;
            }

            /*2.用浏览器内部转换器实现html解码*/
            $.htmlDecode = function(text) {
                //1.首先动态创建一个容器标签元素，如DIV
                var temp = document.createElement("div");
                //2.然后将要转换的字符串设置为这个元素的innerHTML(ie，火狐，google都支持)
                temp.innerHTML = text;
                //3.最后返回这个元素的innerText(ie支持)或者textContent(火狐，google支持)，即得到经过HTML解码的字符串了。
                var output = temp.innerText || temp.textContent;
                temp = null;
                return output;
            }

            /*3.用正则表达式实现html转码*/
            $.htmlEncodeByRegExp = function(str) {
                var s = "";
                if (str.length == 0) return "";
                s = str.replace(/&/g, "&amp;");
                s = s.replace(/</g, "&lt;");
                s = s.replace(/>/g, "&gt;");
                s = s.replace(/ /g, "&nbsp;");
                s = s.replace(/\'/g, "&#39;");
                s = s.replace(/\"/g, "&quot;");
                return s;
            }
            
            /*4.用正则表达式实现html解码*/
            $.htmlDecodeByRegExp = function(str) {
                var s = "";
                if (str.length == 0) return "";
                s = str.replace(/&amp;/g, "&");
                s = s.replace(/&lt;/g, "<");
                s = s.replace(/&gt;/g, ">");
                s = s.replace(/&nbsp;/g, " ");
                s = s.replace(/&#39;/g, "\'");
                s = s.replace(/&quot;/g, "\"");
                return s;
            }

        })($);
    }

    if (typeof module !== 'undefined' && typeof exports === 'object') {
        // 能够满足Node.js的需求
        module.exports = factory();
    } else if (typeof define === 'function' && (define.cmd || define.amd)) {
        // 当没有上述全局变量，且有define全局变量时，我们认为是AMD或CMD，可以直接将factory传入define;     
        // 注意：CMD其实也支持return返回模块接口，所以两者可以通用    
        define(factory);
    } else {
        // 最后是script标签全局引入
        // 我们可以将模块放在window上为了模块内部在浏览器和Node.js中都能使用全局对象，我们可以做此判断
        factory();
    }

})(typeof window !== 'undefined' ? window : global);