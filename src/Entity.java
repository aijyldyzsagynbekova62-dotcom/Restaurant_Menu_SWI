public abstract class Entity {
    private static int nextId = 1;

    private final int id = nextId++;

    public int getId() {
        return id;
    }

    @Override
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Entity other = (Entity) obj;
        return id == other.id;
    }

    @Override
    public final int hashCode() {
        return Integer.hashCode(id);
    }
}
