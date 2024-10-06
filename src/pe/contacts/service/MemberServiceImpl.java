package pe.contacts.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import pe.contacts.connect.ManageConn;
import pe.contacts.dao.MemberDAO;
import pe.contacts.dao.MemberDAOImpl;
import pe.contacts.vo.MemberKind;
import pe.contacts.vo.MemberVO;
/**
 * 입력값을 받아 작업한 다음 MemberDAOImpl에 넘겨줌.
 * @author      Seungyeop Park 
 */
public class MemberServiceImpl implements MemberService {
	MemberDAO dao = new MemberDAOImpl();
		
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
				case "y":
					delList = searchList;
					isEnd = false;
					break;
				case "N":
				case "n":
					System.out.println("처음으로 돌아갑니다. ");
					return;
				default:
					System.out.println("잘못된 값을 입력하셨습니다.");
					isEnd = true;
					break;	
				}
			} while (isEnd);
			try {
				dao.memberDel(delList);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("삭제가 완료되었습니다.");
		} else {
			System.out.println(searchList.size() + "개의 연락처가 탐색되었습니다.");
			for (int i = 0; i < searchList.size(); i++) {
				System.out.println((i + 1) + "번 연락처를 지우시고 싶으신 경우 " + (i + 1) + "번");
			}
			
			System.out.println("리스트에 나온 모든 연락처를 지우시고 싶으신 경우 " + (searchList.size() + 1) + " 번을 눌러주세요." );
			System.out.println("처음으로 돌아가고 싶으신 경우 0번을 눌러주세요.");
			delList = chooseMember(sc, searchList);
			if (delList == null) {
				System.out.println("처음으로 돌아갑니다.");
			} else {
				try {
					dao.memberDel(delList);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				System.out.println("삭제 완료");
			}
		}
		
	}
	
	@Override
    public void memberListKind(Scanner sc) {
		ArrayList<String> list = null;
    	System.out.println("보기 원하시는 그룹을 선택해주십시오.");
    	MemberKind kind = kindChoice(sc);
    	try {
			list = dao.memberListKind(kind);
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	if (list.isEmpty()) {
    		System.out.println("연락처가 없습니다.");
    		return;
    	}
    	for (String tell : list) {
			MemberVO member = new MemberVO();
			try {
				member = dao.memberSearchTell(tell);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println(member);
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
			
			System.out.println("처음으로 돌아가고 싶으신 경우 0 번을 눌러주세요.");
            ArrayList<MemberVO> list = chooseMember(sc, searchList);
            do {
            	if (list == null) {
                	System.out.println("처음으로 돌아갑니다.");
                	return;
    			} else if (list.size() > 1) {
    				System.out.println("잘못된 값을 입력하셨습니다.");
    				list = chooseMember(sc, searchList);
    			} else {
    				MemberVO oldMember = list.get(0);
    				MemberReviseChoice(oldMember, sc);
    				return;
    			}
			} while (true);
            
    	}	
			
    	
    }
	
	@Override
	public void memberAdd(Scanner sc) {
		System.out.println("연락처를 추가합니다.");
		MemberVO member = makeMember(sc);
        if (member != null) {
        	try {
				dao.memberAdd(member);
			} catch (SQLException e) {
				e.printStackTrace();
			}
        	System.out.println("성공적으로 저장하였습니다.");
		}
        return;
	}
	
	@Override
	public void memberList() {
		ArrayList<MemberVO> list = null;
		try {
			list = dao.memberList();
		} catch (SQLException e) {
			e.printStackTrace();
		}
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
				return MemberSearchName (sc);
			case "2":
				return MemberSearchTell (sc);
			default:
				System.out.println("잘못된 값을 입력하셨습니다. 다시 시도해주십시오.");
				return null;
			}
		} while (true);
	}

	private ArrayList<MemberVO> MemberSearchName (Scanner sc) {
		System.out.println("찾으실 이름을 입력해주십시오.");
		String name = sc.nextLine();
		ArrayList<MemberVO> searchList = null;
		try {
			searchList = dao.listShow(name);
		} catch (SQLException e) {
			e.printStackTrace();
		}				
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
	}
	
	private void MemberReviseChoice (MemberVO oldMember,Scanner sc) {
    	MemberVO member = null;
		try {
			member = (MemberVO) oldMember.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		System.out.println("수정하실 연락처를 입력해주세요.");
		member = makeMember(sc);
		if (member == null) {
			return;
		}
		try {
			dao.memberRevise(oldMember, member);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("다음과 같이 수정이 완료되었습니다.");
		System.out.println(member);
    }
	
	private ArrayList<MemberVO> MemberSearchTell (Scanner sc) {
			System.out.println("찾으시는 전화번호를 입력해주십시오.");
			String tell = sc.nextLine();
			
			MemberVO member = null;
			try {
				member = dao.memberSearchTell(tell);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
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
		}
    
	private ArrayList<MemberVO> chooseMember (Scanner sc, ArrayList<MemberVO> searchList) {
			ArrayList<MemberVO> delList=  new ArrayList<MemberVO>();
			boolean isWrong = false;
			do {
				isWrong = false;
				String command = sc.nextLine();
				int choice = -1;
				try {
					choice = Integer.parseInt(command); 
				} catch (Exception e) {
					System.out.println("잘못된 값을 입력하셨습니다. 숫자를 입력하여 주십시오.");
					isWrong = true;
				}
				if (choice > searchList.size()||choice < 0 ) {
					System.out.println("올바른 숫자를 입력하여주십시오.");
					isWrong = true;
				}
				
				if ((choice + 1) == searchList.size()) {
					delList = searchList;
				}else if (choice == 0) {
					delList = null;
				} else {
					MemberVO member = searchList.get(choice - 1);
					delList.add(0,member);
				}
			} while (isWrong);
			return delList;
		}

	private MemberVO makeMember(Scanner sc) {
		MemberVO member = null;
			
		System.out.print("이름: ");
		String name = sc.nextLine();
		System.out.print("전화번호: ");
		String tell = null;
		do {
			tell  = sc.nextLine();	
			try {
				member = dao.memberSearchTell(tell);
			} catch (SQLException e) {
				e.printStackTrace();
			}	
			
			if (member != null) {
				System.out.println("전화번호가 중복됩니다.");
			}else {
				break;
			}
			
		} while (true);
			
		System.out.print("주소: ");
		String address = sc.nextLine();
		
		System.out.print("나이: ");
		
		int age = -1;
		do {
			try {
				String invalue = sc.nextLine();
				age = Integer.parseInt(invalue);			
			} catch (Exception e) {
				System.out.println("잘못된 값을 입력하셨습니다. 숫자를 입력하여 주십시오.");
			}
			if (age < 0) {
				System.out.println("잘못된 값을 입력하셨습니다. 0 또는 0보다 큰 정수를 입력해주십시오.");
			}
		} while (age <= 0);
		
		System.out.println("관계를 입력하시려면 4가지 번호중 하나를 입력해주십시오.");
		
		
		
		MemberKind kind = kindChoice(sc);
			
		member = new MemberVO(name, tell, address, age, kind);
		
		return member;
	}
		
	private MemberKind kindChoice(Scanner sc) {
		System.out.println("1. 친구 2. 가족 3. 직장 4. 기타");
		MemberKind kind = null;
		boolean isWrong;
		do {
			isWrong = false;
			String choice = sc.nextLine();
			switch (choice) {
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
				System.out.println("다시 입력해 주세요.");
				isWrong = true;
			}
		} while (isWrong);
		return kind;
	}

	@Override
	public void memberDefault() {
		try {
			dao.memberDefault();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void exit(Scanner sc) {
		boolean isDisconn = false;
		boolean isExit = false;
		int age = -1;
		String invalue = null;
		System.out.println(	"저장 하시려면 1번, "
						+ 	"저장하지 않으시려면 2번을 눌러주십시오");
		do {
			try {
				invalue = sc.nextLine();
				age = Integer.parseInt(invalue);			
			} catch (Exception e) {
				System.out.println("잘못된 값을 입력하셨습니다. 숫자를 입력하여 주십시오.");
			}
		
			try {
				isDisconn = ManageConn.serverDisconnect(age);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			switch (age) {
			case 1:
				if (isDisconn) {
					System.out.println("성공적으로 저장되었습니다.");					
				}else {
					System.out.println("서버와 연결이 되어있지 않습니다.");
				}
				isExit = true;
				break;
			case 2:
				if (isDisconn) {
					System.out.println("저장하지 않고 종료합니다.");					
				}else {
					System.out.println("서버와 연결이 되어있지 않습니다.");
				}
				isExit = true;
				break;
			default:
				System.out.println("올바른 값을 입력해주십시오");
				break;
			}
		
			
		} while (!isExit);
		
	}
		
		
}
