package com.example.nicocommunity.Service.Goods;

import com.example.nicocommunity.domain.Good;

import java.util.List;
import java.util.Map;

/**
 * @author yang
 */
public interface GoodService {

    /**获取商品列表*/
    List<Map<String,Object>> getGoodsList(Integer cat_id);

    /**获取商品详情*/
    Good getGoodDetail(Integer good_id);

    /**获取轮播图信息*/
    List<Map<String,Object>> getGoodSwiper(Integer good_id);

    /**获取规格信息*/
    List<Map<String, Object>> getGoodTp(Integer good_id);

    /**获取搜索建议*/
    List<Map<String, Object>> getGood(String query);
}
