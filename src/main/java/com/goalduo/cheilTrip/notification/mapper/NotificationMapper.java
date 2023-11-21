package com.goalduo.cheilTrip.notification.mapper;

import com.goalduo.cheilTrip.notification.dto.Notification;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NotificationMapper {
    int saveNotification(Notification notification);
    List<Notification> findAllByFromId(String toId);

    Notification findNotificationById(int notificationId);
}
