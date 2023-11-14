package com.goalduo.cheilTrip.tripplan.mapper;

import com.goalduo.cheilTrip.tripplan.dto.Tripplan;
import com.goalduo.cheilTrip.tripplan.dto.TripplanCourseInsertDto;
import com.goalduo.cheilTrip.tripplan.dto.TripplanDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.sql.SQLException;
import java.util.List;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // 실제 db로 테스트
public class TripplanMapperTest {

    @Autowired
    private TripplanMaper tripplanMaper;

    @Test
    @DisplayName("여행 planId로 여행과 여행 course 같이 가져올 수 있다.")
    void getTripplanAndTripCoursesByPlanId() throws SQLException {
        TripplanDto tripplanAndCourses = tripplanMaper.getTripplanAndTripCoursesByPlanId(1);
        System.out.println(tripplanAndCourses);
    }

    @Test
    @DisplayName("회원 userId로 여행과 여행 course 같이 가져올 수 있다.")
    void getTripplansAndTripCoursesByuserId() throws SQLException {
        List<TripplanDto> tripplanList = tripplanMaper.getTripplansAndTripCoursesByuserId("ssafy");
        System.out.println(tripplanList);
    }

    @Test
    @DisplayName("Tripplan을 추가한다.")
    void insertTripplan(){
        Tripplan tripplan = new Tripplan();
        tripplan.setPlanName("db테스트");
        int result = tripplanMaper.insertTripplan(tripplan);
        System.out.println(tripplan);
    }

    @Test
    @DisplayName("Tripcourse를 추가한다.")
    void insertTripCourses(){
        TripplanCourseInsertDto dto = new TripplanCourseInsertDto();
        // TODO
        // dto 파라미터 세팅 후, 테스트 해볼것
    }

    @Test
    @DisplayName("Tripplan을 삭제한다.")
    void deleteTripplan(){
        TripplanDto tripplanAndCourses1 = tripplanMaper.getTripplanAndTripCoursesByPlanId(2);
        System.out.println(tripplanAndCourses1);
        int result = tripplanMaper.deleteTripplan(2);
        TripplanDto tripplanAndCourses2 = tripplanMaper.getTripplanAndTripCoursesByPlanId(2);
        System.out.println(tripplanAndCourses2);
    }

    @Test
    @DisplayName("여행계획의 갯수를 계산한다.")
    void countByPlanId(){
        int count = tripplanMaper.countByPlanId(1);
        System.out.println(count);
    }
}
