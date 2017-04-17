package com.ztravel.member.opera.service;
import java.util.List;

import com.ztravel.member.opera.entity.DHSearchCriteria;
import com.ztravel.member.opera.vo.DHOrderDetailVO;
import com.ztravel.member.opera.vo.DHOrderListVO;
import com.ztravel.common.bean.AjaxResponse;
//import com.ztravel.member.entity.*;
import com.ztravel.member.po.Dhpo;

public interface IOrderDhService {
	/**
	 * 搜索兑换订单
	 * @param criteria
	 * @return
	 * @throws Exception
	 */
	public List<DHOrderListVO> searchDH(DHSearchCriteria criteria) throws Exception;
	/**
	 * 符合搜索条件的兑换数
	 * @param criteria
	 * @return
	 * @throws Exception
	 */
	public Integer countDHs(DHSearchCriteria criteria) throws Exception;
	/**
	 * 兑换详情
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public DHOrderDetailVO getDHDetailByDHId(String dhId) throws Exception;


	//存储兑换数据
	//AjaxResponse adddh(DhPO dh ) throws Exception;
	//查询兑换数据
	public Dhpo search(String dhid) throws Exception;
	//更新兑换数据
	public AjaxResponse update(Dhpo dh) throws Exception;
}






