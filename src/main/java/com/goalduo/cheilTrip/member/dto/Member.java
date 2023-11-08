package com.goalduo.cheilTrip.member.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Member {
    private String userId;
    private String userName;
    private String userPass;
    private String userEmail;
    private String joinDate;
    private String salt;
}
