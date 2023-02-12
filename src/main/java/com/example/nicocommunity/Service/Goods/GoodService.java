package com.example.nicocommunity.Service.Goods;

import com.example.nicocommunity.domain.Good;

import java.util.List;
import java.util.Map;

/**
 * @author yang
 */
public interface GoodService {

    /**获取商品列表*/
    List<Good> getGoodsList(Integer cat_id);

    /**获取商品详情*/
    Good getGoodDetail(Integer good_id);

    /**获取轮播图信息*/
    List<Map<String,Object>> getGoodSwiper(Integer good_id);

    /**获取规格信息*/
    List<Map<String, Object>> getGoodTp(Integer good_id);

    /**获取搜索建议*/
    List<Map<String, Object>> getGood(String query);

    List<Map<String, Object>> getQuery(String query);

    /**根据分类id获取商品集合*/
    List<Good> getSuggestList(List<String> list);

    /**随机获取商品*/
    List<Good> getGoodsListRandom();

    List<Good> getNavGoods(Integer cat_id);
}
