package com.example.demo.service;

import java.util.List;

import com.example.demo.vo.Criteria;
import com.example.demo.vo.PagingVO;

public interface PagingService {
	
	public List<PagingVO> selectList(Criteria cri);
	
	public List<PagingVO> selectListNotice(Criteria cri); 
	
	public List<PagingVO> selectListAll(Criteria cri);
	
	public int countListCriteria(Criteria cri);
}
