package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import VO.PostVO;

public class BoardDAO {
	Conn conn = new Conn();
	Connection con = conn.con();
	
	public int getBoardLastNum() {
		String query = "select MAX(post_num) from infixel_board";
		int result = 0;
		try (Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(query);){
			
			rs.next();
			result = rs.getInt(1);
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			return result;
		}
		
	}
	
	
	public void insertPost(PostVO vo) {
		String query = "insert into infixel_board values (?, ?, ?, ?, ?, ?, ?, ?)";
		
		try (PreparedStatement stmt = con.prepareStatement(query);){
			
			stmt.setInt(1, vo.getPostNum());
			stmt.setString(2, vo.getTitle());
			stmt.setString(3, vo.getUser());
			stmt.setString(4, vo.getDate());
			stmt.setInt(5, vo.getDownload());
			stmt.setString(6, vo.getFileName());
			stmt.setString(7, vo.getLink());
			stmt.setInt(8, 0);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<PostVO> getPostList() {
		String query = "select * from infixel_board order by post_num DESC";
		List<PostVO> list = new ArrayList<>();
		
		try (Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(query);){
			
			while(rs.next()) {
				PostVO vo = new PostVO();
				vo.setDate(rs.getString("upload_time"));
				vo.setDownload(rs.getInt("download_or_not"));
				vo.setFileName(rs.getString("filename"));
				vo.setLink(rs.getString("from_link"));
				vo.setTitle(rs.getString("title"));
				vo.setUser(rs.getString("upload_user"));
				vo.setViews(rs.getInt("views"));
				list.add(vo);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return list;
		} 
	}
	
	public List<PostVO> getSearchList(String search) {
		String query ="select DISTINCT b.* from post_tag p, infixel_board b where p.post_num = b.post_num and p.tag_name LIKE ?";
		List<PostVO> list = new ArrayList<>();
		
		try (PreparedStatement stmt = con.prepareStatement(query);) {
			
			stmt.setString(1, "%" + search + "%");
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				PostVO vo = new PostVO();
				vo.setDate(rs.getString("upload_time"));
				vo.setDownload(rs.getInt("download_or_not"));
				vo.setFileName(rs.getString("filename"));
				vo.setLink(rs.getString("from_link"));
				vo.setPostNum(rs.getInt("post_num"));
				vo.setTitle(rs.getString("title"));
				vo.setUser(rs.getString("upload_user"));
				list.add(vo);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			return list;
		}
	}
	
	
	public PostVO getPostFromFileName(String fileName) {
		String query = "select * from infixel_board where filename = ?";
		
		try (PreparedStatement stmt = con.prepareStatement(query);) {
			
			stmt.setString(1, fileName);
			ResultSet rs = stmt.executeQuery();
			PostVO vo = new PostVO();
			if(rs.next()) {
				vo.setFileName(rs.getString("filename"));
				vo.setDate(rs.getString("upload_time"));
				vo.setDownload(rs.getInt("download_or_not"));
				vo.setLink(rs.getString("from_link"));
				vo.setTitle(rs.getString("title"));
				vo.setUser(rs.getString("upload_user"));
				vo.setPostNum(rs.getInt("post_num"));
			} 
			return vo;
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public String getFileNameFromPostNum(int postNum) {
		String query = "select filename from infixel_board where post_num = ?";
		String fileName = "";
		try (PreparedStatement stmt = con.prepareStatement(query);) {
			
			stmt.setInt(1, postNum);
			ResultSet rs = stmt.executeQuery();
			rs.next();
			fileName = rs.getString("filename");
			return fileName;
		} catch (SQLException e) {
			e.printStackTrace();
			return fileName;
		}
	}
	
	public void updatePost(PostVO vo) {
		String title = vo.getTitle();
		String fileName = vo.getFileName();
		String link = vo.getLink();
		int postNum = vo.getPostNum();
		
		String query = "UPDATE infixel_board SET title = ?, filename = ?, link= ? where POST_NUM = ?";
		
		try (PreparedStatement stmt = con.prepareStatement(query);) {
			
			stmt.setString(1, title);
			stmt.setString(2, fileName);
			stmt.setString(3, link);
			stmt.setInt(4, postNum);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
