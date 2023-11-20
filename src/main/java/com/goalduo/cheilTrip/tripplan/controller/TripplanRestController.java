package com.goalduo.cheilTrip.tripplan.controller;

import com.goalduo.cheilTrip.tripplan.dto.Tripplan;
import com.goalduo.cheilTrip.tripplan.dto.TripplanDto;
import com.goalduo.cheilTrip.tripplan.dto.TripplanUserDto;
import com.goalduo.cheilTrip.tripplan.service.TripplanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/tripplan")
public class TripplanRestController {

    private final TripplanService tripplanService;

    @GetMapping("/{planId}")
    public ResponseEntity<?> getTripplanByplanId(@PathVariable Integer planId){
        TripplanDto result = tripplanService.getTripplanAndTripCoursesByPlanId(planId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/planId")
    public ResponseEntity<?> getTripplan(){
        Tripplan tripplan = new Tripplan();
        tripplan.setPlanName("");
        int id = tripplanService.insertTripplan(tripplan);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> postTripPlanandTripCourses(@RequestBody TripplanDto tripplanDto,
                                                        @RequestHeader("Authorization") String token){
        int i = tripplanService.postTripPlanandTripCourses(tripplanDto, token);
        return new ResponseEntity<>(i,HttpStatus.OK);
    }

    @PostMapping("/user")
    public ResponseEntity<?> addUserIdAtAttraction(@RequestBody TripplanUserDto tripplanUserDto){
        tripplanService.addUserIdAtAttraction(tripplanUserDto);
        return new ResponseEntity<>(1, HttpStatus.OK);
    }

    @GetMapping("/check")
    public ResponseEntity<?> isUserInAttractionSet(@RequestParam int id, @RequestParam String userId){
        int result = tripplanService.isUserInAttractionSet(id, userId);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getTripplanByUserId(@PathVariable String userId){
        List<TripplanDto> tripplanDtoList = tripplanService.getTripplanByUserId(userId);
        return new ResponseEntity<>(tripplanDtoList, HttpStatus.OK);
    }

}
