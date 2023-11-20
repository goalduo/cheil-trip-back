package com.goalduo.cheilTrip.tripplan.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
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
    private String hashtags;
    private String planCreatedAt;
    private List<TripCourse> tripCourseList;
}
