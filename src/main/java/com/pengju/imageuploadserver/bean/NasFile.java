package com.pengju.imageuploadserver.bean;

import com.baomidou.mybatisplus.annotation.TableField;
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
public class NasFile {

    private String id;
    private String filename;
    private String storageName;
    private String fileType;
    @TableField(exist = false)
    private MultipartFile file;
    private Long size;
    private String path;
    private String userId;

}


