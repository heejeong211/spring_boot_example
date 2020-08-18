package com.test.ex.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.ex.mapper.BoardMapper;
import com.test.ex.vo.BoardVO;

@Service("BoardService")
public class BoardServiceImpl implements BoardService{

	@Autowired
	BoardMapper boardMapper;
	
	//리스트 출력
	@Override
	public ArrayList<BoardVO> selectBoardList() {
		
		return boardMapper.selectBoardList();
	}

	//게시물 보기
	@Override
	public HashMap<Integer, Object> selectBoardOne(int b_no) {
		
		return boardMapper.selectBoardOne(b_no);
	}

	//게시물 쓰기
	@Override
	public void insertBoardWrite(BoardVO boardVO) {
		
		boardMapper.insertBoardWrite(boardVO);
		
	}

	//게시물 수정(put방식)
	@Override
	public void updateBoardOne(BoardVO boardVO) {
		
		boardMapper.updateBoardOne(boardVO);
		
	}

	//게시물 삭제(일반 controller)
	@Override
	public void deleteBoardOne(BoardVO boardVO) {
		
		boardMapper.deleteBoardOne(boardVO);
		
	}

	//게시물 삭제(restcontroller)
	@Override
	public void deleteBoard(int b_no) {
		
		boardMapper.deleteBoard(b_no);
		
	}
	
	//게시물 수정(patch방식 - query문으로 조건 줌)
	@Override
	public void patchBoard(BoardVO boardVO) {
			
		boardMapper.patchBoard(boardVO);
			
	}

	//게시물 보기(이게 HashMap보다 간단할 수 있음, patch방식 - controller단에서 조건 줌)
	@Override
	public BoardVO selectBoard(int b_no) {
		
		return boardMapper.selectBoard(b_no);
	}

}
