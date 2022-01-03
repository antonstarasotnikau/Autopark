package by.incubator.Entity;

import java.util.Date;

public class Rent {
    private Long id;
    private Date dateRent;
    private double incomeRent;

    public Rent() {
    }

    public Rent(Long id, Date dateRent, double incomeRent) {
        this.id = id;
        this.dateRent = dateRent;
        this.incomeRent = incomeRent;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDateRent() {
        return dateRent;
    }

    public void setDateRent(Date dateRent) {
        this.dateRent = dateRent;
    }

    public double getIncomeRent() {
        return incomeRent;
    }

    public void setIncomeRent(double incomeRent) {
        this.incomeRent = incomeRent;
    }
}
