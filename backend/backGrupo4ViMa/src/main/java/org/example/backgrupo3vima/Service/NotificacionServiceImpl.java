package org.example.backgrupo3vima.Service;

import org.example.backgrupo3vima.Entity.TurnoEstado;
import org.example.backgrupo3vima.Entity.Turnos;
import org.example.backgrupo3vima.Repository.turnosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificacionServiceImpl implements NotificacionService {

    @Autowired
    private turnosRepository turnosRepository;

    @Autowired
    private PushNotificationServiceImpl pushNotificationService; // Servicio para enviar notificaciones

    @Scheduled(fixedRate = 3600000) // Ejecuta cada hora
    public void enviarNotificaciones() {
        LocalDateTime ahora = LocalDateTime.now();
        LocalDateTime dentroDe24Horas = ahora.plusHours(24);

        // Buscar turnos que ocurren dentro de 24 horas y están pendientes
        List<Turnos> turnos = turnosRepository.findByFechaTurnoAndEstado(LocalDate.from(dentroDe24Horas), TurnoEstado.PENDIENTE);

        for (Turnos turno : turnos) {
            pushNotificationService.enviarNotificacion(turno.getUsuario(), "Recordatorio de turno", "Tienes un turno programado en 24 horas.");
        }
    }
}
