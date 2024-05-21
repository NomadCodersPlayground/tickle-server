package com.techblogfinder.api.article.presentation;

import com.techblogfinder.api.article.application.ArticleService;
import com.techblogfinder.api.article.dto.request.SaveArticleRequest;
import com.techblogfinder.api.common.utils.CSVReader;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "아티클 API 리스트", description = "아티클과 관련된 API 리스트")
@RequestMapping("/api/v1/articles")
public class ArticleController {

    private final ArticleService articleService;

    @PostMapping("")
    @ApiResponse(
            responseCode = "200",
            description = "아티클 저장 완료시",
            content = @Content(
                    mediaType = "text/plain",
                    schema = @Schema(example = "Success to save articles")
            )
    )
    @Operation(summary = "아티클 저장", description = "단일 아티클을 저장합니다.")
    public ResponseEntity<String> saveAll(@RequestBody SaveArticleRequest saveArticleRequest) {
        try {
            articleService.save(saveArticleRequest);
        } catch (IOException | IllegalArgumentException e) {
            articleService.fail(saveArticleRequest, e);
        }

        return ResponseEntity.ok("Success to save articles");
    }

    @PostMapping("/bulk")
    @ApiResponse(
            responseCode = "200",
            description = "아티클 일괄 저장 완료시",
            content = @Content(
                    mediaType = "text/plain",
                    schema = @Schema(example = "Success to bulk save articles")
            )
    )
    @Operation(summary = "아티클 일괄 저장", description = "다수의 아티클을 저장합니다.")
    public ResponseEntity<String> bulkSave(@RequestPart MultipartFile file) {
        List<SaveArticleRequest> articleRequests = CSVReader.convertToModel(file, SaveArticleRequest.class);

        articleService.saveAll(articleRequests);

        return ResponseEntity.ok("Success to bulk save articles");
    }
}
