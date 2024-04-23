package com.oficial.C1739.Service.imp;

import com.oficial.C1739.Entity.Profile;
import com.oficial.C1739.Entity.Rol;
import com.oficial.C1739.Entity.Usuario;
import com.oficial.C1739.Exception.InvalidPasswordException;
import com.oficial.C1739.Extern.Email.Entity.MailToken;
import com.oficial.C1739.Extern.Email.Repository.MailTokenRepository;
import com.oficial.C1739.Repository.RolRepository;
import com.oficial.C1739.Repository.UsuarioRepository;
import com.oficial.C1739.Service.UserService;
import com.oficial.C1739.dto.GuardarUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Calendar;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    RolRepository rolRepository;


    @Autowired
    MailTokenRepository mailTokenRepository;


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Usuario registrarUnUsuario(GuardarUsuario nuevoUsuario) {


        validarPassword(nuevoUsuario);

        Optional<Usuario> verificarUsuario= usuarioRepository.findByCorreo(nuevoUsuario.getCorreo());

        if (verificarUsuario.isPresent()){
            throw new RuntimeException("El correo ya esta en uso");
        }


        Usuario usuario = new Usuario();
        usuario.setCorreo(nuevoUsuario.getCorreo());
        usuario.setNombre(nuevoUsuario.getNombre());
        usuario.setApellido(nuevoUsuario.getApellido());
        //Creacion del rol en caso de no existir
        String nombreRol= "ROLE_USUARIO";
        Rol nuevoRol= agregarRol(nombreRol);
        usuario.setRol(nuevoRol);
        usuario.setPassword(passwordEncoder.encode(nuevoUsuario.getPassword()));

        //creamos su perfil y lo asociamos al usuario y el usuario con su perfil respectivamente
        Profile nuevoPerfil= new Profile();
        nuevoPerfil.setUsuario(usuario);
        nuevoPerfil.setNombre(usuario.getNombre());
        nuevoPerfil.setApellido(usuario.getApellido());
        nuevoPerfil.setCorreo(usuario.getCorreo());
        usuario.setPerfil(nuevoPerfil);

        usuarioRepository.save(usuario);
        return usuario;
    }

    @Override
    public Optional<Usuario> findOneByCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo);
    }




    private Rol agregarRol( String nombreRol) {

        Optional<Rol>  verificarRol= rolRepository.findByNombre(nombreRol);
        if (verificarRol.isEmpty()){
            Rol nuevoRol= new Rol();
            nuevoRol.setNombre(nombreRol);
            rolRepository.save(nuevoRol);
            return  nuevoRol;
        }
        else {
            return verificarRol.get();
        }
    }



    private void validarPassword(GuardarUsuario dto) {

        if (!dto.getPassword().equals(dto.getRePassword())) {
            throw new InvalidPasswordException("Las contraseñas no coinciden");
        }

        if (StringUtils.isEmpty(dto.getPassword()) || StringUtils.isEmpty(dto.getRePassword())) {
            throw new InvalidPasswordException("La contraseña no  debe ser vacia");
        }
    }


    public void verificarToken( Usuario nuevoUsuario,String token){

        //Guarda el token que espera ser validado por el usuario que se creo la cuenta
        MailToken mailToken= new MailToken(nuevoUsuario,token);

        mailTokenRepository.save(mailToken);


    }

    @Override
    public String validarToken(String token) {
        MailToken verificarToken= mailTokenRepository.findByToken(token);
        if(verificarToken == null){
            return "token Invalido";
        }



        Usuario usuario = verificarToken.getUsuario();
        Calendar calendar = Calendar.getInstance();
        if ((verificarToken.getExpiracionToken().getTime() - calendar.getTime().getTime()) <= 0){
            mailTokenRepository.delete(verificarToken);
            return "Token ya ha expirado";
        }
        usuario.setIsEnabled(true);
        usuarioRepository.save(usuario);
        return "valido";
    }

    @Override
    public Boolean cambiarPassword(String nuevoPassword,String correo) {

        Optional<Usuario> buscarUsuario= findOneByCorreo(correo);
        if (buscarUsuario.isPresent()){
            buscarUsuario.get().setPassword(passwordEncoder.encode(nuevoPassword));
            usuarioRepository.save(buscarUsuario.get());
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void createPasswordResetTokenForUser(Usuario usuario, String token) {

    }

}
