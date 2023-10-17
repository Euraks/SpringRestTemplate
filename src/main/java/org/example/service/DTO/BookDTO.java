package org.example.service.DTO;

import java.util.List;
import java.util.UUID;

public class BookDTO {
    private UUID uuid;
    private String bookText;
    private List<TagDTO> tagEntities;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getBookText() {
        return bookText;
    }

    public void setBookText(String bookText) {
        this.bookText = bookText;
    }

    public List<TagDTO> getTagEntities() {
        return tagEntities;
    }

    public void setTagEntities(List<TagDTO> tagEntities) {
        this.tagEntities = tagEntities;
    }
}
