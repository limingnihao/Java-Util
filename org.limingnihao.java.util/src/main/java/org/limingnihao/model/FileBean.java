package org.limingnihao.model;

import java.io.Serializable;

public class FileBean implements Serializable {

	private String fileName;
	private String saveName;
	private String fileType;
	private Long fileSize;
	private String folderPath;
	private String fileMd5;
	private boolean isDirectory;

	@Override
	public String toString() {
		return "FileBean{" +
				"fileName='" + fileName + '\'' +
				", saveName='" + saveName + '\'' +
				", fileType='" + fileType + '\'' +
				", fileSize=" + fileSize +
				", folderPath='" + folderPath + '\'' +
				", fileMd5='" + fileMd5 + '\'' +
				", isDirectory=" + isDirectory +
				'}';
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getSaveName() {
		return saveName;
	}

	public void setSaveName(String saveName) {
		this.saveName = saveName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public Long getFileSize() {
		return fileSize;
	}

	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}

	public String getFolderPath() {
		return folderPath;
	}

	public void setFolderPath(String folderPath) {
		this.folderPath = folderPath;
	}

	public String getFileMd5() {
		return fileMd5;
	}

	public void setFileMd5(String fileMd5) {
		this.fileMd5 = fileMd5;
	}

	public boolean getIsDirectory() {
		return isDirectory;
	}

	public void setIsDirectory(boolean directory) {
		isDirectory = directory;
	}
}
