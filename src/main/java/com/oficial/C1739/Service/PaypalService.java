package com.oficial.C1739.Service;

import com.oficial.C1739.Entity.Usuario;
import com.oficial.C1739.Repository.UsuarioRepository;
import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaypalService {
    private final APIContext apiContext;

    private final UsuarioRepository usuarioRepository;

    public Payment createPayment(String payerName, String payerEmail, Double total, String currency, String intent, String description, String cancelUrl, String successUrl) throws PayPalRESTException {

        Optional<Usuario> validarUsuario= usuarioRepository.findByCorreo(payerEmail);
         if (validarUsuario.isEmpty()){
             throw  new RuntimeException("El correo del usuario que quiere hacer el pago no existe");
         }

        List<String> errorMessages = new ArrayList<>();

        // Verificar los campos requeridos

            if (payerName == null || payerName.isEmpty()) {
                errorMessages.add("Te falta el campo nombre");
            }
            if (payerEmail == null || payerEmail.isEmpty()) {
                errorMessages.add("Te falta el campo email");
            }
            if (total == null || total <= 0) {
                errorMessages.add("Te falta el campo monto");
            }
            if (currency == null || currency.isEmpty()) {
                errorMessages.add("Te falta el campo moneda");
            }
            if (description == null || description.isEmpty()) {
                errorMessages.add("Te falta el campo descripcion");
            }

        // Verificar si se encontraron errores
        if (!errorMessages.isEmpty()) {
            throw new RuntimeException(String.join(", ", errorMessages));
        }

        Amount amount = new Amount();
        amount.setCurrency(currency);
        amount.setTotal(String.format(Locale.forLanguageTag(currency), "%,.2f", total));

        Transaction transaction = new Transaction();
        transaction.setDescription(description);
        transaction.setAmount(amount);

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        Payer payer = new Payer();
        payer.setPaymentMethod("paypal");
        payer.setPayerInfo(new PayerInfo().setFirstName(payerName).setEmail(payerEmail));

        Payment payment = new Payment();
        payment.setIntent(intent);
        payment.setPayer(payer);
        payment.setTransactions(transactions);

        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl(cancelUrl);
        redirectUrls.setReturnUrl(successUrl);

        payment.setRedirectUrls(redirectUrls);

        return payment.create(apiContext);
    }

    public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException {
        Payment payment = new Payment();
        payment.setId(paymentId);

        PaymentExecution paymentExecute = new PaymentExecution();
        paymentExecute.setPayerId(payerId);

        return payment.execute(apiContext, paymentExecute);
    }


}
