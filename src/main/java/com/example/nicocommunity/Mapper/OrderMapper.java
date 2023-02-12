package com.example.nicocommunity.Mapper;

import com.example.nicocommunity.domain.Order;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * @author yang
 */
@Mapper
public interface OrderMapper {

    /**修改订单状态*/
    @Update("UPDATE t_order set pay_status=#{payStatus} WHERE order_id = #{orderId} and user_id = #{userId}")
    int modifyOrderStatus(Long orderId, String payStatus, long userId);

    /**根据用户id获取所有的订单*/
    @Select("Select * from t_order where user_id = #{userId}")
    List<Order> getAllOrders(long userId);

    /**根据订单号获取该订单号下所有的商品信息*/
    @Select("Select * from t_orderitem where order_id = #{orderId}")
    List<Map<String,Object>> getAllOrdersItem(Long orderId);

    @Select("Select * from t_order where user_id = #{userId} and pay_status = #{payStatus}")
    List<Order> getOrdersBySatus(String payStatus, long userId);

    /**添加订单*/
    @Insert("INSERT INTO t_order VALUES (#{order_id},#{user_id},#{order_number},#{order_price},#{create_time},#{update_time},#{order_pay},#{receiver_name},#{receiver_phone},#{order_address},#{pay_status})")
    int addOrder(Order order);

    /**添加订单item*/
    @Insert("INSERT INTO t_orderitem VALUES (0,#{userId},#{orderId},#{goods_id},#{cat_one_id},#{cat_two_id},#{goods_price},#{goods_count},#{goods_name},#{goods_tp},#{goods_small_logo})")
    int addOrderItems(long userId, Long orderId, Object goods_id,Object cat_one_id,Object cat_two_id,Object goods_price, Object goods_count, Object goods_name, Object goods_tp, Object goods_small_logo);

    /**删除订单*/
    @Delete("DELETE FROM t_order WHERE order_id = #{orderId} AND user_id = #{userId}")
    int deleteOrder(Long orderId,long userId);

    /**删除订单item*/
    @Delete("DELETE FROM t_orderitem WHERE order_id = #{orderId} ")
    int deleteOrderItems(Long orderId,long userId);

    /**获取某个用户购买过得分类id的list*/
    @Select("select cat_two_id from t_orderitem WHERE user_id = #{userId} ")
    List<String> getUserBuyCat(long userId);

    @Select("SELECT user_id FROM `t_orderitem` where cat_two_id = #{e}  and user_id != #{userId}")
    List<String> getCatIdList(String e,long userId);

}
