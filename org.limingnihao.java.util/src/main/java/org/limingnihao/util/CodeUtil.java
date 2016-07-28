package org.limingnihao.util;

import java.util.Date;

public class CodeUtil {

	public static String getCodeByDateAndRandom() {
		return getCodeByDateAndRandom(4);
	}
	public static String getCodeByDateAndRandom(int length) {
		int random = NumberUtil.randomInt((int)Math.pow(10, length-1), (int)Math.pow(10, length));
		String code = DateUtil.format(new Date()) + random;
		code = code.replace(" ", "");
		code = code.replace("-", "");
		code = code.replace(":", "");
		return code;
	}
	
}
