package com.example.demo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.service.PagingService;
import com.example.demo.vo.Criteria;
import com.example.demo.vo.PageMaker;

@Controller
public class PagingController {
	
	@Autowired
	PagingService pagingService;
	
	//목록보기+페이징
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public String list(Model model, @ModelAttribute("cri")Criteria cri) {
		
		//공지사항 리스트 출력
		model.addAttribute("notice", pagingService.selectListNotice(cri));
		
		//일반 리스트 출력 
		model.addAttribute("list", pagingService.selectList(cri));
		
		//페이징
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(pagingService.countListCriteria(cri));
		
		model.addAttribute("pageMaker", pageMaker);
		
		return "list";
	}
	
	//목록보기+페이징+검색
	@RequestMapping(value="/listSearch", method=RequestMethod.POST)
	public String list_search(Model model, @ModelAttribute("cri")Criteria cri) {
		
		//공지사항 리스트 출력
		model.addAttribute("notice", pagingService.selectListNotice(cri));
		
		//일반 리스트 출력 
		model.addAttribute("list", pagingService.selectList(cri));
		
		//페이징
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(pagingService.countListCriteria(cri));
		
		model.addAttribute("pageMaker", pageMaker);
		
		return "list";
	}
	
	/*======================================================================================================================*/
	
	//ajax
	//목록보기+페이징
	@RequestMapping(value="/ajaxList", method= {RequestMethod.GET, RequestMethod.POST})
	public String ajax_list(Model model, @ModelAttribute("cri")Criteria cri) {
		
		//공지사항 리스트 출력
		model.addAttribute("notice", pagingService.selectListNotice(cri));
		
		//일반 리스트 출력
		model.addAttribute("list", pagingService.selectList(cri));
		
		//union all을 사용한 리스트 출력 => 이걸 이용해서 출력하고 싶다..
	//	model.addAttribute("list", pagingService.selectListAll(cri));
		
		//페이징
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(pagingService.countListCriteria(cri));
		
		model.addAttribute("pageMaker", pageMaker);
		
		return "ajaxList2";
	}
	
	//목록보기+페이징+검색
	@RequestMapping(value="/ajaxListSearch", method= {RequestMethod.GET, RequestMethod.POST})
	public String ajax_list_search(Model model, @ModelAttribute("cri")Criteria cri) {
		
		//공지사항 리스트 출력
		model.addAttribute("notice", pagingService.selectListNotice(cri));
		
		//일반 리스트 출력
		model.addAttribute("list", pagingService.selectList(cri));
		
		//union all을 사용한 리스트 출력
	//	model.addAttribute("list", pagingService.selectListAll(cri));
		
		//페이징
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(pagingService.countListCriteria(cri));
		
		model.addAttribute("pageMaker", pageMaker);
		
		return "ajaxList2";
	}

}

//목록보기+페이징+검색을 하나의 매핑으로 할 순 없을까??? => 검색하는 친구도 ajax로 하니까 됨ㅎ

//return을 "ajaxList"로 했을 때 => url이 /ajaxListSearch인 상태로 페이징이 되는데 문제는 페이징이 따르는 url은 /ajaxList로 검색된 채 페이지 이동이 안됨...

//return을 "/ajaxList"로 했을 때 => url이 /ajaxList로 뜨면서 파라미터도 다 나옴... 마찬가지로 검색한 걸 토대로 페이지 이동은 안됨...

//return을 "/ajaxList :: #list"로 했을 때 => 에러남;; 아마도 페이지 번호에 걸려 있는 함수와 값이 서로 달라서 인듯.. form에 input hidden로 되어있는 애를 살짝 바꿔주면 될것 같기도함!! 해보고 오겠음!! 해봤는데.. 안됨....ㅠ

//return을 "/ajaxList :: #list"로 했을 때 그리고 done 이부분 지워 봄..=> 음... /ajaxList :: #list와 같은 결과 나옴... 에러남;;

//일단은 해결함.. 하지만 상시 목록보기만 css 적용을 시키는지는 방법을 잘 모르겠음... 그거 해결해야 함!!