package com.ztravel.operator.service;

import java.util.List;

import com.ztravel.common.bean.AjaxResponse;
import com.ztravel.operator.electronicWallet.entity.DnCouponSearchCriteria;

public interface IDnSearchService {

		 public AjaxResponse ChangeAuditStatusrByVoucherCode(String voucherCode);

		 public Integer countByCriteria(DnCouponSearchCriteria criteria) throws Exception;

		public List searchByCriteria(DnCouponSearchCriteria criteria) throws Exception;


}
