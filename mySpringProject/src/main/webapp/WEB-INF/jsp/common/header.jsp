
<style>
	
	html {
	  position: relative;
	  min-height: 100%;
	}
	
	body {
	  /* Margin bottom by footer height */
	  margin-bottom: 60px;
	}
	
	.footer {
	  position: absolute;
	  bottom: 0;
	  width: 100%;
	  /* Set the fixed height of the footer here */
	  height: 60px;
	  background-color: #f5f5f5;
	}
	
	body > .container {
	  padding: 60px 15px 0;
	}
	
	.container .text-muted {
	  margin: 20px 0;
	}
	
	.footer > .container {
	  padding-right: 15px;
	  padding-left: 15px;
	}
	
	code {
	  font-size: 80%;
	}
	
	.navbar-default{
	  background-color: white;
	}	
</style>

	<%
		String id = (String)session.getAttribute("user_id");
  		if(id == null){
	%>
		<script>
			location.href="/"
		
			alert('로그인 후 이용해주세요.');
		</script>

	<%} else{%>
		<script type="text/javascript">
			sessionStorage.setItem('user_id','<%=id%>');
		</script>
	<%}%> 

<nav class="navbar navbar-default navbar-fixed-top">
   <div class="container">
      <div class="navbar-header">
         <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="/board">Side Project</a>
      </div>
        
      <div id="navbar" class="navbar-collapse collapse" aria-expanded="false" style="height: 1px;">
        <!-- <ul class="nav navbar-nav">
           <li class="active"><a href="/board">Home</a></li>
        </ul> -->
           
      	<ul class="nav navbar-nav navbar-right">
      		
      		<li class="dropdown">
              <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">마이페이지 <span class="caret"></span></a>
              <ul class="dropdown-menu" role="menu">
                <li><a href="/mylist">내가 쓴 글</a></li>
              
              </ul>
            </li>
      	
           <li><a href="/logout">로그아웃</a></li>
     	</ul>
     	
      </div>
   </div>
</nav>