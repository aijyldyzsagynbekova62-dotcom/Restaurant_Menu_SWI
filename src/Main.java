import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

    private static final Scanner input = new Scanner(System.in);

    private static final Restaurant restaurant =
            new Restaurant("My Restaurant", new Chef("Alex"));

    private static final Menu menu = restaurant.getMenu();

    private static final KitchenService kitchen =
            restaurant.getKitchen();

    private static final Map<Integer, Customer> customers =
            new LinkedHashMap<>();

    public static void main(String[] args) {
        fillMenu();
        restaurant.showRestaurantInfo();

        while (true) {
            System.out.println("\n===== MAIN MENU =====");
            System.out.println("1. Administrator: manage menu");
            System.out.println("2. Waiter: create order");
            System.out.println("3. Chef: kitchen");
            System.out.println("4. Show orders");
            System.out.println("5. Pay for order");
            System.out.println("6. Statistics");
            System.out.println("7. Show customers");
            System.out.println("8. Cancel waiting order");
            System.out.println("0. Exit");

            int choice = readInt("Choose: ");

            try {
                switch (choice) {
                    case 1:
                        manageMenu();
                        break;

                    case 2:
                        createOrder();
                        break;

                    case 3:
                        manageKitchen();
                        break;

                    case 4:
                        kitchen.showOrders();
                        break;

                    case 5:
                        payOrder();
                        break;

                    case 6:
                        menu.showStatistics();
                        kitchen.showStatistics();
                        break;

                    case 7:
                        for (Customer customer : customers.values()) {
                            customer.displayInfo();
                        }
                        break;

                    case 8:
                        showResult(kitchen.cancelOrder(
                                readInt("Order ID: ")
                        ));
                        break;

                    case 0:
                        System.out.println("Goodbye!");
                        return;

                    default:
                        System.out.println("Unknown option.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static void fillMenu() {
        menu.addItem(new MenuItem(
                "Salad", 5.50, "Starter", true
        ));

        menu.addItem(new MenuItem(
                "Soup", 6.00, "Starter", true
        ));

        menu.addItem(new Drink(
                "Cola", 2.50, "Drink", true, "Large", true
        ));

        menu.addItem(new Drink(
                "Coffee", 3.00, "Drink", true, "Medium", false
        ));

        menu.addItem(new MainDish(
                "Pizza", 12.50, "Main Dish", true, 500, false
        ));

        menu.addItem(new Dessert(
                "Cheesecake", 6.50, "Dessert", true,
                "Vanilla", true
        ));
    }

    private static void manageMenu() {
        System.out.println("\n===== ADMINISTRATOR =====");
        System.out.println("1. Show menu");
        System.out.println("2. Add item");
        System.out.println("3. Find by ID");
        System.out.println("4. Update price");
        System.out.println("5. Update category");
        System.out.println("6. Delete item");
        System.out.println("7. Sort");
        System.out.println("8. Filter by category");
        System.out.println("9. Filter by maximum price");
        System.out.println("10. Change availability");
        System.out.println("0. Back");

        switch (readInt("Choose: ")) {
            case 1:
                menu.showMenu();
                break;

            case 2:
                addItem();
                break;

            case 3:
                MenuItem item = menu.searchById(readInt("Item ID: "));

                if (item == null) {
                    System.out.println("Item not found.");
                } else {
                    item.displayInfo();
                }
                break;

            case 4:
                showResult(menu.updatePrice(
                        readInt("Item ID: "),
                        readDouble("New price: ")
                ));
                break;

            case 5:
                showResult(menu.updateCategory(
                        readInt("Item ID: "),
                        readText("New category: ")
                ));
                break;

            case 6:
                showResult(menu.removeItem(readInt("Item ID: ")));
                break;

            case 7:
                sortMenu();
                break;

            case 8:
                menu.showByCategory(readText("Category: "));
                break;

            case 9:
                menu.showByPrice(readDouble("Maximum price: "));
                break;

            case 10:
                int id = readInt("Item ID: ");
                int value = readInt("1 = available, 0 = unavailable: ");

                if (value != 0 && value != 1) {
                    System.out.println("Enter 0 or 1.");
                } else {
                    showResult(menu.updateAvailability(id, value == 1));
                }
                break;

            case 0:
                break;

            default:
                System.out.println("Unknown option.");
        }
    }

    private static void addItem() {
        System.out.println("1. Basic item");
        System.out.println("2. Drink");
        System.out.println("3. Main dish");
        System.out.println("4. Dessert");

        int type = readInt("Type: ");

        if (type < 1 || type > 4) {
            System.out.println("Unknown type.");
            return;
        }

        String name = readText("Name: ");
        double price = readDouble("Price: ");
        MenuItem item;

        switch (type) {
            case 2:
                item = new Drink(
                        name, price, "Drink", true,
                        readText("Size: "),
                        readInt("Cold? 1 = yes, 0 = no: ") == 1
                );
                break;

            case 3:
                int weight = readInt("Weight in grams: ");

                if (weight <= 0) {
                    System.out.println("Weight must be positive.");
                    return;
                }

                item = new MainDish(
                        name, price, "Main Dish", true, weight,
                        readInt("Spicy? 1 = yes, 0 = no: ") == 1
                );
                break;

            case 4:
                item = new Dessert(
                        name, price, "Dessert", true,
                        readText("Flavor: "),
                        readInt("Sugar? 1 = yes, 0 = no: ") == 1
                );
                break;

            default:
                item = new MenuItem(
                        name, price, readText("Category: "), true
                );
        }

        showResult(menu.addItem(item));
        item.displayInfo();
    }

    private static void sortMenu() {
        System.out.println("1. ID");
        System.out.println("2. Name");
        System.out.println("3. Price");
        System.out.println("4. Category");

        switch (readInt("Sort by: ")) {
            case 1:
                menu.showSorted(null);
                break;

            case 2:
                menu.showSorted(MenuItemComparators.BY_NAME);
                break;

            case 3:
                menu.showSorted(MenuItemComparators.BY_PRICE);
                break;

            case 4:
                menu.showSorted(MenuItemComparators.BY_CATEGORY);
                break;

            default:
                System.out.println("Unknown option.");
        }
    }

    private static void createOrder() {
        int customerId = readInt(
                "Customer ID, or 0 for a new customer: "
        );

        Customer customer;

        if (customerId == 0) {
            customer = new Customer(readText("Customer name: "));
            customers.put(customer.getId(), customer);
            customer.displayInfo();
        } else {
            customer = customers.get(customerId);
        }

        if (customer == null) {
            System.out.println("Customer not found.");
            return;
        }

        Order order = new Order(customer);
        menu.showMenu();

        while (true) {
            int itemId = readInt("Item ID, or 0 to finish: ");

            if (itemId == 0) {
                break;
            }

            if (!order.addItem(menu.searchById(itemId))) {
                System.out.println("Item not found or unavailable.");
            } else {
                System.out.println("Added.");
            }
        }

        if (kitchen.submitOrder(order)) {
            System.out.println("Order sent to kitchen.");
            order.displayInfo();
        } else {
            System.out.println("Empty order was not submitted.");
        }
    }

    private static void manageKitchen() {
        System.out.println("\n===== CHEF =====");
        System.out.println("1. Show queue");
        System.out.println("2. Take next order");
        System.out.println("3. Mark order ready");
        System.out.println("0. Back");

        switch (readInt("Choose: ")) {
            case 1:
                kitchen.showQueue();
                break;

            case 2:
                restaurant.getChef().takeNextOrder(kitchen);
                break;

            case 3:
                restaurant.getChef().finishOrder(
                        kitchen, readInt("Order ID: ")
                );
                break;

            case 0:
                break;

            default:
                System.out.println("Unknown option.");
        }
    }

    private static void payOrder() {
        Order order = kitchen.searchById(readInt("Order ID: "));

        if (order == null) {
            System.out.println("Order not found.");
            return;
        }

        if (order.processPayment(new CardPayment())) {
            order.displayInfo();
        } else {
            System.out.println(
                    "Only a ready, unpaid order can be paid."
            );
        }
    }

    private static String readText(String message) {
        while (true) {
            System.out.print(message);
            String text = input.nextLine().trim();

            if (!text.isEmpty()) {
                return text;
            }

            System.out.println("Enter a value.");
        }
    }

    private static int readInt(String message) {
        while (true) {
            try {
                return Integer.parseInt(readText(message));
            } catch (NumberFormatException e) {
                System.out.println("Enter a whole number.");
            }
        }
    }

    private static double readDouble(String message) {
        while (true) {
            try {
                double value = Double.parseDouble(
                        readText(message).replace(',', '.')
                );

                if (Double.isFinite(value) && value >= 0) {
                    return value;
                }
            } catch (NumberFormatException e) {
                // Сообщение выводится ниже.
            }

            System.out.println("Enter a non-negative number.");
        }
    }

    private static void showResult(boolean success) {
        System.out.println(
                success ? "Done." : "Operation failed."
        );
    }
}