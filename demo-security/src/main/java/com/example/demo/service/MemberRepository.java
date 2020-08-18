package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.GrantedAuthority;

import com.example.demo.vo.Member;

public interface MemberRepository extends JpaRepository<Member, Integer>{
	Optional<Member> findByAccount(String account);
	
}
