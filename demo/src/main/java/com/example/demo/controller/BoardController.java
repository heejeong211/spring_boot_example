package com.example.demo.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.service.BoardService;
import com.example.demo.vo.BoardVO;

/**
 * Handles requests for the application home page.
 */
@RequestMapping("/")
@Controller
public class BoardController {
	
	@Autowired
	BoardService boardService;
	
	//리스트 출력
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Model model) {
		
		System.out.println("list");
		
		model.addAttribute("list", boardService.selectBoardList());
		
		return "list";
	}
	
	//게시물 보기
	@RequestMapping(value = "/content_view", method = RequestMethod.GET)
	public String content_view(Model model, HttpServletRequest request) {
		
		System.out.println("content_view");
		
		int b_no = Integer.parseInt(request.getParameter("b_no"));
		System.out.println("b_no: " + b_no);
		model.addAttribute("content_view",boardService.selectBoardOne(b_no));
		
		return "content_view";
	}
	
	//게시물 쓰기 view
	@RequestMapping(value = "/write_view", method = RequestMethod.GET)
	public String write_view(Model model) {
			
		System.out.println("write_view");
			
		return "write_view";
	}
	
	//게시물 쓰기
	@RequestMapping(value = "/write", method = RequestMethod.POST)
	public String write(BoardVO boardVO) {
			
		System.out.println("write");
		
		boardService.insertBoardWrite(boardVO);
			
		return "redirect:list";
	}
	
	//게시물 수정하기
	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	public String modify(BoardVO boardVO) {
				
		System.out.println("modify");
			
		boardService.updateBoardOne(boardVO);
				
		return "redirect:list";
	}
	
	//게시물 삭제하기
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String delete(BoardVO boardVO) {
					
		System.out.println("delete");
				
		boardService.deleteBoardOne(boardVO);
					
		return "redirect:list";
	}
	
}
