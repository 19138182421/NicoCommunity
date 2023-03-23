package com.example.nicocommunity.Adminapi;

import com.example.nicocommunity.domain.Admin;
import com.example.nicocommunity.domain.Good;
import com.example.nicocommunity.domain.Order;
import com.example.nicocommunity.domain.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author yang
 */
@Mapper
@Repository
public interface MisMapper {

    @Select("SELECT count(order_id) FROM `t_order` where create_time LIKE #{currentDay} and pay_status = '待发货'")
    String getTodayCount(String currentDay);

    @Select("SELECT sum(order_price) FROM `t_order` where create_time LIKE #{currentMonth} and pay_status = '交易完成'")
    String getMonthVolume(String currentMonth);

    @Select("SELECT sum(order_price) FROM `t_order` where create_time LIKE #{currentYear} and pay_status = '交易完成'")
    String getYearVolume(String currentYear);

    @Select("SELECT count(order_id) FROM `t_order` where create_time LIKE #{currentDate} and pay_status = #{status}")
    String getOrderCountByStatus(String currentDate, String status);

    @Select("SELECT COUNT(order_id) from t_order WHERE create_time LIKE #{currentDate}")
    String getOrderCounts(String currentDate);

    @Select("SELECT sum(order_price) FROM `t_order` where update_time LIKE #{date} and pay_status = '交易完成'")
    String getvolumeByMonth(String date);

    @Select("SELECT * from t_order WHERE create_time LIKE #{date}")
    List<Order> getOrders(String date);

    @Select(" <script> SELECT * from t_order WHERE 1+1 " +
            " <if test= 'orderId != null '>and order_id = #{orderId} </if> " +
            " <if test= 'userId != null '>and user_id = #{userId} </if> " +
            " <if test= 'orderNumber != null '>and order_number = #{orderNumber} </if> " +
            " <if test= 'payStatus != null '>and pay_status = #{payStatus} </if> </script>")
    List<Order> getAllOrders(String orderId, String userId, String orderNumber, String payStatus);

    @Select("SELECT * from t_order WHERE order_id = #{orderId}")
    List<Order> getOrderByOrderId(String orderId);

    @Update("UPDATE t_order set update_time = #{updateTime},receiver_name = #{receiverName},receiver_phone = #{receiverPhone},order_address = #{orderAddress},pay_status = #{payStatus} WHERE  order_id = #{orderId}")
    Integer modifyOrder(String updateTime, String orderId, String receiverName, String receiverPhone, String orderAddress, String payStatus);

    @Insert("insert INTO t_category VALUES(0,#{catId},#{catName},#{catPid},#{catImgsrc},#{navImgsrc},#{createTime})")
    Integer addCategory(String catId, String catName, String catPid, String catImgsrc, String navImgsrc, String createTime);

    @Update("UPDATE t_category set cat_name = #{catName},cat_imgsrc = #{catImgsrc},nav_imgsrc = #{navImgsrc} where cat_id = #{catId}")
    Integer modifyCategory(String catId, String catName, String catImgsrc, String navImgsrc);

    @Delete("DELETE from t_category WHERE cat_id = #{catId}")
    Integer deleteCategory(String catId);

    @Select("select count(cat_id) from t_category where cat_id = #{catId}")
    Integer selectCategory(String catId);

    @Select("SELECT * from t_tp WHERE good_id = #{goodId}")
    List<Map<String, Object>> getTpByGoodId(String goodId);

    @Insert("insert into t_tp VALUES(0,#{goodId},#{tpName},#{tpPrice},#{tpImgSrc})")
    Integer addTpByGoodId(String goodId, String tpName, String tpPrice, String tpImgSrc);

    @Update("UPDATE t_tp set tp_name = #{tpName},tp_price = #{tpPrice},tp_imgsrc = #{tpImgSrc} WHERE tp_id = #{tpId} and good_id = #{goodId}")
    Integer modifyTpByGoodId(String tpName, String tpPrice, String tpImgSrc, String tpId, String goodId);

    @Delete("delete from t_tp WHERE tp_id = #{tpId} and good_id = #{goodId}")
    Integer deleteTpByGoodId(String tpId, String goodId);

    @Select("SELECT * from t_goodswiper WHERE good_id = #{goodId}")
    List<Map<String, Object>> getSwipersByGoodId(String goodId);

    @Insert("insert into t_goodswiper VALUES(0,#{goodId},#{picUrl})")
    Integer addSwiperByGoodId(String goodId, String picUrl);

    @Update("UPDATE t_goodswiper set pic_url = #{picUrl} WHERE pic_id = #{picId} and good_id = #{goodId}")
    Integer modifySwiperByGoodId(String picUrl, String goodId, String picId);

    @Delete("delete from t_goodswiper WHERE pic_id = #{picId} and good_id = #{goodId}")
    Integer deleteSwiperByGoodId(String picId, String goodId);

    @Select(" <script> SELECT * from t_goods WHERE 1+1 " +
            " <if test= 'goodId != null '>and good_id = #{goodId} </if> " +
            " <if test= 'catId != null '>and cat_one_id = #{catId} </if> " +
            " <if test= 'status != null '>and status = #{status} </if> </script>")
    List<Good> getAllgoods(String goodId, String catId, String status);

    @Insert("insert INTO t_goods VALUES(#{good_id},#{good_name},#{cat_id},#{good_price},#{good_num},#{good_weight},#{good_big_logo},'',#{add_time},#{upd_time},0,#{cat_one_id},#{cat_two_id},NULL,#{key_words},#{status})")
    Integer addGood(Good good);

    @Update("UPDATE t_goods set good_name = #{good_name},cat_id = #{cat_id},good_price = #{good_price},good_num = #{good_num},good_big_logo = #{good_big_logo},upd_time = #{upd_time},cat_one_id = #{cat_one_id},cat_two_id = #{cat_id},status = #{status} WHERE good_id = #{good_id}")
    Integer modifygoods(Good good);

    @Update("UPDATE t_goods set good_num = 0,status = '已下架',upd_time = #{date} WHERE good_id = #{goodId}")
    Integer downGood(String goodId, Date date);

    @Delete("delete from t_goods where good_id = #{goodId}")
    Integer deleteGood(String goodId);

    @Select("<script> SELECT * from t_user WHERE 1+1 " +
            " <if test= 'userId != null '>and userid = #{userId} </if> </script>")
    List<User> getUsers(String userId);

    @Update("UPDATE t_goods set good_num = #{goodNum},status = '已上架',upd_time = #{date} WHERE good_id = #{goodId}")
    Integer upGood(String goodId, Date date, Integer goodNum);

    @Update("UPDATE t_goods set good_num = #{goodNum} WHERE good_id = #{goodId}")
    Integer updateGoodNum(Integer goodNum);

    @Select("select cat_pid from t_category where cat_id = #{catId}")
    Integer getCatPid(Integer catId);

    @Delete("delete from t_user where userid = #{userId}")
    Integer deleteUser(String userId);

    @Delete("delete from t_order where user_id = #{userId}")
    int deleteOrder(String userId);

    @Delete("delete from t_orderitem where user_id = #{userId}")
    int deleteOrderItem(String userId);

    @Select("Select * from t_admin where adminId = #{adminId}")
    Admin getAdmin(String adminId);

    @Update("update t_admin set avatar = #{picUrl} where adminId = #{adminId}")
    Integer changeAvatar(String adminId, String picUrl);

    @Update("update t_admin set adminpwd = #{adminPwd} where adminId = #{adminId}")
    Integer changePassword(String adminId, String adminPwd);
}
