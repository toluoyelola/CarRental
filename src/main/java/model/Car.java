package model;

public class Car {
    private int id;
    private String brand;
    private String model;
    private int buildYear;
    private String licensePlate;
    private double pricePerDay;
    private boolean available;
    private int numberOfSeats;

    // Getters and Setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }
    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }
    public void setModel(String model) {
        this.model = model;
    }

    public int getBuildYear() {
        return buildYear;
    }
    public void setBuildYear(int buildYear) {
        this.buildYear = buildYear;
    }

    public String getLicensePlate() {
        return licensePlate;
    }
    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public double getPricePerDay() {
        return pricePerDay;
    }
    public void setPricePerDay(double pricePerDay) {
        this.pricePerDay = pricePerDay;
    }


    public boolean isAvailable() {
        return available;
    }
    public void setAvailable(boolean available) {
        this.available = available;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }
    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", buildYear=" + buildYear +
                ", rentalPricePerDay=" + pricePerDay +
                ", damageWaiver=" + available +
                ", available=" + available +
                ", numberOfSeats=" + numberOfSeats +
                '}';
    }
}
