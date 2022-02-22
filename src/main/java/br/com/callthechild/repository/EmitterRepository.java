package br.com.callthechild.repository;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Optional;

public interface EmitterRepository {

    void addOrReplaceEmitter(long ageGroup, SseEmitter emitter);

    void remove(long ageGroup);

    Optional<SseEmitter> get(long ageGroup);
}
