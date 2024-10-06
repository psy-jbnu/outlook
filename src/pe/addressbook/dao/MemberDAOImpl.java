package pe.addressbook.dao;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import pe.addressbook.vo.MemberKind;
import pe.addressbook.vo.MemberVO;

/**
 * MemberVO 저장 및 관리하는 Class
 * @author      Seungyeop Park 
 */
public class MemberDAOImpl implements MemberDAO {
	public HashMap<String, MemberVO> members = new HashMap<String, MemberVO>();
    public HashMap<String, ArrayList<String>> names = new HashMap<String, ArrayList<String>>();
	public HashMap<MemberKind, ArrayList<String>> kinds = new HashMap<MemberKind, ArrayList<String>>(4);
	public final ArrayList<String> kkk = new ArrayList<String>();
	
	//members에 연락처를 추가
	@Override
	public void memberAdd(MemberVO member) {
		String tell = member.getTell();
		members.put(tell, member);
		memberAddkinds(member);
		memberAddnames(member);
		return;
		
	}

	private void memberAddkinds (MemberVO member) {
		String tell = member.getTell();
		MemberKind kind = member.getKind();
		ArrayList<String> tells = new ArrayList<String>();
		kinds.putIfAbsent(kind, tells);
		tells = kinds.get(kind);
		tells.add(tell);
		kinds.put(kind, tells);
		return;
		}
	
    private void memberAddnames (MemberVO member) {
		String name = member.getName();
		String tell = member.getTell();
		ArrayList<String> tells = new ArrayList<String>();
		names.putIfAbsent(name, tells);
		tells = names.get(name);
		tells.add(tell);
		Collections.sort(tells);
		names.put(name, tells);
		return;
	}
	
	// 연락처들을 ArrayList로 묶음. (없으면 null return)
	@Override
	public ArrayList<MemberVO> memberList() {
		ArrayList<MemberVO> list = new ArrayList<MemberVO>(members.values());
		Collections.sort(list);
		return list;
	}
	
	// 이름을 찾은 다음 없을 경우 ArrayList index = 0가 null고, 있을 경우 ArrayList를 보냄.
	@Override
	public ArrayList<MemberVO> listShow(String name) {
		ArrayList<MemberVO> searchList = new ArrayList<MemberVO>();
		ArrayList<String> tells = names.get(name);
		if (tells == null) {
			return searchList;
		}
		
		for (String tell : tells) {
			MemberVO member = members.get(tell);
			searchList.add(member);
		}
		return searchList;
	}
	
	// 지우기
	public void memberDel(ArrayList<MemberVO> delList) {
		String name = delList.get(0).getName();
		ArrayList<String> tellList = names.get(name);
		for (MemberVO member : delList) {
			String tell = member.getTell();
			members.remove(tell);
			MemberKind kind = member.getKind();
			kinds.get(kind).remove(tell);
			tellList.remove(tell);
			names.remove(name, kkk);
		}
	}

	public MemberVO memberSearchTell(String tell) { 
		MemberVO member = members.get(tell);
		return member;
	}
	@Override
	
    public boolean memberSearchName(String name) {
		return names.containsKey(name);
	}
	@Override
    public ArrayList<String> memberListKind (MemberKind kind){
		ArrayList<String> list = kinds.getOrDefault(kind, kkk);
		Collections.sort(list);
		return list;
	}

	@Override
	public void memberRevise (MemberVO oldMember, MemberVO member) {
		ArrayList<MemberVO> delList = new ArrayList<MemberVO>(1);
		delList.add(oldMember);
		memberDel(delList);
		memberAdd(member);
	}
	@Override
	public void memberWrite () throws IOException{
		FileOutputStream membersFileOut = new FileOutputStream("save\\members.ser",false);
		BufferedOutputStream membersbuffer = new BufferedOutputStream(membersFileOut, 1024);		
		ObjectOutputStream membersObjout = new ObjectOutputStream(membersbuffer);
		membersObjout.writeObject(members);
		membersObjout.close();
		membersbuffer.close();
		membersFileOut.close();
		FileOutputStream namesFileOut = new FileOutputStream("save\\names.ser",false);
		BufferedOutputStream namesbuffer = new BufferedOutputStream(namesFileOut, 1024);		
		ObjectOutputStream namesObjout = new ObjectOutputStream(namesbuffer);
		namesObjout.writeObject(names);
		namesObjout.close();
		namesbuffer.close();
		namesFileOut.close();
		FileOutputStream kindsFileOut = new FileOutputStream("save\\kinds.ser",false);
		BufferedOutputStream kindsbuffer = new BufferedOutputStream(kindsFileOut, 1024);		
		ObjectOutputStream kindsObjout = new ObjectOutputStream(kindsbuffer);
		kindsObjout.writeObject(kinds);
		kindsObjout.close();
		kindsbuffer.close();
		kindsFileOut.close();
	}
	@Override
	@SuppressWarnings("unchecked")
	public void MemberRead( ) throws IOException, ClassNotFoundException{
		FileInputStream membersFileIn = new FileInputStream("save\\members.ser");
		BufferedInputStream membersbuffer = new BufferedInputStream(membersFileIn, 1024);		
		ObjectInputStream membersObjIn = new ObjectInputStream(membersbuffer);
		members = (HashMap<String, MemberVO>)membersObjIn.readObject();
		membersFileIn.close();
		membersbuffer.close();
		membersObjIn.close();
		FileInputStream namesFileIn = new FileInputStream("save\\names.ser");
		BufferedInputStream namesbuffer = new BufferedInputStream(namesFileIn, 1024);		
		ObjectInputStream namesObjIn = new ObjectInputStream(namesbuffer);
		names = (HashMap<String, ArrayList<String>>)namesObjIn.readObject();
		namesFileIn.close();
		namesbuffer.close();
		namesObjIn.close();
		FileInputStream kindsFileIn = new FileInputStream("save\\kinds.ser");
		BufferedInputStream kindsbuffer = new BufferedInputStream(kindsFileIn, 1024);		
		ObjectInputStream kindsObjIn = new ObjectInputStream(kindsbuffer);
		kinds = (HashMap<MemberKind, ArrayList<String>>)kindsObjIn.readObject();
		kindsFileIn.close();
		kindsbuffer.close();
		kindsObjIn.close();
	}

}
