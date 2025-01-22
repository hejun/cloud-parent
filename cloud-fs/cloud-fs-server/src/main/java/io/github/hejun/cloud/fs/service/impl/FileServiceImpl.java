package io.github.hejun.cloud.fs.service.impl;

import io.github.hejun.cloud.fs.properties.MinioProperties;
import io.github.hejun.cloud.fs.service.FileService;
import io.minio.*;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * 文件服务
 *
 * @author HeJun
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class FileServiceImpl implements FileService {

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

	@Override
	public String upload(MultipartFile file) throws Exception {
		PutObjectArgs args = PutObjectArgs.builder()
			.bucket(minioProperties.getBucket())
			.object(file.getOriginalFilename())
			.contentType(file.getContentType())
			.stream(file.getInputStream(), file.getSize(), -1)
			.build();
		ObjectWriteResponse resp = client.putObject(args);
		log.debug("Upload File, Bucket: {}, File: {}, StorePath: {}",
			minioProperties.getBucket(), file.getOriginalFilename(), resp.object());
		return resp.object();
	}

	@Override
	public StatObjectResponse objectInfo(String path) throws Exception {
		StatObjectArgs args = StatObjectArgs.builder()
			.bucket(minioProperties.getBucket())
			.object(path)
			.build();
		log.debug("Get FileInfo, Bucket: {}, Path: {}", minioProperties.getBucket(), path);
		return client.statObject(args);
	}

	@Override
	public InputStream download(String path, Long firstBytePos, Long lastBytePos) throws Exception {
		GetObjectArgs.Builder builder = GetObjectArgs.builder()
			.bucket(minioProperties.getBucket())
			.offset(firstBytePos)
			.length(lastBytePos)
			.object(path);
		log.debug("Download File, Bucket: {}, Path: {}, Range: {}", minioProperties.getBucket(), path, firstBytePos + "-" + lastBytePos);
		return client.getObject(builder.build());
	}
}
