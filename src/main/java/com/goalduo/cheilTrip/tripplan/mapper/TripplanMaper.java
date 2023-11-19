package com.goalduo.cheilTrip.tripplan.mapper;

import com.goalduo.cheilTrip.tripplan.dto.Tripplan;
import com.goalduo.cheilTrip.tripplan.dto.TripplanDto;
import com.goalduo.cheilTrip.tripplan.dto.TripplanCourseInsertDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TripplanMaper {

    TripplanDto getTripplanAndTripCoursesByPlanId(int planId);
    List<TripplanDto> getTripplansAndTripCoursesByUserId(String userId);

    int insertTripplan(Tripplan tripplan);

    int insertTripCourses(TripplanCourseInsertDto tripplanCourseInsertDto);

    int deleteTripplan(int planId);

    int countByPlanId(int planId);

}
