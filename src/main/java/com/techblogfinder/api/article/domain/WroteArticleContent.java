package com.techblogfinder.api.article.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor
public class WroteArticleContent {
    private String title;
    private String description;

    public WroteArticleContent(String title, String description) {
        this.title = title;
        this.description = description;
    }
}
