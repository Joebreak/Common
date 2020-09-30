package org.joe.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VideoData {

    @JsonProperty("streams")
    private List<VideoStream> videoStreams;
    @JsonProperty("format")
    private VideoFormat format;

    public List<VideoStream> getVideoStreams() {
        return videoStreams;
    }

    public void setVideoStreams(List<VideoStream> videoStreams) {
        this.videoStreams = videoStreams;
    }

    public VideoFormat getFormat() {
        return format;
    }

    public void setFormat(VideoFormat format) {
        this.format = format;
    }

}
