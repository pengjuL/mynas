package com.pengju.imageuploadserver.controller;

import com.pengju.imageuploadserver.config.CustomConf;
import com.pengju.imageuploadserver.entry.requestData.SimpleData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

/**
 * @author pengju
 */
@RestController
@RequestMapping("/imageUploadServer")
public class ImagesUploadController {

    @Autowired
    CustomConf customConf;

    @GetMapping("/")
    public String hello(){
        System.out.println(customConf.getEnv());
        return "hello";
    }

    @PostMapping("/simpleUpload")
    public HashMap<String,Object> simpleUpload( SimpleData simpleData){


        HashMap<String,Object> resultData = new HashMap<>(2);

        MultipartFile image = simpleData.getImage();
        String location = customConf.getEnv().getBasePath();
        String originalName = image.getOriginalFilename();
        assert originalName != null;


        String newFilename = UUID.randomUUID()+originalName.substring(originalName.indexOf("."));
        String filePath = "/"+simpleData.getAccount()+simpleData.getPath()+"/"+newFilename;
        String url = customConf.getEnv().getBaseUrl()+filePath;
        File pretreatmentFile = new File(location+filePath);

        if (!pretreatmentFile.exists()){
            pretreatmentFile.mkdirs();
        }
        try {
            image.transferTo(pretreatmentFile);
            resultData.put("url",url);
            resultData.put("result",true);
            resultData.put("message","image has been uploaded");
        } catch (IOException e) {
            e.printStackTrace();
            resultData.put("result",false);
            resultData.put("message",e.getMessage());
            return resultData;
        }

        return resultData;


    }

}
