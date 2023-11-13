package com.goalduo.cheilTrip.tripplan.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Tripplan {
    private String planId;
    private String planName;
    private String joinDate;
    private List<TripCourse> tripCourseList;

}
