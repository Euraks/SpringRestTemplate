package org.example.service.DTO;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AuthorEntityDTO {

    private UUID uuid;
    private String authorName;
    private List<ArticleDTO> articleList;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public List<ArticleDTO> getArticleList() {
        if (this.articleList == null) {
            this.articleList = new ArrayList<>();
        }
        return this.articleList;
    }

    public void setArticleList(List<ArticleDTO> articleList) {
        this.articleList = articleList;
    }

}
