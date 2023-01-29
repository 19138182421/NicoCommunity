package com.example.nicocommunity.Controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.nicocommunity.Service.Goods.GoodService;
import com.example.nicocommunity.domain.Good;
import com.example.nicocommunity.util.EncapsulatedUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author yang
 */
@RestController
public class GoodsController {

    @Autowired
    private GoodService goodService;

    @Autowired
    private EncapsulatedUtil encapsulatedUtil;

    @GetMapping("/search")
    public Object getGoodsList(@RequestParam(value = "query",required = false)String query,
                               @RequestParam(value = "cat_id",required = false)Integer cat_id,
                               @RequestParam(value = "pageNum",required = false,defaultValue = "1")Integer pageNum,
                               @RequestParam(value = "pageSize",required = false,defaultValue = "6")Integer pageSize){
        /**
         * 只有紧跟下面一行代码的查询语句会分页
         * */
        PageHelper.startPage(pageNum, pageSize);
        /**获取商品列表信息*/
        List<Map<String,Object>> list = goodService.getGoodsList(cat_id);
        PageInfo pageInfo = new PageInfo(list);
//        System.out.println(pageInfo.getTotal());
        /**返回搜索到的商品列表*/
        JSONObject jsonObject = new JSONObject();
        JSONObject message = new JSONObject();
        JSONObject meta = new JSONObject();

        message.put("total",pageInfo.getTotal());
        message.put("pageNum",pageInfo.getPageNum());
        message.put("goods",list);
        meta.put("msg","获取成功！");
        meta.put("status",200);
        jsonObject.put("message",message);
        jsonObject.put("meta",meta);
        return jsonObject;
    }

    @GetMapping("/detail")
    public Object getGoodDetail(@RequestParam(value = "good_id",required = true)Integer good_id){
        System.out.println(good_id);
        JSONObject jsonObject = new JSONObject();
        JSONObject meta = new JSONObject();

        /**获取商品基本信息*/
        Good good = goodService.getGoodDetail(good_id);
        JSONObject message =  (JSONObject) JSON.toJSON(good);
        /**获取轮播图信息*/
        List<Map<String,Object>> pics = goodService.getGoodSwiper(good_id);
        message.put("pics",pics);
        /**获取规格信息*/
        List<Map<String,Object>> attrs = goodService.getGoodTp(good_id);
        message.put("attrs",attrs);

        meta.put("msg","获取成功！");
        meta.put("status",200);
        jsonObject.put("message",message);
        jsonObject.put("meta",meta);
        return jsonObject;
    }

    /**搜索建议接口*/
    @GetMapping("/qsearch")
    public Object getSuggest(@RequestParam(value = "query",required = true)String query){
        System.out.println(query);
        query = '%'+query+'%';
        List<Map<String, Object>> list = goodService.getGood(query);

        return encapsulatedUtil.encapsData(list);
    }
}
