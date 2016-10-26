package org.limingnihao.util;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class CryptAESUtil {

    private static final String AESTYPE = "AES/ECB/PKCS5Padding";

    @Deprecated
    public static String AES_Encrypt(String keyStr, String plainText) {
        return CryptAESUtil.encrypt(keyStr, plainText);
    }

    @Deprecated
    public static String AES_Decrypt(String keyStr, String plainText) {
        return CryptAESUtil.decrypt(keyStr, plainText);
    }

    /**
     * 加密
     * @param keyStr
     * @param plainText
     * @return
     */
    public static String encrypt(String keyStr, String plainText) {
        byte[] encrypt = null;
        try {
            Key key = generateKey(keyStr);
            Cipher cipher = Cipher.getInstance(AESTYPE);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            encrypt = cipher.doFinal(plainText.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new String(Base64.encodeBase64(encrypt));
    }

    /**
     * 解密
     * @param keyStr
     * @param encryptData
     * @return
     */
    public static String decrypt(String keyStr, String encryptData) {
        byte[] decrypt = null;
        try {
            Key key = generateKey(keyStr);
            Cipher cipher = Cipher.getInstance(AESTYPE);
            cipher.init(Cipher.DECRYPT_MODE, key);
            decrypt = cipher.doFinal(Base64.decodeBase64(encryptData.getBytes()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new String(decrypt).trim();
    }

    private static Key generateKey(String key) throws Exception {
        try {
            SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
            return keySpec;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

    }

}
