public class Restaurant extends Entity {
    private final String name;
    private final Chef chef;

    private final Menu menu = new Menu("Restaurant Menu");

    private final KitchenService kitchen = new KitchenService();

    public Restaurant(String name, Chef chef) {
        this.name = name;
        this.chef = chef;
    }

    public Menu getMenu() {
        return menu;
    }

    public Chef getChef() {
        return chef;
    }

    public KitchenService getKitchen() {
        return kitchen;
    }

    public void showRestaurantInfo() {
        System.out.println("\n===== " + name + " =====");
        chef.cook();
    }
}