package com.goalduo.cheilTrip.member.service;

import com.goalduo.cheilTrip.member.dto.Member;
import com.goalduo.cheilTrip.member.dto.MemberDto;
import com.goalduo.cheilTrip.member.mapper.MemberMapper;
import com.goalduo.cheilTrip.util.Encrypt;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {

    private final MemberMapper memberMapper;

    @Override
    @Transactional
    public int registerMember(Member member) {
        String salt = Encrypt.getSalt();
        String encrypted = Encrypt.getEncrypt(member.getUserPass(), salt);
        member.setSalt(salt);
        member.setUserPass(encrypted);
        return memberMapper.registerMember(member);
    }

    @Override
    public MemberDto login(String userId, String userPass) {
        Member member = memberMapper.getMember(userId);
        if (member == null) return null;
        String encrypted = Encrypt.getEncrypt(userPass, member.getSalt());
        Member loginMember = memberMapper.loginMember(userId, encrypted);
        MemberDto result = MemberDto.builder()
                .userId(loginMember.getUserId())
                .userName(loginMember.getUserName())
                .userEmail(loginMember.getUserEmail())
                .joinDate(loginMember.getJoinDate())
                .build();
        return result;
    }

    @Override
    public MemberDto updateMember(Member member) {
        Member updateMember = memberMapper.updateMember(member);
        MemberDto result = MemberDto.builder()
                .userId(updateMember.getUserId())
                .userName(updateMember.getUserName())
                .userEmail(updateMember.getUserEmail())
                .joinDate(updateMember.getJoinDate())
                .build();
        return result;
    }

    @Override
    public int deleteMember(String userId) {
        return memberMapper.deleteMember(userId);
    }

}