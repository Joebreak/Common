package org.joe.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VideoStream {

    private int index;
    @JsonProperty("codec_name")
    private String codecName;
    @JsonProperty("codec_type")
    private String videoTypeString;
    private int width;
    private int height;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getCodecName() {
        return codecName;
    }

    public void setCodecName(String codecName) {
        this.codecName = codecName;
    }

    public String getVideoTypeString() {
        return videoTypeString;
    }

    public void setVideoTypeString(String videoTypeString) {
        this.videoTypeString = videoTypeString;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

}
