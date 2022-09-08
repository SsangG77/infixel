package Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import DAO.BoardDAO;
import DAO.PostDAO;
import VO.PostVO;

@WebServlet("/EditPostServlet")
public class EditPostServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
		PostDAO pdao = new PostDAO();
		
		String savePath = "imgs";
		int uploadFileSizeLimit = 15 * 2048 * 2048;
		String encType = "utf-8";
		ServletContext context = getServletContext();
		String uploadFilePath = context.getRealPath(savePath);
		PrintWriter out = response.getWriter();
		
		try {
			MultipartRequest multi = new MultipartRequest(
				request,
				uploadFilePath,
				uploadFileSizeLimit,
				encType,
				new DefaultFileRenamePolicy()
			);
			
			String fileName = multi.getFilesystemName("image");
			String title = multi.getParameter("title");
			String download = multi.getParameter("link");
			String url = multi.getParameter("link");
			int postNum = Integer.parseInt(multi.getParameter("postNum"));
			pdao.deleteTags(postNum); //해당 포스트의 태그들을 모두 삭제한다.
			
			String[] tags = multi.getParameterValues("tag");
			
			HttpSession session = request.getSession(true);
			String user = (String)session.getAttribute("nickname");
			
			if(tags.length >= 1) {				
				for(int i = 0; i < tags.length; i++) {
					pdao.insertPostTag(tags[i], postNum);
				}
			}
			
			if(fileName == null) {
				System.out.println("파일업로드 안됨");
				out.print("<script>alert('파일이 업로드 되지 않았습니다.');"
						+ "location.href= 'main.jsp'</script>");
				out.close();
			} else {
				PostVO vo = new PostVO();
				vo.setFileName(fileName);
				vo.setTitle(title);
				vo.setDownload(Integer.parseInt(download));
				vo.setUser(user);
				vo.setLink(url);
				vo.setPostNum(postNum);
				 LocalDate now = LocalDate.now();
				vo.setDate(now.toString());
				
				BoardDAO bdao = new BoardDAO();
				//boardDao.insertPost(vo);
				//업로드가 아니라 수정하는 메소드를 불러옴.
				bdao.updatePost(vo);
				response.sendRedirect("main.jsp");
				
			}
			
			
		} catch(Exception e) {
			System.out.println("파일 업로드 실패");
			System.out.println(e.getMessage());
		}
		
		
		
	}

}
