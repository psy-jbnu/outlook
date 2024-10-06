package pe.contacts.vo;

public enum MemberKind {
	FRIENDS("친구"), 
	FAMILLIES("가족"), 
	OFFICE("직장"), 
	OTHERS("기타");
	
	private String value;

	private MemberKind(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
	
	
	
}
