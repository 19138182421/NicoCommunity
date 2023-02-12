package com.example.nicocommunity.Mapper;

import com.example.nicocommunity.domain.Admin;
import com.example.nicocommunity.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author yang
 */
@Mapper
@Repository
public interface AdminMapper {

    @Select("SELECT * FROM `t_admin` where adminId = #{adminId}")
    Admin findAdminById(String adminId);

    @Select("select * from t_admin")
    List<Admin> getAllAdmins();

    /**插入数据测试*/
    @Insert("insert into t_admin values (#{adminId},#{adminName},#{adminPwd},#{add_time})")
    int insertAdmin(Admin admin);

    @Select("SELECT * FROM `gooditem`")
    List<Map<String,Object>> getImg();

    /**获取首页轮播图信息
     * Map中的Object存放查找到的每一行的数据。一个{String,Object}对应一行数据。
     * List存放Map
     * */
    @Select("SELECT * FROM `t_swiper`")
    List<Map<String,Object>> getSwiperImg();


    @Select("select * from t_user where userid = #{userId}")
    User findUserById(Long userId);

    /**插入新用户*/
    @Insert("INSERT INTO t_user VALUES (#{userId},#{userName},#{petName},#{userPhone},#{signTime})")
    int addUser(User user);

    /**根据手机号判断是否注册*/
    @Select("SELECT * FROM `t_user` where userPhone = #{userPhone}")
    User findUserByPhone(String userPhone);


}
