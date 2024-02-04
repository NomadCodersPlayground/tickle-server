package com.techblogfinder.api.common.utils;

import com.techblogfinder.api.common.dto.OpenGraphMetaInfo;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class OpenGraphExtractor {

    public static OpenGraphMetaInfo getOpenGraphMetaInfo(String html) {
        Document doc = Jsoup.parse(html);

        String ogTitle = getMetaTagContent(doc, "og:title");
        String ogDescription = getMetaTagContent(doc, "og:description");
        String ogImage = getMetaTagContent(doc, "og:image");

        return new OpenGraphMetaInfo(ogTitle, ogDescription, ogImage);
    }

    private static String getMetaTagContent(Document document, String attr) {
        Elements elements = document.select("meta[property=" + attr + "]");
        for (Element element : elements) {
            return element.attr("content");
        }
        return null;
    }
}
