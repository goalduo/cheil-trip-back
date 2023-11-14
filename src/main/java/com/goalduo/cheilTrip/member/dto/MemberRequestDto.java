package com.goalduo.cheilTrip.member.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MemberRequestDto {
    private String userId;
    private String userPass;
}
