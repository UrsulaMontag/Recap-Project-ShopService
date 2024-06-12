import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ShopService {
    private OrderRepo orderRepo = new OrderMapRepo();
    private ProductRepo productRepo = new ProductRepo();

    public Order addOrder(List<String> productIds) throws IDNotNullException {
        List<Product> products = new ArrayList<>();
        for (String productId : productIds) {
            Product productToOrder = productRepo.getProductById(productId).orElseThrow(() -> new IDNotNullException("Product mit der Id: " + productId + " konnte nicht bestellt werden!"));
            products.add(productToOrder);
        }

        Order newOrder = new Order(UUID.randomUUID().toString(), products, OrderStatus.PROCESSING);

        return orderRepo.addOrder(newOrder);
    }

    public List<Order> getOrdersByStatus(OrderStatus orderStatus) {
        return orderRepo.getOrders().stream()
                .filter(order -> order.orderStatus().equals(orderStatus)).toList();
    }

    //TODO: delete this when mock the products. Actually needed for tests
    public void setProductRepo(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

}
