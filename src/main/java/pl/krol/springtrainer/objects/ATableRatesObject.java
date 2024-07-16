package pl.krol.springtrainer.objects;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ATableRatesObject {
    private String no;
    private LocalDate effectiveDate;
    private double mid;
}
