package io.github.hejun.cloud.fs.controller;

import io.github.hejun.cloud.common.vo.Result;
import io.github.hejun.cloud.fs.service.FileService;
import io.minio.GetObjectResponse;
import io.minio.StatObjectResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRange;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Stream;

/**
 * 文件Controller
 *
 * @author HeJun
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FileController {

	private final FileService fileService;

	@PostMapping("upload")
	public Result<String> upload(MultipartFile file) throws Exception {
		String path = fileService.upload(file);
		return Result.SUCCESS(path);
	}

	@GetMapping("/{path}")
	public void download(@PathVariable String path,
						 @RequestHeader(value = HttpHeaders.RANGE, required = false) String range,
						 HttpServletResponse resp) throws Exception {
		StatObjectResponse stat = fileService.objectInfo(path);

		Long offset = null, length = null;
		if (StringUtils.isNotBlank(range)) {
			List<HttpRange> ranges = HttpRange.parseRanges(range);
			if (!CollectionUtils.isEmpty(ranges)) {
				offset = ranges.get(0).getRangeStart(0);
				length = ranges.get(0).getRangeEnd(stat.size()) + 1;
			}
		}
		offset = offset == null ? 0L : offset;
		length = length == null ? stat.size() : length;

		try (GetObjectResponse object = fileService.download(path, offset, length)) {
			Stream.of(
					HttpHeaders.ACCEPT_RANGES,
					HttpHeaders.LAST_MODIFIED,
					HttpHeaders.CONTENT_TYPE,
					HttpHeaders.CONTENT_DISPOSITION,
					HttpHeaders.CONTENT_LENGTH,
					HttpHeaders.CONTENT_RANGE,
					HttpHeaders.DATE)
				.forEach(h -> resp.setHeader(h, object.headers().get(h)));

			StreamUtils.copy(object, resp.getOutputStream());
		}
	}

}
