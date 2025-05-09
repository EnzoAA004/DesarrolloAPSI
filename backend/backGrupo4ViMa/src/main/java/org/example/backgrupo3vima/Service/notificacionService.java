package org.example.backgrupo3vima.Service;

import org.example.backgrupo3vima.Entity.Notificaciones;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface notificacionService {
    void crearYEnviarNotificacion(int userId, String tipo, String mensaje, String logo);

    List<Notificaciones> getAllNotificacionesByUserId(Long id);

}
