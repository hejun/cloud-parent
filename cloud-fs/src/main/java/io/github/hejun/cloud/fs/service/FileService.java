package io.github.hejun.cloud.fs.service;

import io.github.hejun.cloud.fs.properties.MinioProperties;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 文件服务
 *
 * @author HeJun
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FileService {

	private final MinioProperties minioProperties;
	private final MinioClient client;

	@PostConstruct
	@SneakyThrows(Exception.class)
	void init() {
		BucketExistsArgs bucketExistsArgs = BucketExistsArgs.builder()
			.bucket(minioProperties.getBucket()).build();
		if (!client.bucketExists(bucketExistsArgs)) {
			MakeBucketArgs makeBucketArgs = MakeBucketArgs.builder()
				.bucket(minioProperties.getBucket())
				.build();
			client.makeBucket(makeBucketArgs);
		}
	}

}
