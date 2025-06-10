package dev.bast.ecommerce.productos.model;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Promotion {

    @NotNull(message = "Discount is required")
    @Min(value = 1, message = "Discount must be at least 1%")
    @Max(value = 99, message = "Discount cannot exceed 99%")
    private Integer discount;

    @NotNull(message = "End date is required")
    private LocalDateTime endsAt;

    public boolean isActive() {
        return LocalDateTime.now().isBefore(endsAt);
    }
}