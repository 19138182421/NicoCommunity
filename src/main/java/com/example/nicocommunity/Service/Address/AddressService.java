package com.example.nicocommunity.Service.Address;

import com.example.nicocommunity.domain.Address;

import java.util.List;
import java.util.Map;

/**
 * @author yang
 */
public interface AddressService {
    /**获取用户所有地址信息*/
    List<Map<String, Object>> getAllAddress(String userId);

    /**添加地址信息*/
    int addAddress(Address address);

    /**根据地址id和用户id来删除某个地址*/
    int deleteAddress(String addressId, Long userId);

    /**修改所有地址为非默认地址*/
    int modify(Long userId);

    /**修改某个地址信息*/
    int modifyAddress(Address address);
}
