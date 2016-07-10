package org.limingnihao.util;

import java.io.File;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.limingnihao.model.FileBean;

public class DownUtil {


	public static FileBean downFile(String imageUrl, String saveFolderPath) {
		try {
			URL url = new URL(imageUrl);
			HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
			httpConnection.setRequestProperty("User-Agent", "NetFox");
			int responseCode = httpConnection.getResponseCode();
			if (responseCode != 200) {
				System.out.println("downFile - error - imageUrl=" + imageUrl + ", responseCode=" + responseCode);
				return null;
			}
			String length = httpConnection.getHeaderField("Content-Length");
			if (length == null || "".equals(length)) {
				System.out.println("downFile - error - imageUrl=" + imageUrl + ", responseCode=" + responseCode + ", length=" + length);
				return null;
			}
			long fileSize = Long.parseLong(length);
			System.out.println("downFile - imageUrl=" + imageUrl + ", saveFolderPath=" + saveFolderPath + ", responseCode=" + responseCode + ", fileSize=" + fileSize);
			if (responseCode == 200) {
				FileBean fileBean = new FileBean();
				String fileName = imageUrl.substring(imageUrl.lastIndexOf("/") + 1).toLowerCase();
				String fileType = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
				String saveFileName = new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "_" + UUID.randomUUID().toString() + "." + fileType;
				String filePath = saveFolderPath + File.separator + saveFileName;
				String fileMd5 = Md5Util.getMD5AndSave(httpConnection.getInputStream(), filePath);
				fileBean.setFileName(fileName);
				fileBean.setSaveName(saveFileName);
				fileBean.setFileSize(fileSize);
				fileBean.setFileType(fileType);
				fileBean.setFolderPath(saveFolderPath);
				fileBean.setFileMd5(fileMd5);
				return fileBean;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
