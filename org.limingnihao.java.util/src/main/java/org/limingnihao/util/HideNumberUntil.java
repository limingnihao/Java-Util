package org.limingnihao.util;

/**
 * Created by 唐溶波 on 16/3/10.
 */
public class HideNumberUntil {


    /**
     * 隐藏手机号中间4位
     * @param phone
     * @return
     */
    public static String hidePhone(String phone){
        if(StringUtil.isBlank(phone)){
            return phone;
        }
        return phone.substring(0,3) + "****" + phone.substring(7, phone.length());
    }

    /**
     * 隐藏邮箱中间部分
     * @param email
     * @param regex 正则表达式
     * @return
     */
    public static String hideEmail(String email ,String regex){
        regex = StringUtil.isNotBlank(regex) ? regex :  "(\\w{3})(\\w+)(\\w{2})(@\\w+)";
        if(StringUtil.isNotBlank(email)){
            return email.replaceAll(regex, "$1****$3$4");
        }

        return email;
    }

}
