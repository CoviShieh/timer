/**
 * Created by skz on 2016/6/16.
 */

define(['jquery'],function(){


/**
 * @description 工具
 * @class
 * @name GXX
 * @interface
 * @global
 * @type {GXX|*|{}}
 */
var GXX = window.GXX || {};

/**====================================================
 * ajax
 ======================================================*/
/**
 * @description ajax 请求工具
 * @name aJax
 * @class
 * @extend GXX
 * @type {{globalSetting: globalSetting, globalNoCache: globalNoCache, globalJsonDataType: globalJsonDataType, fetch: fetch, postJSON: postJSON}}
 */
GXX.aJax = {
    /**
     * @method
     * @name globalSetting
     * @memberof aJax
     * @description ajax请求的全局设置
     * @param {Object} setting	自定义设置对象
     * @example GXX.aJax.globalSetting(setting);
     */
    globalSetting : function(setting){
        $.ajaxSetting(setting);
    },
    /**
     * @method
     * @name globalNoCache
     * @memberof aJax
     * @description ajax请求不缓存页面url
     * @example GXX.aJax.globalNoCache();
     */
    globalNoCache : function(){
        $.ajaxSetting({cache : false});
    },
    /**
     * @method
     * @name globalJsonDataType
     * @memberof aJax
     * @description 设置ajax请求全局数据类型为json类型
     * @example GXX.aJax.globalJsonDataType();
     */
    globalJsonDataType : function(){
        $.ajaxSetting({dataType : "json"});
    },
    /**
     * @method
     * @name fetch
     * @memberof aJax
     * @description 同步从服务器获取数据
     * @param {String} url	请求url
     * @param {Object} data	请求参数
     * @param {String} methodType 请求方法类型 "GET"(默认) "POST" "PUT"..
     * @returns {Object} JSON格式数据
     */
    fetch : function(url,data,methodType){
        var value = "";
        if(!url) throw "url should not be empty!";
        data = !data ? {} : ($.type(data)==="object" ? data : {});
        $.ajax({
            async : false, // 同步请求
            type : methodType || "GET",
            data : data,
            url : url,
            success : function(data) {
                try{
                    value = data.indexOf('{') !== -1 ? JSON.parse(data) : data;
                }catch (e) {
                    throw GXX.Exception.throwException(e);
                }
            }
        });
        return value;
    },
    /**
     * @method
     * @name getJSON
     * @memberof aJax
     * @description 从服务器获取数据
     * @param {String} url	请求url
     * @param {Object} data	请求参数
     * @param {function} successCallback 请求成功回调
     * @param {function} errCallback     请求失败回调
     */
    postJSON: function(url,data,successCallback,cusAsync,errCallback){
    	if(cusAsync == false){
    		cusAsync = cusAsync;
    	}else{
    		cusAsync = true;
    	}
    	var option = {
                type : "POST",
                dataType: 'json',
                data:data,
                async:cusAsync,
                url : url,
                success :function(data, textStatus, jqXHR){
                    successCallback && successCallback(data, textStatus, jqXHR);
                },
                error: function(XMLHttpRequest, textStatus, errorThrown){
                    errCallback && errCallback(XMLHttpRequest, textStatus, errorThrown);
                }
            };
        $.ajax(option);
    }
};

/**====================================================
 * Base on bsTable
 ======================================================*/
/**
 * @description bsTable
 * @class
 * @name bsTable
 * @extend GXX
 * @type {{settings: {url: string, height: number, classes: string, method: string, undefinedText: string, pagination: boolean, cache: boolean, sidePagination: string, formatLoadingMessage: formatLoadingMessage, formatShowingRows: formatShowingRows, formatRecordsPerPage: formatRecordsPerPage, paginationPreText: string, paginationNextText: string, queryParams: queryParams}, initTable: initTable, refreshTableOptions: refreshTableOptions, getTableData: getTableData, insertRow: insertRow, removeRow: removeRow, onDblClickRow: onDblClickRow, getRowsCount: getRowsCount, getSelections: getSelections, regCheckBoxEvents: regCheckBoxEvents}}
 */
GXX.bsTable = {
    /**
     * @property settings
     * @name settings
     * @type variable
     * @description 默认设置项
     * @memberof bsTable
     */
    settings : {
        url : "",
        height: 450,
        classes : 'table table-hover table-striped',
        method : 'get',
        undefinedText : '暂无',
        pagination : true,
        cache : false,
        pageSize:15,
        sidePagination : 'server',
        contentType:'application/x-www-form-urlencoded',
        //escape : true,         //显示html标签 data-escape
        formatLoadingMessage : function() {
            return '加载中, 请稍等…';
        },
        formatShowingRows : function(pageFrom, pageTo, totalRows){
            return ' 总共 <b class="total-row">' + totalRows + '</b> 条记录';
        },
        formatRecordsPerPage : function(pageNumber) {
            return '每页显示 ' + pageNumber + ' 条';
        },
        paginationPreText : "<i class='glyphicon glyphicon-menu-left'></i>",
        paginationNextText : "<i class='glyphicon glyphicon-menu-right'></i>",
        queryParams : function(params) {
            return params;
        }
    },
    /**
     * @method
     * @name initTable
     * @memberof bsTable
     * @description 初始化表格
     * @param {String} target 表格ID "#table"
     * @param {Object} [settings] 自定义设置项
     */
    initTable : function(target,settings){
        this.settings = $.extend({},this.settings,settings || {});
        return $(target).bootstrapTable(this.settings);
    },
    /**
     * @method
     * @name refreshTableOptions
     * @memberof bsTable
     * @description 刷新Table设置项
     *   可以通过修改配置项来刷新表格.如：<br>
     *   <li>设置pageSize,更改页面显示条数;
     * 	 <li>url,从服务器中重新加载数据;
     * 	 <li>data 通过数据初始化表格
     * @param {String} target	target 表格ID "#table"
     * @param {Object} options	bootstrapTable设置项
     */
    refreshTableOptions : function(target,options){
        $(target).bootstrapTable('refreshOptions', options || {});
    },
    refreshTable: function(target,options){
    	$(target).bootstrapTable('refresh', options || {});
    },
    /**
     * @method
     * @name getTableData
     * @memberof bsTable
     * @description 获取表格数据
     * @param {String} target			target 表格ID "#table"
     * @param {Integer} currentPage		当前页的数据 true / false
     */
    getTableData : function(target,currentPage){
        return $(target).bootstrapTable('getData', currentPage || false);
    },
    /**
     * @method
     * @name insertRow
     * @memberof bsTable
     * @description 表格插入数据
     * @param {String} target 	target 表格ID "#table"
     * @param {Object} options	设置项<br><code>
     *     options = {
     *        index: index,	{Integer}	插入位置
     *        row : row		{Object}	插入数据
     *     }</code>
     */
    insertRow : function(target,options){
        $(target).bootstrapTable('insertRow', options);
    },
    /**
     * @method
     * @name removeRow
     * @memberof bsTable
     * @description 表格移除数据
     * @param {String} target 	target 表格ID "#table"
     * @param {Object} options	设置项<br><code>
     *     options = {
     *        field: id,	{Integer}	标识的字段
     *        values : ids	{Array}		标识值
     *     }</code>
     */
    removeRow : function(target,options){
        $(target).bootstrapTable('remove', options);
    },
    /**
     * @method
     * @name onDblClickRow
     * @memberof bsTable
     * @description 表格行双击事件
     * @param {String} target		表格选择器
     * @param {function} callback	事件回调函数
     */
    onDblClickRow : function(target,callback){
        $(target).on("dbl-click-row.bs.table",callback)
    },
    /**
     * @method
     * @name getRowsCount
     * @memberof bsTable
     * @description 获取表格记录总数
     * @param {String} target target 表格ID "#table"
     * @returns {number}
     */
    getRowsCount: function(target){
        return $(target).bootstrapTable('getData').length;
    },
    /**
     * @method
     * @name getSelections
     * @memberof bsTable
     * @description 获取被选中的表格记录
     * @param {String} target target target 表格ID "#table"
     * @returns {Array}
     */
    getSelections: function(target){
        /*return $.map($(target).bootstrapTable('getSelections'), function (row) {
         return row;
         });*/
        return $(target).bootstrapTable('getSelections');
    },
    /**
     * @method
     * @name regCheckBoxEvents
     * @memberof bsTable
     * @description checkbox 点击事件
     * @param target
     * @param fn
     */
    regCheckBoxEvents: function(target,fn){
        $(target).on('check.bs.table uncheck.bs.table ' +
            'check-all.bs.table uncheck-all.bs.table', function () {
            fn && fn();
        });
    }
};

/**====================================================
 * Base on artDialog for OCX
 ======================================================*/
/**
 * @description artDialog
 * @class
 * @name Dialog
 * @extend GXX
 * @type {{defaultOptions: {id: string, title: string, width: number, cancelValue: string, onshow: onshow, onclose: onclose, oniframeload: oniframeload}, alertStyle: {$alert: (*|jQuery|HTMLElement), $text: (*|jQuery), $success: (*|jQuery), $warning: (*|jQuery), $error: (*|jQuery)}, _getInstance: _getInstance, showModal: showModal, showDialog: showDialog, confirm: confirm, closeDialog: closeDialog, _getAlertContent: _getAlertContent, success: success, error: error, warning: warning, alert: alert, download: download}}
 */
GXX.Dialog = {
    /**
     * @property defaults
     * @name defaultOptions
     * @memberof Dialog
     * @description 默认配置项
     * @type {Object}
     * @private
     */
    defaultOptions : {
        id : '_artbox_id',
        title : '提示框',
        width : $(window).width() / 4 * 2,
        cancelValue: '关闭',
        onshow : function() {
            GXX.FrameUtil.createBgFrame(this.id);
        },
        onclose : function() {
            GXX.FrameUtil.hasBgFrame(this.id) && GXX.FrameUtil.removeBgFrame(this.id);
        },
        oniframeload: function(){

        }
    },
    /**
     * @private
     * @property style
     * @name alertStyle
     * @memberof Dialog
     * @description 弹窗样式
     */
    alertStyle : {
        $alert : $("<div class='sweet-alert'>"),
        $text : $('<p class="sa-tips">').text("操作提示"),
        $success : $('<div class="sa-icon sa-success">'),
        $warning : $('<div class="sa-icon sa-warn">'),
        $error : $('<div class="sa-icon sa-error">'),
        $info: $('<div class="sa-icon sa-info">')
    },

    /**
     * @name _getInstance
     * @memberof Dialog
     * @description 获取弹窗对象
     * @name getInstance
     * @param {Object} options artDialog配置项
     * @returns {*}
     * @private
     */
    _getInstance: function(options){
        var opt = $.extend(true,{},this.defaultOptions,options);
        GXX.FrameUtil.hasBgFrame(opt.id) && GXX.FrameUtil.removeBgFrame(opt.id);
        return dialog(opt);
    },
    /**
     * @method
     * @name showModal
     * @memberof Dialog
     * @description 包含钟罩层的模型弹窗框
     * @example <caption>Usage of showModal</caption>
     * GXX.Dialog.showModal({
	 *		id : id,
	 *		title : '车辆详情',
	 *		url : your url,
	 *		data : your data
	 *	})
     * @param {Object} options artDialog 配置项
     * @returns {Object} artDialog 实例对象
     */
    showModal : function(options){
        return this._getInstance(options).showModal();
    },
    /**
     * @method
     * @name showDialog
     * @memberof Dialog
     * @description 不包含钟罩层的模型弹窗框
     * @example <caption>Usage of showDialog</caption>
     * GXX.Dialog.showDialog({
	 *		id : id,
	 *		title : '车辆详情',
	 *		url : your url,
	 *		data : your data
	 *	})
     * @param {Object} options artDialog 配置项
     * @returns {Object} artDialog 实例对象
     */
    showDialog : function(options){
        return this._getInstance(options).show(options.anchor);
    },
    /**
     * @method
     * @name confirm
     * @memberof Dialog
     * @description 确认弹窗框
     * @param {Object} options 配置项
     * @param {String} options.msg 提示信息
     * @param {function} options.confirmCallback 确认回调
     * @param {function} options.cancelCallBack  取消回调
     * @returns {Object} artDialog 实例对象
     */
    confirm : function(options){
        var confirmDialog,
            msg = options.msg,
            confirmCallback = options.confirmCallback,
            cancelCallBack = options.cancelCallBack,
            dialogContent = this._getAlertContent("info",msg).prop("outerHTML"),
            setting = $.extend(true,{
                content: dialogContent,
                width: 410
            },options || {});

        confirmDialog = this.showDialog(setting);

        confirmDialog.button([{//按钮操作
        	value : '取消',
        	callback : function() {
        		cancelCallBack && cancelCallBack();
        	}
        },{
        	value : '确定',
        	autofocus: true,
        	callback : function() {
            	confirmCallback && confirmCallback();
        	}
        }]);
        return confirmDialog;
    },
    /**
     * @method
     * @name closeDialog
     * @memberof Dialog
     * @description 删除dialog
     * @param {Object} dialogObj
     */
    closeDialog : function(dialogObj){
        dialogObj && dialogObj.close().remove();
    },
    /**
     * @method
     * @name getAlertContent
     * @memberof Dialog
     * @description 获取弹框样式 HTML 内容 的jQuery对象
     * @param {String} [type] 弹框类型
     * @param {String} [msg]  描述信息
     * @returns {*}
     * @private
     */
    _getAlertContent : function(type,msg){
        var htmlMap = {
            "success": this.alertStyle.$success,
            "error": this.alertStyle.$error,
            "warning": this.alertStyle.$warning,
            'info': this.alertStyle.$info
        };
        type === 'info' ? this.alertStyle.$alert.addClass('sa-confirm') :  this.alertStyle.$alert.removeClass('sa-confirm');
        this.alertStyle.$alert.empty();
        this.alertStyle.$alert.append(htmlMap[type]);
        this.alertStyle.$alert.append(this.alertStyle.$text.html(msg));
        return this.alertStyle.$alert;
    },
    /**
     * @method
     * @name success
     * @memberof Dialog
     * @description 成功弹出框
     * @param {String} [msg]	提示信息	 默认"操作成功"
     * @param {Integer} [time]  弹框消失时间 默认1500
     */
    success : function (msg,time){
        var self = this;
        this.alert(msg, time, {
            content : self._getAlertContent("success",msg).prop("outerHTML")
        });
    },
    /**
     * @method
     * @name error
     * @memberof Dialog
     * @description 失败弹出框
     * @param {String} [msg]	提示信息	 默认"操作成功"
     * @param {Integer} [time]  弹框消失时间 默认1500
     */
    error : function (msg,time){
        var self = this;
        this.alert(msg, time, {
            content : self._getAlertContent("error",msg).prop("outerHTML")
        });
    },
    /**
     * @method
     * @name warning
     * @memberof Dialog
     * @description 警示弹出框
     * @param {String} [msg]	提示信息	 默认"操作成功"
     * @param {Integer} [time]  弹框消失时间 默认1500
     */
    warning: function(msg,time){
        var self = this;
        this.alert(msg, time, {
            content : self._getAlertContent("warning",msg).prop("outerHTML")
        });  
    },
    /**
     * @method
     * @name alert
     * @memberof Dialog
     * @description artDialog 的 Base 提示框
     * @param {String} [msg]	提示信息	 默认"操作成功"
     * @param {Integer} [time]  弹框消失时间 默认1500
     * @param {Object} [options]  弹框配置项
     */
    alert : function(msg,time,options){
        var self = this,
            dialog,
            opt = $.extend(true,{
                id: "alert",
                title : false,
                width : 'initial',
                content : self._getAlertContent("",msg).prop("outerHTML"),
                onshow: function(){
                    $('.ui-dialog-content').addClass('ui-dialog-content-spec');
                }
            
            },options || {});

        dialog = this.showDialog(opt);
        setTimeout(function() {
            self.closeDialog(dialog);
        }, time || 1500);
    },
    /**
     * @method
     * @name download
     * @memberof Dialog
     * @description 控件下载弹框
     * @param {String} msg	描述信息
     * @param {String} url	下载链接
     */
    download : function(msg,url){
        var self = this;
        url || this.warning('url 不能为空!');
        this.showDialog({
            title: '提示',
            width: 'inherit',
            content : self._getAlertContent("",msg || '下载?').prop("outerHTML"),
            button : [  {
            	value : '取消',
            	callback : function() {this.close();}
            },{
            	value : '确定',
                autofocus: true,
                callback : function() {dialog({url : url});}
            }]
        })
    }
};
/**====================================================
 * IFrameUtil
 ======================================================*/
/**
 * @description Iframe Util
 * @class
 * @name FrameUtil
 * @extend GXX
 * @type {{getChildWindow: getChildWindow, getChildDocument: getChildDocument, getParentWindow: getParentWindow, getParentDocument: getParentDocument, getParentWinDialog: getParentWinDialog, getParentWinDialogInfo: getParentWinDialogInfo, createBgFrame: createBgFrame, hasBgFrame: hasBgFrame, removeBgFrame: removeBgFrame}}
 */
GXX.FrameUtil = {
    /**
     * @method
     * @name getChildWindow
     * @memberof FrameUtil
     * @description 获取子窗口的window对象
     * @param {String|Array} frameNames iframe的name值或ID值
     * @returns {Object} window
     */
    getChildWindow : function(frameNames){
        var win,txt = 'window.';
        try{
            if(typeof frameNames === 'array'){
                $.each(frameNames,function(index,name){
                    txt += 'frames[' + name +']';
                });
                win = eval(txt);
            }else{
                win = window.frames[frameNames];
            }
        }catch (err){
            throw GXX.Exception.throwException(err) ;
        }
        return win;
    },
    /**
     * @method
     * @name getChildDocument
     * @memberof FrameUtil
     * @description 获取子窗口的document对象
     * @param {String|Array} iframeName iframe的name值或ID值
     * @returns {Object} document
     */
    getChildDocument : function(iframeName){
        var win = this.getChildWindow(iframeName);
        return win.document || win.contentDocument ;
    },
    /**
     * @method
     * @name getParentWindow
     * @memberof FrameUtil
     * @description 获取父窗口的window对象
     * @param {number} [level] 层级|包含多个window
     * @returns {parent|*|Window}
     */
    getParentWindow : function(level){
        var win = window.parent,
            i = 1;
        if(!level || level <= 1) return window.parent;
        for(; i < level; i++){
            win = win.parent;
        }
        return win;
    },
    /**
     * @method
     * @name getParentDocument
     * @memberof FrameUtil
     * @description 获取父窗口的document对象
     * @param level {number} [level] 层级|包含多个window
     * @returns {exports.browser.document|*|jsDump.parsers.document|defined.document|HTMLDocument|.Reloader.document}
     */
    getParentDocument : function(level){
        return this.getParentWindow(level).document;
    },
    /**
     * @method
     * @name getParentWinDialog
     * @memberof FrameUtil
     * @description 获取父窗口的 Dialog 实例对象
     * @param level
     * @returns {*}
     */
    getParentWinDialog: function(level){
        var parentWin = this.getParentWindow(level);
        return parentWin.dialog.get(window);
    },
    /**
     * @method
     * @name getParentWinDialogInfo
     * @memberof FrameUtil
     * @description 获取父窗口 Dialog 对象传递的数据
     * @param level
     * @returns {*}
     */
    getParentWinDialogInfo : function(level){
        return this.getParentWinDialog(level).data;
    },
    /**
     * @description 创建iframe
     * @param id
     * @memberof FrameUtil
     * @private
     */
    createBgFrame: function(id) {
        var iframeHtml = "<iframe src='about:blank' style=\"position:absolute; visibility:inherit; top:0px; left:0px;border:none;padding:0;margin:0;  height:100%;width:100%; z-index:-1; filter='progid:DXImageTransform.Microsoft.Alpha(style=0,opacity=0)'\"/>";
        $(".ui-dialog").append(iframeHtml);
    },
    /**
     * @description 是否包含iframe
     * @memberof FrameUtil
     * @private
     */
    hasBgFrame : function(id){
        var iframes = $("iframe[name='"+id+"']"),
            len = iframes.length;
        if(len > 0) return true;
        return false;
    },
    /**
     * @private
     * @memberof FrameUtil
     * @description 移除iframe
     * @param {String} id   元素ID
     */
    removeBgFrame : function(id){
        $("iframe[name='"+id+"']").remove();
    }
};
/**====================================================
 * Event Handler
 ======================================================*/
/**
 * @description event handler
 * @class
 * @name EvtHandler
 * @extend GXX
 * @type {{stopBubble: stopBubble, stopDefault: stopDefault}}
 */
GXX.EvtHandler = {
    /**
     * @method
     * @name stopBubble
     * @memberof EvtHandler
     * @description 阻止事件冒泡
     * @param {Object} e 事件对象
     */
    stopBubble: function(e){
        e && e.stopPropagation ? e.stopPropagation()/*W3C*/: window.event.cancelBubble = true/*IE*/;
    },
    /**
     * @method
     * @name stopDefault
     * @memberof EvtHandler
     * @description 阻止浏览器默认行为
     * @param {Object} e 事件对象
     * @returns {Boolean}
     */
    stopDefault: function(e){
        e && e.preventDefault ? e.preventDefault()/*W3C*/: window.event.returnValue = false/*IE*/;
        return false;
    }
};
/**====================================================
 * Download file
 ======================================================*/
/**
 * @description Download
 * @class
 * @name Download
 * @extend GXX
 * @type {{downloadImage: downloadImage}}
 */
GXX.Download = {
    downloadImage: function(actionUrl,filePath,fileName){
        if(filePath){
            new Downloadit({
                actionUrl: actionUrl,
                pathParam: '?filePath="' + filePath + '"&fileName="' + fileName +'"'
            }).download();
        }else{
            //Utils.alert("图片损坏");
        }
    }
};
/**
 * @description Download Constructor
 * @param options
 * @constructor
 */
function Downloadit(options){
    this.defaults = $.extend(true,{
        actionUrl: '',
        pathParam: '', // '?filePath="xxx/xxxx.jpg"&fileName="ds.jpg"',
        // modern
        isOpenWin: false
    },options)
}
Downloadit.prototype = {
    constructor:Downloadit,
    isIE: function(){
        return (!!window.ActiveXObject || "ActiveXObject" in window) ? true : false;
    },
    log: function(msg){
        window.console && window.console.log(msg);
    },
    getPath: function(){
        var postUrl;
        postUrl = this.defaults.pathParam ? this.defaults.actionUrl + this.defaults.pathParam : this.defaults.actionUrl;
        return postUrl;
    },
    parsePathParam: function(){
        var path,
            filePath,
            fileName,
            filePathObj = {},
            fileNameObj = {};
        if(this.defaults.pathParam){
            path = this.defaults.pathParam.split('&');
            filePath = path[0].indexOf('?') != -1 ? path[0].slice(path[0].indexOf('?')+1) : path[0];
            fileName = path[1];
            filePathObj.pathKey = filePath.split('=')[0];
            filePathObj.pathValue = filePath.split('=')[1];
            fileNameObj.nameKey = fileName.split('=')[0];
            fileNameObj.nameValue = fileName.split('=')[1];
            return [filePathObj,fileNameObj];
        }
    },
    createAnchorTag: function(){
        var $a = $("<a>").attr({
            "href": this.getPath(),
            "download": this.parsePathParam()[1].nameValue
        });
        this.defaults.isOpenWin && $a.attr('target','_blank');
        $a.appendTo("body");
        return $a;
    },
    createIframeTag: function(){
        var exportImg;

        exportImg =  $('<iframe style="" id="exportImg" name="exportImg" width="0" height="0" src="about:blank"></iframe>');
        exportImg.attr('src',this.getPath());
        exportImg.appendTo("body");
        return exportImg;
    },
    createIframeForm: function(){
        var exportImg,
            $form,
            $input;
        exportImg =  $('<iframe style="" id="exportImg" name="exportImg" width="0" height="0" src="about:blank"></iframe>');
        $form = $('<form method="post" id="multiUrl">')
            .attr('action',this.defaults.actionUrl);
        $input = $('<input type="hidden">')
            .attr({
                'name': this.parsePathParam()[0].pathKey,
                'value': this.parsePathParam()[1].pathValue
            });
        $form.append($input).end().appendTo(exportImg);
        exportImg.appendTo("body");
        return exportImg;
    },
    isSingleUrl: function(){
        var pathParam;
        if(this.defaults.actionUrl && (pathParam = this.parsePathParam())){
            if(typeof pathParam[0].pathValue === 'string' && pathParam[0].pathValue.split(',').length <= 1){
                return true;
            }else if($.type(pathParam[0].pathValue) === 'array' && pathParam[0].pathValue.length <= 1){
                return true;
            }
        }
        return false;
    },
    saveFile: function(){
        if ($("#exportImg").attr('src') != "about:blank"){
            try {
                window.frames["exportImg"].document.execCommand("SaveAs");
            } catch (e) {
                this.log(e);
            }
        }
    },
    loadForIE: function(){
        var exportImg = this.createIframeTag(),
            that = this;
        exportImg.load(function(){
            var _export = $(this);
            _export.attr('src') != that.getPath && _export.attr('src',that.getPath());
            that.saveFile();
        });
        window.setTimeout(function(){
            exportImg.remove();
        },500);
    },
    loadForModern: function(){
        var $a = this.createAnchorTag();
        $a[0].click();
        $a.remove();
        delete $a[0];
    },
    download: function(){
        var $frame;
        if(this.isSingleUrl()){
            this.isIE() ? this.loadForIE() : this.loadForModern();
        }else{
            $frame = this.createIframeForm();
            $('form',$frame).submit();
            $frame.remove();
        }
    }
};
/**====================================================
 * Simple LazyLoad image | trigger by load event
 ======================================================*/
/**
 * @class
 * @name LazyLoad
 * @extend GXX
 * @description LazyLoad
 * @type {{loadImage: loadImage}}
 */
GXX.LazyLoad = {
    /**
     * @method
     * @memberof LazyLoad
     * @description 图片懒加载
     * @param {String} 图片url
     */
    loadImage: function(url){
        new LazyLoad({
            imageUrl: url
        });
    }
};
/**
 * @description LazyLoad Function
 * @param options
 * @constructor
 */
function LazyLoad(options){
    this.init(options);
}
LazyLoad.prototype = {
  constructor: LazyLoad,
  defaults: {
     target: document,
     imageUrl: '',
     loading: 'data:image/gif;base64,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',
     errUrl: 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAADMAAAAmCAMAAABeZFMwAAAABGdBTUEAALGPC/xhBQAAACBjSFJNAAB6JgAAgIQAAPoAAACA6AAAdTAAAOpgAAA6mAAAF3CculE8AAAACXBIWXMAAAsSAAALEgHS3X78AAAA2FBMVEUAAABmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZma4ycv6AAAASHRSTlMAAQIDBAUHCAkKCwwNDxAREhMUFRYXGBkaGxwdHh8gISIjJCUmJygpKissLS4vMDEyMzQ1Njc4OTo7PD0+P0BBQkNERUdJSkvbdElMAAAAAWJLR0QAiAUdSAAAAbVJREFUOMvN1FlX2mAUheFXZgRBBikpU6giFVEzz58JpdX//496URkTArnrvso+az0rycnAwrBicb8BcO9YSVlgWPNYgh8AedOYJ0QzsGfEslwC1Fd9EtJ3sebx8VwBaK1aSWbgJJoHE6AnyhmM7BYB2SSDGYkK8PqcxUhRHa7s+90kf507Y7pRA6rhcDcwLL2ZbhphC9rrzqbfvtua69VSTU10YSJK2z36iqoEs1RTCrowiaqbPvNUVfHnqSbnz+D612jT25Gp2qKRahj9GcOztu1Dy7Wk9B2A/Dmm+ftmd7WNIucM088+3jTLe9AB+aM/1i83UuR2YBq9eK1LjSS0UMnDOAzkC40kdE8pA4xWau4iIwndUwtfK/7oQbGZP2P2CTy+QXutlVLNIQG5QUF5N0op5phABYaBq1dOmkFwTABqnuGYlRNmILQj8joEaPuGvUMHJk6e3oMRQGcf7ZvvQvMPSOFnoIb//gkd37SNaszkHMd7OzjL3VoXk6/jlmdGTzFz9Ri+HD71mrElcOsGdwn3c3O8sGJ9r5SrZ7+f0/nPjfOQ0fQ8LG2QLUubhelki734C3PeZaqIHokbAAAAAElFTkSuQmCC'
  },
  img: null,
  createImage: function(){
      return new Image();
  },
  setImagePath: function(opts,path,type){
      var $element;
      $element = opts.target ? $('img.lazy',opts.target) : $('img.lazy');
      if(type == "loading"){
          $element.removeClass('crush')
              .addClass('loading');
      }else if(type == "error"){
          $element.removeClass('loading')
              .addClass('crush');
      }else{
          $element.removeClass('loading crush');
      }
      if($element.is('img')){
          $element.attr('src',path);
      }
      return $element;
  },
  beforeHook: function(opts){
     this.setImagePath(opts,opts.loading,'loading');
  },
  before: function(opts){
    this.beforeHook(opts.element);
    opts.before && opts.before();
  },
  afterHook: function(){

  },
  after: function(){

  },
  success: function(opts){
      this.setImagePath(opts,opts.imageUrl,'success');
  },
  error: function(opts){
      this.setImagePath(opts,opts.errUrl,'error');
  },
  loadHandle: function(opts){
      var that = this;
      $(this.img).load(function(){
          opts.success && opts.success.call(that.img,opts);
          that.img = that.img.onload = that.img.onerror = null;
      });
      that.img.src = opts.imageUrl;
  },
  errorHandle: function(opts){
      var that = this;
      $(that.img).error(function(){
          opts.error && opts.error.call(that.img,opts);
          that.img = that.img.onload = that.img.onerror = null;
      })
  },
  init: function(settings){
      var opts = $.extend(true,{},this.defaults,settings);
      this.img = this.createImage();
      this.before(opts);
      this.loadHandle(opts);
      this.errorHandle(opts);
      this.after(opts);
  }
};
/**====================================================
 * Exception Handler
 ======================================================*/
/**
 * @class
 * @name Exception
 * @extend GXX
 * @description Exception异常处理
 * @type {{throwException: throwException}}
 */
GXX.Exception = {
    /**
     * @method
     * @name throwException
     * @memberof Exception
     * @description 抛出异常
     * @example <caption>Usage of throwException</caption>
     * GXX.Exception.throwException(err,msg)
     * @param err
     * @param msg
     * @returns {Error}
     */
  throwException: function(err,msg){
     var stack = "\n Path: "
               + (err.fileName || window.location)
               + "\n Line/Col: "
               + (err.lineNumber || '') + '/'
               + (err.columnNumber || '')
               + "\n errType: "
               + err.toString().slice(0,err.toString().indexOf(':'))
               + '\n Reason: '
               + err.toString().slice(err.toString().indexOf(':')+1)
               + '\r\r'
               + (msg || '');

     throw new Error(stack);
  },
  print: function(e){
      window.console && window.console.trace(e);
  }
};
/**====================================================
 * Array Utils
 ======================================================*/
/**
 * @class
 * @name Array
 * @extend GXX
 * @description 数组工具
 * @type {{sort: sort, group: group}}
 */
GXX.Array = {
    /**
     * @name sort
     * @method
     * @memberof Array
     * @description 数组排序
     * @example <caption>Usage of sort</caption>
     * var list = [1,2,4,3,1,4,1,2,7,4,3,8,5,3];
     * GXX.Array.sort(list);
     * result: -->  [1,1,1,2,2,3,3,3,4,4,4,5,7,8];
     * @param {Array} list
     * @param {String} [order]	asc desc
     */
    sort: function(list,order){
        list.sort(function(a,b){
            var type = {
                "asc": a - b,
                "desc": b - a
            };
            return type[(order || 'asc')];
        })
    },
    /**
     * @name group
     * @method
     * @memberof Array
     * @description 对相同元素分组
     * @example <caption>Usage of group</caption>
     * var list = [1,2,4,3,1,4,1,2,7,4,3,8,5,3];
     * GXX.Array.group(list);
     * result: -->  [[1,1,1],[2,2],[3,3,3],[4,4,4],[5],[7],[8]];
     * @param {Array} list
     * @returns {Array}
     */
    group: function(list){
        var sameGroup = [],
            i = 0,
            n = 0,
            len = list.length;
        this.sort(list);
        for (; i < len; i++) {
            if (list[i] != list[i + 1]) {
                sameGroup.push(list.slice(n, i + 1));
                n = i + 1;
            }
        }
        return sameGroup;
    },
    /**
     * @method
     * @name clone
     * @memberof Array
     * @description 克隆数组
     * @example <caption>Usage of clone</caption>
     * <h5>Usage 1</h5>
     * var list = [1,2,3,4];
     * GXX.Array.clone(list);
     * result: -->  [1,2,3,4];
     * result.length == 4
     * <h5>Usage 2</h5>
     * var list = [];
     * list['key1'] = 3;
     * list['key2'] = 4;
     * GXX.Array.clone(list);
     * result: -->  ['key1'==>3,'key2'==>4];
     * result.length == 0
     * <h5>Usage 3</h5>
     * var list = [0,1];
     * list['key1'] = 3;
     * list['key2'] = 4;
     * GXX.Array.clone(list);
     * result: -->  [0,1,'key1'==>3,'key2'==>4];
     * result.length == 2
     * @param {Array} list
     * @returns {*}
     */
    clone: function(list){
        return new Array(list)[0];
    }
};

/**====================================================
 * Detection Utils
 ======================================================*/
/**
 * @class
 * @name Detect
 * @extend GXX
 * @description 检测工具
 * @type {{isIE: isIE, ieLessThan: ieLessThan}}
 */
GXX.Detect = {
    /**
     * @name isIE
     * @method
     * @memberof Detect
     * @description 是否IE
     * @returns {boolean}
     */
    isIE: function(){
        return (!!window.ActiveXObject || "ActiveXObject" in window) ? true : false;
    },
    /**
     * @name ieLessThan
     * @method
     * @memberof Detect
     * @description IE版本是否低于ver
     * @param {number} ver IE版本（10,11）
     * @returns {boolean}
     */
    ieLessThan: function(ver){
        var ua = navigator.userAgent,
            isIE = ua.indexOf('MSIE') > -1,
            v = isIE ? /\d+/.exec(ua.split(';')[1]) : 0;
        return parseInt(v) < ver;
    }
};

/**====================================================
 * Moment Utils
 ======================================================*/
GXX.Moment = (function(){
    function Moment(datetime){
        this.date = new Date(datetime);
        this.now = function(){
            return this.date;
        };
    }
    function MomentWare(){}
    MomentWare.prototype = Date.prototype;
    MomentWare.constructor = MomentWare;

    Moment.prototype = new MomentWare();
    Moment.constructor = Moment;

    Moment.prototype.defaults = {
        timeStamp: {
            S: 1000,
            s: 1000 * 1,
            m: 1000 * 1 * 60,
            h: 1000 * 1 * 60 * 60,
            d: 1000 * 1 * 60 * 60 * 24,
            w: 1000 * 1 * 60 * 60 * 24 * 7,
            M: 1000 * 1 * 60 * 60 * 24 * 30,
            y: 1000 * 1 * 60 * 60 * 24 * 30 * 12
        }
    };
    Moment.prototype.today = function(){
        return new Moment(new Date()).toString();
    };
    Moment.prototype.yesterday = function(){
      return new Moment(new Date().getDate()+1).toString();
    };
    Moment.prototype.tomorrow = function(){

    };
    Moment.prototype.period = function(){

    };
    Moment.prototype.add = function(){

    };
    Moment.prototype.plus = function(timeStr,type,streme){
        return new Moment(this.__plus(timeStr,type,streme)).toString();
    };
    /**
     * @description 日期相差 num [年|月|日|时|分|秒|星期]
     * @param timeStr1
     * @param timeStr2
     * @param type [y|M|d|h|m|s|w]
     * @returns {number}
     */
    Moment.prototype.gap = function(timeStr1,timeStr2,type){
      return this.minutes(timeStr1,timeStr2) / this.defaults.timeStamp[type];
    };
    /**
     * @description 日期相减
     * @param timeStr1
     * @param timeStr2
     * @returns {number}
     */
    Moment.prototype.minutes = function(timeStr1,timeStr2){
        var t1 = new Moment(timeStr1).now().getTime(),
            t2 = new Moment(timeStr2).now().getTime();
        return t1 - t2;
    };
    Moment.prototype._plus = function(timeStamp1,timeStamp2){
        return timeStamp1 + timeStamp2;
    };
    Moment.prototype.__plus = function(timeStr,type,streme){
        var plusMillis,args;
        typeof timeStr !== 'number' && (timeStr = new Moment(timeStr).now().getTime());
        plusMillis = timeStr + this.defaults.timeStamp[type] * streme;
        /*args = arguments.slice(0,arguments.length);
        if(args.length === 2){
            plusMillis = this._plus(args[0],args[1]);
        }else{
            !typeof timeStr === 'number' && (timeStr = new Moment(timeStr).getTime());
            plusMillis = timeStr + this.defaults.timeStamp[type] * streme;
        }*/
        return plusMillis;
    };
    Moment.prototype.clone = function(){
      return new Moment(this);
    };
    Moment.prototype.toString = function(fmt){
        fmt = fmt  || 'yyyy-MM-dd hh:mm:ss';
        var o ={
            "M+" : this.date.getMonth() + 1,
            "d+" : this.date.getDate(),
            "h+" : this.date.getHours(),
            "m+" : this.date.getMinutes(),
            "s+" : this.date.getSeconds(),
            "q+" : Math.floor((this.date.getMonth() + 3) / 3),
            "S" : this.date.getMilliseconds()
        };
        if (/(y+)/.test(fmt))
            fmt = fmt.replace(RegExp.$1, (this.date.getFullYear() + "")
                .substr(4 - RegExp.$1.length));
        for ( var k in o)
            if (new RegExp("(" + k + ")").test(fmt))
                fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k])
                    : (("00" + o[k]).substr(("" + o[k]).length)));
        return fmt;
    };

    return new Moment();
})();
/**====================================================
 * JSON Utils
 ======================================================*/
