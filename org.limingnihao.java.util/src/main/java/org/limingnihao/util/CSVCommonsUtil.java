package org.limingnihao.util;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.QuoteMode;
import org.limingnihao.model.FileBean;
import org.limingnihao.model.SortBean;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 使用Commons csv生成文件
 */
public class CSVCommonsUtil {


    /**
     * 写CSV文件
     *
     * @param fileName
     */
    public static void writeCsvFile(String fileName, List<Object> list) {
        FileWriter fileWriter = null;
        CSVPrinter csvFilePrinter = null;
        //创建 CSVFormat
        CSVFormat csvFileFormat = CSVFormat.DEFAULT.withRecordSeparator('\n').withDelimiter(',').withQuote('"').withQuoteMode(QuoteMode.ALL);
        try {
            //初始化FileWriter
            fileWriter = new FileWriter(fileName);
            //初始化 CSVPrinter
            csvFilePrinter = new CSVPrinter(fileWriter, csvFileFormat);
            //创建CSV文件头
            csvFilePrinter.printRecord(SortBean.class);
            //插入数据
            csvFilePrinter.printRecord(list);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fileWriter.flush();
                fileWriter.close();
                csvFilePrinter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



}
