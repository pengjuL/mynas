package com.pengju.imageuploadserver.entry.requestData;

import lombok.Data;

/**
 * @author pengju
 */
@Data
public class RegisterData {
    private String account;
    private String password;
    private String nickname;
    private String rePassword;
}
