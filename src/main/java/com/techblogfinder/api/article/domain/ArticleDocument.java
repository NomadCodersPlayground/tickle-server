package com.techblogfinder.api.article.domain;

import com.techblogfinder.api.common.converter.ListToStringConverter;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.elasticsearch.annotations.*;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.data.elasticsearch.annotations.FieldType.Text;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Document(indexName = "articles")
public class ArticleDocument {
    @Id
    @Field(type = FieldType.Keyword)
    private Long id;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "title", column = @Column(name = "title")),
            @AttributeOverride(name = "description", column = @Column(name = "description")),
            @AttributeOverride(name = "mainImageUrl", column = @Column(name = "mainImageUrl"))
    })
    private OriginArticleContent originArticleContent;

    @MultiField(
            mainField = @Field(type = FieldType.Text, analyzer = "korean_analyzer", searchAnalyzer = "korean_analyzer"),
            otherFields = {
                    @InnerField(suffix = "english", type = FieldType.Text, analyzer = "standard", searchAnalyzer = "standard")
            }
    )
    private String articleContents;

    private String url;

    @Field(type= Text)
    @Convert(converter = ListToStringConverter.class)
    private List<String> tags;

    @Field(type = FieldType.Long)
    private Long views;

    @Field(type = FieldType.Long)
    private Long todayViews;

    @Field(type = FieldType.Long)
    private Long weeklyViews;

    @Field(type = FieldType.Long)
    private Long monthlyViews;

    @Field(type = FieldType.Date)
    private LocalDate createdDate;

    public static ArticleDocument of(Article article, String articleContents) {
        return ArticleDocument.builder()
                .id(article.getId())
                .articleContents(articleContents)
                .tags(article.getTagNames())
                .originArticleContent(article.getOriginArticleContent())
                .createdDate(LocalDate.now())
                .url(article.getUrl())
                .views(0L)
                .todayViews(0L)
                .weeklyViews(0L)
                .monthlyViews(0L)
                .build();
    }

    public void increaseViewCount() {
        this.views++;
        this.todayViews++;
        this.weeklyViews++;
        this.monthlyViews++;
    }
}
