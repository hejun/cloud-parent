package io.github.hejun.cloud.fs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

/**
 * 文件存储服务
 *
 * @author HeJun
 */
@SpringBootApplication
@ConfigurationPropertiesScan(basePackages = "io.github.hejun.cloud.fs.properties")
public class FsApplication {

	public static void main(String[] args) {
		SpringApplication.run(FsApplication.class, args);
	}

}
