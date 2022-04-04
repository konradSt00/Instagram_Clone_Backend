package com.example.InstagramClon;

import com.example.InstagramClon.utils.ConfigProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableConfigurationProperties(ConfigProperties.class)
@EnableSwagger2
public class InstagramClonApplication {

	public static void main(String[] args) {
		SpringApplication.run(InstagramClonApplication.class, args);
	}

}
