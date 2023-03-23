package com.example.nicocommunity.Adminapi;

import com.example.nicocommunity.domain.Admin;
import com.example.nicocommunity.domain.Good;
import com.example.nicocommunity.domain.Order;
import com.example.nicocommunity.domain.User;

import java.util.List;
import java.util.Map;

/**
 * @author yang
 */
public interface MisSerivce {

    String getTodayCount(String currentDate);

    String getMonthVolume(String currentMonth);

    String getYearVolume(String currentYear);

    String getOrderCountByStatus(String currentYear,String status);

    List<String> getVoulume(String year);



    List<Order> getOrders(String date);

    List<Order> getAllOrders(String orderId,String userId,String orderNumber,String payStatus);

    List<Order> getOrderByOrderId(String orderId);

    Integer modifyOrder(String updateTime, String orderId, String receiverName, String receiverPhone, String orderAddress,String payStatus);


    Integer addCategory(String catId, String catName, String catPid, String catImgsrc, String navImgsrc, String createTime);

    Integer modifyCategory(String catId, String catName, String catImgsrc, String navImgsrc);

    Integer deleteCategory(String catId);

    Integer selectCategory(String catId);


    List<Map<String, Object>> getGoodTps(String goodId);

    Integer addTpByGoodId(String goodId, String tpName, String tpPrice, String tpImgSrc);

    Integer modifyTpByGoodId(String tpName, String tpPrice, String tpImgSrc, String tpId, String goodId);

    Integer deleteTpByGoodId(String tpId, String goodId);


    List<Map<String, Object>> getSwipersByGoodId(String goodId);

    Integer addSwiperByGoodId(String goodId, String picUrl);

    Integer modifySwiperByGoodId(String picUrl, String goodId, String picId);

    Integer deleteSwiperByGoodId(String picId, String goodId);


    List<Good> getAllgoods(String goodId, String catId, String status);

    Integer addGood(Good good);

    Integer modifygoods(Good good);

    Integer deleteGood(String goodId);

    List<User> getUsers(String userId);

    String getOrderCounts(String currentYear);

    Integer downGood(String goodId);

    Integer upGood(String goodId,Integer goodNum);

    Integer deleteUser(String userId);

    Admin getAdmin(String adminId);

    Integer changeAvatar(String adminId, String picUrl);

    Integer changePassword(String adminId, String adminPwd);
}
