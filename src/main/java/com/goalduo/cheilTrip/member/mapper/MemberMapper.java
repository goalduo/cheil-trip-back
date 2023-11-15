package com.goalduo.cheilTrip.member.mapper;

import com.goalduo.cheilTrip.member.dto.Member;
import com.goalduo.cheilTrip.member.dto.MemberDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {

    int registerMember(Member member);

    Member getMember(String userId);

    Member loginMember(String userId, String userPass);

    Member updateMember(Member member);

    int deleteMember(String userId);

    MemberDto findMemberByUserId(String userId);
}
