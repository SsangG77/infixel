package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PostDAO {
	Conn conn = new Conn();
	Connection con = conn.con();
	
	public void insertTag(String tag) {
		String query = "insert into infixel_tags values (tag_seq.nextval, ?)";
		
		try (PreparedStatement stmt = con.prepareStatement(query);) {
			
			stmt.setString(1, tag);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<String> getTagList() {
		String query = "select * from infixel_tags";
		List<String> tagList = new ArrayList<>();
		try (PreparedStatement stmt = con.prepareStatement(query);) {
			
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				tagList.add(rs.getString("tag_name"));
			}
			return tagList;
		} catch (SQLException e) {
			e.printStackTrace();
			return tagList;
		}
	}

	
	public void insertPostTag(String tagName, int postNum) {
		String query = "insert into post_tag values (?, ?)";
		try (PreparedStatement stmt = con.prepareStatement(query);) {
			
			stmt.setString(1, tagName);
			stmt.setInt(2, postNum);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<String> getPostTagList(int postNum) {
		String query = "select * from post_tag where post_num = ?";
		List<String> list = new ArrayList<>();
		try (PreparedStatement stmt = con.prepareStatement(query);){
			
			stmt.setInt(1, postNum);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				list.add(rs.getString("tag_name"));
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			return list;
		}
	}
	
	public void deletePost(int postNum) {
		String query = "delete from infixel_board where post_num = ?";
		try (PreparedStatement stmt = con.prepareStatement(query);){
			
			stmt.setInt(1, postNum);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void deleteTags(int postNum) {
		String query = "delete from post_tag where post_num = ?";
		
		try (PreparedStatement stmt = con.prepareStatement(query);) {
			
			stmt.setInt(1, postNum);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void countViews(int postNum) {
		String query = "UPDATE infixel_board SET views = ? where post_num = ?";
		int nowViews = getPostViews(postNum);
		
		try (PreparedStatement stmt = con.prepareStatement(query);) {
			
			stmt.setInt(1, nowViews+1);
			stmt.setInt(2, postNum);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public int getPostViews(int postNum) {
		String query = "select views from infixel_board where post_num = ?";
		int result = -1;
		try (PreparedStatement stmt = con.prepareStatement(query);){
			
			stmt.setInt(1, postNum);
			ResultSet rs = stmt.executeQuery();
			rs.next();
			result = rs.getInt(1);
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return result;
		}
	}
	
}






