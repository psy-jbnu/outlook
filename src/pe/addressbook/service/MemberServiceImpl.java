package pe.addressbook.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import pe.addressbook.dao.MemberDAO;
import pe.addressbook.dao.MemberDAOImpl;
import pe.addressbook.vo.MemberKind;
import pe.addressbook.vo.MemberVO;

public class MemberServiceImpl implements MemberService {
	MemberDAO dao = new MemberDAOImpl();
	String enter = "";
	@Override
	public void memberAdd(Scanner sc) {
		
		System.out.println("연락처를 추가합니다.");
		System.out.print("이름: ");
		enter = sc.nextLine();
		String name = sc.nextLine();
		System.out.print("전화번호: ");
		String tell = sc.nextLine();
		System.out.print("주소: ");
		String address = sc.nextLine();
		System.out.print("나이: ");
		String age = sc.nextLine();
		System.out.println("관계를 입력하시려면 4가지 번호중 하나를 입력해주십시오.");
		System.out.println("1. 친구 2. 가족 3. 직장 4. 기타");
		boolean isWrong = false;
		MemberKind kind = null;
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
		} while (isWrong);
		do {
			switch (choice) {
			case 1:
				kind = MemberKind.FRIENDS;
				break;
			case 2:
				kind = MemberKind.FAMILLIES;
				break;
			case 3:
				kind = MemberKind.OFFICE;
				break;
			case 4:
				kind = MemberKind.OTHERS;
				break;
			default:
				System.out.println("다시 입력해 주세요.");
				isWrong = true;
			}
		} while (isWrong);
		
		MemberVO member = dao.memberSearchTell(tell);

		if (member != null) {
			System.out.println("전화번호가 중복됩니다.");
			return;
		}
		
