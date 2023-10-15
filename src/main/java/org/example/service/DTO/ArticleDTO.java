package org.example.service.DTO;

import java.util.UUID;

public class ArticleDTO {

    private UUID uuid;
    private String text;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
