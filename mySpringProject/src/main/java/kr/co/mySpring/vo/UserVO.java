package kr.co.mySpring.vo;

public class UserVO {
	String keyword;						//�˻���
	String currentCount;				//���� ������
	String pageCount;					//������ �� ��� ��ȯ����
	String keywordType;					//�˻�����  1. ȸ�����̵� 2. ȸ���̸�
	String address;						//�ּ�
	String user_key;					//ȸ��pk
	String user_id;						//���̵�
	String password;					//��й�ȣ
	String user_name; 					//ȸ���̸�
	String phone_number;				//����ȣ
	String keepLogin;					//�ڵ��α��� ����
	
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
