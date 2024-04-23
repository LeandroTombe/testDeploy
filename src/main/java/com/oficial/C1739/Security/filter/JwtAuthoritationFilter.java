package com.oficial.C1739.Security.filter;


import com.oficial.C1739.Entity.Usuario;
import com.oficial.C1739.Service.JwtService;
import com.oficial.C1739.Service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * OncePerRequestFilter es una clase abstracta en Spring Framework que proporciona una base para implementar
 * filtros que se ejecutarán solo una vez por solicitud HTTP, incluso si la solicitud atraviesa varios filtros.
 *
 *
 *
 *
 *
 * Dentro de esta clase, debemos implementar un método llamado doFilterInternal que se ejecutará una vez por solicitud.
 *
 * doFilterInternal es un método abstracto que se define en la clase Filter de Spring Framework.
 * Es el método central donde se implementa la lógica de un filtro.
 *
 * 1 Invocación por el contenedor de Spring: El contenedor de Spring invoca doFilterInternal
 *   para cada solicitud que pasa por el filtro.
 *
 * 2 Acceso a la solicitud y respuesta: Dentro de doFilterInternal, tienes acceso a la solicitud HTTP (HttpServletRequest) y
 *   la respuesta HTTP (HttpServletResponse).
 *
 * 3 Modificación de la solicitud y respuesta: Puedes modificar la solicitud y la respuesta
 *   antes de que se envíen al siguiente filtro o al destino final.
 *
 * 4 Invocación de la cadena de filtros: Debes llamar a filterChain.doFilter(request, response) para continuar la cadena
 *   de filtros y procesar la solicitud en el siguiente filtro o destino.
 */

@Component
public class JwtAuthoritationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


        String jwt= jwtService.extractJwtFromRequest(request);

        if(!StringUtils.hasText(jwt) || jwt==null){
            filterChain.doFilter(request, response);
            return;
        }

        //3 Verificar el subject/usuario del token
        // esta accion a su vez valida el formato del token

        String correo= jwtService.extraerUsuarioDelToken(jwt);

        //4 setear el objeto autentication que representa el usuario logueado dentro de security context holder
        //traemos los datos del usuario para tener el rol
        Usuario usuario = userService.findOneByCorreo(correo).orElseThrow(()-> new RuntimeException("Usuario no encontrado"));


        //tipo de autenticacion que se va a realizar
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(correo, null, usuario.getAuthorities());


        //Esto es opcional, nos sirve para guardar informacion adicional del usuario logueado
        //con la clase WebAuthenticationDetails podemos guardar la direccion remota y la sesion
        authenticationToken.setDetails(new WebAuthenticationDetails(request));

        SecurityContextHolder //es una clase que se encarga de almacenar el contexto de seguridad actual
                .getContext()
                .setAuthentication(authenticationToken);

        //5 continuar con la cadena de filtros
        filterChain.doFilter(request, response);
    }
}
