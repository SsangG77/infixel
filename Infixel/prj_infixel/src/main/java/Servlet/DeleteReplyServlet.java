package Servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.ReplyDAO;


@WebServlet("/DeleteReplyServlet")
public class DeleteReplyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String replyNum_str = request.getParameter("replyNum");
		String fileName = request.getParameter("fileName");
		int replyNum = Integer.parseInt(replyNum_str);
		
		ReplyDAO dao = new ReplyDAO();
		dao.deleteReply(replyNum);
		
		response.sendRedirect("detailPage.jsp?fileName="+fileName);
	}

}
