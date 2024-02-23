package dev.codeunlu.currencyapi.repository;

import dev.codeunlu.currencyapi.model.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CurrencyRepository extends MongoRepository<Currency, String> {
    Optional<Currency> findFirstByBaseCodeAndCodeOrderByUpdatedTime(String baseCode, String code);
}
