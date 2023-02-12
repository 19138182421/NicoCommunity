package com.example.nicocommunity.Service;

import com.example.nicocommunity.domain.Admin;
import com.example.nicocommunity.domain.User;

import java.util.List;
import java.util.Map;

/**
 * @author yang
 */
public interface AdminService {

    Admin findAdminById(String adminId);

    List<Admin> getAllAdmins();

    /**插入数据测试
     * */
    int insertAdmin( Admin admin);

    List<Map<String,Object>> getImg();

    /**获取首页轮播图信息*/
    List<Map<String,Object>>  getSwiperImg();

    /**获取用户登录信息*/
    User findUserById(Long userId);

    /**添加用户*/
    int addUser(User user);

    /**根据手机号判断是否注册*/
    User findUserByPhone(String userPhone);
}
