package Servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.LoginDAO;

/**
 * Servlet implementation class FindIdServlet
 */
@WebServlet("/FindIdServlet")
public class FindIdServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		
		String name = request.getParameter("name");
		String birthday = request.getParameter("birthday");
		String nickname = request.getParameter("nickname");
		
		LoginDAO dao = new LoginDAO();
		String result = dao.findId(name, birthday, nickname);
		System.out.println("ID Servlet : " + result);
		out.print(result);
		
	}

}
