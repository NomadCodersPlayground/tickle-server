package com.techblogfinder.api.common.enumrable;

import lombok.Getter;

@Getter
public enum BucketName {
    ARTICLE_META_IMAGE("article-images");

    final String value;

    BucketName(String value) {
        this.value = value;
    }
}
