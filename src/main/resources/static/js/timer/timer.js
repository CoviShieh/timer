require(['jquery', 'GXX', 'bootstrap','jqUtils', 'ejs' ,'text!ejsTemplate/templet.ejs','text!ejsTemplate/newRecord.ejs'],
    function($, GXX, bootstrap, jqUtils, ejs ,ejsTemp,ejsNew) {
    	
    	var Dialog = GXX.Dialog
    	
    	var main = {
    		
    		init: function() {
                var that = this;
				
                that.getData()
                
                // 绑定监听事件
                that._listenEvent();

            },
            
            getData: function() {
                var that = this;
				//获取当天时间值
                var _datetime='2019-04-25',
                	//_datetime = $('#selectTime').val(),
                    _url = '/timer/searchEventByDatetime.action',
                    dtd = $.Deferred(),
                    _data = {
	                    datetime: _datetime
                	};
                	dataJson ={
                			"id": 1, 
    						"userId": 1,
    						"datetime": "2018-10-30",
    						"events": [{
    						    "id": 1, 
    							"event": "读书",
    							"duration": "3"
    						}, {
    							"id": 2,
    							"event": "睡觉",
    							"duration": "6"
    						}]
                	};

                $.ajax({
                    url: _url,
                    type: 'get',
                    // timeout: 2000,
                    data: _data,
                    dataType: 'json',
                    async: true,
                    success: function(res) {
                        that.removeLoader(); //取消动画
                        if (res.ret === 1) {
                            if (res.data.total === 0) {
                                Dialog.warning('查询数据为空');
                            }
                            dtd.resolve(res.data);
                            that.update(res.data);
                        } else {
                            dtd.reject(res.msg);
                        }
                    },
                    error: function(err) {
                        that.removeLoader();
                        dtd.reject('网络错误！');
                    }
                });

                return dtd;
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
				
				//$("#dataSubmit").on('click', that.dataSubmitHandle());
				
				
            },
            
            dataSubmitHandle: function() {
                var that = this,
                _url = '/saveOrUpdateData.action',
                _id = 1,
                _events = [],
                _times = [];
				
                $('.data-item-box').each(function(i, k) {
                    var item = {};

                    item.id = k.dataset.questionid;
                    item.question = $(k).find('[data-show="editEvent"] option').val();
                    _events = _events ? _events.concat(item) : [item]
                    
                    item.answer = $(k).find('[data-show="editTime"] option').val();
                    _times = _times ? _times.concat(item) : [item];
                        
                });

                var _data = {
                    id: _id,
                    date: '2019-04-21',
                    events: _events,
                    times: _times
                };

                /**
                 * delete the value of null/''
                 * @param  {[type]} data [description]
                 * @return {[type]}      [description]
                 */
                (function(data) {
                    var fn = arguments.callee;

                    for (var i in data) {
                        var n = data[i];
                        if (Array.isArray(n)) {
                            n.forEach(function(k) {
                                fn.call(this, k);
                            })
                        } else if (typeof n == 'object') {
                            fn.call(this, n);
                        } else if (n == '') {
                            delete data[i]
                        } else {
                            // 防XSS过滤
                            data[i] = $.htmlEncodeByRegExp(data[i].toString());
                        }
                    }
                })(_data);

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
                            Dialog.success('保存成功');

                            that.cache.originalData ? that.cache.originalData.push(res) : [].concat($.extend(true, [], res));
                            that.update();
                        } else {
                            Dialog.error(res.msg || '保存失败');
                        }
                    },
                    error: function(err) {
                        Dialog.error('网络错误，保存失败');
                    }

                })
                return true;
            },
            
            addDialogHandle: function(btn) {
                var that = this,
                    _html = that.render('', ejsNew, -1),
                    newDom = $(_html);

                $('.data-item-wrapper:first').prepend(newDom);
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
                    rows = $.isNull(data) ? [
                        {}
                    ] : data,
                    startFrom = startFrom ? startFrom : 0;

                return ejs.render(template, { 'rows': rows, 'startFrom': startFrom }, {
                    compileDebug: true
                });

            },
            //删除事件
            delEvent: function(event) {
                var that = this;
                var target = event.currentTarget,
                    classname = target.className,
                    dialogBox = $(target).parents('.dialog-item-box')[0];

                if (classname.indexOf('btn-cancel-hack') > -1) {
                    $(dialogBox).remove();
                }
            },
            /**
             * update data, update UI
             * @param  {[object]} data [not necessary]
             * @return {[type]}      [description]
             */
            update: function(data, type) {
                var that = this,
                    index = 0,
                    flag = true; // mean the switch of adding content, or following new content

                // pick the data whick is selected to handle
                if ($.isNull(data)) {
                    // data is  undefined, get data from the cache`s originalData
                    if ($.isNull(that.cache.originalData)) {
                        return;
                    } else {
                        data = that.cache.originalData;
                        flag = false;
                    }
                }

                var _data = data,
//                    _html = html ='',
                	_html = that.render(_data, ejsTemp, index),
                	container = $('.data-item-wrapper:first');
                
                flag ? container.append(_html) : (container.html(''), container.append(_html));
					
//				_data.forEach(function (item) {
//					console.log(item)
//					html += ejs.render(ejsTemp, _data);
//				}) 
//				$("#content").html(html)


            },
            
    	}
            main.init();
})