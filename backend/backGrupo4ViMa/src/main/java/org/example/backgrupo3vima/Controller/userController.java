package org.example.backgrupo3vima.Controller;

import jakarta.validation.Valid;
import org.example.backgrupo3vima.Entity.ObrasSociales;
import org.example.backgrupo3vima.Entity.User;
import org.example.backgrupo3vima.Entity.dto.UserRequest;
import org.example.backgrupo3vima.Entity.dto.registroRequestParte1;
import org.example.backgrupo3vima.Entity.dto.registroRequestParte2;
import org.example.backgrupo3vima.Repository.userRepository;
import org.example.backgrupo3vima.Service.tokensService;
import org.example.backgrupo3vima.Service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/Usuario")
@Tag(name = "Usuarios", description = "Gestión de usuarios del sistema")
public class userController {

    @Autowired
    private userService userService;

    @Autowired
    private userRepository userRepository;

    @Autowired
    private tokensService tokensService;

    @Operation(summary = "Obtener todos los usuarios", description = "Devuelve una lista de todos los usuarios registrados en el sistema.")
    @GetMapping("/usuarios")
    public ResponseEntity<List<User>> todosLosUsuarios() {
        List<User> userList = userService.getAllUsers();
        return ResponseEntity.ok(userList);
    }

    //    @Operation(summary = "Registrar un nuevo usuario", description = "Crea un nuevo usuario en el sistema con los datos proporcionados.")
//    @PostMapping("/registrar")
//    public ResponseEntity<?> crearUsuario(@RequestBody UserRequest userRequest) {
//        // Validar que todos los campos tengan información
//        StringBuilder errores = new StringBuilder();
//        if (userRequest.getCorreo() == null || userRequest.getCorreo().isEmpty()) errores.append("El campo 'correo' es obligatorio. ");
//        if (userRequest.getDni() == null || userRequest.getDni().isEmpty()) errores.append("El campo 'dni' es obligatorio. ");
//        if (userRequest.getNombre() == null || userRequest.getNombre().isEmpty()) errores.append("El campo 'nombre' es obligatorio. ");
//        if (userRequest.getApellido() == null || userRequest.getApellido().isEmpty()) errores.append("El campo 'apellido' es obligatorio. ");
//        if (userRequest.getContrasenia() == null || userRequest.getContrasenia().isEmpty()) errores.append("El campo 'contrasenia' es obligatorio. ");
//        if (userRequest.getEdad() == null) errores.append("El campo 'edad' es obligatorio. ");
//        if (userRequest.getFechaNacimiento() == null) errores.append("El campo 'fechaNacimiento' es obligatorio. ");
//        if (userRequest.getIdObraSocial() == null) errores.append("El campo 'idObraSocial' es obligatorio. ");
//
//        if (errores.length() > 0) {
//            return ResponseEntity.badRequest().body(Map.of("errores", errores.toString().trim()));
//        }
//
//        User user = new User();
//        user.setCorreo(userRequest.getCorreo());
//        user.setDni(userRequest.getDni());
//        user.setNombre(userRequest.getNombre());
//        user.setApellido(userRequest.getApellido());
//        user.setContrasenia(userRequest.getContrasenia());
//        user.setEdad(userRequest.getEdad());
//        user.setGenero(userRequest.getGenero()); // M o F para masculino o femenino. Luego O para otro.
//        user.setFechaNacimiento(userRequest.getFechaNacimiento());
//
//        // Buscar la obra social por ID
//        ObrasSociales obraSocial = userService.getObraSocialById(userRequest.getIdObraSocial());
//        if (obraSocial == null) {
//            return ResponseEntity.badRequest().body(Map.of("error", "No se encontró la obra social con el ID proporcionado."));
//        }
//        user.setObraSocial(obraSocial);
//
//        User nuevoUsuario = userService.crearUsuario(user);
//        return ResponseEntity.ok(Map.of("mensaje", "Usuario creado exitosamente. Ahora ve a iniciar sesión.", "usuario", nuevoUsuario));
//    }
    @Operation(summary = "Registrar datos básicos del usuario", description = "Registra los datos básicos del usuario en el sistema.")
    @PostMapping("/registrar-parte1")
    public ResponseEntity<?> registrarParte1(@RequestBody @Valid registroRequestParte1 datosBasicos) {
        if (!datosBasicos.getContrasenia().equals(datosBasicos.getRepetirContrasenia())) {
            return ResponseEntity.badRequest().body(Map.of("error", "Las contraseñas no coinciden."));
        }

        User user = new User();
        user.setCorreo(datosBasicos.getCorreo());
        user.setNombre(datosBasicos.getNombre());
        user.setApellido(datosBasicos.getApellido());
        user.setContrasenia(datosBasicos.getContrasenia());

        User usuarioTemporal = userService.guardarUsuarioTemporal(user);
        return ResponseEntity.ok(Map.of("mensaje", "Datos básicos registrados exitosamente.", "usuarioId", usuarioTemporal.getId()));
    }

