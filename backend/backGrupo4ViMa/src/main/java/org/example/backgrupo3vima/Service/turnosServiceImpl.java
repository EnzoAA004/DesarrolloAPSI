package org.example.backgrupo3vima.Service;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.firebase.cloud.StorageClient;
import org.example.backgrupo3vima.Entity.Doctor;
import org.example.backgrupo3vima.Entity.Turnos;
import org.example.backgrupo3vima.Entity.User;
import org.example.backgrupo3vima.Repository.doctorRepository;
import org.example.backgrupo3vima.Repository.turnosRepository;
import org.example.backgrupo3vima.Repository.userRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class turnosServiceImpl implements turnosService {

    @Autowired
    private turnosRepository turnosRepository;
    @Autowired
    private userRepository userRepository;

    @Autowired
    private doctorRepository doctorRepository;

    @Autowired
    private turnosRepository turnoRepository;



    @Override
    public User obtenerUsuarioPorId(Integer usuarioId) {
        return userRepository.findById(Long.valueOf(usuarioId)).orElse(null);
    }

    @Override
    public List<Turnos> listarTurnosPorUsuario(int usuarioId) {
        return turnosRepository.findByUsuarioId(usuarioId);
    }

    @Override
    public String guardarArchivo(MultipartFile archivo) {
        try {
            String nombreArchivo = archivo.getOriginalFilename();

            // Inicializar Firebase Storage
            Bucket bucket = StorageClient.getInstance().bucket();

            // Subir el archivo al bucket
            Blob blob = bucket.create(nombreArchivo, archivo.getInputStream(), archivo.getContentType());

            // Retornar la URL pública del archivo
            return String.format("https://storage.googleapis.com/%s/%s", bucket.getName(), blob.getName());
        } catch (IOException e) {
            throw new RuntimeException("Error al guardar el archivo en Firebase Storage: " + e.getMessage());
        }
    }



    @Override
    public List<LocalDate> obtenerDiasDisponibles(int idDoctor) {
        Doctor doctor = doctorRepository.findById(idDoctor)
                .orElseThrow(() -> new RuntimeException("Doctor no encontrado"));

        // Suponiendo que trabaja de lunes a viernes durante el próximo mes
        LocalDate hoy = LocalDate.now();
        LocalDate fin = hoy.plusMonths(1);

        List<LocalDate> diasConEspacio = new ArrayList<>();

        for (LocalDate fecha = hoy; !fecha.isAfter(fin); fecha = fecha.plusDays(1)) {
            if (fecha.getDayOfWeek() == DayOfWeek.SATURDAY || fecha.getDayOfWeek() == DayOfWeek.SUNDAY) {
                continue;
            }

            List<Turnos> turnosEnEseDia = turnoRepository.findByDoctorIdAndFechaTurno(idDoctor, fecha);

            int espaciosOcupados = turnosEnEseDia.size();

            int cantidadMaximaTurnos = calcularCantidadTurnosPosibles(doctor.getHorarioInicio(), doctor.getHorarioFin());

            if (espaciosOcupados < cantidadMaximaTurnos) {
                diasConEspacio.add(fecha);
            }
        }

        return diasConEspacio;
    }

    private int calcularCantidadTurnosPosibles(LocalTime inicio, LocalTime fin) {
        Duration duracion = Duration.between(inicio, fin);
        return (int) (duracion.toMinutes() / 30); // turnos de 30 minutos
    }

    @Override
    public List<LocalTime> obtenerHorariosDisponibles(int idDoctor, LocalDate fecha) {
        Doctor doctor = doctorRepository.findById(idDoctor)
                .orElseThrow(() -> new RuntimeException("Doctor no encontrado"));

        LocalTime inicio = doctor.getHorarioInicio();
        LocalTime fin = doctor.getHorarioFin();

        List<Turnos> turnosOcupados = turnoRepository.findByDoctorIdAndFechaTurno(idDoctor, fecha);

        Set<LocalTime> horariosOcupados = turnosOcupados.stream()
                .map(Turnos::getHoraInicio)
                .collect(Collectors.toSet());

        List<LocalTime> horariosDisponibles = new ArrayList<>();
        for (LocalTime t = inicio; t.isBefore(fin); t = t.plusMinutes(30)) {
            if (!horariosOcupados.contains(t)) {
                horariosDisponibles.add(t);
            }
        }

        return horariosDisponibles;
    }



}
