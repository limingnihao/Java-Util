package org.limingnihao.util;

import org.limingnihao.model.DateBean;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * xml解析器
 *
 * @author limingnihao
 */
public class XmlUtil {

    // private static final Logger logger = LoggerFactory.getLogger(HTTPUtil.class);

    private static String SIMPLE_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss:SSS";

    public static void main(String args[]) {
        DateBean bean = DateUtil.getCurrentDateBean();
        String str = XmlUtil.objectToXml(bean);
        System.out.println(str);
        DateBean b = XmlUtil.xmlToObject(DateBean.class, str);
        System.out.println(b.getMonth());
    }

    public static String objectToXml(Object targetObj) {
        StringBuffer xmlString = new StringBuffer();
        xmlString.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        xmlString.append("<" + targetObj.getClass().getSimpleName() + ">");
        try {
            for (Field field : targetObj.getClass().getDeclaredFields()) {
                String fileName = field.getName();
                String methodName = getMethodGetName(fileName);
                Method method = targetObj.getClass().getMethod(methodName);
                Object value = method.invoke(targetObj);
                String valueString = "";
                if (value != null && value.getClass().getName().equals(Date.class.getName())) {
                    SimpleDateFormat format = new SimpleDateFormat(SIMPLE_DATE_FORMAT);
                    valueString = format.format(value);
                } else if (value != null) {
                    valueString = value.toString();
                }
                xmlString.append("<" + fileName + ">" + valueString + "</" + fileName + ">");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        xmlString.append("</" + targetObj.getClass().getSimpleName() + ">");
        return xmlString.toString();
    }

    public static String objectToXmlNoHead(Object targetObj) {
        StringBuffer xmlString = new StringBuffer();
        xmlString.append("<xml>");
        try {
            for (Field field : targetObj.getClass().getDeclaredFields()) {
                String fileName = field.getName();
                String methodName = getMethodGetName(fileName);
                Method method = targetObj.getClass().getMethod(methodName);
                Object value = method.invoke(targetObj);
                String valueString = "";
                if (value != null && value.getClass().getName().equals(Date.class.getName())) {
                    SimpleDateFormat format = new SimpleDateFormat(SIMPLE_DATE_FORMAT);
                    valueString = format.format(value);
                } else if (value != null) {
                    valueString = value.toString();
                }
                xmlString.append("<" + fileName + ">" + valueString + "</" + fileName + ">");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        xmlString.append("</xml>");
        return xmlString.toString();
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public static <T> T xmlToObject(Class clazz, String xmlString) {
        try {
            Object targetObj = clazz.newInstance();
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document dom = builder.parse(new ByteArrayInputStream(xmlString.getBytes()));
            Element root = dom.getDocumentElement();// 根元素
            NodeList nodeList = root.getChildNodes();
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                String name = node.getNodeName();
                String value = node.getNodeValue();
                String content = node.getTextContent();
                //				System.out.println("name=" + name + ", value=" + value + ", content=" + content );
                Field field = clazz.getDeclaredField(name);
                String typeName = field.getType().getName();

                String val = content;
                field.setAccessible(true);
                if (byte.class.getName().equals(typeName) || Byte.class.getName().equals(typeName)) {
                    field.set(targetObj, Byte.parseByte(val));
                } else if (short.class.getName().equals(typeName) || Short.class.getName().equals(typeName)) {
                    field.set(targetObj, Short.parseShort(val));
                } else if (int.class.getName().equals(typeName) || Integer.class.getName().equals(typeName)) {
                    field.set(targetObj, Integer.parseInt(val));
                } else if (long.class.getName().equals(typeName) || Long.class.getName().equals(typeName)) {
                    field.set(targetObj, Long.parseLong(val));
                } else if (float.class.getName().equals(typeName) || Float.class.getName().equals(typeName)) {
                    field.set(targetObj, Float.parseFloat(val));
                } else if (double.class.getName().equals(typeName) || Double.class.getName().equals(typeName)) {
                    field.set(targetObj, Double.parseDouble(val));
                } else if (char.class.getName().equals(typeName)) {
                    char[] chars = val.toCharArray();
                    if (chars != null && chars.length > 0) {
                        field.set(targetObj, chars[0]);
                    }
                } else if (boolean.class.getName().equals(typeName) || Boolean.class.getName().equals(typeName)) {
                    field.set(targetObj, Boolean.parseBoolean(val));
                } else if (Date.class.getName().equals(typeName)) {
                    SimpleDateFormat format = new SimpleDateFormat(SIMPLE_DATE_FORMAT);
                    field.set(targetObj, format.parse(val));
                } else {
                    field.set(targetObj, val);
                }
            }
            return (T) targetObj;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public static List xmlToList(Class clazz, String xmlString) {
        List list = new ArrayList<Object>();
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document dom = builder.parse(new ByteArrayInputStream(xmlString.getBytes()));
            Element root = dom.getDocumentElement();// 根元素
            NodeList nodeList = root.getChildNodes();
            for (int i = 0; i < nodeList.getLength(); i++) {
                Object targetObj = clazz.newInstance();
                Node node_i = nodeList.item(i);
                for (int j = 0; j < node_i.getChildNodes().getLength(); j++) {
                    Node node_j = node_i.getChildNodes().item(j);
                    String name = node_j.getNodeName();
                    String value = node_j.getNodeValue();
                    // System.out.println("name_j=" + name + ", value_j=" + value);
                    Field field = clazz.getDeclaredField(name);
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
                    } else if (Date.class.getName().equals(typeName)) {
                        SimpleDateFormat format = new SimpleDateFormat(SIMPLE_DATE_FORMAT);
                        field.set(targetObj, format.parse(value));
                    } else {
                        field.set(targetObj, value);
                    }
                }
                list.add(targetObj);
            }
        } catch (Exception e) {
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
}
