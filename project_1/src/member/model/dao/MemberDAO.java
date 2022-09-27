package member.model.dao;

import static common.JDBCTemplate.*;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import member.model.vo.Member;
import oracle.net.aso.m;

public class MemberDAO {
	
	private Statement stmt=null;
	private PreparedStatement pstmt=null;
	private ResultSet rs=null;
	
	private Properties prop=null;
	
	public MemberDAO() {
		try {
			prop = new Properties();
			prop.loadFromXML(new FileInputStream("member-query.xml"));
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** 정보 조회 DAO
	 * @param conn 
	 * @param loginMember
	 * @return member
	 * @throws Exception
	 */
	public Member myInfo(Connection conn, Member loginMember) throws Exception{
		Member member = null;
		
		try {
			String sql = prop.getProperty("myInfo");
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, loginMember.getMemberNo());
			pstmt.setInt(2, loginMember.getMemberNo());
			
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				member = new Member();
				member.setMemberNo(rs.getInt("MEMBER_NO"));
				member.setMemberId(rs.getString("MEMBER_ID"));
				member.setMemberName(rs.getString("MEMBER_NM"));
				member.setMemberGender(rs.getString("MEMBER_GENDER"));
				member.setEnrollDate(rs.getString("ENROLL_DATE"));
				member.setGradeName(rs.getString("GRADE_NM"));
				member.setPostCnt(rs.getInt("POST_CNT"));
				member.setLikeCnt(rs.getInt("POST_LIKE"));
			}
			
		} finally {
			close(rs);
			close(pstmt);
		}
		
		return member;
	}

	/** 비밀번호 변경 DAO
	 * @param conn
	 * @param currentPw
	 * @param newPw1
	 * @param memberNo
	 * @return result
	 * @throws Exception
	 */
	public int updatePw(Connection conn, String currentPw, String newPw1, int memberNo) throws Exception{
		int result=0;
		
		try {
			String sql = prop.getProperty("updatePw");
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, newPw1);
			pstmt.setInt(2, memberNo);
			pstmt.setString(3, currentPw);
			
			result = pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}
		return result;
	}

	/** 회원 탈퇴
	 * @param conn
	 * @param memberPw
	 * @param memberNo
	 * @return result
	 * @throws Exception
	 */
	public int deleteInfo(Connection conn, String memberPw, int memberNo) throws Exception {
		int result=0;
		
		try {
			String sql = prop.getProperty("deleteInfo");
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, memberNo);
			pstmt.setString(2, memberPw);
			
			result = pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}
		return result;
	}

	/** 회원 목록 조회 DAO
	 * @param conn
	 * @return memberList
	 * @throws Exception
	 */
	public List<Member> selectAll(Connection conn) throws Exception{
		List<Member> memberList=new ArrayList<>();
		
		try {
			String sql = prop.getProperty("selectAll");
			stmt=conn.createStatement();
			rs=stmt.executeQuery(sql);
			
			while(rs.next()) {
				Member member=new Member();
				member.setMemberNo(rs.getInt(1));
				member.setMemberId(rs.getString(2));
				member.setMemberName(rs.getString(3));
				member.setMemberGender(rs.getString(4));
				member.setGradeName(rs.getString(5));
				memberList.add(member);
			}
			
		} finally {
			close(rs);
			close(stmt);
		}
		
		return memberList;
	}

}



























