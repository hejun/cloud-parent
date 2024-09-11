package io.github.hejun.cloud.msg.feign.falback;

import feign.FeignException;
import io.github.hejun.cloud.common.vo.Result;
import io.github.hejun.cloud.msg.common.dto.MsgDto;
import io.github.hejun.cloud.msg.feign.MsgFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * 消息Feign回滚
 *
 * @author HeJun
 */
@Slf4j
@Component
public class MsgFeignFallbackFactory implements FallbackFactory<MsgFeign> {

	@Override
	public MsgFeign create(Throwable cause) {
		return new MsgFeign() {

			@Override
			public Result<String> send(MsgDto msg) {
				log.error("MsgFeign send Error: {}", cause.getMessage());
				if (cause instanceof FeignException e) {
					return Result.ERROR(e.status(), e.contentUTF8());
				}
				return Result.ERROR(500, cause.getMessage());
			}

		};
	}

}
