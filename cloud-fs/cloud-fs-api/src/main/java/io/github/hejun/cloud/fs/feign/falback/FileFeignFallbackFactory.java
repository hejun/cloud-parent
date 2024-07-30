package io.github.hejun.cloud.fs.feign.falback;

import feign.FeignException;
import io.github.hejun.cloud.common.exception.StatefulException;
import io.github.hejun.cloud.common.vo.Result;
import io.github.hejun.cloud.fs.feign.FileFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件Feign回滚
 *
 * @author HeJun
 */
@Slf4j
@Component
public class FileFeignFallbackFactory implements FallbackFactory<FileFeign> {

	@Override
	public FileFeign create(Throwable cause) {
		return new FileFeign() {
			@Override
			public Result<String> upload(MultipartFile file) throws Exception {
				log.error("FileFeign upload Error: {}", cause.getMessage());
				if (cause instanceof FeignException e) {
					return Result.ERROR(e.status(), e.contentUTF8());
				}
				return Result.ERROR(500, cause.getMessage());
			}

			@Override
			public Resource download(String path, String range) throws Exception {
				log.error("FileFeign download Error: {}", cause.getMessage());
				throw new StatefulException(500, cause);
			}

		};
	}

}
