<%@page import="VO.PostVO"%>
<%@page import="java.util.List"%>
<%@page import="DAO.BoardDAO"%>
<%@page import="java.io.File"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html onLoad="noScroll()">
<head>
<meta charset="UTF-8">
<title>Infixel - Login</title>
<script src="js/jquery.js"></script>
<link rel="stylesheet" href="css/reset.css">
<link rel="stylesheet" href="css/form_page.css">

</head>
<Style>

	#background {
		margin-top: 0;
		height: 300%;
		margin-right: 30px;
      column-width:350px;
      column-gap: 15px;
      position: relative;
      top: 0;
      left: 0;
    }
    
    #background figure figcaption{
      padding:10px;
      margin-top:11px;
    }
	#login_box {
		margin-top: 40px;
	}
	#background figure img{
      width:100%;
      height: 100%;
    }
    .post {
	    border-radius: 10px;
    	box-shadow : 0px 4px 8px 2px grey;
    	position: relative;
    	overflow: hidden;
    	margin: 10px;
    	width: 100%;
    }
    .post_info_bg {
    	position: absolute;
    	left: 0px;
    	bottom: 0px;
    	padding: 5px;
    	color: white;
    	background: linear-gradient( to top, rgba(0,0,0,1), rgba(0,0,0,0) );
    	width: 100%;
    	border-radius: 10px;
    }

    
    .img_box {
    	overflow: hidden;
    	object-fit: cover;
    	background-color: black;
    }
	#login_box {
		margin-top: 40px;
	}
    main {
    	position: absolute;
    	top: 0;
    	left: 0;
    }
    
</Style>
<body>
	<div id="background"></div>
	<main>
		<div id="login_box_background">
			<div id="login_box">
		
				<div id="form_box">
					<form action="LoginServlet">
						<div class="input">
							<input type="text" placeholder="ID" name="id" id="id" required>
						</div>
						<div class="input">
							<input type="password" placeholder="Password" name="pw" id="pw" required>
						</div>
						<div class="btn">
							<input type="submit" value="LOGIN" name="login_btn" id="login_btn">
						</div>
					</form>
				</div>
				
				<div id="link_box">
					<div id="sign_up"><a href="signup.jsp">Sign Up</a></div>
					<div id="find_id"><a href="findId.jsp">Find ID</a></div>
					<div id="find_pw"><a href="#">Find PW</a></div>
				</div>
			
			</div> <!-- #login_box 끝 -->
		</div>
	</main>
</body>
<script>
putImg();
function putImg() {
	var fileNameArray = [];
	<%
	BoardDAO dao = new BoardDAO();
	List<PostVO> list = dao.getPostList();
	int listSize = list.size();
	
	for(int v = 0; v < listSize; v++) {
		String fileName = list.get(v).getFileName();
		%>
			fileNameArray.push("<%=fileName%>");
		<%
	}
	%>
	var filesLength = <%=listSize%>;
	
	for(var i = 0; i < filesLength; i++) {
        $("#background").append(
        "<figure class='post'>" +
	        "<div class='img_box'>" +
		        	"<img class='img' src='./imgs/"+fileNameArray[i]+"'>" +
	        "</div>" +
        "</figure>"
        );
      };
};
</script>
</html>