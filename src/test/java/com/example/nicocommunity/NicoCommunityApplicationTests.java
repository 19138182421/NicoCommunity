package com.example.nicocommunity;

import com.example.nicocommunity.util.IdWorker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.SQLException;


@SpringBootTest
class NicoCommunityApplicationTests {


    @Test
    void contextLoads() {
       IdWorker idWorker = new IdWorker(2, 3);
            long id = idWorker.nextId();
            System.out.println(id);
        long id1 = idWorker.nextId();
        System.out.println(id1);
    }

    //数据源组件
    @Autowired
    DataSource dataSource;
    //用于访问数据库的组件
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Test
    void contextLoad() throws SQLException {
        System.out.println("默认数据源为：" + dataSource.getClass());
        System.out.println("数据库连接实例：" + dataSource.getConnection());
        //访问数据库
        Integer i = jdbcTemplate.queryForObject("SELECT count(*) from t_admin", Integer.class);
        System.out.println("user 表中共有" + i + "条数据。");
    }
//    @Autowired
//    private Hello hello;
//    @Test
//    public void helloTest(){
//        String h = hello.helloSpringboot();
//        System.out.print(h);
//
//    }


}
