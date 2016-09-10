package org.limingnihao.util;

import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sourceforge.jeval.Evaluator;
import org.apache.commons.lang.StringUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.context.Context;

/**
 * String常用方法
 * 
 * @author 黎明你好
 * 
 */
public class StringUtil {

	// 汉字的UTF-8编码起始 19968-40869
	public static int ChineseCharStartIndex = 19968;
	public static int ChineseCharEndIndex = 40869;

	/**
	 * 是否是空字符串
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isBlank(String str) {
		int strLen;
		if (str == null || (strLen = str.length()) == 0 || "null".equals(str.toLowerCase())) {
			return true;
		}
		for (int i = 0; i < strLen; i++) {
			if ((Character.isWhitespace(str.charAt(i)) == false)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 是否不是空字符串
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNotBlank(String str) {
		return !isBlank(str);
	}

	/**
	 * 解码
	 * 
	 * @param value
	 * @return
	 */
	public static String decode(String value) {
		return decode(value, "UTF-8");
	}

	public static String decode(String value, String charset) {
		if (StringUtils.isNotBlank(value)) {
			try {
				return URLDecoder.decode(value, charset);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return "";
	}

	/**
	 * 编码
	 * @param value
	 * @return
     */
	public static String encode(String value){
		return encode(value, "UTF-8");
	}

	public static String encode(String value, String charset){
		if (StringUtils.isNotBlank(value)) {
			try {
				return URLEncoder.encode(value, charset);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return "";
	}

	/**
	 * 得到指定长度的，随机英文字符
	 * 
	 * @param length
	 * @return
	 */
	public static String randomChar(int length) {
		String resultString = "";
		for (int i = 0; i < length; i++) {
			int random = (int) (Math.random() * 100);
			int flag = (random % 2) == 0 ? (int) 'a' : (int) 'A';
			System.out.println(flag);
			int charIndex = (int) Math.round(Math.random() * 26) + flag;
			if (charIndex > (flag + 25)) {
				charIndex = flag + 25;
			}
			resultString += (char) charIndex;
		}
		return resultString;
	}

	/**
	 * 得到指定长度的，随机汉字字符
	 * 
	 * @param length
	 * @return String
	 */
	public static String randomChineseChar(int length) {
		String resultString = "";
		for (int i = 0; i < length; i++) {
			int charIndex = (int) Math.round(Math.random() * (ChineseCharEndIndex - ChineseCharStartIndex)) + ChineseCharStartIndex;
			if (charIndex > ChineseCharEndIndex) {
				charIndex = ChineseCharEndIndex;
			}
			resultString += (char) charIndex;
		}
		return resultString;
	}

	/**
	 * 传入unicode，得到字符
	 * 
	 * @param utfString
	 * @return
	 */
	public static String unicodeConvert(String utfString) {
		StringBuilder sb = new StringBuilder();
		int i = -1;
		int pos = 0;

		while ((i = utfString.indexOf("\\u", pos)) != -1) {
			sb.append(utfString.substring(pos, i));
			if (i + 5 < utfString.length()) {
				pos = i + 6;
				sb.append((char) Integer.parseInt(utfString.substring(i + 2, i + 6), 16));
			}
		}

		return sb.toString();
	}

	/**
	 * 检测是否是手机号码
	 * @param str
	 * @return
	 */
	public static boolean isMobile(String str) {
		Pattern p = Pattern.compile("^[1][3-9][0-9]{9}$");
		Matcher m = p.matcher(str);
		return m.matches();
	}

    public static boolean isIdentityCard(String str) {
        Pattern p = Pattern.compile("^(\\d{15}|\\d{17}[x0-9])$");
        Matcher m = p.matcher(str);
        return m.matches();
    }


	/**
	 * 根据公式计算结果
	 * @param expression
	 * @param value
	 * @return
	 */
    public static double calculator(String expression, String[] value){
		try {
			Context context  = new VelocityContext();
			for(int i=0; i<value.length; i++){
				context.put("a" + i, value[i]);
			}
			StringWriter sw = new StringWriter();
			Velocity.evaluate(context, sw, "velocity", expression);
			Evaluator eval = new Evaluator();
			String result = eval.evaluate(sw.toString());
			double r= NumberUtil.getDoubleScale(NumberUtil.parseDouble(result), 2);
			return r;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	public static void main(String[] args) {
//		System.out.println(unicodeConvert("\\u627F\\u5FB7"));
//        System.out.println(decode("%E3%80%90%E7%9F%AD%E4%BF%A1%E9%80%9A%E3%80%91%E6%82%A8%E7%9A%84%E9%AA%8C%E8%AF%81%E7%A0%81%EF%BC%9A888888"));
//        System.out.println(isIdentityCard("152625198901272515"));
//        System.out.println(isMobile("18310862221"));

		String s1 = StringUtil.encode("我");
		String s2 = StringUtil.decode(s1);
		System.out.println(s1 + " - " + s2);
        try {
            System.out.println(URLEncoder.encode("interface/family/getProfile.do?familyId=2894123", "utf-8"));
        } catch (UnsupportedEncodingException e2) {
            e2.printStackTrace();
        }
    }
}
