package com.example.gestion_inventario.config;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.gestion_inventario.model.Color;
import com.example.gestion_inventario.model.NivelPrioridad;
import com.example.gestion_inventario.model.Talla;
import com.example.gestion_inventario.model.TipoProblema;
import com.example.gestion_inventario.model.TipoUsuario;
import com.example.gestion_inventario.model.Usuario;
import com.example.gestion_inventario.repository.ColorRepository;
import com.example.gestion_inventario.repository.NivelPrioridadRepository;
import com.example.gestion_inventario.repository.TallaRepository;
import com.example.gestion_inventario.repository.TipoProblemaRepository;
import com.example.gestion_inventario.repository.TipoUsuarioRepository;
import com.example.gestion_inventario.repository.UsuarioRepository;

// Con esta clase se agregan los tipos de usuario a la tabla
// 'tipo_usuario' de la base de datos.
// Un buen recurso para entender la interfaz 'CommandLineRunner': https://kscodes.com/spring-boot/using-commandlinerunner-and-applicationrunner-in-spring-boot/
@Component
public class DataLoader implements CommandLineRunner {
    @Autowired
    private TipoUsuarioRepository tipoUsuarioRepository; // Para usar el metodo 'existsById' de Spring JPA

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TipoProblemaRepository tipoProblemaRepository;

    @Autowired
    private NivelPrioridadRepository nivelPrioridadRepository;

    @Autowired
    private ColorRepository colorRepository;

    @Autowired
    private TallaRepository tallaRepository;


    // Este metodo se ejecutara cada vez que se ejecute el backend
    @Override
    public void run(String... args) {
        // Se definen los dos tipos de usuarios del sistema: 'administrador' y 'vendedor'
        List<TipoUsuario> tiposUsuario = List.of(
            new TipoUsuario(1, "administrador", null),
            new TipoUsuario(2, "vendedor", null)
        );

        // Se cargara la tabla solo si no hay datos
        for(TipoUsuario tipoUser: tiposUsuario) {
            if(!tipoUsuarioRepository.existsById(tipoUser.getId())) {
                tipoUsuarioRepository.save(tipoUser);
            }
        }

        // Usuario inicial (es el que se usara para hacer login a la aplicacion)
        TipoUsuario tipoAdmin = new TipoUsuario();
        tipoAdmin.setId(1);

        Usuario usuarioInicio = new Usuario(null, "John", "Doe", "j.doe@admin.com", "*admin_12345Joe", tipoAdmin, LocalDate.now());

        // El usuario de inicio se va a guardar solo si no existe en la entidad
        if(!usuarioRepository.findByCorreo(usuarioInicio.getCorreo()).isPresent()) {
            usuarioRepository.save(usuarioInicio);
        }

        // Se definen los tipos de problema
        List<TipoProblema> tiposProblema = List.of(
            new TipoProblema(1, "Carga de datos inventario", null),
            new TipoProblema(2, "Sincronización con servidor", null),
            new TipoProblema(3, "Rendimiento lento", null),
            new TipoProblema(4, "Agregar/Eliminar ítems", null),
            new TipoProblema(5, "Conectividad", null),
            new TipoProblema(6, "Otro", null)
        );

        for(TipoProblema tipoProbl : tiposProblema) {
            if(!tipoProblemaRepository.existsById(tipoProbl.getId())) {
                tipoProblemaRepository.save(tipoProbl);
            }
        }

        // Se definen los niveles de prioridad del reporte de problema
        List<NivelPrioridad> nivelesPrioridad = List.of(
            new NivelPrioridad(1, "Alto", null),
            new NivelPrioridad(2, "Medio", null),
            new NivelPrioridad(3, "Bajo", null)
        );

        for(NivelPrioridad nivelPrio : nivelesPrioridad) {
            if(!nivelPrioridadRepository.existsById(nivelPrio.getId())) {
                nivelPrioridadRepository.save(nivelPrio);
            }
        }
        
        // Se definen los colores de los productos
        List<Color> colores = List.of(
            new Color(1, "Negro", null),
            new Color(2, "Cafe", null),
            new Color(3, "Beige", null),
            new Color(4, "Blanco", null),
            new Color(5, "Azul", null)
        );

        for(Color color : colores) {
            if(!colorRepository.existsById(color.getId())){
                colorRepository.save(color);
            }
        }

        // Se definen las tallas de los productos
        List<Talla> tallas = List.of(
            new Talla(1, 39, null),
            new Talla(2, 40, null),
            new Talla(3, 41, null),
            new Talla(4, 42, null),
            new Talla(5, 43, null),
            new Talla(6, 44, null),
            new Talla(7, 45, null)
        );

        for(Talla talla : tallas) {
            if(!tallaRepository.existsById(talla.getId())) {
                tallaRepository.save(talla);
            }
        }

    }
}
