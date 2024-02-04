package com.techblogfinder.api.article.enumerable;

public enum ArticleCategory {
    TECHNOLOGY,
    PROJECT,
    DAILY_LIFE,
    JOB,
    ETC,
    TEAM_CULTURE
    ;

    public static ArticleCategory of(String value) {
        switch (value) {
            case "기술 & 언어":
                return ArticleCategory.TECHNOLOGY;
            case "프로젝트":
                return ArticleCategory.PROJECT;
            case "일상 & 생각":
                return ArticleCategory.DAILY_LIFE;
            case "기타":
                return ArticleCategory.ETC;
            case "취준 & 이직":
                return ArticleCategory.JOB;
            case "조직 & 문화":
                return ArticleCategory.TEAM_CULTURE;
            default:
                throw new IllegalArgumentException(
                        String.format("Conversion error: '%s' does not correspond to any known enumeration type.", value)
                );
        }
    }
}
