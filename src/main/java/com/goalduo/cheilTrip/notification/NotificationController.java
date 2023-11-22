package com.goalduo.cheilTrip.notification;

import com.goalduo.cheilTrip.notification.dto.Notification;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;


@RestController
@RequestMapping("/notification")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping(value = "/connect", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<SseEmitter> connect(@RequestParam String userId) {
        return new ResponseEntity<>(notificationService.connection(userId), HttpStatus.OK);
    }

    @GetMapping(value = "/user")
    public ResponseEntity<?> getNotificationByUserId(@RequestParam String userId) {
        List<Notification> notificationList = notificationService.getNotificationByUserId(userId);
        return new ResponseEntity<>(notificationList,HttpStatus.OK);
    }
}