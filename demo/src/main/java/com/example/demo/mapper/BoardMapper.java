package com.example.demo.mapper;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.vo.BoardVO;

@Mapper
public interface BoardMapper {

	public ArrayList<BoardVO> selectBoardList(); //리스트 출력
	
	public HashMap<Integer, Object> selectBoardOne(int b_no); //게시물 보기
	
	public void insertBoardWrite(BoardVO boardVO); //게시물 쓰기
	
	public void updateBoardOne(BoardVO boardVO); //게시물 수정(put방식)
	
	public void deleteBoardOne(BoardVO boardVO); //게시물 삭제(일반 controller)
	
	public void deleteBoard(int b_no); //게시물 삭제(restcontroller)
	
	public void patchBoard(BoardVO boardVO); //게시물 수정(patch방식 - query문으로 조건 줌)
	
	public BoardVO selectBoard(int b_no); //게시물 보기(이게 HashMap보다 간단할 수 있음, patch방식 - controller단에서 조건 줌)
	
}
