package com.goalduo.cheilTrip.tripplan.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Tripplan {
    private int planId;
    private String planName;
    private String hashtags;
    private String planCreatedAt;
}
