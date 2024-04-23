package com.oficial.C1739.Service.imp;

import com.oficial.C1739.Entity.Usuario;
import com.oficial.C1739.dto.UsuarioRegistrado;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {
    /*@Override

    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null || !authentication.isAuthenticated()){
            return Optional.of("Registro Inicial");
        }

        return Optional.of(authentication.getName());
    }*/

    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getPrincipal())) {
            return Optional.of("registro inicial");  // Valor predeterminado para creaciones sin autenticación
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof Usuario) {
            Usuario user = (Usuario) principal;
            return Optional.of(user.getCorreo());
        } else {
            return Optional.empty();
        }
    }
}
