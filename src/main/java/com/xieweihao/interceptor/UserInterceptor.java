package com.xieweihao.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.xieweihao.entity.User;
import com.xieweihao.service.UserService;
import com.xieweihao.utils.CookieUtil;
import com.xieweihao.utils.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by ozc on 2017/12/8.
 *
 * @author ozc
 * @version 1.0
 * <p>
 * 实现自动登陆功能
 */
public class UserInterceptor implements HandlerInterceptor {


    @Autowired
    private UserService userService;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        User sessionUser = (User) request.getSession().getAttribute("user");

        // 已经登陆了，放行
        if (sessionUser != null) {
            return true;
        } else {
            //得到带过来cookie是否存在
            String loginToken = CookieUtil.findCookieByName(request, "loginToken");
            if (StringUtils.isStrNotBlank(loginToken)) { 
                //到数据库查询有没有该Cookie
                User user = userService.findUserByLoginToken(loginToken);
                if (user != null) {
                    request.getSession().setAttribute("user", user);
                    return true;
                } else {
                    //没有该Cookie与之对应的用户(Cookie不匹配)
                    CookieUtil.clearCookie(request, response, "loginToken");
                    return false;
                }
            } else {

                //没有cookie、也没有登陆。是index请求获取用户信息，可以放行
                if (request.getRequestURI().contains("session")) {
                    return true;
                }

                //没有cookie凭证
                response.sendRedirect("/login.html");
                return false;
            }
        }


    }

    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {


    }

    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}

