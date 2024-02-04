package com.techblogfinder.api.article.presentation;

import com.techblogfinder.api.article.application.ArticleService;
import com.techblogfinder.api.article.domain.ArticleDocument;
import com.techblogfinder.api.article.domain.ArticleSortOption;
import com.techblogfinder.api.article.dto.request.SaveArticleRequest;
import com.techblogfinder.api.article.dto.response.GetArticleResponse;
import com.techblogfinder.api.common.utils.CSVReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/articles")
public class ArticleController {

    private final ArticleService articleService;

    @GetMapping("")
    public ResponseEntity<Slice<GetArticleResponse>> findAll(Pageable pageable, @RequestParam(required = false) ArticleSortOption sortOption) {
        Slice<GetArticleResponse> articles = articleService.findAll(pageable, sortOption)
                .map(GetArticleResponse::new);

        return ResponseEntity.ok(articles);
    }

    @PostMapping("/file")
    public ResponseEntity<String> saveAll(@RequestPart MultipartFile file) {
        try {
            List<SaveArticleRequest> articleRequests = CSVReader.convertToModel(file, SaveArticleRequest.class);

            for (SaveArticleRequest saveArticleRequest : articleRequests) {
                try {
                    articleService.save(saveArticleRequest);
                } catch (URISyntaxException | IOException | InterruptedException | IllegalArgumentException e) {
                    articleService.fail(saveArticleRequest, e);
                }
            }
        } catch (IllegalArgumentException ex) {
            log.error("Failed to upload article: ", ex);
            return ResponseEntity.badRequest()
                    .body("Failed article upload");
        }

        return ResponseEntity.ok("successes articles upload");
    }

    @PostMapping("/indexing")
    public ResponseEntity<String> indexingAll() {
        articleService.indexingAll();

        return ResponseEntity.ok("successful articles indexing");
    }

    @GetMapping("/search")
    public ResponseEntity<Slice<ArticleDocument>> search(Pageable pageable, @RequestParam String keywords) {
        return ResponseEntity.ok(articleService.search(pageable, keywords));
    }


    @PostMapping("/{id}/views/increase")
    public ResponseEntity<String> increaseViewCount(@PathVariable Long id) {
        articleService.increaseViewCount(id);

        return ResponseEntity.ok("success increase view count");
    }
}
