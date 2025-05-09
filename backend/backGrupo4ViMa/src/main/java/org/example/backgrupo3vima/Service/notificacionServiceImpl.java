package org.example.backgrupo3vima.Service;

import org.example.backgrupo3vima.Entity.Notificaciones;
import org.example.backgrupo3vima.Entity.TurnoEstado;
import org.example.backgrupo3vima.Entity.Turnos;
import org.example.backgrupo3vima.Entity.User;
import org.example.backgrupo3vima.Repository.notificacionesRepository;
import org.example.backgrupo3vima.Repository.turnosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class notificacionServiceImpl implements notificacionService {

    @Autowired
    private turnosRepository turnosRepository;

    @Autowired
    private notificacionesRepository notificacionesRepository; // Repositorio para guardar notificaciones

    @Autowired
    private PushNotificationServiceImpl pushNotificationService; // Servicio para enviar notificaciones

    @Autowired
    private userService userService;

    @Override
    public void crearYEnviarNotificacion(int userId, String tipo, String mensaje, String logo) {
        User usuario = userService.obtenerPorId((long) userId);

        Notificaciones notificacion = new Notificaciones();
        notificacion.setUser(usuario);
        notificacion.setTipoNotificacion(tipo);
        notificacion.setMensaje(mensaje);
        notificacion.setLogoNotificacion(logo);
        notificacion.setEstado("NO_LEÍDA");

        // Guarda en base de datos
        notificacionesRepository.save(notificacion);

        // Enviar push si tiene token
        if (usuario.getFcmToken() != null && !usuario.getFcmToken().isEmpty()) {
            Map<String, String> data = new HashMap<>();
            data.put("id", String.valueOf(notificacion.getId()));
            data.put("tipo", tipo);
            data.put("mensaje", mensaje);
            data.put("logo", logo);
            data.put("estado", "NO_LEÍDA");

            pushNotificationService.enviarNotificacionFirebase(
                    usuario.getFcmToken(),
                    tipo,
                    mensaje,
                    data
            );
        }
    }


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

    @Override
    public List<Notificaciones> getAllNotificacionesByUserId(Long id) {
        return List.of();
    }
}
