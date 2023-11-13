package music.musicapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import music.musicapp.model.user.User;

import java.math.BigDecimal;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "Amount cannot be null")
    @Positive(message = "Amount must be positive")
    private BigDecimal amount;
    private String firstName;
    private String sureName;
    @Size (max = 16, min = 16, message = "has to be 16 numbers without characters")
    private int numberOfCard;
    @Size (max = 3, min = 3, message = "has to be 3 numbers without characters")
    private int CVV;
    @NotNull(message = "Payment method cannot be null")
    private String paymentMethod;
    @ManyToOne
    private User user;
}
