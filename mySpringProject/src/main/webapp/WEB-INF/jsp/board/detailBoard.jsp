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
			<input type='button' class='insert_btn' onclick='javascript:history.back()' value='취소'>
		</div>
		
       
    </div>
   
    <%@include file ="../common/footer.jsp" %>
  	
</body>
</html>