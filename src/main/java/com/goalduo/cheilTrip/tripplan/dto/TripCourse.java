package com.goalduo.cheilTrip.tripplan.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
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
