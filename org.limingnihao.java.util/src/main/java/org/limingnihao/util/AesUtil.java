package org.limingnihao.util;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.limingnihao.exception.AesErrorException;

public class AesUtil {

	private final static String HEX = "0123456789ABCDEF";

	private final static String CHARACTER_SET = "UTF-8";

	public static void main(String args[]) {
		try {
			String masterPassword = "1234567890abcdef";
			String encryptingCode = AesUtil.encrypt(masterPassword, "123456");
			System.out.println(encryptingCode);
			String originalText = AesUtil.decrypt(masterPassword, encryptingCode);
			System.out.println(originalText);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 加密
	 * 
	 * @param password
	 * @param code
	 * @return
	 */
	public static String encrypt(String password, String code) throws AesErrorException{
		// byte[] rawKey = getRawKey(password.getBytes(CHARACTER_SET));暂时不用
		try {
			byte[] result = encrypt(password.getBytes(CHARACTER_SET), code.getBytes(CHARACTER_SET));
			return toHex(result);
		} catch (Exception e) {
			 e.printStackTrace();
		}
		throw new AesErrorException();
	}

	/**
	 * 解密
	 * 
	 * @param password
	 * @param encrypted
	 */
	public static String decrypt(String password, String encrypted) throws AesErrorException{
		// byte[] rawKey = getRawKey(password.getBytes());暂时不用
		try {
			byte[] enc = toByte(encrypted);
			byte[] result = decrypt(password.getBytes(), enc);
			return new String(result);
		} catch (Exception e) {
			 e.printStackTrace();
		}
		throw new AesErrorException();
	}

	public static byte[] getRawKey(byte[] seed) throws NoSuchAlgorithmException {
		KeyGenerator kgen = KeyGenerator.getInstance("AES");
		SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
		sr.setSeed(seed);
		kgen.init(128, sr); // 192 and 256 bits may not be available
		SecretKey skey = kgen.generateKey();
		byte[] raw = skey.getEncoded();
		return raw;
	}

	private static byte[] encrypt(byte[] key, byte[] clear) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
		byte[] encrypted = cipher.doFinal(clear);
		return encrypted;
	}

	private static byte[] decrypt(byte[] raw, byte[] encrypted) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.DECRYPT_MODE, skeySpec);
		byte[] decrypted = cipher.doFinal(encrypted);
		return decrypted;
	}

	public static byte[] toByte(String hexString) {
		int len = hexString.length() / 2;
		byte[] result = new byte[len];
		for (int i = 0; i < len; i++)
			result[i] = Integer.valueOf(hexString.substring(2 * i, 2 * i + 2), 16).byteValue();
		return result;
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
