require(['jquery', 'bootstrap','datapicker','jqUtils','DateUtil', 'ejs' ,'text!ejsTemplate/templet.ejs','text!ejsTemplate/newRecord.ejs'],
    function($, bootstrap, datapicker,jqUtils,DateUtil, ejs ,ejsTemp,ejsNew) {
    	
    	var main = {
    		
    		init: function() {
    			this.setPlugin();
                this.getData()
                // 绑定监听事件
                this._listenEvent();
                
            },
            
            _listenEvent: function() {
            	var that = this;
            	
            	$('.data-item-wrapper').on('click', '.btn-operation button', that.delEvent.bind(that));
            	
            	// toolbar 按钮
				$('.toolBar').on('click', 'button', function(event) {
					var target = event.currentTarget,
						type = target.dataset.type;
					
					if ($.isNull(type)) return;
				
					if (type = 'btn_addData') {
						that.addDialogHandle(target);
					}
				});
				
				$("#dataSubmit").on('click', that.dataSubmitHandle.bind(that));
				$("#cancelEvent").on('click', that.getData.bind(that));
				$(".recordSearch").on('click', that.getData.bind(that));
				
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
            
            getData: function() {
                var that = this;
                var _datetime=$('#selectTime').val(),
                	_userId = 1,
                    _url = '/searchEventByDatetime.action',
                    _data = {
                    	userId:_userId,
	                    datetime: _datetime
                	};
                	dtd = $.Deferred(),
                	dataWrapper = document.getElementById('dataWrapper');

                $.ajax({
                    url: _url,
                    type: 'get',
                    // timeout: 2000,
                    data: _data,
                    dataType: 'json',
                    async: true,
                    success: function(res) {
                        if (res.ret === 1) {
                        	$("#dataWrapper").html('');
                            dtd.resolve(res.data);
                            if(res.data.events != undefined){
                            	that.update(res.data.events);
                            	common.getEvent();
                                dataWrapper.dataset.dateid = res.data.dateId;
                            }
                            
                        } else {
                            dtd.reject(res.msg);
                        }
                    },
                    error: function(err) {
                        dtd.reject('网络错误！');
                    }
                });

                return dtd;
            },
            
            dataSubmitHandle: function(event) {
                var that = this,
                	target = event.currentTarget,
                	_url = '/saveOrUpdateData.action',
                	dataWrapper = document.getElementById('dataWrapper');
                	_dateid = dataWrapper.dataset.dateid,
                	_userId = 1,
                	_datetime = $('#selectTime').val(),
                	_events = [];
				
                $('.data-item-box').each(function(i, k) {
                    var item = {};
                    item.id = k.dataset.xid;
                    item.event = $(k).find('[data-show="editEventMode"] select').val();
                    item.duration = $(k).find('[data-show="editDurationMode"] select').val();
                    if(item.event !="" && item.duration!=""){
                    	_events = _events ? _events.concat(item) : [item]; 
                    }
                        
                });

                var _data = {
                    id: _dateid,
                    userId: _userId,
                    datetime: _datetime,
                    events: _events
                };

                $.ajax({
                    type: 'post',
                    url: _url,
                    data: JSON.stringify(_data),
                    contentType: "application/json",
                    dataType: 'json',
                    async: false,
                    timeout: 3000,
                    success: function(res) {
                        if (res.ret === 1) {
                            sweetAlert({
                                title: '保存成功',
                                text:res.msg,
                                type:"success",
                                showConfirmButton: true,
                            });
                            
                            $("#dataWrapper").html('');
                            if(res.data != null){
                            	that.update(res.data.events);
                            	common.getEvent();
                                dataWrapper.dataset.dateid = res.data.dateId;
                            }else{
                            	dataWrapper.dataset.dateid = "";
                            }
                        } else {
                        	sweetAlert({
                                title: '保存失败',
                                text:res.msg,
                                type:"error",
                            });
                        }
                    },
                    error: function(err) {
                    	sweetAlert({
                            title: '网络错误',
                            text:'网络错误，保存失败',
                            type:"error",
                        });
                    }

                })
                return true;
            },
            
            addDialogHandle: function(btn) {
                var that = this,
                    _html = that.render('', ejsNew, -1),
                    newDom = $(_html);

                $('.data-item-wrapper:last').prepend(newDom);
                common.getEvent();
            },
            
            /**
             * 渲染ejs模板
             * @param  {[object || array]} data [用于渲染模板的数据],
             * @param  {[string]} ejsTemp [模板]
             * @param  {[string]} index [模板]
             * @return {[string]}      [返回HTML 字符串]
             */
            render: function(data, template, startFrom) {
                var _html,
                	_data = $.isNull(data) ? [ {} ] : data,
                    startFrom = startFrom ? startFrom : 0;

                return ejs.render(template, { 'events': _data, 'startFrom': startFrom }, {
                    compileDebug: true
                });

            },
            //删除事件
            delEvent: function(event) {
                var that = this;
                var target = event.currentTarget,
                    classname = target.className,
                    dialogBox = $(target).parents('.data-item-box')[0];

                if (classname.indexOf('btn-cancel-hack') > -1) {
                    $(dialogBox).remove();
                }
            },
            /**
             * update data, update UI
             * @param  {[object]} data [not necessary]
             * @return {[type]}      [description]
             */
            update: function(data) {
                var that = this,
                    index = 0;
 
                var _data = data,
                	_html = that.render(_data, ejsTemp, index),
                	container = $('.data-item-wrapper:first');
                
                container.append(_html);
                
//                flag ? container.append(_html) : (container.html(''), container.append(_html));
					
            },
            
    	}
            main.init();
})
