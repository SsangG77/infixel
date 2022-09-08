package Servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DAO.LoginDAO;
import DAO.UserDAO;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		System.out.println("LoginServlet : id = "+id +", pw = "+pw);
		HttpSession session = request.getSession(true);
		
		LoginDAO dao = new LoginDAO();
		boolean result = dao.login(id, pw);
		System.out.println("LoginServlet : result = "+result);
		if(result) {
			UserDAO uDao = new UserDAO();
			String nickname = uDao.getNickFromId(id);
			session.setAttribute("nickname", nickname);
			
			//response.sendRedirect("User.jsp?nickname="+nickname);
			response.sendRedirect("main.jsp");
		} else {
			//아이디가 없다는 알림 띄우고 회원가입페이지로 이동
			out.print("<script>"
					+ "alert('없는 계정입니다. 회원가입 페이지로 이동됩니다.');"
					+ "location.href= 'signup.jsp'"
					+ "</script>");
			out.close();
		}
	}

}
