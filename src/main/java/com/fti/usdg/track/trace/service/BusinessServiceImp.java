/**
 * 
 */
package com.fti.usdg.track.trace.service;

import java.util.ArrayList;
 
import java.util.HashSet;
import java.util.List;
 
import java.util.Optional;
import java.util.UUID;

import com.fti.usdg.track.trace.repository.FeatureRepository;
import com.fti.usdg.track.trace.repository.FeedFileUploadRepository;
import com.fti.usdg.track.trace.repository.ShipmentDataRepository;
import com.fti.usdg.track.trace.repository.ShipmentDataVersionsRepository;
import com.fti.usdg.track.trace.repository.UserGroupRepository;
import com.fti.usdg.track.trace.repository.UserRepository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpMethod;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fti.usdg.track.trace.common.AppCacheUtils;
import com.fti.usdg.track.trace.common.Constants;
import com.fti.usdg.track.trace.common.FtiHelper;
import com.fti.usdg.track.trace.common.TrackTraceResponse;
import com.fti.usdg.track.trace.common.UtilityMethods;
import com.fti.usdg.track.trace.dto.DeleteShipmentReq;
import com.fti.usdg.track.trace.dto.Filter;
import com.fti.usdg.track.trace.dto.FilterDataDto;
import com.fti.usdg.track.trace.dto.LoggedInUserGroup;
import com.fti.usdg.track.trace.dto.SaveLedgerRequest;
import com.fti.usdg.track.trace.dto.SaveLedgerResponse;
import com.fti.usdg.track.trace.dto.SearchParameters;
import com.fti.usdg.track.trace.dto.ShipmentDataDto;
import com.fti.usdg.track.trace.dto.ShipmentDataVersionsDto;
import com.fti.usdg.track.trace.dto.UserDataHolder;
import com.fti.usdg.track.trace.dto.UserDetailsDto;
import com.fti.usdg.track.trace.models.ApplicationFeature;
import com.fti.usdg.track.trace.models.ERole;
import com.fti.usdg.track.trace.models.FeedFileUploadDetails;
import com.fti.usdg.track.trace.models.LabDataEntity;
import com.fti.usdg.track.trace.models.Role;
import com.fti.usdg.track.trace.models.ShipmentDataEntity;
import com.fti.usdg.track.trace.models.ShipmentDataVersions;
import com.fti.usdg.track.trace.models.User;
import com.fti.usdg.track.trace.models.UserGroup;
import java.util.Set;
import com.fti.usdg.track.trace.repository.LabDataRepository;
import com.fti.usdg.track.trace.repository.RoleRepository;

/**
 * @author Anup
 *
 */
@Service
public class BusinessServiceImp implements BusinessService {

	private static final Logger logger = LoggerFactory.getLogger(BusinessServiceImp.class);

	@Autowired
	private FtiHelper FtiHelper = null;
	@Autowired
	private ShipmentDataRepository ShipmentDataRepository = null;
	@Autowired
	private FeedFileUploadRepository FeedFileUploadRepository = null;
	@Autowired
	private UtilityMethods UtilityMethods = null;
	@Autowired
	private ShipmentDataVersionsRepository VersionsRepository = null;
	@Autowired
	private ObjectMapper objectMapper = null;
	@Autowired
	private ApplicationRestClient ApplicationRestClient = null;
	@Autowired
	private LabDataRepository LabDataRepository = null;
	@Autowired
	private UserGroupRepository UserGroupRepository = null;
	@Autowired
	private FeatureRepository FeatureRepository = null;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private PasswordEncoder encoder;

