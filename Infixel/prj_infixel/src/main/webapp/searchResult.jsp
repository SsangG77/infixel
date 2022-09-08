<%@page import="VO.PostVO"%>
<%@page import="java.util.List"%>
<%@page import="DAO.BoardDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>infixel - Search Result</title>
<link rel="shortcut icon" href="#">
<link href="css/style.css" rel="stylesheet" type="text/css" media="screen" />
<link href="css/main.css" rel="stylesheet" type="text/css" media="screen" />
<link href="css/main_sidebar.css" rel="stylesheet" type="text/css" media="screen" />
<link href="css/board.css" rel="stylesheet" type="text/css" media="screen" />

<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<script src="js/jquery.nested.js"></script>
<script src="js/makeboxes.js"></script>
</head>
<%
	String nickname = (String)session.getAttribute("nickname");
	String search = request.getParameter("search");
	
	BoardDAO dao = new BoardDAO();
	List<PostVO> list = dao.getSearchList(search);
%>
<body>
        <div id="sidebar">
            <div id="logo_box">
                <span>Infixel</span>
            </div>
            <div id="search">
                <form action="searchResult.jsp" method="get">
                    <input id="searchbar" name="search" type="text" placeholder="Search">
                    <input id="search_btn" type="submit" value="Find" >
                </form>
            </div>
            
            <%
            	if(nickname == null) {
            		%>
            		<div id="login_box">
                		<button onclick="location.href='login.jsp'">로그인</button>
            		</div>
            		<%
            	} else {
					%>
            		
            		<div id="login_box">
            			<div><a href="User.jsp?user=<%=nickname %>"><%=nickname %></a></div>
                		<button id="logout" onclick="location.href='logout.jsp'">Logout</button>
            		</div>
            		<div>
            			<a href="upload.jsp">업로드</a>
            		</div>
            		
            		<%
            	}
            %>
        </div>
    	<div id="hover_bg"></div>
    	<main id="background"></main>
	</body>
<script type="text/javascript">

putImg();
function putImg() {
	var fileNameArray = [];
	var fileTitleArray = [];
	var fileDateArray = [];
	var fileUserArray = [];
	var postViewArray = [];
	<%
	int listSize = list.size();
	
	for(int v = 0; v < listSize; v++) {
		String fileName = list.get(v).getFileName();
		String fileTitle = list.get(v).getTitle();
		String fileDate = list.get(v).getDate();
		String user = list.get(v).getUser();
		int getView = list.get(v).getViews();
		%>
			fileNameArray.push("<%=fileName%>");
			fileTitleArray.push("<%=fileTitle%>");
			fileDateArray.push("<%=fileDate%>");
			fileUserArray.push("<%=user%>");
			var view = <%=getView%>;
			if(view == 0) {
				postViewArray.push(1);
			} else if(view > 9) {
				postViewArray.push(9);
			} else {
				postViewArray.push(<%=getView%>);
			}
		<%
	}
	%>
	var filesLength = <%=listSize%>;
	
	for(var i = 0; i < filesLength; i++) {
        $("#background").append(
        "<div class='post box size"+postViewArray[i]+postViewArray[i]+"'>" +
	        "<div class='img_box'>" +
		        "<a href='detailPage.jsp?fileName=" + fileNameArray[i] + "'>" +
		        	"<img class='img' src='./imgs/"+fileNameArray[i]+"'></a>" +
	        "</div>" +
	        "<div class='post_info_bg' style='cursor:pointer;' onclick='location.href=\"detailPage.jsp?fileName="+fileNameArray[i]+"\"\'>" +
		        "<div class='post_info'>" +
			        "<div class='post_title'>"+
			        	"<a href='detailPage.jsp?fileName=" + fileNameArray[i] + "'>" +
			        		fileTitleArray[i] +
			        	"<a/>" +
			        "</div>" +
			        "<div class='upload_user'>" +
			        	"<a href='User.jsp?user="+fileUserArray[i]+"'>"+ fileUserArray[i] +"</a>" + 
			        "</div>" +
			        "<div class='post_date'>"+fileDateArray[i]+"</div>" +
		        "</div>" +
	        "</div>" +
        "</div>"
        )
        console.log(fileTitleArray[i]);
      };
};


	
$(function() {
	   
    $('#background').nested(); 

  });
</script>
</html>