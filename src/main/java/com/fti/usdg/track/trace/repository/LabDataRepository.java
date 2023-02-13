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
public interface LabDataRepository extends JpaRepository<LabDataEntity, Long>, JpaSpecificationExecutor<LabDataEntity> {
	List<LabDataEntity> findAll();

	LabDataEntity findByCustomerAndProduct(String customer, String product);

	LabDataEntity findByCustomerAndProductAndStatus(String customer, String product, String active);
	 
 
}
