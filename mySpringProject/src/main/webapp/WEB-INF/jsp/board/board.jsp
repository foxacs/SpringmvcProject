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
<link rel="shortcut icon" href="#">

<!-- Custom styles for this template -->
<script src="${ctx }/resources/js/common/jquery-3.1.1.min.js"></script>
<script src="${ctx }/resources/js/bootstrap/bootstrap.min.js"></script>
<script src="${ctx }/resources/js/board/board.js"></script>

</head>
<body>
	<%@include file ="../common/header.jsp" %>
	
    <div class="container">
    	<div class="p-right search_text">
			<input type="text" id="search_input" name="" value="">
			<input type="button" id="search_button" value="검색">
		</div>
    
      	<div id="board_div"></div>
		
		<div class="p-right">
			<input type="button" class="insert_btn" id="insert_button" value="글쓰기">
		</div>
		
        <div class="page_wrap" id="paging_wrap"></div>
    </div>
    
    <%@include file ="../common/footer.jsp" %>
  	
  	
</body>
</html>