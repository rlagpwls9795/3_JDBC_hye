package post.view;

import java.util.InputMismatchException;
import java.util.Scanner;

import main.view.MainView;

public class BoardView {
	private Scanner sc = new Scanner(System.in);
	public static int input=-1;
	private PostView postView = new PostView();
	
	public void boardMenu() {
		
		System.out.println("\n*** 게시판 메뉴 ***\n");
		
		do {
			try {
				System.out.println("1. 1번 게시판 (모든 등급 접근 가능)");
				System.out.println("2. 2번 게시판 (새싹부터  접근 가능)");
				System.out.println("3. 3번 게시판 (잎새부터  접근 가능)");
				System.out.println("4. 4번 게시판 (가지부터  접근 가능)");
				System.out.println("5. 5번 게시판 (열매부터  접근 가능)");
				System.out.println("6. 6번 게시판 (나무부터  접근 가능)");
				System.out.println("0. 메인 메뉴로 돌아가기");
				
				System.out.print("\n게시판 선택 : ");
				input=sc.nextInt();
				sc.nextLine();
				
				switch(input) {
				case 1: postView.subPostMenu(input, MainView.loginMember); break;
				case 2: 
					if(MainView.loginMember.getGradeNo()<20) {
						System.out.println("\n[해당 등급은 접근할 수 없습니다.]\n");
					} else {
						postView.subPostMenu(input, MainView.loginMember);
						input=-1;
					}
					break;
				case 3: 
					if(MainView.loginMember.getGradeNo()<30) {
						System.out.println("\n[해당 등급은 접근할 수 없습니다.]\n");
					} else {
						postView.subPostMenu(input, MainView.loginMember);
						input=-1;
					}
					break;
				case 4: 
					if(MainView.loginMember.getGradeNo()<40) {
					System.out.println("\n[해당 등급은 접근할 수 없습니다.]\n");
					} else {
						postView.subPostMenu(input, MainView.loginMember);
						input=-1;
					}
					break;
				case 5: 
					if(MainView.loginMember.getGradeNo()<50) {
						System.out.println("\n[해당 등급은 접근할 수 없습니다.]\n");
					} else {
						postView.subPostMenu(input, MainView.loginMember);
						input=-1;
					}
					break;
				case 6:
					if(MainView.loginMember.getGradeNo()<60) {
						System.out.println("\n[해당 등급은 접근할 수 없습니다.]\n");
					} else {
						postView.subPostMenu(input, MainView.loginMember);
						input=-1;
					}
					break;
				case 0: System.out.println("\n[메인 메뉴로 이동합니다.]\n"); break;
				default: System.out.println("\n<<메뉴에 작성된 번호만 입력하세요.>>\n");
				}
				
				
			} catch(InputMismatchException e) {
				System.out.println("\n<<입력 형식이 올바르지 않습니다.>>\n");
				sc.nextLine();
				e.printStackTrace();
			}
		} while(input!=0);
	}
	
}