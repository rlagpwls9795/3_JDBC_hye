package edu.kh.jdbc.board.view;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import edu.kh.jdbc.board.model.service.BoardService;
import edu.kh.jdbc.board.model.service.CommentService;
import edu.kh.jdbc.board.model.vo.Board;
import edu.kh.jdbc.board.model.vo.Comment;
import edu.kh.jdbc.main.view.MainView;

public class BoardView {
	
	private Scanner sc = new Scanner(System.in);
	private BoardService bService = new BoardService();
	private CommentService cService = new CommentService();
	
	/**
	 * 게시판 기능 메뉴 화면
	 */
	public void boardMenu() {
		int input=-1;
		
		do {
			try {
				System.out.println("\n***** 게시판 기능 *****\n");
				System.out.println("1. 게시글 목록 조회");
				System.out.println("2. 게시글 상세 조회(+ 댓글 기능)");
				System.out.println("3. 게시글 작성");
				System.out.println("4. 게시글 검색");
				System.out.println("0. 로그인 메뉴로 이동");
				
				System.out.print("\n메뉴 선택 : ");
				input=sc.nextInt();
				sc.nextLine();
				
				System.out.println();
				
				switch(input) {
				case 1: selectAllBoard(); break; //게시글 목록 조회
				case 2: selectBoard(); break; //게시글 상세 조회
				case 3: insertBoard(); break;
				case 4: break;
				case 0: System.out.println("로그인 메뉴로 이동합니다."); break;
				default : System.out.println("메뉴에 작성된 번호만 입력해주세요.");
				}
				
			} catch (InputMismatchException e) {
				System.out.println("\n<<입력 형식이 올바르지 않습니다.>>\n");
				sc.nextLine();
				e.printStackTrace();
			}
			
		}while(input!=0);
	}
	
	/**
	 * 게시글 목록 조회 메서드
	 */
	public void selectAllBoard() {
		System.out.println("\n[게시글 목록 조회]\n");
		
		try {
			List<Board> boardList = bService.selectAllBoard();
			//-> DAO에서 new ArrayList<>(); 구문으로 인해 반환되는 조회 결과는 null이 될 수 없다
			
			if(boardList.isEmpty()) {
				System.out.println("게시글이 존재하지 않습니다.");
			} else {
				
				for(Board board : boardList) {
					System.out.printf(" %d | %s[%d] |  %s  |   %s   | %d\n",
							board.getBoardNo(), board.getBoardTitle(),board.getCommentCount(),
							board.getMemberName(),board.getCreateDate(),board.getReadCount());
				}
			}
			
			
		} catch (Exception e) {
			System.out.println("\n<<게시글 목록 조회 중 예외 발생>>\n");
			e.printStackTrace();
		}
	}
	
	/**
	 * 게시글 상세 조회 메서드
	 */
	public void selectBoard() {
		System.out.println("\n[게시글 상세 조회]\n");
		
		try {
			System.out.print("게시글 번호 입력 : ");
			int boardNo = sc.nextInt();
			sc.nextLine();
			System.out.println();
			
			Board board = bService.selectBoard(boardNo,MainView.loginMember.getMemberNo());
			// 게시글 번호, 로그인한 회원의 회원번호(자신의 글 조회수 증가X)
			
			if(board == null) {
				System.out.println("해당 번호의 게시글이 존재하지 않습니다.");
			} else {
				System.out.println("-----------------------------------------------------------");
				System.out.printf("%d | 제목 : %s\n",board.getBoardNo(),board.getBoardTitle());
				System.out.println("내용 : "+board.getBoardContent());
				System.out.println();
				System.out.printf("작성자 : %s | 작성일 : %s | 조회수 : %d \n",board.getMemberName(),
						board.getCreateDate(), board.getReadCount());
				System.out.println("-----------------------------------------------------------");
				
				if(!board.getCommnetList().isEmpty()) {
					System.out.println("\n[댓글]");
					for(Comment comment : board.getCommnetList() ) {
						System.out.printf("%d | %s | %s | %s\n",comment.getCommentNo(),
								comment.getCommentContent(), comment.getMemberName(),
								comment.getCreateDate());
					}
				} else {
					System.out.println("\n해당 게시글에 댓글이 없습니다.");
				}
				System.out.println();
				
			}
			
			
			
		} catch (Exception e) {
			System.out.println("\n<<게시글 상세 조회 중 예외 발생>>\n");
			e.printStackTrace();
		}
	}
	
	/**
	 * 게시글 작성 메서드
	 */
	public void insertBoard() {
		System.out.println("\n[게시글 작성]\n");
		
		try {
			System.out.print("제목 : ");
			String boardTitle = sc.nextLine();
			System.out.print("내용 : ");
			String boardContent = sc.nextLine();
			System.out.println();
			
			int result = bService.insertBoard(boardTitle, boardContent,MainView.loginMember.getMemberNo());
			
			if(result>0) {
				System.out.println("작성이 완료되었습니다.\n");
				
				
				
			} else { 
				System.out.println("작성 실패");
			}
			
		} catch (Exception e) {
			System.out.println("\n<<게시글 작성 중 예외 발생>>\n");
			e.printStackTrace();
		}
	}

}

