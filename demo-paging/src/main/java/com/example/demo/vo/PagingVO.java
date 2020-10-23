package com.example.demo.vo;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagingVO {
	
	private int b_no;
	private String division;
	private String title;
	private String content;
	private String user;
	private Timestamp createDate;
	
	private Criteria cri;
}
