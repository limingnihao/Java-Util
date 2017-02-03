package org.limingnihao.poi;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.limingnihao.model.ExcelBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * Created by lishiming on 2017/2/3.
 * 导出，将数据保存成excel文件
 */
public class ExcelExportUtil {

    private static final Logger logger = LoggerFactory.getLogger(ExcelExportUtil.class);

    public static boolean saveTemplate2003(String templateFilePath, String saveFilePath, int sheetIndex, List<ExcelBean> datas){
        try {
            //excel模板路径
            File file = new File(templateFilePath);
            if (!file.exists()) {
                logger.error("saveTemplate2003 - 文件不存在");
                return false;
            }
            POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(templateFilePath));
            // 得到Excel工作簿对象
            HSSFWorkbook workbook = new HSSFWorkbook(fs);

            //读取了模板内所有sheet内容
            HSSFSheet sheet = workbook.getSheetAt(sheetIndex);
            for(ExcelBean bean : datas){
                int row = bean.getRow();
                int cell = bean.getCell();
                String value = bean.getValue();
                HSSFRow hssfRow = sheet.getRow(row);
                logger.debug("saveTemplate2003 - row=" + row + ", cell=" + cell + ", value=" + value);
                if(hssfRow == null){
                    logger.error("saveTemplate2003 - xssfRow is null - row=" + row + ", cell=" + cell + ", value=" + value);
                    return false;
                }
                HSSFCell hssfCell = hssfRow.getCell(cell);
                if(hssfCell == null){
                    logger.error("saveTemplate2003 - xssfCell is null - row=" + row + ", cell=" + cell + ", value=" + value);
                    return false;
                }
                hssfCell.setCellValue(value);
            }
            //修改模板内容导出新模板
            FileOutputStream out = new FileOutputStream(saveFilePath);
            workbook.write(out);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean saveTemplate2007(String templateFilePath, String saveFilePath, int sheetIndex, List<ExcelBean> datas){
        try {
            //excel模板路径
            File file = new File(templateFilePath);
            if (!file.exists()) {
                logger.error("saveTemplate2007 - 文件不存在");
                return false;
            }
            //读取excel模板
            XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(file));
            //读取了模板内所有sheet内容
            XSSFSheet sheet = workbook.getSheetAt(sheetIndex);
            for(ExcelBean bean : datas){
                int row = bean.getRow();
                int cell = bean.getCell();
                String value = bean.getValue();
                XSSFRow xssfRow = sheet.getRow(row);
                logger.debug("saveTemplate2007 - row=" + row + ", cell=" + cell + ", value=" + value);
                if(xssfRow == null){
                    logger.error("saveTemplate2007 - xssfRow is null - row=" + row + ", cell=" + cell + ", value=" + value);
                    return false;
                }
                XSSFCell xssfCell = xssfRow.getCell(cell);
                if(xssfCell == null){
                    logger.error("saveTemplate2007 - xssfCell is null - row=" + row + ", cell=" + cell + ", value=" + value);
                    return false;
                }
                xssfCell.setCellValue(value);
            }
            //修改模板内容导出新模板
            FileOutputStream out = new FileOutputStream(saveFilePath);
            workbook.write(out);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean exportExcel2003(final String filePath, final HashMap<String, ArrayList<ArrayList<String>>> data){
        try {
            HSSFWorkbook workbook = new HSSFWorkbook();
            for (final String label : data.keySet()) {
                HSSFSheet sheet = workbook.createSheet(label.replaceAll("\\[|\\]|\\\\|\\:|\\?|\\/", "_"));
                ArrayList<ArrayList<String>> value = data.get(label);
                for (int i = 0; i < value.size(); i++) {
                    List<String> items = value.get(i);
                    HSSFRow row = sheet.createRow(i);
                    for (int j = 0; j < items.size(); j++) {
                        HSSFCell cell = row.createCell(j);
                        if (items.get(j) != null && !items.get(j).trim().equals("")) {
                            cell.setCellValue(items.get(j));
                        }
                    }
                }
            }
            FileOutputStream out = new FileOutputStream(filePath);
            workbook.write(out);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean exportExcel2007(final String filePath, final HashMap<String, ArrayList<ArrayList<String>>> data){
        try {
            XSSFWorkbook workbook = new XSSFWorkbook();
            for (final String label : data.keySet()) {
                XSSFSheet sheet = workbook.createSheet(label.replaceAll("\\[|\\]|\\\\|\\:|\\?|\\/", "_"));
                ArrayList<ArrayList<String>> value = data.get(label);
                for (int i = 0; i < value.size(); i++) {
                    List<String> items = value.get(i);
                    XSSFRow row = sheet.createRow(i);
                    for (int j = 0; j < items.size(); j++) {
                        XSSFCell cell = row.createCell(j);
                        if (items.get(j) != null && !items.get(j).trim().equals("")) {
                            cell.setCellValue(items.get(j));
                        }
                    }
                }
            }
            FileOutputStream out = new FileOutputStream(filePath);
            workbook.write(out);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
