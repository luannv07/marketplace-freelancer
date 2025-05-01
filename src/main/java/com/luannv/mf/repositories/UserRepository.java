package com.luannv.mf.repositories;

import com.luannv.mf.dto.response.UserResponse;
import com.luannv.mf.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.lang.annotation.Native;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

	Optional<User> findByUsername(String admin);

	Optional<User> findByEmail(String email);
//	@Query(value = "SELECT * FROM USERS", nativeQuery = true)
//	List<User> getAllUser();
}
