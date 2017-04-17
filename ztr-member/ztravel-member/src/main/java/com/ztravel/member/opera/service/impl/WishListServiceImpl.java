package com.ztravel.member.opera.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import com.github.jmkgreen.morphia.query.Query;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.ztravel.member.dao.MemberDao;
import com.ztravel.member.po.WishEntity;
import com.ztravel.member.dao.IWishListDao;
import com.ztravel.member.po.Member;
import com.ztravel.member.opera.service.IWishListService;
import com.ztravel.member.opera.wo.WishDetailPageWo;
import com.ztravel.member.opera.wo.WishListSearchWo;
import com.ztravel.member.opera.wo.WishMemberWo;
import com.ztravel.member.opera.wo.WishProductResponseWo;

@Service
public class WishListServiceImpl implements IWishListService {

	@Resource
	private IWishListDao wishListDaoImpl;

	@Resource
	private MemberDao memberDao;

	@Override
	public List<WishEntity> searchWishsByMemberId(String memberId) throws Exception{

		List<WishEntity> wishs = wishListDaoImpl.queryWishsByMemberId(memberId);

		return wishs ;

	}

	@Override
	public long searchWishMembers(WishListSearchWo searchWo, List<WishProductResponseWo> response) throws Exception{

		DBCursor cursor = wishListDaoImpl.queryWishMembers(searchWo.getpName());

		DBObject sort = new BasicDBObject();
		sort.put("value", -1); //默认以第一个字段排序

		long num = cursor.count();
		List<DBObject> dbList = cursor.sort(sort).skip((searchWo.getPageNo() - 1) * searchWo.getPageSize()).limit(searchWo.getPageSize()).toArray();

		for(DBObject object : dbList){
			BasicDBObject product = (BasicDBObject)object.get("value");
			WishProductResponseWo productWo = new WishProductResponseWo();
			productWo.setProductId(product.getString("productId"));
			productWo.setCount(product.getDouble("count"));
			productWo.setRecentTime(new DateTime(product.getDate("recentTime").getTime()).toString("yyyy-MM-dd HH:mm:ss"));
			response.add(productWo);
		}

		return num;

	}

	@Override
	public long searchWishsByProductId(WishDetailPageWo pageWo, List<WishMemberWo> members) throws Exception{

		Query<WishEntity> query = wishListDaoImpl.queryWishsByProductId(pageWo.getProductId());

		long num = query.countAll();

		List<WishEntity> list = query.offset((pageWo.getPageNo() - 1) * pageWo.getPageSize()).limit(pageWo.getPageSize()).asList();

		for(WishEntity wish : list){
			Member dbMember = memberDao.selectById(wish.getMemberId());
			if(dbMember != null){
				WishMemberWo member = convert2WishMemberWo(dbMember, wish);
				members.add(member);
			}
		}

		return num;

	}

	private WishMemberWo convert2WishMemberWo(Member dbMember, WishEntity wish){

		WishMemberWo member = new WishMemberWo();
		member.setMemberId(dbMember.getId());
		member.setmId(dbMember.getMid());
		member.setRealName(dbMember.getRealName());
		member.setMobile(dbMember.getMobile());
		member.setNickName(dbMember.getNickName());
		member.setProvince(dbMember.getProvince());
		member.setCity(dbMember.getCity());
		member.setRecentTime(wish.getCreatedTime().toString("yyyy-MM-dd HH:mm:ss"));
		return member;

	}

}
