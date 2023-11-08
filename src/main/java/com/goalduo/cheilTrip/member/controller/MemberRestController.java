package com.goalduo.cheilTrip.member.controller;


import com.goalduo.cheilTrip.member.dto.Member;
import com.goalduo.cheilTrip.member.dto.MemberDto;
import com.goalduo.cheilTrip.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class MemberRestController {

    private final MemberService memberService;

    @PostMapping("/regist")
    public ResponseEntity<?> regist(@RequestBody Member member){
        int result = memberService.registerMember(member);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @PostMapping("/login")
    public ResponseEntity<MemberDto> login(@RequestBody Member member, HttpSession session){
        MemberDto loginMember = memberService.login(member.getUserId(), member.getUserPass());
        if(loginMember == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        System.out.println(loginMember);
        session.setAttribute("userinfo", loginMember);
        Object userinfo = session.getAttribute("userinfo");
        System.out.println(userinfo);
        return new ResponseEntity<>(loginMember, HttpStatus.OK);
    }
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session){
        session.invalidate();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/info")
    public ResponseEntity<?> getInfo(HttpSession session){
        MemberDto userinfo = (MemberDto) session.getAttribute("userinfo");
        System.out.println(userinfo);
        if(userinfo == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(userinfo,HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<MemberDto> update(@RequestBody Member member, HttpSession session){
        MemberDto updatedMember = memberService.updateMember(member);
        session.setAttribute("userinfo", updatedMember);
        return new ResponseEntity<>(updatedMember, HttpStatus.OK);
    }

    @PostMapping("/delete")
    public ResponseEntity<?> delete(@RequestParam String userId, HttpSession session){
        memberService.deleteMember(userId);
        session.invalidate();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
