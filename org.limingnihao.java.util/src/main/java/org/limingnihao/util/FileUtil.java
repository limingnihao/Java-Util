package org.limingnihao.util;

import java.io.*;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileUtil {

	private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);

	public static final String AUDIO_M4A = "m4a";
	public static final String AUDIO_MID = "mid";
	public static final String AUDIO_MP3 = "mp3";
	public static final String AUDIO_OGG = "ogg";
	public static final String AUDIO_WAV = "wav";
	public static final String AUDIO_WMA = "wma";
	public static final String AUDIO_XMF = "xmf";
	
	/** 视频文件格式 */
	public static final String VIDEO_3GP = "3gp";
	public static final String VIDEO_AVI = "avi";
	public static final String VIDEO_FLV = "flv";
	public static final String VIDEO_MKV = "mkv";
	public static final String VIDEO_MP4 = "mp4";
	public static final String VIDEO_RM = "rm";
	public static final String VIDEO_RMVB = "rmvb";
	public static final String VIDEO_WMV = "wmv";

	public static final String CHM = "chm";
	public static final String HTML_HTM = "htm";
	public static final String HTML_HTML = "html";
	public static final String IMAGE_BMP = "bmp";
	public static final String IMAGE_GIF = "gif";

	public static final String IMAGE_JPEG = "jpeg";
	public static final String IMAGE_JPG = "jpg";
	public static final String IMAGE_PNG = "png";

	public static final String OFFICE_EXCEL = "xls";
	public static final String OFFICE_EXCEL_X = "xlsx";

	public static final String OFFICE_PPT = "ppt";
	public static final String OFFICE_PPT_X = "pptx";

	public static final String OFFICE_WORD = "doc";
	public static final String OFFICE_WORD_X = "docx";

	public static final String PDF = "pdf";
	public static final String SWF = "swf";
	public static final String TXT = "txt";


	public static void main(String args[]) {
		System.out.println(getFileType("asdkflasdf.Pdf.ad.df.dowc"));
	}


	/**
	 * 文件是否存在
	 * @param path
	 */
	public static boolean isExists(String path){
		File file = new File(path);
		if(file.exists()){
			return true;
		}
		return false;
	}

	/**
	 * 输出文件到前台
	 * 
	 * @param data
	 * @param fileName
	 * @param response
	 * @throws IOException
	 */
	public static void byteToFile(byte[] data, String fileName, final HttpServletResponse response) throws IOException {
		fileName = URLEncoder.encode(fileName, "UTF-8");
		response.reset();
		response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
		response.addHeader("Content-Length", "" + data.length);
		response.setContentType("application/octet-stream;charset=UTF-8");
		OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
		outputStream.write(data);
		outputStream.flush();
		outputStream.close();
	}

	public static String getFileType(String filePath) {
		if (filePath == null || "".equals(filePath)) {
			return "";
		}
		int index = filePath.lastIndexOf(".");
		if (index > 0) {
			return filePath.substring(index + 1).toLowerCase();
		} else {
			return "";
		}
	}

	public static boolean isAudio(String type) {
		type = type.toLowerCase().replace(".", "");
		if (type.equals(AUDIO_MP3) || type.equals(AUDIO_WMA) || type.equals(AUDIO_WAV) || type.equals(AUDIO_M4A) || type.equals(AUDIO_MID) || type.equals(AUDIO_XMF) || type.equals(AUDIO_OGG)) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isCHM(String type) {
		type = type.toLowerCase().replace(".", "");
		if (type.equals(CHM)) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isExcel(String type) {
		type = type.toLowerCase().replace(".", "");
		if (type.equals(OFFICE_EXCEL) || type.equals(OFFICE_EXCEL_X)) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isHtml(String type) {
		type = type.toLowerCase().replace(".", "");
		if (type.equals(HTML_HTML) || type.equals(HTML_HTM)) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isImage(String type) {
		type = type.toLowerCase().replace(".", "");
		if (type.equals(IMAGE_JPG) || type.equals(IMAGE_JPEG) || type.equals(IMAGE_GIF) || type.equals(IMAGE_PNG) || type.equals(IMAGE_BMP)) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isPDF(String type) {
		type = type.toLowerCase().replace(".", "");
		if (type.equals(PDF)) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isPowerPoint(String type) {
		type = type.toLowerCase().replace(".", "");
		if (type.equals(OFFICE_PPT) || type.equals(OFFICE_PPT_X)) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isSWF(String type) {
		type = type.toLowerCase().replace(".", "");
		if (type.equals(SWF)) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isTXT(String type) {
		type = type.toLowerCase().replace(".", "");
		if (type.equals(TXT)) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isVideo(String type) {
		type = type.toLowerCase().replace(".", "");
		if (type.equals(VIDEO_3GP) || type.equals(VIDEO_MP4) || type.equals(VIDEO_RMVB) || type.equals(VIDEO_RM) || type.equals(VIDEO_MKV) || type.equals(VIDEO_AVI) || type.equals(VIDEO_WMV)
				|| type.equals(VIDEO_FLV)) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isWord(String type) {
		type = type.toLowerCase().replace(".", "");
		if (type.equals(OFFICE_WORD) || type.equals(OFFICE_WORD_X)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 保存从InputStream到服务器指定的路径下
	 * 
	 * @param is
	 * @param filePath
	 * @param fileName
	 * @throws IOException
	 */
	public static void saveFileToPath(InputStream is, String filePath, String fileName) throws IOException {
		File tempFile = new File(filePath + fileName);
		if (tempFile.exists()) {
			boolean delResult = tempFile.delete();
			logger.info("删除已存在的文件: " + filePath + fileName + ", " + delResult);
		}
		if (!fileName.equals("")) {
			FileOutputStream fos = new FileOutputStream(filePath + fileName);
			byte[] buffer = new byte[8192];
			int count = 0;
			while ((count = is.read(buffer)) > 0) {
				fos.write(buffer, 0, count);
			}
			logger.info("保存文件: " + filePath + fileName);
			fos.close();
			is.close();
		}
	}

	/**
	 * 保存从InputStream到服务器指定的路径下
	 *
	 * @param content
	 * @param filePath
	 * @param fileName
	 * @throws IOException
	 */
	public static void saveFileToPath(String content, String filePath, String fileName) throws IOException {
		File tempFile = new File(filePath + fileName);
		if (tempFile.exists()) {
			boolean delResult = tempFile.delete();
			logger.info("删除已存在的文件: " + filePath + fileName + ", " + delResult);
		}
		if (!fileName.equals("")) {
			FileWriter fw=new FileWriter(tempFile);
			fw.write(content);
			logger.info("保存文件: " + filePath + fileName);
			fw.close();
		}
	}

	/**
	 * 将文件转换成byte数组
	 * 
	 * @param filename
	 * @return
	 * @throws IOException
	 */
	public static byte[] toByteArray(String filename) throws IOException {
		File file = new File(filename);
		if (!file.exists()) {
			throw new FileNotFoundException(filename);
		}
		FileInputStream in = new FileInputStream(file);
		FileChannel channel = in.getChannel();
		ByteBuffer byteBuffer = ByteBuffer.allocate((int) channel.size());
		while ((channel.read(byteBuffer)) > 0) {
		}
		channel.close();
		in.close();
		return byteBuffer.array();
	}

	/**
	 * 压缩多个文件到一个zip文件中
	 * 
	 * @param srcFileList
	 * @param zipfile
	 * @throws IOException
	 */
	public static void zipFiles(List<File> srcFileList, File zipfile) throws IOException {
		byte[] buf = new byte[1024];
		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipfile));
		for (File file : srcFileList) {
			FileInputStream in = new FileInputStream(file);
			out.putNextEntry(new ZipEntry(file.getName()));
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			out.closeEntry();
			in.close();
		}
		out.close();
	}
}
