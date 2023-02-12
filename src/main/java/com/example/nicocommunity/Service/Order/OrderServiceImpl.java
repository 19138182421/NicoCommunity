package com.example.nicocommunity.Service.Order;

import com.example.nicocommunity.Mapper.OrderMapper;
import com.example.nicocommunity.domain.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author yang
 */
@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public List<Order> getAllOrders(long userId) {
//        System.out.println();
        List<Order> list = orderMapper.getAllOrders(userId);
        list.forEach((e)->{
            List<Map<String,Object>> goods = orderMapper.getAllOrdersItem(e.getOrder_id());
            e.setGoods(goods);
        });
//        System.out.println(list);
        return list;
    }

    @Override
    public List<Order> getOrdersByStatus(String payStatus, long userId) {

        List<Order> list = orderMapper.getOrdersBySatus(payStatus,userId);
        list.forEach((e)->{
            List<Map<String,Object>> goods = orderMapper.getAllOrdersItem(e.getOrder_id());
            e.setGoods(goods);
        });
        return list;
    }

    @Override
    public int addOrders(Order order) {

        int result = orderMapper.addOrder(order);
        final int[] result1 = new int[1];
        if(result >= 1 ){
            /**循环goods数组，插入orderitem*/
            order.getGoods().forEach((e) ->{
                result1[0] = orderMapper.addOrderItems(order.getUser_id(),order.getOrder_id(),e.get("goods_id"),e.get("cat_one_id"),e.get("cat_two_id"),e.get("goods_price"),e.get("goods_count"),e.get("goods_name"),e.get("goods_tp"),e.get("goods_small_logo"));
            });
        }
        return result1[0];
    }

    @Override
    public int deleteOrder(Long orderId,long userId) {

        int result = orderMapper.deleteOrder(orderId,userId);
        int result1 = 0;
        if(result >= 1 ){
            /**循环goods数组，插入orderitem*/
            result1 = orderMapper.deleteOrderItems(orderId,userId);
        }
        return result1;
    }

    @Override
    public int modifyOrderStatus(Long orderId, String payStatus, long userId) {
        return orderMapper.modifyOrderStatus(orderId,payStatus,userId);
    }

    @Override
    public List<String> getUserBuyCat(long userId) {
        /**用户购买过得分类id*/
        List<String> list = orderMapper.getUserBuyCat(userId);
        /**过滤list*/
        Set<String> set = new HashSet<String>();
        set.addAll(list);
//        System.out.println(set);

        /**根据分类id查找订单中购买过相同分类商品的用户id*/
        Set<String> set1 = new HashSet<String>();
        set.forEach((e)->{
            set1.addAll(orderMapper.getCatIdList(e,userId));
        });
//        System.out.println(set1);

        /**根据用户id找出用户购买过得分类id*/
        Set<String> set2 = new HashSet<String>();
        set1.forEach((e)->{
            set2.addAll(orderMapper.getUserBuyCat(Long.parseLong(e)));
        });
//        System.out.println(set2);

        return new ArrayList<String>(set2);
    }
}
