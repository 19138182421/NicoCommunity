package com.example.nicocommunity.Mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author yang
 */
@Mapper
@Repository
public interface CategoryMapper {

    @Select("SELECT cat_id,cat_name,cat_pid,cat_imgsrc,nav_imgsrc,create_time FROM `t_category` where cat_pid = #{catPid}")
    List<Map<String,Object>> getCatItems(Integer catPid);

    @Select("SELECT cat_id,cat_name,cat_pid,cat_imgsrc,nav_imgsrc,create_time FROM `t_category` where cat_id = #{catId}")
    List<Map<String, Object>> getCategoryById(String catId);
}
