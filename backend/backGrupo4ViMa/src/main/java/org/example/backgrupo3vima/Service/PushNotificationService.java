package org.example.backgrupo3vima.Service;

import org.example.backgrupo3vima.Entity.User;

import java.util.Map;

public interface PushNotificationService {
    //void enviarNotificacion(User usuario, String titulo, String mensaje);

    void enviarNotificacionFirebase(String fcmToken, String titulo, String cuerpo, Map<String, String> data);


    void enviarNotificacion(User usuario, String recordatorioDeTurno, String s);
}
