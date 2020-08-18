package com.example.demo.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name="board")
public class BoardEntityVO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long b_no;
	
	@Column(name="b_title", nullable=false)
	private String b_title;
	
	@Column(name="b_content", nullable=false)
	private String b_content;
	
	@Column(name="b_nick", nullable=false)
	private String b_nick;
	
	public BoardEntityVO(Long b_no, String b_title, String b_content, String b_nick) {
		super();
		this.b_no = b_no;
		this.b_title = b_title;
		this.b_content = b_content;
		this.b_nick = b_nick;
	}

	public BoardEntityVO() { }

	public Long getB_no() {
		return b_no;
	}

	public void setB_no(Long b_no) {
		this.b_no = b_no;
	}

	public String getB_title() {
		return b_title;
	}

	public void setB_title(String b_title) {
		this.b_title = b_title;
	}

	public String getB_content() {
		return b_content;
	}

	public void setB_content(String b_content) {
		this.b_content = b_content;
	}

	public String getB_nick() {
		return b_nick;
	}

	public void setB_nick(String b_nick) {
		this.b_nick = b_nick;
	}

	@Override
	public String toString() {
		return "BoardEntityVO [b_no=" + b_no + ", b_title=" + b_title + ", b_content=" + b_content + ", b_nick="
				+ b_nick + "]";
	}
	
	
}
