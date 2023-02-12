package com.example.nicocommunity.Mapper;

import com.example.nicocommunity.domain.Address;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * @author yang
 */
@Mapper
public interface AddressMapper {

    @Select("SELECT * FROM `t_address` WHERE user_id = #{userId}")
    List<Map<String, Object>> getAllAddress(String userId);

    @Insert("INSERT INTO t_address VALUES (0,#{userId},#{receiverName},#{receiverPhone},#{addressArea},#{addressDetail},#{isDefault})")
    int addAddress(Address address);

    @Delete("DELETE FROM t_address WHERE address_id = #{addressId} AND user_id = #{userId}")
    int deleteAddress(String addressId, Long userId);

    @Update("UPDATE t_address set is_default = 1 WHERE user_id = #{userId}")
    int modify(Long userId);

    @Update("UPDATE t_address set receiver_name = #{receiverName},receiver_phone = #{receiverPhone},address_area = #{addressArea},address_detail = #{addressDetail},is_default = #{isDefault} where address_id = #{addressId} and user_id = #{userId}")
    int modifyAddress(Address address);
}
