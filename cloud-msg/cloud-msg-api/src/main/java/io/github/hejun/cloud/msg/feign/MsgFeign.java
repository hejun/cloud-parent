package io.github.hejun.cloud.msg.feign;

import io.github.hejun.cloud.common.vo.Result;
import io.github.hejun.cloud.msg.common.dto.MsgDto;
import io.github.hejun.cloud.msg.feign.falback.MsgFeignFallbackFactory;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 消息服务Feign
 *
 * @author HeJun
 */
@FeignClient(name = "cloud-msg", contextId = "msg", fallbackFactory = MsgFeignFallbackFactory.class)
public interface MsgFeign {

	@PostMapping
	Result<String> send(@Valid @RequestBody MsgDto msg) throws Exception;

}
