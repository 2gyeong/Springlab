package com.springlab.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class JDBCUtil {
	
	// 1. DB 연결
	public static Connection getConnection() {
		String driver = "oracle.jdbc.driver.OracleDriver";
		
		String url = "jdbc:oracle:thin:@localhost:1521:XE";
		
		Connection conn = null;
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, "C##HR", "1234");
			
			System.out.println("DB 연결 되었습니다.");
			
			return conn;
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println("DB 연결에 실패했습니다.");
		}
		return null;
	}

	// 2. DB 연결 제거 (Connection, PreparedStatement 객체를 제거)
	public static void close(PreparedStatement pstmt, Connection conn) {
		if(pstmt != null) {
			try {
				if(!pstmt.isClosed()) {
					pstmt.close();
					System.out.println("pstmt 객체 close()");
				}
			} catch(Exception e) {
				e.printStackTrace();
			} finally {
				pstmt = null;
			}
			
			if(conn != null) {
				try {
					if(!conn.isClosed()) {
						conn.close();
						System.out.println("conn 객체 close()");
					}
				} catch(Exception e) {
					e.printStackTrace();
				} finally {
					conn = null;
				}
			}
		}
	}
	
	// 3.  DB연결을 제거 (Connection, PreparedStatement, ResultSet 객체를 제거)
	
	public static void close(ResultSet rs, PreparedStatement pstmt, Connection conn) {
		if(rs != null) {
			try {
				if (!rs.isClosed()) {
					rs.close();
				}
			} catch(Exception e) {
				e.printStackTrace();
			} finally {
				rs = null;
			}
		
		if (pstmt != null) {
			try {
				if(!pstmt.isClosed()) {
					pstmt.close();
				} 
				}catch(Exception e) {
					e.printStackTrace();
				} finally {
					pstmt = null;
				}
				}
		if (conn != null) {
			try {
				if(!conn.isClosed()) {
					conn.close();
				}
			} catch(Exception e) {
				e.printStackTrace();
			}finally {
				conn = null;
				
			}
		}
			
			
			
		}
	}
	
	
	
	
	
	
	
}
