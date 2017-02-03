package org.limingnihao.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.limingnihao.model.DateBean;
import org.limingnihao.model.DateScopeBean;
import org.limingnihao.model.WeekBean;
import org.limingnihao.type.DateFastParamType;

/**
 * Date常用方法
 *
 * @author 黎明你好
 */
public class DateUtil {

    private static final long ONE_MINUTE = 60000L;
    private static final long ONE_HOUR = 3600000L;
    private static final long ONE_DAY = 86400000L;
    private static final long ONE_WEEK = 604800000L;

    private static final String ONE_SECOND_AGO = "秒前";
    private static final String ONE_MINUTE_AGO = "分钟前";
    private static final String ONE_HOUR_AGO = "小时前";
    private static final String ONE_DAY_AGO = "天前";
    private static final String ONE_MONTH_AGO = "月前";
    private static final String ONE_YEAR_AGO = "年前";

    public static String defaultDateTimeFormatString = "yyyy-MM-dd HH:mm:ss";
    public static String defaultDateFormatString = "yyyy-MM-dd";
    public static String defaultTimeFormatString = "HH:mm:ss";

    public static void main(String args[]) {
//		System.out.println("" + format(getMonthStart(parse("2015-10-5"))) + ", " + getMonthNumber(parse("2015-10-5")));
//		System.out.println("" + format(getMonthStart(parse("2015-10-30"))) + ", " + getMonthNumber(parse("2015-10-30")));
//		System.out.println("" + format(getMonthStart(parse("2015-11-10"))) + ", " + getMonthNumber(parse("2015-11-10")));
//		System.out.println("" + format(getMonthStart(parse("2015-12-10"))) + ", " + getMonthNumber(parse("2015-12-10")));
//        System.out.println(getMonthNumber(new Date()));

//        System.out.print(getTodayScope(new Date()).getStartDate() + "-" + getTodayScope(new Date()).getEndDate());
//        System.out.print(getWeekScope(new Date()).getStartDate() + "-" + getWeekScope(new Date()).getEndDate());
//        System.out.print(getMonthScope(DateUtil.parse("2015-11-11")).getStartDate() + "-" + getMonthScope(DateUtil.parse("2015-11-11")).getEndDate());
//        System.out.print(getYearScope(new Date()).getStartDate() + "-" + getYearScope(new Date()).getEndDate());

        List<DateBean> list = DateUtil.getListByStartEnd("2016-08-01", "2016-08-31");
        for(DateBean b : list){
            System.out.println(b);
        }
    }


