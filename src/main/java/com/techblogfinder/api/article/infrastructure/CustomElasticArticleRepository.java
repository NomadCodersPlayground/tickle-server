package com.techblogfinder.api.article.infrastructure;

import com.techblogfinder.api.article.domain.Article;
import com.techblogfinder.api.article.domain.ArticleDocument;
import com.techblogfinder.api.article.domain.ArticleSortOption;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface CustomElasticArticleRepository {
    Slice<ArticleDocument> searchByArticleContents(String keywords, Pageable pageable);

    Slice<ArticleDocument> findAll(Pageable pageable, ArticleSortOption articleSortOption);
}
