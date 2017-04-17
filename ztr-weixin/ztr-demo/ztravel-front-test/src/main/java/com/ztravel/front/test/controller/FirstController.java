package com.ztravel.front.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/freemarker")
public class FirstController {
	@RequestMapping("/hello")
	public String welcome(){
		return "index" ;
	}
}
