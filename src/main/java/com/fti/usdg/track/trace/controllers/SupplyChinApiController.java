package com.fti.usdg.track.trace.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fti.usdg.track.trace.common.Constants;
import com.fti.usdg.track.trace.common.TrackTraceResponse;
import com.fti.usdg.track.trace.dto.BatteryDetails;
import com.fti.usdg.track.trace.dto.DeleteShipmentReq;
import com.fti.usdg.track.trace.dto.LitCompanyDetails;
import com.fti.usdg.track.trace.dto.LoggedInUserGroup;
import com.fti.usdg.track.trace.dto.LogisticsDetails;
import com.fti.usdg.track.trace.dto.QaDetails;
import com.fti.usdg.track.trace.dto.SearchParameters;
import com.fti.usdg.track.trace.dto.ShipmentDataDto;
import com.fti.usdg.track.trace.dto.TulsaElectricVehicles;
import com.fti.usdg.track.trace.dto.UserDataHolder;
import com.fti.usdg.track.trace.dto.UserDetailsDto;
import com.fti.usdg.track.trace.models.LabDataEntity;
import com.fti.usdg.track.trace.models.User;
import com.fti.usdg.track.trace.models.UserGroup;
import com.fti.usdg.track.trace.payload.response.JwtResponse;
import com.fti.usdg.track.trace.repository.RoleRepository;
import com.fti.usdg.track.trace.repository.UserGroupRepository;
import com.fti.usdg.track.trace.repository.UserRepository;
import com.fti.usdg.track.trace.security.jwt.JwtUtils;

import com.fti.usdg.track.trace.service.BusinessService;
import com.fti.usdg.track.trace.service.SupplyChainBusinessService;
import com.fti.usdg.track.trace.common.UtilityMethods;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//http://18.140.234.199:7001/fti/swagger-ui/index.html
@CrossOrigin("*")
@RestController
@RequestMapping("api/v1/supplychain/")
@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
public class SupplyChinApiController {

	private static final Logger logger = LoggerFactory.getLogger(SupplyChinApiController.class);

	@Autowired
	private SupplyChainBusinessService BusinessService = null;
	@Autowired
	private UtilityMethods UtilityMethods = null;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	UserGroupRepository userGroupRepository;
	@Autowired
	RoleRepository roleRepository;
	@Autowired
	PasswordEncoder encoder;
	@Autowired
	JwtUtils jwtUtils;
	
	
	@PostMapping("/saveBatteryCoDetails")
	@ResponseBody
	public TrackTraceResponse saveBatteryCoDetails(@RequestBody BatteryDetails BatteryDetails, HttpServletRequest httpRequest) {
		logger.info("Entering in to saveBatteryCoDetails" + BatteryDetails);
		TrackTraceResponse TrackTraceResponse = null;
		try {
			TrackTraceResponse = BusinessService.saveBatteryCoDetails(BatteryDetails,
					jwtUtils.getUserUUIDJwtToken(getHeaderKey(httpRequest, Constants.JWT_TOKEN_KEY)));
		} catch (Exception ex) {
			TrackTraceResponse = UtilityMethods.handleException(ex);
		}
		logger.info("Leaving from saveBatteryCoDetails " + TrackTraceResponse);
		return TrackTraceResponse;
	}
	
	@GetMapping("/getBatteryCoDetails")
	@ResponseBody
	public TrackTraceResponse saveBatteryCoDetails(HttpServletRequest httpRequest) {
		logger.info("Entering in to getBatteryCoDetails");
		TrackTraceResponse TrackTraceResponse = null;
		try {
			TrackTraceResponse = BusinessService.getBatteryCoDetails(jwtUtils.getUserUUIDJwtToken(getHeaderKey(httpRequest, Constants.JWT_TOKEN_KEY)));
		} catch (Exception ex) {
			TrackTraceResponse = UtilityMethods.handleException(ex);
		}
		logger.info("Leaving from getBatteryCoDetails " + TrackTraceResponse);
		return TrackTraceResponse;
	}
	
