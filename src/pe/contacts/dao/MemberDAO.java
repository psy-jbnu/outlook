package pe.contacts.dao;
import java.sql.SQLException;
import java.util.ArrayList;

import pe.contacts.vo.MemberKind;
import pe.contacts.vo.MemberVO;
/**
 * MemberDAOimpl가 implement할 Method 정의
 * @author      Seungyeop Park 
 */
public interface MemberDAO {
	void memberAdd(MemberVO member) throws SQLException;
	ArrayList<MemberVO> memberList() throws SQLException;
	ArrayList<MemberVO> listShow(String name) throws SQLException;
	void memberDel(ArrayList<MemberVO> delList) throws SQLException;
	MemberVO memberSearchTell(String tell) throws SQLException;
	ArrayList<String> memberListKind(MemberKind kind) throws SQLException;
	void memberRevise(MemberVO oldMember, MemberVO member) throws SQLException;
	void memberDefault() throws SQLException;
}
