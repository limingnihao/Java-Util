package org.limingnihao.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 数字常用方法
 * 
 * @author 黎明你好
 * 
 */
public class NumberUtil {

	/**
	 * 文件大小字符串转换
	 * 
	 * @param size
	 * @param scale
	 * @return
	 */
	public static String conversionUnitMemory(Double size, int scale) {
		Double value = (double) 0;
		String unitString = " B";
		if (size == null || size == 0) {
			value = (double) 0;
			unitString = " B";
		} else if (size < Math.pow(1024, 1)) {
			value = size;
			unitString = " KB";
		} else if (size >= Math.pow(1024, 1) && size < Math.pow(1024, 2)) {
			value = size / Math.pow(1024, 1);
			unitString = " KB";
		} else if (size >= Math.pow(1024, 2) && size < Math.pow(1024, 3)) {
			value = size / Math.pow(1024, 2);
			unitString = " MB";
		} else if (size >= Math.pow(1024, 3) && size < Math.pow(1024, 4)) {
			value = size / Math.pow(1024, 3);
			unitString = " GB";
		} else if (size >= Math.pow(1024, 4) && size < Math.pow(1024, 5)) {
			value = size / Math.pow(1024, 4);
			unitString = " TB";
		} else if (size >= Math.pow(1024, 5) && size < Math.pow(1024, 6)) {
			value = size / Math.pow(1024, 5);
			unitString = " PB";
		} else {
			value = size;
			unitString = " B";
		}
		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(scale, BigDecimal.ROUND_HALF_UP);
		return bd.doubleValue() + unitString;
	}

	/**
	 * 时间字符串转换 - 毫秒
	 * 
	 * @param millisecond
	 * @return
	 */
	public static String conversionUnitMillisecond(Double millisecond) {
		String unitString = "0秒";
		if (millisecond == null || millisecond == 0) {
			return "0 秒";
		} else if (millisecond < 1000) {
			unitString = millisecond + "毫秒";
		} else if (millisecond < 1000 * 60) {
			int second = (int) (millisecond / 1000);
			unitString = second + "秒";
		} else if (millisecond < 1000 * 60 * 60) {
			int minute = (int) (millisecond / (1000 * 60));
			int second = (int) (millisecond % (1000 * 60) / 1000);
			if (second != 0) {
				unitString = minute + "分" + second + "秒";
			} else {
				unitString = minute + "分";
			}
		} else if (millisecond < 1000 * 60 * 60 * 60) {
			int hour = (int) (millisecond / (1000 * 60 * 60));
			int minute = (int) (millisecond % (1000 * 60 * 60) / 1000 / 60);
			int second = (int) (millisecond / 1000) % 60;
			unitString = hour + "时" + minute + "分" + second + "秒";
		} else {
			int day = (int) (millisecond / (1000 * 60 * 60 * 24));
			int hour = (int) (millisecond % (1000 * 60 * 60 * 24) / 1000 / 60 / 60);
			int minute = (int) (millisecond % (1000 * 60 * 60) / 1000 / 60);
			int second = (int) (millisecond / 1000) % 60;
			unitString = day + "天" + hour + "时" + minute + "分" + second + "秒";
		}
		return unitString;
	}

	public static double getDoubleScale(double value, int scale) {
		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(scale, BigDecimal.ROUND_HALF_UP);
		return bd.doubleValue();
	}

	public static BigDecimal toBigDecimal(Double value, int scale){
		if(value != null){
			return new BigDecimal(value).setScale(10, BigDecimal.ROUND_HALF_UP);
		}else{
			return new BigDecimal(0).setScale(10, BigDecimal.ROUND_HALF_UP);
		}
	}

	/**
	 * 是否是正整数 大于 0的int类型
	 * 
	 * @return
	 */
	public static boolean isSignless(Integer value) {
		if (value != null && value.intValue() > 0) {
			return true;
		}
		return false;
	}


