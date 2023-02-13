package com.fti.usdg.track.trace.service;

import com.fti.usdg.track.trace.common.TrackTraceResponse;
import com.fti.usdg.track.trace.dto.BatteryDetails;
import com.fti.usdg.track.trace.dto.LitCompanyDetails;
import com.fti.usdg.track.trace.dto.LogisticsDetails;
import com.fti.usdg.track.trace.dto.QaDetails;
import com.fti.usdg.track.trace.dto.TulsaElectricVehicles;

public interface SupplyChainBusinessService {


	/**
	 * @param batteryDetails
	 * @param userUUIDJwtToken
	 * @return
	 */
	TrackTraceResponse saveBatteryCoDetails(BatteryDetails batteryDetails, String userUUIDJwtToken);

	/**
	 * @param litCompanyDetails
	 * @param userUUIDJwtToken
	 * @return
	 */
	TrackTraceResponse saveLitCoDetails(LitCompanyDetails litCompanyDetails, String userUUIDJwtToken);

	/**
	 * @param qaDetails
	 * @param userUUIDJwtToken
	 * @return
	 */
	TrackTraceResponse saveQaConsultingDetails(QaDetails qaDetails, String userUUIDJwtToken);

	 /**
	 * @param tulsaElectricVehicles
	 * @param userUUIDJwtToken
	 * @return
	 */
	TrackTraceResponse saveTulsaElectricVehicles(TulsaElectricVehicles tulsaElectricVehicles, String userUUIDJwtToken);

	/**
	 * @param userUUIDJwtToken
	 * @return
	 */
	TrackTraceResponse getTulsaElectricVehicles(String userUUIDJwtToken);

	/**
	 * @param userUUIDJwtToken
	 * @return
	 */
	TrackTraceResponse getShippingCoDetails(String userUUIDJwtToken);

	/**
	 * @param userUUIDJwtToken
	 * @return
	 */
	TrackTraceResponse getQaConsultingDetails(String userUUIDJwtToken);

	/**
	 * @param userUUIDJwtToken
	 * @return
	 */
	TrackTraceResponse getLitCoDetails(String userUUIDJwtToken);

	/**
	 * @param userUUIDJwtToken
	 * @return
	 */
	TrackTraceResponse getBatteryCoDetails(String userUUIDJwtToken);

	/**
	 * @param logisticsDetails
	 * @param userUUIDJwtToken
	 * @return
	 */
	TrackTraceResponse saveShippingCoDetails(LogisticsDetails logisticsDetails, String userUUIDJwtToken);

	
}
