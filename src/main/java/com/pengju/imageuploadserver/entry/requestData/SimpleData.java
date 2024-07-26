package com.pengju.imageuploadserver.entry.requestData;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Setter
@Getter
@ToString
public class SimpleData {
    private MultipartFile image;
    private String account;
    private String password;
    private String path;
}
