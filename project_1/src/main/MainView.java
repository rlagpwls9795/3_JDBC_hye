package main;

import java.util.InputMismatchException;
import java.util.Scanner;

import board.view.BoardView;
import member.model.vo.Member;
import member.view.MemberView;

public class MainView {
	
	private Scanner sc = new Scanner(System.in);
	public static Member loginMember =null;
	private MainService service = new MainService();
	private MemberView  memberView = new MemberView();
	private BoardView boardView = new BoardView();
	
	public void mainMenu() {
		int input = -1;
		
		do {
			try {
				if(loginMember==null) {
					System.out.println("\n***** 카페 메인 메뉴 *****\n");
					System.out.println("1. 로그인");
					System.out.println("2. 회원 가입");
					System.out.println("0. 프로그램 종료");
					
					System.out.print("\n메뉴 선택 >> ");
					input=sc.nextInt();
					sc.nextLine();
					
					switch(input) {
					case 1: logIn(); break;
					case 2: signUp(); break;
					case 0: System.out.println("\n<<프로그램 종료>>\n"); break;
					default: System.out.println("\n<<메뉴에 작성된 번호만 입력하세요.>>\n");
					}
				} else {
					System.out.println("\n*** 카페 메뉴 ***\n");
					System.out.println("1. 게시판 메뉴");
					System.out.println("2. 회원 정보 페이지"); 
					System.out.println("3. 로그아웃");
					System.out.println("0. 프로그램 종료");
					
					System.out.print("\n메뉴 선택 >> ");
					input=sc.nextInt();
					sc.nextLine();
					
					switch(input) {
					case 1: boardView.boardMenu(loginMember); break;
					case 2: memberView.memberMenu(loginMember); break;
					case 3: 
						loginMember=null;
						System.out.println("\n[로그아웃 되었습니다.]\n");
						break;
					case 0: System.out.println("\n<<프로그램 종료>>\n"); break;
					default: System.out.println("\n<<메뉴에 작성된 번호만 입력하세요.>>\n");
					}
					
				}
				
			} catch(InputMismatchException e) {
				System.out.println("\n<<입력 형식이 올바르지 않습니다.>>");
				sc.nextLine();
				e.printStackTrace();
			}
			
		} while(input !=0);
		
	}

	/**
	 * 로그인
	 */
	private void logIn() {
		System.out.println("\n<<로그인>>\n");
		System.out.print("아이디 입력 : ");
		String memberId=sc.next();
		System.out.print("비밀번호 입력 : ");
		String memberPw=sc.next();
				
		try {	
			loginMember=service.logIn(memberId, memberPw);
			
			System.out.println();
			if(loginMember!=null) { 
				System.out.printf("\n [ %s님 환영합니다! ] \n\n",loginMember.getMemberName());
			} else { 
				System.out.println("\n[아이디 또는 비밀번호가 일치하지 않습니다.]\n");
			}
			System.out.println();
			
		} catch(Exception e) {
			System.out.println("\n<<로그인 중 예외 발생>>");
			e.printStackTrace();
		}
		
	}

	/**
	 * 회원가입
	 */
	private void signUp() {
		System.out.println("\n<<회원가입>>\n");
		
		String memberId = null;
		String memberPw1 = null;
		String memberPw2 = null;
		String memberName = null;
		String memberGender = null;
		
		try {
			while(true) {
				System.out.print("아이디 : ");
				memberId = sc.next();
				
				int result = service.idDupCheck(memberId);
				
				if(result==0) {
					System.out.println("\n[사용 가능한 아이디 입니다.]\n");
					break;
				} else {
					System.out.println("\n[이미 사용 중인 아이디 입니다.]\n");
				}
			}
			System.out.println();
			
			while(true) {
				System.out.print("비밀번호 : ");
				memberPw1 = sc.next();
				System.out.print("비밀번호 확인 : ");
				memberPw2 = sc.next();
				
				if(memberPw1.equals(memberPw2)) {
					System.out.println("\n[일치합니다.]\n");
					break;
				} else {
					System.out.println("\n[비밀번호가 일치하지 않습니다. 다시 입력해주세요.]\n");
				}
			}
			System.out.println();
			
			System.out.print("이름 : ");
			memberName = sc.next();
			System.out.println();
			
			while(true) {
				System.out.print("성별 (M/F) : ");
				memberGender = sc.next().toUpperCase();
				
				if(memberGender.equals("M")|| memberGender.equals("F")) {
					break;
				} else {
					System.out.println("\n[M 또는 F만 입력해주세요.]\n");
				}
			}
			
			Member member = new Member();
			member.setMemberId(memberId);
			member.setMemberPw(memberPw2);
			member.setMemberName(memberName);
			member.setMemberGender(memberGender);
			
			int result=service.signUp(member);
			
			if(result>0) {
				System.out.println("\n<<회원 가입 성공>>\n");
			} else {
				System.out.println("\n<<회원 가입 실패>>\n");
			}
			
		} catch(Exception e) {
			System.out.println("\n<<회원가입 중 예외 발생>>\n");
			e.printStackTrace();
		}
		
	}
	
}
