package com.ztravel.member.dao.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.bson.types.ObjectId;
import org.codehaus.plexus.util.StringUtils;
import org.springframework.stereotype.Repository;

import com.github.jmkgreen.morphia.Datastore;
import com.github.jmkgreen.morphia.mapping.Mapper;
import com.github.jmkgreen.morphia.query.Query;
import com.github.jmkgreen.morphia.query.UpdateOperations;
import com.github.jmkgreen.morphia.query.UpdateResults;
import com.ztravel.member.dao.TravellerDao;
import com.ztravel.member.dto.TravellerDto;
import com.ztravel.member.po.TravelerEntity;

/**
 *
 * @author zuoning.shen
 */
@Repository("travelerDao")
public class TravellerDaoImpl implements TravellerDao {

    @Resource
    protected Datastore datastore;

    @Override
    public String save(TravelerEntity entity) {
        return datastore.save(entity).getId().toString();
    }

    @Override
    public void deleteById(String id) {
        UpdateOperations<TravelerEntity> ops = datastore.createUpdateOperations(TravelerEntity.class);
        ops.set("isActive", false);
        Query<TravelerEntity> query = datastore.createQuery(TravelerEntity.class);
        datastore.update(query.field(Mapper.ID_KEY).equal(new ObjectId(id)), ops);
    }

    @Override
    public TravelerEntity getById(String id) {
        return datastore.get(TravelerEntity.class, buildObjectId(id));
    }

    @Override
    public List<TravelerEntity> findByMemberId(String memberId, int page, int pageSize) {
        if (page < 1) {
            page = 1;
        }

        if (pageSize < 1) {
            pageSize = 10;
        }

        Query<TravelerEntity> lvQuery = datastore.createQuery(TravelerEntity.class);
        lvQuery.field("memberId").equal(memberId).field("isActive").equal(true);
        int offset = (page - 1) * pageSize;
        lvQuery.offset(offset);
        lvQuery.limit(pageSize);
        List<TravelerEntity> lvResult = lvQuery.asList();
        return lvResult;
    }

    @Override
    public int countByMemberId(String memberId) {
        Query<TravelerEntity> query = datastore.createQuery(TravelerEntity.class);
        query.field("memberId").equal(memberId).field("isActive").equal(true);
        return (int)query.countAll();
    }

    public ObjectId buildObjectId(String idStr){
        return new ObjectId(idStr);
    }

    @Override
    public List<TravelerEntity> findByMemberId(String memberId) {
        Query<TravelerEntity> lvQuery = datastore.createQuery(TravelerEntity.class);
        lvQuery.field("memberId").equal(memberId).field("isActive").equal(true);
        List<TravelerEntity> lvResult = lvQuery.asList();
        return lvResult;
    }

    @Override
    public TravelerEntity getDeaultTraveler(String memberId) {
        Query<TravelerEntity> query = datastore.createQuery(TravelerEntity.class);
        query.field("memberId").equal(memberId).field("isActive").equal(true).field("isDefault").equal(true);
        List<TravelerEntity> entityList = query.asList();
        if(entityList != null && entityList.size() == 1){
            return entityList.get(0);
        }
        return null;
    }

    @Override
    public void cancelDeaultTraveler(String memberId) {
        UpdateOperations<TravelerEntity> ops = datastore.createUpdateOperations(TravelerEntity.class);
        ops.set("isDefault", false);
        Query<TravelerEntity> query = datastore.createQuery(TravelerEntity.class);
        query.field("memberId").equal(memberId);
        datastore.update(query, ops);
    }

