package org.example.backgrupo3vima.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.backgrupo3vima.Entity.ObrasSociales;
import org.example.backgrupo3vima.Service.obrasSociales;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/obrasSociales")
@Tag(name = "Obras Sociales", description = "Gestión de obras sociales del sistema")
public class obrasSocialesController {
    @Autowired
    private obrasSociales obrasSocialesService;

    @Autowired
    private obrasSociales obrasSocialesRepository;


    @Operation(summary = "Obtener todas las obras sociales", description = "Devuelve una lista con todas las obras sociales disponibles en el sistema.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de obras sociales obtenida exitosamente."),
            @ApiResponse(responseCode = "404", description = "No se encontraron obras sociales."),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta.")
    })
    @GetMapping("/todos")
    public ResponseEntity<List<ObrasSociales>> todaslasObrasSociales() {
        List<ObrasSociales> obrasSociales = obrasSocialesService.todasLasObrasSociales();
        return ResponseEntity.ok(obrasSociales);
    }
}
