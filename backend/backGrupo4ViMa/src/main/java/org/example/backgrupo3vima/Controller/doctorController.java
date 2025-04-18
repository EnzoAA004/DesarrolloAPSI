package org.example.backgrupo3vima.Controller;

import org.example.backgrupo3vima.Entity.Doctor;
import org.example.backgrupo3vima.Service.doctorService;
import org.example.backgrupo3vima.Service.doctorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/doctor")
public class doctorController {
    @Autowired
    private doctorService doctorService;

    //Busqueda de todos los doctores para pruebas
    @GetMapping("/doctores")
    public ResponseEntity<List<Doctor>> listarDoctores(){
        List<Doctor> doctores = doctorService.todosLosDoctores();
        return ResponseEntity.ok(doctores);
    }


    /*
    //Busqueda de doctor por especialidadMedica y su nombre
    @GetMapping("/doctores/especialidad")
    public ResponseEntity<List<Doctor>> listarDoctoresPorEspecialidad(int idEspecialidad){
        List<Doctor> doctores = doctorService.buscarPorEspecialidad(idEspecialidad);
        return ResponseEntity.ok(doctores);
    }

    //Busqueda de doctor por profesional-nombre
    @GetMapping("/doctores/profesional")
    public ResponseEntity<List<Doctor>> listarDoctoresPorNombreCompleto(String nombreCompleto) {
        List<Doctor> doctores = doctorService.buscarPorNombreCompleto(nombreCompleto);
        return ResponseEntity.ok(doctores);
    }*/

    //Busqueda de doctor por especialidad, profesional-nombre y/o rango de fechas
    @GetMapping("/doctores/filtro")
    public ResponseEntity<List<Doctor>> listarDoctoresPorEspecialidadYNombre(
            Integer idEspecialidad, String nombreCompleto, String horaInicio, String horaFin, String fechaInicio, String fechaFin) {
        List<Doctor> doctores = doctorService.buscarPorEspecialidadYNombre(idEspecialidad, nombreCompleto, horaInicio, horaFin, fechaInicio, fechaFin);
        return ResponseEntity.ok(doctores);
    }

    //Busqueda de doctor por fechayhora de atencion

}
