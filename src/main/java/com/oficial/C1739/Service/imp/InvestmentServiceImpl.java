package com.oficial.C1739.Service.imp;

import com.oficial.C1739.Entity.Paid;
import com.oficial.C1739.Entity.Usuario;
import com.oficial.C1739.Repository.InvestmentRepository;
import com.oficial.C1739.Repository.PaidRepository;
import com.oficial.C1739.Repository.UsuarioRepository;
import com.oficial.C1739.Service.InvestmentService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class InvestmentServiceImpl implements InvestmentService {


    private final UsuarioRepository usuarioRepository;

    private final PaidRepository paidRepository;

    public InvestmentServiceImpl(UsuarioRepository usuarioRepository,PaidRepository paidRepository) {
        this.usuarioRepository = usuarioRepository;
        this.paidRepository= paidRepository;
    }

    @Override
    public void registrarUnaCompra(String email, Double monto) {

        Optional<Usuario> buscarUsuario= usuarioRepository.findByCorreo(email);

        //persisto la compra hecha por el usuario
        Paid compra=new Paid();
        compra.setMonto(monto);
        compra.setFechaPago(LocalDate.now());
        compra.setUsuario(buscarUsuario.get());
        paidRepository.save(compra);
    }

    @Override
    public List<Paid> visualizarPagosPorCorreo(String email) {
        Optional<Usuario> verificarUsuario= usuarioRepository.findByCorreo(email);

        if (verificarUsuario.isPresent()){
            return  verificarUsuario.get().getPagos();
        } else {
            throw new RuntimeException("Mail inexistente");
        }

    }
}
