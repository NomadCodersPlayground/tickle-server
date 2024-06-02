package com.techblogfinder.api.article.application;

import com.techblogfinder.api.article.domain.ArticleDocument;
import com.techblogfinder.api.article.infrastructure.ArticleDocumentRepository;
import com.techblogfinder.api.article.dto.request.SaveArticleRequest;
import com.techblogfinder.api.common.components.SlackMessageSender;
import com.techblogfinder.api.common.dto.OpenGraphMetaInfo;
import com.techblogfinder.api.common.model.AlarmMessage;
import com.techblogfinder.api.common.utils.HtmlParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class ArticleService {
    private final SlackMessageSender slackMessageSender;

    private final ArticleDocumentRepository articleDocumentRepository;

    private final String[] PARSING_TAGS = new String[]{"h1", "h2", "h3", "h4", "h5", "p"};

    public void save(SaveArticleRequest saveArticleRequest) throws IOException, IllegalArgumentException {
        Document htmlDocument = fetchHtmlContents(saveArticleRequest.getUrl());

        OpenGraphMetaInfo openGraphMetaInfo = parseOpenGraphMetaInfo(htmlDocument);

        String contents = parseHtmlContents(htmlDocument);

        articleDocumentRepository.save(saveArticleRequest.toEntity(contents, openGraphMetaInfo));
    }

    @Async
    public void saveAll(List<SaveArticleRequest> saveArticleRequests) {
        List< ArticleDocument> articleDocuments = new ArrayList<>();

        saveArticleRequests.forEach(saveArticleRequest -> {
            try {
                Document htmlContents = fetchHtmlContents(saveArticleRequest.getUrl());

                OpenGraphMetaInfo openGraphMetaInfo = parseOpenGraphMetaInfo(htmlContents);

                String contents = parseHtmlContents(htmlContents);

                articleDocuments.add(saveArticleRequest.toEntity(contents, openGraphMetaInfo));
            } catch (IOException | IllegalArgumentException e) {
                fail(saveArticleRequest, e);
            }
        });

        articleDocumentRepository.saveAll(articleDocuments);
    }

    private Document fetchHtmlContents(String url) throws IOException{

        return Jsoup.connect(url).get();
    }

    private String parseHtmlContents(Document htmlDocument) {
        return HtmlParser.findElementValuesByTags(htmlDocument, PARSING_TAGS);
    }

    private OpenGraphMetaInfo parseOpenGraphMetaInfo(Document htmlDocument) {
        OpenGraphMetaInfo openGraphMetaInfo = HtmlParser.findOpenGraphMetaInfo(htmlDocument);

        if (openGraphMetaInfo.isEmpty()) {
            throw new IllegalArgumentException("OpenGraphMetaInfo is empty");
        }

        return openGraphMetaInfo;
    }


    public void fail(SaveArticleRequest saveArticleRequest, Exception e) {
        log.error("Failed to save article: {}", saveArticleRequest.getUrl());
        slackMessageSender.sendError(AlarmMessage.of(e));
    }
}
