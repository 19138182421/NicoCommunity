package com.example.nicocommunity.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.example.nicocommunity.Service.AdminService;
import com.example.nicocommunity.domain.Admin;
import com.example.nicocommunity.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author yang
 * token工具类。用来生成token。
 */
@Service
public class TokenUtil {

    @Autowired
    private AdminService adminService;

    public String getToken(Admin admin){
        String token = "";
//        借助jwt生成token
        //withExpiresAt()表示设置token的有效时长，参数为失效时间的时间戳(毫秒数)，即生成的时间的时间戳 加 毫秒数即为失效的时间点。20*1000即为存活时间为20秒。
        token = JWT.create()
                .withAudience(String.valueOf(admin.getAdminId()))
                .withExpiresAt(new Date(System.currentTimeMillis() + (30 * 1000)))
                .sign(Algorithm.HMAC256(admin.getAdminPwd()));
        System.out.println("生成的token为："+token);
        return token;
    }

    public String parseToken(String token){
        String adminId;
        try {
            //解析token获取用户id
            adminId = JWT.decode(token).getAudience().get(0);
        } catch (JWTDecodeException j) {
            throw new RuntimeException("401：无权限");
        }
        //验证用户是否已经注册
        Admin admin = adminService.findAdminById(adminId);
        if (admin == null) {
            throw new RuntimeException("用户不存在，请先注册再重新登录");
        }
        return adminId;
    }


    public String getUserToken(User user) {
        String token = "";
//        借助jwt生成token
        //withExpiresAt()表示设置token的有效时长，参数为失效时间的时间戳(毫秒数)，即生成的时间的时间戳 加 毫秒数即为失效的时间点。20*1000即为存活时间为20秒。
        token = JWT.create()
                .withAudience(String.valueOf(user.getUserId()))
                .withExpiresAt(new Date(System.currentTimeMillis() + (24 * 60 * 60 * 1000)))
                .sign(Algorithm.HMAC256(user.getUserPhone()));
        System.out.println("生成的token为："+token);
        return token;
    }

    public String parseUserToken(String token){
        String userId;
        try {
            //解析token获取userId
            userId = JWT.decode(token).getAudience().get(0);
        } catch (JWTDecodeException j) {
            throw new RuntimeException("401：无权限yo");
        }
        //验证用户是否已经注册
        User user = adminService.findUserById(Long.valueOf(userId));
        if (user == null) {
            throw new RuntimeException("用户还未注册，先注册再重新登录");
        }
        return userId;
    }
}
