package service;

import persistence.CarDAO;
import model.Car;
import java.sql.SQLException;
import java.util.List;

public class CarService {
    private CarDAO carDAO;

    public CarService(CarDAO carDAO) {
        this.carDAO = carDAO;
    }
    public void addCar(Car car) throws SQLException {
        carDAO.createCar(car);
    }

    public Car getCarById(int id) throws SQLException {
        return carDAO.getCarById(id);
    }

    public List<Car> getAllCars() throws SQLException {
        return carDAO.getAllCars();
    }

    public void updateCar(Car car) throws SQLException {
        carDAO.updateCar(car);
    }

    public void deleteCar(int id) throws SQLException {
        carDAO.deleteCar(id);
    }


    public List<Car> searchCars(int minSeats, int maxSeats, double maxPrice, String brand, String model) throws SQLException {
        return carDAO.searchCars(minSeats, maxSeats, maxPrice, brand, model);
    }
}
