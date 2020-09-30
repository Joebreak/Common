package org.joe.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VideoFormat {

    private double duration;
    private long size;
    @JsonProperty("bit_rate")
    private long bitRate;

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getBitRate() {
        return bitRate;
    }

    public void setBitRate(long bitRate) {
        this.bitRate = bitRate;
    }

}
