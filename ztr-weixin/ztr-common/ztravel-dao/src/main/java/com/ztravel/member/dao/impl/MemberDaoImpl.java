package com.ztravel.member.dao.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.github.jmkgreen.morphia.Datastore;
import com.travelzen.framework.redis.client.RedisClient;
import com.ztravel.common.dao.BaseDaoImpl;
import com.ztravel.member.dao.MemberDao;
import com.ztravel.member.po.Member;
import com.ztravel.member.po.MemberTimeEntity;
import com.ztravel.member.po.NickNameEntity;
import com.ztravel.member.po.WxUserEntity;

/**
 * @author wanhaofan
 * */
@Repository
public class MemberDaoImpl extends BaseDaoImpl<Member> implements MemberDao {

	private static final RedisClient redisClient = RedisClient.getInstance() ;

	private static final String NICKNAMELIB_MAXSIZE = "NICKNAMELIB_MAXSIZE" ;

	@Resource
	private Datastore datastore ;

	private static final String UPDATE_MEMBER_HEAD_IMAGE = ".updateMemberHeadImage" ;

	private static final String UPDATE_MEMBER_HEAD_IMAGE_BYID = ".updateMemberHeadImageById" ;

	private static final String UPDATE_MEMBER_IMPROVE_DATA_BYID = ".updateImproveDataById" ;

	private static final String UPDATE_MEMBER_PASSWORD_BY_MOBILE = ".updateMemberPasswordByMobile" ;

	private static final String UPDATE_MEMBER_PASSWORD_BY_EMAIL = ".updateMemberPasswordByEmail" ;

	private static final String COUNT_BY_ID_PASSWORD = ".countByIdPassword";

	private static final String UPDATE_BY_EXAMPLE = ".updateByExample";

	private static final String COUNT_MOBILE = ".countMobile";

	private static final String SELECT_BY_MOBILE = ".selectByMobile";

	private static final String GET_REGISTERTIME_BY_ID = ".getMemberRegisterTime";

	private static final String SELECT_MIN_BY_ID = ".selectMinById";

	private static final String COUNT_ALL = ".countAll" ;

	private static final String SELECT_MINS = ".selectMinsByIds";

	private static final String SELECT_MEMBER_BY_MID = ".selectMemberByMid";

	private static final String SELECT_RECOMMENDER = ".selectRecommender";

    private static final String INSERT_WX_USER = ".insertWxUser";

    private static final String SELECT_WXUSER_BY_OPENID = ".selectWxUserByOpenId";

    private static final String SELECT_WXNICKNAME_BY_OPENID = ".selectWxNickNameByOpenId";

    private static final String COUNT_OPENID_MEMBER_BY_MEMBERID = ".countOpenidMemberByMemberId";

    private static final String UPDATE_MOBILE_AND_PASSWORD = ".updateMemberMobileAndPassword";

    private static final String SELECT_MEMBERID_BY_OPENID = ".selectMemberIdByOpenId";
    
    private static final String UPDATE_ACTIVE_BY_IDS = ".updateActiveByIds" ;
    
	@Override
	public Integer updateMemberHeadImage(Member member) {
		return session.update(nameSpace + UPDATE_MEMBER_HEAD_IMAGE, member);
	}

	@Override
	public String getRandomNickName() {
		Long maxSize = redisClient.get(NICKNAMELIB_MAXSIZE, Long.class) ;
		if(maxSize == null || maxSize == 0){
			maxSize = datastore.getCount(NickNameEntity.class) ;
			redisClient.setNoJSON(NICKNAMELIB_MAXSIZE, maxSize) ;
			redisClient.persist(NICKNAMELIB_MAXSIZE);
		}
		Random random = new Random() ;

		return datastore.find(NickNameEntity.class).offset(random.nextInt(maxSize.intValue())).get().getNickName() ;
	}

