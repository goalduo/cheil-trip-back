package com.goalduo.cheilTrip.notification.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Notification {
    int NotificationId;
    String fromId;
    String toId;
    int planId;
    String createdAt;
}
