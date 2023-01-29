package com.example.nicocommunity.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.nicocommunity.domain.Admin;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author yang
 * token工具类。用来生成token。
 */
@Service
public class TokenUtil {
    public String getToken(Admin admin){
        String token = "";
//        借助jwt生成token
        //withExpiresAt()表示设置token的有效时长，参数为失效时间的时间戳(毫秒数)，即生成的时间的时间戳 加 毫秒数即为失效的时间点。20*1000即为存活时间为20秒。
        token = JWT.create()
                .withAudience(String.valueOf(admin.getAdminId()))
                .withExpiresAt(new Date(System.currentTimeMillis() + (20 * 1000)))
                .sign(Algorithm.HMAC256(admin.getAdminPwd()));
        System.out.println("生成的token为："+token);
        return token;
    }
}
