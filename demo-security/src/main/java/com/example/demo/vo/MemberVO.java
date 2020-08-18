package com.example.demo.vo;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MemberVO {

	
	private Integer id;
	private String name;
	private String account;
	private String password;
	
	public Member toEntity() {
		return new Member(id, name, account, password);
	}
	
	
}
