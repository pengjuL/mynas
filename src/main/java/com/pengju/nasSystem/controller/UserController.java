package com.pengju.nasSystem.controller;

import com.pengju.nasSystem.bean.User;
import com.pengju.nasSystem.config.CustomConf;
import com.pengju.nasSystem.entry.requestData.RegisterData;
import com.pengju.nasSystem.service.UserService;
import com.pengju.nasSystem.utils.CharUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.HashMap;

/**
 * @author pengju
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    CustomConf customConf;
    @Autowired
    UserService userService;

    @GetMapping("/test")
    public User test(){
        return userService.getByAccount("ikier");
    }

    /**
     * 账号唯一验证方法
     * 当输入账号不存在于数据库时，即验证通过，表示该账号可用。
     * @param user 用户对象，用于接收账号信息（单纯使用String接收参数，会将前端传来的所有内容当成字符串进行接收）
     * @return 返回一个int值，用于前端展示
     *         0输入内容为空，1账号格式有误，2、已占用，3、可用
     */
    @PostMapping("accountUniqueVerify")
    public HashMap<String, Integer> accountUniqueVerify(@RequestBody User user){
        String account = user.getAccount();
        System.out.println(account);
        HashMap<String, Integer> resultMap = new HashMap<>(1);
        int result;
        int accountLimit = customConf.getParamRule().getAccountLimit();
        /*1、判断账号内容够是否为空*/
        if (account==null||"".equals(account)){
           result = 0;
        }else if(account.length() > accountLimit||CharUtils.hasChinese(account)) {
            result = 1;
        }else if(userService.getByAccount(account)!=null){
            result = 2;
        }else {
            result = 3;
        }
        resultMap.put("result",result);
        return resultMap;
    }


    /**
     * 用户注册方法
     * @param registerData 用户注册信息
     * @return 返回结果值和信息
     */
    @PostMapping("/register")
    public HashMap<String,Object> register(@RequestBody RegisterData registerData){
        HashMap<String,Object> resultMap = new HashMap<>(2);
        boolean result = false;
        String message;

        if (registerData.getPassword().equals(registerData.getRePassword())){
            User user = new User();
            user.setAccount(registerData.getAccount());
            user.setNickname(registerData.getNickname());
            user.setPassword(registerData.getPassword());
            user.setMaxVolume(10240000);
            user.setVolume(0L);
            userService.save(user);
            ;
            File file = new File(customConf.getEnv().getBasePath()+user.getId());
            if (!file.exists()){
                file.mkdirs();
            }
            result=true;
            message = "注册成功";
        }else {
            message = "两次密码不一致";
        }
        resultMap.put("result",result);
        resultMap.put("message",message);

        return resultMap;
    }

    /**
     * 用户登录方法
     * @param user 用户独享，用于接收用户登录参数（账号和密码）
     * @return 返回一个登录结果
     */
    @PostMapping("/login")
    public HashMap<String,Object> login(@RequestBody User user){
        HashMap<String,Object> resultMap = new HashMap<>(2);
        boolean result = false;

        User byAccount = userService.getByAccount(user.getAccount());

        if (byAccount != null&&byAccount.getPassword().equals(user.getPassword())){
            result = true;
            byAccount.setPassword("******");
        }
        resultMap.put("result",result);
        resultMap.put("user",byAccount);



        return resultMap;
    }
}
