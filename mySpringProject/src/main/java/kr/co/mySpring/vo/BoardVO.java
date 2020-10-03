package kr.co.mySpring.vo;

public class BoardVO {
	String board_key;
	String mem_idx;
	String title;
	String content;
	String input_date;
	String update_date;
	String keyword;						//검색어
	String currentCount;				//현재 페이지
	String pageCount;					//페이지 당 결과 반환갯수
	String mem_userid;
	
	public String getMem_userid() {
		return mem_userid;
	}
	public void setMem_userid(String mem_userid) {
		this.mem_userid = mem_userid;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getCurrentCount() {
		return currentCount;
	}
	public void setCurrentCount(String currentCount) {
		this.currentCount = currentCount;
	}
	public String getPageCount() {
		return pageCount;
	}
	public void setPageCount(String pageCount) {
		this.pageCount = pageCount;
	}
	public String getBoard_key() {
		return board_key;
	}
	public void setBoard_key(String board_key) {
		this.board_key = board_key;
	}
	public String getMem_idx() {
		return mem_idx;
	}
	public void setMem_idx(String mem_idx) {
		this.mem_idx = mem_idx;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getInput_date() {
		return input_date;
	}
	public void setInput_date(String input_date) {
		this.input_date = input_date;
	}
	public String getUpdate_date() {
		return update_date;
	}
	public void setUpdate_date(String update_date) {
		this.update_date = update_date;
	}
}
