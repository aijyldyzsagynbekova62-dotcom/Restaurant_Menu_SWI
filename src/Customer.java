import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Customer extends Entity {
    private final String name;
    private final List<Order> orders = new ArrayList<>();

    public Customer(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name is required.");
        }

        this.name = name.trim();
    }

    public String getName() {
        return name;
    }

    public List<Order> getOrders() {
        return Collections.unmodifiableList(orders);
    }

    void addOrder(Order order) {
        if (!orders.contains(order)) {
            orders.add(order);
        }
    }

    void removeOrder(Order order) {
        orders.remove(order);
    }

    public void displayInfo() {
        System.out.println(
                "#" + getId() + " | " + name
                        + " | Orders: " + orders.size()
        );
    }
}
