package controller;

import com.jfoenix.controls.JFXButton;
import com.sun.javafx.stage.EmbeddedWindow;
import db.DBConnection;
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

    public CustomerFormController(){}
    @FXML
    void btnOnActionAddCustomer(ActionEvent event) throws SQLException {

        Customer customer = new Customer(txtId.getText(),comBoxTitle.getValue(),txtName.getText(),datrBirthday.getValue(),Double.parseDouble(txtSalary.getText()),
                txtAddress.getText(),txtCity.getText(),txtProvince.getText(),txtPostalCode.getText());
        //System.out.println(customer);

        String SQL = "INSERT INTO customer VALUES(?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(SQL);
            pstm.setObject(1, customer.getId());
            pstm.setObject(2, customer.getTitle());
            pstm.setObject(3, customer.getName());
            pstm.setObject(4, customer.getDob());
            pstm.setObject(5, customer.getSalary());
            pstm.setObject(6, customer.getAddress());
            pstm.setObject(7, customer.getCity());
            pstm.setObject(8, customer.getProvince());
            pstm.setObject(9, customer.getPostalCode());
            boolean isCustomerAdd = pstm.executeUpdate() > 0;
            //System.out.println(isCustomerAdd);
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
        String SQL = "DELETE FROM customer WHERE CustID='"+txtId.getText()+"'";
        try {
           boolean isDelete = DBConnection.getInstance().getConnection().createStatement().executeUpdate(SQL)>0;
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
        String SQL = "SELECT * FROM customer WHERE CustID=?";
        try {
            PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(SQL);
            stm.setObject(1,txtId.getText());
            ResultSet resultSet = stm.executeQuery();

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

            ResultSet resultSet = DBConnection.getInstance().getConnection().createStatement().executeQuery(SQL);

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

        String SQL = "UPDATE customer SET CustTitle=?,CustName=?,DOB =?,salary =?,CustAddress = ?, City = ? ,Province =?, PostalCode = ? WHERE CustID = ?";
        try {
           PreparedStatement psTm = DBConnection.getInstance().getConnection().prepareStatement(SQL);
           psTm.setObject(1,customer.getTitle());
           psTm.setObject(2,customer.getName());
           psTm.setObject(3,customer.getDob());
           psTm.setObject(4,customer.getSalary());
           psTm.setObject(5,customer.getAddress());
           psTm.setObject(6,customer.getCity());
           psTm.setObject(7,customer.getProvince());
           psTm.setObject(8,customer.getPostalCode());
           psTm.setObject(9,customer.getId());

            boolean isUpdate = psTm.executeUpdate()>0 ;
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
