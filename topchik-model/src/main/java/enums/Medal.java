package enums;

public enum Medal {
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
}
