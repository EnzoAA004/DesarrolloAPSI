package org.example.backgrupo3vima.Service;

import org.example.backgrupo3vima.Entity.User;

public interface PushNotificationService {
    void enviarNotificacion(User usuario, String titulo, String mensaje);
}
