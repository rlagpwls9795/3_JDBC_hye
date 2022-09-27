package post.view;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import member.model.vo.Member;
import post.model.service.PostService;
import post.model.vo.Post;

public class PostView {
	private Scanner sc = new Scanner(System.in);
	private int input=-1;
	private PostService pService = new PostService();
	
	public void subPostMenu(int boardNo, Member loginMember) {
		do {
			try {
				System.out.printf("\n*** %d번 게시판 메뉴 ***\n",boardNo);
				System.out.println();
				System.out.println("1. 게시글 목록 조회");
				System.out.println("2. 게시글 상세 조회"); //+ 수정, 삭제 (내 게시글인 경우)
				System.out.println("3. 게시글 작성");
				System.out.println("4. 게시글 검색");
				System.out.println("0. 이전 메뉴로 돌아가기");
				
				System.out.print("\n메뉴 선택 : ");
				input=sc.nextInt();
				sc.nextLine();
				
				switch(input) {
				case 1: selectAllPost(boardNo); break;
				case 2: 
					selectPost(boardNo, loginMember);
					input=-1;
					break;
				case 3: insertPost(boardNo, loginMember); break;
				case 4: searchPost(boardNo); break;
				case 0: System.out.println("\n[게시판 메뉴로 이동합니다.]\n"); break;
				default: System.out.println("\n<<메뉴에 작성된 번호만 입력하세요.>>\n");
				}
				
			} catch(InputMismatchException e) {
				System.out.println("\n<<입력 형식이 올바르지 않습니다.>>\n");
				sc.nextLine();
				e.printStackTrace();
			}
		} while(input!=0);
	}

	/** 게시글 목록
	 * @param boardNo
	 */
	private void selectAllPost(int boardNo) {
		System.out.printf("\n<<%d번 게시판 게시글 목록>>\n", boardNo);
		
		try {
			List<Post> postList	 = pService.selectAllPost(boardNo);
			
			if(postList.isEmpty()) {
				System.out.println("\n[게시글이 존재하지 않습니다.]\n");
			} else {
				System.out.println("\n번호 |      제목       | 작성자 |  작성일 | 좋아요 수");
				System.out.println("----------------------------------------------");
				for(Post p : postList) {
					System.out.printf("%d | %s | %s | %s | %d \n",
							p.getPostNo(), p.getPostTitle(),
							p.getMemberName(), p.getCreateDate(),p.getPostLike());
				}
			}
		
		} catch (Exception e) {
			System.out.println("\n<<게시글 목록 조회 중 예외 발생>>\n");
			e.printStackTrace();
		}
		
	}

	/** 게시글 상세 조회
	 * @param boardNo
	 * @param loginMember
	 */
	private void selectPost(int boardNo, Member loginMember) {
		int postNo=0;
		try {
			System.out.println("\n<<게시글 상세 조회>>\n");
			System.out.print("게시글 번호 입력 : ");
			postNo = sc.nextInt();
			sc.nextLine();
			
			Post post = pService.selectPost(boardNo,postNo,loginMember);
			if(post==null) {
				System.out.println("\n[해당 번호의 게시글이 존재하지 않습니다.]\n");
			} else {
				System.out.println("----------------------------------------");
				System.out.printf("%d | 제목 : %s \n",post.getPostNo(), post.getPostTitle());
				System.out.println("----------------------------------------");
				System.out.println(post.getPostContent());
				System.out.println();
				System.out.printf("작성자 : %s | 작성일 : %s | 좋아요 : %s \n",post.getMemberName(),post.getCreateDate(), post.getPostLike());
				System.out.println("----------------------------------------");
				System.out.println();
				
				if(post.getMemberNo()==loginMember.getMemberNo()) {
					System.out.println("1) 게시글 수정 ");
					System.out.println("2) 게시글 삭제 ");
					System.out.println("0) 이전 메뉴로 돌아가기");
					
					System.out.print("\n메뉴 번호 입력 : ");
					input = sc.nextInt();
					sc.nextLine();
					
					switch(input) {
					case 1: updatePost(boardNo,postNo); break;
					case 2: deletePost(boardNo,postNo); break;
					case 0: System.out.println("\n[이전 메뉴로 돌아갑니다.]\n"); break;
					default: System.out.println("\n[메뉴에 작성된 번호만 입력해주세요.]\n");
					}
				} else {
					System.out.println("1) 좋아요 누르기");
					System.out.println("0) 이전 메뉴로 돌아가기");
					
					System.out.print("\n메뉴 번호 입력 : ");
					input = sc.nextInt();
					sc.nextLine();
					
					switch(input) {
					case 1: updatePostLike(boardNo,post); break;
					case 0: System.out.println("\n[이전 메뉴로 돌아갑니다.]\n"); break;
					default: System.out.println("\n[메뉴에 작성된 번호만 입력해주세요.]\n");
					}
					
				}
			}
			
		} catch (Exception e) {
			System.out.println("\n<<게시글 상세 조회 중 예외 발생>>\n");
			e.printStackTrace();
		}
		
	}
	

