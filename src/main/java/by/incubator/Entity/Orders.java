package by.incubator.Entity;

import by.incubator.infrastructure.config.annotations.Column;
import by.incubator.infrastructure.config.annotations.ID;
import by.incubator.infrastructure.config.annotations.Table;
import lombok.*;

@Table(name="orders")
@Builder
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Orders {
    @ID
    private Long id;
    @Column(name = "id_vehicle")
    private Long idVehicle;
    @Column(name = "name_breaking")
    private String nameBreaking;
    @Column(name = "count_breaking")
    private Integer countBreaking;
}
