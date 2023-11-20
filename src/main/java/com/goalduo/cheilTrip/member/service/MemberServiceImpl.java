package com.goalduo.cheilTrip.member.service;

import com.goalduo.cheilTrip.jwt.JwtProvider;
import com.goalduo.cheilTrip.jwt.JwtToken;
import com.goalduo.cheilTrip.member.dto.Member;
import com.goalduo.cheilTrip.member.dto.MemberDto;
import com.goalduo.cheilTrip.member.mapper.MemberMapper;
import com.goalduo.cheilTrip.util.ApiException;
import com.goalduo.cheilTrip.util.Encrypt;
import com.goalduo.cheilTrip.util.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {

    private final MemberMapper memberMapper;
    private final JwtProvider jwtProvider;
    private final RedisService redisService;

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
    public JwtToken login(String userId, String userPass) {
        Member member = memberMapper.getMember(userId);
//        if (member == null) return null;
        String encrypted = Encrypt.getEncrypt(userPass, member.getSalt());
        Member loginMember = memberMapper.loginMember(userId, encrypted);
        if (loginMember == null) throw new RuntimeException("로그인 실패, 회원 정보를 확인해주세요.");
        JwtToken jwtToken = jwtProvider.createJwtToken(loginMember);
        return jwtToken;
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

    @Override
    public MemberDto findMemberByUserId(String userId) {
        MemberDto memberDto = memberMapper.findMemberByUserId(userId);
        if (memberDto == null) throw new ApiException("해당 멤버가 존재하지 않습니다.");
        return memberDto;
    }

    @Override
    public String getUserIdAttractionMapping(String userId) {
        String values = redisService.getValues(userId);
        if (values.equals("false")){
            redisService.setValues(userId, userId);
        }
        return redisService.getValues(userId);
    }
    @Override
    public String setUserAttractionMapping(String from, String to) {
        redisService.setValues(from, to);
        return redisService.getValues(from);
    }
}