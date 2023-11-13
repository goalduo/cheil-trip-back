package com.goalduo.cheilTrip.tripplan.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TripCourse {
    private int courseId;
    private int planId;
    private String userId;
    private String addressName;
    private String categoryGroupName;
    private String placeName;
    private String placeUrl;
    private String roadAddressName;
    private String x;
    private String y;
}
