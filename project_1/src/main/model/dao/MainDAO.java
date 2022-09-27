package main.model.dao;

import static common.JDBCTemplate.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

import member.model.vo.Member;

public class MainDAO {
	
	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private Properties prop;
	
	public MainDAO() {
		try {
			prop=new Properties();
			prop.loadFromXML(new FileInputStream("main-query.xml"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** 로그인 DAO
	 * @param conn
	 * @param memberId
	 * @param memberPw
	 * @return loginMember
	 * @throws Exception
	 */
	public Member logIn(Connection conn, String memberId, String memberPw) throws Exception{
		
		Member loginMember = null;
		
		try {
			String sql = prop.getProperty("logIn");
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, memberId);
			pstmt.setString(2, memberPw);
			
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				loginMember = new Member();
				loginMember.setMemberNo(rs.getInt("MEMBER_NO"));
				loginMember.setMemberId(memberId);
				loginMember.setMemberName(rs.getString("MEMBER_NM"));
				loginMember.setMemberGender(rs.getString("MEMBER_GENDER"));
				loginMember.setEnrollDate(rs.getString("ENROLL_DATE"));
				loginMember.setGradeNo(rs.getInt("GRADE_NO"));
				loginMember.setPostCnt(rs.getInt("POST_CNT"));
			}
			
		} finally {
			close(rs);
			close(pstmt);
		}
		return loginMember;
	}

	/** 아이디 중복 검사 DAO
	 * @param conn
	 * @param memberId
	 * @return result
	 * @throws Exception
	 */
	public int idDupCheck(Connection conn, String memberId) throws Exception{
		int result=0;
		
		try {
			String sql = prop.getProperty("idDupCheck");
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, memberId);
			rs=pstmt.executeQuery();
			
			if(rs.next()) result=rs.getInt(1);
		
		} finally {
			close(rs);
			close(pstmt);
		}
		return result;
	}

	/** 회원가입 DAO
	 * @param conn
	 * @param member
	 * @return result
	 * @throws Exception
	 */
	public int signUp(Connection conn, Member member) throws Exception{
		int result=0;
		
		try {
			String sql = prop.getProperty("signUp");
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, member.getMemberId());
			pstmt.setString(2, member.getMemberPw());
			pstmt.setString(3, member.getMemberName());
			pstmt.setString(4, member.getMemberGender());
			
			result=pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}
		return result;
	}

}








