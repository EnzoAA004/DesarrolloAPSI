package org.example.backgrupo3vima.Service;


import org.example.backgrupo3vima.Entity.Turnos;
import org.example.backgrupo3vima.Entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface turnosService {
    User obtenerUsuarioPorId(Integer usuarioId);

    List<Turnos> listarTurnosPorUsuario(int usuarioId);

    String guardarArchivo (MultipartFile archivo);

    List<LocalDate> obtenerDiasDisponibles(int idDoctor);

    List<LocalTime> obtenerHorariosDisponibles(int idDoctor, LocalDate fecha);
}
