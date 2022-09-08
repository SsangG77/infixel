<%@page import="java.util.ArrayList"%>
<%@page import="java.io.File"%>
<%@page import="VO.PostVO"%>
<%@page import="java.util.List"%>
<%@page import="DAO.BoardDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Infixel - Main</title>
<link rel="shortcut icon" href="#"/>
<link href="css/style.css" rel="stylesheet" type="text/css" media="screen" />
<link href="css/main.css" rel="stylesheet" type="text/css" media="screen" />
<link href="css/main_sidebar.css" rel="stylesheet" type="text/css" media="screen" />
<link href="css/board.css" rel="stylesheet" type="text/css" media="screen" />
<link rel="stylesheet" href="css/button.css">

<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<script src="js/jquery.nested.js"></script>
<script src="js/makeboxes.js"></script>

<%
	String nickname = (String)session.getAttribute("nickname");
	System.out.println("Get Attribute : " + nickname);
%>
</head>

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
                		<button class="sidebar_btn" onclick="location.href='login.jsp'">Login</button>
            		</div>
            		<%
            	} else {
					%>
            		<div id="login_box">
            			<div><button class="sidebar_btn" onclick="location.href='User.jsp?user=<%=nickname %>'"><span class="btn_text"><%=nickname %></span></button></div>
                		<button class="sidebar_btn" id="logout" onclick="location.href='logout.jsp'"><span class="btn_text">Logout</span></button>
            			<button class="sidebar_btn" onclick="location.href='upload.jsp'"><span class="btn_text">Upload</span></button>
            		</div>
            		
            		<%
            	}
            %>
        </div>
    	<div id="hover_bg"></div>
    	<main id="background">
    		
    	</main>
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
	BoardDAO dao = new BoardDAO();
	List<PostVO> list = dao.getPostList();
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

$(document).ready(function(e) {
    var imgs = $(".img");
    for(var i = 0; i < imgs.length; i++) {
        var imgWidth = imgs.eq(i).width();
        var imgHeight = imgs.eq(i).height();
            console.log(imgs.eq(i));
            console.log("가로 : " + imgWidth);
            console.log("세로 : " + imgHeight);
        if(imgHeight - imgWidth < 0) {
            imgs.eq(i).css("height","100%");
        } else {
            imgs.eq(i).css("width","100%");
        };
    };
});


	
$(function() {
    $('#background').nested(); 
  });
</script>
</html>


