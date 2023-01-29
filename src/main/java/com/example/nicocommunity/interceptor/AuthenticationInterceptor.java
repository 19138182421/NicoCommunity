package com.example.nicocommunity.interceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.example.nicocommunity.Service.AdminService;
import com.example.nicocommunity.annotation.PassToken;
import com.example.nicocommunity.annotation.UserLoginToken;
import com.example.nicocommunity.domain.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @author yang
 * 配置拦截器。之后需要注册拦截器才能起作用。
 * 默认是所有的请求均无需验证。
 * 在拦截器里验证token是否存在、正确、过期。
 */
public class AuthenticationInterceptor implements HandlerInterceptor {

    @Autowired
    AdminService adminService;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws Exception {
        // 从 http 请求头中取出 token
        String token = httpServletRequest.getHeader("token");
//        System.out.println(token);
        // 如果不是映射到方法直接通过，就是@XXXMapping是否紧跟着方法。
        if(!(object instanceof HandlerMethod)){
            return true;
        }
        HandlerMethod handlerMethod=(HandlerMethod)object;
        Method method=handlerMethod.getMethod();
        //检查是否有Passtoken注解，有则跳过认证
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required()) {
                return true;
            }
        }
        //检查有没有需要UserLoginToken用户权限的注解
        if (method.isAnnotationPresent(UserLoginToken.class)) {
            UserLoginToken userLoginToken = method.getAnnotation(UserLoginToken.class);
            if (userLoginToken.required()) {
                // 执行认证
                //验证请求头中是否存在token
                if (token == null) {
                    throw new RuntimeException("还未登录，请先登录");
                }
                // 从请求头的token中利用JWT 解析获取 token 中的 userId值
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
                // 验证 token中用户的密码是否正确。
                //acceptExpiresAt()表示延续token的有效时长，单位为秒，类型为Long。
                JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(admin.getAdminPwd()))
                                              .acceptExpiresAt(10)
                                             .build();
                try {
                    jwtVerifier.verify(token);
                } catch(TokenExpiredException e){
                    throw  new RuntimeException("Token已过期，请重新登录");
                }
                catch (JWTVerificationException e) {
                    throw new RuntimeException("401");
                }
                //返回true表示token验证成功，开始响应用户请求。
                return true;
            }
        }
        return true;
    }


    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
