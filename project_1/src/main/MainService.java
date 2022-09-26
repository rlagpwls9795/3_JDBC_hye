package main;

import static common.JDBCTemplate.*;

import java.sql.Connection;

import member.model.vo.Member;

public class MainService {
	
	private MainDAO dao = new MainDAO();

	/** 로그인 서비스
	 * @param memberId
	 * @param memberPw
	 * @return loginMember
	 * @throws Exception
	 */
	public Member logIn(String memberId, String memberPw) throws Exception{
		Connection conn = getConnection();
		
		Member loginMember = dao.logIn(conn, memberId, memberPw);
		
		close(conn);
		
		return loginMember;
	}

	/** 아이디 중복 검사 서비스
	 * @param memberId
	 * @return result
	 * @throws Exception
	 */
	public int idDupCheck(String memberId) throws Exception{
		int result=0;
		
		Connection conn = getConnection();
		
		result = dao.idDupCheck(conn, memberId);
		
		close(conn);
		
		return result;
	}

	public int signUp(Member member) throws Exception{
		int result=0;
		
		Connection conn = getConnection();
		
		result = dao.signUp(conn, member);
		
		close(conn);
		
		return result;
	}

}
