package org.limingnihao.poi;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.limingnihao.model.ExcelBean;
import org.limingnihao.util.FileUtil;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
            return ExcelImpotUtil.importBySheetIndex2003(filePath, sheetIndex);
        } else if (type.equals("xlsx")) {
            return ExcelImpotUtil.importBySheetIndex2007(filePath, sheetIndex);
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
            return ExcelImpotUtil.importBySheetIndex2003(is, sheetIndex);
        } else if (fileType.equals("xlsx")) {
            return ExcelImpotUtil.importBySheetIndex2007(is, sheetIndex);
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
            return ExcelExportUtil.exportExcel2003(filePath, datas);
        } else if (type.equals("xlsx")) {
            return ExcelExportUtil.exportExcel2007(filePath, datas);
        } else {
            return false;
        }
    }

    /**
     * 导出 - 带模板
     * @param templateFilePath
     * @param saveFilePath
     * @param sheetIndex
     * @param datas
     * @return
     */
    public static boolean saveTemplate(String templateFilePath, String saveFilePath, int sheetIndex, List<ExcelBean> datas) {
        String type = FileUtil.getFileType(templateFilePath);
        if (type.equals("xls")) {
            return ExcelExportUtil.saveTemplate2003(templateFilePath, saveFilePath, sheetIndex, datas);
        } else if (type.equals("xlsx")) {
            return ExcelExportUtil.saveTemplate2007(templateFilePath, saveFilePath, sheetIndex, datas);
        } else {
            return false;
        }
    }


}
