package com.techblogfinder.api.article.infrastructure;

import com.techblogfinder.api.article.domain.ArticleDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ArticleDocumentRepository extends ElasticsearchRepository<ArticleDocument, String> {
}
