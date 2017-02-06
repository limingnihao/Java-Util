package org.limingnihao.java.util.test;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.limingnihao.model.ExcelBean;
import org.limingnihao.poi.ExcelExport2003Util;
import org.limingnihao.poi.ExcelExport2007Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by lishiming on 2017/2/3.
 */
public class ExcelTest {

    @Test
    public void export(){
        try {
            HashMap<String, ArrayList<ArrayList<String>>> data = new HashMap<>();
            ArrayList<ArrayList<String>> list1 = new ArrayList<>();
            for(int i=1; i<20; i++){
                ArrayList<String> list_list = new ArrayList<>();
                for(int j=1; j<10; j++){
                    list_list.add("第" + i + "行，第" + j + "列");
                }
                list1.add(list_list);
            }
            data.put("第一单元", list1);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void moban(){
        try {
            // 替换的数据
            List<ExcelBean> data = new ArrayList<>();
            data.add(new ExcelBean(1, 3, "a007-我是替换后的编码"));

            // 插入的数据
            List<List<Object>> rowList = new ArrayList<>();
            int startRow = 5;
            for(int i=1; i<=5; i++){
                List<Object> cellList = new ArrayList<>();
                cellList.add("" + i);
                cellList.add("a-" + i);
                cellList.add("iphone7");
                cellList.add("t-" + i);
                cellList.add("部");
                cellList.add(222);
                cellList.add(6255);
                cellList.add("=SUM(F" + (startRow+i+1) + "*G" + (startRow+i+1) + ")");
                cellList.add("价格依据");
                cellList.add("状态依据");
                rowList.add(cellList);
            }
//            ExcelExport2003Util.saveTemplate2007("/Volumes/Workspace/空军项目/军工项目文件/合同模板0.xlsx", "/Volumes/Software/合同模板01.xlsx", 1, data);

            XSSFWorkbook workbook = ExcelExport2007Util.getWorkbook("/Volumes/Workspace/空军项目/军工项目文件/合同模板0.xlsx");
            ExcelExport2007Util.replace(workbook, 1, data);
            ExcelExport2007Util.insert(workbook, 1, startRow, rowList);
            ExcelExport2007Util.saveWorkbook(workbook, "/Volumes/Software/合同模板01.xlsx");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
