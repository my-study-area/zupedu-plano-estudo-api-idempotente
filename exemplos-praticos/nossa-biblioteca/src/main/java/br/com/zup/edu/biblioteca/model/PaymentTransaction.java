package br.com.zup.edu.biblioteca.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
public class PaymentTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private UUID transactionId;

    @NotNull
    private BigDecimal paymentAmount;

    @NotBlank
    private String currency;

    public PaymentTransaction() {
    }

    public PaymentTransaction(UUID transactionId, BigDecimal paymentAmount, String currency) {
        this.transactionId = transactionId;
        this.paymentAmount = paymentAmount;
        this.currency = currency;
    }

    public Long getId() {
        return id;
    }

}
