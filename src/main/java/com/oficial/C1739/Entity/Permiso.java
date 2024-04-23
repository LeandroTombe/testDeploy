package com.oficial.C1739.Entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "permission")
public class Permiso {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPermisos;

    private String nombre;

    @ManyToMany(mappedBy = "permisos")
    @JsonIgnore
    private List<Rol> roles;

    public Permiso() {
    }

    public Permiso(String nombre) {
        this.nombre = nombre;
    }

    public Long getIdPermisos() {
        return idPermisos;
    }

    public void setIdPermisos(Long idPermisos) {
        this.idPermisos = idPermisos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Rol> getRoles() {
        return roles;
    }

    public void setRoles(List<Rol> roles) {
        this.roles = roles;
    }
}
