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
public interface ShipmentDataVersionsRepository extends JpaRepository<ShipmentDataVersions, Long>, JpaSpecificationExecutor<ShipmentDataVersions> {
	List<ShipmentDataVersions> findAll();
	List<ShipmentDataVersions>  findByBolNo(String bolNumber);
	List<ShipmentDataVersions> findByBolNoOrderByVersion(String bolNumber);
	List<ShipmentDataVersions> findByBolNoOrderByVersionDesc(String bolNumber);
}
