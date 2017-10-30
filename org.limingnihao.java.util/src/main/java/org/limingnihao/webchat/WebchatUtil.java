package org.limingnihao.webchat;

import org.limingnihao.model.WXSignBean;
import org.limingnihao.util.FTPUtil;
import org.limingnihao.util.HTTPUtil;
import org.limingnihao.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.TreeMap;

public class WebchatUtil {

    private static final Logger logger = LoggerFactory.getLogger(WebchatUtil.class);

    public static String AppId = "";

    public static String Secret = "";

    public static String AccessToken = "";

    public static String JsapiTicket = "";

    /**
     * 刷 accessToken
     */
    public String accessToken() {
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + this.AppId + "&Secret=" + this.Secret;
        String result = HTTPUtil.sendGetHttpRequest(url);
        logger.info("accessToken - result=" + result);
        this.AccessToken = JsonUtil.getString(result, "access_token");
        return this.AccessToken;
    }

    /**
     * 刷 JS-ticket
     */
    public String getJSTicket() {
        String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + this.AccessToken + "&type=jsapi";
        String result = HTTPUtil.sendGetHttpRequest(url);
        logger.info("getJSTicket - result=" + result);
        this.JsapiTicket = JsonUtil.getString(result, "ticket");
        return this.JsapiTicket;
    }

    /**
     * 签名JSAPI
     * @param url
     */
    public WXSignBean getJSSign(String url) {
        //参与签名的字段包括noncestr（随机字符串）, 有效的jsapi_ticket, timestamp（时间戳）, url（当前网页的URL，不包含#及其后面部分
        Map<String, String> data2 = new TreeMap<String, String>();
        String timeStamp = SignUtil.generateTimeStamp();
        String nonceStr = SignUtil.generateNonceStr();
        data2.put("timestamp", timeStamp);
        data2.put("noncestr", nonceStr);
        data2.put("jsapi_ticket", this.JsapiTicket);
        data2.put("url", url);
        try {
            String result = SignUtil.generateSignature(data2, "", SignUtil.SignType.SHA1);
            WXSignBean sign = new WXSignBean();
            sign.setNonceStr(nonceStr);
            sign.setTimestamp(timeStamp);
            sign.setSignature(result);
            return sign;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据openId获取用户信息
     * @param openId
     */
    public String getUserByOpenId(String openId){
        String url = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=" + AccessToken + "&openid=" + openId + "&lang=zh_CN ";
        String result = HTTPUtil.sendGetHttpRequest(url);
        logger.info("getUserByOpenId - result=" + result);
        return result;
    }

    /**
     * 根据网页授权code，获取信息
     * @param code
     */
    public String getUserInfoByCode(String code) {
        String url1 = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + this.AppId + "&secret=" + this.Secret + "&code=" + code + "&grant_type=authorization_code";
        String result1 = HTTPUtil.sendGetHttpRequest(url1);
        logger.info("getUserInfoByCode - getJSToken - result=" + result1);
        String openid = JsonUtil.getString(result1, "openid");
        String user = this.getUserByOpenId(openid);
        logger.info("getUserInfoByCode - result=" + user);
        return user;
    }


}
