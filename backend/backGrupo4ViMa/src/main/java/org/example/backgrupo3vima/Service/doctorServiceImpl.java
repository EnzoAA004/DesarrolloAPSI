package org.example.backgrupo3vima.Service;

import org.example.backgrupo3vima.Entity.Doctor;
import org.example.backgrupo3vima.Entity.dto.DoctorDTO;
import org.example.backgrupo3vima.Repository.doctorRepository;
import org.example.backgrupo3vima.Repository.turnosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class doctorServiceImpl implements doctorService {

    @Autowired
    private doctorRepository doctorRepository;

    @Autowired
    private turnosRepository turnosRepository;

    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter timestampFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public List<Doctor> todosLosDoctores() {
        return doctorRepository.findAll();
    }


    @Override
    public void crearDoctor(DoctorDTO doctorDTO) {
        Doctor doctor = new Doctor();
        doctor.setNombre(doctorDTO.getNombre());
        doctor.setApellido(doctorDTO.getApellido());
        doctor.setIdEspecialidad(doctorDTO.getIdEspecialidad());
        doctor.setTelefono(doctorDTO.getTelefono());
        doctor.setCorreo(doctorDTO.getCorreo());
        doctor.setHorarioInicio(LocalTime.parse(doctorDTO.getHorarioInicio()));
        doctor.setHorarioFin(LocalTime.parse(doctorDTO.getHorarioFin()));
        doctor.setInformacionAdicional(doctorDTO.getInformacionAdicional());
        doctor.setActivo(!doctorDTO.isEliminado()); // Set activo based on eliminado

        doctorRepository.save(doctor);
    }

    @Override
    public void actualizarDoctor(Doctor doctor) {
        Doctor doctorExistente = doctorRepository.findById(doctor.getId())
                .orElseThrow(() -> new RuntimeException("Doctor no encontrado"));

        // Actualizar los campos necesarios
        doctorExistente.setNombre(doctor.getNombre());
        doctorExistente.setApellido(doctor.getApellido());
        doctorExistente.setIdEspecialidad(doctor.getIdEspecialidad());
        doctorExistente.setTelefono(doctor.getTelefono());
        doctorExistente.setCorreo(doctor.getCorreo());
        doctorExistente.setHorarioInicio(doctor.getHorarioInicio());
        doctorExistente.setHorarioFin(doctor.getHorarioFin());
        doctorExistente.setInformacionAdicional(doctor.getInformacionAdicional());
        doctorExistente.setActivo(doctor.isActivo());

        // Guardar los cambios
        doctorRepository.save(doctorExistente);
    }

    @Override
    public boolean verificarDisponibilidadTurno(Integer doctorId, String fechaInicio, String fechaFin, String horaInicio, String horaFin) {
        LocalDate fechaInicioParsed = LocalDate.parse(fechaInicio, dateFormatter);
        LocalDate fechaFinParsed = LocalDate.parse(fechaFin, dateFormatter);
        Timestamp horaInicioParsed = Timestamp.valueOf(horaInicio);
        Timestamp horaFinParsed = Timestamp.valueOf(horaFin);

        return !turnosRepository.existsTurnoAsignado(doctorId, fechaInicioParsed, fechaFinParsed, horaInicioParsed, horaFinParsed);
    }

    @Override
    public Doctor obtenerDoctorPorId(Integer doctorId) {
        return doctorRepository.findById(doctorId).orElse(null);
    }


    @Override
    public void actualizarCalificacion(int doctorId, int calificacion) {
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow(() -> new RuntimeException("Doctor no encontrado"));

        doctor.setCalificacionTotal(doctor.getCalificacionTotal() + calificacion);
        doctor.setNumeroCalificaciones(doctor.getNumeroCalificaciones() + 1);
        doctor.setCalificacionPromedio(doctor.getCalificacionTotal() / doctor.getNumeroCalificaciones());

        doctorRepository.save(doctor);
    }


    @Override
    public List<Doctor> buscarPorFiltros(Integer idEspecialidad, String nombreCompleto, String horaInicio, String horaFin, String fecha, Integer calificacion) {
        List<Doctor> todosDoctores = doctorRepository.findAll();

        return todosDoctores.stream()
                .filter(doc -> idEspecialidad == null || doc.getIdEspecialidad() == idEspecialidad)
                .filter(doc -> nombreCompleto == null || (doc.getNombre() + " " + doc.getApellido()).toLowerCase().contains(nombreCompleto.toLowerCase()))
                .filter(doc -> calificacion == null || doc.getCalificacionPromedio() >= calificacion)
                .filter(doc -> {
                    // Como no tenés una lista de "disponibilidades", usaremos los Timestamp fijos del doctor
                    if (horaInicio == null && horaFin == null) return true;

                    boolean horaInicioOk = true;
                    boolean horaFinOk = true;

                    if (horaInicio != null) {
                        try {
                            LocalTime horaInicioFiltro = LocalTime.parse(horaInicio);
                            horaInicioOk = !doc.getHorarioInicio().isAfter(horaInicioFiltro);
                        } catch (Exception e) {
                            horaInicioOk = false;
                        }
                    }

                    if (horaFin != null) {
                        try {
                            LocalTime horaFinFiltro = LocalTime.parse(horaFin);
                            horaFinOk = !doc.getHorarioFin().isBefore(horaFinFiltro);
                        } catch (Exception e) {
                            horaFinOk = false;
                        }
                    }

                    return horaInicioOk && horaFinOk;
                })
                .collect(Collectors.toList());
    }


}
