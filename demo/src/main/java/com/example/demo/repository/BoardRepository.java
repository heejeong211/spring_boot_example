package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.vo.BoardEntityVO;

@Repository
public interface BoardRepository extends JpaRepository<BoardEntityVO, Long> {
	//비워있어도 잘 작동함.
	//long이 아니라 Long으로 작성. ex) int => Integer 같이 primitive형식 사용하지 못함.
	
	//findBy 뒤에 컬럼명을 붙여주면 이를 이용한 검색이 가능하다. 근데 왜 난 findBy컬럼명(Invalid derived query!)이 아니라 find컬럼명이 되는 거지...??
	/*
	 * public List<BoardEntityVO> findB_no(String b_no); public List<BoardEntityVO>
	 * findB_title(String b_title); public List<BoardEntityVO> findB_content(String
	 * b_content); public List<BoardEntityVO> findB_nick(String b_nick);
	 */
	
	//like 검색 가능
	/* public List<BoardEntityVO> findB_noLike(String keyword); */
}