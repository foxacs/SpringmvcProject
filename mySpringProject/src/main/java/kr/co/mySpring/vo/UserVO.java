package kr.co.mySpring.vo;

public class UserVO {
	String keyword;						//검색어
	String currentCount;				//현재 페이지
	String pageCount;					//페이지 당 결과 반환갯수
	String keywordType;					//검색조건  1. 회원아이디 2. 회원이름
	String address;						//주소
	String user_key;					//회원pk
	String user_id;						//아이디
	String password;					//비밀번호
	String user_name; 					//회원이름
	String phone_number;				//폰번호
	String keepLogin;					//자동로그인 여부
	
	public String getKeepLogin() {
		return keepLogin;
	}

	public void setKeepLogin(String keepLogin) {
		this.keepLogin = keepLogin;
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
	
	public String getKeywordType() {
		return keywordType;
	}
	
	public void setKeywordType(String keywordType) {
		this.keywordType = keywordType;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getUser_key() {
		return user_key;
	}
	
	public void setUser_key(String user_key) {
		this.user_key = user_key;
	}
	
	public String getUser_id() {
		return user_id;
	}
	
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getUser_name() {
		return user_name;
	}
	
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	
	public String getPhone_number() {
		return phone_number;
	}
	
	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}
	
	@Override
	public String toString() {
		return "UserVO [keyword=" + keyword + ", currentCount=" + currentCount + ", pageCount=" + pageCount
				+ ", keywordType=" + keywordType + ", address=" + address + ", user_key=" + user_key + ", user_id="
				+ user_id + ", password=" + password + ", user_name=" + user_name + ", phone_number=" + phone_number
				+ "]";
	}
}
