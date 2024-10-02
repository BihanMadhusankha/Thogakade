package controller.item;

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
import model.Item;
import util.CrudUtil;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ItemFormController implements Initializable {

    @FXML
    private Button btnAddItem;

    @FXML
    private Button btnDeleteItem;

    @FXML
    private Button btnSearchItem;

    @FXML
    private Button btnUpdateItem;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colItemCode;

    @FXML
    private TableColumn<?, ?> colPackSize;

    @FXML
    private TableColumn<?, ?> colQtyOnHand;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private TableView<Item> tblItem;

    @FXML
    private TextField txtDescription;

    @FXML
    private TextField txtItemCode;

    @FXML
    private TextField txtPackSize;

    @FXML
    private TextField txtQtyOnHand;

    @FXML
    private TextField txtUnitPrice;

    ItemServices itemController = new ItemController();
    @FXML
    void OnActionAddItem(ActionEvent event) {
        Item item = new Item(
                txtItemCode.getText(),
                txtDescription.getText(),
                txtPackSize.getText(),
                Double.parseDouble(txtUnitPrice.getText()),
                Integer.parseInt(txtQtyOnHand.getText())
        );
            boolean isItemAdd = itemController.addItem(item);
            if (isItemAdd){
                new Alert(Alert.AlertType.INFORMATION,"Items Added :)").show();
                loadItemTable();
            }
            else  {
            new Alert(Alert.AlertType.ERROR,"Items not Added :)").show();
            }
    }

    @FXML
    void OnActionDeleteItem(ActionEvent event) {

            boolean isDelete = itemController.deleteItem( txtItemCode.getText());
            if (isDelete){
                new Alert(Alert.AlertType.INFORMATION,"Deleted Item ").show();
                loadItemTable();
            }
            else {
            new Alert(Alert.AlertType.ERROR,"Item Not Deleted :(").show();
            }
    }

    @FXML
    void OnActionSearchItem(ActionEvent event) {

        setValueToText(itemController.searchItem(txtItemCode.getText()));
    }

    @FXML
    void OnActionUpdateItem(ActionEvent event) {
        Item item = new Item(
                txtItemCode.getText(),
                txtDescription.getText(),
                txtPackSize.getText(),
                Double.parseDouble(txtUnitPrice.getText()),
                Integer.parseInt(txtQtyOnHand.getText())
        );
            boolean isUpdate = itemController.updateItem(item);
            if (isUpdate){
                new Alert(Alert.AlertType.INFORMATION,"Updated Item ").show();
                loadItemTable();
            }
       else {
            new Alert(Alert.AlertType.ERROR,"Item Not Updated ").show();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colPackSize.setCellValueFactory(new PropertyValueFactory<>("packSize"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));

        loadItemTable();

        tblItem.getSelectionModel().selectedItemProperty().addListener((observableValue, item, newItemValue) ->  {
            if (newItemValue!=null){
                setValueToText(newItemValue);
            }
        });
    }

    void loadItemTable(){

            tblItem.setItems(itemController.getAllItem());

    }

    private void setValueToText(Item newItemValue){
        txtItemCode.setText(newItemValue.getItemCode());
        txtDescription.setText(newItemValue.getDescription());
        txtPackSize.setText(newItemValue.getPackSize());
        txtUnitPrice.setText(newItemValue.getUnitPrice()+"");
        txtQtyOnHand.setText(newItemValue.getQtyOnHand()+"");
    }

    public void OnActionCustomerForm(ActionEvent actionEvent) {
        Stage stage = new Stage();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/customer_form.fxml"))));
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
