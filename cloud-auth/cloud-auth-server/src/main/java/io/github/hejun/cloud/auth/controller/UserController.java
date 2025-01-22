package io.github.hejun.cloud.auth.controller;

import io.github.hejun.cloud.auth.common.vo.UserVo;
import io.github.hejun.cloud.common.vo.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户Controller
 *
 * @author HeJun
 */
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class UserController {

	@GetMapping("/{id}")
	public Result<UserVo> findUserById(@PathVariable Long id) {
		return Result.SUCCESS();
	}

}
