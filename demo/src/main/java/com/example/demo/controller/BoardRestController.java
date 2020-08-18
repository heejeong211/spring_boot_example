package com.example.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.BoardService;
import com.example.demo.vo.BoardVO;

@RestController
@RequestMapping("/rest/*")
public class BoardRestController {
	
	@Autowired
	BoardService boardService;
	
	//리스트 출력(이렇게 해도 되나..?)
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public ArrayList<BoardVO> list(Model model) throws Exception{
		
		System.out.println("list");
		
		return boardService.selectBoardList();
	}
	
	//게시물 보기
	@RequestMapping(value="/{b_no}", method=RequestMethod.GET)
	public BoardVO content_view(Model model, @PathVariable("b_no") int b_no) throws Exception{
		
		System.out.println("content_view");
		
		return boardService.selectBoard(b_no);
	}
	
	//게시물 쓰기
	@RequestMapping(value="/write", method=RequestMethod.POST, consumes = "application/json")
	public String write(@RequestBody BoardVO boardVO) throws Exception{
		
		System.out.println("write");
		
		boardService.insertBoardWrite(boardVO);
		
		return "write success!!";
	}
	
	//게시물 삭제
	@RequestMapping(value="/{b_no}", method=RequestMethod.DELETE, consumes = "application/json")
	public String delete(@PathVariable("b_no") int b_no) throws Exception{
			
		System.out.println("delete");
			
		boardService.deleteBoard(b_no);
			
		return "delete success!!";
	}
	
	//게시물 수정(put 방식)
	@RequestMapping(value="/{b_no}", method=RequestMethod.PUT, consumes = "application/json")
	public String update(@PathVariable("b_no") int b_no, @RequestBody BoardVO boardVO) throws Exception{
		
		System.out.println("update");
		
		boardVO.setB_no(b_no); //boardVO를 수정해야하는데, 그럴려면 b_no의 값이 들어와야 수정이 가능함
		boardService.updateBoardOne(boardVO);
		
		return "update success!!";
	}
	
	//게시물 수정(patch 방식) -> query문으로 조건을 줌(case when ~then)
	@RequestMapping(value="/{b_no}", method=RequestMethod.PATCH, consumes = "application/json")
	public String modify(@PathVariable("b_no") int b_no, @RequestBody BoardVO boardVO) throws Exception{
		
		System.out.println("modify");
		
		boardVO.setB_no(b_no); //boardVO를 수정해야하는데, 그럴려면 b_no의 값이 들어와야 수정이 가능함
		boardService.patchBoard(boardVO);
		
		return "patch success!!";
	}
	
	//게시물 수정(patch 방식) -> controller단에서 조건을 줌(.length() > 0 으로 null 체크해도 되고, null!= 로 null 체크해도 됨)
	@RequestMapping(value="/patch/{b_no}", method=RequestMethod.PATCH, consumes = "application/json")
	public String modifyOne(@PathVariable("b_no") int b_no, @RequestBody BoardVO boardVO) throws Exception{
		
		System.out.println("modify");
		
		/**********
		 * 
		 */
		BoardVO selectVO = boardService.selectBoard(b_no);
		
		if (null!=boardVO.getB_title()) selectVO.setB_title(boardVO.getB_title());
		if (null!=boardVO.getB_content()) selectVO.setB_content(boardVO.getB_content());
		if (null!=boardVO.getB_nick()) selectVO.setB_nick(boardVO.getB_nick());
		/**********
		 * 
		 */
		
		boardVO.setB_no(b_no);
		boardService.updateBoardOne(selectVO);
		
		
		return "patch success!!";
	}
	
}
