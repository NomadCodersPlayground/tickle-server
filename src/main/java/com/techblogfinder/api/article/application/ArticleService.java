package com.techblogfinder.api.article.application;

import com.techblogfinder.api.article.domain.*;
import com.techblogfinder.api.article.domain.repository.ArticleRepository;
import com.techblogfinder.api.article.domain.repository.TagRepository;
import com.techblogfinder.api.article.dto.request.SaveArticleRequest;
import com.techblogfinder.api.article.enumerable.IndexingStatus;
import com.techblogfinder.api.article.infrastructure.ArticleDocumentRepository;
import com.techblogfinder.api.common.dto.OpenGraphMetaInfo;
import com.techblogfinder.api.common.utils.HtmlParser;
import com.techblogfinder.api.common.utils.HttpClientBuilder;
import com.techblogfinder.api.common.utils.OpenGraphExtractor;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ArticleService {
    private final TagRepository tagRepository;

    private final ArticleRepository articleRepository;
    private final ArticleDocumentRepository articleDocumentRepository;
    private final String[] parsableTags = new String[]{"h1", "h2", "h3", "h4", "h5", "p"};

    @Transactional
    public Article save(SaveArticleRequest saveArticleRequest) throws URISyntaxException, IOException, InterruptedException {
        HttpClient httpClient = HttpClientBuilder.create();

        String htmlContents = httpClient.send(HttpClientBuilder.get(saveArticleRequest.getUri()), HttpResponse.BodyHandlers.ofString())
                .body();

        OpenGraphMetaInfo openGraphMetaInfo = OpenGraphExtractor.getOpenGraphMetaInfo(htmlContents);

        if (openGraphMetaInfo.isEmpty()) {
            throw new IllegalArgumentException("OpenGraphMetaInfo is empty");
        }

        OriginArticleContent originArticleContent = new OriginArticleContent(
                openGraphMetaInfo.getTitle(),
                openGraphMetaInfo.getDescription(),
                openGraphMetaInfo.getImageUrl()
        );

        WroteArticleContent wroteArticleContent = new WroteArticleContent(
                saveArticleRequest.getTitle(),
                saveArticleRequest.getDescription()
        );

        List<Tag> tags = findOrSaveTags(saveArticleRequest.getTagNames());

        return articleRepository.save(saveArticleRequest.toEntity(originArticleContent, wroteArticleContent, htmlContents, tags));
    }

    @Transactional
    public Article fail(SaveArticleRequest saveArticleRequest, Exception ex) {

        return articleRepository.save(saveArticleRequest.toFailedArticleEntity(ex.getMessage()));
    }

    @Transactional
    public void indexingAll() {
        List<Article> notIndexedArticles = articleRepository.findAllByStatus(IndexingStatus.NOT_INDEXED);

        List<ArticleDocument> articleDocuments = notIndexedArticles.stream().map(article -> {
            String contents = HtmlParser.findElementValuesByTags(article.getHtmlContents(), parsableTags);
            return ArticleDocument.of(article, contents);
        }).toList();

        articleDocumentRepository.saveAll(articleDocuments);

        notIndexedArticles.forEach(Article::successIndexing);
        articleRepository.saveAll(notIndexedArticles);
    }

    @Transactional
    public Slice<ArticleDocument> search(Pageable page, String keyword) {
        return articleDocumentRepository.searchByArticleContents(keyword, page);
    }

    @Transactional
    public Slice<ArticleDocument> findAll(Pageable page, ArticleSortOption sortOption) {
        return articleDocumentRepository.findAll(page, sortOption);
    }

    @Transactional
    public void increaseViewCount(Long id) {
        ArticleDocument article = articleDocumentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 아이디의 게시글이 존재하지 않습니다."));

        article.increaseViewCount();

        articleDocumentRepository.save(article);
    }

    private List<Tag> findOrSaveTags(List<String> tagNames) {
        if (tagNames == null) {
            return Collections.emptyList();
        }

        return tagNames.stream()
                .map(name -> tagRepository.findByName(name)
                        .orElseGet(() -> tagRepository.save(Tag.create(name))))
                .collect(Collectors.toList());
    }
}
