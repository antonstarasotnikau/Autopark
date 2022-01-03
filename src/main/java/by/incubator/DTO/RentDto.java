package by.incubator.DTO;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
@Builder
public class RentDto {
    private int vehicleId;
    private Date dateRent;
    private double incomeRent;
}
