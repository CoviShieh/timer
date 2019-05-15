var bar ={
		
	init: function () {
   	 	bar.setPlugin();
   	 	bar.setEchart();
   	 	
	   	 $(".recordSearch").click(function () {
	   		bar.setEchart();
	     });
   	 	
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
    
    setEchart:function (){
		var myChart = echarts.init(document.getElementById('echartsBar'));
//		myChart.setOption({
//			 title: {
//		        text: '事件记录柱状图统计',
//		     },
//			 tooltip : {
//		         show : true
//		     },
//		     legend : {	//图例组件
//		         data : [ '事件' ]
//		     },
//		     toolbox : {
//					show : true,
//					feature : {
//						mark : {
//							show : true
//						},
//						dataView : {
//							show : true,
//							readOnly : false
//						},
//						magicType : {
//							show : true,
//							type : [ 'line', 'bar' ],
//						},
//						restore : {
//							show : true
//						},
//						saveAsImage : {
//							show : true
//						}
//					}
//				},
//		     xAxis : [ {
//		         	type : 'category',
//		         	//data : [ "睡觉", "工作", "阅读" ]
//		     		data : []
//		     } ],
//		     yAxis : [ {
//		         type : 'value',
//		         axisLabel: {
//		             formatter: '{value} 小时'
//		         }
//		     } ],
//		     series : [ {
//		        // "name" : "时间",
//		         "type" : "bar",
//		         "data" : []
//		     } ]
//		});
		//数据加载完之前先显示一段简单的loading动画
		myChart.showLoading();

		var today = $('#selectTime').val();
			userId = $('#userId').val();
			eventList=[];//定义两个数组
			durationList=[]; 
		    
		$.ajax({
		    type: "get",
		    url: "/searchEventByDatetime.action",
		    data : {
		    	"userId":userId,
		    	"datetime":today
		    	},
		    cache : false,  //禁用缓存
		    dataType: "json",
		    success: function(res) {
		    	if(res.data.events != undefined){
		    		$.each(res.data.events,function(key,values){
		    	    	   eventList.push(values.event);
		    	    	   durationList.push(values.duration);
		    	        });
		    	}
		       
		       myChart.hideLoading();    //隐藏加载动画
		       myChart.setOption({        //加载数据图表
		    	   title: {
		    	        text: '',
		    	     },
		    		 tooltip : {
		    	         show : true
		    	     },
		    	     legend : {	//图例组件
		    	         data : [ '用时' ]
		    	     },
		    	     toolbox : {
		    				show : true,
		    				feature : {
//		    					mark : {
//		    						show : true
//		    					},
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
		    	     xAxis : [ {
		    	         	type : 'category',
		    	         	//data : [ "睡觉", "工作", "阅读" ]
		    	     		data : eventList
		    	     } ],
		    	     yAxis : [ {
		    	         type : 'value',
		    	         axisLabel: {
		    	             formatter: '{value} 小时'
		    	         }
		    	     } ],
		    	     series : [ {
//		    	         "name" : "时间",
		    	         "type" : "bar",
		    	         "data" : durationList
		    	     } ]
			   		 
		       });
		    			
		       
		    },
		    error: function(errorMsg) {
		      alert("图表请求数据失败!");
		      myChart.hideLoading();
		    }
		  });

	}
}


