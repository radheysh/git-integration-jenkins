package com.fti.usdg.track.trace.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
import com.fti.usdg.track.trace.dto.DeleteShipmentReq;
import com.fti.usdg.track.trace.dto.LoggedInUserGroup;
import com.fti.usdg.track.trace.dto.SearchParameters;
import com.fti.usdg.track.trace.dto.ShipmentDataDto;
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
import com.fti.usdg.track.trace.security.services.UserDetailsImpl;
import com.fti.usdg.track.trace.service.BusinessService;
import com.fti.usdg.track.trace.common.UtilityMethods;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//http://18.140.234.199:7001/fti/swagger-ui/index.html
@CrossOrigin("*")
@RestController
@RequestMapping("api/v1/webapp/")
@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
public class FtiWebAppApiController {

	private static final Logger logger = LoggerFactory.getLogger(FtiWebAppApiController.class);

	@Autowired
	BusinessService BusinessService = null;
	@Autowired
	private UtilityMethods UtilityMethods = null;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	UserGroupRepository userGroupRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;
	
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
	@PostMapping("/changeLoggedInUserGroup")
	@ResponseBody
	public ResponseEntity<?> changeLoggedInUserGroup(@RequestBody LoggedInUserGroup LoggedInUserGroup,
			HttpServletRequest httpRequest) {
		logger.info("Entering in to changeLoggedInUserGroup" + LoggedInUserGroup);
		
		User User = userRepository.findByUuid(LoggedInUserGroup.getUuid());
		UserGroup UserGroup = null;
		String jwt = null;
		String swicthBackFTID = Constants.SWITCH_BACK_FTID;
		if(LoggedInUserGroup.switchback!=null &&LoggedInUserGroup.switchback) {
			swicthBackFTID="";
		}
		UserGroup = userGroupRepository.findByGroupName(LoggedInUserGroup.targetGroup);
		jwt = jwtUtils.generateJwtTokenWithoutLogin(User.getUsername(), User.getUuid(), LoggedInUserGroup.targetGroup+Constants.HASH+UserGroup.getFeatureIds()+swicthBackFTID);
		return ResponseEntity
				.ok(new JwtResponse(jwt, User.getId(), User.getUsername(), User.getEmail(),
						User.getUuid(), LoggedInUserGroup.targetGroup,   UserGroup.getFeatureIds() + swicthBackFTID,LoggedInUserGroup.getLoggedInGroup()));
		
	}

	@PostMapping("/saveUserGroup")
	@ResponseBody
	public TrackTraceResponse saveUserGroup(@RequestBody UserGroup UserGroup, HttpServletRequest httpRequest) {
		logger.info("Entering in to saveUserGroup" + UserGroup);
		TrackTraceResponse TrackTraceResponse = null;
		try {
			TrackTraceResponse = BusinessService.saveUserGroup(UserGroup,
					jwtUtils.getUserUUIDJwtToken(getHeaderKey(httpRequest, Constants.JWT_TOKEN_KEY)));
		} catch (Exception ex) {
			TrackTraceResponse = UtilityMethods.handleException(ex);
		}
		logger.info("Leaving from saveUserGroup " + TrackTraceResponse);
		return TrackTraceResponse;
	}

	@PostMapping("/deleteUserGroup")
	@ResponseBody
	public TrackTraceResponse deleteUserGroup(@RequestBody UserDataHolder UserDataHolder,
			HttpServletRequest httpRequest) {
		logger.info("Entering in to saveUserGroup" + UserDataHolder);
		TrackTraceResponse TrackTraceResponse = null;
		try {
			TrackTraceResponse = BusinessService.deleteUserGroup(UserDataHolder,
					jwtUtils.getUserUUIDJwtToken(getHeaderKey(httpRequest, Constants.JWT_TOKEN_KEY)));
		} catch (Exception ex) {
			TrackTraceResponse = UtilityMethods.handleException(ex);
		}
		logger.info("Leaving from saveUserGroup " + TrackTraceResponse);
		return TrackTraceResponse;
	}

