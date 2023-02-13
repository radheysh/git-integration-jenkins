/**
 * 
 */
package com.fti.usdg.track.trace.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fti.usdg.track.trace.dto.ShipmentDataShipper;
import com.fti.usdg.track.trace.models.*;


/**
 * @author Anup
 *
 */

@Repository
public interface ShipmentDataRepository extends JpaRepository<ShipmentDataEntity, Long>, JpaSpecificationExecutor<ShipmentDataEntity> {
	/**
	 *
	 */
	List<ShipmentDataEntity> findAll();
	/**
	 * @param uuid
	 * @return
	 */
	ShipmentDataEntity findByLedgerUuid(String uuid);
	/**
	 * @param bolNumber
	 * @return
	 */
	ShipmentDataEntity findByBolNo(String bolNumber);
	/**
	 * @param ledgerUuid
	 */
	void deleteByLedgerUuid(String ledgerUuid);
	/**
	 * @param bolNumber
	 */
	void deleteByBolNo(String bolNumber);
	/**
	 * @param no
	 * @return
	 */
	List<ShipmentDataEntity> findByLedgerSyncFlag(String no);
	/**
	 * @param active
	 * @return
	 */
	@Query("SELECT DISTINCT shipper FROM ShipmentDataEntity")
	Set<String> getDistinctShhippers();
	/**
	 * @return
	 */
	@Query("SELECT DISTINCT commodityName FROM ShipmentDataEntity")
	Set<String> getDistinctCommodityNames();
	/**
	 * @return
	 */
	@Query("SELECT DISTINCT crudeClassification FROM ShipmentDataEntity where crudeClassification IS NOT NULL ")
	Set<String> getDistinctCrudeTypes();
	
 
 
}
