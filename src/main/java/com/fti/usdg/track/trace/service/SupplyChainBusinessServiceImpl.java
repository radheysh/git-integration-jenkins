package com.fti.usdg.track.trace.service;

import org.springframework.stereotype.Service;

import com.fti.usdg.track.trace.common.TrackTraceResponse;
import com.fti.usdg.track.trace.dto.BatteryDetails;
import com.fti.usdg.track.trace.dto.LitCompanyDetails;
import com.fti.usdg.track.trace.dto.LogisticsDetails;
import com.fti.usdg.track.trace.dto.QaDetails;
import com.fti.usdg.track.trace.dto.ShipmentDataDto;
import com.fti.usdg.track.trace.dto.TulsaElectricVehicles;

 

@Service
public class SupplyChainBusinessServiceImpl implements SupplyChainBusinessService {

	@Override
	public TrackTraceResponse saveBatteryCoDetails(BatteryDetails batteryDetails, String userUUIDJwtToken) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TrackTraceResponse saveLitCoDetails(LitCompanyDetails litCompanyDetails, String userUUIDJwtToken) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TrackTraceResponse saveQaConsultingDetails(QaDetails qaDetails, String userUUIDJwtToken) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TrackTraceResponse saveTulsaElectricVehicles(TulsaElectricVehicles tulsaElectricVehicles,
			String userUUIDJwtToken) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TrackTraceResponse getTulsaElectricVehicles(String userUUIDJwtToken) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TrackTraceResponse getShippingCoDetails(String userUUIDJwtToken) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TrackTraceResponse getQaConsultingDetails(String userUUIDJwtToken) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TrackTraceResponse getLitCoDetails(String userUUIDJwtToken) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TrackTraceResponse getBatteryCoDetails(String userUUIDJwtToken) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TrackTraceResponse saveShippingCoDetails(LogisticsDetails logisticsDetails, String userUUIDJwtToken) {
		// TODO Auto-generated method stub
		return null;
	}  
	
 
}