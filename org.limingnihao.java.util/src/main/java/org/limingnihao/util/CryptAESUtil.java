package org.limingnihao.util;

import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class CryptAESUtil {

    private static final String AESTYPE = "AES/ECB/PKCS5Padding";

    private static final String ALGORITHM = "AES";

    private static final String CHARSET_NAME = "UTF-8";

    /**
     * 加密
     * @param keyString
     * @param plainText
     * @return
     */
    public static String encrypt(String keyString, String plainText){
        byte[] encrypt = null;
        try {
            Key key = new SecretKeySpec(keyString.getBytes(CHARSET_NAME), ALGORITHM);
            Cipher cipher = Cipher.getInstance(AESTYPE);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            encrypt = cipher.doFinal(plainText.getBytes(CHARSET_NAME));
            return new String(Base64.encodeBase64(encrypt), CHARSET_NAME);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解密
     * @param keyString
     * @param encryptData
     * @return
     */
    public static String decrypt(String keyString, String encryptData) {
        byte[] decrypt = null;
        try {
            Key key = new SecretKeySpec(keyString.getBytes(CHARSET_NAME), ALGORITHM);
            Cipher cipher = Cipher.getInstance(AESTYPE);
            cipher.init(Cipher.DECRYPT_MODE, key);
            decrypt = cipher.doFinal(Base64.decodeBase64(encryptData.getBytes(CHARSET_NAME)));
            return new String(decrypt, CHARSET_NAME).trim();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
