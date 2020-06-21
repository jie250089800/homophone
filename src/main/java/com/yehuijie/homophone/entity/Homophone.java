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
public class Homophone extends Model<Homophone> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String name;
    private String shengMu;
    private String yunMu;
    private String shengDiao;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShengMu() {
        return shengMu;
    }

    public void setShengMu(String shengMu) {
        this.shengMu = shengMu;
    }

    public String getYunMu() {
        return yunMu;
    }

    public void setYunMu(String yunMu) {
        this.yunMu = yunMu;
    }

    public String getShengDiao() {
        return shengDiao;
    }

    public void setShengDiao(String shengDiao) {
        this.shengDiao = shengDiao;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Homophone{" +
        ", id=" + id +
        ", name=" + name +
        ", shengMu=" + shengMu +
        ", yunMu=" + yunMu +
        ", shengDiao=" + shengDiao +
        "}";
    }
}
