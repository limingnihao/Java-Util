package org.limingnihao.model;

public class WXSignBean {

    private String timestamp;   // 必填，生成签名的时间戳
    private String nonceStr;    // 必填，生成签名的随机串
    private String signature;   // 必填，签名，见附录1

    @Override
    public String toString() {
        return "WXSignBean{" +
                "timestamp='" + timestamp + '\'' +
                ", nonceStr='" + nonceStr + '\'' +
                ", signature='" + signature + '\'' +
                '}';
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
