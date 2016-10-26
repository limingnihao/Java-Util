package org.limingnihao.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class ExcelUtil {

    private static final Logger logger = LoggerFactory.getLogger(ExcelUtil.class);

    /**
     * 将Map导出Excel文件 一个map对应一个工作表，map的键值为工作表名称，map的vector为工作表内容
     *
     * @param outputStream
     * @param data
     * @throws IOException
     * @throws RowsExceededException
     * @throws WriteException
     */
    public static void exportExcel(final OutputStream outputStream, final HashMap<String, ArrayList<ArrayList<String>>> data) throws IOException, RowsExceededException, WriteException {
        WritableWorkbook workbook = Workbook.createWorkbook(outputStream);
        int s = 0;
        for (final String label : data.keySet()) {
            WritableSheet sheet = workbook.createSheet(label.replaceAll("\\[|\\]|\\\\|\\:|\\?|\\/", "_"), s++);
            ArrayList<ArrayList<String>> value = data.get(label);
            for (int i = 0; i < value.size(); i++) {
                List<String> items = value.get(i);
                for (int j = 0; j < items.size(); j++) {
                    if (items.get(j) != null && !items.get(j).trim().equals("")) {
                        sheet.addCell(new Label(j, i, items.get(j)));
                    }
                }
            }
        }
        workbook.write();
        workbook.close();
        outputStream.close();
    }

    public static ArrayList<ArrayList<String>> importExcel(String filePath, int sheetIndex) {
        String type = FileUtil.getFileType(filePath);
        if (type.equals("xls")) {
            return ExcelUtil.import2003(filePath, sheetIndex);
        } else if (type.equals("xlsx")) {
            return ExcelUtil.import2007(filePath, sheetIndex);
        } else {
            return null;
        }
    }

    public static ArrayList<ArrayList<String>> importExcelSingleSheet(String filePath, int sheetIndex) {
        String type = FileUtil.getFileType(filePath);
        if (type.equals("xls")) {
            return ExcelUtil.import2003SingleSheet(filePath, sheetIndex);
        } else if (type.equals("xlsx")) {
            return ExcelUtil.import2007SingleSheet(filePath, sheetIndex);
        } else {
            return null;
        }
    }

    public static ArrayList<ArrayList<String>> import2003(String filePath, int sheetIndex) {
        logger.info("import2003 - filePath=" + filePath + ", sheetIndex=" + sheetIndex);
        File file = new File(filePath);
        if (!file.exists()) {
            logger.info("import2003 - 文件不存在");
            return null;
        }
        //logger.info("import2003 - 1.文件存在 - filePath=" + filePath);
        ArrayList<ArrayList<String>> rowList = new ArrayList<ArrayList<String>>();
        try {
            POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(filePath));
            // 得到Excel工作簿对象
            HSSFWorkbook workbook = new HSSFWorkbook(fs);
            // 得到Excel工作表对象
            int max = workbook.getNumberOfSheets();
            sheetIndex = sheetIndex <= 0 ? max : sheetIndex;
            for (int k = 0; k < sheetIndex; k++) {
                // 得到Excel工作表对象
                HSSFSheet sheet = workbook.getSheetAt(k);
                int rowNumLast = sheet.getLastRowNum();
                if (rowNumLast <= 0) {
                    continue;
                }
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
                            if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                                Double d = cell.getNumericCellValue();
                                value = d.doubleValue();
                            } else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
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
            }
        } catch (Exception e) {
            logger.info("import2003 - filePath=" + filePath + ", " + e);
            e.printStackTrace();
        }
        return rowList;
    }

    public static ArrayList<ArrayList<String>> import2007(String filePath, int sheetIndex) {
        logger.info("import2007 - filePath=" + filePath + ", sheetIndex=" + sheetIndex);
        File file = new File(filePath);
        if (!file.exists()) {
            logger.info("import2007 - 文件不存在");
            return null;
        }
        //logger.info("import2007 - 1.文件存在 - filePath=" + filePath);
        ArrayList<ArrayList<String>> rowList = new ArrayList<ArrayList<String>>();
        try {
            //NPOIFSFileSystem fs = new NPOIFSFileSystem();
            // 得到Excel工作簿对象
            XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(filePath));
            // 得到Excel工作表对象
            int max = workbook.getNumberOfSheets();
            sheetIndex = sheetIndex <= 0 ? max : sheetIndex;
            for (int k = 0; k < sheetIndex; k++) {
                XSSFSheet sheet = workbook.getSheetAt(k);
                int rowNumLast = sheet.getLastRowNum();
                logger.info("import2007 - filePath=" + filePath + ", sheet=" + k + ", rowNumLast=" + rowNumLast);
                if (rowNumLast <= 0) {
                    continue;
                }
                // i循环行
                for (int i = 0; i <= rowNumLast; i++) {
                    ArrayList<String> columnList = new ArrayList<String>();
                    // 得到Excel工作表的行
                    XSSFRow row = sheet.getRow(i);
                    if (row == null) {
                        continue;
                    }
                    // j循环列
                    for (int j = 0; j < row.getLastCellNum(); j++) {
                        // 得到Excel工作表指定行的单元格
                        XSSFCell cell = row.getCell(j);
                        //logger.info("~~~~~~[" + i + "," + j + "] - cell=" + cell );
                        Object value = null;
                        if (cell != null) {
                            if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                    value = sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue())).toString();
                                } else {
                                    Double d = cell.getNumericCellValue();
                                    value = d.intValue();
                                }
                            } else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                                value = cell.getStringCellValue();
                            }
                            columnList.add(value != null ? value.toString().trim() : "");
                        } else {
                            columnList.add("");
                        }
