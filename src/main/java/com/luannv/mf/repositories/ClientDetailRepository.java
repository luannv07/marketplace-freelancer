package com.luannv.mf.repositories;

import com.luannv.mf.models.ClientProfile;
import com.luannv.mf.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientDetailRepository extends JpaRepository<ClientProfile, Integer> {
	boolean existsByUserClientProfile(User user);
}
