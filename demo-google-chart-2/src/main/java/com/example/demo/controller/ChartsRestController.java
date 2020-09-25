package com.example.demo.controller;

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.service.ChartsService;
import com.example.demo.vo.BarToppingVO;

@RestController
public class ChartsRestController {

	@Autowired
	ChartsService chartService;

	//ajax 통신 부분(select)
	@RequestMapping(value="/list" ,method=RequestMethod.GET)
	public ResponseEntity<JSONObject> list() {
		
		System.out.println("controller start");
		
		ResponseEntity<JSONObject> entity = null;
		List<BarToppingVO> items = chartService.selectChartList();
		
		//리스트 형태를 json 형태로 만들어서 리턴
		JSONObject data = new JSONObject();
		
		//컬럼 객체
		//서버에서 웹으로 넘겨줄 가장 큰 단위인 JSONObject
		JSONObject col1 = new JSONObject(); //cols의 첫 번째 object를 담을 JSONObject
		JSONObject col2 = new JSONObject(); //cols의 두 번째 object를 담을 JSONObject
		JSONArray title = new JSONArray(); //위의 두개의 JSONObject를 담을 JSONArray
		
		col1.put("label", "토핑재료"); //JSONObject에 값을 담을 때는 put을 사용한다.
		col1.put("type", "string");
		
		col2.put("label", "개수");
		col2.put("type", "number");
		
		title.add(col1); //JSONArray에 추가할 때는 add를 사용한다.
		title.add(col2);
		
		data.put("cols", title);
		
		//들어갈 형태  => rows 객체에 배열  <- c 라는 객체에 배열  <- v 객체
		//data 객체 => rows 객체 -> 배열  <- c 객체  -> 배열  <- v 객체 2개
		
		JSONArray body = new JSONArray();
		
		for(BarToppingVO vo : items) { //items만큼 반복하면 형식을 만든다.
			JSONObject name = new JSONObject();
			name.put("v", vo.getName()); //이름 -> v객체
			
			JSONObject num = new JSONObject();
			num.put("v", vo.getNum()); //개수 -> v객체
			
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
	
	//ajax 통신 부분(insert)
	@PostMapping(value="/write", produces={MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<BarToppingVO> insert(BarToppingVO barToppingVO) {
		
		System.out.println("controller start");
		
		System.out.println("name: "+barToppingVO.getName());
		System.out.println("num: "+barToppingVO.getNum());
		
		chartService.insertChart(barToppingVO);
		
		System.out.println("end");
		
		return new ResponseEntity<BarToppingVO>(barToppingVO, HttpStatus.OK);
	}
}


/*	구글 차트 JSON 데이터의 형식
 *  크게 보면 JSONObject안에 cols와 rows라는 2개의 JSONArray가 있고 그 안에 또 JSONObject와 JSONArray, String값이 있는 거임.
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

sample data를 보면 c, v, f가 있는데, c->cell, v->value, f->format(f는 필수X)
*/

