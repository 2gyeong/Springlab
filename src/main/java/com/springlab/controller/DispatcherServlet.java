package com.springlab.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.springlab.member.MemberDAO;
import com.springlab.member.MemberDTO;

// @WebServlet("/DispatcherServlet")
public class DispatcherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public DispatcherServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		process(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		process(request, response);
	}

	public void process (HttpServletRequest request, HttpServletResponse response) throws IOException {
		//doGet, doPost 메소드에서 받는 내용을 처리
		
		// 1. 클라이언트의 요청 path 추출
		String url = request.getRequestURL().toString();
			System.out.println("url : " + url);
		String uri = request.getRequestURI();
			System.out.println("uri : " + uri);
		String path = uri.substring(uri.lastIndexOf("/"));
			System.out.println("path : "+ path);
		
		// 2. 요청에 따른 처리
		if (path.equals("/login.do")) {
			System.out.println("/login.do 요청을 보냈습니다.");
			
			// a. 클라이언트에서 보내는 값을 받아서 변수에 저장
			//int cnt = Integer.parseInt(request.getParameter("cnt"));
			String id = request.getParameter("id");
			String pass = request.getParameter("pass");

			//System.out.println("id : " + cnt);
			System.out.println("id : " + id);
			System.out.println("pass : " + pass);
			
			// b. 클라이언트에서 넘긴 변수값을 받아서 저장된 변수를 DTO에 Setter 주입
			MemberDTO dto = new MemberDTO();
			
			
			dto.setId(id);
			dto.setPass(pass);
			
			// c. 비즈니스 로직을 처리하는 인터페이스에 dto를 주입해서 비즈니스 로직을 처리
			
			MemberDAO member = new MemberDAO();
			MemberDTO memberD = member.getMember(dto);
			
			System.out.println(memberD);
			
			// D. View 페이지로 이동
			if(memberD != null) {
				response.sendRedirect("getMemberList.do");
				System.out.println("아이디와 패스워드 일치");

						
			} else {
				response.sendRedirect("login.jsp");
				System.out.println("아이디와 패스워드 불일치");
			}
			
		}else if(path.equals("/getMemberList.do")) {
				System.out.println("회원 목록 출력");
				
				MemberDTO dto = new MemberDTO();
				MemberDAO dao = new MemberDAO();
				
				List<MemberDTO> memberList = dao.getMemberList(dto);
			
				HttpSession session = request.getSession();
				
				session.setAttribute("memberList", memberList);
				
				
				response.sendRedirect("getMemberList.jsp");
			
			
		} else if(path.equals("/insertMember.do")) {
			System.out.println("member 테이블에 값을 저장");
			
			String id = request.getParameter("id");
			String pass = request.getParameter("pass");
			String name = request.getParameter("name");
			String email = request.getParameter("email");
			int age = Integer.parseInt(request.getParameter("age")) ;
			double weight = Double.parseDouble(request.getParameter("weight"));
			
			MemberDTO dto = new MemberDTO();
			MemberDAO dao = new MemberDAO();
			
			dto.setId(id);
			dto.setPass(pass);
			dto.setName(name);
			dto.setEmail(email);
			dto.setAge(age);
			dto.setEmail(email);
			
			dao.insertMember(dto);
			
			response.sendRedirect("getMemberList.do");
			
			
		}  else if(path.equals("/getMemberList.do")) {
			System.out.println("MemberList 출력");
			
			MemberDTO dto = new MemberDTO();
			MemberDAO dao = new MemberDAO();
			
			List<MemberDTO> memberList = dao.getMemberList(dto);
			
			HttpSession session = request.getSession();
			
			response.sendRedirect("getMemberList.jsp");
			
		}  else if(path.equals("/getMember.do")) {
			System.out.println("member 상세 페이지 보기");
			
			String idx = request.getParameter("idx");
			
			MemberDTO dto = new MemberDTO();
			MemberDAO dao = new MemberDAO();
			
			dto.setIdx(Integer.parseInt(idx));
			
			System.out.println("idx 값 : " + idx);
			
			MemberDTO member = dao.getMember(dto);
			
			HttpSession session = request.getSession();
			
			session.setAttribute("member", member);
			
			response.sendRedirect("getMember.jsp");
			
			
		}  else if(path.equals("/deleteMember.do")) {
			System.out.println("member 정보 삭제");
			
			String idx = request.getParameter("idx");
			
			MemberDTO dto = new MemberDTO();
			MemberDAO dao = new MemberDAO();
			
			dto.setIdx(Integer.parseInt(idx));
			
			dao.deleteMember(dto);
			
			response.sendRedirect("getMemberList.do");

			
		}  else if(path.equals("/updateMember.do")) {
			System.out.println("member 정보 수정");
			
			String pass = request.getParameter("pass");
			String name = request.getParameter("name");
			String email = request.getParameter("email");
			int age = Integer.parseInt(request.getParameter("age")) ;
			double weight = Double.parseDouble(request.getParameter("weight"));
			
			MemberDTO dto = new MemberDTO();
			MemberDAO dao = new MemberDAO();
			
			dto.setPass(pass);
			dto.setName(name);
			dto.setEmail(email);
			dto.setAge(age);
			dto.setEmail(email);
			
			dao.insertMember(dto);
			
			response.sendRedirect("getMemberList.do");
		} 
		
		
	}

}
