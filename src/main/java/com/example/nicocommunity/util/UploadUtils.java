package com.example.nicocommunity.util;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @author yang
 */
@Service
public class UploadUtils {
    //图片上传位置，静态资源文件夹文件存储路径
//    private static final String BASE_PATH = "D:\\NicoCommunity\\src\\main\\resources\\static\\images\\";
    private static final String BASE_PATH = "/uploadedImg/";

    //访问路径
    //在浏览器上访问该路径可以看到图片，ip和端口号可以根据自身需求设置
//    private static final String ACCESS_PATH = "http://localhost:8080/images/";
    private static final String ACCESS_PATH = "http://8.130.64.111:8800/images/";
//
    /**
     * 图片上传
     * @param file
     * @return 访问图片的网址
     */
    public String upload( MultipartFile file){

        //获取图片的原始名称
        String fileName = file.getOriginalFilename();
        //判断文件类型是否符合要求
        if(!fileName.endsWith(".png")) {
            return "文件类型错误";
        }
        //生成新名称
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        String newFileName = UUID.randomUUID()+suffixName;

        File dest =new File(BASE_PATH+newFileName);
//        System.out.println(dest);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String fileUrl = ACCESS_PATH + newFileName;

        return fileUrl;
    }

}
