package com.example.nicocommunity.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author yang
 */
@RestController
public class Hello {
    @GetMapping("/hello")
    public String helloSpringboot(){
        return "hello啊,springboot";
    }
//    public String helloSpringboot(@RequestHeader("userid") String userid){
//        String userId = userid;
//        System.out.println(userid);
//        return userId;
//    }

    @RestController
    public class JdbcController {
        @Autowired
        private JdbcTemplate jdbcTemplate;
        @GetMapping("/jdbc")
        public List<Map<String, Object>> query(){
            String sql="select * from t_user";
            List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql);
            return maps;
        }
    }
}
