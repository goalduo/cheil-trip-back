package com.goalduo.cheilTrip.member.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class MemberDto {
    private String userId;
    private String userName;
    private String userEmail;
    private String joinDate;
}