package board.view;

import java.util.Scanner;

import board.model.service.BoardService;
import member.model.vo.Member;

public class BoardView {
	private Scanner sc = new Scanner(System.in);
	private Member loginMember = null;
	private int input=-1;
	
	private BoardService service = new BoardService();
	
	public void boardMenu(Member loginMember) {
		
		this.loginMember=loginMember;
		
		System.out.println("\n*** 게시판 메뉴 ***\n");
		
		do {
			try {
				System.out.println("1. 1번 게시판");
				System.out.println("2. 2번 게시판");
				System.out.println("3. 3번 게시판");
				System.out.println("4. 4번 게시판");
				System.out.println("5. 5번 게시판");
				System.out.println("6. 6번 게시판");
				System.out.println("0. 메인 메뉴로 돌아가기");
				
				System.out.print("\n메뉴 선택 : ");
				input=sc.nextInt();
				sc.nextLine();
				
				switch(input) {
				case 1: subBoardMenu(input, loginMember); break;
				case 2: 
					if(loginMember.getGradeNo()<20) {
						System.out.println("\n[해당 등급은 접근할 수 없습니다.]\n");
					} else {
						subBoardMenu(input, loginMember);
						input=-1;
					}
					break;
				case 3: 
					if(loginMember.getGradeNo()<30) {
						System.out.println("\n[해당 등급은 접근할 수 없습니다.]\n");
					} else {
						subBoardMenu(input, loginMember);
						input=-1;
					}
					break;
				case 4: 
					if(loginMember.getGradeNo()<40) {
					System.out.println("\n[해당 등급은 접근할 수 없습니다.]\n");
					} else {
						subBoardMenu(input, loginMember);
						input=-1;
					}
					break;
				case 5: 
					if(loginMember.getGradeNo()<50) {
						System.out.println("\n[해당 등급은 접근할 수 없습니다.]\n");
					} else {
						subBoardMenu(input, loginMember);
						input=-1;
					}
					break;
				case 6:
					if(loginMember.getGradeNo()<60) {
						System.out.println("\n[해당 등급은 접근할 수 없습니다.]\n");
					} else {
						subBoardMenu(input, loginMember);
						input=-1;
					}
					break;
				case 0: System.out.println("\n[메인 메뉴로 이동합니다.]\n"); break;
				default: System.out.println("\n<<메뉴에 작성된 번호만 입력하세요.>>\n");
				}
				
				
			} catch(Exception e) {
				e.printStackTrace();
			}
		} while(input!=0);
	}
	
	private void subBoardMenu(int boardNo, Member loginMember) {
		do {
			try {
				System.out.printf("\n*** %d번 게시판 메뉴 ***\n",boardNo);
				System.out.println();
				System.out.println("1. 게시글 목록 조회");
				System.out.println("2. 게시글 상세 조회");
				System.out.println("3. 게시글 작성");
				System.out.println("4. 게시글 검색");
				System.out.println("0. 이전 메뉴로 돌아가기");
				
				
				
			} catch(Exception e) {
				e.printStackTrace();
			}
		} while(input!=0);
	}

}



