	@Override
	public TrackTraceResponse saveUser(UserDetailsDto UserDetails, String userUUID) {
		TrackTraceResponse TrackTraceResponse = null;
		Boolean userExists = false;
		Integer NoOfUser = null;
		if (UserDetails != null && UserDetails.getGroupName() != null && UserDetails.getUsername() != null
				&& UserDetails.getEmail() != null && UserDetails.getUsername() != null) {
			UserGroup NewUserGroup = UserGroupRepository.findByGroupName(UserDetails.getGroupName());
			if (NewUserGroup != null) {
				NoOfUser = NewUserGroup.getNoOfUserAttached();
				if (UserDetails.getId() == null) {
					if (userRepository.existsByUsername(UserDetails.getUsername())) {
						TrackTraceResponse = new TrackTraceResponse(Constants.RESPONSE_CODE_500,
								"Error: Username is already taken!");
						userExists = true;
					}
					if (userRepository.existsByUsername(UserDetails.getEmail())) {
						TrackTraceResponse = new TrackTraceResponse(Constants.RESPONSE_CODE_500,
								"Error: Email is already in use!");
						userExists = true;
					}
					if (!userExists) {
						String password = Constants.DEFAULT_PASSWORD;
						User user = new User(UserDetails.getUsername(), UserDetails.getEmail(),
								encoder.encode(password), UUID.randomUUID().toString(), UserDetails.getGroupName());
						Set<Role> roles = new HashSet<>();
						Role userRole = roleRepository.findByName(ERole.ROLE_USER)
								.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
						roles.add(userRole);
						user.setRoles(roles);
						user.setStatus(Constants.ACTIVE);
						user.setUpdatedAt(UtilityMethods.getCurrentDate());
						user.setUpdatedBy(userUUID);
						userRepository.save(user);
						++NoOfUser;
					}
				} else if (UserDetails.getId() != null) {
					Optional<UserGroup> userFromDbOps = UserGroupRepository.findById(UserDetails.getId());
					if (userFromDbOps.isPresent()) {
						UserGroup UserGroupFromDb = userFromDbOps.get();
						if (!UserDetails.getGroupName().equalsIgnoreCase(UserGroupFromDb.getGroupName())) {
							++NoOfUser;
						}
						UserGroupFromDb.setNoOfUserAttached(UserGroupFromDb.getNoOfUserAttached() - 1);
						UserGroupFromDb.setUpdatedAt(UtilityMethods.getCurrentDate());
						UserGroupFromDb.setUpdatedBy(userUUID);
						UserGroupRepository.save(UserGroupFromDb);
					}
					Optional<User> userFromDbOp = userRepository.findById(UserDetails.getId());
					User userFromDb = userFromDbOp.get();
					userFromDb.setEmail(UserDetails.getEmail());
					userFromDb.setGroupName(UserDetails.getGroupName());
					userFromDb.setUpdatedAt(UtilityMethods.getCurrentDate());
					userFromDb.setUpdatedBy(userUUID);
					if(UserDetails.getStatus()!=null) {
						userFromDb.setStatus(Constants.ACTIVE);
					}
					userRepository.save(userFromDb);
					TrackTraceResponse = new TrackTraceResponse(Constants.RESPONSE_CODE_200,
							"Save Operation perfomed successfully!");
				}
				NewUserGroup.setUpdatedAt(UtilityMethods.getCurrentDate());
				NewUserGroup.setUpdatedBy(userUUID);
				NewUserGroup.setNoOfUserAttached(NoOfUser);
				UserGroupRepository.save(NewUserGroup);
				TrackTraceResponse = new TrackTraceResponse(Constants.RESPONSE_CODE_200,
						Constants.RESULT_SAVEED_SUCCESSFULLY);
			} else {
				TrackTraceResponse = new TrackTraceResponse(Constants.RESPONSE_CODE_500,
						Constants.MANDATORY_PARAM_MISSING);
			}
		} else {
			TrackTraceResponse = new TrackTraceResponse(Constants.RESPONSE_CODE_500, Constants.MANDATORY_PARAM_MISSING);
		}
		return TrackTraceResponse;
	}

	@Override
	public TrackTraceResponse getFeatures(String userUUID) {
		logger.info("start getFeatures");
		TrackTraceResponse TrackTraceResponse = null;
		List<ApplicationFeature> list = FeatureRepository.findByStatus(Constants.ACTIVE);
		if (list != null && list.size() > 0) {
			TrackTraceResponse = new TrackTraceResponse(Constants.RESPONSE_CODE_200, Constants.RESULT_FOUND, list);
		} else {
			TrackTraceResponse = new TrackTraceResponse(Constants.RESPONSE_CODE_500, Constants.RESULT_NOT_FOUND);
		}
		return TrackTraceResponse;
	}

