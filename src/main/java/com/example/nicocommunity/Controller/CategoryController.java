package com.example.nicocommunity.Controller;

import com.alibaba.fastjson.JSON;
import com.example.nicocommunity.Service.Category.CategoryService;
import com.example.nicocommunity.util.EncapsulatedUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.event.ObjectChangeListener;
import java.util.List;
import java.util.Map;

/**
 * @author yang
 * 分类导航列表接口
 */
@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private EncapsulatedUtil encapsulatedUtil;

    @GetMapping("getcategory/{catPid}")
    public Object getCategory(@PathVariable("catPid") Integer catPid){
        List<Map<String, Object>> list = categoryService.getCategory(catPid);

        return encapsulatedUtil.encapsData(list);
    }

    @GetMapping("/getcatitems")
    public Object getCatItems(){
        List<Map<String, Object>> list = categoryService.getCatItems(0);
//        System.out.println(list);
        return encapsulatedUtil.encapsData(list);
    }

}
