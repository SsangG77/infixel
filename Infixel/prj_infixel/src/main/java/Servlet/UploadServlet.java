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


@WebServlet("/UploadServlet")
public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		
		
		String savePath = "imgs"; //업로드될 폴더 이름 지정
		int uploadFileSizeLimit = 15 * 2048 * 2048; //업로드 가능한 파일의 최대 크기 지정
		String encType = "utf-8";
		ServletContext context = getServletContext(); //컨텍스트단위((=프로젝트)=어플리케이션)단위로 생성된다.
		String uploadFilePath = context.getRealPath(savePath); //파일이 저장된 폴더의 경로(폴더이름)
		
			
		
		try {
			MultipartRequest multi = new MultipartRequest(
					request, 
					uploadFilePath, 
					uploadFileSizeLimit, 
					encType,
					new DefaultFileRenamePolicy() //이름이 중복되면 파일이름을 알아서 처리해줌.
			);
			
			String fileName = multi.getFilesystemName("image"); //저장된 파일의 이름
			String title = multi.getParameter("title");
			String download = multi.getParameter("download");
			String url = multi.getParameter("link");
			int postNum = Integer.parseInt(multi.getParameter("postNum")); 
			String[] tags = multi.getParameterValues("tag");
			PostDAO pdao = new PostDAO();
			if(tags.length >= 1) {
				for(int i = 0; i < tags.length; i++) {
					pdao.insertPostTag(tags[i], postNum);
				}
			}
			
			//테스트용 유저(원래는 세션에 저장된 유저정보 입력)
			HttpSession session = request.getSession(true);
			String user = (String)session.getAttribute("nickname");
			
			if(fileName == null) {
				System.out.println("파일업로드 안됨");
				out.print("<script>alert('파일이 업로드 되지 않았습니다.');"
						+ "location.href= 'upload.jsp'</script>");
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
				
				BoardDAO boardDao = new BoardDAO();
				boardDao.insertPost(vo);
				response.sendRedirect("main.jsp");
				
			}
		} catch(Exception e) {
			System.out.println("파일 업로드 실패");
			System.out.println(e.getMessage());
			out.print("<script>alert('파일이 업로드 되지 않았습니다.');"
					+ "location.href= 'upload.jsp'</script>");
			out.close();
		}
	}

}
