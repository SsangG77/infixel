<%@page import="VO.PostVO"%>
<%@page import="java.util.List"%>
<%@page import="DAO.BoardDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Infixel - Find PW</title>
<script src="js/jquery.js"></script>
<link rel="stylesheet" href="css/reset.css">
<link rel="stylesheet" href="css/form_page.css">
<style>
	 #background {
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
    
</style>
</head>
<body>
	<div id="background">
	</div>
	<main>
		<div id="login_box_background">
			<div id="login_box">
		
				<div id="form_box">
					<div id="from_box">
						<div class="input">
							<input type="text" placeholder="ID" name="id" id="id" required>
						</div>
						<div class="input">
							<input type="text" placeholder="Name" name="name" id="name" required>
						</div>
						<div class="input">
							<input type="text" onfocus="(this.type = 'date')" placeholder="BirthDay" name="birthday" id="birthday" 
							required>
						</div>
						
						<div class="input">
							<input type="text" placeholder="Nickname" name="nickname" id="nickname" required>
						</div>
						<div class="btn">
							<button id="login_btn" onclick="findPw()">Find PW</button>
						</div>
					</div>
				</div>
				
				<div id="link_box">
					<div id="sign_up"><a href="login.jsp">Log In</a></div>
					<div id="find_id"><a href="findId.jsp">Find ID</a></div>
					<div id="find_pw"><a href="findPw.jsp">Find PW</a></div>
				</div>
			
			</div> <!-- #login_box 끝 -->
		</div>
	</main>
</body>
<script>

function findPw() {
	var name = $("#name").val();
	var birthday = $("#birthday").val();
	var nickname = $("#nickname").val();
	var id = $("#id").val();
	
	$.ajax({
		url: "FindPwServlet",
		type: "get",
		dataType : "text",
		data : {
			name : name,
			birthday : birthday,
			nickname : nickname,
			id : id
		},
		success : function(data) {
			
			if(data == "비밀번호를 찾을 수 없습니다.") {
				alert(data);
			} else {			
				alert("당신의 비밀번호는 [" + data + "] 입니다.");
			}
		},
		error : function() {
			alert("error");
		}
	});
}



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