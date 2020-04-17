package enums;

/**
 * Перечисление возможных статусов Апрува
 * */
public enum ReviewStatus {
    UNKNOWN(-1, "Unknown status"),
    PENDING(0, "Pending"),
    APPROVED(1, "Approved"),
    CHANGES_REQUESTED(2, "Changes requested"),
    COMMENTED(3, "Commented"),
    DISMISSED(4, "Dismissed");

    private int id;
    private String description;

    ReviewStatus(final int id, final String description) {
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

    public static ReviewStatus getById(final int id) {
        for (final ReviewStatus status : ReviewStatus.values()) {
            if (status.id == id) {
                return status;
            }
        }
        return UNKNOWN;
    }
}
