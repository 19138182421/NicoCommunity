package com.example.nicocommunity.Adminapi;

import com.example.nicocommunity.util.EncapsulatedUtil;
import com.example.nicocommunity.util.UploadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.LongToIntFunction;

@RestController
public class MisTpController {
    @Autowired
    private MisSerivce misSerivce;

    @Autowired
    private EncapsulatedUtil encapsulatedUtil;

    @Autowired
    private UploadUtils uploadUtils;

//    根据商品id获取商品的规格信息
    @GetMapping("/getTpByGoodId")
    public Object getTpByGoodId(@RequestParam(value = "goodId") String goodId){

        List<Map<String,Object>> list = misSerivce.getGoodTps(goodId);
        return encapsulatedUtil.encapsObject(list);
    }

//    添加规格信息
    @PostMapping("/addTpByGoodId")
    public Object addTpByGoodId(@RequestParam(value = "goodId") String goodId,
                                @RequestParam(value = "tpName") String tpName,
                                @RequestParam(value = "tpPrice") String tpPrice,
                                MultipartFile file){

        /**存下来后返回地址*/
        String tpImgSrc = uploadUtils.upload(file);
        if(Objects.equals(tpImgSrc, "文件类型错误")){
            return encapsulatedUtil.encapsObject(tpImgSrc);
        }

        Integer affectRow = misSerivce.addTpByGoodId(goodId,tpName,tpPrice,tpImgSrc);

        return encapsulatedUtil.encapsObject(affectRow);
    }

//    修改规格
    @PostMapping("/modifyTpByGoodId")
    public Object modifyTpByGoodId(@RequestParam(value = "tpId") String tpId,
                                    @RequestParam(value = "goodId") String goodId,
                                    @RequestParam(value = "tpName") String tpName,
                                    @RequestParam(value = "tpPrice") String tpPrice,
                                    MultipartFile file){

         /**存下来后返回地址*/
        String tpImgSrc = uploadUtils.upload(file);
        if(Objects.equals(tpImgSrc, "文件类型错误")){
            return encapsulatedUtil.encapsObject(tpImgSrc);
        }
        Integer affectRow = misSerivce.modifyTpByGoodId(tpName,tpPrice,tpImgSrc,tpId,goodId);

        return encapsulatedUtil.encapsObject(affectRow);
    }

//    删除规格
    @GetMapping("/deleteTpByGoodId")
    public Object deleteTpByGoodId(@RequestParam(value = "tpId") String tpId,
                                 @RequestParam(value = "goodId") String goodId){

        Integer affectRow = misSerivce.deleteTpByGoodId(tpId,goodId);
        return encapsulatedUtil.encapsObject(affectRow);
    }


}
