package Servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.BoardDAO;
import VO.PostVO;


@WebServlet("/random")
public class RandomImageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Random rd = new Random();
		
		BoardDAO dao = new BoardDAO();
		List<PostVO> list = dao.getPostList();
		
		List<String> fileNameArray = new ArrayList<>();
		for(int i = 0; i < list.size(); i++) {
			fileNameArray.add(list.get(i).getFileName());
		}
		
		response.sendRedirect("./imgs/" +fileNameArray.get(rd.nextInt(list.size())));
		
	}

}
