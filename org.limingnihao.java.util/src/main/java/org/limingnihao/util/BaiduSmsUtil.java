package org.limingnihao.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.limingnihao.model.BaiduSmsBean;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * 百度短信平台(短信通)工具
 */
public class BaiduSmsUtil {


    /**
     * @param httpUrl :请求接口
     * @param mobile  :手机号码
     * @param content :内容
     * @return 返回结果
     */
    public static BaiduSmsBean sendMessage(String httpUrl, String apikey, String smsSign, String mobile, String content) {
        BufferedReader reader = null;
        String result = null;
        StringBuffer sbf = new StringBuffer();
        try {
            httpUrl = httpUrl + "?mobile=" + mobile + "&content=" + URLEncoder.encode(smsSign + content, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        try {
            URL url = new URL(httpUrl);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setRequestMethod("GET");
            // 填入apikey到HTTP header
            connection.setRequestProperty("apikey", apikey);
            connection.connect();
            InputStream is = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sbf.append(strRead);
                sbf.append("\r\n");
            }
            reader.close();
            result = sbf.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (StringUtil.isNotBlank(result) && result.contains("<?xml")) {
            return BaiduSmsUtil.xmlToBean(result);
        }
        return null;
    }

    public static BaiduSmsBean xmlToBean(String xml) {
        try {
            BaiduSmsBean bean = new BaiduSmsBean();
            String xmlAll = "<?xml version=\"1.0\" encoding=\"utf-8\" ?>" + xml.replace("<?xml version=\"1.0\" encoding=\"utf-8\" ?>", "").replace("\r\n", "").replace(" ", "");
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document doc = builder.parse(new ByteArrayInputStream(xmlAll.getBytes()));
            doc.getDocumentElement().normalize();
            Element root = doc.getDocumentElement();
            NodeList returnstatusNode = root.getElementsByTagName("returnstatus");
            if (returnstatusNode.getLength() > 0) {
                String returnstatus = returnstatusNode.item(0).getFirstChild().getNodeValue();
                bean.setReturnstatus(returnstatus);
            }
            NodeList messageNode = root.getElementsByTagName("message");
            if (messageNode.getLength() > 0) {
                String message = messageNode.item(0).getFirstChild().getNodeValue();
                bean.setMessage(message);
            }
            return bean;

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
