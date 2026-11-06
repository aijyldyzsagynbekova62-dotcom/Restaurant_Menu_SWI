public class MenuItem extends Entity
        implements Comparable<MenuItem> {

    private final String name;
    private double price;
    private String category;
    private boolean available;

    public MenuItem(String name, double price,
                    String category, boolean available) {

        if (name == null || name.trim().isEmpty()
                || category == null || category.trim().isEmpty()
                || !Double.isFinite(price) || price < 0) {
            throw new IllegalArgumentException("Invalid item data.");
        }

        this.name = name.trim();
        this.price = price;
        this.category = category.trim();
        this.available = available;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setPrice(double price) {
        if (!Double.isFinite(price) || price < 0) {
            throw new IllegalArgumentException("Invalid price.");
        }

        this.price = price;
    }

    void setCategory(String category) {
        if (category == null || category.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid category.");
        }

        this.category = category.trim();
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public void displayInfo() {
        System.out.printf(
                "#%d | %s | $%.2f | %s | Available: %s%n",
                getId(), name, price, category, available
        );
    }

    @Override
    public int compareTo(MenuItem other) {
        return Integer.compare(getId(), other.getId());
    }
}