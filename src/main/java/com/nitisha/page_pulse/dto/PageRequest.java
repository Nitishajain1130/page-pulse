package com.nitisha.page_pulse.dto;

import jakarta.validation.constraints.NotBlank;

public class PageRequest {

    @NotBlank(message = "URL is required")
    private String url;

    public PageRequest() {
    }

    public PageRequest(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}