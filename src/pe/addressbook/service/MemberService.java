package pe.addressbook.service;
import java.util.ArrayList;
import java.util.Scanner;

import pe.addressbook.vo.MemberVO;

public interface MemberService {
	void memberAdd(Scanner sc);
	void memberList();
	ArrayList<MemberVO> memberSearch(Scanner sc);
	void memberDel(Scanner sc);
	void memberRevise(Scanner sc);
	void memberListKind(Scanner sc);
	void MemberWrite();
	void MemberRead();

}
