package com.example.nicocommunity.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author yang
 */
@Service
public class EncapsulatedUtil {
    public Object encapsData(List<Map<String,Object>> list){
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
}
