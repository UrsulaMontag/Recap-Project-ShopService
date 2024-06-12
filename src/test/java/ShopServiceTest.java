import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ShopServiceTest {
    private static ShopService shopService;


    @BeforeEach
    void setUp() {
        shopService = new ShopService();

        Product product1 = new Product("1", "Apfel");
        Product product2 = new Product("2", "Karotte");
        Product product3 = new Product("3", "Mango");

        shopService.getProductRepo().addProduct(product1);
        shopService.getProductRepo().addProduct(product2);
        shopService.getProductRepo().addProduct(product3);

        Order order1 = new Order("1", List.of(product1, product2), OrderStatus.COMPLETED);
        Order order2 = new Order("2", List.of(product1), OrderStatus.PROCESSING);
        Order order3 = new Order("3", List.of(product2, product3), OrderStatus.COMPLETED);
        Order order4 = new Order("4", List.of(product3, product1), OrderStatus.IN_DELIVERY);

        shopService.getOrderRepo().addOrder(order1);
        shopService.getOrderRepo().addOrder(order2);
        shopService.getOrderRepo().addOrder(order3);
        shopService.getOrderRepo().addOrder(order4);

    }


    @Test
    void addOrderTest() throws IDNotNullException {
        //GIVEN
        List<String> productsIds = List.of("1");

        //WHEN
        Order actual = shopService.addOrder(productsIds);

        //THEN
        Order expected = new Order("-1", List.of(new Product("1", "Apfel")), OrderStatus.PROCESSING);
        assertEquals(expected.products(), actual.products());
        assertNotNull(expected.id());

    }

    @Test
    void addOrderTest_whenInvalidProductId_expectNull() {
        assertThrows(IDNotNullException.class, () -> shopService.addOrder(List.of("1", "invalid-id")));
    }

    @Test
    void getOrdersByStatus_returnsListOfOrders_withGivenStatus() {
        System.out.println(shopService.getProductRepo());
        int actualProcessingOrders = shopService.getOrdersByStatus(OrderStatus.PROCESSING).size();
        int actualCompletedOrders = shopService.getOrdersByStatus(OrderStatus.COMPLETED).size();
        int actualInDeliveryOrders = shopService.getOrdersByStatus(OrderStatus.IN_DELIVERY).size();

        assertEquals(1, actualProcessingOrders);
        assertEquals(2, actualCompletedOrders);
        assertEquals(1, actualInDeliveryOrders);
    }

    @Test
    void updateOrder_createsNewOrder_OnBaseOfOrderToUpdate() throws IDNotNullException {
        String orderId = "2";
        OrderStatus newOrderStatus = OrderStatus.IN_DELIVERY;
        OrderStatus actual = shopService.getOrderRepo()
                .getOrderById(orderId)
                .orElseThrow(() -> new IDNotNullException("id not found"))
                .orderStatus();
        shopService.updateOrder(orderId, newOrderStatus);
        OrderStatus expected = shopService.getOrderRepo()
                .getOrderById(orderId)
                .orElseThrow(() -> new IDNotNullException("id not found"))
                .orderStatus();
        assertEquals(expected, newOrderStatus);
        assertEquals(OrderStatus.PROCESSING, actual);
    }
}