	@PostMapping("/saveLitCoDetails")
	@ResponseBody
	public TrackTraceResponse saveLitCoDetails(@RequestBody LitCompanyDetails LitCompanyDetails, HttpServletRequest httpRequest) {
		logger.info("Entering in to saveLitCoDetails" + LitCompanyDetails);
		TrackTraceResponse TrackTraceResponse = null;
		try {
			TrackTraceResponse = BusinessService.saveLitCoDetails(LitCompanyDetails,
					jwtUtils.getUserUUIDJwtToken(getHeaderKey(httpRequest, Constants.JWT_TOKEN_KEY)));
		} catch (Exception ex) {
			TrackTraceResponse = UtilityMethods.handleException(ex);
		}
		logger.info("Leaving from saveLitCoDetails " + TrackTraceResponse);
		return TrackTraceResponse;
	}
	@GetMapping("/getLitCoDetails")
	@ResponseBody
	public TrackTraceResponse getLitCoDetails(HttpServletRequest httpRequest) {
		logger.info("Entering in to getLitCoDetails");
		TrackTraceResponse TrackTraceResponse = null;
		try {
			TrackTraceResponse = BusinessService.getLitCoDetails(jwtUtils.getUserUUIDJwtToken(getHeaderKey(httpRequest, Constants.JWT_TOKEN_KEY)));
		} catch (Exception ex) {
			TrackTraceResponse = UtilityMethods.handleException(ex);
		}
		logger.info("Leaving from getLitCoDetails " + TrackTraceResponse);
		return TrackTraceResponse;
	}
	
	@PostMapping("/saveQaConsultingDetails")
	@ResponseBody
	public TrackTraceResponse saveQaConsultingDetails(@RequestBody QaDetails QaDetails, HttpServletRequest httpRequest) {
		logger.info("Entering in to saveQaConsultingDetails" + QaDetails);
		TrackTraceResponse TrackTraceResponse = null;
		try {
			TrackTraceResponse = BusinessService.saveQaConsultingDetails(QaDetails,
					jwtUtils.getUserUUIDJwtToken(getHeaderKey(httpRequest, Constants.JWT_TOKEN_KEY)));
		} catch (Exception ex) {
			TrackTraceResponse = UtilityMethods.handleException(ex);
		}
		logger.info("Leaving from saveQaConsultingDetails " + TrackTraceResponse);
		return TrackTraceResponse;
	}
	
	@GetMapping("/getQaConsultingDetails")
	@ResponseBody
	public TrackTraceResponse getQaConsultingDetails(HttpServletRequest httpRequest) {
		logger.info("Entering in to getQaConsultingDetails");
		TrackTraceResponse TrackTraceResponse = null;
		try {
			TrackTraceResponse = BusinessService.getQaConsultingDetails(jwtUtils.getUserUUIDJwtToken(getHeaderKey(httpRequest, Constants.JWT_TOKEN_KEY)));
		} catch (Exception ex) {
			TrackTraceResponse = UtilityMethods.handleException(ex);
		}
		logger.info("Leaving from getQaConsultingDetails " + TrackTraceResponse);
		return TrackTraceResponse;
	}
	
	@PostMapping("/saveShippingCoDetails")
	@ResponseBody
	public TrackTraceResponse saveShippingCoDetails(@RequestBody LogisticsDetails LogisticsDetails, HttpServletRequest httpRequest) {
		logger.info("Entering in to saveShippingCoDetails" + LogisticsDetails);
		TrackTraceResponse TrackTraceResponse = null;
		try {
			TrackTraceResponse = BusinessService.saveShippingCoDetails(LogisticsDetails,
					jwtUtils.getUserUUIDJwtToken(getHeaderKey(httpRequest, Constants.JWT_TOKEN_KEY)));
		} catch (Exception ex) {
			TrackTraceResponse = UtilityMethods.handleException(ex);
		}
		logger.info("Leaving from saveShippingCoDetails " + TrackTraceResponse);
		return TrackTraceResponse;
	}
	
