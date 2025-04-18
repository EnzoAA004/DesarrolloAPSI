package org.example.backgrupo3vima.Service;

import org.example.backgrupo3vima.Entity.User;
import org.springframework.stereotype.Service;

@Service
public class PushNotificationServiceImpl implements PushNotificationService{

    @Override
    public void enviarNotificacion(User usuario, String titulo, String mensaje) {
        // Implementa la lógica para enviar la notificación push
        // Por ejemplo, usando Firebase Cloud Messaging (FCM)
    }

}
