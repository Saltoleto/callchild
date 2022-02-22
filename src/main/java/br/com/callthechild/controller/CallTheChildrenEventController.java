package br.com.callthechild.controller;

import br.com.callthechild.dto.CallTheChildrenEventDTO;
import br.com.callthechild.service.EmitterService;
import br.com.callthechild.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;


@Slf4j
@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class CallTheChildrenEventController {

    private final EmitterService emitterService;
    private final NotificationService notificationService;

    @GetMapping("/{ageGroup}")
    public SseEmitter subscribeToEvents(@PathVariable long ageGroup) {
        return emitterService.createEmitter(ageGroup);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void publishEvent(@RequestBody CallTheChildrenEventDTO event) {
        notificationService.sendNotification(event);
    }
}
