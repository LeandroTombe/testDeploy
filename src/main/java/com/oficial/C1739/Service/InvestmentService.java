package com.oficial.C1739.Service;


import com.oficial.C1739.Entity.Paid;

import java.util.List;

public interface InvestmentService {
    void registrarUnaCompra(String email, Double monto);

    List<Paid> visualizarPagosPorCorreo(String email);
}
