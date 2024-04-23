package com.oficial.C1739.Service;

import com.oficial.C1739.Entity.Usuario;
import com.oficial.C1739.dto.GuardarUsuario;

import java.util.Optional;

public interface UserService {
    Usuario registrarUnUsuario(GuardarUsuario nuevoUsuario);
    Optional<Usuario> findOneByCorreo(String correo);

    void verificarToken( Usuario nuevoUsuario,String token);

    String validarToken(String token);

    Boolean cambiarPassword(String nuevoPassword,String correo);

    void createPasswordResetTokenForUser(Usuario usuario, String token);
}
