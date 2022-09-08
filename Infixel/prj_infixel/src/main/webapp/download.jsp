<%@page import="java.io.OutputStream"%>
<%@page import="java.io.BufferedInputStream"%>
<%@page import="java.io.FileInputStream"%>
<%@page import="java.io.InputStream"%>
<%@page import="java.io.File"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<%
	String fname = request.getParameter("fname");
	String dir = application.getRealPath("/imgs");
	
	File f = new File(dir, fname);
	InputStream is = new FileInputStream(f);
	BufferedInputStream bis = new BufferedInputStream(is);
	
	/////////////
	response.reset();
	response.setContentType("application/octet-stream");//바이트자료로 읽어들이는 코드
	
	//한글파일을 다운받을 때 사용하는 코드
	String encodedFname = new String(fname.getBytes("UTF-8"), "ISO-8859-1");
	
	//밑에 다운로드 상태 나타나게 하는 코드ㅣ
	response.setHeader("Content-Disposition", "attachment; filename=\"" + encodedFname + "\""); 
	response.setHeader("Content-Length", "" + f.length());
	
	out.clear();
	out = pageContext.pushBody();
	
	OutputStream os = response.getOutputStream();
	byte[] b = new byte[(int)f.length()]; //컨텐츠의 크기만큼의 배열을 생성한다.
	int readBuffer = 0;
	
	while((readBuffer = bis.read(b)) > 0) {
		os.write(b, 0, readBuffer); //갯수를 세어서 출력한다. 끝날때까지
	}
	os.flush();
	os.close();
	bis.close();
	is.close();
%>
<body>

</body>
</html>