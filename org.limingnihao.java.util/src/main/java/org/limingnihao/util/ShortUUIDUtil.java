package org.limingnihao.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

/**
 * Created by lipenghui on 2017/2/17.
 */
public class ShortUUIDUtil {

    private ShortUUIDUtil() throws IOException {

        throw new IOException("工具类不能实例化");

    }

    private static String[] chars = new String[]{
            "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z",
            "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
            "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"
    };

    public static String getShortUuid() {
        StringBuffer stringBuffer = new StringBuffer();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        for (int i = 0; i < 8; i++) {
            String str = uuid.substring(i * 4, i * 4 + 4);
            int strInteger = Integer.parseInt(str, 16);
            stringBuffer.append(chars[strInteger % 0x3E]);
        }

        return stringBuffer.toString();
    }

    public static void main(String[] args) {

        HashMap<String, Integer> map = new HashMap<>();
        for (int i = 1; i < 100; i++) {
            String uuid = ShortUUIDUtil.getShortUuid();
            System.out.println(uuid);
            if (map.get(uuid) != null) {
                System.out.println("重复的码");
            }
            map.put(uuid, 1);
        }
    }

}
