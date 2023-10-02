package com.tutorial.RestApiTutorial;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Transcript {
    @JsonProperty
    private String audio_url;
    @JsonProperty //maps json property to Java field
    private String id;
    @JsonProperty
    private String status;
    @JsonProperty
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAudio_url() {
        return audio_url;
    }

    public void setAudio_url(String audio_url) {
        this.audio_url = audio_url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
