package com.example.nicocommunity.interceptor;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author yang
 * 全局异常捕获。自定义返回的异常提示
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public Object handlerException(Exception e){
        String msg = e.getMessage();
        if (msg == null || msg.equals("")) {
            msg = "服务器异常sd";
        }
        JSONObject jsonObject = new JSONObject();
        JSONObject meta = new JSONObject();
        meta.put("msg","获取信息失败！");
        meta.put("status",400);
        jsonObject.put("meta",meta);
        jsonObject.put("message", msg);
        return jsonObject;
    }
}
