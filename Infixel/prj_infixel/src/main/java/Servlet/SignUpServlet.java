package Servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.LoginDAO;
import VO.UserVO;

@WebServlet("/SignUpServlet")
public class SignUpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		String name = request.getParameter("name");
		String birthday = request.getParameter("birthday");
		String nickname = request.getParameter("nickname");
		System.out.println("SignUpServlet: "+id+" "+pw+" "+name+" "+birthday+" "+nickname);
		
		LoginDAO dao = new LoginDAO();
		boolean checkId = dao.checkId(id);
		if(checkId) {
			out.print("<script>"
					+ "alert('이미 존재하는 계정입니다. 로그인 페이지로 이동됩니다.');"
					+ "location.href= 'signup.jsp'"
					+ "</script>");
			out.close();
		} else {
			UserVO vo = new UserVO();
			vo.setId(id);
			vo.setPw(pw);
			vo.setBirthday(birthday);
			vo.setName(name);
			vo.setNickname(nickname);
			dao.signup(vo);
			response.sendRedirect("main.jsp");
		}
	}

}
