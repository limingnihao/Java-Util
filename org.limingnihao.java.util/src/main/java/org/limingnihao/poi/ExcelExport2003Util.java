package org.limingnihao.poi;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.CellType;
import org.limingnihao.model.ExcelBean;
import org.limingnihao.util.NumberUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by lishiming on 2017/2/3.
 * 导出，将数据保存成excel文件
 */
public class ExcelExport2003Util {

    private static final Logger logger = LoggerFactory.getLogger(ExcelExport2003Util.class);

    public static boolean saveTemplate(String templateFilePath, String saveFilePath, int sheetIndex, List<ExcelBean> datas){
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
                Object value = bean.getValue();
                HSSFRow fRow = sheet.getRow(row);
                logger.debug("saveTemplate2003 - row=" + row + ", cell=" + cell + ", value=" + value);
                if(fRow == null){
                    logger.error("saveTemplate2003 - xssfRow is null - row=" + row + ", cell=" + cell + ", value=" + value);
                    return false;
                }
                HSSFCell ffCell = fRow.getCell(cell);
                if(ffCell == null){
                    logger.error("saveTemplate2003 - xssfCell is null - row=" + row + ", cell=" + cell + ", value=" + value);
                    return false;
                }
                if(value instanceof Integer){
                    ffCell.setCellType(CellType.NUMERIC);
                    ffCell.setCellValue(NumberUtil.parseInt(value));
                } else{
                    ffCell.setCellType(CellType.STRING);
                    ffCell.setCellValue(value.toString());
                }
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


    public static boolean exportExcel(final String filePath, final HashMap<String, ArrayList<ArrayList<Object>>> data){
        try {
            HSSFWorkbook workbook = new HSSFWorkbook();
            for (final String label : data.keySet()) {
                HSSFSheet sheet = workbook.createSheet(label.replaceAll("\\[|\\]|\\\\|\\:|\\?|\\/", "_"));
                ArrayList<ArrayList<Object>> value = data.get(label);
                for (int i = 0; i < value.size(); i++) {
                    List<Object> items = value.get(i);
                    HSSFRow row = sheet.createRow(i);
                    for (int j = 0; j < items.size(); j++) {
                        Object val = items.get(j);
                        if(val instanceof String ){
                            HSSFCell cell = row.createCell(j, CellType.STRING);
                            cell.setCellValue(val.toString());
                        }
                        else if(items.get(j) instanceof Double || val instanceof Integer){
                            HSSFCell cell = row.createCell(j, CellType.NUMERIC);
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

}
