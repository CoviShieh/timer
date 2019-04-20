var register = {

    URL: {
        checkUserNickNameURL: function () {
            return path + "/user/userNickName";
        },
        checkMobileNoURL: function () {
            return path + "/user/mobileNo";
        },
        userRegisterURL: function () {
            return path + "/user";
        },
        getMobileCodeURL: function () {
            return path + "/user/mobileCode";
        },
        backLoginViewURL: function () {
            return path + "/login.html";
        }
    },

    //验证表单
    validateForm: function () {
        $("#registerForm").validate({
            rules: {
                userNickName: {
                    required: true,
                    checkName: true,
                    //ajax验证，如果后台返回true则通过，返回false则失败（即提示错误）
                    remote: {
                        url: register.URL.checkUserNickNameURL(),
                        type: "get",
                        dataType: "json",
                        data: {
                            userNickName: function () {
                                return $("#userNickName").val();
                            }
                        }
                    }
                },
                password: {
                    required: true,
                    checkPwd: true,
                },
                mobileNo: {
                    required: true,
                    checkPhone: true,
                    remote: {
                        url: register.URL.checkMobileNoURL(),
                        type: "get",
                        dataType: "json",
                        data: {
                            mobileNo: function () {
                                return $("#mobileNo").val();
                            }
                        }
                    }
                },
                captcha: {
                    required: true,
                }

            },
            messages: {
                password: {
                    required: "*必填！",
                    rangelength: "密码长度为6到18位！！"
                },
                mobileNo: {
                    req: "*必填！",
                    rangelength: "手机号码长度为11位！",
                    remote: "该手机号码已注册"
                },
                captcha: {
                    required: "*必填！",
                    rangelength: "验证码只有4位！",
                    remote: "验证码有误！"
                },
                userNickName: {
                    req: "*必填！",
                    remote: "已存在该用户名！"
                },
            },
            //显示错误的信息
            errorElement: 'div',
            errorPlacement: function (error, element) {
                var placement = $(element).data('error');
                if (placement) {
                    $(placement).append(error)
                } else {
                    error.insertAfter(element);
                }
            },


            //不通过回调
            invalidHandler: function (form, validator) {
                return false;
            },

            //ajax提交表单
            submitHandler: function () {
                $.ajax({
                    url: register.URL.userRegisterURL(),
                    type: "post",
                    data: $("#registerForm").serialize(),	//序列化表单值
                    success: function (result) {
                        if (result && result['ret'] == 1) {
                            //3秒后跳转到登陆页面
                            sweetAlert({
                                title: "注册成功！",
                                text: "3秒后跳转到登陆页面！",
                                timer: 3000,
                                type: "success",
                                showConfirmButton: false
                            });
                            setTimeout(function () {
                                window.location.href = register.URL.backLoginViewURL();
                            }, 3000);

                        } else {

                            sweetAlert({
                                title: result.msg,
                                showConfirmButton: true
                            });

                            Error.displayError(result);
                        }
                    },
                    error: function () {
                        Error.displayError(result);
                    }
                });
            },
            // 是否在敲击键盘时验证
            onkeyup: false
        });

        //自定义正则表达示验证方法
        $.validator.addMethod("checkName", function (value, element, params) {
            var checkName = /^[0-9a-zA-Z\u4e00-\u9fa5_]{3,18}$/g;
            return this.optional(element) || (checkName.test(value));
        }, "只允许3-18位英文字母、数字、汉字、下画线！");

        $.validator.addMethod("checkPwd", function (value, element, params) {
            var checkPwd = /^\w{6,18}$/g;
            return this.optional(element) || (checkPwd.test(value));
        }, "只允许6-18位英文字母、数字或者下画线！");

        $.validator.addMethod("checkPhone", function (value, element, params) {
            var checkPhone = /^1[3,5,7,8]\d{9}$/;
            return this.optional(element) || (checkPhone.test(value));
        }, "手机号码格式必须正确！");
    },

    //版本1.0：点击获取验证码，按钮倒数(刷新页面，倒数就无效了)
    reciprocal: function () {
        var wait = 5;

        function time(o) {
            if (wait == 0) {
                o.removeAttribute("disabled");
                o.innerHTML = "获取验证码";
                wait = 5;
            } else {
                o.setAttribute("disabled", true);
                o.innerHTML = wait + "秒后重新发送";
                wait--;
                setTimeout(function () {
                        time(o)
                    },
                    1000)
            }
        }

        document.getElementById("getCaptcha").onclick = function () {
            time(this);
        }
    },


    //点击获取验证码，按钮倒数(刷新页面，倒数还有效，使用了Cookie)
    reciprocal2: function () {
        //开始倒计时
        var countdown = Cookie.getCookieValue("wait");

        if (countdown == 0) {
            $("#getCaptcha")['0'].removeAttribute("disabled");
            $("#getCaptcha")['0'].innerHTML = "获取验证码";
            return;
        } else if (countdown > 0) {
            $("#getCaptcha")['0'].setAttribute("disabled", true);
            $("#getCaptcha")['0'].innerHTML = "重新发送(" + countdown + ")";
            countdown--;
            Cookie.editCookie("wait", countdown, countdown + 1);
        } else {
            return;
        }
        setTimeout(function () {
            register.reciprocal2()
        }, 1000) //每1000毫秒执行一次


    },

    //页面初始化
    init: function () {

        //表单验证
        register.validateForm();

        //获取手机验证码
        $("#getCaptcha").click(function () {

            //发送验证码
            Cookie.addCookie("wait", 60, 60);

            //如果cookie的值大于0，那么就开始倒数了
            console.log(Cookie.getCookieValue("wait"));
            if (Cookie.getCookieValue("wait") > 0) {
                register.reciprocal2();
            }
            //向输入的手机号码发送验证码
            $.ajax({
                url: register.URL.getMobileCodeURL(),
                type: "get",
                data: {
                    "mobileNo": $("#mobileNo").val()
                }
            });
        });

        //当页面重新加载的时候，也开始倒数
        register.reciprocal2();
    }

};

