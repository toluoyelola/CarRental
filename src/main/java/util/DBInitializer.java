package util;

import persistence.DatabaseConnection;
import model.Car;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DBInitializer {
    public void initializeDB() {
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {


            stmt.executeUpdate("DROP TABLE IF EXISTS RentalEvent");
            stmt.executeUpdate("DROP TABLE IF EXISTS Customer");
            stmt.executeUpdate("DROP TABLE IF EXISTS Car");


            String createCar = "CREATE TABLE Car ("
                    + "id INT AUTO_INCREMENT PRIMARY KEY, "
                    + "brand VARCHAR(50) NOT NULL, "
                    + "model VARCHAR(50) NOT NULL, "
                    + "buildYear INT NOT NULL, "
                    + "licensePlate VARCHAR(20) NOT NULL UNIQUE, "
                    + "rentalPricePerDay DOUBLE NOT NULL, "
                    + "available BOOLEAN NOT NULL, "
                    + "numberOfSeats INT NOT NULL"
                    + ")";
            stmt.executeUpdate(createCar);

            // Create Customer table
            String createCustomer = "CREATE TABLE Customer ("
                    + "id INT AUTO_INCREMENT PRIMARY KEY, "
                    + "firstName VARCHAR(50) NOT NULL, "
                    + "lastName VARCHAR(50) NOT NULL, "
                    + "email VARCHAR(100) NOT NULL UNIQUE, "
                    + "phoneNumber VARCHAR(20), "
                    + "driverLicenseNumber VARCHAR(50) NOT NULL UNIQUE, "
                    + "countryCode VARCHAR(3) NOT NULL"
                    + ")";
            stmt.executeUpdate(createCustomer);

            // Create RentalEvent table
            String createRentalEvent = "CREATE TABLE RentalEvent ("
                    + "id INT AUTO_INCREMENT PRIMARY KEY, "
                    + "carId INT NOT NULL, "
                    + "customerId INT NOT NULL, "
                    + "rentalDate DATE NOT NULL, "
                    + "returnDate DATE NOT NULL, "
                    + "totalCost DOUBLE NOT NULL, "
                    + "isClosed BOOLEAN NOT NULL, "
                    + "FOREIGN KEY (carId) REFERENCES Car(id), "
                    + "FOREIGN KEY (customerId) REFERENCES Customer(id)"
                    + ")";
            stmt.executeUpdate(createRentalEvent);


            stmt.executeUpdate("INSERT INTO Car (brand, model, buildYear, licensePlate, rentalPricePerDay, available, numberOfSeats) " +
                    "VALUES ('Toyota', 'Corolla', 2018, 'ABC123', 50.0, true, 5)");
            stmt.executeUpdate("INSERT INTO Car (brand, model, buildYear, licensePlate, rentalPricePerDay, available, numberOfSeats) " +
                    "VALUES ('Honda', 'Civic', 2019, 'DEF456', 55.0, true, 5)");
            stmt.executeUpdate("INSERT INTO Car (brand, model, buildYear, licensePlate, rentalPricePerDay, available, numberOfSeats) " +
                    "VALUES ('Ford', 'Focus', 2017, 'GHI789', 45.0, true, 5)");
            stmt.executeUpdate("INSERT INTO Car (brand, model, buildYear, licensePlate, rentalPricePerDay, available, numberOfSeats) " +
                    "VALUES ('BMW', '3 Series', 2020, 'JKL012', 80.0, true, 5)");
            stmt.executeUpdate("INSERT INTO Car (brand, model, buildYear, licensePlate, rentalPricePerDay, available, numberOfSeats) " +
                    "VALUES ('Audi', 'A4', 2021, 'MNO345', 85.0, true, 5)");
            stmt.executeUpdate("INSERT INTO Car (brand, model, buildYear, licensePlate, rentalPricePerDay, available, numberOfSeats) " +
                    "VALUES ('Mercedes', 'C Class', 2020, 'PQR678', 90.0, true, 5)");
            stmt.executeUpdate("INSERT INTO Car (brand, model, buildYear, licensePlate, rentalPricePerDay, available, numberOfSeats) " +
                    "VALUES ('Volkswagen', 'Golf', 2018, 'STU901', 60.0, true, 5)");
            stmt.executeUpdate("INSERT INTO Car (brand, model, buildYear, licensePlate, rentalPricePerDay, available, numberOfSeats) " +
                    "VALUES ('Kia', 'Optima', 2019, 'VWX234', 55.0, true, 5)");
            stmt.executeUpdate("INSERT INTO Car (brand, model, buildYear, licensePlate, rentalPricePerDay, available, numberOfSeats) " +
                    "VALUES ('Hyundai', 'Elantra', 2017, 'YZA567', 50.0, true, 5)");
            stmt.executeUpdate("INSERT INTO Car (brand, model, buildYear, licensePlate, rentalPricePerDay, available, numberOfSeats) " +
                    "VALUES ('Nissan', 'Sentra', 2020, 'BCD890', 52.0, true, 5)");


            for (int i = 1; i <= 20; i++) {
                String firstName = "First" + i;
                String lastName = "Last" + i;
                String email = "customer" + i + "@example.com";
                String phone = "555-010" + (i < 10 ? "0" + i : i);
                String driverLicense = "DL" + (1000 + i);
                String countryCode = "USA"; // All customers from USA for this sample
                String insertCustomer = String.format(
                        "INSERT INTO Customer (firstName, lastName, email, phoneNumber, driverLicenseNumber, countryCode) " +
                                "VALUES ('%s', '%s', '%s', '%s', '%s', '%s')",
                        firstName, lastName, email, phone, driverLicense, countryCode);
                stmt.executeUpdate(insertCustomer);
            }


            for (int i = 1; i <= 50; i++) {
                int carId = (i % 10) + 1;       // car IDs from 1 to 10
                int customerId = (i % 20) + 1;    // customer IDs from 1 to 20
                // Generate sample dates (using day values between 1 and 28)
                String rentalDate = "2025-01-" + String.format("%02d", (i % 28) + 1);
                // Return date is 3 days later (capped at day 28 for simplicity)
                int returnDay = ((i % 28) + 4 > 28) ? 28 : (i % 28) + 4;
                String returnDate = "2025-01-" + String.format("%02d", returnDay);
                // For sample purposes, we use a fixed cost calculation (e.g., 50.0 * 3 days)

                double totalCost = 100.0 * 3;

                boolean isClosed = (i % 2 == 1);
                String insertRental = String.format(java.util.Locale.US,
                        "INSERT INTO RentalEvent (carId, customerId, rentalDate, returnDate, totalCost, isClosed) " +
                                "VALUES (%d, %d, '%s', '%s', %.2f, %b)",
                        carId, customerId, rentalDate, returnDate, totalCost, isClosed);
                stmt.executeUpdate(insertRental);
            }

            System.out.println("Database initialized successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
