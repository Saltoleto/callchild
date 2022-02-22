package br.com.callthechild.service;

import br.com.callthechild.dto.CallTheChildrenEventDTO;
import br.com.callthechild.repository.EmitterRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@Service
@Primary
@AllArgsConstructor
@Slf4j
public class SseNotificationService implements NotificationService {

    private final EmitterRepository emitterRepository;

    @Override
    public void sendNotification(CallTheChildrenEventDTO event) {
        if (event == null) {
            log.debug("No server event to send to device.");
            return;
        }
        doSendNotification(event);
    }

    private void doSendNotification(CallTheChildrenEventDTO event) {
        emitterRepository.get(event.getAgeGroup()).ifPresentOrElse(sseEmitter -> {
            try {
                sseEmitter.send(SseEmitter.event()
                        .name(String.valueOf(event.getAgeGroup()))
                        .data(event.getNumberChild()));
            } catch (IOException | IllegalStateException e) {
                emitterRepository.remove(event.getAgeGroup());
            }
        }, () -> log.debug("No emitter for ageGroup {}", event.getAgeGroup()));
    }

}
