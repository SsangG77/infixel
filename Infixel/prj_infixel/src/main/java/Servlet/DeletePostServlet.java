package Servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.PostDAO;


@WebServlet("/DeletePostServlet")
public class DeletePostServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		
		String postNum_str = request.getParameter("postNum");
		PostDAO dao = new PostDAO();
		int postNum = Integer.parseInt(postNum_str);
		
		String fileName = request.getParameter("fileName");
		String savePath = "imgs";
		ServletContext context = getServletContext();
		String uploadFilePath = context.getRealPath(savePath);
		
		System.out.println("DeletePostServlet - Path : " + uploadFilePath +"\\"+ fileName);
		System.out.println("DeletePostServlet - postNum_str : " + postNum_str);
		
		File file = new File(uploadFilePath  +"\\"+  fileName);
		if(file.exists()) {
			file.delete();
			dao.deletePost(postNum);
			dao.deleteTags(postNum);
			response.sendRedirect("main.jsp");
		} else {
			System.out.println("파일 삭제 실패");
			out.print("<script>alert('파일이 삭제 되지 않았습니다.');"
					+ "location.href= 'main.jsp'</script>");
			out.close();
		}
		
		
		
		
		
	}

}
