package org.limingnihao.aliyun;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.ParseException;
import java.util.Date;

import org.limingnihao.util.FileUtil;
import org.limingnihao.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aliyun.oss.HttpMethod;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.utils.DateUtil;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;
import com.aliyun.oss.model.ListObjectsRequest;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import com.aliyun.oss.model.PutObjectResult;

/**
 * 阿里云文件操作
 */
public class OSSUtil {

    private static final Logger logger = LoggerFactory.getLogger(OSSUtil.class);

    private String shanghaiEndpoint = "oss-cn-shanghai.aliyuncs.com";
    private String beijingEndpoint = "oss-cn-beijing.aliyuncs.com";
    private String endpoint = "oss-cn-beijing.aliyuncs.com";
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName = "";
    private OSSClient client = null;

    public OSSUtil(String accessKeyId, String accessKeySecret){
        this.accessKeyId = accessKeyId;
        this.accessKeySecret = accessKeySecret;
    }

    /**
     * 链接
     */
    public void connect(){
        this.client = new OSSClient("https://" + endpoint, accessKeyId, accessKeySecret);
        client.getClientConfiguration().setMaxConnections(100);
        client.getClientConfiguration().setConnectionTimeout(5000);
        client.getClientConfiguration().setMaxErrorRetry(3);
        client.getClientConfiguration().setSocketTimeout(2000);
    }

    /**
     * 链接
     */
    public void connectShanghai(){
        endpoint = shanghaiEndpoint;
        connect();
    }

    /**
     * 链接
     */
    public void connectBeijing(){
        endpoint = beijingEndpoint;
        connect();
    }

    /**
     * 断开
     */
    public void shutdown(){
        client.shutdown();
    }

    public void list(){
        ObjectListing objectListing = client.listObjects(new ListObjectsRequest(bucketName));
        for (OSSObjectSummary objectSummary : objectListing.getObjectSummaries()) {
            System.out.println(" - " + objectSummary.getKey() + "  " +  "(size = " + objectSummary.getSize() + ")");
        }

    }

    private String createFile(String key, String filePath)  throws Exception{
        return createFile(key, filePath, null);
    }

    private String createFile(String key, String filePath, String dns) throws Exception {
        logger.info("createFile - key=" + key + ", filePath=" + filePath + ", dns=" + dns);
        if(FileUtil.isExists(filePath)){
            PutObjectResult result = client.putObject(bucketName, key, new File(filePath));
            if(StringUtil.isNotBlank(dns)){
                return dns + "/" + key;
            }
            else{
                return "http://" + bucketName + "." + endpoint + "/" + key;
            }
        }
        throw new FileNotFoundException("文件不存在!");
    }

    private String createFile(String key, InputStream is){
        return createFile(key, is, null);
    }

    private String createFile(String key, InputStream is, String dns){
        PutObjectResult result = client.putObject(bucketName, key, is);
        if(StringUtil.isNotBlank(dns)){
            return dns + "/" + key;
        }
        else{
            return "http://" + bucketName + "." + endpoint + "/" + key;
        }
    }

    private boolean createFolder(String folder){
        try{
            PutObjectResult result = client.putObject(bucketName, folder + "/", new ByteArrayInputStream(new byte[0]));
            logger.info("createFolder - folder=" + folder + ", " + result.toString());
            return true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * ----------------------------------- new --------------------------------------------
     */

    public String createPublicFile(String bucket, String key, String filePath)  throws Exception{
        bucketName = bucket;
        endpoint = beijingEndpoint;
        return createPublicFile(bucketName, key, filePath, null);
    }

    public String createPrivateFile(String bucket, String key, String filePath)  throws Exception{
        bucketName = bucket;
        endpoint = shanghaiEndpoint;
        return createPrivateFile(bucketName, key, filePath, null);
    }

    public String createPublicFile(String bucket, String key, String filePath, String dns) throws Exception {
        bucketName = bucket;
        endpoint = beijingEndpoint;
        return createFile(key, filePath, dns);
    }

    public String createPrivateFile(String bucket, String key, String filePath, String dns) throws Exception {
        bucketName = bucket;
        endpoint = shanghaiEndpoint;
        return createFile(key, filePath, dns);
    }

    public String createPublicFile(String bucket, String key, InputStream is){
        bucketName = bucket;
        endpoint = beijingEndpoint;
        return createFile(key, is, null);
    }

    public String createPrivateFile(String bucket, String key, InputStream is){
        bucketName = bucket;
        endpoint = shanghaiEndpoint;
        return createFile(key, is, null);
    }

    public String createPublicFile(String bucket, String key, InputStream is, String dns){
        bucketName = bucket;
        endpoint = beijingEndpoint;
        return createFile(key, is, dns);
    }

    public String createPrivateFile(String bucket, String key, InputStream is, String dns){
        bucketName = bucket;
        endpoint = shanghaiEndpoint;
        return createFile(key, is, dns);
    }

    public boolean createPublicFolder(String bucket, String folder){
        bucketName = bucket;
        endpoint = beijingEndpoint;
        return createFolder(folder);
    }

    public boolean createPrivateFolder(String bucket, String folder){
        bucketName = bucket;
        endpoint = shanghaiEndpoint;
        return createFolder(folder);
    }

    /**
     * 获取文件相关
     */
    public InputStream getPrivateObjectInputStream(String bucket, String key) throws ParseException, IOException {
        bucketName = bucket;
        endpoint = shanghaiEndpoint;

        //服务器端生成url签名字串
        Date expiration = DateUtil.parseRfc822Date("Wed, 18 Mar 2099 14:20:00 GMT");
        GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(bucketName, key, HttpMethod.GET);
        //设置过期时间
        request.setExpiration(expiration);
        // 生成URL签名(HTTP GET请求)
        URL signedUrl = client.generatePresignedUrl(request);
        System.out.println("signed url for getObject: " + signedUrl);

        return signedUrl.openStream();
    }

    public URL getPrivateObjectURL(String bucket, String key) throws ParseException {
        bucketName = bucket;
        endpoint = shanghaiEndpoint;

        //服务器端生成url签名字串
        Date expiration = DateUtil.parseRfc822Date("Wed, 18 Mar 2099 14:20:00 GMT");
        GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(bucketName, key, HttpMethod.GET);
        //设置过期时间
        request.setExpiration(expiration);
        // 生成URL签名(HTTP GET请求)
        URL signedUrl = client.generatePresignedUrl(request);
        return signedUrl;
    }

}
