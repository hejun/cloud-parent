package io.github.hejun.cloud.fs.controller;

import io.github.hejun.cloud.common.vo.Result;
import io.github.hejun.cloud.fs.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * 文件Controller
 *
 * @author HeJun
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FileController {

	private final FileService fileService;

	@PostMapping
	public Mono<Result<String>> upload(@RequestPart("file") FilePart filePart) {
		return DataBufferUtils.join(filePart.content())
			.<String>handle((dataBuffer, sink) -> {
				try {
					sink.next(fileService.upload(dataBuffer.asInputStream(), filePart.filename()));
				} catch (Exception e) {
					sink.error(e);
				}
			})
			.map(Result::SUCCESS)
			.onErrorResume(err -> Mono.just(Result.ERROR(500, err.getMessage())));
	}

}
