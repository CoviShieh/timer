package com.xieweihao.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import com.xieweihao.captcha.Captcha;
import com.xieweihao.captcha.GifCaptcha;
import com.xieweihao.entity.User;
import com.xieweihao.enums.ResultEnum;
import com.xieweihao.exception.UserException;
import com.xieweihao.message.SendSmsDemo;
import com.xieweihao.service.UserService;
import com.xieweihao.utils.CookieUtil;
import com.xieweihao.utils.Result;
import com.xieweihao.utils.WebUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

@RestController
public class UserController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;

    /**
     * 用户注册
     *
     * @param user
     */
    @PostMapping(value = "/user", produces = {"application/json;charset=UTF-8"})
    public Result register(@Valid User user, String captcha, BindingResult bindingResult, HttpSession session) {

        try {
        	//BindingResult 接收 @Valid 错误参数信息
            // 传过来的参数错误
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            if (allErrors != null && allErrors.size() > 0) {
                logger.error(bindingResult.getFieldError().getDefaultMessage());
                return Result.error("提交的参数有误").put("data", bindingResult.getFieldError().getDefaultMessage());
            }

            //判断验证码是否正确
            if (WebUtils.validateCaptcha(captcha, "validateCode", session)) {

                //判断完就把验证码删除了
                session.removeAttribute("validateCode");

                // 注册
                User saveUser = userService.userRegister(user);
                if (saveUser != null) {
                    return Result.ok("成功");
                } else {
                    return Result.error();
                }
            } else {
                return Result.error("验证码错误");
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new UserException(ResultEnum.REGISTER_ERROR);
        }
    }

    /**
     * 更新用户的数据
     */
    @PutMapping(value = "/user", produces = {"application/json;charset=UTF-8"})
    public Result uploadUserInfo(String email, String headPortrait, HttpSession session) {

        try {
            User user = (User) session.getAttribute("user");
            if (email != null && email != "") {
                user.setEmail(email);
            }
            if (headPortrait != null && headPortrait != "") {
                user.setHeadPortrait(headPortrait);
            }
            userService.userUpload(user);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
//            throw new BusinessException("更新用户信息失败");
            throw new UserException(ResultEnum.UPLOAD_USERINFO_ERROR);
        }

        return Result.ok("更新成功");
    }


    /**
     * 发送手机验证码(设置验证码到session中)
     */
    @GetMapping("/user/mobileCode")
    public void getMobileCode(HttpSession session, String mobileNo) {
        //生成4位随机数
        try {
            String fourRandom = WebUtils.getFourRandom();
            SendSmsDemo.sendSMS(mobileNo, "3", new String[]{fourRandom, "3"}, session);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new UserException(ResultEnum.SEND_MOBILEMESSAGE_ERROR);
        }

    }

    /**
     * 获取用户信息
     *
     * @param
     */
    @GetMapping(value = "/session", produces = {"application/json;charset=UTF-8"})
    public Result getUserInfo(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
        	return Result.ok("获取成功").put("data", user);
        } else {
            return Result.error("找不到用户/用户还没登陆");
        }
    }
    /**
     * 检查手机是否已被使用（由Juqery-validation的remote调用，返回true或者false）
     */
    @GetMapping("/user/mobileNo")
    public void validateMobileNo(String mobileNo, PrintWriter writer) {
        List<User> users = userService.findUserByMobileNo(mobileNo);
        if (users != null && users.size() > 0) {
            writer.write("false");
        } else {
            writer.write("true");
        }

    }

    /**
     * 检查昵称是否已被使用（由Juqery-validation的remote调用，返回true或者false）
     */
    @GetMapping("/user/userNickName")
    public void validateUserNickName(String userNickName, PrintWriter writer) {
        List<User> users = userService.findUserByUserNickName(userNickName);
        if (users != null && users.size() > 0) {
            writer.write("false");
        } else {
            writer.write("true");
        }
    }

    /**
     * 获取GIF验证码
     *
     * @param response
     * @param request
     * @throws IOException
     */
    @GetMapping("/user/gifCode")
    public void getGifCode(HttpServletResponse response, HttpServletRequest request) throws IOException {
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/gif");

        // gif格式动画验证码 宽，高，位数。
        Captcha captcha = new GifCaptcha(146, 42, 4);

        ServletOutputStream out = response.getOutputStream();
        captcha.out(out);
        request.getSession().setAttribute("captcha", captcha.text().toLowerCase());
    }


    /**
     * 用户登陆
     *
     * @param mobileNo
     * @param password
     * @param inputCaptcha
     * @param session
     * @return
     */
    @PostMapping(value = "/user/session", produces = {"application/json;charset=UTF-8"})
    public Result login(String mobileNo, String password, String inputCaptcha, HttpSession session, HttpServletResponse response) {

        //判断验证码是否正确
        if (WebUtils.validateCaptcha(inputCaptcha, "captcha", session)) {

            //判断有没有该用户
            User user = userService.userLogin(mobileNo, password);
            if (user != null) {

                /*设置自动登陆，一个星期.  将token保存在数据库中*/
                String loginToken = WebUtils.md5(new Date().toString() + session.getId());
                user.setLoginToken(loginToken);
                User userInfo = userService.userUpload(user);

                session.setAttribute("user", userInfo);

                CookieUtil.addCookie(response,"loginToken",loginToken,604800);

                return Result.ok().put("data", userInfo);

            } else {
                return Result.error("用户名/密码错误");
            }
        } else {
        	return Result.error("验证码错误");
        }

    }

    /**
     * 用户退出
     *
     * @param session
     * @return
     */
    @DeleteMapping(value = "/session", produces = {"application/json;charset=UTF-8"})
    public Result logout(HttpSession session,HttpServletRequest request,HttpServletResponse response ) {

        //删除session和cookie
        session.removeAttribute("user");

        CookieUtil.clearCookie(request, response, "loginToken");

        return Result.ok("用户已退出");
    }


}

