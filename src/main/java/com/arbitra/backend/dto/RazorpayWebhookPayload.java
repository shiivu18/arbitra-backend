package com.arbitra.backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RazorpayWebhookPayload {

    private String event;
    
    @JsonProperty("contains")
    private List<String> contains;

    private Map<String, Object> payload;

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public List<String> getContains() {
        return contains;
    }

    public void setContains(List<String> contains) {
        this.contains = contains;
    }

    public Map<String, Object> getPayload() {
        return payload;
    }

    public void setPayload(Map<String, Object> payload) {
        this.payload = payload;
    }
}