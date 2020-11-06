package org.joe.model.response;

import java.util.List;

import org.joe.model.PathItem;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FolderInfoResponse {

    @JsonProperty("files")
    private List<PathItem> items;

    public List<PathItem> getItems() {
        return items;
    }

    public void setItems(List<PathItem> items) {
        this.items = items;
    }

}
