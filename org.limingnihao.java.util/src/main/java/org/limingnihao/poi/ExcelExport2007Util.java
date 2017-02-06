package org.limingnihao.poi;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.limingnihao.model.ExcelBean;
import org.limingnihao.util.NumberUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by lishiming on 2017/2/3.
 * 导出，将数据保存成excel文件
 */
public class ExcelExport2007Util {

    private static final Logger logger = LoggerFactory.getLogger(ExcelExport2007Util.class);

    public static XSSFWorkbook getWorkbook(String filePath) throws IOException {
        return new XSSFWorkbook(new FileInputStream(filePath));
    }

    public static void saveWorkbook(XSSFWorkbook workbook, String filePath) throws IOException {
        //修改模板内容导出新模板
        FileOutputStream out = new FileOutputStream(filePath);
        workbook.write(out);
        out.close();
    }

    /**
     * 替换文本
     * @param workbook
     * @param sheetIndex
     * @param datas
     */
    public static void replace(XSSFWorkbook workbook, int sheetIndex, List<ExcelBean> datas){
        //读取了模板内所有sheet内容
        XSSFSheet sheet = workbook.getSheetAt(sheetIndex);
        for(ExcelBean bean : datas){
            int row = bean.getRow();
            int cell = bean.getCell();
            Object value = bean.getValue();
            XSSFRow fRow = sheet.getRow(row);
            logger.debug("replace - row=" + row + ", cell=" + cell + ", value=" + value);
            if(fRow == null){
                throw new NullPointerException("此行不存在:" + row);
            }
            XSSFCell fCell = fRow.getCell(cell);
            if(fCell == null){
                throw new NullPointerException("此单元格不存在:" + row + ", " + cell);
            }
            if(value instanceof Integer){
                fCell.setCellType(CellType.NUMERIC);
                fCell.setCellValue(NumberUtil.parseInt(value));
            } else{
                String v = value.toString();
                if(v.startsWith("=")){
                    fCell.setCellType(CellType.FORMULA);
                }else{
                    fCell.setCellType(CellType.STRING);
                }
                fCell.setCellValue(value.toString());
            }
        }
    }

    /**
     * 插入行
     * @param workbook
     * @param sheetIndex
     * @param startRow - 需要复制的行号，从0开始
     * @param rowList - 数据
     */
    public static void insert(XSSFWorkbook workbook, int sheetIndex, int startRow, List<List<Object>> rowList){

        //读取了模板内所有sheet内容
        XSSFSheet sheet = workbook.getSheetAt(sheetIndex);
        for(int i=0; i<rowList.size(); i++){
            XSSFRow sourceRow = null;
            XSSFRow targetRow = null;
            XSSFCell sourceCell = null;
            XSSFCell targetCell = null;

            sheet.shiftRows(startRow + 1, sheet.getLastRowNum(), 1,true,true);

            sourceRow = sheet.getRow(startRow);
            targetRow = sheet.createRow(startRow + 1);
            targetRow.setHeight(sourceRow.getHeight());
//            targetRow.createCell(0).setCellValue("num:" + startRow);

            //复制格式
            for (int m = sourceRow.getFirstCellNum(); m < sourceRow.getLastCellNum(); m++) {
                sourceCell = sourceRow.getCell(m);
                targetCell = targetRow.createCell(m);
                targetCell.setCellStyle(sourceCell.getCellStyle());
            }

            //填充数据
            for(int j=0; j< rowList.get(i).size(); j++){
                Object value = rowList.get(i).get(j);
                XSSFCell fCell = targetRow.getCell(j);
                if(value instanceof Integer){
                    logger.debug("" + (startRow+1) + ", " + j + ", 数值， " + value);
                    fCell.setCellType(CellType.NUMERIC);
                    fCell.setCellValue(NumberUtil.parseInt(value));
                } else{
                    String v = value.toString();
                    if(v.startsWith("=")){
                        logger.debug("" + (startRow+1) + ", " + j + ", 公式， " + value);
                        fCell.setCellType(CellType.FORMULA);
                        fCell.setCellFormula(value.toString().replace("=", ""));
                    }else{
                        logger.debug("" + (startRow+1) + ", " + j + ", 字符， " + value);
                        fCell.setCellType(CellType.STRING);
                        fCell.setCellValue(value.toString());
                    }
                }
            }
            startRow+=1;
        }
    }


    public static boolean exportExcel(final String filePath, final HashMap<String, ArrayList<ArrayList<String>>> data){
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
