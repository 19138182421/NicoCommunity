package com.example.nicocommunity.Controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.nicocommunity.Service.Address.AddressService;
import com.example.nicocommunity.domain.Address;
import com.example.nicocommunity.util.EncapsulatedUtil;
import com.example.nicocommunity.util.TokenUtil;
import org.apache.logging.log4j.spi.ObjectThreadContextMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author yang
 */
@RestController
public class AddressController {

    @Autowired
    private AddressService addressService;

    @Autowired
    private EncapsulatedUtil encapsulatedUtil;

    @Autowired
    private TokenUtil tokenUtil;

    /***根据用户id获取用户所有的地址信息
     */
    @GetMapping("/getAddress")
    public Object getAllAddressById(@RequestHeader(value = "token") String token){

//        Integer userId = 10000;
        long userId = Long.parseLong(tokenUtil.parseUserToken(token));
        /**获取数据*/
        List<Map<String, Object>> list = addressService.getAllAddress(String.valueOf(userId));

        /**封装返回的数据*/
        return encapsulatedUtil.encapsData(list);
    }

    @PostMapping("/addAddress")
    public Object addAddress(@RequestHeader(value = "token") String token,
                             @RequestBody Address address,
                             @RequestParam(value = "isChange",defaultValue = "1") Integer isChange){

//        Integer userId = 10000;
        long userId = Long.parseLong(tokenUtil.parseUserToken(token));
        address.setUserId(userId);
//        System.out.println(isChange);

        /**如果已有默认且要新增默认，就调用全部修改为非默认地址的方法，再插入新的默认地址，否则直接插入非默认地址*/
        if(isChange == 0){
            /**调用修改所有地址为非默认的方法*/
            int modifyResult = addressService.modify(userId);
        }

        /**加入信息数据库*/
        int result = addressService.addAddress(address);
        return encapsulatedUtil.encapsObject(address);
    }

    @GetMapping("/deleteAddress")
    public Object deleteAddress(@RequestHeader(value = "token") String token,
                                @RequestParam("addressId") String addressId){

//        Integer userId = 10000;
        long userId = Long.parseLong(tokenUtil.parseUserToken(token));
        int result = addressService.deleteAddress(addressId,userId);
        return encapsulatedUtil.encapsObject("ok");
    }

    @PostMapping("/modifyAddress")
    public Object modifyAddress(@RequestHeader(value = "token") String token,
                                @RequestBody Address address,
                                @RequestParam(value = "isChange",defaultValue = "1") Integer isChange){

//        Integer userId = 10000;
        long userId = Long.parseLong(tokenUtil.parseUserToken(token));
        address.setUserId(userId);
        /**当isChange为0时，代表需要*/
        if(isChange == 0){
            int modifyResult = addressService.modify(userId);
        }
//        System.out.println(address.getAddressDetail());
        int result = addressService.modifyAddress(address);
        return encapsulatedUtil.encapsObject(address);
    }
}
