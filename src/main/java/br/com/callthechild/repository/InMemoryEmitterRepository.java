package br.com.callthechild.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@RequiredArgsConstructor
@Slf4j
public class InMemoryEmitterRepository implements EmitterRepository {

    private Map<Long, SseEmitter> userEmitterMap = new ConcurrentHashMap<>();

    @Override
    public void addOrReplaceEmitter(long ageGroup, SseEmitter emitter) {
        userEmitterMap.put(ageGroup, emitter);
    }

    @Override
    public void remove(long ageGroup) {
        if (userEmitterMap != null && userEmitterMap.containsKey(ageGroup)) {
            log.debug("Removing emitter for ageGroup: {}", ageGroup);
            userEmitterMap.remove(ageGroup);
        } else {
            log.debug("No emitter to remove for ageGroup: {}", ageGroup);
        }
    }

    @Override
    public Optional<SseEmitter> get(long memberId) {
        return Optional.ofNullable(userEmitterMap.get(memberId));
    }
}
