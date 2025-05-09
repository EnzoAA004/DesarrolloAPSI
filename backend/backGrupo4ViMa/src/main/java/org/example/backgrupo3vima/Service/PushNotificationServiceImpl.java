package org.example.backgrupo3vima.Service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import org.example.backgrupo3vima.Entity.User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PushNotificationServiceImpl implements PushNotificationService {

    @Override
    public void enviarNotificacionFirebase(String fcmToken, String titulo, String cuerpo, Map<String, String> data) {
        Message message = Message.builder()
                .putAllData(data)
                .setToken(fcmToken)
                .setNotification(Notification.builder()
                        .setTitle(titulo)
                        .setBody(cuerpo)
                        .build())
                .build();

        try {
            String response = FirebaseMessaging.getInstance().send(message);
            System.out.println("Notificación enviada con éxito: " + response);
        } catch (FirebaseMessagingException e) {
            System.err.println("Error al enviar la notificación: " + e.getMessage());
        }
    }

    @Override
    public void enviarNotificacion(User usuario, String recordatorioDeTurno, String s) {
        if (usuario.getFcmToken() == null || usuario.getFcmToken().isEmpty()) {
            System.err.println("El usuario no tiene un token FCM válido.");
            return;
        }
        Map<String, String> data = Map.of("detalle", s);
        enviarNotificacionFirebase(usuario.getFcmToken(), "Recordatorio", recordatorioDeTurno, data);
    }
}