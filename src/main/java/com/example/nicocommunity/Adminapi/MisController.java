package com.example.nicocommunity.Adminapi;

import com.alibaba.fastjson.JSONObject;
import com.example.nicocommunity.Service.Category.CategoryService;
import com.example.nicocommunity.domain.Admin;
import com.example.nicocommunity.domain.Order;
import com.example.nicocommunity.domain.User;
import com.example.nicocommunity.util.CodeUtil;
import com.example.nicocommunity.util.EncapsulatedUtil;
import com.example.nicocommunity.util.UploadUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author yang
 */
@RestController
public class MisController {

    @Autowired
    private MisSerivce misSerivce;

    @Autowired
    private EncapsulatedUtil encapsulatedUtil;

    @Autowired
    private UploadUtils uploadUtils;

    @Autowired
    private CodeUtil codeUtil;

    public static final Integer CATLISTLENGTH = 2;

//    首页接口

    /**
     * 首页需要的接口数据 今日订单数、月成交额、年成交额
     */
    @GetMapping("/getHomeData")
    public Object getHomeData() {

        /**获取当前时间*/
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM");
        SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy");

        String currentDate = '%' + formatter.format(date) + '%';
        String currentMonth = '%' + formatter1.format(date) + '%';
        String currentYear = '%' + formatter2.format(date) + '%';

        /**今日的订单数 参数为当前年月日 时间格式化为yyyy-mm-dd*/
        String count = misSerivce.getTodayCount(currentDate);
        /**当前月份的成交额  参数为当前年份和月份 时间格式化为yyyy-mm*/
        String monthVolume = misSerivce.getMonthVolume(currentMonth);

        /**某一年的成交额 参数为当前年份 时间格式化为yyyy*/
        String yearVolume = misSerivce.getYearVolume(currentYear);


        /**当前年份的已完成订单数、待付款订单数、退换货订单数、订单总量*/
        String allCount = misSerivce.getOrderCounts(currentYear);
        String finished = misSerivce.getOrderCountByStatus(currentYear, "交易完成");
        String waitPay = misSerivce.getOrderCountByStatus(currentYear, "待付款");
        String backOrChange = misSerivce.getOrderCountByStatus(currentYear, "退换货");

        JSONObject obj = new JSONObject();
        obj.put("count", count);
        obj.put("monthVolume", monthVolume);
        obj.put("yearVolume", yearVolume);
        obj.put("allCount", allCount);
        obj.put("finished", finished);
        obj.put("waitPay", waitPay);
        obj.put("backOrChange", backOrChange);

        return encapsulatedUtil.encapsObject(obj);
    }

    /**
     * 获取某一年每个月份的成交额
     */
    @GetMapping("/getvolume")
    public Object getVolume(@RequestParam(value = "year") String year) {

        List<String> list = misSerivce.getVoulume(year);

        JSONObject obj = new JSONObject();
        obj.put("list", list);
        return encapsulatedUtil.encapsObject(obj);
    }

//    订单管理接口

    /**
     * 获取所有订单  分页且可以根据传入的排序字段进行排序
     */
    @GetMapping("/getOrders")
    public Object getOrders(@RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                            @RequestParam(value = "pageSize", required = false, defaultValue = "8") Integer pageSize,
                            @RequestParam(value = "orderField", required = false, defaultValue = "create_time") String orderField,
                            @RequestParam(value = "mode", required = false, defaultValue = "asc") String mode,
                            @RequestParam(value = "orderId", required = false) String orderId,
                            @RequestParam(value = "userId", required = false) String userId,
                            @RequestParam(value = "orderNumber", required = false) String orderNumber,
                            @RequestParam(value = "payStatus", required = false) String payStatus) {

        if (Objects.equals(orderId, "")) {
            orderId = null;
        }
        if (Objects.equals(userId, "")) {
            userId = null;
        }
        if (Objects.equals(orderNumber, "")) {
            orderNumber = null;
        }
        if (Objects.equals(payStatus, "")) {
            payStatus = null;
        }


        String orderBy = orderField + " " + mode;
        PageHelper.startPage(pageNum, pageSize, orderBy);
        List<Order> list = misSerivce.getAllOrders(orderId, userId, orderNumber, payStatus);
        PageInfo pageInfo = new PageInfo(list);

        return encapsulatedUtil.encapsObject(pageInfo);
    }

    /**
     * 根据订单号搜索指定订单
     */
    @GetMapping("/getOrderByOrderId")
    public Object getOrderByOrderId(@RequestParam(value = "orderId") String orderId) {

        List<Order> list = misSerivce.getOrderByOrderId(orderId);

        JSONObject obj = new JSONObject();
        obj.put("list", list);
        return encapsulatedUtil.encapsObject(obj);
    }

    /**
     * 修改订单地址
     */
    @GetMapping("/modifyOrder")
    public Object modifyOrderAddress(@RequestParam(value = "orderId") String orderId,
                                     @RequestParam(value = "receiverName") String receiverName,
                                     @RequestParam(value = "receiverPhone") String receiverPhone,
                                     @RequestParam(value = "orderAddress") String orderAddress,
                                     @RequestParam(value = "payStatus") String payStatus) {

        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String updateTime = formatter.format(date);

        Integer affectRow = misSerivce.modifyOrder(updateTime, orderId, receiverName, receiverPhone, orderAddress, payStatus);

        return encapsulatedUtil.encapsObject(affectRow);
    }

