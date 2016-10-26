package org.limingnihao.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.limingnihao.model.FileBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ftp常用方法
 */
public class FTPUtil {

    private static final Logger logger = LoggerFactory.getLogger(FTPUtil.class);

    private FTPClient ftp;

    public static void main(String args[]){
        FTPUtil util = new FTPUtil();
        util.login("101.201.105.103", 21, "dhcc", "Dhcc2015-2016");
    }


    public FTPUtil() {
    }

    /**
     * 登陆服务器
     *
     * @param url      - 服务器地址
     * @param port     - 端口号,默认21
     * @param username - 用户名
     * @param password - 密码
     * @return - 登陆结果
     */
    public boolean login(String url, int port, String username, String password) {
        //连接FTP服务器, 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
        try {
            this.ftp = new FTPClient();
            this.ftp.connect(url, port);
            this.ftp.setControlEncoding("UTF-8");

            boolean login = this.ftp.login(username, password);
            int replyCode = this.ftp.getReplyCode();
            this.ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
            this.ftp.enterLocalPassiveMode();

            logger.info("login - url=" + url + ":" + port + ", username=" + username + ", password=" + password + ", login=" + login + ", replyCode=" + replyCode + ", isPositiveCompletion=" + FTPReply.isPositiveCompletion(replyCode) +
                    ", names=" + Arrays.toString(this.ftp.listNames()));
            if (!FTPReply.isPositiveCompletion(replyCode)) {
                this.ftp.disconnect();
                return false;
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 注销
     */
    public void logout() {
        try {
            boolean logout = this.ftp.logout();
            this.ftp.disconnect();
            this.ftp = null;
            logger.info("logout - " + logout);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 上传文件
     *
     * @param ftpPath  - FTP上保存的路径
     * @param filePath - 实际文件路径
     * @return
     */
    public boolean uploadFile(String ftpPath, String filePath) {
        logger.info("uploadFile - 0 - start - ftpPath=" + ftpPath + ", filePath=" + filePath + ", isConnected=" + this.ftp.isConnected() + ", isAvailable=" + this.ftp.isAvailable());

        boolean success = false;
        InputStream is = null;
        try {
            boolean changeDir = this.ftp.changeWorkingDirectory(ftpPath);
            logger.info("uploadFile - 1 - changeDir - ftpPath=" + ftpPath + ", filePath=" + filePath + ", changeDir=" + changeDir);
            File file = new File(filePath);
            //logger.info("uploadFile - filePath=" + filePath + ", exists=" + file.exists() + ", canRead=" + file.canRead());
            is = new FileInputStream(file);
            boolean result = this.ftp.storeFile(file.getName(), is);
            logger.info("uploadFile - 2 - storeFile - ftpPath=" + ftpPath + ", filePath=" + filePath + ", result=" + result);
            if (changeDir && result) {
                success = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return success;
    }

    /**
     * 下载文件
     *
     * @param ftpPath   远程服务器上的路径
     * @param fileName  文件名称
     * @param localPath 本地保存路径
     * @return
     */
    public boolean downloadFile(String ftpPath, String fileName, String localPath) {
        logger.info("downloadFile - 0 - start - ftpPath=" + ftpPath + ", fileName=" + fileName + ", localPath=" + localPath + ", isConnected=" + this.ftp.isConnected() + ", isAvailable=" + this.ftp.isAvailable());
        boolean success = false;
        String localFile = localPath + File.separator + fileName;
        OutputStream os = null;
        try {
            File local = new File(localPath);
            if (!local.exists()) {
                local.mkdirs();
            }
            boolean changeDir = this.ftp.changeWorkingDirectory(ftpPath);
            logger.info("downloadFile - 1 - changeDir - ftpPath=" + ftpPath + ", fileName=" + fileName + ", localPath=" + localPath + ", changeDir=" + changeDir);
            os = new FileOutputStream(localFile);
            boolean downFile = this.ftp.retrieveFile(fileName, os);
            logger.info("downloadFile - 2 - retrieveFile - ftpPath=" + ftpPath + ", fileName=" + fileName + ", localPath=" + localPath + ", downFile=" + downFile);
            os.close();
            success = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.flush();
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        logger.info("downloadFile - 3 - over - success=" + success + ", ftpPath=" + ftpPath + ", fileName=" + fileName + ", localPath=" + localPath);
        return success;
    }

    /**
     * 获取文件列表
     *
     * @return
     */
    public List<FileBean> getFileList() {
        logger.info("getFileList - isConnected=" + this.ftp.isConnected() + ", isAvailable=" + this.ftp.isAvailable());
        if (!this.ftp.isConnected()) {
            return null;
        }
        List<FileBean> list = new ArrayList<FileBean>();
        try {
            FTPFile[] files = this.ftp.listFiles();
            for (FTPFile f : files) {
                FileBean bean = new FileBean();
                bean.setFileSize(f.getSize());
                bean.setFileName(f.getName());
                bean.setSaveName(f.getName());
                bean.setFolderPath("");
                list.add(bean);
                logger.info("getFileList - " + bean);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 获取文件列表
     *
     * @param ftpPath
     * @return
     */
    public List<FileBean> getFileList(String ftpPath) {
//        logger.info("getFileList - ftpPath=" + ftpPath + ", isConnected=" + this.ftp.isConnected() + ", isAvailable=" + this.ftp.isAvailable());
        if (!this.ftp.isConnected()) {
            return null;
        }
        List<FileBean> list = new ArrayList<FileBean>();
        try {
            FTPFile[] files = this.ftp.listFiles(ftpPath);
            for (FTPFile f : files) {
                FileBean bean = new FileBean();
                bean.setFileSize(f.getSize());
                bean.setFileName(f.getName());
                bean.setSaveName(f.getName());
                bean.setFolderPath(ftpPath);
                bean.setIsDirectory(f.isDirectory());
                list.add(bean);
                logger.info("getFileList - ftpPath=" + ftpPath + ", " + bean);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 获取文件信息
     *
     * @param ftpPath
     * @param fileName
     * @return
     */
    public FileBean getFile(String ftpPath, String fileName) {
        if (!this.ftp.isConnected()) {
            return null;
        }
        try {
            FTPFile[] files = this.ftp.listFiles(ftpPath);
            for (FTPFile f : files) {
                if (f.getName().equals(fileName)) {
                    FileBean bean = new FileBean();
                    bean.setFileSize(f.getSize());
                    bean.setFileName(f.getName());
                    bean.setSaveName(f.getName());
                    bean.setFolderPath(ftpPath);
                    logger.info("getFile - ftpPath=" + ftpPath + ", fileName=" + fileName + ", " + bean);
                    return bean;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("getFile - ftpPath=" + ftpPath + ", fileName=" + fileName + ", " + null);
        return null;
    }

}
