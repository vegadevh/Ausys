package com.digitalatmosphere.ausys.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {
	
	@RequestMapping("/403")
	public ModelAndView error() {
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("403");
		return mav;
		
	}
	
}
