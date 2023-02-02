package com.springlab.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("memberService")
public class memberServicelmpl implements memberService {

	@Autowired
	MemberDAO memberDAO;
	
	@Override
	public MemberDTO getMember(MemberDTO dto) {
		System.out.println(dto.getId());
		System.out.println(dto.getPass());
		return memberDAO.getMember(dto);
	}

}
