var board = {
	init : function() {
		board.event();
		board.checkForHash();
	},

	event : function() {
		
		$(window).on('hashchange', function() {
			if(document.location.hash){
				var HashLocationName = document.location.hash;
				HashLocationName = HashLocationName.replace("#","");
				board.getList(HashLocationName);
			} else {
				board.getList(1);
			}
		});
		
		// 글쓰기 버튼
		$("#insert_button").on("click", function() {
			location.href='/insert';
		});
		
		// 삭제버튼 클릭 시
		$(document).on('click','#delete_button',function(){
			 var board_key = $(this).attr('name');
			
			 if(board_key==null){
				 alert("삭제 중 오류가 발생했습니다.");
				 return false;
			 }
			 
			 if (confirm("정말 삭제하시겠습니까??") == false){
				 return false;
			 }else{
				 board.deleteBoard(board_key);
			 }
		}); 
		
		// 검색 
		$("#search_button").on("click", function() {
			board.getList(1);
		});

		// 엔터
		$("#search_input").on('keydown', function(e) { // keydown 이벤트시
			if (e.which == 13) { // 엔터(enter) 아스키코드일 때
				board.getList(1);				
				return false;
			}
		});
		
		// /////////////////////////페이징-숫자버튼////////////////////////////////////
		$("#paging_wrap").on("click", ".page_box", function() {
			$(this).attr("class", "page_on");
		});

	},

	// 검색 ajax
	getList : function(page) {
		
		document.location.hash = "#" + page;
		
		$.ajax({
			url : "/list",
			type : "GET",
			data : "currentCount=" + page + "&pageCount=10" 
			       + "&keyword="+encodeURIComponent($('#search_input').val())+ "&mem_userid="+sessionStorage.getItem('user_id'),
			dataType : "JSON",
			success : function(resultData) {
				if (resultData.Result.ResultCode == "Y") {
					if (resultData.contents.TotalCount == 0) {
						board.showEmptyPaging('paging_wrap');
					}
					board.drawList(resultData.contents, page);
				} else {
					alert(resultData.Result.ResultMessage);
				}
			}
		});
	},
	
	// 검색 리스트 출력
	drawList : function(data, page) {
		
		dataList = data.DataList;
		totalCount = data.TotalCount;
		totalPage = data.TotalPage;

		var data_size = 10;

		$('#board_div').empty();

		for (var i = 0; i < dataList.length; i++) {

			$div = $('<div/>').addClass('page-header');
			$a = $('<a/>').attr({href : '/detailBoard?board_key='+ dataList[i].board_key}).html(dataList[i].title)
			
			$upbtn = $('<a/>').attr({href : '/moveUpdateBoard?board_key='+ dataList[i].board_key});
			$upbtn.append('<input type="button" id="update_button" style ="margin-left:5px;" value="수정">');
			
			$delbtn = '<input type="button" id="delete_button" style ="margin-left:5px;" name = '+ dataList[i].board_key +' value="삭제">' 
			
			$tempDiv = $('<div/>').addClass('p-right').attr({"val" : dataList[i].board_key}).append($upbtn).append($delbtn);
			
			$div.append($('<h1/>').append($a));
			$div.append($('<p/>').addClass('p-right').html(dataList[i].mem_userid));
			$div.append($('<p/>').addClass('p-right').html(dataList[i].update_date));
			$div.append($tempDiv);
			
			$('#board_div').append($div);
		}

		this.showPaging(totalCount, page, "board.getList", 'paging_wrap', data_size);
	},
	
	// 페이징 처리
	showPaging : function(total_count, page_num, func_name, id, data_size) {

		totalCnt = parseInt(total_count);	// 전체레코드수 
		dataSize = parseInt(data_size); 			// 페이지당 보여줄 데이타수 
		pageSize = parseInt(10); 			// 페이지 그룹 범위 1 2 3 5 6 7 8 9 10 
		pageNo 	 = parseInt(page_num); 		// 현재페이지

		if(total_count == 0){ return ""; }
		
		var pageHtml 		= "";
	    var function_name 	= func_name;
	    
	    // 페이지 카운트 
	    var pageCnt = totalCnt % dataSize; 
	    
	    if(pageCnt == 0){ 
	    	pageCnt = parseInt(totalCnt / dataSize); 
	    }else{ 
	    	pageCnt = parseInt(totalCnt / dataSize) + 1; 
	    }

	    var pRCnt = parseInt(pageNo / pageSize);
	    
	    if(pageNo % pageSize == 0)
	    {
	    	pRCnt = parseInt(pageNo / pageSize) - 1; 
	    }

	    // 페이지 좌측 이미지
	    if(pageNo > pageSize){
	    	var a;
	    	if(pageNo % pageSize == 0){
	    		a = pageNo - pageSize;
	    	}else{
	    		a = pageNo - pageNo%pageSize;
	    	}
	    	
	    	pageHtml += '<a href="javascript:' + func_name + '(' + Number(a) + ')" class="page_prev"><</a>';
	    }else{
	    	pageHtml += '<a href="#" class="page_prev"><</a>';
	    }
	    
	    // 페이지 구성
	    for ( var i=pRCnt*pageSize + 1; i<(pRCnt + 1)*pageSize + 1; i++){
	    	if(i == pageNo)
	    	{
	    		pageHtml += '<a href="#" class="page_on">' + i + '</a>';
	    	}
	    	else
	    	{
	    		pageHtml += '<a href="javascript:' + func_name + '(' + i + ')" class="page_box">' + i + '</a>';
	    	}
	    	if(i==pageCnt){
	    		break;
	    	}
	    }	
	    
	    
	    // 페이지 우측 이미지
	    if (pageCnt > (pRCnt+1) * pageSize)
	    {
	    	pageHtml += '<a href="javascript:' + func_name + '(' + (((pRCnt + 1)*pageSize)+1) + ')" class="page_next">></a>';
	    } else {
	    	pageHtml += '<a href="#" class="page_next">></a>';
	    }
	    
	    $("#" + id).html(pageHtml);
	}, 
	
	showEmptyPaging : function (id) {

	    var pageHtml = '';

	    pageHtml += '<a href="#" class="page_prev"><</a>';
	    pageHtml += '<a href="#" class="page_box">1</a>';
	    pageHtml += '<a href="#" class="page_next">></a>';

	    $("#" + id).html(pageHtml);
	    
	},
	
	checkForHash : function () {
		if(document.location.hash){
			var HashLocationName = document.location.hash;
			HashLocationName = HashLocationName.replace("#","");
			
			board.getList(HashLocationName);
		} else {
			board.getList(1);
		}
	},
	
	// 검색 리스트 출력
	deleteBoard : function(board_key) {
		
		$.ajax({
			url : "/deleteBoard",
			type : "POST",
			data : "board_key=" + board_key ,
			dataType : "JSON",
			success : function(resultData) {
				if (resultData.Result.ResultCode == "Y") {
					alert(resultData.Result.ResultMessage);
					location.reload();
				} else {
					alert(resultData.Result.ResultMessage);
					return false;
				}
			}
		});
		
		
	}
	
};

$(document).ready(function() {
	board.init();
});