//						logger.info("~~~~~~[" + i + "," + j + "] - " + cell.getCellType() + ", cell=" + cell + ", value=" + value);
                    }
                    //logger.info("i=" + i + "" + Arrays.toString(columnList.toArray()));
                    rowList.add(columnList);
                }
            }
        } catch (Exception e) {
            logger.info("import2007 - filePath=" + filePath + ", " + e);
            e.printStackTrace();
        }
        return rowList;
    }


    public static ArrayList<ArrayList<String>> import2003SingleSheet(String filePath, int sheetIndex) {
        logger.info("import2003 - filePath=" + filePath + ", sheetIndex=" + sheetIndex);
        File file = new File(filePath);
        if (!file.exists()) {
            logger.info("import2003 - 文件不存在");
            return null;
        }
        //logger.info("import2003 - 1.文件存在 - filePath=" + filePath);
        ArrayList<ArrayList<String>> rowList = new ArrayList<ArrayList<String>>();
        try {
            POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(filePath));
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
                        if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                            Double d = cell.getNumericCellValue();
                            value = d.doubleValue();
                        } else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
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
            logger.info("import2003 - filePath=" + filePath + ", " + e);
            e.printStackTrace();
        }
        return rowList;
    }

    public static ArrayList<ArrayList<String>> import2007SingleSheet(String filePath, int sheetIndex) {
        logger.info("import2007 - filePath=" + filePath + ", sheetIndex=" + sheetIndex);
        File file = new File(filePath);
        if (!file.exists()) {
            logger.info("import2007 - 文件不存在");
            return null;
        }
        //logger.info("import2007 - 1.文件存在 - filePath=" + filePath);
        ArrayList<ArrayList<String>> rowList = new ArrayList<ArrayList<String>>();
        try {
            //NPOIFSFileSystem fs = new NPOIFSFileSystem();
            // 得到Excel工作簿对象
            XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(filePath));
            // 得到Excel工作表对象
            int max = workbook.getNumberOfSheets();
            sheetIndex = sheetIndex <= 0 ? max : sheetIndex;
            XSSFSheet sheet = workbook.getSheetAt(sheetIndex - 1);
            int rowNumLast = sheet.getLastRowNum();
            logger.info("import2007 - filePath=" + filePath + ", sheet=" + sheetIndex + ", rowNumLast=" + rowNumLast);
            // i循环行
            for (int i = 0; i <= rowNumLast; i++) {
                ArrayList<String> columnList = new ArrayList<String>();
                // 得到Excel工作表的行
                XSSFRow row = sheet.getRow(i);
                if (row == null) {
                    continue;
                }
                // j循环列
                for (int j = 0; j < row.getLastCellNum(); j++) {
                    // 得到Excel工作表指定行的单元格
                    XSSFCell cell = row.getCell(j);
                    //logger.info("~~~~~~[" + i + "," + j + "] - cell=" + cell );
                    Object value = null;
                    if (cell != null) {
                        if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                            if (HSSFDateUtil.isCellDateFormatted(cell)) {
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                value = sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue())).toString();
                            } else {
                                Double d = cell.getNumericCellValue();
                                value = d.intValue();
                            }
                        } else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                            value = cell.getStringCellValue();
                        }
                        columnList.add(value != null ? value.toString().trim() : "");
                    } else {
                        columnList.add("");
                    }
//						logger.info("~~~~~~[" + i + "," + j + "] - " + cell.getCellType() + ", cell=" + cell + ", value=" + value);
                }
                //logger.info("i=" + i + "" + Arrays.toString(columnList.toArray()));
                rowList.add(columnList);
            }
        } catch (Exception e) {
            logger.info("import2007 - filePath=" + filePath + ", " + e);
            e.printStackTrace();
        }
        return rowList;
    }
}