	/** 내용 입력 메서드
	 * @return content
	 */
	private String inputContent() {
		String content="";
		String input = null;
		
		System.out.println("입력 종료 시 ($exit) 입력");
		while(true) {
			input=sc.nextLine();
			if(input.equals("$exit")) break;
			content += input+"\n"; //입력된 내용을 content에 누적
		}	
		return content;
	}

	/** 게시글 수정
	 * @param boardNo
	 * @param postNo
	 */
	private void updatePost(int boardNo, int postNo) {
		try {
			System.out.println("\n<<게시글 수정>>\n");
			
			System.out.print("수정할 제목 : ");
			String postTitle=sc.nextLine();
			
			System.out.println("수정할 내용 : ");
			String postContent=inputContent();
			
			//수정된 제목/내용 + 게시글 번호를 한 번에 전달하기 위한 Post 객체 생성
			Post post = new Post();
			post.setPostNo(postNo);
			post.setPostTitle(postTitle);
			post.setPostContent(postContent);
			post.setBoardNo(boardNo);
			
			int result = pService.updatePost(post);
			
			if(result>0) System.out.println("\n[게시글 수정 성공]\n");
			else System.out.println("\n[게시글 수정 실패]\n");
			
			
		} catch (Exception e) {
			System.out.println("\n<<게시글 수정 중 예외 발생>>\n");
			e.printStackTrace();
		}
		
	}
	
	/** 게시글 삭제
	 * @param boardNo
	 * @param postNo
	 */
	private void deletePost(int boardNo, int postNo) {
		try {
			System.out.println("\n<<게시글 삭제>>\n");
			
			System.out.print("정말 삭제하시겠습니까? (Y/N) : ");
			char input=sc.next().toUpperCase().charAt(0);
			
			if(input=='Y') {
				int result = pService.deletePost(boardNo,postNo);
				
				if(result>0) System.out.println("\n[게시글 삭제 성공]\n");
				else System.out.println("\n[게시글 삭제 실패]\n");
				
			} else {
				System.out.println("\n[삭제 취소]\n");
			}
			
		} catch (Exception e) {
			System.out.println("\n<<게시글 수정 중 예외 발생>>\n");
			e.printStackTrace();
		}
		
	}

