package com.techblogfinder.api.article.dto.request;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import com.opencsv.bean.CsvDate;
import com.techblogfinder.api.article.domain.ArticleDocument;
import com.techblogfinder.api.article.enumerable.ArticleCategory;
import com.techblogfinder.api.common.converter.StringToListConverter;
import com.techblogfinder.api.common.dto.OpenGraphMetaInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class SaveArticleRequest {
    @CsvBindByName(column = "user_id")
    @Schema(description = "사용자 아이디", example = "U04LA9X6C93")
    private String userId;

    @CsvBindByName(column = "username")
    @Schema(description = "사용자 이름", example = "홍길동")
    private String userName;

    @CsvBindByName(column = "title")
    @Schema(description = "글 제목", example = "글또 8기 다짐글")
    private String title;

    @CsvBindByName(column = "content_url")
    @Schema(description = "글 URL", example = "https://blog.naver.com/techblogfinder/222222222222")
    private String url;

    @CsvBindByName(column = "dt")
    @CsvDate(value = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "작성날짜 (포맷: 'yyyy-MM-dd HH:mm:ss')", example = "2024-01-01 13:00:00")
    private LocalDateTime dueDate;

    @CsvBindByName(column = "category")
    @Schema(description = "카테고리", example = "일상 & 생각")
    private String category;

    @CsvBindByName(column = "description")
    @Schema(description = "글 설명", example = "진행중인 토이 프로젝트를 첫 배포하면서...")
    private String description;

    @CsvCustomBindByName(column = "tags", converter = StringToListConverter.class)
    @Schema(description = "태그", example = "[\"토이프로젝트\", \"배포\", \"글또\"]")
    private List<String> tags;

    public ArticleDocument toEntity(String content, OpenGraphMetaInfo openGraphMetaInfo) {
        return ArticleDocument.builder()
                .userId(userId)
                .userName(userName)
                .title(title)
                .url(url)
                .dueDate(dueDate)
                .category(ArticleCategory.of(category))
                .description(description)
                .tags(tags)
                .content(content)
                .ogTitle(openGraphMetaInfo.getTitle())
                .ogDescription(openGraphMetaInfo.getDescription())
                .ogImage(openGraphMetaInfo.getImageUrl())
                .build();
    }
}
