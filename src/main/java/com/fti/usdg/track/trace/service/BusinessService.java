/**
 * 
 */
package com.fti.usdg.track.trace.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fti.usdg.track.trace.common.TrackTraceResponse;
import com.fti.usdg.track.trace.dto.DeleteShipmentReq;
import com.fti.usdg.track.trace.dto.LoggedInUserGroup;
import com.fti.usdg.track.trace.dto.SearchParameters;
import com.fti.usdg.track.trace.dto.ShipmentDataDto;
import com.fti.usdg.track.trace.dto.UserDataHolder;
import com.fti.usdg.track.trace.dto.UserDetailsDto;
import com.fti.usdg.track.trace.models.LabDataEntity;
import com.fti.usdg.track.trace.models.UserGroup;

/**
 * @author Anup
 *
 */
public interface BusinessService {
 
	/**
	 * @param uuid
	 * @param userUUID
	 * @return
	 */
	public TrackTraceResponse getShipmentDataByUuid(String uuid,String userUUID);

	/**
	 * @param shipmentData
	 * @param userUUID
	 * @return
	 * @throws JsonProcessingException
	 */
	public TrackTraceResponse saveShipmentData(ShipmentDataDto shipmentData,String userUUID) throws JsonProcessingException;

	/**
	 * @param deleteShipmentData
	 * @param userUUID
	 * @return
	 */
	public TrackTraceResponse deleteShipmentData(DeleteShipmentReq deleteShipmentData,String userUUID);

	/**
	 * @param uuid
	 * @param userUuid
	 * @return
	 * @throws JsonMappingException
	 * @throws JsonProcessingException
	 */
	public TrackTraceResponse getShipmentDataVersions(String uuid, String userUuid) throws JsonMappingException, JsonProcessingException;

	/**
	 * @param searchCriteria
	 * @param userUUID
	 * @return
	 */
	public TrackTraceResponse searchExcelUploadInfo(SearchParameters searchCriteria, String userUUID);

	/**
	 * @param searchCriteria
	 * @param userUUID
	 * @return
	 */
	public TrackTraceResponse searchShipmentData(SearchParameters searchCriteria, String userUUID);

	/**
	 * @param bolNumber
	 * @param userUUID
	 * @return
	 */
	public TrackTraceResponse getShipmentDataByBolNo(String bolNumber, String userUUID);

	/**
	 * @param userUUID
	 * @return
	 */
	public TrackTraceResponse getFilterData(String userUUID);

	/**
	 * @param labDataEntity
	 * @param userUUID
	 * @return
	 */
	public TrackTraceResponse saveLabData(LabDataEntity labDataEntity, String userUUID);

	/**
	 * @param userGroup
	 * @param userUUID
	 * @return
	 */
	public TrackTraceResponse saveUserGroup(UserGroup userGroup, String userUUID);

	/**
	 * @param userUUID
	 * @return
	 */
	public TrackTraceResponse getFeatures(String userUUID);

	/**
	 * @param user
	 * @param userUUID
	 * @return
	 */
	public TrackTraceResponse saveUser(UserDetailsDto user, String userUUID);

	/**
	 * @param groupId
	 * @param userUUID
	 * @return
	 */
	public TrackTraceResponse getUserGroup(SearchParameters searchCriteria, String userUUID);

	/**
	 * @param userDataHolder
	 * @param userUUID
	 * @return
	 */
	public TrackTraceResponse deleteUserGroup(UserDataHolder userDataHolder, String userUUID);

	/**
	 * @param userId
	 * @param userUUIDJwtToken
	 * @return
	 */
	public TrackTraceResponse getUserDetails(SearchParameters searchCriteria , String userUUIDJwtToken);

	/**
	 * @return
	 */
	public TrackTraceResponse getShippers();

	/**
	 * @param groupName
	 * @param userUUIDJwtToken
	 * @return
	 */
	public TrackTraceResponse getUserGroups(String groupName, String userUUIDJwtToken);

	/**
	 * @param userDataHolder
	 * @param userUUIDJwtToken
	 * @return
	 */
	public TrackTraceResponse deleteUser(UserDataHolder userDataHolder, String userUUIDJwtToken);

	/**
	 * @param loggedInUserGroup
	 * @param userUUIDJwtToken
	 * @return
	 */
	public TrackTraceResponse changeLoggedInUserGroup(LoggedInUserGroup loggedInUserGroup, String userUUIDJwtToken);

}
