package member.model.service;

import static common.JDBCTemplate.*;

import java.sql.Connection;
import java.util.List;

import member.model.dao.MemberDAO;
import member.model.vo.Member;

public class MemberService {
	
	private MemberDAO dao = new MemberDAO();

	/** 정보 조회 서비스
	 * @param loginMember
	 * @return member
	 * @throws Exception
	 */
	public Member myInfo(Member loginMember) throws Exception{
		Connection conn = getConnection();
		
		Member member = dao.myInfo(conn, loginMember);
		
		close(conn);
		
		return member;
	}

	/** 비밀번호 변경 서비스
	 * @param currentPw
	 * @param newPw1
	 * @param memberNo
	 * @return result
	 * @throws Exception
	 */
	public int updatePw(String currentPw, String newPw1, int memberNo) throws Exception{
		Connection conn = getConnection();
		
		int result = dao.updatePw(conn, currentPw, newPw1, memberNo);
		
		if(result>0) commit(conn);
		else rollback(conn);
		
		close(conn);
		
		return result;
	}

	/** 회원 탈퇴 서비스
	 * @param memberPw
	 * @param memberNo
	 * @return result
	 * @throws Exception
	 */
	public int deleteInfo(String memberPw, int memberNo) throws Exception{
		Connection conn = getConnection();
		
		int result = dao.deleteInfo(conn, memberPw, memberNo);
		
		if(result>0) commit(conn);
		else rollback(conn);
		
		close(conn);
		
		return result;
	}

	/** 회원 목록 조회 서비스
	 * @return memberList
	 * @throws Exception
	 */
	public List<Member> selectAll() throws Exception{
		Connection conn = getConnection();
		
		List<Member> memberList = dao.selectAll(conn);
		
		close(conn);
		
		return memberList;
	}

}
