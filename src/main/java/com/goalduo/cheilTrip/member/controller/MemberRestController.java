package com.goalduo.cheilTrip.member.controller;


import com.goalduo.cheilTrip.jwt.JwtToken;
import com.goalduo.cheilTrip.member.dto.Member;
import com.goalduo.cheilTrip.member.dto.MemberDto;
import com.goalduo.cheilTrip.member.dto.MemberRequestDto;
import com.goalduo.cheilTrip.member.service.MemberService;
import com.goalduo.cheilTrip.tripplan.dto.TripplanDto;
import com.goalduo.cheilTrip.tripplan.service.TripplanService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class MemberRestController {

    private final MemberService memberService;
    private final TripplanService tripplanService;


    @PostMapping("/regist")
    public ResponseEntity<?> regist(@RequestBody Member member){
        int result = memberService.registerMember(member);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @PostMapping("/login")
    public ResponseEntity<JwtToken> login(@RequestBody MemberRequestDto memberRequestDto){
        JwtToken jwtToken = memberService.login(memberRequestDto.getUserId(), memberRequestDto.getUserPass());
        return new ResponseEntity<>(jwtToken, HttpStatus.OK);
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

    @GetMapping("/tripplan")
    public ResponseEntity<?> getTripplanByUserId(){
        List<TripplanDto> result = tripplanService.getTripplansAndTripCoursesByUserId("ssafy");
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/test")
    public ResponseEntity<?> test(){
        return new ResponseEntity<>(1, HttpStatus.OK);
    }
}
