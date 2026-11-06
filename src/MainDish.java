public class MainDish extends MenuItem {
    private final int weight;
    private final boolean spicy;

    public MainDish(String name, double price, String category,
                    boolean available, int weight, boolean spicy) {

        super(name, price, category, available);
        this.weight = weight;
        this.spicy = spicy;
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println(
                "  Weight: " + weight + " g, Spicy: " + spicy
        );
    }

    public void cook() {
        System.out.println("Cooking " + getName());
    }
}