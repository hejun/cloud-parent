package io.github.hejun.cloud.auth.feign.falback;

import feign.FeignException;
import io.github.hejun.cloud.auth.common.vo.UserVo;
import io.github.hejun.cloud.auth.feign.UserFeign;
import io.github.hejun.cloud.common.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * 用户服务Feign回滚
 *
 * @author HeJun
 */
@Slf4j
@Component
public class UserFeignFallbackFactory implements FallbackFactory<UserFeign> {

	@Override
	public UserFeign create(Throwable cause) {
		return new UserFeign() {

			@Override
			public Result<UserVo> findUserById(Long id) {
				log.error("UserFeign findUserById Error: {}", cause.getMessage());
				if (cause instanceof FeignException e) {
					return Result.ERROR(e.status(), e.contentUTF8());
				}
				return Result.ERROR(500, cause.getMessage());
			}

		};
	}

}
