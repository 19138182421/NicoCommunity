package com.example.nicocommunity.Controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.nicocommunity.Service.AdminService;
import com.example.nicocommunity.annotation.PassToken;
import com.example.nicocommunity.annotation.UserLoginToken;
import com.example.nicocommunity.domain.Admin;
import com.example.nicocommunity.util.TokenUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**Controller层书写规范
 *1.@RequestMapping("/dee")注解 即url路径
 *2.@Controller注解标识类为控制类
 *3.@Autowried注解自动装配Service层接口实现类
 *4.@RequestMapping(value = "/query",method = RequestMethod.POST)注解 即进一步url路径
 *5.@ResponseBody注解标识请求体
 * @author yang
 */
@Controller
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private TokenUtil tokenUtil;

    //验证token是否生效
    /**
     * 用户登录验证请求  无需token验证且需要返回token。
     * */
    @PostMapping("/login")
    @ResponseBody
    public Object login(@RequestBody Admin admin){

        JSONObject jsonObject = new JSONObject();
        //通过前端发来的用户i判断用户是否注册
        Admin adminResult = adminService.findAdminById(String.valueOf(admin.getAdminId()));
        if(adminResult==null){
            System.out.println("1");
            jsonObject.put("message","用户不存在");
            return jsonObject;
        }else{
            if(!adminResult.getAdminPwd().equals(admin.getAdminPwd())){
                jsonObject.put("message","登录失败，密码错误");
                return jsonObject;
            }else{
                //密码验证通过，调用工具类生成token;
                String token = tokenUtil.getToken(admin);
                //返回token
                jsonObject.put("admin",admin);
                jsonObject.put("token",token);
                return jsonObject;
            }

        }
    }
    /**用户请求数据请求  需要验证token后才能响应请求并返回数据。*/
//    @UserLoginToken  //需要验证
    @PassToken     //无需验证
    @GetMapping("/getMessage")
    @ResponseBody
    public Object getMessage(){
        //从数据库获取数据
        List<Map<String,Object>> list = adminService.getSwiperImg();

        //封装数据发给前端。
        JSONObject jsonObject = new JSONObject();
        JSONArray message = new JSONArray();
        message.addAll(list);
        //meta
        JSONObject meta = new JSONObject();
        meta.put("msg","获取成功");
        meta.put("status",200);
        jsonObject.put("message",message);
        jsonObject.put("meta",meta);
        return jsonObject;
    }

    @PostMapping("/query")
    @ResponseBody
    public String getAllAdmin(@RequestParam("adminname") String adminName){
        List<Admin> list = adminService.getAllAdmins();
        System.out.println(list.get(0).getAdd_time());
        return JSON.toJSONString(list);
//        return "asdjkfsad";
    }

    @PostMapping("/insert")
    @ResponseBody
    public String insertAdmin(@RequestBody Admin admin){
        int result = adminService.insertAdmin(admin);
        return JSON.toJSONString("ok");
    }


    @RequestMapping("/test")
    @ResponseBody
    public String getImg(@RequestParam(value = "pageNum",required = false,defaultValue = "1")Integer pageNum, Model model){
        System.out.println(pageNum);
        /**
         * 只有紧跟下面一行代码的查询语句会分页
         * */
        PageHelper.startPage(pageNum, 5);
        List<Map<String,Object>> list = adminService.getImg();
        PageInfo pageInfo = new PageInfo(list);
        System.out.println(pageInfo);
         return JSON.toJSONString(pageInfo);
    }


}
