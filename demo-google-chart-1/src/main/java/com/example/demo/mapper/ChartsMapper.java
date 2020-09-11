package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.vo.ToppingVO;

@Mapper
public interface ChartsMapper {
	
	public List<ToppingVO> selectBarChartList();
}
