package org.limingnihao.model;

import java.io.Serializable;

/**
 * Created by lishiming on 2017/2/3.
 */
public class ExcelBean implements Serializable {

    public int row;
    public int cell;
    public String value;

    @Override
    public String toString() {
        return "ExcelBean{" +
                "row=" + row +
                ", cell=" + cell +
                ", value='" + value + '\'' +
                '}';
    }

    public ExcelBean(){

    }

    public ExcelBean(int i, int j, String v){
        this.row = i;
        this.cell = j;
        this.value = v;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCell() {
        return cell;
    }

    public void setCell(int cell) {
        this.cell = cell;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
