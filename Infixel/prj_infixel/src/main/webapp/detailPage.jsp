<%@page import="VO.ReplyVO"%>
<%@page import="DAO.ReplyDAO"%>
<%@page import="DAO.PostDAO"%>
<%@page import="DAO.UserDAO"%>
<%@page import="java.util.List"%>
<%@page import="VO.PostVO"%>
<%@page import="DAO.BoardDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Detail Page</title>
<link rel="shortcut icon" href="#">
<link rel="stylesheet" href="css/reset.css">
<link rel="stylesheet" href="css/main_sidebar.css">
<link rel="stylesheet" href="css/button.css">
<link rel="stylesheet" href="css/detailPage.css">
<link rel="stylesheet" href="css/style.css">
<style>
	
  
</style>
</head>
<%
	//request로 받은 데이터
	String getFileName = request.getParameter("fileName");
	BoardDAO bdao = new BoardDAO();
	PostDAO pdao = new PostDAO();
	//파일이름으로 객체 추출
	PostVO vo = bdao.getPostFromFileName(getFileName);
	
	String nickname = (String)session.getAttribute("nickname");
	System.out.println("login page : " + nickname);
	int postNum = vo.getPostNum();
	int download = vo.getDownload();
	
	pdao.countViews(postNum);
	
	String uploadUser = vo.getUser();
	String title = vo.getTitle();
	String link = vo.getLink();
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
    	
    	<div id="hover_bg"></div>
    	
    	<main id="background">
    		<div id="detail_page">
    		
	    		<div id="info_box">
	    			<div id="title_box">
	    				<span><%=title %></span>
	    			</div>
	    			<div id="other_box">
	    				<div class="info_title">User</div>
	    				<hr>
	    				<div id="user" class="info"><a href="User.jsp?user=<%=vo.getUser() %>"><%=vo.getUser() %></a></div><br>
	    				<%
	    					if(link != null) {
	    						%>
	    						<div class="info_title">Link</div>
	    						<hr>
	    						<div id="link" class="info"><%=link %></div><br><%
	    					}
	    				%>
	    				<div class="info_title">Date</div>
	    				<hr>
						<div id="date" class="info"><%=vo.getDate() %></div>
						<div class="info_title">Tags</div>
    					<hr>
						<div id="tag_box">
						<%
							List<String> tags = pdao.getPostTagList(vo.getPostNum());
							for(int i = 0; i < tags.size(); i++) {
								%> <div class="tag"><%=tags.get(i) %></div> 
						<%
							}
						%>
	    				</div>
	    				<div id="button_box">
	    					<!-- 게시물 삭제 버튼 -->
							<%
								if(nickname != null && nickname.equals(uploadUser)) {
							%>
									<form action="DeletePostServlet" method="get">
										<input type="text" value="<%=postNum %>" name="postNum" hidden>
										<input type="text" value="<%=getFileName%>" name="fileName" hidden>
										<input id="delete_btn" type="submit" value="삭제">
									</form>
				<!-- 게시물 수정 -->
									<div id="edit_post_box">
										<!-- <a href="editPost.jsp?title=<%=title %>&fileName=<%=getFileName %>&link=<%=link%>&postNum=<%=postNum%>&download=<%=download%>">수정</a> -->
										<button id="edit_btn" onclick="location.href='editPost.jsp?title=<%=title %>&fileName=<%=getFileName %>&link=<%=link%>&postNum=<%=postNum%>&download=<%=download%>'">Edit</button>
									</div>
							<%
								}
							%>
							
				<!-- 다운로드 버튼 -->
							<%
								if(vo.getDownload() == 1) {
							%>
									<div>
										<form action="download.jsp" method="get">
											<input type="text" value="<%=getFileName %>" name="fname" hidden/>
											<input id="download_btn" type="submit" value="Download"/>
										</form>
									</div>		
							<%		
								}
							
							%>
	    				</div>
	    			</div>
	    			<div id="reply_box">
	    				<%
							if(nickname == null) {
						%>
								<div id="reply_form_box">
				    				<input id="reply_text" type="text" placeholder="댓글은 회원만 입력할 수 있습니다." name="reply" readonly>
				    				<input id="reply_btn" type="button" value="Reply">
								</div>	
						<%
							} else {
						%>
			<!-- 댓글 입력 Form -->
					    	<div id="reply_form_box">
					    		<form action="ReplyServlet" method="get">
					    			<input name = "post_num" type="text" value="<%=vo.getPostNum() %>" hidden>
					    			<input id="reply_text" type="text" placeholder="댓글을 입력해 주세요." name="reply">
					    			<input id="reply_btn" type="submit" value="Reply">
					    		</form>
					    	</div>
				    	<%
							}
						%>
			<!-- 댓글 리스트 -->
						<div id="reply_list">
							<table border="1">
								<tbody>
									<tr><th>댓글</th><th>작성자</th><th>X</th></tr>
							<%
							
								ReplyDAO rdao = new ReplyDAO();
								List<ReplyVO> replyList = rdao.getReplyList(postNum);
							
								for(int i = 0; i < replyList.size(); i++) {
							%>
									<tr>
										<td align=center><%=replyList.get(i).getReply() %></td>
										<td align=center><a href="User.jsp?user=<%=replyList.get(i).getNickName() %>"><%=replyList.get(i).getNickName() %></a></td>
										<td align=center>
											<form action="DeleteReplyServlet">
												<input type="text" value="<%=replyList.get(i).getReplyNum() %>" name="replyNum" hidden>
												<input type="text" value="<%=getFileName %>" name="fileName" hidden>
												<input type="submit" value="X">
											</form>
										</td>
									</tr>
							<%
								}
							%>
								</tbody>
							</table>
						</div>
	    			</div>
	    		</div>
				
				<div id="img_box">
					<img oncontextmenu='return false' src="./imgs/<%=getFileName %>">
				</div>
    		</div>
    	</main>
	</body>
</html>










