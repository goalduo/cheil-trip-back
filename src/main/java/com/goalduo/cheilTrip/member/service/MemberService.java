package com.goalduo.cheilTrip.member.service;

import com.goalduo.cheilTrip.jwt.JwtToken;
import com.goalduo.cheilTrip.member.dto.Member;
import com.goalduo.cheilTrip.member.dto.MemberDto;

public interface MemberService {

    int registerMember(Member member);

    JwtToken login(String userId, String userPass);

    MemberDto updateMember(Member member);

    int deleteMember(String userId);

    MemberDto findMemberByUserId(String userId);
}