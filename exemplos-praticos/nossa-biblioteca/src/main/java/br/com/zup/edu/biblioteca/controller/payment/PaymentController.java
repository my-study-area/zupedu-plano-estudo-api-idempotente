package br.com.zup.edu.biblioteca.controller.payment;

import br.com.zup.edu.biblioteca.model.PaymentTransaction;
import br.com.zup.edu.biblioteca.repository.PaymentTransactionRepository;
import br.com.zup.edu.biblioteca.service.RedisCachingService;
import com.fasterxml.jackson.core.JsonProcessingException;
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
    private final RedisCachingService cache;

    public PaymentController(PaymentTransactionRepository repository, RedisCachingService cache) {
        this.repository = repository;
        this.cache = cache;
    }

    @PostMapping("/api/payments1")
    public ResponseEntity<String> createPaymentPrimaryKey(@RequestHeader("If-Match") UUID transactionId, @RequestBody @Valid PaymentRequestDTO paymentRequest, UriComponentsBuilder uriComponentsBuilder) {
        boolean existTransaction = repository.existsByTransactionId(transactionId);
        if(existTransaction) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "There is a payment for this transaction");
        }
        PaymentTransaction paymentTransaction = paymentRequest.toModel();
        repository.save(paymentTransaction);
        URI location = uriComponentsBuilder.path("/api/payments1/{id}")
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
        URI location = uriComponentsBuilder.path("/api/payments2/{id}")
                .buildAndExpand(paymentTransaction.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PostMapping("/api/payments3")
    public ResponseEntity<?> createPaymentIdempotencyKey(@RequestHeader("Idempotency-Key") String transactionId, @RequestBody @Valid PaymentRequestDTO paymentRequest, UriComponentsBuilder uriComponentsBuilder) throws JsonProcessingException {
        CachedResponse cachedResponse = cache.get(transactionId);
        if(cachedResponse != null) {
            return new ResponseEntity<>(
                    cachedResponse.getBody(),
                    cachedResponse.getHeaders(),
                    cachedResponse.getStatusCode());
        }
        PaymentTransaction paymentTransaction = paymentRequest.toModel();
        repository.save(paymentTransaction);
        URI location = uriComponentsBuilder.path("/api/payments3/{id}")
                .buildAndExpand(paymentTransaction.getId())
                .toUri();
        ResponseEntity<?> response = ResponseEntity.created(location).build();
        cache.set(transactionId, new CachedResponse().toResponse(response));
        return response;
    }
}

