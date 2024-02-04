package com.techblogfinder.api.common.dto;

import lombok.Getter;

@Getter
public class OpenGraphMetaInfo {
    private String title;
    private String description;
    private String imageUrl;

    public OpenGraphMetaInfo(String title, String description, String imageUrl) {
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public boolean isEmpty() {
        return title == null && description == null && imageUrl == null;
    }
}
