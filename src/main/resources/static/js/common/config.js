require.config({
	paths:{
        'base': 'js/common/base',
        'GXX': 'js/common/Gxx',
        'jqUtils': 'js/common/jqUtils',
        'DateUtil': 'js/common/DateUtil',
		'jquery':"uilib/jquery/jquery-1.11.2.min",
		'bootstrap':"uilib/bootstrap/dist/js/bootstrap.min",
		'bootstrap-table':"uilib/bootstrapTable/ext/bootstrap-table",
		'datapicker':"uilib/datapicker/bootstrap-datepicker",
		'datetimepicker':"uilib/datapicker/js/bootstrap-datetimepicker",
		'text': "uilib/requirejs-text/text", 
		'ejs': 'uilib/ejs/ejs.min',
        'dialog': 'uilib/artDialog/dist/dialog-plus-min',
        'layer':'uilib/layer/layer',
        'materialize':'js/common/materialize',
		
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
	}
	
})
                
                	
                
                