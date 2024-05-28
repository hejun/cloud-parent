package io.github.hejun.cloud.fs.service;

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
	 * @param is   文件流
	 * @param name 文件名
	 * @return 上传ID
	 * @throws Exception 上传异常
	 */
	String upload(InputStream is, String name) throws Exception;

}
