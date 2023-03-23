package com.example.nicocommunity.Adminapi;

import com.example.nicocommunity.domain.Admin;
import com.example.nicocommunity.domain.Good;
import com.example.nicocommunity.domain.Order;
import com.example.nicocommunity.domain.User;
import com.example.nicocommunity.util.EncapsulatedUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author yang
 */
@Service
public class MisServiceImpl implements MisSerivce{

    @Autowired
    private MisMapper misMapper;

    @Autowired
    private EncapsulatedUtil encapsulatedUtil;

    @Override
    public String getTodayCount(String  currentDate) {
        return misMapper.getTodayCount(currentDate);
    }

    @Override
    public String getMonthVolume(String currentMonth) {
        return misMapper.getMonthVolume(currentMonth);
    }

    @Override
    public String getYearVolume(String currentYear) {
        return misMapper.getYearVolume(currentYear);
    }

    @Override
    public String getOrderCountByStatus(String currentYear,String status) {

        return misMapper.getOrderCountByStatus(currentYear,status);
    }

    @Override
    public List<String> getVoulume(String year) {
        List<String> list = new ArrayList<String>();
        list.add("01");
        list.add("02");
        list.add("03");
        list.add("04");
        list.add("05");
        list.add("06");
        list.add("07");
        list.add("08");
        list.add("09");
        list.add("10");
        list.add("11");
        list.add("12");

        List<String> volume = new ArrayList<String>();
        list.forEach(e -> {
            String date = '%'+year+'-'+e+'%';
            if(misMapper.getvolumeByMonth(date) == null){
                volume.add("0");
            }else{
                volume.add(misMapper.getvolumeByMonth(date));
            }
        });
        return volume;
    }

    @Override
    public List<Order> getOrders(String date) {
        return misMapper.getOrders(date);
    }

    @Override
    public List<Order> getAllOrders(String orderId,String userId,String orderNumber,String payStatus) {
        return misMapper.getAllOrders(orderId,userId,orderNumber,payStatus);
    }

    @Override
    public List<Order> getOrderByOrderId(String orderId) {
        return misMapper.getOrderByOrderId(orderId);
    }

    @Override
    public Integer modifyOrder(String updateTime, String orderId, String receiverName, String receiverPhone, String orderAddress,String payStatus) {

        return misMapper.modifyOrder(updateTime,orderId,receiverName,receiverPhone,orderAddress,payStatus);
    }

    @Override
    public Integer addCategory(String catId, String catName, String catPid, String catImgsrc, String navImgsrc, String createTime) {
        return misMapper.addCategory(catId,catName,catPid,catImgsrc,navImgsrc,createTime);
    }

    @Override
    public Integer modifyCategory(String catId, String catName, String catImgsrc, String navImgsrc) {
        return misMapper.modifyCategory(catId,catName,catImgsrc,navImgsrc);
    }

    @Override
    public Integer deleteCategory(String catId) {
        return misMapper.deleteCategory(catId);
    }

    @Override
    public Integer selectCategory(String catId) {
        return misMapper.selectCategory(catId);
    }

    @Override
    public List<Map<String, Object>> getGoodTps(String goodId) {
        return misMapper.getTpByGoodId(goodId);
    }

    @Override
    public Integer addTpByGoodId(String goodId, String tpName, String tpPrice, String tpImgSrc) {
        return misMapper.addTpByGoodId(goodId,tpName,tpPrice,tpImgSrc);
    }

    @Override
    public Integer modifyTpByGoodId(String tpName, String tpPrice, String tpImgSrc, String tpId, String goodId) {
        return misMapper.modifyTpByGoodId(tpName,tpPrice,tpImgSrc,tpId,goodId);
    }

    @Override
    public Integer deleteTpByGoodId(String tpId, String goodId) {
        return misMapper.deleteTpByGoodId(tpId,goodId);
    }

    @Override
    public List<Map<String, Object>> getSwipersByGoodId(String goodId) {
        return misMapper.getSwipersByGoodId(goodId);
    }

    @Override
    public Integer addSwiperByGoodId(String goodId, String picUrl) {
        return misMapper.addSwiperByGoodId(goodId,picUrl);
    }

    @Override
    public Integer modifySwiperByGoodId(String picUrl, String goodId, String picId) {
        return misMapper.modifySwiperByGoodId(picUrl,goodId,picId);
    }

    @Override
    public Integer deleteSwiperByGoodId(String picId, String goodId) {
        return misMapper.deleteSwiperByGoodId(picId,goodId);
    }

    @Override
    public List<Good> getAllgoods(String goodId, String catId, String status) {
//
        return misMapper.getAllgoods(goodId, catId, status);
    }

    @Override
    public Integer addGood(Good good) {

        Integer catOneId = misMapper.getCatPid(good.getCat_id());
        good.setCat_one_id(catOneId);

        return misMapper.addGood(good);
    }

    @Override
    public Integer modifygoods(Good good) {
        return misMapper.modifygoods(good);
    }

    @Override
    public Integer deleteGood(String goodId) {
        return misMapper.deleteGood(goodId);
    }

    @Override
    public List<User> getUsers(String userId) {
        return misMapper.getUsers(userId);
    }

    @Override
    public String getOrderCounts(String currentYear) {
        return misMapper.getOrderCounts(currentYear);
    }

    @Override
    public Integer downGood(String goodId) {
        Date date = new Date();

        return misMapper.downGood(goodId,date);
    }

    @Override
    public Integer upGood(String goodId,Integer goodNum) {
        Date date = new Date();

        return misMapper.upGood(goodId,date,goodNum);
    }

    @Override
    public Integer deleteUser(String userId) {
        if(misMapper.deleteUser(userId) == 0){
            return 0;
        } else {
            misMapper.deleteOrder(userId);
            misMapper.deleteOrderItem(userId);
            return 1;
        }

    }

    @Override
    public Admin getAdmin(String adminId) {
        return misMapper.getAdmin(adminId);
    }

    @Override
    public Integer changeAvatar(String adminId, String picUrl) {
        return misMapper.changeAvatar(adminId,picUrl);
    }

    @Override
    public Integer changePassword(String adminId, String adminPwd) {
        return misMapper.changePassword(adminId,adminPwd);
    }
}
