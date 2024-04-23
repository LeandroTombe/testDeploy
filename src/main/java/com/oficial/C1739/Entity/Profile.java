package com.oficial.C1739.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Table(name = "profile")
@DynamicInsert
public class Profile extends Auditable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_profile")
    private long idProfile;

    //Establecemos relacion uno a uno con la clase Usuario, asi para perfil estara asociado a un perfil
    //Agregamos props adicionales a nuestra relacion
    //la propiedad cascade nos permite definir el comportamiendo en cascada que tendra la persistencia de los datos.
    //usaremos el type PERSIST ya que con este si guardamos un objeto contro otros relacionados, estos tambien se guardaran

    @Column(nullable = false)
    private String nombre;
    @Column(nullable = false)
    private String apellido;
    @Column(nullable = false, unique = true)
    private String correo;

    private int age;
    private String avatar;
    private String phone;
    private String linkedin;
    private String git;
    private String especialidad;
    private String nacionalidad;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_usuario")
    @JsonIgnore // revisar porque esta esta annotation
    private Usuario usuario;

    public Profile() {
    }

    public Profile(long idProfile, String nombre, String apellido, String correo, int age, String avatar, String phone, String linkedin, String git, String especialidad, String nacionalidad, Usuario usuario) {
        this.idProfile = idProfile;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.age = age;
        this.avatar = avatar;
        this.phone = phone;
        this.linkedin = linkedin;
        this.git = git;
        this.especialidad = especialidad;
        this.nacionalidad = nacionalidad;
        this.usuario = usuario;
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

    public long getIdProfile() {
        return idProfile;
    }

    public void setIdProfile(long idProfile) {
        this.idProfile = idProfile;
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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
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
