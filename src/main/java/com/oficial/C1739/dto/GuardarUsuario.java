package com.oficial.C1739.dto;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

public class GuardarUsuario  implements Serializable {


    private String correo;


    @NotNull(message = "El nombre no debe estar nulo")
    @Size(min = 3, message = "El nombre debe ser como minimo 3")
    private String nombre;

    @NotNull(message = "El apellido no debe estar nulo")
    @Size(min = 3, message = "El apellido debe ser como minimo 3")
    private String apellido;


    @NotNull(message = "El password no debe estar nulo")
    @Size(min = 5, max = 20, message = "La contraseña debe tener entre 5 y 20 caracteres")
    private String password;

    @NotNull(message = "El re ingreso de password no debe estar nulo")
    private String rePassword;


    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRePassword() {
        return rePassword;
    }

    public void setRePassword(String rePassword) {
        this.rePassword = rePassword;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
}
