package com.techblogfinder.api.article.dto.response;

import com.techblogfinder.api.article.domain.ArticleDocument;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class GetArticleResponse {
    private Long id;
    private String uri;
    private String title;
    private String description;
    private String mainImageUrl;
    private LocalDate createdDate;

    public GetArticleResponse(ArticleDocument articleDocument) {
        this.id = articleDocument.getId();
        this.uri = articleDocument.getUrl();
        this.title = articleDocument.getOriginArticleContent().getTitle();
        this.description = articleDocument.getOriginArticleContent().getDescription();
        this.mainImageUrl = articleDocument.getOriginArticleContent().getMainImageUrl();
        this.createdDate = articleDocument.getCreatedDate();
    }
}
