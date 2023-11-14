package com.goalduo.cheilTrip.jwt;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class JwtToken {
    private String userId;
    private String userName;
    private String accessToken;
    private String refreshToken;
}
