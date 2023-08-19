package br.com.zup.edu.biblioteca.controller.payment;

import br.com.zup.edu.biblioteca.model.PaymentTransaction;
import br.com.zup.edu.biblioteca.repository.PaymentTransactionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping
public class PaymentController {

    private final PaymentTransactionRepository repository;

    public PaymentController(PaymentTransactionRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/api/payments1")
    public ResponseEntity<String> createPaymentPrimaryKey(@RequestHeader("If-Match") UUID transactionId, @RequestBody @Valid PaymentRequestDTO paymentRequest, UriComponentsBuilder uriComponentsBuilder) {
        boolean existTransaction = repository.existsByTransactionId(transactionId);
        if(existTransaction) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "There is a payment for this transaction");
        }
        PaymentTransaction paymentTransaction = paymentRequest.toModel();
        repository.save(paymentTransaction);
        URI location = uriComponentsBuilder.path("/api/payments/{id}")
                .buildAndExpand(paymentTransaction.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PostMapping("/api/payments2")
    public ResponseEntity<String> createPaymentSecondaryKey(@RequestBody @Valid PaymentRequestDTO paymentRequest, UriComponentsBuilder uriComponentsBuilder) {
        boolean existTransaction = repository.existsByTransactionId(paymentRequest.getTransactionId());
        if(existTransaction) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "The payment has been processed");
        }
        PaymentTransaction paymentTransaction = paymentRequest.toModel();
        repository.save(paymentTransaction);
        URI location = uriComponentsBuilder.path("/api/payments/{id}")
                .buildAndExpand(paymentTransaction.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }
}

