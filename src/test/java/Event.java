import java.time.LocalDateTime;
import java.util.UUID;

public class Event {
    private UUID uuid;
    private LocalDateTime date;
    private String description;


    public Event(UUID uuid, LocalDateTime date, String description) {
        this.uuid = uuid;
        this.date = date;
        this.description = description;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
