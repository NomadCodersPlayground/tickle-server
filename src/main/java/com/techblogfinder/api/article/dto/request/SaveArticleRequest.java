package com.techblogfinder.api.article.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.techblogfinder.api.article.domain.ArticleDocument;
import com.techblogfinder.api.article.enumerable.ArticleCategory;
import com.techblogfinder.api.common.dto.OpenGraphMetaInfo;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class SaveArticleRequest {
    private String userId;
    private String userName;
    private String title;
    private String url;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDate dueDate;
    private String category;
    private String description;
    private List<String> tags;

    public ArticleDocument toEntity(String content, OpenGraphMetaInfo openGraphMetaInfo) {
        return ArticleDocument.builder()
                .userId(userId)
                .userName(userName)
                .title(title)
                .url(url)
                .dueDate(dueDate)
                .category(ArticleCategory.of(category))
                .description(description)
                .tags(tags)
                .content(content)
                .ogTitle(openGraphMetaInfo.getTitle())
                .ogDescription(openGraphMetaInfo.getDescription())
                .ogImage(openGraphMetaInfo.getImageUrl())
                .build();
    }
}
