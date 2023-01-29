package com.example.nicocommunity.Service;

import com.example.nicocommunity.domain.Admin;

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

}
