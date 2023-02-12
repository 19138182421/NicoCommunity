package com.example.nicocommunity.Service.Goods;

import com.example.nicocommunity.Mapper.GoodMapper;
import com.example.nicocommunity.domain.Good;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author yang
 */
@Service
public class GoodServiceImpl implements GoodService {

    @Autowired
    private GoodMapper goodMapper;

    @Override
    public List<Good> getGoodsList(Integer cat_id) {
        return goodMapper.getGoodsList(cat_id);
    }

    @Override
    public Good getGoodDetail(Integer good_id) {
        return goodMapper.getGoodDetail(good_id);
    }

    @Override
    public List<Map<String, Object>> getGoodSwiper(Integer good_id) {
        return goodMapper.getGoodSwiper(good_id);
    }

    @Override
    public List<Map<String, Object>> getGoodTp(Integer good_id) {
        return goodMapper.getGoodTp(good_id);
    }

    @Override
    public List<Map<String, Object>> getGood(String query) {
        return goodMapper.getGood(query);
    }

    @Override
    public List<Map<String, Object>> getQuery(String query) {
        return goodMapper.getQuery(query);
    }

    @Override
    public List<Good> getSuggestList(List<String> list) {
        /**循环数组获取商品并存入list*/
        List<Good> list1 = new ArrayList<Good>();
        list.forEach((e)->{
            list1.addAll(goodMapper.getGoodsList(Integer.valueOf(e)));
        });
//        System.out.println(list1);
        return list1;
    }

    @Override
    public List<Good> getGoodsListRandom() {
        return goodMapper.getGoodsListRandom();
    }

    @Override
    public List<Good> getNavGoods(Integer cat_id) {
        return goodMapper.getNavGoods(cat_id);
    }
}
