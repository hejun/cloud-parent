package io.github.hejun.cloud.msg.controller;

import io.github.hejun.cloud.common.vo.Result;
import io.github.hejun.cloud.msg.common.dto.Msg;
import io.github.hejun.cloud.msg.common.enums.MsgType;
import io.github.hejun.cloud.msg.service.MsgService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 消息Controller
 *
 * @author HeJun
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MsgController {

	private Map<MsgType, MsgService> msgHandStrategy;

	@PostMapping
	public Result<String> send(@RequestBody Msg msg) throws Exception {
		MsgService msgService = msgHandStrategy.get(msg.getMsgType());
		String resp = msgService.send(msg);
		return Result.SUCCESS(resp);
	}

	@Autowired
	public void setMsgHandStrategy(List<MsgService> msgServiceList) {
		if (msgHandStrategy == null) {
			msgHandStrategy = new HashMap<>();
		}
		for (MsgService msgService : msgServiceList) {
			this.msgHandStrategy.put(msgService.supportType(), msgService);
		}
	}

}
