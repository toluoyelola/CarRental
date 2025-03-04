package service;

import model.Car;
import model.RentalEvent;
import persistence.CarDAO;
import persistence.RentalEventDAO;

import java.sql.SQLException;
import java.util.List;

public class RentalEventService {

    private RentalEventDAO rentalEventDAO;
    private CarDAO carDAO;

    public RentalEventService(RentalEventDAO rentalEventDAO, CarDAO carDAO) {
        this.rentalEventDAO = rentalEventDAO;
        this.carDAO = carDAO;
    }


    public boolean rentCar(RentalEvent rentalEvent) throws SQLException {
        return rentalEventDAO.createRentalEvent(rentalEvent);
    }


    public void returnCar(int rentalEventId) throws SQLException {
        RentalEvent rentalEvent = rentalEventDAO.getRentalEventById(rentalEventId);
        if (rentalEvent == null) {
            throw new SQLException("Rental event not found.");
        }
        if (rentalEvent.isClosed()) {
            System.out.println("Car " + rentalEvent.getCarId() + " rented by customer " + rentalEvent.getCustomerId());

        }
        rentalEvent.setClosed(true);
        rentalEventDAO.updateRentalEvent(rentalEvent);

        Car car = carDAO.getCarById(rentalEvent.getCarId());
        if (car != null) {
            car.setAvailable(true);
            carDAO.updateCar(car);
        }
    }

    public RentalEvent getRentalEventById(int id) throws SQLException {
        return rentalEventDAO.getRentalEventById(id);
    }

    public List<RentalEvent> getAllRentalEvents() throws SQLException {
        return rentalEventDAO.getAllRentalEvents();
    }

    public void updateRentalEvent(RentalEvent rentalEvent) throws SQLException {
        rentalEventDAO.updateRentalEvent(rentalEvent);
    }

    public void deleteRentalEvent(int id) throws SQLException {
        rentalEventDAO.deleteRentalEvent(id);
    }
}
