package pl.hotel.customer;

import java.sql.Connection;
import java.util.List;


public class CustomerService implements CustomerServiceInterface {

    private Connection connection;
    private CustomerRepository customerRepository;


    public CustomerService(Connection connection) {
        this.connection = connection;
        this.customerRepository = new CustomerRepository(connection);
    }

    public Customer create(Customer customer) {
        return customerRepository.create(customer);
    }

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public Customer getCustomer(CustomerFilter customerFilter) {
        return customerRepository.get(customerFilter);
    }

    public Customer update(Customer customer) {
        return customerRepository.update(customer);
    }

    public void delete(int id) {
        customerRepository.delete(id);
    }
}
