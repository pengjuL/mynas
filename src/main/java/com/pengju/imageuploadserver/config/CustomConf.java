package com.pengju.imageuploadserver.config;

import com.pengju.imageuploadserver.bean.confBean.Env;
import com.pengju.imageuploadserver.bean.confBean.ParameterRule;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author pengju
 */

@Data
@Configuration
@ConfigurationProperties(prefix = "spring.custom")
public class CustomConf {
    private Env env;
    private ParameterRule paramRule;
}