	@GetMapping("/getUserGroups/{groupName}")
	@ResponseBody
	public TrackTraceResponse getUserGroups(@PathVariable String groupName, HttpServletRequest httpRequest) {
		logger.info("Entering in to getUserGroup");
		TrackTraceResponse TrackTraceResponse = null;
		try {
			TrackTraceResponse = BusinessService.getUserGroups(groupName,
					jwtUtils.getUserUUIDJwtToken(getHeaderKey(httpRequest, Constants.JWT_TOKEN_KEY)));
		} catch (Exception ex) {
			TrackTraceResponse = UtilityMethods.handleException(ex);
		}
		logger.info("Leaving from getGroup " + TrackTraceResponse);
		return TrackTraceResponse;
	}

	@PostMapping("/getUserGroup")
	@ResponseBody
	public TrackTraceResponse getUserGroup(@RequestBody SearchParameters searchCriteria,
			HttpServletRequest httpRequest) {
		logger.info("Entering in to getUserGroup");
		TrackTraceResponse TrackTraceResponse = null;
		try {
			TrackTraceResponse = BusinessService.getUserGroup(searchCriteria,
					jwtUtils.getUserUUIDJwtToken(getHeaderKey(httpRequest, Constants.JWT_TOKEN_KEY)));
		} catch (Exception ex) {
			TrackTraceResponse = UtilityMethods.handleException(ex);
		}
		logger.info("Leaving from getGroup " + TrackTraceResponse);
		return TrackTraceResponse;
	}

	@GetMapping("/getFeatures")
	@ResponseBody
	public TrackTraceResponse getFeatures(HttpServletRequest httpRequest) {
		logger.info("Entering in to getFeatures");
		TrackTraceResponse TrackTraceResponse = null;
		try {
			TrackTraceResponse = BusinessService
					.getFeatures(jwtUtils.getUserUUIDJwtToken(getHeaderKey(httpRequest, Constants.JWT_TOKEN_KEY)));
		} catch (Exception ex) {
			TrackTraceResponse = UtilityMethods.handleException(ex);
		}
		logger.info("Leaving from getGroup " + TrackTraceResponse);
		return TrackTraceResponse;
	}

	@PostMapping("/saveUser")
	@ResponseBody
	public TrackTraceResponse saveUser(@RequestBody UserDetailsDto User, HttpServletRequest httpRequest) {
		logger.info("Entering in to saveUser" + User);
		TrackTraceResponse TrackTraceResponse = null;
		try {
			TrackTraceResponse = BusinessService.saveUser(User,
					jwtUtils.getUserUUIDJwtToken(getHeaderKey(httpRequest, Constants.JWT_TOKEN_KEY)));
		} catch (Exception ex) {
			TrackTraceResponse = UtilityMethods.handleException(ex);
		}
		logger.info("Leaving from saveUser " + TrackTraceResponse);
		return TrackTraceResponse;
	}

	@PostMapping("/getUser")
	@ResponseBody
	public TrackTraceResponse getUser(@RequestBody SearchParameters searchCriteria, HttpServletRequest httpRequest) {
		logger.info("Entering in to getUserGroup");
		TrackTraceResponse TrackTraceResponse = null;
		try {
			TrackTraceResponse = BusinessService.getUserDetails(searchCriteria,
					jwtUtils.getUserUUIDJwtToken(getHeaderKey(httpRequest, Constants.JWT_TOKEN_KEY)));
		} catch (Exception ex) {
			TrackTraceResponse = UtilityMethods.handleException(ex);
		}
		logger.info("Leaving from getGroup " + TrackTraceResponse);
		return TrackTraceResponse;
	}

