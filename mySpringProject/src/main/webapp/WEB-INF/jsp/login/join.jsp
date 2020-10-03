<%-- <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> --%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>회원가입</title>
<!-- Bootstrap core CSS -->
<link href="${ctx }/resources/css/bootstrap.min.css" rel="stylesheet">
<link rel="shortcut icon" href="#">

<!-- Custom styles for this template -->
<link href="${ctx }/resources/css/join.css" rel="stylesheet">

<script src="${ctx }/resources/js/common/jquery-3.1.1.min.js"></script>
<script src="${ctx }/resources/js/join/join.js"></script>

</head>
<body>
	<div class="site-wrapper">

		<div class="site-wrapper-inner-join" >
			
				<div class="masthead clearfix">
					<div class="inner">
						<h3 class="masthead-brand"><a href="/">SIDE PROJECT</a></h3>
						
					</div>
				</div>

				<div class="inner cover">

					<form class="form-signin">
						<h2 class="form-signin-heading">회원가입</h2>
						
						<div class = "div-margin-bottom">
							<label for="id">아이디</label> 
							<input type="text" id="id" class="form-control id" placeholder="아이디"> 
							<div class="success" id="idMsg" style="display: none;" aria-live="assertive"></div>
						</div>
						
						<div class = "div-margin-bottom">
							<label for="userPassword">비밀번호</label> 
							<input type="password" id="userPassword" class="form-control" placeholder="비밀번호">
							<div class="error" id="pwMsg" style="display: none;" aria-live="assertive"></div>
						</div>
						
						<div class = "div-margin-bottom">
							<label for="userPassword2">비밀번호 확인</label>
							<input type="password" id="userPassword2" class="form-control" placeholder="비밀번호 확인">
							<div class="error" id="pwMsg2" style="display: none;" aria-live="assertive"></div>
						</div>
						
						<div class = "div-margin-bottom">
							
							<label for="email">이메일</label>
							<input type="text" id="email" class="form-control" placeholder="이메일">
							
							<select id="selectEmail" title="이메일 주소" class = "form-control">
									<option value="">주소선택</option>
									<option value="custom">직접입력</option>
									<option value="@naver.com">@naver.com</option>
									<option value="@nate.com">@nate.com</option>
									<option value="@korea.com">@korea.com</option>
									<option value="@hanmail.net">@hanmail.net</option>
									<option value="@gmail.com">@gmail.com</option>
									<option value="@yahoo.co.kr">@yahoo.co.kr</option>
							</select>
							
							<div class="error" id="emailMsg" style="display: none;" aria-live="assertive"></div>
							
							<div class="joinButton">
								<button id = "sendMail">이메일  인증하기</button>
							</div>
							
							<input type="text" id="emailAuthCheck" 	style="display: none;" class="form-control" placeholder="인증번호 확인">
							<div class="error" id="authCheckMsg" 	style="display: none;" aria-live="assertive"></div>
						</div>
						
						<button class="btn btn-lg btn-primary btn-block" id = "join_button">가입하기</button>
					</form>

				</div>


				<div class="mastfoot">
					<div class="inner">
						<p>This page referred to bootstrap.</p>
					</div>
				</div> 

			</div>

	</div>
</body>
</html>