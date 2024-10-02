package controller.item;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import model.Item;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ItemController implements ItemServices{

    @Override
    public boolean addItem(Item item) {
        try {
            return CrudUtil.execute(
                    "INSERT INTO item VALUES(?,?,?,?,?)",
                    item.getItemCode(),
                    item.getDescription(),
                    item.getPackSize(),
                    item.getUnitPrice(),
                    item.getQtyOnHand()
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Item searchItem(String itemCode) {
        try {

            ResultSet resultSet = CrudUtil.execute(
                    "SELECT * FROM item WHERE ItemCode= ?",
                    itemCode
            );
            resultSet.next();
            return new Item(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDouble(4),
                    resultSet.getInt(5)
            );

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean updateItem(Item item) {
        try {
            return CrudUtil.execute(
                    "UPDATE item SET Description=?,PackSize=?,UnitPrice=?,QtyOnHand=? WHERE ItemCode=?",
                    item.getDescription(),
                    item.getPackSize(),
                    item.getUnitPrice(),
                    item.getQtyOnHand(),
                    item.getItemCode()
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public boolean deleteItem(String itemCode) {
        try {
            return CrudUtil.execute(
                     "DELETE FROM item WHERE ItemCode= ?",
                    itemCode
             );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ObservableList<Item> getAllItem() {
        ObservableList<Item> itemObservableList = FXCollections.observableArrayList();
        ResultSet resultSet = null;
        try {
            resultSet = CrudUtil.execute("SELECT * FROM item");
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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return  itemObservableList;
    }

    public ObservableList<String> getItemCodes(){
        ObservableList<Item> itemObservableList = getAllItem();
        ObservableList<String> itemList = FXCollections.observableArrayList();

        itemObservableList.forEach(item -> {
            itemList.add(item.getItemCode());
        });
        return itemList;
    }
}
