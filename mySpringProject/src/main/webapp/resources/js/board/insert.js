var insert = {
	init : function() {
		insert.event();
	},

	event : function() {
		
		//수정버튼 클릭 시
		$("#update_button").on("click", function(e) {
			var content 	= CKEDITOR.instances.editor1.getData();
			var title   	= $('#title').val();
			var board_key 	= $(this).attr('name');
			
			if(title.length==0){
				alert('제목을 입력해 주세요.');
				return false;
			}
			
			if(content.length==0){
				alert('본문을 입력해 주세요.');
				return false;
			}
			
			if (confirm("게시 글을 수정하시겠습니까") == false){
				 return false;
			 }else{
				 if(board_key ==null){
					 alert("오류가 발생했습니다.");
					 
					 return false;
				 }
				 
				 insert.updateBoard(title,content,board_key);
			 }
		}),
		
		// 취소 버튼 클릭 시
		$("#cancel_button").on("click", function(e) {
			if (confirm("수정사항은 저장되지 않습니다.") == false){
				return false;
			 }else{
				 history.back();
			 }
		}),
		
		
		// 글쓰기 버튼 클릭 시
		$("#insert_button").on("click", function(e) {
			var content = CKEDITOR.instances.editor1.getData();
			var title   = $('#title').val();
			var id  	= sessionStorage.getItem('user_id');
			
			if(title.length==0){
				alert('제목을 입력해 주세요.');
				return false;
			}
			
			if(content.length==0){
				alert('본문을 입력해 주세요.');
				return false;
			}
			
			insert.insertBoard(title,content,id);
		});

	},

	insertBoard : function(title, content, id) {
		
		$.ajax({
			url : "/insertBoard/",
			type : "POST",
			data : "mem_userid="+encodeURIComponent(id)+"&title="+encodeURIComponent(title)+"&content="+encodeURIComponent(content),
			dataType : "JSON",
			success : function(resultData) {
				if(resultData.Result.ResultCode == "Y") {
					alert(resultData.Result.ResultMessage);
					location.href = "/board";
				}else {
					alert(resultData.Result.ResultMessage);
					return false;
				}
			}
		});
	},
	
	updateBoard : function(title, content, board_key) {
		
		$.ajax({
			url : "/updateBoard/",
			type : "POST",
			data : "board_key="+encodeURIComponent(board_key)+"&title="+encodeURIComponent(title)+"&content="+encodeURIComponent(content),
			dataType : "JSON",
			success : function(resultData) {
				if(resultData.Result.ResultCode == "Y") {
					alert(resultData.Result.ResultMessage);
					location.href = "/board";
				}else {
					alert(resultData.Result.ResultMessage);
					return false;
				}
			}
		});
	}
};

$(document).ready(function() {
	insert.init();
});