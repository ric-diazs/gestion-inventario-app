
package com.example.gestion_inventario.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.gestion_inventario.model.Usuario;
import com.example.gestion_inventario.repository.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    /* FUNCIONES CRUD */
    // Funcion para retornar todos los usuarios registrados
    public List<Usuario> obtenerUsuarios() {
        return usuarioRepository.findAll();
    }

    // Funcion para buscar un solo usuario a partir de su id
    public Usuario obtenerUnUsuario(Integer id) {
        return usuarioRepository.findById(id).get();
    }

    // Funcion para obtener guardar un registro del usuario
    public Usuario guardarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    // Funcion para eliminar a un usuario
    public void eliminarUsuario(Integer id) {
        return usuarioRepository.deleteById(id);
    }


    /* OTRAS FUNCIONES */
    // Define el nombre de usuario
    public String definirNombreUsuario(String nombre, String apellidos) {
        // Toma las 3 primeras letras del nombre
        String substrNombre = nombre.toLowerCase().substring(1, 4);

        // Las dos primeras del apellido
        String substrApellido = apellidos.toLowerCase().substring(1, 3);

        return substrNombre + "." + substrApellido;
    }

    // Define correo: Es el nombre de usuario + '@gestion.com'
    public String definirCorreo(String nombreUsuario) {
        return nombreUsuario + "@gestion.com";
    }

}
