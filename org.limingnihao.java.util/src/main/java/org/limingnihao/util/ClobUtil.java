package org.limingnihao.util;

import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;

import javax.sql.rowset.serial.SerialException;

public class ClobUtil {

	/**
	 * 将String转成Clob ,静态方法
	 * 
	 * @param content 字段
	 * @return clob对象，如果出现错误，返回 null
	 * @throws SQLException
	 * @throws SerialException
	 */
	public static java.sql.Clob stringToClob(String content) throws SerialException, SQLException {
		if (null == content || "".equals(content)) {
			return null;
		} else {
			java.sql.Clob c = new javax.sql.rowset.serial.SerialClob(content.toCharArray());
			return c;
		}
	}

	/**
	 * 将Clob转成String ,静态方法
	 * 
	 * @param clob 字段
	 * @return 内容字串，如果出现错误，返回 null
	 * @throws SQLException
	 * @throws IOException
	 */
	public static String clobToString(java.sql.Clob clob) throws SQLException, IOException {
		if (clob == null) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		Reader clobStream = clob.getCharacterStream();
		char[] b = new char[60000];
		int i = 0;
		while ((i = clobStream.read(b)) != -1) {
			sb.append(b, 0, i);
		}
		if (clobStream != null) {
			clobStream.close();
		}
		return sb.toString();
	}

}
