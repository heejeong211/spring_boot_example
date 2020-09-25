package com.example.demo.controller;

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.service.ChartsService;
import com.example.demo.vo.BarToppingVO;

@Controller
public class ChartsController {

	@Autowired
	ChartsService chartService;
	
	//bar_charts_view 호출 부분
	@RequestMapping(value="/bar_charts_view", method=RequestMethod.GET)
	public ModelAndView list_bar_view () {
		
		System.out.println("bar_charts_view");
		
		return new ModelAndView("google-bar-charts");
	}
}
