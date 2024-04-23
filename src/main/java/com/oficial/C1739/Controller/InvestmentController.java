package com.oficial.C1739.Controller;

import com.oficial.C1739.Entity.Paid;
import com.oficial.C1739.Service.InvestmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/investment")
public class InvestmentController {

    private final InvestmentService investmentService;

    public InvestmentController(InvestmentService investmentService) {
        this.investmentService = investmentService;
    }


    @GetMapping()
    public ResponseEntity<?> visualizarPagosPorCorreo(@RequestParam("email") String email){

        try {
            List<Paid> listaPagos= investmentService.visualizarPagosPorCorreo(email);

            return ResponseEntity.ok().body(listaPagos);
        } catch (Exception e){
            return ResponseEntity.badRequest().body("error en la solicitud de visualizar pagos:"+ e.getMessage());
        }
    }
}
