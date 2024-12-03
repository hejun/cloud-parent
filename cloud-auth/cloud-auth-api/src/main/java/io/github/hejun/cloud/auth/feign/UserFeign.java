package io.github.hejun.cloud.auth.feign;

import io.github.hejun.cloud.auth.common.vo.UserVo;
import io.github.hejun.cloud.auth.feign.falback.UserFeignFallbackFactory;
import io.github.hejun.cloud.common.vo.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 用户服务Feign
 *
 * @author HeJun
 */
@FeignClient(name = "cloud-auth", contextId = "user", fallbackFactory = UserFeignFallbackFactory.class)
public interface UserFeign {

	@GetMapping("/user/{id}")
	Result<UserVo> findUserById(@PathVariable Long id);

}
