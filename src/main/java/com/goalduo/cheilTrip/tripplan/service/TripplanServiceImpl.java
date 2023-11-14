package com.goalduo.cheilTrip.tripplan.service;

import com.goalduo.cheilTrip.tripplan.dto.Tripplan;
import com.goalduo.cheilTrip.tripplan.dto.TripplanCourseInsertDto;
import com.goalduo.cheilTrip.tripplan.dto.TripplanDto;
import com.goalduo.cheilTrip.tripplan.mapper.TripplanMaper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TripplanServiceImpl implements TripplanService{

    private final TripplanMaper tripplanMaper;

    @Override
    public TripplanDto getTripplanAndTripCoursesByPlanId(int planId) {
        return tripplanMaper.getTripplanAndTripCoursesByPlanId(planId);
    }

    @Override
    public List<TripplanDto> getTripplansAndTripCoursesByUserId(String userId) {
        return tripplanMaper.getTripplansAndTripCoursesByUserId(userId);
    }

    @Override
    @Transactional
    public int insertTripplan(Tripplan tripplan) {
        return tripplanMaper.insertTripplan(tripplan);
    }

    @Override
    @Transactional
    public int insertTripCourses(TripplanCourseInsertDto tripplanCourseInsertDto) {
        return tripplanMaper.insertTripCourses(tripplanCourseInsertDto);
    }

    @Override
    @Transactional
    public int deleteTripplan(int planId) {
        return tripplanMaper.deleteTripplan(planId);
    }

    @Override
    public int countByPlanId(int planId) {
        return tripplanMaper.countByPlanId(planId);
    }
}
