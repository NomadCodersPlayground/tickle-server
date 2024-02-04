package com.techblogfinder.api.article.infrastructure.impl;

import com.techblogfinder.api.article.domain.ArticleDocument;
import com.techblogfinder.api.article.domain.ArticleSortOption;
import com.techblogfinder.api.article.infrastructure.CustomElasticArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;

import java.util.List;

@RequiredArgsConstructor
public class CustomElasticArticleRepositoryImpl implements CustomElasticArticleRepository {
    private final ElasticsearchOperations elasticsearchOperations;

    @Override
    public Slice<ArticleDocument> searchByArticleContents(String searchTerm, Pageable pageable) {
        NativeQuery nativeQuery = NativeQuery.builder().withQuery(query -> {
            query.bool(boolQuery -> {

                    boolQuery.filter(filterQuery -> {
                        return filterQuery.match(matchQuery -> {
                            List.of("articleContents","articleContents.english").forEach(field -> {
                                matchQuery.field(field)
                                        .query(searchTerm);
                            });

                            return matchQuery;
                        });
                    });


                return boolQuery;
            });

            return query;
        }).build();

        List<SearchHit<ArticleDocument>> searchResult =  elasticsearchOperations.search(nativeQuery, ArticleDocument.class).getSearchHits();

        List<ArticleDocument> articleDocuments = searchResult.stream().map(SearchHit::getContent)
                .toList();

        return new SliceImpl<>(articleDocuments, pageable, hasNext(pageable, articleDocuments));
    }

    @Override
    public Slice<ArticleDocument> findAll(Pageable pageable, ArticleSortOption sortOption) {
        NativeQuery findAllQuery = NativeQuery.builder()
                .withSort(getArticleDocumentSort(sortOption))
                .build();

        List<ArticleDocument> articleDocuments = elasticsearchOperations.search(findAllQuery, ArticleDocument.class)
                .map(SearchHit::getContent)
                .stream().toList();

        return new SliceImpl<>(articleDocuments, pageable, hasNext(pageable, articleDocuments));
    }

    private Sort getArticleDocumentSort(ArticleSortOption sortOption) {
        if (sortOption == null) {
            return Sort.by("createdAt").descending();
        }

        switch (sortOption) {
            case VIEWS:
                return Sort.by("views").descending();
            case TODAY_VIEWS:
                return Sort.by("todayViews").descending();
            case WEEKLY_VIEWS:
                return Sort.by("weeklyViews").descending();
            case MONTHLY_VIEWS:
                return Sort.by("monthlyViews").descending();
            default:
                return Sort.by("createdAt").descending();
        }
    }

    private boolean hasNext(Pageable pageable, List<ArticleDocument> articleDocuments) {
        return articleDocuments.size() > pageable.getPageSize();
    }
}
