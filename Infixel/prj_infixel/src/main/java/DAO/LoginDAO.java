package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import VO.UserVO;

public class LoginDAO {
	Conn conn = new Conn();
	Connection con = conn.con();
	
	//아이디 중복 체크
	public boolean checkId(String id) {
		String query = "select count(*) from infixel_user where id = ?";
		int result = 0;
		try (PreparedStatement stmt = con.prepareStatement(query);) {
			
			stmt.setString(1, id);
			ResultSet rs = stmt.executeQuery();
			rs.next();	
				result = rs.getInt(1);
				System.out.println("LoginDAO-checkId : " + result);
			
			if(result == 1) {
				return true;
			} else {
				return false;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	} //아이디중복체크---
	
	//로그인
	public boolean login(String id, String pw) {
		String query = "select count(*) from infixel_user where id = ? and pw = ?";
		int result = -1;
		try (PreparedStatement stmt = con.prepareStatement(query);){
			
			stmt.setString(1, id);
			stmt.setString(2, pw);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {				
				result = rs.getInt(1);
			}
			if(result == 1) {
				return true;
			} else {
				return false;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}//로그인---
	
	//회원가입
	public void signup(UserVO vo) {
		String query = "insert into infixel_user values (user_seq.nextval, ?, ?, ?, ?, ?)";
		
		try (PreparedStatement stmt = con.prepareStatement(query);){
			
			stmt.setString(1, vo.getId());
			stmt.setString(2, vo.getPw());
			stmt.setString(3, vo.getName());
			stmt.setString(4, vo.getBirthday());
			stmt.setString(5, vo.getNickname());
			stmt.executeUpdate();
			System.out.println("LoginDAO-signup메소드 실행됨.");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//아이디 찾기
	public String findId(String name, String birthday, String nickname) {
		String query = "select id from infixel_user where name = ? and birthday = ? and nickname = ?";
		String result = "";
		try (PreparedStatement stmt = con.prepareStatement(query);){
			
			stmt.setString(1, name);
			stmt.setString(2, birthday);
			stmt.setString(3, nickname);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {				
				result = rs.getString("id");
			} else {
				result = "아이디를 찾을 수 없습니다.";
			}
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			return result;
		}
	}
	
	
	//비밀번호 찾기
		public String findPw(String name, String birthday, String nickname, String id) {
			String query = "select pw from infixel_user where name = ? and birthday = ? and nickname = ? and ID = ?";
			String result = "";
			try (PreparedStatement stmt = con.prepareStatement(query);){
				
				stmt.setString(1, name);
				stmt.setString(2, birthday);
				stmt.setString(3, nickname);
				stmt.setString(4, id);
				ResultSet rs = stmt.executeQuery();
				if(rs.next()) {				
					result = rs.getString("pw");
				} else {
					result = "비밀번호를 찾을 수 없습니다.";
				}
				return result;
			} catch (SQLException e) {
				e.printStackTrace();
				return result;
			}
		}
	
	
	
	
	
	
	
}
