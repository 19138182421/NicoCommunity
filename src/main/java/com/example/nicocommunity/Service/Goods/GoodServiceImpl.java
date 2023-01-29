package com.example.nicocommunity.Service.Goods;

import com.example.nicocommunity.Mapper.GoodMapper;
import com.example.nicocommunity.domain.Good;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public List<Map<String, Object>> getGoodsList(Integer good_id) {
        return goodMapper.getGoodsList(good_id);
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
}