	@Override
	public TrackTraceResponse saveLabData(LabDataEntity labDataEntity, String userUUID) {
		logger.info("start saveLabData" + labDataEntity);
		TrackTraceResponse TrackTraceResponse = null;
		try {
			LabDataEntity ldFromDb = LabDataRepository.findByCustomerAndProductAndStatus(labDataEntity.getCustomer(),
					labDataEntity.getProduct(), Constants.ACTIVE);
			if (ldFromDb != null) {
				ldFromDb.setStatus(Constants.DELETED);
				ldFromDb.setUpdatedAt(UtilityMethods.getCurrentDate());
				ldFromDb.setUpdatedBy(userUUID);
				LabDataRepository.save(ldFromDb);
			}
			SaveLedgerRequest slrq = null;
			String dataAsString = objectMapper.writeValueAsString(labDataEntity);
			slrq = new SaveLedgerRequest("CreateLabData", dataAsString);
			String finalRequest = objectMapper.writeValueAsString(slrq);
			String callResponse = ApplicationRestClient.callAPI(finalRequest, Constants.SAVE_ENDPOINT, "NA",
					HttpMethod.POST, false);
			if (callResponse != null && callResponse.length() > 0 && callResponse.contains("successful")) {
				SaveLedgerResponse slr = objectMapper.readValue(callResponse, SaveLedgerResponse.class);
				if (slr.getSuccessful()) {
					labDataEntity.setTrasactionHash(slr.getTransactionId());
					labDataEntity.setCreatedAt(UtilityMethods.getCurrentDate());
					labDataEntity.setCreatedBy(userUUID);
					labDataEntity.setBlockNo(slr.getBlockNumber());
					ldFromDb.setStatus(Constants.ACTIVE);
					LabDataRepository.save(labDataEntity);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return TrackTraceResponse;
	}

	@Override
	public TrackTraceResponse saveShipmentData(ShipmentDataDto shipmentData, String userUUID)
			throws JsonProcessingException {
		TrackTraceResponse TrackTraceResponse = null;
		if (shipmentData != null && shipmentData.getBolNo() != null && shipmentData.getBolNo().length() > 0) {
			ShipmentDataEntity _shipmentDataEntity = ShipmentDataRepository.findByBolNo(shipmentData.getBolNo());
			if (_shipmentDataEntity == null) {
				_shipmentDataEntity = new ShipmentDataEntity();
			}
			copyShipmentDataToEntity(shipmentData, _shipmentDataEntity);
			_shipmentDataEntity.setUpdatedDate(UtilityMethods.getCurrentDate());
			_shipmentDataEntity.setUpdatedBy(userUUID);
			_shipmentDataEntity.setInputFileId(Constants.SCREEN);
			ShipmentDataRepository.save(_shipmentDataEntity);
			TrackTraceResponse = new TrackTraceResponse(Constants.RESPONSE_CODE_200,
					Constants.RESULT_SAVEED_SUCCESSFULLY);
		} else {
			TrackTraceResponse = new TrackTraceResponse(Constants.RESPONSE_CODE_500,
					Constants.OPS_FAILED + ": Bol No is mandatory.");
		}
		return TrackTraceResponse;
	}

	public TrackTraceResponse search(SearchParameters searchCriteria) {
		logger.debug("Entering into search SearchCriteria " + searchCriteria);
		TrackTraceResponse TrackTraceResponse = null;
		Pageable pageable = null;
		if (searchCriteria != null) {
			if (searchCriteria != null && searchCriteria.getPageNumber() != null
					&& searchCriteria.getPerPage() != null) {
				if (searchCriteria.getSortKey() != null && searchCriteria.getSortKey().getSortType() != null) {
					if (searchCriteria.getSortKey().getSortType().equalsIgnoreCase(Constants.DESC)) {
						pageable = PageRequest.of(searchCriteria.getPageNumber(), searchCriteria.getPerPage(),
								Sort.by(searchCriteria.getSortKey().getAttibName()).descending());
					} else {
						pageable = PageRequest.of(searchCriteria.getPageNumber(), searchCriteria.getPerPage(),
								Sort.by(searchCriteria.getSortKey().getAttibName()).ascending());
					}
				} else {
					pageable = PageRequest.of(searchCriteria.getPageNumber(), searchCriteria.getPerPage(),
							Sort.by("updatedDate").descending());
				}
			} else {
				pageable = PageRequest.of(0, 20, Sort.by("updatedDate").descending());
			}
			if (searchCriteria.getLookup().equalsIgnoreCase(Constants.SHIPMENT_DATA)) {
				Specification<ShipmentDataEntity> Specification = new Specification<ShipmentDataEntity>() {
					@Override
					public Predicate toPredicate(Root<ShipmentDataEntity> root, CriteriaQuery<?> query,
							CriteriaBuilder criteriaBuilder) {
						logger.debug(" Entering into toPredicate ");
						List<Predicate> predicates = FtiHelper.addPredicate(searchCriteria, root, criteriaBuilder);
						return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
					}
				};
				TrackTraceResponse = FtiHelper.prepareResponse(
						ShipmentDataRepository.findAll(Specification, pageable).getContent(),
						ShipmentDataRepository.count(Specification));
			} else if (searchCriteria.getLookup().equalsIgnoreCase(Constants.EXCEL_UPLOAD)) {
				Specification<FeedFileUploadDetails> Specification = new Specification<FeedFileUploadDetails>() {

					@Override
					public Predicate toPredicate(Root<FeedFileUploadDetails> root, CriteriaQuery<?> query,
							CriteriaBuilder criteriaBuilder) {
						logger.debug(" Entering into toPredicate ");
						List<Predicate> predicates = FtiHelper.addPredicate(searchCriteria, root, criteriaBuilder);
						return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
					}

				};
				TrackTraceResponse = FtiHelper.prepareResponse(
						FeedFileUploadRepository.findAll(Specification, pageable).getContent(),
						FeedFileUploadRepository.count(Specification));
			} else if (searchCriteria.getLookup().equalsIgnoreCase(Constants.GROUP_LIST)) {
				Specification<UserGroup> Specification = new Specification<UserGroup>() {
					@Override
					public Predicate toPredicate(Root<UserGroup> root, CriteriaQuery<?> query,
							CriteriaBuilder criteriaBuilder) {
						logger.debug(" Entering into toPredicate ");
						List<Predicate> predicates = FtiHelper.addPredicate(searchCriteria, root, criteriaBuilder);
						return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
					}
				};
				TrackTraceResponse = FtiHelper.prepareResponse(
						UserGroupRepository.findAll(Specification, pageable).getContent(),
						UserGroupRepository.count(Specification));
			} else if (searchCriteria.getLookup().equalsIgnoreCase(Constants.USER_LIST)) {
				Specification<User> Specification = new Specification<User>() {
					@Override
					public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query,
							CriteriaBuilder criteriaBuilder) {
						logger.debug(" Entering into toPredicate ");
						List<Predicate> predicates = FtiHelper.addPredicate(searchCriteria, root, criteriaBuilder);
						return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
					}
				};
				TrackTraceResponse = FtiHelper.prepareResponse(
						userRepository.findAll(Specification, pageable).getContent(),
						userRepository.count(Specification));
			} else {
				TrackTraceResponse = FtiHelper.prepareResponse(null, null);
			}
		} else {
			TrackTraceResponse = FtiHelper.prepareResponse(null, null);
		}
		logger.debug("Leaving from into search pdrq " + TrackTraceResponse);
		return TrackTraceResponse;
	}

	@Override
	public TrackTraceResponse getShipmentDataByUuid(String uuid, String userUUID) {
		TrackTraceResponse response = null;
		boolean isRecodFound = false;
		List<ShipmentDataEntity> _respList = null;
		if (uuid != null && uuid.length() > 0) {
			ShipmentDataEntity ShipmentData = ShipmentDataRepository.findByLedgerUuid(uuid);
			if (ShipmentData != null) {
				isRecodFound = true;
				_respList = new ArrayList<ShipmentDataEntity>();
				_respList.add(ShipmentData);
				response = new TrackTraceResponse(Constants.RESPONSE_CODE_200, Constants.RESULT_FOUND, _respList);
			}
		}
		if (!isRecodFound) {
			response = new TrackTraceResponse(Constants.RESPONSE_CODE_500, Constants.RESULT_NOT_FOUND);
		}
		return response;
	}

	@Override
	public TrackTraceResponse deleteShipmentData(DeleteShipmentReq deleteShipmentData, String userUUID) {
		TrackTraceResponse response = null;
		boolean isRecodFound = false;
		ShipmentDataEntity ShipmentData = null;
		if (deleteShipmentData != null && deleteShipmentData.getLedgerUuid() != null
				&& deleteShipmentData.getLedgerUuid().length() > 0) {
			ShipmentData = ShipmentDataRepository.findByLedgerUuid(deleteShipmentData.getLedgerUuid());
			if (ShipmentData != null) {
				ShipmentData.setStatus(Constants.DELETED);
				ShipmentData.setLedgerSyncFlag(Constants.No);
				ShipmentData.setUpdatedBy(userUUID);
				ShipmentDataRepository.save(ShipmentData);
				response = new TrackTraceResponse(Constants.RESPONSE_CODE_200, Constants.RECORD_DELETED);
				isRecodFound = true;

			}
		} else if (deleteShipmentData != null && deleteShipmentData.getBolNumber() != null
				&& deleteShipmentData.getBolNumber().length() > 0) {
			ShipmentData = ShipmentDataRepository.findByBolNo(deleteShipmentData.getBolNumber());
			if (ShipmentData != null) {
				ShipmentData.setStatus(Constants.DELETED);
				ShipmentData.setLedgerSyncFlag(Constants.No);
				ShipmentData.setUpdatedBy(userUUID);
				ShipmentDataRepository.save(ShipmentData);
				response = new TrackTraceResponse(Constants.RESPONSE_CODE_200, Constants.RECORD_DELETED);
				isRecodFound = true;
			}
		}
		if (!isRecodFound) {
			response = new TrackTraceResponse(Constants.RESPONSE_CODE_500, Constants.RESULT_NOT_FOUND);
		}
		return response;
	}

	@Override
	public TrackTraceResponse getShipmentDataVersions(String bolNumber, String userUUID)
			throws JsonMappingException, JsonProcessingException {
		TrackTraceResponse response = null;
		Boolean dataPresent = false;
		List<ShipmentDataVersionsDto> retunType = null;
		if (bolNumber != null && bolNumber.length() > 0) {
			List<ShipmentDataVersions> versions = VersionsRepository.findByBolNoOrderByVersionDesc(bolNumber);
			if (versions != null && versions.size() > 0) {
				retunType = new ArrayList<ShipmentDataVersionsDto>();
				for (ShipmentDataVersions sdv : versions) {
					retunType.add(mapToDto(sdv));
				}
				response = new TrackTraceResponse(Constants.RESPONSE_CODE_200, Constants.RESULT_FOUND, retunType);
				dataPresent = true;
			}
		}
		if (!dataPresent) {
			response = new TrackTraceResponse(Constants.RESPONSE_CODE_500, Constants.RESULT_NOT_FOUND);
		}
		return response;
	}

	private ShipmentDataVersionsDto mapToDto(ShipmentDataVersions sdv)
			throws JsonMappingException, JsonProcessingException {
		ShipmentDataVersionsDto dto = new ShipmentDataVersionsDto();
		dto.setBlockNo(sdv.getBlockNo());
		dto.setBolNo(sdv.getBolNo());
		dto.setChannelName(sdv.getChannelName());
		dto.setCreatedAt(sdv.getCreatedAt());
		dto.setData(objectMapper.readValue(sdv.getData(), ShipmentDataDto.class));
		dto.setTransactionHash(sdv.getTransactionHash());
		dto.setVersion(sdv.getVersion());
		return dto;
	}

	@Override
	public TrackTraceResponse searchExcelUploadInfo(SearchParameters searchCriteria, String userUUID) {
		searchCriteria.setLookup(Constants.EXCEL_UPLOAD);
		TrackTraceResponse TrackTraceResponse = search(searchCriteria);
		return TrackTraceResponse;
	}

	@SuppressWarnings("unchecked")
	@Override
	public TrackTraceResponse searchShipmentData(SearchParameters searchCriteria, String userGroup) {
		logger.debug("Entering into searchShipmentData");
		searchCriteria.setLookup(Constants.SHIPMENT_DATA);
		String shipperDetails = (String)AppCacheUtils.getValue(Constants.USER_GROUP_SHIPPER+userGroup);
		if(shipperDetails!=null && shipperDetails.contains(Constants.TILDE)) {
			List<Filter> filters = searchCriteria.getFilters();
			filters.add(new Filter("moreShipper",shipperDetails));
		}else if(shipperDetails!=null && !shipperDetails.equalsIgnoreCase(Constants.All)){
			List<Filter> filters = searchCriteria.getFilters();
			filters.add(new Filter("shipper",shipperDetails));
		}
		TrackTraceResponse TrackTraceResponse = search(searchCriteria);
		logger.debug("Leaving from searchShipmentData");
		return TrackTraceResponse;
	}

	private ShipmentDataEntity copyShipmentDataToEntity(ShipmentDataDto shipmentData,
			ShipmentDataEntity _shipmentDataEntity) {
		LabDataEntity ldFromDb = LabDataRepository.findByCustomerAndProductAndStatus(shipmentData.getShipper(),
				shipmentData.getProduct(), Constants.ACTIVE);
		if (ldFromDb != null) {
			_shipmentDataEntity.setDotClass(ldFromDb.getDotClass());
			_shipmentDataEntity.setHazardWarning(ldFromDb.getHazardWarning());
			_shipmentDataEntity.setBswPerc(ldFromDb.getBswPercentage());
		} else {
			_shipmentDataEntity.setDotClass(shipmentData.getDotClass());
			_shipmentDataEntity.setHazardWarning(shipmentData.getHazardWarning());
			_shipmentDataEntity.setBswPerc(shipmentData.getBswPerc());
		}
		_shipmentDataEntity.setShipper(shipmentData.getShipper());
		_shipmentDataEntity.setStccCode(shipmentData.getStccCode());
		_shipmentDataEntity.setUnNaNo(shipmentData.getUnNaNo());
		_shipmentDataEntity.setProduct(shipmentData.getProduct());
		_shipmentDataEntity.setPackingGroup(shipmentData.getPackingGroup());
		_shipmentDataEntity.setCrudeClassification(shipmentData.getCrudeClassification());
		_shipmentDataEntity.setShipDate(shipmentData.getShipDate());
		_shipmentDataEntity.setUnitTrainNo(shipmentData.getUnitTrainNo());
		_shipmentDataEntity.setBolNo(shipmentData.getBolNo());
		_shipmentDataEntity.setErpPiu(shipmentData.getErpPiu());
		_shipmentDataEntity.setDensity(shipmentData.getDensity());
		_shipmentDataEntity.setUnitTrainSize(shipmentData.getUnitTrainSize());
		_shipmentDataEntity.setCommodityName(shipmentData.getCommodityName());
		_shipmentDataEntity.setEraflashD93b(shipmentData.getEraflashD93b());
		_shipmentDataEntity.setCreatedDate(UtilityMethods.getCurrentDate());
		_shipmentDataEntity.setInputFileId(Constants.SCREEN);
		_shipmentDataEntity.setLedgerSyncFlag(Constants.No);
		_shipmentDataEntity.setLedgerUuid(UUID.randomUUID().toString());
		_shipmentDataEntity.setStatus(Constants.ACTIVE);
		_shipmentDataEntity.setKeyword(getKeywordSearchText(shipmentData));
		return _shipmentDataEntity;
	}

	private String getKeywordSearchText(ShipmentDataDto shipmentData) {

		StringBuilder keyword = new StringBuilder();
		keyword.append(shipmentData.getShipper()).append(Constants.SPACE);
		keyword.append(shipmentData.getDotClass()).append(Constants.SPACE);
		keyword.append(shipmentData.getHazardWarning()).append(Constants.SPACE);
		keyword.append(shipmentData.getStccCode()).append(Constants.SPACE);
		keyword.append(shipmentData.getUnNaNo()).append(Constants.SPACE);
		keyword.append(shipmentData.getProduct()).append(Constants.SPACE);
		keyword.append(shipmentData.getPackingGroup()).append(Constants.SPACE);
		keyword.append(shipmentData.getCrudeClassification()).append(Constants.SPACE);
		keyword.append(shipmentData.getShipDate()).append(Constants.SPACE);
		keyword.append(shipmentData.getUnitTrainNo()).append(Constants.SPACE);
		keyword.append(shipmentData.getBolNo()).append(Constants.SPACE);
		keyword.append(shipmentData.getErpPiu()).append(Constants.SPACE);
		keyword.append(shipmentData.getBswPerc()).append(Constants.SPACE);
		keyword.append(shipmentData.getDensity()).append(Constants.SPACE);
		keyword.append(shipmentData.getUnitTrainSize()).append(Constants.SPACE);
		keyword.append(shipmentData.getCommodityName()).append(Constants.SPACE);
		keyword.append(shipmentData.getEraflashD93b()).append(Constants.SPACE);
		return keyword.toString();

	}

	@Override
	public TrackTraceResponse getShipmentDataByBolNo(String bolNumber, String userUUID) {
		TrackTraceResponse response = null;
		boolean isRecodFound = false;
		List<ShipmentDataEntity> _respList = null;
		if (bolNumber != null && bolNumber.length() > 0) {
			ShipmentDataEntity ShipmentData = ShipmentDataRepository.findByBolNo(bolNumber);
			if (ShipmentData != null) {
				isRecodFound = true;
				_respList = new ArrayList<ShipmentDataEntity>();
				_respList.add(ShipmentData);
				response = new TrackTraceResponse(Constants.RESPONSE_CODE_200, Constants.RESULT_FOUND, _respList);
			}
		}
		if (!isRecodFound) {
			response = new TrackTraceResponse(Constants.RESPONSE_CODE_500, Constants.RESULT_NOT_FOUND);
		}
		return response;
	}

	// API for All shippers, all Commodity Name, all Crude Type
	@Override
	public TrackTraceResponse getFilterData(String userUUID) {
		TrackTraceResponse response = null;
		FilterDataDto fdDto = new FilterDataDto();
		fdDto.setCommodityNames(ShipmentDataRepository.getDistinctCommodityNames());
		fdDto.setShippers(ShipmentDataRepository.getDistinctShhippers());
		fdDto.setCrudTypes(ShipmentDataRepository.getDistinctCrudeTypes());
		List<FilterDataDto> fdDtos = new ArrayList<FilterDataDto>();
		fdDtos.add(fdDto);
		response = new TrackTraceResponse(Constants.RESPONSE_CODE_200, Constants.RESULT_FOUND, fdDtos);
		return response;
	}

	@Override
	public TrackTraceResponse saveUserGroup(UserGroup userGroup, String userUUID) {
		TrackTraceResponse response = null;
		logger.debug(" Entering into saveUserGroup " + userGroup);
		if (userGroup != null && userGroup.getId() != null) {
			if(userGroup.getNoOfUserAttached()==null) {
				userGroup.setNoOfUserAttached(0);
			}
			if(userGroup.getStatus()==null) {
				userGroup.setStatus(Constants.ACTIVE);
			}
		} else if (userGroup != null) {
			userGroup.setCreatedAt(UtilityMethods.getCurrentDate());
			userGroup.setCreatedByValue(userUUID);
			userGroup.setNoOfUserAttached(0);
			userGroup.setStatus(Constants.ACTIVE);
		}
		userGroup.setUpdatedAt(UtilityMethods.getCurrentDate());
		userGroup.setUpdatedByValue(userUUID);
		UserGroupRepository.save(userGroup);
		response = new TrackTraceResponse(Constants.RESPONSE_CODE_200, Constants.RESULT_SAVEED_SUCCESSFULLY);
		FtiHelper.refreshCache();
		return response;
	}

	@Override
	public TrackTraceResponse getUserGroup(SearchParameters searchCriteria, String userUUID) {
		logger.debug("Entering into getUserGroup");
		searchCriteria.setLookup(Constants.GROUP_LIST);
		TrackTraceResponse TrackTraceResponse = search(searchCriteria);
		logger.debug("Leaving from getUserGroup");
		return TrackTraceResponse;

	}

	@Override
	public TrackTraceResponse deleteUserGroup(UserDataHolder userDataHolder, String userUUID) {
		List<UserGroup> groups = null;
		TrackTraceResponse response = null;
		if (userDataHolder != null && userDataHolder.getGroupName() != null) {
			groups = UserGroupRepository.findByStatusAndGroupName(Constants.ACTIVE, userDataHolder.getGroupName());
			UserGroup UserGroup = groups.get(0);
			if (groups != null && groups.size() > 0 && UserGroup.getNoOfUserAttached() < 1) {
				UserGroup.setStatus(Constants.DELETED);
				UserGroup.setUpdatedAt(UtilityMethods.getCurrentDate());
				UserGroup.setUpdatedBy(userUUID);
				UserGroupRepository.save(UserGroup);
				response = new TrackTraceResponse(Constants.RESPONSE_CODE_200, Constants.RESULT_DELETED_SUCCESSFULLY);
			} else {
				response = new TrackTraceResponse(Constants.RESPONSE_CODE_500, Constants.RESULT_NOT_FOUND);
			}
		}
		return response;
	}

	@Override
	public TrackTraceResponse getUserDetails(SearchParameters searchCriteria, String userUUID) {
		logger.debug("Entering into getUserDetails");
		searchCriteria.setLookup(Constants.USER_LIST);
		TrackTraceResponse TrackTraceResponse = search(searchCriteria);
		logger.debug("Leaving from getUserDetails");
		return TrackTraceResponse;

	}

	@Override
	public TrackTraceResponse getShippers() {
		TrackTraceResponse response = null;
		logger.debug(" Entering into getUserDetails ");
		Set<String> shippers = ShipmentDataRepository.getDistinctShhippers();
		if (shippers != null) {
			response = new TrackTraceResponse(Constants.RESPONSE_CODE_200, Constants.RESULT_FOUND, shippers);
		} else {
			response = new TrackTraceResponse(Constants.RESPONSE_CODE_500, Constants.RESULT_NOT_FOUND);
		}
		return response;
	}

	@Override
	public TrackTraceResponse getUserGroups(String GroupName, String userUUID) {
		TrackTraceResponse response = null;
		List<UserGroup> groups = null;
		logger.debug(" Entering into getUserGroup " + GroupName);
		if (GroupName != null && GroupName.equalsIgnoreCase(Constants.All)) {
			groups = UserGroupRepository.findByStatus(Constants.ACTIVE);
		} else if (GroupName != null) {
			groups = UserGroupRepository.findByStatusAndGroupName(Constants.ACTIVE, GroupName);
		}
		if (groups != null) {
			response = new TrackTraceResponse(Constants.RESPONSE_CODE_200, Constants.RESULT_FOUND, groups);
		} else {
			response = new TrackTraceResponse(Constants.RESPONSE_CODE_500, Constants.RESULT_NOT_FOUND);
		}
		return response;
	}

    @Override
	public TrackTraceResponse deleteUser(UserDataHolder userDataHolder, String userUUID) {
    	User User = null;
		TrackTraceResponse response = null;
		if (userDataHolder != null && userDataHolder.getUserUUID() != null) {
			User = userRepository.findByUuid(userDataHolder.getUserUUID());
			if (User != null) {
				User.setStatus(Constants.DELETED);
				User.setUpdatedAt(UtilityMethods.getCurrentDate());
				User.setUpdatedBy(userUUID);
				userRepository.save(User);
				response = new TrackTraceResponse(Constants.RESPONSE_CODE_200, Constants.RESULT_DELETED_SUCCESSFULLY);
			} else {
				response = new TrackTraceResponse(Constants.RESPONSE_CODE_500, Constants.RESULT_NOT_FOUND);
			}
		}
		return response;
	}

	@Override
	public TrackTraceResponse changeLoggedInUserGroup(LoggedInUserGroup loggedInUserGroup, String userUUIDJwtToken) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
