package com.goalduo.cheilTrip.notification;

import com.goalduo.cheilTrip.notification.dto.Notification;
import com.goalduo.cheilTrip.notification.mapper.NotificationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class NotificationService {
    private static final Long DEFAULT_TIMEOUT = 60L * 1000 * 60;

    private final EmitterRespository emitterRepository;
    private final NotificationMapper notificationMapper;

    public SseEmitter connection(String id) {

        // 클라이언트의 sse 연결 요청에 응답하기 위한 SseEmitter 객체 생성
        // 유효시간 지정으로 시간이 지나면 클라이언트에서 자동으로 재연결 요청함
        SseEmitter emitter = emitterRepository.save(id, new SseEmitter(DEFAULT_TIMEOUT));

        // SseEmitter 의 완료/시간초과/에러로 인한 전송 불가 시 sseEmitter 삭제
        emitter.onCompletion(() -> emitterRepository.deleteAllStartByWithId(id));
        emitter.onTimeout(() -> emitterRepository.deleteAllStartByWithId(id));
        emitter.onError((e) -> emitterRepository.deleteAllStartByWithId(id));

        // 연결 직후, 데이터 전송이 없을 시 503 에러 발생. 에러 방지 위한 더미데이터 전송
        sendToClient(emitter, "sse", id ,"연결되었습니다. " + id + "님");
        send(id);
        return emitter;
    }
    public void send(String toId) {

        List<Notification> notificationList = notificationMapper.findAllByFromId(toId);
        // 로그인 한 유저의 SseEmitter 모두 가져오기
        Map<String, SseEmitter> sseEmitters = emitterRepository.findAllStartById(toId);
        sseEmitters.forEach(
                (key, emitter) -> {
                    // 데이터 캐시 저장(유실된 데이터 처리하기 위함)
//                    emitterRepository.saveEventCache(key, notification);
                    // 데이터 전송
                    sendToClient(emitter, "notification", key, notificationList);
                }
        );
    }

    // 3
    private void sendToClient(SseEmitter emitter, String name, String id, Object data) {
        try {
            emitter.send(SseEmitter.event()
                    .id(id)
                    .name(name)
                    .data(data));
        } catch (IOException exception) {
            emitterRepository.deleteAllStartByWithId(id);
            throw new RuntimeException("연결 오류!");
        }
    }

    private Notification createNotification(String fromId, String toId, int tripplanId) {
        return Notification.builder()
                .fromId(fromId)
                .toId(toId)
                .planId(tripplanId)
                .build();
    }

    public void sendNewNotification(String toId, Notification notification) {
        Map<String, SseEmitter> sseEmitters = emitterRepository.findAllStartById(toId);
        sseEmitters.forEach(
                (key, emitter) -> {
                    // 데이터 캐시 저장(유실된 데이터 처리하기 위함)
//                    emitterRepository.saveEventCache(key, notification);
                    // 데이터 전송;
                    sendToClient(emitter, "notification", key, notification);
                }
        );
    }
}