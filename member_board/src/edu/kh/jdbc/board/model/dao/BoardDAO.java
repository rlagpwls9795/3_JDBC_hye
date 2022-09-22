package edu.kh.jdbc.board.model.dao;

import static edu.kh.jdbc.common.JDBCTemplate.*;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import edu.kh.jdbc.board.model.vo.Board;

public class BoardDAO {
	
	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private Properties prop;
	
	public BoardDAO() {
		try {
			prop = new Properties();
			prop.loadFromXML(new FileInputStream("board-query.xml"));
			
		} catch(Exception e) {
			
		}
	}

	/** 게시글 목록 조회 DAO
	 * @param conn
	 * @return boardList
	 * @throws Exception
	 */
	public List<Board> selectAllBoard(Connection conn) throws Exception{
		
		List<Board> boardList = new ArrayList<>();
		
		try {
			stmt=conn.createStatement();
			String sql=prop.getProperty("selectAllBoard");
			rs=stmt.executeQuery(sql);
			while(rs.next()) {
				int boardNo = rs.getInt("BOARD_NO");
				String boardTitle=rs.getString("BOARD_TITLE");
				String memberName=rs.getString("MEMBER_NM");
				int readCount = rs.getInt("READ_COUNT");
				String createDate = rs.getString("CREATE_DT");
				int commentCount = rs.getInt("COMMENT_COUNT");
				
				Board board = new Board();
				board.setBoardNo(boardNo);
				board.setBoardTitle(boardTitle);
				board.setMemberName(memberName);
				board.setReadCount(readCount);
				board.setCreateDate(createDate);
				board.setCommentCount(commentCount);
				
				boardList.add(board);
			}
			
		} finally {
			close(rs);
			close(stmt);
		}
		return boardList;
	}

	/** 게시글 상세 조회 DAO
	 * @param conn
	 * @param boardNo
	 * @return board
	 * @throws Exception
	 */
	public Board selectBoard(Connection conn, int boardNo) throws Exception{
        
		Board board =null;
		
		try {
			String sql=prop.getProperty("selectBoard");
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, boardNo);
			
			rs=pstmt.executeQuery();
		
			if(rs.next()) {
				board=new Board(); //존재하면 board는 null이 아님
				
				board.setBoardNo(boardNo);
				board.setBoardTitle(rs.getString("BOARD_TITLE"));
				board.setBoardContent(rs.getString("BOARD_CONTENT"));
				board.setMemberNo(rs.getInt("MEMBER_NO"));
				board.setMemberName(rs.getString("MEMBER_NM"));
				board.setReadCount(rs.getInt("READ_COUNT"));
				board.setCreateDate(rs.getString("CREATE_DT"));
				
			}
			
		} finally {
			close(rs);
			close(pstmt);
		}
		
		return board;
	}

	/** 조회수 증가 DAO
	 * @param conn
	 * @param boardNo
	 * @return result
	 * @throws Exception
	 */
	public int increaseReadCount(Connection conn, int boardNo) throws Exception{
		int result=0;
		
		try {
			String sql=prop.getProperty("increaseReadCount");
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, boardNo);
			
			result=pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}
		
		return result;
	}

	/** 게시글 작성 DAO
	 * @param conn
	 * @param boardTitle
	 * @param boardContent
	 * @param memberNo
	 * @return result
	 * @throws Exception
	 */
	public int insertBoard(Connection conn, String boardTitle, String boardContent, int memberNo) throws Exception{
		int result=0;
		
		try {
			String sql = prop.getProperty("insertBoard");
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, boardTitle);
			pstmt.setString(2, boardContent);
			pstmt.setInt(3, memberNo);
			
			result=pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}
		return result;
	}

}
