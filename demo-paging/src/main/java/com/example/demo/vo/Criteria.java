package com.example.demo.vo;

//게시판 페이징 전용 클래스
//Criteria: 사전적 의미로는 검색기준, 분류기준
public class Criteria {
	
	private int page; //보여줄 페이지 번호
	private int perPageNum; //페이지 당 보여줄 게시글의 개수
	private int orderByField;
	
	private String searchDivision; //검색구분
	private String searchType; //검색타입
	private String keyword; //검색어
	
	public Criteria() {
		this.page = 1;
		this.perPageNum = 10;
		this.orderByField = 0;
		this.searchDivision = "";
		this.searchType = "";
		this.keyword = ""; 
	}
	
	public int getPage() {
		return page;
	}
	
	public void setPage(int page) {
		if(page <= 0) {
			this.page = 1;
			return;
		}
		this.page = page;
	}
	
	public int getPerPageNum() {
		return perPageNum;
	}
	
	public void setPerPageNum(int perPageNum) {
		if(perPageNum <= 0 || perPageNum > 100) {
			this.perPageNum = 10;
			return;
		}
		this.perPageNum = perPageNum;
	}
	
	//=> 이게 무슨 소리래........?
	//limit 구문에서 시작 위치를 지정할 때 사용된다. 예를 들어, 10개씩 출력하는 경우 3페이지의 데이터는 limit 20, 10 과 같은 형태가 되어야 한다.
	//this.page 가 1이면 0이 되어야 한다. mysql limit 0, 10 해야 처음부터 10개씩 나온다.
	//mybatis 조회쿼리의 #{pageStart}에 전달된다
	public int getPageStart() {
		return (this.page -1) * perPageNum;
	}
	
	public int getPageEnd() {
		return (getPageStart() + perPageNum) - 1;
	}

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
	public String getSearchDivision() {
		return searchDivision;
	}

	public void setSearchDivision(String searchDivision) {
		this.searchDivision = searchDivision;
	}
	
	public int getOrderByField() {
		return orderByField;
	}

	public void setOrderByField(int orderByField) {
		this.orderByField = orderByField;
	}

	@Override
	public String toString() {
		return "Criteria [page=" + page + ", perPageNum=" + perPageNum + ", orderByField=" + orderByField
				+ ", searchDivision=" + searchDivision + ", searchType=" + searchType + ", keyword=" + keyword + "]";
	}

}
