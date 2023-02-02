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
	
	private final String MEMBER_GET = "select * from member where idx = ?";
	private final String MEMBER_INSERT = "insert into member(idx, id, pass, name, email, age, weight) "
			+ "values((select nvl(max(idx),0)+1 from member),?,?,?,?,?,?)";
	private final String MEMBER_LIST = "select * from member order by idx desc";
	private final String MEMBER_DELETE = "delete member where idx=?";
	private final String MEMBER_UPDATE = "update member set pass=?, name=?, email=?, age=?, weight=?";
	
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
				member = new MemberDTO();
				
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
				
				System.out.println("JDBC로 DB를 잘 쿼리해서 DTO로 잘 전송");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("JDBC로 쿼리 실행 중 오류 발생");
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
		pstmt.executeQuery();
		
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

		//update member set pass=?, name=?, email=?, age=?, weight=?
		pstmt.setString(1, dto.getPass());
		pstmt.setString(2,dto.getName());
		pstmt.setString(3,dto.getEmail());
		pstmt.setString(4,dto.getName());
		pstmt.setInt(5, dto.getAge());
		pstmt.setDouble(6, dto.getWeight());
		
		System.out.println("JDBC로 updateMember() 기능 처리 - 완료");

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("JDBC로 updateMember() 기능 처리 - 실패");
		} finally {
			JDBCUtil.close(pstmt, conn);
		}
	}
	
	
	
	
	
}
