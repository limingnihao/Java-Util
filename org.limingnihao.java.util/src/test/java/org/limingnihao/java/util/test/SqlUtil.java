package org.limingnihao.java.util.test;

import java.sql.SQLException;
import java.util.ArrayList;

import org.limingnihao.util.ExcelUtil;
import org.limingnihao.util.MySqlUtil;
import org.limingnihao.util.NumberUtil;

/**
 * Created by lishiming on 16/7/25.
 */
public class SqlUtil {

    public static void main(String[] args){
        MySqlUtil mysql = null;
        try {
            mysql = new MySqlUtil("jdbc:mysql://101.201.102.62:3306/test?useUnicode=true&characterEncoding=utf-8", "admin", "admin");
            System.out.println(mysql.execute("delete from test.part_info;"));
            String start = "INSERT INTO test.part_info ( part_code, product_code, self_code, part_brand, product_name, oe_code, part_specification, car_series, car_brand, car_factory, car_model, car_year, car_motor, price, stock)";
            ArrayList<ArrayList<String>> data = ExcelUtil.importExcel("/Volumes/Workspace/飞扬恒信/数据字段模版.xlsx", 0);
            data.remove(0);
            for(ArrayList<String> list : data){
                String sql = "";
                sql += "'" + list.get(0) + "', '" + list.get(1) + "', '" + list.get(2) + "', '" + list.get(3) + "', '" + list.get(4) + "', '"
                        + list.get(5) + "', '" + list.get(6) + "', '" + list.get(7) + "', '" + list.get(8) + "', '" + list.get(9) + "', '"
                        + list.get(10) + "', '" + list.get(11) + "', '" + list.get(12) + "', '" + list.get(13) + "', " + (NumberUtil.parseInt(list.get(14).equals(""), 0));
                sql = start + " value(" + sql + ")";
                mysql.executeUpdate(sql, null);
                System.out.println(sql);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
