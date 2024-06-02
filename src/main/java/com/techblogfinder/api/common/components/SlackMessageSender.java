package com.techblogfinder.api.common.components;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.techblogfinder.api.common.model.AlarmMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class SlackMessageSender {
    @Value("${slack.webhook-url}")
    private String SLACK_WEBHOOK_URL;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public void sendError(AlarmMessage alarmMessage) {
        RestTemplate restTemplate = new RestTemplate();

        restTemplate.postForEntity(SLACK_WEBHOOK_URL, alarmMessage.toJson(), String.class);
    }
}
