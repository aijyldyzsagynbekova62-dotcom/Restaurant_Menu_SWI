import java.util.Comparator;

public class MenuItemComparators {

    public static final Comparator<MenuItem> BY_NAME =
            Comparator.comparing(MenuItem::getName)
                    .thenComparingInt(MenuItem::getId);

    public static final Comparator<MenuItem> BY_PRICE =
            Comparator.comparingDouble(MenuItem::getPrice)
                    .thenComparingInt(MenuItem::getId);

    public static final Comparator<MenuItem> BY_CATEGORY =
            Comparator.comparing(MenuItem::getCategory)
                    .thenComparing(MenuItem::getName)
                    .thenComparingInt(MenuItem::getId);
}