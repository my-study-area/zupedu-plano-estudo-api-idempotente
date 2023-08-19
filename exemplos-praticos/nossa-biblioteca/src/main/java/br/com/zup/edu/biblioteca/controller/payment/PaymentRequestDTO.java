package br.com.zup.edu.biblioteca.controller.payment;

import br.com.zup.edu.biblioteca.model.PaymentTransaction;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

public class PaymentRequestDTO {

    @NotNull
    private UUID transactionId;

    @NotBlank
    private String paymentAmount;

    @NotBlank
    private String currency;

    public PaymentRequestDTO(UUID transactionId, String paymentAmount, String currency) {
        this.transactionId = transactionId;
        this.paymentAmount = paymentAmount;
        this.currency = currency;
    }

    public UUID getTransactionId() {
        return transactionId;
    }

    public String getPaymentAmount() {
        return paymentAmount;
    }

    public String getCurrency() {
        return currency;
    }

    public PaymentTransaction toModel() {
        return new PaymentTransaction(
                this.transactionId,
                new BigDecimal(this.paymentAmount),
                this.currency
        );
    }
}
