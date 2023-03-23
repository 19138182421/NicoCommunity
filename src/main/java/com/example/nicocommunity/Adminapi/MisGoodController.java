package com.example.nicocommunity.Adminapi;


import com.example.nicocommunity.domain.Good;
import com.example.nicocommunity.util.EncapsulatedUtil;
import com.example.nicocommunity.util.UploadUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author yang
 */
@RestController
public class MisGoodController {

    @Autowired
    private MisSerivce misSerivce;

    @Autowired
    private EncapsulatedUtil encapsulatedUtil;

    @Autowired
    private UploadUtils uploadUtils;

    /**
     * 获取所有商品
     */
    @GetMapping("/getAllgoods")
    public Object getAllgoods(@RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                              @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                              @RequestParam(value = "goodField", required = false, defaultValue = "good_id") String goodField,
                              @RequestParam(value = "mode", required = false, defaultValue = "asc") String mode,
                              @RequestParam(value = "good_id", required = false) String goodId,
                              @RequestParam(value = "cat_id", required = false) String catId,
                              @RequestParam(value = "status", required = false) String status) {

        if (Objects.equals(goodId, "")) {
            goodId = null;
        }
        if (Objects.equals(catId, "")) {
            catId = null;
        }
        if (Objects.equals(status, "")) {
            status = null;
        }
        String orderBy = goodField + " " + mode;
        PageHelper.startPage(pageNum, pageSize, orderBy);
        List<Good> list = misSerivce.getAllgoods(goodId, catId, status);
        PageInfo pageInfo = new PageInfo(list);

        return encapsulatedUtil.encapsObject(pageInfo);
    }

    /**
     * 增加商品
     */
    @PostMapping("/addgoods")
    public Object addGood(Good good,
                          MultipartFile file) {
        Date date = new Date();
        good.setAdd_time(date);
        good.setUpd_time(date);
        good.setCat_two_id(good.getCat_id());

        String goodBigLogo = uploadUtils.upload(file);
        if(Objects.equals(goodBigLogo, "文件类型错误")){
            return encapsulatedUtil.encapsObject(goodBigLogo);
        }
        good.setGood_big_logo(goodBigLogo);


        if (misSerivce.getAllgoods(String.valueOf(good.getGood_id()), null, null).size() != 0) {
            return encapsulatedUtil.encapsObject("该商品id已存在");
        } else {
            Integer affectRow = misSerivce.addGood(good);

            return encapsulatedUtil.encapsObject(affectRow);
        }


//        return "ok";
    }

    /**
     * 修改商品
     */
    @PostMapping("/modifygoods")
    public Object modifygoods(Good good,
                              MultipartFile file) {

        Date date = new Date();
        good.setUpd_time(date);

        if (file != null) {
            String goodBigLogo = uploadUtils.upload(file);
            if(Objects.equals(goodBigLogo, "文件类型错误")){
                return encapsulatedUtil.encapsObject(goodBigLogo);
            }
            good.setGood_big_logo(goodBigLogo);
        }

        Integer affectRow = misSerivce.modifygoods(good);

        return encapsulatedUtil.encapsObject(affectRow);
    }

    /**
     * 上架商品
     */
    @GetMapping("/upGood")
    public Object upGood(@RequestParam(value = "goodId") String goodId,
                         @RequestParam(value = "goodNum") Integer goodNum) {

        Integer affectRow = misSerivce.upGood(goodId,goodNum);

        return encapsulatedUtil.encapsObject(affectRow);
    }

    /**
     * 下架商品
     */
    @GetMapping("/downGood")
    public Object downGood(@RequestParam(value = "goodId") String goodId) {

        Integer affectRow = misSerivce.downGood(goodId);

        return encapsulatedUtil.encapsObject(affectRow);
    }


    /**
     * 删除商品
     */
    @GetMapping("/deleteGood")
    public Object deleteGood(@RequestParam(value = "goodId") String goodId) {

        Integer affectRow = misSerivce.deleteGood(goodId);

        return encapsulatedUtil.encapsObject(affectRow);
    }

}