	@GetMapping("/getShippingCoDetails")
	@ResponseBody
	public TrackTraceResponse getShippingCoDetails(HttpServletRequest httpRequest) {
		logger.info("Entering in to getShippingCoDetails");
		TrackTraceResponse TrackTraceResponse = null;
		try {
			TrackTraceResponse = BusinessService.getShippingCoDetails(jwtUtils.getUserUUIDJwtToken(getHeaderKey(httpRequest, Constants.JWT_TOKEN_KEY)));
		} catch (Exception ex) {
			TrackTraceResponse = UtilityMethods.handleException(ex);
		}
		logger.info("Leaving from getShippingCoDetails " + TrackTraceResponse);
		return TrackTraceResponse;
	}
	
	@PostMapping("/saveTulsaElectricVehicles")
	@ResponseBody
	public TrackTraceResponse saveTulsaElectricVehicles(@RequestBody TulsaElectricVehicles TulsaElectricVehicles, HttpServletRequest httpRequest) {
		logger.info("Entering in to saveTulsaElectricVehicles" + TulsaElectricVehicles);
		TrackTraceResponse TrackTraceResponse = null;
		try {
			TrackTraceResponse = BusinessService.saveTulsaElectricVehicles(TulsaElectricVehicles,
					jwtUtils.getUserUUIDJwtToken(getHeaderKey(httpRequest, Constants.JWT_TOKEN_KEY)));
		} catch (Exception ex) {
			TrackTraceResponse = UtilityMethods.handleException(ex);
		}
		logger.info("Leaving from saveTulsaElectricVehicles " + TrackTraceResponse);
		return TrackTraceResponse;
	}
	
	@GetMapping("/getTulsaElectricVehicles")
	@ResponseBody
	public TrackTraceResponse getTulsaElectricVehicles(HttpServletRequest httpRequest) {
		logger.info("Entering in to getTulsaElectricVehicles");
		TrackTraceResponse TrackTraceResponse = null;
		try {
			TrackTraceResponse = BusinessService.getTulsaElectricVehicles(jwtUtils.getUserUUIDJwtToken(getHeaderKey(httpRequest, Constants.JWT_TOKEN_KEY)));
		} catch (Exception ex) {
			TrackTraceResponse = UtilityMethods.handleException(ex);
		}
		logger.info("Leaving from getTulsaElectricVehicles " + TrackTraceResponse);
		return TrackTraceResponse;
	}
	
	@GetMapping("/whoami")
	@ResponseBody
	public TrackTraceResponse whoami(HttpServletRequest httpRequest) {
		logger.info("Entering in to whoami");
		TrackTraceResponse TrackTraceResponse = null;
		List<LoggedInUserGroup> LoggedInUserGroups = new ArrayList<LoggedInUserGroup>();
		try {
			LoggedInUserGroup LoggedInUserGroup= new LoggedInUserGroup();
			String tokens [] = jwtUtils.getUserDetailsJwtToken(getHeaderKey(httpRequest, Constants.JWT_TOKEN_KEY)).split(Constants.HASH);
			LoggedInUserGroup.setUsername(tokens[0]);
			LoggedInUserGroup.setUuid(tokens[1]);
			LoggedInUserGroup.setLoggedInGroup(tokens[2]);
			LoggedInUserGroup.setFeatureIds(tokens[3]);
			LoggedInUserGroup.setToken(getHeaderKey(httpRequest, Constants.JWT_TOKEN_KEY));
			LoggedInUserGroups.add(LoggedInUserGroup);
			TrackTraceResponse = new TrackTraceResponse(Constants.SUCCESS,Constants.RESULT_FOUND,LoggedInUserGroups);
		} catch (Exception ex) {
			TrackTraceResponse = UtilityMethods.handleException(ex);
		}
		logger.info("Leaving from getGroup " + TrackTraceResponse);
		return TrackTraceResponse;
	}


	@SuppressWarnings("rawtypes")
	private String getHeaderKey(HttpServletRequest httpRequest, String key) {
		Enumeration values = httpRequest.getHeaders(key);
		String value = null;
		if (values != null) {
			while (values.hasMoreElements()) {
				value = (String) values.nextElement();
			}
		}
		return value;
	}
}