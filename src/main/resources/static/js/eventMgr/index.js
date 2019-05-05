require(['bootstrap','bootstrap-table','dialog','base','GXX','layer'],
		function(bootstrap,bootstraptable,dialog,base,Gxx,layer){
	
	var	Dialog = Gxx.Dialog,
	bsTable = Gxx.bsTable;

	/**
	 * 布局模块
	 */
	var layout = {
		init:function(){
			this._setHeight();
		},
		_setHeight:function(){
			var docHeight = $(document).height();
			$(".data-list").height(docHeight-120);
		}
	};
	
	/**
	 * 表格模块
	 */
	var tableOpe = {
		init:function(){
			this._search();
			this._clickEvent();
		},
	
		_clickEvent:function(){
			var that = this;
			$("#search").click(function(){
				var url = '/getEventbyUserId.action';
				that._refresh(url);
			});
			$("#add").click(function(){
				that._add();
			});
		},
		operateEvents:function(){
			return {
	//			'click #edit': function (e, value, row, index) {
	//				tableOpe._edit(row);
	//			},
				'click #del': function (e, value, row, index) {
					tableOpe._del(row.id);
				}
			}
		},
		_refresh:function(url){
			var	params = {
					userId: 1,
				}
			base._search("bootstrapTable",url,params);
		},
		_search:function(){
			var url = '/getEventbyUserId.action';
	        var height = $(document).height() - 150;
	        var settings = {
	            method: 'post',
	            url: url,
	            height: height,
	            classes: 'table table-hover table-condensed',
	            queryParams: function(params) {
	            	params['userId'] = 1;
	//                params['eventName'] = $.trim($("#s_eventName").val());
	                return params;
	            },
	            responseHandler: function(res) {
	                if (res.ret === 1) {
	                    return res.data;
	                } else {
	                    return { rows: [], total: 0, currentPage: 1 };
	                }
	            }
	        }
	        bsTable.initTable("#bootstrapTable", settings)
		},
		_add:function(){
			var that = this,
				url = '/addEvent.action';
				
			addWin = dialog({
		 		title: '增加',
	 			width:600,
	 			content:$(".curdBox"),
	 			onshow: function(){
	 				that._clearBox();
	 			},
				onclose: function(){
					$(".curdbox").css('display','none');
					this.close();
				},
				button:[
					{
					    value : "确定",
					    autofocus: true,
					    callback :function() {
					    	var data = that._getPostParams();
					   	 	if(tableOpe._validater()){
					   	 		var i=0;
					   	 		$.ajax({
					   	 			type: 'post',
					   	 			dataType:'json',
					   	 			url: url,
					   	 			data:data,
					   	 			async: false,
					   	 			success:function(data){
					   	 				if(data.ret == 1){
					   	 					tableOpe._refresh();
					   	 					Gxx.Dialog.success(data.msg);
					   	 				}else{
					   	 					i=1;
					   	 					Gxx.Dialog.error(data.msg);
					   	 				}
					   	 			},
					   	 			error:function(){
					   	 				i=1;
					   	 				Gxx.Dialog.error("请求服务器失败!");
					   	 			}
					   	 		})
					   	 		if(i==1){
					   	 			//请求失败，不关闭弹出框
					   	 			return false;
					   	 		}
					   	 	}else{
				   	 			return false;
				   	 		}
					    }
					},
					{
				        value : "取消",
				        callback : function() {
				        	$(".curdbox").css('display','none');
							this.close();
				        }
				    },
					
				]
	 		});
			addWin.showModal();
		},
		_del:function(id){
			var that = this,
				url = '/deleteEvent.action';
			
			delWin = dialog({
		 		title: '提示框',
	 			width:400,
	 			content:"<div class='sweet-alert sa-confirm'><div class='sa-icon sa-info'></div><p class='sa-tips'>确定删除  ?</p></div>",
	 			onshow: function(){
	 			},
				onclose: function(){
					this.close();
				},
				button:[
					{
					    value : "确定",
					    autofocus: true,
					    callback :function() {
					   	 	var data = {
					   	 		id: id
					   	 	};
					   	 	$.ajax({
					   	 		type: 'post',
					   	 		dataType:'json',
					   	 		url:url,
					   	 		data:data,
					   	 		success:function(data){
					   	 			if(data.ret == 1){
					   	 				tableOpe._refresh();
					   	 				Gxx.Dialog.success(data.msg);
					   	 			}else{
					   	 				Gxx.Dialog.error(data.msg);
					   	 			}
					   	 		},
					   	 		error:function(){
					   	 			Gxx.Dialog.error("请求服务器失败！");
					   	 		}
					   	 	})
					    }
					},
				    {
				        value : "取消",
				        callback : function() {
				        	this.close();
				        }
				    },
	
				]
	 		});
			delWin.showModal();
		},
		_clearBox:function(){
			$("#f_eventName").val("");
		},
		_getPostParams:function(){
			var data = {};
			data.userId =1;
			data.eventName = $("#f_eventName").val();
			return data;
		},
		_validater:function(){
			var eventName = $("#f_eventName").val();
			if(!eventName){
				Dialog.error("要添加的事件名称不能为空！");
				return false;
			}
			return true;
		}
	};
	
	layout.init();
	tableOpe.init();
	operateEvents = tableOpe.operateEvents;
})

var handleFormat = function(){
	return [
//	        '<a class="handle edit" id="edit" href="javascript:void(0)" title="编辑">',
//	        '</a>  ',
	        '<a class="handle delete" id="del" href="javascript:void(0)" title="删除"> ',
	        '</a>'
	    ].join('');
}