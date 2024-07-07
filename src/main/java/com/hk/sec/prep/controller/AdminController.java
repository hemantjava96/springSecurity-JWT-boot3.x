package com.hk.sec.prep.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/private/admin")
public class AdminController {
	
	
	@GetMapping
	public String admin() {
		return "admin works";
	}

}
