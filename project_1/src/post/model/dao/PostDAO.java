package post.model.dao;

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
import post.model.vo.Post;

public class PostDAO {
	
	private Statement stmt=null;
	private PreparedStatement pstmt=null;
	private ResultSet rs=null;
	
	private Properties prop=null;
	
	public PostDAO() {
		try {
			prop = new Properties();
			prop.loadFromXML(new FileInputStream("post-query.xml"));
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** 게시글 목록 조회
	 * @param conn
	 * @param boardNo
	 * @return postList
	 * @throws Exception
	 */
	public List<Post> selectAllPost(Connection conn, int boardNo) throws Exception{
		List<Post> postList = new ArrayList<>();
		
		try {
			String sql = prop.getProperty("selectAllPost");
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, boardNo);
			rs=pstmt.executeQuery();
			while(rs.next()) {
				Post post = new Post();
				post.setPostNo(rs.getInt(1));
				post.setPostTitle(rs.getString(2));
				post.setMemberName(rs.getString(3));
				post.setPostLike(rs.getInt(4));
				post.setCreateDate(rs.getString(5));
				
				postList.add(post);
			}
		
		} finally {
			close(rs);
			close(pstmt);
		}
		return postList;
	}

	/** 게시글 상세 조회
	 * @param conn
	 * @param boardNo
	 * @param postNo
	 * @param loginMember
	 * @return post
	 * @throws Exception
	 */
	public Post selectPost(Connection conn, int boardNo, int postNo, Member loginMember) throws Exception{
		Post post=null;
		try {
			String sql = prop.getProperty("selectPost");
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, boardNo);
			pstmt.setInt(2, postNo);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				post = new Post();
				post.setPostNo(postNo);
				post.setPostTitle(rs.getString(2));
				post.setPostContent(rs.getString(3));
				post.setMemberName(rs.getString(4));
				post.setMemberNo(rs.getInt(5));
				post.setPostLike(rs.getInt(6));
				post.setCreateDate(rs.getString(7));
			}
		} finally {
			close(rs);
			close(pstmt);
		}
		return post;
	}

	/** 게시글 수정
	 * @param conn
	 * @param post
	 * @return result
	 * @throws Exception
	 */
	public int updatePost(Connection conn, Post post) throws Exception {
		int result=0;
		
		try {
			String sql = prop.getProperty("updatePost");
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, post.getPostTitle());
			pstmt.setString(2, post.getPostContent());
			pstmt.setInt(3, post.getPostNo());
			pstmt.setInt(4, post.getBoardNo());
			
			result=pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}
		return result;
	}

	/** 게시글 삭제
	 * @param conn
	 * @param boardNo
	 * @param postNo
	 * @return result
	 * @throws Exception
	 */
	public int deletePost(Connection conn, int boardNo, int postNo) throws Exception{
		int result=0;
		
		try {
			String sql = prop.getProperty("deletePost");
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, postNo);
			pstmt.setInt(2, boardNo);
			
			result=pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}
		return result;
	}

	/** 게시글 작성
	 * @param conn
	 * @param post
	 * @return result
	 * @throws Exception
	 */
	public int insertPost(Connection conn, Post post) throws Exception{
		int result=0;
		
		try {
			String sql = prop.getProperty("insertPost");
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, post.getPostNo());
			pstmt.setString(2, post.getPostTitle());
			pstmt.setString(3, post.getPostContent());			
			pstmt.setInt(4, post.getMemberNo());
			pstmt.setInt(5, post.getBoardNo());
			
			result=pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}
		return result;
	}

	/** 다음 게시글 번호 생성
	 * @param conn
	 * @return postNo
	 * @throws Exception
	 */
	public int nextPostNo(Connection conn) throws Exception{
		int postNo=0;
		
		try {
			String sql = prop.getProperty("nextPostNo");
			stmt=conn.createStatement();
			rs=stmt.executeQuery(sql);
			
			if(rs.next()) {
				postNo=rs.getInt(1);
			}
			
		} finally {
			close(rs);
			close(stmt);
		}
		
		return postNo;
	}

	/** 게시글 수 증가
	 * @param conn
	 * @param loginMember
	 * @return PostCntResult
	 * @throws Exception
	 */
	public int increasePostCnt(Connection conn,Member loginMember) throws Exception{
		int PostCntResult=0;
		
		try {
			String sql = prop.getProperty("increasePostCnt");
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, loginMember.getMemberNo());
			
			PostCntResult=pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}
		
		return PostCntResult;
	}

	/** 등급 업데이트
	 * @param conn
	 * @param loginMember
	 * @return result
	 * @throws Exception
	 */
	public int updateGrade(Connection conn, Member loginMember) throws Exception{
		int result=0;
		
		try {
			String sql = prop.getProperty("updateGrade");
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, loginMember.getMemberNo());
			
			result=pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}
		return result;
	}

	/** 게시글 검색
	 * @param conn
	 * @param boardNo
	 * @param condition
	 * @param query
	 * @return postList
	 * @throws Exception
	 */
	public List<Post> searchPost(Connection conn, int boardNo, int condition, String query) throws Exception{
		List<Post> postList = new ArrayList<>();
		
		try {
			String sql = prop.getProperty("searchPost1")
					+ prop.getProperty("searchPost2_"+ condition)
					+ prop.getProperty("searchPost3");
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, boardNo);
			pstmt.setString(2, query);
			if(condition==3) pstmt.setString(3, query);
			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				Post post = new Post();
				post.setPostNo(rs.getInt("POST_NO"));
				post.setPostTitle(rs.getString("POST_TITLE"));
				post.setMemberName(rs.getString("MEMBER_NM"));
				post.setCreateDate(rs.getString("CREATE_DT"));
				
				postList.add(post);
			}
		
		} finally {
			close(rs);
			close(pstmt);
		}
		return postList;
	}

	/** 좋아요 업데이트
	 * @param conn
	 * @param boardNo
	 * @param post
	 * @return result
	 * @throws Exception
	 */
	public int updatePostLike(Connection conn, int boardNo, Post post) throws Exception{
		int result = 0;
		
		try {
			String sql = prop.getProperty("updatePostLike");
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, boardNo);
			pstmt.setInt(2, post.getPostNo());
			result=pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}
		
		return result;
	}

}




















