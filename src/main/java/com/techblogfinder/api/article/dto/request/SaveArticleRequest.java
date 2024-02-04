package com.techblogfinder.api.article.dto.request;

import com.opencsv.bean.CsvBindByName;
import com.techblogfinder.api.article.domain.Article;
import com.techblogfinder.api.article.domain.OriginArticleContent;
import com.techblogfinder.api.article.domain.Tag;
import com.techblogfinder.api.article.domain.WroteArticleContent;
import com.techblogfinder.api.article.enumerable.ArticleCategory;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public class SaveArticleRequest {
    @CsvBindByName(column = "Url",required = true)
    private String uri;
    @CsvBindByName(column = "Title", required = true)
    private String title;
    @CsvBindByName(column = "Category", required = true)
    private String category;
    @CsvBindByName(column = "Description")
    private String description;

    @CsvBindByName(column = "Tag1")
    private String tag1;

    @CsvBindByName(column = "Tag2")
    private String tag2;

    @CsvBindByName(column = "Tag3")
    private String tag3;

    public Article toEntity(
            OriginArticleContent originArticleContent,
            WroteArticleContent wroteArticleContent,
            String htmlContents,
            List<Tag> tags
    ) {

        return Article.builder()
                .uri(this.uri)
                .originArticleContent(originArticleContent)
                .wroteArticleContent(wroteArticleContent)
                .htmlContents(htmlContents)
                .category(ArticleCategory.of(this.category))
                .description(this.description)
                .tags(tags)
                .build();
    }

    public Article toFailedArticleEntity(String failedReason) {

        return Article.fail(this.uri, failedReason);
    }

    public List<String> getTagNames() {
        return Arrays.asList(tag1, tag2, tag3);
    }
}
