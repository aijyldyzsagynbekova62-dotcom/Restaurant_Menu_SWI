import java.util.*;

public class KitchenService {

    private final Queue<Order> queue = new ArrayDeque<>();

    private final Map<Integer, Order> orders =
            new LinkedHashMap<>();

    public boolean submitOrder(Order order) {
        if (order == null || orders.containsKey(order.getId())
                || !order.submit()) {
            return false;
        }

        orders.put(order.getId(), order);
        queue.offer(order);
        order.getCustomer().addOrder(order);

        return true;
    }

    public Order takeNextOrder() {
        Order order = queue.poll();

        if (order != null) {
            order.startPreparing();
        }

        return order;
    }

    public boolean markOrderReady(int id) {
        Order order = orders.get(id);
        return order != null && order.markReady();
    }

    public Order searchById(int id) {
        return orders.get(id);
    }

    public boolean cancelOrder(int id) {
        Order order = orders.get(id);

        if (order == null || order.getStatus() != Order.Status.QUEUED) {
            return false;
        }

        queue.remove(order);
        orders.remove(id);
        order.getCustomer().removeOrder(order);

        return true;
    }

    public void showQueue() {
        System.out.println("\n===== QUEUE =====");

        if (queue.isEmpty()) {
            System.out.println("Queue is empty.");
        }

        for (Order order : queue) {
            order.displayInfo();
        }
    }

    public void showOrders() {
        if (orders.isEmpty()) {
            System.out.println("No orders.");
        }

        for (Order order : orders.values()) {
            order.displayInfo();
        }
    }

    public void showStatistics() {
        double revenue = 0;
        int paid = 0;

        for (Order order : orders.values()) {
            if (order.getStatus() == Order.Status.PAID) {
                revenue += order.getTotalPrice();
                paid++;
            }
        }

        System.out.println("Total orders: " + orders.size());
        System.out.println("Waiting: " + queue.size());
        System.out.println("Paid orders: " + paid);
        System.out.printf("Revenue: $%.2f%n", revenue);
    }
}
