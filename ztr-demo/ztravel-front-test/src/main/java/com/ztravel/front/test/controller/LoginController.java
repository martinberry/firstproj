package com.ztravel.front.test.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/ztravel")
public class LoginController {

	@RequestMapping("/main")
	public ModelAndView showMainPage(HttpServletRequest request){
		ModelAndView mav = new ModelAndView("mainPage");
		//StringBuffer str = request.getRequestURL();
		HttpSession session = request.getSession();
		session.getAttribute("userInfo");

		return mav;
	}

}