    @Operation(summary = "Completar registro del usuario", description = "Completa el registro del usuario con los datos adicionales.")
    @PostMapping("/registrar-parte2")
    public ResponseEntity<?> registrarParte2(@RequestParam Long usuarioId, @RequestBody @Valid registroRequestParte2 datosAdicionales) {
        User user = userService.obtenerUsuarioTemporalPorId(usuarioId);
        if (user == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "Usuario no encontrado."));
        }

        user.setDni(datosAdicionales.getDni());
        user.setFechaNacimiento(LocalDate.parse(datosAdicionales.getFechaNacimiento()));
        user.setEdad(String.valueOf(datosAdicionales.getEdad()));
        user.setGenero(datosAdicionales.getGenero());
        user.setCelular(datosAdicionales.getCelular());

        ObrasSociales obraSocial = userService.getObraSocialById(Math.toIntExact(datosAdicionales.getIdObraSocial()));
        if (obraSocial == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "No se encontró la obra social con el ID proporcionado."));
        }
        user.setObraSocial(obraSocial);

        User usuarioCompleto = userService.finalizarRegistroUsuario(user);
        return ResponseEntity.ok(Map.of("mensaje", "Registro completado exitosamente.", "usuario", usuarioCompleto));
    }

    @Operation(summary = "Actualizar un usuario", description = "Actualiza los datos de un usuario existente en el sistema.")
    @PatchMapping("/actualizarPorCorreo")
    public ResponseEntity<User> actualizarUsuario(@RequestParam String correo, @RequestBody Map<String, Object> updates) {
        User usuario = userService.actualizarUsuarioPorCorreo(correo, updates);
        return ResponseEntity.ok(usuario);
    }

    @Operation(summary = "Solicitar cambio de contraseña", description = "Genera un token para cambiar la contraseña de un usuario.")
    @PostMapping("/solicitarCambioContrasenia")
    public ResponseEntity<?> solicitarCambioContrasenia(@RequestBody Map<String, String> datos) {
        String correo = datos.get("correo");

        if (correo == null || correo.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "El correo es obligatorio."));
        }

        if (!userService.validarSesionActiva(correo)) {
            return ResponseEntity.badRequest().body(Map.of("error", "El usuario no tiene una sesión activa o no existe."));
        }

        try {
            String token = userService.generarTokenDeCambioContrasenia(correo);
            return ResponseEntity.ok(Map.of("mensaje", "Token generado exitosamente.", "token", token));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @Operation(summary = "Cambiar contraseña", description = "Permite cambiar la contraseña de un usuario utilizando un token.")
    @PostMapping("/cambiarContrasenia")
    public ResponseEntity<?> cambiarContrasenia(@RequestBody Map<String, String> datos) {
        String correo = datos.get("correo");
        String nuevaContrasenia = datos.get("nuevaContrasenia");

        if (correo == null || correo.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "El correo es obligatorio."));
        }

        if (!userService.validarSesionActiva(correo)) {
            return ResponseEntity.badRequest().body(Map.of("error", "El usuario no tiene una sesión activa o no existe."));
        }

        if (nuevaContrasenia == null || nuevaContrasenia.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "La nueva contraseña es obligatoria."));
        }

        try {
            userService.actualizarContrasenia(correo, nuevaContrasenia); // Método para actualizar la contraseña.
            return ResponseEntity.ok(Map.of("mensaje", "Contraseña actualizada exitosamente."));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }



    }

    // Actualiza el token FCM de un usuario específico para permitir el envío de notificaciones push.
    @PutMapping("/usuario/{id}/fcm-token")
    public ResponseEntity<?> actualizarFcmToken(@PathVariable int id, @RequestBody Map<String, String> request) {
        String token = request.get("token");
        User user = userService.obtenerPorId((long) id);
        user.setFcmToken(token);
        userRepository.save(user);
        return ResponseEntity.ok("Token actualizado");
    }


    //Traer usuario por id
    @Operation(summary = "Obtener usuario por ID", description = "Devuelve el objeto completo del usuario correspondiente al ID proporcionado.")
    @GetMapping("/usuario/{id}")
    public ResponseEntity<User> obtenerUsuarioPorId(@PathVariable Long id) {
        User user = userService.obtenerPorId(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    //Parte de CONFIG
    @PatchMapping("/usuario/{id}/configuraciones")
    public ResponseEntity<String> actualizarConfiguraciones(
            @PathVariable int id,
            @RequestBody Map<String, Boolean> configuraciones
    ) {
        Optional<User> optionalUser = userRepository.findById((long) id);
        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }

        User usuario = optionalUser.get();

        if (configuraciones.containsKey("notificacionesActivadas")) {
            usuario.setNotificacionesActivadas(configuraciones.get("notificacionesActivadas"));
        }
        if (configuraciones.containsKey("vibracionActivada")) {
            usuario.setVibracionActivada(configuraciones.get("vibracionActivada"));
        }
        if (configuraciones.containsKey("actualizacionesApp")) {
            usuario.setActualizacionesApp(configuraciones.get("actualizacionesApp"));
        }
        if (configuraciones.containsKey("modoOscuro")) {
            usuario.setModoOscuro(configuraciones.get("modoOscuro"));
        }

        userRepository.save(usuario);

        return ResponseEntity.ok("Configuraciones actualizadas correctamente");
    }


    @Operation(summary = "Enviar mensaje de ayuda", description = "Permite a los usuarios enviar un mensaje de ayuda al soporte.")
    @PostMapping("/ayuda")
    public ResponseEntity<String> enviarMensajeAyuda(@RequestBody Map<String, String> body) {
        String correoUsuario = body.get("correoUsuario");
        String mensaje = body.get("mensaje");

        if (correoUsuario == null || mensaje == null || correoUsuario.isBlank() || mensaje.isBlank()) {
            return ResponseEntity.badRequest().body("Faltan campos requeridos");
        }

        String asunto = "Mensaje de ayuda desde la app";
        String cuerpo = "Correo del usuario: " + correoUsuario + "\n\nMensaje:\n" + mensaje;


        //tokensService.enviarCorreo("enzoandreaasplanatti@gmail.com", asunto, cuerpo); // <- usa el correo de soporte
        tokensService.enviarCorreoSoporte("enzoandreaasplanatti@gmail.com", asunto, cuerpo, correoUsuario);


        return ResponseEntity.ok("Mensaje enviado correctamente");
    }


    @PostMapping("/cerrarSesion")
    public ResponseEntity<?> cerrarSesion(@RequestParam Long usuarioId) {
        User user = userService.obtenerPorId(usuarioId);
        if (user == null || !userService.validarSesionActiva(user.getCorreo())) {
            return ResponseEntity.badRequest().body(Map.of("error", "Usuario no encontrado o la sesión no está activa."));
        }

        user.setSesionActiva(false);
        user.setUltimaActividad(LocalDateTime.now());
        userService.guardarUsuario(user); // Método para guardar los cambios en el usuario.

        return ResponseEntity.ok(Map.of("mensaje", "Sesión cerrada exitosamente."));
    }

    @DeleteMapping("/eliminarCuenta")
    public ResponseEntity<?> eliminarCuenta(@RequestParam Long usuarioId) {
        User user = userService.obtenerPorId(usuarioId);
        if (user == null || !userService.validarSesionActiva(user.getCorreo())) {
            return ResponseEntity.badRequest().body(Map.of("error", "Usuario no encontrado o la sesión no está activa."));
        }

        userService.eliminarUsuarioPorId(usuarioId); // Método para eliminar el usuario.
        return ResponseEntity.ok(Map.of("mensaje", "Cuenta eliminada exitosamente."));
    }




}


