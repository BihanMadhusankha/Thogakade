package controller.placeholder;

import controller.customer.CustomerController;
import controller.item.ItemController;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import model.CartTM;
import model.Customer;
import model.Item;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.ResourceBundle;

public class PlaceOrderFormController implements Initializable {

    @FXML
    private Button btnAddToCart;

    @FXML
    private Button btnPlaceOrder;

    @FXML
    private TableColumn<?, ?> colDiscription;

    @FXML
    private TableColumn<?, ?> colQuantity;

    @FXML
    private TableColumn<?, ?> colTotal;

    @FXML
    private TableColumn<?, ?> colUnitCode;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private ComboBox<String> comBoxItemCode;

    @FXML
    private ComboBox<String> comBoxcustId;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblNetTotal;

    @FXML
    private Label lblTime;

    @FXML
    private TableView<CartTM> tblCart;

    @FXML
    private TextField txtCity;

    @FXML
    private TextField txtItemDiscription;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtOrderId;

    @FXML
    private TextField txtQty;

    @FXML
    private TextField txtSalary;

    @FXML
    private TextField txtStock;

    @FXML
    private TextField txtUnitPrice;

    ObservableList<CartTM> cart = FXCollections.observableArrayList();

    @FXML
    void OnActionAddToCart(ActionEvent event) {

        Double unitePrice = Double.parseDouble(txtUnitPrice.getText());
        Integer qty = Integer.parseInt(txtQty.getText());
        Double total = unitePrice*qty;

        cart.add(
                new CartTM(
                        comBoxItemCode.getValue(),
                        txtItemDiscription.getText(),
                        qty,
                        unitePrice,
                        total
                )
        );
        tblCart.setItems(cart);
        calNetTotal();
    }

    private void calNetTotal() {
        Double total = 0.0;
        for (CartTM cartTM : cart){
            total += cartTM.getTotal();
        }
        lblNetTotal.setText(total.toString());
    }

    @FXML
    void OnActionPlaceOrder(ActionEvent event) {

    }
    Date date;
    Timeline time;
    private  void loadDateAndTime(){
       date = new Date();
      SimpleDateFormat D =new SimpleDateFormat("yyyy-MM-dd");
      lblDate.setText(D.format(date));

      time = new Timeline(new KeyFrame(Duration.ZERO,e->{
          LocalTime now = LocalTime.now();
          lblTime.setText(
                  now.getHour()+" : "+ now.getMinute()+" : "+ now.getSecond()
          );
      }),
        new KeyFrame(Duration.seconds(1))
      );
        time.setCycleCount(Animation.INDEFINITE);
        time.play();
    }

    private void loadCustomerId(){

        comBoxcustId.setItems(new CustomerController().getCustomersIds());
    }
    private void loadItemCode(){

        comBoxItemCode.setItems(new ItemController().getItemCodes());
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colUnitCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colDiscription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitePrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        comBoxItemCode.getSelectionModel().selectedItemProperty().addListener((observableValue, itemCode, newValue) -> {
            if (newValue!= null){
                setValueToItemText(newValue);
            }
        });
        comBoxcustId.getSelectionModel().selectedItemProperty().addListener((observableValue, customerId, newValue) -> {
            if (newValue!= null){
                setValueToCustomerText(newValue);
            }
        });
        loadDateAndTime();
        loadCustomerId();
        loadItemCode();
    }

    private void setValueToCustomerText(String newValue) {
        Customer customer = new CustomerController().searchCustomer(newValue);
        txtName.setText(customer.getName());
        txtCity.setText(customer.getCity());
        txtSalary.setText(String.valueOf(customer.getSalary()));
    }

    private void setValueToItemText(String newValue) {
        Item item = new ItemController().searchItem(newValue);
        txtItemDiscription.setText(item.getDescription());
        txtUnitPrice.setText(String.valueOf(item.getUnitPrice()));
        txtStock.setText(String.valueOf(item.getQtyOnHand()));
    }
}
