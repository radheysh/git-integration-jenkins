/**
 * 
 */
package com.fti.usdg.track.trace.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.fti.usdg.track.trace.models.*;


/**
 * @author Anup
 *
 */

@Repository
public interface UserGroupRepository extends JpaRepository<UserGroup, Long>, JpaSpecificationExecutor<UserGroup> {
	List<UserGroup> findAll();

	List<UserGroup> findByStatus(String active);

	List<UserGroup> findByStatusAndGroupName(String active, String groupName);

	UserGroup findByGroupName(String groupName);
	 
 
}
