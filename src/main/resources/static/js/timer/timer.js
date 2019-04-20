require(['jquery', 'path', 'GXX', 'md5', 'bootstrap', 'datapicker','jqUtils', 'ejs', 'text!ejsTemplate/timer/templet.ejs'],
    function($, path, GXX, md5, bootstrap,datapicker, jqUtils, ejs, ejsTemp) {
    	
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
            	
            	$('.data-item-wrapper').on('click', '.btn-operation button', that.bindingEvent_footer.bind(that));
            	
            	// toolbar 按钮
				$('.toolBar').on('click', 'button', function(event) {
					var target = event.currentTarget,
						type = target.dataset.type;
				
					if ($.isNull(type)) return;
				
					if (type = 'btn_addData') {
						that.addDialogHandle(target);
					}
				});
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
                    $('[data-type="btn_addDialog"]')[0].dataset.status = 'false'; // 回复状态，允许继续添加
                }
            },
            
            
    	}
            main.init();
})