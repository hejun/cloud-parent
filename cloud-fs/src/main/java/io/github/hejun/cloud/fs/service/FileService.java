package io.github.hejun.cloud.fs.service;

import io.minio.GetObjectResponse;
import io.minio.StatObjectResponse;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件服务
 *
 * @author HeJun
 */
public interface FileService {

	/**
	 * 上传文件
	 *
	 * @param file 文件
	 * @return 存储路径
	 * @throws Exception 上传异常
	 */
	String upload(MultipartFile file) throws Exception;

	/**
	 * 获取文件信息
	 *
	 * @param path 文件存储路径
	 * @return 文件信息
	 * @throws Exception 查询异常
	 */
	StatObjectResponse objectInfo(String path) throws Exception;

	/**
	 * 文件下载
	 *
	 * @param path   文件存储路径
	 * @param offset 下载起始
	 * @param length 下载大小
	 * @return 下载文件信息
	 * @throws Exception 下载异常
	 */
	GetObjectResponse download(String path, Long offset, Long length) throws Exception;

}