	@Override
	public Integer updateMemberPasswordByMobile(String mobile, String password) {
		Map<String, Object> params = new HashMap<String, Object>() ;
		params.put("mobile", mobile) ;
		params.put("password", password) ;
		return session.update(nameSpace + UPDATE_MEMBER_PASSWORD_BY_MOBILE, params);
	}

	@Override
	public Integer updateMemberPasswordByEmail(String email, String password) {
		Map<String, Object> params = new HashMap<String, Object>() ;
		params.put("email", email) ;
		params.put("password", password) ;
		return session.update(nameSpace + UPDATE_MEMBER_PASSWORD_BY_EMAIL, params);
	}

	@Override
	public Integer updateByExample(Member member){
		return session.update(nameSpace + UPDATE_BY_EXAMPLE, member);
	}

	@Override
	public Integer countByIdPassword(Map params) {
		return session.selectOne(nameSpace + COUNT_BY_ID_PASSWORD, params);
	}

	@Override
	public Integer countMobile(String mobile) {
		return session.selectOne(nameSpace + COUNT_MOBILE, mobile);
	}

	@Override
	public Integer updateMemberImproveDataById(Map params) {
		return session.update(nameSpace + UPDATE_MEMBER_IMPROVE_DATA_BYID, params);
	}

	@Override
	public Integer updateMemberHeadImageById(Map params) {
		return session.update(nameSpace + UPDATE_MEMBER_HEAD_IMAGE_BYID, params);
	}

	@Override
	public String selectByMobile(String mobile) {
		return session.selectOne(nameSpace + SELECT_BY_MOBILE, mobile);
	}

	@Override
	public Integer countAll() {
		return session.selectOne(nameSpace + COUNT_ALL);
	}

	@Override
	public Member selectMinById(String id) {
		return session.selectOne(nameSpace + SELECT_MIN_BY_ID, id);
	}

	@Override
	public MemberTimeEntity getMemberRegisterTime(String id) {
		return session.selectOne(nameSpace + GET_REGISTERTIME_BY_ID, id);
	}

	@Override
	public List<Member> selectMinsByIds(List<String> ids) {
		return session.selectList(nameSpace + SELECT_MINS,ids);
	}

	@Override
	public Member selectMemberByMid(String mid) {
		return session.selectOne(nameSpace + SELECT_MEMBER_BY_MID, mid);
	}

	@Override
	public String selectRecommender(String id) {
		return session.selectOne(nameSpace + SELECT_RECOMMENDER, id);
	}

    @Override
    public WxUserEntity selectWxUserByOpenId(String openId) {
        return session.selectOne(nameSpace + SELECT_WXUSER_BY_OPENID, openId);
    }

    @Override
    public String selectWxNickNameByOpenId(String openId) {
        return session.selectOne(nameSpace + SELECT_WXNICKNAME_BY_OPENID, openId);
    }

    @Override
    public Integer countOpenidMemberByMemberId(String memberId) {
        return session.selectOne(nameSpace + COUNT_OPENID_MEMBER_BY_MEMBERID, memberId);
    }

    @Override
    public void insertWxUser(WxUserEntity wxUserEntity) {
        session.insert(nameSpace + INSERT_WX_USER, wxUserEntity);
    }

    @Override
    public Integer updateMobileAndPasswordById(String id, String mobile, String password) {
        Map<String, Object> params = new HashMap<String, Object>() ;
        params.put("id", id) ;
        params.put("mobile", mobile) ;
        params.put("password", password) ;
        return session.update(nameSpace + UPDATE_MOBILE_AND_PASSWORD, params);
    }

    @Override
    public String selectMemberIdByOpenId(String openId) {
        return session.selectOne(nameSpace + SELECT_MEMBERID_BY_OPENID, openId);
    }
    
	@Override
	public int updateActiveByIds(Map params) throws SQLException {
		return session.update(nameSpace + UPDATE_ACTIVE_BY_IDS, params);
	}

}
