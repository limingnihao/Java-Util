package org.limingnihao.model;

import java.io.Serializable;

public class BaiduSmsBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private String returnstatus;

    private String message;

    public String getReturnstatus() {
        return returnstatus;
    }

    public void setReturnstatus(String returnstatus) {
        this.returnstatus = returnstatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "BaiduSmsBean{" +
                "returnstatus='" + returnstatus + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
