package com.oficial.C1739.Entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "rol")
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRol;

    @NotNull
    //@Pattern(regexp = "^ROLE_(.*)$",message = "El rol debe empezar con 'ROLE_' seguido del nombre que le quieras gregar ")
    private String nombre;






    @OneToMany(mappedBy="rol")
    @JsonIgnore
    private Set<Usuario> usuarios;

    @ManyToMany(fetch= FetchType.EAGER)
    //para evitar que haga llamadas ciclicas
    @JsonIgnore
    @JoinTable(name = "rol_permisos",
            joinColumns = @JoinColumn(name = "idRol"),
            inverseJoinColumns = @JoinColumn(name = "idPermisos"))
    private Set<Permiso> permisos;

    public Rol() {
        this.permisos=new HashSet<>();
    }

    public Long getIdRol() {
        return idRol;
    }

    public void setIdRol(Long idRol) {
        this.idRol = idRol;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }



    public Set<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Set<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public Set<Permiso> getPermisos() {
        return permisos;
    }

    public void setPermisos(Set<Permiso> permisos) {
        this.permisos = permisos;
    }
}
