package controller.order;

import model.OrderDetail;
import util.CrudUtil;

import java.sql.SQLException;
import java.util.List;

public class OrderDetailsController {
    public static boolean addOrderDetails(List<OrderDetail> orderDetails) throws SQLException {
        for (OrderDetail orderDetail : orderDetails){
            boolean isOrderDetailsAdd = addOrderDetails(orderDetail);
            if (!isOrderDetailsAdd){
                return false;
            }
        }
        return true;
    }
    public static boolean addOrderDetails(OrderDetail orderDetails) throws SQLException {
      return CrudUtil.execute("INSERT INTO orderdetail VALUES(?,?,?,?)",
                orderDetails.getOrderId(),
                orderDetails.getItemCode(),
                orderDetails.getQty(),
                orderDetails.getDiscount()
        );
    }
}
