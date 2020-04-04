package enums;

/**
 * Перечисление медалей, выдающихся топ-3 по каждой категории
 * */
public enum Medal {
    UNKNOWN(-1),
    NONE(0),
    GOLD(1),
    SILVER(2),
    BRONZE(3);

    private int id;

    Medal(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static Medal getById(int id) {
        for (Medal medal : Medal.values()) {
            if (medal.id == id) {
                return medal;
            }
        }
        return UNKNOWN;
    }
}
