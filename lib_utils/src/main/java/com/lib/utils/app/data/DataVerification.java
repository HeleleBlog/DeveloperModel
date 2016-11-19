package com.lib.utils.app.data;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by orangeLe on 2016/8/5 0005.
 * 数据验证工具类
 */
public class DataVerification {

    public static boolean isFit(String regex, String testString) {
        boolean flag = false;
        Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
        if (isNull(testString)) {
            return false;
        } else {
            Matcher matcher = pattern.matcher(testString);
            flag = matcher.find();
            return flag;
        }
    }

    /**
     * 数据为空验证
     */
    public static boolean isNull(String string) {
//        string.isEmpty();
        if (string == null || string.trim().equals("")) {
            return true;
        } else {
            return false;
        }
    }

    private static long lastClickTime;

    /**
     * 避免按钮在 longtime 毫秒内多次被点击，
     * 应用场景：发送验证码按钮等等
     * @return true表示在短时间内点击了多次，false 表示短时间内没有被点击多次
     */
    public static boolean isFastDoubleClick1S(int longtime) {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < longtime) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    /**
     * 验证电话号码
     */
    public static boolean isMobile(String mobiles) {
        return isFit(
                "^((13[0-9])|(147)|(15[^4,\\D])|(17[0,6,7])|(18[0-9]))\\d{8}$",
                mobiles);
    }

    /**
     * 验证手机号码
     *
     * 移动：134、135、136、137、138、139、150、151、147(TD)、157(TD)、158、159、178、187、188
     * 联通：130、131、132、152、155、156、176、185、186 电信：133、153、177、180、189、（1349卫通）
     * 总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9 String telRegex =
     * "[1][34578]\\d{9}"
     * ;//"[1]"代表第1位为数字1，"[4578]"代表第二位可以为3、4、5、7、8中的一个，"\\d{9}"
     * 代表后面是可以是0～9的数字，有9位。
     *
     */
    public static boolean isMobileNO(String mobiles) {

        Pattern p = Pattern.compile("[1][34578]\\d{9}");

        Matcher m = p.matcher(mobiles);


        return m.matches();

    }

    /**
     * 电话号码 隐藏中间4位
     */
    public static String subMobile(String mobiles) {
        String maskNumber = null;
        try {
            maskNumber = mobiles.substring(0, 3) + "****"
                    + mobiles.substring(7, mobiles.length());
        } catch (Exception e) {
            e.printStackTrace();
            return mobiles;
        }
        return maskNumber;
    }

    /**
     * 验证网址或Url
     */
    public static boolean isURL(String Url) {
        return isFit("[a-zA-z]+://[^\\s]*", Url);
    }

    /**
     * 验证邮政编码
     */
    public static boolean isPostNum(String mobiles) {
        String telRegex = "^[1-9][0-9]{5}$";
        if (TextUtils.isEmpty(mobiles))
            return false;
        else
            return mobiles.matches(telRegex);
    }

    /**
     *验证邮箱地址是否正确
     *
     */
    public static boolean isEmail(String strEmail) {
        String strPattern = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
        Pattern p = Pattern.compile(strPattern);
        Matcher m = p.matcher(strEmail);
        return m.matches();
    }
}
