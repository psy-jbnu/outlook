package pe.addressbook.dao;
import java.io.IOException;
import java.util.ArrayList;

import pe.addressbook.vo.MemberKind;
import pe.addressbook.vo.MemberVO;
//	MemberDAO가 implement할 Method 정의
public interface MemberDAO {
	void memberAdd(MemberVO member);
	ArrayList<MemberVO> memberList();
	ArrayList<MemberVO> listShow(String name);
	void memberDel(ArrayList<MemberVO> delList);
	MemberVO memberSearchTell(String tell);
	boolean memberSearchName(String name);
	ArrayList<String> memberListKind(MemberKind kind);
	void memberRevise(MemberVO oldMember, MemberVO member);
	void MemberRead() throws IOException, ClassNotFoundException;
	void memberWrite() throws IOException;
}
