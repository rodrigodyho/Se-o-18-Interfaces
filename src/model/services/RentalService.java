package model.services;

import model.entities.CarRental;
import model.entities.Invoice;

public class RentalService {

    private Double pricePerDay;
    private Double pricePerHour;

    private BrazilTaxService taxService;

    public RentalService(Double pricePerDay, Double pricePerHour, BrazilTaxService taxService) {
        this.pricePerDay = pricePerDay;
        this.pricePerHour = pricePerHour;
        this.taxService = taxService;
    }

    public void processInvoid(CarRental carRental) {
        long t1 = carRental.getStart().getTime();
        long t2 = carRental.getFinish().getTime();
        double hours = (double)(t2 - t1) / 1000 / 60 / 60;

        double basicPatment;
        if (hours <= 12.0) {
            basicPatment = Math.ceil(hours) * pricePerHour;
        }
        else {
            basicPatment = Math.ceil(hours / 24) * pricePerDay;
        }
        double tax = taxService.tax(basicPatment);

        carRental.setInvoice(new Invoice(basicPatment, tax));
    }
}
