package com.goalduo.cheilTrip.tripplan.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TripplanDto {
    private String planId;
    private String planName;
    private String planCreatedAt;
    private List<TripCourse> tripCourseList;
}
