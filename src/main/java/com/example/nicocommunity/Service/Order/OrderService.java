package com.example.nicocommunity.Service.Order;

import com.example.nicocommunity.domain.Order;

import java.util.List;
import java.util.Map;

/**
 * @author yang
 */
public interface OrderService {

    /**获取所有订单*/
    List<Order> getAllOrders(long userId);

    /**根据用户id和状态查询订单*/
    List<Order> getOrdersByStatus(String payStatus, long userId);

    /**创建订单*/
    int addOrders(Order order);

    /**删除订单*/
    int deleteOrder(Long orderId,long userId);

    /**修改订单状态*/
    int modifyOrderStatus(Long orderId, String payStatus, long userId);

    /**根据用户id获取用户购买过得商品的分类id数组*/
    List<String> getUserBuyCat(long userId);
}
