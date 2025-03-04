package persistence;

import model.Car;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarDAO {
    public void createCar(Car car) throws SQLException {
        String sql = "INSERT INTO Car (brand, model, buildYear, licensePlate, rentalPricePerDay, available, numberOfSeats) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, car.getBrand());
            stmt.setString(2, car.getModel());
            stmt.setInt(3, car.getBuildYear());
            stmt.setString(4, car.getLicensePlate());
            stmt.setDouble(5, car.getPricePerDay());
            stmt.setBoolean(6, car.isAvailable());
            stmt.setInt(7, car.getNumberOfSeats());
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating car failed, no rows affected.");
            }
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    car.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating car failed, no ID obtained.");
                }
            }
        }
    }

    public Car getCarById(int id) throws SQLException {
        String sql = "SELECT * FROM Car WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToCar(rs);
                }
            }
        }
        return null;
    }

    public List<Car> getAllCars() throws SQLException {
        List<Car> cars = new ArrayList<>();
        String sql = "SELECT * FROM Car";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                cars.add(mapResultSetToCar(rs));
            }
        }
        return cars;
    }

    public void updateCar(Car car) throws SQLException {
        String sql = "UPDATE Car SET brand=?, model=?, buildYear=?, licensePlate=?, rentalPricePerDay=?, available=?, numberOfSeats=? WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, car.getBrand());
            stmt.setString(2, car.getModel());
            stmt.setInt(3, car.getBuildYear());
            stmt.setString(4, car.getLicensePlate());
            stmt.setDouble(5, car.getPricePerDay());
            stmt.setBoolean(6, car.isAvailable());
            stmt.setInt(7, car.getNumberOfSeats());
            stmt.setInt(8, car.getId());
            stmt.executeUpdate();
        }
    }

    public void deleteCar(int id) throws SQLException {
        String sql = "DELETE FROM Car WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }


    public List<Car> searchCars(Integer minSeats, Integer maxSeats, Double maxPrice, String brand, String model) throws SQLException {
        List<Car> cars = new ArrayList<>();
        String sql = "SELECT * FROM Car WHERE " +
                "numberOfSeats BETWEEN COALESCE(?, numberOfSeats) AND COALESCE(?, numberOfSeats) " +
                "AND rentalPricePerDay <= COALESCE(?, rentalPricePerDay) " +
                "AND brand LIKE COALESCE(?, brand) " +
                "AND model LIKE COALESCE(?, model)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setObject(1, minSeats);
            stmt.setObject(2, maxSeats);
            stmt.setObject(3, maxPrice);
            stmt.setString(4, brand != null ? brand + "%" : null); // Partial match for brand
            stmt.setString(5, model != null ? model + "%" : null); // Partial match for model

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    cars.add(mapResultSetToCar(rs));
                }
            }
        }
        return cars;
    }


    private Car mapResultSetToCar(ResultSet rs) throws SQLException {
        Car car = new Car();
        car.setId(rs.getInt("id"));
        car.setBrand(rs.getString("brand"));
        car.setModel(rs.getString("model"));
        car.setBuildYear(rs.getInt("buildYear"));
        car.setLicensePlate(rs.getString("licensePlate"));
        car.setPricePerDay(rs.getDouble("rentalPricePerDay"));
        car.setAvailable(rs.getBoolean("available"));
        car.setNumberOfSeats(rs.getInt("numberOfSeats"));
        return car;
    }
}
