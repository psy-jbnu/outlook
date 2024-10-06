package pe.contacts.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import pe.contacts.connect.ManageConn;
import pe.contacts.vo.MemberKind;
import pe.contacts.vo.MemberVO;

public class MemberDAOImpl implements MemberDAO {
	
	@Override
	public void memberAdd(MemberVO member) throws SQLException {
		Connection conn = ManageConn.getConnection();
		PreparedStatement pstmt = null;
		
		String sql = "INSERT INTO MEMBER VALUES (?, ?, ?, ?, ?)";
		
		if (conn == null) {
			return;
		}
		
		pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1, member.getName());
		pstmt.setString(2, member.getTell());
		pstmt.setString(3, member.getAddress());
		pstmt.setInt(4, member.getAge());
		pstmt.setInt(5, member.getKind().ordinal());
		pstmt.execute();
		
		pstmt.close();
	}

	@Override
	public ArrayList<MemberVO> memberList() throws SQLException {
		Connection conn = ManageConn.getConnection();
		ArrayList<MemberVO> memberList = new ArrayList<MemberVO>();
		PreparedStatement pstmt = null;
		
		String sql	=	"SELECT  *							"  
					+ 	"  FROM  MEMBER	a					"
					+ 	" ORDER  BY  a.MEMBER_NAME, a.TELL	";
		
		if (conn == null) {
			return memberList;
		}
		
		pstmt = conn.prepareStatement(sql);
		
		ResultSet rs = pstmt.executeQuery();
		
		while (rs.next()) {
			String name = rs.getNString(1);
			String tell = rs.getNString(2);
			String address = rs.getNString(3);
			int age = rs.getInt(4);
			int	groupID = rs.getInt(5);
			MemberKind [] kinds = MemberKind.values();
			MemberKind kind = kinds[groupID];
			MemberVO member = new MemberVO(name, tell, address, age, kind);
			memberList.add(member);
		}
		
		pstmt.close();

		return memberList;
	}

	@Override
	public ArrayList<MemberVO> listShow(String name) throws SQLException {
		Connection conn = ManageConn.getConnection();
		ArrayList<MemberVO> memberList = new ArrayList<MemberVO>();
		PreparedStatement pstmt = null;
		
		String sql	=	"SELECT  *					"  
					+ 	"  FROM  MEMBER	a			"
					+ 	" WHERE  member_name = ?	"
					+ 	" ORDER  BY  a.TELL			";
		
		if (conn == null) {
			return null;
		}
		
		pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1, name);

		ResultSet rs = pstmt.executeQuery();
		
		while (rs.next()) {
			name = rs.getNString(1);
			String tell = rs.getNString(2);
			String address = rs.getNString(3);
			int age = rs.getInt(4);
			int	groupID = rs.getInt(5);
			MemberKind [] kinds = MemberKind.values();
			MemberKind kind = kinds[groupID];
			MemberVO member = new MemberVO(name, tell, address, age, kind);
			memberList.add(member);
		}
		
		pstmt.close();

		return memberList;
	}

	@Override
	public void memberDel(ArrayList<MemberVO> delList) throws SQLException {
		Connection conn = ManageConn.getConnection();
		PreparedStatement pstmt = null;
		
		String sql = 	"DELETE  member		" 
					+	" WHERE  tell = ?	" ;
		
		if (conn == null) {
			return;
		}
		
		pstmt = conn.prepareStatement(sql);
		
		for (MemberVO member : delList) {
			pstmt.setString(1,member.getTell());
			pstmt.execute();
			pstmt.clearParameters();
		}
		
		pstmt.close();

	}

	@Override
	public MemberVO memberSearchTell(String tell) throws SQLException {
		Connection conn = ManageConn.getConnection();
		MemberVO member = null;
		PreparedStatement pstmt = null;
		
		String sql	=	"SELECT  * 			" 
					+	"  FROM  MEMBER a	" 
					+	" WHERE  a.tell = ?	";
		
		if (conn == null) {
			return null;
		}
		
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, tell);
		
		ResultSet rs = pstmt.executeQuery();
		
		
		while (rs.next()) {
			String name = rs.getNString("MEMBER_NAME");
			String tel = rs.getNString("TELL");
			String address = rs.getNString("ADDRESS");
			int age = rs.getInt("AGE");
			int	groupID = rs.getInt("GROUP_ID");
			MemberKind [] kinds = MemberKind.values();
			MemberKind kind = kinds[groupID];
			member = new MemberVO(name, tel, address, age, kind);
		}
		
		pstmt.close();

		return member;
	}

	@Override
	public ArrayList<String> memberListKind(MemberKind kind) throws SQLException {
		Connection conn = ManageConn.getConnection();
		ArrayList<String> memberList = new ArrayList<String>();
		PreparedStatement pstmt = null;
		
		String sql	=	"SELECT  a.tell						"  
					+ 	"  FROM  MEMBER	a					"
					+ 	" WHERE  a.GROUP_ID = ?				"
					+ 	" ORDER  BY  a.MEMBER_NAME, a.TELL	";
		
		if (conn == null) {
			return null;
		}
		
		pstmt = conn.prepareStatement(sql);
		
		int ord = kind.ordinal();
		pstmt.setInt(1, ord);
		
		ResultSet rs = pstmt.executeQuery();
		
		while (rs.next()) {
			String tell = rs.getNString(1);
			memberList.add(tell);
		}
		
		pstmt.close();

		return memberList;
	}

	@Override
	public void memberRevise(MemberVO oldMember, MemberVO member) throws SQLException {
		Connection conn = ManageConn.getConnection();
		PreparedStatement pstmt = null;
		
		String sql =	"UPDATE  MEMBER				" 
					+	"   SET  member_name = ?	" 
					+	"      , tell        = ?	" 
					+	"      , ADDRESS     = ?	" 
					+	"      , age         = ?	" 
					+	"      , GROUP_ID    = ?	"
					+ 	" WHERE  tell    	 = ?	";
		
		if (conn == null) {
			return;
		}
		
		pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1, member.getName());
		pstmt.setString(2, member.getTell());
		pstmt.setString(3, member.getAddress());
		pstmt.setInt(4, member.getAge());
		pstmt.setInt(5, member.getKind().ordinal());
		pstmt.setString(6, oldMember.getTell());
		
		pstmt.execute();
		
		pstmt.close();
	}
	@Override
	public void memberDefault( ) throws SQLException {
		Connection conn = ManageConn.getConnection();
		PreparedStatement pstmt = null;
		
		String sql = 	"DELETE  member" ;
		
		if (conn == null) {
			return;
		}
		
		pstmt = conn.prepareStatement(sql);
		
		pstmt.execute();

		pstmt.close();

	}
}
	