	/** 게시글 작성
	 * @param boardNo
	 * @param loginMember
	 */
	private void insertPost(int boardNo, Member loginMember) {
		try {
			System.out.println("\n<<게시글 작성>>\n");
			
			System.out.print("제목 : ");
			String postTitle=sc.nextLine();
			System.out.println("내용 : ");
			String postContent=inputContent();
			System.out.println();
			
			Post post = new Post();
			post.setPostTitle(postTitle);
			post.setPostContent(postContent);
			post.setBoardNo(boardNo);
			post.setMemberNo(loginMember.getMemberNo());
			
			int result = pService.insertPost(post);
			
			if(result>0) {
				System.out.println("\n[게시글 등록이 완료되었습니다.]\n");
				int PostCntResult = pService.increasePostCnt(loginMember);
				if(PostCntResult>0) {
					updateGrade(loginMember);
				} 
				Post p = pService.selectPost(boardNo, result, loginMember);
				if(p==null) {
					System.out.println("\n[해당 번호의 게시글이 존재하지 않습니다.]\n");
				} else {
					System.out.println("----------------------------------------");
					System.out.printf("%d | 제목 : %s \n",p.getPostNo(), p.getPostTitle());
					System.out.println("----------------------------------------");
					System.out.println(post.getPostContent());
					System.out.println();
					System.out.printf("작성자 : %s | 작성일 : %s \n",p.getMemberName(),p.getCreateDate());
					System.out.println("----------------------------------------");
					System.out.println();
				}
				
				
				
			} else {
				System.out.println("\n[게시글 등록 실패]\n");
			}
			
		} catch (Exception e) {
			System.out.println("\n<<게시글 작성 중 예외 발생>>\n");
			e.printStackTrace();
		}
		
	}
	
	/** 등급 업데이트
	 * @param loginMember
	 */
	private void updateGrade(Member loginMember) {
		int result=0;
		try {
			if(loginMember.getPostCnt()%2==0 && loginMember.getPostCnt()<=10) {
				result = pService.updateGrade(loginMember);
				if(result==0) {
					System.out.println("\n<<등급 업데이트 실패>>\n");
				}
			}
		} catch (Exception e) {
			System.out.println("\n<<등급 업데이트 중 예외 발생>>\n");
			e.printStackTrace();
		}
	}

	/** 게시글 검색
	 * @param boardNo
	 */
	private void searchPost(int boardNo) {
		System.out.println("\n<<게시글 검색>>\n");
		
		try {
			System.out.println("1) 제목");
			System.out.println("2) 내용");
			System.out.println("3) 제목 + 내용");
			System.out.println("4) 작성자");
			System.out.print("\n검색 조건 선택 : ");
			int condition = sc.nextInt();
			sc.nextLine();
			
			if(condition>=1 && condition<=4) {
				System.out.print("검색어 입력 : ");
				String query = sc.nextLine();
				
				List<Post> postList = pService.searchPost(boardNo, condition, query);
				
				if(postList.isEmpty()) {
					System.out.println("\n[검색 결과가 없습니다.]\n");
				} else {
					for(Post p : postList) {
						System.out.printf("%d | %s | %s | %s \n",
								p.getPostNo(), p.getPostTitle(),
								p.getMemberName(), p.getCreateDate());
					}
				}
			}
			
		} catch (Exception e) {
			System.out.println("\n<<게시글 검색 중 예외 발생>>\n");
			e.printStackTrace();
		}
		
	}

	/** 좋아요 업데이트
	 * @param boardNo
	 * @param post
	 */
	private void updatePostLike(int boardNo, Post post) {
		int result=0;
		try {
			result=pService.updatePostLike(boardNo,post);
			if(result>0) {
				
				System.out.println("----------------------------------------");
				System.out.printf("%d | 제목 : %s \n",post.getPostNo(), post.getPostTitle());
				System.out.println("----------------------------------------");
				System.out.println(post.getPostContent());
				System.out.println();
				System.out.printf("작성자 : %s | 작성일 : %s | 좋아요 : %s \n",post.getMemberName(),post.getCreateDate(), post.getPostLike());
				System.out.println("----------------------------------------");
				System.out.println();
				
			} else {
				System.out.println("\n[실행 실패]\n");
			}
			
		} catch (Exception e) {
			System.out.println("\n<<예외 발생>>\n");
			e.printStackTrace();
		}
		
	}

}