		member = new MemberVO(name, tell, address, age, kind);
		dao.memberAdd(member);
		System.out.println(member);
		System.out.println("성공적으로 저장하였습니다.");
		MemberWrite();
		
	}
	@Override
	public void memberList() {
		ArrayList<MemberVO> list = dao.memberList();
		if (list.isEmpty()) {
			System.out.println("주소록에 연락처가 존재하지 않습니다.");
			return;
		} else {
			for (int i = 0; i < list.size(); i++) {
			System.out.println((i+1) + "번");
			System.out.println(list.get(i));
			}
			return;
		}
	}

	@Override
	public ArrayList<MemberVO> memberSearch(Scanner sc) {
		System.out.println("연락처를 검색합니다.");
		System.out.println("무엇으로 검색하시겠습니까?");
		System.out.println("1. 이름으로 검색 2. 전화번호로 검색");
		boolean isWrong = false;
		String choice = "";
		enter = sc.nextLine();
		do {
			
			choice = sc.nextLine();
			isWrong = false;
			if (!(choice.equals("1")||choice.equals("2"))) {
				System.out.println("다시 입력해주세요.");
				isWrong = true;				
			}
			
		} while (isWrong);
	
		do {
			switch (choice) {
			case "1":
				System.out.println("찾으실 이름을 입력해주십시오.");
				String name = sc.nextLine();
				ArrayList <MemberVO> searchList = dao.listShow(name);				
				if (searchList.isEmpty()) {
					System.out.println("찾으시는 연락처가 없습니다.");
					return null;
				}
				
				else {
					for (int i = 0; i < searchList.size(); i++) {
						System.out.println(i+1);
						System.out.println(searchList.get(i));
					}
					return searchList;
				}
				
				
			case "2":
				System.out.println("찾으시는 전화번호를 입력해주십시오.");
				String tell = sc.nextLine();
				
				MemberVO member = dao.memberSearchTell(tell);
				
				if (member == null) {
					System.out.println("찾으시는 연락처가 없습니다.");
					return null;	
				}
				
				System.out.println("다음 연락처가 검색되었습니다.");
				System.out.println(1);
				System.out.println(member);
				ArrayList<MemberVO> serchList = new ArrayList<MemberVO>();
				serchList.add(member);
				return serchList;
				
				
			default:
				System.out.println("잘못된 값을 입력하셨습니다. 다시 시도해주십시오.");
				return null;
			}
		} while (true);
	}

	@Override
	public void memberDel(Scanner sc) {
	    ArrayList<MemberVO> searchList = memberSearch(sc);
	    ArrayList<MemberVO> delList = new ArrayList<MemberVO>();
	    if (searchList == null) {
	    	return;
	    }
		if (searchList.size() == 1) {
			boolean isEnd = false;
			System.out.println("이 회원을 지우시겠습니까? Y/N");
			do {
				switch (sc.nextLine()) {
				case "Y":
					delList = searchList;
					isEnd = false;
					break;
				case "y":
					delList = searchList;
					isEnd = false;
					break;
				case "N":
					System.out.println("처음으로 돌아갑니다.");
					return;
				case "n":
					System.out.println("처음으로 돌아갑니다. ");
					return;
				default:
					System.out.println("잘못된 값을 입력하셨습니다.");
					isEnd = true;
					break;	
				}
			} while (isEnd);
			dao.memberDel(delList);
			System.out.println("삭제가 완료되었습니다.");
			MemberWrite();
		} else {
			System.out.println(searchList.size() + "개의 연락처가 탐색되었습니다.");
			for (int i = 0; i < searchList.size(); i++) {
				System.out.println((i + 1) + "번 연락처를 지우시고 싶으신 경우 " + (i + 1) + "번");
			}
			
			System.out.println("리스트에 나온 모든 연락처를 지우시고 싶으신 경우 " + (searchList.size() + 1) + " 번을 눌러주세요." );
			System.out.println("처음으로 돌아가고 싶으신 경우 " + (searchList.size() + 2) + "번을 눌러주세요.");
			boolean isWrong = false;
			int choice = 0;
			do {
				try {
					choice = sc.nextInt();
					isWrong = false;
				} catch (InputMismatchException e) {
					
					System.out.println("다시 입력해주세요.");
					isWrong = true;
				}
			}while (isWrong);
			
			for (int i = 0; i < searchList.size(); i++) {
				if(choice == (1+i)) {
					MemberVO member = searchList.get(i);
					delList.add(member);
					dao.memberDel(delList);
					System.out.println("삭제가 완료되었습니다.");
					return;	
				}
				
			}
			
			if (choice == (searchList.size()+1)) {
				delList = searchList;
				dao.memberDel(delList);
				System.out.println("삭제가 완료되었습니다.");
				return;	
			}
			else if (choice == (searchList.size()+2)) {
				return;
			}
			else {
				System.out.println("잘못된 값을 입력하셨습니다.");
				return;
			}
		}
		
	}
	
	@Override
    public void memberListKind(Scanner sc) {
		ArrayList<String> list = null;
    	System.out.println("보기 원하시는 그룹을 선택해주십시오.");
    	System.out.println("1. 친구 2. 가족 3. 직장 4. 기타 5.종료");
    	boolean isContinue = false;
    	enter = sc.nextLine();
    	do {
			switch (sc.nextLine()) {
			case "1":
				list = dao.memberListKind(MemberKind.FRIENDS);
				break;
			case "2":
				list = dao.memberListKind(MemberKind.FAMILLIES);
				break;
			case "3":
				list = dao.memberListKind(MemberKind.OFFICE);
				break;
			case "4":
				list = dao.memberListKind(MemberKind.OTHERS);
				break;
			case "5":
				return;
			default:
				System.out.println("잘못된 값을 입력하셨습니다.");
				isContinue = true;
				break;
			}
		} while (isContinue);
    	for (String tell : list) {
			MemberVO member = new MemberVO();
			member = dao.memberSearchTell(tell);
			System.out.println(member);
		}
    	if (list.isEmpty()) {
			System.out.println("연락처가 없습니다.");
		}
	}
    @Override
    public void memberRevise(Scanner sc) {
    	ArrayList<MemberVO> searchList = new ArrayList<MemberVO>();
    	searchList = memberSearch(sc);
    	if (searchList == null) {
			return;
		}
    	else {
			System.out.println(searchList.size() + "개의 연락처가 탐색되었습니다.");
			for (int i = 0; i < searchList.size(); i++) {
				System.out.println((i + 1) + "번 연락처를 수정하고 싶으신 경우 " + (i + 1) + "번");
			}
			
			System.out.println("처음으로 돌아가고 싶으신 경우 " + (searchList.size() + 1) + "번을 눌러주세요.");
			boolean isWrong = false;
			do {
				String command = sc.nextLine();
				for (int i = 0; i < searchList.size(); i++) {
					int numbera = (1+i);
					String numa = Integer.toString(numbera);
					int number = (searchList.size() + 1);
					String num = Integer.toString(number);
					if(command.equals(numa)) {
						MemberVO oldMember = searchList.get(i);
						MemberReviseChoice (oldMember,sc);
						return;
					} 
					else if (command.equals(num)) {
						return;
					}
					else {
						System.out.println("다시 입력해주세요.");
						isWrong = true;
					}	
				}
			}while (isWrong);
    	}	
			
    	
    }
    private void MemberReviseChoice (MemberVO oldMember,Scanner sc) {
    	boolean isWrong = false;
    	MemberVO member = null;
		try {
			member = (MemberVO) oldMember.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		System.out.println("어떤 것을 수정하시겠습니까?");
		System.out.println("1. 이름 2. 전화번호 3. 주소 4. 관계 5. 나이 6. 처음으로 이동");
		do {
			isWrong = false;
			switch (sc.nextLine()) {
			case "1":
			    System.out.println("이름을 입력해주세요.");
			    String name = sc.nextLine();
			    member.setName(name);
				break;
			case "2":
			    System.out.println("전화번호를 입력해주세요.");
			    member.setTell(sc.nextLine());
				break;
			case "3":
			    System.out.println("주소를 입력해주세요.");
			    member.setAddress(sc.nextLine());
				break;
			case "4":
			    System.out.println("관계를 입력해주세요.");
			    System.out.println("1. 친구 2. 가족 3. 직장 4. 기타");
			    boolean isContinue = false;
			    MemberKind kind = null;
		    	do {
		    		isContinue = false;
					switch (sc.nextLine()) {
					case "1":
						kind = MemberKind.FRIENDS;
						break;
					case "2":
						kind = MemberKind.FAMILLIES;
						break;
					case "3":
						kind = MemberKind.OFFICE;
						break;
					case "4":
						kind = MemberKind.OTHERS;
						break;
					default:
						System.out.println("잘못된 값을 입력하셨습니다.");
						isContinue = true;
						break;
					}
				} while (isContinue);
			    member.setKind(kind);
				break;
			case "5":
			    System.out.println("나이를 입력해주세요.");
			    member.setAge(sc.nextLine());
				break;
			case "6":
			    System.out.println("처음으로 돌아갑니다.");
			    return;
			default:
				System.out.println("잘못된 값을 입력하셨습니다.");
				isWrong = true;
				break;
			}
		} while (isWrong);
		System.out.println("다음과 같이 수정이 완료되었습니다.");
		System.out.println(member);
		dao.memberRevise(oldMember, member);
		MemberWrite();
    }
    @Override
    public void MemberWrite() {
    	try {
			dao.memberWrite();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    @Override
    public void MemberRead() { 
    	try {
			dao.MemberRead();
		} catch (ClassNotFoundException e) {
			System.out.println("저장된 파일이 잘못된 파일이거나 버전과 맞지 않습니다.");
			Scanner sc = new Scanner(System.in);
			System.out.println("저장된 파일을 삭제하시겠습니까?");
			System.out.println("1. 예 2. 아니오");
			String command = sc.nextLine();
			do {
				if (sc.nextLine().equals("1")) {
					MemberWrite();
					sc.close();
					return;
				} else if (command.equals("2")) {
					System.out.println("지금 파일을 백업해주십시오. 프로그램 종료시 삭제될 위험이 있습니다.");
					sc.close();
					return;
				} else {
					System.out.println("다시 입력해주십시오.");
				}
			} while (true);
			
		} catch (IOException e) {
			if (e instanceof FileNotFoundException) {
				System.out.println("파일이 없습니다.");
				MemberWrite();
			return;	
			}
		}
    	
    }
}
