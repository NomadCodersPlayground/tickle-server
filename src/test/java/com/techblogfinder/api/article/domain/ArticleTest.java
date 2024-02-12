package com.techblogfinder.api.article.domain;

import com.techblogfinder.api.article.enumerable.ArticleCategory;
import com.techblogfinder.api.article.enumerable.IndexingStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ArticleTest {

    @Test
    @DisplayName("아티클 생성시 인덱스 되지 않은 상태로 저장")
    void successBuild() {

        // given
        String htmlContents = "<h1>helloWorld</h1>";
        String uri = "https://abc.com";
        String description = "블로그 설명";
        ArticleCategory articleCategory = ArticleCategory.TEAM_CULTURE;
        List<Tag> tags = new ArrayList<>();

        // when
        Article article = Article.builder()
                .originArticleContent(getTestOriginArticleContent())
                .wroteArticleContent(getWroteArticleContent())
                .htmlContents(htmlContents)
                .uri(uri)
                .description(description)
                .category(articleCategory)
                .tags(tags)
                .build();

        // then
        assertThat(article.getOriginArticleContent()).isEqualTo(getTestOriginArticleContent());
        assertThat(article.getIndexingStatus()).isEqualTo(IndexingStatus.NOT_INDEXED);
        assertThat(article.getWroteArticleContent()).isEqualTo(getWroteArticleContent());
        assertThat(article.getHtmlContents()).isEqualTo(htmlContents);
        assertThat(article.getUrl()).isEqualTo(uri);
        assertThat(article.getCategory()).isEqualTo(articleCategory);
        assertThat(article.getArticleTags()).isEmpty();
    }

    private OriginArticleContent getTestOriginArticleContent() {
        return new OriginArticleContent(
                "ogTitle",
                "ogDescription",
                "ogMainImage.jpg"
        );

    }

    private WroteArticleContent getWroteArticleContent() {
        return new WroteArticleContent(
                "wroteTitle",
                "wroteDescription"
        );
    }

    @Test
    @DisplayName("실패한 아티클 생성")
    public void failedArticle() {
        // given
        String uri = "www.naver.com";
        String errorMessage = "failed scrapping Uri";

        // when
        Article faiedArticle = Article.fail(uri, errorMessage);

        // then
        assertThat(faiedArticle.getFailedMessages()).isEqualTo(errorMessage);
        assertThat(faiedArticle.getUrl()).isEqualTo(uri);
    }
}
