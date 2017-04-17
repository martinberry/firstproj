package com.ztravel.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * 
 * @author liuzhuo
 *
 */

@Controller
@RequestMapping("/message")
public class DwrDemoController {
	
	@RequestMapping(value="/send", method={RequestMethod.GET})
	public String toSend() {
		return "dwr/send";
	}
	
	@RequestMapping(value="receive", method={RequestMethod.GET})
	public String toReceive() {
		return "dwr/receive";
	}

}
