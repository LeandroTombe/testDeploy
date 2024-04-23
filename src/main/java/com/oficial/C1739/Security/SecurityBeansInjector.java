package com.oficial.C1739.Security;


import com.oficial.C1739.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityBeansInjector {

    @Autowired
    private UsuarioRepository usuarioRepository;


    /**
     *se encarga de verificar la identidad de un usuario a través de credenciales como nombre de usuario y contraseña.
     */
    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

    /**
     *  Es el componente central responsable de coordinar y administrar el proceso de autenticación, delegando la
     * autenticación a uno o más AuthenticationProvider configurados en la aplicación
     * @throws Exception
     */

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
       return authenticationConfiguration.getAuthenticationManager();

    }

    /** Creacion nuestra estrategia de autenticacion y nuestra estrategia sea un DaoAuthenticationProvider
    * DaoAuthenticationProvider es una clase que implementa UserDetails, que esta a su vez implementa el AuthenticationProvider.
     * Esta clase necesita un passwordEncoder porque las contra tienen que estar encriptadas
     * Tambien necesita un UserDetailsService, esta es una interfaz con un solo metodo, este debe conectarse a la base de datos para obtener
     * un usuario y este debe comparar las contras enviadas desde el front con las contras que van dentro del UserDetails
     * Este userDetails tiene atributos asociados al username, password, roles, entre otros...
    **/

    //NUESTRO OBJETIVO CON LA CLASE DaoAuthenticationProvider ES TENER IMPLEMENTADO EL PASSWORDENCODE Y LE USERDETAILSSERVICE


    @Bean
    public AuthenticationProvider authenticationProvider (){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());

        return daoAuthenticationProvider;
    }




    //creo un passwordencoder para encriptar las contras enviadas por el front
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    //implementacion del userDetailsService para obtener los datos del usuario
    @Bean
    public UserDetailsService userDetailsService(){
        return (correo) -> {
            return usuarioRepository.findByCorreo(correo)
                    .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con el correo: " + correo));
        };
    }
}
