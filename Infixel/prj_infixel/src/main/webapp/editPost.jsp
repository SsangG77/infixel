<%@page import="DAO.BoardDAO"%>
<%@page import="java.util.List"%>
<%@page import="DAO.PostDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="js/jquery.js"></script>
<link rel="stylesheet" href="css/upload.css">
<link rel="stylesheet" href="css/main_sidebar.css">
<link rel="stylesheet" href="css/reset.css">
<link rel="stylesheet" href="css/button.css">
</head>
<%
	PostDAO pdao = new PostDAO();
	List<String> tagList = pdao.getTagList();

	String title = request.getParameter("title");
	String fileName = request.getParameter("fileName");
	String link = request.getParameter("link");
	String setLink = "";
	if(link.equals("null")) {
		setLink = "";
	} else {
		setLink = link;
	}
	
	
	String postNum_str = request.getParameter("postNum");
	int postNum = Integer.parseInt(postNum_str);
	String download = request.getParameter("download");
	
	String yes = "";
	String no = "";
	if(download.equals("1")) {
		yes = "checked";
	} else {
		no = "checked";
	}
	
	BoardDAO bdao = new BoardDAO();
	int lastPostNum = bdao.getBoardLastNum();
	
	
	String nickname = (String)session.getAttribute("nickname");
	
%>
<body>
	<div id="sidebar">
            <div id="logo_box">
                <span><a href="main.jsp">Infixel</a></span>
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
            			<div><button class="sidebar_btn" onclick="location.href='User.jsp?user=<%=nickname %>'"><%=nickname %></button></div>
                		<button class="sidebar_btn" id="logout" onclick="location.href='logout.jsp'">Logout</button>
            			<button class="sidebar_btn" onclick="location.href='upload.jsp'">Upload</button>
            		</div>
            		
            		<%
            	}
            %>
        </div>
	<div id="background">
		<div>
			<form action="EditPostServlet" method="POST" enctype="multipart/form-data">
				<input type="hidden" name="postNum" value="<%=postNum%>">
				<span class="input_title">Title</span>
				<hr> 
				<input type="text" name="title" value="<%=title %>" required><br>
				<span class="input_title">Download</span>
				<hr>
				<input type="radio" value="1" name="download" <%=yes %>>Yes
				<input type="radio" value="0" name="download" <%=no %>>No<br>
				<span class="input_title">Link</span> 
				<hr>
				<input type="url" name="link" value="<%=setLink%>"><br>
				<div id="tag_box">
					<span class="input_title">Tags</span>
					<hr>
					<div id="tag_list"></div>
				</div>
			<div id="ad_tag">
				<input type="text" id="input_tag" name="add_tag" placeholder="????????? ????????? ?????????.">
				<input type="button" value="?????? ??????" onclick="addTag()">
			</div>
			<h5>????????? ?????? ????????? ?????????.</h5>
			<span class="input_title">Image File</span>
			<hr>
			<input onchange="setThumbnail(event)" type="file" name="image" accept="image/png, image/jpeg" required><br>
				<input id="submit_btn" type="submit" value="Edit">
			</form>
		</div>
		<div id="upload_img_box">
		</div>
	</div>
</body>
	<script>
	function setThumbnail(e) {
		$("#upload_img").remove();
		console.log("setThumbnail ?????????.");
		var reader = new FileReader();
		reader.onload = function(e) {
			console.log("setThumbnail ???????????? ?????????.");
			var img = $("<img id='upload_img'/>");
			img.attr("src", e.target.result); //src?????? ??????
			//#img_box ???????????? ??????
			var img_box = $("<div id='img_box'></div>");
			$("#upload_img_box").append(img_box);
			$("#img_box").append(img);
		};
		reader.readAsDataURL(e.target.files[0]);
	}
	

	showTag();
	function showTag() {
		var tagList = [];
		<%
			for(int i = 0; i < tagList.size(); i++) {
		%>
				tagList.push("<%=tagList.get(i)%>");
		<%
			}
		%>
		var tagListLength = <%=tagList.size()%>;
		for(var i = 0; i < tagListLength; i++) {			
			$("#tag_list").append(
				"<div class='tag_item'><input type='checkbox' name='tag' value='"+tagList[i]+"'><div class='tag_name'>" + tagList[i] + "</div></div>"	
			);
		};
	};

	function addTag() {
		var input_tag = $("#input_tag").val();
		console.log("input tag : " + input_tag);
		
		var tag_exist = false;
		
		$(document).ready(function(){
		    var $divs =$(".tag_name");  // class??? ?????? ".????????????" 
		    console.log($divs);
		    for(var i=0; i<$divs.length; i++){
		        var $div = $divs.eq(i);
		        if(input_tag == $div[0].innerText) {
		        	tag_exist = true;
		        }
		        console.log("i ", i,$div[0]);	
		    }
		});
		
		
		if(input_tag == "") {
			alert("????????? ??????????????????.");
		} else if(tag_exist) {
			alert("?????? ???????????? ?????? ?????????.")
		} else {
			$.ajax({
				type: "GET",
				url : "AddTagServlet",
				data: {
					tag : input_tag
				},
				dataType: "JSON",
				success : function(data) {
					var getTag = data.list[0][data.list[0].length-1];
					$("#tag_list").append(
						"<div class='tag_item'><input type='checkbox' name='tag' value='"+getTag+"'/>"+ getTag+ "</div></div>"	
					);
					console.log("getTag : " + getTag);
				},
				error : function() {
					alert("error");
				}
			});
		}
	};
</script>

</html>