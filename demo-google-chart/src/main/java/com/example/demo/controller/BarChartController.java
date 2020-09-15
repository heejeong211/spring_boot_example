package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.service.BarChartService;
import com.example.demo.vo.ToppingVO;

@Controller
public class BarChartController {

	@Autowired
	BarChartService barChartService;
	
	@GetMapping("/list")
	public ModelAndView list() {
		
		ModelAndView modelAndView = new ModelAndView();
		
		List<ToppingVO> list = barChartService.selectBarChartList();
		modelAndView.addObject("list", list);
		modelAndView.setViewName("html-google-charts");
		
		System.out.println("리스트 사이즈 : " + list.size());

		String str = "[";
		str +="['topping', 'number'], ";
		int num = 0;
		for(ToppingVO vo : list){
			
			str +="['";
			str += vo.getName();
			str +="' , ";
			str += vo.getNum();
			str +="]";
			
//			str += "['"+vo.getName()+"', "+vo.getNum()+"]";
			
			num ++;
			if(num<list.size()){
				str +=", ";
			}		
		}
		str += "]";
		modelAndView.addObject("str", str);
		
		System.out.println(str);
		
		return modelAndView;
	}
}
//JavaScript 오류
//Uncaught (in promise) Error: Data for arrayToDataTable is not an array.
//Uncaught (in promise) Error: Invalid row #1

//String으로 보내서 그런것 같긴한데... 근데 왜... 예제에서는 잘되고 난 안되지???ㅜㅠ
//아니면 내가 thymeleaf를 잘못쓰고 있나......
//html 말고 jsp로 해봐야하나.........

//jsp로 하면 잘됨... 그럼... 내가 thymeleaf를 잘못 사용하나봄....
//thymeleaf ${} VS el ${}

//thymeleaf로 성공... 근데 이유를 모르겠어..ㅋㅋㅋ

//예제
//http://macaronics.net/index.php/m01/spring/view/159
//https://vvh-avv.tistory.com/28
