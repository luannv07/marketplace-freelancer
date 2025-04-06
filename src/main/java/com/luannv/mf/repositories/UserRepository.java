package com.luannv.mf.repositories;

import com.luannv.mf.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

	Optional<User> findByUsername(String admin);

	Optional<User> findByEmail(String email);
}
