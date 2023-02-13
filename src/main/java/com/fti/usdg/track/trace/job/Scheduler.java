/**
 * 
 */
package com.fti.usdg.track.trace.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fti.usdg.track.trace.common.Constants;
import com.fti.usdg.track.trace.common.UtilityMethods;
import com.fti.usdg.track.trace.dto.SaveLedgerRequest;
import com.fti.usdg.track.trace.dto.SaveLedgerResponse;
import com.fti.usdg.track.trace.dto.ShipmentDataDto;
import com.fti.usdg.track.trace.models.ShipmentDataEntity;
import com.fti.usdg.track.trace.models.ShipmentDataVersions;
import com.fti.usdg.track.trace.repository.ShipmentDataRepository;
import com.fti.usdg.track.trace.repository.ShipmentDataVersionsRepository;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fti.usdg.track.trace.service.ApplicationRestClient;

/**
 * @author Anup
 *
 */
@Component
public class Scheduler {

	@Autowired
	private ShipmentDataRepository ShipmentDataRepo = null;
	@Autowired
	private UtilityMethods UtilityMethods = null;
	@Autowired
	private ShipmentDataVersionsRepository VersionsRepository = null;
	@Autowired
	private ObjectMapper objectMapper = null;

	@Autowired
	private ApplicationRestClient ApplicationRestClient = null;
	private static final Logger logger = LoggerFactory.getLogger(Scheduler.class);

	@Scheduled(cron = "${publish.to.ledger}")
	public void callLedgerSyncServiceJob() {
		logger.info("start callLedgerSyncServiceJob");
		Integer counter = 0;
		try {
			List<ShipmentDataEntity> unsent = ShipmentDataRepo.findByLedgerSyncFlag(Constants.No);
			if (unsent != null && unsent.size() > 0) {
				for (ShipmentDataEntity ShipmentDataEntity : unsent) {
					SaveLedgerRequest slrq = null;
					if (ShipmentDataEntity.getStatus() != null
							&& ShipmentDataEntity.getStatus().equalsIgnoreCase(Constants.ACTIVE)) {
						ShipmentDataDto _sdDto = copyShipmentEntityToDto(ShipmentDataEntity);
						_sdDto.setUpdatedDate(UtilityMethods.getCurrentDate());
						String dataAsString = objectMapper.writeValueAsString(_sdDto);
						slrq = new SaveLedgerRequest("CreateShipment", dataAsString);
					} else if (ShipmentDataEntity.getStatus() != null
							&& ShipmentDataEntity.getStatus().equalsIgnoreCase(Constants.DELETED)) {
						slrq = new SaveLedgerRequest("DeleteShipment", ShipmentDataEntity.getBolNo());
					}
					Thread.sleep(200);
					String finalRequest = objectMapper.writeValueAsString(slrq);
					String callResponse = ApplicationRestClient.callAPI(finalRequest, Constants.SAVE_ENDPOINT, "NA",
							HttpMethod.POST, false);
					if (callResponse != null && callResponse.length() > 0 && callResponse.contains("successful")) {
						SaveLedgerResponse slr = objectMapper.readValue(callResponse, SaveLedgerResponse.class);
						if (slr.getSuccessful()) {
							ShipmentDataEntity.setTransactionHash(slr.getTransactionId());
							ShipmentDataEntity.setLedgerSyncFlag(Constants.Yes);
							ShipmentDataEntity.setUpdatedDate(UtilityMethods.getCurrentDate());
							ShipmentDataRepo.save(ShipmentDataEntity);
							VersionsRepository.save(getVersionEntity(ShipmentDataEntity, slr.getBlockNumber()));
						}
					}
					if(counter==50) {
						break;
					}
					counter++;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private ShipmentDataVersions getVersionEntity(ShipmentDataEntity shipmentData, String blockNo)
			throws JsonProcessingException {
		Integer versionNo = 1;
		ShipmentDataDto shipmentDataDto = copyShipmentEntityToDto(shipmentData);
		List<ShipmentDataVersions> versions = VersionsRepository.findByBolNoOrderByVersion(shipmentData.getBolNo());
		if (versions != null && versions.size() > 0) {
			versionNo = versions.get(versions.size() - 1).getVersion() + 1;
		}
		ShipmentDataVersions history = new ShipmentDataVersions();
		history.setBlockNo(blockNo);
		history.setBolNo(shipmentData.getBolNo());
		history.setChannelName(Constants.CHANNEL_NAME);
		history.setCreatedAt(UtilityMethods.getCurrentDate());
		history.setTransactionHash(shipmentData.getTransactionHash());
		String dataAsString = objectMapper.writeValueAsString(shipmentDataDto);
		history.setData(dataAsString);
		history.setVersion(versionNo);
		return history;
	}

	private ShipmentDataDto copyShipmentEntityToDto(ShipmentDataEntity shipmentData) {
		logger.debug("Entering into copyShipmentEntityToDto");
		ShipmentDataDto _sdDto = new ShipmentDataDto();
		_sdDto.setShipper(shipmentData.getShipper());
		_sdDto.setDotClass(shipmentData.getDotClass());
		_sdDto.setHazardWarning(shipmentData.getHazardWarning());
		_sdDto.setStccCode(shipmentData.getStccCode());
		_sdDto.setUnNaNo(shipmentData.getUnNaNo());
		_sdDto.setProduct(shipmentData.getProduct());
		_sdDto.setPackingGroup(shipmentData.getPackingGroup());
		_sdDto.setCrudeClassification(shipmentData.getCrudeClassification());
		_sdDto.setShipDate(shipmentData.getShipDate());
		_sdDto.setUnitTrainNo(shipmentData.getUnitTrainNo());
		_sdDto.setBolNo(shipmentData.getBolNo());
		_sdDto.setErpPiu(shipmentData.getErpPiu());
		_sdDto.setBswPerc(shipmentData.getBswPerc());
		_sdDto.setDensity(shipmentData.getDensity());
		_sdDto.setUnitTrainSize(shipmentData.getUnitTrainSize());
		_sdDto.setCommodityName(shipmentData.getCommodityName());
		_sdDto.setEraflashD93b(shipmentData.getEraflashD93b());
		_sdDto.setStatus(shipmentData.getStatus());
		_sdDto.setUpdatedDate(shipmentData.getUpdatedDate());
		return _sdDto;
	}
}
