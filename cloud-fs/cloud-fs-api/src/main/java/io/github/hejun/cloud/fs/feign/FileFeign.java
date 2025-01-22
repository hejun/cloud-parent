package io.github.hejun.cloud.fs.feign;

import io.github.hejun.cloud.common.vo.Result;
import io.github.hejun.cloud.fs.feign.fallback.FileFeignFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件服务Feign
 *
 * @author HeJun
 */
@FeignClient(name = "cloud-fs", contextId = "file", fallbackFactory = FileFeignFallbackFactory.class)
public interface FileFeign {

	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	Result<String> upload(@RequestPart("file") MultipartFile file) throws Exception;

	@GetMapping("/{path}")
	ResponseEntity<Resource> download(@PathVariable String path,
									  @RequestHeader(value = HttpHeaders.RANGE, required = false) String range) throws Exception;

}
