package com.example.demo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ToppingVO {
	
	private int t_no;
	private String name;
	private int num;
	private int fresh;
}
