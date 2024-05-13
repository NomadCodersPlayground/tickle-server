package com.techblogfinder.api.article.enumerable;

public enum ArticleCategory {
    EMPTY,
    GEULTTO_REVIEWS,
    TECH,
    UDEMY_REVIEW,
    ETC,
    LIFE,
    CULTURE,
    CAREER,
    CODE_TREE_REVIEW,
    PROJECT
    ;

    public static ArticleCategory of(String value) {
        if (value.isBlank()) {
            return ArticleCategory.EMPTY;
        }

        switch (value) {
            case "글또 참여 후기":
                return ArticleCategory.GEULTTO_REVIEWS;
            case "기술 & 언어":
                return ArticleCategory.TECH;
            case "유데미 후기":
                return ArticleCategory.UDEMY_REVIEW;
            case "코드트리 x 글또 블로그 챌린지":
                return ArticleCategory.CODE_TREE_REVIEW;
            case "프로젝트":
                return ArticleCategory.PROJECT;
            case "일상 & 생각":
                return ArticleCategory.LIFE;
            case "기타":
                return ArticleCategory.ETC;
            case "취준 & 이직":
                return ArticleCategory.CAREER;
            case "조직 & 문화":
                return ArticleCategory.CULTURE;
            default:
                throw new IllegalArgumentException(
                        String.format("Conversion error: '%s' does not correspond to any known enumeration type.", value)
                );
        }
    }
}
