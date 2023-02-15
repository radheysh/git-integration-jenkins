/**
 * 
 */
package com.fti.usdg.track.trace.common;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fti.usdg.track.trace.dto.SearchParameters;
import com.fti.usdg.track.trace.dto.SearchResultWrapper;
import com.fti.usdg.track.trace.models.UserGroup;
import com.fti.usdg.track.trace.repository.UserGroupRepository;
import com.fti.usdg.track.trace.dto.Filter;

/**
 * @author Anup
 *
 */
@Service
public class FtiHelper {

	private static final Logger logger = LoggerFactory.getLogger(FtiHelper.class);
	
	@Autowired
	private UserGroupRepository UserGroupRepository = null;
	
	public void refreshCache() {
		List<UserGroup>  ugList = UserGroupRepository.findByStatus(Constants.ACTIVE);
		if(ugList!=null && ugList.size()>0) {
			for(UserGroup UserGroup : ugList) {
				AppCacheUtils.putValue(Constants.USER_GROUP_NAME+UserGroup.getGroupName(), UserGroup.getFeatureIds());
				AppCacheUtils.putValue(Constants.USER_GROUP_OBJECT+UserGroup.getGroupName(), UserGroup);
				if(UserGroup.getShipper()!=null) {
					AppCacheUtils.putValue(Constants.USER_GROUP_SHIPPER+UserGroup.getGroupName(), UserGroup.getShipper());
				}
			}
		}
	}

	public List<Predicate> addPredicate(SearchParameters SearchCriteria, Root<?> root,
			CriteriaBuilder criteriaBuilder) {
		String startDate = null;
		String endDate = null;
		String name = null;
		logger.debug(" addPredicate " + SearchCriteria);
		List<Predicate> predicates = new ArrayList<>();
		if (SearchCriteria != null && SearchCriteria.getFilters() != null && SearchCriteria.getFilters().size() > 0) {
			logger.debug(" Entering into addPredicate ");
			for (Filter Parameter : SearchCriteria.getFilters()) {
				if (Parameter != null && Parameter.getType() != null) {
					if (Parameter.getType().equalsIgnoreCase("START_DATE")) {
						startDate = Parameter.getValue() + Constants.START_TS;
						name = Parameter.getName();
					} else if (Parameter.getType() != null && Parameter.getType().equalsIgnoreCase("END_DATE")) {
						endDate = Parameter.getValue() + Constants.END_TS;
					} else if (Parameter.getType() != null
							&& Parameter.getType().equalsIgnoreCase(Constants.EXACT_MATCH)
							&& Parameter.getName() != null && Parameter.getValue() != null
							&& Parameter.getValue().trim().length() > 0) {
						logger.debug(Parameter.getType() + " Parameter.getType() " + Parameter.getName()
								+ Parameter.getValue());
						predicates.add(criteriaBuilder.and(
								criteriaBuilder.equal(root.get(Parameter.getName()), Parameter.getValue().trim())));
					} else if (Parameter.getType() != null && Parameter.getType().equalsIgnoreCase("STATUS")) {
						logger.debug(Parameter.getType() + " Parameter.getType() " + Parameter.getName()
								+ Parameter.getValue());
						predicates.add(criteriaBuilder.and(
								criteriaBuilder.equal(root.get(Parameter.getName()), Parameter.getValue().trim())));
					}

				} else if (Parameter.getName() != null && Parameter.getValue() != null
						&& Parameter.getName().equalsIgnoreCase("moreShipper")) {
					logger.debug(" Entering into Parameter.getName() " + Parameter.getName() + Parameter.getValue());
					String shippers[] = Parameter.getValue().split(Constants.TILDE);
					if (shippers.length == 2) {
						predicates.add(criteriaBuilder.or(criteriaBuilder.equal(root.get("shipper"), shippers[0]),
								criteriaBuilder.equal(root.get("shipper"), shippers[1])));
					}
					if (shippers.length == 3) {
						predicates.add(criteriaBuilder.or(criteriaBuilder.equal(root.get("shipper"), shippers[0]),
								criteriaBuilder.equal(root.get("shipper"), shippers[1]),
								criteriaBuilder.equal(root.get("shipper"), shippers[2])));
					}
					if (shippers.length == 4) {
						predicates.add(criteriaBuilder.or(criteriaBuilder.equal(root.get("shipper"), shippers[0]),
								criteriaBuilder.equal(root.get("shipper"), shippers[1]),
								criteriaBuilder.equal(root.get("shipper"), shippers[2]),
								criteriaBuilder.equal(root.get("shipper"), shippers[3])));
					}
					if (shippers.length == 5) {
						predicates.add(criteriaBuilder.or(criteriaBuilder.equal(root.get("shipper"), shippers[0]),
								criteriaBuilder.equal(root.get("shipper"), shippers[1]),
								criteriaBuilder.equal(root.get("shipper"), shippers[2]),
								criteriaBuilder.equal(root.get("shipper"), shippers[3]),
								criteriaBuilder.equal(root.get("shipper"), shippers[4])));
					}
					if (shippers.length == 6) {
						predicates.add(criteriaBuilder.or(criteriaBuilder.equal(root.get("shipper"), shippers[0]),
								criteriaBuilder.equal(root.get("shipper"), shippers[1]),
								criteriaBuilder.equal(root.get("shipper"), shippers[2]),
								criteriaBuilder.equal(root.get("shipper"), shippers[3]),
								criteriaBuilder.equal(root.get("shipper"), shippers[4]),
								criteriaBuilder.equal(root.get("shipper"), shippers[5])));
					}

				} else if (Parameter.getName() != null && Parameter.getValue() != null
						&& Parameter.getValue().trim().length() > 0) {
					logger.debug(" Entering into Parameter.getName() " + Parameter.getName() + Parameter.getValue());
					predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get(Parameter.getName()),
							"%" + Parameter.getValue().trim() + "%")));
				}

				if (name != null && startDate != null && endDate != null) {
					predicates.add(criteriaBuilder
							.and(criteriaBuilder.between(root.get(Parameter.getName()), startDate, endDate)));
					startDate = null;
					endDate = null;
				}

			}
		}
		return predicates;
	}

	public TrackTraceResponse prepareResponse(List<?> resultList, Long totalCount) {
		TrackTraceResponse TrackTraceResponse = null;
		logger.debug(" Entering into prepareResponse " + totalCount);
		List<SearchResultWrapper> seearchResult = null;
		if (resultList != null && resultList.size() > 0) {
			seearchResult = new ArrayList<SearchResultWrapper>();
			seearchResult.add(new SearchResultWrapper(totalCount, resultList));
			TrackTraceResponse = new TrackTraceResponse(Constants.SUCCESS, Constants.RESULT_FOUND, seearchResult);
		} else {
			TrackTraceResponse = new TrackTraceResponse(Constants.RESPONSE_CODE_500, Constants.RESULT_NOT_FOUND);
		}
		logger.debug(" Entering into PriceManagerResponse " + TrackTraceResponse);
		return TrackTraceResponse;
	}

}
