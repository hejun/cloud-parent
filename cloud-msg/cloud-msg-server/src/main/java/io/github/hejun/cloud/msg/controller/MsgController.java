package io.github.hejun.cloud.msg.controller;

import io.github.hejun.cloud.common.vo.Result;
import io.github.hejun.cloud.msg.common.dto.MsgDto;
import io.github.hejun.cloud.msg.service.MsgService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 消息Controller
 *
 * @author HeJun
 */
@RestController
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class MsgController {

	private final MsgService msgService;

	@PostMapping
	public Result<String> send(@Valid @RequestBody MsgDto msg) throws Exception {
		String result = msgService.send(msg);
		return Result.SUCCESS(result);
	}

}
