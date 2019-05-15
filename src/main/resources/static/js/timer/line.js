require(['jquery', 'bootstrap','datapicker','jqUtils','DateUtil'],
    function($, bootstrap, datapicker,jqUtils,DateUtil) {
    	
    	var main = {
    		
    		init: function() {
    			this.setPlugin();
    			this.getWeekData();
    			this.listenEvent();
                
            },
            
            listenEvent: function() {
            	var that = this;
				$("#getWeek").on('click', that.getWeekData.bind(that));
				$("#getMonth").on('click', that.getMonthData.bind(that));
				$("#getYear").on('click', that.getYearData.bind(that));
				
            },
            
            setPlugin: function(){
            	 $('#selectTime').val(new Date().Format("yyyy-MM-dd"));
            	 $("#selectTime").datepicker({
 					format: "yyyy-mm-dd", //显示日期格式
 					autoclose: true,
 					todayBtn: "linked",
 					minView: "month", //只选择到天
 					language: 'zh-CN',
 				});
            	 
            },
            
            getWeekData: function() {
            	$('#selectScope label').each(function(){
            		$(this).removeClass('active');
                });
            	$('#getWeek').addClass('active');
            	
                var that = this;
                
                var myChart = echarts.init(document.getElementById('echartsLine'));
                	_userId = $('#userId').val(),
                	_datetime=$('#selectTime').val(),
                	_eventName=$('#event').val(),
                    _url = '/getWeekRecord.action',
                    _data = {
                    	userId:_userId,
	                    datetime: _datetime,
	                    eventname: _eventName
                	};
                	dtd = $.Deferred(),
//                	dateList=[];
                	durationList=[]; 

                	myChart.showLoading();
	                $.ajax({
	                    url: _url,
	                    type: 'post',
	                    // timeout: 2000,
	                    data: _data,
	                    dataType: 'json',
	                    async: true,
	                    success: function(res) {
	                        if (res.ret === 1) {
	                        	$.each(res.data ,function(key,values){
//	                        		dateList.push(values.date);
	                         	   	durationList.push(values.duration);
	                             });
	                        	
	                        	myChart.hideLoading();
	                        	myChart.setOption({        //加载数据图表
	                         	   title : {
	                         			text : '近七日情况'
	                         		},
	                         		tooltip : {
	                         			trigger : 'axis'
	                         		},
	                         		legend : {
	                         			data : [ '近七日情况' ]
	                         		},
	                         		grid : {
	                         			left : '3%',
	                         			right : '4%',
	                         			bottom : '3%',
	                         			containLabel : true
	                         		},
	                         		toolbox : {
	                         			show : true,
	                         			feature : {
	                         				mark : {
	                         					show : true
	                         				},
	                         				dataView : {
	                         					show : true,
	                         					readOnly : false
	                         				},
	                         				magicType : {
	                         					show : true,
	                         					type : [ 'line', 'bar' ],
	                         				},
	                         				restore : {
	                         					show : true
	                         				},
	                         				saveAsImage : {
	                         					show : true
	                         				}
	                         			}
	                         		},
	                         		xAxis : {
	                         			type : 'category',
	                         			boundaryGap : false,
	                         			data : [ "周一", "周二", "周三", "周四", "周五","周六","周日" ]
	                         		},
	                         		yAxis : {
	                         			type : 'value'
	                         		},
	                         		series : [

	                         		{
	                         			name : _eventName,
	                         			type : 'line',
	                         			stack : '总量',
	                         			data : durationList
	                         		} ]
	                            });	
	                            
	                        } else {
	                            dtd.reject(res.msg);
	                        }
	                    },
	                    error: function(err) {
	                        dtd.reject('网络错误！');
//	                        alert("图表请求数据失败!");
	                        myChart.hideLoading();
	                    }
	                });

                return dtd;
            },
            
            getMonthData: function() {
            	$('#selectScope label').each(function(){
            		$(this).removeClass('active');
                });
            	$('#getMonth').addClass('active');
            	
                var that = this;
                var myChart = echarts.init(document.getElementById('echartsLine'));
                	_userId = $('#userId').val(),
                	_datetime=$('#selectTime').val(),
                	_eventName=$('#event').val(),
                    _url = '/getMonthRecord.action',
                    _data = {
                    	userId:_userId,
	                    datetime: _datetime,
	                    eventname: _eventName
                	};
                	dtd = $.Deferred(),
                	dateList=[];
                	durationList=[]; 

                	myChart.showLoading();
	                $.ajax({
	                    url: _url,
	                    type: 'post',
	                    // timeout: 2000,
	                    data: _data,
	                    dataType: 'json',
	                    async: true,
	                    success: function(res) {
	                        if (res.ret === 1) {
	                        	$.each(res.data ,function(key,values){
	                        		dateList.push(values.date);
	                         	   	durationList.push(values.duration);
	                             });
	                        	
	                        	myChart.hideLoading();
	                        	myChart.setOption({        //加载数据图表
	                         	   title : {
	                         			text : '近一个月情况'
	                         		},
	                         		tooltip : {
	                         			trigger : 'axis'
	                         		},
	                         		legend : {
	                         			data : [ '近一个月情况' ]
	                         		},
	                         		grid : {
	                         			left : '3%',
	                         			right : '4%',
	                         			bottom : '3%',
	                         			containLabel : true
	                         		},
	                         		toolbox : {
	                         			show : true,
	                         			feature : {
	                         				mark : {
	                         					show : true
	                         				},
	                         				dataView : {
	                         					show : true,
	                         					readOnly : false
	                         				},
	                         				magicType : {
	                         					show : true,
	                         					type : [ 'line', 'bar' ],
	                         				},
	                         				restore : {
	                         					show : true
	                         				},
	                         				saveAsImage : {
	                         					show : true
	                         				}
	                         			}
	                         		},
	                         		xAxis : {
	                         			type : 'category',
	                         			boundaryGap : false,
	                         			data : dateList
	                         		},
	                         		yAxis : {
	                         			type : 'value'
	                         		},
	                         		series : [

	                         		{
	                         			name : _eventName,
	                         			type : 'line',
	                         			stack : '总量',
	                         			data : durationList
	                         		} ]
	                            });	
	                            
	                        } else {
	                            dtd.reject(res.msg);
	                        }
	                    },
	                    error: function(err) {
	                        dtd.reject('网络错误！');
//	                        alert("图表请求数据失败!");
	                        myChart.hideLoading();
	                    }
	                });

                return dtd;
            },
            
            getYearData: function() {
            	$('#selectScope label').each(function(){
            		$(this).removeClass('active');
                });
            	$('#getYear').addClass('active');
            	
                var that = this;
                
                var myChart = echarts.init(document.getElementById('echartsLine'));
                	_userId = $('#userId').val(),
                	_datetime=$('#selectTime').val(),
                	_eventName=$('#event').val(),
                    _url = '/getYearRecord.action',
                    _data = {
                    	userId:_userId,
	                    datetime: _datetime,
	                    eventname: _eventName
                	};
                	dtd = $.Deferred(),
//                	dateList=[];
                	durationList=[]; 

                	myChart.showLoading();
	                $.ajax({
	                    url: _url,
	                    type: 'post',
	                    // timeout: 2000,
	                    data: _data,
	                    dataType: 'json',
	                    async: true,
	                    success: function(res) {
	                        if (res.ret === 1) {
	                        	$.each(res.data ,function(key,values){
//	                        		dateList.push(values.date);
	                         	   	durationList.push(values.duration);
	                             });
	                        	
	                        	myChart.hideLoading();
	                        	myChart.setOption({        //加载数据图表
	                         	   title : {
	                         			text : '2019年'
	                         		},
	                         		tooltip : {
	                         			trigger : 'axis'
	                         		},
	                         		legend : {
	                         			data : [ '2019年' ]
	                         		},
	                         		grid : {
	                         			left : '3%',
	                         			right : '4%',
	                         			bottom : '3%',
	                         			containLabel : true
	                         		},
	                         		toolbox : {
	                         			show : true,
	                         			feature : {
	                         				mark : {
	                         					show : true
	                         				},
	                         				dataView : {
	                         					show : true,
	                         					readOnly : false
	                         				},
	                         				magicType : {
	                         					show : true,
	                         					type : [ 'line', 'bar' ],
	                         				},
	                         				restore : {
	                         					show : true
	                         				},
	                         				saveAsImage : {
	                         					show : true
	                         				}
	                         			}
	                         		},
	                         		xAxis : {
	                         			type : 'category',
	                         			boundaryGap : false,
	                         			data : [ "一月","二月","三月","四月","五月","六月","七月","八月","九月","十月","十一月","十二月" ]
	                         		},
	                         		yAxis : {
	                         			type : 'value'
	                         		},
	                         		series : [

	                         		{
	                         			name : _eventName,
	                         			type : 'line',
	                         			stack : '总量',
	                         			data : durationList
	                         		} ]
	                            });	
	                            
	                        } else {
	                            dtd.reject(res.msg);
	                        }
	                    },
	                    error: function(err) {
	                        dtd.reject('网络错误！');
	                        myChart.hideLoading();
	                    }
	                });

                return dtd;
            },
            
    	}
            main.init();
})


