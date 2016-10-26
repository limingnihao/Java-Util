package org.limingnihao.util;

import java.util.Base64;

/**
 * base64编解码工具类
 * 
 * @author lishiming
 * 
 */
public class Base64Util {

	/**
	 * 编码 - 新的
	 * @param val
	 * @return
	 */
	public static String encode(String val){
		String asB64 = null;
		if (val == null || "".equals(val)) {
			return null;
		}
		try {
			asB64 = java.util.Base64.getEncoder().encodeToString(val.getBytes("utf-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return asB64;
	}

	/**
	 * 解码 - 新的
	 * @param val
	 * @return
     */
	public static String decoder(String val){
		if (val == null || "".equals(val)) {
			return null;
		}
		byte[] asBytes = Base64.getDecoder().decode(val);
		String result = null;
		try {
			result = new String(asBytes, "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}


}
