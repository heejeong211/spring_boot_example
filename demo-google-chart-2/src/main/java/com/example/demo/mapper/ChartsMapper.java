package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.vo.BarToppingVO;

@Mapper
public interface ChartsMapper {
	
	public List<BarToppingVO> selectChartList();
	
	public void insertChart(BarToppingVO barToppingVO);
}
