package org.limingnihao.util;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * csv的操作
 */
public class CSVUtil {

    private static String SIMPLE_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 解析csv文件
     * @param path
     * @return
     */
    public static ArrayList<ArrayList<String>> getList(String path){
        ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
        try {
            FileInputStream fis = new FileInputStream(path);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String line = "";
            while ((line = br.readLine()) != null) {
                line = line.replace(" ", "").replace("\"", "");
                ArrayList<String> list = new ArrayList<>();
                String[] vs = line.split(",");
                for(int i=0; i<vs.length; i++){
                    list.add(vs[i]);
                }
                data.add(list);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
    /**
     * 保存成文件
     * @param dataList - 数据数组
     * @param dirPath - 保存文件夹
     * @param fileName - 保存文件名
     * @return
     */
    public static long saveListToCsvFile(Object[] dataList, String dirPath, String fileName) {

        String dateString = CSVUtil.listToCSV(dataList);
        //路径
        File dir = new File(dirPath);
        boolean mkResult = true;
        if (!dir.exists()) {
            mkResult = dir.mkdirs();
        } else if (!dir.isDirectory()) {
            dir.delete();
            mkResult = dir.mkdirs();
        }

        //文件
        File file = new File(dirPath + File.separator + fileName);

        FileOutputStream out=null;
        OutputStreamWriter osw=null;
        BufferedWriter bw=null;
        try {
            file.createNewFile();
            out = new FileOutputStream(file);
            int pageSize = 100000;
            for(int i=0; i<dataList.length; i+=100000){

            }
            byte[] dates = dateString.getBytes("UTF-8");
            out.write(dates);
            out.flush();
            return file.length();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }finally {
            if (out != null) {
                try {
                    out.close();
                    out = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static String listToCSV(Object[] list){
        StringBuffer dateString = new StringBuffer();
        if(list != null && list.length > 0){
            dateString.append(objectToCSVByFieldName(list[0]) + "\n");
            for(Object obj : list){
                dateString.append(objectToCSV(obj) + "\n");
            }
        }
        return dateString.toString();
    }

    public static String objectToCSVByFieldName(Object targetObj){
        StringBuffer dateString = new StringBuffer();
        try {
            for (Field field : targetObj.getClass().getDeclaredFields()) {
                String fileName = field.getName();
                String valueString = "";
                dateString.append("\"" + fileName + "\"" + ",");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(dateString.length() > 0){
            return dateString.substring(0, dateString.length() -1);
        }else{
            return "";
        }
    }

    public static String objectToCSV(Object targetObj) {
        StringBuffer dateString = new StringBuffer();
        try {
            for (Field field : targetObj.getClass().getDeclaredFields()) {
                String fileName = field.getName();
                String methodName = getMethodGetName(fileName);
                Method method = targetObj.getClass().getMethod(methodName);
                Object value = method.invoke(targetObj);
                String valueString = "";
                // 识别日期
                if (value != null && value.getClass().getName().equals(Date.class.getName())) {
                    SimpleDateFormat format = new SimpleDateFormat(SIMPLE_DATE_FORMAT);
                    valueString = format.format(value);
                }
                //其他直接获取值
                else if(value != null) {
                    valueString = value.toString();
                }
                dateString.append("\"" + valueString + "\"" + ",");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(dateString.length() > 0){
            return dateString.substring(0, dateString.length() -1);
        }else{
            return "";
        }
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
