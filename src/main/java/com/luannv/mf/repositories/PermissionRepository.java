package com.luannv.mf.repositories;

import com.luannv.mf.models.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Integer> {
	public Optional<Permission> findByName(String name);
}
