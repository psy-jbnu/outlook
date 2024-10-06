package pe.addressbook.main;

import java.io.File;
import java.util.InputMismatchException;
import java.util.Scanner;

import pe.addressbook.service.MemberService;
import pe.addressbook.service.MemberServiceImpl;
/**
 * 프로그램을 실행하는 클래스
 * @author      Seungyeop Park 
 */
public class MemberMain {

	public static void main(String[] args) {
		MemberService memberService = new MemberServiceImpl();
		System.out.println("프로그램 구동 중");
		memberService.MemberRead();
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
			System.out.println("프로그램을 종료하고 싶으시면 7번을 눌러주세요. 모든 데이터 삭제 후 종료는 8번입니다.");
			System.out.println("==================================================================");
			boolean isWrong = false;
			int choice = 0;
			do {
				try {
					choice = sc.nextInt();
					isWrong = false;
				} catch (InputMismatchException e) {
					System.out.println("다시 입력해주세요.");
					sc = new Scanner(System.in);
					isWrong = true;
				}
			}while (isWrong);
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
				sc.close();
				memberService.MemberWrite();
				break;
			case 8:
				System.out.println("연락처 삭제 중...");
				File members = new File("save/members.ser");
				File names = new File("save/names.ser");
				File kinds = new File("save/kinds");
				if(members.delete() && names.delete() && kinds.delete()) {
					System.out.println("모든 연락처를 삭제하였습니다.");
				}
				isExit = true;
				sc.close();
				break;
			default:
				System.out.println("키를 잘못 누르셨습니다. 다시 시도해주십시오.");
				break;
			}
		} while (!isExit);	
		
		
		
	}

}
