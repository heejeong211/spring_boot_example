package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.mapper.BarChartMapper;
import com.example.demo.vo.ToppingVO;

@Service
public class BarChartServiceImpl implements BarChartService{
	
	@Autowired
	BarChartMapper barChartMapper;

	@Override
	public List<ToppingVO> selectBarChartList() {
		
		return barChartMapper.selectBarChartList();
	}

}
