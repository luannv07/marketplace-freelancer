package com.luannv.mf.repositories;

import com.luannv.mf.enums.PermissionEnum;
import com.luannv.mf.models.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Integer> {
	Optional<Permission> findByName(String name);

	boolean existsByName(String permissionName);
}
