package post.model.service;

import static common.JDBCTemplate.*;

import java.sql.Connection;
import java.util.List;

import member.model.vo.Member;
import post.model.dao.PostDAO;
import post.model.vo.Post;

public class PostService {
	
	private PostDAO pDao = new PostDAO();
	
	/** 게시글 목록
	 * @param boardNo
	 * @return postList
	 * @throws Exception
	 */
	public List<Post> selectAllPost(int boardNo) throws Exception{
		Connection conn= getConnection();
		List<Post> postList = pDao.selectAllPost(conn, boardNo);
		close(conn);
		return postList;
	}

	/** 게시글 상세 조회
	 * @param boardNo
	 * @param postNo
	 * @param loginMember
	 * @return post
	 * @throws Exception
	 */
	public Post selectPost(int boardNo, int postNo, Member loginMember) throws Exception{
		Connection conn= getConnection();
		Post post = pDao.selectPost(conn, boardNo, postNo, loginMember);
		close(conn);
		return post;
	}

	/** 게시글 수정
	 * @param post 
	 * @return result
	 * @throws Exception
	 */
	public int updatePost(Post post) throws Exception{
		Connection conn= getConnection();
		int result = pDao.updatePost(conn, post);
		if(result>0) commit(conn);
		else rollback(conn);
		return result;
	}

	/** 게시글 삭제
	 * @param boardNo
	 * @param postNo
	 * @return result
	 * @throws Exception
	 */
	public int deletePost(int boardNo, int postNo) throws Exception{
		Connection conn= getConnection();
		int result = pDao.deletePost(conn, boardNo, postNo);
		if(result>0) commit(conn);
		else rollback(conn);
		return result;
	}

	/** 게시글 작성
	 * @param post 
	 * @return result
	 * @throws Exception
	 */
	public int insertPost(Post post) throws Exception{
		Connection conn= getConnection();
		
		int postNo = pDao.nextPostNo(conn);
		post.setPostNo(postNo);
		
		int result = pDao.insertPost(conn, post);
		if(result>0) {
			commit(conn);
			result=postNo;			
		}
		else rollback(conn);
		close(conn);
		return result;
	}

	/** 게시글 수 증가
	 * @param loginMember
	 * @return PostCntResult
	 * @throws Exception
	 */
	public int increasePostCnt(Member loginMember) throws Exception{
		Connection conn= getConnection();
		
		int PostCntResult=pDao.increasePostCnt(conn, loginMember);
		
		if(PostCntResult>0) {
			commit(conn);
			loginMember.setPostCnt(loginMember.getPostCnt()+1);
		} else rollback(conn);
		
		return PostCntResult;
	}

	/** 등급 업데이트
	 * @param loginMember
	 * @return result
	 * @throws Exception
	 */
	public int updateGrade(Member loginMember) throws Exception{
		Connection conn= getConnection();
		int result = pDao.updateGrade(conn, loginMember);
		if(result>0) {
			commit(conn);
			loginMember.setGradeNo(loginMember.getGradeNo()+10);
		}
		else rollback(conn);
		return result;
	}

	/** 게시글 검색
	 * @param boardNo
	 * @param condition
	 * @param query
	 * @return postList
	 * @throws Exception
	 */
	public List<Post> searchPost(int boardNo, int condition, String query) throws Exception{
		Connection conn= getConnection();
		List<Post> postList = pDao.searchPost(conn, boardNo,condition,query);
		close(conn);
		return postList;
	}

	/** 좋아요 업데이트
	 * @param boardNo
	 * @param post
	 * @return result
	 * @throws Exception
	 */
	public int updatePostLike(int boardNo, Post post) throws Exception{
		Connection conn=getConnection();
		int result= pDao.updatePostLike(conn, boardNo, post);
		if(result>0) {
			commit(conn);
			post.setPostLike(post.getPostLike()+1);
		}
		else rollback(conn);
		
		return result;
	}

}






















