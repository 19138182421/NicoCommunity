package com.example.nicocommunity.Adminapi;

import com.example.nicocommunity.util.EncapsulatedUtil;
import com.example.nicocommunity.util.UploadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author yang
 */
@RestController
public class MisSwiperController {

    @Autowired
    private MisSerivce misSerivce;

    @Autowired
    private EncapsulatedUtil encapsulatedUtil;

    @Autowired
    private UploadUtils uploadUtils;

    /**根据商品id获取轮播图*/
    @GetMapping("/getSwipersByGoodId")
    public Object getSwipersByGoodId(@RequestParam(value = "goodId") String goodId){

        List<Map<String,Object>> list = misSerivce.getSwipersByGoodId(goodId);

        return encapsulatedUtil.encapsObject(list);
    }

    /**添加轮播图*/
    @PostMapping("/addSwiperByGoodId")
    public Object addSwiperByGoodId(@RequestParam(value = "goodId") String goodId,
                                    MultipartFile file){

//        System.out.println(file.getOriginalFilename());
        /**存入后返回存储地址*/
        String picUrl = uploadUtils.upload(file);
        if(Objects.equals(picUrl, "文件类型错误")){
            return encapsulatedUtil.encapsObject(picUrl);
        }

        Integer affectRow = misSerivce.addSwiperByGoodId(goodId,picUrl);

        return encapsulatedUtil.encapsObject(affectRow);
//        return encapsulatedUtil.encapsObject(picUrl);

    }

    /**修改轮播图*/
    @PostMapping("/modifySwiperByGoodId")
    public Object modifySwiperByGoodId(@RequestParam(value = "goodId") String goodId,
                                       @RequestParam(value = "picId") String picId,
                                       MultipartFile file){

        /**存入后返回存储地址*/
        String picUrl = uploadUtils.upload(file);
        if(Objects.equals(picUrl, "文件类型错误")){
            return encapsulatedUtil.encapsObject(picUrl);
        }

        Integer affectRow = misSerivce.modifySwiperByGoodId(picUrl,goodId,picId);

        return encapsulatedUtil.encapsObject(affectRow);
    }

    /**删除轮播图*/
    @GetMapping("/deleteSwiperByGoodId")
    public Object deleteSwiperByGoodId(@RequestParam(value = "goodId") String goodId,
                                       @RequestParam(value = "picId") String picId){

        Integer affectRow = misSerivce.deleteSwiperByGoodId(picId,goodId);

        return encapsulatedUtil.encapsObject(affectRow);
    }

}
