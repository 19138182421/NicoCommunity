package com.example.nicocommunity.Controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.nicocommunity.Service.Category.CategoryService;
import com.example.nicocommunity.Service.Goods.GoodService;
import com.example.nicocommunity.Service.Order.OrderService;
import com.example.nicocommunity.annotation.PassToken;
import com.example.nicocommunity.domain.Good;
import com.example.nicocommunity.util.EncapsulatedUtil;
import com.example.nicocommunity.util.TokenUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
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

    @Autowired
    private TokenUtil tokenUtil;

    @Autowired
    private OrderService orderService;

    @Autowired
    private CategoryService categoryService;

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
        List<Good> list = goodService.getGoodsList(cat_id);
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
//        System.out.println(good_id);
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

    /**根据关键词搜索数据*/
    @GetMapping("/qsearch")
    public Object getSuggest(@RequestParam(value = "query",required = true)String query){
//        System.out.println(query);
        query = '%'+query+'%';
        List<Map<String, Object>> list = goodService.getGood(query);

        return encapsulatedUtil.encapsData(list);
    }

    /**搜索建议接口*/
    @GetMapping("/getquery")
    public Object getQuery(@RequestParam(value = "query",required = true)String query){
        //根据传入的关键词搜索包含该关键词的关键词列表
        query = '%'+query+'%';
        List<Map<String, Object>> list = goodService.getQuery(query);

        return encapsulatedUtil.encapsData(list);
    }

    /**根据用户token及用户的购买情况来推荐商品*/
    @PassToken
    @GetMapping("/getsuggest")
    public Object getSuggestGoods(@RequestHeader(value = "token",required = false) String token) {

        List<Good> goodsList = new ArrayList<Good>();
        if (token == null || token == "") {
            //如果没有token代表用户还未登录，此时可以随机抽取商品，后续可以尝试推荐点击量高的商品
            System.out.println("suiji");
            goodsList = goodService.getGoodsListRandom();
        } else {
            //根据token解析出的用户id来查找用户购买过得商品的分类id数组
            long userId = Long.parseLong(tokenUtil.parseUserToken(token));
//            System.out.println(userId);
            List<String> list = orderService.getUserBuyCat(userId);
//            System.out.println(list);
            if (list.size() == 0) {
                System.out.println("随机");
                goodsList = goodService.getGoodsListRandom();
            } else {
                /**根据分类id搜索商品*/
                goodsList = goodService.getSuggestList(list);
            }
        }
        return encapsulatedUtil.encapsObject(goodsList);
    }

    @PassToken
    @GetMapping("/getrandom")
    public Object getRandom() {
        List<Good> goodsList = goodService.getGoodsListRandom();
        return encapsulatedUtil.encapsObject(goodsList);
    }

//    @PassToken
    @GetMapping("/getnavgoods")
    public Object getNavGoods(@RequestParam(value = "cat_id") Integer cat_id) {
        /**根据一级分类id获取二级分类的子id数组*/
        List<Map<String, Object>> catList = categoryService.getCatItems(cat_id);
        List<Good> goodsList = goodService.getNavGoods(cat_id);
        //封装数据发给前端。
        JSONObject jsonObject = new JSONObject();
        //message
        JSONObject message = new JSONObject();
        message.put("catList",catList);
        message.put("goodsList",goodsList);

        //meta
        JSONObject meta = new JSONObject();
        meta.put("msg","获取成功");
        meta.put("status",200);

        jsonObject.put("message",message);
        jsonObject.put("meta",meta);
        return jsonObject;
    }


}
