var pie ={
		
	init: function () {
		pie.setPlugin();
		pie.setEchart();
   	 	
	   	 $(".recordSearch").click(function () {
	   		pie.setEchart();
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
    	var myChart = echarts.init(document.getElementById('echartsPie'));

    	myChart.setOption({
    		title : {
    			text : '事件用时分布',
    			x : 'center'
    		},
    		tooltip : {
    			trigger : 'item',
    			formatter : "{a} <br/>{b} : {c} 小时"
    		},
    		legend : {
    			orient : 'vertical',
    			x : 'left',
    			//data : [ '睡觉', '工作', '阅读' ]
    			data : []
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
    					type : [ 'bar', 'funnel' ],
    					option : {
    						funnel : {
    							x : '25%',
    							width : '50%',
    							funnelAlign : 'left',
    							max : 1548
    						}
    					}
    				},
    				restore : {
    					show : true
    				},
    				saveAsImage : {
    					show : true
    				}
    			}
    		},
    		calculable : true,
    		series : [ {
    			name : '事件',
    			type : 'pie',
    			radius : '60%',//饼图的半径大小
    			center : [ '50%', '60%' ],//饼图的位置
    			data : []
    		} ]
    	});
    	//数据加载完之前先显示一段简单的loading动画
    	myChart.showLoading();

    	var today = $('#selectTime').val();
    		userId = 1;
    		events=[];//定义两个数组
    	    recordData=[]; 
    	    
    	$.ajax({
    	    type: "get",
    	    url: "/searchEventByDatetime.action",
    	    data : {"userId":userId,"datetime":today},
    	    cache : false,  //禁用缓存
    	    dataType: "json",
    	    success: function(result) {
    	    	if(result.data.events != undefined){
    	    		$.each(result.data.events,function(key,values){
    	    	      	 events.push(values.event);
    	    	      	 var obj = new Object();
    	    	      	 obj.name = values.event;
    	    	      	 obj.value = values.duration;
    	    	      	 recordData.push(obj);
    	    	        });
    	    	}
    	       
    	       myChart.hideLoading();    //隐藏加载动画
    	       myChart.setOption({        //加载数据图表
    	    	   title : {
    	   			text : '时间用时分布',
    	   			x : 'center'
    		   		},
    		   		tooltip : {
    		   			trigger : 'item',
    		   			formatter: "{a} <br/>{b}: {c}小时 ({d}%)"
    		   		},
    		   		legend : {
    		   			orient : 'vertical',
    		   			x : 'left',
    		   			//data : [ '睡觉', '工作', '阅读' ]
    		   			data : events
    		   		},
    		   		toolbox : {
    		   			show : true,
    		   			feature : {
//    		   				mark : {
//    		   					show : true
//    		   				},
    		   				dataView : {
    		   					show : true,
    		   					readOnly : false
    		   				},
    		   				magicType : {
    		   					show : true,
    		   					type : [ 'pie', 'funnel'],
    		   					option : {
    		   						funnel : {
    		   							x : '25%',
    		   							width : '50%',
    		   							funnelAlign : 'left',
    		   							max : 1548
    		   						}
    		   					}
    		   				},
    		   				restore : {
    		   					show : true
    		   				},
    		   				saveAsImage : {
    		   					show : true
    		   				}
    		   			}
    		   		},
    		   		calculable : true,
    		   		series : [ {
    		   			name : '事件',
    		   			type : 'pie',
    		   			radius : '60%',//饼图的半径大小
    		   			center : [ '50%', '60%' ],//饼图的位置
    		   			data : recordData,
    		   			
    		   			itemStyle: {
    		   				           normal:{
    		   					            label:{
    		   						            show:true,
    		   						            formatter: '{b} : {c}小时 \n ({d}%)'
    		   					            },
    		   					            labelLine:{
    		   					            	show:true
    		   					            }
    		   				            },
    		   			               emphasis: {
    		   			                   shadowBlur: 10,
    		   			                   shadowOffsetX: 0,
    		   			                   shadowColor: 'rgba(0, 0, 0, 0.5)'
    		   			               }
    		   			   		 }
    		   			
    		   		} ],
    		   		 
    	       });
    	    			
    	       
    	    },
    	    error: function(errorMsg) {
    	      alert("图表请求数据失败!");
    	      myChart.hideLoading();
    	    }
    	  });
	}
}

