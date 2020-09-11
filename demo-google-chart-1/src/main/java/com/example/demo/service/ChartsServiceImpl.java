package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.mapper.ChartsMapper;
import com.example.demo.vo.ToppingVO;

@Service
public class ChartsServiceImpl implements ChartsService{
	
	@Autowired
	ChartsMapper barChartMapper;

	@Override
	public List<ToppingVO> selectBarChartList() {
		
		return barChartMapper.selectBarChartList();
	}

}