//var myChart = echarts.init(document.getElementById('echartsLine'));
//
//myChart.setOption({
//	title : {
//		//text : '近七日情况'
//		//text : '近一个月情况'
//		text : '2019年'
//	},
//	tooltip : {
//		trigger : 'axis'
//	},
//	legend : {
//		data : [ '近七日情况' ]
//		//data : [ '近一个月情况' ]
//		//data : [ '2019年' ]
//	},
//	grid : {
//		left : '3%',
//		right : '4%',
//		bottom : '3%',
//		containLabel : true
//	},
//	toolbox : {
//		show : true,
//		feature : {
//			mark : {
//				show : true
//			},
//			dataView : {
//				show : true,
//				readOnly : false
//			},
//			magicType : {
//				show : true,
//				type : [ 'line', 'bar' ],
//			},
//			restore : {
//				show : true
//			},
//			saveAsImage : {
//				show : true
//			}
//		}
//	},
//	xAxis : {
//		type : 'category',
//		boundaryGap : false,
//		data : [ ]
//		//data : [ "周一", "周二", "周三", "周四", "周五","周六","周日" ]
//		//data : [ "1","4","7","10","13","16","19","22","25","28","31" ]
//		//data : [ "一月","二月","三月","四月","五月","六月","七月","八月","九月","十月","十一月","十二月" ]
//	},
//	yAxis : {
//		type : 'value'
//	},
//	series : [
//
//	{
//		name : '阅读',
//		type : 'line',
//		stack : '总量',
//		data : [ ]
//		//data : [ "2","3","2","4","0","1","2" ]
//		//data : [ "2","3","2","4","0","1","2","2","0","1","2" ]
//		//data : [ "55","53","52","54","50","51","62","42","50","61","52","52" ]
//	} ]
//});



