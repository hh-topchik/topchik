package enums;

/**
 * Перечисление возможных статусов Пулл Реквестов
 * */
public enum PullRequestStatus {
    UNKNOWN(-1, "Unknown status"),
    ALL(0, "All"),
    OPEN(1, "Open"),
    CLOSED(2, "Closed"),
    MERGED(3, "Merged");

    private int id;
    private String description;

    PullRequestStatus(final int id, final String description) {
        this.id = id;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static PullRequestStatus getById(final int id) {
        for (final PullRequestStatus status : PullRequestStatus.values()) {
            if (status.id == id) {
                return status;
            }
        }
        return UNKNOWN;
    }
}
