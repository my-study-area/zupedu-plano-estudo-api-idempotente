package br.com.zup.edu.biblioteca.repository;

import br.com.zup.edu.biblioteca.model.PaymentTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface PaymentTransactionRepository extends JpaRepository<PaymentTransaction, Long> {
    boolean existsByTransactionId(UUID transactionId);
}

