package com.techblogfinder.api.common.model;


import lombok.Builder;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class AlarmMessage {
    private final String message;
    private final String exceptionType;
    private final List<String> stackTrace;

    @Builder
    public AlarmMessage(String message, String exceptionType, List<String> stackTrace) {
        this.message = message;
        this.exceptionType = exceptionType;
        this.stackTrace = stackTrace;
    }

    public static AlarmMessage of(Exception e) {
        return AlarmMessage.builder()
                .message(e.getMessage())
                .exceptionType(e.getClass().getName())
                .stackTrace(Arrays.stream(e.getStackTrace())
                        .map(StackTraceElement::toString)
                        .collect(Collectors.toList()))
                .build();
    }

    public String toJson() {
        return  "{\n" +
                "  \"text\": \"Error Alarm\",\n" +
                "  \"attachments\": [\n" +
                "    {\n" +
                "      \"color\": \"#FF0000\",\n" +
                "      \"fields\": [\n" +
                "        {\n" +
                "          \"title\": \"Message\",\n" +
                "          \"value\": \"" + message + "\",\n" +
                "          \"short\": false\n" +
                "        },\n" +
                "        {\n" +
                "          \"title\": \"Exception Type\",\n" +
                "          \"value\": \"" + exceptionType + "\",\n" +
                "          \"short\": false\n" +
                "        },\n" +
                "        {\n" +
                "          \"title\": \"Stack Trace\",\n" +
                "          \"value\": \"" + stackTrace + "\",\n" +
                "          \"short\": false\n" +
                "        }\n" +
                "      ]\n" +
                "    }\n" +
                "  ]\n" +
                "}";
    }
}
