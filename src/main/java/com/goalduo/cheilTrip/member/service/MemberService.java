package com.goalduo.cheilTrip.member.service;

import com.goalduo.cheilTrip.member.dto.Member;
import com.goalduo.cheilTrip.member.dto.MemberDto;

public interface MemberService {

    int registerMember(Member member);

    MemberDto login(String userId, String userPass);

    MemberDto updateMember(Member member);

    int deleteMember(String userId);

}