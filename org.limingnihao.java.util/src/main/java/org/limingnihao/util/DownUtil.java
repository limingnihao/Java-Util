package org.limingnihao.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.limingnihao.model.FileBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DownUtil {

    private static final Logger logger = LoggerFactory.getLogger(DownUtil.class);

	public static FileBean downFile(String imageUrl, String savePath) throws IOException {
		String fileName = imageUrl.substring(imageUrl.lastIndexOf("/") + 1).toLowerCase();
		String fileType = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        String saveName = new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "_" + UUID.randomUUID().toString() + "." + fileType;
        return downFile(imageUrl, savePath, saveName, fileName, fileType);
	}

    /**
     * 下载文件
     * @param imageUrl 文件地址
     * @param savePath 保存路径
     * @param fileName 文件名称
     * @param fileType 文件类型
     * @return
     * @throws IOException
     */
	public static FileBean downFile(String imageUrl, String savePath, String saveName, String fileName, String fileType) throws FileNotFoundException, IOException {
		URL url = new URL(imageUrl);
		HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
		httpConnection.setRequestProperty("User-Agent", "NetFox");
		int responseCode = httpConnection.getResponseCode();
		if (responseCode != 200) {
			logger.error("downFile - error - imageUrl=" + imageUrl + ", responseCode=" + responseCode);
			return null;
		}
		String length = httpConnection.getHeaderField("Content-Length");
		if (length == null || "".equals(length)) {
            logger.error("downFile - error - imageUrl=" + imageUrl + ", responseCode=" + responseCode + ", length=" + length);
			return null;
		}
		long fileSize = Long.parseLong(length);
		if (responseCode == 200) {
			FileBean fileBean = new FileBean();
			String filePath = savePath + File.separator + saveName;
			String fileMd5 = Md5Util.getMD5AndSave(httpConnection.getInputStream(), filePath);
            logger.info("downFile - imageUrl=" + imageUrl + ", savePath=" + savePath + ", filePath=" + filePath + ", fileMd5=" + fileMd5);
            fileBean.setFileName(fileName);
			fileBean.setSaveName(saveName);
			fileBean.setFileSize(fileSize);
			fileBean.setFileType(fileType);
			fileBean.setFolderPath(savePath);
			fileBean.setFileMd5(fileMd5);
			return fileBean;
		}
		return null;
	}
}
