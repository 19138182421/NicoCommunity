package com.example.nicocommunity.Service.Category;

import com.example.nicocommunity.Mapper.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author yang
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;


    @Override
    public List<Map<String, Object>> getCatItems(Integer catPid) {

        /**获取一级分类列表*/
        List<Map<String,Object>> list = categoryMapper.getCatItems(catPid);

        /**获取每个一级分类的二级分类*/
        list.forEach((e)->{
            List<Map<String,Object>> children = categoryMapper.getCatItems((Integer) e.get("cat_id"));
            e.put("children",children);
        });

        return list;
    }

    /**获取对应一级分类下的二级分类名单*/
    @Override
    public List<Map<String, Object>> getCategory(Integer catPid) {
        return categoryMapper.getCatItems(catPid);
    }

    @Override
    public List<Map<String, Object>> getCategoryById(String catId) {
        return categoryMapper.getCategoryById(catId);
    }
}
