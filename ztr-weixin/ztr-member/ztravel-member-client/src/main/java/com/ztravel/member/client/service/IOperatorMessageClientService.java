package com.ztravel.member.client.service;

import com.ztravel.common.enums.MessageTitleType;

public interface IOperatorMessageClientService {
	void add(MessageTitleType title, String mid, String productName, String link);
}
