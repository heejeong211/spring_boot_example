package com.example.demo.vo;

import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name="tb_member")
@Table(name="tb_member")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Member {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id; //GenerationType.IDENTITY 사용하려면 db에 ai 추가..
	
	@Column(length = 255, nullable = false)
	private String name;
	
	@Column(length = 255, nullable = false, unique = true)
	private String account;
	
	@Column(length = 255, nullable = false)
	private String password;
	

}
