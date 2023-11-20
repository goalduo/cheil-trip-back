package com.goalduo.cheilTrip.tripplan.service;

import com.goalduo.cheilTrip.tripplan.dto.Tripplan;
import com.goalduo.cheilTrip.tripplan.dto.TripplanCourseInsertDto;
import com.goalduo.cheilTrip.tripplan.dto.TripplanDto;
import com.goalduo.cheilTrip.tripplan.dto.TripplanUserDto;

import java.util.List;

public interface TripplanService {
    TripplanDto getTripplanAndTripCoursesByPlanId(int planId);

    List<TripplanDto> getTripplansAndTripCoursesByUserId(String userId);

    int insertTripplan(Tripplan tripplan);

    int insertTripCourses(TripplanCourseInsertDto tripplanCourseInsertDto);

    int deleteTripplan(int planId);

    int countByPlanId(int planId);


    int postTripPlanandTripCourses(TripplanDto tripplanDto, String token);

    void addUserIdAtAttraction(TripplanUserDto tripplanUserDto);

    int isUserInAttractionSet(int id, String userId);

    List<TripplanDto> getTripplanByUserId(String userId);
}
