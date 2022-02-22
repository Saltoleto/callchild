package br.com.callthechild.service;


import br.com.callthechild.dto.CallTheChildrenEventDTO;

public interface NotificationService {

    void sendNotification(CallTheChildrenEventDTO event);
}
