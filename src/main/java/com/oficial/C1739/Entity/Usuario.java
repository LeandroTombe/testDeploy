package com.oficial.C1739.Entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "usuario")
public class Usuario extends Auditable implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private long idUsuario;

    private String nombre;

    private String apellido;

    @Column(unique = true)
    private String correo;

    private String password;

    //esto me sirve para hacer la validacion a traves de un correo
    @Column(name = "is_enabled")
    private Boolean estaHabilitado= false;



    //Relacion con Perfil
    @OneToOne(cascade = CascadeType.ALL,mappedBy = "usuario")
    private Profile perfil;

    //Relacion con rol
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="rol_id")
    private Rol rol;

    //Relacion con comentarios
    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
    //comments made sig comentarios hechos o comentarios realizados
    private List<Comment> commentsMade;

    //Relacion con inversion
    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
    private List<Investment> investmentsMade;

    //Relacion 1aN con proyecto
    @OneToMany(mappedBy = "creator")
    @JsonIgnore
    private List<Project> createdProjects;

    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
    private List<Paid> pagos;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (rol== null) {
            return null;
        }
        if (rol.getPermisos()== null) {
            return null;
        }

        /**
         * 1 Obtiene la colección de permisos asociados a un rol específico.
         * 2 Transforma cada permiso en un objeto SimpleGrantedAuthority.
         * 3 Recolecta los objetos SimpleGrantedAuthority resultantes en una nueva lista.
         * 4 Devuelve la lista final, que contiene los permisos representados como objetos SimpleGrantedAuthority.
         */

        /*
     return user.getRoles().stream()
            .map(role -> new SimpleGrantedAuthority(role.getName()))  // `role.getName()` debe retornar "ROLE_USUARIO", etc.
            .collect(Collectors.toList());

        */
        return rol.getPermisos().stream()
                .map(permiso -> new SimpleGrantedAuthority(permiso.getNombre()))
                .collect(Collectors.toList());

    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return this.correo;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return estaHabilitado;
    }


    public void setIsEnabled(Boolean estaHabilitado) {
        this.estaHabilitado = estaHabilitado;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(long idUsuario) {
        this.idUsuario = idUsuario;
    }


    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
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

    public Profile getPerfil() {
        return perfil;
    }

    public void setPerfil(Profile perfil) {
        this.perfil = perfil;
    }

    public Boolean getEstaHabilitado() {
        return estaHabilitado;
    }

    public void setEstaHabilitado(Boolean estaHabilitado) {
        this.estaHabilitado = estaHabilitado;
    }

    public List<Comment> getCommentsMade() {
        return commentsMade;
    }

    public void setCommentsMade(List<Comment> commentsMade) {
        this.commentsMade = commentsMade;
    }

    public List<Investment> getInvestmentsMade() {
        return investmentsMade;
    }

    public void setInvestmentsMade(List<Investment> investmentsMade) {
        this.investmentsMade = investmentsMade;
    }



}
