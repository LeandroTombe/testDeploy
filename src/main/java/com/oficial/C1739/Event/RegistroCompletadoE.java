package com.oficial.C1739.Event;

import com.oficial.C1739.Entity.Usuario;
import org.springframework.context.ApplicationEvent;

public class RegistroCompletadoE extends ApplicationEvent {
    private Usuario usuario;
    private String urlAplicacion;

    public RegistroCompletadoE(Usuario usuario, String urlAplicacion) {
        super(usuario);
        this.usuario = usuario;
        this.urlAplicacion = urlAplicacion;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getUrlAplicacion() {
        return urlAplicacion;
    }

    public void setUrlAplicacion(String urlAplicacion) {
        this.urlAplicacion = urlAplicacion;
    }
}
