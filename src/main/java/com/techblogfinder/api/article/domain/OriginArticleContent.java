package com.techblogfinder.api.article.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
}
