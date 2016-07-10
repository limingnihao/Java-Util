package org.limingnihao.util;


import javax.sql.rowset.serial.SerialBlob;
import java.io.UnsupportedEncodingException;
import java.sql.Blob;
import java.sql.SQLException;

public class BlobUtil {

    /**
     * string 转 blob 静态方法
     */
    public static Blob stringToBlob(String s){
        try {
            Blob b = new SerialBlob(s.getBytes("GBK"));
            return b;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * blob 转 string 静态方法
     */
    public static String blobToString(Blob b) {
        try {
            String s = new String(b.getBytes(1, (int)b.length()), "GBK");
            return s;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        Blob b = BlobUtil.stringToBlob("lipenghui");
        String s = BlobUtil.blobToString(b);
        System.out.println(s);
    }
}
