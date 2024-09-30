package controller.customer;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import model.Customer;
import util.CrudUtil;

import java.net.URL;
import java.sql.*;

import java.util.ResourceBundle;

public class CustomerFormController implements Initializable {

    public TextField txtId;
    public Button btnItemForm;
    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXButton btnDelete;



    @FXML
    private JFXButton btnSearch;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colBirthday;

    @FXML
    private TableColumn<?, ?> colCity;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colPostalCode;

    @FXML
    private TableColumn<?, ?> colProvince;

    @FXML
    private TableColumn<?, ?> colSalary;

    @FXML
    private TableColumn<?, ?> colTitle;

    @FXML
    private ComboBox<String> comBoxTitle;

    @FXML
    private DatePicker datrBirthday;

    @FXML
    private TableView<Customer> tblCustomerForm;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtCity;


    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPostalCode;

    @FXML
    private TextField txtProvince;

    @FXML
    private TextField txtSalary;

    public CustomerFormController(){}
    @FXML
    void btnOnActionAddCustomer(ActionEvent event) throws SQLException {

        Customer customer = new Customer(txtId.getText(),comBoxTitle.getValue(),txtName.getText(),datrBirthday.getValue(),Double.parseDouble(txtSalary.getText()),
                txtAddress.getText(),txtCity.getText(),txtProvince.getText(),txtPostalCode.getText());

        try {

            boolean isCustomerAdd = CrudUtil.execute("INSERT INTO customer VALUES(?,?,?,?,?,?,?,?,?)",
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

            if (isCustomerAdd) {
                new Alert(Alert.AlertType.INFORMATION, "Customer Added :)").show();
                loadTable();
            }
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR, "Customer Not Added :(").show();

        }
    }

    @FXML
    void btnOnActionCustomerDelete(ActionEvent event) {

        try {
           boolean isDelete = CrudUtil.execute(
                   "DELETE FROM customer WHERE CustID=?",
                   txtId.getText()
           );
           if (isDelete){
               new Alert(Alert.AlertType.INFORMATION, "Customer Deleted :)").show();
               loadTable();
           }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Customer Not Deleted :(").show();
        }
    }

    @FXML
    void btnOnActionCustomerSearch(ActionEvent event) {

        try {

            ResultSet resultSet =CrudUtil.execute("SELECT * FROM customer WHERE CustID=?",txtId.getText());;

                resultSet.next();
               Customer customer = new Customer(
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
               setValueToText(customer);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    void loadTable(){
        ObservableList<Customer> customerObservableList = FXCollections.observableArrayList();

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colBirthday.setCellValueFactory(new PropertyValueFactory<>("dob"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colCity.setCellValueFactory(new PropertyValueFactory<>("city"));
        colPostalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        colProvince.setCellValueFactory(new PropertyValueFactory<>("province"));
        try {

            String SQL ="SELECT * FROM customer";

            ResultSet resultSet = CrudUtil.execute(SQL);

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

            tblCustomerForm.setItems(customerObservableList);


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @FXML
    void btnOnActionUpateCustomer(ActionEvent event) {
        Customer customer = new Customer(txtId.getText(),comBoxTitle.getValue(),txtName.getText(),datrBirthday.getValue(),Double.parseDouble(txtSalary.getText()),
                txtAddress.getText(),txtCity.getText(),txtProvince.getText(),txtPostalCode.getText());

        try {

            boolean isUpdate = CrudUtil.execute(
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
            if (isUpdate){
                new  Alert(Alert.AlertType.INFORMATION,"Customer Updated !").show();
                loadTable();
            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,"Customer Not Updated !").show();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> customerTitleList = FXCollections.observableArrayList();
        customerTitleList.add("Mr");
        customerTitleList.add("Mrs");
        customerTitleList.add("Miss");
        customerTitleList.add("Ms");
        comBoxTitle.setItems(customerTitleList);
        loadTable();

        tblCustomerForm.getSelectionModel().selectedItemProperty().addListener((observableValue, customer, newValue) -> {
            if (newValue!= null){
                setValueToText(newValue);
            }
        });
    }

    private void setValueToText(Customer newValue) {
        txtId.setText(newValue.getId());
        txtName.setText(newValue.getName());
        txtAddress.setText(newValue.getAddress());
        txtSalary.setText(newValue.getSalary().toString());
        datrBirthday.setValue(newValue.getDob());
        comBoxTitle.setValue(newValue.getTitle());
        txtCity.setText(newValue.getCity());
        txtProvince.setText(newValue.getProvince());
        txtPostalCode.setText(newValue.getPostalCode());
    }

    public void btnOnActionItemForm(ActionEvent actionEvent) {

    }
}
