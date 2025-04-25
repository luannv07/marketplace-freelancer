package com.luannv.mf.repositories;

import com.luannv.mf.models.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Integer> {
	boolean existsByName(String skillName);

	Optional<Skill> findByName(String s);
}
