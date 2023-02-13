package com.fti.usdg.track.trace.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.fti.usdg.track.trace.models.User;
 

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
	List<User> findAll();
	
	Optional<User> findByUsername(String username);

	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);

	List<User> findByStatus(String active);

	List<User> findByStatusAndUuid(String active, String userId);
	
	User findByUuid(String userId);
	
	
}
 
	