package org.limingnihao.poi;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.limingnihao.model.ExcelBean;
import org.limingnihao.util.NumberUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.*;

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
     * 导出一个excel
     * @param filePath
     * @param data
     * @return
     */
    public static boolean exportExcel(final String filePath, final HashMap<String, ArrayList<ArrayList<Object>>> data){
        try {
            XSSFWorkbook workbook = new XSSFWorkbook();
            for (final String label : data.keySet()) {
                XSSFSheet sheet = workbook.createSheet(label.replaceAll("\\[|\\]|\\\\|\\:|\\?|\\/", "_"));
                ArrayList<ArrayList<Object>> value = data.get(label);
                for (int i = 0; i < value.size(); i++) {
                    List<Object> items = value.get(i);
                    XSSFRow row = sheet.createRow(i);
                    for (int j = 0; j < items.size(); j++) {
                        Object val = items.get(j);
                        if(val instanceof String ){
                            XSSFCell cell = row.createCell(j, CellType.STRING);
                            cell.setCellValue(val.toString());
                        }
                        else if(val instanceof Double || val instanceof Integer){
                            XSSFCell cell = row.createCell(j, CellType.NUMERIC);
                            cell.setCellValue(NumberUtil.parseDouble(val));
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
                if (value instanceof Integer) {
                    logger.debug("" + (startRow+1) + ", " + j + ", 数值， " + value);
                    fCell.setCellType(CellType.NUMERIC);
                    fCell.setCellValue(NumberUtil.parseInt(value));
                } else if (value instanceof Double) {
                    logger.debug("" + (startRow+1) + ", " + j + ", 数值， " + value);
                    fCell.setCellType(CellType.NUMERIC);
                    fCell.setCellValue(NumberUtil.parseDouble(value));
                } else {
                    String v = "";
                    if(value != null){
                        v = value.toString();
                    }
                    if(v.startsWith("=")){
                        logger.debug("" + (startRow+1) + ", " + j + ", 公式， " + value);
                        fCell.setCellType(CellType.FORMULA);
                        fCell.setCellFormula(v.replace("=", ""));
                    } else {
                        logger.debug("" + (startRow+1) + ", " + j + ", 字符， " + value);
                        fCell.setCellType(CellType.STRING);
                        fCell.setCellValue(v);
                    }
                }
            }
            startRow+=1;
        }
        sheet.getPrintSetup().setLandscape(true);
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

            if(value instanceof Double || value instanceof Integer){
                fCell.setCellType(CellType.NUMERIC);
                fCell.setCellValue(NumberUtil.parseDouble(value));
                logger.debug("replace - row=" + row + ", cell=" + cell + ", value=" + value + ", type=CellType.NUMERIC");
            } else  {
                String v = "";
                if(value != null){
                    v = value.toString();
                }
                if(v.startsWith("=")){
                    fCell.setCellType(CellType.FORMULA);
                    fCell.setCellFormula(v.replace("=", ""));
                    logger.debug("replace - row=" + row + ", cell=" + cell + ", value=" + value + ", type=CellType.FORMULA");
                }else{
                    fCell.setCellType(CellType.STRING);
                    fCell.setCellValue(v);
                    logger.debug("replace - row=" + row + ", cell=" + cell + ", value=" + value + ", type=CellType.STRING");
                }
            }
        }
    }


    /**
     * 复制sheet
     * @param srcSheet
     * @param tartSheet
     * @param workbook
     */
    public static void copySheet(int srcSheet, int tartSheet, XSSFWorkbook workbook){
        Sheet sheet1 = workbook.getSheetAt(srcSheet);
        Sheet sheet2 = workbook.createSheet("附表" + tartSheet);
        sheet2.getPrintSetup().setLandscape(true);//设置横向打印

        CellRangeAddress region = null;
        for (int i = 0; i < sheet1.getNumMergedRegions(); i++) {
            region = sheet1.getMergedRegion(i);
            if ((region.getFirstColumn() >= sheet1.getFirstRowNum())  && (region.getLastRow() <= sheet1.getLastRowNum())) {
                sheet2.addMergedRegion(region);
            }
        }

        //复制内容
        Row rowFrom = null;
        Row rowTo = null;
        Cell cellFrom = null;
        Cell cellTo = null;
        for (int i = sheet1.getFirstRowNum(); i < sheet1.getLastRowNum(); i++) {
            rowFrom = sheet1.getRow(i);
            if (null == rowFrom){
                continue;
            }

            rowTo = sheet2.createRow(i);
            rowTo.setHeight(rowFrom.getHeight());
            for (int j = 0; j < rowFrom.getLastCellNum(); j++) {
                sheet2.setColumnWidth(j, sheet1.getColumnWidth(j));
                if(null != sheet1.getColumnStyle(j)){
                    sheet2.setDefaultColumnStyle(j, sheet1.getColumnStyle(j));
                }

                cellFrom = rowFrom.getCell(j);
                if (null == cellFrom){
                    continue;
                }

                cellTo = rowTo.createCell(j);
                cellTo.setCellStyle(cellFrom.getCellStyle());
                cellTo.setCellType(cellFrom.getCellTypeEnum());

                if(CellType.STRING == cellFrom.getCellTypeEnum()){
                    cellTo.setCellValue(cellFrom.getStringCellValue());
                }else if(CellType.NUMERIC == cellFrom.getCellTypeEnum()){
                    cellTo.setCellValue(cellFrom.getNumericCellValue());
                }
            }
        }

        sheet2.setDisplayGridlines(true);//
        sheet2.getPrintSetup().setLandscape(true);//设置横向打印
    }

    public static void main(String[] args){
        try{
            XSSFWorkbook workbook = ExcelExport2007Util.getWorkbook("/Users/wangheng/Desktop/贵州贵阳世纪城社区.xlsx");
//            Sheet sheet = workbook.cloneSheet(0, "附表1");
            ExcelExport2007Util.copySheet(0,1,workbook);
            ExcelExport2007Util.replace(workbook, 1, new ArrayList<>());
            ExcelExport2007Util.insert(workbook, 1,1, new ArrayList<>());
            System.out.println(workbook.getSheetAt(0).getPrintSetup().getLandscape());
            System.out.println(workbook.getSheetAt(1).getPrintSetup().getLandscape());

//            PrintSetup printSetup = workbook.getSheetAt(1).getPrintSetup();
//            printSetup.setLandscape(true);
//            System.out.println(printSetup.getLandscape());
//            System.out.println(workbook.getSheetAt(2).getPrintSetup().getLandscape());
           workbook.write(new FileOutputStream("/Users/wangheng/Desktop/贵州贵阳世纪城社区.xlsx"));

        }catch (Exception e){
            e.printStackTrace();
        }


    }

    /**
     * 导出一个excel
     * @return
     */
    public static boolean exportExcel(OutputStream out, final HashMap<String, ArrayList<ArrayList<Object>>> data){
        try {
            XSSFWorkbook workbook = new XSSFWorkbook();
            for (final String label : data.keySet()) {
                XSSFSheet sheet = workbook.createSheet(label.replaceAll("\\[|\\]|\\\\|\\:|\\?|\\/", "_"));
                ArrayList<ArrayList<Object>> value = data.get(label);
                for (int i = 0; i < value.size(); i++) {
                    List<Object> items = value.get(i);
                    XSSFRow row = sheet.createRow(i);
                    for (int j = 0; j < items.size(); j++) {
                        Object val = items.get(j);
                        if(val instanceof String ){
                            XSSFCell cell = row.createCell(j, CellType.STRING);
                            cell.setCellValue(val.toString());
                        }
                        else if(val instanceof Double || val instanceof Integer){
                            XSSFCell cell = row.createCell(j, CellType.NUMERIC);
                            cell.setCellValue(NumberUtil.parseDouble(val));
                        }
                    }
                }
            }
            workbook.write(out);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