/**
 * @class
 * @name JSON
 * @extend GXX
 * @description JSON工具
 * @type {{stringify: stringify}}
 */
GXX.JSON = {
    /**
     * @name stringify
     * @method
     * @memberof JSON
     * @description 处理IE8下 JSON.stringify(data), data 包含中文时出现乱码的问题
     * @param {String} data
     * @returns {string}
     */
    stringify: function(data){
       var objStr = '';
       if(GXX.Detect.ieLessThan(9)){
           eval("objStr = '" + JSON.stringify(data) + "';");
       }else{
           objStr = JSON.stringify(data);
       }
        return objStr;
    },
    /**
     * @name isJsonStr
     * @method
     * @memberof JSON
     * @description 是否JSON格式字符串
     * @param {String} str
     * @returns {boolean}
     */
    isJsonStr: function(str){
        try{
            JSON.parse(str);
        }catch(e){
            return false;
        }
        return true;
    }
};
GXX.HTMLParser = {
    escapeHtml: function(html){
        return html.replace(/</g,'&lt;').replace(/>/g,'&gt;');
    },
    grepHtml: function(html){
       // return html.replace(/(<[\w+]>).*(<\/[\w+]+>)]/g,'');
    }
};
(function(GXX){
    var cache = {};
    /**
     * @description Tmpl
     * @param str
     * @param data
     * @returns {*}
     */
    GXX.Tmpl = function tmpl(str, data){
        // Simple JavaScript Templating
        // John Resig – http://ejohn.org/ – MIT Licensed
        // Figure out if we're getting a template, or if we need to
        // load the template - and be sure to cache the result.
        var fn = !/\W/.test(str) ?
            cache[str] = cache[str] || tmpl(document.getElementById(str).innerHTML) :

            // Generate a reusable function that will serve as a template
            // generator (and which will be cached).
            new Function("obj",
                    "var p=[],print=function(){p.push.apply(p,arguments);};" +
                    // Introduce the data as local variables using with(){}
                    "with(obj){p.push('" +
                    // Convert the template into pure JavaScript
                    str
                        .replace(/[\r\t\n]/g, " ")
                        .split("<%").join("\t")
                        .replace(/((^|%>)[^\t]*)'/g, "$1\r")
                        .replace(/\t=(.*?)%>/g, "',$1,'")
                        .split("\t").join("');")
                        .split("%>").join("p.push('")
                        .split("\r").join("\\'")
                    + "');}return p.join('');");

        // Provide some basic currying to the user
        return data ? fn( data ) : fn;
    };
})(GXX);

    return GXX;
});