package controller.customer;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.stage.Stage;
import model.Customer;
import util.CrudUtil;

import java.io.IOException;
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

    CustomerService customerService = new CustomerController();
    public CustomerFormController(){}
    @FXML
    void btnOnActionAddCustomer(ActionEvent event) throws SQLException {

        Customer customer = new Customer(txtId.getText(),comBoxTitle.getValue(),txtName.getText(),datrBirthday.getValue(),Double.parseDouble(txtSalary.getText()),
                txtAddress.getText(),txtCity.getText(),txtProvince.getText(),txtPostalCode.getText());

            boolean isCustomerAdd = customerService.addCustomer(customer);

            if (isCustomerAdd) {
                new Alert(Alert.AlertType.INFORMATION, "Customer Added :)").show();
                loadTable();
            }
        else{
            new Alert(Alert.AlertType.ERROR, "Customer Not Added :(").show();

        }
    }

    @FXML
    void btnOnActionCustomerDelete(ActionEvent event) {

           boolean isDelete = customerService.deleteCustomer(txtId.getText());

           if (isDelete){
               new Alert(Alert.AlertType.INFORMATION, "Customer Deleted :)").show();
               loadTable();
           }
           else {
                new Alert(Alert.AlertType.ERROR, "Customer Not Deleted :(").show();
            }
    }

    @FXML
    void btnOnActionCustomerSearch(ActionEvent event) {

               setValueToText(customerService.searchCustomer(txtId.getText()));

    }

    void loadTable(){
        tblCustomerForm.setItems(customerService.getAllCustomer());
    }

    @FXML
    void btnOnActionUpateCustomer(ActionEvent event) {
        Customer customer = new Customer(txtId.getText(),comBoxTitle.getValue(),txtName.getText(),datrBirthday.getValue(),Double.parseDouble(txtSalary.getText()),
                txtAddress.getText(),txtCity.getText(),txtProvince.getText(),txtPostalCode.getText());

            boolean isUpdate = customerService.updateCustomer(customer);
            if (isUpdate){
                new  Alert(Alert.AlertType.INFORMATION,"Customer Updated !").show();
                loadTable();
            }
            else {
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


        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colBirthday.setCellValueFactory(new PropertyValueFactory<>("dob"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colCity.setCellValueFactory(new PropertyValueFactory<>("city"));
        colPostalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        colProvince.setCellValueFactory(new PropertyValueFactory<>("province"));
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
        Stage stage = new Stage();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/item_form.fxml"))));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    public void OnActionDashBoardBtn(ActionEvent actionEvent) {
        Stage stage = new Stage();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/dash_board.fxml"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.show();
    }
}
