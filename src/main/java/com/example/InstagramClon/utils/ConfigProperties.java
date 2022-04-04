package com.example.InstagramClon.utils;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@ConfigurationProperties(prefix = "mypath")
@Getter @Setter
public class ConfigProperties {
    private String imgsPath;
    private String imgsFormat;
}
