package com.techblogfinder.api.article.domain;

import com.techblogfinder.api.article.enumerable.ArticleCategory;
import com.techblogfinder.api.common.converter.ListToStringConverter;
import jakarta.persistence.Convert;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@Document(indexName = "articles")
public class ArticleDocument {

    @Id
    @Field(name = "id", type = FieldType.Keyword)
    private String id;

    @Field(name = "user_id", type = FieldType.Keyword)
    private String userId;

    @Field(name = "user_name", type = FieldType.Keyword)
    private String userName;

    @Field(name = "title", type = FieldType.Keyword)
    private String title;

    @Field(name = "url", type = FieldType.Text)
    private String url;

    @Field(name = "due_date", type = FieldType.Date)
    private LocalDateTime dueDate;

    @Field(name = "category", type = FieldType.Keyword)
    private ArticleCategory category;

    @Field(name = "description", type = FieldType.Text)
    private String description;

    @Convert(converter = ListToStringConverter.class)
    @Field(name = "tags", type = FieldType.Keyword)
    private List<String> tags;

    @MultiField(
            mainField = @Field(type = FieldType.Text, analyzer = "korean_analyzer", searchAnalyzer = "korean_analyzer"),
            otherFields = {
                    @InnerField(suffix = "english", type = FieldType.Text, analyzer = "standard", searchAnalyzer = "standard")
            }
    )
    private String content;

    @MultiField(
            mainField = @Field(type = FieldType.Text, analyzer = "korean_analyzer", searchAnalyzer = "korean_analyzer"),
            otherFields = {
                    @InnerField(suffix = "english", type = FieldType.Text, analyzer = "standard", searchAnalyzer = "standard")
            }
    )
    private String ogTitle;

    @Field(name = "og_description", type = FieldType.Text)
    private String ogDescription;

    @Field(name = "og_image", type = FieldType.Keyword)
    private String ogImage;

    @Field(name = "created_at", type = FieldType.Date)
    private LocalDate createdAt;

    @Field(name = "updated_at", type = FieldType.Date)
    private LocalDate updatedAt;


    @Builder
    public ArticleDocument(
            String userId,
            String userName,
            String title,
            String url,
            LocalDateTime dueDate,
            ArticleCategory category,
            String description,
            List<String> tags,
            String content,
            String ogTitle,
            String ogDescription,
            String ogImage
    ) {
        this.userId = userId;
        this.userName = userName;
        this.title = title;
        this.url = url;
        this.dueDate = dueDate;
        this.category = category;
        this.description = description;
        this.tags = tags;
        this.content = content;
        this.ogTitle = ogTitle;
        this.ogDescription = ogDescription;
        this.ogImage = ogImage;
    }
}
