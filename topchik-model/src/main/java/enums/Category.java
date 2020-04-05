package enums;

/**
 * Перечисление категорий, по которым составляются топы
 * */
public enum Category {
    UNKNOWN(-1, "Неизвестная категория"),
    SPRINTERS(1, "Количество замёрдженных PR"),
    MIND_GIGANTS(2, "Общее количество строк кода"),
    RENOVATORS(3, "Количество удаленных строк кода"),
    MASTERS(4, "Количество комментариев на ревью в PR других людей"),
    OVERSEERS(5, "Количество PR, в которых оставили комментарий"),
    PATRONS(6, "Количество апрувнутых PR"),
    KIND_MEN(7, "Скорость апрува");

    private int id;
    private String description;

    Category(int id, String description) {
        this.id = id;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
