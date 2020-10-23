package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.vo.Criteria;
import com.example.demo.vo.PagingVO;

@Mapper
public interface PagingMapper {

	public List<PagingVO> selectList(Criteria cri);
	
	public List<PagingVO> selectListNotice(Criteria cri); 
	
	public List<PagingVO> selectListAll(Criteria cri);
	
	public int countListCriteria(Criteria cri);
}