    /**
     * 在指定时间上，增加一个时间戳
     *
     * @param date
     * @param calendarField
     * @param amount
     * @return
     */
    private static Date add(Date date, int calendarField, int amount) {
        if (date == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(calendarField, amount);
        return c.getTime();
    }

    public static Date addYears(Date date, int amount) {
        return add(date, Calendar.YEAR, amount);
    }

    public static Date addMonths(Date date, int amount) {
        return add(date, Calendar.MONTH, amount);
    }

    public static Date addWeeks(Date date, int amount) {
        return add(date, Calendar.WEEK_OF_YEAR, amount);
    }

    public static Date addDays(Date date, int amount) {
        return add(date, Calendar.DAY_OF_MONTH, amount);
    }

    public static Date addHours(Date date, int amount) {
        return add(date, Calendar.HOUR_OF_DAY, amount);
    }

    public static Date addMinutes(Date date, int amount) {
        return add(date, Calendar.MINUTE, amount);
    }

    public static Date addSeconds(Date date, int amount) {
        return add(date, Calendar.SECOND, amount);
    }

    public static Date addMilliseconds(Date date, int amount) {
        return add(date, Calendar.MILLISECOND, amount);
    }

    public static Date getDefaultStartDate() {
        return DateUtil.parse("1900-01-01");
    }

    public static Date getDefaultEndDate() {
        return new Date();
    }

    /**
     * 当前时间格式化
     *
     * @return
     */
    public static String getNowString() {
        return format(new Date(System.currentTimeMillis()));
    }

    /**
     * 当天日期 0时0分0秒
     *
     * @return
     */
    public static Date getToday() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 当天的 - 昨天日期0时0分0秒
     *
     * @return
     */
    public static Date getYesterday() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 某天的 - 昨天日期0时0分0秒
     *
     * @return
     */
    public static Date getYesterday(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 当前的 - 明天日期0时0分0秒
     *
     * @return
     */
    public static Date getTomorrow() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 某天的 - 明天日期0时0分0秒
     *
     * @return
     */
    public static Date getTomorrow(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 将字符串转换为时间类型，按照yyyy-MM-dd 或yyyy-MM-dd HH:mm:ss格式
     *
     * @param source
     * @return Date
     */
    public static Date parse(String source) {
        if (source == null || "".equals(source)) {
            return null;
        }
        try {
            SimpleDateFormat format = new SimpleDateFormat();
            if (source.indexOf(":") > 0) {
                format.applyPattern(defaultDateTimeFormatString);
            } else {
                format.applyPattern(defaultDateFormatString);
            }
            return format.parse(source.trim());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将字符串转换为时间类型，按照指定的输入格式
     *
     * @param source
     * @return Date
     */
    public static Date parse(String source, String formatString) {
        if (source == null || "".equals(source)) {
            return null;
        }
        try {
            SimpleDateFormat format = new SimpleDateFormat();
            format.applyPattern(formatString.trim());
            return format.parse(source.trim());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将时间转换为格式为 yyyy-MM-dd HH:mm:ss 的字符串
     *
     * @param timestamp
     * @return String
     */
    public static String format(Timestamp timestamp) {
        if (timestamp != null) {
            return format(new Date(timestamp.getTime()), defaultDateTimeFormatString);
        }
        return "";
    }

    /**
     * 将时间转换为格式为 yyyy-MM-dd HH:mm:ss 的字符串
     *
     * @param timestamp
     * @param formatString
     * @return String
     */
    public static String format(Timestamp timestamp, String formatString) {
        if (timestamp != null) {
            return format(new Date(timestamp.getTime()), formatString);
        }
        return "";
    }

    /**
     * 将时间转换为格式为 yyyy-MM-dd HH:mm:ss 的字符串
     *
     * @param date
     * @return String
     */
    public static String format(Date date) {
        return format(date, defaultDateTimeFormatString);
    }

    /**
     * 将时间转换为字符串，按照指定的格式
     *
     * @param date
     * @param formatString
     * @return String
     */
    public static String format(Date date, String formatString) {
        try {
            if (date != null) {
                SimpleDateFormat format = new SimpleDateFormat(formatString);
                return format.format(date);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 随机生成一个时间
     *
     * @return
     */
    public static Date randomDate() {
        Calendar startCalendar = Calendar.getInstance();
        Calendar endCalendar = Calendar.getInstance();
        Calendar resultCalendar = Calendar.getInstance();
        startCalendar.setTime(getDefaultStartDate());
        endCalendar.setTime(getDefaultEndDate());
        long mills = (long) (Math.random() * (endCalendar.getTimeInMillis() - startCalendar.getTimeInMillis())) + startCalendar.getTimeInMillis();
        resultCalendar.setTimeInMillis(mills);
        return resultCalendar.getTime();
    }

    /**
     * 在一个时间段内，随机生成一个时间
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static Date randomDate(Date startDate, Date endDate) {
        Calendar startCalendar = Calendar.getInstance();
        Calendar endCalendar = Calendar.getInstance();
        Calendar resultCalendar = Calendar.getInstance();
        startCalendar.setTime(getDefaultStartDate());
        endCalendar.setTime(getDefaultEndDate());
        if (startDate != null) {
            startCalendar.setTime(startDate);
        }
        if (endDate != null) {
            endCalendar.setTime(endDate);
        }
        long mills = (long) (Math.random() * (endCalendar.getTimeInMillis() - startCalendar.getTimeInMillis())) + startCalendar.getTimeInMillis();
        resultCalendar.setTimeInMillis(mills);
        return resultCalendar.getTime();
    }

    /**
     * 获取本周对象 - 当天
     *
     * @param date
     * @return WeekBean
     */
    public static WeekBean getWeekBean(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        Calendar start = Calendar.getInstance();
        start.setFirstDayOfWeek(Calendar.MONDAY);
        start.setTime(calendar.getTime());
        start.set(Calendar.HOUR_OF_DAY, 0);
        start.set(Calendar.MINUTE, 0);
        start.set(Calendar.SECOND, 0);
        start.set(Calendar.MILLISECOND, 0);
        start.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);//设置为周一

        Calendar end = Calendar.getInstance();
        end.setFirstDayOfWeek(Calendar.MONDAY);
        end.setTime(start.getTime());
        end.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);//设置为周日
        WeekBean weekBean = new WeekBean();
        weekBean.setStartDate(start.getTime());
        weekBean.setEndDate(end.getTime());
        if (calendar.get(Calendar.MONTH) == start.get(Calendar.MONTH)) {
            weekBean.setWeekId(start.get(Calendar.WEEK_OF_MONTH));
        } else {
            weekBean.setWeekId(end.get(Calendar.WEEK_OF_MONTH));
        }
        // System.out.println(format(calendar.getTime()) + " - " + format(start.getTime()) + " - " + format(end.getTime()) + ", weekId=" + weekBean.getWeekId());
        return weekBean;
    }

    /**
     * 获取周列表 - 当月
     *
     * @param date
     * @return List<WeekBean>
     */
    public static List<WeekBean> getWeekList(Date date) {
        List<WeekBean> list = new ArrayList<WeekBean>();
        Calendar start = Calendar.getInstance();
        start.setTime(date);
        start.set(Calendar.DAY_OF_MONTH, 1);
        start.set(Calendar.HOUR_OF_DAY, 0);
        start.set(Calendar.MINUTE, 0);
        start.set(Calendar.SECOND, 0);
        start.set(Calendar.MILLISECOND, 0);
        // System.out.println("本月第一天=" + format(start.getTime()));

        Calendar end = Calendar.getInstance();
        end.setTime(start.getTime());
        end.add(Calendar.MONTH, 1);
        end.add(Calendar.DAY_OF_MONTH, -1);
        // System.out.println("本月最后天=" + format(end.getTime()));

        for (int i = 0; start.getTime().getTime() <= end.getTime().getTime(); i++, start.add(Calendar.DAY_OF_MONTH, 1)) {
            if (i % 7 == 0 || i % 30 == 0) {
                boolean isHave = false;
                WeekBean weekNew = getWeekBean(start.getTime());
                for (WeekBean bean : list) {
                    if (bean.getWeekId() == weekNew.getWeekId()) {
                        isHave = true;
                        break;
                    }
                }
                if (!isHave) {
                    // System.out.println(format(weekNew.getStartDate()) + " - " + format(weekNew.getEndDate()) + "-" + weekNew.getWeekId());
                    list.add(weekNew);
                }
            }
        }
        return list;
    }

    /**
     * 获取本周日期列表
     *
     * @param date
     * @return List<DateBean>
     */
    public static List<DateBean> getDateListBySame(Date date) {
        List<DateBean> list = new ArrayList<DateBean>();
        WeekBean weekBean = getWeekBean(date);
        Calendar start = Calendar.getInstance();
        start.setTime(weekBean.getStartDate());
        list.add(getDateBean(start.getTime()));
        // System.out.println(format(start.getTime()));
        while (start.getTime().getTime() < weekBean.getEndDate().getTime()) {
            start.add(Calendar.DAY_OF_MONTH, 1);
            // System.out.println(format(start.getTime()));
            list.add(getDateBean(start.getTime()));
        }
        return list;
    }

    /**
     * 获取上周日期列表
     *
     * @param date
     * @return List<DateBean>
     */
    public static List<DateBean> getDateListByPrevious(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -7);
        return getDateListBySame(calendar.getTime());
    }

    /**
     * 获取下周日期列表
     *
     * @param date
     * @return List<DateBean>
     */
    public static List<DateBean> getDateListByNext(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, 7);
        return getDateListBySame(calendar.getTime());
    }

    /**
     * 获取时间Bean
     *
     * @param date
     * @return DateBean
     */
    public static DateBean getDateBean(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        DateBean bean = new DateBean();
        bean.setDate(date);
        bean.setYear(calendar.get(Calendar.YEAR));
        bean.setMonth(calendar.get(Calendar.MONTH) + 1);
        bean.setDay(calendar.get(Calendar.DAY_OF_MONTH));
        bean.setHour(calendar.get(Calendar.HOUR_OF_DAY));
        bean.setMinute(calendar.get(Calendar.MINUTE));
        bean.setSecond(calendar.get(Calendar.SECOND));
        bean.setWeek(calendar.get(Calendar.DAY_OF_WEEK) - 1);
        bean.setWeek(bean.getWeek() == 0 ? 7 : bean.getWeek());
        String weekName = "";
        switch (bean.getWeek()) {
            case 1:
                weekName = "星期一";
                break;
            case 2:
                weekName = "星期二";
                break;
            case 3:
                weekName = "星期三";
                break;
            case 4:
                weekName = "星期四";
                break;
            case 5:
                weekName = "星期五";
                break;
            case 6:
                weekName = "星期六";
                break;
            case 7:
                weekName = "星期日";
                break;
        }
        bean.setWeekName(weekName);
        return bean;
    }

    /**
     * 当前时间bean形式
     *
     * @return
     */
    public static DateBean getCurrentDateBean() {
        return getDateBean(new Date());
    }


    /**
     * 获取某月的总天数
     *
     * @param year
     * @param month
     * @return
     */
    public static int getMonthCount(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);//Java月份才0开始算
        int dateOfMonth = cal.getActualMaximum(Calendar.DATE);
        return dateOfMonth;
    }

    /**
     * 获取当前时间,所在周的第一天. 周一为第一天
     *
     * @param now
     * @return
     */
    public static Date getWeekStart(Date now) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        Calendar start = Calendar.getInstance();
        start.setFirstDayOfWeek(Calendar.MONDAY);
        start.setTime(calendar.getTime());
        start.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);//设置为周一
        return new Date(start.getTimeInMillis());
    }


    /**
     * 获取当前时间, 是本周第几天
     *
     * @param now
     * @return
     */
    public static int getWeekNumber(Date now) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);

        Calendar start = Calendar.getInstance();
        start.setFirstDayOfWeek(Calendar.MONDAY);
        start.setTime(calendar.getTime());
        start.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);//设置为周一
        int number = calendar.get(Calendar.DAY_OF_WEEK) - start.get(Calendar.DAY_OF_WEEK) + 1;
        number = number == 0 ? 7 : number;
        return number;
    }

