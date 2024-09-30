package controller.item;

import model.Item;

public interface ItemServices {
    boolean addItem(Item item);
    boolean searchItem(Item item);
    boolean updateItem(Item item);
    boolean deleteItem(Item item);
}
