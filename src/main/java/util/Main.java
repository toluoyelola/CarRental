package util;

import persistence.CarDAO;
import persistence.CustomerDAO;
import persistence.RentalEventDAO;


import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {

        //System.out.printf("Hello and welcome!");

        System.out.println("H2 server started on port 64845.");
        new DBInitializer().initializeDB();
        try {
            System.out.println(new CustomerDAO().getAllCustomers().getFirst());
            System.out.println(new CustomerDAO().getAllCustomers().getLast());
            System.out.println(new CarDAO().getAllCars().getFirst());
            System.out.println(new CarDAO().getAllCars().getLast());
            System.out.println(new RentalEventDAO().getAllRentalEvents().getFirst());
            System.out.println(new RentalEventDAO().getAllRentalEvents().getLast());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}