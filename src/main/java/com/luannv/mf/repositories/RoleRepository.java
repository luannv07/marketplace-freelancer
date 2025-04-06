package com.luannv.mf.repositories;

import com.luannv.mf.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
	boolean existsByName(String name);

	Optional<Role> findByName(String name);
}
