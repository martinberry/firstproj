package com.ztravel.member.front.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * C端公共页面Controller
 * @author haofan.wan
 */

@Controller
public class CommonController {

	@RequestMapping("/about")
	public String about(String selector, String title, String menuName,Model model) throws UnsupportedEncodingException {
		model.addAttribute("selector", selector) ;
		model.addAttribute("title", title) ;
		model.addAttribute("menuName", menuName) ;
		return "common/front/about" ;
	}
	
	@RequestMapping("/500")
	public String e1() {
		return "common/front/error/500" ;
	}

	@RequestMapping("/404")
	public String e2() {
		return "common/front/error/404" ;
	}

	@RequestMapping("/uncaughtException")
	public String e3() {
		return "common/front/error/500" ;
	}
	
}
