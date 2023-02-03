package com.springlab.member;

import java.beans.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.springlab.common.JDBCUtil;

@Repository("memberDAO")
public class MemberDAO {
	private Connection conn = null;
	private Statement stmt = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	private final String MEMBER_LOGIN = "select * from member where id=? and pass=?";
	private final String MEMBER_GET = "select * from member where idx = ?";
	private final String MEMBER_INSERT = "insert into member(idx, id, pass, name, email, age, weight) "
			+ "values((select nvl(max(idx),0)+1 from member),?,?,?,?,?,?)";
	private final String MEMBER_LIST = "select * from member order by idx desc";
	private final String MEMBER_DELETE = "delete member where idx=?";
	private final String MEMBER_UPDATE = "update member set pass=?, weight=? where idx = ?";
	private final String LOGIN_COUNT = "update member set cnt=((select cnt from member where id=?)+1) where id=?";
	
	// 로그인 
	public Boolean Login(MemberDTO dto) {
		
		MemberDTO member = null ;
		
		System.out.println("로그인 DAO - " + dto.getId());
		System.out.println("로그인 DAO - " + dto.getPass());
		
		try {
			System.out.println("로그인 - 시작");
			
			conn = JDBCUtil.getConnection();
			pstmt = conn.prepareStatement(MEMBER_LOGIN);
			
			pstmt.setString(1, dto.getId());
			pstmt.setString(2, dto.getPass());
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				System.out.println("DB에서 select되었습니다.");
				return true;
			} else {
				return false;
			}
			
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("오류 발생 - 로그인 실패");
		} finally {
			JDBCUtil.close(rs, pstmt, conn);
		}
		
		return false;
	}

	// 1. getMember()
	public MemberDTO getMember(MemberDTO dto) {
		System.out.println("JDBC로 getMember() 기능 처리 - 시작");
	
		MemberDTO member = new MemberDTO();
		
		System.out.println("DAO - " + dto.getIdx());
		
		try {
			conn = JDBCUtil.getConnection();
			pstmt = conn.prepareStatement(MEMBER_GET);
			
			pstmt.setInt(1, dto.getIdx());
			
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
			
				System.out.println("DB에서 잘 select 되었습니다.");
				
				member.setIdx(rs.getInt("IDX"));
				member.setId(rs.getString("ID"));
				member.setPass(rs.getString("PASS"));
				member.setName(rs.getString("NAME"));
				member.setEmail(rs.getString("EMAIL"));
				member.setAge(rs.getInt("AGE"));
				member.setWeight(rs.getDouble("WEIGHT"));
				member.setRegdate(rs.getString("REGDATE"));
				member.setCnt(rs.getInt("CNT"));

			} else {
				System.out.println("레코드 결과가 없습니다.");
			}
			
			System.out.println(" JDBC로 getMember() 기능 처리 - 완료");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(" JDBC로 getMember() 기능 처리 - 실패");
		} finally {
			JDBCUtil.close(rs, pstmt, conn);
		}
		return member;
	}
	
	// 2. InsertMember()
	public void insertMember(MemberDTO dto) {
		System.out.println(" JDBC로 InsertMember() 기능 처리 - 시작");
		
		try {
			
			conn = JDBCUtil.getConnection();
			pstmt = conn.prepareStatement(MEMBER_INSERT);
			
			// insert into member(idx, id, pass, name, email, age, weight) "
			// + "values((select nvl(max(idx),0)+1 from member),?,?,?,?,?,?)
			pstmt.setString(1, dto.getId());
			pstmt.setString(2, dto.getPass());
			pstmt.setString(3, dto.getName());
			pstmt.setString(4, dto.getEmail());
			pstmt.setInt(5, dto.getAge());
			pstmt.setDouble(6, dto.getWeight());
			
			pstmt.executeQuery();
			System.out.println("JDBC로 insertMember() 기능 처리 - 완료");
			
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println("JDBC로 insertMember() 기능 처리 - 실패");
		} finally {
			JDBCUtil.close(pstmt, conn);
			System.out.println("모든 객체가 close() 되었습니다.");
		}
		
		
	}
	
	// 3. getMemberList()
	// select * from member order by idx desc
	
	public List<MemberDTO> getMemberList(MemberDTO dto) {
		System.out.println("JDBC로 getMemberList() 기능 처리 - 시작");
		
		List<MemberDTO> memberList = new ArrayList<MemberDTO>();
		MemberDTO member;
		
		try {
			conn = JDBCUtil.getConnection();
			
			pstmt = conn.prepareStatement(MEMBER_LIST);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				do {
					member = new MemberDTO();
					
					member.setIdx(rs.getInt("idx"));
					member.setId(rs.getString("ID"));
					member.setPass(rs.getString("PASS"));
					member.setName(rs.getString("NAME"));
					member.setEmail(rs.getString("EMAIL"));
					member.setAge(rs.getInt("AGE"));
					member.setWeight(rs.getDouble("WEIGHT"));
					member.setRegdate(rs.getString("REGDATE"));
					member.setCnt(rs.getInt("CNT"));
					
					memberList.add(member);
					
				} while (rs.next());
				
			} else {
				System.out.println("테이블에 레코드가 존재하지 않습니다.");
			}
			System.out.println("JDBC로 getMemberList() 기능 처리 - 완료");
			
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println("JDBC로 getMemberList() 기능 처리 - 실패");
		} finally {
			JDBCUtil.close(rs, pstmt, conn);
		}
		return memberList;
		
	}
	
	// 4. delestMember()
	public void deleteMember(MemberDTO dto) {
		System.out.println("JDBC로 deleteMember() 기능 처리 - 시작");
	
	try {
		conn = JDBCUtil.getConnection();
		pstmt = conn.prepareStatement(MEMBER_DELETE);
		pstmt.setInt(1, dto.getIdx());
		pstmt.executeUpdate();
		
		System.out.println("JDBC로 deleteMember() 기능 처리 - 완료");
	} catch(Exception e) {
		e.printStackTrace();
		System.out.println("JDBC로 deleteMember() 기능 처리 - 실패");
	} finally {
		JDBCUtil.close(pstmt, conn);
	}
	
	
	}
	
	// 5. updateMember()
	public void updateMember(MemberDTO dto) {
		System.out.println("JDBC로 updateMember() 기능 처리 - 시작");
		
		try {
					
		conn = JDBCUtil.getConnection();
		pstmt = conn.prepareStatement(MEMBER_UPDATE);

		pstmt.setString(1, dto.getPass());
		pstmt.setDouble(2,dto.getWeight());
		pstmt.setInt(3, dto.getIdx());
		
		pstmt.executeUpdate();
		
		System.out.println("JDBC로 updateMember() 기능 처리 - 완료");

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("JDBC로 updateMember() 기능 처리 - 실패");
		} finally {
			JDBCUtil.close(pstmt, conn);
		}
	}
	
	public void loginCount(MemberDTO dto) {
		try {
		conn = JDBCUtil.getConnection();
		pstmt = conn.prepareStatement(LOGIN_COUNT);
		
		pstmt.setString(1, dto.getId());
		pstmt.setString(2, dto.getId());
		
		pstmt.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(pstmt, conn);
		}
		

		
	}
	
	
	
}
