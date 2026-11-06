import java.util.ArrayList;
import java.util.List;

public class Order extends Entity {

    public enum Status {
        NEW, QUEUED, PREPARING, READY, PAID
    }

    private static class Line {
        final String name;
        final double price;

        Line(MenuItem item) {
            name = item.getName();
            price = item.getPrice();
        }
    }

    private final Customer customer;
    private final List<Line> lines = new ArrayList<>();

    private Status status = Status.NEW;

    public Order(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("Customer is required.");
        }

        this.customer = customer;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Status getStatus() {
        return status;
    }

    public boolean addItem(MenuItem item) {
        if (status != Status.NEW || item == null
                || !item.isAvailable()) {
            return false;
        }

        lines.add(new Line(item));
        return true;
    }

    public double getTotalPrice() {
        double total = 0;

        for (Line line : lines) {
            total += line.price;
        }

        return total;
    }

    boolean submit() {
        if (status != Status.NEW || lines.isEmpty()) {
            return false;
        }

        status = Status.QUEUED;
        return true;
    }

    boolean startPreparing() {
        if (status != Status.QUEUED) {
            return false;
        }

        status = Status.PREPARING;
        return true;
    }

    boolean markReady() {
        if (status != Status.PREPARING) {
            return false;
        }

        status = Status.READY;
        return true;
    }

    public boolean processPayment(PaymentService payment) {
        if (status != Status.READY || payment == null) {
            return false;
        }

        payment.pay(getTotalPrice());
        status = Status.PAID;
        return true;
    }

    public void displayInfo() {
        System.out.println(
                "\nOrder #" + getId()
                        + " | " + customer.getName()
                        + " | " + status
        );

        for (Line line : lines) {
            System.out.printf(
                    "  %s — $%.2f%n", line.name, line.price
            );
        }

        System.out.printf("Total: $%.2f%n", getTotalPrice());
    }
}