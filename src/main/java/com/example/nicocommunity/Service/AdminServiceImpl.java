package com.example.nicocommunity.Service;

import com.example.nicocommunity.Mapper.AdminMapper;
import com.example.nicocommunity.domain.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 1.注解@Service
 * 2.实现接口implements
 * 3.自动装配Mapper接口实现类以便创建对象来调用其方法
 * 4.重写Service接口方法
 * 5.调用mapper中的方法来访问数据库获取数据
 *
 * @author yang*/
@Service
public class AdminServiceImpl implements AdminService  {

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public Admin findAdminById(String adminId) {
        return adminMapper.findAdminById(adminId);
    }

    @Override
    public List<Admin> getAllAdmins() {
        return adminMapper.getAllAdmins();
    }

    @Override
    public int insertAdmin(Admin admin) {
        return adminMapper.insertAdmin(admin);
    }

    @Override
    public List<Map<String, Object>> getImg() {
        return adminMapper.getImg();
    }

    /**获取首页轮播图方法的实现*/
    @Override
    public List<Map<String, Object>> getSwiperImg() {
        return adminMapper.getSwiperImg();
    }




}
