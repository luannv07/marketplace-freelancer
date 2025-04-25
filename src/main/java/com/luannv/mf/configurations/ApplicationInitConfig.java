package com.luannv.mf.configurations;

import com.luannv.mf.dto.request.RoleRequest;
import com.luannv.mf.enums.PermissionEnum;
import com.luannv.mf.enums.RoleEnum;
import com.luannv.mf.enums.SkillEnum;
import com.luannv.mf.exceptions.ErrorCode;
import com.luannv.mf.exceptions.SingleErrorException;
import com.luannv.mf.mappers.RoleMapper;
import com.luannv.mf.models.Permission;
import com.luannv.mf.models.Role;
import com.luannv.mf.models.Skill;
import com.luannv.mf.models.User;
import com.luannv.mf.repositories.PermissionRepository;
import com.luannv.mf.repositories.RoleRepository;
import com.luannv.mf.repositories.SkillRepository;
import com.luannv.mf.repositories.UserRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;
import java.util.stream.Collectors;

@Configuration
public class ApplicationInitConfig {
	@Bean
	ApplicationRunner applicationRunner(UserRepository userRepository,
																			RoleRepository roleRepository,
																			PasswordEncoder passwordEncoder,
																			RoleMapper roleMapper,
																			PermissionRepository permissionRepository,
																			SkillRepository skillRepository) {
		return args -> {
			// skill
			for (SkillEnum skillEnum : SkillEnum.values()) {
				Optional<Skill> optSkill = skillRepository.findByName(skillEnum.name());

				if (skillRepository.existsByName(skillEnum.name()) ||
								(optSkill.isPresent() && optSkill.get().getIsActive() == 0)	) continue;
				Skill skill = Skill.builder().name(skillEnum.name()).build();
				skillRepository.save(skill);
			}

			Set<Permission> permissions = new HashSet<>();
			for (PermissionEnum permissionEnum : PermissionEnum.values()) {
				if (permissionRepository.existsByName(permissionEnum.name())) continue;
				Permission permission = Permission.builder()
								.name(permissionEnum.name())
								.description(permissionEnum.name())
								.build();
				permissions.add(permission);
				permissionRepository.save(permission);
			}
			if (!roleRepository.existsByName(RoleEnum.ADMIN.name()))
				roleRepository.save(Role.builder()
								.permissions(permissions)
								.description("ADMIN FULL PERMISSIONS")
								.name(RoleEnum.ADMIN.name())
								.build());
			if (!userRepository.findByUsername("admin").isEmpty())
				return;
			Role role = roleRepository.findByName(RoleEnum.ADMIN.name())
							.orElseThrow(() -> new SingleErrorException(ErrorCode.ROLE_NOTFOUND));
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
