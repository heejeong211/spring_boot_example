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
import com.example.demo.vo.ToppingVO;

@Controller
public class ChartsController {

	@Autowired
	ChartsService barChartService;
	
	//bar_charts_view 호출 부분
	@RequestMapping(value="/bar_charts_view", method=RequestMethod.GET)
	public ModelAndView list_bar_view () {
		
		System.out.println("bar_charts_view");
		
		return new ModelAndView("google-bar-charts");
	}

	//line_charts_view 호출 부분
	@RequestMapping(value="/line_charts_view", method=RequestMethod.GET)
	public ModelAndView list_line_view () {
			
		System.out.println("line_charts_view");
			
		return new ModelAndView("google-line-charts");
	}
	
	//line_charts_view 호출 부분
	@RequestMapping(value="/pie_charts_view", method=RequestMethod.GET)
	public ModelAndView pie_line_view () {
			
		System.out.println("pie_charts_view");
			
		return new ModelAndView("google-pie-charts");
	}
	
	//ajax 통신 부분
	@ResponseBody
	@RequestMapping(value="/list" ,method=RequestMethod.GET)
	public ResponseEntity<JSONObject> list() {
		
		System.out.println("controller start");
		
		ResponseEntity<JSONObject> entity = null;
		List<ToppingVO> items = barChartService.selectBarChartList();
		
		//리스트 형태를 json 형태로 만들어서 리턴
		JSONObject data = new JSONObject();
		
		//컬럼 객체
		JSONObject col1 = new JSONObject();
		JSONObject col2 = new JSONObject();
		JSONArray title = new JSONArray();
		col1.put("label", "토핑재료");
		col1.put("type", "string");
		col2.put("label", "개수");
		col2.put("type", "number");
		
		title.add(col1);
		title.add(col2);
		
		data.put("cols", title);
		
		//들어갈 형태  ->  rows 객체 에 배열  <- 
		//  <- [  c 라는 객체에 배열 <- 객체
		//  data 객체 -> rows 배열 <-  c 객체  ->배열  <- v 객체 2개/
		
		JSONArray body = new JSONArray();
		
		for(ToppingVO vo : items) {
			JSONObject name = new JSONObject();
			name.put("v", vo.getName()); //이름 -> v객체
			JSONObject num = new JSONObject();
			num.put("v", vo.getNum()); //가격 -> v객체
			
			//v객체를 row 배열을 만든 후 추가한다.
			JSONArray row = new JSONArray();
			row.add(name);
			row.add(num);
			
			//c객체를 만든 후 row 배열을 담는다.
			JSONObject c = new JSONObject();
			c.put("c", row);
			
			//c객체를 배열 형태의 body에 담는다.
			body.add(c);
		}
		
		//배열 형태의 body를 rows 키값으로 객체 data에 담는다.
		data.put("rows", body);
		try {
			entity = new ResponseEntity<JSONObject>(data, HttpStatus.OK);
		} catch(Exception e) {
			System.out.println("에러                  --");
			entity = new ResponseEntity<JSONObject>(HttpStatus.BAD_REQUEST);
		}
		
		System.out.println("json 형식의 데이터: "+data);
		
		System.out.println("end");
		
		return entity;
	}
	
}


/*	 구글 차트 JSON 데이터의 형식
{
    "cols": [
        {"label":"Topping","type":"string"},
        {"label":"Slices","type":"number"}
    ],        
    "rows": [
        {"c":[{"v":"Mushrooms"},{"v":3}]},
        {"c":[{"v":"Onions"},{"v":1}]},
        {"c":[{"v":"Olives"},{"v":1}]},
        {"c":[{"v":"Zucchini"},{"v":1}]},
        {"c":[{"v":"Pepperoni"},{"v":2}]}
    ]
}

rows : [ 배열 (객체 :배열[객체])]
*/

