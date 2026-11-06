public class Chef extends Entity {
    private final String name;

    public Chef(String name) {
        this.name = name;
    }

    public void cook() {
        System.out.println("Chef: " + name);
    }

    public void takeNextOrder(KitchenService kitchen) {
        Order order = kitchen.takeNextOrder();

        if (order == null) {
            System.out.println("No waiting orders.");
            return;
        }

        System.out.println(
                name + " is preparing order #" + order.getId()
        );

        order.displayInfo();
    }

    public void finishOrder(KitchenService kitchen, int id) {
        System.out.println(
                kitchen.markOrderReady(id)
                        ? "Order is ready."
                        : "Order not found or not preparing."
        );
    }
}