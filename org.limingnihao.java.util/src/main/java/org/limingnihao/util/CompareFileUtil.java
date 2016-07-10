package org.limingnihao.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 唐溶波 on 16/4/11.
 */
public class CompareFileUtil {

    private static final Logger logger = LoggerFactory.getLogger(CompareFileUtil.class);


    /**
     * 比较导入的excel与模板是否一致
     * @param templateList 模板数据
     * @param fileList 导入数据
     * @param rowIndex 头信息的起始行
     * @return
     */
    public static boolean compareExcel(ArrayList<ArrayList<String>> templateList,  ArrayList<ArrayList<String>> fileList, int rowIndex){

        if(templateList.size() <= rowIndex) return false;
        if(fileList.size() <= rowIndex) return false;

        List<String> templateData = templateList.get(rowIndex);
        List<String> fileData = fileList.get(rowIndex);

        if(templateData.size() < 1) return false;
        if(fileData.size() < 1) return false;
        if(templateData.size() != fileData.size()) return false;

        for (int x = 0; x < fileData.size();x++) {
            if(!fileData.get(x).equals(templateData.get(x))){
                return false;
            }
        }

        return true;
    }


}
