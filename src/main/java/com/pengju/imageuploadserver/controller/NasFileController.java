package com.pengju.imageuploadserver.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pengju.imageuploadserver.bean.NasFile;
import com.pengju.imageuploadserver.bean.User;
import com.pengju.imageuploadserver.config.CustomConf;
import com.pengju.imageuploadserver.service.NasFileService;
import com.pengju.imageuploadserver.service.UserService;
import com.pengju.imageuploadserver.utils.FileTypeDeterminer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.File;
import java.util.List;
import java.util.UUID;

/**
 * @author pengju
 */
@RestController
@RequestMapping("/file")
public class NasFileController {

    @Autowired
    CustomConf customConf;
    @Autowired
    NasFileService nasFileService;
    @Autowired
    UserService userService;

    /**
     * 用户登录方法
     *         {
     *             id:'30000004',
     *             name:'学习资料',
     *             fileType:'folder'
     *         },
     */
    @PostMapping("/files")
    public HashMap<String,Object> getFilesInFolder(@RequestBody HashMap<String,Object> req){
        HashMap<String,Object> resultMap = new HashMap<>(2);
        String currentPath = req.get("path").toString();
        String userId = req.get("userId").toString();
        boolean result = true;
        String filepath = customConf.getEnv().getBasePath() + userId +currentPath;

        File directory = new File(filepath);
        ArrayList<NasFile> nasFiles = new ArrayList<>();
        // 物理结果
        if (directory.exists() && directory.isDirectory()){

            File[] files = directory.listFiles();
            if (files != null){
                for (File file : files) {

                    NasFile nasFile = new NasFile();
                    nasFile.setFilename(file.getName());
                    nasFile.setId("111111");
                    nasFile.setFileType(FileTypeDeterminer.getFileType(file));
                    nasFile.setPath(currentPath);
                    nasFiles.add(nasFile);
                }
            }
        }
        // 逻辑结果
        QueryWrapper<NasFile> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        queryWrapper.eq("path",currentPath);

        List<NasFile> nasFileList = nasFileService.list(queryWrapper);
        nasFileList.forEach(nasFile -> {
            nasFile.setStorageName("****");
            nasFile.setUserId("****");
        });
        resultMap.put("result",result);
        resultMap.put("file_list", nasFileList);


        return resultMap;
    }

    @PostMapping("/add")
    public HashMap<String,Object> add(@RequestParam("file") MultipartFile file,
                                      @RequestParam("fileType") String fileType,
                                      @RequestParam("path") String path,
                                      @RequestParam("userId") String userId){
        HashMap<String,Object> result = new HashMap<>();
        /*
         * 逻辑：
         * 1、新增类型检查（文件夹、文件）
         * 2、新增文件、新增文件夹
         */


        result.put("result",false);
//      准备一个路径
        String truthPath = customConf.getEnv().getBasePath() + userId + path;
        String fileName = file.getOriginalFilename();
//        是否为一个文件夹，是：在对应路径新建文件夹
        if ("folder".equals(fileType)){
//            新增文件夹逻辑
            File newFile = new File(truthPath + fileName);
            if (!newFile.exists()){
                if (newFile.mkdirs()){
                    result.put("result",true);
                    NasFile nasFile = new NasFile();
                    nasFile.setFilename(fileName);
                    nasFile.setFileType("folder");
                    nasFile.setStorageName(fileName);
                    nasFile.setPath(path);
                    nasFile.setUserId(userId);
                    nasFile.setSize(0L);
                    nasFileService.save(nasFile);
                }else {
                    result.put("mes","系统错误");
                }
            }else {
                result.put("mes","文件已存在");
            }
        }

        else {
            /*
             * 新增文件逻辑：
             * 1、用户空间检查
             * 2、文件新增
             */
            long fileSize = file.getSize()/1024;
//            检查用户是否还有空间
            User user = userService.getById(userId);
            if (user.getMaxVolume() - user.getVolume() < fileSize){
                result.put("result", false);
                result.put("mes", "剩余空间不足");

            }
            else {
                String storageName = UUID.randomUUID()+fileName.substring(fileName.indexOf("."));
                try {
                    file.transferTo(new File(truthPath + storageName));
                    user.setVolume(user.getVolume()+fileSize);
                    userService.updateById(user);
                    NasFile nasFile = new NasFile();
                    nasFile.setFilename(fileName);
                    nasFile.setFileType(FileTypeDeterminer.getFileType(fileName));
                    nasFile.setStorageName(storageName);
                    nasFile.setPath(path);
                    nasFile.setSize(fileSize);
                    nasFile.setUserId(userId);
                    nasFileService.save(nasFile);

                    result.put("result", true);
                } catch (IOException e) {
                    e.printStackTrace();
                    result.put("result", false);
                    result.put("mes", "系统错误");
                }
            }

        }

        return result;
    }


    @GetMapping("/downloadFile")
    public ResponseEntity<Resource> downloadFile(@RequestParam String fileId) {
        try {
            NasFile file = nasFileService.getById(fileId);

            Path filePath = Paths.get(customConf.getEnv().getBasePath()+file.getUserId()+file.getPath()+file.
                    getStorageName()).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                String contentType = Files.probeContentType(filePath);
                if (contentType == null){
                    contentType = "application/octet-stream";
                }
                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(contentType))
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
}
