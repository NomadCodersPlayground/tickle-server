package com.techblogfinder.api.article.domain;

import com.techblogfinder.api.article.enumerable.ArticleCategory;
import com.techblogfinder.api.article.enumerable.IndexingStatus;
import com.techblogfinder.api.common.model.BaseModel;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@Entity
@NoArgsConstructor
public class Article extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "MEDIUMBLOB")
    private String htmlContents;

    @Column(columnDefinition = "TEXT")
    private String url;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "title", column = @Column(name = "origin_title")),
            @AttributeOverride(name = "description", column = @Column(name = "origin_description", columnDefinition = "TEXT")),
            @AttributeOverride(name = "mainImageUrl", column = @Column(name = "origin_main_image_url"))
    })
    private OriginArticleContent originArticleContent;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "title", column = @Column(name = "wrote_title")),
            @AttributeOverride(name = "description", column = @Column(name = "wrote_description"))
    })
    private WroteArticleContent wroteArticleContent;

    @Enumerated(value = EnumType.STRING)
    private ArticleCategory category;

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ArticleTag> articleTags = new ArrayList<>();

    @Enumerated(value = EnumType.STRING)
    private IndexingStatus indexingStatus;

    private LocalDateTime indexedAt;

    private LocalDateTime indexingFailedAt;

    @Column(columnDefinition = "TEXT")
    private String failedMessages;

    @Builder
    public Article(OriginArticleContent originArticleContent, WroteArticleContent wroteArticleContent, String htmlContents, String uri, String description, ArticleCategory category, List<Tag> tags) {
        this.originArticleContent = originArticleContent;
        this.wroteArticleContent = wroteArticleContent;
        this.htmlContents = htmlContents;
        this.url = uri;
        this.category = category;
        this.indexingStatus = IndexingStatus.NOT_INDEXED;

        Optional.ofNullable(tags)
                .orElse(Collections.emptyList())
                .forEach(this::addTagToArticleTags);
    }

    public static Article fail(String uri, String errorMessage) {
        Article failedArticle = Article.builder()
                .uri(uri)
                .build();

        failedArticle.failIndexing(errorMessage);

        return failedArticle;
    }

    public void successIndexing() {
        this.indexingStatus = IndexingStatus.INDEXED;
        this.indexedAt = LocalDateTime.now();
    }

    public void failIndexing(String message) {
        this.indexingStatus = IndexingStatus.FAILED;
        this.indexingFailedAt = LocalDateTime.now();
        this.failedMessages = message;
    }

    public List<String> getTagNames() {
        if (articleTags == null || articleTags.isEmpty()) {
            return null;
        }

        return articleTags.stream()
                .map(ArticleTag::getTagName)
                .collect(Collectors.toList());
    }

    private void addTagToArticleTags(Tag tag){
        this.articleTags.add(
                ArticleTag.builder()
                        .article(this)
                        .tag(tag)
                        .build()
        );
    }
}
