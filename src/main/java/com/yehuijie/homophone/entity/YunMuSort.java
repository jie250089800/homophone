package com.yehuijie.homophone.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.activerecord.Model;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author yhj
 * @since 2020-06-21
 */
public class YunMuSort extends Model<YunMuSort> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String yunMu;
    private Integer sort;
    private Integer lengthSort;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getYunMu() {
        return yunMu;
    }

    public void setYunMu(String yunMu) {
        this.yunMu = yunMu;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getLengthSort() {
        return lengthSort;
    }

    public void setLengthSort(Integer lengthSort) {
        this.lengthSort = lengthSort;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "YunMuSort{" +
        ", id=" + id +
        ", yunMu=" + yunMu +
        ", sort=" + sort +
        ", lengthSort=" + lengthSort +
        "}";
    }
}
