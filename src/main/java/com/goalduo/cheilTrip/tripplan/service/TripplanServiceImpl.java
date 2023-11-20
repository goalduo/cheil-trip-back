package com.goalduo.cheilTrip.tripplan.service;

import com.goalduo.cheilTrip.jwt.JwtProvider;
import com.goalduo.cheilTrip.tripplan.dto.TripCourse;
import com.goalduo.cheilTrip.tripplan.dto.Tripplan;
import com.goalduo.cheilTrip.tripplan.dto.TripplanCourseInsertDto;
import com.goalduo.cheilTrip.tripplan.dto.TripplanDto;
import com.goalduo.cheilTrip.tripplan.mapper.TripplanMaper;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TripplanServiceImpl implements TripplanService{

    private final TripplanMaper tripplanMaper;
    private final JwtProvider jwtProvider;

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


    @Override
    public int postTripPlanandTripCourses(TripplanDto tripplanDto, String token) {
        Claims claims = jwtProvider.getClaims(token);
        String userId = String.valueOf(claims.get("userId"));
        Tripplan tripplan = new Tripplan();
        tripplan.setPlanName(tripplanDto.getPlanName());
        int insertId = tripplanMaper.insertTripplan(tripplan);
        System.out.println(tripplan);
        TripplanCourseInsertDto tripplanCourseInsertDto = new TripplanCourseInsertDto();
        tripplanCourseInsertDto.setPlanId(tripplan.getPlanId());
        tripplanCourseInsertDto.setUserId(userId);
        tripplanCourseInsertDto.setTripCourseList(tripplanDto.getTripCourseList());
        int result = tripplanMaper.insertTripCourses(tripplanCourseInsertDto);
        return result;
    }


}
