package com.yehuijie.homophone.model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 *单元格属性
 * Created by yhj on 2018/10/19
 */
@ApiModel
public class cell extends BaseModel {

    private static final long serialVersionUID = -98719237123792139L;
    @ApiModelProperty(value = "数值")
    private String value;

    @ApiModelProperty(value = "行")
    private String row;

    @ApiModelProperty(value = "列")
    private String col;

    @ApiModelProperty(value = "宽")
    private String width;


    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getRow() {
        return row;
    }

    public void setRow(String row) {
        this.row = row;
    }

    public String getCol() {
        return col;
    }

    public void setCol(String col) {
        this.col = col;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }



}
