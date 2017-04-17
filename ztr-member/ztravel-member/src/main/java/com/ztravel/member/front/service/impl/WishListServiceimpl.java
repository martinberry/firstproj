package com.ztravel.member.front.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.jmkgreen.morphia.query.Query;
import com.ztravel.common.constants.Const;
import com.ztravel.common.exception.ZtrBizException;
import com.ztravel.member.dao.IWishListDao;
import com.ztravel.member.dao.MemberDao;
import com.ztravel.member.po.WishEntity;
import com.ztravel.member.front.converter.WishConvert;
import com.ztravel.member.front.vo.AddToWishRequest;
import com.ztravel.member.front.vo.MemberResponse;
import com.ztravel.member.front.vo.WishDetailResponse;
import com.ztravel.member.front.service.IWishListService;
import com.ztravel.member.po.Member;
import com.ztravel.order.client.service.IOrderClientService;
import com.ztravel.product.client.entity.ProductBasicDetail;
import com.ztravel.product.client.service.IProductDetailService;

@Service
public class WishListServiceimpl implements IWishListService {

	@Resource
	private IWishListDao wishListDaoImpl;

	@Resource
	private MemberDao memberDaoImpl;

	@Resource
	private IProductDetailService productClientDetailServiceImpl;

	@Resource
	private IOrderClientService orderClientServiceImpl;

	@Override
	public void add2WishList(String memberId, String mid, AddToWishRequest product) throws Exception{

		List<WishEntity> wishs = wishListDaoImpl.queryWishs(memberId, product.getProductId());
		WishEntity entity = WishConvert.convertAddToWishRequest2WishEntity(product) ;
		if(wishs == null || wishs.size() < 1){
			wishListDaoImpl.insertWish(memberId, entity);
		}else{
			throw ZtrBizException.instance(Const.FF_MEMB_CODE_1003, Const.FF_MEMB_REASON_1003) ;
		}

	}

	@Override
	public List<WishDetailResponse> searchWishsDetailByMemberId(String memberId, String mid) throws Exception{

		List<WishDetailResponse> response = new ArrayList<>();

		List<WishEntity> wishsBm = wishListDaoImpl.queryWishsByMemberId(memberId);

		for(WishEntity wish : wishsBm){
			ProductBasicDetail entity = productClientDetailServiceImpl.getProductById(wish.getProductId());
			if(entity != null){
				Query<WishEntity> query = wishListDaoImpl.queryWishsByProductId(wish.getProductId());
				long count = query.countAll();
				List<WishEntity> wishsBp = query.offset(0).limit(5).asList();
				Boolean hasGo = orderClientServiceImpl.orderProductIsGone(mid, wish.getProductId());
				WishDetailResponse res = convert2WishDetailResponse(wishsBp, wish, entity, count, hasGo);
				response.add(res);
			}
		}

		return response;

	}

	@Override
	public void deleteWish(String wishId) throws Exception{

		wishListDaoImpl.deleteWish(wishId);

	}

	@Override
	public String getProductIdsByMemberId(String memberId) throws Exception{

		List<WishEntity> wishs = wishListDaoImpl.queryWishsByMemberId(memberId);

		String str = "";
		for(WishEntity wish : wishs){
			str += wish.getProductId() + ",";
		}

		return str==""? str : str.substring(0, str.length()-1);

	}

	private WishDetailResponse convert2WishDetailResponse(List<WishEntity> wishsBp, WishEntity wish, ProductBasicDetail entity, long count, Boolean hasGo) throws Exception{

		WishDetailResponse response = new WishDetailResponse();
		response.setId(wish.getId().toString());
		response.setProductId(wish.getProductId());
		response.setPid(entity.getPid());
		response.setpName(entity.getpName());
		response.setImage(entity.getImage());
		response.setStatus(entity.getStatus());
		response.setCreatedTime(wish.getCreatedTime().toString("yyyy-MM-dd HH:mm:ss"));
		response.setCount(count);
		response.setHasGo(hasGo);

		List<MemberResponse> members = new ArrayList<>();
		for(WishEntity ent : wishsBp){
			MemberResponse member = new MemberResponse();
			Member dbMember = memberDaoImpl.selectById(ent.getMemberId());
			if(dbMember != null){
				member.setId(ent.getMemberId());
				member.setMid(dbMember.getMid());
				member.setNickName(dbMember.getNickName());
				member.setHeadImageId(dbMember.getHeadImageId());
				members.add(member);
			}
		}
		response.setMembers(members);

		return response;

	}

}
