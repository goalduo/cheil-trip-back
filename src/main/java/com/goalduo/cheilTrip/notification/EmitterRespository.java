package com.goalduo.cheilTrip.notification;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
@Slf4j
public class EmitterRespository {

    public static final long TIMEOUT = 60L * 1000;
    public static Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();

    SseEmitter save(String userId, SseEmitter emitter) {
        emitters.put(userId, emitter);
        log.info("new emitter added: {}", emitter);
        return emitter;
    }
    public Map<String, SseEmitter> findAllStartById(String id) {
        return emitters.entrySet().stream()
                .filter(entry -> entry.getKey().equals(id))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
    public void deleteAllStartByWithId(String id) {
        emitters.forEach((key, emitter) -> {
            if (key.startsWith(id)) emitters.remove(key);
        });
    }


}
