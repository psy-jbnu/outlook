package pe.contacts.service;
import java.util.ArrayList;
import java.util.Scanner;

import pe.contacts.vo.MemberVO;
/**
 * MemberServiceImpl에 구현할 메소드 정의
 * @author      Seungyeop Park 
 */
public interface MemberService {
	void memberAdd(Scanner sc);
	void memberList();
	ArrayList<MemberVO> memberSearch(Scanner sc);
	void memberDel(Scanner sc);
	void memberRevise(Scanner sc);
	void memberListKind(Scanner sc);
	void memberDefault();
	void exit(Scanner sc);
}
