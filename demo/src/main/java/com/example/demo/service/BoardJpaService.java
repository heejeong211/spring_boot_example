package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.repository.BoardRepository;
import com.example.demo.vo.BoardEntityVO;

@Service
public class BoardJpaService {
	
	@Autowired 
	private BoardRepository boardRepository;
	
	//조회
	public List<BoardEntityVO> findAll() {
		List<BoardEntityVO> boards = new ArrayList<>();
		boardRepository.findAll().forEach(e -> boards.add(e));
		return boards;
	}
	
	//하나만 조회
	public Optional<BoardEntityVO> findById(Long b_no) {
		Optional<BoardEntityVO> boardVO = boardRepository.findById(b_no);
		return boardVO;
	}
	
	//삭제
	public void deleteById(Long b_no) {
		boardRepository.deleteById(b_no);
	}
	
	//입력
	public BoardEntityVO save(BoardEntityVO boardVO) {
		boardRepository.save(boardVO);
		return boardVO;
	}
	
	//수정
	//Optional객체를 사용하면 예상치 못한 NullPointerException 예외를 회피할 수 있음.
	//.isPresent()메소드를 사용하여 Optional객체에 저장된 값이 null인지 아닌지를 판단할 수 있음.
	public void updateById(Long b_no, BoardEntityVO boardVO) {
		Optional<BoardEntityVO> e = boardRepository.findById(b_no);
		
		if(e.isPresent()) {
			e.get().setB_no(boardVO.getB_no());
			e.get().setB_title(boardVO.getB_title());
			e.get().setB_content(boardVO.getB_content());
			e.get().setB_nick(boardVO.getB_nick());
			boardRepository.save(boardVO);
		}
	}

}
