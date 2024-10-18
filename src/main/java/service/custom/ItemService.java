package service.custom;

import javafx.collections.ObservableList;
import model.Item;
import service.SuperService;

public interface ItemService extends SuperService {
    boolean addItem(Item item);
    Item searchItem(String id);
    boolean updateItem(Item item);
    boolean deleteItem(String id);
    ObservableList<Item> getAllItem();
}
