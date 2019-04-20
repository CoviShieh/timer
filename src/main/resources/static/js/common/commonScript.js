//获取项目的路径
var path = "";
$(function () {
    var strFullPath = window.document.location.href;
    var strPath = window.document.location.pathname;
    var pos = strFullPath.indexOf(strPath);
    var prePath = strFullPath.substring(0, pos);
    var postPath = strPath.substring(0, strPath.substr(1).indexOf('/') + 1);
    path = prePath;
    
    //侧边导航条
    $("#button-collapse").sideNav();
    
    //退出用户
    $("#logout").click(function () {
        common.logOutUser();
    });
});


var common = {
    URL: {
        backIndexURL: function () {
            return path + "/index.html";
        },
        getUserURL: function () {
            return path + "/session";
        },
        logOutUserURL: function () {
            return path + "/session";
        }
    },

    //退出用户
    logOutUser: function () {
        $.ajax({
            url: common.URL.logOutUserURL(),
            type: "delete",
            success: function (result) {
                if (result && result['ret'] == 1) {
                    //退出成功返回首页
                    window.location.href = common.URL.backIndexURL();
                } else {
                    console.log(result)
                }
            },
            error: function () {
                Error.displayError(result);
            }
        });
    },
    //得到用户的信息(需要用到result，因此得设置成同步)
    getUser: function () {

        var responseText = "";
        $.ajax({
            url: common.URL.getUserURL(),
            type: "get",
            async: false,
            success: function (result) {
                if (result && result['ret'] == 1) {

                    //如果登陆了，那么将注册和登陆按钮隐藏掉
                    $("#registerLi").hide();
                    $("#loginLi").hide();

                    //为id赋值(很多地方可能都会用到)
                    $("#userId").val(result['data'].id);


                    //如果存在，那么初始化“完善个人信息”表单的数据(左侧导航条)
                    $("#userNickName").html(result['data'].userNickName);
//                    if (result['data'].email != null && result['data'].email != "") {
//                        $("#userEmail").html(result['data'].email);
//                    }
                    if (result['data'].headPortrait != null && result['data'].headPortrait != "") {
                        $("#slide-out-headPortrait").attr("src", file_path + result['data'].headPortrait);
                    }

                    //返回值给调用者判断
                    responseText = result;

                } else {
                    //如果没有登陆，将个人中心模块的按钮隐藏掉
                    $("#personalLi").hide();
                }
            },
            error: function () {
                Error.displayError(result);
            }
        });
        return responseText;
    }
};

// 显示或者记录错误
var Error = {

    //显示错误
    displayError: function (result) {
        console.log(Error.sweetAlertInfo(result.msg));
    },
    //配置错误的信息
    sweetAlertInfo: function (info) {

        //sweertAlertObj默认的配置
        var sweertAlertObj = {
            title: "操作失败",
            type: "error",
            showConfirmButton: true
        };

        //灵活的配置
        sweertAlertObj.text = info;
        return sweertAlertObj;
    }

};

var Cookie = {
    //发送验证码时添加cookie
    addCookie: function (name, value, expiresHours) {

        var cookieString = name + "=" + escape(value);
        //判断是否设置过期时间,0代表关闭浏览器时失效
        if (expiresHours > 0) {
            var date = new Date();
            date.setTime(date.getTime() + expiresHours * 1000);
            cookieString = cookieString + ";expires=" + date.toUTCString();
        }
        document.cookie = cookieString;

    },
    //修改cookie的值
    editCookie: function (name, value, expiresHours) {
        var cookieString = name + "=" + escape(value);	//escape() 函数可对字符串进行编码，这样就可以在所有的计算机上读取该字符串。
        if (expiresHours > 0) {
            var date = new Date();
            date.setTime(date.getTime() + expiresHours * 1000); //单位是毫秒
            cookieString = cookieString + ";expires=" + date.toGMTString();
        }
        document.cookie = cookieString;
    },
    //根据名字获取cookie的值
    getCookieValue: function (name) {
        var strCookie = document.cookie;
        var arrCookie = strCookie.split("; ");
        for (var i = 0; i < arrCookie.length; i++) {
            var arr = arrCookie[i].split("=");
            if (arr[0] == name) {
                return unescape(arr[1]);
                break;
            }
        }
    }
}
