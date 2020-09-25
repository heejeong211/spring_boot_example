package com.example.demo.service;

import java.util.List;

import com.example.demo.vo.BarToppingVO;

public interface ChartsService {

	public List<BarToppingVO> selectChartList();
	
	public void insertChart(BarToppingVO barToppingVO);
}
