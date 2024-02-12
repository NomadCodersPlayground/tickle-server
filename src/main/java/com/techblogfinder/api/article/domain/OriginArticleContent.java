package com.techblogfinder.api.article.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Getter
@Embeddable
@NoArgsConstructor
public class OriginArticleContent {

    private String title;
    private String description;
    private String mainImageUrl;

    public OriginArticleContent(String title, String description, String mainImageUrl) {
        this.title = title;
        this.description = description;
        this.mainImageUrl = mainImageUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OriginArticleContent that = (OriginArticleContent) o;
        return Objects.equals(title, that.title) && Objects.equals(description, that.description) && Objects.equals(mainImageUrl, that.mainImageUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, mainImageUrl);
    }
}
