/**
 * 基础操作
 * @author skz/zxl
 * @date 2016年7月12日
 * @time 下午8:19:30
 */
define(['jquery','GXX'],function($,Gxx){
	var Dialog = Gxx.Dialog;
	var bsTable = Gxx.bsTable;
	return {
		_list: function(id,url,height,method,sidePagination,query,onDblClickRow){
			height = height || ($(window).height()-118);
			sidePagination = sidePagination || 'server';
			method = method||'post';
			onDblClickRow = onDblClickRow || function(){};
			bsTable.initTable('#'+id,{
		        url: url,
		        classes : 'table table-hover table-condensed',
		        height: height,
		        sidePagination : sidePagination,
		        method : method,
		        queryParams : function(params){
		        	params = $.extend(params,query||{});
		        	return params;
		        },
		        onDblClickRow:onDblClickRow
		    });
		},
		/**
		 * @param url  请求url
		 * @param params 
		 */
		_search: function(id,url,params){
			Gxx.bsTable.refreshTable('#'+id,{
				url: url,
				query: params
			});
		},
		/**
		 * @param url  请求url
		 */
		_refresh: function(id,url){
			Gxx.bsTable.refreshTable('#'+id,{
				url: url
			});
		},
		/**
		 * @param self 事件对象
		 * @param url  请求url
		 * @param rows 表格数据
		 */
		_del: function(self,url,params,refreshUrl,refresh){
			Dialog.confirm({
				msg: '确定删除？',
				confirmCallback: function(){
					Gxx.aJax.postJSON(url,params,function(res){
						if(res.success == true){
							Dialog.success('删除成功');
							if(refreshUrl && refresh){
								refresh(refreshUrl);
							}
						}else{
							Dialog.error(res.message);
						}
					},
					function(){
						Dialog.error('服务出错'); 
					})
				}
			})
		},
		/**
		 * 清空表单
		 */
		_clearForm: function(content){
			$(content).find('input[type="text"]').val('');
			$(content).find('select').val('');
			$(content).find('textarea').val('');
		},
		/**
		 * TODO 表单验证
		 */
		_validate: function(){
			
		},
		/**
		 * post请求
		 * @param url  请求url
		 * @param params 请求参数
		 * @param prefix 提示前缀
		 */
		_post: function(url,params,prefix,refreshUrl,refresh){
			prefix = prefix || '操作';
			Gxx.aJax.postJSON(url,params,function(res){
				if(res.success == true){
					Dialog.success(prefix + '成功');
					if(refreshUrl && refresh){
						refresh(refreshUrl);
					}
				}else{
					Dialog.error(res.message);
				}
			},
			function(){
				Dialog.error('服务出错！');
			})
		},
		/**
		 * 弹窗
		 * @param title  弹窗标题
		 * @param rows 	 表格数据
		 * @param onshow
		 * @param callback 确定回调
		 */
		_confirm: function(id,content,title,rows,onshow,callback,method,width,okName,cancelName,secCallback){
			var that = this;
			width = width || 400;
			okName = okName||'确定';
			cancelName = cancelName || '取消';
			var option = {
					 id: id,
	                 title: title || '新增',
	                 width: width,
	                 content: $(content),
	                 onshow: function(){
	 					$(content).css('display','block');
	 					that._clearForm(content);
	 					onshow && onshow(rows);
	 				},
	 				onclose: function(){
	 					$(content).css('display','none');
	 				},
	                 button:[{
	                	 value : cancelName,
	                     callback : secCallback/*function() {
	                    	 if(secCallback){
	                    		 secCallback && secCallback();
	                    	 }else{
	                    		 this.close();
	                    	 }
	                     }*/
	                 },{
	                	 value : okName,
	                     autofocus: true,
	                     callback :callback /*function() {
	                    	 callback && callback();
	                     }*/
	                 }]
				};
			if(method && method == 'show'){
				Dialog.showDialog(option);
			}else{
				Dialog.showModal(option);
			}
		},
		/**
		 * 弹窗Plus
		 * @param url	载入页面url
		 * @param title  弹窗标题
		 * @param rows 	 表格数据
		 * @param onshow
		 * @param callback 确定回调
		 */
		_confirmPlus:function(option,method){
			if(method && method == 'show'){
				return Dialog.showDialog(option);
			}else{
				return Dialog.showModal(option);
			}
		},
		/**
		 * 判断是否符合IP格式
		 * @param ip 
		 */
		isIP:function(ip){
			var re =  /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/   
		    return re.test(ip);  
		},
		/**
		 * 设置日期(0为今天，-1为昨天，以此类推)
		 * @param AddDayCount
		 * @returns {String}/2016-01-01/
		 */
		GetDateStr:function(AddDayCount){
			var dd = new Date();
		    dd.setDate(dd.getDate()+AddDayCount);//获取AddDayCount天后的日期
		    var y = dd.getFullYear();
		    var m = dd.getMonth()+1;//获取当前月份的日期
		    if(m<10){
		    	m = '0'+m;
		    }
		    var d = dd.getDate();
		    if(d<10){
		    	d = '0'+d;
		    }
		    
		    return y+"/"+m+"/"+d;
		},
		/**
		 * 根据一个日期查找另一个日期
		 * @param {} theDay：2016-04-20
		 * @param {} AddDayCount：(-1为前一天，1为后一天，0不变，以此类推)
		 * @return {String}
		 */
		findDayByDay:function(theDay,AddDayCount){
			var formatDay = new Date(theDay);
			formatDay.setDate(formatDay.getDate()+AddDayCount);//获取AddDayCount天后的日期
			var y = formatDay.getFullYear();
		    var m = formatDay.getMonth()+1;//获取当前月份的日期
		    if(m<10){
		    	m = '0'+m;
		    }
		    var d = formatDay.getDate();
		    if(d<10){
		    	d = '0'+d;
		    }
		    
		    return y+"/"+m+"/"+d;
		},
		/**
		 * 返回距 1970 年 1 月 1 日之间的毫秒数(可用于比较时间先后)
		 * @param {} Date 格式为：yyyy-mm-dd
		 */
		formatTimesFromDate:function(Date){
			var arr = Date.split("-");
			var newDate = new Date(arr[0],arr[1],arr[2]);
			var resultDate = newDate.getTime();
			return resultDate;
		},
		/**
		 * 返回距 1970 年 1 月 1 日之间的毫秒数(可用于比较时间先后)
		 * @param {} Time 格式为：hh:mm:ss
		 */
		formatTimesFromTime:function(Time){
			var arr = Time.split(":");
			var newTime = new Date('','','',arr[0],arr[1],arr[2]);
			var resultDate = newTime.getTime();
			return resultDate;
		},
		/**
		 * 查看图集
		 */
		showPics:function(pics){
			var temp = '<a class="close-img" title="点击关闭"></a><div class="big-pic" id="bigPic"></div>';
			$("body").append(temp);
			
			var content = $("#carousel").clone(true).appendTo("#bigPic"),
				$slider = content.find("#slider"),
				$showscreen = content.find(".showscreen"),
				$toleft = content.find(".toleft"),
				$toright = content.find(".toright"),
				$showflag = content.find("#showflag"),
				num = pics.length;
			var	topHeight = ($("#bigPic").height()-$toleft.height())/2;
			$toleft.css("top",topHeight);
			$toright.css("top",topHeight);
			$slider.width($showscreen.width()*num);
			
			$.each(pics,function(i,item){
				var liTemp = '<li><img src="'+item.picUrl+'"></li>';
				$slider.append(liTemp);
				var flagTemp = '<li></li>';
				$showflag.append(flagTemp);
			});
			
			$slider.find("li").width($showscreen.width());
			$slider.find("li").height($showscreen.height());
			$showflag.width(33*num);
			$showflag.find("li").first().addClass("flag-active");
			
			$(".close-img").click(function(){
				$("body").find("#bigPic").remove();
				$("body").find(".close-img").remove();
			});
			
			if(pics.length > 1){
				$toright.click(function(){
					$slider.animate({
						left:-$showscreen.width()
					},"slow",function(){
						$slider.css("left",0).find("li").last().after($slider.find("li").first());
					});
					var curNum = $showflag.find(".flag-active").index();
					if(curNum<(num-1)){
						$showflag.find("li:eq("+(curNum+1)+")").addClass("flag-active").siblings().removeClass("flag-active");
					}else{
						$showflag.find("li:eq(0)").addClass("flag-active").siblings().removeClass("flag-active");
					}
				});
				
				$toleft.click(function(){
					$slider.css("left",-$showscreen.width()).find("li").first().before($slider.find("li").last());
					$slider.animate({
						left:0
					},"slow");
					var curNum = $showflag.find(".flag-active").index();
					if(curNum>0){
						$showflag.find("li:eq("+(curNum-1)+")").addClass("flag-active").siblings().removeClass("flag-active");
					}else{
						$showflag.find("li:eq("+(num-1)+")").addClass("flag-active").siblings().removeClass("flag-active");
					}
				});
			}
				
		}
	}
})