package org.iesfm.shop;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class Tag {

    private int articleId;
    private String name;

    @JsonCreator
    public Tag(
            @JsonProperty("article_id") int articleId,
            @JsonProperty("name") String name) {
        this.articleId = articleId;
        this.name = name;
    }

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tag tag = (Tag) o;
        return articleId == tag.articleId && Objects.equals(name, tag.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(articleId, name);
    }

    @Override
    public String toString() {
        return "Tag{" +
                "article_id=" + articleId +
                ", name='" + name + '\'' +
                '}';
    }
}
