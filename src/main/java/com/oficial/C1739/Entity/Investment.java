package com.oficial.C1739.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "investment")
public class Investment extends Auditable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_investment")
    private long idInvestment;

    private double amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_project")
    @JsonIgnore
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    public Investment() {
    }

    public Investment(long idInvestment, double amount, Project project, Usuario usuario) {
        this.idInvestment = idInvestment;
        this.amount = amount;
        this.project = project;
        this.usuario = usuario;
    }

    public long getIdInvestment() {
        return idInvestment;
    }

    public void setIdInvestment(long idInvestment) {
        this.idInvestment = idInvestment;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
