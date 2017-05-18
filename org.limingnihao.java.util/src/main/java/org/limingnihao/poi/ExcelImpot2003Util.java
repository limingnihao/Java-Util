package org.limingnihao.poi;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.CellType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Created by lishiming on 2017/2/3.
 */
public class ExcelImpot2003Util {

    private static final Logger logger = LoggerFactory.getLogger(ExcelImpot2003Util.class);

    public static ArrayList<ArrayList<String>> importBySheetIndex(String filePath, int sheetIndex){
        logger.info("import2003 - filePath=" + filePath + ", sheetIndex=" + sheetIndex);
        File file = new File(filePath);
        if (!file.exists()) {
            logger.info("import2003 - 文件不存在");
            return null;
        }
        try {
            FileInputStream is = new FileInputStream(filePath);
            return importBySheetIndex(is, sheetIndex);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<ArrayList<String>> importBySheetIndex(FileInputStream is, int sheetIndex) {
        //logger.info("import2003 - 1.文件存在 - filePath=" + filePath);
        ArrayList<ArrayList<String>> rowList = new ArrayList<ArrayList<String>>();
        try {
            POIFSFileSystem fs = new POIFSFileSystem(is);
            // 得到Excel工作簿对象
            HSSFWorkbook workbook = new HSSFWorkbook(fs);
            // 得到Excel工作表对象
            int max = workbook.getNumberOfSheets();
            sheetIndex = sheetIndex <= 0 ? max : sheetIndex;
            // 得到Excel工作表对象
            HSSFSheet sheet = workbook.getSheetAt(sheetIndex - 1);
            int rowNumLast = sheet.getLastRowNum();
            //logger.info("import2003 - filePath=" + filePath + ", sheet=" + k + ", rowNumLast=" + rowNumLast);
            // i循环行
            for (int i = 0; i <= rowNumLast; i++) {
                ArrayList<String> columnList = new ArrayList<String>();
                // 得到Excel工作表的行
                HSSFRow row = sheet.getRow(i);
                if (row == null) {
                    continue;
                }
                // j循环列
                for (int j = 0; j < row.getLastCellNum(); j++) {
                    // 得到Excel工作表指定行的单元格
                    HSSFCell cell = row.getCell(j);
                    //logger.info("~~~~~~[" + i + "," + j + "] - cell=" + cell );
                    Object value = null;
                    if (cell != null) {
                        if (cell.getCellTypeEnum() == CellType.NUMERIC) {
                            Double d = cell.getNumericCellValue();
                            value = d.doubleValue();
                        } else if (cell.getCellTypeEnum() == CellType.STRING) {
                            value = cell.getStringCellValue();
                        }
                        columnList.add(value != null ? value.toString().trim() : "");
                    } else {
                        columnList.add("");
                    }
                    //logger.info("~~~~~~[" + i + "," + j + "] - cell=" + cell + ", value=" + value);
                }
                //logger.info("i=" + i + "" + Arrays.toString(columnList.toArray()));
                rowList.add(columnList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rowList;
    }

}
