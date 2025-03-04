package persistence;

import model.RentalEvent;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RentalEventDAO {

    public boolean createRentalEvent(RentalEvent rentalEvent) throws SQLException {
        String checkSql = "SELECT available FROM Car WHERE id = ? FOR UPDATE";
        String insertSql = "INSERT INTO RentalEvent (carId, customerId, rentalDate, returnDate, totalCost, isClosed) VALUES (?, ?, ?, ?, ?, ?)";
        String updateCarSql = "UPDATE Car SET available = false WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
                checkStmt.setInt(1, rentalEvent.getCarId());
                try (ResultSet rs = checkStmt.executeQuery()) {
                    if (!rs.next()) {
                        conn.rollback();
                        throw new SQLException("Car with id " + rentalEvent.getCarId() + " not found.");
                    }
                    if (!rs.getBoolean("available")) {
                        conn.rollback();
                        throw new SQLException("Car is not available for rent.");
                    }
                }
            }

            try (PreparedStatement insertStmt = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
                insertStmt.setInt(1, rentalEvent.getCarId());
                insertStmt.setInt(2, rentalEvent.getCustomerId());
                insertStmt.setDate(3, rentalEvent.getRentalDate());
                insertStmt.setDate(4, rentalEvent.getReturnDate());
                insertStmt.setDouble(5, rentalEvent.getTotalCost());
                insertStmt.setBoolean(6, rentalEvent.isClosed());

                int affectedRows = insertStmt.executeUpdate();
                if (affectedRows == 0) {
                    conn.rollback();
                    throw new SQLException("Creating rental event failed, no rows affected.");
                }

                // Get the generated rental event ID
                try (ResultSet generatedKeys = insertStmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        rentalEvent.setId(generatedKeys.getInt(1));
                    } else {
                        conn.rollback();
                        throw new SQLException("Creating rental event failed, no ID obtained.");
                    }
                }
            }

            try (PreparedStatement updateCarStmt = conn.prepareStatement(updateCarSql)) {
                updateCarStmt.setInt(1, rentalEvent.getCarId());
                updateCarStmt.executeUpdate();
            }

            // Commit the transaction
            conn.commit();
            return true;

        } catch (SQLException e) {
            System.err.println("Error creating rental event: " + e.getMessage());
            throw e;
        }
    }


    public RentalEvent getRentalEventById(int id) throws SQLException {
        String sql = "SELECT * FROM RentalEvent WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToRentalEvent(rs);
                }
            }
        }
        return null;
    }

    public List<RentalEvent> getAllRentalEvents() throws SQLException {
        List<RentalEvent> events = new ArrayList<>();
        String sql = "SELECT * FROM RentalEvent";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                events.add(mapResultSetToRentalEvent(rs));
            }
        }
        return events;
    }

    public void updateRentalEvent(RentalEvent rentalEvent) throws SQLException {
        String sql = "UPDATE RentalEvent SET carId=?, customerId=?, rentalDate=?, returnDate=?, totalCost=?, isClosed=? WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, rentalEvent.getCarId());
            stmt.setInt(2, rentalEvent.getCustomerId());
            stmt.setDate(3, rentalEvent.getRentalDate());
            stmt.setDate(4, rentalEvent.getReturnDate());
            stmt.setDouble(5, rentalEvent.getTotalCost());
            stmt.setBoolean(6, rentalEvent.isClosed());
            stmt.setInt(7, rentalEvent.getId());
            stmt.executeUpdate();
        }
    }

    public void deleteRentalEvent(int id) throws SQLException {
        String sql = "DELETE FROM RentalEvent WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    private RentalEvent mapResultSetToRentalEvent(ResultSet rs) throws SQLException {
        RentalEvent event = new RentalEvent();
        event.setId(rs.getInt("id"));
        event.setCarId(rs.getInt("carId"));
        event.setCustomerId(rs.getInt("customerId"));
        event.setRentalDate(rs.getDate("rentalDate"));
        event.setReturnDate(rs.getDate("returnDate"));
        event.setTotalCost(rs.getDouble("totalCost"));
        event.setClosed(rs.getBoolean("isClosed"));
        return event;
    }
}
