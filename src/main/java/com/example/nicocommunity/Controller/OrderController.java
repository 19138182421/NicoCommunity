package com.example.nicocommunity.Controller;

import com.alibaba.fastjson.JSON;
import com.example.nicocommunity.Service.Order.OrderService;
import com.example.nicocommunity.annotation.PassToken;
import com.example.nicocommunity.domain.Order;
import com.example.nicocommunity.util.EncapsulatedUtil;
import com.example.nicocommunity.util.IdWorker;
import com.example.nicocommunity.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @author yang
 */
@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private EncapsulatedUtil encapsulatedUtil;

    @Autowired
    private TokenUtil tokenUtil;

    /**获取用户所有订单*/
    @GetMapping("/getAllOrders")
    public Object getAllOrders(@RequestHeader(value = "token") String token){
        long userId = Long.parseLong(tokenUtil.parseUserToken(token));
//        System.out.println(userId);
        /**根据用户id获取所有订单*/
        List<Order> list = orderService.getAllOrders(userId);
//        System.out.println(list);
        return encapsulatedUtil.encapsObject(list);
    }

    /**根据状态搜索订单*/
    @GetMapping("/getOrdersByStatus")
    public Object getOrdersByStatus(@RequestHeader(value = "token") String token,@RequestParam(value = "pay_status") String payStatus){

        long userId = Long.parseLong(tokenUtil.parseUserToken(token));

        List<Order> list = orderService.getOrdersByStatus(payStatus,userId);

        return encapsulatedUtil.encapsObject(list);

    }

    /**添加订单*/
    @PostMapping("/addOrders")
    public Object addOrders(@RequestHeader(value = "token") String token,@RequestBody Order order){

        /**从token中获取userId*/
        long userId = Long.parseLong(tokenUtil.parseUserToken(token));
        order.setUser_id(userId);

        /**生成订单id*/
        IdWorker idWorker = new IdWorker(2, 3);
        long id = idWorker.nextId();
        order.setOrder_id(id);

        /**生成订单编号*/
        long random = new Double((Math.random() + 1) * Math.pow(10,3)).longValue();
        String orderNumber = "100" + random + System.currentTimeMillis();
        order.setOrder_number(orderNumber);

        /**设置createTime*/
        order.setCreate_time(new Date());
        order.setUpdate_time(new Date());

        /**填入数据库*/
        int result = orderService.addOrders(order);

//        System.out.println(order.getOrder_id());

        return encapsulatedUtil.encapsObject(order);

    }

    /**根据order_id修改订单状态*/
    @GetMapping("/modifyOrderStatus")
    public Object modifyOrderStatus(@RequestHeader(value = "token") String token,
                                    @RequestParam(value = "orderId") Long orderId,
                                    @RequestParam(value = "payStatus") String payStatus){

        /**从token中获取userId*/
        long userId = Long.parseLong(tokenUtil.parseUserToken(token));

        int result = orderService.modifyOrderStatus(orderId,payStatus,userId);
        if(result >= 1){
            return encapsulatedUtil.encapsObject("修改订单状态成功");
        }else{
            return encapsulatedUtil.encapsObject("修改订单状态失败");
        }

    }

    /**根据order_id删除订单*/
    @GetMapping("/deleteOrder")
    public Object deleteOrder(@RequestHeader(value = "token") String token,
                              @RequestParam(value = "order_id") Long orderId){

        /**从token中获取userId*/
        long userId = Long.parseLong(tokenUtil.parseUserToken(token));

        int result = orderService.deleteOrder(orderId,userId);

        return encapsulatedUtil.encapsObject("ok");
    }

}
