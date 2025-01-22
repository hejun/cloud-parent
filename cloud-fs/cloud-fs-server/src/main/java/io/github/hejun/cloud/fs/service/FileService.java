package io.github.hejun.cloud.fs.service;

import io.minio.StatObjectResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

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
	 * @param path         文件存储路径
	 * @param firstBytePos 下载起始字节位置
	 * @param lastBytePos  下载结束字节位置
	 * @return 下载文件信息
	 * @throws Exception 下载异常
	 */
	InputStream download(String path, Long firstBytePos, Long lastBytePos) throws Exception;

}
