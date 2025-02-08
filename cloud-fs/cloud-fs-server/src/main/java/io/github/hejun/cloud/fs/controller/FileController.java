package io.github.hejun.cloud.fs.controller;

import io.github.hejun.cloud.common.vo.Result;
import io.github.hejun.cloud.fs.service.FileService;
import io.minio.StatObjectResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRange;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * 文件Controller
 *
 * @author HeJun
 */
@RestController
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class FileController {

	private final FileService fileService;

	@PostMapping
	public Result<String> upload(MultipartFile file) throws Exception {
		String path = fileService.upload(file);
		return Result.SUCCESS(path);
	}

	@GetMapping("/{path}")
	public void download(@PathVariable String path,
						 @RequestHeader(value = HttpHeaders.RANGE, required = false) String range,
						 HttpServletResponse resp) throws Exception {
		StatObjectResponse stat = fileService.objectInfo(path);

		Long firstBytePos = null, lastBytePos = null;
		if (StringUtils.isNotBlank(range)) {
			List<HttpRange> ranges = HttpRange.parseRanges(range);
			if (!CollectionUtils.isEmpty(ranges)) {
				firstBytePos = ranges.get(0).getRangeStart(0);
				lastBytePos = ranges.get(0).getRangeEnd(stat.size()) + 1;
			}
		}

		try (InputStream is = fileService.download(path, firstBytePos, lastBytePos)) {
			resp.setContentType(stat.headers().get(HttpHeaders.CONTENT_TYPE));
			resp.setContentLengthLong(stat.size());
			String contentDisposition = ContentDisposition
				.attachment()
				.filename(stat.object(), StandardCharsets.UTF_8)
				.build()
				.toString();
			resp.setHeader(HttpHeaders.CONTENT_DISPOSITION, contentDisposition);
			if (firstBytePos != null) {
				resp.setHeader(HttpHeaders.ACCEPT_RANGES, "bytes");
				resp.setHeader(HttpHeaders.RANGE, HttpRange.createByteRange(firstBytePos, lastBytePos).toString());
				if (lastBytePos < stat.size()) {
					resp.setStatus(HttpStatus.PARTIAL_CONTENT.value());
				}
			}

			resp.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);

			StreamUtils.copy(is, resp.getOutputStream());
		}
	}

}
