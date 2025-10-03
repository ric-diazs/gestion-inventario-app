package com.example.gestion_inventario.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.gestion_inventario.model.TipoUsuario;
import com.example.gestion_inventario.repository.TipoUsuarioRepository;

// Con esta clase se agregan los tipos de usuario a la tabla
// 'tipo_usuario' de la base de datos.
// Un buen recurso para entender la interfaz 'CommandLineRunner': https://kscodes.com/spring-boot/using-commandlinerunner-and-applicationrunner-in-spring-boot/
@Component
public class DataLoader implements CommandLineRunner {
    @Autowired
    private TipoUsuarioRepository tipoUsuarioRepository; // Para usar el metodo 'existsById' de Spring JPA

    // Este metodo se ejecutara cada vez que se ejecute el backend
    @Override
    public void run(String... args) {
        // Se definen los dos tipos de usuarios del sistema: 'administrador' y 'vendedor'
        List<TipoUsuario> tiposUsuario = List.of(
            new TipoUsuario(1, "administrador", null),
            new TipoUsuario(2, "vendedor", null)
        );

        // Se cargara la tabla solo si no hay datos
        for(TipoUsuario tipo : tiposUsuario) {
            if(!tipoUsuarioRepository.existsById(tipo.getId())) {
                tipoUsuarioRepository.save(tipo);
            }
        }
    }
}
