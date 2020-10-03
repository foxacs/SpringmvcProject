var login = {

	init : function() {
		// 이벤트 초기화
		login.events(); // 로그인 이벤트 호출
	},
	
	/**
	 * 이벤트 선언.
	 */
	events : function() {
		
		// 엔터
		$("#id, #inputPassword").on('keydown', function(e) { // keydown 이벤트시
			if (e.which == 13) { // 엔터(enter) 아스키코드일 때
				login.login(); // 로그인 이벤트
				return false;
			}
		});

		
		// 로그인 버튼 클릭
		$("#login_button").on("click", function() {
			login.login();
			return false;
		});
		
		
		// 로그아웃 버튼
		$('.btn_logout').on("click", function() {
			location.href = "/login/logout";
			return false;
		});
	},

	/**
	 * 로그인
	 */
	
	login : function() {
		
		if (($('#id').val()).length == 0) { // 아이디 입력창이 공백일 때		
			$('#err_empty_pw').css("display","none");
			$('#err_empty_id').css("display","block");
			$('#id').focus();

			return false;
		}
		
		if (($('#inputPassword').val()).length == 0) { // 비밀번호 입력창이 공백일 때
			$('#err_empty_id').css("display","none");
			$('#err_empty_pw').css("display","block");
			$('#inputPassword').focus();
			return false;
		}
		
		var keeplogin = $('#keep_login_chk').is(":checked");
		
		$.ajax({
			url : "/login/",
			type : "POST",
			data : "mem_userid="+$('#id').val()+"&mem_password="+$('#inputPassword').val()+"&keepLogin="+(keeplogin==true ? "Y" : "N") ,
			dataType : "JSON",
			success : function(resultData) {
				if(resultData.Result.ResultCode == "Y") {
					alert(resultData.Result.ResultMessage);
					location.href = "/board";
				}else {
					alert(resultData.Result.ResultMessage);
				}
			}
		});
		return false;
	},
};

//onload
$(document).ready(function() {
	login.init();
});