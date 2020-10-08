package kr.co.mySpring.vo;

public class ReplyVO {
	String reply_key;
	String depth;
	String board_key;
	String mem_idx;
	String mem_userid;
	String comment;
	String input_date;
	String update_date;
	String currentCount;				//현재 페이지
	String pageCount;					//페이지 당 결과 반환갯수
	String target_mem_idx;				
	String target_id;
	
	
	public String getReply_key() {
		return reply_key;
	}
	public void setReply_key(String reply_key) {
		this.reply_key = reply_key;
	}
	public String getDepth() {
		return depth;
	}
	public void setDepth(String depth) {
		this.depth = depth;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getTarget_mem_idx() {
		return target_mem_idx;
	}
	public void setTarget_mem_idx(String target_mem_idx) {
		this.target_mem_idx = target_mem_idx;
	}
	public String getTarget_id() {
		return target_id;
	}
	public void setTarget_id(String target_id) {
		this.target_id = target_id;
	}
	public String getMem_userid() {
		return mem_userid;
	}
	public void setMem_userid(String mem_userid) {
		this.mem_userid = mem_userid;
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
	public String getContent() {
		return comment;
	}
	public void setContent(String content) {
		this.comment = content;
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
