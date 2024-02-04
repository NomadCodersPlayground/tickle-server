package com.techblogfinder.api.article.infrastructure;

import org.springframework.stereotype.Component;

public interface ArticleDocumentRepository extends ElasticArticleRepository, CustomElasticArticleRepository {
}
