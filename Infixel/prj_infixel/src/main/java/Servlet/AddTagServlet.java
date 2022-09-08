package Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import DAO.PostDAO;


@WebServlet("/AddTagServlet")
public class AddTagServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		PostDAO pdao = new PostDAO();
		
		String getTag = request.getParameter("tag");
		
		if(getTag == null) {
			//바로 리스트 반환
			List<String> tagList = pdao.getTagList();
			JSONObject obj = new JSONObject();
			obj.append("list", tagList);
			out.print(obj);
		} else {			
			pdao.insertTag(getTag);
			List<String> tagList = pdao.getTagList();
			JSONObject obj = new JSONObject();
			obj.append("list", tagList);
			out.print(obj);
		}
		
	}

}
