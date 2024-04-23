package com.oficial.C1739.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

public class PasswordRequest implements Serializable {

    @NotNull
    @NotBlank
    private String nuevoPassword;
    @NotNull
    @NotBlank
    private String confirmarNuevoPassword;

    public String getNuevoPassword() {
        return nuevoPassword;
    }

    public void setNuevoPassword(String nuevoPassword) {
        this.nuevoPassword = nuevoPassword;
    }

    public String getConfirmarNuevoPassword() {
        return confirmarNuevoPassword;
    }

    public void setConfirmarNuevoPassword(String confirmarNuevoPassword) {
        this.confirmarNuevoPassword = confirmarNuevoPassword;
    }
}
