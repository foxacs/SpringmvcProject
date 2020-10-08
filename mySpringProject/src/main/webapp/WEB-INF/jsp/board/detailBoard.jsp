<%-- <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> --%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>게시판</title>
<!-- Bootstrap core CSS -->
<link href="${ctx }/resources/css/bootstrap.min.css" rel="stylesheet">
<link href="${ctx }/resources/css/board.css" rel="stylesheet">
<link href="${ctx }/resources/js/ckeditor/contents.css" rel="stylesheet">
<link rel="shortcut icon" href="#">

<!-- Custom styles for this template -->
<script src="${ctx }/resources/js/common/jquery-3.1.1.min.js"></script>
<script src="${ctx }/resources/js/bootstrap/bootstrap.min.js"></script>
<script src="${ctx }/resources/js/board/insert.js"></script>

</head>
<body class="cke_editable cke_editable_themed cke_contents_ltr cke_show_borders" >
	<%@include file ="../common/header.jsp" %>
	
    <div class="container">
    	<div class="page-header">
    		<textarea class='textbox input_title' id ='title' readonly>${title }</textarea>
    		
    		<p style ='color:#b7b4b4; font-weight: normal;'>${user_id } ・  ${update_date }</p>
    	</div>
      	
      	<div class="page-header" id = 'content'>
			${content }    		
    	</div>
		
		<div class="p-right">
		
		<div style="text-align:left; margin-bottom:5px">댓글 0개</div>
		
			<textarea class='input_title' id ='reply' placeholder='댓글을 입력해 주세요'></textarea>
			
			<input type='button' class='reply_btn' id = "reply_btn" onclick='javascript:history.back()' value='댓글 달기'>
			<div style = "border-bottom: 1px solid #eee;"></div>
			
			<div class = "reply-page-header">
				<div>kdh359 | 1분 전</div>
				<div class='reply-div'>이건 정말 아니지 않나 싶다</div>
				
				<div class="reply-btn">
						<input type="button" value="답글달기">
						<input type="button" value="수정">
						<input type="button" value="삭제">
						
				</div>
			</div>
			<div class="reply-page-header reply-background">
				<div class="reply-div-depth1">└ kdh359 | 1분 전</div>
				<div class="reply-div reply-div-depth2">이건 정말 아니지 않나 싶다</div>
			</div>
			<div class = "reply-page-header" >
				<div>kdh359 | 1분 전</div>
				<div class="reply-div">이건 정말 아니지 않나 싶다</div>
			</div>
			<div class = "reply-page-header">
				<div>kdh359 | 1분 전</div>
				<div class="reply-div">이건 정말 아니지 않나 싶다</div>
			</div>
			<div style = "border-bottom: 1px solid #eee;"></div>
		</div>
		
    </div>
   
    <%@include file ="../common/footer.jsp" %>
  	
</body>
</html>