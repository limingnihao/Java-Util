package org.limingnihao.util;

import java.io.UnsupportedEncodingException;

public class ByteUtil {

	/**
	 * 转换short为byte
	 */
	public static byte[] toBytesHigh(short value) {
		byte[] data = new byte[2];
		data[0] = (byte) (value >> 0 & 0xff);
		data[1] = (byte) (value >> 8 & 0xff);
		return data;
	}

	/**
	 * 转换int为byte数组
	 */
	public static byte[] toBytesHigh(int value) {
		byte[] data = new byte[4];
		data[0] = (byte) (value >> 0 & 0xff);
		data[1] = (byte) (value >> 8 & 0xff);
		data[2] = (byte) (value >> 16 & 0xff);
		data[3] = (byte) (value >> 24 & 0xff);
		return data;
	}

	/**
	 * 转换long型为byte数组
	 */
	public static byte[] toBytesHigh(long value) {
		byte[] data = new byte[8];
		data[0] = (byte) (value >> 0 & 0xff);
		data[1] = (byte) (value >> 8 & 0xff);
		data[2] = (byte) (value >> 16 & 0xff);
		data[3] = (byte) (value >> 24 & 0xff);
		data[4] = (byte) (value >> 32 & 0xff);
		data[5] = (byte) (value >> 40 & 0xff);
		data[6] = (byte) (value >> 48 & 0xff);
		data[7] = (byte) (value >> 56 & 0xff);
		return data;
	}

	/**
	 * 通过byte数组取到short
	 */
	public static short getShortHigh(byte[] data) {
		return (short) (((data[0] & 0xff | data[1] << 8)));
	}

	/**
	 * 通过byte数组取到int
	 */
	public static int getIntHigh(byte[] data) {
		return (int)((
                ((data[3] & 0xff) << 24) | 
                ((data[2] & 0xff) << 16) | 
                ((data[1] & 0xff) << 8) | 
                ((data[0] & 0xff) << 0)));
	}

	/**
	 * 通过byte数组取到long
	 */
	public static long getLongHigh(byte[] bb, int index) {
		return ((((long) bb[7] & 0xff) << 56) | (((long) bb[6] & 0xff) << 48) | (((long) bb[5] & 0xff) << 40) | (((long) bb[4] & 0xff) << 32) | (((long) bb[3] & 0xff) << 24)
				| (((long) bb[2] & 0xff) << 16) | (((long) bb[1] & 0xff) << 8) | (((long) bb[0] & 0xff) << 0));
	}

    public static String byteToString(byte[] bb) {
        String s = null;
        if (bb != null) {
            try {
                s = new String(bb, "UTF-8");
                return s;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
