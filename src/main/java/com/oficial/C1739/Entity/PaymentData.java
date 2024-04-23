package com.oficial.C1739.Entity;

import lombok.Getter;

public class PaymentData {
    @Getter
    private String nombre;
    @Getter
    private String email;

    private Double Monto;

    private String Moneda;
    private String descripcion;

    public PaymentData() {
    }

    public PaymentData(String nombre, String email, Double monto, String moneda, String descripcion) {
        this.nombre = nombre;
        this.email = email;
        Monto = monto;
        Moneda = moneda;
        this.descripcion = descripcion;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Double getMonto() {
        return Monto;
    }

    public void setMonto(Double monto) {
        Monto = monto;
    }

    public String getMoneda() {
        return Moneda;
    }

    public void setMoneda(String moneda) {
        Moneda = moneda;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
