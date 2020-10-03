var join = {

	init : function() {
		// 이벤트 초기화
		join.events(); // 로그인 이벤트 호출
	},
	
	/**
	 * 이벤트 선언.
	 */
	events : function() {
		pattern_num = /[0-9]/;					// 숫자 
    	pattern_eng = /[a-zA-Z]/;				// 문자 
    	pattern_spc = /[~!@#$%^&*()_+|<>?:{}]/; // 특수문자
    	pattern_kor = /[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/; 		// 한글체크
    	
		// 아이디 체크
		$('#id').on('blur', function()
		{
			return join.chkUserId();
		});

		// 비밀번호 체크
		$('#userPassword').on('blur', function(){
			join.chkPw();
		});
		
		//비밀번호 확인 체크
		$('#userPassword2').on('blur', function(){
			join.chkPw2();
		});
		
		//
		$('#emailAuthCheck').on('blur', function(){
			join.chkEmailAuth();
		});
		
		// 엔터
		$("#id, #inputPassword, #userPassword2, #email").on('keydown', function(e) { // keydown 이벤트시
			if (e.which == 13) { // 엔터(enter) 아스키코드일 때
				join.join(); // 로그인 이벤트
				return false;
			}
		});

		// 이메일 체크
		$("#sendMail").on("click", function() {
			join.chkEmail();
			return false;
		});
		
		$("#join_button").on("click", function() {

			var chkEmailAuth = join.chkEmailAuth();
			
			console.log('chkEmailAuth : ' + chkEmailAuth);
			if(chkEmailAuth==false)
			{
				$('#emailAuthCheck').focus();
				return false;
			}
			
			var chkUserId = join.chkUserId();
			console.log(chkUserId);
			if(chkUserId==false){
				$('#id').focus();
				return false;
			}
			
			var chkPw = join.chkPw();
			console.log('chkPw : ' + chkPw);
			if(chkPw==false){
				$('#userPassword').focus();
				return false;
			}
			
			var chkPw2 = join.chkPw2();
			console.log('chkPw2 : ' + chkPw2);
			if(chkPw2==false){
				$('#userPassword2').focus();
				return false;
			}
			
			join.join();
			return false;
			
		});
	},
	
	/**
	 * 회원가입
	 */
	//아이디 체크
	chkUserId : function(){
		var id = $('#id').val();
		
		if(id.length==0){
			$('#idMsg').removeClass('success').addClass('error');
			$('#idMsg').css("display","block").text("아이디를 입력해 주세요.");
			
			return false;
		}
		//한글 , 특수문자 체크
		if((pattern_spc.test(id)) || (pattern_kor.test(id)) )
		{
			$('#idMsg').removeClass('success').addClass('error');
			$('#idMsg').css("display","block").text("아이디는 영어와 숫자만 사용 가능합니다.");
			
			return false;
    	}
		// 길이 체크
		if(id.length<6 ||id.length>16){
			$('#idMsg').removeClass('success').addClass('error');
			$('#idMsg').css("display","block").text("사용가능한 아이디 글자 수는 6~16글자 입니다.");
			
			return false;
		}

		$.ajax({
			url : "/chkId",
			type : "GET",
			data : "mem_userid="+id,
			dataType : "JSON",
			success : function(resultData) {
				if (resultData.Result.ResultCode == "Y") {
					$('#idMsg').removeClass('error').addClass('success');
					$('#idMsg').css("display","block").text(resultData.Result.ResultMessage);
					
					return true;
				} else {
					$('#idMsg').removeClass('success').addClass('error');
					$('#idMsg').css("display","block").text(resultData.Result.ResultMessage);
					
					return false;
				}
			}
		});
	},
	
	//비밀번호 체크
	chkPw : function(){
		var pw = $('#userPassword').val();
		
		if(pw.length==0){
			$('#pwMsg').removeClass('success').addClass('error');
			$('#pwMsg').css("display","block").text("비밀번호를 입력해주세요.");
			
			return false;
		}
		//한글 , 특수문자 체크
		else if((/[~$%^&*()_+|<>?:{}]/.test(pw)))
		{
			$('#pwMsg').removeClass('success').addClass('error');
			$('#pwMsg').css("display","block").text("특수 기호는 ! @ # 만 사용 가능합니다.");
			
    		return false;
    	}
		// 길이 체크
		else if(pw.length<8 ||pw.length>24){
			$('#pwMsg').removeClass('success').addClass('error');
			$('#pwMsg').css("display","block").text("비밀번호 길이는 8~24글자로 설정해 주세요.");
			
			return false;
		}
		else{
			$('#pwMsg').css("display","none");
			
			return true;
		}
	},
	
	//비밀번호 확인 체크
	chkPw2 : function(){
		var pw 		= $('#userPassword2').val();
		var exPw 	= $('#userPassword').val();
		
		// 길이 체크
		if(pw.length==0){
			$('#pwMsg2').removeClass('success').addClass('error');
			$('#pwMsg2').css("display","block").text("필수 정보입니다.");
			
			return false;
		}
		// 비밀번호 비교
		else if(exPw === pw){
			$('#pwMsg2').css("display","none");
			
			return true;
		}
		else{
			$('#pwMsg2').removeClass('success').addClass('error');
			$('#pwMsg2').css("display","block").text("비밀번호가 일치하지 않습니다.");
			
			return false;
		}
	},
	
	//아이디 체크
	chkEmail : function(){
		var email 			= $('#email').val();
		var selectEmail 	= $('#selectEmail').val();
		var totalEmail		= '';
		
		var pattern_email 	= /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;

		// 이메일 주소 체크
		if(email.length==0){
			$('#emailMsg').removeClass('success').addClass('error');
			$('#emailMsg').css("display","block").text("이메일을 입력해 주세요.");
			
			return false;
		}
		// 주소 선택 체크
		else if(selectEmail.length==0)
		{
			$('#emailMsg').removeClass('success').addClass('error');
			$('#emailMsg').css("display","block").text("이메일 주소를 선택해주세요.");
			
    		return false;
    	}
		// 직접입력 체크
		else
		{
			if(selectEmail=='custom'){
				selectEmail 	 = '';
			}
			
			totalEmail = email + selectEmail;
			
			if((pattern_email.test(totalEmail) == false)){
				$('#emailMsg').removeClass('success').addClass('error');
				$('#emailMsg').css("display","block").text("이메일 주소를 확인해주세요.");
				
				return false;
			}
		}
		
		$.ajax({
			url : "/chkEmail",
			type : "GET",
			data : "mem_email="+totalEmail,
			dataType : "JSON",
			success : function(resultData) {
				if (resultData.Result.ResultCode == "Y") {
					$('#emailMsg').removeClass('error').addClass('success');
					$('#emailMsg').css("display","block").text(resultData.Result.ResultMessage);
					
					$('#emailAuthCheck').css("display","block");
					
					return true;
				} else {
					$('emailMsg').removeClass('success').addClass('error');
					$('#emailMsg').css("display","block").text(resultData.Result.ResultMessage);
					
					return false;
				}
			}
		});
	},
	
	//인증번호 체크
	chkEmailAuth : function(){
		var AuthNum	= $('#emailAuthCheck').val();
		
		// 길이 체크
		if(AuthNum.length==0){
			$('#authCheckMsg').removeClass('success').addClass('error');
			$('#authCheckMsg').css("display","block").text("인증이 필요합니다.");
			
			return false;
		}
		
		var email 			= $('#email').val();
		var selectEmail 	= $('#selectEmail').val();
		
		if(selectEmail=='custom'){
			selectEmail 	 = '';
		}
		
		var totalEmail = email + selectEmail;
		
		$.ajax({
			url : "/getAuthNumber",
			type : "GET",
			data : "mem_email="+totalEmail,
			dataType : "JSON",
			success : function(resultData) {
				if (resultData.Result.ResultCode == "Y") {
					
					if(resultData.contents.auth_number== AuthNum){
						$('#authCheckMsg').removeClass('error').addClass('success');
						$('#authCheckMsg').css("display","block").text("인증이 되었습니다.");
						
						return true;
					}else{
						$('#authCheckMsg').removeClass('success').addClass('error');
						$('#authCheckMsg').css('display','block').text('인증번호가 맞지 않습니다.');
						
						return false;
					}
					
				} else {
					$('#authCheckMsg').removeClass('success').addClass('error');
					$('#authCheckMsg').css("display","block").text(resultData.Result.ResultMessage);
					
					return false;
				}
			}
		});
		
		
		
	},
	
	//회원가입
	join : function() {
		var email 			= $('#email').val();
		var selectEmail 	= $('#selectEmail').val();
		
		if(selectEmail=='custom'){
			selectEmail 	 = '';
		}
		
		var totalEmail = email + selectEmail;
		
		
		$.ajax({
			url : "/insertUser/",
			type : "POST",
			data : "mem_userid="+$('#id').val()+"&mem_password="+$('#userPassword').val()+"&mem_email="+totalEmail ,
			dataType : "JSON",
			success : function(resultData) {
				if(resultData.Result.ResultCode == "Y") {
					alert(resultData.Result.ResultMessage);
					location.href = "/";
				}else {
					alert(resultData.Result.ResultMessage);
					return false;
				}
			}
		});
	},
};

//onload
$(document).ready(function() 
{
	join.init();
});