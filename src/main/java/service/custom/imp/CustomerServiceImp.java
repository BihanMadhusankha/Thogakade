package service.custom.imp;

import entity.CustomerEntity;
import javafx.collections.ObservableList;
import model.Customer;
import org.modelmapper.ModelMapper;
import repository.DaoFactory;
import repository.custom.CustomerDao;
import service.custom.CustomerService;
import util.DaoType;

public class CustomerServiceImp implements CustomerService {
    @Override
    public boolean addCustomer(Customer customer) {
        System.out.println("Customer Service :"+customer);

        CustomerDao customerDao = DaoFactory.getInstance().getDao(DaoType.CUSTOMER);
        CustomerEntity map = new ModelMapper().map(customer, CustomerEntity.class);
        return customerDao.save(map);
    }

    @Override
    public boolean updateCustomer(Customer customer) {
        return false;
    }

    @Override
    public Customer searchCustomer(String id) {
        return null;
    }

    @Override
    public boolean deleteCustomer(String id) {
        return false;
    }

    @Override
    public ObservableList<Customer> getAllCustomer() {
        return null;
    }
}
