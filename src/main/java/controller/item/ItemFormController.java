package controller.item;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Item;
import util.CrudUtil;

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

    @FXML
    void OnActionAddItem(ActionEvent event) {
        Item item = new Item(
                txtItemCode.getText(),
                txtDescription.getText(),
                txtPackSize.getText(),
                Double.parseDouble(txtUnitPrice.getText()),
                Integer.parseInt(txtQtyOnHand.getText())
                );


        try {

            boolean isItemAdd = CrudUtil.execute(
                    "INSERT INTO item VALUES(?,?,?,?,?)",
                    item.getItemCode(),
                    item.getDescription(),
                    item.getPackSize(),
                    item.getUnitPrice(),
                    item.getQtyOnHand()
                    );
            if (isItemAdd){
                new Alert(Alert.AlertType.INFORMATION,"Items Added :)").show();
                loadItemTable();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,"Items not Added :)").show();
        }
    }

    @FXML
    void OnActionDeleteItem(ActionEvent event) {

        try {

            boolean isDelete = CrudUtil.execute(
                    "DELETE FROM item WHERE ItemCode= ?",
                    txtItemCode.getText()
            );
            if (isDelete){
                new Alert(Alert.AlertType.INFORMATION,"Deleted Item ").show();
                loadItemTable();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,"Item Not Deleted :(").show();
        }
    }

    @FXML
    void OnActionSearchItem(ActionEvent event) {

        try {

            ResultSet resultSet = CrudUtil.execute(
                    "SELECT * FROM item WHERE ItemCode= ?",
                    txtItemCode.getText()
                    );
            resultSet.next();
            Item item = new Item(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDouble(4),
                    resultSet.getInt(5)
            );
            setValueToText(item);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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

        try {

            boolean isUpdate = CrudUtil.execute(
                    "UPDATE item SET Description=?,PackSize=?,UnitPrice=?,QtyOnHand=? WHERE ItemCode=?",
                    item.getDescription(),
                    item.getPackSize(),
                    item.getUnitPrice(),
                    item.getQtyOnHand(),
                    item.getItemCode()
            );
            if (isUpdate){
                new Alert(Alert.AlertType.INFORMATION,"Updated Item ").show();
                loadItemTable();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,"Item Not Updated ").show();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadItemTable();

        tblItem.getSelectionModel().selectedItemProperty().addListener((observableValue, item, newItemValue) ->  {
            if (newItemValue!=null){
                setValueToText(newItemValue);
            }
        });
    }

    void loadItemTable(){
        ObservableList<Item> itemObservableList = FXCollections.observableArrayList();

        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colPackSize.setCellValueFactory(new PropertyValueFactory<>("packSize"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));


        try {
            ResultSet resultSet = CrudUtil.execute("SELECT * FROM item");
            while (resultSet.next()){
                Item item = new Item(
                        resultSet.getString("ItemCode"),
                        resultSet.getString("Description"),
                        resultSet.getString("PackSize"),
                        resultSet.getDouble("UnitPrice"),
                        resultSet.getInt("QtyOnHand")
                );
                itemObservableList.add(item);
            }
            tblItem.setItems(itemObservableList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setValueToText(Item newItemValue){
        txtItemCode.setText(newItemValue.getItemCode());
        txtDescription.setText(newItemValue.getDescription());
        txtPackSize.setText(newItemValue.getPackSize());
        txtUnitPrice.setText(newItemValue.getUnitPrice()+"");
        txtQtyOnHand.setText(newItemValue.getQtyOnHand()+"");
    }

}
