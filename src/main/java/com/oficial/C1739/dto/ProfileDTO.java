package com.oficial.C1739.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;

import java.io.Serializable;

public class ProfileDTO  implements Serializable {

    private String nombre;
    private String apellido;
    private String correo;
    private String Rol;
    @Min(value = 15, message = "La edad no puede ser menor que 15")
    @Max(value = 100, message = "La edad no puede ser mayor que 100")
    private int age;
    private String avatar;

    @Pattern(regexp = "^(\\+\\d{1,3}[- ]?)?\\d{10}$", message = "Número de teléfono no válido")
    //+codArea - nro cel
    private String phone;
    @URL(message = "La URL de LinkedIn no es válida")
    private String linkedin;
    @URL(message = "La URL de Git no es válida")
    private String git;

    @Size(max = 30, message = "La especialidad no puede tener más de 100 caracteres")
    private String especialidad;
    @Size(max = 30, message = "La especialidad no puede tener más de 100 caracteres")
    private String nacionalidad;

    public ProfileDTO() {
    }

    public ProfileDTO(String nombre, String apellido, String correo, String rol, int age, String avatar, String phone, String linkedin, String git, String especialidad, String nacionalidad) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        Rol = rol;
        this.age = age;
        this.avatar = avatar;
        this.phone = phone;
        this.linkedin = linkedin;
        this.git = git;
        this.especialidad = especialidad;
        this.nacionalidad = nacionalidad;
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

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getRol() {
        return Rol;
    }

    public void setRol(String rol) {
        Rol = rol;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLinkedin() {
        return linkedin;
    }

    public void setLinkedin(String linkedin) {
        this.linkedin = linkedin;
    }

    public String getGit() {
        return git;
    }

    public void setGit(String git) {
        this.git = git;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }
}