	@PostMapping("/deleteUser")
	@ResponseBody
	public TrackTraceResponse deleteUser(@RequestBody UserDataHolder UserDataHolder, HttpServletRequest httpRequest) {
		logger.info("Entering in to deleteUser" + UserDataHolder);
		TrackTraceResponse TrackTraceResponse = null;
		try {
			TrackTraceResponse = BusinessService.deleteUser(UserDataHolder,
					jwtUtils.getUserUUIDJwtToken(getHeaderKey(httpRequest, Constants.JWT_TOKEN_KEY)));
		} catch (Exception ex) {
			TrackTraceResponse = UtilityMethods.handleException(ex);
		}
		logger.info("Leaving from saveUserGroup " + TrackTraceResponse);
		return TrackTraceResponse;
	}

	@GetMapping("/getShippers")
	@ResponseBody
	public TrackTraceResponse getShippers() {
		logger.info("Entering in to getShippers");
		TrackTraceResponse TrackTraceResponse = null;
		try {
			TrackTraceResponse = BusinessService.getShippers();
		} catch (Exception ex) {
			TrackTraceResponse = UtilityMethods.handleException(ex);
		}
		logger.info("Leaving from getShippers " + TrackTraceResponse);
		return TrackTraceResponse;
	}

	@PostMapping("/saveLabData")
	@ResponseBody
	public TrackTraceResponse saveLabData(@RequestBody LabDataEntity LabDataEntity, HttpServletRequest httpRequest) {
		logger.info("Entering in to saveLabData" + LabDataEntity);
		TrackTraceResponse TrackTraceResponse = null;
		try {
			TrackTraceResponse = BusinessService.saveLabData(LabDataEntity,
					jwtUtils.getUserUUIDJwtToken(getHeaderKey(httpRequest, Constants.JWT_TOKEN_KEY)));
		} catch (Exception ex) {
			TrackTraceResponse = UtilityMethods.handleException(ex);
		}
		logger.info("Leaving from saveLabData " + TrackTraceResponse);
		return TrackTraceResponse;
	}

	@GetMapping("/getFilterData")
	@ResponseBody
	public TrackTraceResponse getFilterData(HttpServletRequest httpRequest) {
		logger.info("Entering in to getShippers");
		TrackTraceResponse TrackTraceResponse = null;
		try {
			TrackTraceResponse = BusinessService
					.getFilterData(jwtUtils.getUserUUIDJwtToken(getHeaderKey(httpRequest, Constants.JWT_TOKEN_KEY)));
		} catch (Exception ex) {
			TrackTraceResponse = UtilityMethods.handleException(ex);
		}
		logger.info("Leaving from getShippers " + TrackTraceResponse);
		return TrackTraceResponse;
	}

	@GetMapping("/getShipmentDataVersions/{bolNo}")
	@ResponseBody
	public TrackTraceResponse getShipmentDataVersions(@PathVariable String bolNo, HttpServletRequest httpRequest) {
		logger.info("Entering in to saveShipmentData" + bolNo);
		TrackTraceResponse TrackTraceResponse = null;
		try {
			TrackTraceResponse = BusinessService.getShipmentDataVersions(bolNo,
					jwtUtils.getUserUUIDJwtToken(getHeaderKey(httpRequest, Constants.JWT_TOKEN_KEY)));
		} catch (Exception ex) {
			TrackTraceResponse = UtilityMethods.handleException(ex);
		}
		logger.info("Leaving from saveShipmentData " + TrackTraceResponse);
		return TrackTraceResponse;
	}

	/**
	 * @param uuid
	 * @return
	 */
	@GetMapping("/getShipmentDataByUuid/{uuid}")
	@ResponseBody
	public TrackTraceResponse getShipmentDataByUuid(@PathVariable String uuid, HttpServletRequest httpRequest) {
		TrackTraceResponse response = null;
		logger.info("Entering in to getShipmentDataByUuid " + uuid);
		response = BusinessService.getShipmentDataByUuid(uuid,
				jwtUtils.getUserUUIDJwtToken(getHeaderKey(httpRequest, Constants.JWT_TOKEN_KEY)));
		return response;
	}

