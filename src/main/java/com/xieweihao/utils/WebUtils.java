package com.xieweihao.utils;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * Web层工具类
 */
public class WebUtils {


    /**
     * 判断验证码是否正确
     *
     * @param inputCaptcha 页面输入的验证码
     * @param sessionAttr  存在session域的key名称
     * @param session
     * @return
     */
    public static boolean validateCaptcha(String inputCaptcha, String sessionAttr, HttpSession session) {

        String captchaVal = (String) session.getAttribute(sessionAttr);
        if (inputCaptcha != null && captchaVal != null && inputCaptcha.equalsIgnoreCase(captchaVal)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取得到4位的随机数
     *
     * @return
     */
    public static String getFourRandom() {
        Random random = new Random();
        String fourRandom = random.nextInt(10000) + "";
        int randLength = fourRandom.length();
        if (randLength < 4) {
            for (int i = 1; i <= 4 - randLength; i++)
                fourRandom = "0" + fourRandom;
        }
        return fourRandom;
    }

    /**
     * 转换BufferedImage 数据为byte数组
     *
     * @param bImage  Image对象
     * @param format image格式字符串.如"gif","png"
     * @return byte数组
     */
    public static byte[] imageToBytes(BufferedImage bImage, String format) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            ImageIO.write(bImage, format, out);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out.toByteArray();
    }


    /**
     * md5加密
     * @param plainText
     * @return
     */
    public static String md5(String plainText) {
        byte[] secretBytes = null;
        try {
            secretBytes = MessageDigest.getInstance("md5").digest(
                    plainText.getBytes());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("没有md5这个算法！");
        }
        String md5code = new BigInteger(1, secretBytes).toString(16);// 16进制数字
        // 如果生成数字未满32位，需要前面补0
        for (int i = 0; i < 32 - md5code.length(); i++) {
            md5code = "0" + md5code;
        }
        return md5code;
    }
}