	/**
	 * 是否是正整数 大于 0的int类型
	 *
	 * @return
	 */
	public static boolean isSignless(Long value) {
		if (value != null && value.longValue() > 0) {
			return true;
		}
		return false;
	}

	public static void main(String args[]) {
		double g = 7D * 1024D * 1024D * 1024D;
		double size = 38855161;
		System.out.println(NumberUtil.conversionUnitMemory(g, 2));
		System.out.println(size / g);
	}

	/**
	 * double类型转换
	 * 
	 * @param value
	 * @return
	 */
	public static double parseDouble(Object value) {
		try {
			if (value != null && value.toString() != null && value.toString().length() > 0) {
				return Double.parseDouble(value.toString());
			}
		} catch (NumberFormatException e) {
		}
		return 0;
	}

	/**
	 * 整数转换
	 * 
	 * @param value
	 * @return
	 */
	public static int parseInt(Object value) {
		try {
			if (value != null && value.toString() != null && value.toString().length() > 0) {
				return Integer.parseInt(value.toString());
			}
		} catch (NumberFormatException e) {
		}
		return 0;
	}

	/**
	 * 整数转换 - 失败使用默认值
	 * 
	 * @param value
	 * @param defaultValue
	 * @return
	 */
	public static int parseInt(Object value, Integer defaultValue) {
		try {
			if (value != null && value.toString() != null && value.toString().length() > 0) {
				return Integer.parseInt(value.toString());
			}
		} catch (NumberFormatException e) {
		}
		return defaultValue;
	}

	/**
	 * 整数批量转换，根据字符串的分隔符
	 * 
	 * @param value
	 * @param regex
	 * @return
	 */
	public static int[] parseInts(String value, String regex) {
		if (value != null && value.length() > 0) {
			String[] values = value.split(regex);
			List<Integer> resultList = new ArrayList<Integer>();
			for (int i = 0; i < values.length; i++) {
				if (values[i] != null && !"".equals(values[i]) && values[i].length() > 0) {
					resultList.add(parseInt(values[i].trim()));
				}
			}
			int[] results = new int[resultList.size()];
			for (int i = 0; i < resultList.size(); i++) {
				results[i] = resultList.get(i);
			}
			return results;
		} else {
			return null;
		}
	}


	/**
	 * 整数批量转换，根据字符串的分隔符
	 *
	 * @param value
	 * @param regex
	 * @return
	 */
	public static Long[] parseLongs(String value, String regex) {
		if (value != null && value.length() > 0) {
			String[] values = value.split(regex);
			List<Long> resultList = new ArrayList<Long>();
			for (int i = 0; i < values.length; i++) {
				if (values[i] != null && !"".equals(values[i]) && values[i].length() > 0) {
					resultList.add(parseLong(values[i].trim()));
				}
			}
			Long[] results = new Long[resultList.size()];
			for (int i = 0; i < resultList.size(); i++) {
				results[i] = resultList.get(i);
			}
			return results;
		} else {
			return null;
		}
	}

	/**
	 * long类型转换
	 * 
	 * @param value
	 * @return
	 */
	public static long parseLong(Object value) {
		try {
			if (value != null && value.toString() != null && value.toString().length() > 0) {
				return Long.parseLong(value.toString());
			}
		} catch (NumberFormatException e) {
		}
		return 0L;
	}

	/**
	 * 得到min到max（包括min和max）之间的随机数
	 * 
	 * @param min
	 * @param max
	 * @return int
	 */
	public static int randomInt(int min, int max) {
		if (max <= min) {
			return max;
		}
		int result = (int) (Math.random() * (max - min + 1)) + min;
		return result;
	}

	/**
	 * 倒转数字
	 */
	public static Integer reverse(Integer number) {
		if (number == null) {
			return null;
		}
		String value = number.toString();
		int length = value.length();
		String reverse = "";
		for (int i = 0; i < length; i++)
			reverse = value.charAt(i) + reverse;
		return Integer.parseInt(reverse);
	}
}
