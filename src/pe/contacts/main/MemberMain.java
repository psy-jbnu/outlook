package pe.contacts.main;

import java.util.Scanner;

import pe.contacts.service.MemberService;
import pe.contacts.service.MemberServiceImpl;
/**
 * 프로그램을 실행하는 클래스
 * @author      Seungyeop Park 
 */
public class MemberMain {

	public static void main(String[] args) {
		MemberService memberService = new MemberServiceImpl();
		System.out.println("프로그램 구동 중");
		boolean isExit = false; 
		do {
			Scanner sc = new Scanner(System.in);
			System.out.println("==================================================================");
			System.out.println("연락처 관리 프로그램입니다.");
			System.out.println("연락처의 입력을 원하시면 1번");
			System.out.println("전체 연락처를 보고 싶으시면 2번");
			System.out.println("그룹별 보기는 3번");
			System.out.println("연락처를 검색하고 싶으시면 4번");
			System.out.println("연락처를 수정하고 싶으시면 5번");
			System.out.println("연락처를 삭제하고 싶으시면 6번");
			System.out.println("프로그램을 종료하고 싶으시면 7번을 눌러주세요");
			System.out.println("모든 데이터 삭제는 8번입니다.");
			System.out.println("==================================================================");
			int choice = 0;
			
			do {
				try {
					String invalue = sc.nextLine();
					choice = Integer.parseInt(invalue);			
				} catch (Exception e) {
					System.out.println("잘못된 값을 입력하셨습니다. 숫자를 입력하여 주십시오.");
				}
				if (choice <= 0) {
					System.out.println("잘못된 값을 입력하셨습니다. 0 또는 0보다 큰 정수를 입력해주십시오.");
				}else {
					break;
				}
			} while (true);
			switch (choice) {
			case 1:
				memberService.memberAdd(sc);
				System.out.println("==================================================================");
				break;
			case 2:
				memberService.memberList();
				System.out.println("==================================================================");
				break;
			case 3:
				memberService.memberListKind(sc);
				System.out.println("==================================================================");
				break;
			case 4:
				memberService.memberSearch(sc);
				System.out.println("==================================================================");
				break;
			case 5:
				memberService.memberRevise(sc);
				System.out.println("==================================================================");
				break;
			case 6:
				memberService.memberDel(sc);
				System.out.println("==================================================================");
				break;
			case 7:
				System.out.println("프로그램 종료");
				isExit = true;
				memberService.exit(sc);
				sc.close();
				break;
			case 8:
				System.out.println("연락처 삭제 중...");
				memberService.memberDefault();
				System.out.println("모든 연락처를 삭제하였습니다.");
				break;
			default:
				System.out.println("잘못 된 값입니다. 다시 입력해주십시오.");
				break;
			}
		} while (!isExit);	
		
		
		
	}

}
