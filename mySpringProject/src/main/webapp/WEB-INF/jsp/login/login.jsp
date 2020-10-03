<%-- <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> --%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>로그인 페이지</title>
<!-- Bootstrap core CSS -->
<link href="${ctx }/resources/css/bootstrap.min.css" rel="stylesheet">
<link rel="shortcut icon" href="#">

<!-- Custom styles for this template -->
<link href="${ctx }/resources/css/login.css" rel="stylesheet">
<script src="${ctx }/resources/js/common/jquery-3.1.1.min.js"></script>
<script src="${ctx }/resources/js/login/login.js"></script>

</head>
<body>
	<div class="site-wrapper">

		<div class="site-wrapper-inner" >
			<div class="cover-container">
				<div class="masthead clearfix">
					<div class="inner">
						<h3 class="masthead-brand"><a href="/">SIDE PROJECT</a></h3>
					</div>
				</div>

				<div class="inner cover">

					<form class="form-signin">
						<h2 class="form-signin-heading">로그인</h2>
						
						<label for="id" class="sr-only">아이디</label> 
						<input type="text" class ="form-control" id="id" placeholder="아이디">
						<div class="error" id="err_empty_id" style="display: none;" aria-live="assertive">아이디를 입력해주세요.</div>
						
						
						<label for="inputPassword" class="sr-only">비밀번호</label> 
						<input type="password" id="inputPassword" class="form-control" placeholder="비밀번호">
						<div class="error" id="err_empty_pw" style="display: none;" aria-live="assertive">비밀번호를 입력해주세요.</div>
						
						<div class="checkbox">
							<label> <input id="keep_login_chk" type="checkbox" value="remember-me">로그인 상태 유지</label>
						</div>
						<!-- https://doublesprogramming.tistory.com/212 (자동로그인구현) -->
						
						<button class="btn btn-lg btn-primary btn-block" id="login_button">로그인</button>
						
						<div class="form-signin-heading" style="text-align:center;">
							<a href="#">아이디 찾기</a> | <a href="#">비밀번호 찾기</a> | <a href="/join">회원가입</a>
						</div>
					</form>

				</div>


				<div class="mastfoot">
					<div class="inner">
						<p>This page referred to bootstrap.</p>
					</div>
				</div>

			</div>

		</div>

	</div>
</body>
</html>
