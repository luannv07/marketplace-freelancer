package com.luannv.mf.repositories;

import com.luannv.mf.models.ClientProfile;
import com.luannv.mf.models.FreelancerProfile;
import com.luannv.mf.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FreelancerDetailRepository extends JpaRepository<FreelancerProfile, Integer> {
	boolean existsByUserFreelancerProfile(User user);
}
