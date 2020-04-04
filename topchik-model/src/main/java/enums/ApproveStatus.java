package enums;

/**
 * Перечисление возможных статусов Апрува
 * */
public enum ApproveStatus {
    UNKNOWN(-1, "Unknown status"),
    PENDING(0, "Pending"),
    APPROVED(1, "Approved"),
    CHANGES_REQUESTED(2, "Changes requested"),
    COMMENTED(3, "Commented"),
    DISMISSED(4, "Dismissed");

    private int id;
    private String description;

    ApproveStatus(final int id, final String description) {
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

    public static ApproveStatus getById(final int id) {
        for (final ApproveStatus status : ApproveStatus.values()) {
            if (status.id == id) {
                return status;
            }
        }
        return UNKNOWN;
    }
}
