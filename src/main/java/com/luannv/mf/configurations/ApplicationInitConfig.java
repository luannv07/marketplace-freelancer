package com.luannv.mf.configurations;

import com.luannv.mf.dto.response.RoleResponse;
import com.luannv.mf.enums.PermissionEnum;
import com.luannv.mf.enums.RoleEnum;
import com.luannv.mf.exceptions.ErrorCode;
import com.luannv.mf.exceptions.SingleErrorException;
import com.luannv.mf.mappers.RoleMapper;
import com.luannv.mf.models.Permission;
import com.luannv.mf.models.Role;
import com.luannv.mf.models.User;
import com.luannv.mf.repositories.PermissionRepository;
import com.luannv.mf.repositories.RoleRepository;
import com.luannv.mf.repositories.UserRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@Configuration

public class ApplicationInitConfig {
	@Bean
	ApplicationRunner applicationRunner(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, RoleMapper roleMapper) {
		return args -> {
			if (!userRepository.findByUsername("admin").isEmpty())
				return;
			Role role = roleRepository.findByName(RoleEnum.ADMIN.name())
							.orElseThrow(() -> new SingleErrorException(ErrorCode.ROLE_NOTFOUND));
			System.out.println("NOT FOUND");
			Set<Role> set = new HashSet<>();
			set.add(role);
			User user = User.builder()
							.username("admin")
							.password(passwordEncoder.encode("admin"))
							.roles(set)
							.build();
			userRepository.save(user);
		};
	}
}
