package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.BoardJpaService;
import com.example.demo.vo.BoardEntityVO;

@RestController
@RequestMapping("/jpa/*")
public class BoardJpaRestController {

	//기본형
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired 
	BoardJpaService boardService;
	
	//리스트 출력
	@GetMapping(value="/list", produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<List<BoardEntityVO>> getAllBoards() {
		List<BoardEntityVO> boards = boardService.findAll();
		return new ResponseEntity<List<BoardEntityVO>>(boards, HttpStatus.OK);
	}

	//게시물 보기
	@GetMapping(value="/{b_no}", produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<BoardEntityVO> getBoard(@PathVariable("b_no") Long b_no) {
		Optional<BoardEntityVO> board = boardService.findById(b_no);
		return new ResponseEntity<BoardEntityVO>(board.get(), HttpStatus.OK);
	}
	
	//게시물 삭제
	@DeleteMapping(value="/{b_no}", produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<Void> deleteBoard(@PathVariable("b_no") Long b_no) {
		boardService.deleteById(b_no);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	//게시물 수정 (일단 되긴 하는데, key-value로 보내야 됨.. json으로 보내면 오류남..)
	@PutMapping(value="/{b_no}", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = "application/json")
	public ResponseEntity<BoardEntityVO> updateBoard(@PathVariable("b_no") Long b_no, BoardEntityVO boardVO) {
		boardService.updateById(b_no, boardVO);
		return new ResponseEntity<BoardEntityVO>(boardVO, HttpStatus.OK);
	}
	
	//게시글 입력 (일단 되긴 하는데, key-value로 보내야 됨.. json으로 보내면 오류남..)
	@PostMapping
	public ResponseEntity<BoardEntityVO> save(BoardEntityVO boardVO) {
		return new ResponseEntity<BoardEntityVO>(boardService.save(boardVO), HttpStatus.OK);
	}
	
	@RequestMapping(value="/write", method=RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = "application/json")
	public ResponseEntity<BoardEntityVO> save(HttpServletRequest req, BoardEntityVO boardVO) {
		return new ResponseEntity<BoardEntityVO>(boardService.save(boardVO), HttpStatus.OK);
	}

}
