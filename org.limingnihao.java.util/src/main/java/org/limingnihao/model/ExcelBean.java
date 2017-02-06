package org.limingnihao.model;

import java.io.Serializable;

/**
 * Created by lishiming on 2017/2/3.
 */
public class ExcelBean implements Serializable {

    public int row;
    public int cell;
    public Object value;

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

    public ExcelBean(int row, int cell, Object v){
        this.row = row;
        this.cell = cell;
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

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
