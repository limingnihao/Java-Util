package org.limingnihao.java.util.test;

import jxl.write.WriteException;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.limingnihao.model.ExcelBean;
import org.limingnihao.poi.ExcelExportUtil;
import org.limingnihao.util.ExcelUtil;

import java.io.*;
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


            ExcelExportUtil.exportExcel2007("/Volumes/Software/a1.xls", data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void moban(){
        try {
            List<ExcelBean> data = new ArrayList<>();
            data.add(new ExcelBean(3, 2, "我是甲方"));

            data.add(new ExcelBean(2, 4, "模板"));
            ExcelExportUtil.saveTemplate2007("/Volumes/Software/合同模板.xlsx", "/Volumes/Software/合同模板3.xlsx", 0, data);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
