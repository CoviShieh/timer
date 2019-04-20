/**
 * 配置文件,配置模块的基础路径和js路径
 * 所有js路径必须要在这里配置和统一管理
 * @author zxl
 */
require.config({
	baseUrl:contextPath+'/resources/',
	paths:{
		'jquery':"uilib/jquery/jquery-1.11.2.min",
		'form':"uilib/jquery/jquery-form",
		'bootstrap':"uilib/bootstrap/dist/js/bootstrap.min",
		'bootstrap-table':"uilib/bootstrapTable/ext/bootstrap-table",
        'bootstrap-editable': "uilib/bootstrap-editable/js/bootstrap-editable",
        'bootstrap-table-editable': "uilib/bootstrap-editable/js/bootstrap-table-editable",
        'datapicker':"uilib/datapicker/bootstrap-datepicker",
		'dialog': 'uilib/artDialog/dist/dialog-plus-min',
		'fancybox':'uilib/fancyBox/source/jquery.fancybox',
		'base': 'js/base/base',
		'GXX': 'js/base/Gxx',
        'md5': 'uilib/md5/md5',
		'path': 'js/base/path',
		'layer':'uilib/layer/layer',
		'autocompleter':'uilib/autocompleter/jquery.autocompleter.min',
		'drawline':'uilib/drawLine/drawline',
		'OCXComm':'js/common/OCXComm',
		'smartZoom':'uilib/zoom/e-smart-zoom-jquery.min',
		'mousewheel':'uilib/zoom/jquery.mousewheel.min',
		
		'storeCurd':'js/repertoryMgr/storeMgr/modules/curd',
		'smokeCurd':'js/repertoryMgr/smokeMgr/modules/curd',
		'WdatePicker':'uilib/My97DatePicker/WdatePicker',
		'storeLink':'js/repertoryMgr/smokeMgr/modules/storeLink',
		'userLink':'js/repertoryMgr/smokeMgr/modules/userLink',
		'userSmsLink':'js/repertoryMgr/searchUser/modules/userLink',
		
		'json':'uilib/jquery/json2',
		'mCustomScrollbar':'uilib/mCustomScrollbar/jquery.mCustomScrollbar.concat.min',
		'kqpCurd':'js/repertoryMgr/kaoqinPlan/modules/curd',
		'reportCurd':'js/repertoryMgr/smokeReport/modules/curd',
		'smokeMgrReportCurd':'js/repertoryMgr/smokeMgrReport/modules/curd',
		'smokeMonitorReportCurd':'js/repertoryMgr/smokeMonitorReport/modules/curd',
		'smokeAggregateReportCurd':'js/repertoryMgr/smokeAggregateReport/modules/curd',
		'smokeMgrRptDetailCurd':'js/repertoryMgr/smokeMgrRptDetail/modules/curd',
		'patrolReportListCurd':'js/patrolMgr/patrolReportList/modules/curd',
		'patrolReportPageCurd':'js/patrolMgr/patrolReportPage/modules/curd',
		'unmannedReportCurd':'js/repertoryMgr/unmannedReport/modules/curd',
		'attenceByDayReportCurd':'js/repertoryMgr/attenceByDay/modules/curd',
		'attenceByDayDetailCurd':'js/repertoryMgr/attenceByDayDetail/modules/curd',
		'attenceByMonthReportCurd':'js/repertoryMgr/attenceByMonth/modules/curd',
		'setStoreAlarmCurd':'js/repertoryMgr/storeAlarmSet/modules/curd',
		'setStoreVentilateCurd':'js/repertoryMgr/storeVentilateSet/modules/curd',
		'storeDeviceControlRecordCurd':'js/repertoryMgr/storeDeviceControlRecord/modules/curd',
		'historyRecordCurd':'js/repertoryMgr/historyRecord/modules/curd',
		'tblockCurd':'js/repertoryMgr/tblockMgr/modules/curd',
        'tblockIpCurd':'js/repertoryMgr/tblockipMgr/modules/curd',
        'tblockHisCurd':'js/repertoryMgr/tblockHisMgr/modules/curd',
        'vifHistoryCurd':'js/repertoryMgr/vifHistory/modules/curd',
		
		'searchRecordListCurd':'js/instrument/searchRecordList/modules/curd',
		'searchRecordDetailCurd':'js/instrument/searchRecordDetail/modules/curd',
		'appRecordListCurd':'js/instrument/appRecordList/modules/curd',
		'searchTmptRecordListCurd':'js/instrument/temperatureRecordList/modules/curd',
		'appTmptRecordListCurd':'js/instrument/appTempRecordList/modules/curd',
		'rdlcRecordListCurd':'js/newRobotRdlc/searchRecordList/modules/curd',
		
		'smsSetCurd':'js/systemMgr/smsSet/modules/curd',
		'voiceTextCurd':'js/systemMgr/voiceText/modules/curd',
		'setCommandCurd':'js/systemMgr/setCommand/modules/curd',
		'setWarnCurd':'js/systemMgr/setWarn/modules/curd',
		'serveMonitorCurd':'js/systemMgr/serveMonitor/modules/curd',
        'robotAlarmCurd':'js/systemMgr/robotAlarm/modules/curd',
		'robotStatusCurd':'js/systemMgr/robotStatus/modules/curd',
		'appReleaseCurd':'js/systemMgr/appRelease/modules/curd',
		'infraredSetCurd':'js/systemMgr/infraredSet/modules/curd',
		'offLineCurd':'js/runSituation/offLine/modules/curd',
		'setFusionCurd':'js/systemMgr/fusionDemo/modules/curd',
		'fengMapCurd':'js/common/map/fengMap/modules/curd',
		'fengMapCurd_old':'js/common/map/fengMap/modules/curd_old',
		'fengMapCurdBase':'js/systemMgr/fengMapBase/modules/curd',
		'faceFinderCurd':'js/systemMgr/faceFinder/modules/curd',
        'faceRecSearchCurd':'js/systemMgr/faceRecSearch/modules/curd',
        'faceUnRecSearchCurd':'js/systemMgr/faceUnRecSearch/modules/curd',
		
		'modernizr': 'uilib/modernizr/modernizr.custom',
		'coordtransform': 'uilib/coordtransform/curd',
		'BScroll': 'uilib/better-scroll/build/bscroll.min',
		'artTemplate': 'uilib/art-template/lib/template-web',
		'text': "uilib/requirejs-text/text", 
		'patrolMgrTemp': 'js/patrolMgr/template',
		'zrender-master': 'uilib/zrender/dist/zrender.min',
		'mockjs': 'uilib/mockjs/dist/mock-min',
		'zTree': 'uilib/zTree_v3/js/jquery.ztree.all.min',
        'lcSwitch': 'uilib/lc_switch/js/lc_switch',
        'ejs': 'uilib/ejs/ejs.min',
        'ejsTemplate': 'tempFiles/temp_html',
        'jqUtils': 'js/base/jqUtils',
        'pagination': 'js/systemMgr/rbQuestionsMgr/modules/pagination.build',
        'mock': 'js/base/mock',
        'dropzone': 'uilib/dropzone/dropzone-amd-module',
	},
	shim: {
        "bootstrap": {
            deps: ["jquery"],
            exports: "bootstrap"
        },
        'bootstrap-table': {
        	deps: ["jquery","bootstrap"],
            exports: "bootstrap-table"
        },
        'bootstrap-editable': {
            deps: ["bootstrap-table"],
            exports: "bootstrap-editable"
        },
        'bootstrap-table-editable': {
            deps: ["bootstrap-editable"],
            exports: "bootstrap-table-editable"
        },
        "datapicker": {
            deps: ["jquery"],
            exports: "datapicker"
        },
        'dialog': {
        	 deps: ["jquery"],
             exports: "dialog"
        },
        'layer': {
       	 	deps: ["jquery"],
            exports: "layer"
       },
        'WdatePicker':{
        	deps: ["jquery"],
        	exports: "WdatePicker"
       },
       'autocompleter':{
    	   	deps: ["jquery"],
       		exports: "autocompleter"
       },
       'drawline':{
    	   deps: ["jquery"],
    	   exports: "drawline"
       },
       'patrolMgrTemp': {
       		exports: 'patrolMgrTemp'
       },
       'zTree': {
			deps: ['jquery'],
			exports: 'zTree'
		},
        'mCustomScrollbar': {
            deps: ["jquery"],
            exports: "mCustomScrollbar"
        },
        'ejsTemplate': {
            exports: 'ejsTemplate'
        },
        'jqUtils': {
            deps: ['jquery'],
            exports: 'jqUtils'
        },
        'pagination': {
            deps: ['jquery'],
            exports: 'pagination'
        },
        'lcSwitch': {
            deps: ['jquery'],
            exports: 'lcSwitch'
        },
    },
    packages:[
        {
        	name: 'home',
        	location: 'js/runSituation/new'
        },
        {
        	name:'patrolPath',
        	location:'js/patrolMgr/patrolLine'
        },
        {
        	name:'warnPath',
        	location:'js/patrolMgr/warnSearch/modules'
        },
        {
        	name:'visitorPath',
        	location:'js/patrolMgr/visitorSearch/modules'
        },
        {
        	name:'setLevelPath',
        	location:'js/baseData/setWarnLevel/modules'
        },
        {
        	name:'attencePath',
        	location:'js/repertoryMgr/attence/modules'
        },
        {
            name: 'zrender',
            location: 'uilib/zrender-1.0.2/src',
            main: 'zrender'
        },
        {
        	name:'kaoqin',
        	location:'js/repertoryMgr/kaoqinPlan/modules'
        },
        {
        	name:'patrolMgr',
        	location:'js/patrolMgr'
        },
        {
        	name:'instrument',
        	location:'js/instrument'
        },
        {
            name: 'echarts',
            location: 'uilib/echarts3.8.4',
            main: 'echarts.min'
        },
        {
        	name: 'BMap',
        	location: 'js/runSituation/offLine/modules'
        },
        {
        	name: 'fengMap',
        	location: 'js/common/map/fengMap/modules'
        },
        {
        	name:'baseRConfigPath',
        	location:'js/patrolMgr/baseRobotConfig/modules'
        },
        {
            name: 'rbQuestionsMgr',
            location: 'js/systemMgr/rbQuestionsMgr/modules'
        },
        {
        	name: 'authority',
        	location: 'js/baseData/authority/modules'
        },
    ]
})