    /**
     * 获取当前时间,所在月的第一天.
     *
     * @param now
     * @return
     */
    public static Date getMonthStart(Date now) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        Calendar start = Calendar.getInstance();
        start.setTime(calendar.getTime());
        start.set(Calendar.DAY_OF_MONTH, 1);
        return new Date(start.getTimeInMillis());
    }


    /**
     * 获取当前时间, 是本月第几天
     *
     * @param now
     * @return
     */
    public static int getMonthNumber(Date now) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public static List<WeekBean> getCustomWeekList(Date startDate, Integer weekPoint, Date endDate, boolean isEnd) {
        if (isEnd) {
            if (weekPoint.intValue() < 7)
                weekPoint = Integer.valueOf(weekPoint.intValue() + 1);
            else {
                weekPoint = Integer.valueOf(1);
            }
        }
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(startDate);
        int dayOfWeek = startCalendar.get(7) - 1;
        if (dayOfWeek != weekPoint.intValue()) {
            int value = weekPoint.intValue() - dayOfWeek;
            if (value < 0) {
                value = 7 + value;
            }
            for (int i = 0; i < value; i++) {
                startDate = getTomorrow(startDate);
            }
        }
        List<WeekBean> list = new ArrayList<WeekBean>();
        Date nowEnd = null;
        int i = 1;
        do {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(startDate);
            calendar.add(5, 6);
            calendar.set(11, 0);
            calendar.set(12, 0);
            calendar.set(13, 0);
            calendar.set(14, 0);
            WeekBean bean = new WeekBean();
            bean.setWeekId(i);
            bean.setStartDate(startDate);
            bean.setEndDate(calendar.getTime());
            bean.setWeekName("[" + format(startDate, "yyyy-MM-dd") + "]" + " - " + "[" + format(calendar.getTime(), "yyyy-MM-dd]"));
            nowEnd = calendar.getTime();
            startDate = getTomorrow(nowEnd);
            list.add(bean);
            i++;
        } while (nowEnd.getTime() < endDate.getTime());

        return list;
    }

    /**
     * 获得当天时间区间
     */
    public static DateScopeBean getTodayScope(Date date) {
        DateScopeBean bean = new DateScopeBean();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        bean.setStartDate(DateUtil.format(calendar.getTime(), "yyyy-MM-dd HH:mm:ss"));
        calendar.set(Calendar.HOUR_OF_DAY, 24);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        bean.setEndDate(DateUtil.format(calendar.getTime(), "yyyy-MM-dd HH:mm:ss"));
        return bean;
    }

    /**
     * 获得本周时间区间
     */
    public static DateScopeBean getWeekScope(Date date) {
        DateScopeBean bean = new DateScopeBean();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        Calendar start = Calendar.getInstance();
        start.setFirstDayOfWeek(Calendar.MONDAY);
        start.setTime(calendar.getTime());
        start.set(Calendar.HOUR_OF_DAY, 0);
        start.set(Calendar.MINUTE, 0);
        start.set(Calendar.SECOND, 0);
        start.set(Calendar.MILLISECOND, 0);
        start.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);//设置为周一
        bean.setStartDate(DateUtil.format(start.getTime(), "yyyy-MM-dd HH:mm:ss"));
        Calendar end = Calendar.getInstance();
        end.setFirstDayOfWeek(Calendar.MONDAY);
        end.setTime(calendar.getTime());
        end.set(Calendar.HOUR_OF_DAY, 24);
        end.set(Calendar.MINUTE, 0);
        end.set(Calendar.SECOND, 0);
        end.set(Calendar.MILLISECOND, 0);
        end.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);//设置为周日
        bean.setEndDate(DateUtil.format(end.getTime(), "yyyy-MM-dd HH:mm:ss"));
        return bean;
    }

    /**
     * 获得本月时间区间
     */
    public static DateScopeBean getMonthScope(Date date) {
        DateScopeBean bean = new DateScopeBean();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        Calendar start = Calendar.getInstance();
        start.setTime(calendar.getTime());
        start.set(Calendar.DAY_OF_MONTH, 1);
        start.set(Calendar.HOUR_OF_DAY, 0);
        start.set(Calendar.MINUTE, 0);
        start.set(Calendar.SECOND, 0);
        start.set(Calendar.MILLISECOND, 0);
        bean.setStartDate(DateUtil.format(start.getTime(), "yyyy-MM-dd HH:mm:ss"));
        Calendar end = Calendar.getInstance();
        end.setTime(calendar.getTime());
        end.set(Calendar.DAY_OF_MONTH, end.getActualMaximum(Calendar.DAY_OF_MONTH));
        end.set(Calendar.HOUR_OF_DAY, 23);
        end.set(Calendar.MINUTE, 59);
        end.set(Calendar.SECOND, 59);
        end.set(Calendar.MILLISECOND, 0);
        bean.setEndDate(DateUtil.format(end.getTime(), "yyyy-MM-dd HH:mm:ss"));
        return bean;
    }

    /**
     * 获得本年的时间区间
     */
    public static DateScopeBean getYearScope(Date date) {
        DateScopeBean bean = new DateScopeBean();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        Calendar start = Calendar.getInstance();
        start.setTime(calendar.getTime());
        start.set(Calendar.DAY_OF_YEAR, 1);
        start.set(Calendar.HOUR_OF_DAY, 0);
        start.set(Calendar.MINUTE, 0);
        start.set(Calendar.SECOND, 0);
        start.set(Calendar.MILLISECOND, 0);
        bean.setStartDate(DateUtil.format(start.getTime(), "yyyy-MM-dd HH:mm:ss"));
        Calendar end = Calendar.getInstance();
        end.setTime(calendar.getTime());
        end.set(Calendar.DAY_OF_YEAR, end.getActualMaximum(Calendar.DAY_OF_YEAR));
        end.set(Calendar.HOUR_OF_DAY, 23);
        end.set(Calendar.MINUTE, 59);
        end.set(Calendar.SECOND, 59);
        end.set(Calendar.MILLISECOND, 0);
        bean.setEndDate(DateUtil.format(end.getTime(), "yyyy-MM-dd HH:mm:ss"));
        return bean;
    }

    /**
     * 获取日期的开始时间
     *
     * @param date
     * @return
     */
    public static Date getDateBegin(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 根据生日计算年龄
     *
     * @param birthday
     * @return
     */
    public static int getAgeByBirthday(Date birthday) {
        Calendar cal = Calendar.getInstance();

        if (cal.before(birthday)) {
            throw new IllegalArgumentException(
                    "The birthDay is before Now.It's unbelievable!");
        }

        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH) + 1;
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);

        cal.setTime(birthday);
        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH) + 1;
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirth;

        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                // monthNow==monthBirth
                if (dayOfMonthNow < dayOfMonthBirth) {
                    age--;
                }
            } else {
                // monthNow>monthBirth
                age--;
            }
        }
        return age;
    }

    public static void setFastDateParams(Integer paramDate, Timestamp startDate, Timestamp endDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        if (paramDate.intValue() == DateFastParamType.ALL.value()) {
            startDate = null;
            endDate = null;
        } else if (paramDate.intValue() == DateFastParamType.TODAY.value()) {
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            startDate.setTime(calendar.getTime().getTime());
            calendar.set(Calendar.HOUR_OF_DAY, 24);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            endDate.setTime(calendar.getTime().getTime());
        } else if (paramDate.intValue() == DateFastParamType.IN_WEEK.value()) {
            calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - 7);
            startDate.setTime(calendar.getTime().getTime());
            endDate.setTime(new Date().getTime());
        } else if (paramDate.intValue() == DateFastParamType.IN_MONTH.value()) {
            calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1);
            startDate.setTime(calendar.getTime().getTime());
            endDate.setTime(new Date().getTime());
        } else if (paramDate.intValue() == DateFastParamType.IN_THREE_MONTH.value()) {
            calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 3);
            startDate.setTime(calendar.getTime().getTime());
            endDate.setTime(new Date().getTime());
        } else if (paramDate.intValue() == DateFastParamType.IN_YEAR.value()) {
            calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR - 1));
            startDate.setTime(calendar.getTime().getTime());
            endDate.setTime(new Date().getTime());
        } else if (paramDate.intValue() == DateFastParamType.ONE_YEAR_AGO.value()) {
            calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) - 1);
            endDate.setTime(calendar.getTime().getTime());
        } else if (paramDate.intValue() == DateFastParamType.NATURAL_MONTH.value()) {
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            startDate.setTime(calendar.getTime().getTime());
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
            calendar.set(Calendar.MILLISECOND, 0);
            endDate.setTime(calendar.getTime().getTime());
        }
    }

    /**
     * 获取时间列表,根据开始、结束时间
     * @return
     */
    public static List<DateBean> getListByStartEnd(String start, String end){
        Calendar calendarStart = Calendar.getInstance();
        calendarStart.setTime(DateUtil.parse(start));

        Calendar calendarEnd = Calendar.getInstance();
        calendarEnd.setTime(DateUtil.parse(end));

        List<DateBean> list = new ArrayList<DateBean>();
        DateBean bean = DateUtil.getDateBean(calendarStart.getTime());
        list.add(bean);
        while(calendarStart.getTimeInMillis() < calendarEnd.getTimeInMillis()){
            calendarStart.add(Calendar.DAY_OF_MONTH, 1);
            bean = DateUtil.getDateBean(calendarStart.getTime());
            list.add(bean);
        }
        return list;
    }

    public static String formatPast(Date date) {
        long delta = new Date().getTime() - date.getTime();
        if (delta < 1L * ONE_MINUTE) {
            long seconds = toSeconds(delta);
            return (seconds <= 0 ? 1 : seconds) + ONE_SECOND_AGO;
        }
        if (delta < 45L * ONE_MINUTE) {
            long minutes = toMinutes(delta);
            return (minutes <= 0 ? 1 : minutes) + ONE_MINUTE_AGO;
        }
        if (delta < 24L * ONE_HOUR) {
            long hours = toHours(delta);
            return (hours <= 0 ? 1 : hours) + ONE_HOUR_AGO;
        }
        if (delta < 48L * ONE_HOUR) {
            return "昨天";
        }
        return format(date, defaultDateFormatString);
//        if (delta < 30L * ONE_DAY) {
//            long days = toDays(delta);
//            return (days <= 0 ? 1 : days) + ONE_DAY_AGO;
//        }
//        if (delta < 12L * 4L * ONE_WEEK) {
//            long months = toMonths(delta);
//            return (months <= 0 ? 1 : months) + ONE_MONTH_AGO;
//        } else {
//            long years = toYears(delta);
//            return (years <= 0 ? 1 : years) + ONE_YEAR_AGO;
//        }
    }

    private static long toSeconds(long date) {
        return date / 1000L;
    }

    private static long toMinutes(long date) {
        return toSeconds(date) / 60L;
    }

    private static long toHours(long date) {
        return toMinutes(date) / 60L;
    }

    private static long toDays(long date) {
        return toHours(date) / 24L;
    }

    private static long toMonths(long date) {
        return toDays(date) / 30L;
    }

    private static long toYears(long date) {
        return toMonths(date) / 365L;
    }

}
