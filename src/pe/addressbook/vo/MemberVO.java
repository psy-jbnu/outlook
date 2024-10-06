package pe.addressbook.vo;

import java.io.Serializable;
/**
 * 회원정보의 저장 및 관리
 * @author      Seungyeop Park 
 */
public class MemberVO implements Comparable<MemberVO>, Serializable , Cloneable{
	private static final long serialVersionUID = 1L;

	// 회원 정보를 저장할 변수들
	private String name;
	private String tell;
	private String address;
	private String age;
	private MemberKind kind;
	
	
	
//	Constructors
    public MemberVO(String name, String tell, String address, String age, MemberKind kind) {
		super();
		this.name = name;
		this.tell = tell;
		this.address = address;
		this.age = age;
		this.kind = kind;
	}  
    public MemberVO() {
		super();
	}


	//	회원정보를 정하거나 받는 getter setter
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTell() {
		return tell;
	}
	public void setTell(String tell) {
		this.tell = tell;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public MemberKind getKind() {
		return kind;
	}
	public void setKind(MemberKind kind) {
		this.kind = kind;
	}

//    정렬시 기준 정함.
	@Override
	public int compareTo(MemberVO o) {
		if (this.name.equals(o.name)) {
			return this.getTell().compareTo(o.getTell());
		} else {
			return this.getName().compareTo(o.getName());
		}
		
	}
	@Override
	public String toString() {
		
		switch (getKind()) {
		case FRIENDS:
			return 	"이름: " + name + System.lineSeparator() +
					"전화번호: " + tell + System.lineSeparator() +
					"주소: " + address + System.lineSeparator() +
					"나이: " + age + System.lineSeparator() +
					"관계: " + "친구"+ System.lineSeparator();
		case FAMILLIES:
			return 	"이름: " + name + System.lineSeparator() +
					"전화번호: " + tell + System.lineSeparator() +
					"주소: " + address + System.lineSeparator() +
					"나이: " + age + System.lineSeparator() +
					"관계: " + "가족"+ System.lineSeparator();
		case OFFICE:
			return 	"이름: " + name + System.lineSeparator() +
					"전화번호: " + tell + System.lineSeparator() +
					"주소: " + address + System.lineSeparator() +
					"나이: " + age + System.lineSeparator() +
					"관계: " + "직장" + System.lineSeparator();
		case OTHERS:
			return 	"이름: " + name + System.lineSeparator() +
					"전화번호: " + tell + System.lineSeparator() +
					"주소: " + address + System.lineSeparator() +
					"나이: " + age + System.lineSeparator() +
					"관계: " + "기타" + System.lineSeparator() 
					;
		default:
			return"";
		}
	}
	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	
	


	
	
}
