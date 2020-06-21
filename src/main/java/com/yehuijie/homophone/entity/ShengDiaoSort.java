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
public class ShengDiaoSort extends Model<ShengDiaoSort> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String shengDiao;
    private Integer sort;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getShengDiao() {
        return shengDiao;
    }

    public void setShengDiao(String shengDiao) {
        this.shengDiao = shengDiao;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ShengDiaoSort{" +
        ", id=" + id +
        ", shengDiao=" + shengDiao +
        ", sort=" + sort +
        "}";
    }
}
