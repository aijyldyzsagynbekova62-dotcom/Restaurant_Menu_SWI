public class Drink extends MenuItem {
    private final String size;
    private final boolean cold;

    public Drink(String name, double price, String category,
                 boolean available, String size, boolean cold) {

        super(name, price, category, available);
        this.size = size;
        this.cold = cold;
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("  Size: " + size + ", Cold: " + cold);
    }

    public void addIce() {
        System.out.println("Ice added to " + getName());
    }
}