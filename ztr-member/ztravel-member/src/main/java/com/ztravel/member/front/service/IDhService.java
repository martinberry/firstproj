package com.ztravel.member.front.service;

import com.ztravel.common.bean.AjaxResponse;
//import com.ztravel.member.entity.*;
import com.ztravel.member.po.Dhpo;

public interface IDhService {
	//存储兑换数据
	AjaxResponse adddh(Dhpo dh ) throws Exception;
}
