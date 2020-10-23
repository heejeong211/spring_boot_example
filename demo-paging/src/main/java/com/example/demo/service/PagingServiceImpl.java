package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.mapper.PagingMapper;
import com.example.demo.vo.Criteria;
import com.example.demo.vo.PagingVO;

@Service
public class PagingServiceImpl implements PagingService {

	@Autowired
	PagingMapper pagingMapper;
	
	@Override
	public List<PagingVO> selectList(Criteria cri) {
		
		return pagingMapper.selectList(cri);
	}

	@Override
	public List<PagingVO> selectListNotice(Criteria cri) {
		
		return pagingMapper.selectListNotice(cri);
	}
	
	@Override
	public List<PagingVO> selectListAll(Criteria cri) {
		
		return pagingMapper.selectListAll(cri);
	}
	
	@Override
	public int countListCriteria(Criteria cri) {
		
		return pagingMapper.countListCriteria(cri);
	}

	

}
