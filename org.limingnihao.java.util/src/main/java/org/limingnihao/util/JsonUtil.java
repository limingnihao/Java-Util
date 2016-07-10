package org.limingnihao.util;

import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.limingnihao.model.DateBean;

/**
 * json解析器
 * 
 * @author limingnihao
 * 
 */
public class JsonUtil {

	private static String SIMPLE_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss:SSS";

	public static void main(String args[]) {
	}

//	public static String objectToJson(Object targetObj) {
//		StringBuffer jsonString = new StringBuffer();
//		jsonString.append("{");
//		try {
//			for (Field field : targetObj.getClass().getDeclaredFields()) {
//				String fileName = field.getName();
//				String methodName = getMethodGetName(fileName);
//				Method method = targetObj.getClass().getMethod(methodName);
//				Object value = method.invoke(targetObj);
//				String valueString = "";
//				if (value.getClass().getName().equals(Date.class.getName())) {
//					SimpleDateFormat format = new SimpleDateFormat(SIMPLE_DATE_FORMAT);
//					valueString = format.format(value);
//				} else {
//					valueString = value.toString();
//				}
//				jsonString.append("\"" + fileName + "\": \"" + valueString + "\", ");
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		if (jsonString.length() - jsonString.lastIndexOf(", ") == 2) {
//			jsonString = jsonString.delete(jsonString.lastIndexOf(", "), jsonString.length());
//		}
//		jsonString.append("}");
//		return jsonString.toString();
//	}

	@SuppressWarnings({ "rawtypes" })
	public static Object jsonToObject(Class clazz, String jsonString) {
		try {
			Object targetObj = clazz.newInstance();
			JSONObject jsonObj = new JSONObject(jsonString);
			for (Field field : targetObj.getClass().getDeclaredFields()) {
				String fileName = field.getName();
				if (!jsonObj.has(fileName)) {
					continue;
				}
				String value = jsonObj.get(fileName).toString();
				String typeName = field.getType().getName();
				field.setAccessible(true);
				if (byte.class.getName().equals(typeName) || Byte.class.getName().equals(typeName)) {
					field.set(targetObj, Byte.parseByte(value));
				} else if (short.class.getName().equals(typeName) || Short.class.getName().equals(typeName)) {
					field.set(targetObj, Short.parseShort(value));
				} else if (int.class.getName().equals(typeName) || Integer.class.getName().equals(typeName)) {
					field.set(targetObj, Integer.parseInt(value));
				} else if (long.class.getName().equals(typeName) || Long.class.getName().equals(typeName)) {
					field.set(targetObj, Long.parseLong(value));
				} else if (float.class.getName().equals(typeName) || Float.class.getName().equals(typeName)) {
					field.set(targetObj, Float.parseFloat(value));
				} else if (double.class.getName().equals(typeName) || Double.class.getName().equals(typeName)) {
					field.set(targetObj, Double.parseDouble(value));
				} else if (char.class.getName().equals(typeName)) {
					char[] chars = value.toCharArray();
					if (chars != null && chars.length > 0) {
						field.set(targetObj, chars[0]);
					}
				} else if (boolean.class.getName().equals(typeName) || Boolean.class.getName().equals(typeName)) {
					field.set(targetObj, Boolean.parseBoolean(value));
				} else if (String.class.getName().equals(typeName)) {
					field.set(targetObj, value.toString());
				} else if (Date.class.getName().equals(typeName)) {
					SimpleDateFormat format = new SimpleDateFormat(SIMPLE_DATE_FORMAT);
					field.set(targetObj, format.parse(value));
				} else if (List.class.getName().equals(typeName)) {
					field.set(targetObj, JsonUtil.jsonToList(getClass(field.getGenericType(), 0), value));
				} else {
					field.set(targetObj, value);
				}
			}
			return targetObj;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> List<T> jsonToList(Class clazz, String jsonString) {
		List<T> list = new ArrayList<T>();
		try {
			if (jsonString == null || "".equals(jsonString)) {
				return list;
			}
			JSONArray jsonArray = new JSONArray(jsonString);
			if (jsonArray == null || jsonArray.length() <= 0) {
				return list;
			}
			for (int i = 0; i < jsonArray.length(); i++) {
				String jsont = jsonArray.get(i).toString();
				list.add((T) JsonUtil.jsonToObject(clazz, jsont));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return list;
	}

	protected static String getMethodSetName(String fieldName) {
		String firstLetter = fieldName.substring(0, 1).toUpperCase();
		return "set" + firstLetter + fieldName.substring(1);
	}

	protected static String getMethodGetName(String fieldName) {
		String firstLetter = fieldName.substring(0, 1).toUpperCase();
		return "get" + firstLetter + fieldName.substring(1);
	}

	protected static String getFieldName(String methodName) {
		String fieldName = methodName.substring(3, methodName.length());
		String firstLetter = fieldName.substring(0, 1).toLowerCase();
		return firstLetter + fieldName.substring(1);
	}

	private static Class<?> getClass(Type type, int i) {
		if (type instanceof ParameterizedType) { // 处理泛型类型
			return getGenericClass((ParameterizedType) type, i);
		} else if (type instanceof TypeVariable) {
			return (Class<?>) getClass(((TypeVariable<?>) type).getBounds()[0], 0); // 处理泛型擦拭对象
		} else {// class本身也是type，强制转型
			return (Class<?>) type;
		}
	}

	private static Class<?> getGenericClass(ParameterizedType parameterizedType, int i) {
		Object genericClass = parameterizedType.getActualTypeArguments()[i];
		if (genericClass instanceof ParameterizedType) { // 处理多级泛型
			return (Class<?>) ((ParameterizedType) genericClass).getRawType();
		} else if (genericClass instanceof GenericArrayType) { // 处理数组泛型
			return (Class<?>) ((GenericArrayType) genericClass).getGenericComponentType();
		} else if (genericClass instanceof TypeVariable) { // 处理泛型擦拭对象
			return (Class<?>) getClass(((TypeVariable<?>) genericClass).getBounds()[0], 0);
		} else {
			return (Class<?>) genericClass;
		}
	}
}
