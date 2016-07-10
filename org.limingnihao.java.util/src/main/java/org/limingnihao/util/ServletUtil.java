package org.limingnihao.util;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.*;

public class ServletUtil {

	private final static String IE11="Trident";
	private final static String IE10="MSIE 10.0";
	private final static String IE9="MSIE 9.0";
	private final static String IE8="MSIE 8.0";
	private final static String IE7="MSIE 7.0";
	private final static String IE6="MSIE 6.0";
	private final static String Edge="Edge";

	private final static String MAXTHON="Maxthon";

	private final static String QQBrowser="QQBrowser";
	private final static String QQ="QQ";

	private final static String GREEN="GreenBrowser";
	private final static String SE360="360SE";
	private final static String FIREFOX="Firefox";
	private final static String OPERA="Opera";
	private final static String CHROME="Chrome";
	private final static String MicroMessenger="MicroMessenger";
	private final static String SAFARI="Safari";

	private final static String OTHER="Other";

	private final static String OS_WINDOWS = "Windows";
	private final static String OS_MAC = "Macintosh";
	private final static String OS_IPHONE = "iPhone";
	private final static String OS_IPAD = "iPad";
	private final static String OS_ANDROID = "Android";


	public static boolean regex(String regex,String str){
		Pattern p =Pattern.compile(regex,Pattern.MULTILINE);
		Matcher m=p.matcher(str);
		return m.find();
	}

	public static String checkBrowse(String userAgent){
		if(regex(SE360, userAgent))return SE360;
		if(regex(GREEN,userAgent))return GREEN;
		if(regex(MAXTHON, userAgent))return MAXTHON;

		if(regex(QQBrowser,userAgent))return QQBrowser;
		if(regex(QQ,userAgent))return QQ;
		if(regex(Edge, userAgent))return Edge;

		if(regex(MicroMessenger, userAgent))return MicroMessenger;

		if(regex(OPERA, userAgent))return OPERA;
		if(regex(CHROME, userAgent))return CHROME;
		if(regex(FIREFOX, userAgent))return FIREFOX;
		if(regex(SAFARI, userAgent))return SAFARI;

		if(regex(IE11,userAgent))return IE11;
		if(regex(IE10,userAgent))return IE10;
		if(regex(IE9,userAgent))return IE9;
		if(regex(IE8,userAgent))return IE8;
		if(regex(IE7,userAgent))return IE7;
		if(regex(IE6,userAgent))return IE6;
		return OTHER;
	}

	public static String checkOS(String userAgent){
		if(regex(OS_WINDOWS, userAgent))return OS_WINDOWS;
		if(regex(OS_MAC, userAgent))return OS_MAC;
		if(regex(OS_IPHONE, userAgent))return OS_IPHONE;
		if(regex(OS_IPAD, userAgent))return OS_IPAD;
		if(regex(OS_ANDROID, userAgent))return OS_ANDROID;
		return OTHER;
	}

	@Deprecated
	public static String getIpAddress(HttpServletRequest request) {
		String ipAddress = request.getHeader("x-forwarded-for");
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getRemoteAddr();
		}
		return ipAddress;
	}

	public static String getClientIp(HttpServletRequest request) {
		String ipAddress = request.getHeader("x-forwarded-for");
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getRemoteAddr();
		}
		return ipAddress;
	}

	/**
	 * 服务器ip
	 * 
	 * @param request
	 * @return
	 */
	public static String getServiceIp(HttpServletRequest request) {
		return request.getServerName();
	}

	/**
	 * 服务器方位地址
	 * 
	 * @param request
	 * @return
	 */
	public static String getServiceUri(HttpServletRequest request) {
		return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/" + request.getContextPath();
	}

}
