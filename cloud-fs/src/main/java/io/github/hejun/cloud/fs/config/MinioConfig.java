package io.github.hejun.cloud.fs.config;

import io.github.hejun.cloud.fs.properties.MinioProperties;
import io.minio.MinioClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Minio配置
 *
 * @author HeJun
 */
@Configuration
public class MinioConfig {

	@Bean
	public MinioClient minioClient(MinioProperties properties) {
		return MinioClient.builder()
			.endpoint(properties.getEndpoint())
			.credentials(properties.getAccessKey(), properties.getSecretKey())
			.build();
	}

}