	/**
	 * @param uuid
	 * @return
	 */
	@GetMapping("/getShipmentDataByBolNo/{bolNo}")
	@ResponseBody
	public TrackTraceResponse getShipmentDataByBolNo(@PathVariable String bolNo, HttpServletRequest httpRequest) {
		TrackTraceResponse response = null;
		logger.info("Entering in to getShipmentDataByBolNo " + bolNo);
		response = BusinessService.getShipmentDataByBolNo(bolNo,
				jwtUtils.getUserUUIDJwtToken(getHeaderKey(httpRequest, Constants.JWT_TOKEN_KEY)));
		return response;
	}

	/**
	 * @param ShipmentData
	 * @return
	 */
	@PostMapping("/saveShipmentData")
	@ResponseBody
	public TrackTraceResponse saveShipmentData(@RequestBody ShipmentDataDto ShipmentData,
			HttpServletRequest httpRequest) {
		logger.info("Entering in to saveShipmentData" + ShipmentData);
		TrackTraceResponse TrackTraceResponse = null;
		try {
			TrackTraceResponse = BusinessService.saveShipmentData(ShipmentData,
					jwtUtils.getUserUUIDJwtToken(getHeaderKey(httpRequest, Constants.JWT_TOKEN_KEY)));
		} catch (Exception ex) {
			TrackTraceResponse = UtilityMethods.handleException(ex);
		}
		logger.info("Leaving from saveShipmentData " + TrackTraceResponse);
		return TrackTraceResponse;
	}

	/**
	 * @param DeleteShipmentReq
	 * @return
	 */
	@PostMapping("/deleteShipmentData")
	@ResponseBody
	public TrackTraceResponse deleteShipmentData(@RequestBody DeleteShipmentReq deleteShipmentData,
			HttpServletRequest httpRequest) {
		logger.info("Entering in to deleteShipmentData" + deleteShipmentData);
		TrackTraceResponse TrackTraceResponse = null;
		try {
			TrackTraceResponse = BusinessService.deleteShipmentData(deleteShipmentData,
					jwtUtils.getUserUUIDJwtToken(getHeaderKey(httpRequest, Constants.JWT_TOKEN_KEY)));
		} catch (Exception ex) {
			TrackTraceResponse = UtilityMethods.handleException(ex);
		}
		logger.info("Leaving from deleteShipmentData " + TrackTraceResponse);
		return TrackTraceResponse;
	}

	/**
	 * @param searchCriteria
	 * @return
	 */
	@PostMapping("/searchShipmentData")
	@ResponseBody
	public TrackTraceResponse search(@RequestBody SearchParameters searchCriteria, HttpServletRequest httpRequest) {
		logger.info("Entering in to search" + searchCriteria);
		TrackTraceResponse TrackTraceResponse = null;
		try {
			TrackTraceResponse = BusinessService.searchShipmentData(searchCriteria,
					jwtUtils.getGroupNameFromJwtToken(getHeaderKey(httpRequest, Constants.JWT_TOKEN_KEY)));
		} catch (Exception ex) {
			TrackTraceResponse = UtilityMethods.handleException(ex);
		}
		logger.info("Leaving from search " + TrackTraceResponse);
		return TrackTraceResponse;
	}

	@PostMapping("/searchExcelUploadInfo")
	@ResponseBody
	public TrackTraceResponse searchExcelUploadInfo(@RequestBody SearchParameters searchCriteria,
			HttpServletRequest httpRequest) {
		logger.info("Entering in to searchExcelUploadInfo" + searchCriteria);
		TrackTraceResponse TrackTraceResponse = null;
		try {
			TrackTraceResponse = BusinessService.searchExcelUploadInfo(searchCriteria,
					jwtUtils.getUserUUIDJwtToken(getHeaderKey(httpRequest, Constants.JWT_TOKEN_KEY)));
		} catch (Exception ex) {
			TrackTraceResponse = UtilityMethods.handleException(ex);
		}
		logger.info("Leaving from searchExcelUploadInfo " + TrackTraceResponse);
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