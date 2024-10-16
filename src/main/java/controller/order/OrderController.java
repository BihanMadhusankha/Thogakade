package controller.order;

import controller.item.ItemController;
import db.DBConnection;
import model.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OrderController {

    public boolean placeOrder(Order order) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try{
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO orders VALUE(?,?,?)");
            preparedStatement.setObject(1,order.getOrderId());
            preparedStatement.setObject(2,order.getLocalDate());
            preparedStatement.setObject(3,order.getCustId());
            boolean isOrderAdd = preparedStatement.executeUpdate() > 0;
            if (isOrderAdd){
                boolean isOrderDetails = OrderDetailsController.addOrderDetails(order.getOrderDetails());

                if (isOrderDetails){
                    boolean isUpdateStock = new ItemController().updateStock(order.getOrderDetails());
                    if (isUpdateStock){
                        connection.commit();
                        return true;
                    }
                }
            }
            connection.rollback();
            return false;
        }finally {
            connection.setAutoCommit(true);
        }

    }
}
