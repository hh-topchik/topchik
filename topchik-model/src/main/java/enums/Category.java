package enums;

/**
 * Перечисление категорий, по которым составляются топы
 * */
public enum Category {
    UNKNOWN(-1, "Неизвестно", "Неизвестная категория"),
    SPRINTERS(1, "Спринтеры", "Количество замёрдженных PR"),
    MIND_GIGANTS(2, "Гиганты мысли", "Количество добавленных строк кода"),
    RENOVATORS(3, "Реноваторы", "Количество удаленных строк кода"),
    MASTERS(4, "Магистры", "Количество комментариев на ревью в PR других людей"),
    OVERSEERS(5, "Надзиратели", "Количество PR, в которых оставили комментарий"),
    PATRONS(6, "Покровители", "Количество апрувнутых PR"),
    KIND_MEN(7, "Добряки", "Скорость апрува");

    private int id;
    private String title;
    private String description;

    Category(int id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
