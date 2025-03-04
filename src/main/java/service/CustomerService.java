package service;


import model.Customer;
import persistence.CustomerDAO;

import java.sql.SQLException;
import java.util.List;

public class CustomerService {
    private CustomerDAO customerDAO = new CustomerDAO();

    public void addCustomer(Customer customer) throws SQLException {
        customerDAO.createCustomer(customer);
    }

    public Customer getCustomerById(int id) throws SQLException {
        return customerDAO.getCustomerById(id);
    }

    public List<Customer> getAllCustomers() throws SQLException {
        return customerDAO.getAllCustomers();
    }

    public void updateCustomer(Customer customer) throws SQLException {
        customerDAO.updateCustomer(customer);
    }

    public void deleteCustomer(int id) throws SQLException {
        customerDAO.deleteCustomer(id);
    }
}
