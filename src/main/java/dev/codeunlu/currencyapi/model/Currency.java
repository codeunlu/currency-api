package dev.codeunlu.currencyapi.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "Currencies")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Currency {
    @Id
    @GeneratedValue(generator = "UUID", strategy = GenerationType.UUID)
    private String id;
    private String baseCode;
    private String code;
    private BigDecimal value;
    @UpdateTimestamp
    private LocalDateTime updatedTime;
    private LocalDateTime responsedTime;
}
