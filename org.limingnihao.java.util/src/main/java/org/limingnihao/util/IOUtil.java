package org.limingnihao.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.codec.binary.Base64;


/**
 * Created by Ran on 2016/6/13.
 */
public class IOUtil {

    /**
     * 输入流转字节流
     * */
    public static byte[] InputStreamToByte(InputStream is) throws IOException {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        byte[] buffer=new byte[1024];
        int ch;
        while ((ch = is.read(buffer)) != -1) {
            byteStream.write(buffer, 0, ch);
        }
        byte data[] = byteStream.toByteArray();
        byteStream.close();
        return data;
    }

    /**
     * 输入流转base64
     * */
    public static String InputStreamToBase64(InputStream is) throws IOException {
    	return Base64.encodeBase64String(IOUtil.InputStreamToByte(is));
    }



}
