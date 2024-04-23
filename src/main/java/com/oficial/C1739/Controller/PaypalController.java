package com.oficial.C1739.Controller;


/*import com.example.demo.config.PaymentData;
import com.example.demo.config.PaypalService;*/
import com.oficial.C1739.Entity.Investment;
import com.oficial.C1739.Entity.PaymentData;
import com.oficial.C1739.Entity.Usuario;
import com.oficial.C1739.Repository.UsuarioRepository;
import com.oficial.C1739.Service.InvestmentService;
import com.oficial.C1739.Service.PaypalService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.PayPalRESTException;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/payment")
public class PaypalController {
    private final PaypalService paypalService;
    private final UsuarioRepository UsuarioRepository;

    private final InvestmentService investmentService;



    @PostMapping("/create")
    public ResponseEntity<String> createPayment(@RequestBody PaymentData paymentData) {
        String cancelUrl = "http://localhost:3000/payment/cancel";
        String successUrl = "http://localhost:3000/payment/success";
        try {
            // Obtener los datos del formulario
            String nombre = paymentData.getNombre();
            String email = paymentData.getEmail();
            Double monto = paymentData.getMonto();
            String moneda = paymentData.getMoneda();
            String descripcion = paymentData.getDescripcion();

            // Crear el pago en PayPal utilizando los datos del formulario
            Payment payment = paypalService.createPayment(
                    nombre, // Nombre del pagador
                    email, // Correo electrónico del pagador
                    monto, // Monto total
                    moneda, // Moneda
                    "sale", // Intent
                    descripcion, // Descripción
                    cancelUrl, // URL de cancelación
                    successUrl + "?email=" + URLEncoder.encode(email, StandardCharsets.UTF_8) + "&monto=" + URLEncoder.encode(String.valueOf(monto), StandardCharsets.UTF_8)// URL de éxito

            );
            //registrar la venta en el usuario cuando se completa el pago exitosamente


            // Redireccionar al usuario a la página de aprobación de PayPal
            for(Links links : payment.getLinks()){
                if(links.getRel().equals("approval_url")){
                    return ResponseEntity.ok(links.getHref());
                }
            }
        } catch (PayPalRESTException e) {
            // Manejar el error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear el pago");
    }

    @GetMapping("/success")
    public String paymentSuccess(
            @RequestParam("email") String email,
            @RequestParam("monto") String monto,
            @RequestParam("paymentId") String paymentId,
            @RequestParam("PayerID") String payerId,
            HttpSession session
    ) {
        try {
            Payment payment = paypalService.executePayment(paymentId, payerId);
            if (payment.getState().equals("approved")) {

                /**
                 * Como la cuenta de sandbox es unica, la idea es averiguar el correo del usuario desde la base de datos
                 * y asi, si ese usuario hace un pago, qué ese pago se asigne a ese usuario
                 *
                 * aún hay que configurar eso
                 * **/


                Double nuevoMonto= Double.valueOf(monto);
                investmentService.registrarUnaCompra(email, nuevoMonto);


                // Redirigir al usuario a la página de éxito en React
                log.info("Payment aprobado: " + payment.getId());

                return "redirect:http://localhost:3000/payment/resuccess";
            } else {
                log.info("Payment no aprobado: " + payment.getState());
                return "redirect:http://localhost:3000/payment/error";
            }
        } catch (PayPalRESTException e) {
            log.error("Ocurrió un error: ", e);
            log.error("Detalles del error: ", e.getDetails());
            return "redirect:http://localhost:3000/paymente/error";
        }
    }

    @GetMapping("/resuccess")
    public ResponseEntity<String> pagoCompleto(){
        return ResponseEntity.ok("Pago completado exitosamente");
    }


    @GetMapping("/cancel")
    public String paymentCancel() {
        // Redirigir al usuario a la página de pago de nuevo
        //return "redirect:http://localhost:5173/pago";

        return "Pago cancelado";
    }

    @GetMapping("/error")
    public String paymentError(){
        return "redirect:http://localhost:5173/error";
    }
}
