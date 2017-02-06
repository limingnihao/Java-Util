package org.limingnihao.poi;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.limingnihao.util.FileUtil;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by lishiming on 2017/2/3.
 */
public class ExcelUtil {

    /**
     * 获取有多少个sheet
     * @param filePath
     * @return
     */
    public static int getSheetCount(String filePath){
        String type = FileUtil.getFileType(filePath);
        if (type.equals("xls")) {
            return getSheetCount2003(filePath);
        } else if (type.equals("xlsx")) {
            return getSheetCount2007(filePath);
        } else {
            return 0;
        }
    }

    /**
     * 获取有多少个sheet - 2003
     * @param filePath
     * @return
     */
    public static int getSheetCount2003(String filePath){
        try {
            POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(filePath));
            // 得到Excel工作簿对象
            HSSFWorkbook workbook =  workbook = new HSSFWorkbook(fs);
            // 得到Excel工作表对象
            int max = workbook.getNumberOfSheets();
            return max;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取有多少个sheet - 2007
     * @param filePath
     * @return
     */
    public static int getSheetCount2007(String filePath){

        try {
            // 得到Excel工作簿对象
            XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(filePath));
            // 得到Excel工作表对象
            int max = workbook.getNumberOfSheets();
            return max;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     *
     * 导入，指定sheet
     * @param filePath
     * @param sheetIndex
     * @return
     */
    public static ArrayList<ArrayList<String>> importExcel(String filePath, int sheetIndex) {
        String type = FileUtil.getFileType(filePath);
        if (type.equals("xls")) {
            return ExcelImpot2003Util.importBySheetIndex(filePath, sheetIndex);
        } else if (type.equals("xlsx")) {
            return ExcelImpot2007Util.importBySheetIndex(filePath, sheetIndex);
        } else {
            return null;
        }
    }

    /**
     * 导入，指定sheet
     * @param is
     * @param fileType
     * @param sheetIndex
     * @return
     */
    public static ArrayList<ArrayList<String>> importExcel(FileInputStream is, String fileType, int sheetIndex) {
        if (fileType.equals("xls")) {
            return ExcelImpot2003Util.importBySheetIndex(is, sheetIndex);
        } else if (fileType.equals("xlsx")) {
            return ExcelImpot2007Util.importBySheetIndex(is, sheetIndex);
        } else {
            return null;
        }
    }

    /**
     * 导出excel
     * @param filePath
     * @param datas
     * @return
     */
    public static boolean exportExcel(final String filePath, final HashMap<String, ArrayList<ArrayList<String>>> datas) {
        String type = FileUtil.getFileType(filePath);
        if (type.equals("xls")) {
            return ExcelExport2003Util.exportExcel(filePath, datas);
        } else if (type.equals("xlsx")) {
            return ExcelExport2007Util.exportExcel(filePath, datas);
        } else {
            return false;
        }
    }

}
