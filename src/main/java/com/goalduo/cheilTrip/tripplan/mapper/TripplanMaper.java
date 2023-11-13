package com.goalduo.cheilTrip.tripplan.mapper;

import com.goalduo.cheilTrip.tripplan.dto.Tripplan;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TripplanMaper {

    Tripplan getTripplanAndTripCoursesByPlanId(int planId);

}
