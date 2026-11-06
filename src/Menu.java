import java.util.*;

public class Menu extends Entity {
    private final String name;

    private final List<MenuItem> items = new ArrayList<>();

    private final Map<Integer, MenuItem> byId = new HashMap<>();

    private final Map<String, Set<MenuItem>> byCategory =
            new TreeMap<>();

    public Menu(String name) {
        this.name = name;
    }

    public boolean addItem(MenuItem item) {
        if (item == null || byId.containsKey(item.getId())) {
            return false;
        }

        items.add(item);
        byId.put(item.getId(), item);

        byCategory.computeIfAbsent(
                item.getCategory(), key -> new LinkedHashSet<>()
        ).add(item);

        return true;
    }

    public MenuItem searchById(int id) {
        return byId.get(id);
    }

    public boolean updatePrice(int id, double price) {
        MenuItem item = byId.get(id);

        if (item == null || !Double.isFinite(price) || price < 0) {
            return false;
        }

        item.setPrice(price);
        return true;
    }

    private void removeFromCategory(MenuItem item) {
        Set<MenuItem> group = byCategory.get(item.getCategory());

        if (group != null) {
            group.remove(item);

            if (group.isEmpty()) {
                byCategory.remove(item.getCategory());
            }
        }
    }

    public boolean updateCategory(int id, String category) {
        MenuItem item = byId.get(id);

        if (item == null || category == null
                || category.trim().isEmpty()) {
            return false;
        }

        removeFromCategory(item);
        item.setCategory(category);

        byCategory.computeIfAbsent(
                item.getCategory(), key -> new LinkedHashSet<>()
        ).add(item);

        return true;
    }

    public boolean updateAvailability(int id, boolean available) {
        MenuItem item = byId.get(id);

        if (item == null) {
            return false;
        }

        item.setAvailable(available);
        return true;
    }

    public boolean removeItem(int id) {
        MenuItem item = byId.remove(id);

        if (item == null) {
            return false;
        }

        items.remove(item);
        removeFromCategory(item);

        return true;
    }

    public void showMenu() {
        System.out.println("\n===== " + name + " =====");

        if (items.isEmpty()) {
            System.out.println("Menu is empty.");
        }

        for (MenuItem item : items) {
            item.displayInfo();
        }
    }

    public void showSorted(Comparator<MenuItem> comparator) {
        List<MenuItem> copy = new ArrayList<>(items);
        copy.sort(comparator);

        for (MenuItem item : copy) {
            item.displayInfo();
        }
    }

    public void showByCategory(String category) {
        Set<MenuItem> group = byCategory.get(category);

        if (group == null) {
            System.out.println("Category not found.");
            return;
        }

        for (MenuItem item : group) {
            item.displayInfo();
        }
    }

    public void showByPrice(double maxPrice) {
        for (MenuItem item : items) {
            if (item.getPrice() <= maxPrice) {
                item.displayInfo();
            }
        }
    }

    public void showStatistics() {
        double sum = 0;
        int available = 0;

        for (MenuItem item : items) {
            sum += item.getPrice();

            if (item.isAvailable()) {
                available++;
            }
        }

        System.out.println("Total items: " + items.size());
        System.out.println("Available: " + available);

        System.out.printf(
                "Average price: $%.2f%n",
                items.isEmpty() ? 0 : sum / items.size()
        );

        for (String category : byCategory.keySet()) {
            System.out.println(
                    category + ": " + byCategory.get(category).size()
            );
        }
    }
}