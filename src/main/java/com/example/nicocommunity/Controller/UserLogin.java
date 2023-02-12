package com.example.nicocommunity.Controller;

import com.alibaba.fastjson.JSONObject;
import com.example.nicocommunity.Service.AdminService;
import com.example.nicocommunity.annotation.UserLoginToken;
import com.example.nicocommunity.domain.User;
import com.example.nicocommunity.util.EncapsulatedUtil;
import com.example.nicocommunity.util.IdWorker;
import com.example.nicocommunity.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author yang
 */
@RestController
public class UserLogin {

    @Autowired
    private AdminService adminService;

    @Autowired
    private TokenUtil tokenUtil;

    @Autowired
    private EncapsulatedUtil encapsulatedUtil;

    /**用户一键登录，传过来user_name,pet_name,user_phone,sign_time*/
    /**先判断用户是否注册，未注册就填入数据库，并返回创建的token；如果已经注册就直接返回token*/
    @PostMapping("/userLogin")
    public Object userLogin(@RequestBody User user){

        JSONObject jsonObject = new JSONObject();

        User userResult = adminService.findUserByPhone(user.getUserPhone());

        if(userResult == null){
            /**生成用户id*/
//            IdWorker idWorker = new IdWorker(2, 3);
//            long id = idWorker.nextId();
            long random = new Double((Math.random() + 1) * Math.pow(10,7)).longValue();
            long id = Long.parseLong("1" + random);
            user.setUserId(id);

            user.setSignTime(new Date());

            /**插入用户*/
            int result = adminService.addUser(user);
            if(result == 1){
//                System.out.println(user.getUserId());
                /**返回token*/
                String token = tokenUtil.getUserToken(user);
                //返回token
                jsonObject.put("User",user);
                jsonObject.put("token",token);
                return encapsulatedUtil.encapsObject(jsonObject);
            }
        }else{
                //调用工具类生成token;
                String token = tokenUtil.getUserToken(userResult);
                //返回token
                jsonObject.put("User",userResult);
                jsonObject.put("token",token);
                return encapsulatedUtil.encapsObject(jsonObject);
        }
        jsonObject.put("error","服务器异常！");
        return jsonObject;
    }

    @UserLoginToken
    @GetMapping("/getsomething")
    public String getSom(HttpServletRequest httpServletRequest){
        String token = httpServletRequest.getHeader("token");

        return tokenUtil.parseUserToken(token);
    }
}
