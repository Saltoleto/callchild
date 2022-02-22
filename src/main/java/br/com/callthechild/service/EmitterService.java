package br.com.callthechild.service;

import br.com.callthechild.repository.EmitterRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;


@Service
@Slf4j
public class EmitterService {

    private final long eventsTimeout;
    private final EmitterRepository repository;

    public EmitterService(@Value("${events.connection.timeout}") long eventsTimeout,
                          EmitterRepository repository) {
        this.eventsTimeout = eventsTimeout;
        this.repository = repository;
    }

    public SseEmitter createEmitter(long ageGroup) {
        SseEmitter emitter = new SseEmitter(eventsTimeout);
        emitter.onCompletion(() -> repository.remove(ageGroup));
        emitter.onTimeout(() -> repository.remove(ageGroup));
        emitter.onError(e -> {
            log.error("Create SseEmitter exception", e);
            repository.remove(ageGroup);
        });
        repository.addOrReplaceEmitter(ageGroup, emitter);
        return emitter;
    }

}
