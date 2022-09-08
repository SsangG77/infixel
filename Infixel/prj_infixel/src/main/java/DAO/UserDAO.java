package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import VO.PostVO;

public class UserDAO {
	Conn conn = new Conn();
	Connection con = conn.con();
	
	//아이디로 닉네임 찾기
	public String getNickFromId(String id) {
		String query = "select nickname from infixel_user where id = ?";
		String findId = "";
		try (PreparedStatement stmt = con.prepareStatement(query);) {
			
			stmt.setString(1, id);
			ResultSet rs = stmt.executeQuery();
			rs.next();
			findId = rs.getString("nickname");
			return findId;
		} catch (SQLException e) {
			e.printStackTrace();
			return findId;
		}
	}
	
	public List<PostVO> getUserPost(String user) {
		String query = "select * from infixel_board where upload_user = ? order by post_num DESC";
		List<PostVO> list = new ArrayList<>();
		
		try (PreparedStatement stmt = con.prepareStatement(query);) {
			
			stmt.setString(1, user);
			ResultSet rs = stmt.executeQuery();
			System.out.println("UserDAO - getUserPost - user name : " + user);
			while(rs.next()) {
				PostVO vo = new PostVO();
				vo.setDate(rs.getString("upload_time"));
				vo.setDownload(rs.getInt("download_or_not"));
				vo.setFileName(rs.getString("filename"));
				vo.setLink(rs.getString("from_link"));
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
	
}
