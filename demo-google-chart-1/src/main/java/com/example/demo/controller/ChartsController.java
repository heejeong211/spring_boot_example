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
	ChartsService chartService;
	
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
	
	//pie_charts_view 호출 부분
	@RequestMapping(value="/pie_charts_view", method=RequestMethod.GET)
	public ModelAndView list_pie_view () {
			
		System.out.println("pie_charts_view");
			
		return new ModelAndView("google-pie-charts");
	}
	
	//donut_charts_view 호출 부분
	@RequestMapping(value="/donut_charts_view", method=RequestMethod.GET)
	public ModelAndView list_donut_view() {
			
		System.out.println("donut_charts_view");
			
		return new ModelAndView("google-donut-charts");
	}
	
	//bar_line_charts_view 호출 부분
	@RequestMapping(value="/bar_line_charts_view", method=RequestMethod.GET)
	public ModelAndView list_bar_line_view () {
		
		System.out.println("bar_line_charts_view");
		
		return new ModelAndView("google-bar-line-charts");
	}
	
	//ajax 통신 부분
	@ResponseBody
	@RequestMapping(value="/list" ,method=RequestMethod.GET)
	public ResponseEntity<JSONObject> list() {
		
		System.out.println("controller start");
		
		ResponseEntity<JSONObject> entity = null;
		List<ToppingVO> items = chartService.selectChartList();
		
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
		
		for(ToppingVO vo : items) { //items만큼 반복하면 형식을 만든다.
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
	
	/*------------------------------- 다른 값으로 데이터 + format -------------------------------*/
	
	//organization_charts_view 호출 부분
	@RequestMapping(value="/organization_charts_view", method=RequestMethod.GET)
	public ModelAndView list_organization_view () {
		
		System.out.println("organization_charts_view");
		
		return new ModelAndView("google-organization-charts");
	}
	
	//ajax 통신 부분
	@ResponseBody
	@RequestMapping(value="/list2" ,method=RequestMethod.GET)
	public ResponseEntity<JSONObject> list2() {
		
		System.out.println("controller start");
		
		ResponseEntity<JSONObject> entity = null;
		List<ToppingVO> items = chartService.selectChartList();
		
		//리스트 형태를 json 형태로 만들어서 리턴
		JSONObject data = new JSONObject();
		
		//컬럼 객체
		//서버에서 웹으로 넘겨줄 가장 큰 단위인 JSONObject
		JSONObject col1 = new JSONObject(); //cols의 첫 번째 object를 담을 JSONObject
		JSONObject col2 = new JSONObject(); //cols의 두 번째 object를 담을 JSONObject
		JSONArray title = new JSONArray(); //위의 두개의 JSONObject를 담을 JSONArray
		
		col1.put("label", "토핑재료"); //JSONObject에 값을 담을 때는 put을 사용한다.
		col1.put("type", "string");
		
		col2.put("label", "종류");
		col2.put("type", "string");
		
		title.add(col1); //JSONArray에 추가할 때는 add를 사용한다.
		title.add(col2);
		
		data.put("cols", title);
		
		//들어갈 형태  => rows 객체에 배열  <- c 라는 객체에 배열  <- v 객체
		//data 객체 => rows 객체 -> 배열  <- c 객체  -> 배열  <- v 객체 2개
		
		JSONArray body = new JSONArray();
		
		for(ToppingVO vo : items) { //items만큼 반복하면 형식을 만든다.
			JSONObject name = new JSONObject();
			name.put("v", vo.getName()); //이름 -> v객체
			name.put("f", vo.getAnno());
			
			JSONObject kindOf = new JSONObject();
			kindOf.put("v", vo.getKindOf());
			
		
			//v객체를 row 배열을 만든 후 추가한다.
			JSONArray row = new JSONArray();
			row.add(name);
			row.add(kindOf);
			
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
	
	/*=============================== 데이터 2개 ===============================*/
	
	//two_bar_charts_view 호출 부분
	@RequestMapping(value="/two_bar_charts_view", method=RequestMethod.GET)
	public ModelAndView list_two_bar_view () {
		
		System.out.println("two_bar_charts_view");
		
		return new ModelAndView("google-bar-charts2");
	}
	
	//combo_charts_view 호출 부분
	@RequestMapping(value="/combo_charts_view", method=RequestMethod.GET)
	public ModelAndView list_combo_view () {
		
		System.out.println("combo_charts_view");
		
		return new ModelAndView("google-combo-charts");
	}
	
	//area_charts_view 호출 부분
	@RequestMapping(value="/area_charts_view", method=RequestMethod.GET)
	public ModelAndView list_area_view () {
		
		System.out.println("area_charts_view");
		
		return new ModelAndView("google-area-charts");
	}
	
	//histogram_charts_view 호출 부분
	@RequestMapping(value="/histogram_charts_view", method=RequestMethod.GET)
	public ModelAndView list_histogram_view () {
			
		System.out.println("histogram_charts_view");
			
		return new ModelAndView("google-histogram-charts");
	}
	
	//ajax 통신 부분
	@ResponseBody
	@RequestMapping(value="/two_list" ,method=RequestMethod.GET)
	public ResponseEntity<JSONObject> two_list() {
		
		System.out.println("controller start");
		
		ResponseEntity<JSONObject> entity = null;
		List<ToppingVO> items = chartService.selectChartList();
		
		//리스트 형태를 json 형태로 만들어서 리턴
		JSONObject data = new JSONObject();
		
		//컬럼 객체
		//서버에서 웹으로 넘겨줄 가장 큰 단위인 JSONObject
		JSONObject col1 = new JSONObject(); //cols의 첫 번째 object를 담을 JSONObject
		JSONObject col2 = new JSONObject(); //cols의 두 번째 object를 담을 JSONObject
		JSONObject col3 = new JSONObject();
		JSONArray title = new JSONArray(); //위의 두개의 JSONObject를 담을 JSONArray
		
		col1.put("label", "토핑재료"); //JSONObject에 값을 담을 때는 put을 사용한다.
		col1.put("type", "string");
		
		col2.put("label", "개수");
		col2.put("type", "number");
		
		col3.put("label", "신선도");
		col3.put("type", "number");
		
		title.add(col1); //JSONArray에 추가할 때는 add를 사용한다.
		title.add(col2);
		title.add(col3);
		
		data.put("cols", title);
		
		//들어갈 형태  => rows 객체에 배열  <- c 라는 객체에 배열  <- v 객체
		//data 객체 => rows 객체 -> 배열  <- c 객체  -> 배열  <- v 객체 2개
		
		JSONArray body = new JSONArray();
		
		for(ToppingVO vo : items) { //items만큼 반복하면 형식을 만든다.
			JSONObject name = new JSONObject();
			name.put("v", vo.getName()); //이름 -> v객체
			
			JSONObject num = new JSONObject();
			num.put("v", vo.getNum()); //개수 -> v객체
			
			JSONObject fresh = new JSONObject();
			fresh.put("v", vo.getFresh());
			
			//v객체를 row 배열을 만든 후 추가한다.
			JSONArray row = new JSONArray();
			row.add(name);
			row.add(num);
			row.add(fresh);
			
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
	
	/*------------------------------- 다른 값으로 데이터 2개 + format -------------------------------*/
	
	//treemap_charts_view 호출 부분
	@RequestMapping(value="/treemap_charts_view", method=RequestMethod.GET)
	public ModelAndView list_treemap_view () {
			
		System.out.println("treemap_charts_view");
			
		return new ModelAndView("google-treemap-charts");
	}
	
	//ajax 통신 부분
	@ResponseBody
	@RequestMapping(value="/two_list2" ,method=RequestMethod.GET)
	public ResponseEntity<JSONObject> two_list2() {
		
		System.out.println("controller start");
		
		ResponseEntity<JSONObject> entity = null;
		List<ToppingVO> items = chartService.selectChartList();
		
		//리스트 형태를 json 형태로 만들어서 리턴
		JSONObject data = new JSONObject();
		
		//컬럼 객체
		//서버에서 웹으로 넘겨줄 가장 큰 단위인 JSONObject
		JSONObject col1 = new JSONObject(); //cols의 첫 번째 object를 담을 JSONObject
		JSONObject col2 = new JSONObject(); //cols의 두 번째 object를 담을 JSONObject
		JSONObject col3 = new JSONObject();
		JSONArray title = new JSONArray(); //위의 두개의 JSONObject를 담을 JSONArray
		
		col1.put("label", "토핑재료"); //JSONObject에 값을 담을 때는 put을 사용한다.
		col1.put("type", "string");
		
		col2.put("label", "종류");
		col2.put("type", "string");
		
		col3.put("label", "개수");
		col3.put("type", "number");
		
		title.add(col1); //JSONArray에 추가할 때는 add를 사용한다.
		title.add(col2);
		title.add(col3);
		
		data.put("cols", title);
		
		//들어갈 형태  => rows 객체에 배열  <- c 라는 객체에 배열  <- v 객체
		//data 객체 => rows 객체 -> 배열  <- c 객체  -> 배열  <- v 객체 2개
		
		JSONArray body = new JSONArray();
		
		for(ToppingVO vo : items) { //items만큼 반복하면 형식을 만든다.
			JSONObject name = new JSONObject();
			name.put("v", vo.getName()); //이름 -> v객체
			name.put("f", vo.getAnno());
			
			JSONObject kindOf = new JSONObject();
			kindOf.put("v", vo.getKindOf());
			
			JSONObject num = new JSONObject();
			num.put("v", vo.getNum()); //개수 -> v객체
		
			//v객체를 row 배열을 만든 후 추가한다.
			JSONArray row = new JSONArray();
			row.add(name);
			row.add(kindOf);
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
	
	/*=============================== 데이터 2개 + annotation options-> 데이터 3개 ===============================*/
	
	//combo_charts_anno_view 호출 부분
	@RequestMapping(value="/combo_charts_anno_view", method=RequestMethod.GET)
	public ModelAndView list_combo_anno_view () {
		
		System.out.println("combo_charts_anno_view");
		
		return new ModelAndView("google-combo-charts2");
	}
	
	//column_charts_anno_view 호출 부분
	@RequestMapping(value="/column_charts_anno_view", method=RequestMethod.GET)
	public ModelAndView list_column_anno_view () {
		
		System.out.println("column_charts_anno_view");
		
		return new ModelAndView("google-column-charts");
	}
	
	//stack_bar_charts_anno_view 호출 부분
	@RequestMapping(value="/stack_bar_charts_anno_view", method=RequestMethod.GET)
	public ModelAndView list_stack_bar_anno_view () {
		
		System.out.println("stack_bar_charts_anno_view");
		
		return new ModelAndView("google-bar-charts3");
	}
	
	//ajax 통신 부분
	@ResponseBody
	@RequestMapping(value="/anno_two_list" ,method=RequestMethod.GET)
	public ResponseEntity<JSONObject> anno_two_list() {
		
		System.out.println("controller start");
		
		ResponseEntity<JSONObject> entity = null;
		List<ToppingVO> items = chartService.selectChartList();
		
		//리스트 형태를 json 형태로 만들어서 리턴
		JSONObject data = new JSONObject();
		
		//컬럼 객체
		//서버에서 웹으로 넘겨줄 가장 큰 단위인 JSONObject
		JSONObject col1 = new JSONObject(); //cols의 첫 번째 object를 담을 JSONObject
		JSONObject col2 = new JSONObject(); //cols의 두 번째 object를 담을 JSONObject
		JSONObject col3 = new JSONObject();
		JSONObject col4 = new JSONObject();
		JSONArray title = new JSONArray(); //위의 두개의 JSONObject를 담을 JSONArray
		
		col1.put("label", "토핑재료"); //JSONObject에 값을 담을 때는 put을 사용한다.
		col1.put("type", "string");
		
		col2.put("label", "개수");
		col2.put("type", "number");
		
		col3.put("label", ""); //annotation options을 사용하기 위해
		col3.put("type", "string");
		col3.put("role", "annotation");
		
		col4.put("label", "신선도");
		col4.put("type", "number");
		
		title.add(col1); //JSONArray에 추가할 때는 add를 사용한다.
		title.add(col2);
		title.add(col3);
		title.add(col4);
		
		data.put("cols", title);
		
		//들어갈 형태  => rows 객체에 배열  <- c 라는 객체에 배열  <- v 객체
		//data 객체 => rows 객체 -> 배열  <- c 객체  -> 배열  <- v 객체 2개
		
		JSONArray body = new JSONArray();
		
		for(ToppingVO vo : items) { //items만큼 반복하면 형식을 만든다.
			JSONObject name = new JSONObject();
			name.put("v", vo.getName()); //이름 -> v객체
			
			JSONObject num = new JSONObject();
			num.put("v", vo.getNum()); //개수 -> v객체
			
			JSONObject anno = new JSONObject();
			anno.put("v", vo.getAnno()); //annotation options을 사용하기 위해 데이터 추가
			
			JSONObject fresh = new JSONObject();
			fresh.put("v", vo.getFresh());
			
			//v객체를 row 배열을 만든 후 추가한다.
			JSONArray row = new JSONArray();
			row.add(name);
			row.add(num);
			row.add(anno);
			row.add(fresh);
			
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
	
	/*=============================== 데이터 4개 ===============================*/
	
	//bubble_charts_view 호출 부분
	@RequestMapping(value="/bubble_charts_view", method=RequestMethod.GET)
	public ModelAndView list_bubble_view () {
		
		System.out.println("bubble_charts_view");
		
		return new ModelAndView("google-bubble-charts");
	}
	
	//ajax 통신 부분
	@ResponseBody
	@RequestMapping(value="/four_list" ,method=RequestMethod.GET)
	public ResponseEntity<JSONObject> four_list() {
		
		System.out.println("controller start");
		
		ResponseEntity<JSONObject> entity = null;
		List<ToppingVO> items = chartService.selectChartList();
		
		//리스트 형태를 json 형태로 만들어서 리턴
		JSONObject data = new JSONObject();
		
		//컬럼 객체
		//서버에서 웹으로 넘겨줄 가장 큰 단위인 JSONObject
		JSONObject col1 = new JSONObject(); //cols의 첫 번째 object를 담을 JSONObject
		JSONObject col2 = new JSONObject(); //cols의 두 번째 object를 담을 JSONObject
		JSONObject col3 = new JSONObject();
		JSONObject col4 = new JSONObject();
		JSONObject col5 = new JSONObject();
		JSONArray title = new JSONArray(); //위의 두개의 JSONObject를 담을 JSONArray
		
		col1.put("label", "토핑재료"); //JSONObject에 값을 담을 때는 put을 사용한다.
		col1.put("type", "string");
		
		col2.put("label", "개수");
		col2.put("type", "number");
		
		col3.put("label", "신선도"); 
		col3.put("type", "number");
		
		col4.put("label", "한글");
		col4.put("type", "string");
		
		col5.put("label", "비율");
		col5.put("type", "number");
		
		title.add(col1); //JSONArray에 추가할 때는 add를 사용한다.
		title.add(col2);
		title.add(col3);
		title.add(col4);
		title.add(col5);
		
		data.put("cols", title);
		
		//들어갈 형태  => rows 객체에 배열  <- c 라는 객체에 배열  <- v 객체
		//data 객체 => rows 객체 -> 배열  <- c 객체  -> 배열  <- v 객체 2개
		
		JSONArray body = new JSONArray();
		
		for(ToppingVO vo : items) { //items만큼 반복하면 형식을 만든다.
			JSONObject name = new JSONObject();
			name.put("v", vo.getName()); //이름 -> v객체
			
			JSONObject num = new JSONObject();
			num.put("v", vo.getNum()); //개수 -> v객체
			
			JSONObject fresh = new JSONObject();
			fresh.put("v", vo.getFresh());
			
			JSONObject anno = new JSONObject();
			anno.put("v", vo.getAnno());
			
			JSONObject ratio = new JSONObject();
			ratio.put("v", vo.getRatio());
			
			//v객체를 row 배열을 만든 후 추가한다.
			JSONArray row = new JSONArray();
			row.add(name);
			row.add(num);
			row.add(fresh);
			row.add(anno);
			row.add(ratio);
			
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
	
	/*------------------------------- 데이터 4개 (숫자) -------------------------------*/
	
	//candlestick_charts_view 호출 부분
	@RequestMapping(value="/candlestick_charts_view", method=RequestMethod.GET)
	public ModelAndView list_candlestick_view () {
		
		System.out.println("candlestick_charts_view");
		
		return new ModelAndView("google-candlestick-charts");
	}
	
	//ajax 통신 부분
	@ResponseBody
	@RequestMapping(value="/four_num_list" ,method=RequestMethod.GET)
	public ResponseEntity<JSONObject> four_num_list() {
		
		System.out.println("controller start");
		
		ResponseEntity<JSONObject> entity = null;
		List<ToppingVO> items = chartService.selectChartList();
		
		//리스트 형태를 json 형태로 만들어서 리턴
		JSONObject data = new JSONObject();
		
		//컬럼 객체
		//서버에서 웹으로 넘겨줄 가장 큰 단위인 JSONObject
		JSONObject col1 = new JSONObject(); //cols의 첫 번째 object를 담을 JSONObject
		JSONObject col2 = new JSONObject(); //cols의 두 번째 object를 담을 JSONObject
		JSONObject col3 = new JSONObject();
		JSONObject col4 = new JSONObject();
		JSONObject col5 = new JSONObject();
		JSONArray title = new JSONArray(); //위의 두개의 JSONObject를 담을 JSONArray
		
		col1.put("label", "토핑재료"); //JSONObject에 값을 담을 때는 put을 사용한다.
		col1.put("type", "string");
		
		col2.put("label", "비율");
		col2.put("type", "number");
		
		col3.put("label", "개수");
		col3.put("type", "number");
		
		col4.put("label", "신선도"); 
		col4.put("type", "number");
		
		col5.put("label", "달러");
		col5.put("type", "number");
		
		title.add(col1); //JSONArray에 추가할 때는 add를 사용한다.
		title.add(col2);
		title.add(col3);
		title.add(col4);
		title.add(col5);
		
		data.put("cols", title);
		
		//들어갈 형태  => rows 객체에 배열  <- c 라는 객체에 배열  <- v 객체
		//data 객체 => rows 객체 -> 배열  <- c 객체  -> 배열  <- v 객체 2개
		
		JSONArray body = new JSONArray();
		
		for(ToppingVO vo : items) { //items만큼 반복하면 형식을 만든다.
			JSONObject name = new JSONObject();
			name.put("v", vo.getName()); //이름 -> v객체
			
			JSONObject ratio = new JSONObject();
			ratio.put("v", vo.getRatio());
			
			JSONObject num = new JSONObject();
			num.put("v", vo.getNum()); //개수 -> v객체
			
			JSONObject fresh = new JSONObject();
			fresh.put("v", vo.getFresh());
			
			JSONObject dollar = new JSONObject();
			dollar.put("v", vo.getDollar());
			
			//v객체를 row 배열을 만든 후 추가한다.
			JSONArray row = new JSONArray();
			row.add(name);
			row.add(ratio);
			row.add(num);
			row.add(fresh);
			row.add(dollar);
			
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
	
	/*------------------------------- 같은 데이터 2개씩 -> 데이터 4개 (숫자) -------------------------------*/
	
	//waterfall_charts_view 호출 부분
	@RequestMapping(value="/waterfall_charts_view", method=RequestMethod.GET)
	public ModelAndView list_waterfall_view () {
		
		System.out.println("waterfall_charts_view");
		
		return new ModelAndView("google-candlestick-charts2");
	}
	
	//ajax 통신 부분
	@ResponseBody
	@RequestMapping(value="/same_four_list" ,method=RequestMethod.GET)
	public ResponseEntity<JSONObject> same_four_list() {
		
		System.out.println("controller start");
		
		ResponseEntity<JSONObject> entity = null;
		List<ToppingVO> items = chartService.selectChartList();
		
		//리스트 형태를 json 형태로 만들어서 리턴
		JSONObject data = new JSONObject();
		
		//컬럼 객체
		//서버에서 웹으로 넘겨줄 가장 큰 단위인 JSONObject
		JSONObject col1 = new JSONObject(); //cols의 첫 번째 object를 담을 JSONObject
		JSONObject col2 = new JSONObject(); //cols의 두 번째 object를 담을 JSONObject
		JSONObject col3 = new JSONObject();
		JSONObject col4 = new JSONObject();
		JSONObject col5 = new JSONObject();
		JSONArray title = new JSONArray(); //위의 두개의 JSONObject를 담을 JSONArray
		
		col1.put("label", "토핑재료"); //JSONObject에 값을 담을 때는 put을 사용한다.
		col1.put("type", "string");
		
		col2.put("label", "개수");
		col2.put("type", "number");
		
		col3.put("label", "개수");
		col3.put("type", "number");
		
		col4.put("label", "신선도"); 
		col4.put("type", "number");
		
		col5.put("label", "신선도");
		col5.put("type", "number");
		
		title.add(col1); //JSONArray에 추가할 때는 add를 사용한다.
		title.add(col2);
		title.add(col3);
		title.add(col4);
		title.add(col5);
		
		data.put("cols", title);
		
		//들어갈 형태  => rows 객체에 배열  <- c 라는 객체에 배열  <- v 객체
		//data 객체 => rows 객체 -> 배열  <- c 객체  -> 배열  <- v 객체 2개
		
		JSONArray body = new JSONArray();
		
		for(ToppingVO vo : items) { //items만큼 반복하면 형식을 만든다.
			JSONObject name = new JSONObject();
			name.put("v", vo.getName()); //이름 -> v객체
			
			JSONObject num = new JSONObject();
			num.put("v", vo.getNum()); //개수 -> v객체
			
			JSONObject fresh = new JSONObject();
			fresh.put("v", vo.getFresh());
			
			//v객체를 row 배열을 만든 후 추가한다.
			JSONArray row = new JSONArray();
			row.add(name);
			row.add(num);
			row.add(num);
			row.add(fresh);
			row.add(fresh);
			
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
	
	/*=============================== only 숫자 데이터  ===============================*/
	
	//trendlines_view 호출 부분
	@RequestMapping(value="/trendlines_view", method=RequestMethod.GET)
	public ModelAndView list_trendlines_view () {
		
		System.out.println("trendlines_view");
		
		return new ModelAndView("google-scatter-charts2");
	}
	
	//ajax 통신 부분
	@ResponseBody
	@RequestMapping(value="/num_list2" ,method=RequestMethod.GET)
	public ResponseEntity<JSONObject> num_list2() {
		
		System.out.println("controller start");
		
		ResponseEntity<JSONObject> entity = null;
		List<ToppingVO> items = chartService.selectChartList();
		
		//리스트 형태를 json 형태로 만들어서 리턴
		JSONObject data = new JSONObject();
		
		//컬럼 객체
		//서버에서 웹으로 넘겨줄 가장 큰 단위인 JSONObject
		JSONObject col1 = new JSONObject(); //cols의 첫 번째 object를 담을 JSONObject
		JSONObject col2 = new JSONObject(); //cols의 두 번째 object를 담을 JSONObject
		JSONObject col3 = new JSONObject();
		JSONArray title = new JSONArray(); //위의 두개의 JSONObject를 담을 JSONArray
		
		col1.put("label", "토핑 ID");
		col1.put("type", "number");
		
		col2.put("label", "개수"); //JSONObject에 값을 담을 때는 put을 사용한다.
		col2.put("type", "number");
		
		col3.put("label", "신선도");
		col3.put("type", "number");
		
		title.add(col1); //JSONArray에 추가할 때는 add를 사용한다.
		title.add(col2);
		title.add(col3);

		
		data.put("cols", title);
		
		//들어갈 형태  => rows 객체에 배열  <- c 라는 객체에 배열  <- v 객체
		//data 객체 => rows 객체 -> 배열  <- c 객체  -> 배열  <- v 객체 2개
		
		JSONArray body = new JSONArray();
		
		for(ToppingVO vo : items) { //items만큼 반복하면 형식을 만든다.
			
			JSONObject t_no = new JSONObject();
			t_no.put("v", vo.getT_no());
			
			JSONObject num = new JSONObject();
			num.put("v", vo.getNum()); //개수 -> v객체
			
			JSONObject fresh = new JSONObject();
			fresh.put("v", vo.getFresh());
			
			
			//v객체를 row 배열을 만든 후 추가한다.
			JSONArray row = new JSONArray();
			row.add(t_no);
			row.add(num);
			row.add(fresh);
			
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
	
	/*------------------------------- only 숫자 데이터 + annotation options -------------------------------*/
	
	//scatter_charts_view 호출 부분
	@RequestMapping(value="/scatter_charts_view", method=RequestMethod.GET)
	public ModelAndView list_scatter_view () {
		
		System.out.println("scatter_charts_view");
		
		return new ModelAndView("google-scatter-charts");
	}
	
	//ajax 통신 부분
	@ResponseBody
	@RequestMapping(value="/num_list" ,method=RequestMethod.GET)
	public ResponseEntity<JSONObject> num_list() {
		
		System.out.println("controller start");
		
		ResponseEntity<JSONObject> entity = null;
		List<ToppingVO> items = chartService.selectChartList();
		
		//리스트 형태를 json 형태로 만들어서 리턴
		JSONObject data = new JSONObject();
		
		//컬럼 객체
		//서버에서 웹으로 넘겨줄 가장 큰 단위인 JSONObject
		JSONObject col1 = new JSONObject(); //cols의 첫 번째 object를 담을 JSONObject
		JSONObject col2 = new JSONObject(); //cols의 두 번째 object를 담을 JSONObject
		JSONObject col3 = new JSONObject();
		JSONObject col4 = new JSONObject();
		JSONObject col5 = new JSONObject();
		JSONArray title = new JSONArray(); //위의 두개의 JSONObject를 담을 JSONArray
		
		col1.put("label", "토핑 ID");
		col1.put("type", "number");
		
		col2.put("label", "개수"); //JSONObject에 값을 담을 때는 put을 사용한다.
		col2.put("type", "number");
		
		col3.put("label", ""); //annotation options을 사용하기 위해
		col3.put("type", "string");
		col3.put("role", "annotation");
		
		col4.put("label", "신선도");
		col4.put("type", "number");
		
		col5.put("label", "");
		col5.put("type", "string");
		col5.put("role", "annotation");
		
		title.add(col1); //JSONArray에 추가할 때는 add를 사용한다.
		title.add(col2);
		title.add(col3);
		title.add(col4);
		title.add(col5);
		
		data.put("cols", title);
		
		//들어갈 형태  => rows 객체에 배열  <- c 라는 객체에 배열  <- v 객체
		//data 객체 => rows 객체 -> 배열  <- c 객체  -> 배열  <- v 객체 2개
		
		JSONArray body = new JSONArray();
		
		for(ToppingVO vo : items) { //items만큼 반복하면 형식을 만든다.
			
			JSONObject t_no = new JSONObject();
			t_no.put("v", vo.getT_no());
			
			JSONObject num = new JSONObject();
			num.put("v", vo.getNum()); //개수 -> v객체
			
			JSONObject anno = new JSONObject();
			anno.put("v", vo.getAnno());
			
			JSONObject fresh = new JSONObject();
			fresh.put("v", vo.getFresh());
			
			
			//v객체를 row 배열을 만든 후 추가한다.
			JSONArray row = new JSONArray();
			row.add(t_no);
			row.add(num);
			row.add(anno);
			row.add(fresh);
			row.add(anno);
			
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
	
	/*=============================== 데이터 3개(boolean) + format ===============================*/
	
	//table_charts_view 호출 부분
	@RequestMapping(value="/table_charts_view", method=RequestMethod.GET)
	public ModelAndView list_table_view () {
		
		System.out.println("table_charts_view");
		
		return new ModelAndView("google-table-charts");
	}
	
	//ajax 통신 부분
	@ResponseBody
	@RequestMapping(value="/two_fomat_list" ,method=RequestMethod.GET)
	public ResponseEntity<JSONObject> two_fomat_list() {
		
		System.out.println("controller start");
		
		ResponseEntity<JSONObject> entity = null;
		List<ToppingVO> items = chartService.selectChartList();
		
		//리스트 형태를 json 형태로 만들어서 리턴
		JSONObject data = new JSONObject();
		
		//컬럼 객체
		//서버에서 웹으로 넘겨줄 가장 큰 단위인 JSONObject
		JSONObject col1 = new JSONObject(); //cols의 첫 번째 object를 담을 JSONObject
		JSONObject col2 = new JSONObject(); //cols의 두 번째 object를 담을 JSONObject
		JSONObject col3 = new JSONObject();
		JSONObject col4 = new JSONObject();
		JSONArray title = new JSONArray(); //위의 두개의 JSONObject를 담을 JSONArray
		
		col1.put("label", "토핑재료"); //JSONObject에 값을 담을 때는 put을 사용한다.
		col1.put("type", "string");
		
		col2.put("label", "개수");
		col2.put("type", "number");
		
		col3.put("label", "신선도");
		col3.put("type", "number");
		
		col4.put("label", "넣은 재료");
		col4.put("type", "boolean");
		
		title.add(col1); //JSONArray에 추가할 때는 add를 사용한다.
		title.add(col2);
		title.add(col3);
		title.add(col4);
		
		data.put("cols", title);
		
		//들어갈 형태  => rows 객체에 배열  <- c 라는 객체에 배열  <- v 객체
		//data 객체 => rows 객체 -> 배열  <- c 객체  -> 배열  <- v 객체 2개
		
		JSONArray body = new JSONArray();
		
		for(ToppingVO vo : items) { //items만큼 반복하면 형식을 만든다.
			JSONObject name = new JSONObject();
			name.put("v", vo.getName()); //이름 -> v객체
			name.put("f", vo.getAnno());
			
			JSONObject num = new JSONObject();
			num.put("v", vo.getNum()); //개수 -> v객체
			num.put("f", vo.getNum()+"개");
			
			JSONObject fresh = new JSONObject();
			fresh.put("v", vo.getFresh());
			fresh.put("f", vo.getFresh()+"점");
			
			JSONObject putIn = new JSONObject();
			putIn.put("v", vo.isPutIn()); //type: boolean
			
			//v객체를 row 배열을 만든 후 추가한다.
			JSONArray row = new JSONArray();
			row.add(name);
			row.add(num);
			row.add(fresh);
			row.add(putIn);
			
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
	
	/*=============================== 데이터 2개 + tooltip options-> 데이터 3개 ===============================*/
	
	//steppedArea_charts_view 호출 부분
	@RequestMapping(value="/steppedArea_charts_view", method=RequestMethod.GET)
	public ModelAndView list_steppedArea_view () {
			
		System.out.println("steppedArea_charts_view");
			
		return new ModelAndView("google-steppedArea-charts");
	}

	//ajax 통신 부분
	@ResponseBody
	@RequestMapping(value="/tooltip_two_list" ,method=RequestMethod.GET)
	public ResponseEntity<JSONObject> tooltip_two_list() {
		
		System.out.println("controller start");
		
		ResponseEntity<JSONObject> entity = null;
		List<ToppingVO> items = chartService.selectChartList();
		
		//리스트 형태를 json 형태로 만들어서 리턴
		JSONObject data = new JSONObject();
		
		//컬럼 객체
		//서버에서 웹으로 넘겨줄 가장 큰 단위인 JSONObject
		JSONObject col1 = new JSONObject(); //cols의 첫 번째 object를 담을 JSONObject
		JSONObject col2 = new JSONObject(); //cols의 두 번째 object를 담을 JSONObject
		JSONObject col3 = new JSONObject();
		JSONObject col4 = new JSONObject();
		JSONArray title = new JSONArray(); //위의 두개의 JSONObject를 담을 JSONArray
		
		col1.put("label", "토핑재료"); //JSONObject에 값을 담을 때는 put을 사용한다.
		col1.put("type", "string");
		
		col2.put("label", "개수");
		col2.put("type", "number");
	
		col3.put("label", ""); //tooltip options을 사용하기 위해
		col3.put("type", "string");
		col3.put("role", "tooltip");
		
		col4.put("label", "신선도");
		col4.put("type", "number");
		
		title.add(col1); //JSONArray에 추가할 때는 add를 사용한다.
		title.add(col2);
		title.add(col3);
		title.add(col4);
		
		data.put("cols", title);
		
		//들어갈 형태  => rows 객체에 배열  <- c 라는 객체에 배열  <- v 객체
		//data 객체 => rows 객체 -> 배열  <- c 객체  -> 배열  <- v 객체 2개
		
		JSONArray body = new JSONArray();
		
		for(ToppingVO vo : items) { //items만큼 반복하면 형식을 만든다.
			JSONObject name = new JSONObject();
			name.put("v", vo.getName()); //이름 -> v객체
			
			JSONObject num = new JSONObject();
			num.put("v", vo.getNum()); //개수 -> v객체
			
			JSONObject anno = new JSONObject();
			anno.put("v", vo.getNum()+"\n"+vo.getAnno()); //tooltip options을 사용하기 위해 데이터 추가
			
			JSONObject fresh = new JSONObject();
			fresh.put("v", vo.getFresh());
			
			//v객체를 row 배열을 만든 후 추가한다.
			JSONArray row = new JSONArray();
			row.add(name);
			row.add(num);
			row.add(anno);
			row.add(fresh);
			
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

