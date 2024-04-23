package com.oficial.C1739.Entity;


import jakarta.persistence.*;

import java.util.Date;

@Entity
public class JwtToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idToken;


    @Column(length = 2048)
    private String jwt;

    private Date ExpireAt;

    private Boolean isValid;

    @ManyToOne
    @JoinColumn(name = "idUsuario")
    private Usuario usuario;




    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public Date getExpireAt() {
        return ExpireAt;
    }

    public void setExpireAt(Date expireAt) {
        ExpireAt = expireAt;
    }

    public Boolean getValid() {
        return isValid;
    }

    public void setValid(Boolean valid) {
        isValid = valid;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public long getIdToken() {
        return idToken;
    }

    public void setIdToken(long idToken) {
        this.idToken = idToken;
    }
}
