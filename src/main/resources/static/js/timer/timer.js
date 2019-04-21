require(['jquery', 'GXX', 'bootstrap','jqUtils', 'ejs' ,'text!ejsTemplate/templet.ejs'],
    function($, GXX, bootstrap, jqUtils, ejs ,ejsTemp) {
    	
    	var Dialog = GXX.Dialog
    	
    	var main = {
    		
    		init: function() {
                var that = this;
				
//                that.fetchQ_AData()
                
                // 绑定监听事件
                that._listenEvent();

            },
            
            fetchQ_AData: function() {
                var that = this;
				//获取当天时间值
                var _datetime = $('#selectTime').val(),
                    _url = '/timer/searchEventByDatetime.action',
                    dtd = $.Deferred(),
                    _data = {
	                    datetime: _datetime
                	};

                $.ajax({
                    url: _url,
                    type: 'get',
                    // timeout: 2000,
                    data: _data,
                    dataType: 'json',
                    async: true,
//                    beforeSend: function() {
//                        that.animatedloader('.main-container'); //加载动画
//                    },
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
            	
//            	$('.data-item-wrapper').on('click', '.btn-operation button', that.bindingEvent_footer.bind(that));
            	
            	// toolbar 按钮
				$('.toolBar').on('click', 'button', function(event) {
					var target = event.currentTarget,
						type = target.dataset.type;
					
					if ($.isNull(type)) return;
				
					if (type = 'btn_addData') {
						that.addDialogHandle(target);
					}
				});
				
				$("#dataSubmit").on('click', that.dataSubmitHandle());
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
                            data[i] = $.htmlEncodeByRegExp(data[i]);
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
                    _html = that.render('', ejsTemp, -1),
                    newDom = $(_html);

                btn.dataset.status = 'true'; // 锁定状态，不让继续添加

                $('.data-item-wrapper:first').prepend(newDom);

//                newDom.find('input:nth-of-type(1)').focus(); //规定属于其父元素的第二个 xx 元素的每个xx：
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
            bindingEvent_footer: function(event) {
                var that = this;
                var target = event.currentTarget,
                    classname = target.className,
                    dialogBox = $(target).parents('.dialog-item-box')[0];

                if (classname.indexOf('btn-cancel-hack') > -1) {
                    $(dialogBox).remove();
                }
            },
            
            
    	}
            main.init();
})