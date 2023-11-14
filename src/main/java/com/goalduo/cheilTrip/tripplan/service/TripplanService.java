package com.goalduo.cheilTrip.tripplan.service;

import com.goalduo.cheilTrip.tripplan.dto.Tripplan;
import com.goalduo.cheilTrip.tripplan.dto.TripplanCourseInsertDto;
import com.goalduo.cheilTrip.tripplan.dto.TripplanDto;

import java.util.List;

public interface TripplanService {
    TripplanDto getTripplanAndTripCoursesByPlanId(int planId);

    List<TripplanDto> getTripplansAndTripCoursesByUserId(String userId);

    int insertTripplan(Tripplan tripplan);

    int insertTripCourses(TripplanCourseInsertDto tripplanCourseInsertDto);

    int deleteTripplan(int planId);

    int countByPlanId(int planId);
}
