package org.limingnihao.model;

import java.io.Serializable;

/**
 * 日期周期
 */
public class DateScopeBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private String startDate;

    private String endDate;

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
