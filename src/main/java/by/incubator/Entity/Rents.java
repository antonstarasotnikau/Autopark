package by.incubator.Entity;

import by.incubator.infrastructure.config.annotations.Column;
import by.incubator.infrastructure.config.annotations.ID;
import by.incubator.infrastructure.config.annotations.Table;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Table(name="rents")
@Builder
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Rents {
    @ID
    private Long id;
    @Column(name = "id_vehicle", nullable = false)
    private Long idVehicle;
    @Column(name = "data_rents", nullable = false)
    private Date data;
    @Column(name = "income_rent", nullable = false)
    private BigDecimal incomeRent;
}
