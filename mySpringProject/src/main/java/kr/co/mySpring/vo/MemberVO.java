package kr.co.mySpring.vo;

public class MemberVO {
	String mem_idx;
	String mem_userid;
	String mem_email;
	String mem_password;
	String mem_status;
	String mem_class;
	String last_login_date;
	String mem_regtime;
	String mem_regip;
	String keepLogin;
	
	public String getKeepLogin() {
		return keepLogin;
	}
	public void setKeepLogin(String keepLogin) {
		this.keepLogin = keepLogin;
	}
	public String getMem_idx() {
		return mem_idx;
	}
	public void setMem_idx(String mem_idx) {
		this.mem_idx = mem_idx;
	}
	public String getMem_userid() {
		return mem_userid;
	}
	public void setMem_userid(String mem_userid) {
		this.mem_userid = mem_userid;
	}
	public String getMem_email() {
		return mem_email;
	}
	public void setMem_email(String mem_email) {
		this.mem_email = mem_email;
	}
	public String getMem_password() {
		return mem_password;
	}
	public void setMem_password(String mem_password) {
		this.mem_password = mem_password;
	}
	public String getMem_status() {
		return mem_status;
	}
	public void setMem_status(String mem_status) {
		this.mem_status = mem_status;
	}
	public String getMem_class() {
		return mem_class;
	}
	public void setMem_class(String mem_class) {
		this.mem_class = mem_class;
	}
	public String getLast_login_date() {
		return last_login_date;
	}
	public void setLast_login_date(String last_login_date) {
		this.last_login_date = last_login_date;
	}
	public String getMem_regtime() {
		return mem_regtime;
	}
	public void setMem_regtime(String mem_regtime) {
		this.mem_regtime = mem_regtime;
	}
	public String getMem_regip() {
		return mem_regip;
	}
	public void setMem_regip(String mem_regip) {
		this.mem_regip = mem_regip;
	}
}
