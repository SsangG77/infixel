package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import VO.ReplyVO;

public class ReplyDAO {
	Conn conn = new Conn();
	Connection con = conn.con();
	
	
	//댓글 등록
	public void insertReply(ReplyVO vo) {
		int postNum = vo.getPostNum();
		String reply = vo.getReply();
		String nickName = vo.getNickName();
		
		String query = "insert into infixel_reply values (?, reply_seq.nextval, ?, ?)";
		try (PreparedStatement stmt = con.prepareStatement(query);){
			
			stmt.setInt(1, postNum);
			stmt.setString(2, reply);
			stmt.setString(3, nickName);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	//댓글 가져오기
	public List<ReplyVO> getReplyList(int postNum) {
		String query = "select * from infixel_reply where post_num = ?";
		List<ReplyVO> list = new ArrayList<>();
		try (PreparedStatement stmt = con.prepareStatement(query);) {
			
			stmt.setInt(1, postNum);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				ReplyVO vo = new ReplyVO();
				vo.setNickName(rs.getString("nickname"));
				vo.setPostNum(postNum);
				vo.setReply(rs.getString("reply"));
				vo.setReplyNum(rs.getInt("reply_num"));
				list.add(vo);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			return list;
		}
		
	}
	
	//댓글 삭제
	public void deleteReply(int replyNum) {
		String query = "delete from infixel_reply where reply_num = ?";
		try (PreparedStatement stmt = con.prepareStatement(query);){
			
			stmt.setInt(1, replyNum);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
}
