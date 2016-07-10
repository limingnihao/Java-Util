package org.limingnihao.util;

import java.util.ArrayList;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class PinyinUtil {

	/**
	 * 获取拼音集合
	 */
	public static String[][] getPinyinArray(String src) {
		if (src == null || src.trim().equalsIgnoreCase("")) {
			return null;
		}
		char[] srcChar = src.toCharArray();
		String[][] temp = new String[src.length()][];
		int index = 0;
		try {
			HanyuPinyinOutputFormat hanYuPinOutputFormat = new HanyuPinyinOutputFormat();
			hanYuPinOutputFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
			hanYuPinOutputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
			hanYuPinOutputFormat.setVCharType(HanyuPinyinVCharType.WITH_V);
			for (int i = 0; i < srcChar.length; i++) {
				char c = srcChar[i];
				// 是中文转换拼音或者a-z或者A-Z
				if (String.valueOf(c).matches("[\\u4E00-\\u9FA5]+")) {
					temp[index++] = PinyinHelper.toHanyuPinyinStringArray(c, hanYuPinOutputFormat);
				}
				// 0-9, a-z, A-Z
				else if (((int) c >= 48 && (int) c <= 57) || ((int) c >= 65 && (int) c <= 90) || ((int) c >= 97 && (int) c <= 122)) {
					temp[index++] = new String[] { String.valueOf(c) };
				}
				// 其他不处理
				else {
				}
			}
		} catch (BadHanyuPinyinOutputFormatCombination e) {
			e.printStackTrace();
		}
		String[][] result = new String[index][];
		for (int i = 0; i < index; i++) {
			result[i] = temp[i];
		}
		return result;
	}

	/**
	 * 获取拼音全量组合
	 * 
	 * @param value
	 * @return
	 */
	public static String[] getPinyinGroup(String src) {
		String value[][] = getPinyinArray(src);
		int count = 1;
		for (int i = 0; i < value.length; i++) {
			count *= value[i].length;
		}
		int index[] = new int[value.length];
		String array[] = new String[count];
		for (int i = 0; i < count; i++) {
			String rs = "";
			for (int j = 0; j < value.length; j++) {
				String[] temp = value[j];
				rs += temp[index[j]];
			}
			for (int index_j = 0; index_j < index.length; index_j++) {
				if (index[index_j] < value[index_j].length - 1) {
					index[index_j]++;
					break;
				} else {
					index[index_j] = 0;
				}
			}
			array[i] = rs;
		}
		return array;
	}

	/**
	 * 获取拼音头字母组合
	 * 
	 * @param value
	 * @return
	 */
	public static String[] getPinyinGroupByFirst(String src) {
		try {

			String value[][] = getPinyinArray(src);
			int count = 1;
			for (int i = 0; i < value.length; i++) {
				count *= value[i].length;
			}
			int index[] = new int[value.length];
			String array[] = new String[count];
			for (int i = 0; i < count; i++) {
				String rs = "";
				for (int j = 0; j < value.length; j++) {
					String[] temp = value[j];
					rs += temp[index[j]].substring(0, 1);
				}
				for (int index_j = 0; index_j < index.length; index_j++) {
					if (index[index_j] < value[index_j].length - 1) {
						index[index_j]++;
						break;
					} else {
						index[index_j] = 0;
					}
				}
				array[i] = rs;
			}
			ArrayList<String> resultList = new ArrayList<String>();
			for (int i = 0; i < array.length; i++) {
				boolean isHave = false;
				for (String r : resultList) {
					if (array[i].equals(r)) {
						isHave = true;
					}
				}
				if (!isHave) {
					resultList.add(array[i]);
				}
			}
			String[] result = new String[resultList.size()];
			for (int i = 0; i < resultList.size(); i++) {
				result[i] = resultList.get(i);
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String args[]) {
		String[] result = getPinyinGroupByFirst("弹我心爱的土琵琶");
		if (result != null) {
			for (String r : result) {
				System.out.println(r);
			}
		} else {
			System.out.println("null");
		}

	}
}
