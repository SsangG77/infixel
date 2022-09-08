package Servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DAO.BoardDAO;
import DAO.ReplyDAO;
import VO.ReplyVO;


@WebServlet("/ReplyServlet")
public class ReplyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//포스트 번호, 댓글 번호, 작성자(닉네임), 댓글 내용
		HttpSession session = request.getSession(true);
		String nickname = (String)session.getAttribute("nickname");
		
		String postNum_str = request.getParameter("post_num");
		
		int postNum = Integer.parseInt(postNum_str);
		String reply = request.getParameter("reply");
		
		ReplyVO vo = new ReplyVO();
		vo.setNickName(nickname);
		vo.setPostNum(postNum);
		vo.setReply(reply);
		BoardDAO bdao = new BoardDAO();
		ReplyDAO rdao = new ReplyDAO();
		rdao.insertReply(vo);
		String getFileName = bdao.getFileNameFromPostNum(postNum);
		response.sendRedirect("detailPage.jsp?fileName=" + getFileName);
		
	}

}
