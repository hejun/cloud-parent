package io.github.hejun.cloud.fs.service.impl;

import io.github.hejun.cloud.fs.properties.MinioProperties;
import io.github.hejun.cloud.fs.service.FileService;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;

/**
 * 文件服务
 *
 * @author HeJun
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FileServiceMinioImpl implements FileService {


	private final MinioProperties minioProperties;
	private final MinioClient client;

	@PostConstruct
	void init() throws Exception {
		BucketExistsArgs bucketExistsArgs = BucketExistsArgs.builder()
			.bucket(minioProperties.getBucket()).build();
		if (!client.bucketExists(bucketExistsArgs)) {
			MakeBucketArgs makeBucketArgs = MakeBucketArgs.builder()
				.bucket(minioProperties.getBucket())
				.build();
			client.makeBucket(makeBucketArgs);
		}
	}

	@Override
	public String upload(InputStream is, String name) throws Exception {
		PutObjectArgs args = PutObjectArgs.builder()
			.bucket(minioProperties.getBucket())
			.object(name)
			.stream(is, is.available(), -1)
			.build();
		return client.putObject(args).object();
	}

}
