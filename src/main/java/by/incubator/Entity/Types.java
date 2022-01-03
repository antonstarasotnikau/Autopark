package by.incubator.Entity;

import by.incubator.infrastructure.config.annotations.Column;
import by.incubator.infrastructure.config.annotations.ID;
import by.incubator.infrastructure.config.annotations.Table;
import lombok.*;

import java.math.BigDecimal;

@Table(name="types")
@Builder
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Types {
    @ID
    private Long id;
    @Column(name = "name", unique = true)
    private String name;
    @Column(name = "coef_tax")
    private BigDecimal coefTax;
}
