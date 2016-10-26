package org.limingnihao.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 生成MD5工具类
 *
 * @author lishiming
 */
public class Md5Util {

    private final static String HEX = "0123456789ABCDEF";
    protected static MessageDigest DIGEST = null;
    protected static char HEX_DIGITS[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    static {
        try {
            Md5Util.DIGEST = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException ne) {
            ne.printStackTrace();
        }
    }


    public static String getMD5(String value, String key) {
        try {
            String sha1 = SHA1Util.SHA1(value);
            Md5Util.DIGEST.update((sha1 + key).getBytes());
            byte[] md5 = Md5Util.DIGEST.digest();
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < md5.length; i++) {
                String shaHex = Integer.toHexString(md5[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getMD5(FileInputStream in) {
        try {
            FileChannel ch = in.getChannel();
            MappedByteBuffer byteBuffer = ch.map(FileChannel.MapMode.READ_ONLY, 0, in.available());
            Md5Util.DIGEST.update(byteBuffer);
            byte[] input = Md5Util.DIGEST.digest();
            StringBuffer stringbuffer = new StringBuffer(2 * input.length);
            for (int i = 0; i < input.length; i++) {
                char c0 = HEX_DIGITS[(input[i] & 0xf0) >> 4];
                char c1 = HEX_DIGITS[input[i] & 0xf];
                stringbuffer.append(c0);
                stringbuffer.append(c1);
            }
            return stringbuffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getMD5AndSave(InputStream in, String path) {
        try {

            FileOutputStream out = new FileOutputStream(path);
            byte[] buffer = new byte[8192];
            int length = 0;
            while ((length = in.read(buffer)) != -1) {
                Md5Util.DIGEST.update(buffer, 0, length);
                out.write(buffer, 0, length);
            }
            byte[] input = Md5Util.DIGEST.digest();
            StringBuffer stringbuffer = new StringBuffer(2 * input.length);
            for (int i = 0; i < input.length; i++) {
                char c0 = Md5Util.HEX_DIGITS[(input[i] & 0xf0) >> 4];
                char c1 = Md5Util.HEX_DIGITS[input[i] & 0xf];
                stringbuffer.append(c0);
                stringbuffer.append(c1);
            }
            out.flush();
            out.close();
            out = null;
            in.close();
            in = null;
            return stringbuffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String toHex(byte[] buf) {
        if (buf == null)
            return "";
        StringBuffer result = new StringBuffer(2 * buf.length);
        for (int i = 0; i < buf.length; i++) {
            appendHex(result, buf[i]);
        }
        return result.toString();
    }

    private static void appendHex(StringBuffer sb, byte b) {
        sb.append(HEX.charAt((b >> 4) & 0x0f)).append(HEX.charAt(b & 0x0f));
    }
}
