package com.example.demo.vo;

public class BoardVO {

	private int b_no;
	private String b_title;
	private String b_content;
	private String b_nick;
	
	public BoardVO(int b_no, String b_title, String b_content, String b_nick) {
		super();
		this.b_no = b_no;
		this.b_title = b_title;
		this.b_content = b_content;
		this.b_nick = b_nick;
	}

	public BoardVO() { }

	public int getB_no() {
		return b_no;
	}

	public void setB_no(int b_no) {
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
	
	
}
