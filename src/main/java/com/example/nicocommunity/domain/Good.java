package com.example.nicocommunity.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author yang
 */
public class Good {
    private Integer good_id;
    private String good_name;
    private Integer cat_id;

    private Double good_price;
    private Integer good_num;
    private String good_big_logo;
    private Integer cat_one_id;
    private Integer cat_two_id;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date add_time;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date upd_time;

    private double good_weight;
    private String key_words;
    private Integer is_promote;
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getGood_id() {
        return good_id;
    }

    public void setGood_id(Integer good_id) {
        this.good_id = good_id;
    }

    public String getGood_name() {
        return good_name;
    }

    public void setGood_name(String good_name) {
        this.good_name = good_name;
    }

    public Integer getCat_id() {
        return cat_id;
    }

    public void setCat_id(Integer cat_id) {
        this.cat_id = cat_id;
    }

    public Double getGood_price() {
        return good_price;
    }

    public void setGood_price(Double good_price) {
        this.good_price = good_price;
    }

    public Integer getGood_num() {
        return good_num;
    }

    public void setGood_num(Integer good_num) {
        this.good_num = good_num;
    }

    public String getGood_big_logo() {
        return good_big_logo;
    }

    public void setGood_big_logo(String good_big_logo) {
        this.good_big_logo = good_big_logo;
    }

    public Integer getCat_one_id() {
        return cat_one_id;
    }

    public void setCat_one_id(Integer cat_one_id) {
        this.cat_one_id = cat_one_id;
    }

    public Integer getCat_two_id() {
        return cat_two_id;
    }

    public void setCat_two_id(Integer cat_two_id) {
        this.cat_two_id = cat_two_id;
    }

    public Date getAdd_time() {
        return add_time;
    }

    public void setAdd_time(Date add_time) {
        this.add_time = add_time;
    }

    public Date getUpd_time() {
        return upd_time;
    }

    public void setUpd_time(Date upd_time) {
        this.upd_time = upd_time;
    }

    public double getGood_weight() {
        return good_weight;
    }

    public void setGood_weight(double good_weight) {
        this.good_weight = good_weight;
    }

    public String getKey_words() {
        return key_words;
    }

    public void setKey_words(String key_words) {
        this.key_words = key_words;
    }

    public Integer getIs_promote() {
        return is_promote;
    }

    public void setIs_promote(Integer is_promote) {
        this.is_promote = is_promote;
    }
}
