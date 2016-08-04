package org.limingnihao.util;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * base64编解码工具类
 * 
 * @author lishiming
 * 
 */
public class Base64Util {


	/**
	 * 编码
	 * 
	 * @param s
	 * @return
	 */
	@Deprecated
	public static String encode(String s) {
		try {
			if (s == null) {
				return null;
			} else {
				BASE64Encoder encoder = new BASE64Encoder();
				return encoder.encode(s.getBytes());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 解码
	 * 
	 * @param s
	 * @return
	 */
	@Deprecated
	public static String decoder(String s) {
		try {
			if (s == null) {
				return null;
			} else {
				BASE64Decoder decoder = new BASE64Decoder();
				byte[] b = decoder.decodeBuffer(s);
				return new String(b);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 编码 - 新的
	 * @param val
	 * @return
	 */
	public static String encodeNew(String val){
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
	public static String decoderNew(String val){
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
