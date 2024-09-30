package controller.customer;

import model.Customer;

public interface CustomerService {
    boolean addCustomer(Customer customer);
    boolean updateCustomer(Customer customer);
    boolean searchCustomer(Customer customer);
    boolean deleteCustomer(Customer customer);
}
