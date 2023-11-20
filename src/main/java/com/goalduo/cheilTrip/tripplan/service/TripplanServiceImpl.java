package com.goalduo.cheilTrip.tripplan.service;

import com.goalduo.cheilTrip.jwt.JwtProvider;
import com.goalduo.cheilTrip.tripplan.dto.*;
import com.goalduo.cheilTrip.tripplan.mapper.TripplanMaper;
import com.goalduo.cheilTrip.util.RedisService;
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
    private final RedisService redisService;

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
        int result = tripplanMaper.insertTripplan(tripplan);
        return tripplan.getPlanId();
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
        tripplan.setHashtags(tripplanDto.getHashtags());
        System.out.println(tripplan);
        int insertId = tripplanMaper.insertTripplan(tripplan);
        System.out.println(tripplan);
        TripplanCourseInsertDto tripplanCourseInsertDto = new TripplanCourseInsertDto();
        tripplanCourseInsertDto.setPlanId(tripplan.getPlanId());
        tripplanCourseInsertDto.setUserId(userId);
        tripplanCourseInsertDto.setTripCourseList(tripplanDto.getTripCourseList());
        System.out.println(tripplanCourseInsertDto);
        int result = tripplanMaper.insertTripCourses(tripplanCourseInsertDto);
        return result;
    }

    @Override
    public void addUserIdAtAttraction(TripplanUserDto tripplanUserDto) {
        int planId = tripplanUserDto.getPlanId();
        String userId = tripplanUserDto.getUserId();
        redisService.addToSet(String.valueOf(planId), userId);
    }

    @Override
    public int isUserInAttractionSet(int id, String userId) {
        boolean valueInSet = redisService.isValueInSet(String.valueOf(id), userId);
        if (valueInSet) return 1;
        else return 0;
    }

    @Override
    public List<TripplanDto> getTripplanByUserId(String userId) {
        return tripplanMaper.getTripplanByUserId(userId);
    }


}
