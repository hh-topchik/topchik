package enums;

/**
 * Перечисление категорий, по которым составляются топы
 * */
public enum Category {
    UNKNOWN(-1),
    SPRINTERS(1),
    MIND_GIGANTS(2),
    RENOVATORS(3),
    MASTERS(4),
    OVERSEERS(5),
    PATRONS(6),
    KIND_MEN(7);

    private int id;

    Category(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static Category getById(int id) {
        for (Category category : Category.values()) {
            if (category.id == id) {
                return category;
            }
        }
        return UNKNOWN;
    }
}
