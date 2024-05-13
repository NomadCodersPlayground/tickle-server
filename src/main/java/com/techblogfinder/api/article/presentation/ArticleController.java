package com.techblogfinder.api.article.presentation;

import com.techblogfinder.api.article.application.ArticleService;
import com.techblogfinder.api.article.dto.request.SaveArticleRequest;
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

    @PostMapping("")
    public ResponseEntity<String> saveAll(@RequestBody SaveArticleRequest saveArticleRequest) {
        try {
            articleService.save(saveArticleRequest);
        } catch (URISyntaxException | IOException | InterruptedException | IllegalArgumentException e) {
            articleService.fail(saveArticleRequest, e);
        }

        return ResponseEntity.ok("Success to save articles");
    }
}
