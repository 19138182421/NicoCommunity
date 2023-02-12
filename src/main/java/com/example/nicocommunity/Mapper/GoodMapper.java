package com.example.nicocommunity.Mapper;

import com.example.nicocommunity.domain.Good;
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
public interface GoodMapper {

    /**获取商品列表信息*/
    @Select("SELECT good_id,cat_id,good_name,good_price,good_num,good_big_logo,add_time,upd_time,cat_one_id,cat_two_id FROM `t_goods` WHERE cat_id = #{cat_id}")
    public List<Good> getGoodsList(Integer cat_id);

    @Select("SELECT good_id,cat_id,good_name,good_price,good_big_logo,add_time,upd_time,cat_one_id,cat_two_id FROM `t_goods` WHERE good_id = #{good_id}")
    Good getGoodDetail(Integer good_id);

    @Select("SELECT * FROM `t_goodswiper` WHERE good_id = #{good_id}")
    List<Map<String, Object>> getGoodSwiper(Integer good_id);

    @Select("SELECT * FROM `t_tp` WHERE good_id = #{good_id}")
    List<Map<String, Object>> getGoodTp(Integer good_id);

    @Select("SELECT good_id,cat_id,good_name,good_price,good_big_logo,add_time,upd_time,cat_one_id,cat_two_id from `t_goods` WHERE key_words like #{query}")
    List<Map<String, Object>> getGood(String query);

    @Select("select * from t_kw where kw_value like #{query}")
    List<Map<String, Object>> getQuery(String query);

    @Select("SELECT * from t_goods ORDER BY  RAND() LIMIT 20")
    List<Good> getGoodsListRandom();

    @Select("SELECT good_id,cat_id,good_name,good_price,good_num,good_big_logo,add_time,upd_time,cat_one_id,cat_two_id FROM `t_goods` WHERE cat_one_id = #{cat_id}")
    List<Good> getNavGoods(Integer cat_id);
}
