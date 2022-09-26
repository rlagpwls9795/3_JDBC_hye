package member.view;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import main.MainView;
import member.model.service.MemberService;
import member.model.vo.Member;

public class MemberView {
	
	private Scanner sc = new Scanner(System.in);
	private Member loginMember = null;
	private int input=-1;
	
	MemberService service= new MemberService();
	
	public void memberMenu(Member loginMember) {
		this.loginMember = loginMember;
		
		do {
			try {
				System.out.println("\n*** 회원 정보 페이지 ***\n");
				System.out.println("1. 나의 정보 조회");
				System.out.println("2. 비밀번호 변경");
				System.out.println("3. 회원 탈퇴");
				System.out.println("4. 회원 목록 조회");
				System.out.println("0. 메인 메뉴로 이동");
				
				System.out.print("\n메뉴 선택 >> ");
				input=sc.nextInt();
				sc.nextLine();
				
				switch(input) {
				case 1: myInfo(); break;
				case 2: updatePw(); break;
				case 3: deleteInfo(); break;
				case 4: selectAll(); break;
				case 0: System.out.println("\n[메인 메뉴로 이동합니다.]\n");break;
				default: System.out.println("\n<<메뉴에 작성된 번호만 입력하세요.>>\n");
				}
				
			} catch (InputMismatchException e) {
				System.out.println("\n<<입력 형식이 올바르지 않습니다.>>");
				sc.nextLine(); 
				e.printStackTrace();
			}
			
		} while(input!=0);
	}
	
	/**
	 * 나의 정보 조회
	 */
	private void myInfo() {
		System.out.println("\n<<나의 정보 조회>>\n");
		
		try {
			Member member = service.myInfo(loginMember);
			
			System.out.println("회원 번호 : "+member.getMemberNo());
			System.out.println("아이디 : "+member.getMemberId());
			System.out.println("이름 : "+member.getMemberName());
			System.out.print("성별 : ");
			if(member.getMemberGender().equals("M")) {
				System.out.println("남");
			} else {
				System.out.println("여");
			}
			System.out.println("가입일 : "+member.getEnrollDate());
			System.out.println("회원 등급 : "+member.getGradeName());
			
			System.out.println();
		} catch(Exception e) {
			System.out.println("\n<<정보 조회 중 예외 발생>>\n");
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 비밀번호 변경
	 */
	private void updatePw() {
		System.out.println("\n<<비밀번호 변경>>\n");
		
		try {
			System.out.print("현재 비밀번호 : ");
			String currentPw = sc.next();
			
			String newPw1=null;
			String newPw2=null;
			
			while(true) {
				System.out.print("새 비밀번호 : ");
				newPw1 = sc.next();
				System.out.print("새 비밀번호 확인: ");
				newPw2 = sc.next();
				
				if(newPw1.equals(newPw2)) {
					break;
				} else {
					System.out.println("\n새 비밀번호가 일치하지 않습니다. 다시 입력해주세요.\n");
				}
			}
			
			int result = service.updatePw(currentPw, newPw1, loginMember.getMemberNo()); 
										//현재비밀번호,  새비밀번호,  로그인회원번호
			
			if(result>0) {
				System.out.println("\n[비밀번호가 변경되었습니다.]\n");
			} else {
				System.out.println("\n[현재 비밀번호가 일치하지 않습니다.]\n");
			}
			
		} catch (Exception e) {
			System.out.println("\n<<비밀번호 변경 중 예외 발생>>\n");
			e.printStackTrace();
		}
	}
	
	/**
	 * 회원 탈퇴
	 */
	private void deleteInfo() {
		System.out.println("\n<<회원 탈퇴>>\n");
		
		try {
			System.out.print("비밀번호 입력 : ");
			String memberPw = sc.next();
			
			while(true) {
				System.out.print("정말 탈퇴하시겠습니까?(Y/N) : ");
				char ch=sc.next().toUpperCase().charAt(0);
				
				if(ch=='Y') {
					int result = service.deleteInfo(memberPw, loginMember.getMemberNo());
					
					if(result>0) {
						System.out.println("\n[탈퇴되었습니다.]\n");
						
						input=0; 
						
						MainView.loginMember=null; 
						
					} else {
						System.out.println("\n[현재 비밀번호가 일치하지 않습니다.]\n");
					}
					
					break;
					
				} else if (ch=='N'){
					System.out.println("\n[취소되었습니다.]\n");
					break;
				} else {
					System.out.println("\n[Y 또는 N만 입력해주세요.]\n");
				}
				
			}
		
		}catch (Exception e) {
			System.out.println("\n<<회원 탈퇴 중 예외 발생>>\n");
			e.printStackTrace();
		}
		
	}
	
	private void selectAll() {
		System.out.println("\n<<회원 목록>>\n");
		try {
			List<Member> memberList = service.selectAll();
			
			if(memberList.isEmpty()) {
				System.out.println("\n[조회 결과가 없습니다.]\n");
			} else {
				System.out.println("번호|  아이디  |  이름  |  성별  | 회원등급 ");
				for(Member m : memberList) {
					System.out.printf(" %d | %2s | %2s |  %2s   | %2s\n", 
							m.getMemberNo(), m.getMemberId(),
							m.getMemberName(),m.getMemberGender(),
							m.getGradeName());
				}
			}
			
		} catch (Exception e) {
			System.out.println("\n<<회원 목록 조회 중 예외 발생>>\n");
			e.printStackTrace();
		}
		
	}
	

}

