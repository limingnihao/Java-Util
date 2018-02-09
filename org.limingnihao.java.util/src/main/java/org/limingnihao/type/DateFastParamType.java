package org.limingnihao.type;

import java.io.Serializable;

/**
 * 日期参数
 */
public enum DateFastParamType implements Serializable {

    /**
     * 全部的
     */
    ALL(0),
    /**
     * 当天
     */
    TODAY(1),

    /**
     * 一周内
     */
    IN_WEEK(2),

    /**
     * 一个月内
     */
    IN_MONTH(3),

    /**
     * 3个月内
     */
    IN_THREE_MONTH(4),

    /**
     * 一年内
     */
    IN_YEAR(5),

    /**
     * 一年前的
     */
    ONE_YEAR_AGO(6),

    /**
     * 自然月内
     */
    NATURAL_MONTH(20);


    private int value;

    DateFastParamType(int value) {
        this.value = value;
    }

    public static DateFastParamType valueOf(Integer i) {
        if (i != null) {
            return valueOf(i.intValue());
        } else {
            return null;
        }
    }

    public static DateFastParamType valueOf(int i) {
        for (DateFastParamType value : values()) {
            if (value.equals(i)) {
                return value;
            }
        }
        return null;
    }

    public boolean equals(int value) {
        return this.value == value;
    }

    public String getName() {
        switch (this) {
            case ALL:
                return "全部的";
            case TODAY:
                return "当天的";
            case IN_WEEK:
                return "一周内";
            case IN_MONTH:
                return "一个月内";
            case IN_THREE_MONTH:
                return "三个月内的";
            case IN_YEAR:
                return "一年内";
            case ONE_YEAR_AGO:
                return "一年前的";
            case NATURAL_MONTH:
                return "自然月";
            default:
                return "未定义";
        }
    }

    public boolean equals(Integer value) {
        if (value != null) {
            return this.value == value.intValue();
        } else {
            return false;
        }
    }

    public int value() {
        return this.value;
    }

}
