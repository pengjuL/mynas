package com.pengju.nasSystem.entry.requestData;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
@ToString
public class SimpleData {
    private MultipartFile image;
    private String account;
    private String password;
    private String path;
}
