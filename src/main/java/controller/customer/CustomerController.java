package controller.customer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerController implements CustomerService{

    @Override
    public boolean addCustomer(Customer customer) throws RuntimeException {
        try {
            return CrudUtil.execute("INSERT INTO customer VALUES(?,?,?,?,?,?,?,?,?)",
                    customer.getId(),
                    customer.getTitle(),
                    customer.getName(),
                    customer.getDob(),
                    customer.getSalary(),
                    customer.getAddress(),
                    customer.getCity(),
                    customer.getProvince(),
                    customer.getPostalCode()
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean updateCustomer(Customer customer) {
        try {
            return CrudUtil.execute(
                    "UPDATE customer SET CustTitle=?,CustName=?,DOB =?,salary =?,CustAddress = ?, City = ? ,Province =?, PostalCode = ? WHERE CustID = ?",
                    customer.getTitle(),
                    customer.getName(),
                    customer.getDob(),
                    customer.getSalary(),
                    customer.getAddress(),
                    customer.getCity(),
                    customer.getProvince(),
                    customer.getPostalCode(),
                    customer.getId()
            ) ;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Customer searchCustomer(String custId) {
        ResultSet resultSet = null;
        try {
            resultSet = CrudUtil.execute("SELECT * FROM customer WHERE CustID=?",custId);
            resultSet.next();
            return new Customer(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDate(4).toLocalDate(),
                    resultSet.getDouble(5),
                    resultSet.getString(6),
                    resultSet.getString(7),
                    resultSet.getString(8),
                    resultSet.getString(9)
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public boolean deleteCustomer(String custId) {

        try {
            return CrudUtil.execute(
                    "DELETE FROM customer WHERE CustID=?",
                    custId
                    );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ObservableList<Customer> getAllCustomer() {
        ObservableList<Customer> customerObservableList = FXCollections.observableArrayList();

        ResultSet resultSet = null;
        try {
            resultSet = CrudUtil.execute("SELECT * FROM customer");
            while (resultSet.next()){
                Customer customer = new Customer(
                        resultSet.getString("CustID"),
                        resultSet.getString("CustTitle"),
                        resultSet.getString("CustName"),
                        resultSet.getDate("dob").toLocalDate(),
                        resultSet.getDouble("salary"),
                        resultSet.getString("CustAddress"),
                        resultSet.getString("city"),
                        resultSet.getString("province"),
                        resultSet.getString("postalCode")
                );
                customerObservableList.add(customer);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return customerObservableList;
    }

    public  ObservableList<String> getCustomersIds(){
        ObservableList<Customer> allCustomer  = getAllCustomer();
        ObservableList<String> idList = FXCollections.observableArrayList();

        allCustomer.forEach(customer -> {
            idList.add(customer.getId());
        });
        return idList;
    }

}
