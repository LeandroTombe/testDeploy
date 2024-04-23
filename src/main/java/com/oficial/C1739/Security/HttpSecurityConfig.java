package com.oficial.C1739.Security;


import com.oficial.C1739.Entity.Rol;
import com.oficial.C1739.Entity.Usuario;
import com.oficial.C1739.Security.filter.JwtAuthoritationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @ENABLEWEBSECURITY
 *
 * Habilita la seguridad web: Activa el soporte de seguridad web de Spring Security, lo que te permite proteger tu aplicación web.
 * Integración con Spring MVC: Integra Spring Security con Spring MVC para una gestión fluida de la autenticación y autorización dentro de tu aplicación web.
 * En esencia, es la anotación principal para configurar Spring Security en tu aplicación.
 */

// Esta clase se convierte en el punto central para configurar la autenticación, la autorización y otros aspectos de seguridad.

@Configuration
@EnableWebSecurity
public class HttpSecurityConfig {

    @Autowired
    private AuthenticationProvider daoAuthenticationProvider;

    @Autowired
    JwtAuthoritationFilter jwtAuthoritationFilter;


    /**
     * Tener una clase SecurityFilterChain de permite realizar las siguientes acciones:
     *
     * 1. Filtrar las solicitudes:
     *
     * Actúa como un interceptor central para todas las solicitudes HTTP que llegan a tu aplicación.
     * Analiza cada solicitud y determina si se necesita autenticación o autorización.
     *
     * 2 Gestionar el flujo de seguridad:
     *
     * Implementa un patrón de cadena de responsabilidad para manejar las diferentes etapas del proceso de seguridad.
     * Cada etapa se define como un filtro de seguridad específico que se encarga de una tarea particular, como la autenticación, la autorización o el registro.
     * La clase SecurityFilterChain define el orden en que se ejecutan estos filtros
     */

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        //Spring security tiene un listado de filtro automatico en la cual cada uno tiene un peso y se ejecutan en orden

      return http.csrf( csrf -> csrf.disable())
                //  sessionManagement nos sirve para manejar las politicas de sesion
                // Crear una aplcacion sin estado, es decir, no se guardara la sesion del usuario.
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(daoAuthenticationProvider)
              //Luego de configurar las sesiones y la autenticacion, procedemos a configurar los filtro de autorizacion del jwt y el segundo parametro a la cual debe preceder ese filtro que creo
                .addFilterBefore(jwtAuthoritationFilter, UsernamePasswordAuthenticationFilter.class)
              // luego con authorizeHttpRequests nos permite configurar las reglas de autorizacion
                .authorizeHttpRequests(authorize -> {

                    //lista de autorizaciones basadas en rol
                    autorizacionPorRol(authorize);

                    //lista de autorizaciones basadas en permisos
                    autorizacionPorPermisos(authorize);
                    //lista de autenticaciones
                    autenticacion(authorize);

                })
                .build();


    }

    private static void autenticacion(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authorize) {
        //autenticacion
        authorize.requestMatchers("/desarrolladores").permitAll();
        authorize.requestMatchers(HttpMethod.GET,"/api/auth/perfil").authenticated();
        authorize.requestMatchers(HttpMethod.POST,"/api/auth/**").permitAll();
        authorize.requestMatchers(HttpMethod.GET,"/api/auth/**").permitAll();
        authorize.requestMatchers(HttpMethod.POST,"/api/email/**").permitAll();
        authorize.requestMatchers(HttpMethod.GET,"/api/email/**").permitAll();
        authorize.requestMatchers(HttpMethod.POST, "/payment/**").permitAll();
        authorize.requestMatchers(HttpMethod.GET, "/api/payment/**").permitAll();
        authorize.requestMatchers(HttpMethod.GET, "/payment/**").permitAll();
        authorize.requestMatchers(HttpMethod.GET, "/success/**").permitAll();
        authorize.requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "swagger-ui.html").permitAll();
        authorize.requestMatchers(HttpMethod.GET,"/api/investment").permitAll();
        authorize.requestMatchers(HttpMethod.GET,"/test").permitAll();
        authorize.anyRequest().authenticated();


    }


    //pondremos todos las autorizacion por tipo de rol
    private static void autorizacionPorPermisos(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authorize) {
        //authorize.requestMatchers("/test").hasAuthority("PERMISO_TESTEO");
        
    }


    //pondremos todos las autorizacion por tipo de permisos
    private static void autorizacionPorRol(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authorize) {
        //autorizacion basado en rol

        //authorize.requestMatchers(HttpMethod.GET,"/test").hasRole("USUARIO");


    }

}
