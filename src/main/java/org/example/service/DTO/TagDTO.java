package org.example.service.DTO;

import java.util.List;
import java.util.UUID;

public class TagDTO {
    private UUID uuid;
    private String tagName;
    private List<BookDTO> bookEntities;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public List<BookDTO> getBookEntities() {
        return bookEntities;
    }

    public void setBookEntities(List<BookDTO> bookEntities) {
        this.bookEntities = bookEntities;
    }
}
