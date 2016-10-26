package org.limingnihao.java.util.test;

import java.net.URL;
import java.text.ParseException;

import org.junit.Test;
import org.limingnihao.aliyun.OSSUtil;

/**
 * Created by lishiming on 16/9/2.
 */
public class OSSTest {

    @Test
    public void getUrl(){
        OSSUtil util = new OSSUtil("nwQIwvJB4XYj5IyM", "vjoSpzG8kbjsC1jKCtTpr6RGOvKaRU");
        util.connectShanghai();
        try {
            URL url = util.getPrivateObjectURL("dhcfcs", "pension/2016-08-13/1471076560526.jpg@0o_0l_400w_90q.src");
            System.out.println(url.toString().replace("https://dhcfcs.oss-cn-shanghai.aliyuncs.com", "http://img-dhcfcs.kjrd.com.cn/"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
