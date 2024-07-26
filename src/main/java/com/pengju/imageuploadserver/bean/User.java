package com.pengju.imageuploadserver.bean;

import lombok.Data;
import lombok.ToString;

/**
 * @author pengju
 */
@Data
@ToString
public class User {
    private String id;
    private String account;
    private String password;
    private String nickname;
    private int isVip;
    private long volume;
    private long maxVolume;
}
