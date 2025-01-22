package io.github.hejun.cloud.fs.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Minio配置参数
 *
 * @author HeJun
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "cloud.fs.minio")
public class MinioProperties {

	private String endpoint;
	private String accessKey;
	private String secretKey;
	private String bucket;

}
