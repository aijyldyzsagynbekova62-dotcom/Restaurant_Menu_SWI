public class Dessert extends MenuItem {
    private final String flavor;
    private final boolean hasSugar;

    public Dessert(String name, double price, String category,
                   boolean available, String flavor,
                   boolean hasSugar) {

        super(name, price, category, available);
        this.flavor = flavor;
        this.hasSugar = hasSugar;
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println(
                "  Flavor: " + flavor + ", Sugar: " + hasSugar
        );
    }

    public void decorate() {
        System.out.println("Decorating " + getName());
    }
}