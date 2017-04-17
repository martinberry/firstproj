package com.ztravel.demo.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/member")
public class MemberController {

	@RequestMapping("/view")
	public String memberView() {
		return "member/view";
	}

}
