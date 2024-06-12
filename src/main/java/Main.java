import java.time.ZonedDateTime;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        //Setup
        OrderMapRepo orderRepo = new OrderMapRepo();

        Product product1 = new Product("1", "Apfel");
        Product product2 = new Product("2", "Karotte");
        Product product3 = new Product("3", "Mango");
        ProductRepo productRepo = new ProductRepo() {{
            addProduct(product1);
            addProduct(product2);
            addProduct(product3);
        }};
        ShopService shopService = new ShopService(productRepo, orderRepo);

        try {
            shopService.addOrder(List.of(product1.id(), product2.id()));
            shopService.addOrder(List.of(product1.id()));
            shopService.addOrder(List.of(product2.id(), product3.id()));
            shopService.addOrder(List.of(product3.id(), product1.id()));
        } catch (IDNotNullException _) {
        }

        orderRepo.getOrders().forEach(System.out::println);
    }
}