	@Override
	public List<TravelerEntity> findByMemberIdAndtravelType(String memberId,String travelType) {
		  Query<TravelerEntity> lvQuery = datastore.createQuery(TravelerEntity.class);
	        lvQuery.field("memberId").equal(memberId).field("isActive").equal(true).field("travelType").equal(travelType);
	        List<TravelerEntity> lvResult = lvQuery.asList();
	        return lvResult;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<TravelerEntity> getTravelersByParams(Map searchParams) {
		Query<TravelerEntity> lvQuery = datastore.createQuery(TravelerEntity.class);

		for (Object paramName : searchParams.keySet()) {
			if(StringUtils.isNotBlank((String) paramName)){
				lvQuery.filter((String) paramName, (String)searchParams.get(paramName)) ;
				lvQuery.field("isActive").equal(true);
			}
		}

		return  lvQuery.asList();
	}

	@Override
	public int updateTraveller(TravelerEntity traveller) {
		Query<TravelerEntity> query = datastore.createQuery(TravelerEntity.class).filter("_id", traveller.getId());
		UpdateOperations<TravelerEntity> ops = datastore.createUpdateOperations(TravelerEntity.class);

		if( traveller.getFirstNameCn() != null )
			ops.set("firstNameCn", traveller.getFirstNameCn());
		if( traveller.getLastNameCn() != null )
			ops.set("lastNameCn", traveller.getLastNameCn());
		if( traveller.getTravelerNameCn() != null )
			ops.set("travelerNameCn", traveller.getTravelerNameCn());
		if( traveller.getFirstEnName() != null )
			ops.set("firstEnName", traveller.getFirstEnName());
		if( traveller.getLastEnName() != null )
			ops.set("lastEnName", traveller.getLastEnName());
		if( traveller.getTravelerNameEn() != null )
			ops.set("travelerNameEn", traveller.getTravelerNameEn());
		if( traveller.getEmail() != null )
			ops.set("email", traveller.getEmail());
		if( traveller.getPhoneNum() != null )
			ops.set("phoneNum", traveller.getPhoneNum());
		if( traveller.getGender() != null )
			ops.set("gender", traveller.getGender());
		if( traveller.getTravelType() != null )
			ops.set("travelType", traveller.getTravelType());
		if( traveller.getNationality() != null )
			ops.set("nationality", traveller.getNationality());
		if( traveller.getBirthday() != null )
			ops.set("birthday", traveller.getBirthday());
		if( traveller.getProvince() != null )
			ops.set("province", traveller.getProvince());
		if( traveller.getCity() != null )
			ops.set("city", traveller.getCity());
		if( traveller.getDistrict() != null )
			ops.set("district", traveller.getDistrict());
		if( traveller.getDetailAddress() != null )
			ops.set("detailAddress", traveller.getDetailAddress());
		if( traveller.getCredentials() != null )
			ops.set("credentials", traveller.getCredentials());

		UpdateResults<TravelerEntity> result = datastore.update(query, ops);
		return result.getUpdatedCount();
	}
	
	@Override
	public List<TravelerEntity> queryTravellersOneMember(TravellerDto travellerDto){

		Query<TravelerEntity> query = datastore.createQuery(TravelerEntity.class);
		
		if(StringUtils.isNotBlank(travellerDto.getMemberId())){
			query.field("memberId").equal(travellerDto.getMemberId());
		}
		
		query.field("isActive").equal(true); //只查未删除的

		if(StringUtils.isNotBlank(travellerDto.getTravelerNameCn())){
			query.field("travelerNameCn").contains(travellerDto.getTravelerNameCn());
		}

		if(StringUtils.isNotBlank(travellerDto.getTravelerNameEn())){
			query.or(query.criteria("firstEnName").contains(travellerDto.getTravelerNameEn())
					, query.criteria("lastEnName").contains(travellerDto.getTravelerNameEn()),query.criteria("travelerNameEn").contains(travellerDto.getTravelerNameEn()));
		}


		if(StringUtils.isNotBlank(travellerDto.getPhoneNum())){
			query.field("phoneNum").equal(travellerDto.getPhoneNum());
		}

		List<TravelerEntity> list = query.asList();

        return list;

	}
	
	@Override
	public List<TravelerEntity> queryTravellersDetail(String travelerId){

		Query<TravelerEntity> query = datastore.createQuery(TravelerEntity.class);

		if(StringUtils.isNotBlank(travelerId)){
			query.field("id").equal(new ObjectId(travelerId));
		}

		List<TravelerEntity> list = query.asList();

		return list;

	}

}
