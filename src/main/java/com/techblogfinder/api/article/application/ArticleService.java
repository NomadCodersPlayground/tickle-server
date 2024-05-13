package com.techblogfinder.api.article.application;

import com.techblogfinder.api.article.infrastructure.ArticleDocumentRepository;
import com.techblogfinder.api.article.dto.request.SaveArticleRequest;
import com.techblogfinder.api.common.dto.OpenGraphMetaInfo;
import com.techblogfinder.api.common.utils.HtmlParser;
import com.techblogfinder.api.common.utils.HttpRequestBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;


@Slf4j
@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleDocumentRepository articleDocumentRepository;

    private final String[] PARSING_TAGS = new String[]{"h1", "h2", "h3", "h4", "h5", "p"};

    public void save(SaveArticleRequest saveArticleRequest) throws URISyntaxException, IOException, InterruptedException {
        String htmlContents = fetchHtmlContents(saveArticleRequest.getUrl());

        OpenGraphMetaInfo openGraphMetaInfo = HtmlParser.findOpenGraphMetaInfo(htmlContents);

        if (openGraphMetaInfo.isEmpty()) {
            throw new IllegalArgumentException("OpenGraphMetaInfo is empty");
        }

        String contents = HtmlParser.findElementValuesByTags(htmlContents, PARSING_TAGS);

        articleDocumentRepository.save(saveArticleRequest.toEntity(contents, openGraphMetaInfo));
    }

    public void fail(SaveArticleRequest saveArticleRequest, Exception e) {
        log.error("Failed to save article: ", e);
    }

    private String fetchHtmlContents(String url) throws IOException, InterruptedException, URISyntaxException {
        HttpClient client = HttpRequestBuilder.create();
        HttpResponse<String> response = client.send(HttpRequestBuilder.build(url), HttpResponse.BodyHandlers.ofString());

        return response.body();
    }
}
