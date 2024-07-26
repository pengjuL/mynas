package com.pengju.nasSystem.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author pengju
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TestFile {

    private String id;
    private String fileName;
    private String storageName;
    private String fileType;
    private MultipartFile file;
    private String path;

    public TestFile(String fileName, String storageName, String fileType, MultipartFile file, String path) {
        this.fileName = fileName;
        this.storageName = storageName;
        this.fileType = fileType;
        this.file = file;
        this.path = path;
    }

}


