package io.github.hejun.cloud.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * 用户Service
 *
 * @author HeJun
 */
@Service
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class UserService implements UserDetailsService {

	private final PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return User
			.withUsername(username)
			.password("1234")
			.passwordEncoder(passwordEncoder::encode)
			.roles("USER")
			.build();
	}

}
