package com.techblogfinder.api.common.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.Arrays;

public class HtmlParser {

    public static String  findElementValuesByTags(String html, String[] tags) {
        StringBuilder elementsText = new StringBuilder();
        Document doc = Jsoup.parse(html);

        Arrays.stream(tags).forEach(tag -> {
            Elements elements = doc.getElementsByTag(tag);
            elementsText.append(getTextByElements(elements));
        });

        return elementsText.toString();
    }

    private static String getTextByElements(Elements elements) {
        StringBuilder tagText = new StringBuilder();

        elements.forEach(element -> {
            tagText.append(element.text());
        });

        return tagText.toString();
    }
}
