package model;
import persistence.CarDAO;
import persistence.CustomerDAO;

import java.sql.Date;
import java.sql.SQLException;

public class RentalEvent {
    private int id;
    private int carId;
    private int customerId;
    private Date rentalDate;
    private Date returnDate;
    private double totalCost;
    private boolean isClosed;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public Date getRentalDate() {
        return rentalDate;
    }

    public void setRentalDate(Date rentalDate) {
        this.rentalDate = rentalDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public void setClosed(boolean closed) {
        isClosed = closed;
    }

    @Override
    public String toString() {
        String carInfo = "Car ID: " + carId;
        String customerInfo = "Customer ID: " + customerId;

        try {
            CarDAO carDAO = new CarDAO();
            // Assuming that getCarById returns a Car object or null if not found.
            model.Car car = carDAO.getCarById(carId);
            if (car != null) {
                carInfo = car.getBrand() + " " + car.getModel();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            CustomerDAO customerDAO = new CustomerDAO();
            // Assuming that getCustomerById returns a Customer object or null if not found.
            model.Customer customer = customerDAO.getCustomerById(customerId);
            if (customer != null) {
                customerInfo = customer.getFirstName() + " " + customer.getLastName() + " (ID: " + customer.getId() + ")";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "RentalEvent [ID: " + id
                + ", " + carInfo
                + ", " + customerInfo
                + ", Rental Date: " + rentalDate
                + ", Return Date: " + returnDate
                + ", Total Cost: " + totalCost
                + ", isClosed: " + isClosed + "]";
    }
}
