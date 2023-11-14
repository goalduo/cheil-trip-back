package com.goalduo.cheilTrip.tripplan.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TripplanCourseInsertDto {
    private int courseId;
    private int planId;
    private String userId;
    private List<TripCourse> tripCourseList;
}