    /**  分类管理接口*/
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/getAllCategory")
    public Object getAllCategory() {

        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String updateTime = formatter.format(date);

        List<Map<String, Object>> list = categoryService.getCatItems(0);

        JSONObject obj = new JSONObject();
        return encapsulatedUtil.encapsObject(list);
    }

    /**
     * 根据catId获取某一个分类的基本信息
     */
    @GetMapping("/getCategoryById")
    public Object getCategoryById(@RequestParam(value = "catId") String catId) {

        List<Map<String, Object>> list = categoryService.getCategoryById(catId);

        JSONObject obj = new JSONObject();
        return encapsulatedUtil.encapsObject(list);
    }

    /**
     * 新增分类
     */
    @GetMapping("/addCategory")
    public Object addCategory(@RequestParam(value = "catId") String catId,
                              @RequestParam(value = "catName") String catName,
                              @RequestParam(value = "catPid", defaultValue = "0") String catPid,
                              @RequestParam(value = "catImgsrc", defaultValue = " ") String catImgsrc,
                              @RequestParam(value = "navImgsrc", defaultValue = " ") String navImgsrc) {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createTime = formatter.format(date);

        Integer isHave = misSerivce.selectCategory(catId);
        Integer affectRow = 0;
        if (isHave == 0) {
            affectRow = misSerivce.addCategory(catId, catName, catPid, catImgsrc, navImgsrc, createTime);
        }

        return encapsulatedUtil.encapsObject(affectRow);
    }

    /**
     * 修改分类图片或名称
     */
    @PostMapping("/modifyCategory")
    public Object modifyCategory(@RequestParam(value = "catId") String catId,
                                 @RequestParam(value = "catName") String catName,
                                 MultipartFile[] fileList) {

        String catImgsrc = uploadUtils.upload(fileList[0]);
        String navImgsrc = "";
        if (fileList.length == CATLISTLENGTH) {
            navImgsrc = uploadUtils.upload(fileList[1]);
        }
        Integer affectRow = misSerivce.modifyCategory(catId, catName, catImgsrc, navImgsrc);

        return encapsulatedUtil.encapsObject(affectRow);
    }

    /**
     * 删除分类
     */
    @GetMapping("/deleteCategory")
    public Object deleteCategory(@RequestParam(value = "catId") String catId) {

        Integer affectRow = misSerivce.deleteCategory(catId);

        JSONObject obj = new JSONObject();
        return encapsulatedUtil.encapsObject(affectRow);
    }


    @GetMapping("/getUsers")
    public Object getUsers(@RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                           @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                           @RequestParam(value = "userField", required = false, defaultValue = "userid") String userField,
                           @RequestParam(value = "mode", required = false, defaultValue = "asc") String mode,
                           @RequestParam(value = "userId", required = false) String userId) {

        if (Objects.equals(userId, "")) {
            userId = null;
        }
        String orderBy = userField + " " + mode;
        PageHelper.startPage(pageNum, pageSize, orderBy);
        List<User> list = misSerivce.getUsers(userId);
        PageInfo pageInfo = new PageInfo(list);

        return encapsulatedUtil.encapsObject(pageInfo);
    }

    @GetMapping("/logout")
    public Object deleteUser(@RequestParam("userId") String userId) {

        Integer affectRow = misSerivce.deleteUser(userId);

        return encapsulatedUtil.encapsObject(affectRow);
    }

    @GetMapping("/adminLogin")
    public Object adminLogin(@RequestParam("adminId") String adminId,
                             @RequestParam("adminpwd") String adminpwd) {

        Admin admin = misSerivce.getAdmin(adminId);
        if(admin == null){
            return encapsulatedUtil.encapsObject("此管理员信息尚不存在！请联系负责人员！");
        }else{
            if(Objects.equals(admin.getAdminPwd(), adminpwd)){
                return encapsulatedUtil.encapsObject(admin);
            }else{
               return encapsulatedUtil.encapsObject("密码错误！请重新输入！");

            }
        }
    }

    @PostMapping("/changeAvatar")
    public Object changeAvatar(@RequestParam("adminId") String adminId,
                               MultipartFile file) {

        String picUrl = uploadUtils.upload(file);
        if(Objects.equals(picUrl, "文件类型错误")){
            return encapsulatedUtil.encapsObject(picUrl);
        }

        Integer affectRow = misSerivce.changeAvatar(adminId,picUrl);

        return encapsulatedUtil.encapsObject(picUrl);

    }

    @GetMapping("/changepassword")
    public Object changePassword(@RequestParam("adminId") String adminId,
                                 @RequestParam("adminPwd") String adminPwd) {

        Admin admin = misSerivce.getAdmin(adminId);
        if(admin == null){
            return encapsulatedUtil.encapsObject("此管理员信息尚不存在！请联系负责人员！");
        }else{
            Integer affectRow = misSerivce.changePassword(adminId,adminPwd);
            return encapsulatedUtil.encapsObject(affectRow);
        }
    }

    @GetMapping("/getCode")
    public Object getCode(@RequestParam("adminId") String adminId) throws Exception {

        Admin admin = misSerivce.getAdmin(adminId);
        if(admin == null){
            return encapsulatedUtil.encapsObject("此管理员信息尚不存在！请联系负责人员！");
        }else{
            //返回验证码
            try{
                String code =  codeUtil.msgInterface(adminId);
                return encapsulatedUtil.encapsObject(code);
            }catch(Exception e){
                return encapsulatedUtil.encapsObject(e);
            }
        }
    }






}
