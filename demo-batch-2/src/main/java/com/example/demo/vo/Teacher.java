package com.example.demo.vo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@NoArgsConstructor
@Entity
public class Teacher {

	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyy-MM-dd hh:mm:ss");
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String tName;
	private Long tStudent;
	
	public Teacher(String tName, Long tStudent) {
		this.tName = tName;
		this.tStudent = tStudent;
	}

	public Teacher(Long id, String tName, Long tStudent) {
		this.id = id;
		this.tName = tName;
		this.tStudent = tStudent;
	}
	
	
	
}
