package pl.hotel.customer;

import java.util.List;

public interface CustomerServiceInterface {
    Customer create(Customer customer);
    List<Customer> findAll();
    Customer getCustomer(CustomerFilter customerFilter);
    Customer update(Customer customer);
    void delete(int id);
}
