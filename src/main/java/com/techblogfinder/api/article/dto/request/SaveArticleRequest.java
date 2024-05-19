package com.techblogfinder.api.article.dto.request;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import com.opencsv.bean.CsvDate;
import com.techblogfinder.api.article.domain.ArticleDocument;
import com.techblogfinder.api.article.enumerable.ArticleCategory;
import com.techblogfinder.api.common.converter.StringToListConverter;
import com.techblogfinder.api.common.dto.OpenGraphMetaInfo;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class SaveArticleRequest {
    @CsvBindByName(column = "user_id")
    private String userId;

    @CsvBindByName(column = "username")
    private String userName;

    @CsvBindByName(column = "title")
    private String title;

    @CsvBindByName(column = "content_url")
    private String url;

    @CsvBindByName(column = "dt")
    @CsvDate(value = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dueDate;

    @CsvBindByName(column = "category")
    private String category;

    @CsvBindByName(column = "description")
    private String description;

    @CsvCustomBindByName(column = "tags", converter = StringToListConverter.class)
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
